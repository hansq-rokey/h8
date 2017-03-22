/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.web.afterSale;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.User;
import com.ibaixiong.erp.exception.DepponException;
import com.ibaixiong.erp.exception.LowStocksException;
import com.ibaixiong.erp.service.afterSale.MallAfterSaleService;
import com.ibaixiong.erp.service.sys.UserService;
import com.ibaixiong.erp.web.util.BarcodeUtil;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.commons.entity.Pager;
import com.papabear.order.api.AfterSaleService;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallAfterSalesService;
import com.papabear.order.entity.MallAfterSalesServiceItem;
import com.papabear.order.entity.MallAfterSalesServiceLog;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
import com.papabear.product.entity.MallBasicCategoryModelFormat;
import com.papabear.product.entity.MallProduct;

/**
 * 售后管理
 * @description
 * @author zhaolei
 * @create 2015年8月18日
 */
@Controller
@RequestMapping("/aftetSale")
public class AfterSaleAction {
	@Resource
	MallAfterSaleService mallAfterSaleService;
	@Resource
	private AfterSaleService afterSaleService;
	@Resource
	private UserService userService;
	@Resource
	private ProductQueryService productQueryService;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private OrderService orderService;
	
	/**
	 * 订单列表查询
	 * @author zhaolei
	 * @date 2015年8月18日
	 * @param queryType 查询类型 1.退货单 2.换货单3.维修单
	 * @param queryStr 查询输入的内容
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryList.html")
	public String queryList(
			@RequestParam(value = "queryType", required = false) Integer queryType,
			@RequestParam(value = "queryStr", required = false) String queryStr,
			@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			HttpServletRequest request,
			Model model){
		if(queryType == null ){
			queryType = 1;
		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("queryType", queryType);
//		map.put("queryStr", queryStr);
//		map.put("pageNo", pageNo);
//		Pager<MallAfterSalesService> pager = afterSaleService.queryAfterSalesServices(queryStr, null, null,null, pageNo, 10);
		Pager<MallAfterSalesService> pager = afterSaleService.queryAfterSalesServices(queryStr, queryType.byteValue(), null, null, null, pageNo, 10);
		for(MallAfterSalesService service:pager.getList()){
			List<MallAfterSalesServiceItem> serviceItems=afterSaleService.queryAfterSalesServiceItems(service.getId());
			for(MallAfterSalesServiceItem item:serviceItems){
				MallProduct product=productQueryService.getProduct(item.getProductId());
				MallBasicCategoryModelFormat format=categoryQueryService.getCategoryModelFormat(item.getProductModelFormatId());
				item.setProductTitle(product==null?null:product.getTitle());
				item.setProductModelFormatName(format==null?null:format.getName());
				item.setModelFormatNumber(format==null?null:format.getCategoryModelFormatNumber());
				item.setPics(productQueryService.queryPics(null, item.getProductModelFormatId(), (short)2));
			}
			service.setItemList(serviceItems);
			User user=userService.getuUser(service.getUserId());
			if(null==user)continue;
			service.setBxNum(user.getBxNum());
			service.setUserName(user.getUserName());
			service.setInformation(orderService.getRevicerByUserIdAndOrderNumber(null, service.getServiceNumber()));
		}
//		List<MallAfterSalesService> dataList = mallAfterSaleService.queryList(map);
		// mallAfterSaleItemService.queryList(map)
//		PageInfo<MallAfterSalesService> pageInfo=new PageInfo<MallAfterSalesService>(dataList);
		model.addAttribute("pageInfo",pager);
		model.addAttribute("dataList",pager.getList());
		model.addAttribute("queryStr",queryStr);
		Map<String, Object> mapData=new HashMap<String, Object>();
		mapData.put("orders", pager.getList());
		System.out.println(JSON.toJSONString(ResponseResult.result(1,"",mapData)));
		model.addAttribute("json", JSON.toJSONString(ResponseResult.result(1,"",mapData)));
		if(queryType.intValue()==1){
			//退货单
			return "/aftersale/backOrderList";
		}
		if(queryType.intValue()==2){
			//换货单
			return "/aftersale/exchangeOrderList";
		}
		if(queryType.intValue()==3){
			//维修单
			return "/aftersale/serviceOrderList";
		}
		return "";
	}
	/**
	 * 去详情页面 
	 * @author zhaolei
	 * @date 2015年8月23日
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toOrderView.html")
	public String toOrderView(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "queryType", required = false) Integer queryType,
			HttpServletRequest request,
			Model model){
//		MallAfterSalesService order = mallAfterSaleService.get(id);
		MallAfterSalesService order = afterSaleService.getAfterSalesService(id);
		order.setInformation(orderService.getRevicerByUserIdAndOrderNumber(null, order.getOrderNumber()));
//		List<MallAfterSalesServiceLog> logs = mallAfterSaleLogService.queryListByOrderId(id);
		List<MallAfterSalesServiceLog> logs = afterSaleService.queryAfterSalesServiceLogs(id);
		for (MallAfterSalesServiceLog mallAfterSalesServiceLog : logs) {
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 10){
				model.addAttribute("status10", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 20){
				model.addAttribute("status20", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 30){
				model.addAttribute("status30", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 40){
				model.addAttribute("status40", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 50){
				model.addAttribute("status50", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 60){
				model.addAttribute("status60", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 31){
				model.addAttribute("status31", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 32){
				model.addAttribute("status32", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 41){
				model.addAttribute("status41", mallAfterSalesServiceLog);
			}
			if(mallAfterSalesServiceLog.getServiceStatus().intValue() == 42){
				model.addAttribute("status42", mallAfterSalesServiceLog);
			}
		}
		model.addAttribute("order",order);
		List<MallAfterSalesServiceItem> serviceItems=afterSaleService.queryAfterSalesServiceItems(id);
		for(MallAfterSalesServiceItem item:serviceItems){
			MallProduct product=productQueryService.getProduct(item.getProductId());
			MallBasicCategoryModelFormat format=categoryQueryService.getCategoryModelFormat(item.getProductModelFormatId());
			item.setProductTitle(product==null?null:product.getTitle());
			item.setProductModelFormatName(format==null?null:format.getName());
		}
		model.addAttribute("listItem",serviceItems);
		model.addAttribute("queryType",queryType);
		return "/aftersale/orderView";
	}
	/**
	 * 操作单据状态
	 * @author zhaolei
	 * @date 2015年8月23日
	 * @param orderId 单据ID
	 * @param orderType 单据类型
	 * @param memo 拒绝理由等需要文字描述的字段
	 * @param expressName 快递公司名字
	 * @param expressNo 快递单号
	 * @param fixMoney 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/operateOrder.html")
	public void operateOrder(
			@RequestParam(value = "serviceId", required = false) Long serviceId,
			@RequestParam(value = "status", required = false) Byte status,
			@RequestParam(value = "refuseMemo", required = false) String refuseMemo,//拒绝理由
			@RequestParam(value = "memo", required = false) String memo,
			@RequestParam(value = "expressName", required = false) String expressName,
			@RequestParam(value = "expressNo", required = false) String expressNo,
			@RequestParam(value = "fixMoney", required = false) Float fixMoney,
			HttpServletRequest request,
			HttpServletResponse response){
//		MallAfterSalesService service = new MallAfterSalesService();
//		service.setId(serviceId);
//		service.setStatus(status);//单据操作状态
		if(StringUtils.isNotBlank(refuseMemo)){
			try {
				refuseMemo = new String(refuseMemo.getBytes("iso-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			service.setRefuseMemo(refuseMemo);
		}
//		mallAfterSaleService.update(service);
		
		afterSaleService.updateAfterSalesService(status, null,refuseMemo, serviceId);
		//插入问题关闭的记录
		SysAdmin loginAdmin = WebUtil.getLoginUser(request);//当前登陆人
//		MallAfterSalesServiceLog log = new MallAfterSalesServiceLog();
//		log.setAdmin(loginAdmin);//处理人是当前登陆人
//		log.setService(service);
//		log.setCreateDateTime(new Date());
//		log.setServiceStatus(status);//本次页面回传的
//		log.setStatus(Constant.Status.NORMAL.getStatus());
//		mallAfterSaleLogService.insert(log);
		
		afterSaleService.addAfterSalesServiceLog(loginAdmin.getId(), serviceId, status);
		
		String outStr = JSON.toJSONString(ResponseResult.result(0, ""));
		System.out.println(outStr);
		PrintWriter writer = null;
        try {
       	 	writer = response.getWriter();
            writer.write(outStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
	}
	
	/**
	 * 发送商品货物快递
	 * @author zhaolei
	 * @date 2015年10月18日
	 * @param hardwares 硬件IDS
	 * @param orderID 订单
	 * @param expressId 快递公司ID
	 * @param expressNumber 快递单号如果快递走的是面单的形式则该参数为必传
	 * @param model
	 * @param request
	 * @return
	 * http://erp.ibaixiong.com/order/sendGoods.html?hardwares=45,46&orderItems=160,160&expressId=1
	 */
	@RequestMapping("/sendGoods.html")
	public void sendGoods(
			@RequestParam(value="hardwares",required=true) String hardwares,  //50,55
			@RequestParam(value="orderId",required=true) String orderId,
			@RequestParam(value="expressId",required=true) Long expressId,    //1
			@RequestParam(value="expressNumber",required=false) String expressNumber,    //SF100010010212
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response){
		String msg = "";
		int code = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map =null;
		try {
			map = mallAfterSaleService.sendGoods(hardwares, orderId, expressId, expressNumber, request);
			msg=map.get("msg").toString();
			code=Integer.parseInt(map.get("code").toString());
			if(code == 0){
				resultMap.put("number", map.get("number").toString());
				//生成条形码
				String filePath = request.getRealPath("") + "/images/saleCode/" + map.get("number").toString() + ".png";
				BarcodeUtil.createBarcode(map.get("number").toString(), filePath);
				String barcodeUrl = "../../../../images/saleCode/" + map.get("number").toString() + ".png";
				resultMap.put("orderCode", barcodeUrl);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			msg="系统出现未知错误!";
			code=999;
			if(e1 instanceof DepponException){
				msg="德邦系统出现未知错误，发货失败!";
				code=991;
			}
			if(e1 instanceof LowStocksException){
				msg="库存不足，发货失败!";
				code=992;
			}
		}
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code,msg,resultMap)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.ibaixiong.erp.web.purchase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpPurchaseOrder;
import com.ibaixiong.entity.ErpUserPermissionRelation;
import com.ibaixiong.erp.service.purchase.ErpPurchaseOrderService;
import com.ibaixiong.erp.service.sysset.ErpUserPermissionRelationService;
import com.ibaixiong.erp.util.PurchaseOrderStatusEnum;
import com.ibaixiong.erp.web.util.WebUtil;
/**
 * 
 * @author zhaolei
 * @description 采购管理相关
 * @create 2015年9月29日
 *
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseOrderAction {
	@Resource
	ErpPurchaseOrderService erpPurchaseOrderService;
	@Resource
	ErpUserPermissionRelationService erpUserPermissionRelationService;
	/**
	 * 采购管理列表页面
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list(
			@RequestParam(value = "msg", required = false) String msg,
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model, HttpServletRequest request) {
		List<ErpPurchaseOrder> list = erpPurchaseOrderService.getList(pageNo);
		PageInfo<ErpPurchaseOrder> pageInfo=new PageInfo<ErpPurchaseOrder>(list);
		model.addAttribute("list",list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("msg",msg);
		return "/purchase/purchaseList";
	}
	/**
	 * 保存采购管理对象
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/save.html")
	public String save(Model model,
			@ModelAttribute("purchase") ErpPurchaseOrder purchase,
			HttpServletRequest request) {
		erpPurchaseOrderService.save(purchase, WebUtil.getLoginUser(request));
		return "redirect:/purchase/list.html";
	}
	@RequestMapping("/del.html")
	public String del(Model model,
			@RequestParam(value = "purchaseId", required = false) Long purchaseId,
			HttpServletRequest request) {
		//删除时判断人员权限表tbl_erp_user_permission_relation中是否使用到了使用到了不允许删除，
		List<ErpUserPermissionRelation> eupList = erpUserPermissionRelationService.selectByPurchaseOrderId(purchaseId);
		if(eupList == null || eupList.size()==0){
			erpPurchaseOrderService.delete(purchaseId);//删除
			return "redirect:/purchase/list.html";
		}else{
			return "redirect:/purchase/list.html?msg=1";
		}
	}
	/**
	 * 检查是否允许修改该采购订单
	 * @author zhaolei
	 * @date 2015年10月8日
	 * @param shelfInfo
	 * @param model
	 * @param response
	 */
	@RequestMapping("/checkUpdate.html")
	public void saveshelfAjax(
			@RequestParam(value = "id", required = false) Long id,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		ErpPurchaseOrder erpPurchaseOrder = erpPurchaseOrderService.get(id);
		if(erpPurchaseOrder != null ){
			if(!erpPurchaseOrder.getStatus().equals(PurchaseOrderStatusEnum.INIT.getCode())){
				code = 2;//非初始化状态不允许修改
			}
		}else{
			//没有找到该对象
			code = 1;
		}
		PrintWriter writer = null;
        try {
       	 	writer = response.getWriter();
            writer.write(JSON.toJSONString(ResponseResult.result(code, msg)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
	}
	@RequestMapping("/getPurchaseOrder.html")
	public void getPurchaseOrder(
			@RequestParam(value = "id", required = false) Long id,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		ErpPurchaseOrder erpPurchaseOrder = erpPurchaseOrderService.get(id);
		map.put("id", erpPurchaseOrder.getId());
		map.put("orderNumber", erpPurchaseOrder.getOrderNumber());
		map.put("categoryId", erpPurchaseOrder.getCategory().getId());
		map.put("categoryName", erpPurchaseOrder.getCategory().getName());
		map.put("categoryModelId", erpPurchaseOrder.getCategoryModel().getId());
		map.put("categoryModelName", erpPurchaseOrder.getCategoryModel().getName());
		map.put("categoryModelFormatId", erpPurchaseOrder.getCategoryModelFormat().getId());
		map.put("categoryModelFormatName", erpPurchaseOrder.getCategoryModelFormat().getName());
		map.put("batch", erpPurchaseOrder.getBatch() );
		map.put("description", erpPurchaseOrder.getDescription());
		map.put("orderNum", erpPurchaseOrder.getOrderNum());
		if(erpPurchaseOrder.getTransportTime()!=null)
			map.put("transportTime", DateUtil.format(erpPurchaseOrder.getTransportTime(),"yyyy-MM-dd"));
		map.put("transportType", erpPurchaseOrder.getTransportType());
		if(erpPurchaseOrder.getEndTime()!=null)
		map.put("endTime", DateUtil.format(erpPurchaseOrder.getEndTime(),"yyyy-MM-dd"));
		PrintWriter writer = null;
        try {
       	 	writer = response.getWriter();
            writer.write(JSON.toJSONString(ResponseResult.result(code, msg,map)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
	}
}

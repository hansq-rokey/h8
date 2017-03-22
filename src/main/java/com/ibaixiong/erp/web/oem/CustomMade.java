/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.web.oem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpOemUserRelation;
import com.ibaixiong.entity.ErpSecurityCodeMacRelation;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.service.oem.ErpOemScanPackageService;
import com.ibaixiong.erp.service.oem.ErpOemUserRelationService;
import com.ibaixiong.erp.service.oem.ErpSecurityCodeMacRelationService;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.web.util.BarcodeUtil;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.commons.entity.Pager;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallOrder;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.product.api.ProductQueryService;
import com.papabear.product.entity.MallProductPic;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2016年1月1日-上午10:52:17
 */
@Controller
@RequestMapping("/oem/custommade")
public class CustomMade {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private MallOrderService mallOrderService;
	@Autowired
	ErpOemUserRelationService erpOemUserRelationService;
	@Autowired
	ErpOemScanPackageService erpOemScanPackageService;
	@Resource
	private OrderService orderService;
	@Resource
	ErpSecurityCodeMacRelationService erpSecurityCodeMacRelationService;
	@Autowired
	ErpHardwareProductService erpHardwareProductService;
	@Autowired
	ProductQueryService productQueryService;

	@RequestMapping("/orderlist.html")
	public String orderList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, Model model, HttpServletRequest request) {
		String orderNumber = request.getParameter("orderNumber");
		String status = request.getParameter("status");
		if (StringUtils.isBlank(status) && StringUtils.isBlank(orderNumber)) {
			status = "24";
		}
		List<Byte> listStatus = new ArrayList<Byte>();
		if (StringUtils.isNotBlank(status)) {
			String[] s = status.split("-");
			for (int i = 0; i < s.length; i++) {
				listStatus.add(Byte.valueOf(s[i]));
			}
		}
//		List<MallOrder> list = mallOrderService.queryByCustomMade(pageNo, PageConstant.pageSize, orderNumber, status);
		Pager<MallOrder> pager=orderService.queryCustomerOrder(orderNumber, (byte)1, listStatus, pageNo, PageConstant.pageSize);
		for(MallOrder order:pager.getList()){
			order.setOrderItems(orderService.queryOrderItems(order.getOrderNumber()));
		}
//		PageInfo<MallOrder> pageInfo = new PageInfo<MallOrder>(list);
		model.addAttribute("pageInfo", pager);
		model.addAttribute("orderList", pager.getList());
		model.addAttribute("status", status);
		if("24".equals(status)){
			return "/oem/custommade/order_list_wait";
		}else{
			return "/oem/custommade/order_list_sure";
		}
	}

	// 生成防伪码界面
	@RequestMapping("/scanqrcode_custommade.html")
	public String scanQrcodeCustomMade(Model model, HttpServletRequest request) {
		SysAdmin admin = WebUtil.getLoginUser(request);

		String orderNumber = request.getParameter("orderNumber");

		MallOrderItem orderItem =orderService.queryOrderItems(orderNumber).get(0);
		
		if("1".equals(request.getParameter("retry"))){
			erpOemScanPackageService.deleteCustomMade(orderItem);
		}

		ErpOemUserRelation erpOemUserRelation = erpOemUserRelationService.getErpOemUserRelationByAdminId(admin.getId());

		model.addAttribute("orderItem", orderItem);
		model.addAttribute("erpOemUserRelation", erpOemUserRelation);
		model.addAttribute("orderNumber", orderNumber);
//		model.addAttribute("isSmart", orderItem.getIsSmart());TODO

		// 如果是非智能类型，就显示一张固定的二维码来扫描TODO
//		if (orderItem.getIsSmart() != 1) {
//			model.addAttribute("notSmartPic", "http://image.ibaixiong.com/erp/0000000000000000.png");
//		}

		return "/oem/custommade/scanuniquecode";
	}

	// 生成防伪码ajax请求
	@RequestMapping("/scanqrcode_custommade_ajax.html")
	@ResponseBody
	public String scanQrcodeAjax(Model model, HttpServletRequest request) {
		String msg = "";
		try {
			SysAdmin admin = WebUtil.getLoginUser(request);
			String orderNumber = request.getParameter("orderNumber");
			String scanMacNumber = request.getParameter("scanMacNumber");
//			MallOrder order = mallOrderService.getOrderAndItemByOrderNumber(orderNumber);
			MallOrder order = orderService.getMallOrder(orderNumber);
			order.setOrderItems(orderService.queryOrderItems(orderNumber));
			MallOrderItem orderItem = order.getOrderItems().get(0);
			if (!"1".equals(request.getParameter("isSmart"))) {
				if (CodeUtil.isSmart(scanMacNumber)) {
					throw new Exception("非智能类目下扫描到智能类型的码！");
				}
				scanMacNumber = CodeUtil.getNotSmartMacNumber();
			}
			ErpOemUserRelation erpOemUserRelation = erpOemUserRelationService.getErpOemUserRelationByAdminId(admin.getId());

			if (StringUtils.isNotBlank(scanMacNumber)) {
				String uniqueCode = erpOemScanPackageService.scanPackageCustomMade(scanMacNumber, orderItem, erpOemUserRelation,request.getRemoteAddr());
				if (StringUtils.isNotBlank(uniqueCode)) {
					// 生成二维码
					String filePath = request.getRealPath("") + "/images/qrcode/";
					String fileName = uniqueCode + ".png";
					String logoPath = request.getRealPath("") + "/images/logo-18x18.png";
					String content = "http://www.ibaixiong.com/h5/scan-record.html?code=" + scanMacNumber;
					BarcodeUtil.createQrCode(content, filePath, fileName);
					String barcodeUrl = "../../../../images/qrcode/" + uniqueCode + ".png";
					return "{\"code\":0,\"qrcode\":\"" + scanMacNumber + "\",\"codeUrl\":\"" + barcodeUrl + "\"}";
				}

			}
		} catch (Exception e) {
			msg = e.getMessage();
			logger.error("生成二维码出错：" + e.getMessage());
		}

		return "{\"code\":-1,\"msg\":\"" + msg + "\"}";
	}
	// 打印唯一码ajax请求
	@RequestMapping("/scanuniquecodeajax.html")
	@ResponseBody
	public String scanUniqueAjax(Model model, HttpServletRequest request) {
		String msg = "";
		ErpSecurityCodeMacRelation relation = null;
		try {
			SysAdmin admin = WebUtil.getLoginUser(request);
			String fwm = request.getParameter("scanCode");
			String orderNumber = request.getParameter("orderNumber");
			MallOrder order = orderService.getMallOrder(orderNumber);
			order.setOrderItems(orderService.queryOrderItems(orderNumber));
			MallOrderItem orderItem = order.getOrderItems().get(0);
			erpOemScanPackageService.deleteCustomMadeRelation(orderItem);
			if(StringUtils.isBlank(fwm)||StringUtils.length(fwm)!=16){
				return "{\"code\":-1,\"msg\":\"防伪码为空或防伪码有误！\"}";
			}
			relation=erpSecurityCodeMacRelationService.getErpSecurityCodeMacRelation(fwm);
			
			if(relation==null||StringUtils.isBlank(relation.getMac())){
//					throw new Exception("没有找到该防伪码下的设备信息！");
				return "{\"code\":-1,\"msg\":\"没有找到该防伪码下的设备信息！\"}";
			}
			if(orderItem.getProductModelFormatId().longValue()!=relation.getFormatId().longValue()){
//					throw new Exception("没有找到该防伪码下的设备信息！");
				return "{\"code\":-1,\"msg\":\"没有找到该规格的防伪码设备信息！\"}";
			}
			String scanMacNumber=relation.getMac();
			String uniqueCode = "";
			ErpHardwareProduct erpHardwareProduct = erpHardwareProductService.getErpHardwareProductByMacNumber(scanMacNumber);
			if (erpHardwareProduct != null) {
				uniqueCode = erpHardwareProduct.getUniqueCode();
			}else{
				uniqueCode = erpOemScanPackageService.scanPackageCustom(scanMacNumber,orderItem,relation,admin,request.getRemoteAddr());
			}
			ErpHardwareProduct erpProduct = erpHardwareProductService.getErpHardwareProductByMacNumber(scanMacNumber);
			// 生成条形码
//				String uniqueCode = erpHardwareProduct.getUniqueCode();
			String filePath = request.getRealPath("") + "/images/uniquecode/" + uniqueCode + ".png";
			BarcodeUtil.createBarcode(uniqueCode, filePath);
			String barcodeUrl = "../../../../images/uniquecode/" + uniqueCode + ".png";


			List<MallProductPic> pics = productQueryService.queryPics(null, erpProduct.getCategoryModelFormatId(), (short) 2);
			String formatPicUrl = (pics != null && pics.size() > 0) ? pics.get(0).getUrl() : "";
			return "{\"code\":0,\"uniqueCode\":\"" + uniqueCode + "\",\"codeUrl\":\"" + barcodeUrl + "\",\"productName\":\""
					+ erpProduct.getCategoryModel().getName() + "\",\"format\":\"" + erpProduct.getCategoryModelFormat().getName()
					+ "\",\"formatPicUrl\":\"" + formatPicUrl + "\",\"batch\":\"" + erpProduct.getBatch() + "\",\"productCode\":\""
					+ erpProduct.getCategoryModelFormat().getCategoryModelFormatNumber() + "\"}";

		} catch (Exception e) {
			logger.error("扫描生成唯一码出错：" + e.getMessage());
			e.printStackTrace();
			return "{\"code\":-1,\"msg\":\"" + e.getMessage() + "\"}";
		}

	}

}

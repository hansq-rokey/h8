/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.web.oem.scanpackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.constant.Constant.Status;
import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.entity.ErpBaseBatch;
import com.ibaixiong.entity.ErpBatchDetails;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpOemUserRelation;
import com.ibaixiong.entity.ErpSecurityCodeMacRelation;
import com.ibaixiong.entity.ErpUserPermissionRelation;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.oem.ErpHardwareProductChangeLogService;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.service.oem.ErpOemScanPackageService;
import com.ibaixiong.erp.service.oem.ErpOemUserRelationService;
import com.ibaixiong.erp.service.oem.ErpSecurityCodeMacRelationService;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.service.sysset.ErpBaseBatchService;
import com.ibaixiong.erp.service.sysset.ErpBatchDetailsService;
import com.ibaixiong.erp.web.util.BarcodeUtil;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;
import com.papabear.product.entity.MallProductPic;

/**
 * @description 扫描打包
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年9月29日-下午4:22:45
 */
@Controller
@RequestMapping("/oem/scanpackage")
public class ScanPackageAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ErpOemScanPackageService erpOemScanPackageService;
	@Autowired
	ErpOemUserRelationService erpOemUserRelationService;
	@Autowired
	ErpHardwareProductService erpHardwareProductService;
	@Autowired
	ProductQueryService productQueryService;
	@Autowired
	ErpHardwareProductChangeLogService erpHardwareProductChangeLogService;
	@Autowired
	MallOrderService mallOrderService;
	@Resource
	ErpSecurityCodeMacRelationService erpSecurityCodeMacRelationService;
	@Resource
	CategoryQueryService categoryQueryService;
	@Autowired
	ErpBatchDetailsService erpBatchDetailsService;
	@Autowired
	ErpBaseBatchService erpBaseBatchService;
	
	// 生成防伪码——选择产品
	@RequestMapping("/selectformat.html")
	public String selectFormat(Model model, HttpServletRequest request) {
//		SysAdmin admin = WebUtil.getLoginUser(request);
//		List<MallBasicCategoryModel> listCategoryModel = erpOemScanPackageService.getMallBasicCategoryModelByAdminId(adminId);
		List<MallBasicCategoryModel> listCategoryModel = erpOemScanPackageService.getMallBasicCategoryModel();

//		ErpOemUserRelation erpOemUserRelation = erpOemUserRelationService.getErpOemUserRelationByAdminId(admin.getId());

		model.addAttribute("listCategoryModel", listCategoryModel);
//		model.addAttribute("erpOemUserRelation", erpOemUserRelation);

		return "/oem/scanpackage/selectformat";
	}

	// 生成防伪码——选择规格
	@RequestMapping("/selectformat_format.html")
	@ResponseBody
	public String selectFormatFromat(Model model, HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		byte code = 0;
		String message = "成功";
		try {
			SysAdmin admin = WebUtil.getLoginUser(request);
			Long categoryModelId = Long.valueOf(request.getParameter("categoryModelId"));
//			List<MallBasicCategoryModelFormat> listCategoryModelFormat = erpOemScanPackageService.getMallBasicCategoryModelFormatByAdminId(admin.getId(),
//					categoryModelId);
			List<MallBasicCategoryModelFormat> listCategoryModelFormat = erpOemScanPackageService.getMallBasicCategoryModelFormat(categoryModelId);
			jsonMap.put("data", listCategoryModelFormat);
		} catch (Exception e) {
			code = 1;
			message = "失败";
		}
		jsonMap.put("code", code);
		jsonMap.put("message", message);
		return JSON.toJSONString(jsonMap);
	}

	// 生成防伪码——选择批次
	@RequestMapping("/selectformat_format_batch.html")
	@ResponseBody
	public String selectFormatFromatBatch(Model model, HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		byte code = 0;
		String message = "成功";
		try {
//			SysAdmin admin = WebUtil.getLoginUser(request);
			Long formatId = Long.valueOf(request.getParameter("formatId"));
//			List<ErpUserPermissionRelation> listErpUserPermissionRelation = erpOemScanPackageService.getListErpUserPermissionRelationByAdminIdAndFormatId(
//					admin.getId(), formatId);

//			List<ErpUserPermissionRelation> listErpUserPermissionRelation = erpOemScanPackageService.getListErpUserPermissionRelationByFormatId(
//					formatId);
			MallBasicCategoryModelFormat format=categoryQueryService.getCategoryModelFormat(formatId);
			if(format==null){
				code = 1;
				message = "没有找到规格";
				jsonMap.put("code", code);
				jsonMap.put("message", message);
				return JSON.toJSONString(jsonMap);
			}
			ErpBaseBatch baseBatch=erpBaseBatchService.selectByBatch(format.getCategoryId(), format.getCategoryModelId(), formatId);
			if(baseBatch==null){
				code = 1;
				message = "没有找到批次";
				jsonMap.put("code", code);
				jsonMap.put("message", message);
				return JSON.toJSONString(jsonMap);
			}
			List<ErpBatchDetails> list=erpBatchDetailsService.getListByBatchId(baseBatch.getId(),(byte)0 );
			jsonMap.put("data", list);
		} catch (Exception e) {
			code = 1;
			message = "失败";
		}
		jsonMap.put("code", code);
		jsonMap.put("message", message);
		return JSON.toJSONString(jsonMap);
	}

	// 生成防伪码界面
	@RequestMapping("/scanqrcode.html")
	public String scanQrcode(Model model, HttpServletRequest request) {
		SysAdmin admin = WebUtil.getLoginUser(request);
		Long selectId = Long.valueOf(request.getParameter("selectId"));

		ErpUserPermissionRelation selectErpUserPermissionRelation = erpOemScanPackageService.getErpUserPermissionRelationBySelectId(selectId);

//		ErpOemUserRelation erpOemUserRelation = erpOemUserRelationService.getErpOemUserRelationByAdminId(admin.getId());
		List<MallProductPic> pics = productQueryService.queryPics(null, selectErpUserPermissionRelation.getCategoryModelFormat().getId(), (short) 2);

		model.addAttribute("selectErpUserPermissionRelation", selectErpUserPermissionRelation);
//		model.addAttribute("erpOemUserRelation", erpOemUserRelation);
		model.addAttribute("pic", (pics != null && pics.size() > 0) ? pics.get(0).getUrl() : null);
		// 如果是非智能类型，就显示一张固定的二维码来扫描
		if (selectErpUserPermissionRelation.getCategoryModelFormat().getIsSmart() != 1) {
			model.addAttribute("notSmartPic", "http://image.ibaixiong.com/erp/0000000000000000.png");
		}

		return "/oem/scanpackage/scanqrcode";
	}

	// 生成防伪码ajax请求
	@RequestMapping("/scanqrcodeajax.html")
	@ResponseBody
	public String scanQrcodeAjax(Model model, HttpServletRequest request) {
		String msg = "";
		try {
			SysAdmin admin = WebUtil.getLoginUser(request);
			Long selectId = Long.valueOf(request.getParameter("selectId"));
			String fwm = request.getParameter("scanMacNumber");

			if(StringUtils.isBlank(fwm)||StringUtils.length(fwm)!=16){
				throw new Exception("防伪码为空或防伪码有误！");
			}
			ErpUserPermissionRelation selectErpUserPermissionRelation = erpOemScanPackageService.getErpUserPermissionRelationBySelectId(selectId);
			
			if(selectErpUserPermissionRelation==null){
				
				throw new Exception("没有权限");
			}

			ErpSecurityCodeMacRelation relation=erpSecurityCodeMacRelationService.getErpSecurityCodeMacRelation(fwm);
			String scanMacNumber="";
			if (selectErpUserPermissionRelation.getCategoryModelFormat().getIsSmart() != 1) {
				String isSmart=fwm.substring(fwm.length()-1, fwm.length());
				if (isSmart.equals("1")) {
					throw new Exception("非智能类目下扫描到智能类型的码！");
				}
				scanMacNumber = CodeUtil.getNotSmartMacNumber();
				if(relation!=null&&relation.getMac()==null){
					erpSecurityCodeMacRelationService.bindMacByFwm(fwm, scanMacNumber);
				}else{
					throw new Exception("该防伪码已经绑定过了！");
				}
			}else{
				if(relation==null){
					throw new Exception("没有找到该防伪码！");
				}
				if(relation.getMac()==null){

					throw new Exception("没有找到该防伪码下的设备信息！");
				}
				if (relation.getIsSmart().intValue()==0) {
					throw new Exception("智能类目下扫描到非智能类型的码！");
				}
				scanMacNumber=relation.getMac();
			}
			ErpOemUserRelation erpOemUserRelation = erpOemUserRelationService.getErpOemUserRelationByAdminId(admin.getId());

			if (StringUtils.isNotBlank(scanMacNumber)) {
				String uniqueCode = erpOemScanPackageService.scanPackage(scanMacNumber, selectErpUserPermissionRelation, erpOemUserRelation);
				if (StringUtils.isNotBlank(uniqueCode)) {
					// 生成二维码
//					String filePath = request.getRealPath("") + "/images/qrcode/";
//					String fileName = uniqueCode + ".png";
//					String logoPath = request.getRealPath("") + "/images/logo-18x18.png";
//					String content = "http://www.ibaixiong.com/h5/scan-record.html?code=" + scanMacNumber;
//					BarcodeUtil.createQrCode(content, filePath, fileName);
//					String barcodeUrl = "../../../../images/qrcode/" + uniqueCode + ".png";
					return "{\"code\":0,\"qrcode\":\"" + scanMacNumber + "\"}";
				}

			}
		} catch (Exception e) {
			msg = e.getMessage();
			logger.error("生成二维码出错：" + e.getMessage());
		}

		return "{\"code\":-1,\"msg\":\"" + msg + "\"}";
	}

	@RequestMapping("/scanuniquecode.html")
	public String scanUniqueCode(Model model, HttpServletRequest request) {
		return "/oem/scanpackage/scanuniquecode";
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
			if(StringUtils.isBlank(fwm)||StringUtils.length(fwm)!=16){
				return "{\"code\":-1,\"msg\":\"防伪码为空或防伪码有误！\"}";
			}
			relation=erpSecurityCodeMacRelationService.getErpSecurityCodeMacRelation(fwm);
			
			if(relation==null||StringUtils.isBlank(relation.getMac())){
//				throw new Exception("没有找到该防伪码下的设备信息！");
				return "{\"code\":-1,\"msg\":\"没有找到该防伪码下的设备信息！\"}";
			}
			String scanMacNumber=relation.getMac();
			String uniqueCode = "";
			ErpHardwareProduct erpHardwareProduct = erpHardwareProductService.getErpHardwareProductByMacNumber(scanMacNumber);
			if (erpHardwareProduct != null) {
				uniqueCode = erpHardwareProduct.getUniqueCode();
			}else{
				uniqueCode = erpOemScanPackageService.scanPackageCode(scanMacNumber, relation,admin);
			}
			ErpHardwareProduct erpProduct = erpHardwareProductService.getErpHardwareProductByMacNumber(scanMacNumber);
			// 生成条形码
//			String uniqueCode = erpHardwareProduct.getUniqueCode();
			String filePath = request.getRealPath("") + "/images/uniquecode/" + uniqueCode + ".png";
			BarcodeUtil.createBarcode(uniqueCode, filePath);
			String barcodeUrl = "../../../../images/uniquecode/" + uniqueCode + ".png";

			ErpHardwareProductChangeLog changeLog = new ErpHardwareProductChangeLog();
			changeLog.setCreateDateTime(new Date());
			changeLog.setHardwareProductId(erpProduct.getId());
			changeLog.setInvalid(false);
			changeLog.setOperateExplain("生产完毕");
			changeLog.setOperateId(admin.getId());
			changeLog.setStatus((byte) 1);
			changeLog.setUserId(admin.getId());
			changeLog.setUserName(admin.getUserName());
			erpHardwareProductChangeLogService.insert(changeLog);

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

	// 扫描记录
	@RequestMapping("/scanhistory.html")
	public String scanHistory(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, Model model, HttpServletRequest request) {
		SysAdmin admin = WebUtil.getLoginUser(request);
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String uniqueCode = request.getParameter("uniqueCode");

		List<ErpHardwareProduct> listErpHardwareProduct;
		if (StringUtils.isNotBlank(uniqueCode)) {
			ErpHardwareProduct erpHardwareProduct = erpHardwareProductService.getErpHardwareProductByUniqueCode(uniqueCode);
			listErpHardwareProduct = new ArrayList<ErpHardwareProduct>();
			listErpHardwareProduct.add(erpHardwareProduct);
			model.addAttribute("uniqueCode", uniqueCode);
		} else {
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			if (StringUtils.isNotBlank(startDate)) {
				startDate += " 00:00:00";
			}
			if (StringUtils.isNotBlank(endDate)) {
				endDate += " 23:59:59";
			}
			listErpHardwareProduct = erpHardwareProductService.getErpHardwareProductByAdminId(admin.getId(), startDate, endDate, pageNo);
			PageInfo<ErpHardwareProduct> pageInfo = new PageInfo<ErpHardwareProduct>(listErpHardwareProduct);
			model.addAttribute("pageInfo", pageInfo);
		}

		model.addAttribute("listErpHardwareProduct", listErpHardwareProduct);

		return "/oem/scanpackage/scanhistory";
	}

}

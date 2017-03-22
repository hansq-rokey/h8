package com.ibaixiong.erp.web.storage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpExceptionProduct;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpPutStorageCount;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.oem.ErpHardwareProductChangeLogService;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.service.oem.ErpTransportCodeService;
import com.ibaixiong.erp.service.purchase.ErpPurchaseOrderService;
import com.ibaixiong.erp.service.storage.ErpExceptionProductService;
import com.ibaixiong.erp.service.storage.ErpPutStorageCountService;
import com.ibaixiong.erp.service.storage.ErpPutStorageService;
import com.ibaixiong.erp.util.DateEditor;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.web.storage.HelpBean.ErpExceptionProductList;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
import com.papabear.product.entity.MallProductPic;

/**
 * 入库操作
 * 
 * @author yaoweiguo
 * @Email  yaoweiguo@ibaixiong.com
 * @Description TODO
 * @date 2015年10月8日
 *
 */

@Controller
@RequestMapping("/storage")
public class PutStorageController {
	
	@Resource
	private ErpTransportCodeService erpTransportCodeService;
	@Resource
	private ErpHardwareProductService erpHardwareProduct;
	@Resource
	private ErpExceptionProductService exceptionProductService;
	@Resource
	private ErpPutStorageService erpPutStorageService;
	@Resource
	private ErpPutStorageCountService erpPutStorageCountService;
	@Resource
	ErpPurchaseOrderService erpPurchaseOrderService;
	@Resource
	private ErpHardwareProductChangeLogService logService;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private ProductQueryService productQueryService;
	/**
	 * 通过运输码查询  
	 * TODO   判断是否是运输码还是唯一码
	 * @param code
	 * @param response
	 */
	@RequestMapping("/put/transport")
	public void queryTransportByCode(@RequestParam("code") String code,HttpServletResponse response){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=1;
		if(CodeUtil.isTransportCode(code)){//扫描的是包装码
			Map<String, Object> transportCodeMap=erpTransportCodeService.getErpTransportCode(code);
			if(transportCodeMap==null){
				resultCode=0;
			}else{
				Long id=(Long) transportCodeMap.get("id");
				List<ErpHardwareProduct> list=erpHardwareProduct.queryErpHardwareProductByTransportCodeId(id);
				transportCodeMap.put("products", list);
				transportCodeMap.put("num", list.size());
				map.put("transport", transportCodeMap);
			}
		}else if(CodeUtil.isUniqueCode(code)){//扫描的是单台产品
			ErpHardwareProduct ehp = erpHardwareProduct.getErpHardwareProductByUniqueCode(code);
			if(ehp == null){
				resultCode=0;
			}else{
				Map<String, Object> transportCodeMap = new HashMap<String, Object>();
				List<ErpHardwareProduct> list= new ArrayList<ErpHardwareProduct>();
				List<MallProductPic>pics=productQueryService.queryPics(null, ehp.getCategoryModelFormat().getId(), (short)2);
				list.add(ehp);
				transportCodeMap.put("products", list);
				transportCodeMap.put("num", list.size());
				transportCodeMap.put("productName", ehp.getCategoryModel().getName());
				transportCodeMap.put("code", "");
				transportCodeMap.put("formatName",ehp.getCategoryModelFormat().getName());
				transportCodeMap.put("pics", pics);
				map.put("transport", transportCodeMap);
			}	
		}else{
			resultCode=0;
		}
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(resultCode, "",map)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*进入入库页面*/
	@RequestMapping("/put")
	public String toPutStorage(){
		
		return "storage/put";
	}
	@RequestMapping("/put/checkStorageProduct.html")
	public void checkStorageProduct(@RequestParam(value = "normalPros", required = false) String normalPros,HttpServletResponse response){
		int resultCode=0;//正常的没有入过库的
		String str = "";
		if(org.apache.commons.lang.StringUtils.isNotBlank(normalPros)){
			String[] normalProducts=StringUtils.tokenizeToStringArray(normalPros, ",");
			for (String string : normalProducts) {
				//List<ErpPutStorage> list = erpPutStorageService.queryErpPutStoragesByHardwareId(Long.parseLong(string));
				//判断如果该硬件状态为在库存中则这一批不允许进行入库操作
				ErpHardwareProduct edp = erpHardwareProduct.get(Long.parseLong(string));
				if(edp != null && edp.getStatus().intValue() == HardwareProductNetStatusEnum.STORAGE.getCode().intValue()){
					resultCode = 1;
					str = str+ edp.getUniqueCode()+",";
				}
			}
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
			str = str.substring(0,str.length()-1);
		}
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(resultCode,str,null)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/put/save")
	public void putStorageSave(@RequestParam(value = "normalPros", required = false) String normalPros,
			@RequestParam(value = "code", required = false) String code,
			@ModelAttribute("products") ErpExceptionProductList products,
			HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("normalPros", normalPros);
		map.put("code", code);
		map.put("products", products);
		map.put("request", request);
		int codePage = 0;
		String msg = "";
		try {
			erpPutStorageService.putSave(map);
		} catch (Exception e1) {
			e1.printStackTrace();
			codePage = 1;
			msg = "系统出错，请联系管理员";
		}
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(codePage,msg,null)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 操作人员入库记录
	 * @return
	 */
	@RequestMapping("/put/record")
	public String putStorageRecord(ModelMap model,@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			@RequestParam(value="keywords",required=false)String keywords,
			@RequestParam(value="startTime",required=false)Date startTime,
			@RequestParam(value="endTime",required=false)Date endTime,
			@RequestParam(value="checkType",required=false)String checkType,
			HttpServletRequest request){
		if(org.apache.commons.lang.StringUtils.isBlank(checkType)){
			checkType = "1";
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)){
			model.addAttribute("keywords",keywords);
		}
		if(startTime != null){
			model.addAttribute("startTime",DateUtil.format(startTime, "yyyy-MM-dd"));
		}
		if(endTime != null){
			model.addAttribute("endTime",DateUtil.format(endTime, "yyyy-MM-dd"));
		}
		SysAdmin admin=WebUtil.getLoginUser(request);
		if(checkType.equals("1")){
			List<ErpPutStorageCount>coutList=erpPutStorageCountService.queryPutStorageList(admin, keywords, startTime, endTime, pageNo,  PageConstant.pageSize);
			PageInfo<ErpPutStorageCount> pageInfo=new PageInfo<ErpPutStorageCount>(coutList);
			model.addAttribute("pageInfo",pageInfo);
			model.addAttribute("putStorageList", coutList);
		}
		if(checkType.equals("2")){
			List<ErpExceptionProduct>exceptionList=exceptionProductService.queryExcetionList(admin, keywords, startTime, endTime, pageNo, PageConstant.pageSize);
			PageInfo<ErpExceptionProduct> pageInfo=new PageInfo<ErpExceptionProduct>(exceptionList);
			model.addAttribute("pageInfo",pageInfo);
			model.addAttribute("exceptionList", exceptionList);
		}
		model.addAttribute("checkType",checkType);
		return "storage/storage_record";
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}

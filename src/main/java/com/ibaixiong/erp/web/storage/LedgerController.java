/* baixiong.com Inc.
 * Copyright (c) 1999-2001 All Rights Reserved.
 */
package com.ibaixiong.erp.web.storage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.constant.Constant.Status;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpExceptionProduct;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpOutStorage;
import com.ibaixiong.entity.ErpPutStorage;
import com.ibaixiong.entity.ErpPutStorageCount;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.oem.ErpHardwareProductChangeLogService;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.service.storage.ErpExceptionProductService;
import com.ibaixiong.erp.service.storage.ErpOutStorageService;
import com.ibaixiong.erp.service.storage.ErpPutStorageCountService;
import com.ibaixiong.erp.service.storage.ErpPutStorageService;
import com.ibaixiong.erp.util.DateEditor;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.product.api.CategoryCUDService;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;
/**
 * 
 * 
 * @author yaoweiguo
 * @Email  yaoweiguo@ibaixiong.com
 * @Description TODO
 * @date 2015年10月9日
 *
 */
@Controller
@RequestMapping("/ledger")
public class LedgerController {

	@Resource
	private ErpPutStorageCountService countService;
	@Resource
	private ErpExceptionProductService ExceptionProductService;
	@Resource
	private ErpOutStorageService outStorageService;
	@Resource
	private ErpPutStorageService erpPutStorageService;
	@Resource
	private ErpHardwareProductChangeLogService logService;
	@Resource
	private ErpHardwareProductService erpHardwareProduct;
	@Resource
	private ErpExceptionProductService exceptionProductService;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private CategoryCUDService categoryCUDService;
	/**
	 * 库存列表
	 * @return
	 */
	@RequestMapping("/storage/list")
	public String storageList(@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			@RequestParam(value="keywords",required=false)String keywords,
			@RequestParam(value="startTime",required=false)Date startTime,
			@RequestParam(value="endTime",required=false)Date endTime,
			HttpServletRequest request,ModelMap model){
		if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)){
			model.addAttribute("keywords",keywords);
		}
		if(startTime != null){
			model.addAttribute("startTime",DateUtil.format(startTime, "yyyy-MM-dd"));
		}
		if(endTime != null){
			model.addAttribute("endTime",DateUtil.format(endTime, "yyyy-MM-dd"));
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("keywords", keywords);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("pageNo", pageNo);
		List<MallBasicCategoryModel> list = categoryQueryService.queryStockAll(map);
		PageInfo<MallBasicCategoryModel> pageInfo=new PageInfo<MallBasicCategoryModel>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		model.addAttribute("flag", 1);
		return "storage/ledger";
	}
	/**
	 * 库存记录详细信息
	 * @return
	 */
	@RequestMapping("/storage/list/detail")
	public String listDetail(@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			@RequestParam("formId")Long formId,ModelMap model){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", 4);
		map.put("formatId", formId);
		map.put("pageNo", pageNo);
		List<ErpHardwareProduct> list = erpHardwareProduct.queryListByFormatId(map);
		PageInfo<ErpHardwareProduct> pageInfo=new PageInfo<ErpHardwareProduct>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		model.addAttribute("formId",formId);
		return "storage/list_storage_detail";
	}
	/**
	 * 出库列表
	 * @return
	 */
	@RequestMapping("/storage/out")
	public String outStorageList(@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			@RequestParam(value="keywords",required=false)String keywords,
			@RequestParam(value="startTime",required=false)Date startTime,
			@RequestParam(value="endTime",required=false)Date endTime,
			HttpServletRequest request,ModelMap model){
		if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)){
			model.addAttribute("keywords",keywords);
		}
		if(startTime != null){
			model.addAttribute("startTime",DateUtil.format(startTime, "yyyy-MM-dd"));
		}
		if(endTime != null){
			model.addAttribute("endTime",DateUtil.format(endTime, "yyyy-MM-dd"));
		}
		List<ErpOutStorage>list=outStorageService.queryOutStorageList(null,keywords,startTime,endTime, pageNo, PageConstant.pageSize);
		PageInfo<ErpOutStorage> pageInfo=new PageInfo<ErpOutStorage>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("flag", 2);
		return "storage/ledger";
	}
	
	/**
	 * 入库列表
	 * @return
	 */
	@RequestMapping("/storage/put")
	public String putStorageList(@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			@RequestParam(value="keywords",required=false)String keywords,
			@RequestParam(value="startTime",required=false)Date startTime,
			@RequestParam(value="endTime",required=false)Date endTime,
			HttpServletRequest request,ModelMap model){
		if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)){
			model.addAttribute("keywords",keywords);
		}
		if(startTime != null){
			model.addAttribute("startTime",DateUtil.format(startTime, "yyyy-MM-dd"));
		}
		if(endTime != null){
			model.addAttribute("endTime",DateUtil.format(endTime, "yyyy-MM-dd"));
		}
		List<ErpPutStorageCount>list=countService.queryPutStorageList(null,keywords,startTime,endTime, pageNo, PageConstant.pageSize);
		PageInfo<ErpPutStorageCount> pageInfo=new PageInfo<ErpPutStorageCount>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("flag", 3);
		return "storage/ledger";
	}
	
	/**
	 * 异常列表
	 * @return
	 */
	@RequestMapping("/storage/excetion")
	public String exceptionStorageList(@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			@RequestParam(value="keywords",required=false)String keywords,
			@RequestParam(value="startTime",required=false)Date startTime,
			@RequestParam(value="endTime",required=false)Date endTime,
			HttpServletRequest request,ModelMap model){
		if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)){
			model.addAttribute("keywords",keywords);
		}
		if(startTime != null){
			model.addAttribute("startTime",DateUtil.format(startTime, "yyyy-MM-dd"));
		}
		if(endTime != null){
			model.addAttribute("endTime",DateUtil.format(endTime, "yyyy-MM-dd"));
		}
		List<ErpExceptionProduct> list=ExceptionProductService.queryExcetionList(null,keywords,startTime,endTime, pageNo,  PageConstant.pageSize);
		PageInfo<ErpExceptionProduct> pageInfo=new PageInfo<ErpExceptionProduct>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list", list);
		model.addAttribute("flag", 4);
		return "storage/ledger";
	}
	//异常添加
	@RequestMapping("/storage/excetion/save")
	public void exceptionStorageSave(
			@RequestParam(value="uniqueCode",required=false) String uniqueCode,
			@RequestParam(value="exceptionDes",required=false)String exceptionDes,
			HttpServletRequest request,ModelMap model,HttpServletResponse response){
		String msg = "";
		int code = 0;
		//非唯一码
		if(CodeUtil.isUniqueCode(uniqueCode)){
			ErpHardwareProduct edp = erpHardwareProduct.getErpHardwareProductByUniqueCode(uniqueCode);
			if(edp == null || edp.getId() == null){
				code=2;
			}else{
				//查询这个唯一码有没有经过入库行为经过了入库才能进行标记为异常
				//List<ErpPutStorage> putList = erpPutStorageService.queryErpPutStoragesByUniqueCode(uniqueCode);
				//if(putList != null && putList.size()>0){
				//更改逻辑为该硬件在库存中状态为入库才能进行操作
				if(edp.getStatus().intValue()==HardwareProductNetStatusEnum.STORAGE.getCode().intValue()){
					SysAdmin loginAdmin = WebUtil.getLoginUser(request);
					ErpExceptionProduct eep = new ErpExceptionProduct();
					eep.setAdminId(loginAdmin.getId());
					eep.setCreateDateTime(new Date());
					eep.setInvalid(InvalidEnum.FALSE.getInvalidValue());
					eep.setStatus(Status.NORMAL.getStatus());
					eep.setBatch(edp.getBatch());
					eep.setOemName(edp.getOemInfoName());
					eep.setProductFormat(edp.getCategoryModelFormat().getName());
					eep.setProductName(edp.getCategoryModel().getName());
					//eep.setTransportCode(code);
					eep.setUniqeCode(edp.getUniqueCode());
					eep.setDescription(exceptionDes);
					eep.setHardwareProductId(edp.getId());
					eep.setExceptionType(3L);//类型为仓损
					exceptionProductService.insert(eep);
					//修改硬件状态
					edp.setStatus(HardwareProductNetStatusEnum.EXCEPTION.getCode());
					erpHardwareProduct.save(edp);
					//修改库存
					MallBasicCategoryModelFormat format = categoryQueryService.getCategoryModelFormat(edp.getCategoryModelFormatId());
					if(format !=null && format.getId() != null){
						categoryCUDService.updateStockById(edp.getCategoryModelFormatId(),-1);
					}
					//插入物联网信息
					ErpHardwareProductChangeLog log = new ErpHardwareProductChangeLog();
					log.setHardwareProductId(edp.getId());
					log.setCreateDateTime(new Date());
					log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
					log.setStatus(Status.NORMAL.getStatus());
					log.setUserId(loginAdmin.getId());
					log.setUserName(loginAdmin.getUserName());
					log.setOperateId(HardwareProductNetStatusEnum.EXCEPTION.getCode().longValue());
					log.setOperateExplain("仓损异常标记");
					logService.insert(log);
				}else{
					code = 3;
				}
			}
		}else{
			code = 1;
		}
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code,msg,null)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/storage/saveWarnCount.html")
	public void saveWarnCount(
			@RequestParam(value="id",required=false) Long id,
			@RequestParam(value="count",required=false) Integer count,
			HttpServletRequest request,ModelMap model,HttpServletResponse response){
		String msg = "";
		int code = 0;
		MallBasicCategoryModelFormat format = categoryQueryService.getCategoryModelFormat(id);
		format.setWarnCount(count);
		categoryCUDService.updateCategoryModelFormat(format);
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code,msg,null)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 运费统计
	 * @return
	 */
	@RequestMapping("/storage/freight")
	public String FreightStatistics(){
		
		
		return "storage/ledger";
	}
	
	
	/**
	 * 入库记录详细信息
	 * @return
	 */
	@RequestMapping("/storage/put/detail")
	public String outStorageDetail(@RequestParam("id")Long	id,ModelMap model){
		List<ErpPutStorage>list=erpPutStorageService.queryErpPutStorages(null, id);
		ErpPutStorageCount erpPutStorageCount=countService.getErpPutStorageCount(id);
		model.addAttribute("list", list);
		model.addAttribute("bean", erpPutStorageCount);
		return "storage/put_storage_detail";
	}
	
	@RequestMapping("/trace")
	public String productTrace(@RequestParam("hardwareId")Long hardwareId,ModelMap model){
		List<ErpHardwareProductChangeLog>list=logService.queryLogsByHardwareId(hardwareId);
		model.addAttribute("list", list);
		List<ErpPutStorage> putList=erpPutStorageService.queryErpPutStoragesByHardwareId(hardwareId);
		model.addAttribute("putList", putList);
		return "storage/hardware_trace";
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}

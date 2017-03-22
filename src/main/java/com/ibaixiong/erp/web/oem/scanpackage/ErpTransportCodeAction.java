package com.ibaixiong.erp.web.oem.scanpackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.constant.Constant.Status;
import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpHardwareTransportRelation;
import com.ibaixiong.entity.ErpTransportCode;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.oem.ErpHardwareProductChangeLogService;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.service.oem.ErpHardwareTransportRelationService;
import com.ibaixiong.erp.service.oem.ErpTransportCodeService;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.BarcodeUtil;
import com.ibaixiong.erp.web.util.WebUtil;
/**
 * @description 运输码生成相关处理
 * @author zhaolei
 * @create 2015年10月15日
 */
@Controller
@RequestMapping("/oem/transport")
public class ErpTransportCodeAction {
	@Resource
	private ErpTransportCodeService erpTransportCodeService;
	@Resource
	private ErpHardwareProductService erpHardwareProduct;
	@Resource
	private ErpHardwareTransportRelationService erpHardwareTransportRelationService;
	@Resource
	private ErpHardwareProductChangeLogService logService;
	/*进入运输码页面*/
	@RequestMapping("/put.html")
	public String toPutStorage(){
		return "oem/transport";
	}
	/**
	 * 通过唯一码查询  
	 * TODO   判断是否是唯一码还是唯一码
	 * @param code
	 * @param response
	 */
	@RequestMapping("/out.html")
	public void queryTransportByCode(@RequestParam("code") String code,HttpServletResponse response){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=1;
		if(StringUtils.isNotBlank(code)){
			ErpHardwareProduct ehp = erpHardwareProduct.getErpHardwareProductByUniqueCode(code);
			if(ehp == null){
				resultCode=0;
			}else{
				//productName 产品名称 formatName 产品规格 uniqueCode 唯一码  id 硬件ID
				Map<String, Object> transportCodeMap = new HashMap<String, Object>();
				transportCodeMap.put("productName", ehp.getCategoryModel().getName());
				transportCodeMap.put("formatName",ehp.getCategoryModelFormat().getName());
				transportCodeMap.put("uniqueCode", ehp.getUniqueCode());
				transportCodeMap.put("batch", ehp.getBatch());
				transportCodeMap.put("id", ehp.getId());
				transportCodeMap.put("productCode", ehp.getProductCode());
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
	
	@RequestMapping("/put/save.html")
	public void putStorageSave(@RequestParam(value = "normalPros", required = false) String normalPros,
			HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		int code = 0;
		String msg = "";
		Map<String, Object> paremMap = new HashMap<String, Object>();
		paremMap.put("normalPros", normalPros);
		paremMap.put("request", request);
		String transtortCode = "";
		try {
			transtortCode = erpTransportCodeService.saveTransportCode(paremMap).get("transtortCode").toString();
		} catch (Exception e1) {
			e1.printStackTrace();
			code=999;
			msg = "系统错误";
		}
		//生成条形码
		String filePath = request.getRealPath("") + "/images/transportCode/" + transtortCode + ".png";
		BarcodeUtil.createBarcode(transtortCode, filePath);
		String barcodeUrl = "../../../../images/transportCode/" + transtortCode + ".png";
		map.put("transportCode", barcodeUrl);
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code,msg,map)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询扫码列表
	 * @author zhaolei
	 * @date 2015年10月16日
	 * @param queryStr
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryList.html")
	public String queryList(
			@RequestParam(value = "queryStr", required = false) String queryStr,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			HttpServletRequest request,
			Model model){
		SysAdmin admin=WebUtil.getLoginUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminId", admin.getId());
		if(StringUtils.isNotBlank(queryStr)){
			map.put("queryStr", queryStr);
			model.addAttribute("queryStr",queryStr);
		}else{
			map.put("queryStr", null);
		}
		if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", startDate+" 00:00:00");
			model.addAttribute("startDate",startDate);
		}else{
			map.put("startDate", null);
		}
		if(StringUtils.isNotBlank(endDate)){
			map.put("endDate", endDate+" 23:59:59");
			model.addAttribute("endDate",endDate);
		}else{
			map.put("endDate", null);
		}
		List<ErpTransportCode> list = erpTransportCodeService.queryList(map,pageNo);
		PageInfo<ErpTransportCode> pageInfo=new PageInfo<ErpTransportCode>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		return "oem/transportList";
	}
	/**
	 * 查询扫码列表
	 * @author zhaolei
	 * @date 2015年10月16日
	 * @param queryStr
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryDetailList.html")
	public String queryList(
			@RequestParam(value = "codeId", required = false) Long codeId,
			HttpServletRequest request,
			Model model){
		model.addAttribute("list",erpHardwareTransportRelationService.queryListByCodeId(codeId));
		model.addAttribute("transportCode",erpTransportCodeService.get(codeId));
		return "oem/transportDetailList";
	}
}

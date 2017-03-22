package com.ibaixiong.erp.web.oem.scanpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpExceptionProduct;
import com.ibaixiong.entity.ErpOemUserRelation;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.oem.ErpOemUserRelationService;
import com.ibaixiong.erp.service.storage.ErpExceptionProductService;
import com.ibaixiong.erp.web.util.WebUtil;
/**
 * oem异常列表查询
 * @author zhaolei
 *
 */
@Controller
@RequestMapping("/oem/exception")
public class ExceptionProductAction {
	@Resource
	ErpExceptionProductService erpExceptionProductService;
	@Resource
	ErpOemUserRelationService erpOemUserRelationService;
	/**
	 * 查询异常产品列表
	 * @author zhaolei
	 * @date 2015年10月21日
	 * @param queryStr
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryList.html")
	public String queryList(
			@RequestParam(value = "keywords", required = false) String keywords,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value="pageNo",defaultValue="1") Integer pageNo,
			HttpServletRequest request,
			Model model){
		SysAdmin admin=WebUtil.getLoginUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Long> adminlist = new ArrayList<Long>();
		//通过当前登陆人查询出与当前登陆人在同一个OEM厂的员工
		List<ErpOemUserRelation> ouList = erpOemUserRelationService.getAlikeListByAdminId(admin.getId());
		for (ErpOemUserRelation erpOemUserRelation : ouList) {
			adminlist.add(erpOemUserRelation.getAdmin().getId());
		}
		List<ErpExceptionProduct> list = new ArrayList<ErpExceptionProduct>();
		if(adminlist.size()>0){
			map.put("adminList", adminlist);
			if(StringUtils.isNotBlank(keywords)){
				map.put("keywords", keywords);
				model.addAttribute("keywords",keywords);
			}else{
				map.put("keywords", null);
			}
			if(StringUtils.isNotBlank(startTime)){
				map.put("startTime", startTime+" 00:00:00");
				model.addAttribute("startTime",startTime);
			}else{
				map.put("startTime", null);
			}
			if(StringUtils.isNotBlank(endTime)){
				map.put("endTime", endTime+" 23:59:59");
				model.addAttribute("endTime",endTime);
			}else{
				map.put("endTime", null);
			}
			map.put("pageNo", pageNo);
			map.put("offset", PageConstant.pageSize);
			list = erpExceptionProductService.queryExcetionList(map);
		}
		PageInfo<ErpExceptionProduct> pageInfo=new PageInfo<ErpExceptionProduct>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		return "oem/exceptionProductList";
	}
}

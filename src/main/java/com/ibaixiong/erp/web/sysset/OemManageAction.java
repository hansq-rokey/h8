package com.ibaixiong.erp.web.sysset;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpBaseRole;
import com.ibaixiong.entity.ErpBatchDetails;
import com.ibaixiong.entity.ErpOemInfo;
import com.ibaixiong.entity.ErpOemUserRelation;
import com.ibaixiong.entity.ErpUserPermissionRelation;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.SysAdminRole;
import com.ibaixiong.erp.service.oem.ErpOemInfoService;
import com.ibaixiong.erp.service.oem.ErpOemUserRelationService;
import com.ibaixiong.erp.service.sys.SysAdminRoleService;
import com.ibaixiong.erp.service.sysset.ErpBaseRoleService;
import com.ibaixiong.erp.service.sysset.ErpBatchDetailsService;
import com.ibaixiong.erp.service.sysset.ErpUserPermissionRelationService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.entity.MallBasicCategory;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;
/**
 * 
 * @author zhaolei
 * @description OEM管理相关
 * @create 2015年9月29日
 *
 */
@Controller
@RequestMapping("/sysSet/oem")
public class OemManageAction {
	@Resource
	ErpOemInfoService erpOemInfoService;
	@Resource
	ErpBaseRoleService erpBaseRoleService;
	@Resource
	SysAdminRoleService sysAdminRoleService;
	@Resource
	ErpOemUserRelationService erpOemUserRelationService;
	@Resource
	ErpUserPermissionRelationService erpUserPermissionRelationService;
	@Resource
	ErpBatchDetailsService erpBatchDetailsService;
	@Resource
	CategoryQueryService queryService;
	private String oem = "oem";
	/**
	 * OEM厂列表页面
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model, HttpServletRequest request) {
		List<ErpOemInfo> list = erpOemInfoService.getList(pageNo);
		PageInfo<ErpOemInfo> pageInfo=new PageInfo<ErpOemInfo>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		return "/sysSet/oemList";
	}
	/**
	 * 保存oem对象
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param erpSysStorage
	 * @param request
	 * @return
	 */
	@RequestMapping("/save.html")
	public String save(Model model,
			@ModelAttribute("erpOemInfo") ErpOemInfo erpOemInfo,
			HttpServletRequest request) {
		if(erpOemInfo.getId() == null || erpOemInfo.getId().intValue() == 0){
			erpOemInfo.setInvalid(InvalidEnum.FALSE.getInvalidValue());
			erpOemInfo.setCreateDateTime(new Date());
			erpOemInfoService.insert(erpOemInfo);
			//获取保存员工跟OEM之间的关系表
			//查询OEM角色列表设置
			ErpBaseRole erpBaseRole = erpBaseRoleService.getErpBaseRoleByType(oem+erpOemInfo.getOemCode());
			if(erpBaseRole != null){
				String roles = erpBaseRole.getRoles();
				if(StringUtils.isNotBlank(roles)){
					Map<Long, Long> ch = new HashMap<Long, Long>();
					if(StringUtils.isNotBlank(roles)){
						String[] rs = roles.split(",");
						for (String string : rs) {
							if(StringUtils.isNotBlank(string)){
								List<SysAdminRole> adminRoleList = sysAdminRoleService.getSysAdminRoleByRoleId(Long.parseLong(string));
								if(adminRoleList != null){
									for (SysAdminRole sysAdminRole : adminRoleList) {
										SysAdmin admin = sysAdminRole.getAdmin();
										if(!ch.containsKey(admin.getId())){//没找到用户说明没有插入过
											ErpOemUserRelation  userRelation = new ErpOemUserRelation();
											userRelation.setAdmin(admin);
											userRelation.setCreateDateTime(new Date());
											userRelation.setInvalid(InvalidEnum.FALSE.getInvalidValue());
											userRelation.setOem(erpOemInfo);
											erpOemUserRelationService.insert(userRelation);
											ch.put(admin.getId(), admin.getId());
										}
									}
								}
							}
						}
					}
				}
			}
		}else{
			erpOemInfoService.update(erpOemInfo);
		}
		return "redirect:/sysSet/oem/list.html";
	}
	/**
	 * 仓库下的员工列表
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param storageId
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminList.html")
	public String adminList(
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model,
			@RequestParam(value = "oemId", required = false) Long oemId,
			HttpServletRequest request) {
		List<ErpOemUserRelation> list = erpOemUserRelationService.getListByOemId(oemId,pageNo);
		PageInfo<ErpOemUserRelation> pageInfo=new PageInfo<ErpOemUserRelation>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		model.addAttribute("oemId",oemId);
		return "/sysSet/oemAdminList";
	}
	/**
	 * 员工的可操作的产品批次相关列表
	 * @author zhaolei
	 * @date 2015年10月10日
	 * @param model
	 * @param storageId
	 * @param request
	 * @return
	 */
	@RequestMapping("/userPermissionList.html")
	public String userPermissionList(Model model,
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			@RequestParam(value = "adminId", required = false) Long adminId,
			@RequestParam(value = "relationId", required = false) Long relationId,
			HttpServletRequest request) {
		List<ErpUserPermissionRelation> list = erpUserPermissionRelationService.selectByAdminId(adminId,pageNo);
		PageInfo<ErpUserPermissionRelation> pageInfo=new PageInfo<ErpUserPermissionRelation>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		ErpOemUserRelation erpOemUserRelation = erpOemUserRelationService.get(relationId);
		model.addAttribute("adminId",adminId);
		model.addAttribute("adminName",erpOemUserRelation.getAdmin().getUserName());
		model.addAttribute("oemName",erpOemUserRelation.getOem().getName());
		model.addAttribute("relationId",relationId);
		model.addAttribute("oemId",erpOemUserRelation.getOem().getId());
		return "/sysSet/oemUserPermissionList";
	}
	@RequestMapping("/saveUserPermission.html")
	public String saveUserPermission(Model model,
			@ModelAttribute("erpUserPermissionRelation") ErpUserPermissionRelation relation,
			@RequestParam(value = "adminId", required = false) Long adminId,
			@RequestParam(value = "relationId", required = false) Long relationId,
			HttpServletRequest request) {
		relation.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		relation.setCreateDateTime(new Date());
		SysAdmin admin = new SysAdmin();
		admin.setId(adminId);
		relation.setAdmin(admin);
		//由页面选中的批次对象级联查询关联出相关的信息
		ErpBatchDetails batchDetail = erpBatchDetailsService.get(relation.getBatchDetail().getId());
		MallBasicCategory category = queryService.getCategoryById(relation.getCategory().getId());
		MallBasicCategoryModel categoryModel = queryService.getModel(relation.getCategoryModel().getId());
		MallBasicCategoryModelFormat format = queryService.getCategoryModelFormat(relation.getCategoryModelFormat().getId());
		relation.setBatchDetail(batchDetail);
		relation.setCategory(category);
		relation.setCategoryModel(categoryModel);
		relation.setCategoryModelFormat(format);
		relation.setCategoryModelFormatCode(format.getCode());
		relation.setErpBatch(batchDetail.getBatch());
		relation.setPurchaseOrder(batchDetail.getOrder());
		erpUserPermissionRelationService.insert(relation);
		return "redirect:/sysSet/oem/userPermissionList.html?adminId="+adminId+"&relationId="+relationId;
	}
	@RequestMapping("/updateUserPermissionInvalid.html")
	public String updateUserPermissionInvalid(Model model,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "invalid", required = false) Byte invalid,
			@RequestParam(value = "adminId", required = false) Long adminId,
			@RequestParam(value = "relationId", required = false) Long relationId,
			HttpServletRequest request) {
		erpUserPermissionRelationService.updateInvalid(id, invalid);
		return "redirect:/sysSet/oem/userPermissionList.html?adminId="+adminId+"&relationId="+relationId;
	}
	/**
	 * 查询批次下拉
	 * @author zhaolei
	 * @date 2015年10月10日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/checkUserPermission.html")
	public void getOemBatchList(
			@RequestParam(value = "batchId", required = false) Long batchId,
			@RequestParam(value = "adminId", required = false) Long adminId,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		//oem查询的下拉列表不一样需要先从产品批次中检索
		ErpUserPermissionRelation epr = erpUserPermissionRelationService.getCheck(batchId, adminId);
		if(epr != null){
			code = 1;
			if(epr.getId()!=null){
				code = 1;
				if(epr.getId().intValue()>0){
					code = 1;
				}else{
					code = 0;
				}
			}
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
}

package com.ibaixiong.erp.web.sysset;

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
import com.ibaixiong.entity.ErpNoticeCategory;
import com.ibaixiong.entity.ErpNoticeInfo;
import com.ibaixiong.entity.ErpStorageUserRelation;
import com.ibaixiong.entity.ErpSysGoodsShelfInfo;
import com.ibaixiong.entity.ErpSysStorage;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.SysAdminRole;
import com.ibaixiong.erp.service.notice.NoticeService;
import com.ibaixiong.erp.service.storage.ErpStorageUserRelationService;
import com.ibaixiong.erp.service.storage.ErpSysStorageService;
import com.ibaixiong.erp.service.sys.SysAdminRoleService;
import com.ibaixiong.erp.service.sys.SysAdminService;
import com.ibaixiong.erp.service.sysset.ErpBaseRoleService;
import com.ibaixiong.erp.service.sysset.ErpSysGoodsShelfInfoService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.WebUtil;
/**
 * 
 * @author zhaolei
 * @description 仓库管理相关
 * @create 2015年9月29日
 *
 */
@Controller
@RequestMapping("/sysSet/storage")
public class StorageManageAction {
	@Resource
	ErpSysStorageService erpSysStorageService;
	@Resource
	SysAdminService adminService;
	@Resource
	ErpStorageUserRelationService erpStorageUserRelationService;
	@Resource
	ErpBaseRoleService erpBaseRoleService;
	@Resource
	SysAdminRoleService sysAdminRoleService;
	@Resource
	ErpSysGoodsShelfInfoService erpSysGoodsShelfInfoService;
	@Resource
	NoticeService noticeService;
	private String storage = "storage";
	/**
	 * 仓库列表页面
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model, HttpServletRequest request) {
		List<ErpSysStorage> list = erpSysStorageService.getList(pageNo);
		PageInfo<ErpSysStorage> pageInfo=new PageInfo<ErpSysStorage>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		return "/sysSet/storageList";
	}
	/**
	 * 保存仓库对象
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param erpSysStorage
	 * @param request
	 * @return
	 */
	@RequestMapping("/save.html")
	public String save(Model model,
			@ModelAttribute("erpSysStorage") ErpSysStorage erpSysStorage,
			HttpServletRequest request) {
		if(erpSysStorage.getId() == null || erpSysStorage.getId().intValue() == 0){
			SysAdmin loginAdmin = WebUtil.getLoginUser(request);
			erpSysStorage.setInvalid(InvalidEnum.FALSE.getInvalidValue());
			erpSysStorage.setCreateDateTime(new Date());
			erpSysStorage.setAdmin(loginAdmin);//当前登陆人
			erpSysStorageService.insert(erpSysStorage);
			//获取保存员工跟仓库之间的关系表
			//查询仓库角色列表设置
			ErpBaseRole erpBaseRole = erpBaseRoleService.getErpBaseRoleByType(storage+erpSysStorage.getStorageCode());
			if(erpBaseRole!=null){
				String roles = erpBaseRole.getRoles();
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
										ErpStorageUserRelation  userRelation = new ErpStorageUserRelation();
										userRelation.setAdmin(admin);
										userRelation.setCreateDateTime(new Date());
										userRelation.setInvalid(InvalidEnum.FALSE.getInvalidValue());
										userRelation.setStorage(erpSysStorage);
										erpStorageUserRelationService.insert(userRelation);
										ch.put(admin.getId(), admin.getId());
									}
								}
							}
						}
					}
				}
			}
		}else{
			erpSysStorageService.update(erpSysStorage);
		}
		return "redirect:/sysSet/storage/list.html";
	}
	/**
	 * 货架列表
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param storageId
	 * @param request
	 * @return
	 */
	@RequestMapping("/shelfList.html")
	public String shelfList(
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model,
			@RequestParam(value = "storageId", required = false) Long storageId,
			HttpServletRequest request) {
		List<ErpSysGoodsShelfInfo> shelfInfoListDate = new ArrayList<ErpSysGoodsShelfInfo>();
		List<ErpSysGoodsShelfInfo> shelfInfoList =  erpSysGoodsShelfInfoService.getListPageByParentId(0L,storageId,pageNo);
		for (ErpSysGoodsShelfInfo erpSysGoodsShelfInfo : shelfInfoList) {
			List<ErpSysGoodsShelfInfo> shelfInfoListTemp =  erpSysGoodsShelfInfoService.getListByParentId(erpSysGoodsShelfInfo.getId(),storageId);
			erpSysGoodsShelfInfo.setChildList(shelfInfoListTemp);
			shelfInfoListDate.add(erpSysGoodsShelfInfo);
		}
		PageInfo<ErpSysGoodsShelfInfo> pageInfo=new PageInfo<ErpSysGoodsShelfInfo>(shelfInfoList);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("storage",erpSysStorageService.get(storageId));
		model.addAttribute("list",shelfInfoListDate);
		model.addAttribute("storageId",storageId);
		return "/sysSet/storageShelfList";
	}
	@RequestMapping("/saveshelf.html")
	public String saveshelf(Model model,
			@ModelAttribute("shelfInfo") ErpSysGoodsShelfInfo shelfInfo,
			@RequestParam(value = "storageId", required = false) Long storageId,
			HttpServletRequest request) {
		if(shelfInfo.getId()==null){
			shelfInfo.setCreateDateTime(new Date());
			shelfInfo.setInvalid(InvalidEnum.FALSE.getInvalidValue());
			ErpSysStorage st = new ErpSysStorage();
			st.setId(storageId);
			shelfInfo.setStorage(st);
			erpSysGoodsShelfInfoService.insert(shelfInfo);
		}else{
			ErpSysGoodsShelfInfo oldShelfInfo = erpSysGoodsShelfInfoService.get(shelfInfo.getId());
			erpSysGoodsShelfInfoService.update(shelfInfo);
			ErpSysGoodsShelfInfo newShelfInfo = erpSysGoodsShelfInfoService.get(shelfInfo.getId());
			//添加一条修改记录
			SysAdmin loginAdmin = WebUtil.getLoginUser(request);
			ErpNoticeInfo info = new ErpNoticeInfo();
			info.setAdminId(loginAdmin.getId());
			info.setCreateDateTime(new Date());
			info.setInvalid(InvalidEnum.FALSE.getInvalidValue());
			ErpNoticeCategory erpNoticeCategory = new ErpNoticeCategory();
			erpNoticeCategory.setId(1L);
			info.setErpNoticeCategory(erpNoticeCategory);
			info.setName("货架变更");
			info.setDescription("原货架信息信息:[货架编号:"+oldShelfInfo.getGoodsShelfNumber()+",产品品类:"+oldShelfInfo.getCategory().getName()+","
					+ "产品名称:"+oldShelfInfo.getCategoryModel().getName()+",产品规格:"+oldShelfInfo.getCategoryModelFormat().getName()+"]<br/>"
					+ "现架信息信息:[货架编号:"+newShelfInfo.getGoodsShelfNumber()+",产品品类:"+newShelfInfo.getCategory().getName()+","
					+ "产品名称:"+newShelfInfo.getCategoryModel().getName()+",产品规格:"+newShelfInfo.getCategoryModelFormat().getName()+"]");
			noticeService.insert(info);
		}
		return "redirect:/sysSet/storage/shelfList.html?storageId="+storageId;
	}
	@RequestMapping("/saveshelfAjax.html")
	public void saveshelfAjax(
			@ModelAttribute("shelfInfo") ErpSysGoodsShelfInfo shelfInfo,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		//shelfInfo.setGoodsShelfNumber(WebUtil.getZHhandle(shelfInfo.getGoodsShelfNumber()));
		//shelfInfo.setProductName(WebUtil.getZHhandle(shelfInfo.getProductName()));
		//shelfInfo.setProductNumber(WebUtil.getZHhandle(shelfInfo.getProductNumber()));
		//shelfInfo.setProductStandard(WebUtil.getZHhandle(shelfInfo.getProductStandard()));
		erpSysGoodsShelfInfoService.update(shelfInfo);
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
	public String adminList(Model model,
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			@RequestParam(value = "storageId", required = false) Long storageId,
			HttpServletRequest request) {
		List<ErpStorageUserRelation> list = erpStorageUserRelationService.getListByStorageId(storageId,pageNo);
		PageInfo<ErpStorageUserRelation> pageInfo=new PageInfo<ErpStorageUserRelation>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		model.addAttribute("storageId",storageId);
		return "/sysSet/storageAdminList";
	}
}

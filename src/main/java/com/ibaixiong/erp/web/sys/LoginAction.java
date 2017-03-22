package com.ibaixiong.erp.web.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibaixiong.constant.Constant;
import com.ibaixiong.core.utils.Md5Util;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.SysAdminRole;
import com.ibaixiong.entity.SysModel;
import com.ibaixiong.entity.SysRole;
import com.ibaixiong.erp.service.sys.SysAdminRoleService;
import com.ibaixiong.erp.service.sys.SysAdminService;
import com.ibaixiong.erp.service.sys.SysModelService;
import com.ibaixiong.erp.service.sys.SysRoleService;

/**
 * @description
 * 
 * @author
 * @email
 * @create 2012-10-9 上午9:35:38
 */
@Controller
public class LoginAction {

	@Resource
	SysAdminService adminService;
	@Resource
	SysRoleService roleService;
	@Resource
	SysModelService modelService;
	@Resource
	SysAdminRoleService sysAdminRoleService;
	
	@RequestMapping("/index.html")
	public String index(Model mod) {
		return "index";
	}
		
	@RequestMapping("/login.html")
	public String login(Model mod) {
		return "login";
	}

	@RequestMapping("/dologin.html")
	public String dologin(@ModelAttribute("admin") SysAdmin admin, Model model,
			HttpSession session, HttpServletRequest request) {
		if (StringUtils.isBlank(admin.getLoginName())
				|| StringUtils.isBlank(admin.getUserPwd())) {
			model.addAttribute("admin", admin);
			model.addAttribute("msg", "用户名和密码不能为空!");
			return "login";
		}

		SysAdmin dbadmin = adminService.getAdminByLoginName(admin.getLoginName());
		if (dbadmin == null
				|| dbadmin.getStatus() != Constant.Status.NORMAL.getStatus()) {
			model.addAttribute("admin", admin);
			model.addAttribute("msg", "该账户不存在或已注销!");
			return "login";
		}

		if (!dbadmin.getUserPwd().equals(Md5Util.encode(admin.getUserPwd()))) {
			model.addAttribute("admin", admin);
			model.addAttribute("msg", "密码错误!");
			return "login";
		}
		List<SysAdminRole> sysAdminRoleList = sysAdminRoleService.getSysAdminRoleByAdmin(dbadmin);
		if (sysAdminRoleList == null || sysAdminRoleList.size()<=0) {
			model.addAttribute("admin", admin);
			model.addAttribute("msg", "没有权限!");
			return "login";
		}
		//获取所有的角色列表
		List<SysRole> roleList = new ArrayList<SysRole>();
		for (SysAdminRole sysAdminRole : sysAdminRoleList) {
			roleList.add(sysAdminRole.getRole());
		}
		//获取所有角色下的models的ID集合
		String modelsStr = ",";
		for (SysRole sysRole : roleList) {
			String[] ss = sysRole.getModels().split(",");
			for (String string : ss) {
				if(modelsStr.indexOf(","+string+",")<0){
					modelsStr = modelsStr+string+",";
				}
			}
		}
		List<SysModel> models = new ArrayList<SysModel>();
		String delHead = modelsStr.substring(1);
		modelsStr = delHead.substring(0,delHead.length()-1);
		//计算完成
		String[] ss = modelsStr.split(",");
		List<Long> modelIds = new ArrayList<Long>();
		for (int i = 0; i < ss.length; i++) {
			modelIds.add(Long.parseLong(ss[i]));
		}
		models = modelService.getModelListByIds(modelIds);

		session.setAttribute("admin", dbadmin);
		session.setAttribute("models", sortModels(models));

		return "redirect:/index.html";
	}
	/**
	 * 对查询出来的菜单进行二次排序
	 * @author zhaolei
	 * @date 2015年8月26日
	 * @param models
	 * @return
	 */
	private List<SysModel> sortModels(List<SysModel> models){
		List<SysModel> returnList = new ArrayList<SysModel>();
		List<SysModel> sortList = new ArrayList<SysModel>();
		//初始化第一层菜单
		for (SysModel sysModel : models) {
			if(sysModel.getSysTag()!=null&&sysModel.getSysTag().equals("erp")){
				if(sysModel.getParentModel().getId().intValue() == 0){
					sortList.add(sysModel);
				}
			}
		}
		Map<Long, List<SysModel>> childMap = new HashMap<Long, List<SysModel>>();
		for (SysModel sysModel : sortList) {
			for (SysModel sysModel1 : models) {
				if(sysModel1.getSysTag()!=null&&sysModel1.getSysTag().equals("erp")){
					if(sysModel1.getParentModel() != null){
						if(sysModel.getId().intValue() == sysModel1.getParentModel().getId().intValue()){
							List<SysModel> tempList = null;
							if(childMap.containsKey(sysModel.getId())){//找到了有存在主键
								tempList = childMap.get(sysModel.getId());
							}else{
								tempList = new ArrayList<SysModel>();
							}
							tempList.add(sysModel1);
							childMap.put(sysModel.getId(), tempList);
						}
					}
				}
			}
		}
		for (SysModel sysModel : sortList) {
			sysModel.setChildList(childMap.get(sysModel.getId()));
			returnList.add(sysModel);
		}
		return returnList;
	}
	@RequestMapping("/logout.html")
	public String logout(HttpSession session) {
		session.setAttribute("admin", null);
		session.setAttribute("models", null);
		return "login";
	}
	public static void main(String[] args) {
		System.out.println(Md5Util.encode("123456"));
	}
}

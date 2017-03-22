/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.web.sysset;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.ibaixiong.core.utils.Md5Util;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.sys.SysAdminService;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年7月2日-下午5:49:12
 */
@Controller
@RequestMapping("/sysSet/admin")
public class AdminAction {
	@Resource
	SysAdminService adminService;
	/**
	 * 系统管理>个人中心页
	 * @author zhaolei
	 * @date 2015年7月8日
	 * @param admin
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAdmin.html")
	public String getAdmin(Model model, HttpServletRequest request) {
		model.addAttribute("admin",com.ibaixiong.erp.web.util.WebUtil.getLoginUser(request));
		return "/sysSet/admin";
	}
	/**
	 * 系统管理>个人中心页>修改手机号码 ajax 提交方式
	 * @author zhaolei
	 * @date 2015年7月8日
	 * @param admin
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePhone.html")
	public void updatePhone(
			@RequestParam(value = "phone", required = false) String phone,
			Model model,HttpServletResponse response, HttpServletRequest request) {
		SysAdmin admin = com.ibaixiong.erp.web.util.WebUtil.getLoginUser(request);
		admin.setPhone(phone);
		adminService.updateAdmin(admin);
		PrintWriter writer = null;
        try {
       	 	writer = response.getWriter();
            writer.write(JSON.toJSONString(ResponseResult.result(0, "")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
	}
	/**
	 * 系统管理>修改密码 ajax 提交方式
	 * @author zhaolei
	 * @date 2015年7月8日
	 * @param admin
	 * @param oldPwd 旧密码
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePwd.html")
	public void updatePwd(
			@RequestParam(value = "oldPwd", required = false) String oldPwd,
			@RequestParam(value = "newPwd", required = false) String newPwd,
			Model model,HttpServletResponse response, HttpServletRequest request) {
		SysAdmin admin = com.ibaixiong.erp.web.util.WebUtil.getLoginUser(request);
		//通过跟数据库的密码对比原密码，比对一致后修改
		String msg = "";
		int code = 0;
		if(admin != null){
			//密码比对正确
			if(admin.getUserPwd().equals(Md5Util.encode(oldPwd))){
				admin.setUserPwd(Md5Util.encode(newPwd));
				adminService.updateAdmin(admin);
			}else{
				code = 1;
				msg = "原密码不正确";
			}
		}else{
			code = 2;
			msg = "用户未找到";
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
	/**
	 * 员工管理  禁用用户与解除禁用 ajax 提交方式
	 * @author zhaolei
	 * @date 2015年7月8日
	 * @param admin
	 * @param oldPwd 旧密码
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateStatus.html")
	public void updateStatus(
			@RequestParam(value = "status", required = false) Byte status,
			@RequestParam(value = "adminId", required = false) Long id,
			Model model,HttpServletResponse response) {
		SysAdmin opadmin = adminService.getAdminById(id);
		String msg = "";
		int code = 0;
		if(opadmin != null){
			//设置状态
			opadmin.setStatus(status);
			adminService.updateAdmin(opadmin);
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
	@RequestMapping("/testTran.html")
	public void testTran(
			Model model,HttpServletResponse response, HttpServletRequest request){
		SysAdmin admin = com.ibaixiong.erp.web.util.WebUtil.getLoginUser(request);
		//通过跟数据库的密码对比原密码，比对一致后修改
		String msg = "";
		int code = 0;
		try {
			adminService.insertBatch();
		} catch (Exception e1) {
			e1.printStackTrace();
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
}

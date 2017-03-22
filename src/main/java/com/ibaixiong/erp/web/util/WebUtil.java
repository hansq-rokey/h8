package com.ibaixiong.erp.web.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.ibaixiong.entity.SysAdmin;

/**
 * @author zhaolei
 * @date 2015-7-13
 */
public class WebUtil {
	
	/**
	 * 获取登陆的用户信息
	 * @param request
	 * @return
	 */
	public static SysAdmin getLoginUser(HttpServletRequest request) {
		return (SysAdmin) request.getSession().getAttribute("admin");
	}
	public static String getZHhandle(String str){
		try {
			str = new String(str.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}

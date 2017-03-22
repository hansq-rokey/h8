package com.ibaixiong.erp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.web.util.WebUtil;



@SuppressWarnings("serial")
public class LoginFilter extends HttpServlet implements Filter{
	
	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		String requri = request.getRequestURI();
		String contextPath = request.getContextPath();
		//过滤器添加判断是否是德邦快递推送url是的话不拦截
		if(requri.endsWith("dbPush.html")||requri.endsWith("/fwmupload.html")||requri.endsWith("/bind.html")){
			filterChain.doFilter(sRequest, sResponse);
		}else{
			SysAdmin admin = WebUtil.getLoginUser(request);
			//用户已登录
			if(admin != null){
				//远程地址
				//String remoteAddr = request.getRemoteAddr();
				sRequest.setAttribute("admin", admin);
				filterChain.doFilter(sRequest, sResponse);
			//访问的是登录页面或登录请求
			}else if(requri.equals(contextPath+"/login.jsp")||requri.contains("/login.html")||requri.contains("/dologin.html")){
				if(requri.endsWith("getIndex.html")){
					response.sendRedirect(contextPath + "/login.jsp");
				}else{
					filterChain.doFilter(sRequest, sResponse);
				}
			}else if(requri.endsWith(".html")||requri.endsWith(".action")||requri.endsWith(".do")||requri.endsWith(".jsp")){
				//如果非注册或错误等直接跳转到login.jsp
				if(requri.equals(contextPath+"/userRegist.jsp")||requri.contains("/popup/")||requri.contains("/system/")){
					filterChain.doFilter(sRequest, sResponse);
				}else{
					response.sendRedirect(contextPath + "/login.jsp");
				}
			}else{
				filterChain.doFilter(sRequest, sResponse);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
}

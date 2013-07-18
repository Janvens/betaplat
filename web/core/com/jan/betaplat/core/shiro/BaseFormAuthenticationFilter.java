/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {
	
	private static final Logger _log = Logger.getLogger(BaseFormAuthenticationFilter.class) ;

	/**
	 * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
				|| request.getParameter("ajax") == null) {// 不是ajax请求
			
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
		} else {
			httpServletRequest.getRequestDispatcher("/login/timeout/success").forward(httpServletRequest, httpServletResponse);
		}
		
		return false;
	}
}

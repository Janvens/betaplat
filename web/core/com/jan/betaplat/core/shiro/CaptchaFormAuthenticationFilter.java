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
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import com.jan.betaplat.core.util.LocationUtil;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public class CaptchaFormAuthenticationFilter extends BaseFormAuthenticationFilter {

	private String captchaParam = SimpleCaptchaServlet.CAPTCHA_KEY;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new CaptchaUsernamePasswordToken(username, password, rememberMe,
				host, captcha);
	}
	
	/* 
	 * desc:
	 * (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#getHost(javax.servlet.ServletRequest)
	 */
	@Override
	protected String getHost(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest)request;
		return LocationUtil.getIpAddr(req);
	}

}

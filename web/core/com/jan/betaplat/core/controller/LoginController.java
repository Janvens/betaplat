/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.controller;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jan.betaplat.core.common.IPSeeker;
import com.jan.betaplat.core.common.SecurityConstants;
import com.jan.betaplat.core.po.UserLoginInfo;
import com.jan.betaplat.core.service.UserLoginInfoService;
import com.jan.betaplat.core.shiro.ShiroDbRealm;
import com.jan.betaplat.core.util.LocationUtil;
import com.jan.betaplat.core.util.dwz.AjaxObject;


/** 
 * desc:用户登陆控制器
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Controller
@RequestMapping("/login")
public class LoginController {
//	private static final String LOGIN_PAGE_HTML4 = "login_html4";
//	private static final String LOGIN_PAGE_HTML5 = "login_html5";
	private static final String LOGIN_DIALOG = "sys/index/loginDialog";
	
	private static String LOGIN_PAGE = "login";
	
	@Autowired
	private UserLoginInfoService userLoginInfoService;

	@RequestMapping(method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		return LOGIN_PAGE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:21:53</p>
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String loginDialog(HttpServletRequest request) {
		AjaxObject ajaxObject = new AjaxObject("会话超时，请重新登录。");
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_TIMEOUT);
		ajaxObject.setCallbackType(AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	@RequestMapping(value = "/timeout", method = { RequestMethod.GET })
	public String timeout() {
		System.out.println("login is time out --------");
		return LOGIN_DIALOG;
	}

	@RequestMapping(value = "/timeout/success", method = { RequestMethod.POST })
	public @ResponseBody
	String timeoutSuccess(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();

		request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		request.getSession().setAttribute(SecurityConstants.LOGIN_INFO, shiroUser.getUser());
		
		AjaxObject ajaxObject = new AjaxObject("登录成功。");
		ajaxObject.setCallbackType(AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:21:57</p>
	 * @param username
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
			Map<String, Object> map, HttpServletRequest request) {

		String msg = parseException(request,username);
		
		map.put("msg", msg);
		map.put("username", username);
		
		return LOGIN_PAGE;
	}

	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:22:00</p>
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String failDialog(HttpServletRequest request) {
		String msg = parseException(request,"");
		
		AjaxObject ajaxObject = new AjaxObject(msg);
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");

		return ajaxObject.toString();
	}

	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:22:02</p>
	 * @param request
	 * @return
	 */
	private String parseException(HttpServletRequest request,String username) {
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String msg = "其他错误！";
		if (error != null) {
			if ("org.apache.shiro.authc.UnknownAccountException".equals(error)){
				msg = "该用户名不存在！";
			}else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(error)){
				msg = "密码错误！";
				pwdError(request, username);
			}else if ("com.umpay.sys.exception.IncorrectCaptchaException".equals(error)){
				msg = "验证码错误！";
			}else if ("org.apache.shiro.authc.AuthenticationException".equals(error)){
				msg = "认证失败！";
			}else if ("org.apache.shiro.authc.DisabledAccountException".equals(error)){
				msg = "账号被冻结！";
			}else if("com.umpay.sys.exception.AccountLoseException".equals(error)){
				msg = "该用户尚未生效或已经失效！";
			}
		}
		return "登录失败，" + msg;
	}
	
	private void pwdError(HttpServletRequest request, String username){
		UserLoginInfo userLoginInfo = userLoginInfoService.findByUsername(username);
		
		// 传入空的用户名，不记录
		if(StringUtils.isEmpty(username)){
			return;
		}
		if(userLoginInfo == null){
			userLoginInfo = new UserLoginInfo();
			userLoginInfo.setUsername(username);
		}
		
		userLoginInfo.setLastip(userLoginInfo.getCurip());
		userLoginInfo.setCurip(LocationUtil.getIpAddr(request));
		userLoginInfo.setLastloginarea(userLoginInfo.getCurloginarea());
		userLoginInfo.setCurloginarea(IPSeeker.getInstance().getAddress(LocationUtil.getIpAddr(request)));
		userLoginInfo.setErrorcount(userLoginInfo.getErrorcount() + 1);
		userLoginInfo.setLastloginstate(userLoginInfo.getCurloginstate());
		userLoginInfo.setCurloginstate(UserLoginInfo.LOGIN_FAIL);
		userLoginInfo.setLastlogintime(userLoginInfo.getCurlogintime());
		userLoginInfo.setCurlogintime(new Date());
		userLoginInfoService.save(userLoginInfo);
	}
}

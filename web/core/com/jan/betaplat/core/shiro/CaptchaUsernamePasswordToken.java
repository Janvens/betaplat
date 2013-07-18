/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;


/** 
 * desc:用户名和密码 Token
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	/** 描述 */
	private static final long serialVersionUID = -3178260335127476542L;

	private String captcha;
	
	private boolean rememberMe;

	/**
	 * Zhang Wensheng 2013-6-20 下午3:10:34
	 */
	public CaptchaUsernamePasswordToken() {
		super();
	}
	
	/**
	 * Zhang Wensheng 2013-6-20 下午3:10:32
	 * @param username 用户名
	 * @param password 密码
	 * @param rememberMe 是否记住
	 * @param host 请求地址
	 * @param captcha 图片验证码
	 */
	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.rememberMe = rememberMe;
	}

	
	/**
	 * @return the captcha
	 */
	public String getCaptcha(){
		return captcha;
	}

	
	/**
	 * @param captcha the captcha to set
	 */
	public void setCaptcha(String captcha){
		this.captcha = captcha;
	}

	
	/**
	 * @return the rememberMe
	 */
	public boolean isRememberMe(){
		return rememberMe;
	}

	
	/**
	 * @param rememberMe the rememberMe to set
	 */
	public void setRememberMe(boolean rememberMe){
		this.rememberMe = rememberMe;
	}
	
}

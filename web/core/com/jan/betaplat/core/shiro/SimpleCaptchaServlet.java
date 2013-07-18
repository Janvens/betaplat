/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.shiro;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jan.betaplat.core.util.VerifyCodeUtil;

/** 
 * desc:图片验证码校验
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public class SimpleCaptchaServlet extends HttpServlet {
	
	/** 描述  */
	private static final long serialVersionUID = -314440845042577575L;
	private int height = 100;
	private int width = 30;
	private int length = 4;

	public static final String CAPTCHA_KEY = "captcha_key";

	/* 
	 * desc:图片大小
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		height = Integer
				.parseInt(getServletConfig().getInitParameter("height"));
		width = Integer.parseInt(getServletConfig().getInitParameter("width"));
	}

	/* 
	 * desc: doGet
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException, ServletException {
		// 截取长度
		if (length > 12 || length < 1) {
			length = 12;
		}
		// 生成图片验证码
		VerifyCodeUtil.create(width, height, 2, CAPTCHA_KEY, req, response);

	}

}
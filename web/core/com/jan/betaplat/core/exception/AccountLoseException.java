/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-5下午4:06:50</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.exception;

import org.apache.shiro.authc.AuthenticationException;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-5 </p>
 * @version V1.0  
 */
public class AccountLoseException extends AuthenticationException{

	/**
	 * long:serialVersionUID 
	 */
	private static final long serialVersionUID = -9119662718667897365L;

	public AccountLoseException(){
		super();
	}
	public AccountLoseException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountLoseException(String message) {
		super(message);
	}

	public AccountLoseException(Throwable cause) {
		super(cause);
	}
	
}

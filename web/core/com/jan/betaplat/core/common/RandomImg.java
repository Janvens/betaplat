/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-5-30下午5:46:24</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.common;

import java.io.Serializable;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-5-30 </p>
 * @version V1.0  
 */
public class RandomImg implements Serializable{
	
	/**
	 * long:serialVersionUID 
	 */
	private static final long serialVersionUID = -8997308901427600043L;
	private static final int timeOut = 2*60*1000;
	private String rand;
	private  long initTime;

	public RandomImg(String rand){
		this.rand = rand;
		this.initTime = System.currentTimeMillis();
	}
	
	public boolean isTimeOut(){
		return (System.currentTimeMillis()-initTime>timeOut);
	}
	public String getRand(){
		return this.rand;
	}
	
}

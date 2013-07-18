/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-6下午03:52:46</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.common;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-6 </p>
 * @version V1.0  
 */
public class IPModel{

	public String beginIp;
	public String endIp;
	public String country;
	public String area;

	public IPModel() {
		beginIp = "";
		endIp = "";
		country = "";
		area = "";
	}

	@Override
	public String toString() {
		return this.area + "  " + this.country + "  IP范围:" + this.beginIp + "-"
				+ this.endIp;
	}

}

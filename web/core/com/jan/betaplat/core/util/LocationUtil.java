/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-6下午03:23:52</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.util;

import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-6 </p>
 * @version V1.0  
 */
public class LocationUtil{

	/**
	 * desc:从ip的字符串形式得到字节数组形式 
	 * <p>创建人：Zhang Wensheng , 2013-6-6 下午03:28:02</p>
	 * @param ip  字符串形式的ip 
	 * @return 字节数组形式的ip
	 */
	public static byte[] getIpByteArrayFromString(String ip){
		byte[] ret = new byte[4];
		StringTokenizer st = new StringTokenizer(ip,".");
		try{
			ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
		}catch (Exception e) {
			e.printStackTrace();//TODO
		}
		return ret;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-6-6 下午03:33:19</p>
	 * @param ip ip的字节数组形式
	 * @return 字符串形式的ip
	 */
	public static String getIpStringFromBytes(byte[] ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		return sb.toString();
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip!=null&&ip.length()>0&&!"unknown".equalsIgnoreCase(ip)){
			return ip;
		}else if(null!=request.getHeader("Proxy-Client-IP")
				&&request.getHeader("Proxy-Client-IP").length()>0
				&&!"unknown".equalsIgnoreCase(request.getHeader("Proxy-Client-IP"))){
			return request.getHeader("Proxy-Client-IP");
		}else if(null!=request.getHeader("WL-Proxy-Client-IP")
				&&request.getHeader("WL-Proxy-Client-IP").length()>0
				&&!"unknown".equalsIgnoreCase(request.getHeader("WL-Proxy-Client-IP"))){
			return request.getHeader("WL-Proxy-Client-IP");
		}else{
			return request.getRemoteHost();
		}
	}
	
	public static void printRequestParams(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		System.out.println(" print Request Params ----------------------------------------------------------");
		while(names.hasMoreElements()){
			String key = names.nextElement();
			String value = request.getParameter(key);
			if(value == null || value.trim().equals("")){
				 continue;
			}
			System.out.println("--------RequestName:" + key + ",RequestValue:" + value);
		}
		System.out.println(" print Request Params ----------------------------------------------------------");
	}
	
}

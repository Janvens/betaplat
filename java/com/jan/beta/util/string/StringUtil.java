/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2012-11-16下午03:34:56</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.beta.util.string;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;

/** 
 * desc:字符串处理工具类
 * <p>创建人：Zhang Wensheng 创建日期：2012-11-16 </p>
 * @version V1.0  
 */
public class StringUtil extends StringUtils{

	/**
	 * desc:去空格,如果是null则返回空串
	 * <p>创建人：Zhang Wensheng , 2012-11-16 下午03:35:57</p>
	 * @param obj 
	 * @return
	 */
	public static String trimObj(Object obj){
		if(null==obj){
			return "";
		}else{
			return trimToEmpty(obj.toString());
		}
	}
	
	/**
	 * desc:获取手机支付平台的MID 14为数字
	 * <p>创建人：Zhang Wensheng , 2012-12-11 上午11:09:34</p>
	 * @param no
	 * @return
	 */
	public static String getMid(String no){
		no = trimObj(no);
		StringBuilder sb = new StringBuilder("2");
		if(no.length()<13){
			int j=13-no.length();
			for(int i=0;i<j;i++){
				sb.append('0');
			}
			sb.append(no);
		}else{
			sb.append(no.subSequence(no.length()-13, no.length()));
		}
		return sb.toString();
	}
	/**
	 * desc:	ISO8859字符串转换成GB2312字符串
	 * <p>创建人：Zhang Wensheng , 2013年7月9日 下午2:36:54</p>
	 * @param s 待转换字符串
	 * @return	转换后字符串
	 */
	public static String ISO8859toGB2312(String s) {

		try {
			return new String(s.getBytes("ISO-8859-1"), "GB2312");
		} catch(UnsupportedEncodingException e) {
			return null;
		}
	}
	
	
	 /**
	  * 
	 * <p>方法名称: fixZeros|描述: 在字符串前补0 </p>
	 * @param length 长度
	 * @param num 原字符串或数字
	 * @return
	  */
	 public static String fixZeros(int length,Object num){
		 StringBuilder sb = new StringBuilder("");
		 int len = null==num?0:num.toString().length();
		 if(len>length){
			 return num.toString().substring(len-length,len);
		 }
			 
		for(int i = 0; i < length - num.toString().length(); i++){
			sb.append("0");
		}
		 return sb.append(num).toString();
	 }
	 
}



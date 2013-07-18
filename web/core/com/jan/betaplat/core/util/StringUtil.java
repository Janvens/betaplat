/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-6下午03:31:06</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.util;

import java.io.UnsupportedEncodingException;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-6 </p>
 * @version V1.0  
 */
public class StringUtil{

	/**
	 * desc:对原始字符串进行编码转换，如果失败，返回原始的字符串 
	 * <p>创建人：Zhang Wensheng , 2013-6-6 下午03:29:01</p>
	 * @param s 原始字符串 
	 * @param srcEncodeing 源编码方式 
	 * @param destEncoding 目标编码方式 
	 * @return 转换编码后的字符串，失败返回原始字符串 
	 */
	public static String getString(String str, String srcEncoding, String destEncoding){
		try{
			return new String(str.getBytes(srcEncoding), destEncoding); 
		}catch (UnsupportedEncodingException  e) {
			return str;
		}
	}

	/**
	 * desc: 根据某种编码方式将字节数组转换成字符串 
	 * <p>创建人：Zhang Wensheng , 2013-6-6 下午03:31:53</p>
	 * @param b 字节数组 
	 * @param offset  要转换的起始位置 
	 * @param len 要转换的长度 
	 * @param encoding 编码方式 
	 * @return  如果encoding不支持，返回一个缺省编码的字符串 
	 */
	public static String getString(byte[] b, int offset, int len, String encoding) {
		try {
			return new String(b, offset, len, encoding);
		} catch (UnsupportedEncodingException e) {
			return new String(b, offset, len);
		}
	}

}

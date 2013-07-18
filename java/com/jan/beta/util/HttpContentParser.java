/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013年7月15日下午3:18:32</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.beta.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013年7月15日 </p>
 * @version V1.0  
 */
public class HttpContentParser {

	private static final Pattern p = Pattern.compile("(.+)=(.*)");

	public static Map<String, String> parse(String rqc) {
		String[] rqcArray = rqc.split("&");
		if(rqcArray.length == 0 ) {
			return null;
		}
		Map<String, String> reqMap = new HashMap<String, String>();
		Matcher match = null;
		for(String s: rqcArray) {
			match = p.matcher(s);
			while(match.find() ) {
				reqMap.put(match.group(1), match.group(2));
			}
		}
		return reqMap;
	}
	
	public static String[] getResArray(String content){
		Pattern regx = Pattern.compile("<META.*>");
		Matcher mMeta = regx.matcher(content);
		if (mMeta.find()) {// 从xml表单中提取<meta>数据
			String meta = mMeta.group();
			if(null == meta || meta.length() == 0 || !meta.contains("MobilePayPlatform"));
			regx = Pattern.compile("(CONTENT=\"(.*)\")");
			Matcher match = regx.matcher(meta);
			if (match.find()) {
				String strData = match.group(2);// 所有明文
				return strData.split("\\|");
			}
		}
		return null;
	}
}

/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013年7月16日上午9:35:57</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.beta.util.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** 
 * desc:小额前置返回报文解析
 * <p>创建人：Zhang Wensheng 创建日期：2013年7月16日 </p>
 * @version V1.0  
 */
public class METAContentUtil {
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013年7月16日 上午9:38:23</p>
	 * @param content
	 * @return
	 */
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

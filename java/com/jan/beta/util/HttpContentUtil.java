/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013年7月16日上午10:04:05</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.beta.util;

import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jan.beta.util.file.FileUtil;
import com.jan.beta.util.string.StringUtil;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013年7月16日 </p>
 * @version V1.0  
 */
public class HttpContentUtil {

	private static Log _log = LogFactory.getLog(FileUtil.class);
	/**
	 * String:BUSI_S 服务端字段配置
	 */
	static final String BUSI_S = ".busi.S";
	/**
	 * String:BUSI_C 客户端字段配置
	 */
	static final String BUSI_C = ".busi.C";
	/**
	 * String:RPDATA 中括号
	 */
	static final String RPDATA = "^\\[.*\\]$";
	/**
	 * String:MIDBRACKETS 中括号
	 */
	static final String MIDBRACKETS = "\\[|\\]";
	/**
	 * String:BEANDATA java方法
	 */
	static final String BEANDATA = "^@\\w+\\(\\d+(\\,\\d+)*\\)$";
	/**
	 * String:BEANNAME Bean 名称
	 */
	static final String BEANNAME = "@|\\(.*";
	/**
	 * String:ANYWORK 任何字符
	 */
	static final String ANYWORK = "\\w+";

	public static String getContent(String funCode, Map reqMap) throws Exception {
		String[] keyArray_C = getClientKeys(funCode);
		String[] keyArray_S = getServerKeys(funCode);
		StringBuilder sb = new StringBuilder();
		String value = null;
		for(int i = 0; i < keyArray_C.length; i++) {
			if(keyArray_S[i].matches(RPDATA) ) {
				value = keyArray_S[i].replaceAll(MIDBRACKETS, "");
			} else if(keyArray_S[i].matches(BEANDATA) ) {
				String beanName = keyArray_S[i].replaceAll(BEANNAME, "");
				String[] values = getParams(keyArray_S[i], keyArray_S, reqMap);
				// RequestGen gen = (RequestGen)
				// BeansContext.getInstance().getBean(beanName);
				// value = gen.getValue(values);
			} else {
				value = StringUtil.trimObj(reqMap.get(keyArray_S[i]));
			}
			sb.append(keyArray_C[i]).append("=").append(value).append("&");
		}
		sb.deleteCharAt(sb.lastIndexOf("&"));
		String sign = "";
//		String sign = SignUtil.sign(sb.toString(), MappingMgr.getInstance().get("mer.priCert",""));
		
		sign = URLEncoder.encode(sign);
		sb.append("&sign=").append(sign);
		return sb.toString();
	}

	private static String[] getServerKeys(String funCode) throws Exception {
//		String keyConfig_S = MappingMgr.getInstance().get(funCode + BUSI_S, "");
//		_log.debug("keyConfig_S:  %s", keyConfig_S);
//		if(StringUtil.isEmpty(keyConfig_S) ) {
//			_log.info("传入的funcode不合法，或没有配置该funcode的请求信息，funcode [%s]", funCode);
//			throw new Exception("传入的funcode不合法");
//		}
//		String[] keyArray_S = keyConfig_S.split(",");
//		return keyArray_S;
		return null;
	}

	private static String[] getClientKeys(String funCode) throws Exception {
//		String keyConfig_C = MappingMgr.getInstance().get(funCode + BUSI_C,"");
//		_log.debug("keyConfig_C :  [%s]", keyConfig_C);
//		if(StringUtil.isEmpty(keyConfig_C) ) {
//			_log.info("传入的funcode不合法，或没有配置该funcode的请求信息，funcode [%s]", funCode);
//			throw new Exception("传入的funcode不合法");
//		}
//		String[] keyArray_C = keyConfig_C.split(",");
//		return keyArray_C;
		return null;
	}

	private static String[] getParams(String paramStr, String[] keyArray_S, Map<String, String> reqMap) {
		final Pattern pat = Pattern.compile("\\d+(\\,\\d+)*");
		Matcher mat = pat.matcher(paramStr);
		String[] params = null;
		if(mat.find() ) {
			params = mat.group(0).split(",");
		}
		String[] values = new String[params.length];
		int i = 0;
		String tmpStr = null;
		for(String int_s: params) {
			tmpStr = keyArray_S[Integer.parseInt(int_s)];
			if(tmpStr.matches(RPDATA) ) {
				values[i] = tmpStr.replaceAll(MIDBRACKETS, "");
			} else if(tmpStr.matches(ANYWORK) ) {
				values[i] = reqMap.get(tmpStr);
			} else {
//				_log.error(null, "%s 参数配置有误", tmpStr);
			}
			i++;
		}
		return values;
	}
}

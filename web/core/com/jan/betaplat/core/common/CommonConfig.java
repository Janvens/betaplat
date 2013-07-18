/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-14下午5:20:20</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/** 
 * desc:系统配置信息类
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-14 </p>
 * @version V1.0  
 */
public class CommonConfig{
	/** 是否判断检查浏览器支持html5*/
	private static boolean isCheckHtml5 = false;
	
	/**
	 * desc:加载系统配置文件
	 * <p>创建人：Zhang Wensheng , 2013-6-17 上午11:04:09</p>
	 * @param cfgFilePath
	 */
	public static void loadConfig(String cfgFilePath){
		File file = new File(cfgFilePath);
		if(!file.exists())throw new RuntimeException("配置文件不存在！"+cfgFilePath);
		Properties prop = new Properties();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			prop.load(fis);
			if(prop.containsKey("sys.isCheckHtml5"))isCheckHtml5=Boolean.parseBoolean(prop.getProperty("sys.isCheckHtml5"));
			
			
		}catch(FileNotFoundException ex){
			throw new RuntimeException(ex);
		}catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * desc:判断浏览器是否支持html5
	 * <p>创建人：Zhang Wensheng , 2013-6-17 上午11:04:28</p>
	 * @return true|fasle
	 */
	public static boolean isCheckHtml5(){
		return isCheckHtml5;
	}
}

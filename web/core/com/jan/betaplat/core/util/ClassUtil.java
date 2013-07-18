/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-17上午10:02:30</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/** 
 * desc:系统类工具类
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-17 </p>
 * @version V1.0  
 */
public class ClassUtil{
	
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}
	
	/**
	 * desc:获取类对象名称
	 * <p>创建人：Zhang Wensheng , 2013-6-17 上午11:05:25</p>
	 * @param clazz
	 * @return
	 */
	public static String getClassName(Class<?> clazz){
		return getSuperClassGenricType(clazz, 0).getSimpleName();
		
	}
}

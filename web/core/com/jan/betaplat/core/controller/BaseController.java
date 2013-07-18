/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-17上午9:59:05</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.controller;

import com.jan.betaplat.core.util.ClassUtil;


/** 
 * desc: 系统控制器基础控制类
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-17 </p>
 * @version V1.0  
 */
public class BaseController<T>{
	
	/**
	 * String:PREFIX 业务页面包的前缀,可以根据业务需求而修改
	 */
	private final static String PREFIX = "busi/";
	
	/**
	 * String:sClassName 泛型类的类名称
	 */
	private final String viewPath = PREFIX + ClassUtil.getClassName(getClass()).toLowerCase();
	
	/**
	 * String:CREATE 新增记录页面
	 */
	protected final String CREATE = viewPath + "/create";
	/**
	 * String:update 更新记录页面
	 */
	protected final String UPDATE = viewPath + "/update";
	/**
	 * String:LIST 记录列表页面
	 */
	protected final String LIST = viewPath + "/list";
	/**
	 * String:download 业务下载页面
	 */
	protected final String DOWNLOAD = viewPath + "/download";
	
	
}

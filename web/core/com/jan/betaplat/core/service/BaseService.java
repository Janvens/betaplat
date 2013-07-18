/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service;

import java.io.Serializable;
import java.util.List;
import com.jan.betaplat.core.util.dwz.Page;



/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public interface BaseService<T, ID extends Serializable> {
	void save(T entity);
	
	void update(T entity);
	
	void delete(ID id);
	
	T get(ID id);
	
	List<T> findAll();
	
	List<T> findAll(Page page);
}

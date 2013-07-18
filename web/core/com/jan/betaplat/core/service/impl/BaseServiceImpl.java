/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service.impl;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.jan.betaplat.core.service.BaseService;
import com.jan.betaplat.core.util.PageUtils;
import com.jan.betaplat.core.util.dwz.Page;



/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

	private JpaRepository<T, ID> jpaRepository;
	
	public BaseServiceImpl(JpaRepository<T, ID> jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	/**   
	 * @param entity  
	 * @see com.umpay.sys.service.BaseService#save(java.lang.Object)  
	 */
	@Transactional
	public void save(T entity) {
		jpaRepository.save(entity);
	}

	/**   
	 * @param entity  
	 * @see com.umpay.sys.service.BaseService#update(java.lang.Object)  
	 */
	@Transactional
	public void update(T entity) {
		jpaRepository.save(entity);
	}

	/**   
	 * @param id  
	 * @see com.umpay.sys.service.BaseService#delete(java.io.Serializable)  
	 */
	@Transactional
	public void delete(ID id) {
		jpaRepository.delete(id);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.umpay.sys.service.BaseService#get(java.io.Serializable)  
	 */
	public T get(ID id) {
		return jpaRepository.findOne(id);
	}

	/**   
	 * @return  
	 * @see com.umpay.sys.service.BaseService#findAll()  
	 */
	public List<T> findAll() {
		return jpaRepository.findAll();
	}

	/**   
	 * @param page
	 * @return  
	 * @see com.umpay.sys.service.BaseService#findAll(com.umpay.sys.util.dwz.Page)  
	 */
	public List<T> findAll(Page page) {
		org.springframework.data.domain.Page<T> springDataPage = jpaRepository.findAll(PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, springDataPage);
		return springDataPage.getContent();
	}
}

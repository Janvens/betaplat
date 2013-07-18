/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jan.betaplat.core.dao.RoleDao;
import com.jan.betaplat.core.po.Role;
import com.jan.betaplat.core.service.RoleService;
import com.jan.betaplat.core.shiro.ShiroDbRealm;
import com.jan.betaplat.core.util.PageUtils;
import com.jan.betaplat.core.util.dwz.Page;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Service
@Transactional(readOnly=true)
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {
	
	private RoleDao roleDao;
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
	@Autowired
	public RoleServiceImpl(RoleDao roleDao) {
		super((JpaRepository<Role, Long>) roleDao);
		this.roleDao = roleDao;
	}

	/**   
	 * @param role  
	 * @see com.umpay.sys.service.RoleService#update(umpay_platCache.Role)  
	 */
	@Transactional
	public void update(Role role) {
		roleDao.save(role);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	/**   
	 * @param id  
	 * @see com.umpay.sys.service.RoleService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) {
		roleDao.delete(id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	/**   
	 * @param page
	 * @param name
	 * @return  
	 * @see com.umpay.sys.service.RoleService#find(com.umpay.sys.util.dwz.Page, java.lang.String)  
	 */
	public List<Role> find(Page page, String name) {
		org.springframework.data.domain.Page<Role> roles = 
				(org.springframework.data.domain.Page<Role>)roleDao.findByNameContaining(name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, roles);
		return roles.getContent();
	}

}

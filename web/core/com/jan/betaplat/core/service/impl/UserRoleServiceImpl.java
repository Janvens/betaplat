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
import com.jan.betaplat.core.dao.UserRoleDao;
import com.jan.betaplat.core.po.UserRole;
import com.jan.betaplat.core.service.UserRoleService;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Service
@Transactional(readOnly=true)
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Long> implements UserRoleService {

	private UserRoleDao userRoleDao;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
	@Autowired
	public UserRoleServiceImpl(UserRoleDao userRoleDao) {
		super((JpaRepository<UserRole, Long>) userRoleDao);
		this.userRoleDao = userRoleDao;
	}

	/**   
	 * @param userId
	 * @return  
	 * @see com.umpay.sys.service.UserRoleService#find(Long)  
	 */
	public List<UserRole> find(Long userId) {
		return userRoleDao.findByUserId(userId);
	}

}

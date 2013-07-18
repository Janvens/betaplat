/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-5下午4:55:43</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jan.betaplat.core.dao.UserLoginInfoDao;
import com.jan.betaplat.core.po.UserLoginInfo;
import com.jan.betaplat.core.service.UserLoginInfoService;
import com.jan.betaplat.core.shiro.ShiroDbRealm;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-5 </p>
 * @version V1.0  
 */
@Service
@Transactional(readOnly=true)
public class UserLoginInfoServiceImpl extends BaseServiceImpl<UserLoginInfo, Long> implements UserLoginInfoService{

	private UserLoginInfoDao userLoginInfoDao;
	
	@Autowired
	private ShiroDbRealm shiroRealm;
	
	/**
	 * Zhang Wensheng 2013-6-5 下午4:57:24
	 * @param jpaRepository
	 */
	@Autowired
	public UserLoginInfoServiceImpl(UserLoginInfoDao userLoginInfoDao) {
		// TODO Auto-generated constructor stubs
		super((JpaRepository<UserLoginInfo, Long>)userLoginInfoDao);
		this.userLoginInfoDao = userLoginInfoDao;
	}
	
	/* 
	 * desc:
	 * (non-Javadoc)
	 * @see com.umpay.sys.service.UserLoginInfoService#findByUsername(java.lang.String)
	 */
	public UserLoginInfo findByUsername(String username){
		UserLoginInfo userLoginInfo = userLoginInfoDao.findByUsername(username);
		return userLoginInfo;
	}
	
	
}

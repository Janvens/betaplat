/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jan.betaplat.core.dao.UserDao;
import com.jan.betaplat.core.exception.ExistedException;
import com.jan.betaplat.core.exception.ServiceException;
import com.jan.betaplat.core.po.User;
import com.jan.betaplat.core.service.UserService;
import com.jan.betaplat.core.shiro.ShiroDbRealm;
import com.jan.betaplat.core.shiro.ShiroDbRealm.HashPassword;
import com.jan.betaplat.core.util.PageUtils;
import com.jan.betaplat.core.util.dwz.Page;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Service
@Transactional(readOnly=true)
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDao userDao;
	
	@Autowired
	private ShiroDbRealm shiroRealm;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		super((JpaRepository<User, Long>) userDao);
		this.userDao = userDao;
	}
	
	/**
	 * 
	 * @param user
	 * @throws ExistedException  
	 * @see com.umpay.sys.service.UserService#save(umpay_platCache.User)
	 */
	@Transactional
	public void save(User user) throws ExistedException {		
		if (userDao.findByUsername(user.getUsername()) != null) {
			throw new ExistedException("用户添加失败，登录名：" + user.getUsername() + "已存在。");
		}
		
		if (userDao.findByRealname(user.getRealname()) != null) {
			throw new ExistedException("用户添加失败，真实名：" + user.getRealname() + "已存在。");
		}
		
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = shiroRealm.encrypt(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		
		userDao.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param user  
	 * @see com.umpay.sys.service.UserService#update(umpay_platCache.User)  
	 */
	@Transactional
	public void update(User user) {
		//if (isSupervisor(user.getId())) {
		//	logger.warn("操作员{},尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
		//	throw new ServiceException("不能修改超级管理员用户");
		//}
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = shiroRealm.encrypt(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		
		userDao.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param id  
	 * @see com.umpay.sys.service.UserService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new ServiceException("不能删除超级管理员用户。");
		}
		userDao.delete(id);
	}

	/**   
	 * @param username
	 * @return  
	 * @see com.umpay.sys.service.UserService#get(java.lang.String)  
	 */
	public User get(String username) {
		return userDao.findByUsername(username);
	}

	/**   
	 * @param page
	 * @param name
	 * @return  
	 * @see com.umpay.sys.service.UserService#find(com.umpay.sys.util.dwz.Page, java.lang.String)  
	 */
	public List<User> find(Page page, String name) {
		org.springframework.data.domain.Page<User> pageUser = 
				userDao.findByUsernameContaining(name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, pageUser);
		return pageUser.getContent();
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
}

/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jan.betaplat.core.po.User;

/** 
 * desc:用户DAO
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public interface UserDao extends JpaRepository<User, Long> {
	// 根据登录名查找用户
	User findByUsername(String Username);
	
	// 根据实名查找用户
	User findByRealname(String realname);
	
	// 根据包含登录名(类似like)查找用户
	Page<User> findByUsernameContaining(String Username, Pageable pageable);
}
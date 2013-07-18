/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-6-5下午4:54:42</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jan.betaplat.core.po.UserLoginInfo;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-6-5 </p>
 * @version V1.0  
 */
public interface UserLoginInfoDao extends JpaRepository<UserLoginInfo, Long>{

	UserLoginInfo findByUsername(String username);
	
}

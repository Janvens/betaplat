/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */ 
package com.jan.betaplat.core.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jan.betaplat.core.po.UserRole;


/** 
 * desc:用户角色DAO
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public interface UserRoleDao extends JpaRepository<UserRole, Long> {
	List<UserRole> findByUserId(Long userId);
}

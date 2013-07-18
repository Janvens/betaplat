/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.dao;

import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import com.jan.betaplat.core.po.Group;



/** 
 * desc:组DAO
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public interface OrganizationDao extends JpaRepository<Group, Long>{
	Page<Group> findByParentId(Long parentId, Pageable pageable);
	
	Page<Group> findByParentIdAndNameContaining(Long parentId, String name, Pageable pageable);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="umpay_platCache")
		}
	)
	@Query("from Organization")
	List<Group> findAllWithCache();
}

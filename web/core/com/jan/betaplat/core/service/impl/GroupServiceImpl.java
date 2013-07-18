/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jan.betaplat.core.dao.GroupDao;
import com.jan.betaplat.core.exception.ServiceException;
import com.jan.betaplat.core.po.Group;
import com.jan.betaplat.core.service.GroupService;
import com.jan.betaplat.core.util.PageUtils;
import com.jan.betaplat.core.util.dwz.Page;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Service
@Transactional(readOnly=true)
public class GroupServiceImpl extends BaseServiceImpl<Group, Long>implements GroupService {
	
	private GroupDao groupDao;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
	@Autowired
	public GroupServiceImpl(GroupDao groupDao) {
		super((JpaRepository<Group, Long>) groupDao);
		this.groupDao = groupDao;
	}

	/**   
	 * @param id
	 * @throws ServiceException  
	 * @see com.umpay.sys.service.GroupService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根组织。");
		}
		
		Group group = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(group.getChildren().size() > 0){
			throw new ServiceException(group.getName() + "组织下存在子组织，不允许删除。");
		}
		
		groupDao.delete(id);
	}

	/**   
	 * @param parentId
	 * @param page
	 * @return  
	 * @see com.umpay.sys.service.GroupService#find(java.lang.Long, com.umpay.sys.util.dwz.Page)  
	 */
	public List<Group> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<Group> p = 
				groupDao.findByParentId(parentId, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}

	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see com.umpay.sys.service.GroupService#find(java.lang.Long, java.lang.String, com.umpay.sys.util.dwz.Page)  
	 */
	public List<Group> find(Long parentId, String name, Page page) {
		org.springframework.data.domain.Page<Group> p = 
				groupDao.findByParentIdAndNameContaining(parentId, name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}
	
	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

	/**
	 * 
	 * @return  
	 * @see com.umpay.sys.service.GroupService#getTree()
	 */
	public Group getTree() {
		List<Group> list = groupDao.findAllWithCache();
		
		List<Group> rootList = makeTree(list);
				
		return rootList.get(0);
	}

	private List<Group> makeTree(List<Group> list) {
		List<Group> parent = new ArrayList<Group>();
		// get parentId = null;
		for (Group e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Group>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Group> parent, List<Group> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<Group> tmp = new ArrayList<Group>();
		for (Group c1 : parent) {
			for (Group c2 : children) {
				c2.setChildren(new ArrayList<Group>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}
}

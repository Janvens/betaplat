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
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jan.betaplat.core.common.SecurityConstants;
import com.jan.betaplat.core.dao.ModuleDao;
import com.jan.betaplat.core.exception.ServiceException;
import com.jan.betaplat.core.po.Module;
import com.jan.betaplat.core.service.ModuleService;
import com.jan.betaplat.core.util.PageUtils;
import com.jan.betaplat.core.util.dwz.Page;


/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Service
@Transactional(readOnly=true)
public class ModuleServiceImpl extends BaseServiceImpl<Module, Long>implements ModuleService {
	private ModuleDao moduleDao;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
	@Autowired
	public ModuleServiceImpl(ModuleDao moduleDao) {
		super((JpaRepository<Module, Long>) moduleDao);
		this.moduleDao = moduleDao;
	}
	
	@Transactional
	public void save(Module module){
		Module parent_m = module.getParent();
		this.moduleDao.save(module);
		if(null != parent_m){
			// 	清除父节点的URL
			parent_m.setUrl("#");
		}
		this.update(parent_m);
	}
	
	/**   
	 * @param id
	 * @throws ServiceException  
	 * @see com.umpay.sys.service.ModuleService#delete(int)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根模块。");
		}
		
		Module module = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(module.getChildren().size() > 0){
			throw new ServiceException(module.getName() + "模块下存在子模块，不允许删除。");
		}
		
		moduleDao.delete(module);
	}

	/**   
	 * @param parentId
	 * @param page
	 * @return  
	 * @see com.umpay.sys.service.ModuleService#find(java.lang.Long, com.umpay.sys.util.dwz.Page)  
	 */
	public List<Module> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<Module> p = 
				moduleDao.findByParentId(parentId, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}

	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see com.umpay.sys.service.ModuleService#find(java.lang.Long, java.lang.String, com.umpay.sys.util.dwz.Page)  
	 */
	public List<Module> find(Long parentId, String name, Page page) {
		org.springframework.data.domain.Page<Module> p = 
				moduleDao.findByParentIdAndNameContaining(parentId, name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}

	/**
	 * 判断是否是根模块.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}
	
	
	public Module getMTree(Set<String> permissionSet){
		List<Module> list = moduleDao.findAllWithCache();
		
		List<Module> rootList = makeMTree(list,permissionSet);
		
		return rootList.get(0);
	}
	
	private List<Module> makeMTree(List<Module> list,Set<String> permissionSet) {
		List<Module> parent = new ArrayList<Module>();
		for (Module e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Module>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeMChildren(parent, list,permissionSet);
		
		return parent;
		
	}
	private void makeMChildren(List<Module> parent, List<Module> children,Set<String> permissionSet) {
		if (children.isEmpty()) {
			return ;
		}
		List<Module> tmp = new ArrayList<Module>();
		for (Module c1 : parent) {
			for (Module c2 : children) {
				c2.setChildren(new ArrayList<Module>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					if(permissionSet.contains(c2.getSn() + ":" + SecurityConstants.OPERATION_VIEW)){
						c1.getChildren().add(c2);
					}
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeMChildren(tmp, children,permissionSet);
	}
	
	

	/**
	 * 
	 * @return  
	 * @see com.umpay.sys.service.ModuleService#getTree()
	 */
	public Module getTree() {
		List<Module> list = moduleDao.findAllWithCache();
		
		List<Module> rootList = makeTree(list);
				
		return rootList.get(0);
	}

	private List<Module> makeTree(List<Module> list) {
		List<Module> parent = new ArrayList<Module>();
		// get parentId = null;
		for (Module e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Module>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Module> parent, List<Module> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<Module> tmp = new ArrayList<Module>();
		for (Module c1 : parent) {
			for (Module c2 : children) {
				c2.setChildren(new ArrayList<Module>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}
	
	public static void main(String[] args) {
		Module module = new Module();
		module.setId(1L);
		module.setName("module_1");
		
		Module module2 = new Module();
		module2.setId(2L);
		module2.setName("module_2");
		module2.setParent(module);
		
		Module module3 = new Module();
		module3.setId(3L);
		module3.setName("module_3");
		module3.setParent(module);
		
		Module module4 = new Module();
		module4.setId(4L);
		module4.setName("module_4");
		module4.setParent(module2);
		
		Module module5 = new Module();
		module5.setId(5L);
		module5.setName("module_5");
		module5.setParent(module4);
		
		List<Module> list = new ArrayList<Module>();
		list.add(module2);
		list.add(module3);
		module.setChildren(list);
		
		list = new ArrayList<Module>();
		list.add(module4);
		module2.setChildren(list);
		
		list = new ArrayList<Module>();
		list.add(module5);
		module4.setChildren(list);
		
		System.out.println(module);
		list = module.getChildren();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			List<Module> list2 = list.get(i).getChildren();
			for (int j = 0; j < list2.size(); j++) {
				System.out.println(list2.get(j));
				List<Module> list3 = list2.get(j).getChildren();
				for (int j2 = 0; j2 < list3.size(); j2++) {
					System.out.println(list3.get(j2));
				}
			}
		}
	}
	
}

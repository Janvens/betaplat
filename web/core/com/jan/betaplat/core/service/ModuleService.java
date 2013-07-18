/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.service;

import java.util.List;
import java.util.Set;
import com.jan.betaplat.core.po.Module;
import com.jan.betaplat.core.util.dwz.Page;




/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
public interface ModuleService extends BaseService<Module, Long>{
	List<Module> find(Long parentId, Page page);
	
	List<Module> find(Long parentId, String name, Page page);
	
	Module getTree();
	
	Module getMTree(Set<String> permissionSet);
}

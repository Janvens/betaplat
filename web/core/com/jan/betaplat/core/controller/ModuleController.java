/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jan.betaplat.core.exception.ServiceException;
import com.jan.betaplat.core.po.Module;
import com.jan.betaplat.core.service.ModuleService;
import com.jan.betaplat.core.util.dwz.AjaxObject;
import com.jan.betaplat.core.util.dwz.Page;



/** 
 * desc:功能模块控制器
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Controller
@RequestMapping("/sys/core/module")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;
	
	private static final String CREATE = "sys/core/module/create";
	private static final String UPDATE = "sys/core/module/update";
	private static final String LIST = "sys/core/module/list";
	private static final String TREE = "sys/core/module/tree";
	
	@RequiresPermissions("Module:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:22:36</p>
	 * @param module
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Module:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(Module module, HttpServletRequest request) {
		final Module parent = (Module)request.getSession().getAttribute("parentModule");
		AjaxObject ajaxObject = new AjaxObject();
		if(null!=parent.getParent() 
				&& null!=parent.getParent().getParent() 
				&& !Long.valueOf("1").equals(parent.getParent().getParent().getId())){
			ajaxObject.setMessage("暂不支持三级父目录！");
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		}else{
			module.setParent((Module)request.getSession().getAttribute("parentModule"));
			moduleService.save(module);
			ajaxObject = new AjaxObject("模块添加成功！");
		}
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:22:40</p>
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);
		
		map.put("module", module);
		return UPDATE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:22:43</p>
	 * @param module
	 * @return
	 */
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(Module module) {
		moduleService.update(module);
		
		AjaxObject ajaxObject = new AjaxObject("模块修改成功！");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:22:46</p>
	 * @param id
	 * @return
	 */
	@RequiresPermissions("Module:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			moduleService.delete(id);
			ajaxObject.setMessage("模块删除成功！");
		} catch (ServiceException e) {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("模块删除失败：" + e.getMessage());
		}
		
		ajaxObject.setCallbackType("");
		ajaxObject.setRel("jbsxBox2module");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		Module module = moduleService.getTree();
		
		map.put("module", module);
		return TREE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:22:49</p>
	 * @param page
	 * @param parentId
	 * @param keywords
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/list/{parentId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, @PathVariable Long parentId, String keywords, 
			Map<String, Object> map, HttpServletRequest request) {
		List<Module> modules = null;
		if (StringUtils.isNotBlank(keywords)) {
			modules = moduleService.find(parentId, keywords, page);
		} else {
			modules = moduleService.find(parentId, page);
		}
		
		request.getSession().setAttribute("parentModule", moduleService.get(parentId));
		
		map.put("page", page);
		map.put("modules", modules);
		map.put("keywords", keywords);

		return LIST;
	}
	
}

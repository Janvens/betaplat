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
import com.jan.betaplat.core.po.Group;
import com.jan.betaplat.core.service.GroupService;
import com.jan.betaplat.core.util.dwz.AjaxObject;
import com.jan.betaplat.core.util.dwz.Page;


/** 组权限管理控制器
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Controller
@RequestMapping("/sys/core/group")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	private static final String CREATE = "sys/core/group/create";
	private static final String UPDATE = "sys/core/group/update";
	private static final String LIST = "sys/core/group/list";
	private static final String TREE = "sys/core/group/tree";
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:24:29</p>
	 * @return
	 */
	@RequiresPermissions("Group:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:24:34</p>
	 * @param group
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Group:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(Group group, HttpServletRequest request) {
		group.setParent((Group)request.getSession().getAttribute("parentGroup"));//TODO
		groupService.save(group);
		
		AjaxObject ajaxObject = new AjaxObject("组织添加成功！");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:24:37</p>
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Group:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Group group = groupService.get(id);
		
		map.put("group", group);
		return UPDATE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:24:40</p>
	 * @param group
	 * @return
	 */
	@RequiresPermissions("Group:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(Group group) {
		groupService.update(group);
		
		AjaxObject ajaxObject = new AjaxObject("组织修改成功！");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:24:44</p>
	 * @param id
	 * @return
	 */
	@RequiresPermissions("Group:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			groupService.delete(id);
			ajaxObject.setMessage("组织删除成功！");
		} catch (ServiceException e) {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("组织删除失败：" + e.getMessage());
		}
		
		ajaxObject.setCallbackType("");
		ajaxObject.setRel("jbsxBox2group");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:25:07</p>
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Group:view")
	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		Group group = groupService.getTree();
		
		map.put("group", group);
		return TREE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:25:10</p>
	 * @param page
	 * @param parentId
	 * @param keywords
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions("Group:view")
	@RequestMapping(value="/list/{parentId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, @PathVariable Long parentId, String keywords, 
			Map<String, Object> map, HttpServletRequest request) {
		List<Group> groups = null;
		if (StringUtils.isNotBlank(keywords)) {
			groups = groupService.find(parentId, keywords, page);
		} else {
			groups = groupService.find(parentId, page);
		}
		
		request.getSession().setAttribute("parentGroup", groupService.get(parentId));
		
		map.put("page", page);
		map.put("groups", groups);
		map.put("keywords", keywords);

		return LIST;
	}
	
}

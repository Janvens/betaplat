/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */ 
package com.jan.betaplat.core.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.jan.betaplat.core.exception.ExistedException;
import com.jan.betaplat.core.po.Group;
import com.jan.betaplat.core.po.Role;
import com.jan.betaplat.core.po.User;
import com.jan.betaplat.core.po.UserRole;
import com.jan.betaplat.core.service.GroupService;
import com.jan.betaplat.core.service.RoleService;
import com.jan.betaplat.core.service.UserRoleService;
import com.jan.betaplat.core.service.UserService;
import com.jan.betaplat.core.util.TimeUtils;
import com.jan.betaplat.core.util.dwz.AjaxObject;
import com.jan.betaplat.core.util.dwz.Page;


/** 
 * desc:用户管理控制器
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Controller
@RequestMapping("/sys/core/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private GroupService groupService;
	
	private static final String CREATE = "sys/core/user/create";
	private static final String UPDATE = "sys/core/user/update";
	private static final String LIST = "sys/core/user/list";
	private static final String LOOK_UP_ROLE = "sys/core/user/assign_role";
	private static final String LOOK_USER_ROLE = "sys/core/user/delete_user_role";
	private static final String LOOK_ORG = "sys/core/user/lookup_org";
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:06</p>
	 * @return
	 */
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:10</p>
	 * @param user
	 * @return
	 */
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(User user,String _losedate, String _effdate) {	
		user.setIntime(new Date());
		user.setModtime(new Date());
		user.setEffdate(TimeUtils.formatStringToDate(_effdate, "yyyy-MM-dd"));
		user.setLosedate(TimeUtils.formatStringToDate(_losedate, "yyyy-MM-dd"));
		try {
			userService.save(user);
		} catch (ExistedException e) {
			AjaxObject ajaxObject = new AjaxObject(e.getMessage());
			ajaxObject.setCallbackType("");
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			return ajaxObject.toString();
		}
		
		AjaxObject ajaxObject = new AjaxObject("用户添加成功！");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:14</p>
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadUser")
	public User getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			User user = userService.get(id);
			return user;
		}
		return null;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:17</p>
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		User user = userService.get(id);
		
		map.put("user", user);
		return UPDATE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:20</p>
	 * @param user
	 * @return
	 */
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@ModelAttribute("preloadUser")User user) {
		userService.update(user);
		
		AjaxObject ajaxObject = new AjaxObject("用户修改成功！");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:22</p>
	 * @param id
	 * @return
	 */
	@RequiresPermissions("User:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject("用户删除成功！");
		try {
			userService.delete(id);
		} catch (ExistedException e) {
			ajaxObject.setMessage(e.getMessage());
		}
		
		//AjaxObject ajaxObject = new AjaxObject("用户删除成功");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:26</p>
	 * @param page
	 * @param keywords
	 * @param map
	 * @return
	 */
	@RequiresPermissions("User:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<User> users;
		if (StringUtils.isNotBlank(keywords)) {
			users = userService.find(page, keywords);
		} else {
			users = userService.findAll(page);
		}
		
		map.put("page", page);
		map.put("users", users);
		map.put("keywords", keywords);
		return LIST;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:29</p>
	 * @param type
	 * @param userId
	 * @return
	 */
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/reset/{type}/{userId}", method=RequestMethod.POST)
	public @ResponseBody String reset(@PathVariable String type, @PathVariable Long userId) {
		User user = userService.get(userId);
		AjaxObject ajaxObject = new AjaxObject();
		if (type.equals("password")) {
			user.setPlainPassword("123456");
			
			ajaxObject.setMessage("重置密码成功，默认密码为123456！"); 
		} else if (type.equals("status")) {
			if (user.getStatus().equals(Short.valueOf("1"))) {
				user.setStatus(Short.valueOf("1"));
			} else {
				user.setStatus(Short.valueOf("0"));
			}
			
			ajaxObject.setMessage("更新状态为" + user.getStatus());
		}
		
		userService.update(user);
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:33</p>
	 * @param userRole
	 */
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create/userRole", method={RequestMethod.POST})
	public @ResponseBody void assignRole(UserRole userRole) {
		userRoleService.save(userRole);
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:36</p>
	 * @param map
	 * @param userId
	 * @return
	 */
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/lookup2create/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUnassignRole(Map<String, Object> map, @PathVariable Long userId) {
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		List<UserRole> userRoles = userRoleService.find(userId);
		List<Role> roles = roleService.findAll(page);
		
		List<Role> hasList = Lists.newArrayList();
		List<Role> allRoles = Lists.newArrayList(roles);
		// 删除已分配roles
		for (UserRole ur : userRoles) {
			for (Role role : roles) {
				if (ur.getRole().getId().equals(role.getId())) {
					hasList.add(role);
					break;
				}
			}
		}
		
		allRoles.removeAll(hasList);
		
		map.put("userRoles", userRoles);
		map.put("roles", allRoles);
		
		map.put("userId", userId);
		return LOOK_UP_ROLE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:39</p>
	 * @param map
	 * @param userId
	 * @return
	 */
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/lookup2delete/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
		List<UserRole> userRoles = userRoleService.find(userId);
		map.put("userRoles", userRoles);
		return LOOK_USER_ROLE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:42</p>
	 * @param userRoleId
	 */
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/delete/userRole/{userRoleId}", method={RequestMethod.POST})
	public @ResponseBody void deleteUserRole(@PathVariable Long userRoleId) {
		userRoleService.delete(userRoleId);
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:27:45</p>
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value={"User:edit", "User:save"})
	@RequestMapping(value="/lookup2org", method={RequestMethod.GET})
	public String lookup(Map<String, Object> map) {
		Group org = groupService.getTree();
		
		map.put("org", org);
		return LOOK_ORG;
	}
}

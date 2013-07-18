/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.controller;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jan.betaplat.core.common.SecurityConstants;
import com.jan.betaplat.core.po.Module;
import com.jan.betaplat.core.po.User;
import com.jan.betaplat.core.po.UserRole;
import com.jan.betaplat.core.service.ModuleService;
import com.jan.betaplat.core.service.UserLoginInfoService;
import com.jan.betaplat.core.service.UserRoleService;
import com.jan.betaplat.core.service.UserService;
import com.jan.betaplat.core.shiro.ShiroDbRealm;
import com.jan.betaplat.core.util.dwz.AjaxObject;


/** 
 * desc:主登陆页面控制器
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Controller
@RequestMapping("/sys/index")
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserLoginInfoService userLoginInfoService;
	
	@Autowired
	private ModuleService moduleService;
	
	private static final String INDEX = "sys/index/index";
	private static final String UPDATE_PASSWORD = "sys/index/updatePwd";
	private static final String UPDATE_BASE = "sys/index/updateBase";
	
	@RequiresAuthentication 
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();
		
		List<UserRole> userRoles = userRoleService.find(shiroUser.getId());
		shiroUser.getUser().setUserRoles(userRoles);
		
		Set<String> permissionSet = Sets.newHashSet();
		for (UserRole userRole : userRoles) {
			Set<String> tmp = Sets.newHashSet(userRole.getRole().getPermissionList());
			permissionSet.addAll(tmp);
		}
		Module menuModule = moduleService.getMTree(permissionSet);
		request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		request.getSession().setAttribute(SecurityConstants.LOGIN_INFO, shiroUser.getUserLoginInfo());
		String basePath = request.getContextPath();
		request.setAttribute("janTree", janTree(menuModule.getChildren(),basePath));
		return INDEX;
	}
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-9 下午03:26:57</p>
	 * @return
	 */
	private String janTree(List<Module> list,String basePath) {
		StringBuilder sb = new StringBuilder();
		for(Module m : list){
			sb.append("<div class=\"accordionHeader\">").append("\n");
			sb.append("<h2><span>Folder</span>").append(m.getName()).append("</h2>").append("\n");
			sb.append("</div>").append("\n");
			sb.append("<div class=\"accordionContent\">").append("\n");
			if(null!=m.getChildren()){
				janMTree(m.getChildren(),sb,basePath);
			}
			sb.append("</div>").append("\n");
		}
		return sb.toString();
	}
	
	private void janMTree(List<Module> list,StringBuilder sb,String basePath){
		if(list.isEmpty())return;
		sb.append("<ul class=\"tree treeFolder\">").append("\n");
		for(Module m : list){
			sb.append("<li>");
			if(!m.getChildren().isEmpty()){
				sb.append("<a>").append(m.getName()).append("</a>");
			}else{
				sb.append("<a href=\"").append(basePath).append(m.getUrl()).append("\" target=\"navTab\" rel=\"moduleListNav_").append(m.getId()).append("\">").append(m.getName()).append("</a>");
			}
			janMTree(m.getChildren(), sb,basePath);
			sb.append("</li>").append("\n");
		}
		sb.append("</ul>").append("\n");
	}

	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:21:24</p>
	 * @param userRoles
	 * @return
	 */
	private Module getMenuModule(List<UserRole> userRoles) {
		// 得到所有权限
		Set<String> permissionSet = Sets.newHashSet();
		for (UserRole userRole : userRoles) {
			Set<String> tmp = Sets.newHashSet(userRole.getRole().getPermissionList());
			permissionSet.addAll(tmp);
		}
		
		// 组装菜单,只获取二级菜单
		//Module rootModule = moduleService.get(1L);
		Module rootModule = moduleService.getTree();
		List<Module> list1 = Lists.newArrayList();
		for (Module m1 : rootModule.getChildren()) {
			// 只加入拥有view权限的Module
			if (permissionSet.contains(m1.getSn() + ":" + SecurityConstants.OPERATION_VIEW)) {
				List<Module> list2 = Lists.newArrayList();
				for (Module m2 : m1.getChildren()) {
					if (permissionSet.contains(m2.getSn() + ":" + SecurityConstants.OPERATION_VIEW)) {
						list2.add(m2);
					}
				}
				m1.setChildren(list2);
				list1.add(m1);
			}
		}
		rootModule.setChildren(list1);
		
		return rootModule;
	}
	
	@RequestMapping(value="/updatePwd", method=RequestMethod.GET)
	public String updatePassword() {
		return UPDATE_PASSWORD;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:21:28</p>
	 * @param request
	 * @param oldPassword
	 * @param plainPassword
	 * @param rPassword
	 * @return
	 */
	@RequestMapping(value="/updatePwd", method=RequestMethod.POST)
	public @ResponseBody String updatePassword(HttpServletRequest request, String oldPassword, 
			String plainPassword, String rPassword) {
		User user = (User)request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
		
		if (plainPassword.equals(rPassword)) {
			user.setPlainPassword(plainPassword);
			userService.update(user);
			
			AjaxObject ajaxObject = new AjaxObject("密码修改成功！");
			return ajaxObject.toString();
		}
		
		AjaxObject ajaxObject = new AjaxObject("密码修改失败！");
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@RequestMapping(value="/updateBase", method=RequestMethod.GET)
	public String preUpdate() {
		return UPDATE_BASE;
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:21:32</p>
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateBase", method=RequestMethod.POST)
	public @ResponseBody String update(User user, HttpServletRequest request) {
		User loginUser = (User)request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
		
		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());

		userService.update(loginUser);

		AjaxObject ajaxObject = new AjaxObject("详细信息修改成功！");
		return ajaxObject.toString();
	}
	

}

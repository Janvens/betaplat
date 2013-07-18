/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>
 * @author Zhang Wensheng
 * @version 1.0
 */
 
package com.jan.betaplat.core.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.jan.betaplat.core.po.Module;
import com.jan.betaplat.core.po.Role;
import com.jan.betaplat.core.service.ModuleService;
import com.jan.betaplat.core.service.RoleService;
import com.jan.betaplat.core.util.dwz.AjaxObject;
import com.jan.betaplat.core.util.dwz.Page;


/** 
 * desc:角色管理控制器
 * <p>创建人：Zhang Wensheng 创建日期：2013-1-14 </p>
 * @version V1.0  
 */
@Controller
@RequestMapping("/sys/core/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModuleService moduleService;
	
	private static final String CREATE = "sys/core/role/create";
	private static final String UPDATE = "sys/core/role/update";
	private static final String LIST = "sys/core/role/list";
	
	private int ind = 0;
	private int level = 0;
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:25:54</p>
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Role:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		map.put("module", moduleService.getTree());
		return CREATE;
	}
	
	// 重新组装PermissionList（切分test:save,test:edit的形式）
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:25:57</p>
	 * @param role
	 */
	private void refactor(Role role) {
		List<String> allList = Lists.newArrayList();
		List<String> list = role.getPermissionList();
		for (String string : list) {
			if (StringUtils.isBlank(string)) {
				continue;
			}
			
			if (string.contains(",")) {
				String[] arr = string.split(",");
				allList.addAll(Arrays.asList(arr));
			} else {
				allList.add(string);
			}
		}
		role.setPermissionList(allList);
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:26:01</p>
	 * @param role
	 * @return
	 */
	@RequiresPermissions("Role:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(Role role) {
		refactor(role);
		roleService.save(role);
		
		AjaxObject ajaxObject = new AjaxObject("角色添加成功！");
		return ajaxObject.toString();
	}
	
//	@RequiresPermissions("Role:edit")
//	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
//	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
//		Role role = roleService.get(id);
//		
//		map.put("module", moduleService.getTree());
//		map.put("role", role);
//		return UPDATE;
//	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:26:04</p>
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Role:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Role role = roleService.get(id);
		
		List<String> permissionList = role.getPermissionList();
		Module module = moduleService.getTree();
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>").append("\n");
		level = 0;//重设全局变量
		getUpdateTree(module.getChildren(), sb, permissionList);
		sb.append("</ul>").append("\n");
		System.out.println("---------------------");
		System.out.println(sb.toString());
		
		map.put("roleName", role.getName());
		map.put("roleId", role.getId());
		map.put("moduleName", module.getName());
		map.put("_html", sb.toString());
		
		return UPDATE;
	}
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:26:09</p>
	 * @param list
	 * @param sb
	 * @param permissionList
	 */
	private void getUpdateTree(List<Module> list,StringBuilder sb,List<String> permissionList){
		level++;
		Module m = null;
		for(int i=0;i<list.size();i++){
			m = list.get(i);
			if(i==list.size()-1){
				sb.append("<li class=\"last\">").append("\n");
			}else{
				sb.append("<li>").append("\n");
			}
			sb.append("<div class=\"\"><div class=\"indent\"></div>");
			for(int l=1;l<level;l++){
				sb.append("<div class=\"line\"></div>");
			}
			if(!m.getChildren().isEmpty()){
				sb.append("<div class=\"collapsable\"></div>").append("\n");
			}else{
				sb.append("<div class=\"node\"></div>").append("\n");
			}
			apen(sb, permissionList, m);
			sb.append("</div>").append("\n");
			if (!m.getChildren().isEmpty()) {
				sb.append("<ul>").append("\n");
				getUpdateTree(m.getChildren(), sb, permissionList);
				sb.append("</ul>").append("\n");
			}
			sb.append("</li>").append("\n");
		}
	}
	
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:26:17</p>
	 * @param sb
	 * @param permissionList
	 * @param m
	 */
	private void apen(StringBuilder sb,List<String> permissionList,Module m){
		ind++;
		sb.append("<a href=\"#\" class=\"permissionList\">").append("\n");
		sb.append("<span class=\"module_name\">").append(m.getName()).append("</span>").append("\n");
		sb.append("<span class=\"inputValue\">").append("\n");
		
		sb.append("	<input type=\"checkbox\" name=\"permissionList[")
		.append(ind).append("]\" value=\"")
		.append(m.getSn())
		.append(":view\"").append(permissionList.contains(m.getSn() + ":view")?"checked=\"checked\"":"").append("/>").append("\n");
		sb.append("	<input type=\"checkbox\" name=\"permissionList[")
		.append(ind).append("]\" value=\"")
		.append(m.getSn())
		.append(":save\"").append(permissionList.contains(m.getSn() + ":save")?"checked=\"checked\"":"").append("/>").append("\n");
		sb.append("	<input type=\"checkbox\" name=\"permissionList[")
		.append(ind).append("]\" value=\"")
		.append(m.getSn())
		.append(":edit\"").append(permissionList.contains(m.getSn() + ":edit")?"checked=\"checked\"":"").append("/>").append("\n");
		sb.append("	<input type=\"checkbox\" name=\"permissionList[")
		.append(ind).append("]\" value=\"")
		.append(m.getSn())
		.append(":delete\"").append(permissionList.contains(m.getSn() + ":delete")?"checked=\"checked\"":"").append("/>").append("\n");
		sb.append("	<input type=\"checkbox\" class=\"checkboxCtrl\" group=\"permissionList[" + ind + "]\"")
		.append(permissionList.contains(m.getSn() + ":view")
				&&permissionList.contains(m.getSn() + ":save")
				&&permissionList.contains(m.getSn() + ":edit")
				&&permissionList.contains(m.getSn() + ":delete")?"checked=\"checked\"":"").append("/>").append("\n");
		
		sb.append("</span>").append("\n");
		sb.append("</a>").append("\n");
	}
	
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:26:21</p>
	 * @param role
	 * @return
	 */
	@RequiresPermissions("Role:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(Role role) {
		refactor(role);
		roleService.update(role);

		AjaxObject ajaxObject = new AjaxObject("角色修改成功！");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:26:26</p>
	 * @param id
	 * @return
	 */
	@RequiresPermissions("Role:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		
		roleService.delete(id);

		AjaxObject ajaxObject = new AjaxObject("角色删除成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	/**
	 * desc:
	 * <p>创建人：Zhang Wensheng , 2013-1-14 下午04:26:29</p>
	 * @param page
	 * @param keywords
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Role:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<Role> roles = null;
		if (StringUtils.isNotBlank(keywords)) {
			roles = roleService.find(page, keywords);
		} else {
			roles = roleService.findAll(page);
		}

		map.put("page", page);
		map.put("roles", roles);
		map.put("keywords", keywords);
		return LIST;
	}

}

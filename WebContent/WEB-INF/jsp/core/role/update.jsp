<%@page import="com.umpay.sys.po.Role"%>
<%@page import="com.umpay.sys.po.Module"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>

<h2 class="contentTitle">修改角色</h2>
<form method="post" action="<%=basePath %>/sys/core/role/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${roleId}">
	<div class="pageFormContent" layoutH="97">
	<dl>
		<dt>名称：</dt>
		<dd>
			<input type="text" name="name" class="required" size="32" maxlength="32" alt="请输入角色名称" value="${roleName }"/>
		</dd>
	</dl>
	
	<div class="divider"></div>
	
	<ul class="treeCustom">
		<li>
			<div class=""><div class="first_collapsable"></div>
			<a href="#" class="permissionList">
				<span class="module_name">${moduleName }</span>
				<span style="float: right;">
				<span class="crud">读取</span>
				<span class="crud">创建</span>
				<span class="crud">修改</span>
				<span class="crud">删除</span>
				<span class="crud">全部</span>
				</span>
			</a>
			</div>
			<%
				out.println((String)request.getAttribute("_html"));
			%>
		</li>
	</ul>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>
<form id="pagerForm" method="post" action="<%=basePath %>/sys/core/user/list">
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" /> 
	<input type="hidden" name="orderField" value="${page.orderField}" />
	<input type="hidden" name="orderDirection" value="${page.orderDirection}" />
	 
	<input type="hidden" name="keywords" value="${keywords}"/>
</form>

<form method="post" action="<%=basePath %>/sys/core/user/list" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>用户名：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="User:save">
				<li><a class="add" target="dialog" rel="lookup2froup" mask="true" width="530" height="380" href="<%=basePath %>/sys/core/user/create" ><span>添加</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:edit">
				<li><a class="edit" target="dialog" rel="lookup2froup" mask="true" width="530" height="380" href="<%=basePath %>/sys/core/user/update/{slt_uid}" ><span>编辑</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:delete">
				<li><a class="delete" target="ajaxTodo" href="<%=basePath %>/sys/core/user/delete/{slt_uid}" title="确认要删除该用户?"><span>删除</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="User:edit">
				<li class="line">line</li>
				<li><a class="edit" target="ajaxTodo" href="<%=basePath %>/sys/core/user/reset/password/{slt_uid}" title="确认重置密码为123456?"><span>重置密码</span></a></li>
				<li><a class="edit" target="ajaxTodo" href="<%=basePath %>/sys/core/user/reset/status/{slt_uid}" title="确认更新状态"><span>更新账户状态</span></a></li>
				<li class="line">line</li>
				<li><a class="add" href="<%=basePath %>/sys/core/user/lookup2create/userRole/{slt_uid}" target="dialog" mask="true" width="400" height="500" title="分配角色"><span>分配角色</span></a></li>
				<li><a class="delete" href="<%=basePath %>/sys/core/user/lookup2delete/userRole/{slt_uid}" target="dialog" mask="true" width="400" height="500" title="删除已分配角色"><span>删除已分配角色</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="100">用户名称</th>
				<th width="100">真实名字</th>
				<th width="200">邮箱地址</th>
				<th width="100">电话</th>
				<th width="50" >性别</th>
				<th width="80" >生效日期</th>
				<th width="80" >失效日期</th>
				<th width="60" >证件类别</th>
				<th width="150" >证件编号</th>
				<th width="60" orderField="status" class="${page.orderField eq 'status' ? page.orderDirection : ''}">账户状态</th>
				<th orderField="intime" class="${page.orderField eq 'intime' ? page.orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.username}</td>
				<td>${item.realname}</td>
				<td>${item.email}</td>
				<td>${item.phone}</td>
				<td>${item.sex == 0 ? "男":"女"}</td>
				<td><fmt:formatDate value="${item.effdate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.losedate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.cardtype}</td>
				<td>${item.cardno}</td>
				<td>${item.status == 0 ? "可用":"不可用"}</td>
				<td><fmt:formatDate value="${item.intime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<c:import url="/WEB-INF/jsp/sys/_frag/pager/panelBar.jsp"></c:import>
	
</div>

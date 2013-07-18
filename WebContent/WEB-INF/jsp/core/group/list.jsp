<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>
<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox2group');" action="<%=basePath %>/sys/core/group/list/${parentgroup.id}" method="post">
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" /> 
	<input type="hidden" name="orderField" value="${page.orderField}" />
	<input type="hidden" name="orderDirection" value="${page.orderDirection}" />
	 
	<input type="hidden" name="keywords" value="${keywords}"/>
</form>

<form method="post" action="<%=basePath %>/sys/core/group/list/${parentgroup.id}" onsubmit="return divSearch(this, 'jbsxBox2group');">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>组织名称：</label>
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
		<shiro:hasPermission name="Group:save">
			<li><a class="add" target="dialog" width="550" height="350" mask="true" href="<%=basePath %>/sys/core/group/create" ><span>添加</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Group:edit">
			<li><a class="edit" target="dialog" width="550" height="350" mask="true" href="<%=basePath %>/sys/core/group/update/{slt_uid}" ><span>编辑</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Group:delete">
			<li><a class="delete" target="ajaxTodo" href="<%=basePath %>/sys/core/group/delete/{slt_uid}" title="确认要删除该组织?"><span>删除</span></a></li>
		</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" layoutH="138" width="100%" rel="jbsxBox2group" >
		<thead>
			<tr>
				<th width="150" >名称</th>
				<th width="150" >父组织</th>
				<th >描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${groups}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.name}</td>
				<td>${item.name}</td>
				<td>${item.description}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>
			<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value}, 'jbsxBox2group')">
				<c:forEach begin="10" end="50" step="10" varStatus="s">
					<option value="${s.index}" ${page.numPerPage eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
				</c:forEach>
			</select>
			<span>总条数: ${page.totalCount}</span>
		</div>
	
		<div class="pagination" rel="jbsxBox2group" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="10" currentPage="${page.pageNum}"></div>
	</div>
	
</div>

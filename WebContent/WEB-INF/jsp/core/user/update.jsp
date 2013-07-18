<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>

<h2 class="contentTitle">修改用户</h2>
<form method="post" action="<%=basePath %>/sys/core/user/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${user.id}"/>
	<div class="pageFormContent" layoutH="97">
		<p>
			<label>登录名称:</label>
			<input type="text" name="username" class="required" size="20" maxlength="32" readonly="readonly" value="${user.username }"/>
		</p>
		<p>
			<label>真实名字:</label>
			<input type="text" name="realname" class="required" size="20" maxlength="32" readonly="readonly" value="${user.realname }"/>
		</p>		
		<p>
			<label>电话:</label>
			<input type="text" name="phone" class="phone" size="20" maxlength="64" value="${user.phone }"/>
		</p>
		<p>
			<label>用户邮箱:</label>
			<input type="text" name="email" class="email" size="20" maxlength="128" value="${user.email }"/>
		</p>		
		<p>
			<label>用户状态:</label>
			<select name="status">
				<option value="0" ${user.status == 0 ? 'selected="selected"' : ''}>可用</option>
				<option value="1" ${user.status == 1 ? 'selected="selected"' : ''}>不可用</option>
			</select>
		</p>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
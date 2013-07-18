<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>
<h2 class="contentTitle">添加用户</h2>
<form method="post" action="<%=basePath %>/sys/core/user/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="97">
		<p>
			<label>登录名称:</label>
			<input type="text" name="username" class="required" size="20" maxlength="32"/>
		</p>
		<p>
			<label>真实名字:</label>
			<input type="text" name="realname" class="required" size="20" maxlength="32"/>
		</p>		
		<p>
			<label>性别:</label>
			<select name="sex" id="sex">
				<option value="0" selected="selected">男</option>
				<option value="1" >女</option>
			</select>
		</p>		
		<p>
			<label>登录密码:</label>
			<input type="text" name="plainPassword" class="required" size="20" minlength="6" maxlength="20" value="123456" alt="字母、数字、下划线 6-20位"/>
			<span class="info">&nbsp;&nbsp;默认:123456</span>
		</p>
		<p>
			<label>电话:</label>
			<input type="text" name="phone" class="phone" size="20" maxlength="64"/>
		</p>		
		<p>
			<label>用户邮箱:</label>
			<input type="text" name="email" class="email" size="20" maxlength="128"/>
		</p>
		<p>
			<label>证件类型：</label>
			<select name="cardtype" id="cardtype">
				<option value="0">身份证</option>
				<option value="1">工号</option>
			</select>
		</p>
		<p>
			<label>证件编号：</label>
			<input type="text" name="cardno" class="digits" size="20" maxlength="20" />
		</p>
		<p>
			<label>生效日期：</label>
			<input type="text" name="_effdate" class="date" size="20" maxlength="20" />
		</p>
		<p>
			<label>失效日期：</label>
			<input type="text" name="_losedate" class="date" size="20" maxlength="20" />
		</p>
		<p>
			<label>用户状态:</label>
			<select name="status">
				<option value="0">可用</option>
				<option value="1">不可用</option>
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
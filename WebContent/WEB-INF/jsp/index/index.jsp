<%@ page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>综合支付管理平台</title>
<link href="<%=basePath %>/styles/management/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=basePath %>/styles/management/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=basePath %>/styles/management/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=basePath %>/styles/management/themes/css/custom.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=basePath %>/styles/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>

<!--[if IE]>
<link href="<%=basePath %>/styles/management/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="<%=basePath %>/styles/management/js/speedup.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<%--
<script src="<%=basePath %>/styles/management/js/jquery.cookie.js" type="text/javascript"></script>
--%>
<script src="<%=basePath %>/styles/management/js/jquery.validate.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/jquery.bgiframe.js" type="text/javascript"></script>
<%--
<script src="<%=basePath %>/styles/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/uploadify/scripts/swfobject.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/uploadify/scripts/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>
--%>

<script src="<%=basePath %>/styles/management/js/dwz.core.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.util.date.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.drag.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.tree.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.accordion.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.ui.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.theme.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.navTab.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.tab.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.resize.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.dialog.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.stable.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.ajax.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.pagination.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.database.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.effects.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.panel.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.history.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.combox.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/management/js/dwz.print.js" type="text/javascript"></script>

<%--
<script src="<%=basePath %>/styles/management/js/dwz.min.js" type="text/javascript"></script>
--%>
<script src="<%=basePath %>/styles/management/js/dwz.regional.zh.js" type="text/javascript"></script>

<!-- 自定义JS -->
<script src="<%=basePath %>/styles/management/js/customer.js" type="text/javascript"></script>

<!-- jqueryform -->
<script src="<%=basePath %>/styles/jqueryform/2.8/jquery.form.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
$(function(){	
	DWZ.init("<%=basePath %>/styles/management/dwz.frag.xml", {
		loginUrl:"<%=basePath %>/login/timeout", loginTitle:"登录",	// 弹出登录对话框
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"<%=basePath %>/styles/management/themes"});
		}
	});
});
</script>
</head>
<body scroll="no">
<div id="layout">
	<div id="header">
		<div class="headerNav">
			<a class="logo" href="<%=basePath %>/sys/index">Logo</a>
			<ul class="nav">
				<li><a href="<%=basePath %>/sys/index">主页</a></li>
				<li><a href="<%=basePath %>/sys/index/updateBase" target="dialog" mask="true">修改用户信息</a></li>
				<li><a href="<%=basePath %>/sys/index/updatePwd" target="dialog" mask="true" width="550" height="200">修改密码</a></li>
				<li><a href="<%=basePath %>/logout">退出</a></li>
			</ul>
		</div>
	</div>
	<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse"><div></div></div>
			</div>
		</div>
		<div id="sidebar">
			<div class="toggleCollapse"><h2>菜单</h2><div>collapse</div></div>
			<div class="accordion" fillSpace="sideBar">
			 <% out.print(request.getAttribute("janTree")); %>
			</div>				
		</div>
	</div>
	<div id="container">
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">主页</span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:void(0)">主页</a></li>
			</ul>
			<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page unitBox">
					<div class="accountInfo">
						<div class="right">
							<p><fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd EEEE"/></p>
						</div>
						<p><span>欢迎, ${user.username } .</span></p>
						<p>
							<span style="font-weight: normal;">您最后一次</span>
							<span style="color: red;">${logininfo.lastloginstate == 0 ? '成功' : '失败'}</span>
							<span style="font-weight: normal;">登录是在:</span>
							<span style="color: red;"><fmt:formatDate value="${logininfo.lastlogintime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span style="font-weight: normal;">所在的IP:</span><span style="color: red;">${logininfo.lastip}--  ${logininfo.lastloginarea}</span> 
						</p>
					</div>
					<div class="pageFormContent" layoutH="80">
						<fieldset>
							<legend>基本信息</legend>
							<dl>
								<dt>登录名称：</dt>
								<dd><span class="unit">${user.username }</span></dd>
							</dl>
							<dl>
								<dt>真实名字：</dt>
								<dd><span class="unit">${user.realname }</span></dd>
							</dl>							
							<dl>
								<dt>电话：</dt>
								<dd><span class="unit">${user.phone }</span></dd>
							</dl>							
							<dl>
								<dt>E-Mail：</dt>
								<dd><span class="unit">${user.email }</span></dd>
							</dl>
							<dl>
								<dt>创建时间：</dt>
								<dd><span class="unit"><fmt:formatDate value="${user.intime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></dd>
							</dl>
							<dl>
								<dt>可用状态：</dt>
								<dd><span class="unit">${user.status == 0 ? "可用":"不可用"}</span></dd>
							</dl>
							<dl>
								<dt>生效时间：</dt>
								<dd><span class="unit"><fmt:formatDate value="${user.effdate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></dd>
							</dl>
							<dl>
								<dt>失效时间：</dt>
								<dd><span class="unit"><fmt:formatDate value="${user.losedate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></dd>
							</dl>
						</fieldset>
					</div><!-- end div class="pageFormContent" -->
				</div>
			</div>
		</div>
	</div>
</div>

<div id="footer">Copyright 2003-2013 联动优势科技有限公司版权所有 </div>
</body>
</html>
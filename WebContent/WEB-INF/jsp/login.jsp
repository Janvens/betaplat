<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合支付管理平台</title>
<link href="<%=basePath %>/styles/management/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/styles/management/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/styles/management/themes/css/login.css" rel="stylesheet" type="text/css" />

<!-- form验证 -->
<link rel="stylesheet" href="<%=basePath %>/styles/formValidator.2.2.1/css/validationEngine.jquery.css" type="text/css"/>
<script src="<%=basePath %>/styles/formValidator.2.2.1/js/jquery-1.6.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/formValidator.2.2.1/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath %>/styles/formValidator.2.2.1/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath %>/styles/formValidator.2.2.1/js/sha1.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function(){
        jQuery("#formID").validationEngine();
    });
    jQuery(document).ready(function(){
    	$("#captcha").click(function(){
    		$(this).attr("src", "<%=basePath %>/Captcha.jpg?time"+new Date().getMilliseconds());
    		return false;
    	});
    });

	jQuery(document).ready(function() {
		$("#submit").click(function() {
			var pwdplain = $("#password").val();
			if (pwdplain != "") {
				var pwdsha1 = $.encoding.digests.hexSha1Str(pwdplain);
				$("#password").val(pwdsha1);

			}
		});
	});
</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<img src="<%=basePath %>/styles/management/themes/default/images/logo_1.gif" />
			</h1>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form method="post" action="<%=basePath %>/login" id="formID" >
					<p>
						<label>用户名:</label>
						<input type="text" name="username" style="width: 150px;" class="validate[required] login_input" id="username" value="${username }"/>
					</p>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
						<input type="password" name="password" style="width: 150px;" class="validate[required] login_input" id="password"/>
					</p>
					
					<p>
						<label>验证码:</label>
						<input type="text" id="captcha_key" name="captcha_key" class="code validate[required,maxSize[6]]" size="6" />
						<span><img src="<%=basePath %>/Captcha.jpg" alt="点击刷新验证码" width="75" height="24" id="captcha"/></span>
					</p>
					<p>
						<label>&nbsp;</label>
						<input type="checkbox" name="rememberMe" id="rememberMe" />记住我
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=""/>
					</div>
					<div class="fg_pwd">
					</div>
				</form>
				<c:if test="${msg != null }">
					<p style="color: red; margin-left: 12px;">${msg }</p>
				</c:if>
			</div>
			<div class="login_banner">
				<span class="login_banner_cen"><img src="<%=basePath %>/styles/management/themes/default/images/logo2.gif" /></span>
				<span class="login_banner_tel"> &nbsp;</span>
			</div>
			<div class="login_main"> </div>
		</div>
		<div id="login_footer">
			Copyright 2003-2013 联动优势科技有限公司版权所有 
		</div>
	</div>
</body>
</html>

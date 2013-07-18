<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全管理平台</title>
<link href="<%=basePath %>/styles/management/themes/css/lghtml5.css" rel="stylesheet" type="text/css" />
<!-- Html5 登录页面 -->
<!-- form验证 -->
<link rel="stylesheet" href="<%=basePath %>/styles/formValidator.2.2.1/css/validationEngine.jquery.css" type="text/css"/>
<script src="<%=basePath %>/styles/formValidator.2.2.1/js/jquery-1.6.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/styles/formValidator.2.2.1/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath %>/styles/formValidator.2.2.1/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function(){
        // binds form submission and fields to the validation engine
        jQuery("#formID").validationEngine();
    });
    jQuery(document).ready(function(){
    	$("#captcha").click(function(){
    		$(this).attr("src", "<%=basePath %>/Captcha.jpg");
    		return false;
    	});
    });
    jQuery(document).ready(function(){
    	$("#captcha").click(function(){
    		$(this).attr("src", "<%=basePath %>/Captcha.jpg");
    		return false;
    	});
    });
</script>
</head>

<body>
	<div class="wrap">
  <form action="#" method="post">
    <section class="loginForm">
      <header>
        <h1>支付管理平台</h1>
      </header>
      <div class="loginForm_content">
      <form method="post" action="<%=basePath %>/login" id="formID" >
        <fieldset>
          <div class="inputWrap">
            <input type="text" name="userName" placeholder="请输入用户名" autofocus required>
          </div>
          <div class="inputWrap">
            <input type="password" name="password" placeholder="请输入密码" required>
          </div>
          <div class="inputImgWrap">
          	<input type="text" id="captcha_key" name="captcha_key" placeholder="请输入验证码" size="6" required>
          	<span style="float: right;position: relative;top: -24px;right: -100px;"><img src="<%=basePath %>/Captcha.jpg" alt="点击刷新验证码" width="85" height="24" id="captcha"></span>
          </div>
        </fieldset>
        </form>
        <fieldset style="text-align: center;">
          <input type="submit" value="登录">
          <a href="#">忘记密码？</a> 
        </fieldset>
      </div>
    </section>
  </form>
</div>
</body>
</html>

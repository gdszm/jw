<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>内蒙古美术职业学院教务管理系统</title>
</head>
<style type="text/css">
body {
	background:url(style/images/bg.jpg) no-repeat top center;
	color:#1565ac;
	font-size:14px;
}
* {
	margin:0;
	padding:0;
}
a {
	text-decoration:none;
}
.h {
	height:550px;
	width:668px;
	margin:0 auto;
}
#login {
	width:668px;
	margin:0 auto;
}
.fl {
	float:left;
}
.login_box {
	margin-left:30px;
	line-height:40px;
}
.login_box .input {
	border:1px solid #a6a6a6;
	width:142px;
	padding:4px;
	color:#333;
}
.login_btn {
	background:url(style/images/btn_bg.png) no-repeat;
	height:33px;
	width:68px;
	line-height:33px;
	text-align:center;
	margin-right:8px;
	color:#FFF;
	border: 0 none;
	cursor: pointer;
	font-size: 14px;
	padding: 0;
}
#foot {
	line-height: 30px;
	text-align: center;
	font-size:12px;
}
.em{
	height:12px;
	line-height:12px;
	margin-left:40px;
	font-size:12px;
	color:#FF0000;
	text-align:left;

}
</style>
  <body>
  <div class="h"></div>
  <form id="loginFormId" name="form" action="user!doNotNeedSession_login.do" method="post" >
  	<div id="login">
  	<div style="margin-left:30px; color: red" >${msg}</div>
	  <div class="login_box">
	    <div>用&nbsp;&nbsp;户：
	      <input type="text" class="input"  id="username" name="cname">
	    </div>
	    <div>密&nbsp;&nbsp;码：
	      <input type="password" class="input" id="password" name="cpwd">
	    </div>
	    <div id="em" class="em"><s:property value="msg"/></div>
	    <div style="margin-left: 42px; margin-top:10px;">
	      <input type="submit" class="login_btn" value="登录" id="">
	      <input type="reset" class="login_btn" value="重置" id="">
	    </div>
	  </div>
	</div>
  </form>
   <div style="clear:both;height:150px;"></div>
   <div id="foot">版权所有&nbsp;@<a href="http://www.nmtlzn.com">内蒙古美术职业学院网络部</a></div>
  </body>
</html>

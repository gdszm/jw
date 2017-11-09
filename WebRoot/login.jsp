<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="uimaker/pages/css/base.css" rel="stylesheet">
<link href="uimaker/pages/css/login/login.css" rel="stylesheet">
<title>内蒙古美术职业学院教务管理系统</title>
</head>
  <body class="white">
	<div class="login-hd">
		<div class="left-bg"></div>
		<div class="right-bg"></div>
		<div class="hd-inner">
			<span class="logo"></span>
			<span class="split"></span>
			<span class="sys-name">教务管理系统</span>
		</div>
	</div>
	<div class="login-bd">
		<div class="bd-inner">
			<div class="inner-wrap">
				<div class="lg-zone">
					<div class="lg-box">
						<div class="lg-label"><h4>用户登录</h4></div>
						<c:if test="${!empty msg}">
							<div class="alert alert-error">
	 			              <i class="iconfont">&#xe62e;</i> 
	 			              <span>${msg}</span> 
	 			            </div> 
						</c:if>
<!-- 						<div class="alert alert-error"> -->
<!-- 			              <i class="iconfont">&#xe62e;</i> -->
<!-- 			              <span>请输入用户名</span> -->
<!-- 			            </div> -->
						 <form id="loginFormId"  name="form" action="user!doNotNeedSession_login.do" method="post" >
							<div class="lg-username input-item clearfix">
								<i class="iconfont">&#xe60d;</i>
								<input type="text" id="username" name="cname" placeholder="账号" value="${user.cname}"/>
							</div>
							<div class="lg-password input-item clearfix">
								<i class="iconfont">&#xe634;</i>
								<input type="password" id="password" name="cpwd" placeholder="请输入密码">
							</div>
							<div class="lg-check clearfix">
								<div class="input-item">
									<i class="iconfont">&#xe633;</i>
									<input id="AntRegUserCode" type="text" placeholder="验证码" name="validCode">
								</div>
								<span class="check-code">
									<img src="kaptcha.jpg" id="regCodeImage" title="看不清楚？换一张" style="cursor: pointer;height:100%;width:100%" onclick="this.src='kaptcha.jpg'+'?noCache='+Math.random();" />
								</span>
							</div>
<!-- 							<div class="clearfix"> -->
<!-- 								<select class="input-item" > -->
<!-- 									<option>请选择登录身份！</option> -->
<!-- 									<option>老师</option> -->
<!-- 									<option>学生</option> -->
<!-- 									<option>管理员</option> -->
<!-- 								</select> -->
<!-- 							</div> -->
<!-- 							<div class="tips clearfix"> -->
<!-- 								<div style="margin-left: 34px;"> -->
<!-- 									<label> -->
<!-- 										<input type="radio"  name="usergroupName">老师&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 										<input type="radio"  name="usergroupName">学生&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 										<input type="radio"  name="usergroupName">管理员 -->
<!-- 									</label> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="tips clearfix">
								<label>
<!-- 									<input type="checkbox" checked="checked" name="rmbUsrName" style="">记住用户名 -->
<!-- 									<input type="checkbox" name="rmbUsrPwd" style="margin-left:4px;">保存密码 -->
									<a href="reg.jsp" target="_blank" style="margin-left:34px;float:none;font-size:14px;color:#a3a3a3">立即注册</a>
<!-- 									<a href="forget.jsp" target="_blank" style="margin-left:14px;float:none;font-size:14px;color:#a3a3a3">忘记密码？</a> -->
								</label>
							</div>
							<div class="enter">
								<a href="javascript:document.getElementById('loginFormId').submit();" class="purchaser" onClick="">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
								<a href="javascript:document.getElementById('loginFormId').reset();" class="supplier" onClick="">重&nbsp;&nbsp;&nbsp;&nbsp;置</a>
							</div>
						</form>
						<div class="line line-y"></div>
						<div class="line line-g"></div>
					</div>
				</div>
				<div class="lg-poster"></div>
			</div>
		</div>
	</div>
	<div class="login-ft">
		<div class="ft-inner">
			<div class="address">地址：内蒙古巴彦淖尔市临河区&nbsp;邮编：015000&nbsp;&nbsp;Copyright&nbsp;©&nbsp;2017&nbsp;内蒙古美术职业学院&nbsp;版权所有</div>
			<div class="other-info">建议使用IE8及以上版本浏览器&nbsp;蒙ICP备&nbsp;14000487号-1&nbsp;</div>
		</div>
	</div>
</body>
</html>

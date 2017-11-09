<%@ page contentType="text/html;charset=utf-8" %> 
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<!--让部分国产浏览器默认采用高速模式渲染页面 -->
    <meta name="renderer" content="webkit">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>找回密码</title>
    <!-- Bootstrap -->
    <link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="bootstrap-3.3.7-dist/js/jquery/jquery-1.12.4.min.js"></script>
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>  
    .col-center-block {  
        float: none;  
        display: block;  
        margin-left: auto;  
        margin-right: auto;  
    }  
    </style>  
  </head>
  <body>
  	<div class="container-fluid">
  		<br/><br/><br/><br/>
		<form id="useraddForm" class="form-horizontal" action="${pageContext.request.contextPath}/user!doNotNeedSession_reg.do" method="post">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-1 col-sm-offset-2 control-label">用户名：</label>
				<div class="col-sm-5">
					<input name="cname" class="form-control" placeholder="用户名"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-1 col-sm-offset-2 control-label">密码：</label>
				<div class="col-sm-5">
					<input name="cpwd" type="password" class="form-control" placeholder="密码"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-1 col-sm-offset-2 control-label">重复密码：</label>
				<div class="col-sm-5">
					<input type="password"  class="form-control" placeholder="重复密码"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-1 col-sm-offset-2 control-label">真实姓名：</label>
				<div class="col-sm-5">
					<input name="crealname" class="form-control" placeholder="真实姓名"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-1 col-sm-offset-2 control-label">手机号：</label>
				<div class="col-sm-5">
					<input name="mobile" class="form-control" placeholder="手机号"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-1 col-sm-offset-2 control-label">邮箱：</label>
				<div class="col-sm-5">
					<input name="cemail" class="form-control" placeholder="邮箱"/>
				</div>
			</div>
			<div class="row">
			  <div class="col-sm-1 col-sm-offset-4">
			     <button type="submit" class="form-control btn btn-success">提交注册</button>
			  </div>
			   <div class="col-sm-1 col-sm-offset-1">
			     <button type="reset" class="form-control btn btn-info">重置</button>
			  </div>
			</div>
		</form>
	</div>
  </body>
</html>
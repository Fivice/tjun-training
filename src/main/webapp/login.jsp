<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
	<link rel="shortcut icon"href="${contextPath }/static/img/login/logo.ico" type="image/x-icon"/>
	<link rel="icon" href="${contextPath }/static/img/login/logo.ico" type="image/x-icon"/>
	<title>培训服务管理系统</title>
	<!-- Bootstrap core CSS -->
	<link href="${contextPath }/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${contextPath }/static/bootstrap/css/font-awesome.min.css" rel="stylesheet">
	<link href="${contextPath }/static/alert/jquery-confirm.min.css" rel="stylesheet">
	<link href="${contextPath }/static/css/style.css" rel="stylesheet">
	<link href="${contextPath }/static/css/style-responsive.css" rel="stylesheet">

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<%--<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->--%>
<style type="text/css">
.login-body {
	background: #65cea7 url(${contextPath }/static/img/login/login-bg.jpg) no-repeat fixed;
	background-size: cover;
	width: 100%;
	height: 100%;
}

#login_form {
	display: block;
}

#register_form {
	display: none;
}
.checkbox {
	padding-left: 21px;
}
</style>
</head>
<body class="login-body">
	<div class="container" style="min-width: 769px">
		<div class="row">
			<div class="col-lg-12 text-center" style="margin-top: 15%">
				<div class="text-center price-head">
					<span class="color-terques" style="font-family: STXingKai;font-size: 80px;color: #FFFF"> 培训服务管理系统 </span>
				</div>
				<p class="text-right col-sm-6 col-sm-offset-4" style="font-family: 华文楷体;font-size: 24px;color: #FFFF">——— 国网安徽培训中心</p>
			</div>
		</div>
		<div class="row" style="background: rgba(255,255,255,0.1);color: #FFFF;margin-right: 29%;margin-left: 29%;margin-top: 2%">
			<form class="form-horizontal" id="login_form">
				<h3 class="form-title col-sm-offset-3">登录</h3>
				<div class="col-sm-6 col-sm-offset-3">
					<div class="form-group" style="margin-top: 20px">
						<div class="iconic-input">
							<i class="fa fa-user"></i>
							<input type="text" class="form-control" placeholder="请输入账号" name="loginAccount"/>
						</div>
					</div>
					<div class="form-group" style="margin-top: 30px">
						<div class="iconic-input">
							<i class="fa fa-lock"></i>
							<input type="password" class="form-control" placeholder="请输入密码" name="loginPass"/>
						</div>
					</div>
					<%--<div class="form-group">--%>
						<%--<label class="checkbox"> <input type="checkbox"--%>
							<%--name="rememberMe" value="1" /> 记住我--%>
						<%--</label>--%>
						<%--<hr />--%>
						<%--<a href="javascript:;" id="register_btn" class="">注册？</a>--%>
					<%--</div>--%>

				</div>
				<div class="col-sm-12" style="border-bottom: #faf3f3a6 1px solid;margin-top: 10px">

				</div>
				<div class="col-sm-6 col-sm-offset-3" style="margin-bottom: 20px;margin-top: 20px">
					<div class="form-group" style="padding-top: 0px">
						<input type="submit" class="btn btn-success pull-right" value="登录 " />
					</div>
				</div>

			</form>
			<form class="form-horizontal col-sm-offset-3 col-md-offset-3" id="register_form">
				<h3 class="form-title">注册</h3>
				<div class="col-sm-9 col-md-9">
					<div class="form-group">
						<i class="fa fa-user fa-lg"></i> <input
							class="form-control required" type="text" placeholder="请输入账号"
							name="loginAccount" autofocus="autofocus" id="register_account" />

					</div>
					<div class="form-group">
						<i class="fa fa-lock fa-lg"></i> <input
							class="form-control required" type="password"
							placeholder="请输入密码" id="register_password" name="loginPass" />
					</div>
					<div class="form-group">
						<i class="fa fa-check fa-lg"></i> <input
							class="form-control required" type="password"
							placeholder="请输入确认密码" name="rloginPass" />
					</div>
					<div class="form-group">
						<input type="submit" class="btn btn-success pull-right"value="注册" />
						<input type="button" class="btn btn-info pull-left" id="back_btn" value="返回" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="${contextPath }/static/common/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${contextPath }/static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${contextPath }/static/alert/jquery-confirm.min.js" ></script>
	<script type="text/javascript" src="${contextPath }/static/common/jquery/jquery.validate.min.js" ></script>
	<script type="text/javascript" src="${contextPath }/static/login/login.js" ></script>
	<script type="text/javascript">
        $(function () {
            if (top.location != location){
                top.location.href = location.href;
            }
        })
        $(function () {
            console.log("cookie"+document.cookie.split(";"))
        })
	</script>
</body>
</html>

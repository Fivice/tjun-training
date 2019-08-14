<%--
  Created by IntelliJ IDEA.
  User: Fivice
  Date: 2018/11/19
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>流水号没有绑定信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="${ctxsta}/common/layer/mobile/need/layer.css" />
    <!-- Theme style -->
    <link rel="stylesheet" href="${ctxsta}/css/noBindingInfo.css">
    <link rel="icon" href="${ctxsta}/img/login/logo.ico"/>

</head>
<body class="hold-transition lockscreen">

<div class="lockscreen-wrapper">
    <div class="lockscreen-logo" style="height: 100px;">
        <a href="http://tjuninfo.com/"><b>Tjun </b>training</a>
    </div>

    <!-- START LOCK SCREEN ITEM -->
    <div class="lockscreen-item">
        <!-- lockscreen image -->
        <div class="lockscreen-image">
            <img src="${ctxsta}/img/code/time.png" alt="流水号信息">
        </div>
        <!-- /.lockscreen-image -->

        <!-- lockscreen credentials (contains the form) -->
        <form class="lockscreen-credentials">
            <div class="input-group">
                <input type="password" class="form-control" placeholder="${message}">

                <div class="input-group-btn">
                    <p type="button" class="btn"><i class="fa fa-warning"></i></p>
                </div>
            </div>
        </form>
        <!-- /.lockscreen credentials -->

    </div>
    <!-- /.lockscreen-item -->
    <div class="help-block text-center" style="margin-bottom:60px">
        流水号状态信息
    </div>
    <!-- <div class="text-center">
      <a href="login.html">Or sign in as a different user</a>
    </div> -->
    <div class="lockscreen-footer text-center">
        Copyright &copy; 2018 <b><a href="http://tjuninfo.com/" class="text-black">天君智能</a></b><br>
        All rights reserved
    </div>
</div>
<!-- /.center -->
<script src="${ctxsta}/jquery/jquery-3.2.1.js"></script>
<script src="${ctxsta}/bootstrap/js/bootstrap.js"></script>
<script src="${ctxsta}/common/layer/mobile/layer.js"></script>
<script src="${ctxsta}/common/layer/layer.js"></script>
<script src="${ctxsta}/entity/signUpManager/commonUtils.js"></script>


</body>
<script type="text/javascript">
    //跳出iframe
    $(function () {
        if (top.location != location){
            top.location.href = location.href;
        }
    })
</script>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
    <title>欢迎</title>
    <style type="text/css">
        #evanyou{
            position:fixed;
            width:100%;
            height:100%;
            left:0;
            top:0;
        }
        html,body{
            margin: 0;
            padding: 0;
            height: 100%;
        }
    </style>
</head>
<body>
<canvas id="evanyou" width="1920" height="934"></canvas>
<div class="jumbotron" style="height: 100%;margin-bottom: 0;">
    <div class="col-sm-6 col-sm-offset-2">
        <h1>欢迎使用 !</h1>
    </div>
    <div class="col-sm-6 col-sm-offset-2" style="border-left:3px solid #00c0ef;margin-top: 25px;margin-bottom: 25px">
        <h4>尊敬的<span style="color: #2f4050;font-weight: 800">${user}</span>使用前请仔细阅读使用说明文档。</h4>
    </div>
    <div class="col-sm-6 col-sm-offset-2">
        <p><a class="btn btn-primary btn-sm" href="/upload/userOperate/培训服务管理系统说明书.docx" role="button"><i class="fa fa-download"> 用户使用说明</i></a></p>
    </div>
    <c:if test="${roleId eq true}">
    <div class="col-sm-6 col-sm-offset-2">
        <p><a class="btn btn-primary btn-sm" href="/upload/userOperate/tjun-training/${day}" role="button"><i class="fa fa-download"> &ensp;备份数据库&ensp;</i></a></p>
    </div>
    </c:if>

</div>
<%--jQuery--%>
<script src="${ctxsta}/jquery/jquery-3.2.1.js"></script>
<%--bootstrap--%>
<script src="${ctxsta}/bootstrap/js/bootstrap.js"></script>
<%--main.js--%>
<script src="${ctxsta}/cms/js/main.js"></script>
</body>
</html>
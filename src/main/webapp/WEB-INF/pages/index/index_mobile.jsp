<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>报到管理统计</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="${ctxsta}/common/layer/mobile/need/layer.css" />
    <link rel="stylesheet" href="${ctxsta}/css/classFundsMobile.css" />
    <link rel="stylesheet" href="${ctxsta}/common/icheck/line/grey.css" />
    <link rel="icon" href="${ctxsta}/img/login/logo.ico"/>
    <link href="${ctxsta}/css/index_mobile.css" rel="stylesheet" type="text/css">
    <link href="${ctxsta}/css/reset.css" rel="stylesheet" type="text/css">
    <style>
        tr th{
            white-space: nowrap;
        }
        body{
            height: inherit;
        }
        @media screen and (max-height: 620px) {
            body {
                height: 620px;
            }
        }
    </style>
    
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-table" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Admin</a>
        </div>
        <div class="collapse navbar-collapse" id="nav-table">
            
        </div>
    </div>
</nav>

<div class="container" style="padding-right: 5px;padding-left: 5px">
    <div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-list"> 欢迎使用</i>
            </div>
            <div class="panel-body" style="padding: 15px 1px">
                <div class="clear"></div>
        <div class="title_box">
            <div class="title_logo">
                <span>国网安徽培训中心</span></div>
        </div>
        <div class="classify_box" style="overflow: scroll;margin-bottom: 50px">
            <ul class="classify_num">
                <li><a onclick="pageJump1()"><i></i><span>年度数据分析</span></a></li>
                <li><a onclick="pageJump2()"><i></i><span>报到管理</span></a></li>
                <li><a onclick="pageJump3()"><i></i><span>班级经费分析</span></a></li>
                <li><a onclick="pageJump4()"><i></i><span>评价管理</span></a></li>
                
            </ul>
        </div>
            </div>
        </div>

    </div>
</div>
<footer style="height: 50px;position: fixed;bottom: 0px;left: 0px;width: 100%;background-color: #333;z-index: 998">
    <div class="col-sm-12" style="color: #eeeeee;line-height: 50px">
        <i class="fa fa-copyright"> copyright 2019</i>
    </div>
</footer>
<%--jQuery--%>
<script src="${ctxsta}/jquery/jquery-3.2.1.js"></script>
<%--bootstrap--%>
<script src="${ctxsta}/bootstrap/js/bootstrap.js"></script>
<%--layer--%>
<script src="${ctxsta}/common/layer/mobile/layer.js"></script>
<script src="${ctxsta}/common/layer/layer.js"></script>
<script src="${ctxsta}/common/layui/layDateChange/layDate/laydate.js"></script>
<%--bootstrap table--%>
<script src="${ctxsta}/common/bootstrap-table/bootstrap-table.js" type="text/javascript"></script>
<%--iCheck--%>
<script src="${ctxsta}/common/bootstrap-table/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<script src="${ctxsta}/common/icheck/icheck.js" type="text/javascript"></script>
<%--nav.js 对导航进行管理--%>
<script src="${ctxsta }/cms/js/mobile/nav.js"></script>
<%--js for this page--%>
<script src="${ctxsta }/entity/signUpManager/signUpMobile.js"></script>
<%--<script src="${ctxsta}/cms/js/common.js"></script>--%>


<script type="text/javascript" src="${ctxsta }/cms/js/mobile/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="${ctxsta }/cms/js/mobile/jquery.touchSlider.js"></script>
<script>
    $(function () {
        if (top.location != location){
            top.location.href = location.href;
        }
    })
    var refreshTable = function () {
        $("#table").bootstrapTable('destroy');
        initClassFundsTable();
    }
    var search = function(){
        refreshTable();

        $("#myModal").modal('hide');
    }
    var reset = function(){
        $("#startStopTime").val("");
        $("#classNumber").val("");
        $("#className").val("");
        $("#teacherName").val("");
    }
    
    function pageJump1(){
    	window.location.href = ctx + '/annualData/mobileView.action';
    }
    function pageJump2(){
    	window.location.href = ctx + '/signUpManager/mobileView.action';
    }
    function pageJump3(){
    	window.location.href = ctx + '/classFunds/mobileView.action';
    }
    function pageJump4(){
    	window.location.href = ctx + '/evaluationManagement/mobileView.action';
    }


</script>
</body>

</html>

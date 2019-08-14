<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>评价管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="${ctxsta}/common/layer/mobile/need/layer.css" />
    <link rel="stylesheet" href="${ctxsta}/css/classFundsMobile.css" />
    <link rel="icon" href="${ctxsta}/img/login/logo.ico"/>

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
                <i class="fa fa-list"> 评价管理</i>
            </div>
            <div class="panel-body" style="padding: 15px 1px">
                <div style="overflow: scroll;margin-bottom: 50px">
                    <table id="table" class="table table-hover table-responsive table-striped table-bordered">

                    </table>
                </div>
            </div>
        </div>


        <!--点击右侧弹出-->
        <a href="javascript:void(0)" class="btn btn-default mybtn" data-toggle="modal" data-target="#myModal" style="z-index: 999"><i class="fa fa-filter"></i></a>
        <!-- 弹出层 modal -->
        <div class="modal right fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel"><i class="fa fa-filter"></i> 请选择过滤条件</h4>
                    </div>
                    <div class="modal-body" style="height: calc(100% - 106px);min-height: 450px">
                        <div class="btn-group m-t-sm">
                            <p style="font-weight: bolder;margin-bottom: 5px">选择开班时间:</p>
                            <input type="text" class="form-control" readonly name="startStopTime" id="startStopTime" placeholder="请选择开班时间" value="${startStopTime}">
                        </div>
                        <hr/>
                        <div class="btn-group m-t-sm">
                            <p style="font-weight: bolder;margin-bottom: 5px">班级名称:</p>
                            <input type="text" class="form-control" name="className" id="className" placeholder="请输入班级名称" >
                        </div>
                        <hr/>

                        <div class="btn-group m-t-sm">
                            <p style="font-weight: bolder;margin-bottom: 5px">班主任名称:</p>
                            <input type="text" class="form-control" name="teacherName" id="teacherName" placeholder="请输入班主任名称称" >
                        </div>
                        <hr/>
                        <div class="btn-group m-t-sm">
                            <p style="font-weight: bolder;margin-bottom: 5px">班级编号:</p>
                            <input type="text" class="form-control" name="classNumber" id="classNumber" placeholder="请输入班级编号" >
                        </div>
                        <hr/>

                        <div class="btn-group m-t-sm">
                            <label for="plan">班级类型:</label>
                            <select class="form-control" name="plan" id="plan">
                                <option value="">全部</option>
                                <option value="0">计划内</option>
                                <option value="1">计划外</option>
                                <option value="2">非培训班</option>
                            </select>
                        </div>

                    </div>
                    <div class="modal-footer right" style="height: 50px;position: absolute;bottom: 0px;padding: 8px 30px;width: 100%;background-color: #333;border-top-color: #333">
                        <div class="btn-group" role="group">
                            <button class="btn btn-default" onclick="reset()">清除</button>
                            <button class="btn btn-default" onclick="search()">确定</button>
                        </div>
                    </div>
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
<script src="${ctxsta}/common/bootstrap-table/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<%--nav.js 对导航进行管理--%>
<script src="${ctxsta }/cms/js/mobile/nav.js"></script>
<%--js for this page--%>
<script src="${ctxsta }/entity/evaluationManagement/evaluationMobile.js"></script>

<script>
    var refreshTable = function () {
        $("#table").bootstrapTable('refresh');
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
        $("#plan").val("");
    }


</script>
</body>

</html>

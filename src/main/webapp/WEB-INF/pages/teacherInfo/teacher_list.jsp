<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>教师信息管理</title>
    <script src="${ctxsta}/cms/js/teacherInfo/teacherInfo.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">

<div  id="cc">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>教师列表</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <input type="hidden" id="contextPath" value="${contextPath }"/>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="添加教师信息"
                                            onclick="add()"> <i class="glyphicon glyphicon-plus"></i>
                                    </button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="编辑教师信息"
                                            onclick="edit()">
                                        <i class="glyphicon glyphicon-pencil"></i>
                                    </button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="删除教师信息"
                                            onclick="del()" id="del">
                                        <i class="glyphicon glyphicon-minus"></i>
                                    </button>
                                </div>
                                <div class="btn-group m-t-sm" style="margin-top:25px">
                                    <input type="text" id="subject"  placeholder="请输入科目" class="form-control" />&emsp;

                                </div>
                                <div class="btn-group m-t-sm" style="margin-top:25px">
                                    <input type="text" id="tiName"  placeholder="请输入姓名" class="form-control"/>&emsp;
                                </div>
                                <div class="btn-group m-t-sm" style="margin-top:25px">
                                    <input type="text" id="tiDepartment"  placeholder="请输入工作单位" class="form-control"/>&emsp;
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button id="search" type="button" class="btn btn-primary">查询</button>&emsp;
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button id="empty" type="button" class="btn">重置</button>
                                </div>
                                <table id="table_teacherInfo" class="table table-bordered table-striped"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // 新建
    function add(){
        // alert("新建数据");
        location = "${ctx}/teacherInfo/form.action";
    }
    // 编辑
    function edit() {
        var getSelectRows=$("#table_teacherInfo").bootstrapTable('getSelections');
        console.log(getSelectRows)
        ids = [];
        for (var i = 0; i < getSelectRows.length; i++) {
            ids.push(getSelectRows[i].tiId)
        }
        console.log(ids)
        commonEdit(ids,"${ctx}/teacherInfo/form.action?id="+ids[0]);

    }

    $("#search").click(function() {
        // var type = $('#type').val();
        // var startTime = $("#startTime").val();
        // var endTime = $("#endTime").val();
        // window.location.href=ctx +'/powerCheck/powerSystem.action';
        $("#table_teacherInfo").bootstrapTable('refresh');
    })
    $("#empty").click(function(){
        $("#subject").val("");
        $("#tiName").val("");
        $("#tiDepartment").val("");
    })
</script>
</body>
</html>
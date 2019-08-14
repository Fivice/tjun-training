<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>成绩与证书管理</title>
    <script src="${ctxsta}/entity/scoreReport/scoreReport.js"></script>
    <script src="${ctxsta }/entity/scoreReport/bootstrapValidator.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">

<div  id="cc">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>成绩与证书列表</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <input type="hidden" id="contextPath" value="${contextPath }"/>
                                <div class="btn-group m-t-sm">
                                    <input type="text" class="form-control" name="month" id="month" value="${month}" placeholder="请选择开班时间">
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button id="search" type="button" class="btn btn-primary">查询</button>&emsp;
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button id="empty" type="button" class="btn">重置</button>
                                </div>
                                <table id="table_scoreReport" class="table table-bordered table-striped"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    //年月范围选择
    laydate.render({
        elem: '#month'
        ,type: 'month'
        ,theme: '#6CA6CD'
        ,isInitValue: true
        /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });
    $("#search").click(function() {
        // var type = $('#type').val();
        // var startTime = $("#startTime").val();
        var month = $("#month").val();
        // window.location.href=ctx +'/powerCheck/powerSystem.action';
        $("#table_scoreReport").bootstrapTable('refresh');
    })
    $("#empty").click(function(){
        $("#month").val("");
    })
</script>
</body>
</html>
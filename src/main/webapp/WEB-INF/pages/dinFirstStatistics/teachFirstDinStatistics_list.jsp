<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/dinFirstStatistics/teachFirstDinStatistics.js"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">
    <ul class="nav nav-tabs">
        <li ><a href="${ctx}/diningFirstCount/stuView.action">学员首日就餐统计</a></li>
        <li class="active"><a href="${ctx}/diningFirstCount/teaView.action"><i class="fa fa-list-alt"></i>教师首日就餐统计</a></li>
    </ul>
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                        <div class="row row-lg">
                                <div class="btn-group m-t-sm">
                                    <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="startStopTime" id="startStopTime" placeholder="请选择就餐时间" value="${startStopTime}">
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-info" id="search"><i class="fa fa-search"></i>查询</button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button id="reset" type="button" class="btn" >清空</button>
                                </div>
                                <!-- 数据表格 -->
                                <table id="table" class="table table-hover"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script>
    $("#search").click(function() {
      var startStopTime = $("#startStopTime").val();
      $("#table").bootstrapTable('refresh');
    })
    $("#reset").click(function(){
        $("#startStopTime").val("");
    })
    // //年月范围选择
    // laydate.render({
    //     elem: '#startStopTime'
    //     ,type: 'date'
    //     ,theme: '#6CA6CD'
    // });
    //年月范围选择
    laydate.render({
        elem: '#startStopTime'
        ,type: 'date'
        ,theme: '#6CA6CD'
        ,range: '至' //或 range: '~' 来自定义分割字符

        });

</script>
</body>
</html>

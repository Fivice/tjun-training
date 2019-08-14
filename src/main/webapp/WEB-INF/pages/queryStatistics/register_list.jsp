<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/queryStatistics/register.js"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-title">
        <h5>班级实到人数详情</h5>
        <div class="ibox-tools"></div>
    </div>
    <div class="ibox-content">
        <div class="row row-lg">
                            <input type="hidden" id="id" name="id" value="${classInfoList.id}">
            <h3>[${classInfoList.classNumber}]${classInfoList.className} &nbsp; 办班时间：${startTime} &nbsp; 培训天数：${classInfoList.dayNum}天</h3>

            <table id="table" class="table table-hover"></table>
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-primary" title="返回班级管理" onclick="window.history.back()"
                                >返回查询统计</button>
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

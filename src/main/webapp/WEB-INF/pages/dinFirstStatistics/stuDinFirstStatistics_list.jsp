<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/dinFirstStatistics/stuDinFirstStatistics.js"></script>
    <script src="${ctxsta }/entity/dinFirstStatistics/teachFirstDinStatistics.js"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <section id="main-content">
        <section class="wrapper">
            <!-- page start-->
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading tab-bg-dark-navy-blue ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a data-toggle="tab" href="#stuDinTableContent">学员首日就餐统计</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" onclick="initTeachDinTable()" href="#teachDinTableContent">教师首日就餐统计</a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div id="stuDinTableContent" class="tab-pane active">
                                    <div class="col-sm-12">
                                        <div class="btn-group m-t-sm">
                                            <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="startStopTime" id="startStopTime" placeholder="请选择就餐时间" value="${startStopTime}" >
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <button type="button" class="btn btn-info" id="search"><i class="fa fa-search"></i>查询</button>
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <button id="reset" type="button" class="btn" >清空</button>
                                        </div>
                                        <!-- 数据表格 -->
                                        <table id="stuDinTable" class="table table-hover"></table>
                                    </div>
                                </div>
                                <div id="teachDinTableContent" class="tab-pane">
                                    <div class="col-sm-12">
                                        <div class="btn-group m-t-sm">
                                            <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="startStopTime" id="startStopTime1" placeholder="请选择就餐时间" value="${startStopTime}" >
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <button type="button" class="btn btn-info" id="search1"><i class="fa fa-search"></i>查询</button>
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <button id="reset1" type="button" class="btn" >清空</button>
                                        </div>
                                        <!-- 数据表格 -->
                                        <table id="teachDinTable" class="table table-hover"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <!-- page end-->


        </section>
    </section>
</div>
<script>
    $("#search").click(function() {
      $("#stuDinTable").bootstrapTable('refresh');
    });
    $("#reset").click(function(){
        $("#startStopTime").val("");
    });

    $("#search1").click(function() {
        $("#teachDinTable").bootstrapTable('refresh');
    });
    $("#reset1").click(function(){
        $("#startStopTime1").val("");
    });

    //加载教师表
    function initTeachDinTable() {
        teacheDingTable();
    }

    //年月范围选择
    laydate.render({
        elem: '#startStopTime'
        ,type: 'date'
        ,theme: '#6CA6CD'
        ,range: '至' //或 range: '~' 来自定义分割字符
        });
    //年月范围选择
    laydate.render({
        elem: '#startStopTime1'
        ,type: 'date'
        ,theme: '#6CA6CD'
        ,range: '至' //或 range: '~' 来自定义分割字符
    });
</script>
</body>
</html>

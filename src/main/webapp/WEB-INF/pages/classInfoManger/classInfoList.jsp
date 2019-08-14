<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>班级管理</title>
    <script src="${ctxsta }/entity/classInfo/classInfoManger.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
  </head>
<body class="fixed-sidebar full-height-layout gray-bg">

<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>班级管理</h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
        <div class="btn-group m-t-sm">
            <input type="text" class="form-control" name="startStopTime" value="${startStopTime }" id="startStopTime" placeholder="请选择开班时间">
        </div>
        <div class="btn-group m-t-sm">
            <input type="text" class="form-control" name="classNumber" id="classNumber" placeholder="请输入班级编号">
        </div>
        <div class="btn-group m-t-sm">
            <input type="text" class="form-control" name="className" id="className" placeholder="请输入班级名称">
        </div>
        <div class="btn-group m-t-sm">
            <input type="text" class="form-control" name="teacherName" id="teacherName" placeholder="请输入班主任姓名">
        </div>
     <div class="btn-group m-t-sm">
        <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="plan">
            <option value="">全部</option>
            <option value="0">计划内</option>
            <option value="1">计划外</option>
            <option value="2">非培训班</option>
        </select>
        </div>
                            <div class="btn-group m-t-sm">
                                <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="regPlace">
                                    <option value="">选择报名地点</option>
                                    <c:forEach items="${placeList }" var="place">
                                        <option value="${place }">${place }</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="entryStartTime" id="entryStartTime" placeholder="请选择报名时间">
                            </div>
        <div class="btn-group m-t-sm">
            <button type="button" class="btn btn-info" id="search" onclick="seach()"><i class="fa fa-search"></i>查询</button>
        </div>
        <div class="btn-group m-t-sm">
            <button id="reset" type="button" class="btn" onclick="reset()">清空</button>
        </div>
    </div>
                        <!-- 数据表格 -->
                        <div class="col-sm-12" style="margin-top: 10px;">
                            <div class="col-sm-12">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <i class="fa fa-list"></i>&nbsp;班级管理
                                    </div>
                                    <div class="panel-body" style="padding-top: 0">
                                        <!-- 数据表格 -->
                                        <table id="table" class="table table-hover"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
</div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    //清空input
    function reset(){
        $("input").prop("value","");
        $("#plan").prop("value","");
        $("#regPlace").prop("value","");
    }
    //年月范围选择
    laydate.render({
        elem: '#startStopTime'
        ,type: 'month'
        ,theme: '#6CA6CD'
    });

    //年月范围选择
    laydate.render({
        elem: '#entryStartTime'
        ,type: 'date'
        ,theme: '#6CA6CD'
    });
</script>
</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/queryStatistics/queryStatistics.js"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <style type="text/css">
        /*解决设置表头列宽无效*/
        #table{
            table-layout: fixed;
        }

    </style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
    <%--<ul class="nav nav-tabs">--%>
        <%--<li class="active"><a href="${ctx}/diningCount/stuView.action"><i class="fa fa-list-alt"></i>学员就餐统计</a></li>--%>
        <%--<li><a href="${ctx}/diningCount/teaView.action">教师就餐统计</a></li>--%>
    <%--</ul>--%>
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>查询统计</h5>
                        <div class="ibox-tools"></div>
                    </div>
                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <div class="btn-group m-t-sm">
                                    <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="startStopTime" id="startStopTime" placeholder="请选择开班时间段" value="${startStopTime.substring(0,7)}">
                                </div>
                                <div class="btn-group m-t-sm">
                                    <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="classNumber" id="classNumber" placeholder="请输入班级编号" >
                                </div>
                                <div class="btn-group m-t-sm">
                                    <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="className" id="className" placeholder="请输入班级名称" >
                                </div>
                                <div class="btn-group m-t-sm">
                                    <select style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" name="trainingType" id="trainingType">
                                        <option value="">请选择培训类型</option>
                                        <c:forEach var="trainingType" items="${trainingType}">
                                            <option value="${trainingType.id}">${trainingType.type}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <select style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" name="plan" id="plan">
                                        <option value="">全部</option>
                                        <option value="0">计划内</option>
                                        <option value="1">计划外</option>
                                        <option value="2">非培训班</option>
                                    </select>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <label for="place"></label>
                                    <select style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" name="place" id="place">

                                    </select>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-info" id="search"><i class="fa fa-search"></i>查询</button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button id="reset" type="button" class="btn" >清空</button>
                                </div>
                        </div>
                                <!-- 数据表格 -->
                                <table id="table" class="table table-hover"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

<script>
    $("#search").click(function() {
      var startStopTime = $("#startStopTime").val();
      var classNumber = $("#classNumber").val();
      var className = $("#className").val();
      var trainingType = $("#trainingType").val();
      var plan = $("#plan").val();
      $("#table").bootstrapTable('refresh');
    })
    $("#reset").click(function(){
        $("#startStopTime").val("");
        $("#classNumber").val("");
        $("#className").val("");
        $("#trainingType").val("");
        $("#plan").val("");
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
        ,type: 'month'
        ,theme: '#6CA6CD'

        });

</script>
</body>
</html>

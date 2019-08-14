<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>就餐统计</title>
    <script src="${ctxsta }/entity/diningDataStatistics/diningDataStatistics.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/diningDataStatistics/firstDayDiningDataStatistics.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <%--search-bar begin --%>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>就餐统计</h5>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="startStopTime" id="startStopTime" placeholder="统计时间范围">
                            </div>
                            <div class="btn-group m-t-sm">
                                <%--<input type="text" class="form-control" name="diningPlace" id="diningPlace" placeholder="统计就餐地点">--%>
                                <label style="margin-bottom: 0">
                                    <select id="diningPlaceSelect" class="form-control">

                                    </select>
                                </label>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button style="margin-bottom: 0px" type="button" class="btn btn-info" id="search" onclick="seach()"><i class="fa fa-search"></i>查询</button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button style="margin-bottom: 0px" class="btn btn-info" onclick="foundsStatistics()">统计经费</button>
                                <%--<button style="margin-bottom: 0px" class="btn btn-default">学员就餐率</button>--%>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button style="margin-bottom: 0px" class="btn btn-info" onclick="firstDayFoundsStatistics()">首日就餐</button>
                            </div>
                        </div>
                        <div class="col-sm-12" style="padding: 0;margin-top: 10px;">
                            <%--<div class="col-sm-3">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        就餐时间：
                                    </div>
                                    <div class="panel-body">
                                        <div id="diningTable" class="col-sm-12" style="padding: 0">

                                        </div>
                                    </div>
                                </div>
                            </div>--%>
                            <%--table begin--%>
                            <div class="col-sm-4">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <i class="fa fa-list"></i>&nbsp班级列表
                                    </div>
                                    <div class="panel-body" style="padding-top: 0">
                                        <table id="classInfo">

                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-8" style="box-sizing: border-box">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <i class="fa fa-cutlery"></i>&nbsp就餐统计
                                    </div>
                                    <div class="panel-body">
                                        <div>
                                            <h3 id="stuDiningTitle" class="col-sm-12" style="margin-bottom: 5px;border-left: #ca4440 2px solid;font-weight: bolder">学员就餐统计</h3>
                                            <div id="stuDiningStatisticsTable" class="col-sm-12">

                                            </div>
                                        </div>
                                        <hr/>
                                        <div>
                                            <h3 id="teachDiningTitle" class="col-sm-12" style="margin-bottom: 5px;border-left: #ca4440 2px solid;font-weight: bolder">教师就餐统计</h3>
                                            <div id="teachDiningStatisticsTable" class="col-sm-12">

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
    </div>
    <%--search-bar end--%>
</div>

</body>
</html>
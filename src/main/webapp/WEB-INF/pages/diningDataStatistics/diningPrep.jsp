<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>备餐统计</title>
    <script src="${ctxsta }/entity/diningDataStatistics/diningPrep.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/diningDataStatistics/firstDayDiningDataStatistics.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script>

    </script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <%--search-bar begin --%>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>备餐统计</h5>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="today" id="today" placeholder="选择日期">
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
                        </div>
                        <div class="col-sm-12" style="padding: 0;margin-top: 10px;">
                            <div class="col-sm-12">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <i class="fa fa-list"></i>&nbsp当日需备餐统计
                                    </div>
                                    <div class="panel-body" style="padding-top: 0">
                                        <table id="classInfo">

                                        </table>
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
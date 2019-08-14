<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>
    <link rel="stylesheet" href="${ctxsta}/bootstrap/x-editable/dist/bootstrap3-editable/css/bootstrap-editable.css" />
    <script src="${ctxsta }/entity/classroom/campusList.js" type="text/javascript"></script>
    <script src="${ctxsta}/bootstrap/x-editable/dist/bootstrap3-editable/js/bootstrap-editable.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <%--<h5>校区列表</h5>--%>
                        <div class="ibox-tools"></div>
                    </div>
                    <div class="ibox-content">
                        <div class="row row-lg">
                                <!-- 数据表格 -->
                                <table id="table" class="table table-hover"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

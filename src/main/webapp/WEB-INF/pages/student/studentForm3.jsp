<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/student/studentForm.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <input type="hidden" id="id" name="id" value="${siId}">
                            <!-- 数据表格 -->
                            <table id="table" class="table table-hover"></table>
                        </div>
                        <div class="btn-group m-t-sm">
                            <button type="button" class="btn btn-primary" title="返回" onclick="window.history.back()"
                            >返回
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

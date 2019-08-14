<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%--<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>--%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/cms/js/classDining/classDining2.js"></script>
</head>
<body>
<!-- 数据表格 -->
        <div class="panel panel-primary">
            <div class="panel-heading">
                <i class="fa fa-list"></i>&nbsp;就餐安排信息列表
            </div>
            <div class="panel-body" style="padding-top: 0">
                <input type="hidden" id="id" name="id" value="">
                <table id="table_class_dining" class="table table-hover"></table>
            </div>
        </div>

</body>
</html>
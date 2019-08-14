<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>成绩录入</title>
    <link rel="stylesheet" href="${ctxsta}/bootstrap/x-editable/dist/bootstrap3-editable/css/bootstrap-editable.css" />
    <script src="${ctxsta}/entity/scoreReport/scoreReport_write.js"></script>
    <script src="${ctxsta }/entity/scheduling/bootstrpValidator.js" type="text/javascript"></script>
    <script src="${ctxsta}/bootstrap/x-editable/dist/bootstrap3-editable/js/bootstrap-editable.js"></script>
</head>
<body class="gray-bg">
<div id="cc">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <div class="col-sm-12">
                            <h3>成绩录入</h3>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <!-- 工具栏 -->
                        <div id="toolbar">
                            <input type="hidden" id="contextPath" value="${contextPath }"/>
                            <input type="hidden" id="id" name="id" value="${id}">

                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-default" title="初始化" onclick="init(${id})"
                                >初始化</button>
                            </div>
                            </div>
                        <table id="table_scoreReport_write" class="table table-bordered table-striped"></table>
                        <div class="btn-group m-t-sm">
                            <button type="button" class="btn btn-primary"  title="返回成绩与证书管理" onclick="window.history.back()"
                            >返回</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#search").click(function () {
        // var type = $('#type').val();
        // var startTime = $("#startTime").val();
        // var endTime = $("#endTime").val();
        // window.location.href=ctx +'/powerCheck/powerSystem.action';
        $("#table_scoreReport_write").bootstrapTable('refresh');
    })
    $("#empty").click(function () {
        $("#stuName").val("");
    })
    var sss = $('#id').val();

</script>
</body>
</html>
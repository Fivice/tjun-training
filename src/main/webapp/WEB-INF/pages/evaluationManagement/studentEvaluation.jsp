<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>学员评价情况</title>
    <script src="${ctxsta }/entity/evaluationManagement/studentEvaluation.js" type="text/javascript"></script>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>学员评价情况</h5>
                    <div class="ibox-tools"></div>
                </div>
                <input type="hidden" name="classId" value="${id}" id="classId">
                <div class="ibox-content">
                    <div class="btn-group m-t-sm">
                        <h3>报到人数:${studentCount} 已评价人数:${evaluationStudentCount} 未评价人数:${noEvaluationStudentCount}</h3>
                    </div>
                    <div class="row row-lg">
                        <!-- 数据表格 -->
                        <table id="table" class="table table-hover"></table>
                    </div>
                    <div class="btn-group m-t-sm">
                        <button type="button" class="btn btn-primary" title="返回评价管理" onclick="window.history.back()"
                        >返回评价管理</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
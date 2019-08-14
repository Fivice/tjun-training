<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>总体评价结果</title>
    <link rel="stylesheet" href="${ctxsta}/bootstrap/bootstrap-tagsinput/bootstrap-tagsinput.css"/>
    <script src="${ctxsta}/bootstrap/bootstrap-tagsinput/bootstrap-tagsinput.js" type="text/javascript"></script>
    <script src="${ctxsta}/entity/evaluationManagement/resultEvaluationTest.js" type="text/javascript"></script>

<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>总体评价结果建议</h5>
                    <div class="ibox-tools"></div>
                </div>
                <input type="hidden" name="classId" value="${id}" id="classId">
                <div class="ibox-content">
                    <div class="wrapper">
                        <div class="row">
                            <div class="col-sm-7">
                                <!-- 数据表格 -->
                                <div class="col-sm-12" style="padding: 0">
                                    <div class="btn-group" role="group" aria-label="Second group" style="position: relative;float: right;">
                                        <button type="button" class="btn btn-default" onclick="refreshEvaluationTable()"><i class="fa fa-refresh" title="刷新"></i></button>
                                        <%--<button type="button" class="btn btn-default"><i class="glyphicon glyphicon-export icon-share" title="导出"></i></button>--%>
                                        <button type="button" class="btn btn-default" onclick="printEvaluationData()"><i class="glyphicon glyphicon-print icon-share" title="打印"></i></button>
                                    </div>
                                </div>
                                <div id="table">

                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-primary" title="返回评价管理" onclick="window.history.back()">返回评价管理</button>
                                </div>
                            </div>
                            <div class="col-sm-5">
                                <div class="col-sm-12">
                                    <div class="col-sm-12">
                                        <label for="uselessWord">
                                            评价过滤关键词
                                        </label>
                                    </div>
                                    <div id="tagsInput" class="col-sm-12">
                                        <input id='uselessWord' data-role="tagsinput"  type='text' value='好,很好,可以,非常好,满意,很满意,非常满意,无,暂无,好极了,无意见,很棒'>
                                    </div>
                                    <div class="col-sm-12" style="margin-top: 5px;">
                                        <%--<div class="btn btn-primary">确定</div>--%>
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
</body>

</html>
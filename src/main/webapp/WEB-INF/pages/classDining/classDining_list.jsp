<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/cms/js/classDining/classDining.js"></script>
    <script src="${ctxsta }/cms/js/classDining/bootstrapValidator.js" type="text/javascript"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <div class="col-sm-12 ">
                            <h3>[${classInfo.classNumber}]${classInfo.className} &nbsp; ${classInfo.hostUnit} &nbsp; 开班时间：${classInfo.startStopTime}&nbsp;  培训天数：${classInfo.dayNum}天</h3>
                        </div>
                    </div>

                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <div class="btn-group m-t-sm">
                                        <button type="button" class="btn btn-default" title="添加记录" onclick="creatClassDining(${classInfo.id})"> <i class="glyphicon glyphicon-plus"></i> </button>
                                    </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="编辑" onclick="classDining_edit()">
                                        <i class="glyphicon glyphicon-pencil"></i></button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="删除" onclick="classDining_delete()"
                                            id="del"><i class="glyphicon glyphicon-minus"></i></button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="初始化" onclick="init(${classInfo.id})"
                                    >初始化</button>
                                </div>

                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="清空" onclick="empty(${classInfo.id})"
                                    >清空</button>
                                </div>

                                <input type="hidden" id="id" name="id" value="${classInfo.id}">


                                <table id="table_class_dining" class="table table-bordered table-striped"></table>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-primary" title="返回班级管理" onclick="window.history.back()"
                                    >返回班级管理</button>
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
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/cms/js/teachDining/teachDinForm.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="ibox-content">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <div class="col-sm-12 ">
                            <h4>[${classInfo.classNumber}]&nbsp;${classInfo.className}&nbsp;${classInfo.hostUnit} 开班时间：${classInfo.startStopTime} 培训天数：${classInfo.dayNum}天</h4>
                        </div>
                    </div>

                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="添加记录" onclick="creatTeacherDining(${classInfo.id},${number})"> <i class="glyphicon glyphicon-plus"></i> </button>
                                    </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="编辑" onclick="teachDining_edit()">
                                        <i class="glyphicon glyphicon-pencil"></i></button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="删除" onclick="teachDining_delete()"
                                            id="del"><i class="glyphicon glyphicon-minus"></i></button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="初始化" onclick="init()" >初始化</button>
                                </div>

                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="清空" onclick="empty()"
                                    >清空</button>
                                </div>

                                <input type="hidden" id="classId" name="classId" value="${classInfo.id}">
                                <input type="hidden" id="number" name="number" value="${number}">
                                <input type="hidden" id="time" name="time" value="${time}">


                                <table id="table_teach_dining" class="table table-bordered table-striped"></table>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-primary" title="返回教师管理" onclick="window.history.back()"
                                    >返回教师管理</button>
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
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>
    <script src="${ctxsta }/entity/classroom/classroom.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/classroom/bootstrapValidator.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/classroom/bootstrapValidator1.js" type="text/javascript"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>校区及教室资源列表</h5>
                        <div class="ibox-tools"></div>
                    </div>
                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="添加记录" onclick="creatClassroom()"> <i class="glyphicon glyphicon-plus"></i> </button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="编辑" onclick="edit()"> <i class="glyphicon glyphicon-pencil"></i> </button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="删除" onclick="del()" > <i class="glyphicon glyphicon-minus"></i> </button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="新增校区" onclick="addCampus()" > <i class="glyphicon glyphicon-plus">新增校区</i> </button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="可以编辑删除校区" onclick="campuslist()" > 校区列表</button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <select style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" id="schoolName" name="schoolName">
                                        <option value="">请选择校区名称</option>
                                        <c:forEach items="${campusList}" var="campusList">
                                            <option value=${campusList.id}>${campusList.schoolName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="btn-group m-t-sm">
                                    <select style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" id="classType" name="classType">
                                        <option value="">请选择教室类型</option>
                                        <option value='1' >普通教室</option>
                                        <option value='2' >机房</option>
                                    </select>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <input  type="text" style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" class="form-control" name="className" id="className" placeholder="请输入教室名称">
                                </div>&emsp;
                                <div class="btn-group m-t-sm">
                                    <button id="find" type="button" class="btn btn-primary" >查询</button>&emsp;
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button id="reset" type="button" class="btn">清空</button>
                                </div>
                                <!-- 数据表格 -->
                                <table id="table" class="table table-hover"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>
    $("#find").click(function() {
        $("#table").bootstrapTable('refresh');
    });
    $("#reset").click(function(){
        $("#schoolName").val("");
        $("#className").val("");
        $("#classType").val("");
    })
</script>
</body>
</html>

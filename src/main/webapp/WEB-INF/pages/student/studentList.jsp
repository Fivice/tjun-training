<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>学员列表</title>
    <script src="${ctxsta }/entity/student/student.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/student/tree3.js" type="text/javascript"></script>
  </head>
<body class="fixed-sidebar full-height-layout gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>学员列表</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="添加"  onclick="add()"> <i class="glyphicon glyphicon-plus"></i> </button>
                                </div>
                                <div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="编辑" onclick="edit()"> <i class="glyphicon glyphicon-pencil"></i> </button>
                                </div>
                                <%--<div class="btn-group m-t-sm">
                                    <button type="button" class="btn btn-default" title="删除" onclick="del()"> <i class="glyphicon glyphicon-minus"></i> </button>
                                </div>&emsp;--%>
                                <div class="btn-group m-t-sm">
                                    <%--<select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="unitId">
                                        <option value="">全部</option>
                                        <c:forEach items="${ unitList}" var="unit">
                                            <option value="${unit.areaId}">${unit.areaName}</option>
                                        </c:forEach>
                                    </select>--%>
                                        <input id="ChanYeName" type="text" class="form-control"  placeholder="请点击选择单位">
                                        <input id="unitId" type="hidden" /> <!--若需要节点id，将节点id存储在此，然后上传到服务器-->
                                        <div id="info" style="z-index: 1;position:absolute; width:320px; height:180px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                                            <ul id="haveclasstree" class="ztree"></ul>
                                        </div>
                                </div>&emsp;
                                <div class="btn-group m-t-sm">
                                    <input id="siIDNumber" name="siIDNumber" type="text" class="form-control"  placeholder="请输入身份证号">
                                </div>&emsp;
                                <div class="btn-group m-t-sm">
                                    <button id="search" type="button" class="btn btn-primary" onclick="seach()">查询</button>&emsp;
                                </div>
                               <%--<div class="btn-group m-t-sm">
                                    <button id="empty" type="button" class="btn">清空</button>
                                </div>--%>
                                <div class="btn-group m-t-sm">
                                    <button id="reset" type="button" class="btn" onclick="reset()">重置</button>
                                </div>
                            </div>

                            <!-- 数据表格 -->
                            <div class="col-sm-12" style="margin-top: 10px;">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <i class="fa fa-list"></i>&nbsp;学员信息列表
                                        </div>
                                        <div class="panel-body" style="padding-top: 0">
                                            <!-- 数据表格 -->
                                            <table id="table" class="table table-hover"></table>
                                        </div>
                                    </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>
    // 新建
    function add(){
        // alert("新建数据");
        location = "${ctx}/student/form.action";
    }
    // 编辑
    function edit() {
        var getSelectRows=$("#table").bootstrapTable('getSelections');
        ids = [];
        for (var i = 0; i < getSelectRows.length; i++) {
            ids.push(getSelectRows[i].siId)
        }
       commonEdit(ids,"${ctx}/student/form.action?id="+ids[0]);

    }
    // 批量删除数据
    function del() {
        var getSelectRows=$("#table").bootstrapTable('getSelections');
        ids = [];
        for (var i = 0; i < getSelectRows.length; i++) {
            ids.push(getSelectRows[i].siId)
        }
        commonDel(ids,"${ctx}/student/delete.action");
    }
  /*  $("#empty").click(function(){
        $("#unitId").val("");
    })*/

    //清空input
    function reset(){
        $("#ChanYeName").prop("value","");
        $("#unitId").val("");
        $("#siIDNumber").val("")
    }
</script>
</body>
</html>
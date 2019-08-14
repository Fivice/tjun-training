<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/teachDinRecord/teachDinRecord.js"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">
    <ul class="nav nav-tabs">
        <li ><a href="${ctx}/stuDining/view.action">学员就餐记录</a></li>
        <li class="active"><a href="${ctx}/teachDining/view.action"><i class="fa fa-list-alt"></i>教师就餐记录(教师证)</a></li>
        <li ><a href="${ctx}/teachFaceDining/view.action">教师就餐记录(人脸识别)</a></li>
    </ul>
            <div class="row">
                <div class="col-sm-12">
                        <div class="ibox-content">
                            <div class="row row-lg">
                                <div class="col-sm-12">
                                    <div class="btn-group m-t-sm">
                                        <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="schoolName"
                                                name="schoolName">
                                            <option value="">请选择就餐地点</option>
                                            <c:forEach items="${list}" var="list">
                                            <option value=${list}>${list}</option>
                                            </c:forEach>
                                            <%--<option value="其他">其他</option>--%>
                                        </select>
                                    </div>&emsp;
                                    <div class="btn-group m-t-sm">
                                        <input type="text" class="form-control" name="classRoom" id="classRoom"
                                               placeholder="请输入班级编号">
                                    </div>&emsp;
                                    <div class="btn-group m-t-sm">
                                        <input type="text" class="form-control" name="className" id="className"
                                               placeholder="请输入班级名称">
                                    </div>&emsp;
                                    <div class="btn-group m-t-sm">
                                        <input type="text" class="form-control" name="month" id="month" value="${month}"
                                               placeholder="请选择就餐月份">
                                    </div>&emsp;
                                    <div class="btn-group m-t-sm">
                                        <button id="find" type="button" class="btn btn-primary">查询</button>&emsp;
                                    </div>
                                    <div class="btn-group m-t-sm">
                                        <button id="reset" type="button" class="btn">清空</button>
                                    </div>
                                    <!-- 数据表格 -->
                                    <table id="table_teach_dining_record" class="table table-hover"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
</div>

<script>
    $("#find").click(function () {
        var schoolName = $("#schoolName").selected().val();
        var classRoom = $("#classRoom").val();
        var className = $("#className").val();
        var month = $("#month").val();
        $("#table_teach_dining_record").bootstrapTable('refresh');
    })
    $("#reset").click(function () {
        $("#schoolName").val("");
        $("#classRoom").val("");
        $("#className").val("");
        $("#month").val("");
    })


    laydate.render({
        elem: '#month'
        ,type: 'month'
        ,theme: '#6CA6CD'
        ,isInitValue: true
        /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });
</script>
</body>
</html>

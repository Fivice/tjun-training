<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>教师就餐管理</title>
    <script src="${ctxsta}/entity/teacherDining/teachDinProfile.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <!--[if lt IE 9]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <![endif]-->
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
<%--search-bar begin --%>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>教师就餐管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="btn-group m-t-sm form-inline">
                                <label class="control-label" for="startStopTime">月份：</label>
                                <input type="text" class="form-control" name="startStopTime" id="startStopTime" placeholder="请选择月份查询">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="searchContent" id="classNumber" placeholder="班级编号">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="searchContent" id="className" placeholder="班级名称">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="searchContent" id="teacherName" placeholder="班主任">
                            </div>

                            <div class="btn-group m-t-sm">
                                <button style="margin-bottom: 0px" type="button" class="btn btn-info" id="search" onclick="seach()"><i class="fa fa-search"></i>查询</button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button style="margin-bottom: 0px" id="reset" type="button" class="btn btn-default" onclick="reset()">清空</button>
                            </div>

                        </div>
                        <%--signUpManager-table begin--%>
                        <table id="table" class="table table-hover">

                        </table>
                        <%--signUpManager-table end--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--search-bar end--%>
</div>

</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>院校基本数据 </title>
    <script src="${ctxsta }/entity/basicParameters/basicParameters.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/basicParameters/bootstrapValidator.js" type="text/javascript"></script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">
    <form id="form" class="form-horizontal"  data-method="post">
        <c:forEach var="basicParametersList" items="${basicParametersList}">
        <div class="form-group m-t">
            <label class="col-sm-2 col-xs-offset-1 control-label">院校名称</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="name" value="${basicParametersList.name}" disabled>
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">系统外网网址</label>
            <div class="col-sm-7">
                <input type="text"  class="form-control" name="url" value="${basicParametersList.url}" disabled>
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">报名住宿地点<br>(逗号分隔)</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="address" value="${basicParametersList.address}" disabled>
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">就餐地点<br>(逗号分隔)</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="eatPlace" value="${basicParametersList.eatPlace}" disabled>
            </div>
        </div>


        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">早/中/晚就餐标准<br>(逗号分隔)</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="eatStandard" value="${basicParametersList.eatStandard}" disabled>
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">标间/单间住宿标准<br>(逗号分隔)</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="houseStandard" value="${basicParametersList.houseStandard}" disabled>
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <div class="col-sm-12 text-center">
                <a class="btn btn-primary" href="javascript:void(0)" onclick="update(${basicParametersList.id})" title="编辑">编辑 </a>
            </div>
        </div>
        </c:forEach>
    </form>
</div>
</div>
</body>
</html>
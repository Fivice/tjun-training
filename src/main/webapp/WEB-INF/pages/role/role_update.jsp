<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>级别更新</title>
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />

<style type="text/css">
.no_line {
	padding: 10px; 
	word-break:keep-all;
    white-space:nowrap;
    overflow:hidden;
    text-overflow:ellipsis;
}
.red_must {
    padding: 2px; 
	color: red;
}

</style>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">

        <div class="ibox-content">
          <form id="form" class="form-horizontal" action="${ctx}/role/${role.roleId}.action" data-method="put">
              <input type="hidden" name="roleId" id="roleId" value="${role.roleId}">
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">级别名称<span class= "red_must"  >*</span></label>
              <div class="col-sm-6">
                <input type="text" class="form-control" name="roleValue" value="${role.roleValue}">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">级别描述<span class= "red_must"  >*</span></label>
              <div class="col-sm-6">
                <input type="text" class="form-control" name="description" value="${role.description}">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
           <%-- <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">角色备注</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" name="roleValue" value="${role.roleValue}">
              </div>
            </div>--%>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <div class="col-sm-12 text-center">
                <button class="btn btn-primary" type="submit">提交</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
  <!-- 自定义js -->
  <script src="${ctxsta}/entity/role/role_update.js"></script> 
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE HTML>
<html>
<head>
<title>更新用户</title>
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

            <input type="hidden" id="contextPath" value="${contextPath }" />
        <div class="ibox-content">
          <form id="form" class="form-horizontal" action="${ctx}/sysuser/${user.userId}.action" method="put">
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">登录账号<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="loginAccount" value="${user.loginAccount}">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">真实姓名<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="userName" value="${user.userName}" >
              </div>
            </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
                  <label class="col-sm-2 col-xs-offset-1 control-label">用户类型<span class= "red_must"  >*</span></label>
                  <div class="col-sm-3">
                      <select id=userType1  name="supType" class="form-control" onchange="selectSubmit3();">
                         <%-- <option value="${user.supType}" > ${user.supType}</option>--%>
                          <option value="一般" <c:if test="${'一般' eq user.supType}">selected</c:if> >一般</option>
                          <option value="食堂" <c:if test="${'食堂' eq user.supType}">selected</c:if>>食堂</option>
                          <option value="前台" <c:if test="${'前台' eq user.supType}">selected</c:if>>前台</option>
                      </select>
                  </div>
                  <div class="col-sm-4">
                      <input type="text" hidden id="userTypeDetSelect" value="${user.userType}">
                      <select id=userTypeDet name="userType" class="form-control" >
                      </select>
                  </div>
              </div>
             <div class="hr-line-dashed"></div>
             <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">所属部门<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                             <select id=sjAera name="sjAera" class="form-control" onchange="selectSubmit();">
				                    <c:forEach items="${sjAera}" var="dep">
										 <option <c:if test="${dep.areaId eq d.areaId}">selected</c:if> value="${dep.areaId}" >${dep.areaName}</option>
									</c:forEach>


				              </select>
                             <%--<select id=xjAera name="xjAera" style="width:120px;height:30px" onchange="selectSubmit2();">
				                  <option value="${d2.areaId}">${d2.areaName}</option>
				                    <c:forEach items="${xjAera}" var="depy">
										 <option value="${depy.areaId}" >${depy.areaName}</option>
									</c:forEach>
				              </select>--%>
				                <input type="hidden" id="areaId" name="department.areaId" value="${user.department.areaId}">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">手机号</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="telephone" value="${user.telephone}" >
                <input type="hidden" class="form-control" name="loginPass" value="${user.loginPass}" >
                <input type="hidden" class="form-control" name="registerTime" value="${user.registerTime}" >
              </div>
            </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
                  <label class="col-sm-2 col-xs-offset-1 control-label">邮箱</label>
                  <div class="col-sm-7">
                      <input type="text" class="form-control" name="email" value="${user.email}">
                  </div>
              </div>
              <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">性别<span class= "red_must"  >*</span></label>
              <div class="col-sm-7 ">
                <label class="radio-inline">
                  <input type="radio" name="sex" value="1" ${user.sex eq '1'?'checked="checked"':''}>
                  男</label>
                <label class="radio-inline">
                  <input type="radio" name="sex" value="2" ${user.sex eq '2'?'checked="checked"':''}>
                  女</label>
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">状态<span class= "red_must"  >*</span></label>
              <div class="col-sm-7 ">
                <label class="radio-inline">
                  <input type="radio" name="state" value="1" ${user.state eq '1'?'checked="checked"':''}>
                  正常</label>
                <label class="radio-inline">
                  <input type="radio" name="state" value="0" ${user.state eq '0'?'checked="checked"':''}>
                  冻结</label>
              </div>
            </div>
         <%--    <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">权限：</label>
              <div class="col-sm-9">
                <c:forEach items="${roles}" var="role">
                  <div class="checkbox col-sm-3">
                    <label>
                      <input type="checkbox" name="roleId" value="${role.roleId}" <c:forEach items="${userRoles}" var="userRole">${role.roleId eq userRole.roleId ?'checked="checked"':''}</c:forEach>/>
                      &nbsp;&nbsp;${role.roleName} </label>
                  </div>
                </c:forEach>
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">归属部门：</label>
              <div class="col-sm-6">
                <select class="form-control" name="organizationId">
                  <option value="">--请选择--</option>
                  <c:forEach items="${organizations }" var="organization">
                    <option value="${organization.organizationId }" ${user.organizationId eq organization.organizationId ?'selected="selected"':''}>${organization.organizationName }</option>
                  </c:forEach>
                </select>
              </div>
            </div> --%>
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
	<!-- 全局js -->
<script  src="${contextPath }/static/common/jquery/jquery.min.js"></script>
	<script src="${ctxsta}/common/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctxsta}/common/metismenu/metisMenu.min.js"></script>
	<script src="${ctxsta}/common/slimscroll/jquery.slimscroll.min.js"></script>
<script  src="${contextPath }/static/common/jquery/jquery.validate.min.js" ></script>
	<!-- layer弹出框js -->
    <script src="${ctxsta}/common/layer/layer.js"></script>
  	<!-- bootstrapvalidator-master前端验证框架 -->
  	<script src="${ctxsta}/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
	<!-- 自定义js -->
	<script src="${ctxsta}/cms/js/hplus.js"></script>
	<script src="${ctxsta}/cms/js/contabs.js"></script>
	<script src="${ctxsta}/cms/js/content.js"></script>
  
    <!-- Bootstrap table --> 
  <script src="${ctxsta}/common/bootstrap-table/bootstrap-table.min.js"></script> 
  <script src="${ctxsta}/common/bootstrap-table/extensions/export/bootstrap-table-export.js"></script> 
  <script src="${ctxsta}/common/bootstrap-table/tableExport.js"></script> 
  <script src="${ctxsta}/common/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script> 
  <!-- 自定义js --> 
  <script src="${ctxsta}/entity/user/user_update.js"></script>
</body>
</html>

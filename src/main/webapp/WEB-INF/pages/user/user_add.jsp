<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML>
<html>
<head>
<title>创建员工</title>
	<link rel="shortcut icon" href="${contextPath}/static/uploads/default/ico/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${contextPath}/static/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${contextPath}/static/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${contextPath}/static/cms/css/animate.css" />
	<link rel="stylesheet" href="${contextPath}/static/cms/css/style.css" />
	<link href="${contextPath }/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath }/static/bootstrap/css/font-awesome.min.css" rel="stylesheet">
<link href="${contextPath }/static/alert/jquery-confirm.min.css" rel="stylesheet">
	<script> var t1 = new Date().getTime(); baselocation='${ctximg}'; imagelocation='${ctximg}/uploads';</script>
<link rel="stylesheet" href="${contextPath}/static/common/icheck/flat/green.css" />

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
          <form id="form" class="form-horizontal"   data-method="post">
          <input type="hidden" id="contextPath" value="${contextPath }" />
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">登录账号<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="loginAccount" id="loginAccount">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">登录密码<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <input type="password" class="form-control" name="loginPass" id="loginPass">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">确认密码<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <input type="password" class="form-control" name="rloginPass">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">真实姓名<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="userName">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">用户类型<span class= "red_must"  >*</span></label>
                <div class="col-sm-3">
                <select id=userType1  class="form-control" onchange="selectSubmit3();" name="supType">
                  <option value=""> --请选择--</option>
                  <option value="一般" >一般</option>
                  <option value="食堂" >食堂</option>
                  <option value="前台" >前台</option>
                </select>
                </div>
              <div class="col-sm-4">
                <select id=userTypeDet class="form-control"  name="userType">
                  <option value=""> --请选择具体类型--</option>
                </select>
              </div>
            </div>
             <div class="hr-line-dashed"></div>
             <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">所属部门<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                             <select id=sjAera name="sjAera"  class="form-control" onchange="selectSubmit();">
				                  <option value=""> --请选择--</option>
				                    <c:forEach items="${sjAera}" var="dep">
										 <option value="${dep.areaId}" >${dep.areaName}</option>
									</c:forEach>
				              </select>
                             <%--<select id=xjAera name="xjAera" style="width:150px;height:30px" onchange="selectSubmit2();">
				                  <option value=""> --请选择--</option>
				                    <c:forEach items="${xjAera}" var="depy">
										 <option value="${depy.areaId}" >${depy.areaName}</option>
									</c:forEach>
				              </select>--%>
				              <input type="hidden" id="areaId" name="department.areaId" value="">
              </div>
            </div>
               <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">级别（权限）<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <select class="form-control" id=role name = "role">
                  <option value="">--请选择--</option>
                  <c:forEach items="${role}" var="role">
                    <option value="${role.roleId}" >${role.roleValue}</option>
                  </c:forEach>
                </select>
              </div>
              </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">手机号码</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="telephone">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">邮箱</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="email">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">状态<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
                <label class="radio-inline">
                  <input type="radio" name="state" value="1" checked="checked"> 正常</label>
                <label class="radio-inline">
                  <input type="radio" name="state" value="0"> 冻结</label>
              </div>
            </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">性别<span class= "red_must"  >*</span></label>
              <div class="col-sm-7">
               <label class="radio-inline">
                  <input type="radio" name="sex" value="1" checked="checked"> 男</label>
                <label class="radio-inline">
                  <input type="radio" name="sex" value="2"> 女</label>
              </div>
            </div>
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
  <!-- bootstrapvalidator-master前端验证框架 --> 
  <!-- 自定义js --> 
 <script type="text/javascript" src="${contextPath }/static/common/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${contextPath }/static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${contextPath }/static/alert/jquery-confirm.min.js" ></script>
	<script type="text/javascript" src="${contextPath }/static/common/jquery/jquery.validate.min.js" ></script>
<script src="${contextPath}/static/cms/js/users/user.js"></script>

</body>
</html>

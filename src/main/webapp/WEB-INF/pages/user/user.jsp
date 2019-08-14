<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML>
<html>
<head>
    <title>用户管理 </title>
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
	<link rel="stylesheet" href="${ctxsta}/common/treeTable/themes/vsStyle/treeTable.min.css" />

  </head>

<body class="gray-bg" >
<div id="cc">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>用户列表</h5>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
            <input type="hidden" id="contextPath" value="${contextPath}" />
            <div class="btn-group m-t-sm">
            	 <button type="button" class="btn btn-default"  title="创建用户" onclick="layer_show('创建用户','${ctx}/sysuser/add/view.action','900','480')"> <i class="glyphicon glyphicon-plus"></i> </button>
              </div>
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="编辑" onclick="user_edit()"> <i class="glyphicon glyphicon-pencil"></i> </button>
              </div>
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="删除" onclick="user_delete()" id="del"> <i class="glyphicon glyphicon-minus"></i> </button>
              </div>
               <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="分配权限" onclick="assign_roles()" id="assignRoles">分配权限 </button>
              </div>
                <div class="btn-group m-t-sm">
                    <input type="text" class="form-control" name="loginAccount" id="loginAccount" placeholder="请输入登录账号">
                </div>
                <div class="btn-group m-t-sm">
                    <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入真实姓名">
                </div>
                <div class="btn-group m-t-sm">
                    <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="departmentName" name="departmentName">
                        <option value="">请选择部门名称</option>
                        <c:forEach items="${sjAera}" var="sjAera">
                            <option value=${sjAera.areaId}>${sjAera.areaName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="btn-group m-t-sm">
                    <button type="button" class="btn btn-info" id="search"><i class="fa fa-search"></i>查询</button>
                </div>
                <div class="btn-group m-t-sm">
                    <button id="reset" type="button" class="btn" onclick="reset()">清空</button>
                </div>
              <table id="table_sysUser" class="table table-bordered table-striped"></table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
    <ul class="pagination" id="pageLimit"></ul>
    <div class="loadPageDataSelector"></div>

	<script src="${contextPath }/static/entity/user/sysUser.js"></script>

<script>

    $("#search").click(function() {
        $("#table_sysUser").bootstrapTable('refresh');
    })
    //清空input
    function reset(){
        $("input").prop("value","");
        $("#departmentName").prop("value","");
    }
</script>
</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE HTML>
<html>
<head>
    <title>部门管理 </title>
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />

<link rel="stylesheet" href="${ctxsta}/common/treeTable/themes/vsStyle/treeTable.min.css" />


  </head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>部门管理</h5>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="刷新列表" onclick="javascript:window.location.reload()"> <i class="glyphicon glyphicon-refresh"></i> </button>
              </div>
                <input type="hidden" id="contextPath" value="${contextPath}" />
                <table id="treeTable" class="table table-bordered table-striped">
                <thead>
                  <tr>
                    <th>名称</th>
                    <th>排序</th>
                   <%-- <th>类型</th>--%>
                    <th class="text-center">操作</th>
                  </tr>
                </thead>
                <tbody>
                   <c:forEach items="${departments}" var="department">
                    <tr id="${department.areaId}" pId="${department.sjareaId ne '0' ? department.sjareaId:''}">
                      <td class="${department.sjareaId eq '0'?'areaName':''}"><i class="fa fa- m-l-xs m-r-xs"></i>${department.areaName}</td>
                      <td>${department.sort }</td>
                     <%-- <td>${department.areaType }</td>--%>
                      <td class="td-manage text-center">
                      <a class="edit m-l-sm text-warning" href="javascript:void(0)" onclick="layer_show('编辑部门','${ctx}/department/${department.areaId}/edit.action','900','500')" title="编辑"> <i class="glyphicon glyphicon-edit"></i> </a>
                      		<c:choose>
                      		<c:when test="${department.areaType != 1 }">
                                <a class="remove m-l-sm text-danger" href="javascript:void(0)" onclick="department_delete(this,'${department.areaId}')" title="删除" id = "del"> <i class="glyphicon glyphicon-remove"></i> </a>
                      		</c:when>
                      		<c:otherwise></c:otherwise>
                      		</c:choose>
                      		<c:choose>
                      		<c:when test="${department.areaType != 4 }">
                      		<a class="remove m-l-sm text-primary" href="javascript:void(0)" onclick="layer_show('创建部门','${ctx}/department/${department.areaId}/create.action','900','500')" title="添加下级菜单"> <i class="glyphicon glyphicon-sort-by-attributes-alt"></i> </a>
                      		</c:when>
                      		<c:otherwise>
                      		</c:otherwise>
                      		</c:choose>
                      		
                       </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- 全局js -->
	<script src="${ctxsta}/common/jquery/jquery-3.2.0.min.js"></script>
	<script src="${ctxsta}/common/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctxsta}/common/metismenu/metisMenu.min.js"></script>
	<script src="${ctxsta}/common/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- layer弹出框js -->
    <script src="${ctxsta}/common/layer/layer.js"></script>
    <!-- iCheck --> 
  	<script src="${ctxsta}/common/icheck/icheck.min.js"></script> 
  	<!-- bootstrapvalidator-master前端验证框架 --> 
  	<script src="${ctxsta}/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script> 
	<!-- 自定义js -->
	<script src="${ctxsta}/cms/js/hplus.js"></script>
	<script src="${ctxsta}/cms/js/contabs.js"></script>
	<script src="${ctxsta}/cms/js/content.js"></script>
	
  <script src="${ctxsta}/common/treeTable/jquery.treeTable.min.js"></script> 
  <script src="${ctxsta}/cms/js/department/departmentlist.js"></script>
</body>
</html>
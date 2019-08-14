<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>菜单管理 </title>
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
          <h5>菜单列表</h5>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
              <div class="btn-group m-t-sm">
               <input type="hidden" id="contextPath" value="${ctx}" />
                <button type="button" class="btn btn-default"  title="创建菜单" onclick="layer_show('创建菜单','${ctx}/system/menu/1/create.action','900','480')"> <i class="glyphicon glyphicon-plus"></i> </button>
                <button type="button" class="btn btn-default"  title="刷新列表" onclick="javascript:window.location.reload()"> <i class="glyphicon glyphicon-refresh"></i> </button>
              </div>
              <table id="treeTable" class="table table-bordered table-striped">
                <thead>
                  <tr>
                    <th>菜单名称</th>
                   <!--  <th>权限代码</th> -->
                    <th>链接地址</th>
                    <th class="text-center">排序</th>
                    <th class="text-center">状态</th>
                    <!-- <th class="text-center">权限标识</th> -->
                    <th class="text-center">操作</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${menus}" var="menu">
                    <tr id="${menu.menuId}" pId="${menu.parentId ne '1' ? menu.parentId:'0'}">
                      <td class="${menu.parentId eq '1'?'menuName':''}"><i class="fa fa-${not empty menu.icon ? menu.icon:' hide'} m-l-xs m-r-xs"></i>${menu.menuName}</td>
                      <%-- <td>${menu.menuCode}</td> --%>
                      <td>${menu.href}</td>
                      <td class="text-center">${menu.sort}</td>
                      <td class="td-status text-center"><span class="label ${menu.status eq '1'?'label-primary':'label-danger'}">${menu.status eq '1'?'显示':'隐藏'}</span></td>
                     <%--  <td class="text-center">${menu.permission}</td> --%>
                      <td class="td-manage text-center">
                          <c:if test="${menu.status eq 1}"><a class="like text-info" href="javascript:void(0)" onClick="status_stop(this,'${menu.menuId}')" title="隐藏"><i class="glyphicon glyphicon-pause"></i></a></c:if>
                          <c:if test="${menu.status eq 0}"><a class="like text-info" href="javascript:void(0)" onClick="status_start(this,'${menu.menuId}')" title="显示"><i class="glyphicon glyphicon-play"></i></a></c:if>
                        <a class="edit m-l-sm text-warning" href="javascript:void(0)" onclick="layer_show('编辑菜单','${ctx}/system/menu/${menu.menuId}/edit.action','900','500')" title="编辑"> <i class="glyphicon glyphicon-edit"></i> </a> 
                       <a class="remove m-l-sm text-danger" href="javascript:void(0)" onclick="menu_delete(this,'${menu.menuId}')" title="删除"> <i class="glyphicon glyphicon-remove"></i> </a> 
                          <c:if test="${menu.menuType != 0}"> <a class="remove m-l-sm text-primary" href="javascript:void(0)" onclick="layer_show('创建菜单','${ctx}/system/menu/${menu.menuId}/create.action','900','500')" title="添加下级菜单"> <i class="glyphicon glyphicon-sort-by-attributes-alt"></i> </a></c:if>
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
  <script src="${ctxsta}/cms/js/systemMenuList.js"></script> 
</body>
</html>
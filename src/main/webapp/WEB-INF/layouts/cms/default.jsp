<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<!DOCTYPE html>
<html>
  <head>
    <title>培训服务管理系统</title>
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
	<link rel="stylesheet" href="${ctxsta}/common/treeTable/themes/vsStyle/treeTable.min.css" />
	<!-- 相关插件 -->
	<link rel="stylesheet" href="${ctxsta}/bootstrap/css/jquery.searchableSelect.css" />
	<link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap-submenu.min.css" />
	<link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap-treeview.css" />
	<link rel="stylesheet" href="${ctxsta}/bootstrap/table/bootstrap-table.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
	  <link rel="stylesheet" href="${ctxsta}/common/bootstrap-table/extensions/treegrid/css/jquery.treegrid.css"/>

	  <script> var t1 = new Date().getTime(); baselocation='${ctx}'; imagelocation='${ctximg}';</script>
	  <%--<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />--%>

	  <%--<link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap-select.min.css" />
      <link rel="stylesheet" href="${ctxsta}/commonProp/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" /><--%>
      <%--<link rel="stylesheet" href="${ctxsta}/common/bootstrap-switch/css/bootstrap-switch.css" />--%>
	<link rel="stylesheet" href="${ctxsta}/common/ztree/css/bootstrapStyle/bootstrapStyle.css" />
    <link rel="stylesheet" href="${ctxsta}/common/layui/layui/css/layui.css" />

	
	<sitemesh:write property='head' />
  </head>
  <body class="fixed-sidebar full-height-layout gray-bg">
  <input type="hidden" id="contextPath" value="<%=projectPath%>" />
  	<sitemesh:write property='body' />
  	<!-- 全局js -->
	<script src="${ctxsta}/common/jquery/jquery-3.2.0.min.js"></script>
	<script src="${ctxsta}/common/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctxsta}/common/metismenu/metisMenu.min.js"></script>
	<script src="${ctxsta}/common/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctxsta}/cms/js/common.js"></script>

	<!-- layer弹出框js -->
    <script src="${ctxsta}/common/layer/layer.js"></script>

    <!-- iCheck --> 
  	<%--<script src="${ctxsta}/common/icheck/icheck.js"></script>--%>
  	<!-- bootstrapvalidator-master前端验证框架 --> 
  	<script src="${ctxsta}/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
	<!-- 自定义js -->
	<script src="${ctxsta}/cms/js/hplus.js"></script>
	<script src="${ctxsta}/cms/js/contabs.js"></script>
	<script src="${ctxsta}/cms/js/content.js"></script>
	<script src="${ctxsta}/cms/js/systemMenuList.js"></script>
	<script src="${ctxsta}/common/treeTable/jquery.treeTable.min.js"></script>
	<!-- 相关插件 -->
	<script src="${ctxsta}/bootstrap/js/bootstrap-select.min.js"></script>
	<script src="${ctxsta}/bootstrap/js/jquery.searchableSelect.js"></script>
	<script src="${ctxsta}/bootstrap/js/bootstrap-submenu.min.js"></script>
	<script src="${ctxsta}/bootstrap/js/bootstrap-treeview.js"></script>
	<script src="${ctxsta}/bootstrap/table/bootstrap-table.js" type="text/javascript"></script>
    <script src="${ctxsta}/bootstrap/table/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
	<script src="${ctxsta}/common/bootstrap-table/extensions/filter-control/bootstrap-table-filter-control.js"></script>
  <script src="${ctxsta}/common/jquery/chosen.jquery.js"></script>
  <script src="${ctxsta }/common/bootstrap-table/extensions/treegrid/js/jquery.cookie.js" type="text/javascript"></script>
  <script src="${ctxsta }/common/bootstrap-table/extensions/treegrid/js/jquery.treegrid.js" type="text/javascript"></script>
	
	<!-- rili -->
	<%--<script src="${ctxsta}/commonProp/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script src="${ctxsta}/commonProp/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>--%>
	
	<!-- dayin -->
	<%--<script src="${ctxsta}/common/jquery/jquery.jqprint-0.3.js"></script>
    <script src="${ctxsta}/common/jquery/jquery-migrate-1.0.0.js"></script>--%>


	<!-- daochu -->
	<script src="${ctxsta}/common/bootstrap-table/extensions/export/Blob.min.js"></script>
    <script src="${ctxsta}/common/bootstrap-table/extensions/export/xlsx.core.min.js"></script>
    <script src="${ctxsta}/common/bootstrap-table/extensions/export/FileSaver.min.js"></script> 
    <script src="${ctxsta}/common/bootstrap-table/extensions/export/tableExport.js"></script>  
    <script src="${ctxsta}/common/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>  
	
	<script src="${ctxsta}/common/jquery/jquery.form.js"></script>
	<%--<script src="${ctxsta}/commonProp/jquery/echarts.js" type="text/javascript"></script>--%>
	<%--<script src="${ctxsta}/common/bootstrap-switch/js/bootstrap-switch.js"></script>--%>
	
    <script type="text/javascript" src="${ctxsta}/common/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctxsta}/common/ztree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="${ctxsta}/common/ztree/js/jquery.ztree.exedit.js"></script>
   <%--qrcode--%>
   <script src="${ctxsta}/common/qrcode/qrcode.min.js"></script>
   <script src="${ctxsta}/common/qrcode/qrcode.js"></script>
	<sitemesh:write property='myfooter' />
  </body>
</html>

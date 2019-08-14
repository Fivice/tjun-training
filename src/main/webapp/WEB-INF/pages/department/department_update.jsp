<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>编辑页面</title>
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <%--<div class="ibox-title">--%>
          <%--<h5>编辑部门</h5>--%>
          <%--<div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>--%>
        <%--</div>--%>
        <div class="ibox-content">
          <form id="form" class="form-horizontal" action="${ctx}/department/${department.areaId}.action" data-method="put">
          <c:choose>  
    		<c:when test="${department.areaType == 1 }"> 
    			 <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 单位名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName" value="${department.areaName }">
              </div>
            </div>
    		</c:when>
    		<c:when test="${department.areaType == 2 }">
    		 <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 单位名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName" disabled="" placeholder="${sjDepartment.areaName }">
              </div>
            </div>
    		 <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 部门名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName" value="${department.areaName }">
              </div>
            </div>
    		</c:when>
    		<c:when test="${department.areaType == 3 }">
    		 <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 上级部门名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName" disabled="" placeholder="${sjDepartment.areaName }">
              </div>
            </div>
    		 <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 下级部门名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName" value="${department.areaName }">
              </div>
            </div>
    		</c:when>
    		<c:otherwise>
    		 <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 上级部门名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName"  disabled="" placeholder="${sjDepartment.areaName }">
              </div>
            </div>
    			 <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 下级部门名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName" value="${department.areaName }">
              </div>
            </div>
    		</c:otherwise>
    		</c:choose>
           <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 排序</label>
              <div class="col-sm-7">
                <input type="hidden" class="form-control" name="areaType" value="${department.areaType }">
                <input type="hidden" class="form-control" name="sjareaId" value="${department.sjareaId }">
                <input type="text" class="form-control" name="sort" value="${department.sort }">
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
	<script src="${ctxsta}/cms/js/content.js"></script>
  <!-- iCheck --> 
  <script src="${ctxsta}/common/icheck/icheck.min.js"></script> 
  <!-- bootstrapvalidator-master前端验证框架 --> 
  <script src="${ctxsta}/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script> 
  <!-- 自定义js --> 
  <script src="${ctxsta}/cms/js/department/department.js"></script> 
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML>
<html>
<head>
<title>基本培训维护</title>
<link rel="shortcut icon" href="${ctximg}/uploads/default/ico/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
	<script> var t1 = new Date().getTime(); baselocation='${ctximg}'; imagelocation='${ctximg}/uploads';</script>

<link rel="stylesheet" href="${ctxsta}/common/treeTable/themes/vsStyle/treeTable.min.css" />

</head>
<body class="gray-bg" >
<div id="cc">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>基本培训维护</h5>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
            <input type="hidden" id="contextPath" value="${contextPath }" />
              <div class="btn-group m-t-sm">
            	 <button type="button" class="btn btn-default"  title="创建" onclick="layer_show('添加培训类型','${ctx}/training/add/view.action','900','480')"> <i class="glyphicon glyphicon-plus"></i> </button>
              </div>
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="编辑" onclick="training_edit()"> <i class="glyphicon glyphicon-pencil"></i> </button>
              </div>
       <%--       <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="删除" onclick="training_delete()" id="del"> <i class="glyphicon glyphicon-minus"></i> </button>
              </div>--%>
              <table id="table_training_type" class="table table-bordered table-striped"></table>
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
	<script src="${contextPath }/static/jquery/jquery.min.js"></script>
	<script src="${contextPath }/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${contextPath }/static/bootstrap/table/bootstrap-table.js"></script>
	<script src="${contextPath }/static/bootstrap/table/locale/bootstrap-table-zh-CN.js"></script>
	<script src="${ctxsta}/common/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
	<script src="${ctxsta}/common/bootstrap-table/tableExport.js"></script>
	<script src="${contextPath }/static/cms/js/trainingType/trainingType.js"></script>
	<script src="${ctxsta}/common/layer/layer.js"></script>
	  	<!-- bootstrapvalidator-master前端验证框架 --> 
  	<script src="${ctxsta}/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script> 
	<!-- 自定义js -->
	<script src="${ctxsta}/cms/js/content.js"></script>
<script type="text/javascript">
</script>
</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>添加</title>
	<link rel="shortcut icon" href="${ctximg}/uploads/default/ico/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
	<script> var t1 = new Date().getTime(); baselocation='${ctximg}'; imagelocation='${ctximg}/uploads';</script>
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
          <form id="form" class="form-horizontal" action="${ctx}/training/save.action" data-method="post">
          
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">培训类型<span class= "red_must"  >*</span></label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="type" id="type" onblur="check()">
              </div>&nbsp;&nbsp;&nbsp;
                <div class="col-sm-4 " id = "span">
                    <span id ="span_id" style="color:red"></span>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">注释</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="tExplan">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">备注</label>
              <div class="col-sm-4">
                <input type="text"  class="form-control" name="remark">
              </div>
            </div>

            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <div class="col-sm-12 text-center">
                <button id="submit" class="btn btn-primary" type="submit">提交</button>
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
  <script src="${ctxsta}/cms/js/trainingType/trainingType_update.js"></script>
<script type="text/javascript">
    function check() {
        var type = $("#type").val();
        $.ajax({
            type: 'POST',
            dataType: 'json',
            data: {
                type: type
            },
            url: ctx + '/training/checkType.action',
            success: function (result) {
                if(result.typeId == 0){
                    $("#span_id").attr("style","color:green;");
                    $("#span_id").text("该培训类型不存在，请添加") ;
                    $("#submit").show();
                }else if(result.typeId == 1){
                    $("#span_id").attr("style","color:red;");
                    $("#span_id").text('该培训类型已存在') ;
                    $("#submit").hide();
                }else {
                    $("#span_id").attr("style","color:red;");
                    $("#span_id").text('该培训类型不能为空') ;
                    $("#submit").hide();
                }
            }
        })
    }
</script>
</body>
</html>

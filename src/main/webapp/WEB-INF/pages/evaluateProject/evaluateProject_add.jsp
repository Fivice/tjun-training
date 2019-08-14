<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
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
    #p{
        display: none;
    }

</style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <form id="form" class="form-horizontal" action="${ctx}/evaluate/save.action" data-method="post">
              <div class="form-group m-t">
                  <label class="col-sm-2 col-xs-offset-1 control-label">评价分类选项<span class= "red_must"  >*</span></label>
                  <div class="col-sm-7">
                      <select id="sll" class="selectpicker" name="largeClass" style="width:150px;height:35px;"
                              onchange="select(this.value)">
                          <option value="1"> 培训总体评价</option>
                          <option value="2"> 培训师授课情况</option>
                          <option value="3"> 教学组织与管理水平</option>
                          <option value="4"> 课程设置评价</option>
                          <option value="5"> 班主任评价</option>
                          <option value="6"> 综合服务评价</option>
                          <option value="7"> 培训的意见和建议</option>
                          <option value="8"> 课程或授课师资</option>
                      </select>
                  </div>
              </div>
              <div class="form-group m-t" id="p">
                  <label class="col-sm-2 col-xs-offset-1 control-label">评价分类<span class= "red_must"  >*</span></label>
                  <div class="col-sm-7">
                      <input type="text" class="form-control" id="myText" name="type" readonly="readonly">
                  </div>
              </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
                  <label class="col-sm-2 col-xs-offset-1 control-label">满分分值<span class= "red_must"  >*</span></label>
                  <div class="col-sm-7">
                      <input type="text" class="form-control" name="score" placeholder="请输入数字">
                  </div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group m-t">
                  <label class="col-sm-2 col-xs-offset-1 control-label">评价项目</label>
                  <div class="col-sm-7">
                      <input type="text" class="form-control" name="project">
                  </div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group">
                  <label class="col-sm-2 col-xs-offset-1 control-label">备注</label>
              <div class="col-sm-7">
                <input type="text"  class="form-control" name="eRemark">
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
  <script src="${ctxsta}/cms/js/evaluateProject/evaluateProject_update.js"></script>
<script>
   function select(value) {
       var largeClass = $("#sll option:selected").val();
       $("#myText").val($("#sll option:selected").text());
   }
</script>
</body>
</html>

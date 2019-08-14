<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
  <script src="${ctxsta }/entity/classFunds/bootstrpValidator.js" type="text/javascript"></script>
<title>添加教室</title>
</head>
<body>
  <div class="ibox-content">
    <form id="form" class="form-horizontal" action="${ctx}/classFunds/saveOrUpdate.action" method="post" >
        <input class="form-control"  name="id" type="hidden" value="${recordChange.id}"/>
        <input class="form-control"  name="classId" type="hidden" value="${recordChange.classId}"/>
      <div class="hr-line-dashed"></div>
      <div class="form-group" style="text-align:center;">
        <label style="color: #00B83F">[${classInfoList.classNumber}]${classInfoList.className}</label>
      </div>
          <div class="hr-line-dashed"></div>
          <div class="form-group" style="text-align:center;">
            <label  >主办：${classInfoList.hostUnit}<br>[班主任：${classInfoList.teacherName}]</label>
          </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">统收培训费合计：</label>
        <div class="col-sm-7">
          <input type="text" class="form-control" name="trainingFeeCollection" value="${recordChange.trainingFeeCollection}">
        </div>
      </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">统收住宿费合计：</label>
        <div class="col-sm-7">
          <input type="text" class="form-control" name="hotelExpenseCollection" value="${recordChange.hotelExpenseCollection}">
        </div>
      </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">统收就餐费合计：</label>
        <div class="col-sm-7">
          <input type="text" class="form-control" name="diningFeeCollection" value="${recordChange.diningFeeCollection}">
        </div>
      </div>
      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">统收其他费用合计：</label>
        <div class="col-sm-7">
          <input type="text" class="form-control" name="otherExpensesCollection" value="${recordChange.otherExpensesCollection}">
        </div>
      </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">统收费用说明：</label>
        <div class="col-sm-7">
          <textarea class="form-control" rows="2" name="explainCollection" >${recordChange.explainCollection}</textarea>
        </div>
      </div>
          <div class="hr-line-dashed"></div>
          <div class="form-group" style="text-align: center">
                <button type="submit" class="btn btn-sm btn-primary" id="btnSubmit"><i class="fa fa-check"></i> 保 存</button>&nbsp;
                <button type="button" class="btn btn-sm btn-default" id="btnCancel" onclick="history.go(-1)"><i class="fa fa-reply-all"></i> 关 闭</button>
            </div>


    </form>
  </div>
</body>
</html>

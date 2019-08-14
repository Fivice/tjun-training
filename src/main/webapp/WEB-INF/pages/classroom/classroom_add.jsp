<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>添加教室</title>
  <script src="${ctxsta }/entity/classroom/bootstrapValidator1.js" type="text/javascript"></script>
</head>
<body>
  <div class="ibox-content">
    <form id="form" class="form-horizontal"  data-method="post">


      <div class="form-group m-t">
        <label class="col-sm-2 col-xs-offset-1 control-label">
          <span class="required" aria-required="true" style="color: #af0000" >*</span> 校区名称：</label>
          <div class="col-sm-7">
            <select class="form-control" id="sName" name="schoolName">
              <c:forEach items="${campusList}" var="campusList">
                <option value=${campusList.id} >${campusList.schoolName}</option>
              </c:forEach>
            </select>
          </div>
      </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">
          <span class="required" aria-required="true" style="color: #af0000">*</span> 教室名称：</label>
        <div class="col-sm-7">
          <input type="text" class="form-control" name="className" id="className">
        </div>
      </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">
          <span class="required" aria-required="true" style="color: #af0000">*</span> 教室类别：</label>
        <div class="col-sm-7">
            <select class="form-control"  name="classroomType" id="classroomType" >
                <option value='1' >普通教室</option>
                <option value='2' >机房</option>
            </select>
        </div>
      </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">
          <span class="required" aria-required="true" style="color: #af0000">*</span> 教室容量：</label>
        <div class="col-sm-7">
          <input type="text" class="form-control" name="capacity" id="capacity">
        </div>
      </div>

      <div class="hr-line-dashed"></div>
      <div class="form-group">
        <label class="col-sm-2 col-xs-offset-1 control-label">备注信息：</label>
        <div class="col-sm-7">
          <textarea class="form-control" rows="2" name="remarks" ></textarea>
        </div>
      </div>

    </form>
  </div>

<script>

</script>
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>编辑</title>
</head>
<body class="gray-bg">
<div class="ibox-content">
    <form id="form" class="form-horizontal"  data-method="post">

        <input type="hidden" id="cid" name="id" value="${classroom.id}" class="form-control">
        <input type="hidden" class="form-control" name="state" value="${classroom.state}">
        <input type="hidden" class="form-control" name="aState" value="${classroom.aState}">

        <div class="form-group m-t">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 校区名称：</label>
            <div class="col-sm-7">
                <select class="form-control" id="sName" name="schoolName">
                    <c:forEach items="${campusList}" var="campusList">
                        <option <c:if test="${classroom.schoolName eq campusList.id}">selected</c:if> value=${campusList.id} >${campusList.schoolName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 教室名称：</label>
            <div class="col-sm-7">
                <input type="text"  class="form-control" name="className" value="${classroom.className}">
            </div>
        </div>


        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 教室类别：</label>
            <div class="col-sm-7">
                <select class="form-control"  name="classroomType">
                    <option <c:if test="${classroom.classroomType eq '1'}">selected</c:if> value='1' >普通教室</option>
                    <option <c:if test="${classroom.classroomType eq '2'}">selected</c:if> value='2' >机房</option>
                </select>
            </div>
        </div>



        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 容量：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="capacity" value="${classroom.capacity}">
            </div>
        </div>

        <%--<div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">排序：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="sort" value="${classroom.sort}">
            </div>
        </div>--%>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">备注信息：</label>
            <div class="col-sm-7">
                <textarea class="form-control" rows="2" name="remarks">${classroom.remarks}</textarea>
            </div>
        </div>

    </form>
</div>
</body>
</html>

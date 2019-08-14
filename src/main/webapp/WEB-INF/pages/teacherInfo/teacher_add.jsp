<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>创建教师信息</title>

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

<body >
                <div class="ibox-content">
                    <form id="form" class="form-horizontal"  data-method="post">
                        <div class="form-group m-t">
                            <label class="col-sm-2 control-label">姓名<span
                                    class="red_must">*</span></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="form-group m-t">
                            <label class="col-sm-2 control-label">电话号码<span
                                    class="red_must">*</span></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                                       placeholder="请输入电话号码">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="form-group m-t">
                            <label class="col-sm-2 control-label">工作单位<span
                                    class="red_must">*</span></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="tiDepartment" name="tiDepartment"
                                       placeholder="请输入工作单位">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 control-label">授课科目<span
                                    class="red_must">*</span></label>
                            <div class="col-sm-8">
                                <%--<select class="form-control" name="trainingSubject.tsId" >
                                    <c:forEach items="${ts}" var="ts">
                                        <option value="${ts.tsId}" >${ts.tsName}</option>
                                    </c:forEach>
                                </select>--%>
                                <input type="text" class="form-control" id="subject" name="subject"
                                       placeholder="请输入授课科目">
                            </div>
                        </div>
                    </form>
                </div>

</body>
</html>

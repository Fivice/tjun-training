<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改培训类型信息</title>

    <style type="text/css">
        .no_line {
            padding: 10px;
            word-break: keep-all;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .red_must {
            padding: 2px;
            color: red;
        }

    </style>

</head>

<body class="gray-bg">
                <div class="ibox-content">
                    <form id="form2" class="form-horizontal" data-method="post">

                        <input type="hidden" name="id" value="${teachDining.id}">
                        <input type="hidden" name="number" value="${teachDining.number}">
                        <input type="hidden" name="day" value="${teachDining.day}">
                        <input type="hidden" name="teacherName" value="${teachDining.teacherName}">
                        <input type="hidden" name="arranger" value="${teachDining.arranger}">
                        <input type="hidden" name="classId" value="${teachDining.classId}">
                        <input type="hidden" name="area" value="${teachDining.area}">
                        <input type="hidden" name="time" value="${teachDining.time}">


                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">早餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="breakfast">
                                    <option value="1" <c:if test="${'1' eq teachDining.breakfast}">selected</c:if>>就餐
                                    </option>
                                    <option value="2" <c:if test="${'2' eq teachDining.breakfast}">selected</c:if>>不就餐
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">中餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="lunch">
                                    <option value="1" <c:if test="${'1' eq teachDining.lunch}">selected</c:if>>就餐
                                    </option>
                                    <option value="2" <c:if test="${'2' eq teachDining.lunch}">selected</c:if>>不就餐
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">晚餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="dinner">
                                    <option value="1" <c:if test="${'1' eq teachDining.dinner}">selected</c:if>>就餐
                                    </option>
                                    <option value="2" <c:if test="${'2' eq teachDining.dinner}">selected</c:if>>不就餐
                                    </option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
</body>
</html>

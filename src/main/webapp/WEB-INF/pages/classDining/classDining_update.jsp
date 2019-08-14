<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改班级就餐记录</title>
</head>

<body class="gray-bg">
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" data-method="post">

                        <input type="hidden" name="id" value="${classDining.id}">
                        <input type="hidden" name="classRoom" value="${classDining.classRoom}">
                        <input type="hidden" name="day" value="${classDining.day}">


                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">早餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="breakfast">
                                    <option value="1" <c:if test="${'1' eq classDining.breakfast}">selected</c:if>>就餐
                                    </option>
                                    <option value="2" <c:if test="${'2' eq classDining.breakfast}">selected</c:if>>不就餐
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">中餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="lunch">
                                    <option value="1" <c:if test="${'1' eq classDining.lunch}">selected</c:if>>就餐
                                    </option>
                                    <option value="2" <c:if test="${'2' eq classDining.lunch}">selected</c:if>>不就餐
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">晚餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="dinner">
                                    <option value="1" <c:if test="${'1' eq classDining.dinner}">selected</c:if>>就餐
                                    </option>
                                    <option value="2" <c:if test="${'2' eq classDining.dinner}">selected</c:if>>不就餐
                                    </option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改信息</title>

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
                    <form id="form" class="form-horizontal" action="${ctx}/teachDin/${teachDining.number}.action?time=${teachDining.time}" data-method="post">
                        <div class="form-group">
                            <label class="col-sm-2 control-label no_line">流水号<span class= "red_must"  >*</span></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="number" value="${teachDining.number}" readonly="readonly">
                            </div>
                            <label class="col-sm-2 control-label no_line">教师姓名<span class= "red_must"  >*</span></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="teacherName" value="${teachDining.teacherName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label no_line">安排人姓名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="arranger" value="${userName}" readonly="readonly">
                            </div>
                            <label class="col-sm-2 control-label no_line">班级名称</label>
                            <div class="col-sm-4">
                                <select style="width: 250px;height: 35px;border-color:#E0E0E0;" id="classId"
                                        name="classId" onchange="select(this.value)">
                                    <option value="">请选择班级</option>
                                    <c:forEach items="${classNameList}" var="classNameList">
                                        <option <c:if test="${teachDining.classId eq classNameList.id}">selected</c:if> value=${classNameList.id} data_obj="${classNameList.diningPlace}">${classNameList.className}</option>
                                    </c:forEach>
                                </select>
                                <%--<input type="text" class="form-control" name="classId" value="${teachDining.classId}">--%>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label no_line">就餐地点<span class= "red_must"  >*</span></label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="area" name="area" value="${teachDining.area}" readonly="readonly">
                                <%--<select style="width: 250px;height: 35px;border-color:#E0E0E0;" id="area"--%>
                                        <%--name="area">--%>
                                    <%--<option value="">请选择就餐地点</option>--%>
                                    <%--<c:forEach items="${list}" var="list">--%>
                                        <%--<option <c:if test="${teachDining.area eq list}">selected</c:if> value=${list}>${list}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
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
                <script>
                    function select(value) {
                        var diningPlace = $("#classId option:selected").attr("data_obj");
                        $("#area").val(diningPlace);
                    }
                </script>
                <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
                <script src="${ctxsta}/cms/js/teachDining/teachDining_update.js"></script>
</body>
</html>

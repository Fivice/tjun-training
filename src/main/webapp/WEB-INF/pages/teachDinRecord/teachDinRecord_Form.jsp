<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/teachDinRecord/teachDinRecordTable.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="ibox-content">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <div class="col-sm-12 ">
                            <h4>[${classInfo.classNumber}]${classInfo.className}${classInfo.hostUnit}
                                开班时间：${classInfo.startStopTime} 培训天数：${classInfo.dayNum}天
                                教师姓名：${teacherName} 班级编号：${classInfo.classNumber}
                            </h4>
                        </div>
                    </div>

                    <div class="ibox-content">
                        <div class="row row-lg">
                            <div class="col-sm-12">
                                <input type="hidden" id="num" name="num" value="${num}">
                                <input type="hidden" id="classId" name="num" value="${classId}">
                                <table id="teach_dining_record" class="table table-bordered table-striped"></table>
                            </div>
                        </div>
                        <div class="btn-group m-t-sm">
                            <button type="button" class="btn btn-primary" title="返回" onclick="window.history.back()"
                            >返回教师就餐统计</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
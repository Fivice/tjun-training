<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>班级列表</title>
    <script src="${ctxsta }/entity/evaluationManagement/evaluationManagement.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
  </head>
<body class="fixed-sidebar full-height-layout gray-bg">
<!-- 導入彈框 -->
<div id="importBox" class="hide">
    <form id="importForm" action="${ctx}/classInfo/import.action"
          method="post" enctype="multipart/form-data" class="form-search">
    </form>
</div>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>评价管理</h5>
                    <div class="ibox-tools"></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="btn-group m-t-sm  form-inline">
                                <label class="control-label" for="startStopTime">开班月：</label>
                                <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="startStopTime" id="startStopTime" placeholder="请选择开班时间" value="${startStopTime}">
                            </div>
                            <div class="btn-group m-t-sm  form-inline">
                                <label class="control-label" for="startStopTime">评价截止时间：</label>
                                <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="evaluateStopTime" id="evaluateStopTime" value="${evaluateStopTime}" placeholder="评价截止时间">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="className" id="className" placeholder="请输入班级名称">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="teacherName" id="teacherName" placeholder="请输入班主任名称">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" type="text" class="form-control" name="classNumber" id="classNumber" placeholder="请输入班级编号">
                            </div>
                            <div class="btn-group m-t-sm">
                                <select style="width: 200px;height: 34px;margin-top:-5px;border-color:#E0E0E0;" id="plan">
                                    <option value="">全部</option>
                                    <option value="0">计划内</option>
                                    <option value="1">计划外</option>
                                    <option value="2">非培训班</option>
                                </select>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-info" id="search" onclick="seach()"><i class="fa fa-search"></i>查询</button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button id="reset" type="button" class="btn" onclick="reset()">清空</button>
                            </div>
                        </div>
                        <!-- 数据表格 -->
                        <table id="table" class="table table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    //清空input
    function reset(){
        $("input").prop("value","");
        $("#plan").prop("value","");
    }
    //年月范围选择
    laydate.render({
        elem: '#startStopTime'
        ,type: 'month'
        ,theme: '#6CA6CD'
       /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });
        laydate.render({
            elem: '#evaluateStopTime'
            ,theme: '#6CA6CD'
            ,value: sessionStorage.getItem("evaluateStopTime")
//            ,isInitValue: true
//            ,done: function(value, date, endDate){
//                //点击事件 点选时间 后 将session中的时间更新
//                sessionStorage.setItem("evaluateStopTime",value);
//            }
        });
        if (sessionStorage.getItem("evaluateStopTime") == null){
            sessionStorage.setItem("evaluateStopTime",new Date().Format("yyyy-MM-dd"));

    };
</script>
</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>总体评价数据分析</title>
    <script src="${ctxsta }/entity/overallEvaluation/overallEvaluation.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/overallEvaluation/plot.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/register/bootstrpValidator.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/register/register.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/register/tree.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script src="${ctxsta}/cms/js/echarts.min.js"></script>
    <style type="text/css">
        /*解决设置表头列宽无效*/
        .row{
            table-layout: fixed;
        }

    </style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">

    <div class="box-header with-border">
        <div class="box-title">
            <i class="fa fa-list-alt"></i>总体评价数据分析
        </div>
    </div>
    <div style="margin-top: 20px">

        <input id="query" type="hidden" value="month">
        <input id="title" type="hidden" value="月份">

        <div class="row">

            <div class="col-xs-7">
                <div class="col-sm-12">
                    <div class="col-sm-3">
                            <input type="text" id="year"  readonly name="year" class="laydate form-control">
                    </div>
                    <div class="col-sm-3">
                        <select id="plan"  onchange="findByPlan(this)" class="form-control">
                            <option value="">全部</option>
                            <option value="0">计划内</option>
                            <option value="1">计划外</option>
                            <option value="2">非培训班</option>
                        </select>
                        </div>
                    <div class="col-sm-3">
                        <select  onchange="findByType(this)" class="form-control">
                            <option value="0">按月份统计</option>
                            <option value="1">按培训类型统计</option>
                        </select>
                    </div>

            </div>
                <!-- 数据表格 -->
                <div class="col-sm-12" style="margin-top: 10px;">
                    <div class="col-sm-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <i class="fa fa-list"></i>&nbsp;总体评价数据分析
                            </div>
                            <div class="panel-body" style="padding-top: 0">
                                <!-- 数据表格 -->
                                <table id="table" class="table table-hover"></table>
                            </div>
                        </div>
                    </div>
                </div>


            </div>

            <div class="col-xs-5">
                <div class="col-sm-12">
                    <div>
                           <select id="testSelect" onchange="seachPlot(this)" class="form-control">
                               <option value="0">班级数</option>
                               <option value="1">培训质量评价</option>
                               <option value="2">综合评价</option>
                           </select>
                    </div>
                </div>
                <div class="col-sm-12">
                    <label>
                        <span class="required hide" aria-required="true">*</span><i class="fa icon-question hide"></i></label>
                    <div>
                        <div id="main" style="height:600px"></div>
                    </div>
                </div>


</div>


</div>
</div>
</div>
</div>
<script>

$(document).ready(function(){
/* $.ajax({ //查询单位的下拉信息
type: 'post',
url: ctx+'/register/findInformation.action?query=unit',
dataType: "json", //返回的结果为json
success: function(data) {
for (var i = 0; i < data.unitList.length; i++) {
$("#unitId").append("<option>" + data.unitList[i].areaName + "</option>");
}
},
});*/
});

//日期时间选择器
$(".laydate").each(function(){
    var nowTime = new Date().valueOf();
    laydate.render({
        elem: this, //指定元素  表示当前的元素
        theme: '#6CA6CD',
        type: 'year',
        value: new Date( Date.parse(new Date())) ,//参数即为：2018-08-20 20:08:08 的时间戳
        max: nowTime,
        btns: ['now', 'confirm'],
        done: function(value, date, endDate){
            $("#year").prop("value",value);
            $("#table").bootstrapTable('refresh');
    }

    })
});



</script>

</body>
</html>
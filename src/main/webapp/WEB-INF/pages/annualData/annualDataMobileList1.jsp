<%--
  Created by IntelliJ IDEA.
  User: win7
  Date: 2019/4/15
  Time: 下午 5:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>年度数据分析</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="${ctxsta}/common/layer/mobile/need/layer.css" />
    <link rel="stylesheet" href="${ctxsta}/css/classFundsMobile.css" />
    <link rel="stylesheet" href="${ctxsta}/common/icheck/line/grey.css" />
    <link rel="icon" href="${ctxsta}/img/login/logo.ico"/>

    <style>
        tr th{
            white-space: nowrap;
        }
        body{
            height: inherit;
        }
        @media screen and (max-height: 620px) {
            body {
                height: 620px;
            }
        }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-table" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Admin</a>
        </div>
        <div class="collapse navbar-collapse" id="nav-table">
            <ul class="nav navbar-nav">

            </ul>
        </div>
    </div>
</nav>
<div class="container" style="padding-right: 5px;padding-left: 5px">
    <div class="row">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li>首页</li>
                <li class="active">年度数据分析</li>
            </ol>
        </div>
    </div>
</div>
<div class="container" style="padding-right: 5px;padding-left: 5px">
    <div>
        <div class="panel-body" style="padding: 5px 1px">
            <div style="overflow: scroll;margin-bottom: 50px">


                <input id="query" type="hidden" value="month">
                <input id="title" type="hidden" value="月份">

                <div class="panel-group" id="accordion1" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="heading">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion1" href="#collapse"
                                   aria-expanded="true" aria-controls="collapse">


                                    <button type="button" class="btn btn-info" data-toggle="tooltip"
                                            data-placement="right" title="">年度分析表格
                                    </button>

                                </a>
                            </h4>

                            <div class="tooltip top" role="tooltip">
                                <div class="tooltip-arrow"></div>
                                <div class="tooltip-inner">
                                    Some tooltip text!
                                </div>
                            </div>

                        </div>
                        <div id="collapse" class="panel-collapse collapse in" role="tabpanel"
                             aria-labelledby="heading">

                            <div class="panel-body" style="padding: 15px 1px">
                                <div style="overflow: scroll;margin-bottom: 50px">
                                    <table id="table" class="table table-hover table-responsive table-striped table-bordered">

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                                   aria-expanded="true" aria-controls="collapseOne">

                                    <button type="button" class="btn btn-info" data-toggle="tooltip"
                                            data-placement="right" title="">年度分析柱状分布图
                                    </button>

                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel"
                             aria-labelledby="headingOne">
                            <div class="panel-body">

                                <div class="col-xs-12">
                                    <div class="form-group">
                                        <div>
                                            <div id="main" style="height:600px"></div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>






            </div>
        </div>
    </div>


    <!--点击右侧弹出-->
    <a href="javascript:void(0)" class="btn btn-default mybtn" data-toggle="modal" data-target="#myModal" style="z-index: 999"><i class="fa fa-filter"></i></a>
    <!-- 弹出层 modal -->
    <div class="modal right fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel"><i class="fa fa-filter"></i> 请选择过滤条件</h4>
                </div>
                <div class="modal-body">
                    <div class="btn-group m-t-sm">
                        <p style="font-weight: bolder;margin-bottom: 5px">选择开班时间:</p>
                        <input type="text" class="laydate" name="year" id="year" placeholder="请选择开班时间段">
                    </div>
                    <hr/>
                    <div class="btn-group m-t-sm">
                        <label for="trainingType">请选择培训类型:</label>
                        <select class="form-control" name="trainingType" id="trainingType" onchange="findByType(this)">
                            <option value="">请选择培训类型</option>
                            <option value="0">按月份统计</option>
                            <option value="1">按培训类型统计</option>
                        </select>
                    </div>
                    <hr/>
                    <div class="btn-group m-t-sm">
                        <label for="plan">班级类型:</label>
                        <select class="form-control" name="plan" id="plan" onchange="findByPlan(this)">
                            <option value="">全部</option>
                            <option value="0">计划内</option>
                            <option value="1">计划外</option>
                            <option value="2">非培训班</option>
                        </select>
                    </div>
                    <hr/>
                    <div class="btn-group m-t-sm">
                        <label for="testSelect">图表类型:</label>
                        <select class="form-control" name="testSelect" id="testSelect" onchange="seachPlot(this)">
                            <option value="0">班级数</option>
                            <option value="1">培训人数</option>
                            <option value="2">培训天数</option>
                            <option value="3">培训费</option>
                            <option value="4">住宿费</option>
                            <option value="5">其它费用</option>
                            <option value="6">就餐费(学员)</option>
                            <option value="7">就餐结余</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>

</div>
</div>
<footer style="height: 50px;position: fixed;bottom: 0px;left: 0px;width: 100%;background-color: #333;z-index: 998">
    <div class="col-sm-12" style="color: #eeeeee;line-height: 50px">
        <i class="fa fa-copyright"> copyright 2019</i>
    </div>
</footer>
<%--jQuery--%>
<script src="${ctxsta}/jquery/jquery-3.2.1.js"></script>
<%--bootstrap--%>
<script src="${ctxsta}/bootstrap/js/bootstrap.js"></script>
<%--layer--%>
<script src="${ctxsta}/common/layer/mobile/layer.js"></script>
<script src="${ctxsta}/common/layer/layer.js"></script>
<script src="${ctxsta}/common/layui/layDateChange/layDate/laydate.js"></script>
<%--bootstrap table--%>
<script src="${ctxsta}/common/bootstrap-table/bootstrap-table.js" type="text/javascript"></script>
<%--iCheck--%>
<script src="${ctxsta}/common/bootstrap-table/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<script src="${ctxsta}/common/icheck/icheck.js" type="text/javascript"></script>
<%--nav.js 对导航进行管理--%>
<script src="${ctxsta }/cms/js/mobile/nav.js"></script>
<%--js for this page--%>
<%--<script src="${ctxsta}/cms/js/common.js"></script>--%>
<%--nav.js 对导航进行管理--%>
<script src="${ctxsta }/cms/js/mobile/nav.js"></script>
<script src="${ctxsta }/entity/annualData/annualData.js" type="text/javascript"></script>
<script src="${ctxsta }/entity/annualData/plot.js" type="text/javascript"></script>
<script src="${ctxsta }/entity/register/register.js" type="text/javascript"></script>
<script src="${ctxsta }/entity/register/tree.js" type="text/javascript"></script>
<script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
<script src="${ctxsta}/cms/js/echarts.js"></script>
<script>
    var refreshTable = function () {
        $("#table").bootstrapTable('destroy');
        initClassFundsTable();
    }
    var search = function(){
        refreshTable();

        $("#myModal").modal('hide');
    }

</script>

<script>

    if (top.location != location) {
        top.location.href = location.href;
    }

    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })


    //日期时间选择器
    $(".laydate").each(function () {
        var nowTime = new Date().valueOf();
        laydate.render({
            elem: this, //指定元素  表示当前的元素
            theme: '#6CA6CD',
            type: 'year',
            value: new Date(Date.parse(new Date())),//参数即为：2018-08-20 20:08:08 的时间戳
            max: nowTime,
            btns: ['now', 'confirm'],
            done: function (value, date, endDate) {
                $("#year").prop("value", value);
                $("#table").bootstrapTable('refresh');
            }

        })
    });



</script>
</body>

</html>

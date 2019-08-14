<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wubin
  Date: 2018/10/29
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${classInfo.className}自主报名</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${ctxsta}/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="${ctxsta}/common/layer/mobile/need/layer.css" />
    <link rel="stylesheet" href="${ctxsta}/css/studentRegisterBySelfForm.css">
    <link rel="icon" href="${ctxsta}/img/login/logo.ico"/>

</head>
<body>
<section class="panel">
    <header class="panel-heading custom-tab navbar-fixed-top">
        <ul class="nav nav-pills navbar-header">
            <li class="active">
                <a href="#class-info" data-toggle="tab" aria-expanded="true">班级信息</a>
            </li>
            <li class="">
                <a href="#search-container" data-toggle="tab" aria-expanded="false">报名</a>
            </li>
            <li class="dropdown">
                <a href="#" id="more-info" class="dropdown-toggle" data-toggle="dropdown">
                    更多
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="more-info">
                    <li><a href="#course-evaluation" tabindex="-1" data-toggle="tab">课程评价</a></li>
                    <li><a href="#training-schedule" tabindex="-1" data-toggle="tab">培训日程</a></li>
                    <li><a href="#training-history" id="training" tabindex="-1" data-toggle="tab">培训记录</a></li>
                    <li><a href="#stu-dining" id="dining" tabindex="-1" data-toggle="tab">就餐安排</a></li>
                    <li><a href="#download" id="file" tabindex="-1" data-toggle="tab">资料下载</a></li>
                </ul>
            </li>
        </ul>
    </header>
    <div class="panel-body">
        <div class="tab-content">
            <div class="tab-pane active" id="class-info">
                <div class="class-info-title" data-toggle="tooltip" data-placement="top" title="${classInfo.className}">
                    《 ${classInfo.className} 》
                </div>
                <div class="class-info-table">
                    <table class="table table-bordered table-striped">
                        <%--<thead>
                        <tr>
                            <th>项目</th>
                            <th>内容</th>
                        </tr>
                        </thead>--%>
                        <tbody>
                        <tr>
                            <td>班级编号</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.classNumber }</td>
                        </tr>
                        <tr>
                            <td>主办单位</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.hostUnit }</td>
                        </tr>
                        <tr>
                            <td>培训时间</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.startStopTime }</td>
                        </tr>
                        <tr>
                            <td>班主任</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.teacherName }</td>
                        </tr>
                        <tr>
                            <td>联系方式</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.phoneNumber }</td>
                        </tr>
                        <tr>
                            <td>报到地点</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.regPlace }</td>
                        </tr>
                        <tr>
                            <td>报到时间</td>
                            <td>${classInfo.entryStartTime==""?"无":(fn:substring(classInfo.entryStartTime,0,10))}&nbsp${fn:substring(classInfo.entryStartTime,0,10)==""?"无":(fn:substring(classInfo.entryStartTime,11,13)>=12?"下午":"上午")}</td>
                        </tr>
                        <tr>
                            <td>住宿地点</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.hotelPlace }</td>
                        </tr>
                        <tr>
                            <td>就餐地点</td>
                            <td>${classInfo.classNumber==""?"无":classInfo.diningPlace }</td>
                        </tr>
                        <tr>
                            <td>应缴费用</td>
                            <td>培训费：${classInfo.trainingExpense }其他费用：${classInfo.otherCharges }</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="tab-pane" id="search-container">
                <div id="search-context" class="input-group">
                    <input type="text" id="idNumber" class="form-control" placeholder="请输入身份证">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="search(${classInfo.id})">搜索</button>
                    </span>
                </div>
                <div id="sign-up-info" class="sign-up-info" hidden = "hidden" >
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="stu-idNumber" class="col-xs-2 control-label">身份证号</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-idNumber" placeholder="" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="stu-name" class="col-xs-2 control-label">姓名</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-name" placeholder="请输入姓名(必填)">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="stu-phone" class="col-xs-2 control-label">手机号</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-phone" placeholder="请输入手机号(必填)">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="stu-number" class="col-xs-2 control-label">员工编号</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-number" placeholder="员工编号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="stu-email" class="col-xs-2 control-label">Email</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-email" placeholder="请输入email">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="stu-dep" class="col-xs-2 control-label">部门</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-dep" placeholder="请输入部门">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nation" class="col-xs-2 control-label">民族</label>
                            <div class="col-xs-5" style="padding-right: 1px;">
                                <select class="form-control" id="nation">
                                    <option>请选择</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="host-units" class="col-xs-2 control-label">单位</label>
                            <div class="col-xs-5" style="padding-right: 1px;">
                                <select class="form-control" id="host-units">

                                </select>
                            </div>
                            <div class="col-xs-5" style="padding-left: 1px;">
                                <select class="form-control" id="next-units">

                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="stu-reg-status" class="col-xs-2 control-label">报名状态</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-reg-status" placeholder="报名状态" readonly>
                            </div>
                        </div>
                        <div class="form-group" hidden>
                            <label for="stu-classId" class="col-xs-2 control-label">班级id</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-classId" placeholder="班级id" value="${classInfo.id}" readonly>
                            </div>
                        </div>
                        <div class="form-group" hidden>
                            <label for="stu-id" class="col-xs-2 control-label">学员id</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="stu-id" placeholder="学员id" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="btn-group col-xs-offset-2 col-xs-10">
                                <a href="#" class="btn btn-primary" style="width: 50%" onclick="registerBySelf()">报名</a>
                                <a href="#" class="btn btn-primary" style="width: 50%" onclick="updateBySelf()">修改</a>
                            </div>
                            <input id="stu-evaluate-status" type="hidden" value="">
                            <input id="stu-nation" type="hidden">
                            <input id="classEva" type="hidden" value="${classInfo.evaluate}">
                            <input id="evaluateStartTime" type="hidden" value="${classInfo.evaluateStartTime}">
                            <input id="evaluateStopTime" type="hidden" value="${classInfo.evaluateStopTime}">
                            <%--<a style="color: #6CA6CD" onclick="intoJudgeManager()" >点击进入培训评价管理</a>--%>
                            <%--<input id="reg" type="hidden" value="${reg.status}">--%>
                        </div>
                    </form>
                </div>
            </div>
            <div class="tab-pane fade" id="course-evaluation">
                <ul class="breadcrumb panel">
                    <li><p><i class="fa fa-list-alt"></i> 课程评价</p></li>
                </ul>
                <div class="panel panel-default" style="overflow: scroll;box-sizing: border-box">
                    <div class="input-group">
                        <input id="idNumber-eva" type="text" class="form-control" placeholder="请输入身份证进入评价">
                        <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="evaluation(${classInfo.id})">进行评价</button>
                    </span>
                    </div>
                </div>

            </div>
            <div class="tab-pane fade" id="training-schedule">
                <ul class="breadcrumb panel">
                    <li><p><i class="fa fa-list-alt"></i> 培训日程安排如下</p></li>
                </ul>
                <div class="panel panel-default" style="overflow: scroll;box-sizing: border-box">

                    <!-- Table -->
                    <div class=table-responsive">
                    <table id="class-scheduling-table" class="table table-striped text-nowrap">

                    </table>
                    </div>
                </div>

            </div>
            <%--培训记录--%>
            <div class="tab-pane fade" id="training-history">
                <ul class="breadcrumb panel">
                    <li><p><i class="fa fa-tasks"></i> 已完成培训</p></li>
                </ul>
                <div class="panel panel-default">

                    <!-- Table -->
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>班级名称</th>
                            <th>主办单位</th>
                            <th>办班时间</th>
                            <th>天数</th>
                            <th>学时</th>
                        </tr>
                        </thead>
                        <tbody id="training-history-body">


                        </tbody>
                    </table>
                </div>

            </div>
            <%--就餐安排--%>
            <div class="tab-pane fade" id="stu-dining">
                <ul class="breadcrumb panel">
                    <li><p><i class="fa fa-coffee"></i> 就餐安排</p></li>
                </ul>
                <div class="panel panel-default">

                    <!-- Table -->
                    <table id="stu-dining-table" class="table">

                    </table>
                </div>

            </div>
            <%--资料下载--%>
            <div class="tab-pane fade" id="download">
                <input id="fileUrl" value="${classInfo.fileUrl}" hidden>
                <ul class="breadcrumb panel">
                    <li><p><i class="fa fa-cloud-download"></i> 文件下载列表</p></li>
                </ul>
                <div class="panel panel-default">
                    <!-- Table -->
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>文件</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="file-list">
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
<div class="main_nav_bottom">
    <nav class="navbar navbar-default navbar-fixed-bottom bottom-info">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-lg-8">Copyright&nbsp<i class="fa fa-copyright"></i>&nbsp2018</div>
                <div class="col-xs-4 col-sm-4 col-lg-4">天君智云</div>
            </div>
        </div>
    </nav>
</div>
<%--jQuery--%>
<script src="${ctxsta}/jquery/jquery-3.2.1.js"></script>
<%--bootstrap--%>
<script src="${ctxsta}/bootstrap/js/bootstrap.js"></script>
<%--layer--%>
<script src="${ctxsta}/common/layer/mobile/layer.js"></script>
<script src="${ctxsta}/common/layer/layer.js"></script>
<%--bootstrap table--%>
<script src="${ctxsta}/common/bootstrap-table/bootstrap-table.js" type="text/javascript"></script>
<script src="${ctxsta}/common/bootstrap-table/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<%--js for this page--%>
<script src="${ctxsta}/entity/signUpManager/commonUtils.js"></script>
<script src="${ctxsta}/entity/signUpManager/studentRegisterBySelfForm.js"></script>
<script src="${ctxsta}/entity/signUpManager/diningInfoInStudentRegisterPage.js"></script>
<script src="${ctxsta}/entity/signUpManager/classScheduling.js"></script>
<%--<script src="${ctxsta}/cms/js/common.js"></script>--%>


</body>

</html>

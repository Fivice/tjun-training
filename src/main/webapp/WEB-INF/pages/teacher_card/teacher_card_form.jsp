<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>教师相关信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="../static/css/studentRegisterBySelfForm.css">
    <link rel="stylesheet" href="../static/common/ztree/css/bootstrapStyle/bootstrapStyle.css" />
    <link rel="stylesheet" href="../static/common/layui/layui/css/layui.css" />

</head>
<body>
<section class="panel">
    <header class="panel-heading custom-tab navbar-fixed-top">
        <ul class="nav nav-pills navbar-header">
            <li class="active">
                <a href="#class-info" data-toggle="tab" aria-expanded="true">教师信息</a>
            </li>
            <li class="">
                <a href="#training-schedule"  data-toggle="tab" aria-expanded="false">教师就餐信息</a>
            </li>
        </ul>
    </header>
    <div class="panel-body">
        <div class="tab-content">
            <div class="tab-pane active" id="class-info">
                <div class="class-info-title">
                    ${teachDining.className}
                </div>
                <div class="class-info-table">
                    <table class="table table-bordered table-striped">
                        <tbody>
                        <tr>
                            <td>教师流水号</td>
                            <td>${teachDining.number==""?"无":teachDining.number }</td>
                        </tr>
                        <tr>
                            <td>教师姓名</td>
                            <td>${teachDining.number==""?"无":teachDining.teacherName }</td>
                        </tr>
                        <tr>
                            <td>安排人姓名</td>
                            <td>${arranger==""?"无":arranger }</td>
                        </tr>
                        <tr>
                            <td>班级名称</td>
                            <td>${teachDining.number==""?"无":teachDining.className }</td>
                        </tr>
                        <tr>
                            <td>就餐地点</td>
                            <td>${teachDining.number==""?"无":teachDining.area }</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="tab-pane fade" id="training-schedule">
                <input type="hidden" id="number" name="number" value="${teachDining.number}">
                <input type="hidden" id="time" name="time" value="${teachDining.time}">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        教师就餐安排如下：
                    </div>
                    <!-- Table -->
                    <div class="table-responsive">
                    <table id="table_teach_dining" class="table text-nowrap"></table>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>
<div class="main_nav_bottom">
    <nav class="navbar navbar-default navbar-fixed-bottom bottom-info">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-lg-8">@copyRight:2017-2018</div>
                <div class="col-xs-4 col-sm-4 col-lg-4">天君智云</div>
            </div>
        </div>
    </nav>
</div>
<script src="../static/jquery/jquery-3.2.1.js"></script>
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script src="../static/bootstrap/table/bootstrap-table.js" type="text/javascript"></script>
<script src="../static/cms/js/common.js"></script>
<script src="../static/common/layer/layer.js"></script>
<script src="../static/bootstrap/table/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<script src="../static/common/ztree/js/jquery.ztree.core.js" type="text/javascript"></script>
<script src="../static/common/ztree/js/jquery.ztree.excheck.js" type="text/javascript"></script>
<script src="../static/common/ztree/js/jquery.ztree.exedit.js" type="text/javascript"></script>
<script src="../static/entity/scheduling/teachDinForm.js"></script>
</body>
<script type="text/javascript">
    $(function () {
        if (top.location != location){
            top.location.href = location.href;
        }
    })
</script>
</html>


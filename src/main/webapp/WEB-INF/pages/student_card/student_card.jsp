<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>选择学员证</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="../static/css/studentRegisterBySelfForm.css">
    <style>
        .gohome{
            display: none;
        }
    </style>
</head>
<body>
<section class="panel">
    <header class="panel-heading custom-tab navbar-fixed-top">
        <header class="panel-heading custom-tab navbar-fixed-top">
            <a href="#class-info" data-toggle="tab" aria-expanded="true">选择学员证号</a>
        </header>
    </header>
    <div class="panel-body">
        <div class="tab-content">
            <div class="tab-pane active" id="class-info">

                <div class="sign-up-info">
                    <div class="form-group">
                        <label class="col-xs-4 control-label">选择学员证号:</label>
                        <div class="col-xs-8">
                            <select class="form-control" id="id" name="number">
                                <option value="">选择学员证号</option>
                                <c:forEach items="${stuCardList}" var="stuCardList">
                                    <option value="${stuCardList.id}">${stuCardList.number}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-4 col-xs-8">
                            <button class="btn btn-sm btn-primary" onclick="myfunction()"><i class="fa fa-check"></i> 确
                                定
                            </button>&nbsp;
                            <button id="reset" type="button" class="btn" onclick="resetUnit()">清除</button>
                        </div>
                    </div>
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
</body>
<script type="text/javascript">
    if (top.location != location) {
        top.location.href = location.href;
    }

    //清除选中的学生证号
    function resetUnit() {
        $("#id").prop("value", "");
    }

    function myfunction() {
        var id = $('#id').val();
        console.log(id)
        $.ajax({
            type: 'get',
            dataType: 'json',
            data: {},
            url: ctx + '/studentCard/form.action?id=' + id,
            success: function (result) {
                if (result.code == 1) {
                    window.location.href = ctx + '/studentCard/form2.action?id=' + id;
                } else {
                    layer.msg('学员证失效!', {
                        icon: 2,
                        time: 1000
                    });
                }
            }
        })
    }
</script>
</html>

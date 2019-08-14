<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/cms/js/teachDining/teachDining.js"></script>
    <script src="${ctxsta }/cms/js/teachDining/bootstrapValidator.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script src="${ctxsta}/entity/teacherDining/classInfo.js"></script>
    <script src="${ctxsta}/entity/teacherDining/teacherInfo.js"></script>
    <script src="${ctxsta}/entity/teacherDining/teacherDiningInfoTable.js"></script>
    <!-- Bootstrap date Picker -->
    <link rel="stylesheet" href="${ctxsta}/common/bootstrap-datepicker/css/bootstrap-datepicker3.css">

    <link rel="stylesheet" href="${ctxsta}/common/layui/layui/css/layui.css"  media="all">
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<!--main content start-->
<div class="wrapper wrapper-content">
    <input type="hidden"  name="classId" id="classId1" value="${classId}">
    <section id="main-content">
    <section class="wrapper">
        <!-- page start-->

        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading tab-bg-dark-navy-blue ">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a data-toggle="tab" href="#home">教师证绑定教师就餐安排</a>
                            </li>
                            <li class="">
                                <a data-toggle="tab" onclick="initTableForBind()" href="#about">人脸数据绑定教师就餐安排</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                        <div class="tab-content">
                            <div id="home" class="tab-pane active">
                                <div class="col-sm-12">
                                    <div class="btn-group m-t-sm">
                                        <button type="button" class="btn btn-default" title="分配教师证"
                                                onclick="layer_show('分配教师证','${ctx}/teachDin/add/view.action','900','480')"><i
                                                class="glyphicon glyphicon-plus"></i></button>
                                    </div>

                                    <div class="btn-group m-t-sm">
                                        <button type="button" class="btn btn-default" title="编辑"
                                                onclick="teachDining_edit()">
                                            <i class="glyphicon glyphicon-pencil"></i></button>
                                    </div>

                                    <%--<div class="btn-group m-t-sm">--%>
                                    <%--<button type="button" class="btn btn-default" title="删除"--%>
                                    <%--onclick="teachDining_delete()"><i class="glyphicon glyphicon-minus"></i></button>--%>
                                    <%--</div>--%>


                                    <div class="btn-group m-t-sm">
                                        <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="schoolName"
                                                name="schoolName">
                                            <option value="">请选择就餐地点</option>
                                            <c:forEach items="${list}" var="list">
                                                <option value=${list}>${list}</option>
                                            </c:forEach>
                                            <%--<option value="其他">其他</option>--%>
                                        </select>
                                    </div>&emsp;
                                    <div class="btn-group m-t-sm">
                                        <input type="text" class="form-control" name="number" id="number"
                                               placeholder="请输入流水号">
                                    </div>&emsp;
                                    <div class="btn-group m-t-sm">
                                        <input type="text" class="form-control" name="month" id="month" value="${month}"
                                               placeholder="请选择就餐月份">
                                    </div>&emsp;
                                    <div class="btn-group m-t-sm">
                                        <button id="find" type="button" class="btn btn-primary">查询</button>&emsp;
                                    </div>
                                    <div class="btn-group m-t-sm">
                                        <button id="reset" type="button" class="btn">清空</button>
                                    </div>
                                    <table id="table_teach_dining" class="table table-bordered table-striped"></table>
                                    <div class="btn-group m-t-sm">
                                        <button type="button" class="btn btn-primary" title="返回" onclick="window.history.back()"
                                        >返回</button>
                                    </div>
                                </div>
                            </div>
                            <div id="about" class="tab-pane">
                                <div class="col-lg-12 col-sm-12">
                                    <%--<button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="top" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">--%>
                                        <%--Popover on 顶部--%>
                                    <%--</button>--%>
                                    <%--<button type="button" class="btn btn-round btn-default">Default</button>--%>
                                </div>
                                <div class="col-lg-12 col-sm-12" style="margin-top:10px;">
                                    <div class="col-lg-4 col-sm-4" style="margin-top:5%;">
                                        <form class="form-horizontal bucket-form">
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">教师</label>
                                                <div class="col-sm-8">
                                                    <input class="form-control" id="teacherName" type="text"
                                                           placeholder="教师名" >
                                                    <input class="form-control" id="teacherId" type="text"
                                                           placeholder="教师id" style="display: none">
                                                    <input class="form-control" id="bindClassId" type="text"
                                                           placeholder="绑定的班级id" style="display: none">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">班级</label>
                                                <div class="col-sm-8">
                                                    <input class="form-control" id="className" type="text"
                                                           placeholder="班级名">
                                                    <input class="form-control" id="classId" type="text"
                                                           placeholder="班级Id" style="display: none">
                                                    <input class="form-control" id="bindTeacherId" type="text"
                                                           placeholder="绑定的老师Id" style="display: none">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-3 control-label"></label>
                                                <div class="col-sm-8">
                                                    <div class="btn-group btn-group-justified">
                                                        <span class="btn btn-success" onclick="bind()"><i class="fa fa-chain"></i> &nbsp绑定</span>
                                                        <span class="btn btn-info" onclick="unBind()"><i class="fa fa-chain-broken"></i> &nbsp解绑</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="col-lg-4 col-sm-4">
                                        <section class="panel panel-primary">
                                            <header class="panel-heading">
                                                <i class="fa fa-user"></i> 教师信息表/点选
                                                <div class="input-group" style="margin-top: 5px;">
                                                    <input id="teacherNameForSearch" type="text" class="form-control" style="color: #0C0C0C" placeholder="请输入教师名字">
                                                    <span class="input-group-btn">
						                                <button class="btn btn-default" type="button" onclick="reInitTeacherTable()">
							                                <i class="fa fa-search"></i>
						                                </button>
					                                </span>
                                                </div>
                                            </header>
                                            <div class="panel-body" style="padding-top: 0px;padding-right: 1px;padding-left: 1px;">
                                                <table id="teacher-info" class="table">

                                                </table>
                                            </div>

                                        </section>
                                    </div>
                                    <div class="col-lg-4 col-sm-4">
                                        <section class="panel panel-primary">
                                            <header class="panel-heading">
                                                <i class="fa fa-building-o"></i> 正在开班班级信息表/点选
                                                <div class="input-group" style="margin-top: 5px;">
                                                    <input id="classNameForSearch" type="text" class="form-control" style="color: #0C0C0C" placeholder="请输入班级名">
                                                    <span class="input-group-btn">
						                                <button class="btn btn-default" type="button" onclick="reInitClassTable()">
							                                <i class="fa fa-search"></i>
						                                </button>
					                                </span>
                                                </div>
                                            </header>
                                            <div class="panel-body" style="padding-top: 0px;padding-right: 1px;padding-left: 1px;">
                                                <table id="class-info" class="table">

                                                </table>
                                            </div>

                                        </section>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
        <!-- page end-->
        <%--教师就餐安排模态框--%>
        <div id="teacherDining" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="teacherDiningTitle">Modal title</h4>
                    </div>
                    <div class="modal-body">
                        <input id="teacherDiningId" type="text" style="display: none">
                        <input id="teacherDiningClassId" type="text" style="display: none">
                        <div id="teacherDiningInfoToolbar" class="btn-group">
                            <div class="row">
                                <div class="col-sm-6" style="padding-right: 0;">
                                    <div class="input-group">
                                        <input id="startTimeValue" type="text" style="display: none" class="form-control">
                                        <input id="stopTimeValue" type="text" style="display: none" class="form-control">
                                        <input id="startTime" type="text" class="form-control" readonly>
                                        <div class="input-group-addon">至</div>
                                        <input id="stopTime" type="text" class="form-control" style="border-radius: 0" readonly>
                                    </div>
                                </div>
                                <div class="col-sm-3" style="padding-left: 0;">
                                    <button class="btn btn-primary form-control" onclick="initTeacherDining()" title="按左侧选择的时间段初始化就餐安排">初始化</button>
                                </div>
                            </div>
                        </div>
                        <table id="teacherDiningTable">

                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="changeDining()">保存修改</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>
</div>
<!-- date-range-picker -->
<script src="${ctxsta}/common/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="${ctxsta}/common/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<script src="${ctxsta}/common/layui/layui/layui.js" charset="utf-8"></script>
<!--main content end-->
<script>
console.log($("#classId1").val())
   // $(function () {
   //     $('[data-toggle="tooltip"]').tooltip()
   // })
    $("#find").click(function () {
        var schoolName = $("#schoolName").selected().val();
        var number = $("#number").val();
        var month = $("#month").val();
        $("#table_teach_dining").bootstrapTable('refresh');
    });



    $("#reset").click(function () {
        $("#schoolName").val("");
        $("#number").val("");
        $("#month").val("");
    });

    laydate.render({
        elem: '#month'
        ,type: 'month'
        ,theme: '#6CA6CD'
        ,isInitValue: true
        /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });
    //加载教师表
    function initTableForBind() {
        teacherTableInit();
    }
    //重新加载教师表
    function reInitTeacherTable() {
        $("#teacher-info").bootstrapTable("destroy")
        teacherTableInit();
    }
    //重新加载班级表
    function reInitClassTable() {
        $("#class-info").bootstrapTable("destroy")
        classTableInit();
    }
    function bind() {
        var teacherId = $("#teacherId").val();
        var bindClassId = $("#bindClassId").val();
        var classId = $("#classId").val();
        console.log("教师id："+teacherId+"/教师绑定班级："+bindClassId+"/班级id:"+classId)
        if (!teacherId){
            layer.msg("请从右边教师信息表中选择老师")
        } else if (bindClassId){
            layer.msg("这个老师已经绑定到一个正在开班的班级了，请选择其他老师");
        }else if (classId){
            layer.msg("绑定中...")
            $.ajax({
                type: "post",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: ctx+"/TeacherDiningForScene/bindOperate.action" ,//url
                data: {"teacherId":teacherId,"classId":classId},
                success:function (data) {
                    console.log(data)
                    if(data.message =="FAILED"){
                        layer.msg("绑定失败")
                    }else{
                        layer.msg("绑定成功")
                    }
                    //销毁表格
                    $("#teacher-info").bootstrapTable("destroy");
                    $("#class-info").bootstrapTable("destroy");
                    //重新加载
                    initTableForBind();
                    //清空teacher Input
                    $("#teacherId").val("");
                    $("#teacherName").val("");
                    $("#bindClassId").val("");
                    // 清空class Input
                    $("#classId").val("");
                    $("#className").val("");
                    $("#bindTeacherId").val("");
                },
                error:function () {
                    layer.msg("绑定请求异常")
                }


            })
        } else{
            layer.msg("请选择一个班级");
        }
        // console.log(teacherId+classId);
    }
    function unBind() {
        var teacherId = $("#teacherId").val();
        var bindClassId = $("#bindClassId").val();
        console.log("教师id："+teacherId+"/教师绑定班级："+bindClassId)
        if (!teacherId){
            layer.msg("请选择一位老师进行解绑操作")
        } else if (!bindClassId){
            layer.msg("该教师没有绑定班级，无需解绑")
        } else{
            $.ajax({
                type: "post",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: ctx+"/TeacherDiningForScene/unBindOperate.action" ,//url
                data: {"teacherId":teacherId,"classId":bindClassId},
                success:function (data) {
                    console.log(data)
                    //销毁表格
                    $("#teacher-info").bootstrapTable("destroy");
                    $("#class-info").bootstrapTable("destroy");
                    //重新加载
                    initTableForBind();
                    //清空teacher Input
                    $("#teacherId").val("");
                    $("#teacherName").val("");
                    $("#bindClassId").val("");
                    // 清空class Input
                    $("#classId").val("");
                    $("#className").val("");
                    $("#bindTeacherId").val("");
                }
            })
        }
    }
    function initTeacherDining() {
        var teacherId = $("#teacherDiningId").val();
        var classId = $("#teacherDiningClassId").val();
        var startTime =$("#startTime").val();
        var stopTime =$("#stopTime").val();
        $.ajax({
            type: "post",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: ctx+"/TeacherDiningForScene/initTeacherDining.action" ,//url
            data: {"teacherId":teacherId,"classId":classId,"startTime":startTime,"stopTime":stopTime},
            success:function (data) {
                console.log(data)
                layer.msg(data.message)
                $("#teacherDiningTable").bootstrapTable("destroy")
                teacherDiningTableInit();
            }
        })
    }
</script>
</body>
</html>
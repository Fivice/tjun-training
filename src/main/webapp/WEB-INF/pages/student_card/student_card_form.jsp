<%
    String path = request.getContextPath();
    String projectPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>培训学员信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="../static/css/studentRegisterBySelfForm.css">
    <link rel="stylesheet" href="../static/common/ztree/css/bootstrapStyle/bootstrapStyle.css" />
    <link rel="stylesheet" href="../static/common/layui/layui/css/layui.css" />
    <link rel="stylesheet" href="../static/common/font-awesome/css/font-awesome.min.css" />
    <link rel="icon" href="../static/img/login/logo.ico"/>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
</head>
<body>
<section class="panel">
<input id="basePath" type="hidden" value="<%=projectPath%>">
    <header class="panel-heading custom-tab navbar-fixed-top">
        <ul class="nav nav-pills navbar-header">
            <li class="active">
                <a href="#class-info" data-toggle="tab" aria-expanded="true">班级信息</a>
            </li>
            <li id="personal-info" class="">
                <a href="#search" data-toggle="tab" aria-expanded="false">个人信息</a>
            </li>
            <li class="dropdown">
                <a href="#" id="more-info" class="dropdown-toggle" data-toggle="dropdown">
                    更多
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="more-info">

                    <li><a href="#training-schedule" tabindex="-1" data-toggle="tab">培训日程</a></li>
                    <li><a href="#training-history" tabindex="-1" data-toggle="tab">培训记录</a></li>
                    <li><a href="#stu-dining" id="dining" tabindex="-1" data-toggle="tab">就餐安排</a></li>
                    <li><a id="file" href="#download" tabindex="-1" data-toggle="tab">资料下载</a></li>
                </ul>
            </li>
        </ul>
    </header>
    <div class="panel-body">
        <div class="tab-content">
            <div class="tab-pane active" id="class-info">
                <input id="classEva" type="hidden" value="${classInfo.evaluate}">
                <input id="reg" type="hidden" value="${reg.status}">
                <div class="class-info-title">
                    ${classInfo.className}
                </div>
                <div class="class-info-table">
                    <table class="table table-bordered table-striped">
                        <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th>项目</th>--%>
                            <%--<th>内容</th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>
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
            <div class="tab-pane" id="search">
                <div class="sign-up-info">
                    <form  class="form-horizontal" role="form">
                        <input type="hidden" id="siId" name="siId" value="${student.siId}"/>
                        <input type="hidden" name="createBy" value="${student.createBy}"/>
                        <input type="hidden" name="createDate" value="${student.createDate}"/>
                        <input type="hidden" name="serialNumber" value="${student.serialNumber}"/>
                        <input type="hidden" name="siDepartmentId" value="${student.siDepartmentId}"/>
                        <input type="hidden" name="deparentmentName" value="${student.deparentmentName}"/>
                        <input type="hidden" name="siIDNumber" value="${student.siIDNumber}"/>
                        <input type="hidden" name="photo" value="${student.photo}"/>
                        <input type="hidden" name="status" value="${student.status}"/>
                        <input type="hidden" name="sex" value="${student.sex}"/>
                        <input type="hidden" name="post" value="${student.post}"/>
                        <input type="hidden" name="skillLevel" value="${student.skillLevel}"/>
                        <input type="hidden" name="siAddress" value="${student.siAddress}"/>
                        <input type="hidden" name="remarks" value="${student.remarks}"/>
                        <input type="hidden" id="stu-nation" value="${student.ethnicGroup}">
                        <%--需要提交的数据--%>
                        <input id="cyids" type="hidden"  value=${student.siUnitId } name="siUnitId" />
                        <input id="stu-classId" type="hidden"  value=${classInfo.id} />
                        <input id="stu-id" type="hidden"  value=${student.siId} />
                        <input id="idNumber" type="hidden"  value=${student.siIDNumber} />
                        <input id="evaluateStartTime" type="hidden" value="${classInfo.evaluateStartTime}">
                        <input id="evaluateStopTime" type="hidden" value="${classInfo.evaluateStopTime}">

                        <div class="form-group">
                            <label for="siName" class="col-xs-2 control-label">姓名</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="siName" name="siName" value="${student.siName}" placeholder="请输入姓名(必填)">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="siName" class="col-xs-2 control-label">员工编号</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="siNumber" name="siNumber" value="${student.siNumber}" placeholder="请输入员工编号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber" class="col-xs-2 control-label">手机号</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${student.phoneNumber}" placeholder="请输入手机号(必填)">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-xs-2 control-label">Email</label>
                            <div class="col-xs-10">
                                <input type="text" class="form-control" id="email" name="email" value="${student.email}" placeholder="请输入邮箱">
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
                            <div class="col-xs-5" style="padding-right: 1px">
                                <select class="form-control" id="host-units">

                                </select>
                            </div>
                            <div class="col-xs-5" style="padding-left: 1px">
                                <select class="form-control" id="departments">

                                </select>
                            </div>
                        </div>
                        <%--<div class="form-group">
                            <label class="control-label col-xs-2" title=""> 单位</label>
                            <div class="col-xs-10">
                                <input id="ChanYeName"  name="unitName" type="text"  class="form-control"  placeholder="" readonly="readonly" value=${student.unitName } >
                                <input id="cyids" type="hidden"  value=${student.siUnitId } name="siUnitId" /> <!--若需要节点id，将节点id存储在此，然后上传到服务器-->
                                <div id="info" style="z-index: 1;position:absolute; width:336px; height:200px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                                    <ul id="haveclasstree" class="ztree"></ul>
                                </div>
                            </div>

                        </div>--%>
                        <div class="form-group">
                            <div class="col-xs-offset-2 col-xs-10">
                                <a href="#" class="btn btn-primary" style="width: 50%" onclick="updateBySelf()">修改</a>
                            </div>
                        </div>
                        <a style="color: #6CA6CD" onclick="myFunction()" >点击进入培训评价管理</a>
                    </form>
                </div>
            </div>

            <div class="tab-pane fade" id="training-schedule">
                <input type="hidden" id="classId" name="classId" value="${classInfo.id}">
                <input  name="day" id="day" type="hidden">
                <input  name="stucardId" id="stucardId" type="hidden" value="${id}">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        培训日程安排如下：
                    </div>
                    <!-- Table -->
                    <div class="table-responsive">
                    <table id="scheduling" class="table text-nowrap">

                    </table>
                    </div>
                </div>

            </div>
            <%--培训记录--%>
            <div class="tab-pane fade" id="training-history">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        已完成培训：
                    </div>
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
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        就餐安排：
                    </div>
                    <!-- Table -->
                    <table id="stu-dining-table" class="table">

                    </table>
                </div>

            </div>
            <div class="tab-pane fade" id="download">
                <input id="fileUrl" value="${classInfo.fileUrl}" hidden>
                <table class="table">
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
<script src="../static/jquery/jquery-3.2.1.js"></script>
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script src="../static/bootstrap/table/bootstrap-table.js" type="text/javascript"></script>
<script src="../static/cms/js/common.js"></script>
<%--<script src="../static/entity/student/tree4.js" type="text/javascript"></script>--%>
<script src="../static/common/layer/layer.js"></script>
<script src="../static/bootstrap/table/locale/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<script src="../static/common/ztree/js/jquery.ztree.core.js" type="text/javascript"></script>
<script src="../static/common/ztree/js/jquery.ztree.excheck.js" type="text/javascript"></script>
<script src="../static/common/ztree/js/jquery.ztree.exedit.js" type="text/javascript"></script>
<script src="../static/entity/student/bootstrapValidator_regStudent.js" type="text/javascript"></script>
<script src="../static/entity/scheduling/register_scheduling.js"></script>
<script src="../static/entity/scheduling/diningInfo.js"></script>
</body>
<script type="text/javascript">
    $(function () {
        if (top.location != location){
            top.location.href = location.href;
        }
    })
    //title提示
    $(function () { $("[data-toggle='tooltip']").tooltip(); });
    //培训记录
    $(function () {
        $("#training").click(function () {
            var idNumber = $("#idNumber").val()
            var studentId = $("#stu-id").val()
            console.log(idNumber)
            var temp;
            if (studentId == null||studentId =="") {
                layer.msg("您还没有报名,没有相关记录")
            }else if(idNumber!==null&&idNumber!==""){
                $.ajax({
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: ctx+"/remoteOperate/studentTrainingHistory.action" ,//url
                    data: {"siIdNumber":idNumber},
                    success:function (data) {
                        console.log(data)
                        if (data.length==0){
                            layer.msg("没有培训记录")
                        }else{
                            for (i in data){
                                var num =parseInt(i)+parseInt(1);
                                temp +="<tr><td>"+num+"</td> <td>"+data[i].className+"</td> <td>"+data[i].hostUnit+"</td> <td>"+data[i].startStopTime+"</td> <td>"+data[i].days+"</td> <td>"+data[i].classHours+"</td></tr>"
                            }
                            console.log(temp)
                            $("#training-history-body").html(temp)
                        }
                    },
                    error:function () {
                        layer.alert("查询培训记录异常");
                    }
                })
            }
        })
    })
    //获取民族json
    $(function () {
        var nation = $("#stu-nation").val();
        var nationId = "1";
        $.ajax({
            url:"${ctx}/remoteOperate/formNationList.action",
            type: "get",//请求方式
            dataType: "json",//数据类型可以为 text xml json  script  jsonp
            success: function(data){//返回的参数就是 action里面所有的有get和set方法的参数
                console.log(data)
                var temp="";
                for (var i in  data) {
                    if (data[i].name == nation){
                        nationId = data[i].id;
                    }
                    temp+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"
                    // console.log(result[i].id);
                }
                $("#nation").html(temp);
                $("#nation").val(nationId)
            }
        })

    })
    //文件下载列表
    $(function () {
        $("#file").click(function () {
            var fileUrl = $("#fileUrl").val();
            console.log(fileUrl)
            var temp ="";
            if (fileUrl==null||fileUrl==""){
                temp+= "<tr><td>没有上传文件</td><td></td></tr>"
            } else{
                var fileArr = String(fileUrl).split(",");
                console.log(fileArr)

                for (var i = 0; i < fileArr.length; i++) {
                    var arr = fileArr[i].split("/");
                    console.log(arr)
                    temp+="<tr><td>"+arr[3]+"</td><td><a href=\'"+fileArr[i]+"\'>下载</a></td></tr>"
                }
            }
            $("#file-list").html(temp)
        })
    })
    //更新学生信息
    function updateBySelf() {
        var stuInfo = {
            classId : $("#stu-classId").val(),
            //siIdNumber:$("#idNumber").val(),
            siId : $("#stu-id").val(),
            siName : $("#siName").val(),//
            siPhone : $("#phoneNumber").val(),//
            email : $("#email").val(),//
            stuNumber : $("#siNumber").val(),//
            firstUnitSelect : $("#host-units").val(),//
            nextUnitSelect : $("#departments").val(),
            nation: $("#nation").val(),
            //regStatus : $("#stu-reg-status").val()
        }
        //console.log(stuInfo)

        var phonePattern = /^1[34578]\d{9}$/;
        if (stuInfo.siName === ""){
            layer.msg("姓名不能为空")
        }else if (stuInfo.siPhone === ""){
            layer.msg("电话不能为空")
        }else if (!phonePattern.exec(stuInfo.siPhone)){
            layer.msg("请输入正确的手机号")
        }else{
            layer.msg("修改中...")
            console.log(stuInfo)
            $.ajax({
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: "${ctx}/remoteOperate/studentInfoSave.action" ,//url
                data: JSON.parse(JSON.stringify(stuInfo)),
                success:function (data) {
                    layer.msg(data.message)
                    //clearRegisterForm();
                },
                error:function () {
                    layer.alert("修改失败");
                }
            })

        }
    }
    //获取单位Id
    $(function () {
        $("#personal-info").click(function () {
            console.log($("#cyids").val())
            setUnitSelect($("#cyids").val());
        })
    })
    var result;//用于保存ajax返回的单位分级信息数据
    //进页面从服务器获取部分级别表并初始化
    $(function () {
        $.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "${ctx}/remoteOperate/unitGradList.action" ,//url
            success:function (data) {
                result = data;
                var selectContext = "<option value=\""+"-1"+"\">"+"请选择"+"</option>";
                for (var i in result) {
                    selectContext +="<option value=\""+result[i].unitId+"\">"+result[i].unitName+"</option>"
                }
                $("#host-units").html(selectContext);

            },
            error:function () {
                layer.alert("获取单位信息失败");
            }
        })
    })
    //当select选中发生变化时，触发下面函数来改变下级单位的select列表
    $(function(){
        $("#host-units").change(function () {
            // console.log($("#host-units").val())
            var nextSelectContext = "<option value=\""+"-1"+"\">"+"请选择"+"</option>";
            var selectUnitId = $("#host-units").val();
            var resultChild ;
            for (var i in result) {
                var unitId = result[i].unitId;
                if(selectUnitId == unitId){
                    resultChild = result[i].unitVOList;
                    for(var j in resultChild){
                        nextSelectContext +="<option value=\""+resultChild[j].unitId+"\">"+resultChild[j].unitName+"</option>"
                    }
                }
            }
            $("#departments").html(nextSelectContext);
        })
    })
    function setUnitSelect(unitId){
        //console.log("部门id："+unitId)
        var nextSelectContext = "<option value=\""+"-1"+"\">"+"请选择"+"</option>";
        var temp = -1;
        var nextTemp =-1;
        for(var i in result){
            //在一级部门里查找
            var id = result[i].unitId;
            if(unitId == id){
                //在一级部门里找到
                $("#host-units").val(id)
                temp = id;
                //console.log("在一级部门里找到："+id)

            }else{
                var childResult = result[i].unitVOList;
                for(var j in childResult){
                    var cId = childResult[j].unitId;
                    nextSelectContext +="<option value=\""+childResult[j].unitId+"\">"+childResult[j].unitName+"</option>"
                    if(unitId == cId){
                        //在二级部门里找到
                        nextTemp = cId;
                        $("#host-units").val(result[i].unitId)
                        console.log("在二级部门里找到："+cId)

                    }
                }
                $("#departments").html(nextSelectContext);

                $("#departments").val(nextTemp);

            }

        }
    }
    function  myFunction() {
        var classId = $('#classId').val();
        var siId = $('#siId').val();
        var classEva = $('#classEva').val();
        var status = $('#reg').val();
        var evaluateStartTime = new Date(Date.parse($('#evaluateStartTime').val()));
        console.log(evaluateStartTime);
        var evaluateStopTime =new Date(Date.parse($('#evaluateStopTime').val()));
        console.log(evaluateStopTime)
        var curDate = new Date();



        if(classEva != null && classEva == 0 ){
            if(curDate >= evaluateStartTime && curDate <= evaluateStopTime){
                if(status != null && status== 0){
                    window.location.href ='${ctx}/studentCard/judgeView.action?classId=' + classId+'&siId='+siId;
                }else{
                    layer.msg('您已评价!', {
                        icon: 6,
                        time: 1000
                    })
                }
            }else if(curDate < evaluateStartTime){
                layer.msg('抱歉，评价暂未开始！', {
                    icon: 7,
                    time: 1000
                })
            }else if(curDate > evaluateStopTime){
                layer.msg('抱歉，评价已结束！', {
                    icon: 7,
                    time: 1000
                })
            }
        }else{
            layer.msg('该班级未参评!', {
                icon: 7,
                time: 1000
            })
        }

    }
</script>
</html>


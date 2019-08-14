<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/stuDinRecord/stuDinRecord.js"></script>
    <script src="${ctxsta }/entity/teachDinRecord/teachDinRecord.js"></script>
    <script src="${ctxsta }/entity/teachDinFaceRecord/teachDinFaceRecord.js"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>

</head>
<body class="fixed-sidebar full-height-layout gray-bg" onload="init()">

<div class="wrapper wrapper-content">
    <section id="main-content">
        <section class="wrapper">
            <!-- page start-->
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading tab-bg-dark-navy-blue ">
                            <ul class="nav nav-tabs" id="nav-ul">
                                <li class="active">
                                    <a data-toggle="tab" id="clickM" onclick="saveStudentData()" href="#stuDinTableContent">学员就餐记录</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" id="clickMe" onclick="initTeachDinTable(),saveTeachData()"
                                       href="#teachDinTableContent">教师就餐记录(教师证)</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" id="clickMee" onclick="initTeachDinTableByFace(),saveTeachFaceData()"
                                       href="#teachDinTableContentByFace">教师就餐记录(人脸识别)</a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div id="stuDinTableContent" class="tab-pane active">
                                    <div class="col-sm-12">
                                        <div class="btn-group m-t-sm">
                                            <select style="width: 200px;height: 35px;border-color:#E0E0E0;"
                                                    id="schoolName"
                                                    name="schoolName">
                                                <option value="">请选择就餐地点</option>
                                                <c:forEach items="${list}" var="list">
                                                    <option value=${list}>${list}</option>
                                                </c:forEach>
                                                <%--  <option value="其他">其他</option>--%>
                                            </select>
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="classRoom" id="classRoom"
                                                   placeholder="请输入完整的班级编号">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="className" id="className"
                                                   placeholder="请输入班级名称">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="studentName" id="studentName"
                                                   placeholder="请输入学员姓名">
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="month" id="month"
                                                   value="${month}"
                                                   placeholder="请选择就餐月份">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <button id="find" type="button" class="btn btn-primary">查询</button>&emsp;
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <button id="reset" type="button" class="btn">清空</button>
                                        </div>
                                        <!-- 数据表格 -->
                                        <table id="table_stud_dining_record" class="table table-hover"></table>
                                    </div>
                                </div>
                                <div id="teachDinTableContent" class="tab-pane">
                                    <div class="col-sm-12">
                                        <div class="btn-group m-t-sm">
                                            <select style="width: 200px;height: 35px;border-color:#E0E0E0;"
                                                    id="schoolName1"
                                                    name="schoolName">
                                                <option value="">请选择就餐地点</option>
                                                <c:forEach items="${list}" var="list">
                                                    <option value=${list}>${list}</option>
                                                </c:forEach>
                                            </select>
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="classRoom" id="classRoom1"
                                                   placeholder="请输入完整的班级编号">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="className" id="className1"
                                                   placeholder="请输入班级名称">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="month" id="month1"
                                                   value="${month}"
                                                   placeholder="请选择就餐月份">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <button id="find1" type="button" class="btn btn-primary">查询</button>&emsp;
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <button id="reset1" type="button" class="btn">清空</button>
                                        </div>
                                        <!-- 数据表格 -->
                                        <table id="table_teach_dining_record" class="table table-hover"></table>
                                    </div>
                                </div>
                                <div id="teachDinTableContentByFace" class="tab-pane">
                                    <div class="col-sm-12">
                                        <div class="btn-group m-t-sm">
                                            <select style="width: 200px;height: 35px;border-color:#E0E0E0;"
                                                    id="schoolName2"
                                                    name="schoolName">
                                                <option value="">请选择就餐地点</option>
                                                <c:forEach items="${list}" var="list">
                                                    <option value=${list}>${list}</option>
                                                </c:forEach>
                                            </select>
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="classRoom" id="classRoom2"
                                                   placeholder="请输入完整的班级编号">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="className" id="className2"
                                                   placeholder="请输入班级名称">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <input type="text" class="form-control" name="month" id="month2"
                                                   value="${month}"
                                                   placeholder="请选择就餐月份">
                                        </div>&emsp;
                                        <div class="btn-group m-t-sm">
                                            <button id="find2" type="button" class="btn btn-primary">查询</button>&emsp;
                                        </div>
                                        <div class="btn-group m-t-sm">
                                            <button id="reset2" type="button" class="btn">清空</button>
                                        </div>
                                        <!-- 数据表格 -->
                                        <table id="table_teach_dining_face_record" class="table table-hover"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>


        </section>
    </section>
</div>

<script>
    $(function () {
        var attrArr = [];
        $("#nav-ul li").each(function () {         //遍历ul中每个li的类属性
            var y = $(this);
            //        console.log(y.attr("class"));
            attrArr.push(y.attr("class"));       //把遍历出来的值存入一个数组
        });
        //    console.log(attrArr);
        sessionStorage.setItem("arr", JSON.stringify(attrArr));     //把数据存入sessionStorage
    });

    function init() {
        var arrArr =JSON.parse(sessionStorage.getItem("arr")); //从sessionStorage中取出数组当前停留在哪
        console.log(arrArr)
        var obj0 = ["active","",""];  //学生就餐记录
        var obj1 = ["","active",""];  //教师就餐记录（number）
        var obj2 = ["","","active"];  //教师就餐记录（人脸）

        if(arrArr.toString() == obj0.toString()){ //当sessionStorage与学生就餐一致时

            $("#clickM").click();  //触发点击事件显示学生记录块
        }
        if(arrArr.toString() == obj1.toString()){

            $("#clickMe").click();//触发点击事件显示教师记录块
        }
        if(arrArr.toString() == obj2.toString()){

            $("#clickMee").click();//触发点击事件显示教师（人脸）记录块
        }

    }

    function saveStudentData() {
        var obj = ["active","",""];  //初始化学生并存入sessionStorage
        sessionStorage.setItem("arr",JSON.stringify(obj));
    }

    function saveTeachData() {
        var obj = ["", "active", ""];//初始化教师并存入sessionStorage
        sessionStorage.setItem("arr", JSON.stringify(obj));
    }

    function saveTeachFaceData() {
        var obj = ["","","active"];//初始化教师（人脸）并存入sessionStorage
        sessionStorage.setItem("arr",JSON.stringify(obj));
    }


    $("#find").click(function () {
        var schoolName = $("#schoolName").selected().val();
        var classRoom = $("#classRoom").val();
        var className = $("#className").val();
        var studentName = $("#studentName").val();
        var month = $("#month").val();
        $("#table_stud_dining_record").bootstrapTable('refresh');
    })
    $("#reset").click(function () {
        $("#schoolName").val("");
        $("#classRoom").val("");
        $("#className").val("");
        $("#studentName").val("");
        $("#month").val("");
    })

    laydate.render({
        elem: '#month'
        , type: 'month'
        , theme: '#6CA6CD'
        , isInitValue: true
        /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });
    laydate.render({
        elem: '#month1'
        , type: 'month'
        , theme: '#6CA6CD'
        , isInitValue: true
        /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });
    laydate.render({
        elem: '#month2'
        , type: 'month'
        , theme: '#6CA6CD'
        , isInitValue: true
        /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });


    $("#find1").click(function () {
        $("#table_teach_dining_record").bootstrapTable('refresh');
    });
    $("#reset1").click(function () {
        $("#schoolName1").val("");
        $("#classRoom1").val("");
        $("#className1").val("");
        $("#studentName1").val("");
        $("#month1").val("");
    });

    $("#find2").click(function () {
        $("#table_teach_dining_face_record").bootstrapTable('refresh');
    });
    $("#reset2").click(function () {
        $("#schoolName2").val("");
        $("#classRoom2").val("");
        $("#className2").val("");
        $("#studentName2").val("");
        $("#month2").val("");
    });

    //加载教师表
    function initTeachDinTable() {
        teacherDingTable();
    }

    //加载教师表（人脸）
    function initTeachDinTableByFace() {
        teacherDingTableByFace();
    }

</script>
</body>
</html>

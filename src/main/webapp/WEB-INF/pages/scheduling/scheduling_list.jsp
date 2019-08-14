<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>日程安排</title>
    <script src="${ctxsta }/common/bootstrap-switch/js/bootstrap-switch.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/scheduling/bootstrpValidator.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script type="text/javascript" src="${ctxsta }/FileUploadQT/js/jquery.media.js"></script>
    <script src="${ctxsta}/entity/scheduling/scheduling.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctxsta}/common/bootstrap-switch/css/bootstrap-switch.css"/>
</head>
<body class="gray-bg">
<div id="cc">
    <input type="hidden" id="id" name="id" value="${classInfo.id}">
    <div id="importBox" class="hide">
    <form id="importForm" action="${ctx}/scheduling/import.action"
          method="post" enctype="multipart/form-data" class="form-search"
          style="padding-left: 20px; text-align: center;"
          onsubmit="/*return checkFile()*/layer.msg('正在导入，请稍等...');">
        <div class="btn-group">
            <label for = "file" class="btn btn-default"><i class="fa fa-plus-square-o"></i>选择文件</label>
            <input type="file" id="file" onchange="addEnclosures()" style="display: none" />
        </div>



        <a  style="color: #6CA6CD" href="${ctx}/scheduling/template.action">下载模板</a><br /> <br />
        <p>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！</p>
    </form>
</div>
    <sys:message content="${message }"/>
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <div class="col-sm-12">
                            <h3>[${classInfo.classNumber}]${classInfo.className}  ${classInfo.hostUnit}&nbsp;&nbsp;日程安排  开班时间：${classInfo.startStopTime}  培训天数：${classInfo.dayNum}天</h3>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <!-- 工具栏 -->
                        <div id="toolbar">
                            <input type="hidden" id="contextPath" value="${contextPath }"/>

                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-default" title="添加日程安排"
                                        onclick="createSch(${classInfo.id})"><i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-default" title="编辑日程安排"
                                        onclick="update(${classInfo.id})">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-default" title="删除"
                                        onclick="del1()" >
                                    <i class="glyphicon glyphicon-minus"></i>
                                </button>
                            </div>
                            <div class="btn-group m-t-sm" style="margin-left: 15px;width:190px;height:30px">
                                <input class="form-control" name="day" placeholder="请选择日期" id="day" type="text">
                            </div>
                            <%--<div class="btn-group m-t-sm">
                                <span> 教室：</span>
                                <select id="className" style="width: 200px;height: 35px;border-color:#E0E0E0;"
                                        name="className">
                                    <option value="">全部</option>
                                    <c:forEach items="${classroom}" var="classroom">
                                        <option value="${classroom.className}">${classroom.className}</option>
                                    </c:forEach>
                                </select>
                            </div>--%>

                            <div class="btn-group m-t-sm">
                                <button id="search" type="button" class="btn btn-primary">查询</button>&emsp;
                            </div>
                            <div class="btn-group m-t-sm">
                                <button id="empty" type="button" class="btn">清空</button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-info"  onclick="daoru()">批量导入</button>
                            </div>
                        </div>
                        <table id="table_scheduling" class="table table-bordered table-striped"></table>
                        <div class="btn-group m-t-sm">
                            <button type="button" class="btn btn-primary"  title="返回班级管理" onclick="window.history.back()"
                            >返回班级管理</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var cid = $('#classId').val();
    laydate.render({
        elem: '#day',
        type: 'date', //默认，可不填

    });
    $("#search").click(function () {
        // var type = $('#type').val();
        // var startTime = $("#startTime").val();
        // var endTime = $("#endTime").val();
        // window.location.href=ctx +'/powerCheck/powerSystem.action';
        $("#table_scheduling").bootstrapTable('refresh');
    })
    $("#empty").click(function () {
        $("#day").val("");
        $("#areaId").val("");
    })

    //批量导入
    function daoru(){
        layer.open({
            type:1
            ,
            title:"导入数据"
            ,content: $('#importBox').html()
        });
    }
    //表格导入
    var addEnclosures = function () {
        var fileDir = $("#file").val();
        console.log(fileDir)
        var suffix = fileDir.substr(fileDir.lastIndexOf("."));
        console.log(suffix)
        if(".xls" != suffix && ".xlsx" != suffix){
            layer.msg('选择Excel格式的文件导入！', {
                icon : 7,
                time : 2000
            });
        }else{
            //格式正确，上传文件
            var id = $('#id').val();
            var enclosure = new FormData();
            enclosure.append("file",document.getElementById("file").files[0]);

            //上传附件
            $.ajax({
                url: ctx+"/scheduling/import.action?id="+id,
                type: "POST",
                data: enclosure,
                /**
                 *必须false才会自动加上正确的Content-Type
                 */
                contentType: false,
                /**
                 * 必须false才会避开jQuery对 formdata 的默认处理
                 * XMLHttpRequest会对 formdata 进行正确的处理
                 */
                processData: false,
                success: function (data) {
                    console.log(data);
                    data = JSON.parse(data);
                    layer.msg(data.msg, {
                        icon : 1,
                        time : 2000
                    }, function() {
                        window.location.reload(); // 刷新父页面
                    });

                },
                error: function (data) {
                    console.log(data);
                    data = JSON.parse(data);
                }
            });
        };
        }





</script>
</body>
</html>
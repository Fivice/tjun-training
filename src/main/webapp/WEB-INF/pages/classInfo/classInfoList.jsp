<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>班级列表</title>
    <script src="${ctxsta }/entity/classInfo/classInfo.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script type="text/javascript" src="${ctxsta }/FileUploadQT/js/jquery.media.js"></script>
  </head>
<body class="fixed-sidebar full-height-layout gray-bg">
<!-- 導入彈框 -->
<div id="importBox" class="hide">
    <form id="importForm" action="${ctx}/classInfo/import.action"
          method="post" enctype="multipart/form-data" class="form-search"
          style="padding-left: 20px; text-align: center;"
          onsubmit="layer.msg('正在导入，请稍等...');">
        <br /> <input id="uploadFile" name="file" type="file"
                      value="" style="width: 330px" /><br /> <br />
        <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " />
        <a  style="color: #6CA6CD" href="${ctx}/classInfo/import/template.action">下载模板</a><br /> <br />
        <p>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！</p>
    </form>

</div>
<sys:message content="${message }"/>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>班级列表</h5>
                    <div class="ibox-tools"></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <!-- 工具栏 -->
                            <c:if test="${empty buserId }">
                              <div class="btn-group m-t-sm">
                                  <button type="button" class="btn btn-default" title="添加"  onclick="add()"> <i class="glyphicon glyphicon-plus"></i> </button>
                              </div>
                            </c:if>

                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-default" title="编辑" onclick="edit()"> <i class="glyphicon glyphicon-pencil"></i> </button>
                            </div>
                            <c:if test="${empty buserId }">
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-default" title="删除" onclick="del()"> <i class="glyphicon glyphicon-minus"></i> </button>
                            </div>
                            </c:if>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control"  name="startStopTime" id="startStopTime" value="${startStopTime }" placeholder="请选择开班时间">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="classNumber" id="classNumber" placeholder="请输入班级编号">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="className" id="className" placeholder="请输入班级名称">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="teacherName" id="teacherName" placeholder="请输入班主任名称">
                            </div>
                            <div class="btn-group m-t-sm">
                                <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="plan">
                                    <option value="">全部</option>
                                    <option value="0">计划内</option>
                                    <option value="1">计划外</option>
                                    <option value="2">非培训班</option>
                                </select>
                            </div>
                            <div class="btn-group m-t-sm">
                                <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="regPlace">
                                    <option value="">选择报名地点</option>
                                    <c:forEach items="${placeList }" var="place">
                                        <option value="${place }">${place }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-info" id="search" onclick="seach()"><i class="fa fa-search"></i>查询</button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button id="reset" type="button" class="btn" onclick="reset()">重置</button>
                            </div>
                            <!-- 导入 -->
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-info" id="btnImport" onclick="daoru()">批量导入</button>
                            </div>
                        </div>
                        <br><br>
                        <!-- 数据表格 -->
                        <div class="col-sm-9" style="margin-top: 10px;">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <i class="fa fa-list"></i>&nbsp;班级信息列表
                                    </div>
                                    <div class="panel-body" style="padding-top: 0">
                                        <table id="table" class="table table-hover"></table>
                                    </div>
                                </div>
                        </div>

                        <div class="col-sm-3">
                            <div style="margin-top: 10px;">
                                <div class="panel panel-default">
                                    <div class="panel-heading" style="display: flex;flex-direction: row;justify-content: space-between;padding-top: 5px;padding-bottom: 0px">
                                        <div style="align-self: center">
                                            <div>
                                                <i class="fa fa-send-o"></i> <span>短信通知</span>
                                            </div>
                                        </div>
                                        <button id="showMsgHistory" class="btn btn-default btn-sm " type="button">查看发送历史</button>
                                    </div>
                                    <div class="panel-body">
                                        <form>
                                            <div class="input-group">
                                                <input id="input-phone" type="text" class="form-control" placeholder="请输入手机号" name="inputPhone">
                                                <span class="input-group-btn">
                                                <button id="add-phone-to-send-list" class="btn btn-default" type="button">添加</button>
                                                <button id="add-many-phone-to-send-list" data-toggle="modal" data-target="#phoneListModal" class="btn btn-default" type="button">批量添加</button>
                                            </span>
                                            </div>
                                            <hr/>
                                            <div id="phone-to-send" class="text-center" style="max-height: 200px;overflow: auto">
                                                请添加手机号码<br/>
                                            </div>
                                            <hr/>
                                            <div class="form-group">
                                                <input readonly id="message-class" type="text" class="form-control" placeholder="请选择班级进行通知" name="selectClass"/>
                                            </div>
                                            <button id="send-messages" type="button" class="btn btn-primary btn-block">短信通知</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal -->
                        <div class="modal fade" id="phoneListModal" tabindex="-1" role="dialog" aria-labelledby="phoneModalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="phoneModalLabel"><i class="fa fa-list-ul"></i> 批量添加号码</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="btn-group">
                                                    <button class="btn btn-default" onclick="PhoneListData.Validate('phoneList','validateResult')">校验</button>
                                                    <button class="btn btn-default" onclick="PhoneListData.Import('phoneList')">导入</button>
                                                    <button class="btn btn-default" onclick="PhoneListData.Clear('phoneList','validateResult')">清空</button>
                                                </div>
                                            </div>
                                            <div class="col-sm-6">
                                                <label>
                                                    添加号码：(每行只能有一个号码)
                                                    <textarea id="phoneList" rows="20" style="width: 100%;resize: none"></textarea>
                                                </label>
                                            </div>
                                            <div class="col-sm-6">
                                                <label>
                                                    校验结果：
                                                    <textarea id="validateResult" rows="20" style="width: 100%;resize: none" readonly></textarea>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal -->
                        <div class="modal fade" id="sendMsgHistoryModal" tabindex="-1" role="dialog" aria-labelledby="msgHistoryModalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="msgHistoryModalLabel"><i class="fa fa-history"></i> 查询班级短信发送历史</h4>
                                    </div>
                                    <div class="modal-body">
                                        <%--<div class="row">
                                            <div class="col-sm-12">
                                                <form class="form-inline">
                                                    <div class="form-group">
                                                        <label for="exampleInputName2">Name</label>
                                                        <input type="text" class="form-control" id="exampleInputName2" placeholder="Jane Doe">
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="exampleInputEmail2">Email</label>
                                                        <input type="email" class="form-control" id="exampleInputEmail2" placeholder="jane.doe@example.com">
                                                    </div>
                                                </form>
                                            </div>
                                        </div>--%>
                                        <table id="history-body">

                                        </table>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
<script src="${ctxsta}/entity/classInfo/sendMessage.js" type="text/javascript"></script>
<script src="${ctxsta}/entity/classInfo/addPhoneList.js" type="text/javascript"></script>
<script src="${ctxsta}/entity/classInfo/msgSendHistory.js" type="text/javascript"></script>
<script>

    // 新建
    function add(){
        location = "${ctx}/classInfo/form.action";
    }
    // 编辑
    function edit() {
        var getSelectRows=$("#table").bootstrapTable('getSelections');
        ids = [];
        for (var i = 0; i < getSelectRows.length; i++) {
            ids.push(getSelectRows[i].id)
        }
       commonEdit(ids,"${ctx}/classInfo/form.action?id="+ids[0]);

    }
    // 批量删除数据
    function del() {
        var getSelectRows=$("#table").bootstrapTable('getSelections');
        ids = [];
        for (var i = 0; i < getSelectRows.length; i++) {
            ids.push(getSelectRows[i].id)
        }
        //commonDel(ids,"${ctx}/classInfo/delete.action");

        if (ids.length == 0) {
            layer.alert('请至少选择一条数据！', {
                icon : 5,
                title : "提示"
            });
        } else {
        //查询是否有就餐安排
        $.ajax({
            type : 'POST',
            url : "${ctx}/classInfo/searchDining.action",// 发送请求
            data : {

                iNumbers : ids.toString()
            },
            dataType : "json",
            success : function(result) {
                //存在班级就餐安排或者存在教师就餐安排
                if (result == "-1") {
                    layer.confirm("已存在就餐安排信息,确定将会删除对应班级的就餐信息？", {
                        btn : [ '确定', '取消' ]
                        // 按钮
                    }, function() {
                        deleClassInfo(ids);
                    })
                }else{
                    layer.confirm("您确定要删除数据吗？", {
                        btn : [ '确定', '取消' ]
                        // 按钮
                    }, function() {
                        deleClassInfo(ids);
                    })
                }

            },
        });
        }

    }

    //删除班级信息
    function deleClassInfo(ids) {
            $.ajax({
                type : 'POST',
                url : "${ctx}/classInfo/delete.action",// 发送请求
                data : {
                    iNumbers : ids.toString()
                },
                dataType : "json",
                success : function(result) {
                    if (result.code == 1) {
                        parent.layer.msg("删除成功!", {
                            shade : 0.3,
                            time : 1500
                        }, function() {
                            window.location.reload(); // 刷新父页面
                        });
                    } else if(result.code == -1){
                        layer.alert('存在已经有学生报到过的班级！', {
                            icon : 5,
                            title : "提示"
                        });
                    } else {
                        layer.msg(result.message, {
                            icon : 2,
                            time : 1000
                        });
                    }

                }

        });
    }

    //清空input
    function reset(){
        $("input").prop("value","");
        $("#plan").prop("value","");
        $("#regPlace").prop("value","");
    }
    //年月范围选择
    laydate.render({
        elem: '#startStopTime'
        ,type: 'month'
        ,theme: '#6CA6CD'
       /* ,range: '至' //或 range: '~' 来自定义分割字符*/
    });
    //批量导入
    function daoru(){
        layer.open({
            type:1
            ,
            title:"导入数据"
            ,content: $('#importBox').html()
        });
    }


</script>
</body>
</html>
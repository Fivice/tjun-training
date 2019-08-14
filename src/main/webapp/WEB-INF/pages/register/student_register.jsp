<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>
    <script src="${ctxsta }/entity/student/students.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/student/tree.js" type="text/javascript"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-content">

        <div class="box-header with-border">
            <div class="box-title">学员报名登记
                <i class="fa fa-list-alt"></i>
            </div>
        </div>

<div class="form-horizontal">
            <input type="hidden" id="siId" name="siId" value="${student.siId}"/>
            <input type="hidden" name="createBy" value="${student.createBy}"/>
            <input type="hidden" name="createDate" value="${student.createDate}"/>
            <div style="margin-top: 20px">

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-2" title="">
                                <span class="required hide" aria-required="true">*</span> 单位：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input id="ChanYeName"  name="unitName" type="text"  class="form-control"  placeholder="请填写单位信息" readonly="readonly" value=${unit.areaName } >
                                <input id="cyids" type="hidden"  value=${student.siUnitId } name="siUnitId" /> <!--若需要节点id，将节点id存储在此，然后上传到服务器-->
                                <div id="info" style="z-index: 1;position:absolute; width:336px; height:200px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                                    <ul id="haveclasstree" class="ztree"></ul>
                                </div>
                                <label class="control-label">
                               <%-- <a  onclick="creatUnit()">未找到所属单位？点击添加单位</a>--%>
                                </label>
                            </div>

                        </div>

                    </div>


            </div>
            <div class="box-footer" style="margin-left: 300px">
                    <div class="row">
                        <div class="col-xs-4">
                            <div class="form-group">
                            </div>
                        </div>
                        <div class="col-sm-offset-2 col-sm-10">
                            <button  class="btn btn-sm btn-primary"  onclick="myfunction()"><i class="fa fa-check"></i> 确 定</button>&nbsp;
                            <button id="reset" type="button" class="btn" onclick="resetUnit()">清除</button>
                        </div>
                    </div>
            </div>
</div>
    </div>
</div>
<script>
    //清除选中的单位
    function resetUnit(){
        $("#cyids").prop("value","");
        $("#ChanYeName").prop("value","");
        $("#unitName").prop("value","");
    }
    function myfunction() {
        var siUnitId = $('#cyids').val();
        console.log(siUnitId)
        if(siUnitId == null || siUnitId.length ==0){
            alert("请填写单位信息!")
        }else{
            window.location.href = ctx +'/register/stuRegister2.action?siUnitId='+siUnitId;
        }

    }

    function creatUnit(){
        layer.open({
            id: 'LAY_layuipro', //设定一个id，防止重复弹出
            type : 2,
            title : '添加单位维护信息',
            skin: 'layui-layer-molv', //样式类名
            area : [ '900px', '700px' ],
            shade : 0,
            maxmin : true,
            content :  '/unit/addUnit1.action',
            btn : [ '保存', '取消' ] //只是为了演示
            ,
            yes : function(index,layero) {
                var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
                //进行表单验证
                yanzheng(inputForm);
                inputForm.bootstrapValidator('validate');
                var flag = inputForm.data('bootstrapValidator').isValid();
                if(flag) {
                    inputForm.ajaxSubmit({
                        url:  'save.action',
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == 1) {
                                parent.layer.msg("添加成功!", {
                                    shade: 0.3,
                                    time: 1500
                                }, function () {
                                    window.location.reload(); // 刷新父页面
                                });
                            } else {
                                layer.msg(result.message, {
                                    icon: 2,
                                    time: 1000
                                });
                            }
                        }
                    });
                }
            },
            btn2 : function() {
                layer.closeAll();
            },
            success : function(layero) {
                layer.setTop(layero); //重点2
            }
        });
    }

</script>
</body>
</html>
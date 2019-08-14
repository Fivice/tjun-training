<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/cms/js/stuRecover/stuRecover.js"></script>
    <script src="${ctxsta }/cms/js/stuRecover/stuRecover_update.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-content">
        <ul class="nav nav-tabs">
            <li class="active"><a href="${ctx}/stuRecover/view.action">学员流水号回收</a></li>
            <li><a href="${ctx}/teachRecover/view.action">教师流水号回收</a></li>
        </ul>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="number" id="number"
                                       placeholder="请输入流水号">
                            </div>&emsp;
                            <div class="btn-group m-t-sm">
                                <button id="find" type="button" class="btn btn-primary">回收</button>&emsp;
                            </div>
                            <div class="btn-group m-t-sm">
                                <button id="reset" type="button" class="btn">清空</button>
                            </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-info" onclick="toTable()">整班回收</button>
                            </div>
                        </div>

                        <div id="table"></div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $("#find").click(function () {
        recover();
    });
    $("#number").bind("keyup",function (e) {
        var key = e.keyCode;
        if (key === 13){
            recover();
        }
    });
    $("#reset").click(function () {
        $("#number").val("");
    });

    function toTable() {
        $.ajax({
            type: 'POST',
            url: path + '/stuRecover/view1.action',
            dataType: "html",
            success: function (result) {
                $("#table").html(result)
            }
        })
    }
    function recover() {
        var number = $("#number").val();
        if (number == "") {
            layer.msg('请输入正确的流水号', {
                time: 1000
            })
        } else {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {number: number},
                url: path + '/stuRecover/findCard.action',
                // url: path + '/stuRecover/recover.action',
                success: function (result) {
                    if (result.studentId == 0) {
                        layer.msg('该流水号不存在!', {
                            icon: 1,
                            time: 1000
                        }, function () {
                            window.location.reload(); // 刷新父页面
                        });
                    } else {
                        if (result.studentId == 1) {
                            layer.msg('该流水号未被占用!', {
                                icon: 1,
                                time: 1000
                            }, function () {
                                window.location.reload(); // 刷新父页面
                            });
                        }
                        else {
                            layer.confirm('确认要回收吗？', {
                                btn: ['确定', '取消'] //按钮
                            }, function () {
                                $.ajax({
                                    type: 'POST',
                                    dataType: 'json',
                                    data: {number: number},
                                    url: path + '/stuRecover/recover.action',
                                    success: function (result) {
                                        if (result.code == 1) {
                                            layer.msg('回收成功', {
                                                icon: 1,
                                                time: 1000
                                            }, function () {
                                                window.location.reload(); // 刷新父页面
                                            });
                                        } else {
                                            layer.alert(result.message, {
                                                icon: 2
                                            });
                                        }
                                    }
                                })
                            });
                        }
                    }
                }
            })
        }
    }
</script>
</body>
</html>

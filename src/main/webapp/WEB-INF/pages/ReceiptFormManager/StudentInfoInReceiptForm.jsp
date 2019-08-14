<%--
  Created by IntelliJ IDEA.
  User: wubin
  Date: 2018/11/11
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<html>
<head>
    <title>回执表</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script src="${ctxsta }/entity/signUpManager/receiptForm.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script src="${ctxsta}/common/bootstrap-table/extensions/print/bootstrap-table-print.js"></script>
    <style>
        .my-input-group input{
            border-width: 0px 0px 1px 0px;
        }
    </style>
</head>
<body>
<!--startprint-->
<div id="receipt-form">
    <div class="wrapper">
        <div class="row">
            <div class="col-lg-12">

                <section class="panel">
                    <div class="panel-heading" style="text-align: center;height: 60px">
                        <h2 id="table-title">${classInfo.className}报名回执表汇总</h2>
                    </div>
                    <div class="panel-body">
                        <%--signUpManager-table begin--%>
                            <div class="col-lg-12">
                                <div class="my-input-group">
                                    <div class="col-sm-3">
                                        <label for="unit">报送单位：</label><input id="unit" type="text">
                                    </div>
                                    <div class="col-sm-3">
                                        <label for="date">报送日期：</label><input id="date" type="text">
                                    </div>
                                    <div class="col-sm-3">
                                        <label for="people">报送人：</label><input id="people" type="text">
                                    </div>
                                    <div class="col-sm-3">
                                        <label for="phone">联系电话：</label><input id="phone" type="text">
                                    </div>
                                </div>
                            </div>
                        <table id="table" class="table  table-hover general-table">

                        </table>
                            <%--signUpManager-table end--%>
                    </div>
                </section>
                    <div class="col-sm-offset-0 col-sm-10">
                        <button type="button" class="btn btn-primary" title="返回上一层"  onclick="history.go(-1)"> <i class="fa fa-reply-all"></i>&nbsp返回班级列表 </button>
                    </div>
            </div>

        </div>

    </div>


</div>
<!--endprint-->
</body>
<script type="text/javascript">
    // $(function () {
    //     $.ajax({
    //         type: "GET",//方法类型
    //         dataType: "json",//预期服务器返回的数据类型
    //         url: ctx+"/ReceiptForm/StudentInfoInReceiptForm.action" ,//url
    //         data: {"classId":4},
    //         success:function (data) {
    //             console.log(data)
    //             //$("#receipt-form").html(data)
    //         },
    //         error:function () {
    //             layer.alert("异常！请联系管理者");
    //         }
    //     })
    //
    // })
</script>
</html>

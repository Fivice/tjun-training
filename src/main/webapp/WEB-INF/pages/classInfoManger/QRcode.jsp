<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/classInfo/classInfoManger.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <style>
        .zt3 {
            font-size: 30px;
            font-family: STKaiti;
        }
    </style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="">
        <div id="print_html">
            <%--<div style="text-align:center;font-size:50px;font-family:STKaiti">${classInfo.className}</div>--%>
            <%--<div style="text-align:center;font-size:30px;font-family:STKaiti">[${classInfo.classNumber}]</div>--%>
            <input id="text" type="hidden" value="" style="text-align:center;"/><br />
            <div class="container" style="text-align:center;">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2" style="font-size: 40px;font-family: STXingkai;">
                        扫码报名
                    </div>
                    <div class="col-lg-8 col-lg-offset-2">
                        <HR SIZE=10>
                    </div>
                    <div class="col-lg-8 col-lg-offset-2" style="font-size: 50px;font-family: STKaiti;">
                        ${classInfo.className}
                    </div>
                    <div class="col-lg-8 col-lg-offset-2" style="font-size: 30px;font-family: STKaiti;">
                        [${classInfo.classNumber}]
                    </div>

                    <div class="col-lg-8 col-lg-offset-2" align="center" style="margin-top: 15px;margin-bottom: 15px">
                        <div id="qrcode"></div>
                    </div>
                    <div class="col-lg-8 col-lg-offset-2 zt3">
                        <div class="">主办单位：${classInfo.hostUnit}</div>
                    </div>
                    <div class="col-lg-8 col-lg-offset-2 zt3">
                        <div class="">办班时间：${classInfo.startStopTime}</div>
                    </div>
                </div>
            </div>
            <%--<div class="zt3">主办单位：${classInfo.hostUnit}</div>--%>
            <%--<div class="zt3">办班时间：${classInfo.startStopTime}</div>--%>
        </div>
    </div><br/>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2" align="center">
                <button type="button" class="btn btn-primary" title="返回" onclick="window.history.back()">返回</button>
                <button type="butn" class="btn" title="打印" onclick="printHTML();">打印</button>
            </div>
        </div>
        <%--<button type="button" class="btn btn-primary" title="返回" onclick="window.history.back()">返回</button>&emsp;--%>
        <%--<button type="butn" class="btn" title="打印" onclick="printHTML();">打印</button>--%>
    </div>
</div>
</body>
<script src="${ctxsta}/common/qrcode/qrcode.min.js"></script>
<script  src="${ctxsta}/common/qrcode/qrcode.js"></script>
<script  type="text/javascript">
    var lj= ctx + "/signUpManager/studentRegisterBySelf.action?classId=" +${classInfo.id};
    $("#text").val(lj);
    var qrcode = new QRCode(document.getElementById("qrcode"), {
        width : 250,
        height : 250,
    });

    function makeCode () {
        var elText = document.getElementById("text");

        if (!elText.value) {
            alert("Input a text");
            elText.focus();
            return;
        }

        qrcode.makeCode(elText.value);
    }

    makeCode();

    $("#text").
    on("blur", function () {
        makeCode();
    }).
    on("keydown", function (e) {
        if (e.keyCode == 13) {
            makeCode();
        }
    });

    // $('input').iCheck('disable'); //将输入框的状态设置为 disabled

    /*
     * 打印页面
     */
    function printHTML(_this){
        // 获取当前页的html代码
        var bdhtml = window.document.body.innerHTML;
        /*//设置打印开始区域
         //var startStr = '<!--startprint-->';
         // 设置打印结束区域
         //var endStr = '<!--endprint-->';
         //从标记里获取需要打印的页面
         var printHtml = bdhtml.substring(bdhtml.indexOf(startStr) + startStr.length, bdhtml.indexOf(endStr));*/
        //隐藏不必要的按钮和样式
        // 通过id获取需要打印的页面
        var printHtml = document.getElementById('print_html').innerHTML;
        // 需要打印的页面
        window.document.body.innerHTML = printHtml;
        if (!!window.ActiveXObject || "ActiveXObject" in window) { //是否ie
            remove_ie_header_and_footer();
        }
        window.print();
        // 还原界面
        window.document.body.innerHTML = bdhtml;
        window.location.reload();
    }
    //去掉页眉、页脚
    function remove_ie_header_and_footer() {
        var hkey_path;
        hkey_path = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
        try {
            var RegWsh = new ActiveXObject("WScript.Shell");
            RegWsh.RegWrite(hkey_path + "header", "");
            RegWsh.RegWrite(hkey_path + "footer", "");
        } catch (e) {
        }
    }
</script>
</html>
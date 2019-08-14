<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>制作证件</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/blue.css" />
    <script src="${ctxsta}/common/icheck/icheck.min.js"></script>
    <script src="${ctxsta }/common/jquery/JsBarcode.all.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/jquery/jquery.jqprint-0.3.js"></script>
    <script src="${ctxsta}/common/jquery/jquery-migrate-1.0.0.js"></script>
    <script src="${ctxsta}/common/qrcode/qrcode.min.js"></script>
    <script  src="${ctxsta}/common/qrcode/qrcode.js"></script>
<style>
    .rotate
    {
        transform:rotate(180deg);
        -ms-transform:rotate(180deg); 	/* IE 9 */
        -moz-transform:rotate(180deg); 	/* Firefox */
        -webkit-transform:rotate(180deg); /* Safari 和 Chrome */
        -o-transform:rotate(180deg); 	/* Opera */
    }
</style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">

    <div class="box-header with-border">
        <div class="box-title">
            <i class="fa fa-list-alt"></i>制作证件
        </div>

        <div style="margin-top: 20px" class="row">

            <div class="col-xs-4">
                <div class="form-group">
                    <label class="control-label col-sm-2" title="">
                        <span class="required hide" aria-required="true">*</span>起始号<i class="fa icon-question hide"></i></label>
                    <div class="col-sm-6">
                        <input type="text" id="startingNumber" name="startingNumber" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-xs-4">
            <div class="form-group checkClass">
                <label class="radio-inline">
                    <input type="radio"  value="学员证" name="type" checked="checked">学员证
                </label>
                <label class="radio-inline">
                    <input type="radio"  value="教师证" name="type">教师证
                </label>
            </div>
            </div>
        </div>


        <div class="row">
            <div class="col-xs-4">
                <div class="form-group">
                    <label class="control-label col-sm-2" title="">
                        <span class="required hide" aria-required="true">*</span>终止号<i class="fa icon-question hide"></i></label>
                    <div class="col-sm-6">
                        <input type="text" id="terminateNumber" name="terminateNumber" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-xs-4">
                <div class="form-group">
                    <label class="control-label" title="">
                        <span class="required hide" aria-required="true">*</span><i class="fa icon-question hide"></i></label>
                    <label class="control-label" title="">
                        <button type="button" class="btn btn-info" id="flowCode"><i class="fa fa-bell-o"></i> 生成流水号</button>
                    </label>
                </div>
            </div>
        </div>

    </div>

    <div id="fcode" style="margin-top: 20px;display: none">

        <div class="row">
            <div class="col-xs-4">
                <div class="form-group">
                    <label class="control-label col-sm-6" title="">
                        <span class="required hide" aria-required="true">*</span><i class="fa icon-question hide"></i></label>
                    <div class="col-sm-6">
                        <button type="button" class="btn btn-success" id="print">打印</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="code">
            <!--正面-->
        <div class="row">
            <div class="col-xs-4">
                    <div>
                    <div><img src="${ctxsta }/img/code/dw.jpg"></div>
                    <div style="width: 228px;text-align: center">
                        <div id="types" style="font-size: 18px;margin-top:20px;font-weight: bold"></div>
                        <div style="margin-top: 30px;"><img id="bcode"/></div>
                        <div id="dinner"></div>
                        <div style="margin-top: 20px;">国网安徽培训中心</div>
                    </div>
                </div>
        </div>
      <%--     <div class="col-xs-4">
                <div>
                    <div class="form-group">
                        <div>title</div>
                        <div>
                            <div>学员信息</div>
                            <div><img id="bcode2"/></div>
                            <div>国家安徽电网中心</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-4">
                <div>
                    <div class="form-group">
                        <div>title</div>
                        <div>
                            <div>学员信息</div>
                            <div><img id="bcode3"/></div>
                            <div>国家安徽电网中心</div>
                        </div>
                    </div>
                </div>
            </div>--%>
        </div>
            <br>
            <br>
            <br>
            <br>
            <!--反面-->
            <div class="row">
                <div class="col-xs-4">
                            <div class="rotate" style="width: 228px;line-height: 25px;">
                            <div>1、凭此学员证出入校园、就餐、上课、参加会议等;</div>
                            <div>2、手机扫右下侧二维码，可查询本培训班相关信息、下载资料、进行培训质量评价;</div>
                            <div>
                                <div style="width:100px;float: left;">
                                    3、培训结束后，请将此证交回招待所总台或班主任处。
                                </div>
                                <div id="qrcode">

                                </div>
                            </div>
                        </div>
                </div>
            </div>
    </div>
</div>
</div>
</div>
<script>
    //判断两个数大小
    function check(num1,num2)
    {
        if(parseInt(num2)>parseInt(num1))
    {
        return false;

    }
        return true;
    }

    $("#flowCode").click(function() {
        /*$("#fcode").toggle(500)*/
        var checkBoxArr = [];
        $('input[name="type"]:checked').each(function() {
            checkBoxArr.push($(this).val());
        });

        //起始号
        var startingNumber=$("#startingNumber").val();
        //终止号
        var terminateNumber=$("#terminateNumber").val();
        var reg = /^\d{4}$/;
        var reg2=/^9\d{3}$/;
        var rg=reg.test(terminateNumber);
        if(checkBoxArr[0]=="学员证"){

            var re=reg.test(startingNumber);
            if(!re){
                layer.alert("请输入四位数字的起始号")
            }else if(!rg) {
                layer.alert("请输入四位数字的终止号")
            }else if(check(startingNumber,terminateNumber)){
                layer.alert("终止号需要大于起始号")
            }else{
                //学员证或者教师证
                $("#types").html(checkBoxArr[0]);
                $("#dinner").html("考勤/就餐条码");
                var barcode=startingNumber+terminateNumber;
                jB(barcode);
                // 二维码
               /* new QRCode(document.getElementById('qrcode'), barcode);*/

                $("#qrcode").html("<img width='100px' height='100px' src='http://qr.liantu.com/api.php?&w=200&text=http://baidu.com'>");

                $("#fcode").css("display","block");
            }
        }

        if(checkBoxArr[0]=="教师证"){

            var re2=reg2.test(startingNumber);
            if(!re2){
                layer.alert("请输入以9开头的四位数字起始号")
            }else if(!rg) {
                layer.alert("请输入四位数字的终止号")
            }else if(check(startingNumber,terminateNumber)){
                layer.alert("终止号需要大于起始号")
            }else{
                //学员证或者教师证
                $("#types").html(checkBoxArr[0]);
                $("#dinner").html("就餐条码");
                var barcode=startingNumber+terminateNumber;
                jB(barcode);
                // 二维码
               /* http://qr.liantu.com/api.php?text=http://localhost:8080/CJ/studentCard/form2.action?id=8*/
                /*new QRCode(document.getElementById('qrcode'), barcode);*/

                $("#qrcode").html("<img width='100px' height='100px' src='http://qr.liantu.com/api.php?&w=200&text=http://baidu.com'>");

                $("#fcode").css("display","block");
            }
        }




    });

    $("#print").click(function() {
        $("#code").jqprint();
    });



    $(document).ready(function(){
        $('.checkClass input').iCheck({
            checkboxClass: 'icheckbox_flat-blue',  // 注意square和blue的对应关系
            radioClass: 'iradio_flat-blue',
            increaseArea: '20%'
        });
});


function jB(barcode){
    JsBarcode("#bcode", barcode, {
        format: "CODE128",//选择要使用的条形码类型
        width:1,//设置条之间的宽度
        height:50,//高度
        displayValue:true,//是否在条形码下方显示文字
        text:barcode,//覆盖显示的文本
        /*fontOptions:"bold italic",//使文字加粗体或变斜体*/
        font:"fantasy",//设置文本的字体
        textAlign:"center",//设置文本的水平对齐方式
        /*textPosition:"top",//设置文本的垂直位置*/
        textMargin:5,//设置条形码和文本之间的间距
        fontSize:15,//设置文本的大小
        background:"#dddddd",//设置条形码的背景
        margin:15,//设置条形码周围的空白边距
    });
}



</script>

</body>
</html>
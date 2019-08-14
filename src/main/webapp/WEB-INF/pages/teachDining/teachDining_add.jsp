<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>添加</title>
    <%--<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />--%>
    <%--<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />--%>
    <%--<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />--%>
    <%--<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />--%>
    <%--<link rel="stylesheet" href="${ctxsta}/common/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">--%>

    <style type="text/css">
        .no_line {
            padding: 10px;
            word-break: keep-all;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .red_must {
            padding: 2px;
            color: red;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/teachDin/save.action" data-method="post">
                        <input  class="form-control" name="time" value="" type="hidden">

                           <div class="form-group ">
                               <label class="col-sm-2 control-label no_line">流水号：<span class= "red_must"  >*</span></label>
                               <div class="col-sm-4 ">
                                   <input type="text" class="form-control" id="number"  name="number"  value="${number}" onchange="aaaa()">
                               </div>&nbsp;&nbsp;&nbsp;
                               <div class="col-sm-4 " id = "span">
                                   <span id ="span_id" style="color:red"></span>
                               </div>
                           </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group ">
                               <label class="col-sm-2 control-label no_line">教师姓名：<span class= "red_must"  >*</span></label>
                               <div class="col-sm-4 ">
                                   <input type="text" class="form-control" id="teacherName" name="teacherName"   value="">
                               </div>
                               <label class="col-sm-2 control-label no_line">安排人姓名：</label>
                               <div class="col-sm-4 ">
                                   <input type="text" class="form-control" name="arranger"  value="${userName}"readonly="readonly">
                               </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group ">
                               <label class="col-sm-2 control-label no_line">班级名称：<span class= "red_must"  >*</span></label>
                               <div class="col-sm-4 ">
                                   <select style="width: 250px;height: 35px;border-color:#E0E0E0;" id="classId"
                                           name="classId" onchange="select(this.value)">
                                       <option value="">请选择班级</option>
                                       <c:forEach items="${classNameList}" var="classNameList">
                                           <option value=${classNameList.id} data_obj="${classNameList.diningPlace}">[${classNameList.classNumber}] ${classNameList.className} [${classNameList.startStopTime}] </option>
                                       </c:forEach>
                                   </select>
                               </div>
                               <label class="col-sm-2 control-label no_line">就餐地点：<span class= "red_must"  >*</span></label>
                               <div class="col-sm-4 ">
                                   <input type="text" class="form-control" id="area" name="area" readonly="readonly">

                               <%--<select style="width: 250px;height: 35px;border-color:#E0E0E0;" id="area"--%>
                                           <%--name="area">--%>
                                       <%--<option value="">请选择就餐地点</option>--%>
                                       <%--<c:forEach items="${list}" var="list">--%>
                                           <%--<option value=${classNameList.id}>${classNameList.diningPlace}</option>--%>
                                       <%--</c:forEach>--%>
                                       <%--<option value="其他">其他</option>--%>
                                   <%--</select>--%>
                               </div>
                        </div>
                           <div class="hr-line-dashed"></div>
                           <div class="form-group">
                               <div class="col-sm-12 text-center">
                                   <button id="submit" class="btn btn-primary" type="submit">提交</button>
                               </div>
                           </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
<script type="text/javascript">
    function aaaa() {
        var number = $("#number").val();
        $.ajax({
            type: 'POST',
            dataType: 'json',
            data: {
                number: number
            },
            url: ctx + '/teachDin/findCard.action',
            success: function (result) {
                if(result.teacherName == 0){
                    $("#span_id").attr("style","color:red;");
                    $("#span_id").text("该流水号不存在,请输入正确的教师流水号") ;
                    $("#submit").hide();
                }else{
                    if(result.teacherName == 1){
                        var mySpan = $("#span_id");
                        $("#span_id").attr("style","color:green;");
                        mySpan.text('该流水号未绑定教师，可用') ;
                     //   $("#teacherName").prop("value","");
                        $("#submit").show();
                    }else {
                        $("#span_id").attr("style","color:red;");
                        $("#span_id").text("该流水号已绑定教师姓名，请先回收该流水号再使用") ;
                        $("#teacherName").val(result.teacherName);
                        $("#submit").hide();
                    }
                }
            }
        })
    }
    //日期选择器
    laydate.render({
        elem: '#cTime1',
        type: 'date', //默认，可不填
        max: 0
    });

    function select(value) {
        var diningPlace = $("#classId option:selected").attr("data_obj");
        $("#area").val(diningPlace);
    }

</script>
<script src="${ctxsta}/cms/js/teachDining/teachDining_update.js"></script>
</body>
</html>

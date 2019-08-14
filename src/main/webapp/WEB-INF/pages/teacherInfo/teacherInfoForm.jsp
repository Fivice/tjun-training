<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>
    <script src="${ctxsta }/cms/js/teacherInfo/bootstrpValidator.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/student/upload.js" type="text/javascript"></script>

    <link type="text/css" rel="stylesheet" href="${ctxsta }/bootstrap-fileinput/css/fileinput.css" />
    <script type="text/javascript" src="${ctxsta }/bootstrap-fileinput/js/fileinput.min.js"></script>
    <script type="text/javascript" src="${ctxsta }/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-content">

        <div class="box-header with-border">
            <div class="box-title">
                <i class="fa fa-list-alt"></i> ${not empty teacherInfo.tiId?'修改':'新增'}数据
            </div>
        </div>

        <form id="inputForm" enctype="multipart/form-data"
              action="${ctx}/teacherInfo/saveOrUpdate.action" method="post" class="form-horizontal">
            <input type="hidden" id="tiId" name="tiId" value="${teacherInfo.tiId}"/>
            <%--<input type="hidden" id="scenePicture" name="scenePicture" value="${teacherInfo.scenePicture}"/>--%>
            <input type="hidden" name="createBy" value="${teacherInfo.createBy}"/>
            <input type="hidden" name="createDate" value="${teacherInfo.createDate}"/>
            <%--<input type="hidden" id="ethnicGroup" value="${student.ethnicGroup}"/>--%>
            <div style="margin-top: 20px">
                <fieldset>
                    <legend>基本信息</legend>
                </fieldset>

                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 姓名：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="tiName" name="tiName" value="${teacherInfo.tiName}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" aria-required="true" style="color: #af0000">*</span> 身份证号：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="testDate" name="siIDNumber" value="${teacherInfo.siIDNumber}" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 民族：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="ethnicGroup" name="ethnicGroup" value="${teacherInfo.ethnicGroup}" class="form-control">
                                <%--<select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="national" name="ethnicGroup" class="form-control">--%>
                                <%--</select>--%>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 性别：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                男：<input type="radio" name="sex" value="0" <c:if test="${'0' eq teacherInfo.sex || empty teacherInfo.sex || teacherInfo.sex==''}">checked</c:if>>

                                女：<input type="radio" name="sex" value="1" <c:if test="${'1' eq teacherInfo.sex}">checked</c:if>>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 工作单位：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input id="tiDepartment" name="tiDepartment" type="text"  class="form-control"  placeholder=""  value=${teacherInfo.tiDepartment } >
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 授课科目：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input id="subject" type="text" name="subject" class="form-control"  placeholder=""  value=${teacherInfo.subject } >
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 手机号码：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="phoneNumber" name="phoneNumber" value="${teacherInfo.phoneNumber}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> Email：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="email" name="email" value="${teacherInfo.email}" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 备注：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <textarea rows="4" type="text" id="remarks" name="remarks" value="${teacherInfo.remarks}" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 证件照：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input id="photo" name="photo" type="hidden" value="${teacherInfo.photo}">
                                <input id="file-pic" name="file" type="file">
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
                            <c:if test="${empty query }">
                            <button type="button" class="btn btn-sm btn-primary" onclick="subForm()" id="btnSubmit"><i class="fa fa-check"></i> 保 存</button>&nbsp;
                            </c:if>
                            <button type="button" class="btn btn-sm btn-default" id="btnCancel" onclick="history.go(-1)"><i class="fa fa-reply-all"></i> 关 闭</button>
                        </div>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>
<script>

//提交表单
function subForm(){

    $("#file-pic").fileinput("upload");
    //开启验证
    $('#inputForm').data('bootstrapValidator').validate();
    setTimeout(function() {
        if($('#inputForm').data('bootstrapValidator').isValid()){

                $.ajax({
                    //几个参数需要注意一下
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: ctx+"/teacherInfo/saveOrUpdate.action" ,//url
                    data: $('#inputForm').serialize(),
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("提交成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.location.href=ctx+"/teacherInfo/view.action"; // 刷新页面*/
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    },
                });
        }
    },2500);

}


    $(document).ready(function(){

        var img = $('#photo').val();
        var imgUrl ="";
        var str ="";
        var oFileInput = new menu.fileInput();
        if(typeof img != "undefined" && img != null && img != ""){
            imgUrl="<img class='file-preview-image' src='"+img+"'/>"
            var index = img .lastIndexOf("\/");
            str  = img .substring(index + 1, img .length);
        }
        oFileInput.Init("file-pic", "${ctx }/upload/fileUpload.action","photo",imgUrl,str);
    });



</script>
</body>
</html>
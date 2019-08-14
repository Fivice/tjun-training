<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>
    <script src="${ctxsta }/entity/student/bootstrpValidator.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/student/students.js" type="text/javascript"></script>

    <link type="text/css" rel="stylesheet" href="${ctxsta }/bootstrap-fileinput/css/fileinput.css" />
    <script type="text/javascript" src="${ctxsta }/bootstrap-fileinput/js/fileinput.min.js"></script>
    <script type="text/javascript" src="${ctxsta }/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>

<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">

    <div class="box-header with-border">
        <div class="box-title">
            <i class="fa fa-list-alt"></i>学员报名登记
        </div>
    </div>

    <form id="inputForm" enctype="multipart/form-data"
          action="${ctx}/student/saveOrUpdate.action" method="post" class="form-horizontal">
    <input type="hidden" id="siId" name="siId" value="${student.siId}"/>
        <input type="hidden" name="createBy" value="${student.createBy}"/>
        <input type="hidden" name="createDate" value="${student.createDate}"/>
        <div style="margin-top: 20px">
            <fieldset>
            <legend>基本信息</legend>
            </fieldset>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 身份证号：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="testDate" name="siIDNumber" value="${student.siIDNumber}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 状态：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="status" name="status">
                                <option value="0" <c:if test="${'0' eq student.status}">selected</c:if>>正常</option>
                                <option value="1" <c:if test="${'1' eq student.status}">selected</c:if>>失效</option>
                            </select>
                            <%--<input type="text" id="status" name="status" value="${student.status}" class="form-control">--%>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 姓名：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="siName" name="siName" value="${student.siName}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 性别：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            男：<input type="radio" name="sex" value="0" checked="checked">

                            <%--<input type="text" id="sex" name="sex" value="${student.sex}" class="form-control">--%>
                            女：<input type="radio" name="sex" value="1">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 员工编号：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="siNumber" name="siNumber" value="${student.siNumber}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                <div class="form-group">
                    <label class="control-label col-sm-4" title="">
                        <span class="required hide" aria-required="true">*</span> 手机号码：<i class="fa icon-question hide"></i></label>
                    <div class="col-sm-8">
                        <input type="text" id="phoneNumber" name="phoneNumber" value="${student.phoneNumber}" class="form-control">
                    </div>
                </div>
            </div>
            </div>
           <%-- <div class="row">
                <div class="col-xs-4">
                    &lt;%&ndash;<div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 部门：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="departmentId" name="siDepartmentId">
                                <option value="" data-deptName=""></option>
                                <c:forEach items="${departmentList}" var="department">
                                    <option <c:if test="${department.areaId eq student.siDepartmentId}">selected</c:if> value="${department.areaId}" >${department.areaName}</option>
                                </c:forEach>
                            </select>
                            &lt;%&ndash;<input type="text" &lt;%&ndash;id="email" name="email"&ndash;%&gt; value="${student.email}" class="form-control">&ndash;%&gt;
                        </div>
                    </div>&ndash;%&gt;

                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 部门：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">

                                <input id="departName" type="text" class="form-control"  placeholder="" readonly="readonly" value=${department.areaName } >
                                <input id="departmentId" type="hidden" value=${student.siDepartmentId } name="siDepartmentId" /> <!--若需要节点id，将节点id存储在此，然后上传到服务器-->
                                <div id="departInfo" style="z-index: 2;position:absolute; width:320px; height:180px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                                    <ul id="departmenTree" class="ztree"></ul>
                                </div>

                                &lt;%&ndash;<input type="text" &lt;%&ndash;id="email" name="email"&ndash;%&gt; value="${student.email}" class="form-control">&ndash;%&gt;
                            </div>
                        </div>

                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 工作部门：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="deparentmentName" name="deparentmentName" value="${student.deparentmentName}" class="form-control">
                        </div>
                    </div>
                </div>
            </div>--%>
            <div class="row">
                <%--<div class="col-xs-4">
                   &lt;%&ndash; <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 单位：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="unitId" name="unit.areaId">
                                <c:forEach items="${ unitList}" var="unit">
                                    <option <c:if test="${unit.areaId eq student.unit.areaId}">selected</c:if> value="${unit.areaId}">${unit.areaName}</option>
                                </c:forEach>
                            </select>
                            &lt;%&ndash;<input type="text" id="siUnitId" name="siUnitId" value="${student.siUnitId}" class="form-control">&ndash;%&gt;
                        </div>
                    </div>&ndash;%&gt;
                       <div class="form-group">
                           <label class="control-label col-sm-4" title="">
                               <span class="required hide" aria-required="true">*</span> 单位：<i class="fa icon-question hide"></i></label>
                           <div class="col-sm-6">
                       <input id="ChanYeName" type="text"  class="form-control"  placeholder="" readonly="readonly" value=${unit.areaName } >
                       <input id="cyids" type="hidden"  value=${student.siUnitId } name="siUnitId" /> <!--若需要节点id，将节点id存储在此，然后上传到服务器-->
                       <div id="info" style="z-index: 1;position:absolute; width:320px; height:180px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                           <ul id="haveclasstree" class="ztree"></ul>
                       </div>
                           </div>
                           <div class="col-sm-2">
                               <button id="reset" type="button" class="btn" onclick="resetUnit()">清除</button>
                           </div>
                       </div>

                </div>--%>

                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 单位名称：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="unitName" name="unitName" value="${unitName}" class="form-control">
                        </div>
                    </div>
                </div>

                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 职业技能等级：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="skillLevel" name="skillLevel" value="${student.skillLevel}" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 地址：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="siAddress" name="siAddress" value="${student.siAddress}" class="form-control">
                        </div>
                    </div>
                </div>


                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> Email：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="email" name="email" value="${student.email}" class="form-control">
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 工作岗位：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="post" name="post" value="${student.post}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 备注：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <textarea rows="4" type="text" id="remarks" name="remarks" value="${student.remarks}" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
            <div class="col-xs-4">
                <div class="form-group">
                    <label class="control-label col-sm-4" title="">
                        <span class="required hide" aria-required="true">*</span> 证件照：<i class="fa icon-question hide"></i></label>
                    <div class="col-sm-8">
                        <input id="file-pic" name="file" type="file">
                        <input id="photo" name="photo" type="hidden" value="${student.photo}">
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
                    <button type="submit" class="btn btn-sm btn-primary" id="btnSubmit"><i class="fa fa-check"></i> 保 存</button>&nbsp;
                    <button type="button" class="btn btn-sm btn-default" id="btnCancel" onclick="history.go(-1)"><i class="fa fa-reply-all"></i> 关 闭</button>
            </div>
        </div>
        </div>
        </div>

    </form>
</div>
</div>
<script>

    $(document).ready(function(){
        var oFileInput = new menu.fileInput();
        var imgUrl = $('#photo').val();
        if(typeof imgUrl != "undefined" && imgUrl != null && imgUrl != ""){
            editImag("photo",imgUrl,"file-pic");
        }else{
            oFileInput.Init("file-pic", "${ctx }/upload/uploadImage.action","photo");
        }
        /*$('input').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
            increaseArea: '20%',
            labelHover: true,
        });*/
    });

    $("#departmentId").change(function(){
        $("#deparentmentName").attr("value",$(this).find("option:selected").text());
    });

    //清除选中的单位
    function resetUnit(){
        $("#cyids").prop("value","");
        $("#ChanYeName").prop("value","");
        $("#unitName").prop("value","");
    }




</script>
</body>
</html>
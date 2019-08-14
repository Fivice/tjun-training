<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/blue.css" />
    <script src="${ctxsta}/common/icheck/icheck.min.js"></script>
    <script src="${ctxsta }/entity/student/bootstrpValidator2.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/student/upload.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/student/tree.js" type="text/javascript"></script>

    <link type="text/css" rel="stylesheet" href="${ctxsta }/bootstrap-fileinput/css/fileinput.css" />
    <script type="text/javascript" src="${ctxsta }/bootstrap-fileinput/js/fileinput.min.js"></script>
    <script type="text/javascript" src="${ctxsta }/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-content">

        <div class="box-header with-border">
            <div class="box-title">
                <i class="fa fa-list-alt"></i> ${not empty student.siId?'修改':'新增'}数据
            </div>
        </div>

        <form id="inputForm" enctype="multipart/form-data"
              action="${ctx}/student/saveOrUpdate.action" method="post" class="form-horizontal">
            <input type="hidden" id="siId" name="siId" value="${student.siId}"/>
            <input type="hidden" name="createBy" value="${student.createBy}"/>
            <input type="hidden" name="createDate" value="${student.createDate}"/>
            <%--<input type="hidden" id="ethnicGroup" value="${student.ethnicGroup}"/>--%>
            <div style="margin-top: 20px">
                <fieldset>
                    <legend>基本信息</legend>
                </fieldset>

                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" aria-required="true" style="color: #af0000">*</span> 身份证号：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="testDate" name="siIDNumber" value="${student.siIDNumber}" class="form-control">
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 状态：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <select class="form-control" id="status" name="status">
                                    <option value="0" <c:if test="${'0' eq student.status}">selected</c:if>>正常</option>
                                    <option value="1" <c:if test="${'1' eq student.status}">selected</c:if>>失效</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 姓名：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="siName" name="siName" value="${student.siName}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group checkClass">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 性别：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                男：<input type="radio" name="sex" value="0" <c:if test="${'0' eq student.sex || empty student.sex || student.sex==''}">checked</c:if>>

                                女：<input type="radio" name="sex" value="1" <c:if test="${'1' eq student.sex}">checked</c:if>>
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
                                <span class="required hide" aria-required="true">*</span> 民族：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="hidden" id="ethnicGroup" name="ethnicGroup" value="${student.ethnicGroup}" class="form-control">
                                    <select  id="national" class="form-control" onchange="selectNational()">
                                    </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 单位：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-4">
                                <input id="ChanYeName" type="text"  class="form-control"  placeholder="请点击选择单位" readonly="readonly" value=${unit.areaName } >
                                <input id="cyids" type="hidden"  name="siUnitId" value=${student.siUnitId } > <!--若需要节点id，将节点id存储在此，然后上传到服务器-->
                                <div id="info" style="z-index: 1;position:absolute; width:320px; height:180px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                                    <ul id="haveclasstree" class="ztree"></ul>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <button id="reset" type="button" class="btn" onclick="resetUnit()">清除</button>
                            </div>
                            <div class="col-sm-2">
                            <button type="button" onclick="addUnit()" class="btn btn-sm btn-info"><i class="fa fa-envelope-o"></i> 新增</button>
                            </div>
                        </div>

                    </div>

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 部门：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="deparentmentName" name="deparentmentName" value="${student.deparentmentName}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="unitName" name="unitName" value="${student.unitName}" class="form-control">
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 职业技能等级：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="skillLevel" name="skillLevel" value="${student.skillLevel}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 地址：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="siAddress" name="siAddress" value="${student.siAddress}" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> Email：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="email" name="email" value="${student.email}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 工作岗位：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="post" name="post" value="${student.post}" class="form-control">
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
                                <textarea rows="4" type="text" id="remarks" name="remarks" value="${student.remarks}" class="form-control"></textarea>
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
                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 证件照：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input id="photo" name="photo" type="hidden" value="${student.photo}">
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
                    url: ctx+"/student/saveOrUpdate.action" ,//url
                    data: $('#inputForm').serialize(),
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("提交成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.location.href=ctx+"/student/view.action"; // 刷新页面*/
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

        $('.checkClass input').iCheck({
            checkboxClass: 'icheckbox_flat-blue',  // 注意square和blue的对应关系
            radioClass: 'iradio_flat-blue',
            increaseArea: '20%'
        });
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
    //新增单位
    function addUnit() {
        window.location.href = ctx +'/unit/view.action';
    }

var national = [
    /*"请选择民族",*/"汉", "壮", "满", "回", "苗", "维吾尔", "土家", "彝", "蒙古", "藏", "布依", "侗", "瑶", "朝鲜", "白", "哈尼",
    "哈萨克", "黎", "傣", "畲", "傈僳", "仡佬", "东乡", "高山", "拉祜", "水", "佤", "纳西", "羌", "土", "仫佬", "锡伯",
    "柯尔克孜", "达斡尔", "景颇", "毛南", "撒拉", "布朗", "塔吉克", "阿昌", "普米", "鄂温克", "怒", "京", "基诺", "德昂", "保安",
    "俄罗斯", "裕固", "乌孜别克", "门巴", "鄂伦春", "独龙", "塔塔尔", "赫哲", "珞巴"
];
 window.onload = function ()
 {
     var nat = document.getElementById ("national");
     var ethnicGroup = $("#ethnicGroup").val();
     // option.appendChild ("请选择民族");
     for ( var i = 0; i < national.length; i++)
     {
         var option = document.createElement ('option');
         if(ethnicGroup == national[i]){
             option.selected=true;
         }
         option.value = national[i];
         var txt = document.createTextNode (national[i]);
         option.appendChild (txt);
         nat.appendChild (option);
     }
 }
function selectNational() {
    var value = $("#national ").val();
    $("#ethnicGroup").prop("value",value);
}
</script>
</body>
</html>
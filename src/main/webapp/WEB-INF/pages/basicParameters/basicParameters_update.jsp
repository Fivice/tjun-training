<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>创建单位</title>
    <link type="text/css" rel="stylesheet" href="${ctxsta }/bootstrap/bootstrap-tagsinput/bootstrap-tagsinput.css" />
    <script src="${ctxsta }/bootstrap/bootstrap-tagsinput/bootstrap-tagsinput.js" type="text/javascript"></script>
</head>
<body>
<div class="ibox-content">
    <form id="form" class="form-horizontal"  data-method="post">

        <input  hidden value="${basicParameters.id}" name="id"><br>
        <div class="form-group m-t">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 院校名称</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="name" value="${basicParameters.name}" >
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 系统外网网址</label>
            <div class="col-sm-7">
                <input type="text"  class="form-control" name="url" value="${basicParameters.url}" >
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 报名住宿地点</label>
            <div class="col-sm-7">
                <%--<textarea class="form-control" id="address" rows="2" name="address">${basicParameters.address}</textarea>--%>
                <input type="text" id="address" class="form-control" name="address" value="${basicParameters.address}" >
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 就餐地点</label>
            <div class="col-sm-7">
                <input type="text" id="eatPlace" class="form-control" name="eatPlace" value="${basicParameters.eatPlace}" >
            </div>
        </div>


        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 就餐标准<br>(早/中/晚)</label>
            <div class="col-sm-2">
                <input type="text" id="eatStandard0" class="form-control" name="eatStandard0" value="${eatStandard0 }" >
                <%--<input type="text" id="eatStandard" class="form-control" name="eatStandard" value="${basicParameters.eatStandard}" >--%>
            </div>
            <div class="col-sm-2">
            <input type="text" id="eatStandard1" class="form-control" name="eatStandard1" value="${eatStandard1 }" >
            </div>
            <div class="col-sm-2">
            <input type="text" id="eatStandard2" class="form-control" name="eatStandard2" value="${eatStandard2 }" >
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">
                <span class="required" aria-required="true" style="color: #af0000">*</span> 住宿标准<br>(标间/单间)</label>
            <div class="col-sm-3">
                <input type="text" id="houseStandard0" class="form-control" name="houseStandard0" value="${houseStandard0 }" >
            </div>
            <div class="col-sm-3">
                <input type="text" id="houseStandard1" class="form-control" name="houseStandard1" value="${houseStandard1 }" >
            </div>
        </div>

    </form>
</div>
</body>
<script>
    $(function () {
        $("#address").tagsinput();
        $('#address').on('itemAdded', function(event) {
            var tag = event.item;
            // Do some processing here
            if (!event.options || !event.options.preventPost) {
                var r = /^[^,，]*$/;
                if(!r.test(tag)){
                    $('#address').tagsinput('remove', tag);
                    layer.msg("不能输入中英文逗号！")
                }
            }
        });
        $("#eatPlace").tagsinput();
        $('#eatPlace').on('itemAdded', function(event) {
            var tag = event.item;
            // Do some processing here
            if (!event.options || !event.options.preventPost) {
                var r = /^[^,，]*$/;
                if(!r.test(tag)){
                    $('#eatPlace').tagsinput('remove', tag);
                    layer.msg("不能输入中英文逗号！")
                }
            }
        });
    })
</script>
</html>

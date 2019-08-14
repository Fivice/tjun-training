<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>

    <script src="${ctxsta }/ckeditor/ckeditor.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxsta }/ckfinder/ckfinder.js"></script>

<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-content">

        <div class="box-header with-border">
            <div class="box-title">
                <i class="fa fa-list-alt"></i>测试数据
            </div>
        </div>

        <form id="inputForm" enctype="multipart/form-data"
              action="${ctx}/test/test.action" method="post" class="form-horizontal">
            <div style="margin-top: 20px">
                <fieldset>
                    <legend>基本信息</legend>
                </fieldset>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 测试：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <textarea name="siIDNumber" class="form-control"></textarea>
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
                            <button type="submit" class="btn btn-sm btn-primary"  id="btnSubmit"><i class="fa fa-check"></i> 保 存</button>&nbsp;
                            <button type="button" class="btn btn-sm btn-default" id="btnCancel" onclick="history.go(-1)"><i class="fa fa-reply-all"></i> 关 闭</button>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </form>
    </div>
</div>
<input type="text" class="form-control" id="url" >
<input type="submit" id="add" class="btn btn-default" onclick="BrowseServer()" value="选择图片">
<script>

    $(document).ready(function(){

        var editor = CKEDITOR.replace('siIDNumber');
        CKFinder.setupCKEditor(editor, '${ctx }/static/ckfinder/');

    });
    function BrowseServer()
    {
        var finder = new CKFinder() ;
        finder.basePath = '${ctxsta }/ckfinder/';  //导入CKFinder的路径
        finder.selectActionFunction = SetFileField; //设置文件被选中时的函数
        // finder.selectActionData = inputId;  //接收地址的input ID
        finder.popup() ;
    }

    //文件选中时执行
    function SetFileField(fileUrl,data)
    {
        document.getElementById("url").value = fileUrl ;
    }
</script>
</body>
</html>
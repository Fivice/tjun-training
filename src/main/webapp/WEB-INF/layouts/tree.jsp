<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>公用模态框</title>
</head>
<body>
<input id="un"  type="hidden" value="" class="form-control">
<!-- 模态框（Modal） -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">选择数据</h4>
            </div>
            <!-- 区域内容 -->
            <div class="modal-body">

                <!-- 区域树结构 -->
                <div id="dynatree" name=dynatree
                     value="1">
                    <ul id="treeDemo" class="ztree">
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭</button>
                <button type="button" class="btn btn-primary" onclick="chooseUnit()">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

</body>
</html>


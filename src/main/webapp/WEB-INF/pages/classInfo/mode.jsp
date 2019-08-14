<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>公用模态框</title>
</head>
<body>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 800px; margin-left: -100px;">
            <div class="modal-header">
                <button type="button"  class="close" data-dismiss="modal"
                        aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">选择数据</h4>
            </div>
            <!-- 内容 -->
            <div class="modal-body">
                <!-- 教室表格 -->
                <table class="table table-bordered">
                    <caption>教室信息</caption>
                    <thead>
                    <tr>
                        <th></th>
                        <th>校区名称</th>
                        <th>学校名称</th>
                        <th>容量</th>
                    </tr>
                    </thead>
                    <tbody id="J_TbData">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭</button>
                <button type="button" class="btn btn-primary" onclick="choose()" id="chooseButton">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

</body>
</html>


<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>新增就餐脱机数据</title>
    <style type="text/css">
        .red_must {
            padding: 2px;
            color: red;
        }
        .kuang{
            width: 300px;height: 35px;border-color:#E0E0E0;
        }

    </style>
</head>

<body >
<div class="ibox-content">
    <form id="form" class="form-horizontal" action="${ctx}/offlineData/add.action" data-method="post">
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 control-label" >流水号<span class="red_must">*</span></label>&emsp;
                <input  v-model="number" type="text" class="kuang"  id="number" name="number" placeholder="请输入流水号">
        </div>
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 control-label">刷卡时间<span
                    class="red_must">*</span></label>
                <input type="text" class="kuang" id="startStopTime" name="day"    placeholder="请输入刷卡时间">
        </div>
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <div class="col-sm-12 text-center">
                <button class="btn btn-primary" type="submit">提交</button>
            </div>
        </div>
    </form>
</div>

</body>
<script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
<script src="${ctxsta}/entity/offlineData/add.js"></script>
</html>

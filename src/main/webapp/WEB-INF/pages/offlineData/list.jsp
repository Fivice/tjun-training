<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>就餐脱机数据</title>
  </head>

<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
  <div class="ibox-content">
    <ul class="nav nav-tabs">
      <li class="active"><a href="${ctx}/offlineData/view.action"><i class="fa fa-list-alt"></i>刷卡就餐脱机数据</a></li>
      <li><a href="${ctx}/offlineData/view1.action">人脸就餐脱机数据</a></li>
    </ul>
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
          <div class="row row-lg">
            <div class="col-sm-12">
            <input type="hidden" id="contextPath" value="${contextPath }" />
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="新增" onclick="layer_show('新增一条就餐数据','${ctx}/offlineData/add/view.action','500','400')"> <i class="glyphicon glyphicon-plus"></i> </button>
              </div>&emsp;
              <div class="btn-group m-t-sm">
                <input type="text" class="form-control" value=${month} name="month" id="startStopTime" placeholder="请选择就餐时间">
              </div>&emsp;
              <div class="btn-group m-t-sm">
                <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="area">
                  <option value="">请选择就餐地点</option>
                  <c:forEach items="${list}" var="list">
                    <option value=${list}>${list}</option>
                  </c:forEach>
                </select>
              </div>&emsp;
              <div class="btn-group m-t-sm">
                <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="timess">
                  <option value="">请选择就餐点</option>
                  <option value="1">早餐</option>
                  <option value="2">中餐</option>
                  <option value="3">晚餐</option>
                </select>
              </div>&emsp;
              <div class="btn-group m-t-sm">
                <select style="width: 200px;height: 35px;border-color:#E0E0E0;" id="status">
                  <option value="">请选择上传结果</option>
                  <option value="1">成功</option>
                  <option value="2">失败</option>
                </select>
              </div>&emsp;
             <%-- <div class="btn-group m-t-sm">
            	 <button type="button" class="btn btn-default"  title="创建" onclick="layer_show('创建级别','${ctx}/role/add/view.action','900','480')"> <i class="glyphicon glyphicon-plus"></i> </button>
              </div>
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="编辑" onclick="role_edit()"> <i class="glyphicon glyphicon-pencil"></i> </button>
              </div>
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default"  title="删除" onclick="role_delete()" id="del"> <i class="glyphicon glyphicon-minus"></i> </button>
              </div>--%>
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-info" id="search" onclick="seach()"><i class="fa fa-search"></i>查询</button>
              </div>
              <div class="btn-group m-t-sm">
                <button id="reset" type="button" class="btn" onclick="reset()">重置</button>
              </div>
              <table id="table" class="table table-bordered table-striped"></table>
            </div>
          </div>
        </div>
      </div>
  </div>
</div>
</div>
</div>
</body>
<script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
<script src="${ctxsta }/entity/offlineData/list.js"></script>
</html>
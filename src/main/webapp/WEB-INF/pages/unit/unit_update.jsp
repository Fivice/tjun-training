<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>创建单位</title>
</head>
<div class="ibox-content">
    <form id="form" class="form-horizontal"  data-method="post">

        <input type="hidden" name="areaId" value="${unit.areaId}">
        <input type="hidden" name="sjareaId" value="${unit.sjareaId}">
        <input type="hidden" name="areaType" value="${unit.areaType}">
        <input type="hidden" name="status" value="${unit.status}">

        <div class="form-group m-t">
            <label class="col-sm-2 col-xs-offset-1 control-label">单位名称：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="areaName" value="${unit.areaName}">
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 联系人 :</label>
            <div class="col-sm-7">
                <input type="text"  class="form-control" name="contacts" value="${unit.contacts}">
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">联系方式：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="contactsTel" value="${unit.contactsTel}">
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">地址：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="address" value="${unit.address}">
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">邮箱：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="email" value="${unit.email}">
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label"><span style="color:red" > *</span> 排序: </label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="sort" value="${unit.sort}">
            </div>
        </div>

        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <label class="col-sm-2 col-xs-offset-1 control-label">备注信息：</label>
            <div class="col-sm-7">
                <textarea class="form-control" rows="2" name="remarks">${unit.remarks}</textarea>
            </div>
        </div>

    </form>
</div>
</body>
</html>

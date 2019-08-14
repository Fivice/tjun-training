<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>添加</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="form" class="form-horizontal"  data-method="post">
                        <input  class="form-control" name="classRoom" value="${classInfo.id}" type="hidden">
                        <div class="hr-line-dashed"></div>
                        <div class="form-group ">
                            <label class="col-sm-2 col-xs-offset-1 control-label">日期：</label>
                            <div class="col-sm-7 ">
                                <input type="text" class="form-control" name="day"  readonly="readonly" value="${startTime}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">早餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="breakfast">
                                    <option value='1' >就餐</option>
                                    <option value='2' >不就餐</option>
                                </select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">中餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="lunch">
                                    <option value='1' >就餐</option>
                                    <option value='2' >不就餐</option>
                                </select>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">晚餐：</label>
                            <div class="col-sm-7">
                                <select style="width: 488px;height: 34px;border-color:#E0E0E0;"  name="dinner">
                                    <option value='1' >就餐</option>
                                    <option value='2' >不就餐</option>
                                </select>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

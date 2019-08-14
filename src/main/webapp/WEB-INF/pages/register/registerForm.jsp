<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>报到登记</title>
    <script src="${ctxsta }/entity/register/classInfo.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/register/bootstrpValidator.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/register/register.js" type="text/javascript"></script>
<%--    <script src="${ctxsta }/entity/register/tree.js" type="text/javascript"></script>--%>
    <script src="${ctxsta }/entity/register/tree2.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox-content">
        <div class="box-header with-border">
            <div class="row">
                <div class="col-sm-8">
                    <i class="fa fa-list-alt"></i>报到登记<c:if test="${!empty userType }">&nbsp;&nbsp;[&nbsp;&nbsp;${userType
                        }&nbsp;&nbsp;<c:if test="${!empty type }">(前台)</c:if>&nbsp;&nbsp;]</c:if>
                </div>
                <div class="col-sm-4">
                    <select id="cho" class="form-control" style="width: auto;">
                        <option value="1">人脸读取</option>
                        <option value="2" selected>机器读卡</option>
                    </select>
                </div>
            </div>
        </div>
        <div style="margin-top: 20px">
            <!--搜索状态码-->
            <input id="search-status" hidden />
            <input id="startStopTime" type="hidden" value="" />
            <!--报名地点-->
            <input id="regPlace" type="hidden" value="${type}" />

            <div class="row">
                <!--报到表单-->
                <div class="col-sm-8">
                    <form id="inputForm" enctype="multipart/form-data" action="${ctx}/register/saveOrUpdate.action" method="post">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 身份证号：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <input id="classroomId" type="hidden" name="classroom" value="${classInfo.classroom }" class="form-control" />
                                        <input type="text" id="siIDNumber" name="siIDNumber" value="${student.siIDNumber}" class="form-control" />
                                        <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary" id="selectButton" title="读取身份证"><i class="fa fa-search"></i>读取</button>
                                    </span>
                                    </div>
                                </div>
                                <%--<div class="col-sm-2">--%>
                                <%--<button type="button" onclick="findStudent()" class="btn btn-sm btn-info" id="chooseButton"><i class="fa fa-search"></i>搜索</button>--%>
                                <%--</div>--%>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 证件照：<i class="fa icon-question hide"></i>
                                </label>
                                <div id="photo" class="col-sm-8">
                                    <%-- <img id="photo" style="width: 200px;height: 200px;" src="${ctx}/uploadPath/Desert.jpg" />--%>
                                    <%--<input type="text" id="photo" readonly name="photo" value="${student.photo}" class="form-control">--%>
                                        <img id="PhotoStr" />
                                </div>
                                <input type="hidden" name="PhotoStr" id="pStr"/>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 姓名：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="siName" name="siName" value="${student.siName}" class="form-control" />
                                </div>
                            </div>
                            <%--<div class="form-group">--%>
                            <%--<label class="control-label col-sm-4" title="">--%>
                            <%--<span class="required hide" aria-required="true">*</span> 流水号：<i class="fa icon-question hide"></i>--%>
                            <%--</label>--%>
                            <%--<div class="col-sm-8">--%>
                            <%--<input type="text" id="serialNumber" readonly name="serialNumber" value="${student.serialNumber}" class="form-control">--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 手机号码：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="phoneNumber" name="phoneNumber" value="${student.phoneNumber}" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 民族：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <%-- <input type="text" id="ethnicGroup" readonly name="ethnicGroup" value="" class="form-control">--%>
                                    <input type="hidden" id="ethnicGroup" name="ethnicGroup" value="汉" class="form-control" />
                                    <select id="national" class="form-control" onchange="selectNational()"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span>工作单位：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input id="ChanYeName" type="text" name="unitName" class="form-control" placeholder="请点击选择单位"
                                           readonly="readonly" />
                                    <%-- <input id="unitId" type="text"  name="unitId">--%>
                                    <!--若需要节点id，将节点id存储在此，然后上传到服务器-->
                                    <div id="info" style="z-index: 1;position:absolute; width:320px; height:180px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                                        <ul id="haveclasstree" class="ztree">

                                        </ul>
                                    </div>
                                    <input type="hidden" readonly id="unitId" name="unitId" class="form-control" />
                                    <%--<div class="form-inline">
                                        <select style="width: 245px;height: 35px;border-color:#E0E0E0;" id="unitId" onchange='getSeCourse(this)'>
                                            <c:forEach items="${ unitList}" var="unit">
                                                <option <c:if test="${unit.areaId eq student.unit.areaId}">selected</c:if> value="${unit.areaId}">${unit.areaName}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="text" id="siUnitId" name="siUnitId" value="${student.siUnitId}" class="form-control">
                                        <select style="width: 245px;height: 35px;border-color:#E0E0E0;" id="unitId2" >

                                        </select>
                                     </div>--%>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 培训天数：<i class="fa icon-question hide"></i></label>
                                <div class="col-sm-8">
                                    <input type="text" value="0" readonly id="dNum" name="dNum" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 实际住宿天数：<i class="fa icon-question hide"></i></label>
                                <div class="col-sm-8">
                                    <input type="text" value="0" id="dayInfact" name="dayInfact" class="form-control"/>
                                </div>
                            </div>


                            <input type="hidden" id="dayNum1">
                            <input type="hidden" value="0" id="dayNum" name="dayNum" class="form-control"/>
                            <input type="hidden" id="increaseDay" readonly name="increaseDay" class="form-control"/>

                            <%--<div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 住宿天数：<i class="fa icon-question hide"></i></label>
                                <div class="col-sm-8">
                                    <input type="hidden" id="dayNum1">
                                    <input type="text" value="0" id="dayNum" name="dayNum" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 住宿增加天数：<i class="fa icon-question hide"></i></label>
                                <div class="col-sm-8">
                                    <input type="text" id="increaseDay" readonly name="increaseDay" class="form-control"/>
                                </div>
                            </div>--%>


                            <%-- <div class="form-group">
                                    <label class="control-label col-sm-4" title="">
                                        <span class="required hide" aria-required="true">*</span> 住宿标准：<i class="fa icon-question hide"></i></label>
                                    <div class="col-sm-8">
                                        <select id="roomType" style="width: 345px;height: 35px;border-color:#E0E0E0;"  onchange='getroomType(this)'>
                                                <option value="0">标间</option>
                                                <option value="1">单间</option>
                                        </select>
                                    </div>
                                </div>--%>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 住宿费用（/天）：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <!--标间-->
                                    <input type="hidden" id="interScaleFee">
                                    <!--单间-->
                                    <input type="hidden" id="singleRoomCharge">

                                    <input type="hidden" id="scaleHotel">
                                    <!--住宿费用-->
                                    <input readonly type="text" value="" id="scaleFee" name="scaleFee" class="form-control"/>
                                </div>
                            </div>


                            <%--<div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 单间住宿费用：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" value="" id="singleRoomCharge" name="singleRoomCharge"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 标间住宿费用：<i class="fa icon-question hide"></i></label>
                                <div class="col-sm-8">
                                    <input type="text" value=""  id="interScaleFee" name="interScaleFee"  class="form-control">
                                </div>
                            </div>--%>

                        </div>
                        <div class="col-sm-6">

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 住宿地点：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input id="hPlace" type="hidden">
                                    <%--<input type="text" value=""  id="hotelPlace" name="hotelPlace" class="form-control">--%>
                                    <select class="form-control" id="hotelPlace" name="hotelPlace">
                                        <option value=""></option>
                                        <c:forEach items="${ list}" var="s">
                                            <option value="${s}">${s}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 就餐费用（/天）：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="hidden" id="interScale">
                                    <input type="text" value="" readonly id="interScaleFee2" name="interScaleFee" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 就餐地点：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input id="dPlace" type="hidden">
                                    <input type="text" value="" readonly id="diningPlace" name="diningPlace" class="form-control"/>
                                </div>
                            </div>

                            <%--<!--报到表单-->
                                <form id="inputForm" enctype="multipart/form-data"
                                      action="${ctx}/register/saveOrUpdate.action" method="post">--%>
                            <!--流水号-->
                            <%--<input type="hidden" id="number" name="number">--%>
                            <input type="hidden" id="siId" name="siId">

                            <input type="hidden" id="classId" name="classId">

                            <input type="hidden" id="pay" name="pay" value="2">

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 培训费：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" readonly value="0" id="trainingExpense" name="trainingExpense" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 其它费用：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" value="0" readonly id="otherCharges" name="otherCharges" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 总住宿费用：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" value="0" readonly id="scaleFeeTotal" name="scaleFeeTotal" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 总就餐费用：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="hidden" id="foodTotal2">
                                    <input type="text" readonly id="foodTotal" name="foodTotal" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 总食宿费：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input style="color: #0d6aad;font-size: 24px;" readonly styletype="text" value="0" id="ssf" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 总费用：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input style="color: #af0000;font-size: 24px;" readonly styletype="text" value="0" id="chargesTotal" class="form-control"/>
                                </div>
                                <%-- <div class="col-sm-2">
                                          <button type="button" onclick="calculationFee()" class="btn btn-sm btn-info"><i class="fa fa-plane"></i> 自定义费用</button>
                                      </div>--%>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 是否就餐：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <select id="dining" name="dining" class="form-control" onchange='getDining(this)'>
                                        <option value="1">就餐</option>
                                        <option value="2">不就餐</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 住宿标准：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <select id="hotel" name="hotel" class="form-control" onchange='getHotel(this)'>
                                        <c:choose>
                                            <c:when test="${empty hotelparam }">
                                                <option value="0" data-options="">标间</option>
                                                <option value="1" data-options="">单间</option>
                                                <option value="2" data-options="0">不住宿</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="1" data-options="">单间</option>
                                                <option value="0" data-options="">标间</option>
                                                <option value="2" data-options="0">不住宿</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 报到时间：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="reportTime" readonly name="reportTime" value="" class="form-control laydate"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 报到地点：<i class="fa icon-question hide"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" readonly id="place" name="place" value="" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required" style="color: #af0000" aria-required="true">*</span> 流水号：<i class="fa icon-question"></i>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="number" name="number" class="form-control"/>
                                </div>
                            </div>
                        </div>
                        <%--<div class="box-footer">
                            <div class="row">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-sm btn-primary" id="btnSubmit"><i class="fa fa-paper-plane"></i> 报 到</button>&nbsp;
                                </div>
                            </div>
                        </div>--%>

                    </form>
                    <hr/>
                    <div class="col-sm-12">
                        <div class="box-footer">
                            <div class="row">
                                <div class="col-sm-offset-2 col-sm-3">
                                    <button type="button" class="btn btn-primary btn-block" id="btnSubmit"><i class="fa fa-paper-plane"></i> 报 到</button>&nbsp;
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                 <!--学生流水号表单-->
                <%--<form id="inputForm" enctype="multipart/form-data" action="${ctx}/studentCard/saveOrUpdate.action" method="post">
                    <input type="hidden" id="number" name="number">
                    <input type="hidden" id="siId2" name="siId">
                    <input type="hidden" id="pay" name="pay" value="2">
                </form>--%>
                <div class="col-sm-4">


                        <div class="col-sm-12" style="padding: 0;">
                            <label>
                                选择班级:
                            </label>
                            <hr/>
                        </div>

                        <div class="col-sm-12" style="padding: 0;">
                            <div class="btn-group btn-group-justified">
                                <span class="btn btn-primary" id="searchAble" onclick="seachAbleClass()"><i class="fa fa-id-card-o"></i> &nbsp;正在报到</span>
                                <span class="btn btn-info" id="search" onclick="seachClass()"><i class="glyphicon glyphicon-blackboard"></i> &nbsp;正在开班</span>
                            </div>

                            <!-- 数据表格 -->
                            <table id="table" class="table table-hover"></table>
                            <%--<div class="panel panel-primary">
                                <div class="panel-heading">
                                    <i class="fa fa-list"></i>&nbsp;就餐安排信息列表
                                </div>
                                <div class="panel-body">

                                </div>
                            </div>--%>
                        </div>







                    <!--班级报到相关信息-->
                    <div id="panel" class="col-sm-12" style="padding: 0;display: none;">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 id="title" class="panel-title"></h3>
                            </div>
                            <div class="panel-body">

                                <div class="row">
                                    <!--内容-->
                                    <div class="col-sm-12">
                                    <!--第一行-->
                                    <%--                    <div class="row">
                                                            <div class="col-sm-8">
                                                                <p id="classTitle"></p>
                                                            </div>
                                                            <div class="col-sm-4">
                                                                <p><span>培训费：</span><span>培训费：</span></p>
                                                                <p><span>标间标准：</span><span>单间标准：</span></p>
                                                            </div>
                                                        </div>
                                                        <!--分割线-->
                                                        <hr>--%>
                                    <!--第二行-->
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <p id="classTime"></p>
                                        </div>
                                    </div>
                                    <!--分割线-->
                                    <hr>
                                    <!--第三行-->
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                    <tr>
                                                        <th>计划</th>
                                                        <th>报到</th>
                                                        <th>住宿</th>
                                                        <th>就餐</th>
                                                        <th>培训费</th>
                                                        <th>食宿费</th>
                                                        <th>其它费</th></tr>

                                                    </thead>
                                                    <tbody id="registerInfo">

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                </div>
                            </div>

                        </div>
                    </div>

                    <!--班级就餐安排-->
                    <div class="col-sm-12" style="padding: 0" id="repast">
                        <%--<div class="col-sm-11" style="padding: 0;margin-left:15px;margin-right:15px;">
                            <label>
                                就餐安排:
                            </label>
                            <hr/>
                        </div>--%>
                       <%-- <div class="col-sm-12" style="margin-top: 10px;" >--%>
                            <%--<label class="control-label col-sm-2" title="">--%>
                            <%--<span class="label label-primary">就餐安排</span>--%>
                            <%--</label>--%>
                                <!-- 就餐安排页面 -->
                                <jsp:include page="/WEB-INF/pages/classDining/classDining_list2.jsp" flush="true" />
                       <%-- </div>--%>
                    </div>

                </div>

        </div>
    </div>
</div>
    <object id="CertCtl" type="application/cert-reader" width="0" height="0" >
        <p style="color:#FF0000;">控件不可用，可能未正确安装控件及驱动，或者控件未启用。</p>
    </object>
<%--<jsp:include page="/WEB-INF/layouts/tree.jsp" flush="true"/>--%>
<script type="text/javascript" src="${ctxsta}/entity/register/registerWebSocket.js"></script>
<script>

$(document).ready(function(){
/* $.ajax({ //查询单位的下拉信息
type: 'post',
url: ctx+'/register/findInformation.action?query=unit',
dataType: "json", //返回的结果为json
success: function(data) {
for (var i = 0; i < data.unitList.length; i++) {
$("#unitId").append("<option>" + data.unitList[i].areaName + "</option>");
}
},
});*/
});

//日期时间选择器
$(".laydate").each(function(){
    laydate.render({
        elem: this, //指定元素  表示当前的元素
        theme: '#6CA6CD',
        type: 'datetime',
        value: new Date( Date.parse(new Date())) //参数即为：2018-08-20 20:08:08 的时间戳
        ,done: function(value, date, endDate){
            //移除上次的校验配置
            $("#inputForm").data('bootstrapValidator').destroy();
            $('#inputForm').data('bootstrapValidator',null);
            //重新添加校验
            formValidator();
        }
    })
});

/*$("#btnSubmit").click(function(){

    var studentId=$("#siId").val();
    var classId=$("#classId").val();
    //身份证号
    var siIDNumber = $("#siIDNumber").val();
    //正则验证是否符合身份证格式
    var patrn = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (!patrn.exec(siIDNumber)) {
        layer.msg("身份证格式不正确");
    }else{

        if(typeof studentId == "undefined" || studentId == null || studentId == ""){

            layer.alert("没有学生信息");
        } else if(typeof classId == "undefined" || classId == null || classId == ""){

            layer.alert("没有班级信息，请选择班级");
        }else{

            $.ajax({
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: ctx+"/register/findRegister.action",//url
                data: {
                    studentId:studentId,
                    classId:classId,
                },
                success: function (result) {
                    if (result.length==0) {
                        layer.confirm('是否确定该学员已缴费？', {
                            btn: ['已缴费','未缴费'] //按钮
                        }, function(){
                            //确认缴费
                            $("#pay").prop("value","1");
                            formRegister();
                        }, function(){
                            formRegister();
                            /!*layer.closeAll();*!/
                        });
                    }else{
                        layer.alert("该学生已经报到！");
                    }
                },
                error : function() {
                    layer.alert("错误信息！请联系管理者！");
                }
            });


        }
    }



})*/

//查询正在开班的班级
function seachClass(){
    var date = new Date();//获取当前时间
    var time = date.Format("yyyy-MM-dd");
    $("#startStopTime").prop("value",time);
    $("#search-status").prop("value",1);
    //console.log($("#search-status").val());
    $("#table").bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
    $("#repast").hide();

}

//查询可用班级列表
function seachAbleClass(){
    var date = new Date();//获取当前时间
    //date.setDate(date.getDate()-1);//设置天数 -1 天
    var time = date.Format("yyyy-MM-dd");
    $("#startStopTime").prop("value",time);
    $("#search-status").prop("value",2);
    //console.log($("#search-status").val());
    $("#table").bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
    $("#repast").hide();
}




//表单提交
function formSub(id,url){
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: ctx+url,//url
        data: $('#'+id).serialize(),
        success: function (result) {
            if (result.code == 1) {
                parent.layer.msg("提交成功!", {
                    shade: 0.3,
                    time: 1500
                }, function () {
                    window.location.href=ctx+"/register/form.action"; // 刷新页面

                });
            }
        },
        error : function() {
            layer.alert("异常！请联系管理者");
        }
    });

}
//提交报到登记的form表单
    function formRegister(){
        formSub("inputForm","/register/saveOrUpdate.action");
       /* $.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: ctx+"/register/saveOrUpdate.action" ,//url
            data: $('#inputForm').serialize(),
            success: function (result) {
                if (result.code == 1) {
                    parent.layer.msg("提交成功!", {
                        shade: 0.3,
                        time: 1500
                    }, function () {
                        window.location.href=ctx+"/register/form.action"; // 刷新页面

                    });
                }
            },
            error : function() {
                layer.alert("异常！请联系管理者");
            }
        });*/
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
//查询学生
$("#siIDNumber").blur(function(){
    findStudent();
});

//查询学生
$("#selectButton").click(function(){
    var cho=$("#cho").val();
    //查询身份证号
    if (cho==1){
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: ctx + '/register/idNumber.action',
            success: function (result) {
                console.log(result)
                if(result!=null && result.length>0){
                    $("#siIDNumber").prop("value",result[0].idNumber);
                    findStudent();
                }
            },
            error: function () {
                layer.alert("错误信息！请联系管理者！");
            }
        })
    }
    //人机读卡
    if(cho==2){
        //alert("renji")
        readCert();
    }
});
function toJson(str)
{
    //var obj = JSON.parse(str);
    //return obj;
    return eval('('+str+')');
}
function clearForm()
{
    //document.getElementById("PhotoStr").value = "";
    $("#siName").val("");
    $("#ethnicGroup").val("");
    $("#siIDNumber").val("");
    $("#PhotoStr").val("");
    $("#pStr").val("");

}
//机器读卡
function readCert(){
    clearForm();

    var CertCtl = document.getElementById("CertCtl");

    try {
        CertCtl.connect();
        var result = CertCtl.readCert();
        console.log(result);
        //document.getElementById("result").value = result;
//
        var resultObj = toJson(result);
        console.log(resultObj.resultContent.partyName)
        if (resultObj.resultFlag == 0) {

            //姓名
            document.getElementById("siName").value = resultObj.resultContent.partyName;
            //身份证
            document.getElementById("siIDNumber").value = resultObj.resultContent.certNumber;
            //民族
            document.getElementById("ethnicGroup").value = resultObj.resultContent.nation;
            //证件照
            document.getElementById("PhotoStr").src = "data:image/jpeg;base64," + resultObj.resultContent.identityPic;

            //证件照隐藏域
            document.getElementById("pStr").value = resultObj.resultContent.identityPic;
        }
    } catch (e) {
        alert(e);
    }
}
</script>

    <!-- 弹出框的头部 -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title" id="modal-title">请选择需缴纳的费用</h4>
                </div>
                <!-- 弹出框的内容   -->
                <div class="modal-body">
                    <%--<div class="row">
                        <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">全选</label>
                            <input type="checkbox" id="allChecked">
                        </div>
                        </div>
                    </div>--%>
                    <div class="row">

                            <div class="col-sm-6">

                            <div class="form-group">
                                <label class="control-label">培训费</label>
                                    <input type='checkbox' name='fee' id="training">
                            </div>

                            <div class="form-group">
                                <label class="control-label">其它费</label>
                                    <input type='checkbox' name='fee' id="other">
                            </div>
                            </div>

                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label">住宿费</label>
                                    <input type='checkbox' name='fee' id="scalefees">
                            </div>

                            <div class="form-group">
                                <label class="control-label">就餐费</label>
                                    <input type='checkbox' name='fee' id="food">
                            </div>
                            </div>
                    </div>

                    <div class="row">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">所需缴纳费用</label>
                            <input style="color: #af0000" type="text" class="form-control" placeholder="总费用" id="total_cost">
                        </div>
                    </div>
                    <%--<div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">需要补交</label>
                            <input type="text" class="form-control" placeholder="需要补交" id="after_payment">
                        </div>
                    </div>--%>
                </div>
                        <div class="row">
                        <div class="col-sm-6">
                            <button type="button" class="btn btn-sm btn-success" id="countFee" onclick="computation_fee()">计算费用</button>
                        </div>
                        </div>
                </div>
                <!-- 弹出框的底部 -->
                <div class="modal-footer">
                    <!-- 设置了data-dismiss="modal"属性，就可以关闭该弹出框 -->
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="definition_fee()">确定</button>
                </div>
            </div>
        </div>
    </div>
    <script>
       /* $(function() {
            //费用全选
            allChecked("#allChecked","fee");
        });*/

       //住宿天数失去焦点事情
    /*   $("#dayNum").blur(function(){
           coutFee();
       });*/

       //实际住宿天数失去焦点事情
       $("#dayInfact").blur(function(){
           coutFee();
       });

       //计算费用
       function coutFee(){

           var classId = $("#classId").val();
           if (typeof classId != "undefined" && classId != null && classId != "") {

           /*    //获取培训天数
               var dayNum = $("#dayNum").val();
               //获取食宿增加天数
               var increaseDay = $("#increaseDay").val();
               //实际住宿天数
               var day = (dayNum * 1 + increaseDay * 1);*/

               var day = $("#dayInfact").val();

               //培训费
               var trainingExpense = parseInt($("#trainingExpense").val())*1;
               //其它费用
               var otherCharges = parseInt($("#otherCharges").val())*1;

               //总住宿费(住宿费用*天数)
               $("#scaleFeeTotal").prop("value", $("#scaleFee").val() * day);

               //食宿费(总住宿费用+总就餐费用)
               $("#ssf").prop("value", $("#scaleFeeTotal").val() * 1 + $("#foodTotal").val() * 1);

               // 总费用（总就餐费用+总住宿费用+其它费用+培训费）
               $("#chargesTotal").prop("value", $("#foodTotal").val()*1+parseInt($("#scaleFee").val()*day)+otherCharges+trainingExpense);
           }
       }

    </script>


</body>
</html>
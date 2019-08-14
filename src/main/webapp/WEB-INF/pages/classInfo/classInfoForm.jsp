<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>班级列表</title>
    <script src="${ctxsta }/entity/classInfo/bootstrpValidator.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script src="${ctxsta }/entity/classInfo/tree.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/classInfo/departTree.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="${ctxsta }/easyUpload/easy-upload.css" />
    <script type="text/javascript" src="${ctxsta }/easyUpload/easyUpload.js"></script>

<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">

    <div class="box-header with-border">
        <div class="box-title">
            <i class="fa fa-list-alt"></i> ${not empty classInfo.id?'修改':'新增'}数据
        </div>
    </div>

    <form id="inputForm" enctype="multipart/form-data"
          action="${ctx}/classInfo/saveOrUpdate.action" method="post" class="form-horizontal">
    <input type="hidden" id="classInfoId" name="id" value="${classInfo.id}"/>
        <input type="hidden" name="createBy" value="${classInfo.createBy}"/>
        <input type="hidden" name="createDate" value="${classInfo.createDate}"/>
        <div style="margin-top: 20px">
            <fieldset>
            <legend>基本信息</legend>
            </fieldset>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 项目编号：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="projectNumber" name="projectNumber" value="${classInfo.projectNumber}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 班级编号：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="classNumber" name="classNumber" <c:if test="${ empty classInfo.id}">value="${m }" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.classNumber}" </c:if> class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 班主任：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                           <%-- <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="userId" name="userId">
                                <c:forEach items="${ teacherInfoList}" var="teacherInfo">
                                    <option <c:if test="${teacherInfo.tiId eq classInfo.userId}">selected</c:if> value="${teacherInfo.tiId}">${teacherInfo.name}</option>
                                </c:forEach>
                            </select>--%>
                               <select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="userId" name="userId">
                                   <c:forEach items="${ sysUserList}" var="user">
                                       <option <c:if test="${user.userId eq classInfo.userId}">selected</c:if> value="${user.userId}">${user.userName}</option>
                                   </c:forEach>
                               </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 状态：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="status" name="status">
                                <option value="0" <c:if test="${'0' eq classInfo.status}">selected</c:if>>开放</option>
                                <option value="1" <c:if test="${'1' eq classInfo.status}">selected</c:if>>关闭</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span>计划：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="plan" name="plan">
                                <option value="0" <c:if test="${'0' eq classInfo.plan}">selected</c:if>>计划内</option>
                                <option value="1" <c:if test="${'1' eq classInfo.plan}">selected</c:if>>计划外</option>
                                <option value="2" <c:if test="${'0' eq classInfo.plan}">selected</c:if>>非培训班</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span>评价：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="evaluate" name="evaluate">
                                <option value="1" <c:if test="${'1' eq classInfo.evaluate }">selected</c:if>>未评价</option>
                                <option value="0" <c:if test="${'0' eq classInfo.evaluate }">selected</c:if>>已评价</option>
                            </select>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 班级名称：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                           <input type="text" id="className" name="className" value="${classInfo.className}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训对象：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="trainingObject" name="trainingObject" value="${classInfo.trainingObject}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训类型：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="typeId" name="typeId">
                                <c:forEach items="${ trainingTypeList}" var="trainingType">
                                    <option <c:if test="${trainingType.id eq classInfo.typeId}">selected</c:if> value="${trainingType.id}">${trainingType.type}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 计划人数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="plannedNumber" name="plannedNumber" value="${classInfo.plannedNumber}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 办班时间：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" readonly id="startStopTime" name="startStopTime" value="${classInfo.startStopTime}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 联系方式：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="phoneNumber" name="phoneNumber" value="${classInfo.phoneNumber}" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span>主办单位：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-4">
                                <input id="unitId"  type="hidden" name="siUnitId" value="${classInfo.siUnitId }" class="form-control">
                                <input type="text" id="hostUnit"  name="hostUnit" value="${classInfo.hostUnit }" class="form-control">

                           <%-- <select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="unitId" name="siUnitId">
                                <option ></option>
                                <c:forEach items="${ unitList}" var="unit">
                                    <option <c:if test="${unit.areaId eq classInfo.siUnitId}">selected</c:if> value="${unit.areaId}">${unit.areaName}</option>
                                </c:forEach>
                            </select>--%>
                        </div>
                        <div class="col-sm-2">
                            <button id="hostUnitButton" type="button" onclick="findUnit(this)" class="btn btn-sm btn-info"><i class="fa fa-eye"></i> 选择</button>
                        </div>
                        <div class="col-sm-2">
                            <button id="resetHostUnit" type="button" class="btn" onclick="addUnit()">新增</button>
                        </div>
                        <%--<div class="col-sm-2">
                            <button id="resetHostUnit" type="button" class="btn" onclick="resetUnit(this)">清除</button>
                        </div>--%>
                    </div>
                </div>
              <%--  <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 主办单位：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="hostUnit" name="hostUnit" value="${classInfo.hostUnit}" class="form-control">
                        </div>
                    </div>
                </div>--%>

                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 承办单位：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-4">
                            <%-- <select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="organizerUnitId" name="organizerUnitId">
                                 <option ></option>
                                 <c:forEach items="${ unitList}" var="unit">
                                     <option <c:if test="${unit.areaId eq classInfo.organizerUnitId}">selected</c:if> value="${unit.areaId}">${unit.areaName}</option>
                                 </c:forEach>
                             </select>--%>
                                <input id="organizerUnitId"  type="hidden" name="organizerUnitId" value="${classInfo.organizerUnitId }" class="form-control">
                                <input type="text" id="organizerUnit"  name="organizerUnit" value="${classInfo.organizerUnit }" class="form-control">
                                <div id="info" style="z-index: 1;position:absolute; width:320px; height:180px; top:33px; background:#fff; border:1px solid #eee;overflow-y:auto; display:none;">
                                    <ul id="haveclasstree" class="ztree"></ul>
                                </div>
                            <%-- <select style="width: 345px;height: 35px;border-color:#E0E0E0;" id="unitId" name="siUnitId">
                                 <option ></option>
                                 <c:forEach items="${ unitList}" var="unit">
                                     <option <c:if test="${unit.areaId eq classInfo.siUnitId}">selected</c:if> value="${unit.areaId}">${unit.areaName}</option>
                                 </c:forEach>
                             </select>--%>
                        </div>
                        <%--<div class="col-sm-2">
                            <button id="organizerUnitButton"  type="button" onclick="findUnit(this)" class="btn btn-sm btn-info"><i class="fa fa-eye"></i> 选择</button>
                        </div>--%>
                        <div class="col-sm-2">
                            <button id="resetOrganizerUnit" type="button" class="btn" onclick="resetUnit(this)">清除</button>
                        </div>
                    </div>
                </div>

                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训费：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" <c:if test="${ empty classInfo.id}">value="0" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.trainingExpense}" </c:if> id="trainingExpense" name="trainingExpense" value="${classInfo.trainingExpense}" class="form-control">
                        </div>
                    </div>
                </div>

              <%--  <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训教室：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <div class="form-inline">
                            <input id="classroomId"  type="hidden" name="classroom" value="${classInfo.classroom }" class="form-control">
                            <input type="text" id="classroomNameId"  name="classroomName" value="${classInfo.classroomName }" class="form-control">
                                <button type="button" onclick="chooseClassroom()" class="btn btn-sm btn-info" id="chooseButton"><i class="fa fa-eye"></i> 选择</button>
                            </div>
                        </div>
                        &lt;%&ndash;<div class="col-sm-1">
                            <button type="button" onclick="chooseClassroom()" class="btn btn-sm btn-info" id="chooseButton"><i class="fa fa-eye"></i> 选择</button>
                        </div>&ndash;%&gt;
                    </div>
                </div>--%>
            </div>
           <%-- <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 承办单位：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                        <div class="form-inline">
                    <input type="text" class="form-control" placeholder="关键字" />
                    <button class="btn btn-primary">搜 索</button>
                </div>
                </div>
                </div>
                </div>
            </div>--%>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 标间费用：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" <c:if test="${ empty classInfo.id}">value="${houseStandard0 }" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.interScaleFee}" </c:if> id="interScaleFee" name="interScaleFee" value="${classInfo.interScaleFee}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 单间费用：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input <c:if test="${ empty classInfo.id}">value="${houseStandard1 }" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.singleRoomCharge}" </c:if> type="text" id="singleRoomCharge" name="singleRoomCharge" value="${classInfo.singleRoomCharge}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 其它费用：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" <c:if test="${ empty classInfo.id}">value="0" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.otherCharges}" </c:if> id="otherCharges" name="otherCharges" value="${classInfo.trainingExpense}" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 报名地点：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="regPlace" name="regPlace">
                                <option ></option>
                                <c:forEach items="${ list}" var="s">
                                    <option <c:if test="${s eq classInfo.regPlace}">selected</c:if> value="${s}">${s}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 住宿地点：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="hotelPlace" name="hotelPlace">
                                <option ></option>
                                <c:forEach items="${ list}" var="s">
                                    <option <c:if test="${s eq classInfo.hotelPlace}">selected</c:if> value="${s}">${s}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 就餐地点：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <select style="width: 335px;height: 35px;border-color:#E0E0E0;" id="diningPlace" name="diningPlace">
                                <option ></option>
                                <c:forEach items="${ diningPlaceList}" var="s">
                                    <option <c:if test="${s eq classInfo.diningPlace}">selected</c:if> value="${s}">${s}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 早餐标准：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="breakfast" name="breakfast" <c:if test="${ empty classInfo.id}">value="${eatStandard0 }" </c:if>  <c:if test="${not empty classInfo.id}">value="${classInfo.breakfast}" </c:if> class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span>午餐标准：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="lunch" name="lunch" <c:if test="${ empty classInfo.id}">value="${eatStandard1 }" </c:if>  <c:if test="${not empty classInfo.id}">value="${classInfo.lunch}" </c:if> class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 晚餐标准：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="dinner" name="dinner" <c:if test="${ empty classInfo.id}">value="${eatStandard2 }" </c:if> <c:if test="${not empty classInfo.id}">value="${classInfo.dinner}" </c:if> class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span>培训天数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" readonly id="dayNum" name="dayNum" value="${classInfo.dayNum }" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 学时：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="timeNum" name="timeNum" <c:if test="${ empty classInfo.id}">value="8" </c:if> <c:if test="${not empty classInfo.id}">value="${classInfo.timeNum}" </c:if> class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 食宿增加天数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" id="increaseDay" name="increaseDay" <c:if test="${ empty classInfo.id}">value="0" </c:if> <c:if test="${not empty classInfo.id}">value="${classInfo.increaseDay}"</c:if> class="form-control">
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 报名开始时间：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" readonly id="entryStartTime" name="entryStartTime" value="${classInfo.entryStartTime}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 报名结束时间：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" readonly id="entryStopTime" name="entryStopTime" value="${classInfo.entryStopTime}" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 评价开始时间：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text" readonly id="evaluateStartTime" name="evaluateStartTime" value="${classInfo.evaluateStartTime}" class="form-control">
                        </div>
                    </div>
                </div>

            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 评价结束时间：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input  type="text" readonly id="evaluateStopTime" name="evaluateStopTime" value="${classInfo.evaluateStopTime}" class="form-control">
                        </div>
                    </div>
                </div>

            </div>


            <div class="row">
                <div class="col-xs-8">
                <div class="form-group">
                    <label class="control-label col-sm-2" title="">
                        <span class="required hide" aria-required="true">*</span> 教室：<i class="fa icon-question hide"></i></label>
                    <div class="col-sm-10">
                        <div class="form-inline ">
                            <select id="campusId" class="form-control" style="width: 180px;" onchange="findClassroom(this)">
                                <option value="">请选择校区</option>
                                <c:forEach items="${campusList}" var="campusList">
                                    <option value=${campusList.schoolName} <c:if test="${schoolName eq campusList.schoolName}">selected</c:if> >${campusList.schoolName}</option>
                                </c:forEach>
                            </select>
                            <!--教室id-->
                            <input type="hidden" id="cRoomId" value="${cRoomId }">
                            <!--校区id-->
                            <input type="hidden" id="cAmId" value="${cAmId }">
                            <!--校区名称-->
                            <input type="hidden" id="schoolName" value="${schoolName }">
                            <select id="classroom" name="classroom" class="form-control" style="width: 180px;" onchange="getClassroom(this)">
                                <option value=''>请选择教室</option>;
                            </select>
                            <input type="text" id="classroomNameId"  name="classroomName" value="${classInfo.classroomName }" class="form-control" placeholder="请您输入教室名称" />
                        </div>
                    </div>
                </div>
                </div>

            </div>


            <div class="row">

            <div class="col-xs-4">
                <div class="form-group">
                    <label class="control-label col-sm-4" title="">
                        <span class="required hide" aria-required="true">*</span> 相关文件：<i class="fa icon-question hide"></i></label>
                    <div class="col-sm-8">
                        <div id="easyContainer"></div>
                        <input  type="hidden" id="fileUrl" name="fileUrl" value="${classInfo.fileUrl}" class="form-control">
                    </div>
                </div>
            </div>

            </div>


            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 简介：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <textarea rows="4" type="text" id="cintroduce" name="cintroduce" value="${classInfo.cintroduce}" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 费用说明：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <textarea rows="4" type="text" id="expenseExplanation" name="expenseExplanation" value="${classInfo.expenseExplanation}" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 备注：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <textarea rows="4" type="text" id="remarks" name="remarks" value="${classInfo.remarks}" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        <div class="box-footer">
            <div class="row">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:if test="${empty query }">
                    <button type="submit" class="btn btn-sm btn-primary" id="btnSubmit"><i class="fa fa-check"></i> 保 存</button>&nbsp;
                    </c:if>
                        <button type="button" class="btn btn-sm btn-default" id="btnCancel" onclick="history.go(-1)"><i class="fa fa-reply-all"></i> 关 闭</button>
            </div>
        </div>
        </div>
        </div>
        <jsp:include page="/WEB-INF/layouts/tree.jsp" flush="true"/>
        <%--<jsp:include page="/WEB-INF/pages/classInfo/mode.jsp" flush="true"/>--%>
    </form>
</div>
</div>
<script>
    //获取下一天
    function getNextDate(dayStr){
        var dd = new Date(dayStr);
        dd.setDate(dd.getDate()+1);
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        return y+"-"+m+"-"+d;
    };

    //日期时间选择器
    $(".laydate").each(function(){
        laydate.render({
            elem: this, //指定元素  表示当前的元素
            theme: '#6CA6CD',
            type: 'datetime',
            value: new Date( Date.parse(new Date())) //参数即为：2018-08-20 20:08:08 的时间戳
        })
    });

    //年月范围选择
    laydate.render({
        elem: '#startStopTime'
        ,type: 'date'
        ,theme: '#6CA6CD'
        ,range: '至' //或 range: '~' 来自定义分割字符
        ,done: function(value, date, endDate){
            arr=value.split('至');//注split可以用字符或字符串分割
            var time1 = arr[0];
            var time2 = arr[1];
            var days=DateDiff(time1,time2);  //调用/计算两个日期天数差的函数，通用
            if(days!=null && typeof(days)!="undefined" && !isNaN(days) ){
                /*$("#dayNum").prop("value",days)*/
                $("#dayNum").prop("value",days*1+1)
            }else{
                $("#dayNum").prop("value",0)
            }
            if(time1==""){
                $("#entryStartTime").prop("value","");
                $("#entryStopTime").prop("value","");
            }else{
                 $("#entryStartTime").prop("value",time1+" 00:00:00");
                 $("#entryStopTime").prop("value",getNextDate(time1)+" 23:59:59");
            }
            if(typeof(time2)=="undefined"){
                $("#evaluateStartTime").prop("value","");
                $("#evaluateStopTime").prop("value","");
            }else{
                $("#evaluateStartTime").prop("value",time2+" 00:00:00");
                $("#evaluateStopTime").prop("value",time2+" 23:59:59");
            }


            //移除上次的校验配置
            $("#inputForm").data('bootstrapValidator').destroy();
            $('#inputForm').data('bootstrapValidator',null);
           //重新添加校验
            formValidator();
        }
    });


    $(document).ready(function(){
        //输入框的值改变时触发
       /* $("#dayNum").on("input",function(e){
            //获取input输入的值
            console.log(e.delegateTarget.value);
            addByTransDate("2018-08-02", 5);
        });
*/

        /*$('input').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
            increaseArea: '20%',
            labelHover: true,
        });*/
    });

       //查询教室
        function chooseClassroom() {
            $.ajax({
                type : 'get',
                url : ctx + '/classroom/findList.action',//发送请求
                dataType : "json",
                success : function(result) {
                    var html = "";
                    for (var i = 0; i < result.length; i++) {
                        html += "<tr>";
                        html += "<td><input type='checkbox' name='roomId' value='"+ result[i].classroom.id+"' data-roomName='"+ result[i].classroom.className+"'></td>"
                        html += "<td>" + result[i].classroom.schoolName + "</td>"
                        html += "<td>" + result[i].classroom.className + "</td>"
                        html += "<td>" + result[i].classroom.capacity + "</td>"
                        html += "</tr>";
                    }
                    $("#J_TbData").html(html);
                    $('#myModal').modal('show');
                },
                error : function(error){
                    layer.alert("加载失败");
                }
            });

        };
        //选择教室
    function choose(){
        var strIds = [];
        var roomNames = [];
        $("input[name='roomId']:checked").each(function(){
            strIds.push($(this).attr('value'));
            roomNames.push($(this).attr('data-roomName'));
        });
        if (strIds.length == 0) {
            layer.alert('请至少选择一条数据！');
        }else if (strIds.length >1 ) {
            layer.alert('只能选择一条数据！');
        }else{
            $("#classroomId").prop("value", strIds[0]);
            $("#classroomNameId").prop("value", roomNames[0]);
            //关闭模态框
            $('#myModal').modal('hide');
        }
    }

    uploadFile("easyContainer", ctx+"/upload/easyUpload.action","fileUrl",$("#fileUrl").val());

    /*$('#easyContainer').easyUpload({
        allowFileTypes: '*.jpg;*.doc;*.pdf;*.zip;',//允许上传文件类型，格式';*.doc;*.pdf'
        allowFileSize: 100000,//允许上传文件大小(KB)
        selectText: '选择文件',//选择文件按钮文案
        multi: false,//是否允许多文件上传
        /!*multiNum: 5,//多文件上传时允许的文件数*!/
        showNote: true,//是否展示文件上传说明
        note: '提示：支持格式为doc、pdf、jpg、zip',//文件上传说明
        showPreview: true,//是否显示文件预览
        url: ctx+'/upload/easyUpload.action',//上传文件地址
        fileName: 'file',//文件filename配置参数
        /!*formParam: {
            token: $.cookie('token_cookie')//不需要验证token时可以去掉
        },//文件filename以外的配置参数，格式：{key1:value1,key2:value2}*!/
        timeout: 30000,//请求超时时间
        okCode: 200,//与后端返回数据code值一致时执行成功回调，不配置默认200
        successFunc: function(res) {
            $("#fileUrl").attr("value",res.substring(1,res.length - 1));
          /!* alert(res.substring(1,res.length - 1))*!/
            console.log('成功回调', res);
        },//上传成功回调函数
        errorFunc: function(res) {
            console.log('失败回调', res);
            alert(res.code)
        },//上传失败回调函数
        deleteFunc: function(res) {
            console.log('删除回调', res);
        }//删除文件回调函数
    });*/

    $("#unitId").change(function(){
        $("#hostUnit").attr("value",$(this).find("option:selected").text());
    });
    $("#organizerUnitId").change(function(){
        $("#organizerUnit").attr("value",$(this).find("option:selected").text());
    });
   function addUnit() {
       window.location.href = ctx +'/unit/view.action';
   }


</script>
</body>
</html>
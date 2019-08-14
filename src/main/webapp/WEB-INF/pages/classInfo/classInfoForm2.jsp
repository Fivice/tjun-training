<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!DOCTYPE HTML>
<html>
<head>
    <title>班级列表</title>
    <link href="${ctxsta }/FileUploadQT/css/iconfont.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxsta }/FileUploadQT/css/fileUpload.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="${ctxsta }/bootstrap/bootstrap-tagsinput/bootstrap-tagsinput.css" />
    <script src="${ctxsta }/bootstrap/bootstrap-tagsinput/bootstrap-tagsinput.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxsta }/FileUploadQT/js/fileUpload.js"></script>
    <script src="${ctxsta }/entity/classInfo/bootstrpValidator2.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
    <script src="${ctxsta }/entity/classInfo/tree.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/classInfo/departTree.js" type="text/javascript"></script>

    <link href="${ctxsta }/select2/select2.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxsta }/select2/select2.js" type="text/javascript"></script>

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
                                <span class="required" aria-required="true" style="color: #af0000">*</span> 班级编号：<i class="fa icon-question"></i></label>
                            <div class="col-sm-8">
                                <%--<input type="text" id="classNumber" name="classNumber" <c:if test="${ empty classInfo.id}">value="${m }" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.classNumber}" </c:if> class="form-control">--%>
                                    <input type="text" id="classNumber" name="classNumber" value="${classInfo.classNumber}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 班级名称：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text" id="className" name="className" value="${classInfo.className}" class="form-control">
                            </div>
                        </div>
                    </div>

                </div>

                <div class="row">
                    <%--    <div class="col-xs-4">
                            <div class="form-group">
                                <label class="control-label col-sm-4" title="">
                                    <span class="required hide" aria-required="true">*</span> 状态：<i class="fa icon-question hide"></i></label>
                                <div class="col-sm-8">
                                    <select style="width: 300px;height: 35px;border-color:#E0E0E0;" id="status" name="status">
                                        <option value="0" <c:if test="${'0' eq classInfo.status}">selected</c:if>>关闭</option>
                                        <option value="1" <c:if test="${'1' eq classInfo.status}">selected</c:if>>开放</option>
                                    </select>
                                </div>
                            </div>
                        </div>--%>
                    <input type="hidden" id="status" name="status" value="1" class="form-control">
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
                                <select class="form-control" id="userId" name="userId" onchange="findPhone()">

                                    <c:forEach items="${sysUserList}" var="user">
                                        <%--<option <c:if test="${user.userId eq classInfo.userId}">selected</c:if> value="${user.userId}" data-telephone="${user.telephone }">${user.userName}</option>--%>
                                        <c:choose>
                                            <c:when test="${classInfo.id==null && user.userId eq buserId }">
                                                <option selected value="${user.userId}" data-telephone="${user.telephone }">${user.userName}</option>
                                            </c:when>
                                            <c:when test="${user.userId eq classInfo.userId }">
                                                <option selected value="${user.userId}" data-telephone="${user.telephone }">${user.userName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${user.userId}" data-telephone="${user.telephone }">${user.userName}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>

                                </select>
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
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span>评价：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <select class="form-control" id="evaluate" name="evaluate">
                                    <option value="1" <c:if test="${'1' eq classInfo.evaluate }">selected</c:if>>不参评</option>
                                    <option value="0" <c:if test="${'0' eq classInfo.evaluate }">selected</c:if>>参评</option>
                                </select>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="row">
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span>计划：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <select class="form-control" id="plan" name="plan">
                                    <option value="0" <c:if test="${'0' eq classInfo.plan}">selected</c:if>>计划内</option>
                                    <option value="1" <c:if test="${'1' eq classInfo.plan}">selected</c:if>>计划外</option>
                                    <option value="2" <c:if test="${'2' eq classInfo.plan}">selected</c:if>>非培训班</option>
                                </select>
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
                                <select class="form-control" id="typeId" name="typeId">
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
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 办班时间：<i class="fa icon-question"></i></label>
                            <div class="col-sm-8">
                                <input type="text" readonly id="startStopTime" name="startStopTime" value="${classInfo.startStopTime}" class="form-control">
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 教室：<i class="fa icon-question hide"></i></label>
                            <%--<div class="col-sm-4">--%>

                                <%--<select id="classroom" name="classroom" class="form-control" class="form-control">
                                    <c:forEach items="${campusList}" var="campusList">
                                        <option value=${campusList.id} <c:if test="${campusId eq campusList.id}">selected</c:if> >${campusList.schoolName}</option>
                                    </c:forEach>
                                </select>--%>

                               <%-- <select id="campusId" class="form-control" class="form-control" onchange="findClassroom(this)">
                                    &lt;%&ndash; <option value="">请选择校区</option>&ndash;%&gt;
                                    <c:forEach items="${campusList}" var="campusList">
                                        <option value=${campusList.schoolName} <c:if test="${schoolName eq campusList.schoolName}">selected</c:if> >${campusList.schoolName}</option>
                                    </c:forEach>
                                </select>
                                <!--教室id-->
                                <input type="hidden" id="cRoomId" value="${cRoomId }">
                                <!--校区id-->
                                <input type="hidden" id="cAmId" value="${cAmId }">
                                <!--校区名称-->
                                <input type="hidden" id="schoolName" value="${schoolName }">--%>

                            <%--</div>--%>
                            <div class="col-sm-8">
                                <%--<select id="classroom" name="classroom" class="form-control" class="form-control" onchange="getClassroom(this)">
                                    &lt;%&ndash; <option value=''>请选择教室</option>;&ndash;%&gt;
                                </select>--%>
                               <%-- <input type="hidden" id="classroomNameId"  name="classroomName" value="${classInfo.classroomName }">--%>
                                <input type="text" id="classroomNameId"  name="classroomName" value="${classInfo.classroomName }" class="form-control" <%--placeholder="请您输入教室名称"--%> />
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
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 标间费用：<i class="fa icon-question"></i></label>
                            <div class="col-sm-8">
                                <input type="text" readonly <c:if test="${ empty classInfo.id}">value="${houseStandard0 }" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.interScaleFee}" </c:if> id="interScaleFee" name="interScaleFee" value="${classInfo.interScaleFee}" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 单间费用：<i class="fa icon-question"></i></label>
                            <div class="col-sm-8">
                                <input  readonly <c:if test="${ empty classInfo.id}">value="${houseStandard1 }" </c:if> <c:if test="${ not empty classInfo.id}">value="${classInfo.singleRoomCharge}" </c:if> type="text" id="singleRoomCharge" name="singleRoomCharge" value="${classInfo.singleRoomCharge}" class="form-control">
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
                                <span class="required hide" aria-required="true">*</span> 报到地点：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <select class="form-control" id="regPlace" name="regPlace">
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
                                <select class="form-control" id="hotelPlace" name="hotelPlace">
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
                                <select class="form-control" id="diningPlace" name="diningPlace">
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
                                <span class="required" style="color: #af0000" aria-required="true">*</span> 早餐标准：<i class="fa icon-question"></i></label>
                            <div class="col-sm-8">
                                <input type="text" readonly id="breakfast"  name="breakfast" <c:if test="${ empty classInfo.id}">value="${eatStandard0 }" </c:if>  <c:if test="${not empty classInfo.id}">value="${classInfo.breakfast}" </c:if> class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" style="color: #af0000" aria-required="true">*</span>午餐标准：<i class="fa icon-question"></i></label>
                            <div class="col-sm-8">
                                <input type="text" readonly id="lunch"  name="lunch" <c:if test="${ empty classInfo.id}">value="${eatStandard1 }" </c:if>  <c:if test="${not empty classInfo.id}">value="${classInfo.lunch}" </c:if> class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required" readonly style="color: #af0000" aria-required="true">*</span> 晚餐标准：<i class="fa icon-question"></i></label>
                            <div class="col-sm-8">
                                <input type="text" readonly id="dinner"  name="dinner" <c:if test="${ empty classInfo.id}">value="${eatStandard2 }" </c:if> <c:if test="${not empty classInfo.id}">value="${classInfo.dinner}" </c:if> class="form-control">
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
                                <input type="text" id="timeNum" readonly name="timeNum" <%--<c:if test="${ empty classInfo.id}">value="8" </c:if> --%><c:if test="${not empty classInfo.id}">value="${classInfo.timeNum}" </c:if> class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 住宿增加天数：<i class="fa icon-question hide"></i></label>
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
                                <span class="required hide" aria-required="true">*</span> 报到时间：<i class="fa icon-question hide"></i></label>
                            <%-- <div class="col-sm-8">
                                 <input type="text" id="entryStartTime" name="entryStartTime" value="${classInfo.entryStartTime}" class="form-control">
                             </div>--%>
                            <div class="col-sm-4">
                                <input type="text" id="entryStart" value="${entryStart }" class="form-control">
                                <input type="hidden" id="entryStart1" value="${entryStart }" class="form-control">
                                <input type="hidden" id="entryStartTime" name="entryStartTime" value="${classInfo.entryStartTime}" class="form-control">
                            </div>
                            <div class="col-sm-4">
                                <select class="form-control" id="mySelect" class="form-control">
                                    <option value="0"  <c:if test="${'12:00:00' eq entryStart1 }">selected</c:if>>下午</option>
                                    <option value="1" <c:if test="${'00:00:00' eq entryStart1 }">selected</c:if>>上午</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <input type="hidden"  id="entryStopTime" name="entryStopTime" value="${classInfo.entryStopTime}" class="form-control">

                    <%-- <div class="col-xs-4">
                         <div class="form-group">
                             <label class="control-label col-sm-4" title="">
                                 <span class="required hide" aria-required="true">*</span> 报名结束时间：<i class="fa icon-question hide"></i></label>
                             <div class="col-sm-8">
                                 <input type="text"  id="entryStopTime" name="entryStopTime" value="${classInfo.entryStopTime}" class="form-control">
                             </div>
                         </div>
                     </div>--%>
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 评价开始时间：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input type="text"  id="evaluateStartTime" name="evaluateStartTime" value="${classInfo.evaluateStartTime}" class="form-control">
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 评价结束时间：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input  type="text"  id="evaluateStopTime" name="evaluateStopTime" value="${classInfo.evaluateStopTime}" class="form-control">
                            </div>
                        </div>
                    </div>

                </div>
                <%--  <div class="row">

                      <div class="col-xs-4">
                          <div class="form-group">
                              <label class="control-label col-sm-4" title="">
                                  <span class="required hide" aria-required="true">*</span> 评价课程：<i class="fa icon-question hide"></i></label>
                              <div class="col-sm-8">
                                  <input  type="text"  id="subject" name="subject" value="${classInfo.subject}" class="form-control">
                              </div>
                          </div>
                      </div>

                  </div>--%>

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

                <div class="row">

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label class="control-label col-sm-4" title="">
                                <span class="required hide" aria-required="true">*</span> 相关文件：<i class="fa icon-question hide"></i></label>
                            <div class="col-sm-8">
                                <input  type="hidden" id="fileUrl" name="fileUrl" value="${classInfo.fileUrl}" class="form-control">
                                <div id="fileUploadContent" class="fileUploadContent"></div>
                                <%--<div id="easyContainer"></div>
                                <input  type="hidden" id="fileUrl" name="fileUrl" value="${classInfo.fileUrl}" class="form-control">--%>
                            </div>
                        </div>
                    </div>

                </div>


                <div class="box-footer">
                    <div class="row">
                        <div class="col-sm-offset-2 col-sm-10">
                            <c:if test="${empty query }">
                                <button type="button" class="btn btn-sm btn-primary" onclick="testUpload()" id="btnSubmit"><i class="fa fa-check"></i> 保 存</button>&nbsp;
                            </c:if>
                            <button type="button" class="btn btn-sm btn-default" id="btnCancel" onclick="/*window.location.href='${ctx }/classInfo/view.action'*//*window.history.back()*/history.go(-1)"><i class="fa fa-reply-all"></i> 关 闭</button>
                            <%--<button type="button" class="btn btn-sm btn-default" id="btnCancel" onclick="tz()"><i class="fa fa-reply-all"></i> 关 闭</button>--%>
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

    $(function(){
        $('#userId').select2();
    });

    function tz(){
        window.location.href = ctx+"/classInfo/view.action";
    }
    //字符串去空格
    String.prototype.Trim = function()
    {
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }
    String.prototype.LTrim = function()
    {
        return this.replace(/(^\s*)/g, "");
    }
    String.prototype.RTrim = function()
    {
        return this.replace(/(\s*$)/g, "");
    }
    //去左空格;
    function ltrim(s){
        return s.replace(/(^\s*)/g, "");
    }
    //去右空格;
    function rtrim(s){
        return s.replace(/(\s*$)/g, "");
    }
    //去左右空格;
    function trim(s){
        return s.replace(/(^\s*)|(\s*$)/g, "");
    }

    $(document).ready(function(){

        findPhone();
        //查询对应校区下的教室
        seachClassroom($('#campusId option:selected') .val());
        imgUrls = [];
        var imgUrl = $('#fileUrl').val();
        if(typeof imgUrl != "undefined" && imgUrl != null && imgUrl != ""){
            arr=imgUrl.split(",");
            /*alert("length="+arr.length);*/
            for (var i = 0; i < arr.length; i++) {
                imgUrls.push(arr[i]);
                uploadTools.showFileResult("fileUploadContent",arr[i],i,true,deleteFileByMySelf);
            }
            /*uploadTools.showFileResult("fileUploadContent",ctx+imgUrl,"1",true,deleteFileByMySelf);*/
        }

    });



    //获取下一天
    function getNextDate(dayStr){
        var dd = new Date(dayStr);
        dd.setDate(dd.getDate()+1);
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        return y+"-"+m+"-"+d;
    };

    function getNextDay(d){
        d = new Date(d);
        d = +d + 1000*60*60*24;
        d = new Date(d);
        //return d;
        //格式化
        return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();

    }


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
            var time1 = arr[0].trim();
            var time2 = arr[1].trim();
            var days=DateDiff(time1,time2);  //调用/计算两个日期天数差的函数，通用
            if(days!=null && typeof(days)!="undefined" && !isNaN(days) ){
                /*$("#dayNum").prop("value",days)*/
                $("#dayNum").prop("value",days*1+1);
                $("#timeNum").prop("value",(days*1+1)*8)
            }else{
                $("#dayNum").prop("value",0)
                $("#timeNum").prop("value",0)
            }
            if(time1==""){
                $("#entryStartTime").prop("value","");
                $("#entryStart").prop("value","");
                $("#entryStopTime").prop("value","");
            }else{
                /*$("#entryStartTime").prop("value",trim(time1+" 00:00:00"));*/
                var p1=$('#mySelect').val();//这就是selected的值
                //下午
                if(p1=="0"){
                    $("#entryStartTime").prop("value",trim(time1+" 12:00:00"));
                }
                //上午
                if(p1=="1"){
                    $("#entryStartTime").prop("value",trim(time1+" 00:00:00"));
                }
                $("#entryStart").prop("value",trim(time1));
                $("#entryStopTime").prop("value",trim(getNextDay(time1)+" 23:59:59"));
            }
            if(typeof(time2)=="undefined"){
                $("#evaluateStartTime").prop("value","");
                $("#evaluateStopTime").prop("value","");
            }else{
                $("#evaluateStartTime").prop("value",trim(time2+" 00:00:00"));
                $("#evaluateStopTime").prop("value",trim(time2+" 23:59:59"));
            }


            //移除上次的校验配置
            $("#inputForm").data('bootstrapValidator').destroy();
            $('#inputForm').data('bootstrapValidator',null);
            //重新添加校验
            formValidator();
        }
    });

    $("#entryStart").blur(function(){
        dateFormat =/^(\d{4})-(\d{2})-(\d{2})$/;
        var entryStart = $("#entryStart").val();
        if(dateFormat.test(trim(entryStart))){
            //true，是yyyy-MM-dd格式
            var p1=$('#mySelect').val();//这就是selected的值
            //下午
            if(p1=="0"){
                $("#entryStartTime").prop("value",trim(entryStart+" 12:00:00"));
            }
            //上午
            if(p1=="1"){
                $("#entryStartTime").prop("value",trim(entryStart+" 00:00:00"));
            }
        }else{
            //false,不是yyyy-MM-dd格式
            $("#entryStart").prop("value",trim($("#entryStart1").val()));
            layer.msg("日期格式不正确");
        }

    });

    $(document).ready(function(){
        /*//获取报到时间的上下午
        let entryStart1=trim($("#entryStart1").val());
        //下午
        if(entryStart1=="12:00:00"){
            $("#mySelect").val("0");
        }
        //上午
        if(entryStart1=="00:00:00"){
            $("#mySelect").val("1");
        }
*/
        $('#mySelect').change(function(){
            var p2=$('#startStopTime').val();
            if(p2 !== null && p2 !== undefined && p2 !== ''){
                var time1=$('#entryStart').val().trim();
                var p1=$(this).children('option:selected').val();//这就是selected的值
                //下午
                if(p1=="0"){
                    $("#entryStartTime").prop("value",trim(time1+" 12:00:00"));
                }
                //上午
                if(p1=="1"){
                    $("#entryStartTime").prop("value",trim(time1+" 00:00:00"));
                }
            }

        })

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
        $("#subject").tagsinput();
        $('#subject').on('itemAdded', function(event) {
            var tag = event.item;
            // Do some processing here
            if (!event.options || !event.options.preventPost) {
                var r = /^[^,，]*$/;
                if(!r.test(tag)){
                    $('#subject').tagsinput('remove', tag);
                    layer.msg("不能输入中英文逗号！")
                }
            }
        });
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


    $("#fileUploadContent").initUpload({
        "uploadUrl":ctx+"/upload/uploadFiles.action",
        "progressUrl":"#",
        "isHiddenUploadBt":true,
        "isHiddenCleanBt":true,
        "isAutoClean":false,
        "scheduleStandard":false,
        "velocity":90,
        "maxFileNumber":3,
        /*"fileType":["pdf","jpg"],*/
        "onUpload":onUploadFun,
    });

    function testUpload(){

        var opt = uploadTools.getOpt("fileUploadContent");
        var fileNumber = uploadTools.getFileNumber(opt);

        /*//判断文件是否存在
        if(uploadTools.fileIsExit(fileList[i],opt)){
            alert("文件（"+fileList[i].name+"）已经存在！");
        }*/

        //开启验证
        $('#inputForm').data('bootstrapValidator').validate();
        setTimeout(function() {
            if($('#inputForm').data('bootstrapValidator').isValid()){
                if(fileNumber<=0){
                    $.ajax({
                        //几个参数需要注意一下
                        type: "POST",//方法类型
                        dataType: "json",//预期服务器返回的数据类型
                        url: ctx+"/classInfo/saveOrUpdate.action" ,//url
                        data: $('#inputForm').serialize(),
                        success: function (result) {
                            if (result.code == 1) {
                                parent.layer.msg("提交成功!", {
                                    shade: 0.3,
                                    time: 1500
                                }, function () {
                                    window.location.href=ctx+"/classInfo/view.action"; // 刷新页面*/
                                });
                            } else {
                                layer.msg(result.message, {
                                    icon: 2,
                                    time: 1000
                                });
                            }
                        },
                    });
                }else{
                    uploadEvent.uploadFileEvent(opt);
                }

            }
        },2500);

    }

    function onUploadFun(opt,data){
        var jsonarray= $.parseJSON(data);
        /*        var imgUrls = $('#fileUrl').val();
                if(typeof imgUrls != "undefined" && imgUrls != null && imgUrls != ""){
                    for (var i = 0; i < jsonarray.length; i++) {
                        imgUrls+=";"+jsonarray[i];
                    }
                    $("#fileUrl").prop("value",imgUrls);
                }else{
                    if(jsonarray.length==1){
                        var imgUrls=jsonarray[0];
                        $("#fileUrl").prop("value",imgUrls);
                    }

                    if(jsonarray.length>1){
                        var imgUrls=jsonarray[0];
                        for (var i = 1; i <jsonarray.length ; i++) {
                            imgUrls+=";"+jsonarray[i];
                            $("#fileUrl").prop("value",imgUrls);
                        }
                    }
                }*/

        if(jsonarray.length>0){

            for (var i = 0; i <jsonarray.length ; i++) {
                imgUrls.push(jsonarray[i]);
            }
            /*   var imgUrl = $('#fileUrl').val();
               if(typeof imgUrl != "undefined" && imgUrl != null && imgUrl != ""){
                   arr=imgUrl.split(",");
                   for (var i = 0; i < arr.length; i++) {
                       imgUrls.push(arr[i]);
                   }
               }*/
            $("#fileUrl").prop("value",unique(imgUrls));
        }


        /* if(jsonarray.length==1){
             var imgpath=jsonarray[0];
             imgUrls = [];
             imgUrls.push(imgpath);
             var imgUrl = $('#fileUrl').val();
             if(typeof imgUrl != "undefined" && imgUrl != null && imgUrl != ""){
                 arr=imgUrl.split(",");
                 for (var i = 0; i < arr.length; i++) {
                     imgUrls.push(arr[i]);
                 }
             }
             unique(imgUrls);
             console.log(unique(imgUrls))
             $("#fileUrl").prop("value",unique(imgUrls));
         }
 */
        /*if(jsonarray.length>1){
            var imgpath=jsonarray[0];
            for (var i = 1; i <jsonarray.length ; i++) {
                imgpath+=","+jsonarray[i];
                $("#fileUrl").prop("value",imgpath);
            }
        }*/


        function unique(arr){
            // 遍历arr，把元素分别放入tmp数组(不存在才放)
            var tmp = new Array();
            for(var i in arr){
                //该元素在tmp内部不存在才允许追加
                if(tmp.indexOf(arr[i])==-1){
                    tmp.push(arr[i]);
                }
            }
            return tmp;
        }

        $.ajax({
            //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: ctx+"/classInfo/saveOrUpdate.action" ,//url
            data: $('#inputForm').serialize(),
            success: function (result) {
                if (result.code == 1) {
                    parent.layer.msg("提交成功!", {
                        shade: 0.3,
                        time: 1500
                    }, function () {
                        window.location.href=ctx+"/classInfo/view.action"; // 刷新页面*/
                    });
                } else {
                    layer.msg(result.message, {
                        icon: 2,
                        time: 1000
                    });
                }
            },
        });



    }

    $("#unitId").change(function(){
        $("#hostUnit").attr("value",$(this).find("option:selected").text());
    });
    $("#organizerUnitId").change(function(){
        $("#organizerUnit").attr("value",$(this).find("option:selected").text());
    });
    function addUnit() {
        window.location.href = ctx +'/unit/view.action';
    }

    function findPhone(){
        var telephone=$("#userId").find("option:selected").data("telephone");
        $("#phoneNumber").prop("value",telephone);
    }

    function deleteFileByMySelf(fileId){
        $(".fileItem").each(function(){
            if(Number($(this).attr('showfilecode'))==fileId){
                var fiName=$(this).find('.fileName').text();
                for (var i = 0; i < imgUrls.length; i++) {
                    var fileName=uploadTools.getFileNameWithUrl(imgUrls[i]);
                    if(fiName==fileName){
                        imgUrls.splice(i,1);
                    }
                }

                $('#fileUrl').prop("value",imgUrls);
                $(this).remove();

            }
        });
    }

    /*    function deleteFileByMySelf(fileId){

            var opt = uploadTools.getOpt("fileUploadContent");
            var uploadId = opt.uploadId;
            $("#"+uploadId+" .fileItem .status i").off();
            $("#"+uploadId+" .fileItem .status i").on("click",function(){
                uploadEvent.deleteFileEvent(opt,this);
            })
        }*/

    $(function () {
        $("#classroomNameId").tagsinput();
        /*$('#classroomNameId').on('itemAdded', function(event) {
            var tag = event.item;
            // Do some processing here
            if (!event.options || !event.options.preventPost) {
                var r = /^[^,，]*$/;
                if(!r.test(tag)){
                    $('#classroomNameId').tagsinput('remove', tag);
                    layer.msg("不能输入中英文逗号！")
                }
            }
        });*/
    })
</script>
</body>
</html>
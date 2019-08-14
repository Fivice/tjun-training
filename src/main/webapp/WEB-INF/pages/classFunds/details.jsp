<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
  <script src="${ctxsta }/entity/classFunds/bootstrpValidator.js" type="text/javascript"></script>
<title>班级基础数据和费用详情</title>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="ibox-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
            <div class="ibox-title">
                <fieldset><legend>班级基础数据和费用详情</legend></fieldset>
            </div>
            </div>
        </div>
    </div>
        <form enctype="multipart/form-data" class="form-horizontal">

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 班级编号：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="classNumber" value="${classInfo.classNumber}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 班级名称：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="className" value="${classInfo.className}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训类型：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="typeName" value="${classInfo.typeName}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 计划：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <c:if test="${'0' eq classInfo.plan}">
                            <input type="text"  name="plan" value="计划内"  class="form-control" readonly>
                            </c:if>
                            <c:if test="${'1' eq classInfo.plan}">
                                <input type="text"  name="plan" value="计划外"  class="form-control" readonly>
                            </c:if>
                            <c:if test="${'2' eq classInfo.plan}">
                                <input type="text"  name="plan" value="非培训班"  class="form-control" readonly>
                            </c:if>

                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 主办单位：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="hostUnit" value="${classInfo.hostUnit}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 开班时间：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="startStopTime" value="${classInfo.startStopTime}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训天数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="timeNum" value="${classInfo.timeNum}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 计划人数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="plannedNumber" value="${classInfo.plannedNumber}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 实到人数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="studentCount" value="${studentCount}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 实到人天数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="studentCountAndDayNum" value="${studentCountAndDayNum}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 住宿人数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="hotel" value="${hotel}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 住宿人天数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="hotelAndDayNum" value="${hotelAndDayNum}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 就餐人数：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="foodTotal" value="${foodTotal}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训费(前台)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="trainingExpense" value="${trainingExpense}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 住宿费(前台)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="scaleFee" value="${scaleFee}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 就餐费(前台)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="foodTotal2" value="${foodTotal2}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 其他费用(前台)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="otherCharges" value="${otherCharges}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 培训费(统收)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="trainingFeeCollection" value="${recordChange.trainingFeeCollection}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 住宿费(统收)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="hotelExpenseCollection" value="${recordChange.hotelExpenseCollection}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 就餐费(统收)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="diningFeeCollection" value="${recordChange.diningFeeCollection}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 其他费用(统收)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="otherExpensesCollection" value="${recordChange.otherExpensesCollection}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 教师实际就餐费：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="teacherDinding" value="${teacherDinding}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 费用说明(统收)：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="explainCollection" value="${recordChange.explainCollection}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label class="control-label col-sm-4" title="">
                            <span class="required hide" aria-required="true">*</span> 备注：<i class="fa icon-question hide"></i></label>
                        <div class="col-sm-8">
                            <input type="text"  name="remarks" value="${classInfo.remarks}" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>


            <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-primary" title="返回班级经费管理" onclick="window.history.back()"
                >返回班级经费管理</button>
            </div>
        </form>
    </div>
  </div>
</body>
</html>

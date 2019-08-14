<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>报名信息管理</title>
    <script src="${ctxsta }/entity/signUpManager/signUpStudentList.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/signUpManager/signUpByHeadMaster.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/signUpManager/classInfoList.js" type="text/javascript"></script>
    <script src="${ctxsta}/common/layui/layDate/laydate/laydate.js"></script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<div class="wrapper wrapper-content">
    <%--search-bar begin --%>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><a style="color: #6CA6CD" href="../classInfo/form.action?query=query&id=${classId}">[ ${className} ]</a>&nbsp学员信息</h5>
                    <input id="stu-classId" value="${classId}" hidden>
                    <input id="userInfo-supType" value="${userInfo.supType}" hidden>
                    <input id="userInfo-userType" value="${userInfo.userType}" hidden>
                    <input id="reportPlace" value="${reportPlace}" hidden>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-2 inline">
                            <button style="display: none" type="button" class="btn btn-primary" title="报名"  onclick="addRegister()"> <i class="fa fa-sign-in"></i>&nbsp报名 </button>
                        </div>
                        <!-- 工具栏 -->
                        <div class="col-sm-12">
                            <!-- 工具栏 -->

                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="siName" id="siName" placeholder="请输入学员名">
                            </div>
                            <div class="btn-group m-t-sm">
                                <input type="text" class="form-control" name="unitName" id="unitName" placeholder="请输入单位名称">
                            </div>
                            <div class="btn-group m-t-sm">
                                <select style="width: 60px;height: 34px;border-color:#E0E0E0;" id="hotel">
                                    <option value="-1" disabled = 'disabled'>住宿</option>
                                    <option value="0">标间</option>
                                    <option value="1">单间</option>
                                    <option value="2">不住宿</option>
                                </select>
                            </div>
                            <div class="btn-group m-t-sm">
                                <select style="width: 60px;height: 34px;border-color:#E0E0E0;" id="dining">
                                    <option value="-1" disabled = 'disabled'>就餐</option>
                                    <option value="1">是</option>
                                    <option value="2">否</option>
                                </select>
                            </div>
                            <div class="btn-group m-t-sm">
                                <select style="width: 85px;height: 34px;border-color:#E0E0E0;" id="payChose">
                                    <option value="-1" disabled = 'disabled'>支付方式</option>
                                    <option value="1">前台缴费</option>
                                    <option value="2">未缴费</option>
                                    <option value="3">国网商旅</option>
                                    <option value="4">统一转账</option>
                                </select>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button type="button" class="btn btn-info" id="search" onclick="seach()" style="margin: 0"><i class="fa fa-search"></i>查询</button>
                            </div>
                            <div class="btn-group m-t-sm">
                                <button id="reset" type="button" class="btn" onclick="reset()" style="margin: 0">清空</button>
                            </div>

                        </div>

                        <%--signUpManager-table begin--%>
                        <table id="table" class="table table-hover formatId">

                        </table>
                        <%--signUpManager-table end--%>
                        <div class="col-sm-offset-0 col-sm-10">
                            <button type="button" class="btn btn-primary" title="返回上一层"  onclick="history.go(-1)"> <i class="fa fa-reply-all"></i>&nbsp返回班级列表 </button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--search-bar end--%>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="payModal" tabindex="-1" role="dialog" aria-labelledby="payModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="payModalLabel">
                        缴费确认
                    </h4>
                </div>
                <div class="modal-body" id="payModalBody">
                    <div id="payConfirmContext">
                        <div style="width: inherit">
                            <div class="row">
                                <div class="col-lg-5">
                                    <section class="panel">
                                        <div class="panel-body" style="padding: 0px">
                                            <form>
                                                <div class="form-group">
                                                    <label for="stu-training-expense" class="col-sm-4 control-label" style="padding: 0px">培训费:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-training-expense" type="text" placeholder="培训费" disabled="">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="stu-hotel" class="col-sm-4 control-label" style="padding: 0px">住宿:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-hotel" type="text" placeholder="住宿费" disabled="">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="stu-dining" class="col-sm-4 control-label" style="padding: 0px">就餐:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-dining" type="text" placeholder="就餐费" disabled="">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="stu-other" class="col-sm-4 control-label" style="padding: 0px">其他:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-other" type="text" placeholder="其他费用" disabled="">
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </section>
                                </div>
                                <div class="col-lg-5">
                                    <section class="panel">
                                        <div class="panel-body" style="padding: 0px">
                                            <form id="pay-form">
                                                <div class="form-group">
                                                    <label for="stu-hotel-select" class="col-sm-4 control-label" style="padding: 0px">住宿:</label>
                                                    <div class="col-sm-8">
                                                        <select class="form-control" id="stu-hotel-select">
                                                            <option value="0">标间</option>
                                                            <option value="1">单间</option>
                                                            <option value="2">不住宿</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="stu-dining-select" class="col-sm-4 control-label" style="padding: 0px">就餐:</label>
                                                    <div class="col-sm-8">
                                                        <select class="form-control" id="stu-dining-select">
                                                            <option value="1">就餐</option>
                                                            <option value="2">不就餐</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="stu-total" class="col-sm-4 control-label" style="padding: 0px;color: #f39c12">合计:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-total" type="text" placeholder="合计费用" disabled="">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="stu-series-number" class="col-sm-4 control-label" style="padding: 0px;color: orangered">流水号:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-series-number" type="text" placeholder="流水号(必填)" >
                                                    </div>
                                                </div>
                                                <div class="form-group" hidden>
                                                    <label for="stu-siId" class="col-sm-4 control-label" style="padding: 0px;color: #f39c12">学员id:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-siId" type="text" placeholder="学员id" disabled="">
                                                    </div>
                                                </div>
                                                <div class="form-group" hidden>
                                                    <label for="stu-pay" class="col-sm-4 control-label" style="padding: 0px;color: #f39c12">支付状态:</label>
                                                    <div class="col-sm-8">
                                                        <input class="form-control" id="stu-pay" type="text" placeholder="支付状态" disabled="">
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="payConfirm">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <%--signUp-botton end--%>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="signUpModal" tabindex="-1" role="dialog" aria-labelledby="signUpModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="signUpModalLabel">
                        报名确认
                    </h4>
                </div>
                <div class="modal-body" id="signUpModalBody">
                    <div class="wrapper" style="padding: 0px">
                        <div class="row">
                            <div class="col-lg-12">
                                <section class="panel">
                                    <div class="panel-body">
                                        <div class="input-group m-bot15">
                                              <span class="input-group-btn">
                                                <button type="button" class="btn btn-default" onclick="stuSearch()"><i class="fa fa-search"></i>搜索</button>
                                              </span>
                                            <input type="text" id="stu-idNumber-se" class="form-control" placeholder="请输入身份证号">
                                        </div>
                                    </div>
                                </section>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <section class="panel">

                                    <div class="panel-body" align="center">
                                        <img class="img-thumbnail" id="stu-head-img" src="${ctxsta}/img/headerImg/head-o.png" style="border: 1px solid #eee">
                                    </div>
                                </section>
                            </div>
                            <div class="col-sm-8">
                                <section class="panel">
                                    <div class="panel-body">
                                        <form class="">
                                            <div class="form-group" hidden>
                                                <label class="col-sm-2 control-label">id</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-id" type="text" placeholder="id" disabled="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">姓名</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-name" type="text" placeholder="姓名" disabled="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">手机</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-phone" type="text" placeholder="手机" disabled="">
                                                </div>
                                            </div>
                                            <%--<div class="form-group">--%>
                                                <%--<label class="col-sm-2 control-label">流水号</label>--%>
                                                <%--<div class="col-sm-10">--%>
                                                    <%--<input class="form-control" id="stu-serial-number" type="text" placeholder="流水号" disabled="">--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">工作单位</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-unit" type="text" placeholder="工作单位" disabled="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">工作部门</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-department" type="text" placeholder="工作部门" disabled="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">报到时间</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-report-time" type="text" placeholder="报到时间" disabled="">
                                                </div>
                                            </div>
                                            <div class="form-group" hidden>
                                                <label class="col-sm-2 control-label">报到状态</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-report-status" type="text" placeholder="报到状态" disabled="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">报到状态</label>
                                                <div class="col-sm-10">
                                                    <input class="form-control" id="stu-report-status-name" type="text" placeholder="报到状态" disabled="">
                                                </div>
                                            </div>

                                        </form>
                                    </div>
                                </section>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="signUpConfirm">
                        报名
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>

<!--补卡模态框-->
<!--隐藏域-->
<input type="hidden" id="student-id">
<input type="hidden" id="class-id">
<!-- 弹出框的头部 -->
<div class="modal fade" id="bukaModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="modal-title"></h4>
            </div>
            <!-- 弹出框的内容   -->
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">流水号</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="stu-card" type="text" placeholder="请输入流水号">
                            </div>
                        </div>
                    </div>

                    <!--提示信息-->
                    <div class="col-sm-12" id="tsmsg" style="display: none;">
                        <div class="form-group" style="color: #af0000;">
                            <label class="col-sm-2 control-label"><%--提示信息--%></label>
                            <div class="col-sm-10" id="msg">
                            </div>
                        </div>
                    </div>

                </div>

            </div>
            <!-- 弹出框的底部 -->
            <div class="modal-footer">
                <!-- 设置了data-dismiss="modal"属性，就可以关闭该弹出框 -->
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="replacementCard()">确定</button>
            </div>
        </div>
    </div>
</div>


<!--调班卡模态框-->

<!-- 弹出框的头部 -->
<div class="modal fade" id="changeClass" tabindex="-1" role="dialog" aria-labelledby="changeClassLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="changeClassLabel" style="font-weight: bolder"><i class="fa fa-refresh"></i> 调班</h4>
            </div>
            <!-- 弹出框的内容   -->
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12" style="margin-bottom: 5px">
                        <form class="form-inline">
                            <div class="form-group">
                                <label for="selectedClass">调至：</label>
                                <input type="text" style="border-bottom: silver 1px solid;border-left: 0px;border-top: 0px;border-right: 0px;height: 35px" id="selectedClass" placeholder="请选择调至" readonly>
                            </div>
                            <button type="button" onclick="confirmChange()" class="btn btn-primary btn-sm">确定</button>
                        </form>
                    </div>
                    <div class="col-sm-12">
                        <div class="col-sm-3" style="padding-right: 2px;padding-left: 0px">
                            <input id="changeClass-startTime" type="text" class="form-control" placeholder="请选择月份">
                        </div>
                        <div class="col-sm-3" style="padding-right: 2px;padding-left: 0px">
                            <input id="changeClass-classId" type="text" class="form-control" placeholder="班级编号">
                        </div>
                        <div class="col-sm-3" style="padding-right: 2px;padding-left: 0px">
                            <input id="changeClass-className" type="text" class="form-control" placeholder="班级名称">
                        </div>
                        <div class="col-sm-3 no-padding">
                            <button class="btn btn-default" onclick="changeClassSearch()"><i class="fa fa-search"></i> 搜索</button>
                        </div>
                    </div>
                </div>
                <table id="changeToClassList">

                </table>

            </div>
            <!-- 弹出框的底部 -->
            <div class="modal-footer">
                <!-- 设置了data-dismiss="modal"属性，就可以关闭该弹出框 -->
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${ctxsta }/entity/signUpManager/modificatio_fee.js"></script>
</head>
<body >

<div class="wrapper wrapper-content">
    <!--班级id-->
    <input type="hidden" id="classId" value="${classId }">
    <!--学员id-->
    <input type="hidden" id="siId" value="${siId }">
    <!--报到登记id-->
    <input type="hidden" id="rId" value="${rId }">

    <!--缴费方式-->
    <input type="hidden" id="pay" value="2">

        <div class="ibox-content">
            <i class="fa fa-asterisk"></i>&nbsp;&nbsp;[ ${className } ]<span style="color: #6CA6CD;">&nbsp;&nbsp;学员：${siName } / ${siIDNumber }</span>
            <hr>
            <div class="row">
    <!-- 面板 -->
    <div class="col-sm-6" style="margin-top: 10px;">
        <div class="panel panel-success">
            <div class="panel-heading">
                <i class="fa fa-list"></i>&nbsp;修改费用
            </div>
            <div class="panel-body" style="padding-top: 0">

                <div class="row" style="margin-top: 10px;">

                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label">住宿标准</label>
                            <select class="form-control" id="houseStandard">

                                <c:choose>
                                    <c:when test="${empty hotelparam }">
                                        <option value="${houseStandard0 }" data-options="0">标间</option>
                                        <option value="${houseStandard1 }" data-options="1">单间</option>
                                        <option value="0" data-options="2">不住宿</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${houseStandard1 }" data-options="1">单间</option>
                                        <option value="${houseStandard0 }" data-options="0">标间</option>
                                        <option value="0" data-options="2">不住宿</option>
                                    </c:otherwise>
                                </c:choose>

                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label">住宿天数</label>
                            <input type="text" class="form-control" placeholder="请输入住宿天数" id="dayNum" value="${dayNum }">
                        </div>
                        <div class="form-group">
                            <label class="control-label">住宿费</label>
                            <input type="text" class="form-control" placeholder="请输入住宿费" id="accommodation" value="">
                        </div>
                        <div class="form-group">
                            <label class="control-label">其它费用</label>
                            <input type="text" class="form-control" placeholder="请输入其它费用" id="other_fees" value="${other_fees }">
                        </div>
                        </div>


                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label">培训费</label>
                                <input type="text" class="form-control" placeholder="请输入培训费" id="groom_cost" value="${groom_cost }">
                            </div>
                            <div class="form-group">
                                <label class="control-label">就餐费</label>
                                <input type="hidden" id="table_money2">
                                <input type="text" class="form-control" placeholder="请输入就餐费" id="table_money">
                            </div>
                        <div class="form-group">
                            <label class="control-label">食宿合计</label>
                            <input style="color: #0e9aef;font-weight: bold;" type="text" class="form-control" placeholder="食宿合计" id="expenses">
                        </div>

                        <div class="form-group">
                            <label class="control-label">总费用</label>
                            <input style="color: #af0000;font-weight: bold;" type="text" class="form-control" placeholder="总费用" id="total_cost">
                        </div>
                        </div>

                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">需要补交</label>
                            <input style="color: #FF8C00;font-weight: bold;" type="text" class="form-control" placeholder="需要补交" id="after_payment">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
                <!--就餐安排-->
                <div class="col-sm-6" style="margin-top: 10px;">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <i class="fa fa-list"></i>&nbsp;就餐安排（班级）
                        </div>
                        <div class="panel-body" style="padding-top: 0">
                            <div id="toolbar" class="btn-group" >
                                    <input type="checkbox" id="allChecked">&nbsp;&nbsp;餐数全选
                            </div>
                            <table id="table" class="table table-hover"></table>
                        </div>
                    </div>
                    <div class="col-sm-6">
                    <button type="button" class="btn btn-sm btn-primary" id="countFee" onclick="countFee()">计算费用</button>
                    </div>
                    <div class="col-sm-6">
                    <button type="button" class="btn btn-sm btn-success" id="modification_fee" onclick="modification_fee()">确认修改费用</button>
                    </div>
                </div>


        </div>
            <hr>
            <button type="button" class="btn btn-sm btn-primary" id="btnCancel" onclick="history.go(-1)"><i class="fa fa-reply-all"></i> 返回上一级</button>

            </div>
</div>


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
                            <input style="color: #af0000" type="text" class="form-control" placeholder="总费用" id="total_fee">
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">需要补交</label>
                            <input type="hidden" value="0" id="result_total">
                            <input style="color: #FF8C00;font-weight: bold;" type="text" class="form-control" placeholder="需要补交" id="payment">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <button type="button" class="btn btn-sm btn-success" id="computationFee" onclick="computationFee()">计算费用</button>
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

</body>
</html>
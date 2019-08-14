<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>单位信息维护 </title>
    <script src="${ctxsta }/entity/unit/unit.js" type="text/javascript"></script>
    <script src="${ctxsta }/entity/unit/bootstrapValidator.js" type="text/javascript"></script>

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>单位信息维护</h5>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
              <div class="btn-group m-t-sm">
                <button type="button" class="btn btn-default" title="添加单位" onclick="creatUnit()"> <i class="glyphicon glyphicon-plus"></i> </button>
                <button type="button" class="btn btn-default" title="刷新列表" onclick="javascript:window.location.reload()"> <i class="glyphicon glyphicon-refresh"></i> </button>
              </div>
               <table id="treeTable" class="table table-bordered table-striped">
                <thead>
                  <tr>
                    <th>单位名称</th>
                    <th>地址</th>
                    <th>联系人</th>
                    <th>邮箱</th>
                    <th>联系方式</th>
                    <th>排序</th>
                 <%--   <th>类型</th>--%>
                    <th>备注</th>
                    <th class="text-center">操作</th>
                  </tr>
                </thead>
                <tbody>
                   <c:forEach items="${unitList}" var="unitList">
                    <tr id="${unitList.areaId}" pId="${unitList.sjareaId ne '0' ? unitList.sjareaId:''}">
                      <td class="${unitList.sjareaId eq '0'?'areaName':''}"><i class="fa fa- m-l-xs m-r-xs"></i>${unitList.areaName}</td>
                      <td>${unitList.address}</td>
                      <td>${unitList.contacts}</td>
                      <td>${unitList.email}</td>
                      <td>${unitList.contactsTel}</td>
                      <td>${unitList.sort }</td>
                      <%--<td>${unitList.areaType }</td>--%>
                      <td>${unitList.remarks }</td>
                      <td class="td-manage text-center">
                        <a class="edit m-l-sm text-warning" href="javascript:void(0)" onclick="unitList_edit(${unitList.areaId})" title="编辑"> <i class="glyphicon glyphicon-edit"></i> </a>
                        <a class="remove m-l-sm text-danger" href="javascript:void(0)" onclick="unitList_delete(${unitList.areaId})" title="删除"> <i class="glyphicon glyphicon-remove"></i> </a>
                          <c:choose>
                      		<c:when test="${unitList.areaType != 4 }">
                      		    <a class="remove m-l-sm text-primary" href="javascript:void(0)" onclick="unitList_creatUnit(${unitList.areaId})" title="添加下级菜单"> <i class="glyphicon glyphicon-sort-by-attributes-alt"></i> </a>
                      		</c:when>
                      		<c:otherwise>
                      		</c:otherwise>
                          </c:choose>
                       </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
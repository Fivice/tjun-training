<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>分配级别</title>
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
	<link rel="stylesheet" href="${ctxsta}/common/jquery/css/chosen.css" />
	<link rel="stylesheet" href="${ctxsta}/common/jquery/css/prism.css" />
	<link rel="stylesheet" href="${ctxsta}/common/jquery/css/style.css" />
	<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>分配级别</h5>
          <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
        </div>
        <div class="ibox-content">
       
          <form id="form" class="form-horizontal" action="${ctx}/sysuser/${user.userId}/updatRoles.action" data-method="put">
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">用户名：</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" name="loginAccount" value="${user.loginAccount}" readonly="readonly">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
               <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">姓&nbsp;&nbsp;名：</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" name="loginAccount" value="${user.userName}" readonly="readonly"><!-- ////// -->
              </div>
            </div>        
            <div class="hr-line-dashed"></div>
            
         
               <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">拥有级别：</label>
    
           
                
                 <div class="col-sm-6   ">
                     <select class="form-control" id="roleId" name = "roleId">
                         <c:forEach items="${sysRole}" var="role">
                             <option <c:if test="${role.roleId eq userRole.roleId}">selected</c:if> value="${role.roleId}" >${role.roleValue}</option>
                         </c:forEach>
                     </select>
                   <%--<select id = "selectRoles"  name = "selectRoles" data-placeholder="请选择级别！" style="width:390px;" multiple class="chosen-select chosen-rtl" tabindex="14">
                      <c:set var="isDoing" value="0"/>
                      <c:forEach items="${sysRole}" var="sysRole" >
                        <c:forEach items="${userRole}" var="userRole">			            
			               <c:if test="${sysRole.roleId eq userRole.roleId}">   
                              <c:set var="isDoing" value="1"/>
                              <option value="${sysRole.roleId}" id="${sysRole.roleId}" selected >
                                 ${sysRole.roleValue}                            
                              </option>
                            </c:if>                          
                          </c:forEach>
                       <c:if test="${isDoing != 1}">    
                         <option value="${sysRole.roleId}" id="${sysRole.roleId}"  >
                              ${sysRole.roleValue}                            
                         </option>
                       </c:if> 
                       <c:set var="isDoing" value="0"/>   
			         </c:forEach>
                   </select>--%>
                </div>
                
            </div> 
            
            <div>
              
        </div>
            
  
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <div class="col-sm-12 text-center">
                <button class="btn btn-primary" type="submit">提交</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
           
	<!-- 全局js -->
	
	<script src="${ctxsta}/common/jquery/jquery-1.6.min.js"></script>
	<script src="${ctxsta}/common/jquery/jquery-3.2.0.min.js"></script>
	<script src="${ctxsta}/common/jquery/prism.js"></script>
	<script src="${ctxsta}/common/jquery/chosen.jquery.js"></script>
	<script src="${ctxsta}/common/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctxsta}/common/metismenu/metisMenu.min.js"></script>
	<script src="${ctxsta}/common/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- layer弹出框js -->
    <script src="${ctxsta}/common/layer/layer.js"></script>
    <!-- iCheck --> 
  	<script src="${ctxsta}/common/icheck/icheck.min.js"></script> 
  	<!-- bootstrapvalidator-master前端验证框架 --> 
  	<script src="${ctxsta}/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script> 
	<!-- 自定义js -->
	<script src="${ctxsta}/cms/js/contabs.js"></script>
	<script src="${ctxsta}/cms/js/content.js"></script>
  
    <!-- Bootstrap table --> 
  <script src="${ctxsta}/common/bootstrap-table/bootstrap-table.min.js"></script> 
  <script src="${ctxsta}/common/bootstrap-table/extensions/export/bootstrap-table-export.js"></script> 
  <script src="${ctxsta}/common/bootstrap-table/tableExport.js"></script> 
  <script src="${ctxsta}/common/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script> 
  <!-- 自定义js --> 
  
  <script src="${ctxsta}/entity/user/assign_roles.js"></script> 
  <script type="text/javascript">

    var config = {

      '.chosen-select'           : {},

      '.chosen-select-deselect'  : {allow_single_deselect:true},

      '.chosen-select-no-single' : {disable_search_threshold:10},

      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},

      '.chosen-select-width'     : {width:"95%"}

    }

    for (var selector in config) {

      $(selector).chosen(config[selector]);

    }

  </script>
  
  
</body>
</html>

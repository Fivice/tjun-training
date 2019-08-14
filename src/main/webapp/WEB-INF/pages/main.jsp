<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <title>培训服务管理系统</title>
    <link rel="icon" href="${contextPath }/static/img/login/logo.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
	<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />

	<style type="text/css">
	  .center_text {
		vertical-align:middle;
		display:table-cell;
		padding:25px;
	  }
      .btn-tog{
        margin: 0;
        padding: 18px;
        text-align: center;
        width: 60px;
        color: #212121;
        border-right: #8D8D8D33 1px solid
      }
      .btn-tog:hover{
        background: #65CEA7;
        border-right: #65CEA7 1px solid;
        color: #FFFF;
        -webkit-transition: all 0.5s;
        transition: all 0.3s;
      }
</style>
  </head>
  <body class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper"> 
  <!--左侧导航开始-->
  <nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i> </div>
    <div class="sidebar-collapse">
      <ul class="nav" id="side-menu">
        <li class="nav-header" style="border-bottom: 1px #f3f3f440 solid;">
          <div class="dropdown profile-element">
              <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                  <span class="clear">
                      <span class="block m-t-xs">
                      <strong class="font-bold">${user.userName}(${sysRole.roleValue})</strong>
                      </span>
                  </span>
              </a>
            <%--<ul class="dropdown-menu animated fadeInRight m-t-xs">--%>
              <%--<li><a class="J_menuItem" href="${ctx}/sysuser/userInfo.action">个人资料</a> </li>--%>
              <%--<li class="divider"></li>--%>
              <%--<li><a href="${ctx}/sysuser/logout.action">安全退出</a> </li>--%>
            <%--</ul>--%>
              <ul class="dropdown-menu animated pull-left">
                  <li><a class="J_menuItem" href="${ctx}/sysuser/userInfo.action"><i class="fa fa-user"></i>  个人资料</a></li>
                  <%--<li><a class="J_menuItem" href="${ctx}/error/404.action"><i class="fa fa-user"></i>  404</a></li>--%>
                  <li><a href="${ctx}/sysuser/logout.action"><i class="fa fa-sign-out"></i> 退出</a></li>
              </ul>
          </div>
        </li>
        <c:forEach items="${menus}" var="menu">
          <c:choose>
            <c:when test="${menu.childMenus != null && menu.childMenus.size()>0}">
              <li> <a href="${ctx}${menu.href}"> <i class="fa fa-${menu.icon}"></i> <span class="nav-label">${menu.menuName}</span> <span class="fa arrow"></span> </a>
                <ul class="nav nav-second-level">
                  <c:forEach items="${menu.childMenus}" var="childMenu">
                  	<c:set var="isDoing" value="0"/>  
                  	
                  	<c:forEach items="${thirdMenu}" var="thirdMenu">
                  	
                  	<c:if test="${thirdMenu.parentId == childMenu.menuId }" >
                  	<c:set var="isDoing" value="1"/>  
                  	
                  	</c:if>
                  	</c:forEach>
                  	<c:choose>
                  	
                  	<c:when test="${isDoing == 1}">
                  	<li> <a  href="${ctx}${childMenu.href}"> <i class="fa fa-${childMenu.icon}"></i> <span class="nav-label">${childMenu.menuName}</span> <span class="fa arrow"></span> </a>
                  	<ul class="nav nav-second-level">
                  	<c:forEach items="${thirdMenu }" var="thirdMenu">
                  	<c:choose>
                  	<c:when test="${thirdMenu.parentId == childMenu.menuId }">
                  	<li> <a class="J_menuItem" href="${ctx}${thirdMenu.href}">&nbsp;&nbsp;&nbsp;>&nbsp;&nbsp;${thirdMenu.menuName}</a> </li> 
                 <%--  	<li> <a class="J_menuItem" href="${ctx}${thirdMenu.href}"><i class="fa fa-${thirdMenu.icon}"></i>${thirdMenu.menuName}</a> </li> --%>
                  	</c:when>
                  	<c:otherwise></c:otherwise>
                  	</c:choose>
                  	</c:forEach>
                  	</ul>
                  	</c:when>
                  	<c:otherwise>
                    <li> <a class="J_menuItem" href="${ctx}${childMenu.href}"> <i class="fa fa-${childMenu.icon}"></i> <span class="nav-label">${childMenu.menuName}</span> </a>
                  </c:otherwise>
                  </c:choose>
                  </c:forEach>
                </ul>
              </li>
            </c:when>
            <c:otherwise>
              <li> <a class="J_menuItem" href="${ctx}${menu.href}"> <i class="fa fa-${menu.icon}"></i> <span class="nav-label">${menu.menuName}</span> </a> </li>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </ul>
    </div>
  </nav>
  <!--左侧导航结束--> 
  <!--右侧部分开始-->
  <div id="page-wrapper" class="gray-bg dashbard-1">
    <div class="border-bottom">
      <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="row">
            <div class="navbar-header col-sm-6" style="padding: 0px">
              <a class="navbar-minimalize minimalize-styl-2 navbar-form-custom btn-tog" href="#">
                <i class="fa fa-bars" style="font-size: 20px;"></i>
              </a>
                <div role="search" class="navbar-form-custom">
                    <div class="form-group">
                        <a class="form-control" id="time"></a>
                    </div>
                </div>
            </div>
            <div class="col-sm-4" style="text-align: right;height: 100%;margin: 0px;line-height: 54px;padding: 0px">
                <span style="height: 100%;font-size: 30px;font-family: 华文楷体;text-shadow:4px 1px 8px #0000008f">国网安徽培训中心</span>
            </div>

        </div>
      </nav>
    </div>
    <div class="row content-tabs">
      <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i> </button>
      <nav class="page-tabs J_menuTabs">
        <div class="page-tabs-content"> <a href="javascript:;" class="active J_menuTab" data-id="">首页</a> </div>
      </nav>
      <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i> </button>
      <div class="btn-group roll-nav roll-right">
      	<a class="J_menuItem" href="${ctx}/sysuser/editPwd.action" >修改密码</a> 
        <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span> </button>
        <ul role="menu" class="dropdown-menu dropdown-menu-right">
          <li class="J_tabShowActive"><a>定位当前选项卡</a> </li>
          <li class="divider"></li>
          <li class="J_tabCloseAll"><a>关闭全部选项卡</a> </li>
          <li class="J_tabCloseOther"><a>关闭其他选项卡</a> </li>
        </ul>
      </div>
      
      <a href="${ctx}/sysuser/logout.action" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a> </div>
    <div class="row J_mainContent" id="content-main">
      <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="index.action" frameborder="0" data-id="" seamless>
      </iframe>
    </div>
    <div class="footer">
      <div class="pull-right"><a href="" target="_Blank"> 培训服务管理系统</a></div>
    </div>
  </div>
  <!--右侧部分结束--> 
  <!--右侧边栏开始-->
  <div id="right-sidebar">
    <div class="sidebar-container">
      <ul class="nav nav-tabs navs-3">
        <li class="active"> <a data-toggle="tab" href="#tab-1"> <i class="fa fa-gear"></i> 主题 </a> </li>
        <li class=""><a data-toggle="tab" href="#tab-2"> 通知 </a> </li>
        <li><a data-toggle="tab" href="#tab-3"> 项目进度 </a> </li>
      </ul>
      <div class="tab-content">
        <div id="tab-1" class="tab-pane active">
          <div class="sidebar-title">
            <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
            <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
          </div>
          <div class="skin-setttings">
            <div class="title">主题设置</div>
            <div class="setings-item"> <span>收起左侧菜单</span>
              <div class="switch">
                <div class="onoffswitch">
                  <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                  <label class="onoffswitch-label" for="collapsemenu"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span> </label>
                </div>
              </div>
            </div>
            <div class="setings-item"> <span>固定顶部</span>
              <div class="switch">
                <div class="onoffswitch">
                  <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                  <label class="onoffswitch-label" for="fixednavbar"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span> </label>
                </div>
              </div>
            </div>
            <div class="setings-item"> <span> 固定宽度 </span>
              <div class="switch">
                <div class="onoffswitch">
                  <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                  <label class="onoffswitch-label" for="boxedlayout"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span> </label>
                </div>
              </div>
            </div>
            <div class="title">皮肤选择</div>
            <div class="setings-item default-skin nb"> <span class="skin-name "> <a href="#" class="s-skin-0"> 默认皮肤 </a> </span> </div>
            <div class="setings-item blue-skin nb"> <span class="skin-name "> <a href="#" class="s-skin-1"> 蓝色主题 </a> </span> </div>
            <div class="setings-item yellow-skin nb"> <span class="skin-name "> <a href="#" class="s-skin-3"> 黄色/紫色主题 </a> </span> </div>
          </div>
        </div>
        <div id="tab-2" class="tab-pane">\
        ssssssss
        </div>
        <div id="tab-3" class="tab-pane">
          <div class="sidebar-title">
          <ul class="sidebar-list">
              </a> </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!--右侧边栏结束--> 
</div>
</div>
<!-- 全局js -->
	<script src="${ctxsta}/common/jquery/jquery-3.2.0.min.js"></script>
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
	<script src="${ctxsta}/cms/js/hplus.js"></script>
	<script src="${ctxsta}/cms/js/contabs.js"></script>
	 
  <!-- 第三方插件 --> 
  <script src="${ctxsta}/common/pace/pace.min.js"></script> 

  </body>
</html>

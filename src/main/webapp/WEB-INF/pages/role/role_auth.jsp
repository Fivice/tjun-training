<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>级别授权</title>
<link rel="shortcut icon"
	href="${ctximg}/uploads/default/ico/favicon.ico" type="image/x-icon" />
<link rel="stylesheet"
	href="${ctxsta}/common/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctxsta}/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctxsta}/cms/css/animate.css" />
<link rel="stylesheet" href="${ctxsta}/cms/css/style.css" />
<link rel="stylesheet"
	href="${ctxsta}/bootstrap/css/bootstrap-select.min.css" />
<link rel="stylesheet"
	href="${ctxsta}/common/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />

<link rel="stylesheet"
	href="${ctxsta}/common/treeTable/themes/vsStyle/treeTable.min.css" />

</head>
<body class="fixed-sidebar full-height-layout gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">

					<div class="ibox-content">
						
							<input type="hidden" id="contextPath" value="${contextPath }" />
							<div class="form-group m-t">
								<label class="col-sm-2 col-xs-offset-1 control-label">级别名称：</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" name="roleValue" readonly="readonly"
										value="${role.roleValue}">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 col-xs-offset-1 control-label">授权：</label>
								<div class="col-sm-7">									
									<ul id="treeDemo" class="ztree"></ul>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12 text-center">
									<input type="button" onClick="doSave()" value="提交" class="btn btn-primary" />
									
								</div>
							</div>
						
					</div>
				</div>
			</div>
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
	<script src="${ctxsta}/cms/js/content.js"></script>

	<script src="${ctxsta}/common/treeTable/jquery.treeTable.min.js"></script>
	<script src="${ctxsta}/cms/js/systemMenuList.js"></script>
	<script src="${ctxsta}/common/ztree/js/jquery.ztree.core.js"></script>
	<script src="${ctxsta}/common/ztree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript">
		// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		var setting = {
			check : {
				enable : true,
			},
			data : {
				simpleData : {
					idKey : "menuId",
					pIdKey : "parentId",
					enable : true,
					rootPId : 0
				},
				key : {
					name : "menuName"
				}
			}
		};
		var sNodes = eval('${selNodes}');
		$(document).ready(function() {
			var zNodes = eval('${nodes}');
			var treeObj = $.fn.zTree.init($("#treeDemo"), setting,
					zNodes);
			//页面加载成功后,开始加载树形结构  
			//$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		
		if (sNodes != null && sNodes.length > 0) {
			//遍历勾选角色关联的菜单数据
			for (var i = 0; i < sNodes.length; i++) {
			//遍历需要选中的角色节点,然后利用该节点ID获得与他相同的tree中的节点，设置为选中
			var nodes = treeObj.getNodesByParam("menuId",
					sNodes[i].menuId, null);
			//勾选当前选中的节点
			treeObj.checkNode(nodes[0], true, false);
			}
			}
		});
		function doSave() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = zTree.getCheckedNodes();
            var tmpNode;
            var ids = "";
            for(var i=0; i<nodes.length; i++){
                tmpNode = nodes[i];
                if(i!=nodes.length-1){
                    ids += tmpNode.menuId+",";//拿到选中菜单的menuId
                }else{
                    ids += tmpNode.menuId;
                }
            }
            var roleId = ${roleId};
            var params = roleId +";"+ids;
          
            $.ajax({
                type: "POST",
                url: '${ctx}/role/authorise.action',
                data: {params:params},
                dataType:'json',
                cache: false,
                success: function(result){
                	if (result.code == 1) {
						parent.layer.msg("授权成功!", {
							shade : 0.3,
							time : 1500
						}, function() {
							window.parent.location.reload(); // 刷新父页面
						});
					} else {
						layer.msg(result.message, {
							icon : 2,
							time : 1000
						});
					}
                }
            });
        }
	</script>
</body>
</html>
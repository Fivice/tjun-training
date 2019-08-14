//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	//初始化Table
	var oTable = new TableInit();
	oTable.Init();
});
var TableInit = function () {
	var oTableInit = new Object();
	//初始化Table
	oTableInit.Init = function () {
		$('#table_sysUser').bootstrapTable({
			url: path+'/sysuser/findUser.action',         //请求后台的URL（*）
			method: 'get',                      //请求方式（*）
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",						//数据类型
            toolbar: '#toolbar',                 	//工具按钮用哪个容器
            striped: true,                      	//是否显示行间隔色
            cache: false,                       	//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   	//是否显示分页（*）
            sortable: true,                    	    //是否启用排序
            sortOrder: "asc",                   	//排序方式
            queryParamsType: 'limit',
            queryParams: oTableInit.queryParams, 	//传递参数（*）
            sidePagination: "server",           	//分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       	//初始化加载第一页，默认第一页
            pageSize: 10,                       	    //每页的记录行数（*）
            pageList: [10, 20, 50, 100],        	//可供选择的每页的行数（*）
            // search: true,                       	//是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            // strictSearch: true,					    //启用全匹配搜索，否则为模糊搜索
            // searchOnEnterKey: false,				//按回车触发搜索方法，否则自动触发搜索方法
            showColumns: true,                  	//是否显示所有的列
            showRefresh: true,                  	//是否显示刷新按钮
            minimumCountColumns: 2,             	//最少允许的列数
            clickToSelect: true,                	//是否启用点击选中行
            //height: 500,                        	//行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                   //每一行的唯一标识，一般为主键列
            showToggle:true,                    	//是否显示详细视图和列表视图的切换按钮
            cardView: false,
            // showFooter:true ,//是否显示详细视图
            detailView: false,                  		//是否显示父子表
            showExport: true,  //是否显示导出按钮
            //exportDataType : "selected", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
            buttonsAlign:"right",  //按钮位置
            //exportTypes:['excel','xlsx'],  //导出文件类型
            exportTypes:['excel'],
            Icons:'glyphicon-export',
            exportOptions:{
                ignoreColumn: [0,0],  //忽略某一列的索引
                fileName: '数据导出',  //文件名称设置
                worksheetName: 'sheet1',  //表格工作区名称
                tableName: '用户列表',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                /*onMsoNumberFormat: DoOnMsoNumberFormat  */
            },
			columns: [
			          {checkbox: true},
			          {title:'登录账号',field: 'sysUser.loginAccount',sortable:true },
			          {title:'真实姓名',field: 'sysUser.userName',sortable:true },
					  {title:'用户类型',field: 'sysUser.userType',sortable:true },
			          {title:'部门',field: 'sysUser.department.areaName',sortable:true },
			          {title:'级别',field: 'sysRole.roleValue',sortable:true },
			          {title:'手机号',field: 'sysUser.telephone',sortable:true },
                	  {title:'邮箱',field: 'sysUser.email',sortable:true },
			          {title:'状态',field: 'sysUser.state',sortable:true,
			        	  formatter:function(v,r,i){
			        		  var str = "";
			        		  switch(v){
			        		  case 1:
			        			  str = '<span class="label label-primary">正常</span>';
			        			  break;
			        		  case 0:
			        			  str = '<span class="label label-danger">冻结</span>';
			        			  break;
			        		  default:
			        			  str = "未知";
			        		  break;
			        		  }
			        		  return str;
			        	  }
			          },
			          {title:'性别',field: 'sysUser.sex',sortable:true,
			        	  formatter:function(v,r,i){
			        		  var str = "";
			        		  switch(v){
			        		  case 1:
			        			  str = '男';
			        			  break;
			        		  case 2:
			        			  str = '女';
			        			  break;
			        		  default:
			        			  str = "待完善";
			        		  break;
			        		  }
			        		  return str;
			        	  }
			          },
			          {title:'注册时间',field: 'sysUser.registerTime',sortable:true }]
		});
	};

	//得到查询的参数
	oTableInit.queryParams = function (params) {
		var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				pageSize: params.limit,   //页面大小
				pageNumber: params.pageNumber, //页码
				loginAccount:$("#loginAccount").val(),
				userName:$("#userName").val(),
				departmentName:$("#departmentName").val(),
		};
		return temp;
	};
	return oTableInit;
};

function search(){  
    $("#table_sysUser").bootstrapTable('refresh');  
    $('#table_sysUser').bootstrapTable('selectPage', 1);  
} 
//删除用户
function user_delete(){
   var rows =$('#table_sysUser').bootstrapTable("getSelections")
   var userIds ="";
   if(rows.length==0){
   	layer.alert('请至少选择一条数据！', {
   		icon: 5,
   		title: "提示"
   		});
   }else {
	   for(var i=0;i<rows.length;i++){
		   userIds += rows[i].sysUser.userId.toString()+","
	   };
	   console.log(userIds);
	   layer.confirm('确认要删除所选用户吗？', {
			btn : [ '确定', '取消' ] //按钮
		}, function() {
			$.ajax({
				type : 'POST',
				dataType : 'json',
				 data:{ 
					 userIds:userIds
	                   	},
				url : path +'/sysuser/delete.action',
				success : function(result) {
					if (result.code == 1) {
						layer.msg('用户删除成功!', {
							icon : 1,
							time : 1000
						}, function() {
  							window.location.reload(); // 刷新父页面
  						});
					} else {
						layer.alert(result.message, {
							icon : 2
						});
					}
				}
			})
		});
//	   for(var i=0;i<rows.length;i++){
//		   value=rows[i]['userId'];
////		   console.log(rows.length);
////		   console.log(value);
//		   layer.confirm('确认要删除该用户吗？', {
//				btn : [ '确定', '取消' ] //按钮
//			}, function() {
//				$.ajax({
//					type : 'delete',
//					dataType : 'json',
//					url : path +'/sysuser/'+value+'/delete.action',
//					success : function(result) {
//						if (result.code == 1) {
//							layer.msg('该用户删除成功!', {
//								icon : 1,
//								time : 1000
//							}, function() {
//	   							window.location.reload(); // 刷新父页面
//	   						});
//						} else {
//							layer.alert(result.message, {
//								icon : 2
//							});
//						}
//					}
//				})
//			});
//	   }
   }
}
		
		
//更新用户
function user_edit(){
   var rows =$('#table_sysUser').bootstrapTable("getSelections")
	console.log(rows)
   var value ;
   if(rows.length==0){
   	layer.alert('请选择一条数据！', {
   		icon: 5,
   		title: "提示"
   		});
   }else if(rows.length>1){
   	layer.alert('只能选择一条数据编辑！', {
   		icon: 5,
   		title: "提示"
   		});
   }else {
	   for(var i=0;i<rows.length;i++){
		   value=rows[0].sysUser.userId;
	   layer_show('更新用户', path + '/sysuser/'+value+'/edit.action', 900, 480)
	   }
   }
}		
	

//角色分配
function assign_roles(){
   var rows =$('#table_sysUser').bootstrapTable("getSelections")
   var value ;
   if(rows.length==0){
   	layer.alert('请选择一条数据！', {
   		icon: 5,
   		title: "提示"
   		});
   }else if(rows.length>1){
   	layer.alert('只能选择一条数据编辑！', {
   		icon: 5,
   		title: "提示"
   		});
   }else {
	   for(var i=0;i<rows.length;i++){
		   value=rows[i].sysUser.userId;
	   layer_show('权限分配', path + '/sysuser/'+value+'/assignRoles.action', 900, 480)
	   }
   }
}	
		
		
		
		
		
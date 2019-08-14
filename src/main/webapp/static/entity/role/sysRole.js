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
		$('#table_sysRole').bootstrapTable({
			url: path+'/role/findRole.action',         //请求后台的URL（*）
			method: 'post',                      //请求方式（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
			striped: true,                      //是否显示行间隔色
			cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   //是否显示分页（*）
			showPaginationSwitch:true,
			paginationPreText:"<",
			paginationNextText:">",
			sortable: true,                     //是否启用排序
			sortName:"id",
			sortOrder: "desc",                   //排序方式
			queryParams: oTableInit.queryParams,//传递参数（*）
			queryParamsType: 'limit',
			sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
			pageNumber:1,                       //初始化加载第一页，默认第一页
			pageSize: 5,                       //每页的记录行数（*）
			pageList: [10, 20, 50],        //可供选择的每页的行数（*）
			// search: true,                       //是否显示表格搜索
			strictSearch: true,					//设置为 true启用 全匹配搜索，否则为模糊搜索
			showColumns: true,                  //是否显示所有的列
			showRefresh: true,                  //是否显示刷新按钮
			minimumCountColumns: 2,             //最少允许的列数
			clickToSelect: true,                //是否启用点击选中行
			//height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "roleId",                     //每一行的唯一标识，一般为主键列
			showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
			cardView: false,                    //是否显示详细视图
			detailView: false,                   //是否显示父子表
			contentType: "application/x-www-form-urlencoded", //解决POST提交问题
			columns: [
			          {checkbox: true},
			          // {title:'角色名',field: 'roleKey',sortable:true },
              		  {title:'级别名称',field: 'roleValue',sortable:true },
			          {title:'描述',field: 'description',sortable:true },
			          {title:'创建时间',field: 'createTime',sortable:true }]
		});
	};

	//得到查询的参数
	oTableInit.queryParams = function (params) {
		var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				pageSize: params.limit,   //页面大小
				pageNumber: params.pageNumber, //页码
				sortName: params.sort,	//排序列名
				sortOrder:params.order,	//排序方式
				searchText:params.search//搜索框参数
		};
		return temp;
	};
	return oTableInit;
};

function search(){  
    $("#table_sysRole").bootstrapTable('refresh');  
    $('#table_sysRole').bootstrapTable('selectPage', 1);  
} 
//删除角色
function role_delete(){
   var rows =$('#table_sysRole').bootstrapTable("getSelections")
   var roleIds ="" ;
   console.log(rows.length);
   console.log(rows);
   
   if(rows.length==0){
   	layer.alert('请至少选择一条数据！', {
   		icon: 5,
   		title: "提示"
   		});
   }else {
	   for(var i=0;i<rows.length;i++){
		   roleIds += rows[i]['roleId'].toString()+","
	   };
	   console.log(roleIds);
	   layer.confirm('确认要删除数据吗？', {
			btn : [ '确定', '取消' ] //按钮
		}, function() {
			$.ajax({
				type : 'POST',
				dataType : 'json',
				 data:{ 
					 roleIds:roleIds
	                   	},
				url : path +'/role/delete.action',
				success : function(result) {
					if (result.code == 1) {
						layer.msg('数据删除成功!', {
							icon : 1,
							time : 1000
						}, function() {
  							window.location.reload(); // 刷新父页面
  						});
					} else {
						layer.alert("该权限无法被删除", {
							icon : 2
						});
					}
				}
			})
		});
   }
}
		
		
//更新角色
function role_edit(){
   var rows =$('#table_sysRole').bootstrapTable("getSelections")
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
		   value=rows[i]['roleId'];
	   layer_show('更新级别', path + '/role/'+value+'/edit.action', 900, 480)
	   }
   }
}	


//角色授权
function role_auth(){
   var rows =$('#table_sysRole').bootstrapTable("getSelections")
   var value ;
   if(rows.length==0){
   	layer.alert('请选择一条数据！', {
   		icon: 5,
   		title: "提示"
   		});
   }else if(rows.length>1){
   	layer.alert('只能选择一条数据！', {
   		icon: 5,
   		title: "提示"
   		});
   }else {
	   for(var i=0;i<rows.length;i++){
		   value=rows[i]['roleId'];
	   layer_show('级别授权', path + '/role/'+value+'/auth.action', 900, 480)
	   }
   }
}	
		
		
		
		
		
		
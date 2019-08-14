$(function() {
	// 查询资产分类
	$.ajax({
		type : 'POST',
		url : ctx + '/index/second.action',//发送请求  
		 contentType:'application/json;charset=utf-8', 
		success : function(result) {
         $("#second").html(result)
		}
	});
	// 查询第三行
	/*$.ajax({
		type : 'POST',
		url : ctx + '/index/three.action',//发送请求  
		  contentType:'application/json;charset=utf-8', 
		success : function(result) {
         $("#three").html(result)
		}
	});*/
});

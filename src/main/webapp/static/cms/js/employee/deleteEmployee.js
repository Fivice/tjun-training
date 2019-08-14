//删除员工
function show() {
	obj = document.getElementsByName("empId");
	empIds = [];
	for (k in obj) {
		if (obj[k].checked)
			empIds.push(obj[k].value);
	}
	/* alert(empIds.length); */
	if (empIds.length == 0) {
		/*alert("请至少选择一条数据！")*/
		layer.alert('请至少选择一条数据！', {
			icon : 5,
			title : "提示"
		});
	} else {
		layer.confirm('您确定要删除員工数据吗？', {
			btn : [ '确定', '取消' ]
		//按钮
		}, function() {

			/* alert(empIds); */
			$.ajax({
				type : 'POST',
				url : ctx + '/employee/delete.action',//发送请求  
				data : {
					/* _method : "delete", */
					empIds : empIds.toString()
				},
				/*  contentType:'application/json;charset=utf-8', */
				dataType : "json",
				success : function(result) {
					if (result.code == 1) {
						parent.layer.msg("删除员工成功!", {
							shade : 0.3,
							time : 1500
						}, function() {
							window.location.reload(); // 刷新父页面
						});
					} else {
						layer.msg(result.message, {
							icon : 2,
							time : 1000
						});
					}

				}
			});

		});

	}
}
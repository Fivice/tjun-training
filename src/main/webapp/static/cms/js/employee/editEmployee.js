$(function() {
	// 控制全选按钮
	$("#cbAll").click(function() {
		$("input[name='empId']").prop("checked", $(this).prop("checked"));
	});
});

// 编辑员工
function edit() {
	obj = document.getElementsByName("empId");
	empIds = [];
	for (k in obj) {
		if (obj[k].checked)
			empIds.push(obj[k].value);
	}
	/*
	 * var e=parseFloat(empIds[0]); alert(e);
	 */
	/* alert(empIds.length); */
	if (empIds.length == 0) {
		/* alert("请选择一条数据！") */
		layer.alert('请选择一条数据！', {
			icon : 5,
			title : "提示"
		});
	} else if (empIds.length > 1) {
		/* alert("只能选择一条数据编辑！") */
		layer.alert('只能选择一条数据编辑！', {
			icon : 5,
			title : "提示"
		});
	} else {
		/* alert(empIds); */
		layer.open({
			type : 2,// 这就是定义窗口类型的属性
			title : "编辑员工",
			shadeClose : true,
			shade : 0.3,
			offset : "20%",
			shadeClose : false,
			area : [ "60%", "90%" ],
			content : ctx + "/employee/edit.action?emp=" + empIds[0]

		});
	}
}
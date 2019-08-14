function nameFormatter(value, row) {
	return '<a href="javascript:void(0)" onclick="layer_show(\'' + row.userName + '\',\'' + baselocation + '/administrator/list/' + row.userId + '\',\'1100\',\'480\')">' + value + '</a>';
}

function statusFormatter(value) {
	if (value == -1) {
		return '<span class="label label-primary">未审核</span>'
	} else if (value == 0) {
		return '<span class="label label-danger">不通过</span>'
	}
}

function userFormatter(value) {
	if (value == 1) {
		return '<span >原料厂家</span>'
	} else if (value == 2) {
		return '<span >委托加工</span>'
	}else if (value == 3) {
		return '<span >运输</span>'
	}else if (value == 4) {
		return '<span >仓库存储</span>'
	}
}

function actionFormatter(value, row, index) {
	if (row.status == -1) {
		return [
			'<a class="edit" href="javascript:void(0)" title="注册信息">',
			'<i>注册信息&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="normal" title="通过">',
			'<i>通过&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="freeze" href="javascript:void(0)" title="不通过">',
			'<i>不通过&nbsp;&nbsp;</i>',
			'</a>'
		].join('');
	} 
}

window.actionEvents = {
	'click .edit' : function(e, value, row, index) {
		show_reg(index, row);
	},
	'click .normal' : function(e, value, row, index) {
		status_start(index, row.userId);
	},
	'click .freeze' : function(e, value, row, index) {
		status_stop(index, row.userId);
	}
	
};

function show_reg(index, row) {
	layer_show(row.userName, baselocation +'/administrator/list/regdetail/'+ row.userId, 900, 480)
}

/**
 * 冻结用户
 */
function status_stop(index, value) {
	layer.confirm('确认该用户审核不通过吗？', {
		btn : [ '确定', '取消' ] //按钮
	}, function() {
		check(index,value,0);
	});
}

/**
 * 启用用户
 */
function status_start(index, value) {
	layer.confirm('确认该用户审核通过吗？', {
		btn : [ '确定', '取消' ] //按钮
	}, function() {
		check(index,value,1);
	});
}

function check(index, value,status){
	$.ajax({
		data: {userId:value},
		dataType : 'json',
		type : 'put',
		url : baselocation + '/administrator/list/check/'+status,
		success : function(result) {
			if (result.code == 1) {
				layer.msg('操作成功!', {
					icon : 6,
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
}
/**
 * 删除用户
 */
/*function admin_delete(index, value) {
	layer.confirm('确认要删除该用户吗？', {
		btn : [ '确定', '取消' ] //按钮
	}, function() {
		$.ajax({
			type : 'delete',
			dataType : 'json',
			url : baselocation + '/administrator/list/' + value,
			success : function(result) {
				if (result.code == 1) {
					$('#table').bootstrapTable('hideRow', {
						index : index
					});
					layer.msg('该用户删除成功!', {
						icon : 1,
						time : 1000
					});
				} else {
					layer.alert(result.message, {
						icon : 2
					});
				}
			}
		})
	});
}*/
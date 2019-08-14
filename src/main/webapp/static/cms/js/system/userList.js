/**
 * 进行格式转换
 */
baselocation='http://localhost:8080/task'
function stateFormatter(value) {
	if (value == 1) {
		return '<span class="label label-primary">正常</span>'
	} else if (value == 0) {
		return '<span class="label label-danger">冻结</span>'
	}
}
function actionFormatter(value, row, index) {
	if (row.status == 1) {
		return [
			'<a class="edit" title="编辑">',
			'<i>编辑&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="remove" href="javascript:void(0)" title="删除">',
			'<i>删除&nbsp;&nbsp;</i>',
			'</a>',
		].join('');
	} else {
		return [
			'<a class="edit" href="javascript:void(0)" title="编辑">',
			'<i>编辑&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="remove" href="javascript:void(0)" title="删除">',
			'<i>删除&nbsp;&nbsp;</i>',
			'</a>',
		].join('');
	}
}

window.actionEvents = {
	'click .edit' : function(e, value, row, index) {
		layer_show(row.userName, baselocation + '/sysuser/' + row.userId + '/edit.action', 900, 480)
	},
	'click .remove' : function(e, value, row, index) {
		admin_delete(index, row.userId);
	}
};


/**
 * 删除管理员
 */
function admin_delete(index, value) {
	layer.confirm('确认要删除该用户吗？', {
		btn : [ '确定', '取消' ] //按钮
	}, function() {
		$.ajax({
			type : 'delete',
			dataType : 'json',
			url : baselocation +'/sysuser/'+value+'/delete.action',
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
}
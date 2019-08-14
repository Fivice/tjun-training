/**
 * 进行格式转换
 */
function timeFormatter(value) {
	return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}
function shelveFormatter(value) {
	if (value == 1) {
		return '<span class="label label-primary">已上架</span>'
	} else if (value == 0) {
		return '<span class="label label-danger">未上架</span>'
	}
}
function navFormatter(value) {
	if (value == 1) {
		return '<span class="label label-primary">显示</span>'
	} else if (value == 0) {
		return '<span class="label label-danger">隐藏</span>'
	}
}
function topFormatter(value) {
	if (value == 1) {
		return '<span class="label label-danger">置顶</span>'
	} else if (value == 0) {
		return '<span class="label label-primary">默认</span>'
	}
}
function hotFormatter(value) {
	if (value == 1) {
		return '<span class="label label-danger">热门</span>'
	} else if (value == 0) {
		return '<span class="label label-primary">默认</span>'
	}
}

var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));

elems.forEach(function(html) {
	var switchery = new Switchery(html, {
		size : 'small'
	});
});

/**
 * 表单验证
 */
$(function() {
	form_sell();
	form_buy()
})

function form_sell(){
	var form1_id = "product_form";
	$('#'+form1_id).bootstrapValidator({
		container : 'tooltip',
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			'name' : {
				message : '商品名称验证失败',
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			},
			'productNumber' : {
				message : '商品编号验证失败',
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			},
			'showPrice' : {
				message : '显示价格验证失败',
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					regexp : {
						regexp : /^[0-9]+([.]{1}[0-9]+){0,1}$/,
						message : '价格只能为数字'
					}
				}
			},
			'introduce' : {
				message : '商品简介验证失败',
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			},
			'stock' : {
				message : '商品数量验证失败',
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '数量只能为数字'
					}
				}
			}
		}
	})
		.on('success.form.bv', function(e) {
			// Prevent form submission
			e.preventDefault();

			// Get the form instance
			var $form = $(e.target);

			// Get the BootstrapValidator instance
			var bv = $form.data('bootstrapValidator');

			var method = $('#'+form1_id).attr('method');
			// Use Ajax to submit form data
			$("#categoryId").val($("#twolevel_category_Id").val());
			if (method == 'put') {
				$.ajax({
					data : $form.serialize(),
					dataType : 'json',
					type : 'put',
					url : $form.attr('action'),
					success : function(result) {
						if (result.code == 1) {
							parent.layer.msg("更新采购商品成功!", {
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
				})
			} else if (method == 'post') {
				$.ajax({
					data : $form.serialize(),
					dataType : 'json',
					type : 'post',
					url : $form.attr('action'),
					success : function(result) {
						if (result.code == 1) {//弹出上传图片的窗口
							//setTimeout(layer_show("添加商品图片", baselocation + '/product/' + result.data + '/pimgedit', 1100, 480),3000)
							parent.layer.msg("添加商品成功!", {
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
				})
			}
		});
}

function form_buy(){
	var form_id = "product_buyform";
	$('#'+form_id).bootstrapValidator({
		container : 'tooltip',
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			'introduce' : {
				message : '商品简介验证失败',
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			},
			'productNumber' : {
				message : '采购编号验证失败',
				validators : {
					notEmpty : {
						message : '采购编号不能为空'
					},
					regexp : {
						regexp : /^[0-9]*$/,
						message : '采购编号只能为数字'
					}
				}
			}
		}
	})
		.on('success.form.bv', function(e) {
			// Prevent form submission
			e.preventDefault();

			// Get the form instance
			var $form = $(e.target);

			// Get the BootstrapValidator instance
			var bv = $form.data('bootstrapValidator');
			var method = $('#'+form_id).attr('method');
			// Use Ajax to submit form data
			if (method == 'put') {
				$.ajax({
					data : $form.serialize(),
					dataType : 'json',
					type : 'put',
					url : $form.attr('action'),
					success : function(result) {
						if (result.code == 1) {
							parent.layer.msg("更新商品成功!", {
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
				})
			} else if (method == 'post') {
				$("#categoryId").val($("#twolevel_category_Id").val());
				$.ajax({
					data : $form.serialize(),
					dataType : 'json',
					type : 'post',
					url : $form.attr('action'),
					success : function(result) {
						if (result.code == 1) {
							parent.layer.msg("添加采购商品成功!", {
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
				})
			}
		});
}

function actionFormatter(value, row, index) {
	if (row.status == 1) {
		return [
			'<a class="freeze" href="javascript:void(0)" title="商品图片">',
			'<i >图片&nbsp;&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="edit " href="javascript:void(0)" title="基本信息编辑">',
			'<i >编辑&nbsp;&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="pattribute" href="javascript:void(0)" title="商品属性">',
			'<i >属性&nbsp;&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="pparm" href="javascript:void(0)" title="商品参数">',
			'<i>参数</i>',
			'</a>',			
		].join('');
	} else {
		return [
			'<a class="normal" href="javascript:void(0)" title="商品图片">',
			'<i>图片&nbsp;&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="edit" href="javascript:void(0)" title="基本信息编辑">',
			'<i >编辑&nbsp;&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="pattribute" href="javascript:void(0)" title="商品属性">',
			'<i >属性&nbsp;&nbsp;&nbsp;</i>',
			'</a>',
			'<a class="pparm" href="javascript:void(0)" title="商品参数">',
			'<i>参数</i>',
			'</a>',	
		].join('');
	}
}

function actionBuyFormatter(value, row, index) {
	return [
		'<a class="edit " href="javascript:void(0)" title="基本信息编辑">',
		'<i >编辑&nbsp;&nbsp;&nbsp;</i>',
		'</a>',		
	].join('');
}

window.actionEvents = {
	'click .freeze' : function(e, value, row, index) {
		layer_show(row.name, baselocation + '/product/' + row.productId + '/pimgedit', 1100, 480)
	},
	'click .normal' : function(e, value, row, index) {
		layer_show(row.name, baselocation + '/product/' + row.productId + '/pimgedit', 1100, 480)
	},
	'click .edit' : function(e, value, row, index) {
		layer_show(row.name, baselocation + '/product/' + row.productId + '/baseedit', 1100, 480)
	},
	'click .pattribute' : function(e, value, row, index) {
		layer_show(row.name, baselocation + '/product/' + row.productId + '/attribute', 800, 480)
	},
	'click .pparm' : function(e, value, row, index) {
		layer_show(row.name, baselocation + '/product/' + row.productId + '/parameter', 800, 480)
	}
};

window.actionBuyEvents = {
		'click .edit' : function(e, value, row, index) {
			layer_show(row.name, baselocation + '/product/' + row.productId + '/buyedit', 800, 480)
		}
	};
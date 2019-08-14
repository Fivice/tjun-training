/**
 * 多选框插件
 */
$(document).ready(function() {
	$('input').iCheck({
		checkboxClass : 'icheckbox_flat-green',
		radioClass : 'iradio_flat-green'
	});
});

/**
 * 表单验证
 */
$(function() {
	
    //表单验证
	$('#form').bootstrapValidator({
		container : 'tooltip',
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			'number' : {
				message : '流水号验证失败',
				validators : {
					notEmpty : {
						message : '流水号不能为空'
					},
                    regexp: {
                       // regexp: /^[a-zA-Z0-9_]+$/,
    				    regexp: /\d{8}/,
                        message: '流水号只能为8位数字'
                    }
				}
			},	
			'day' : {
				message : '刷卡时间验证失败',
				validators : {
					notEmpty : {
						message : '刷卡时间不能为空'
					}
				}
			}
		}
	})
		.on('success.form.bv', function(e) {//点击提交之后
			// Prevent form submission
			e.preventDefault();

			// Get the form instance
			var $form = $(e.target);

			// Get the BootstrapValidator instance
			var bv = $form.data('bootstrapValidator');

			var method = $('#form').attr('data-method');
			// Use Ajax to submit form data
			if (method == 'put') {
				$.ajax({
					data : $form.serialize(),
					dataType : 'json',
					type : 'put',
					url : $form.attr('action'),
					success : function(result) {
						if (result.code == 1) {
							parent.layer.msg("更新成功!", {
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
						if (result.code == 1) {
							parent.layer.msg("新增成功!", {
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
})

//年月范围选择
laydate.render({
    elem: '#startStopTime'
    ,type: 'datetime'
    ,theme: '#6CA6CD'
	/* ,range: '至' //或 range: '~' 来自定义分割字符*/
});
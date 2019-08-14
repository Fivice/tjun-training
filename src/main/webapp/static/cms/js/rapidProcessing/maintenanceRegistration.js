
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
	$('#maintenanceRegistrationForm').bootstrapValidator({
		container : 'tooltip',
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			/*'selectedEmpId' : {
				message : '维修人验证失败',
				validators : {
					notEmpty : {
						message : '维修人不能为空'
					}
				}
			},
			'time' : {
				message : '时间验证失败',
				validators : {
					notEmpty : {
						message : '时间不能为空'
					}
				}
			},
			'money' : {
				message : '维修花费验证失败',
				validators : {
					notEmpty : {
						message : '维修花费不能为空'
					},
					regexp : {
						regexp : /^1[3|4|5|7|8]\d{9}$/,
						message : '手机号码格式不正确'
					}
				}
			},
			'state' : {
				message : '状态验证失败',
				validators : {
					notEmpty : {
						message : '状态不能为空'
					}
				}
			}*/
			 
		}
	})
		.on('success.form.bv', function(e) {
			// Prevent form submission
			e.preventDefault();

			// Get the form instance
			var $form = $(e.target);

			// Get the BootstrapValidator instance
			var bv = $form.data('bootstrapValidator');

			var method = $('#maintenanceRegistrationForm').attr('data-method');
			console.info(method);
			// Use Ajax to submit form data
			if (method == 'post') {
				$.ajax({
					data : $form.serialize(),
					dataType : 'json',
					type : 'post',
					url : $form.attr('action'),
					success : function(result) {
						if (result.code == 1) {
							parent.layer.msg("新增维修登记单成功!", {
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
});






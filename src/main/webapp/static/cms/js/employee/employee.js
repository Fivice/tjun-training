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
	$('#form').bootstrapValidator({
		container : 'tooltip',
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			/*'employee_no' : {
				validators : {
					notEmpty : {
						message : '员工编码不能为空'
					}
				}
			},
			'employeeName' : {
				validators : {
					notEmpty : {
						message : '员工姓名不能为空'
					}
				}
			},*/
			'areaName' : {
				validators : {
					notEmpty : {
						message : '部门编码不能为空'
					}
				}
			},
			'telephone' : {
				validators : {
					notEmpty : {
						message : '电话号码不能为空'
					},
					stringlength:{
					       min:11,
					       max:11,
					       message:'请输入11位手机号码'
					      },
					      regexp:{
					       regexp:/^1[3|5|8]{1}[0-9]{9}$/,
					       message:'请输入正确的手机号码'
					      }
					     
				}
			},	
			'mail' : {
				validators : {
					notEmpty : {
						message : '邮箱地址不能为空'
					},
					emailAddress:{
					       message:'请输入正确的邮箱地址'
					      } 
				}
			},	
		}
	})
		.on('success.form.bv', function(e) {
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
							parent.layer.msg("更新员工成功!", {
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
							parent.layer.msg("创建员工成功!", {
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
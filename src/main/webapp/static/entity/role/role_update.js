
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
            'roleValue' : {
                validators : {
                    notEmpty : {
                        message : '级别名称不能为空'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: ctx+"/role/findRoleValue.action",
                        message: '该级别已存在',
                        delay:500, //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源
                        type: 'POST',
                        //自定义参数
                        data: {
                            roleValue: $('#roleValue').val(),
                            roleId: $('#roleId').val(),
                        }
                    },
                }
            },
			'description' : {
				message : '级别描述验证失败',
				validators : {
					notEmpty : {
						message : '级别描述不能为空'
					}
				}
			},
			'telephone' : {
				validators : {
					notEmpty : {
						message : '移动电话不能为空'
					}/*,
					regexp : {
						regexp : /^1[3|4|5|7|8]\d{9}$/,
						message : '手机号码格式不正确'
					}*/
				}
			}/*,
			'email' : {
				validators : {
					notEmpty : {
						message : '电子邮箱不能为空'
					},
					regexp : {
						regexp : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
						message : '电子邮箱格式不正确'
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

			var method = $('#form').attr('data-method');
			console.info(method);
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
							parent.layer.msg("创建成功!", {
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
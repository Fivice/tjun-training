/**
 * 多选框插件
 */
$(document).ready(function () {
});

/**
 * 表单验证
 */
$(function () {
    $('#form').bootstrapValidator({
        container: 'tooltip',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'number' : {
                message : '流水号验证失败',
                validators : {
                    notEmpty : {
                        message : '流水号不能为空'
                    }
                }
            }
        }
    })
        .on('success.form.bv', function (e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            var method = $('#form').attr('data-method');
            console.info(method);
            // Use Ajax to submit form data
            if (method == 'put') {//更新
                $.ajax({
                    data: $form.serialize(),
                    dataType: 'json',
                    type: 'put',
                    url: $form.attr('action'),
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("班级回收成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.parent.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    }
                })
            } else if (method == 'post') {//保存
                $.ajax({
                    data: $form.serialize(),
                    dataType: 'json',
                    type: 'post',
                    url: $form.attr('action'),
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("添加成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.parent.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    }
                })
            }
        });
})
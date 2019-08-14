
$(function () {
    formValidator();
})
function formValidator() {
    $('#form').bootstrapValidator({
        message: '该值无效',
        trigger: 'blur keyup',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            /*验证：规则*/
            oldpass: {
                validators: {
                    notEmpty: {
                        message: '用户旧密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 19,
                        message: '用户旧密码长度大于5小于20'
                    },
                    regexp: {
                        regexp: /^[^ ]+$/,
                        message: '用户旧密码不能有空格'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: ctx + "/sysuser/validPwd.action",
                        message: '密码输入错误',
                        delay: 2000, //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
                        type: 'POST',
                        //自定义参数
                        data: {
                            oldpass: $('#oldpass').val(),
                        }
                    },
                }
            },
            loginPass: {
                validators: {
                    notEmpty: {
                        message: '用户新密码不能为空'
                    },
                    identical: {
                        field: 'comfirmPassword',
                        message: '用户新密码与确认密码不一致！'
                    },
                    stringLength: {
                        min: 6,
                        max: 19,
                        message: '用户新密码长度大于5小于20'
                    },
                    regexp: {
                        regexp: /^[^ ]+$/,
                        message: '用户新密码不能有空格'
                    }

                }
            },
            comfirmPassword: {
                validators: {
                    identical: {
                        field: 'loginPass',
                        message: '用户新密码与确认密码不一致！'
                    },
                    notEmpty: {
                        message: '用户确认密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 19,
                        message: '用户确认密码长度大于5小于20'
                    },

                    regexp: {
                        regexp: /^[^ ]+$/,
                        message: '用户确认密码不能有空格'
                    }
                }
            }
        }
    })
        .on('error.validator.bv', function (e, data) {//这个方法是让错误信息只显示最新的一个（有时会出现多个错误信息同时显示用这个方法解决）
            data.element
                .data('bv.messages')
                // Hide all the messages
                .find('.help-block[data-bv-for="' + data.field + '"]').hide()
            // Show only message associated with current validator
                .filter('[data-bv-validator="' + data.validator + '"]').show();
        })
        .on('error.field.bv', function (e, data) {//‘用户确认密码’ 没输入的时候，‘用户新密码’不提示‘用户新密码与确认密码不一致’
            // $(e.target)  --> The field element
            // data.bv      --> The BootstrapValidator instance
            // data.field   --> The field name
            // data.element --> The field element
            if (data.field == 'loginPass') {
                var len1 = data.element.val().length;
                var len2 = $('#comfirmPassword').val().length;
                var k = data.element.val().indexOf(" ");
                if (len1 > 5 && len2 < 6 && k < 0) {
                    var $parent = data.element.parents('.form-group');
                    $parent.removeClass('has-error');
                    $parent.find('.form-control-feedback[data-bv-icon-for="' + data.field + '"]').hide();
                    data.element.siblings('[data-bv-validator="identical"]').hide();
                }
            }
        });
}
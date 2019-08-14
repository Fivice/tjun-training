
$(function () {
    formValidator();
})

function formValidator(){
    $("#form").bootstrapValidator({
        container : 'tooltip',
        live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
        submitButtons: '#btnSubmit',//指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面
        message: '通用的验证失败消息',//好像从来没出现过
        feedbackIcons: {//根据验证结果显示的各种图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'hotelExpenseCollection' : {
                validators : {
                    regexp: {//正则验证
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '只能输入数字'
                    },
                }
            },
            'trainingFeeCollection' : {
                validators : {
                    regexp: {//正则验证
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '只能输入数字'
                    },
                }
            },
            'diningFeeCollection' : {
                validators : {
                    regexp: {//正则验证
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '只能输入数字'
                    },
                }
            },
            'otherExpensesCollection' : {
                validators : {
                    regexp: {//正则验证
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '只能输入数字'
                    }
                }
            }

        }
    }
    ).on('success.form.bv', function(e) {
        // Prevent form submission
        e.preventDefault();
        // Get the form instance
        var $form = $(e.target);

        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');

        var method = $('#form').attr('method');
        // Use Ajax to submit form data
        if (method == 'post') {
            $.ajax({
                data: $form.serialize(),
                dataType: 'json',
                type: 'post',
                url: $form.attr('action'),
                success: function (result) {
                    if (result.code == 1) {
                        parent.layer.msg("提交成功!", {
                            shade: 0.3,
                            time: 1500
                        }, function () {
                            window.location.href=ctx+"/classFunds/view.action"; // 刷新页面
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


    })
};
$(function () {
    $("#inputForm").bootstrapValidator({
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
            'siName' : {
                validators : {
                    notEmpty : {
                        message : '姓名不能为空'
                    }
                }
            },
            'phoneNumber' : {
                validators : {
                    notEmpty : {
                        message : '手机号不能为空'
                    },
                  regexp: {//正则验证
                        regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                        message: '手机格式不正确'
                    },
                }
            },
            email: {
                validators: {
                    /*notEmpty: {
                        message: '邮箱不能为空'
                    },*/
                    emailAddress: {
                        message: '邮箱地址格式有误'
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

        var method = $('#inputForm').attr('method');
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
                            window.location.reload(); // 刷新页面
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
    /* $("#btnSubmit").click(function () {//非submit按钮点击后进行验证，如果是submit则无需此句直接验证
     $("#inputForm").bootstrapValidator('validate');//提交验证
       if ($("#inputForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
           /!*alert("yes");*!///验证成功后的操作，如ajax
           $('#inputForm').submit();
       }

    });*/
});
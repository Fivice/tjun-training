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
            'siIDNumber' : {
                validators : {
                    notEmpty : {
                        message : '身份证号不能为空'
                    },
                    regexp: {//正则验证
                        regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                        message: '身份证输入不合法'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: ctx+"/student/findIdCard.action",
                        message: '身份证号已存在',
                        delay:2000, //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
                        type: 'POST',
                        //自定义参数
                        data: {
                            siIDNumber: $('#siIDNumber').val(),
                            siId: $('#siId').val(),
                        }
                        /**
                         data: function (validator) {
                           return {
                                clusterName: $('.clusterName').val(),
                                 "apptype": 1
                                };
                            }
                         */
                    },
                }
            },
            /*'serialNumber' : {
                validators : {
                    notEmpty : {
                        message : '流水号不能为空'
                    }
                }
            },*/

            'siName' : {
                validators : {
                    notEmpty : {
                        message : '姓名不能为空'
                    }
                }
            },
        /*    'siNumber' : {
                validators : {
                    notEmpty : {
                        message : '员工编号不能为空'
                    }
                }
            },*/
            'phoneNumber' : {
                validators : {
                    /*notEmpty : {
                        message : '手机号不能为空'
                    },*/
                  regexp: {//正则验证
                        regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                        message: '手机格式不正确'
                    },
                }
            },
            /*'post' : {
                validators : {
                    notEmpty : {
                        message : '工作岗位不能为空'
                    }
                }
            },*/
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
  /*          text: {
                validators: {
                    notEmpty: {//检测非空,radio也可用
                        message: '文本框必须输入'
                    },
                    stringLength: {//检测长度
                        min: 6,
                        max: 30,
                        message: '长度必须在6-30之间'
                    },
                    regexp: {//正则验证
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '所输入的字符不符要求'
                    },
                    remote: {//将内容发送至指定页面验证，返回验证结果，比如查询用户名是否存在
                        url: '指定页面',
                        message: 'The username is not available'
                    },
                    different: {//与指定文本框比较内容相同
                        field: '指定文本框name',
                        message: '不能与指定文本框内容相同'
                    },
                    emailAddress: {//验证email地址
                        message: '不是正确的email地址'
                    },
                    identical: {//与指定控件内容比较是否相同，比如两次密码不一致
                        field: 'confirmPassword',//指定控件name
                        message: '输入的内容不一致'
                    },
                    date: {//验证指定的日期格式
                        format: 'YYYY/MM/DD',
                        message: '日期格式不正确'
                    },
                    choice: {//check控件选择的数量
                        min: 2,
                        max: 4,
                        message: '必须选择2-4个选项'
                    }
                }
            }*/
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
                            window.location.href=ctx+"/student/view.action"; // 刷新页面
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
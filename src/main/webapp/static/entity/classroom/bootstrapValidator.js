//表单验证
function yanzheng1(id) {
    $(id).bootstrapValidator({
        enabled: true,
        message : 'This value is not valid',
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        // container : 'tooltip',
        // message : 'This value is not valid',
        // feedbackIcons : {
        //     valid : 'glyphicon glyphicon-ok',
        //     invalid : 'glyphicon glyphicon-remove',
        //     validating : 'glyphicon glyphicon-refresh'
        // },
            // container: 'tooltip',
            // live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
            // excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
            // submitButtons: '#btnSubmit',//指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面
            // message: '通用的验证失败消息',//好像从来没出现过
            // feedbackIcons: {//根据验证结果显示的各种图标
            //     valid: 'glyphicon glyphicon-ok',
            //     invalid: 'glyphicon glyphicon-remove',
            //     validating: 'glyphicon glyphicon-refresh'
            // },
            fields: {
                'schoolName': {
                    // message: '校区名称验证失败',
                    validators: {
                        notEmpty: {
                            message: '校区名称不能为空'
                        },
                        remote: {//ajax验证。server result:{"valid",true or false}
                            url: ctx + "/classroom/getSchoolName.action",
                            message: '校区名称已存在',
                            delay: 2000, //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
                            type: 'POST'
                        }

                    }
                },
                // 'className': {
                //     validators: {
                //         notEmpty: {
                //             message: '教室名称不能为空'
                //         },
                //         remote: {//ajax验证。server result:{"valid",true or false}
                //             url: ctx + "/classroom/getClassName.action",
                //             message: '教室名称已存在',
                //             delay: 2000, //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
                //             type: 'POST',
                //             data: {
                //                 // schoolName: $("#schoolName").find("option:selected").val(),
                //                 className: $('#className').val(),
                //                 capacity:$("#capacity").val()
                //             }
                //         }
                //     }
                // },
                // 'capacity': {
                //     validators: {
                //         notEmpty: {
                //             message: '教室容量不能为空'
                //         },
                //         numeric: {
                //             message: '教室容量只能是数字'
                //         }
                //     }
                // }

            }
        }
    ).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();
        // Get the form instance
        var $form = $(e.target);

        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');

        var method = $('#form').attr('method');

    })
}
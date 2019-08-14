
//表单验证
function verifyForm(id,schoolName,cid) {
    $(id).bootstrapValidator({
        // enabled: true,
        live: 'disabled',
        message : 'This value is not valid',
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
            fields: {
                'className': {
                    validators: {
                        notEmpty: {
                            message: '教室名称不能为空'
                        },
                        remote: {//ajax验证。server result:{"valid",true or false}
                            url: ctx + "/classroom/getClassName.action",
                            message: '教室名称已存在',
                            delay: 2000, //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
                            type: 'POST',
                            data:{
                                schoolName: function () {
                                    return schoolName;
                                },
                                className: function () {
                                    return  $("#className").val().trim();
                                },
                                id: function () {
                                    return cid;
                                },
                            }
                            // function () {
                            //     return {
                            //         schoolName: $("#classroomType option:selected").val(),
                            //         className:$("#className").val().trim()
                            //     }
                            // }
                            // data: {
                            //     // schoolName: $("#schoolName").find("option:selected").val(),
                            //     schoolName:1,
                            //     className: $('#className').val(),
                            //     // capacity:$("#capacity").val()
                            // }
                        }
                    }
                },
                'capacity': {
                    validators: {
                        notEmpty: {
                            message: '教室容量不能为空'
                        },
                        numeric: {
                            message: '教室容量只能是数字'
                        },
                        regexp: {
                            regexp: /^\d+(\.\d{0,2})?$/,
                            message: '请输入大于0的正数'
                        }
                    }
                }

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
//表单验证
function yanzheng(id){

$(id).bootstrapValidator({
    /*container : 'tooltip',*/
   /* live: 'enabled',*/
    enabled: true,
    message : 'This value is not valid',
    feedbackIcons : {
        valid : 'glyphicon glyphicon-ok',
        invalid : 'glyphicon glyphicon-remove',
        validating : 'glyphicon glyphicon-refresh'
    },
    fields : {
        'name' : {
            validators : {
                notEmpty : {
                    message : '姓名不能为空'
                }
            }
        },
        'phoneNumber' : {
            validators : {
                notEmpty : {
                    message : '电话号码不能为空'
                },
                regexp: {
                    regexp: /^(1[35784]\d{9})$/,
                    message: '电话号码格式有误'
                },
            }
        },
        'tiDepartment' : {
            validators : {
                notEmpty : {
                    message : '单位不能为空'
                }
            }
        },
        'subject' : {
            validators : {
                notEmpty : {
                    message : '授课科目不能为空'
                }
            }
        },
    }
})
}
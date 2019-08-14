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
        'schoolName' : {
            validators : {
                notEmpty : {
                    message : '校区名称不能为空'
                }
            }
        },
    'className' : {
        validators : {
            notEmpty : {
                message : '教室名称不能为空'
            }
        }
    },
    'capacity' : {
        validators: {
            notEmpty: {
                message: '教室容量不能为空'
            },
            numeric: {
                message: '教室容量只能是数字'
            }
        }
    }
    }
})
}
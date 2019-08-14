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
        'areaName' : {
            validators : {
                notEmpty : {
                    message : '单位名称不能为空'
                }
            }
        },
        'contactsTel' : {
            validators : {
                regexp: {
                    regexp: /^((0\d{2,3}-\d{7,8})|(1[35784]\d{9}))$/,
                    message: '联系方式格式有误。'
                },
            }
        },
        'email' :{
            validators : {
                emailAddress: {
                    message: '邮箱地址格式有误。'
                },
            }
        },
        'sort' :{
            validators : {
                notEmpty : {
                    message : '排序不能为空'
                },digits: {
        message: '排序只能填数字。'
    }
            }
        }
    }
})
}
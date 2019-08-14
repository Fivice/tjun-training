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
                    message : '院校名称不能为空'
                }
            }
        },
        'url' : {
            validators : {
                notEmpty : {
                    message : '系统外网网址不能为空'
                },
                regexp: {//正则验证
                    regexp: /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/,
                    message: '不合法的外网网址！'
                },
            }
        },
        'address' :{
            validators : {
                notEmpty : {
                    message : '报名住宿地点不能为空'
                }
            }
        },
        'eatPlace' :{
            validators : {
                notEmpty : {
                    message : '就餐地点不能为空'
                }
            }
        },
        'eatStandard0' :{
            validators : {
                notEmpty : {
                    message : '早餐标准不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                    message: '只能输入数字'
                }
            }
        },
        'eatStandard1' :{
            validators : {
                notEmpty : {
                    message : '午餐标准不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                    message: '只能输入数字'
                }
            }
        },
        'eatStandard2' :{
            validators : {
                notEmpty : {
                    message : '晚餐标准不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                    message: '只能输入数字'
                }
            }
        },
        'houseStandard0' :{
            validators : {
                notEmpty : {
                    message : '标间住宿标准不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                    message: '只能输入数字'
                }
            }
        },
        'houseStandard1' :{
            validators : {
                notEmpty : {
                    message : '单间住宿标准不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                    message: '只能输入数字'
                }
            }
        }
    }
})
}
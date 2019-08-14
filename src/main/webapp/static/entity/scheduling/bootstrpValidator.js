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
            'day' : {
                validators : {
                    notEmpty : {
                        message : '日期不能为空'
                    }
                }
            },


        }
    })
}
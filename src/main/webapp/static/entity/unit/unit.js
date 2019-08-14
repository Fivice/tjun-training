//添加单位信息维护
function creatUnit(){
    layer.open({
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        type : 2,
        title : '添加单位维护信息',
        skin: 'layui-layer-molv', //样式类名
        area : [ '900px', '700px' ],
        shade : 0,
        maxmin : true,
        content :  'create.action',
        btn : [ '保存', '取消' ] //只是为了演示
        ,
        yes : function(index,layero) {
            var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
            //进行表单验证
            yanzheng(inputForm);
            inputForm.bootstrapValidator('validate');
            var flag = inputForm.data('bootstrapValidator').isValid();
            if(flag) {
                inputForm.ajaxSubmit({
                    url:  'save.action',
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("添加成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    }
                });
            }
        },
        btn2 : function() {
            layer.closeAll();
        },
        success : function(layero) {
            layer.setTop(layero); //重点2
        }
    });
}

//删除单位信息
function unitList_delete(areaId) {
    layer.confirm('您确定要删除这条数据吗？', {
        btn : [ '确定', '取消' ]
        //按钮
    }, function() {
        $.ajax({
            type : 'POST',
            url : 'delete.action',//发送请求
            data : {
                areaId : areaId
             },
            dataType : "json",
            success : function(result) {
                if (result.code == 1) {
                    layer.msg('该单位删除成功!', {
                        icon : 1,
                        time : 1000
                    }, function() {
                        window.location.reload(); // 刷新页面
                    });
                } if (result.code == 0) {
                    layer.msg('该单位已被使用，删除不成功，请先清空该单位下级单位!', {
                        icon : 5,
                        time : 3000
                    }, function() {
                        window.location.reload(); // 刷新页面
                    });
                }
                else {
                    layer.msg(result.message, {
                        icon : 2
                    });
                }
            }
        });
    });
}

//编辑单位信息
function unitList_edit(areaId){
    layer.open({
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        type : 2,
        title : '编辑单位信息',
        skin: 'layui-layer-molv', //样式类名
        area : [ '900px', '700px' ],
        shade : 0,
        maxmin : true,
        content :  'update.action?areaId='+areaId,
        btn : [ '保存', '取消' ] //只是为了演示
        ,
        yes : function(index,layero) {

            var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
            yanzheng(inputForm);
            inputForm.bootstrapValidator('validate');
            var flag = inputForm.data('bootstrapValidator').isValid();
            if(flag) {
                inputForm.ajaxSubmit({
                    url: 'saveUnit.action',
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("更新成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    }
                });
            }
        },
        btn2 : function() {
            layer.closeAll();
        },
        success : function(layero) {
            layer.setTop(layero); //重点2
        }
    });

}

//添加下级单位信息维护
function unitList_creatUnit(areaId){
    layer.open({
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        type : 2,
        title : '添加下级单位维护信息',
        skin: 'layui-layer-molv', //样式类名
        area : [ '900px', '700px' ],
        shade : 0,
        maxmin : true,
        content :  'addUnit.action?areaId='+areaId,
        btn : [ '保存', '取消' ] //只是为了演示
        ,
        yes : function(index,layero) {
            var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
            //进行表单验证
            yanzheng(inputForm);
            inputForm.bootstrapValidator('validate');
            var flag = inputForm.data('bootstrapValidator').isValid();
            if(flag) {
                inputForm.ajaxSubmit({
                    url:  'saveLowerUnit.action',
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("添加成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    }
                });
            }
        },
        btn2 : function() {
            layer.closeAll();
        },
        success : function(layero) {
            layer.setTop(layero); //重点2
        }
    });
}


$(function () {
    formValidator();
})

function formValidator() {
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
                    }
                }
            },
            'siName' : {
                validators : {
                    notEmpty : {
                        message : '姓名不能为空'
                    }
                }
            },
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
            /* 'reportTime' : {
                 validators : {
                     notEmpty : {
                         message : '报到时间不能为空'
                     }
                 }
             },*/
            'number' : {
                validators : {
                    /*notEmpty : {
                        message : '流水号不能为空'
                    },*/
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: ctx+"/register/findStudentCardNumber.action",
                        message: '此流水号不存在',
                        delay:2000, //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
                        type: 'POST',
                        //自定义参数
                        data: {
                            number: $('#number').val(),
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
        }
    })
    $("#btnSubmit").click(function () {//非submit按钮点击后进行验证，如果是submit则无需此句直接验证
        $("#inputForm").bootstrapValidator('validate');//提交验证
        setTimeout(function() {
            if ($("#inputForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码


                var studentId = $("#siId").val();
                var classId = $("#classId").val();
                //身份证号
                var siIDNumber = $("#siIDNumber").val().trim();
                //正则验证是否符合身份证格式
                var patrn = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if (!patrn.exec(siIDNumber)) {
                    layer.msg("身份证格式不正确");
                } else {

                    /* if (typeof studentId == "undefined" || studentId == null || studentId == "") {

                         layer.alert("没有学员信息");
                     } else */if (typeof classId == "undefined" || classId == null || classId == "") {

                        layer.alert("没有班级信息，请选择班级");
                    } else {

                        //根据身份证号查询学生信息
                        /*   $.ajax({
                               type: "POST",//方法类型
                               dataType: "json",//预期服务器返回的数据类型
                               url: ctx + "/register/findStudentByNumber.action",//url
                               data: {
                                   siIDNumber: siIDNumber,
                               },
                               success: function (result) {

                                   if(result.student==null){
                                       layer.alert("没有此身份证的学生信息");
                                   }else{
                                       //赋值学生id
                                        studentId = result.student.siId;
                                       $("#siId").prop("value",result.student.siId);*/

                        $.ajax({
                            type: "POST",//方法类型
                            dataType: "json",//预期服务器返回的数据类型
                            url: ctx + "/register/findRegister.action",//url
                            data: {
                                studentId: studentId,
                                classId: classId,
                            },
                            success: function (result) {
                                console.log(studentId);
                                //更新学生
                                if(typeof studentId != "undefined" && studentId != null && studentId != ""){

                                    if (result.length == 0) {
                                        layer.confirm('请确定缴费方式？', {
                                                btn: ['前台缴费', '未缴费', '国网商旅', '统一转账'], //按钮
                                                area:'400px',
                                                skin: 'layui-layer-lan',

                                                //国网商旅
                                                btn3: function(index, layero){
                                                    //按钮【按钮三】的回调
                                                    //赋值
                                                    $("#training").prop("value", $("#trainingExpense").val());
                                                    $("#other").prop("value", $("#otherCharges").val());
                                                    $("#scalefees").prop("value", $("#scaleFeeTotal").val());
                                                    $("#food").prop("value", $("#foodTotal").val());

                                                    //改变状态
                                                    $("#pay").prop("value", "3");

                                                    $("#myModal").modal();

                                                    /*$("#trainingExpense").prop("value", 0);
                                                    $("#otherCharges").prop("value", 0);
                                                    $("#scaleFeeTotal").prop("value", 0);
                                                    $("#foodTotal").prop("value", 0);

                                                    //改变状态
                                                    $("#pay").prop("value", "3");

                                                    formRegister();
                                                    layer.close(index);*/



                                                },
                                                //统一转账
                                                btn4: function(index, layero){
                                                    //按钮【按钮四】的回调

                                                    //赋值
                                                    $("#training").prop("value", $("#trainingExpense").val());
                                                    $("#other").prop("value", $("#otherCharges").val());
                                                    $("#scalefees").prop("value", $("#scaleFeeTotal").val());
                                                    $("#food").prop("value", $("#foodTotal").val());

                                                    //改变状态
                                                    $("#pay").prop("value", "4");

                                                    $("#myModal").modal();

                                                    /*$("#trainingExpense").prop("value", 0);
                                                    $("#otherCharges").prop("value", 0);
                                                    $("#scaleFeeTotal").prop("value", 0);
                                                    $("#foodTotal").prop("value", 0);

                                                    //改变状态
                                                    $("#pay").prop("value", "4");

                                                    formRegister();
                                                    layer.close(index);*/
                                                }
                                                //已缴费
                                            }, function () {
                                                //确认缴费
                                                $("#pay").prop("value", "1");
                                                formRegister();

                                                //未缴费
                                            }, function () {
                                                //未交费
                                                /*   layer.msg('未交费将无法进行就餐和住宿', {
                                                       time: 0 //不自动关闭
                                                       ,btn: ['确定', '取消']
                                                       ,yes: function(index){*/

                                                //未交费设置不就餐和不住宿
                                                //$("#dining").find("option[value='2']").attr("selected",true);//不就餐
                                                //$("#hotel").find("option[value='2']").attr("selected",true);//不住宿

                                                $("#trainingExpense").prop("value", 0);
                                                $("#otherCharges").prop("value", 0);
                                                $("#scaleFeeTotal").prop("value", 0);
                                                $("#foodTotal").prop("value", 0);
                                                formRegister();
                                                layer.close(index);
                                                /*   }
                                               });*/

                                                /*layer.closeAll();*/


                                            }
                                        );
                                    } else {
                                        layer.alert("该学员已经报到！");
                                    }


                                    //新增学生
                                }else{
                                    layer.confirm('请确定缴费方式？', {
                                            btn: ['前台缴费', '未缴费', '国网商旅', '统一转账'], //按钮
                                            area: '400px',
                                            skin: 'layui-layer-lan',
                                            //国网商旅
                                            btn3: function(index, layero){
                                                //按钮【按钮三】的回调

                                                //赋值
                                                $("#training").prop("value", $("#trainingExpense").val());
                                                $("#other").prop("value", $("#otherCharges").val());
                                                $("#scalefees").prop("value", $("#scaleFeeTotal").val());
                                                $("#food").prop("value", $("#foodTotal").val());

                                                //改变状态
                                                $("#pay").prop("value", "3");

                                                $("#myModal").modal();

                                                /*$("#trainingExpense").prop("value", 0);
                                                $("#otherCharges").prop("value", 0);
                                                $("#scaleFeeTotal").prop("value", 0);
                                                $("#foodTotal").prop("value", 0);

                                                //改变状态
                                                $("#pay").prop("value", "3");

                                                formRegister();
                                                layer.close(index);*/



                                            },
                                            //统一转账
                                            btn4: function(index, layero){
                                                //按钮【按钮四】的回调

                                                //赋值
                                                $("#training").prop("value", $("#trainingExpense").val());
                                                $("#other").prop("value", $("#otherCharges").val());
                                                $("#scalefees").prop("value", $("#scaleFeeTotal").val());
                                                $("#food").prop("value", $("#foodTotal").val());

                                                //改变状态
                                                $("#pay").prop("value", "3");

                                                $("#myModal").modal();

                                                /*$("#trainingExpense").prop("value", 0);
                                                $("#otherCharges").prop("value", 0);
                                                $("#scaleFeeTotal").prop("value", 0);
                                                $("#foodTotal").prop("value", 0);

                                                //改变状态
                                                $("#pay").prop("value", "4");

                                                formRegister();
                                                layer.close(index);*/
                                            }

                                        }, function () {
                                            //确认缴费
                                            $("#pay").prop("value", "1");
                                            formRegister();
                                        }, function () {
                                            //未交费

                                            //未交费
                                            /* layer.msg('未交费将无法进行就餐和住宿', {
                                                 time: 0 //不自动关闭
                                                 ,btn: ['确定', '取消']
                                                 ,yes: function(index){*/

                                            //未交费设置不就餐和不住宿
                                            //$("#dining").find("option[value='2']").attr("selected",true);//不就餐
                                            //$("#hotel").find("option[value='2']").attr("selected",true);//不住宿

                                            $("#trainingExpense").prop("value", 0);
                                            $("#otherCharges").prop("value", 0);
                                            $("#scaleFeeTotal").prop("value", 0);
                                            $("#foodTotal").prop("value", 0);
                                            formRegister();
                                            layer.close(index);
                                            /*   }
                                           });*/

                                            /*layer.closeAll();*/


                                        }
                                    );
                                }

                            },
                            error: function () {
                                layer.alert("错误信息！请联系管理者！");
                            }
                        });

                        /* }

                     }
                 })*/







                    }
                }


            }
        },3000);

    });
};

//计算费用按钮
function computation_fee() {
    var total = 0;
    $('input[name="fee"]:checked').each(function(){
        total+=Number($(this).val());
    })
    //设置现有总费用(住宿费+就餐费+其它费用+培训费)
    $("#total_cost").prop("value",total);
}


//模态框确定按钮
function definition_fee() {

    //判断培训费是否选中(未选中则设置费用为0)
    if(!$('#training').is(':checked')) {
        $("#trainingExpense").prop("value", 0);
    }
    //判断其它费用是否选中(未选中则设置费用为0)
    if(!$('#other').is(':checked')) {
        $("#otherCharges").prop("value", 0);
    }
    //判断住宿费是否选中(未选中则设置费用为0)
    if(!$('#scalefees').is(':checked')) {
        $("#scaleFeeTotal").prop("value", 0);
    }
    //判断就餐费是否选中(未选中则设置费用为0)
    if(!$('#food').is(':checked')) {
        $("#foodTotal").prop("value", 0);
    }
    $('#myModal').modal('hide');
    formRegister();

}


//全选按钮函数封装
function allChecked(id,name) {
    // 获得复选框
    var $selectAll = $(id);
    $selectAll.click(function() {
        if ($selectAll.prop("checked") == true) {
            // 上面的复选框已被选中
            $(":checkbox[name="+name+"]").prop("checked", true);
        } else {
            // 上面的复选框没被选中
            $(":checkbox[name="+name+"]").prop("checked", false);
        }
    });

}
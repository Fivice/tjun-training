//查询学生
function findStudent() {
    var siIDNumber = $("#siIDNumber").val();
    /*window.location.href=ctx +'/register/findInfo.action?siIDNumber='+siIDNumber;*/
    //正则验证是否符合身份证格式
    var patrn = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (!patrn.exec(siIDNumber)) {

        //学生id
        $("#siId").prop("value", "");
        $("#siName").prop("value", "");
        $("#phoneNumber").prop("value", "");
        $("#unitId").prop("value", "");
        //学生照
        $("#photo").html("");
        layer.msg("身份证格式不正确");
    } else {

        $.ajax({ //查询学生信息
            type: 'post',
            url: ctx + '/register/findInformation.action',
            data: {
                siIDNumber: siIDNumber,
            },
            dataType: "json", //返回的结果为json
            success: function (data) {
                if (data.student == null) {
                    layer.confirm('没有学员信息，是否添加新学员？', {
                        btn: ['确定', '取消'] //按钮
                    }, function () {
                        window.location.href = ctx + '/student/form.action?siIDNumber='+siIDNumber;
                    }, function () {
                        //学生id
                        $("#siId").prop("value", "");
                        $("#siName").prop("value", "");
                        $("#phoneNumber").prop("value", "");
                        $("#unitId").prop("value", "");
                        //学生照
                        $("#photo").html("")
                        layer.closeAll();
                    });
                } else if (data.student != null) {
                    //学生id
                    $("#siId").prop("value", data.student.siId);
                    $("#siName").prop("value", data.student.siName);
                    $("#phoneNumber").prop("value", data.student.phoneNumber);
                    /*$("#unitId").prop("value",data.student.unit.areaName);*/
                    //学生照
                    var p = ctx + data.student.photo;
                    $("#photo").html("<img style='width: 200px;height: 200px;' src=" + p + " />");
                    var siUId=data.student.siUnitId;
                    if(typeof siUId != "undefined" && siUId != null && siUId != ""){
                        //查询单位
                        $.ajax({
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                areaId: data.student.siUnitId,
                            },
                            url: ctx + '/register/findUnitList.action',
                            success: function (result) {
                                var dataObj = result, //返回的result为json格式的数据
                                    con = "";
                                $.each(dataObj, function (index, item) {
                                    if (index > 0) {
                                        con += ">>" + item.areaName;
                                    } else {
                                        con += item.areaName;
                                    }

                                });
                                $("#unitId").prop("value", con); //把内容入到这个div中即完成
                            }
                        })
                    }else{
                        $("#unitId").prop("value", data.student.unitName);
                    }
                }
            },
        });
    }
}
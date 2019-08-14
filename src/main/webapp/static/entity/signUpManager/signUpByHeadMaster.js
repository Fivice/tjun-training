
function stuSearch(){
    var siIdNumber = $("#stu-idNumber-se").val();
    var classId = $("#stu-classId").val();
    // console.log("班级id："+classId)
    // console.log(siIdNumber)
    //正则验证是否符合身份证格式
    var patrn = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (siIdNumber == ""){
        layer.msg("身份证号不能为空")
    } else if(!patrn.exec(siIdNumber)){
        layer.msg("身份证格式不正确")
    }else{
        // console.log(siIdNumber)
        $.ajax({
            type:"GET",
            dataType:"json",
            url:ctx+"/signUpManager/signUpByHeadMaster.action",
            data:{"idNumber":siIdNumber,"classId":classId},
            success:function (data) {
                // console.log(data)
                if(data.signUpInfo == "NO_STUDENT_INFO"){
                    layer.confirm('没有学员信息，是否添加新学员？', {
                        btn: ['确定', '取消'] //按钮
                    }, function () {
                        window.location.href = ctx + '/student/form.action?siIDNumber='+siIdNumber;
                    }, function () {
                        //清空列表
                        $("#stu-name").prop("value", "");
                        $("#stu-serial-number").prop("value", "");
                        $("#stu-phone").prop("value", "");
                        $("#stu-unit").prop("value", "");
                        $("#stu-report-status").prop("value", "");
                        $("#stu-report-time").prop("value", "");
                        $("#stu-department").prop("value", "")
                        $("#stu-report-status-name").prop("value","");
                        layer.closeAll();
                    });
                }else {
                    var statusName;
                    if (data.signUpInfo.status == "1"){
                        statusName = "已报名"
                    }
                    if (data.signUpInfo.status == "2"){
                        statusName = "未报名"
                    }
                    $("#stu-name").val(data.signUpInfo.student.siName)
                    $("#stu-serial-number").val(data.signUpInfo.student.serialNumber)
                    $("#stu-phone").val(data.signUpInfo.student.phoneNumber)
                    $("#stu-unit").val(data.signUpInfo.student.unitName)
                    $("#stu-report-time").val(data.signUpInfo.reportTime)
                    $("#stu-report-status").val(data.signUpInfo.status)
                    $("#stu-department").val(data.signUpInfo.student.deparentmentName)
                    $("#stu-report-status-name").val(statusName)
                    $("#stu-id").val(data.signUpInfo.student.siId)
                    if (data.signUpInfo.student.photo!=null||data.signUpInfo.student.photo!=""){
                        var value = data.signUpInfo.student.photo
                        $("#stu-head-img").attr("src", value);
                    }

                }

            },
            error:function () {
                layer.msg("异常！请联系管理员")
            }

        })
    }
}
$(document).ready(function(){
    $("#signUpConfirm").bind("click",function(){
        submitRegister();
    });
});

//提交学员Id和班级Id到后台，走报名流程
function submitRegister() {
    var siId = $("#stu-id").val();
    var classId = $("#stu-classId").val();
    var status  = $("#stu-report-status").val();
    // console.log("学员id："+siId+"班级id："+classId)

    if (siId ==""||classId==""){
        layer.msg("没有学员信息")
    } else if (status == "1"){
        layer.msg("该学员已经在该班级报过名")
    } else {
        $.ajax({
            type:"GET",
            dataType:"json",
            url:ctx+"/signUpManager/submitSignUpByHeadMaster.action",
            data:{"siId":siId,"classId":classId,"status":status},
            success:function (data) {
                if (data.message == "1") {
                    layer.msg("该学员已经在该班级报过名")
                } else if (data.message == "3"){
                    layer.msg("报名成功")
                    $("#signUpModal").modal("hide");
                    $("#table").bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
                    //清空列表
                    $("#stu-name").prop("value", "");
                    $("#stu-serial-number").prop("value", "");
                    $("#stu-phone").prop("value", "");
                    $("#stu-unit").prop("value", "");
                    $("#stu-report-status").prop("value", "");
                    $("#stu-department").prop("value", "");
                    $("#stu-report-status-name").prop("value","");
                    $("#stu-report-time").prop("value", "")
                }
            },
            error:function () {
                layer.msg("异常！请联系管理员")
            }
        })
    }
}
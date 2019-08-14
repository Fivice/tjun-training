//跳出iframe
$(function () {
    if (top.location != location){
        top.location.href = location.href;
    }
    ctx = getRootPath();
    // console.log(ctx)
})
var ctx;
function getRootPath()
{
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    return window.location.protocol + '//' + window.location.host + '/'+ webName;
}
//进入评价页面判断
function evaluation(classId) {
    var idNumber = $("#idNumber-eva").val();
    var evaluateStartTime = new Date(Date.parse($('#evaluateStartTime').val()));
    var evaluateStopTime =new Date(Date.parse($('#evaluateStopTime').val()));
    var curDate = new Date();
    $.ajax({
        type: "get",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: ctx+"/remoteOperate/judgeStudentEvaluationQualification.action" ,//url
        data: {"classId":classId,"idNumber":idNumber},
        success:function (data) {
            console.log(data);
            if (data.classInfo != null){
                evaluateStartTime = new Date(Date.parse(data.classInfo.evaluateStartTime));
                evaluateStopTime = new Date(Date.parse(data.classInfo.evaluateStopTime));
            }
            if (data.register == null){
                //没有注册信息或者没有学员信息，也就是没报到
                layer.msg("对不起！您没有在该班级报到，无法评价！")
            } else if (data.register.status == 1) {
                layer.msg("您已经参与过评价！")
            }else if (data.classInfo.evaluate == 1) {
                layer.msg("该班级不参与评价")
            }else if(curDate<=evaluateStartTime||curDate>=evaluateStopTime){
                layer.msg("不在班级评价时间内")
            }else {
                console.log("可以参加评价");
                window.location.href =ctx+'/remoteOperate/judgeView.action?classId=' + data.register.classId+'&siId='+data.register.siId;
            }
            

        }
    })
}

//培训记录
$(function () {
    $("#training").click(function () {
        var idNumber = $("#idNumber").val()
        var studentId = $("#stu-id").val()
        // console.log(idNumber)
        var temp;
        if (studentId == null||studentId =="") {
            layer.msg("您还没有报到,没有相关记录")
        }else if(idNumber!==null&&idNumber!==""){
            $.ajax({
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: ctx+"/remoteOperate/studentTrainingHistory.action" ,//url
                data: {"siIdNumber":idNumber},
                success:function (data) {
                    // console.log(data)
                    if (data.length==0){
                        layer.msg("没有培训记录")
                    }else{
                        for (i in data){
                            var num =parseInt(i)+parseInt(1);
                            temp +="<tr><td>"+num+"</td> <td>"+data[i].className+"</td> <td>"+data[i].hostUnit+"</td> <td>"+data[i].startStopTime+"</td> <td>"+data[i].days+"</td> <td>"+data[i].classHours+"</td></tr>"
                        }
                        // console.log(temp)
                        $("#training-history-body").html(temp)
                    }
                },
                error:function () {
                    layer.alert("查询培训记录异常");
                }
            })
        }
    })
})
//文件下载列表
$(function () {
    $("#file").click(function () {
        var fileUrl = $("#fileUrl").val();
        // console.log(fileUrl)
        var temp ="";
        if (fileUrl==null||fileUrl==""){
            temp+= "<tr><td>没有上传文件</td><td></td></tr>"
        } else{
            var fileArr = String(fileUrl).split(",");
            // console.log(fileArr)

            for (var i = 0; i < fileArr.length; i++) {
                var arr = fileArr[i].split("/");
                // console.log(arr)
                temp+="<tr><td>"+arr[3]+"</td><td><a href=\'"+fileArr[i]+"\'>下载</a></td></tr>"
            }
        }

        $("#file-list").html(temp)
    })
})

//获取民族json
function geNationList() {
    var nation = $("#stu-nation").val();
    var nationId = "1";
    $.ajax({
        url:ctx+"/remoteOperate/formNationList.action",
        type: "get",//请求方式
        dataType: "json",//数据类型可以为 text xml json  script  jsonp
        success: function(data){//返回的参数就是 action里面所有的有get和set方法的参数
            // console.log(data)
            var temp="";
            for (var i in  data) {
                if (data[i].name == nation){
                    nationId = data[i].id;
                }
                temp+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"
                // console.log(result[i].id);
            }
            $("#nation").html(temp);
            $("#nation").val(nationId)
        }
    })
}

//搜索学生信息
function search(id){

    var classId = id;
    var idNumber = $("#idNumber").val()
    // console.log("班级id："+id+" 身份证号码："+idNumber)
    //正则验证是否符合身份证格式
    var patrn = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(""===idNumber){
        layer.msg("请输入身份证号码")
    }else if (!patrn.exec(idNumber)) {
        layer.msg("身份证格式不正确")
    }else{
        $("#stu-idNumber").val(idNumber)
        $.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: ctx+"/remoteOperate/registerBySelf.action" ,//url
            data: {"classId":classId,"idNumber":idNumber},
            success:function (data) {
                var studentInfo = data.studentInfo;
                // console.log(studentInfo)
                if(studentInfo != null){
                    $("#stu-name").val(studentInfo.student.siName)
                    $("#stu-phone").val(studentInfo.student.phoneNumber)
                    $("#stu-email").val(studentInfo.student.email)
                    $("#stu-number").val(studentInfo.student.siNumber)
                    $("#stu-id").val(studentInfo.student.siId)
                    $("#stu-dep").val(studentInfo.student.deparentmentName)
                    $("#stu-nation").val(studentInfo.student.ethnicGroup)
                    switch (studentInfo.regStatus) {
                        case "1":$("#stu-reg-status").val("已报到");break;
                        case "2":$("#stu-reg-status").val("未报到");break;
                        case "3":$("#stu-reg-status").val("已报名");break;
                        case "4":$("#stu-reg-status").val("未报名");break;

                    }
                    // $("#stu-reg-status").val(studentInfo.regStatus==1?"已报名":"未报名")
                    geNationList();
                    setUnitSelect(studentInfo.student.siUnitId);
                }else{
                    geNationList();
                    studentInfo = null;
                    $("#stu-reg-status").val("未报名")
                    layer.msg("没有学员信息！请填报")
                }
            },
            error:function () {
                layer.alert("查询学员信息异常");
            }
        })

        $("#sign-up-info").show()
        $("#search-context").hide()
    }
}
function setUnitSelect(unitId){
    //console.log("部门id："+unitId)
    var nextSelectContext = "<option value=\""+"-1"+"\">"+"请选择"+"</option>";
    var temp = -1;
    var nextTemp =-1;
    for(var i in result){
        //在一级部门里查找
        var id = result[i].unitId;
        if(unitId == id){
            //在一级部门里找到
            $("#host-units").val(id)
            temp = id;
            //console.log("在一级部门里找到："+id)

        }else{
            var childResult = result[i].unitVOList;
            for(var j in childResult){
                var cId = childResult[j].unitId;
                nextSelectContext +="<option value=\""+childResult[j].unitId+"\">"+childResult[j].unitName+"</option>"
                if(unitId == cId){
                    //在二级部门里找到
                    nextTemp = cId;
                    $("#host-units").val(result[i].unitId)
                    // console.log("在二级部门里找到："+cId)

                }
            }
            $("#next-units").html(nextSelectContext);

            $("#next-units").val(nextTemp);

        }

    }
}
var result;//用于保存ajax返回的单位分级信息数据
//进页面从服务器获取部分级别表并初始化
$(function () {
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: ctx+"/remoteOperate/unitGradList.action" ,//url
        success:function (data) {
            result = data;
            var selectContext = "<option value=\""+"-1"+"\">"+"请选择"+"</option>";
            for (var i in result) {
                selectContext +="<option value=\""+result[i].unitId+"\">"+result[i].unitName+"</option>"
            }
            $("#host-units").html(selectContext);

        },
        error:function () {
            layer.alert("获取单位信息失败");
        }
    })
})
//当select选中发生变化时，触发下面函数来改变下级单位的select列表
$(function(){
    $("#host-units").change(function () {
        // console.log($("#host-units").val())
        var nextSelectContext = "<option value=\""+"-1"+"\">"+"请选择"+"</option>";
        var selectUnitId = $("#host-units").val();
        var resultChild ;
        for (var i in result) {
            var unitId = result[i].unitId;
            if(selectUnitId == unitId){
                resultChild = result[i].unitVOList;
                for(var j in resultChild){
                    nextSelectContext +="<option value=\""+resultChild[j].unitId+"\">"+resultChild[j].unitName+"</option>"
                }
            }
        }
        $("#next-units").html(nextSelectContext);
    })
})
function clearRegisterForm(){
    $("#idNumber").val("")
    $("#stu-id").val("")
    $("#stu-name").val("")
    $("#stu-phone").val("")
    $("#stu-email").val("")
    $("#stu-number").val("")
    $("#stu-reg-status").val("")
    $("#stu-dep").val("")
    $("#nation").val()
}
function registerBySelf() {
    var stuInfo = {
        classId : $("#stu-classId").val(),
        siIdNumber:$("#idNumber").val(),
        siId : $("#stu-id").val(),
        siName : $("#stu-name").val(),
        siPhone : $("#stu-phone").val(),
        email : $("#stu-email").val(),
        stuNumber : $("#stu-number").val(),
        firstUnitSelect : $("#host-units").val(),
        nextUnitSelect : $("#next-units").val(),
        regStatus : $("#stu-reg-status").val(),
        departmentName: $("#stu-dep").val(),
        nation: $("#nation").val()
    }
    // console.log(stuInfo)
    //正则验证是否符合身份证格式
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    var phonePattern = /^1[34578]\d{9}$/;
    if (stuInfo.regStatus == "已报名") {
        layer.msg("已经过报名")
    }else if (stuInfo.regStatus == "已报到"){
        layer.msg("已经过报到")
    } else if(""===stuInfo.siIdNumber){
        layer.msg("请输入身份证号码")
    }else if (!pattern.exec(stuInfo.siIdNumber)) {
        layer.msg("身份证格式不正确")
    }else if (stuInfo.siName === ""){
        layer.msg("姓名不能为空")
    }else if (stuInfo.siPhone === ""){
        layer.msg("电话不能为空")
    }else if (!phonePattern.exec(stuInfo.siPhone)){
        layer.msg("请输入正确的手机号")
    }else{
        layer.msg("报名中...")
        //console.log(JSON.parse(JSON.stringify(stuInfo)))
        $.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: ctx+"/remoteOperate/registerInfo.action" ,//url
            data: JSON.parse(JSON.stringify(stuInfo)),
            success:function (data) {
                layer.msg(data.message)
                clearRegisterForm();
            },
            error:function () {
                layer.alert("报名失败");
            }
        })
        $("#sign-up-info").hide()
        $("#search-context").show()
    }
}
function updateBySelf() {
    var stuInfo = {
        classId : $("#stu-classId").val(),
        siIdNumber:$("#idNumber").val(),
        siId : $("#stu-id").val(),
        siName : $("#stu-name").val(),
        siPhone : $("#stu-phone").val(),
        email : $("#stu-email").val(),
        stuNumber : $("#stu-number").val(),
        firstUnitSelect : $("#host-units").val(),
        nextUnitSelect : $("#next-units").val(),
        regStatus : $("#stu-reg-status").val(),
        departmentName: $("#stu-dep").val(),
        nation: $("#nation").val()
    }
    // console.log(stuInfo)
    //正则验证是否符合身份证格式
    var patrn = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    var phonePattern = /^1[34578]\d{9}$/;
    if (stuInfo.regStatus == "未报名") {
        layer.msg("还未报名")
    }else if(""===stuInfo.siIdNumber){
        layer.msg("请输入身份证号码")
    }else if (!patrn.exec(stuInfo.siIdNumber)) {
        layer.msg("身份证格式不正确")
    }else if (stuInfo.siName === ""){
        layer.msg("姓名不能为空")
    }else if (stuInfo.siPhone === ""){
        layer.msg("电话不能为空")
    }else if (!phonePattern.exec(stuInfo.siPhone)){
        layer.msg("请输入正确的手机号")
    }else{
        layer.msg("修改中...")
        // console.log(stuInfo)
        //console.log(JSON.parse(JSON.stringify(stuInfo)))
        $.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: ctx+"/remoteOperate/updateBySelf.action" ,//url
            data: JSON.parse(JSON.stringify(stuInfo)),
            success:function (data) {
                layer.msg(data.message)
                clearRegisterForm();
            },
            error:function () {
                layer.alert("修改失败");
            }
        })
        $("#sign-up-info").hide()
        $("#search-context").show()
    }
}
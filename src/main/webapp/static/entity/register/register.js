$(function() {


});

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
                    $("#siId").prop("value", "");
                    $("#siName").prop("value", "");
                    $("#phoneNumber").prop("value", "");
                    $("#unitId").prop("value", "");
                    $("#ChanYeName").prop("value", "");
                    /* layer.confirm('没有学员信息，是否添加新学员？', {
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
                     });*/
                } else if (data.student != null) {
                    //学生id
                    $("#siId").prop("value", data.student.siId);
                    //学生姓名
                    $("#siName").prop("value", data.student.siName);
                    //电话号码
                    $("#phoneNumber").prop("value", data.student.phoneNumber);
                    //民族
                    /*console.log(data.student.ethnicGroup)*/
                    $("#ethnicGroup").val(data.student.ethnicGroup);
                    $("#national").val(data.student.ethnicGroup);

                    //单位
                   /* $("#unitId").val(data.student.unitName);*/
                      /*clickNode("haveclasstree","unitId","ChanYeName");
                      chooseNode2(data.student.siUnitId,"haveclasstree");*/
                      $("#unitId").prop("value", data.student.siUnitId);
                    var id=data.student.siUnitId;
                    if(typeof id != "undefined" && id != null && id != "") {
                        var  zTree = $.fn.zTree.getZTreeObj("haveclasstree");
                        var nodes = zTree.getNodes(); //可以获取所有的父节点
                        var node = zTree.getNodeByParam("id",id);//根据ID找到该节点
                        zTree.selectNode(node);//根据该节点选中
                        $("#ChanYeName").prop("value", node.name);
                    }

                    /*$("#serialNumber").prop("value", data.student.serialNumber);
                    $("#number").prop("value", data.student.serialNumber);*/
                    /*$("#unitId").prop("value",data.student.unit.areaName);*/
                    //学生照
                    var p =data.student.photo;
                    $("#photo").html("<img style='width: 200px;height: 200px;' src=" + p + " />");
                    /*           var siUId=data.student.siUnitId;
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
                               }*/


                    /*var html="<option value=''></option>";
                    for (var i = 0; i < data.unitList.length; i++) {
                        html+="<option value='"+data.unitList[i].areaId+"'>" + data.unitList[i].areaName + "</option>"
                    }
                    $("#unitId").html(html);
                    if(data.student.unit.sjareaId==0){
                        $("#unitId").find("option[value="+data.student.unit.areaId+"]").attr("selected",true);
                    }else{
                        alert(data.student.unit.areaId)
                        getSeCourse(data.student.unit.sjareaId);
                        $("#unitId").find("option[value="+data.student.unit.areaId+"]").attr("selected",true);
                        $("#unitId2").find("option[value="+data.student.unit.areaId+"]").attr("selected",true);
                    }*/


                }


            },
        });
    }
}

//选中节点
function chooseNode2(f,treeId) {
    /*alert(f)*/
    if(typeof f != "undefined" && f != null && f != ""){
        var treeObj = $.fn.zTree.getZTreeObj(treeId);//ztree树的ID
        var node = treeObj.getNodeByParam("id", f);//根据ID找到该节点
        treeObj.selectNode(node);//根据该节点选中
    }


}

//查询单位数据
function findUnit() {

    createTree("findUnit.action","#treeDemo");

    $("#modal").modal();

    //关闭模态框
    $(function() {
        $('#modal').on('hide.bs.modal', function() {
        })
    });
}

//单位下拉框改变事件
function getSeCourse(sjareaId){

    $.ajax({ //根据父级id查询子单位信息
        type: 'post',
        url: ctx+'/register/findUnit.action',
        data:{
            sjareaId:sjareaId,
        },
        dataType: "json", //返回的结果为json
        success: function(data) {
            var html="<option value=''></option>";
            if(data.unitList!=null){
                for (var i = 0; i < data.unitList.length; i++) {
                    html+="<option value='"+data.unitList[i].areaId+"'>" + data.unitList[i].areaName + "</option>"
                }
            }
            $("#unitId2").html(html);

        },
    });
};

/*//住宿标准下拉框改变事件
function getroomType(obj){
    var value = obj.options[obj.selectedIndex].value;
    //获取班级id
    var classId=$("#classId").val();
    if(typeof classId != "undefined" && classId != null && classId != ""){
        var s="";
        var v="";
        var va="";
        //标间
        if(value=="0"){
            //获取标间
            s=$("#interScaleFee").val();

            //获取就餐选中值
            v=$("#dining").find("option:selected").val();
            //获取住宿选中值
            va=$("#hotel").find("option:selected").val();

        }
        //单间
        if(value=="1"){
            //获取单间
            s=$("#singleRoomCharge").val();
            //获取就餐选中值
            var v=$("#dining").find("option:selected").val();
            //获取住宿选中值
            var va=$("#hotel").find("option:selected").val();

        }
        if(typeof s != "undefined" && s != null && s != "") {
            //住宿费用
            $("#scaleFee").prop("value", s);
            //获取培训天数
            var dayNum = $("#dayNum").val();
            //获取食宿增加天数
            var increaseDay = $("#increaseDay").val();
            var day=dayNum*1+increaseDay*1;
            //获取就餐费用
            var interScaleFee2 = $("#interScaleFee2").val();
            //获取住宿费用
            var scaleFee = $("#scaleFee").val();
            //获取培训费用
            var trainingExpense = $("#trainingExpense").val();
            //获取其它费用费用
            var otherCharges = $("#otherCharges").val();
            // （就餐费用+住宿费用）*培训天数+培训费+其它费用
            var totalFee=(parseInt(interScaleFee2)+parseInt(scaleFee))*day+parseInt(trainingExpense)+parseInt(otherCharges);
            $("#chargesTotal").prop("value", totalFee);
        }
    }

}*/

//就餐下拉框改变事件
function getDining(obj){
    var value = obj.options[obj.selectedIndex].value;
    //获取班级id
    var classId=$("#classId").val();
    if(typeof classId != "undefined" && classId != null && classId != "") {
        findDining(value);
        //console.log( "住宿费"+$("#scaleFeeTotal").val());
        //console.log( "就餐费"+$("#foodTotal").val());
        //食宿费(总住宿费用+总就餐费用)
        $("#ssf").prop("value",  $("#scaleFeeTotal").val()*1+$("#foodTotal").val()*1);
    }
}

//住宿下拉框改变事件
function getHotel(obj){
    var value = obj.options[obj.selectedIndex].value;
    //获取班级id
    var classId = $("#classId").val();
    if (typeof classId != "undefined" && classId != null && classId != "") {
        findHotel(value);
        //console.log( "住宿费"+$("#scaleFeeTotal").val());
        //console.log( "就餐费"+$("#foodTotal").val());
        //食宿费(总住宿费用+总就餐费用)
        $("#ssf").prop("value",  $("#scaleFeeTotal").val()*1+$("#foodTotal").val()*1);
    }

}

//获取就餐状况
function findDining(value){

       /* //获取培训天数
        var dayNum = $("#dayNum").val();
        //获取食宿增加天数
        var increaseDay = $("#increaseDay").val();
        //总天数
        var day=dayNum*1+increaseDay*1;*/

        //var day=(dayNum*1+increaseDay*1);

        //获取总费用
        var total = parseInt($("#chargesTotal").val());

        //不就餐
        if (value == "2") {

            //就餐费用
            $("#interScale").prop("value", $("#interScaleFee2").val());
            $("#interScaleFee2").prop("value", "0");
            //就餐地点
            $("#dPlace").prop("value", $("#diningPlace").val());
            $("#diningPlace").prop("value", "");

            //获取就餐费用
            /*var obj = $("#interScale").val();
            if (typeof obj == "undefined" || obj == null || obj == "") {
                obj = "0";
            }*/

            //总就餐费用
            $("#foodTotal2").prop("value", $("#foodTotal").val());
            $("#foodTotal").prop("value", "0");
            //总费用减去总就餐费
            $("#chargesTotal").prop("value", total - parseInt($("#foodTotal2").val()));




        } else {
            //就餐费用
            $("#interScaleFee2").prop("value", $("#interScale").val());
            //就餐地点
            $("#diningPlace").prop("value", $("#dPlace").val());
            //获取就餐费用
            var obj = $("#interScale").val();
            if (typeof obj == "undefined" || obj == null || obj == "") {
                obj = "0";
            }
            //总就餐费用
            $("#foodTotal").prop("value", $("#foodTotal2").val());
            //总费用加上总就餐费
            $("#chargesTotal").prop("value", total + parseInt($("#foodTotal2").val()));

        }

}

//获取住宿状况
function findHotel(value) {

       /* var s = "";
        var v = "";
        var va = "";
        //标间
        if (value == "0") {
            //获取标间
            s = $("#interScaleFee").val();

            //获取就餐选中值
            v = $("#dining").find("option:selected").val();
            //获取住宿选中值
            va = $("#hotel").find("option:selected").val();

            //住宿地点
            $("#hotelPlace").prop("value", $("#hPlace").val());

            //住宿天数
            $("#dayNum").prop("value", $("#dayNum1").val());

        }
        //单间
        if (value == "1") {
            //获取单间
            s = $("#singleRoomCharge").val();
            //获取就餐选中值
            var v = $("#dining").find("option:selected").val();
            //获取住宿选中值
            var va = $("#hotel").find("option:selected").val();

            //住宿地点
            $("#hotelPlace").prop("value", $("#hPlace").val());

            //住宿天数
            $("#dayNum").prop("value", $("#dayNum1").val());

        }
        //不住宿
        if (value == "2") {
            s = '0';
            //住宿费用
            $("#scaleHotel").prop("value", $("#scaleFee").val());
            /!* $("#scaleFee").prop("value", "0");*!/
            //住宿地点
            $("#hPlace").prop("value", $("#hotelPlace").val());
            $("#hotelPlace").prop("value", "");
            //住宿天数隐藏域
            //$("#dayNum1").prop("value", $("#dayNum").val());
            //住宿天数
            var dayNumber = $("#dayNum").val();
            $("#dayNum").prop("value", "0");
            $("#dayNum1").prop("value", dayNumber);

        }

        if (typeof s != "undefined" && s != null && s != "") {
            //住宿费用
            $("#scaleFee").prop("value", s);
            //获取培训天数
            var dayNum = $("#dayNum").val();
            //获取食宿增加天数
            var increaseDay = $("#increaseDay").val();
            /!*var day = dayNum * 1 + increaseDay * 1;*!/
            var day=(dayNum*1+increaseDay*1);
            //获取就餐费用
            var interScaleFee2 = $("#interScaleFee2").val();
            //获取住宿费用
            var scaleFee = $("#scaleFee").val();
            //获取培训费用
            var trainingExpense = $("#trainingExpense").val();
            //获取其它费用费用
            var otherCharges = $("#otherCharges").val();
            //获取就餐总费用
            var foodTotal = $("#foodTotal").val();
            // 就餐总费用+住宿费用*培训天数+培训费+其它费用
            var totalFee = parseInt(foodTotal) + parseInt(scaleFee) * day + parseInt(trainingExpense) + parseInt(otherCharges);
            $("#chargesTotal").prop("value", totalFee);

            //总住宿费用
            $("#scaleFeeTotal").prop("value",day*scaleFee);

        }*/

        //设置住宿费用
        var scaleFee = $('#hotel').find("option:selected").data("options");
        $("#scaleFee").prop("value",scaleFee);
        //不住宿
       if (value == "2") {
        //住宿地点
        $("#hPlace").prop("value", $("#hotelPlace").val());
        $("#hotelPlace").prop("value", "");
        //住宿
      }else{

           $("#hotelPlace").prop("value",$("#hPlace").val());
       }
    coutFee();



}

/*
//获取住宿状况
function findHotel(value){
    //获取班级id
    var classId=$("#classId").val();
    if(typeof classId != "undefined" && classId != null && classId != "") {
        //获取培训天数
        var dayNum = $("#dayNum").val();
        //获取食宿增加天数
        var increaseDay = $("#increaseDay").val();
        var day=dayNum*1+increaseDay*1;
        //获取总费用
        var total = parseInt($("#chargesTotal").val());

        //不住宿
        if (value == "2") {

            //住宿费用
            $("#scaleHotel").prop("value", $("#scaleFee").val());
            $("#scaleFee").prop("value", "0");
            //住宿地点
            $("#hPlace").prop("value", $("#hotelPlace").val());
            $("#hotelPlace").prop("value", "");

            //获取住宿费用
            var obj = $("#scaleHotel").val();
            if (typeof obj == "undefined" || obj == null || obj == "") {
                obj = "0";
            }
            //总费用扣除住宿费
            $("#chargesTotal").prop("value", total - parseInt(obj * day));

        } else {
            //住宿费用
            $("#scaleFee").prop("value", $("#scaleHotel").val());
            //住宿地点
            $("#hotelPlace").prop("value", $("#hPlace").val());
            //获取住宿费用
            var obj = $("#scaleHotel").val();
            if (typeof obj == "undefined" || obj == null || obj == "") {
                obj = "0";
            }
            //总费用加上住宿费
            $("#chargesTotal").prop("value", total + parseInt(obj * day));

        }
    }
}*/

//重新就算费用
function calculationFee(){
    //培训费
    var trainingExpense=$("#trainingExpense").val();
    //其它费用
    var otherCharges=$("#otherCharges").val();
    //总住宿费用
    var scaleFeeTotal=$("#scaleFeeTotal").val();
    //总就餐费用
    var foodTotal=$("#foodTotal").val();
    //总费用
    var total=trainingExpense*1+otherCharges*1+scaleFeeTotal*1+foodTotal*1;
    $("#chargesTotal").prop("value", total);

}

//首日餐经费统计
var firstDayFoundsStatistics =function () {
    console.log("统计首日餐")
    var row =$("#classInfo").bootstrapTable('getSelections');
    var startStopTime= $("#startStopTime").val();
    var diningPlace = $("#diningPlaceSelect option:selected").val();
    // console.log(row);
    var classIdArr = [];
    for (i in row){
        classIdArr[i] = row[i].classId;
    }

    // console.log(JSON.stringify(classIdArr));
    var temp = "<table class='table table-bordered table-hover table-responsive'>" +
        "<thead style='font-weight: bolder'>" +
        "<tr><td rowspan='2'>就餐地点</td><td colspan='3'>早餐</td><td colspan='3'>中餐</td><td colspan='3'>晚餐</td><td rowspan='2'>实到合计金额</td><td rowspan='2'>应到合计金额</td><td rowspan='2'>就餐率</td></tr>" +
        "<tr><td>应到人次</td><td>实到人次</td><td>实到金额</td><td>应到人次</td><td>实到人次</td><td>实到金额</td><td>应到人次</td><td>实到人次</td><td>实到金额</td></tr>"+
        "</thead>";
    var stuTemp = temp;
    var teachTemp = temp;
    if (classIdArr.length == 0){
        layer.alert("请选择至少一个班级")
    }else {
        $.ajax({
            url: ctx+"/diningFirstCount/stuFirstDayDiningStatistics.action",
            type:"get",
            dataType:"json",
            data:{"classIdArrString":(JSON.stringify(classIdArr)),"startStopTime":startStopTime,"diningPlace":diningPlace},
            async: false,
            success:function (data) {
                console.log(data);
                stuTemp+="<tbody>";
                for (i in data){
                    var totalEatCount = (parseInt(data[i].breakfastEatCount)+parseInt(data[i].lunchEatCount)+parseInt(data[i].dinnerEatCount));
                    var totalEatCountReal = (parseInt(data[i].breakfastEatCountReal)+parseInt(data[i].lunchEatCountReal)+parseInt(data[i].dinnerEatCountReal));
                    var diningRate = totalEatCount==0?("100%"):(Math.round(totalEatCountReal / totalEatCount * 10000) / 100.00 + "%");
                    stuTemp+="<tr><td>"+data[i].diningPlace+"</td>"+
                        "<td>"+data[i].breakfastEatCount+"</td>"+
                        "<td>"+data[i].breakfastEatCountReal+"</td>"+
                        "<td>"+data[i].breakfastEatMoneyReal+"</td>"+
                        "<td>"+data[i].lunchEatCount+"</td>"+
                        "<td>"+data[i].lunchEatCountReal+"</td>"+
                        "<td>"+data[i].lunchEatMoneyReal+"</td>"+
                        "<td>"+data[i].dinnerEatCount+"</td>"+
                        "<td>"+data[i].dinnerEatCountReal+"</td>"+
                        "<td>"+data[i].dinnerEatMoneyReal+"</td>"+
                        "<td>"+(parseInt(data[i].breakfastEatMoneyReal)+parseInt(data[i].lunchEatMoneyReal)+parseInt(data[i].dinnerEatMoneyReal))+"</td>"+
                        "<td>"+data[i].totalMoney+"</td>"+
                        "<td>"+diningRate+"</td>"+
                        "</tr>";
                }
                stuTemp+="</tbody>";
            },
            error:function () {
                layer.alert("查询班级首日就餐统计异常");
            }
        });
        $.ajax({
            url: ctx+"/diningFirstCount/teachFirstDayDiningStatistics.action",
            type:"get",
            dataType:"json",
            data:{"classIdArrString":(JSON.stringify(classIdArr)),"startStopTime":startStopTime,"diningPlace":diningPlace},
            async:false,
            success:function (data) {
                console.log(data);
                teachTemp+="<tbody>";
                for (i in data){
                    var totalEatCount = (parseInt(data[i].breakfastEatCount)+parseInt(data[i].lunchEatCount)+parseInt(data[i].dinnerEatCount));
                    var totalEatCountReal = (parseInt(data[i].breakfastEatCountReal)+parseInt(data[i].lunchEatCountReal)+parseInt(data[i].dinnerEatCountReal));
                    var diningRate = totalEatCount==0?("100%"):(Math.round(totalEatCountReal / totalEatCount * 10000) / 100.00 + "%");
                    teachTemp+="<tr><td>"+data[i].diningPlace+"</td>"+
                        "<td>"+data[i].breakfastEatCount+"</td>"+
                        "<td>"+data[i].breakfastEatCountReal+"</td>"+
                        "<td>"+data[i].breakfastEatMoneyReal+"</td>"+
                        "<td>"+data[i].lunchEatCount+"</td>"+
                        "<td>"+data[i].lunchEatCountReal+"</td>"+
                        "<td>"+data[i].lunchEatMoneyReal+"</td>"+
                        "<td>"+data[i].dinnerEatCount+"</td>"+
                        "<td>"+data[i].dinnerEatCountReal+"</td>"+
                        "<td>"+data[i].dinnerEatMoneyReal+"</td>"+
                        "<td>"+(parseInt(data[i].breakfastEatMoneyReal)+parseInt(data[i].lunchEatMoneyReal)+parseInt(data[i].dinnerEatMoneyReal))+"</td>"+
                        "<td>"+data[i].totalMoney+"</td>"+
                        "<td>"+diningRate+"</td>"+
                        "</tr>";
                }
                teachTemp+="</tbody>";
            },
            error:function () {
                layer.alert("查询教师首日就餐统计异常")
            }
        })
    }

    stuTemp +="</table>";
    teachTemp +="</table>";
    $("#stuDiningTitle").html("学员<span style='color: #ca4440'>首日</span>就餐统计");
    $("#stuDiningStatisticsTable").html(stuTemp);
    $("#teachDiningTitle").html("教师<span style='color: #ca4440'>首日</span>就餐统计");
    $("#teachDiningStatisticsTable").html(teachTemp);
};
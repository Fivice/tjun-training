$(function () {
    calenderInit();
    // timeRange();
    initDiningPlaceSelect();
    // diningTable();
    // 1.初始化Table
    var classInfoTable = new classInfoTableInit();
    classInfoTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});
//获取前一周时间段字符串
var timeRange = function(){
    var today = new Date();
    var endTime = today.Format('yyyy-MM-dd');
    var startTime = new Date((today.setDate(today.getDate()-6))).Format('yyyy-MM-dd');
    return startTime+" - "+endTime;
};
//年月范围选择
var calenderInit = function () {
    laydate.render({
        elem: '#startStopTime'
        ,theme: '#6CA6CD'
        ,value: timeRange()
        ,isInitValue: true
        ,range: true
    });
};
//清空就餐地点查询内容
function reset(){
    // $("input").prop("value","");
    $("#diningPlace").prop("value","");
}
var diningTable = function(timeRange){
    var timeArr,startTime,endTime,startTimeString,endTimeString,timeRangeArr=[],dayBetween;
    var weekArray = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    //不带参数时从页面取值
    if (timeRange == null){
        timeRange = $("#startStopTime").val();
    }
    timeArr = timeRange.split(' - ');
    startTimeString = timeArr[0];
    endTimeString = timeArr[1];
    // console.log(startTimeString+"?"+endTimeString);
    startTime = new Date(startTimeString);
    endTime = new Date(endTimeString);
    dayBetween = (endTime-startTime)/(24*60*60*1000)
    // console.log("时间间隔："+dayBetween);
    //将时间段内时间分成每一天初始化为数组
    for (var i =0;i<=dayBetween;i++){
        var day = new Date();
        timeRangeArr[i] = new Date(day.setDate(startTime.getDate()+i));
    }
    // console.log(timeRangeArr);
    //按照天数时间数组初始化出一个表格
    var temp = "<table class='table table-hover table-bordered table-striped'>";
    temp += "<thead><tr><th>日期</th><th>星期</th><th>早餐</th><th>中餐</th><th>晚餐</th></tr></thead>";
    temp += "<tbody>";
    for (j in timeRangeArr){
        temp +="<tr><td>"+timeRangeArr[j].Format('yyyy-MM-dd')+"</td><td>"+weekArray[timeRangeArr[j].getDay()]+"</td><td><input type='checkbox' checked/></td><td><input type='checkbox' checked/></td><td><input type='checkbox' checked/></td></tr>"
    }
    temp += "</tbody></table>";
    //将表格插入指定位置
    $("#diningTable").empty();
    $("#diningTable").append(temp);
};

//班级列表
var classInfoTableInit = function() {
    var classInfoTableInit = new Object();
    // 初始化Table
    classInfoTableInit.Init = function() {
        $('#classInfo')
            .bootstrapTable(
                {
                    url : ctx+'/diningDataStatistics/classInfo.action', // 请求后台的URL（*）
                    method : 'post', // 请求方式（*）
                    contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType : "json", // 数据类型
                    toolbar : '#toolbar', // 工具按钮用哪个容器
                    striped : true, // 是否显示行间隔色
                    cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination : true, // 是否显示分页（*）
                    sortable : true, // 是否启用排序
                    sortOrder : "asc", // 排序方式
                    queryParamsType : 'limit',
                    queryParams : classInfoTableInit.queryParams, // 传递参数（*）
                    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, // 初始化加载第一页，默认第一页
                    pageSize : 10, // 每页的记录行数（*）
                    pageList: [10, 20, 50, 120], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : false, // 是否显示所有的列
                    showRefresh : false, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : true, // 是否启用点击选中行
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "", // 每一行的唯一标识，一般为主键列
                    showToggle : false, // 是否显示详细视图和列表视图的切换按钮
                    cardView : false, // 是否显示详细视图
                    detailView : false, // 是否显示父子表
                    showExport : false, // 是否显示导出按钮
                    // exportDataType : "selected", //basic'导出当前页,
                    // 'all'导出全部, 'selected'导出选中项.
                    buttonsAlign : "right", // 按钮位置
                    // exportTypes:['excel','xlsx'], //导出文件类型
                    exportTypes : [ 'excel' ],
                    Icons : 'glyphicon-export',
                    exportOptions : {
                        ignoreColumn : [ 0, 0 ], // 忽略某一列的索引
                        fileName : '班级信息', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '班级信息',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        {checkbox: true, visible: true},
                        {title: '班级编号', field: 'classNumber'},
                        {title: '班级名称', field: 'className'},
                        {title: '地点', field: 'diningPlace'},
                        {title: '计划', field: 'plannedNumber'},
                        {title: '报到', field: 'registerNumber'}
                    ],
                    onLoadSuccess : function(data) {

//								layer.msg("数据加载完成");

                    },
                    onLoadError : function() {
                        layer.msg("数据加载失败！");
                    },

                    // 注册加载子表的事件。注意下这里的三个参数！
                    onExpandRow : function(index, row, $detail) {
                        oInit.InitSubTable(index, row, $detail);
                    },

                });
    };

    //得到查询的参数
    classInfoTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            startStopTime: $("#startStopTime").val(),
            diningPlace: $("#diningPlaceSelect option:selected").val()
        };
        // console.log(temp)
        return temp;
    };

    return classInfoTableInit;
};



var ButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};
//经费统计
var foundsStatistics =function () {
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
            url: ctx+"/diningDataStatistics/stuDiningDataStatistics.action",
            type:"get",
            dataType:"json",
            data:{"classIdArrString":(JSON.stringify(classIdArr)),"startStopTime":startStopTime,"diningPlace":diningPlace},
            async: false,
            success:function (data) {
                // console.log(data);
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
                layer.alert("查询班级就餐统计异常");
            }
        });
        $.ajax({
            url: ctx+"/diningDataStatistics/teachDiningDataStatistics.action",
            type:"get",
            dataType:"json",
            data:{"classIdArrString":(JSON.stringify(classIdArr)),"startStopTime":startStopTime,"diningPlace":diningPlace},
            async:false,
            success:function (data) {
                // console.log(data);
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
                layer.alert("查询教师就餐统计异常")
            }
        })
    }

    stuTemp +="</table>";
    teachTemp +="</table>";
    $("#stuDiningTitle").html("学员就餐统计");
    $("#stuDiningStatisticsTable").html(stuTemp);
    $("#teachDiningTitle").html("教师就餐统计");
    $("#teachDiningStatisticsTable").html(teachTemp);
};
//查询
function seach(){
    $("#classInfo").bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
}
var initDiningPlaceSelect = function () {
    var temp = "";
    $.ajax({
        url:ctx+"/diningDataStatistics/diningPlaceSelect.action",
        type:"get",
        dataType:"json",
        async:false,
        success:function (data) {
            // console.log(data);
            if (data.length!=0&&data[0]=="就餐管理员") {
                for (var i = 1; i < data.length; i++) {
                    temp+="<option value='"+data[i]+"'>"+data[i]+"</option>";
                }
            }else{
                temp+="<option value='全部地区'>全部</option>";
                for (j in data){
                    temp+="<option value='"+data[j]+"'>"+data[j]+"</option>";
                }
            }

        },
        error:function () {
            layer.msg("初始化就餐地点下拉选项失败")
        }
    });
    $("#diningPlaceSelect").html(temp);
};
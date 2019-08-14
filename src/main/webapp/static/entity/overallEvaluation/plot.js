$(function() {

/*var myChart = echarts.init(document.getElementById('main'));*/
    /*searchPlot("0",$("#query").val());*/

});

function plot(text,myChart,xData,sData){
    option = {
        title : {
            text: text,
            subtext: '',
            textStyle:{
                color:'#1c84c6'
            }
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : xData,
                /*axisLabel: {
                    textStyle: {
                        color: '#8B475D',//坐标值得具体的颜色

                    }
                }*/
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'',
                type:'bar',
                data:sData,
                itemStyle: {
                    normal: {
                        label: {
                            show: true, //开启显示
                            position: 'top', //在上方显示
                            textStyle: { //数值样式
                                color: '#1c84c6',
                                fontSize: 16
                            }
                        },
                        // 随机显示
                        color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}

                        // 定制显示（按顺序）
                        /*color: function(params) {
                            var colorList = ['#C33531','#EFE42A','#64BD3D','#EE9201','#29AAE3', '#B74AE5','#0AAF9F','#E89589','#16A085','#4A235A','#C39BD3 ','#F9E79F','#BA4A00','#ECF0F1','#616A6B','#EAF2F8','#4A235A','#3498DB' ];
                            return colorList[params.dataIndex]
                        }*/
                    },
                },
            }
        ]
    };

    myChart.setOption(option);

}

function searchPlot(type,query){
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: ctx+'/annualData/findTable.action',//url
        data: {
            query:query,
            year:$("#year").val()
        },
        success: function (dataObj) {
            var myChart = echarts.init(document.getElementById('main'));
            var date = new Date();
            date.getFullYear(); //获取完整的年份(4位)
            var xData = [];
            var sData = [];

                if (type == "0") {
                    $.each(dataObj, function (index, item) {
                        var month = item.annualDataDto.month;
                        if (query == "month"){
                            month+="月份";
                        }
                        var classNum = item.annualDataDto.classNum;
                        xData.push(month);
                        sData.push(classNum);
                    });
                    if (query == "month"){
                        plot(date.getFullYear() + "年度__班级数__月分布图:", myChart, xData, sData);
                    }else if(query == "type"){
                        plot(date.getFullYear() + "年度__班级数__培训类型分布图:", myChart, xData, sData);
                    }
                }
            if (type == "1") {
                $.each(dataObj, function (index, item) {
                    var month = item.annualDataDto.month;
                    var classNum = item.annualDataDto.studentNum;
                    if (query == "month"){
                        month+="月份";
                    }
                    xData.push(month);
                    sData.push(classNum);
                });
                if (query == "month"){
                    plot(date.getFullYear() + "年度__培训人数__月分布图:", myChart, xData, sData);
                }else if(query == "type"){
                    plot(date.getFullYear() + "年度__培训人数__培训类型分布图:", myChart, xData, sData);
                }

            }
            if (type == "2") {
                $.each(dataObj, function (index, item) {
                    var month = item.annualDataDto.month;
                    var classNum = item.annualDataDto.dayNum;
                    if (query == "month"){
                        month+="月份";
                    }
                    xData.push(month);
                    sData.push(classNum);
                });
                if (query == "month"){
                    plot(date.getFullYear() + "年度__培训天数__月分布图:", myChart, xData, sData);
                }else if(query == "type"){
                    plot(date.getFullYear() + "年度__培训天数__培训类型分布图:", myChart, xData, sData);
                }

            }
            if (type == "3") {
                $.each(dataObj, function (index, item) {
                    var month = item.annualDataDto.month;
                    var classNum = item.annualDataDto.trainingExpense;
                    if (query == "month"){
                        month+="月份";
                    }
                    xData.push(month);
                    sData.push(classNum);
                });
                if (query == "month"){
                    plot(date.getFullYear() + "年度__培训费__月分布图:", myChart, xData, sData);
                }else if(query == "type"){
                    plot(date.getFullYear() + "年度__培训费__培训类型分布图:", myChart, xData, sData);
                }

            }
            if (type == "4") {
                $.each(dataObj, function (index, item) {
                    var month = item.annualDataDto.month;
                    var classNum = item.annualDataDto.scaleFeeTotal;
                    if (query == "month"){
                        month+="月份";
                    }
                    xData.push(month);
                    sData.push(classNum);
                });
                if (query == "month"){
                    plot(date.getFullYear() + "年度__食宿费__月分布图:", myChart, xData, sData);
                }else if(query == "type"){
                    plot(date.getFullYear() + "年度__食宿费__培训类型分布图:", myChart, xData, sData);
                }

            }
            if (type == "5") {
                $.each(dataObj, function (index, item) {
                    var month = item.annualDataDto.month;
                    var classNum = item.annualDataDto.otherCharges;
                    if (query == "month"){
                        month+="月份";
                    }
                    xData.push(month);
                    sData.push(classNum);
                });
                if (query == "month"){
                    plot(date.getFullYear() + "年度__统收其它费__月分布图:", myChart, xData, sData);
                }else if(query == "type"){
                    plot(date.getFullYear() + "年度__统收其它费__培训类型分布图:", myChart, xData, sData);
                }

            }
            if (type == "6") {
                $.each(dataObj, function (index, item) {
                    var month = item.annualDataDto.month;
                    var classNum = item.annualDataDto.foodTotal;
                    if (query == "month"){
                        month+="月份";
                    }
                    xData.push(month);
                    sData.push(classNum);
                });
                if (query == "month"){
                    plot(date.getFullYear() + "年度__就餐费__月分布图:", myChart, xData, sData);
                }else if(query == "type"){
                    plot(date.getFullYear() + "年度__就餐费__培训类型分布图:", myChart, xData, sData);
                }

            }
            if (type == "7") {
                $.each(dataObj, function (index, item) {
                    var month = item.annualDataDto.month;
                    var classNum = item.annualDataDto.balance;
                    if (query == "month"){
                        month+="月份";
                    }
                    xData.push(month);
                    sData.push(classNum);
                });
                if (query == "month"){
                    plot(date.getFullYear() + "年度__结余__月分布图:", myChart, xData, sData);
                }else if(query == "type"){
                    plot(date.getFullYear() + "年度__结余__培训类型分布图:", myChart, xData, sData);
                }

            }

        }
    });
}
//月份的下拉框改变事件
function seachPlot(obj){
    //alert("22222")
    var value = obj.options[obj.selectedIndex].value;
    //$("#table").bootstrapTable('refresh');
    /*searchPlot(value,$("#query").val());*/
    findshju();
}
//计划的下拉框改变事件
function findByPlan(obj){
    var value = obj.options[obj.selectedIndex].value;
    $("#table").bootstrapTable('refresh');
}


function findByType(obj){
    var value = obj.options[obj.selectedIndex].value;
    var c=$('#testSelect option:selected') .val();//选中的值
    //按月统计
    if(value=="0"){
        $("#query").prop("value","month");
        $("#title").prop("value","月份");
        $("#table").bootstrapTable('refresh');
       /* searchPlot(c,$("#query").val());*/
    }
    //按类型统计
    if(value=="1"){
        $("#query").prop("value","type");
        $("#title").prop("value","培训类型");
        $("#table").bootstrapTable('refresh');
       /* searchPlot(c,$("#query").val());*/
    }
}

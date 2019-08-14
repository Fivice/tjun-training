
$(function() {
    var date=new Date;
    var year=date.getFullYear();
    $("#year").prop("value",year);

    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});

//
var TableInit = function() {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function() {
        $('#table')
            .bootstrapTable(
                {
                    url : ctx+'/annualData/findTable.action', // 请求后台的URL（*）
                    method : 'post', // 请求方式（*）
                    contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType : "json", // 数据类型
                    toolbar : '#toolbar', // 工具按钮用哪个容器
                    striped : true, // 是否显示行间隔色
                    cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination : true, // 是否显示分页（*）
                    sortable : true, // 是否启用排序
                    sortOrder : "asc", // 排序方式
                    queryParamsType : '',
                    queryParams : oTableInit.queryParams, // 传递参数（*）
                    /*
                     * queryParams: function (params)
                     * {//自定义参数，这里的参数是传给后台的，我这是是分页用的 return
                     * {//这里的params是table提供的 pageSize : params.limit,
                     * //每一页的数据行数，默认是上面设置的5(pageSize) pageNumber :
                     * params.offset/params.limit+1,
                     * //当前页面,默认是上面设置的1(pageNumber) }; },
                     */
                    sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, // 初始化加载第一页，默认第一页
                    pageSize : 20, // 每页的记录行数（*）
                    pageList: [40, 50, 120], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : false, // 是否显示所有的列
                    showRefresh : false, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : false, // 是否启用点击选中行
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
                        fileName : '数据导出', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '年度数据分析',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        {title: "", field: 'annualDataDto.month'},
                        {title: '班级数', field: 'annualDataDto.classNum'},
                        {title: '培训人数', field: 'annualDataDto.studentNum'},
                        {title: '培训天数', field: 'annualDataDto.dayNum'},
                        {
                            title: '人天数', field: '', formatter: function (value, row, index) {
                                return row.annualDataDto.studentNum * row.annualDataDto.dayNum;
                            }
                        },
                        {title: '培训费', field: 'annualDataDto.trainingExpense'},
                        {title: '住宿费', field: 'annualDataDto.scaleFeeTotal'},
                        {title: '其它费用', field: 'annualDataDto.otherCharges'},
                        {title: '就餐费(学员)', field: 'annualDataDto.foodTotal'},
                        {title: '就餐结余', field: 'annualDataDto.balance'},

                    ],
                    onLoadSuccess : function(data) {

                        findShuju();

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
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            query:$("#query").val(),
            year:$("#year").val(),
            plan:$("#plan").val(),
        };
        return temp;
    };

    return oTableInit;
};

var ButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};

function findShuju() {

    var title=$("#title").val();
    /*var valueTd=document .getElementById ("table").rows [0].cells[0];*/
    $(".th-inner ").eq(0).html(title);
    /*valueTd.html(title);*/
    var type=$('#testSelect option:selected') .val();//选中的值
    var query=$("#query").val();
    var year=$("#year").val();
    var myChart = echarts.init(document.getElementById('main'));
    /*var date = new Date();
    date.getFullYear(); //获取完整的年份(4位)*/
    var xData = [];
    var sData = [];
    var dataObj = $("#table").bootstrapTable('getData');//获取表格的所有内容行
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
            plot(year + "年度__班级数__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__班级数__培训类型分布图:", myChart, xData, sData);
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
            plot(year + "年度__培训人数__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__培训人数__培训类型分布图:", myChart, xData, sData);
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
            plot(year + "年度__培训天数__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__培训天数__培训类型分布图:", myChart, xData, sData);
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
            plot(year + "年度__培训费__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__培训费__培训类型分布图:", myChart, xData, sData);
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
            plot(year + "年度__食宿费__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__食宿费__培训类型分布图:", myChart, xData, sData);
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
            plot(year + "年度__统收其它费__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__统收其它费__培训类型分布图:", myChart, xData, sData);
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
            plot(year + "年度__就餐费__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__就餐费__培训类型分布图:", myChart, xData, sData);
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
            plot(year + "年度__结余__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__结余__培训类型分布图:", myChart, xData, sData);
        }

    }
    if (type == "8") {
        $.each(dataObj, function (index, item) {
            var month = item.annualDataDto.month;
            if (query == "month"){
                month+="月份";
            }
            var classNum = (item.annualDataDto.studentNum)*(item.annualDataDto.dayNum);
            xData.push(month);
            sData.push(classNum);
        });
        if (query == "month"){
            plot(year + "年度__人天数__月分布图:", myChart, xData, sData);
        }else if(query == "type"){
            plot(year + "年度__人天数数__培训类型分布图:", myChart, xData, sData);
        }
    }

}
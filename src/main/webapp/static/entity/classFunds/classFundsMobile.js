var ctx;

$(function() {
    ctx = getRootPath();
    initLayuiDate(initClassFundsTable);
});
//年月范围选择
var initLayuiDate = function (callback) {
    callback();
    laydate.render({
        elem: '#startStopTime'
        ,type: 'date'
        // ,theme: '#6CA6CD'
        ,range: '至' //或 range: '~' 来自定义分割字符
        ,done: function(value, date, endDate){
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
            refreshTable();
        }
        // ,change: function(value, date, endDate){
        //     console.log(value); //得到日期生成的值，如：2017-08-18
        //     console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
        //     console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
        //     refreshTable();
        // }

    });

}
var initClassFundsTable = function () {

    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

}
function getRootPath()
{
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    return window.location.protocol + '//' + window.location.host + '/'+ webName;
}
//
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: 'findTable.action',  //请求后台的URL（*）
            method: 'post',                       	//请求方式（*）
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",						//数据类型
            toolbar: '#toolbar',                 	//工具按钮用哪个容器
            striped: true,                      	//是否显示行间隔色
            cache: false,                       	//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   	//是否显示分页（*）
            sortable: true,                    	    //是否启用排序
            sortOrder: "asc",                   	//排序方式
            queryParamsType: 'limit',
            queryParams: oTableInit.queryParams, 	//传递参数（*）
            sidePagination: "server",           	//分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       	//初始化加载第一页，默认第一页
            pageSize: 10,                       	    //每页的记录行数（*）
            pageList: [10, 20, 50],        	//可供选择的每页的行数（*）

            showColumns: false,                  	//是否显示所有的列
            showRefresh: false,                  	//是否显示刷新按钮
            minimumCountColumns: 2,             	//最少允许的列数
            clickToSelect: true,                	//是否启用点击选中行
            //height: 500,                        	//行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                   //每一行的唯一标识，一般为主键列
            showToggle:false,                    	//是否显示详细视图和列表视图的切换按钮
            cardView: false,

            detailView: false,                  		//是否显示父子表
            showExport: false,  //是否显示导出按钮
            exportDataType : "basic", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
            buttonsAlign:"right",  //按钮位置

            exportTypes:['excel'],
            Icons:'glyphicon-export',
            exportOptions:{
                // ignoreColumn: [0,1],  //忽略某一列的索引
                fileName: '班级经费管理',  //文件名称设置
                worksheetName: 'sheet1',  //表格工作区名称
                tableName: '',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                /*onMsoNumberFormat: DoOnMsoNumberFormat  */
            },
            columns: [
                [
                    // {checkbox: true},
                    //
                    {title: '班级编号', field: 'classInfo.classNumber', visible:true, valign:"middle",align:"center",colspan: 1,
                        rowspan: 2,footerFormatter: function () {
                            return "合计";
                        }},
                    {title: '班级名称', field: 'classInfo.className',  valign:"middle",align:"center",colspan: 1,rowspan: 2},
                    {
                        title: "前台",
                        valign:"middle",
                        align:"center",
                        colspan: 4,
                        rowspan: 1
                    },
                    {
                        title: "统收",
                        valign:"middle",
                        align:"center",
                        colspan: 4,
                        rowspan: 1
                    },
                    {title: '教师实际就餐费', field: 'teacherDinding', valign:"middle",align:"center", colspan: 1, rowspan: 2,
                        formatter:function(value){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    }
                ],
                [
                    {title: '培训费', field: 'trainingExpense', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    },
                    {title: '住宿费', field: 'scaleFee', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    },
                    {title: '就餐费', field: 'foodTotal2', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    },
                    {title: '其它费用', field: 'otherCharges', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    },

                    {title: '培训费', field: 'recordChange.trainingFeeCollection', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    },
                    {title: '住宿费', field: 'recordChange.hotelExpenseCollection', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    },
                    {title: '就餐费', field: 'recordChange.diningFeeCollection', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    },
                    {title: '其它费用', field: 'recordChange.otherExpensesCollection',valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                return value
                            }
                            return value;
                        }
                    }

                ]
            ],//自定义方法，添加详情按钮
            // 注册加载子表的事件。注意下这里的三个参数！
            onExpandRow : function(index, row, $detail) {
                oInit.InitSubTable(index, row, $detail);
            },
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var p = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            startStopTime:$("#startStopTime").val(),
            classNumber:$("#classNumber").val(),
            className:$("#className").val(),
            trainingType:$("#trainingType").val(),
            plan:$("#plan").val()
        };
        return p;
    };
    return oTableInit;
};

Date.prototype.Format = function(format){
    var o = {
        "M+" : this.getMonth()+1, //month 
        "d+" : this.getDate(), //day 
        "h+" : this.getHours(), //hour 
        "m+" : this.getMinutes(), //minute 
        "s+" : this.getSeconds(), //second 
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
        "S" : this.getMilliseconds() //millisecond 
    }
    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
}

// function opera (value, row, index) {
//     var result = "";
//     var date = new Date();
//     var time = date.Format("yyyy-MM-dd");
//     var endTime = row.classInfo.startStopTime.split(" 至 ")[1];
//     if(row.roleValue == "超级管理员"){
//         var a = ""
//         if (endTime > time){
//             // alert("111")
//             a += "<button type=\"button\" disabled class=\"btn btn-info\"  title='班级没有结束，不可录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
//         }else {
//             var b =""
//             if (row.recordChange == undefined) {
//                 // var id = value.id;
//                 b += "<button type=\"button\" class=\"btn btn-info\" onclick=\"recordChange('" + row.classInfo.id + "')\" title='录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
//             } else {
//                 b += "<button type=\"button\" class=\"btn btn-info\" onclick=\"recordChange1('" + row.classInfo.id + "','" + row.recordChange.id + "')\" title='录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
//             }
//             a = b;
//         }
//         result = a;
//     }else {
//         var a = ""
//         if (endTime > time){
//             // alert("111")
//             a += "<button type=\"button\" disabled class=\"btn btn-info\"  title='班级没有结束，不可录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
//         }else {
//             var b =""
//             if (row.recordChange == undefined) {
//                 // var id = value.id;
//                 b += "<button type=\"button\" class=\"btn btn-info\" onclick=\"recordChange('" + row.classInfo.id + "')\" title='录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
//             } else {
//                 b += "<button type=\"button\" class=\"btn btn-info\" disabled onclick=\"recordChange1('" + row.classInfo.id + "','" + row.recordChange.id + "')\" title='已录改，不能进行第二次录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
//             }
//             a = b;
//         }
//         result = a;
//     }
//
//     return result;
// }
//
// //录改(修改之前已有的录改表)
// function recordChange1(classId,id){
//     window.location.href=ctx +'/classFunds/recordChange.action?id='+id+'&classId='+classId;
// }
//
// //录改(新增录改表)
// function recordChange(classId){
//     window.location.href=ctx +'/classFunds/recordChange2.action?classId='+classId;
// }



// function operate (value, row, index) {
//     var result = "";
//     result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"details('" + row.classInfo.id + "')\" title='详情'><i class=\"fa fa-chevron-circle-right\"></i></button>";
//     return result;
// } ;

// //详情
// function details(id){
//     window.location.href=ctx +'/classFunds/details.action?id='+id;
// }
//
// //班级详情
// function EditViewById(id){
//     window.location.href=ctx +'/classInfo/form.action?query=query&&id='+id;
// }

var ButtonInit = function() {
    var oInit = new Object();

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};
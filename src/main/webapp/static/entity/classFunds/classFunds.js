//项目根目录
var path = $("#contextPath").val();
$(document).ready(function () {
    //初始化Table
    var oTable = new TableInit();
    oTable.Init();
});
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
            // search: true,                       	//是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            // strictSearch: true,					    //启用全匹配搜索，否则为模糊搜索
            // searchOnEnterKey: false,				//按回车触发搜索方法，否则自动触发搜索方法
            showColumns: false,                  	//是否显示所有的列
            showRefresh: true,                  	//是否显示刷新按钮
            minimumCountColumns: 2,             	//最少允许的列数
            clickToSelect: true,                	//是否启用点击选中行
            //height: 500,                        	//行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                   //每一行的唯一标识，一般为主键列
            showToggle:true,                    	//是否显示详细视图和列表视图的切换按钮
            cardView: false,
            // showFooter:true ,//是否显示详细视图
            detailView: false,                  		//是否显示父子表
            showExport: true,  //是否显示导出按钮
            exportDataType : "basic", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
            buttonsAlign:"right",  //按钮位置
            // exportTypes:['excel','xlsx'],  //导出文件类型
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
                {title: '班级名称', field: 'classInfo',  valign:"middle",align:"center",colspan: 1,rowspan: 2,formatter:function(value){
                        var temp = '<a style="color: #6CA6CD" href="javascript:void(0)" onclick=EditViewById(' + value.id + ');>' + value.className + '</a>';
                        return temp;
                    }
                    },
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
                                value
                            }
                            return value;
                        }
                    },
                    {title: '录改', colspan: 1, rowspan: 2, valign:"middle",align:"center",field: 'Button',
                        formatter: opera//自定义方法，添加详情按钮
                    },
                    { field: 'Button',colspan: 1, rowspan: 2, valign:"middle",align:"center",title: '详情',
                        formatter: operate//自定义方法，添加详情按钮
                    }
                    ],
                [
                {title: '培训费', field: 'trainingExpense', valign:"middle",align:"center", formatter:function(value, row, index){
                    if(value==null){
                        value="0.00"
                    }else {
                        value
                    }
                    return value;
                    }
                },
                {title: '住宿费', field: 'scaleFee', valign:"middle",align:"center", formatter:function(value, row, index){
                        if(value==null){
                            value="0.00"
                        }else {
                            value
                        }
                        return value;
                    }
                },
                {title: '就餐费', field: 'foodTotal2', valign:"middle",align:"center", formatter:function(value, row, index){
                        if(value==null){
                            value="0.00"
                        }else {
                            value
                        }
                        return value;
                    }
                },
                    {title: '其它费用', field: 'otherCharges', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                value
                            }
                            return value;
                        }
                    },

                    {title: '培训费', field: 'recordChange.trainingFeeCollection', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                value
                            }
                            return value;
                        }
                    },
                    {title: '住宿费', field: 'recordChange.hotelExpenseCollection', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                value
                            }
                            return value;
                        }
                    },
                    {title: '就餐费', field: 'recordChange.diningFeeCollection', valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                value
                            }
                            return value;
                        }
                    },
                    {title: '其它费用', field: 'recordChange.otherExpensesCollection',valign:"middle",align:"center", formatter:function(value, row, index){
                            if(value==null){
                                value="0.00"
                            }else {
                                value
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
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            startStopTime:$("#startStopTime").val(),
            classNumber:$("#classNumber").val(),
            className:$("#className").val(),
            trainingType:$("#trainingType").val(),
            plan:$("#plan").val()
        };
        return temp;
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

function opera (value, row, index) {
    var result = "";
    var date = new Date();
    var time = date.Format("yyyy-MM-dd");
    var endTime = row.classInfo.startStopTime.split(" 至 ")[1];
    if(row.roleValue == "超级管理员"){
        var a = ""
        if (endTime > time){
            // alert("111")
            a += "<button type=\"button\" disabled class=\"btn btn-info\"  title='班级没有结束，不可录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
        }else {
            var b =""
            if (row.recordChange == undefined) {
                // var id = value.id;
                b += "<button type=\"button\" class=\"btn btn-info\" onclick=\"recordChange('" + row.classInfo.id + "')\" title='录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
            } else {
                b += "<button type=\"button\" class=\"btn btn-info\" onclick=\"recordChange1('" + row.classInfo.id + "','" + row.recordChange.id + "')\" title='录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
            }
            a = b;
        }
        result = a;
    }else {
        var a = ""
            if (endTime > time){
                // alert("111")
                a += "<button type=\"button\" disabled class=\"btn btn-info\"  title='班级没有结束，不可录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
            }else {
                var b =""
                if (row.recordChange == undefined) {
                    // var id = value.id;
                    b += "<button type=\"button\" class=\"btn btn-info\" onclick=\"recordChange('" + row.classInfo.id + "')\" title='录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
                } else {
                    b += "<button type=\"button\" class=\"btn btn-info\" disabled onclick=\"recordChange1('" + row.classInfo.id + "','" + row.recordChange.id + "')\" title='已录改，不能进行第二次录改'><i class=\"fa fa-pencil-square-o\"></i></button>";
                }
                a = b;
        }
        result = a;
    }

    return result;
}

//录改(修改之前已有的录改表)
function recordChange1(classId,id){
    window.location.href=ctx +'/classFunds/recordChange.action?id='+id+'&classId='+classId;
}

//录改(新增录改表)
function recordChange(classId){
    window.location.href=ctx +'/classFunds/recordChange2.action?classId='+classId;
}



function operate (value, row, index) {
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"details('" + row.classInfo.id + "')\" title='详情'><i class=\"fa fa-chevron-circle-right\"></i></button>";
    return result;
} ;

//详情
function details(id){
    window.location.href=ctx +'/classFunds/details.action?id='+id;
}

//班级详情
function EditViewById(id){
    window.location.href=ctx +'/classInfo/form.action?query=query&&id='+id;
}

var ButtonInit = function() {
    var oInit = new Object();

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};

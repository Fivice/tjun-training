$(function() {
    /*var time = getyymm();
    $("#startStopTime").prop("value",time);*/

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
                    url : ctx+'/classInfo/findTable.action', // 请求后台的URL（*）
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
                    queryParams : oTableInit.queryParams, // 传递参数（*）
                    /*
                     * queryParams: function (params)
                     * {//自定义参数，这里的参数是传给后台的，我这是是分页用的 return
                     * {//这里的params是table提供的 pageSize : params.limit,
                     * //每一页的数据行数，默认是上面设置的5(pageSize) pageNumber :
                     * params.offset/params.limit+1,
                     * //当前页面,默认是上面设置的1(pageNumber) }; },
                     */
                    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, // 初始化加载第一页，默认第一页
                    pageSize : 10, // 每页的记录行数（*）
                    pageList: [10, 20, 50, 120], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : true, // 是否显示所有的列
                    showRefresh : true, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : true, // 是否启用点击选中行
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "", // 每一行的唯一标识，一般为主键列
                    showToggle : true, // 是否显示详细视图和列表视图的切换按钮
                    cardView : false, // 是否显示详细视图
                    detailView : false, // 是否显示父子表
                    showExport : true, // 是否显示导出按钮
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
                        tableName : '班级信息表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        // {checkbox: true, visible: true},
                        {title: '班级编号', field: 'classNumber',formatter:function(value, row, index){
                                // var temp = '<a style="color: #6CA6CD" href="../signUpManager/studentRegisterBySelf.action?classId='+row.id+'">' + row.classNumber + '</a>';
                            var temp = '<a style="color: #6CA6CD" href="../classInfo/QRcode.action?classId='+row.id+'">' + row.classNumber + '</a>';
                                return temp;
                            }},
                        {title: '班级名称', field: 'className', formatter:function(value, row, index){
                                var temp = '<a style="color: #6CA6CD" href="form.action?query=query&&id='+row.id+'">' + row.className + '</a>';
                                return temp;
                            }},
                        {title: '办班时间', field: 'startStopTime'},
                        {title: '报到地点', field: 'regPlace'},
                        {title: '计划', field: 'plan',formatter : function(value, row, index) {
                                if (value == 0) {
                                    return "计划内";
                                }
                                if (value == 1) {
                                    return "计划外";
                                }
                                if (value == 2) {
                                    return "非培训班";
                                }
                                return "";
                            }},
                        {title: '主办单位', field: 'hostUnit'},
                        {title: '班主任', field: 'teacherName',},
                        {title: '评价', field: 'evaluate',formatter : function(value, row, index) {
                                if (value == 0) {
                                    return "参评";
                                }
                                if (value == 1) {
                                    return "不参评";
                                }
                                return "";
                            }},
                        {title: '日程安排', field: 'schedulingId',
                            formatter: opera//自定义方法，添加详情按钮
                        },
                        { field: 'Button', title: '就餐安排',
                            formatter: operate//自定义方法，添加详情按钮
                        },
                        /*{title: '状态', field: 'status',formatter : function(value, row, index) {
                                if (value == '0') {
                                    return "<span class='label label-warning'>关闭</span>";
                                }
                                if (value == '1') {
                                    return "<span class='label label-primary'>开放</span>";
                                }
                                return "";
                            }},*/
                        {title: '报到时间', field: 'entryStartTime',formatter : function(value, row, index) {
                            if (value.substring(11,13) == '00') {
                                return value.substring(0,10)+" 上午";
                            }
                            if (value.substring(11,13) == '12') {
                                return value.substring(0,10)+" 下午";
                            }
                            return "";
                        }},

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
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            plan:$("#plan").val(),
            classNumber:$("#classNumber").val(),
            startStopTime:$("#startStopTime").val(),
            className:$("#className").val(),
            teacherName:$("#teacherName").val(),
            regPlace:$('#regPlace').val(),
            entryStartTime:$('#entryStartTime').val()
        };
        return temp;
    };

    return oTableInit;
};


function operateFormatter (value, row, index) {
    var id = value;
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"EditViewById('" + row.id + "')\" title='查看详情'><i class=\"fa fa-chevron-circle-right\"></i></button>";
    return result;
} ;

function operate (value, row, index) {
    var id = value;
    var result1 = "";
    if(row.diningPlace==""){
        result1 += "<button type=\"button\" class=\"btn btn-danger\" disabled title='班级没有就餐地点，不能安排就餐'><i class=\"fa fa-map-o\"></i></button>";
    }else {
        // alert(aaa)
        $.ajax({
            type: 'POST',
            url: 'classDining.action',//发送请求
            data: {
                id: row.id
            },
            async:false,
            dataType: "json",
            success: function (result) {
                if (result.code == 1) {
                    // alert(result)
                    result1 += "<button type=\"button\" class=\"btn btn-info\" onclick=\"repast('" + row.id + "')\" title='修改就餐安排'><i class=\"fa fa-map-o\"></i></button>";

                }else {
                    result1 += "<button type=\"button\" class=\"btn btn-primary\" onclick=\"repast('" + row.id + "')\" title='就餐安排'><i class=\"fa fa-map-o\"></i></button>";
                }
            }
        });
        // result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"repast('" + row.id + "')\" title='就餐安排'><i class=\"fa fa-map-o\"></i></button>";
    }
    return result1;
} ;

//就餐安排
function repast(id){
        window.location.href = ctx + '/classDining/view.action?id=' + id;
}

function opera (value, row, index) {
    var id = value;
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"repas('" + row.id + "')\" title='日程安排'><i class=\"fa fa-envira\"></i></button>";
    return result;
} ;

var ButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};


// //就餐
// function repast(id){
//     window.location.href=ctx +'/classInfo/form.action?id='+id;
// }

//日程
function repas(id){
    window.location.href=ctx +'/scheduling/view.action?id='+id;
}

/*
//获取当前年月
function getyymm(){
    //创建日期对象
    var date=new Date;
    //获取年份
    var yy=date.getFullYear();
    //获取月份
    var mm=date.getMonth()+1;
    //如果月份小于10 前面加0
    mm=(mm<10 ? "0"+mm:mm);
    //返回日期
    return (yy.toString()+"-"+mm.toString());
}*/

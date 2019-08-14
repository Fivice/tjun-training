$(function() {
    initCondition();
    calenderInit();
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
                    url : ctx+'/signUpManager/findTable.action', // 请求后台的URL（*）
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
                        fileName : '班级信息表', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '班级信息表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        {checkbox: true, visible: true},
                        {title: '班级id', field:'id', visible: false},
                        {title: '班级编号', field: 'classNumber',sortable:true,formatter:function(value, row, index){
                                var temp = '<a style="color: #6CA6CD" href="../classInfo/QRcode.action?classId='+row.id+'">' + row.classId + '</a>';
                                return temp;
                            }},
                        {title: '班级名称', field: 'className', formatter:function(value, row, index){
                                var temp = '<a style="color: #6CA6CD" href="../classInfo/form.action?query=query&&id='+row.id+'">' + row.className + '</a>';
                                return temp;
                            }},
                        {title: '开班时间段', field: 'startStopTime',sortable:true},
                        {title: '报到区域', field: 'regPlace',sortable:true},

                        {title: '班主任', field: 'teacherName',sortable:true/*, formatter:function(value, row, index){
                                var obj =row.userId;
                                if (typeof obj == "undefined" || obj == null || obj == "") {
                                    var temp =row.teacherName;
                                }else{
                                    var temp = '<a style="color: #6CA6CD" onclick="searchTeacher('+obj+')">' + row.teacherName + '</a>';
                                }

                                return temp;
                            }*/
                        },
                        {title: '报到', field: 'signUpCount', formatter:function(value, row, index){
                                var temp = '<a style="color: #6CA6CD" href="signUpStudent.action?id='+row.id+'">' + row.signUpCount +'&nbsp<i class="fa fa-user"></i></a>';
                                return temp;
                            }},
                        {title: '计划人数',field: 'signUpCountPlan'},
                        {title: '参培率',field: '',formatter:function (value, row, index) {
                                var signUpCount = row.signUpCount;
                                var signUpCountPlan = row.signUpCountPlan;
                                if (isNaN(signUpCount)||isNaN(signUpCountPlan)){
                                    return "-";
                                } else {
                                    return signUpCountPlan<=0?"0%":(Math.round(signUpCount/signUpCountPlan*10000)/100.00)+"%"
                                }
                            }},
                        {title: '主办单位', field: 'hostUnit',sortable:true},
                        {title: '日程安排',
                            formatter: opera//自定义方法，添加详情按钮
                        },
                        {title: '培训天数',field: 'days'},
                        // {title: '状态', field: 'status',formatter : function(value, row, index) {
                        //         if (value == '0') {
                        //             return "<span class='label label-warning'>关闭</span>";
                        //         }
                        //         if (value == '1') {
                        //             return "<span class='label label-primary'>开放</span>";
                        //         }
                        //         return "";
                        //     }},

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
            classNumber:$("#classNumber").val(),
            startStopTime:$("#startStopTime").val(),
            className:$("#className").val(),
            teacherName:$("#teacherName").val(),
            entryStartTime:$("#entryStartTime").val(),
            orderName: params.sort,
            orderBy: params.order

        };
        // console.log(temp)
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
function opera (value, row, index) {
    var id = value;
    var result = "";
    result += "<span type=\"button\" style='margin-bottom: 0px' class=\"btn btn-info btn-xs\" onclick=\"repas('" + row.id + "')\" title='日程安排'><i class=\"fa fa-calendar\"></i> &nbsp编辑</span>";
    return result;
} ;
//日程
function repas(id){
    window.location.href=ctx +'/scheduling/view.action?id='+id;
}
//获取选中班级Id
function receiptForm() {
    // console.log("回执表")
    var row =$("#table").bootstrapTable('getSelections');
    if(row.length==1){

        location = ctx+"/ReceiptForm/view.action?id="+(row[0].id)

    }else{
        layer.msg(row.length == 2?"只能选择一个班级":"请选择一个班级")
    }
    //console.log(row[0].id)

    //
}
//清空input
function reset(){
    $("input").prop("value","");
}
//年月范围选择
function calenderInit() {
    laydate.render({
        elem: '#startStopTime'
        ,type: 'month'
        ,theme: '#6CA6CD'
        ,value: sessionStorage.getItem("startStopTime")
        ,isInitValue: true
        ,done: function(value, date, endDate){
            //点击事件 点选时间 后 将session中的时间更新
            sessionStorage.setItem("startStopTime",value);
            ///console.log(sessionStorage.getItem("startStopTime"));
            $('#table').bootstrapTable("refresh");
        }
    });
    laydate.render({
        elem: '#entryStartTime'
        ,theme: '#6CA6CD'
        ,value: sessionStorage.getItem("entryStartTime")
        ,isInitValue: true
        ,done: function(value, date, endDate){
            //点击事件 点选时间 后 将session中的时间更新
            sessionStorage.setItem("entryStartTime",value);
            //console.log(sessionStorage.getItem("entryStartTime"));
            $('#table').bootstrapTable("refresh");
        }
    });
}
//session存储查询条件
var initCondition = function () {
    // sessionStorage.setItem("startStopTime",new Date().Format("yyyy-MM"));
    // sessionStorage.setItem("classNumber","");
    // sessionStorage.setItem("className","");
    // sessionStorage.setItem("teacherName","");

    //如果是首次加载则初始化session中的时间为当前日期
    if (sessionStorage.getItem("startStopTime") == null){
        sessionStorage.setItem("startStopTime",new Date().Format("yyyy-MM"));
        console.log(sessionStorage.getItem("startStopTime"))
    }
    if (sessionStorage.getItem("entryStartTime") == null){
        sessionStorage.setItem("entryStartTime",new Date().Format("yyyy-MM-dd"));
        console.log(sessionStorage.getItem("entryStartTime"))
    }


};
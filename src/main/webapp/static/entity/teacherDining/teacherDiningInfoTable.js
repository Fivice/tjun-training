var teacherDiningTableInit = function() {
    // 1.初始化Table
    var oTable = new teacherDiningInfoTableInit();
    oTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new teacherDiningInfoButtonInit();
    oButtonInit.Init();
};
//
var teacherDiningInfoTableInit = function() {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function() {
        console.log(ctx)
        $('#teacherDiningTable')
            .bootstrapTable(
                {
                    url : ctx+"/TeacherDiningForScene/teacherDiningInfo.action", // 请求后台的URL（*）
                    method : 'post', // 请求方式（*）
                    contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType : "json", // 数据类型
                    toolbar : '#teacherDiningInfoToolbar', // 工具按钮用哪个容器
                    striped : true, // 是否显示行间隔色
                    cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination : true, // 是否显示分页（*）
                    sortable : true, // 是否启用排序
                    sortOrder : "asc", // 排序方式
                    queryParamsType : 'limit',
                    queryParams : oTableInit.queryParams, // 传递参数（*）
                    fixedColumns: true,
                    fixedNumber: 1,
                    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, // 初始化加载第一页，默认第一页
                    pageSize : 10, // 每页的记录行数（*）
                    pageList: [10, 20, 50, 120], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    // showColumns : true, // 是否显示所有的列
                    // showRefresh : true, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : true, // 是否启用点击选中行
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "", // 每一行的唯一标识，一般为主键列
                    // showToggle : true, // 是否显示详细视图和列表视图的切换按钮
                    // cardView : false, // 是否显示详细视图
                    detailView : false, // 是否显示父子表
                    // showExport : true, // 是否显示导出按钮
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
                    columns: [
                        // {checkbox: true,formatter:stateFormatter},
                        {title: '#', field: '',formatter:function (value,row,index) {
                                var pageSize = $('#teacherDiningTable').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                                var pageNumber = $('#teacherDiningTable').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                            }},
                        {title: 'id', field: 'id',visible: false},
                        {title: '注册就餐id', field: 'teacherDiningRegId',visible: false},
                        {title: '就餐地点', field: 'diningPlace'},
                        {title: '就餐日期', field: 'diningDate'},
                        {title: '星期', field: 'diningDate',formatter : function(value, row, index) {
                                var d=new Date(value)
                                var weekday=new Array(7)
                                weekday[0]="星期天"
                                weekday[1]="星期一"
                                weekday[2]="星期二"
                                weekday[3]="星期三"
                                weekday[4]="星期四"
                                weekday[5]="星期五"
                                weekday[6]="星期六"
                                return weekday[d.getDay()];
                            }},
                        {title: '早餐',align:"center", field: 'breakfast',formatter:formatDiningValue},
                        {title: '中餐',align:"center", field: 'lunch',formatter:formatDiningValue},
                        {title: '晚餐',align:"center", field: 'dinner',formatter:formatDiningValue},
                        // {title: '编辑', field: '',formatter:function (value,row,index) {
                        //         var result = "<button class='btn btn-primary btn-sm' value='"+row.id+"' onclick='changeDining(this)' >修改</button>";
                        //         return result;
                        //     }}
                    ],
                    onLoadSuccess : function(data) {
                        result = data.rows
                        console.log(result);
                        var teacherDiningRegArr = [];
                        for (var i = 0; i < result.length; i++) {
                            teacherDiningRegArr[i] = result[i].id;
                        }
                        // console.log(teacherDiningRegArr)
                        window.teacherDiningRegArr = teacherDiningRegArr;
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
            teacherId:$("#teacherDiningId").val(),
            classId:$("#teacherDiningClassId").val()
        };
        console.log(temp)
        return temp;
    };

    return oTableInit;
};
function teacherDiningInfo(id,breakfast,lunch,dinner){
    this.id = id;
    this.breakfast = breakfast;
    this.lunch = lunch;
    this.dinner = dinner;
};
function changeDining(obj) {

    console.log(window.teacherDiningRegArr)
    var idArr = window.teacherDiningRegArr;
    //input 分组
    var inputArr;
    var dinArr = [];
    inputArr = $("#teacherDiningTable input[type=checkbox]");
    console.log(inputArr);

    for (var i = 0; i < idArr.length; i++) {
        var breakfast = inputArr[i*3].checked?"1":"2";
        var lunch = inputArr[i*3+1].checked?"1":"2";
        var dinner = inputArr[i*3+2].checked?"1":"2";
        var teacherDin = new teacherDiningInfo(idArr[i],breakfast,lunch,dinner);
        dinArr.push(teacherDin);
    }
    console.log(JSON.stringify(dinArr))
    $.ajax({
        url:ctx+"/TeacherDiningForScene/updateTeacherDining.action",
        type:'post',
        dataType:'json',
        data:{"dinArr":JSON.stringify(dinArr)},
        success:function (data) {
            console.log(data)
            layer.msg("保存成功")
            $("#teacherDiningTable").bootstrapTable("destroy");
            teacherDiningTableInit();
        }
    })
}
function formatDiningValue(value,row,index){
    if (value == '1') {
        return "<input type=\"checkbox\" class='checkbox' checked>";
    }
    if (value == '2') {
        return "<input type=\"checkbox\" class='checkbox'>";
    }
    return "";
}

var teacherDiningInfoButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};

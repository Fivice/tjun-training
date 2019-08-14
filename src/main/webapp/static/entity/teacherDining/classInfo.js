var classTableInit = function() {
    // 1.初始化Table
    var oTable = new classInfoTableInit();
    oTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new classInfoButtonInit();
    oButtonInit.Init();
};
//
var classInfoTableInit = function() {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function() {
        console.log(ctx)
        $('#class-info')
            .bootstrapTable(
                {
                    url : ctx+"/TeacherDiningForScene/classInfoBindClass.action", // 请求后台的URL（*）
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
                                var pageSize = $('#class-info').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                                var pageNumber = $('#class-info').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                            }},
                        {title: '班级id', field: 'classId',visible: false},
                        {title: '班级', field: 'className',formatter:clickToSelectClass},
                        {title: '绑定的教师', field: 'teacherId',visible:false},
                        {title: '绑定的教师', field: 'teacherName'}
                    ],
                    onLoadSuccess : function(data) {

//								layer.msg("数据加载完成");

                    },
                    onLoadError : function() {
                        layer.msg("班级数据加载失败！");
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
            className:$("#classNameForSearch").val()
        };
        console.log(temp)
        return temp;
    };

    return oTableInit;
};


var classInfoButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};
function classInfo(classId,className,teacherId){
    this.classId = classId;
    this.className = className;
    this.teacherId = teacherId;
};
function clickToSelectClass (value, row, index) {
    var Info = new classInfo(row.classId,row.className,row.teacherId);
    console.log(Info);
    var result = "<button class='btn btn-green btn-sm' onclick='getClass("+JSON.stringify(Info)+")' >"+row.className+"</button>";
    return result;
};
function getClass(Info) {
    console.log(Info)
    $("#classId").val(Info.classId);
    $("#className").val(Info.className);
    $("#bindTeacherId").val(Info.teacherId);
}

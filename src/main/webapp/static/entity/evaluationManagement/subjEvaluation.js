
$(function() {

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
                    url : 'findSubjEvaluationTable.action', // 请求后台的URL（*）
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
                    pageList: [10, 20, 50], // 可供选择的每页的行数（*）
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
                        // ignoreColumn : [ 0, 0 ], // 忽略某一列的索引
                        fileName : '数据导出', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '课程评价表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        // {checkbox: true, visible: true},
                        {title: '课程名称', field: '',formatter:function (value, row, index) {
                                return row.subjEvaluateVo.schedulingContent+" [ "+row.subjEvaluateVo.teacher+" ]"
                            }},
                        {title: '评分', field: 'subjEvaluateVo.result' , formatter:function(value){
            var temps =Number(value).toFixed(2);
            return temps;
        } }
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
            classId:$("#classId").val(),
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

//班级详情
function showImg(id){
    // alert(id)
    window.location.href=ctx +'/classInfo/form.action?id='+id;
}

//查看教师
function searchTeacher(id){
    layer.open({
        skin: 'layui-layer-lan',
        area: ['520px', '500px'], //宽高
        type: 2,
        title: '班主任详细信息',
        /*content: ctx+'/teacherInfo/addUpdate.action?tiId=' + id*/
        content: ctx+'/sysuser/'+id+'/edit.action'
    });

}

//详情
function EditViewById(id){
    window.location.href=ctx +'/classInfo/form.action?id='+id;
}

//学生评价情况
function studentEvaluation() {
    var rows=$("#table").bootstrapTable('getSelections');
    if (rows.length == 0) {
        layer.alert('请选择一条数据！', {
            icon : 5,
            title : "提示"
        });
    } else if (rows.length > 1) {
        layer.alert('只能选择一条数据查看！', {
            icon : 5,
            title : "提示"
        });
    }else {
        for (var i = 0; i < rows.length; i++) {
            var id = rows[0].classInfo.id;
            window.location.href=ctx +'/evaluationManagement/findStudent.action?id='+id;
        }

    }
}

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
}


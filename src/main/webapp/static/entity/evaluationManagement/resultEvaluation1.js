
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
                    url : 'findResultTable1.action', // 请求后台的URL（*）
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
                        tableName : '总体评价结果建议表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
            //             {
            //             //是否显示复选框
            //             field : 'Number',
            // title : '序号',
            // align: 'center',
            // width: 50,
            // formatter : function(value, row, index) {
            //     //return index + 1;
            //     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
            //     var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
            //     return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            // }},
                        {title: '评价分类', field: 'type'},
                        {title: '评价项目', field: 'project'},
                        {title: '意见和建议', field: 'suggestResult'}

                    ],
                    onLoadSuccess : function(data) {
                        var data = $('#table').bootstrapTable('getData', true);
                        mergeTable(data,'type',1, $('#table'));//行合并
                        // mergeTable1(data,'project',1, $('#table'));//行合并

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
            classId:$("#classId").val()
        };
        return temp;
    };

    return oTableInit;
};


var ButtonInit = function() {
    var oInit = new Object();

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};

/**
 * 合并单元格
 * @param data  原始数据（在服务端完成排序）
 * @param fieldName 合并属性名称
 * @param colspan   合并列
 * @param target    目标表格对象
 */
function mergeTable(data,fieldName,colspan,target){
    //声明一个map计算相同属性值在data对象出现的次数和
    var sortMap = {};
    for(var i = 0 ; i < data.length ; i++){
        for(var prop in data[i]){
            if(prop == fieldName){
                var key = data[i][prop]
                if(sortMap.hasOwnProperty(key)){
                    sortMap[key] = sortMap[key] * 1 + 1;
                } else {
                    sortMap[key] = 1;
                }
                break;
            }
        }
    }
    for(var prop in sortMap){
        // console.log(prop,sortMap[prop])
    }
    var index = 0;
    for(var prop in sortMap){
        var count = sortMap[prop] * 1;
        $(target).bootstrapTable('mergeCells',{index:index, field:'type', colspan: colspan, rowspan: count});
        index += count;
    }
}

// function mergeTable1(data,fieldName,colspan,target){
//     //声明一个map计算相同属性值在data对象出现的次数和
//     var sortMap = {};
//     for(var i = 0 ; i < data.length ; i++){
//         for(var prop in data[i]){
//             if(prop == fieldName){
//                 var key = data[i][prop]
//                 if(sortMap.hasOwnProperty(key)){
//                     sortMap[key] = sortMap[key] * 1 + 1;
//                 } else {
//                     sortMap[key] = 1;
//                 }
//                 break;
//             }
//         }
//     }
//     for(var prop in sortMap){
//         // console.log(prop,sortMap[prop])
//     }
//     var index = 0;
//     for(var prop in sortMap){
//         var count = sortMap[prop] * 1;
//         $(target).bootstrapTable('mergeCells',{index:index, field:'project', colspan: colspan, rowspan: count});
//         index += count;
//     }
// }



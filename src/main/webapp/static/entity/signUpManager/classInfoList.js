var initClassListTable = (function ($) {
    'use strict';
    var _this = {};
    _this.initTable = function (tableId) {
        //对指定tableId渲染表格
        Table(tableId);
    };
    var Table = function (tableId) {
        var inlineObject = {};
        inlineObject.Init = function(){
            $("#"+tableId+"").bootstrapTable(
                {
                    url : ctx+'/commonApi/classInfoList.action', // 请求后台的URL（*）
                    method : 'get', // 请求方式（*）
                    contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType : "json", // 数据类型
                    toolbar : '#toolbar', // 工具按钮用哪个容器
                    striped : true, // 是否显示行间隔色
                    cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination : true, // 是否显示分页（*）
                    sortable : true, // 是否启用排序
                    sortOrder : "asc", // 排序方式
                    queryParamsType : 'limit',
                    singleSelect:true,
                    queryParams : inlineObject.queryParams, // 传递参数（*）
                    onCheck: inlineObject.check,
                    onUncheck: inlineObject.unCheck,
                    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, // 初始化加载第一页，默认第一页
                    pageSize : 10, // 每页的记录行数（*）
                    pageList: [10, 20, 50, 120], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : false, // 是否显示所有的列
                    showRefresh : false, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : true, // 是否启用点击选中行
                    uniqueId : "", // 每一行的唯一标识，一般为主键列
                    showToggle : false, // 是否显示详细视图和列表视图的切换按钮
                    cardView : false, // 是否显示详细视图
                    detailView : false, // 是否显示父子表
                    buttonsAlign : "right", // 按钮位置
                    columns : [
                        //是否显示复选框
                        {checkbox: true, visible: true},
                        {title: '班级编号', field: 'classNumber',sortable:true,formatter:function(value, row, index){
                                return row.classId;
                            }},
                        {title: '班级名称', field: 'className',sortable:true},
                        {title: '开班时间', field: 'startStopTime',sortable:true},
                        {title: '报到区域', field: 'regPlace',sortable:true},
                        {title: '班主任', field: 'teacherName',sortable:true}
                    ],
                    onLoadSuccess : function(data) {

//								layer.msg("数据加载完成");

                    },
                    onLoadError : function() {
                        layer.msg("数据加载失败！");
                    },

                    // 注册加载子表的事件。注意下这里的三个参数！
                    // onExpandRow : function(index, row, $detail) {
                    //     oInit.InitSubTable(index, row, $detail);
                    // }
                });
        };
        inlineObject.queryParams = function (params) {
            return {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit,   //页面大小
                pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
                orderName: params.sort,
                orderBy: params.order,
                classNumber:$("#changeClass-classId").val(),
                className:$("#changeClass-className").val(),
                startTime:$("#changeClass-startTime").val()
            };
        };
        inlineObject.check = function(row, $element){
            console.log("check");
            $("#selectedClass").val(row.className)
            sessionStorage.setItem("newClassId",row.id);
        };
        inlineObject.unCheck = function(row, $element){
            console.log("uncheck");
            $("#selectedClass").val("");
        };
        //
        inlineObject.Init();
    };
    return _this;
})(jQuery);


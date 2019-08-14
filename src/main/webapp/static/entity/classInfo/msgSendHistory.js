var initMsgHistoryTable = (function ($) {
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
                    url : ctx+'/messageHistory/classMsgHistory.action', // 请求后台的URL（*）
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
                    sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, // 初始化加载第一页，默认第一页
                    pageSize : 20, // 每页的记录行数（*）
                    pageList: [20, 50, 120], // 可供选择的每页的行数（*）
                    search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : false, // 是否显示所有的列
                    showRefresh : true, // 是否显示刷新按钮
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
                        {title: '电话', field: 'messageHistory.phone',sortable:true},
                        {title: '发送时间', field: 'messageHistory.sendTime',sortable:true,formatter:function(value, row, index){
                                var date = new Date(parseInt(row.messageHistory.sendTime));
                                return date.Format("yyyy-MM-dd HH:mm:ss");
                            }},
                        {title: '发送人', field: 'sysUser.userName',sortable:true}
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
                classId : sessionStorage.getItem("selectClassId")
            };
        };
        //
        inlineObject.Init();
    };
    return _this;
})(jQuery);

$(function () {
    //监听班级是否选中
    $("#showMsgHistory").click(function () {
        var classId = sessionStorage.getItem("selectClassId");
        var className = $("#message-class").val();
        console.log(className);
        if (classId !== null&&className!==""){
            $("#sendMsgHistoryModal").modal("show");
            initMsgHistoryTable.initTable("history-body");
        } else {
            layer.open({
                content:"您未选中任何班级！"
            })
        }
    });

    //监听模态框变化
    $("#sendMsgHistoryModal").on('hidden.bs.modal', function (e) {
        console.log("批量添加模态框关闭");
        //判定是否已经选择班级
        $("#history-body").bootstrapTable("destroy");
    });
});

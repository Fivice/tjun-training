var path = $("#contextPath").val();
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
        $('#table_teacherInfo')
            .bootstrapTable(
                {
                    url : ctx+'/teacherInfo/findTable.action', // 请求后台的URL（*）
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
                    pageList : [  10, 25, 50, 100 ], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : true, // 是否显示所有的列
                    showRefresh : true, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : true, // 是否启用点击选中行
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "tiId", // 每一行的唯一标识，一般为主键列
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
                        ignoreColumn : [ 0 ], // 忽略某一列的索引
                        fileName : '教师信息表', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '教师信息表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [ {
                        checkbox : true, // 是否显示复选框
                        visible : true
                    }, {title: '姓名', field: 'tiName', formatter:function(value, row, index){
                            var temp = '<a style="color: #6CA6CD" href="form.action?query=query&&id='+row.tiId+'">' + row.tiName + '</a>';
                            return temp;
                        }},
                        {
                        field : 'phoneNumber',
                        title : '手机号',
                        sortable : true
                    },{
                        field : 'tiDepartment',
                        title : '工作单位',
                        sortable : true
                    },{
                        field : 'subject',
                        title : '授课科目',
                        sortable : true
                    }],
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
            /*limit: params.limit,   //页面大小
            offset: params.offset,  //页码*/
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1,
            subject: $("#subject").val(),
            tiName:$("#tiName").val(),
            tiDepartment:$("#tiDepartment").val(),
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





// 批量删除数据
function del() {

    //使用getSelections即可获得，row是json格式的数据
    var getSelectRows = $("#table_teacherInfo").bootstrapTable('getSelections', function (row) {
        return row;
    });

    iNumbers = [];
    for (var i = 0; i < getSelectRows.length; i++) {
        iNumbers.push(getSelectRows[i].tiId)
    }

    if (iNumbers.length == 0) {
        layer.alert('请至少选择一条数据！', {
            icon: 5,
            title: "提示"
        });
    } else {
        layer.confirm('您确定要删除数据吗？', {
            btn: ['确定', '取消']
            //按钮
        }, function () {
            /*alert(iNumbers); */
            $.ajax({
                type: 'POST',
                url: 'delete.action',//发送请求
                data: {

                    iNumbers: iNumbers.toString()
                },
                /*  contentType:'application/json;charset=utf-8', */
                dataType: "json",
                success: function (result) {
                    if (result.code == 1) {
                        parent.layer.msg("删除数据成功!", {
                            shade: 0.3,
                            time: 1500
                        }, function () {
                            window.location.reload(); // 刷新父页面
                        });
                    } else {
                        layer.msg(result.message, {
                            icon: 2,
                            time: 1000
                        });
                    }

                }
            });

        });

    }
}





//添加单位信息维护
function createTeacher(){
    layer.open({
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        type : 2,
        title : '添加教师信息',
        skin: 'layui-layer-molv', //样式类名
        area : [ '900px', '700px' ],
        shade : 0,
        maxmin : true,
        content :  'add.action',
        btn : [ '保存', '取消' ] //只是为了演示
        ,
        yes : function(index,layero) {
            var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
            //进行表单验证
            yanzheng(inputForm);
            inputForm.bootstrapValidator('validate');
            var flag = inputForm.data('bootstrapValidator').isValid();
            if(flag) {
                inputForm.ajaxSubmit({
                    url:  'save.action',
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("添加成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                window.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    }
                });
            }
        },
        btn2 : function() {
            layer.closeAll();
        },
        success : function(layero) {
            layer.setTop(layero); //重点2
        }
    });
}


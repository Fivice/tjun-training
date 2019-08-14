$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});
    var TableInit = function () {
        var oTableInit = {};
        //初始化Table
        oTableInit.Init = function () {
            $('#table').bootstrapTable({
                url: 'findTable.action',  //请求后台的URL（*）
                method: 'get',                       	//请求方式（*）
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
                showColumns: true,                  	//是否显示所有的列
                showRefresh: true,                  	//是否显示刷新按钮
                minimumCountColumns: 2,             	//最少允许的列数
                clickToSelect: true,                	//是否启用点击选中行
                //height: 500,                        	//行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",                   //每一行的唯一标识，一般为主键列
                showToggle:true,                    	//是否显示详细视图和列表视图的切换按钮
                cardView: false,                    	//是否显示详细视图
                detailView: false,                  		//是否显示父子表
                showExport: true,  //是否显示导出按钮
                exportDataType : "basic", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
                buttonsAlign:"right",  //按钮位置
                exportTypes:['excel','xlsx'],  //导出文件类型
                // exportTypes:['excel'],
                Icons:'glyphicon-export',
                exportOptions:{
                    ignoreColumn: [0,0],  //忽略某一列的索引
                    fileName: '数据导出',  //文件名称设置
                    worksheetName: 'sheet1',  //表格工作区名称
                    tableName: '区域及教室资源',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                },
                columns : [ {
                    checkbox : true, // 是否显示复选框
                    visible : true
                }, {
                    field : 'campus.schoolName',
                    title : '校区名称'
                }, {
                    field : 'classroom.className',
                    title : '教室名称'
                },{
                    field : 'classroom.classroomType',
                    title : '类别',
                    formatter:function(value){
                        if(value == '1') {
                            return "<span >普通教室</span>";
                        }
                        if(value == '2') {
                            return "<span >机房</span>";
                        }
                        return "";
                    }
                }
                ,{
                    field : 'classroom.capacity',
                    title : '容量'
                }
                    ,{
                        field : 'classroom.remarks',
                        title : '备注'
                    }],
                onLoadSuccess : function(data) {
              // layer.msg("数据加载完成");

                },
                onLoadError: function () {
                     layer.msg("数据加载失败！");
                },

                //注册加载子表的事件。注意下这里的三个参数！
                onExpandRow: function (index, row, $detail) {
                    oInit.InitSubTable(index, row, $detail);
                },

            });
        };



        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                // limit: params.limit,   //页面大小
                // offset: params.offset,  //页码
                pageSize: params.limit,   //页面大小
                pageNumber: params.offset/params.limit+1, //页码
                schoolName:$("#schoolName").val(),
                className:$("#className").val(),
                classType:$("#classType").val()
            };
            return temp;
        };




        return oTableInit;
    };


    var ButtonInit = function () {
        var oInit = {};
        oInit.Init = function () {
            //初始化页面上面的按钮事件
        };

        return oInit;
    };



//添加教室信息
function creatClassroom(){
 layer.open({
     id: 'LAY_layuipro', //设定一个id，防止重复弹出
     type : 2,
     title : '添加教室',
     skin: 'layui-layer-molv', //样式类名
     area : [ '800px', '450px' ],
     shade : 0,
     maxmin : true,
     content :  'create.action',
     btn : [ '保存', '取消' ] //只是为了演示
     ,
     yes : function(index,layero) {
         var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
         // var schoolName = $(window.frames["layui-layer-iframe" + index].document).contents().find("#schoolName");
         // var nameValue = schoolName.val();
         // if (nameValue == null || nameValue == undefined || nameValue == '') {
         //     var type = $(window.frames["layui-layer-iframe" + index].document).contents().find("#type").find("option:selected").data("type");
         //     schoolName.prop("value", type);
         // }
         //进行表单验证
         var s=$(window.frames["layui-layer-iframe" + index].document).contents().find("#sName option:selected").val();
         inputForm.bootstrapValidator('destroy');
         inputForm.data('bootstrapValidator',null);
         verifyForm(inputForm,s,null);
         inputForm.bootstrapValidator('validate');
         var flag =false;
         setTimeout(function() {
         flag = inputForm.data('bootstrapValidator').isValid();
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
         },2500);
     },
     btn2 : function() {
         layer.closeAll();
     },
     success : function(layero) {
         layer.setTop(layero); //重点2
     }
 });
}

//删除教师信息
function del() {
    //使用getSelections即可获得，row是json格式的数据
    var getSelectRows = $("#table").bootstrapTable('getSelections', function (row) {
        return row;
    });
    iNumbers = [];
    for (var i = 0; i < getSelectRows.length; i++) {
        iNumbers.push(getSelectRows[i].classroom.id)
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


//编辑教室信息
function edit(){
    var rows = $('#table').bootstrapTable("getSelections");
    var value;
    if (rows.length == 0) {
        layer.alert('请选择一条数据！', {
            icon : 5,
            title : "提示"
        });
    } else if (rows.length > 1) {
        layer.alert('只能选择一条数据编辑！', {
            icon : 5,
            title : "提示"
        });
    } else {
        for (var i = 0; i < rows.length; i++) {
            var id = rows[0].classroom.id;
            layer.open({
                id : 'LAY_layuipro', // 设定一个id，防止重复弹出
                type : 2,
                title : '编辑数据',
                skin : 'layui-layer-molv', // 样式类名
                area : [ '800px', '450px' ],
                shade : 0,
                maxmin : true,
                content : 'update.action?id='+ id,
                btn : [ '保存', '取消' ] // 只是为了演示
                ,
                yes : function(index, layero) {
                    var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
                    var s=$(window.frames["layui-layer-iframe" + index].document).contents().find("#sName option:selected").val();
                    var cid=$(window.frames["layui-layer-iframe" + index].document).contents().find("#cid").val();
                    inputForm.bootstrapValidator('destroy');
                    inputForm.data('bootstrapValidator',null);
                    verifyForm(inputForm,s,cid);
                    inputForm.bootstrapValidator('validate');
                    var flag =false;
                        setTimeout(function() {
                         flag = inputForm.data('bootstrapValidator').isValid();
                            if(flag) {
                                inputForm.ajaxSubmit({
                                    url: 'saveClassroom.action',
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (result) {
                                        if (result.code == 1) {
                                            parent.layer.msg("更新成功!", {
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
                    },2500);


                },
                btn2 : function() {
                    layer.closeAll();
                },
                success : function(layero) {
                    layer.setTop(layero); // 重点2
                }
            });

        }

    }

}

//添加教室信息
function addCampus(){
    layer.open({
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        type : 2,
        title : '添加校区',
        skin: 'layui-layer-molv', //样式类名
        area : [ '500px', '300px' ],
        shade : 0,
        maxmin : true,
        content :  'addCampus.action',
        btn : [ '保存', '取消' ] //只是为了演示
        ,
        yes : function(index,layero) {
            var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
            //进行表单验证
            yanzheng1(inputForm);
            inputForm.bootstrapValidator('validate');
            var flag = inputForm.data('bootstrapValidator').isValid();
            if(flag) {
                inputForm.ajaxSubmit({
                    url:  'saveCampus.action',
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


//校区列表
function campuslist() {
    layer.open({
        type : 2,
        title : '添加校区',
        skin: 'layui-layer-molv', //样式类名
        area : [ '700px', '600px' ],
        shade : 0,
        maxmin : true,
        content :  'campusList.action',
        // btn : [ '保存', '取消' ] //只是为了演示
        // ,
        // window.location.reload()// 刷新父页面
});
}
    // window.location.href=ctx +'/classInfo/form.action?id='+id;

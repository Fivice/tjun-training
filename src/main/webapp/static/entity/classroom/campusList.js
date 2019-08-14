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
                url: 'campusListTable.action',  //请求后台的URL（*）
                method: 'get',                       	//请求方式（*）
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                dataType: "json",						//数据类型
                toolbar: '#toolbar',                 	//工具按钮用哪个容器
                striped: true,                      	//是否显示行间隔色
                cache: false,                       	//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   	//是否显示分页（*）
                sortable: true,                    	    //是否启用排序
                sortOrder: "asc",                   	//排序方式
                queryParamsType: '',
                queryParams: oTableInit.queryParams, 	//传递参数（*）
                sidePagination: "client",           	//分页方式：client客户端分页，server服务端分页（*）
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
                showExport: false,  //是否显示导出按钮
                // exportDataType : "basic", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
                buttonsAlign:"right",  //按钮位置
                //exportTypes:['excel','xlsx'],  //导出文件类型
                exportTypes:['excel'],
                Icons:'glyphicon-export',
                exportOptions:{
                    // ignoreColumn: [0,0],  //忽略某一列的索引
                    fileName: '数据导出',  //文件名称设置
                    worksheetName: 'sheet1',  //表格工作区名称
                    tableName: '校区列表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
                },
                columns : [
                    {
                    field : 'campus.schoolName',
                    title : '校区名称',
                formatter: function (value, row) {
                    return "<a href='#'  id='schoolName' name='schoolName' style='color: #1b6d85' data-v="+value+" data-type='text' data-pk=\""+row.campus.id+"\" data-title=\"校区名称\">" + value + "</a>";
                }
                }, {title: '操作',field: 'campus.id',
                    formatter: function (value) {
                        result = '<button type="button" class="btn btn-info" onclick=deleteCampus('+value+') title="删除"><i class="fa fa-trash" aria-hidden="true"></i></button>';
                        return result;
                    }//自定义方法，添加详情按钮
                }],
                onLoadSuccess : function(data) {
                    $("#table a").editable({
                        url: function (params) {
                            var v=$(this)[0].dataset.v;
                            var id= $(this)[0].dataset.pk;
                            var schoolName = params.value;
                            $.ajax({
                                type: 'POST',
                                url: 'saveOrUpdate.action',
                                data:{
                                    id:id,
                                    schoolName:schoolName
                                },
                                dataType: 'JSON',
                                success: function (result) {
                                    if (result.code == 1) {
                                        layer.msg('修改成功!', {
                                            icon: 1,
                                            time: 1000
                                        }, function () {
                                            window.location.reload(); // 刷新父页面
                                            // parent.location.reload();
                                        });
                                    }else{
                                        layer.msg("已经存在此校区")
                                        $("#schoolName").editable('setValue', v)
                                    }
                                }
                            });
                        },
                        type: 'text'
                    });

                },
                onLoadError: function () {
                     layer.msg("数据加载失败！");
                },

                //注册加载子表的事件。注意下这里的三个参数！
                onExpandRow: function (index, row, $detail) {
                }

            });
        };



        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
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

//详情
function deleteCampus(id){
        layer.confirm('您确定要删除数据吗？', {
            btn: ['确定', '取消']
            //按钮
        }, function () {
            $.ajax({
                type: 'POST',
                url: 'deleteCampus.action',//发送请求
                data: {
                    id: id
                },
                dataType: "json",
                success: function (result) {
                    if (result.code == 1) {
                        parent.layer.msg("删除数据成功!", {
                            shade: 0.3,
                            time: 1500
                        }, function () {
                            window.location.reload(); // 刷新父页面
                        });
                    }else if (result.code == 0 ) {
                        parent.layer.msg("校区下面有可用的教室，不能直接删除!", {
                            shade: 0.3,
                            time: 1500
                        }, function () {
                            window.location.reload(); // 刷新父页面
                        });
                    }else {
                        layer.msg(result.message, {
                            icon: 2,
                            time: 1000
                        });
                    }

                }
            });

        });

    }



$(function() {

    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    var cid=$("#id").val();

});
var path = $("#contextPath").val();

//
var TableInit = function() {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function() {
        $('#table_scoreReport_write')
            .bootstrapTable(
                {
                    url : 'findTable2.action', // 请求后台的URL（*）
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
                        ignoreColumn : [ 0 ], // 忽略某一列的索引
                        fileName : '成绩证书表', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '成绩证书表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        {checkbox: true, visible: true},

                        {title: '姓名', field: 'stu.siName', formatter:function(value, row, index){
                                /*var temp = '<a style="color: #6CA6CD" href="/student/form.action?id='+row.stu.siId+'">' + row.stu.siName + '</a>';*/
                                var temp = '<a style="color: #6CA6CD" href="javascript:void(0)" onclick=showImg(' + row.stu.siId + ');>' + row.stu.siName + '</a>';

                                return temp;
                            }},
                        {
                            title: '身份证号',
                            field: 'stu.siIDNumber',
                            sortable : true
                        },
                        {
                            title: '成绩',
                            field: 'scoreReport.mark',
                            sortable : true,
                            formatter: function (value, row, index) {
                                if(typeof value == "undefined" || value == null || value == ""){
                                    return"<a class = 'Xeditable' style='color: #1b6d85' data-pk='0' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='成绩'></a>";
                                }else{
                                    return "<a class = 'Xeditable' style='color: #1b6d85' data-pk='0' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='成绩'>"+value+"</a>";
                                }

                            }
                        },
                        {
                            title: '证书名称',
                            field: 'scoreReport.reportName',
                            sortable : true,
                            formatter: function (value, row, index) {
                                if(typeof value == "undefined" || value == null || value == ""){
                                    return"<a class = 'Xeditable' style='color: #1b6d85' data-pk='1' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='证书名称'></a>";
                                }else{
                                    return "<a class = 'Xeditable' style='color: #1b6d85' data-pk='1' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='证书名称'>"+value+"</a>";
                                }

                            }

                        },
                        {
                            title: '证书编号',
                            field: 'scoreReport.reportNumber',
                            sortable : true,
                            formatter: function (value, row, index) {
                                if(typeof value == "undefined" || value == null || value == ""){
                                    return"<a class = 'Xeditable' style='color: #1b6d85' data-pk='2' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='证书编号'></a>";
                                }else{
                                    return "<a class = 'Xeditable' style='color: #1b6d85' data-pk='2' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='证书编号'>"+value+"</a>";
                                }

                            }
                        },{
                            field : 'scoreReport.remark',
                            title : '备注',
                            sortable : true,
                            formatter: function (value, row, index) {
                                if(typeof value == "undefined" || value == null || value == ""){
                                    return"<a class = 'Xeditable' style='color: #1b6d85' data-pk='3' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='备注'></a>";
                                }else{
                                    return "<a class = 'Xeditable' style='color: #1b6d85' data-pk='3' data-reId="+row.register.id+ " data-siId="+row.stu.siId+ " data-title='备注'>"+value+"</a>";
                                }

                            }
                        }],
                    onLoadSuccess : function(data) {

//								layer.msg("数据加载完成");
                        $('.Xeditable').editable({
                            /*  url:function(params){
                                  return updateValue(params,$(this))
                              },*/
                            success: function(response, newValue) {
                                editValue($(this)[0].dataset.pk,$(this)[0].dataset.reid,$(this)[0].dataset.siid,$("#id").val(),newValue)

                                /* alert(allTableData[0].studentScoreVo.siId);*/
                                /*alert(id);*/
                                /*console.log($(this)[0].dataset.pk);*/
                                /*alert(newValue);*/
                            }
                        });
                        /*$('#table_scoreReport_write a').editable();*/

                    },
                    onLoadError : function() {
                        layer.msg("数据加载失败！");
                    },
                    onClickRow: function (row) {
                        /*alert(row.studentScoreVo.siId);*/
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
            stuName: $("#stuName").val(),
            id:$("#id").val(),
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
    window.location.href=ctx +'/student/form.action?query=query&&id='+id;
    // window.location.href=ctx +'/stuDining/form.action?studentId='+id;
}

//初始化就餐安排
function init(id){

    $.ajax({
        type: 'POST',
        dataType: 'json',
        data: {
        },
        url: path + '/scoreReport/findByClassId.action?id='+id,
        success: function (result) {
            if (result.code == 1) {

                layer.msg('初始化成功!', {
                    icon: 1,
                    time: 1000
                }, function () {
                    $('#table_scoreReport_write').bootstrapTable("refresh");
                    /*window.location.reload(); // 刷新父页面*/
       /*             var allTableData = $("#table_scoreReport_write").bootstrapTable('getData');//获取表格的所有内容行

                    for( i=0;i<allTableData.length;i++) {
                        var s="mark_"+i;
                        var rows = {
                            index : i,  //更新列所在行的索引
                            field : "studentScoreVo.mark", //要更新列的field
                            value:"<a style='color: #1b6d85' data-pk="+allTableData[i].studentScoreVo.siId+" id="+s+" data-title='成绩'>合格</a>"
                        }
                        //更新表格数据
                        $('#table_scoreReport_write').bootstrapTable("updateCell",rows);
                        $('#'+s).editable();
                    }
                    $('#table_scoreReport_write a').editable({
                        /!*  url:function(params){
                              return updateValue(params,$(this))
                          },*!/
                        success: function(response, newValue) {
                            console.log($(this)[0].dataset.pk);
                            editValue($(this)[0].dataset.pk,$("#id").val(),newValue)

                            /!* alert(allTableData[0].studentScoreVo.siId);*!/
                            /!*alert(id);*!/
                            /!*console.log($(this)[0].dataset.pk);*!/
                            /!*alert(newValue);*!/
                        }
                    });*/

                });
            } else {
                layer.alert(result.message, {
                    icon: 2
                });
            }
        }
    })

/*    $.ajax({
        type: 'POST',
        dataType: 'json',
        data: {
        },
        url: path + '/scoreReport/init.action?id='+id,
        success: function (result) {
            if (result.code == 1) {
                layer.msg('初始化成功!', {
                    icon: 1,
                    time: 1000
                }, function () {
                    window.location.reload(); // 刷新父页面
                });
            } else {
                layer.alert(result.message, {
                    icon: 2
                });
            }
        }
    })*/

/*    var allTableData = $("#table_scoreReport_write").bootstrapTable('getData');//获取表格的所有内容行

    for( i=0;i<allTableData.length;i++) {
        var s="mark_"+i;
        var rows = {
            index : i,  //更新列所在行的索引
            field : "studentScoreVo.mark", //要更新列的field
            value:"<a style='color: #1b6d85' id="+s+" data-title='成绩'>合格</a>"
           /!* value : "<input id="+s+" value='合格' class='form-control' data-type='text' data-title='成绩'>" ,//要更新列的数据*!/
        }
        //更新表格数据
        $('#table_scoreReport_write').bootstrapTable("updateCell",rows);
       /!* console.log('#'+s)
        $('#'+s).editable();*!/
    }
    $('#table_scoreReport_write a').editable();*/

}
//修改值
function editValue(pk,reId,sid,cid,mark){
    $.ajax({
        type: 'POST',
        dataType: 'json',
        data: {
           /* id:$(this)[0].dataset.pk,//报到id
            classId:id, //班级id
            mark:newValue,//成绩*/
            pk:pk,
            reId:reId,//报道Id
            id:sid,//学生id
            classId:cid, //班级id
            mark:mark,//成绩
        },
        url: path + '/scoreReport/saveOrUpdate.action?',
        success: function (result) {
            if (result.code == 1) {
                layer.msg('修改成功!', {
                    icon: 1,
                    time: 1000
                }, function () {
                    window.location.reload(); // 刷新父页面
                });
            } else {
                layer.alert(result.message, {
                    icon: 2
                });
            }
        }
    })
}

function updateValue(params,ele){
    /*$.ajax({
        type: "post",
        url: "/springboot/editvalue",
        data: {name:params.name,value:params.value,pk:params.pk},
        dataType: 'json',
        success: function (result) {
            console.log(result)
            //更新editable内存对象值，如果不加，那么再次点编辑的时候，输入框显示的值不是转小写字母的值，而是最初输入的原值
            ele.editable('setValue',result.newValue);
            //更新页面上的显示值
            ele.html(result.newValue);
        },
        error: function () {
            Ewin.alert('服务器繁忙，请稍后重试');
        },
        complete: function () {
        }
    });*/

}
//保存
function saveRepory(){
    var allTableData = $("#table_scoreReport_write").bootstrapTable('getData');//获取表格的所有内容行

    for(i=0;i<allTableData.length;i++) {
        //发送请求保存数据
    }
}




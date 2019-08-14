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
                    url : ctx+'/student/findTable.action', // 请求后台的URL（*）
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
                        ignoreColumn : [ 0, 0 ], // 忽略某一列的索引
                        fileName : '学员信息数据导出', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '学员信息表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        {checkbox: true, visible: true},
                        {title: '姓名', field: 'siName', formatter:function(value, row, index){
                                var temp = '<a style="color: #6CA6CD" href="form.action?query=query&&id='+row.siId+'">' + row.siName + '</a>';
                                return temp;
                            }},
                        {title: '单位名称', field: 'unitName',
                            /*formatter:function(value, row, index){
                                var obj =row.siUnitId;
                                var temp = "";
                                if (typeof obj == "undefined" || obj == null || obj == "") {
                                    temp =row.unitName;
                                }else{
                                    $.ajax({
                                        type: 'POST',
                                        dataType: 'json',
                                        async: false,//设置为同步传输
                                        data: {
                                            areaId: obj,
                                        },
                                        url: ctx + '/register/findUnitList.action',
                                        success: function (result) {
                                            var dataObj = result; //返回的result为json格式的数据
                                            $.each(dataObj, function (index, item) {
                                                if (index > 0) {
                                                    temp += ">>" + item.areaName;
                                                } else {
                                                    temp += item.areaName;
                                                }
                                            });
                                        }
                                    });

                                }

                                return temp;
                            }*/
           /*                 formatter:function(value, row, index){
                       var obj =row.siUnitId;
                       var temp = "";
                       if (typeof obj == "undefined" || obj == null || obj == "") {
                           temp =row.unitName;
                       }else{
                           $.ajax({
                               type: 'POST',
                               dataType: 'json',
                               async: false,//设置为同步传输
                               data: {
                                   areaId: obj,
                               },
                               url: ctx + '/register/findUnitList.action',
                               success: function (result) {
                                   var dataObj = result; //返回的result为json格式的数据
                                   temp += dataObj.areaName;
                               }
                           });

                       }

                       return temp;
                   }*/
                            },
                       /* {title: '工作部门', field: 'deparentmentName'},*/
                        {title: '身份证号', field: 'siIDNumber'},
                        /* {title: '证件照', field: 'photo'},*/
                        {title: '手机', field: 'phoneNumber'},
                        {title: '状态', field: 'status',formatter : function(value, row, index) {
                                if (value == '1') {
                                    return "<span class='label label-warning'>失效</span>";
                                }
                                if (value == '0') {
                                    return "<span class='label label-primary'>正常</span>";
                                }
                                return "<span class='label label-danger'>未知</span>";
                            }},
                        /* {title: '员工编号', field: 'siNumber'},
                         {title: '邮箱', field: 'email'},
                         {title: '工作岗位', field: 'post'},
                         {title: '职业技能等级', field: 'skillLevel'},
                         {title: '地址', field: 'siAddress'},
                         {title: '评价', field: 'evaluate'},
                         {title: '性别', field: 'sex'}*/
                        {title: '备注', field: 'remarks'},
                                        /*
										 * ,{ field: 'Button', title: '详情',
										 * formatter: operateFormatter
										 * //自定义方法，添加详情按钮 }
										 */
                        {title: '培训记录', field: 'Button',
                            formatter : operateFormatter
                            },

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
            unitId:$("#unitId").val(),
            unitName:$("#ChanYeName").val(),
            siIDNumber:$("#siIDNumber").val(),
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

function operateFormatter (value, row, index) {
    // alert(row.siId)
    // alert(row.siName)
    // alert(row.siIDNumber)
    var result = "";
    result += "<span class='label label-info' onclick=\"EditViewById('" + row.siId + "','" + row.siIDNumber + "')\" title='查看学员培训记录'><i class=\"fa fa-chevron-circle-right\"></i></span>";
    return result;
} ;
//详情
function EditViewById(siId,siIDNumber){
    window.location.href=ctx +'/student/form1.action?siId='+siId+'&siIDNumber='+siIDNumber;
}

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
                    url : ctx+'/ReceiptForm/StudentInfoInReceiptForm.action', // 请求后台的URL（*）
                    method : 'get', // 请求方式（*）
                    contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType : "json", // 数据类型
                    toolbar : '#toolbar', // 工具按钮用哪个容器
                    showPrint: true,
                    printPageBuilder: printFormat,
                    striped : true, // 是否显示行间隔色
                    cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination : true, // 是否显示分页（*）
                    sortable : true, // 是否启用排序
                    sortOrder : "asc", // 排序方式
                    queryParamsType : 'limit',
                    queryParams : oTableInit.queryParams, // 传递参数（*）

                    sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
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
                        fileName : '班级信息表', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '班级信息表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        // {checkbox: true, visible: true},
                        {
                            field: 'no',
                            title: '序号',
                            //sortable: true,
                            align: "center",
                            width: 40,
                            formatter: function (value, row, index) {
                                //获取每页显示的数量
                                var pageSize=$('#table').bootstrapTable('getOptions').pageSize;
                                //获取当前是第几页
                                var pageNumber=$('#table').bootstrapTable('getOptions').pageNumber;
                                //返回序号，注意index是从0开始的，所以要加上1
                                return pageSize * (pageNumber - 1) + index + 1;
                            }
                        },
                        {title: '员工编号',field: 'siNumber'},
                        {title: '姓名',field: 'siName'},
                        {title: '性别',field: 'sex',formatter:function (value,row,index) {
                            var sex;
                            if (row.sex == "1"){
                                sex = "女"
                            } else if (row.sex == "0"){
                                sex = "男"
                            } else {
                                sex = " "
                            }
                                var temp = "<span class=\"label label-sm btn-success\">"+sex+"</span>"
                                return temp;
                            }},
                        {title: '民族',field: 'ethnicGroup'},
                        {title: '工作单位',field: 'unitName',formatter:function (value,row,index) {
                                if (row.unitName == null||row.unitName ==""){
                                    return " "
                                } else {
                                    return row.unitName;
                                }
                            }},
                        {title: '工作部门',field: 'deparentmentName',formatter:function (value,row,index) {
                                if (row.deparentmentName == null||row.deparentmentName ==""){
                                    return " "
                                } else {
                                    return row.deparentmentName;
                                }
                            }},
                        {title: '工作岗位',field: 'post',formatter:function (value,row,index) {
                                if (row.post == null||row.post ==""){
                                    return " "
                                } else {
                                    return row.post;
                                }
                            }},
                        {title: '职称、技能等级',field: 'skillLevel',formatter:function (value,row,index) {
                                if (row.skillLevel == null||row.skillLevel ==""){
                                    return " "
                                } else {
                                    return row.skillLevel;
                                }
                            }},
                        {title: '联系电话',field: 'phoneNumber'},
                        {title: '备注',field: 'remarks'}
                        // {title: '可见',field: 'no',formatter:function (value, row, index) {
                        //         return '<a style="color: #6CA6CD" onclick="editForm(this)">' +"<i class='fa fa-eye-slash'> </i>"+ '</a>'
                        //     }
                        // }

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
function editForm(obj) {
    // console.log(obj.children)
}
function tableForPrint() {
    var tab=document.getElementById("table");
    var rows=tab.rows;
    // console.log(rows.length);//获取表格的行数
    // console.log(rows[0].cells.length);//获取表格的行数

    var tempTable="<table><tbody>";
    for(var i=0;i<rows.length;i++){ //遍历表格的行
        tempTable+="<tr>";
        for(var j=0;j<rows[i].cells.length;j++){  //遍历每行的列
            if (j==0&&i!=0){
                tempTable+="<td>"+(i)+"</td>";
            }else{
                tempTable+="<td>"+rows[i].cells[j].innerHTML+"</td>";
            }
        }
        tempTable+="</tr>";
    }
    for(var k=0;k<21-rows.length;k++){ //遍历表格的行
        tempTable+="<tr>";
        for(var l=0;l<rows[0].cells.length;l++){  //遍历每行的列
            if (l==0){
                tempTable+="<td>"+(rows.length+k)+"</td>";
            }else{
                tempTable+="<td></td>";
            }
        }
        tempTable+="</tr>";
    }
    tempTable+="</tbody></table>"
    //console.log(tempTable);
    return tempTable;
}
//打印
function printFormat(table) {
    var unit = $("#unit").val();
    var date = $("#date").val();
    var people = $("#people").val();
    var phone = $("#phone").val();
    var title = $("#table-title").text();
    // console.log(title);
    var temp = tableForPrint();
    return '<html><head>' +
        '<style type="text/css" media="print">' +
        '  @page { size: auto;   margin: 25px 0 25px 0; }' +
        '</style>' +
        '<style type="text/css" media="all">' +
        'table{border-collapse: collapse; font-size: 14px; }\n' +
        'table, th, td {border: 1px solid grey}\n' +
        'th, td {text-align: center; vertical-align: middle;height: 25px;}\n' +
        'table { width:94%; margin-left:3%; margin-right:3%}\n' +
        'div.bs-table-print { text-align:center;}\n' +
        '</style></head><title>'+title+'</title><body>' +
        '<div style="text-align: center;height: 60px;font-size: 18px">培训班报名回执</div>'+
        '<div style="font-size: 18px;text-align: center;font-weight: bolder">'+new Date().getFullYear()+'年'+title+'</div>'+
        '<div style="width: 100%;margin: 5px 3% 5px 3%;">' +
        '<div style="width: 25%;display: inline;position: relative;float: left">报送单位：'+unit+'</div>'+
        '<div style="width: 25%;display: inline;position: relative;float: left">报送日期：'+date+'</div>'+
        '<div style="width: 25%;display: inline;position: relative;float: left">报送人：'+people+'</div>'+
        '<div style="width: 25%;display: inline;position: relative;float: left">联系电话：'+phone+'</div>'+
        '</div>'+
        '<div class="bs-table-print">' + temp + "</div>" +
        '</div>'+
        "</body></html>";
}



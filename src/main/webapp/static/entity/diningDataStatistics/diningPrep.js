$(function () {
    calenderInit();
    // timeRange();
    initDiningPlaceSelect();
    // diningTable();
    // 1.初始化Table
    var classInfoTable = new classInfoTableInit();
    classInfoTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});

//年月范围选择
var calenderInit = function () {
    var date = new Date(); //创建时间对象
    var year = date.getFullYear(); //获取年
    var month = date.getMonth()+1;//获取月
    var day = date.getDate(); //获取当日
    var time = year+"-"+month+"-"+day; //组合时间
    laydate.render({
        elem: '#today',
        type: 'date', //默认，可不填
        min:time,
        value:new Date(),
        btns: ['confirm']

    });
};

//班级列表
var classInfoTableInit = function() {
    var classInfoTableInit = new Object();
    // 初始化Table
    classInfoTableInit.Init = function() {
        $('#classInfo')
            .bootstrapTable(
                {
                    url : ctx+'/diningDataStatistics/findPrep.action', // 请求后台的URL（*）
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
                    queryParams : classInfoTableInit.queryParams, // 传递参数（*）
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
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "", // 每一行的唯一标识，一般为主键列
                    showToggle : false, // 是否显示详细视图和列表视图的切换按钮
                    showFooter: true,
                    cardView : false, // 是否显示详细视图
                    detailView : false, // 是否显示父子表
                    showExport : false, // 是否显示导出按钮
                    // exportDataType : "selected", //basic'导出当前页,
                    // 'all'导出全部, 'selected'导出选中项.
                    buttonsAlign : "right", // 按钮位置
                    // exportTypes:['excel','xlsx'], //导出文件类型
                    exportTypes : [ 'excel' ],
                    Icons : 'glyphicon-export',
                    exportOptions : {
                        ignoreColumn : [ 0, 0 ], // 忽略某一列的索引
                        fileName : '班级信息', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '班级信息',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns: [
                        [
                            {
                                field: 'className',
                                title: '班级名称',
                                align: 'center',
                                valign: 'middle',
                                colspan:1,
                                rowspan:2,footerFormatter: function (value) {
                                    return "合计";
                                }
                            },
                            {
                                title: '学员早餐',
                                align: 'center',
                                valign: 'middle',
                                colspan:2,
                                rowspan:1
                            },
                            {
                                title: '学员中餐',
                                align: 'center',
                                valign: 'middle',
                                colspan:2,
                                rowspan:1
                            },
                            {
                                title: '学员晚餐',
                                align: 'center',
                                valign: 'middle',
                                colspan:2,
                                rowspan:1
                            },
                            {
                                title: '教师备餐',
                                align: 'center',
                                valign: 'middle',
                                colspan:3,
                                rowspan:1
                            },
                        ],
                        [
                            {
                                field: 'plannedNumberBf',
                                title: "计划需备餐（份）",
                                align: 'center',
                                valign: 'middle',
                                footerFormatter: function (value) {
                                    var count =0;
                                    for (var i =0;i<value.length;i++) {
                                        count += JSON.parse(JSON.stringify( value[i])).plannedNumberBf;
                                    }
                                    return count;
                                }
                            },{
                            field: 'registerNumberBf',
                            title: "实际需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).registerNumberBf;
                                }
                                return count;
                            }
                        },{
                            field: 'plannedNumberLu',
                            title: "计划需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).plannedNumberLu;
                                }
                                return count;
                            }
                        },{
                            field: 'registerNumberLu',
                            title: "实际需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).registerNumberLu;
                                }
                                return count;
                            }
                        },{
                            field: 'plannedNumberDi',
                            title: "计划需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).plannedNumberDi;
                                }
                                return count;
                            }
                        },{
                            field: 'registerNumberDi',
                            title: "实际需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).registerNumberDi;
                                }
                                return count;
                            }
                        },{
                            field: 'teacherBf',
                            title: "早餐需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).teacherBf;
                                }
                                return count;
                            }
                        },{
                            field: 'teacherLu',
                            title: "中餐需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).teacherLu;
                                }
                                return count;
                            }
                        },{
                            field: 'teacherDi',
                            title: "晚餐需备餐（份）",
                            align: 'center',
                            valign: 'middle',
                            footerFormatter: function (value) {
                                var count =0;
                                for (var i =0;i<value.length;i++) {
                                    count += JSON.parse(JSON.stringify( value[i])).teacherDi;
                                }
                                return count;
                            }
                        }
                        ]
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
                    onPostBody:function () {
                        //合并页脚
                        merge_footer();
                    }
                });
    };

    //得到查询的参数
    classInfoTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            today: $("#today").val(),
            diningPlace: $("#diningPlaceSelect option:selected").val()
        };
        // console.log(temp)
        return temp;
    };

    return classInfoTableInit;
};

//合并页脚
function merge_footer() {
    //获取table表中footer 并获取到这一行的所有列
    var footer_tbody = $('.fixed-table-footer table tbody');
    var footer_tr = footer_tbody.find('>tr');
    var footer_td = footer_tr.find('>td');
    var footer_td_1 = footer_td.eq(0);
    console.log(footer_td_1)
    // alert(footer_td.length)
    //由于我们这里做统计只需要两列，故可以将除第一列与最后一列的列全部隐藏，然后再设置第一列跨列
    //遍历隐藏中间的列 下标从1开始
    // for(var i=1;i<footer_td.length-1;i++) {
    //     footer_td.eq(0).hide();
    //footer_td.eq(1).hide();
    // }
    //设置跨列
    //footer_td_1.attr('colspan', 2).show();
    //这里可以根据自己的表格来设置列的宽度 使对齐
    footer_td_1.attr('width', "1199px").show();

}

function operate (value, row, index) {
    var id = value;
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"repast('" + row.number + "','" + row.time + "','" + row.classId + "')\" title='就餐安排'><i class=\"fa fa-cutlery\"></i></button>";
    return result;
}
//就餐安排
function repast(number,time,classId){
    console.log(time)
    console.log(ctx)
    window.location.href=ctx +'/teachDin/view.action';
}


var ButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};

//查询
function seach(){
    $("#classInfo").bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
}
var initDiningPlaceSelect = function () {
    var temp = "";
    $.ajax({
        url:ctx+"/diningDataStatistics/diningPlaceSelect.action",
        type:"get",
        dataType:"json",
        async:false,
        success:function (data) {
            // console.log(data);
            if (data.length!=0&&data[0]=="就餐管理员") {
                for (var i = 1; i < data.length; i++) {
                    temp+="<option value='"+data[i]+"'>"+data[i]+"</option>";
                }
            }else{
                for (j in data){
                    temp+="<option value='"+data[j]+"'>"+data[j]+"</option>";
                }
            }

        },
        error:function () {
            layer.msg("初始化就餐地点下拉选项失败")
        }
    });
    $("#diningPlaceSelect").html(temp);
};
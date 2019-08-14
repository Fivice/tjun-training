//项目根目录
var path = $("#contextPath").val();
$(document).ready(function () {
    loginUserType();
    renderRegPlace();
    //初始化Table
    var oTable = new TableInit();
    oTable.Init();
});
var TableInit = function () {
    var oTableInit = new Object();
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
            cardView: false,
            showFooter:true ,//是否显示详细视图
            detailView: false,                  		//是否显示父子表
            showExport: true,  //是否显示导出按钮
            exportDataType : "basic", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
            buttonsAlign:"right",  //按钮位置
            exportTypes:['excel'],  //导出文件类型
            Icons:'glyphicon-export',
            exportOptions:{
                // ignoreColumn: [0,1],  //忽略某一列的索引
                fileName: '查询统计',  //文件名称设置
                worksheetName: 'sheet1',  //表格工作区名称
                tableName: '查询统计表',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                /*onMsoNumberFormat: DoOnMsoNumberFormat  */
            },
            columns: [
                [
                // {checkbox: true},
                //
                    {title: '班级编号',
                        field: 'classInfo.classNumber',visible:true,footerFormatter: function (value) {
                            return "合计";
                        }},
                    {title: '班级名称', field: 'classInfo',  formatter:function(value, row, index){
                            var temp = '<a style="color: #6CA6CD" href="javascript:void(0)" onclick=showImg(' + value.id + ');>' + value.className + '</a>';
                            return temp;
                        }
                        },
                    {title: '实到人数', field: 'studentCount', formatter:function(value, row, index){
                            var temp = '<a style="color: #6CA6CD" href="javascript:void(0)" onclick=showPlan(' + row.classInfo.id + ');>' + value + '</a>';
                            return temp;
                        },
                        footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).studentCount;
                            }
                            return count;
                        }
                    },
                    {title: '培训费(前台)',titleTooltip: "培训费(前台)", field: 'trainingExpense', formatter:function(value, row, index){
                        if(value==null){
                            value="0"
                        }else {
                            value
                        }
                        return value;
                        },footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).trainingExpense;
                            }
                            return count;
                        }
                    },
                    {title: '交培训费人数',titleTooltip: "交培训费人数", field: 'trainingFee', footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).trainingFee;
                            }
                            return count;
                        }
                        },
                    {title: '住宿费(前台)',titleTooltip: "住宿费(前台)", field: 'scaleFee', formatter:function(value, row, index){
                            if(value==null){
                                value="0"
                            }else {
                                value
                            }
                            return value;
                        },footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).scaleFee;
                            }
                            return count;
                        }
                    },
                    {title: '住宿总天数',
                        field: 'hotelDayCount',visible:true,footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).hotelDayCount;
                            }
                            return count;
                        }},
                    {title: '住宿人数',titleTooltip: "住宿人数", field: 'hotel', footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).hotel;
                            }
                            return count;
                        }
                    },
                    {title: '其它费用(前台)',titleTooltip: "其它费用(前台)", field: 'otherCharges',  formatter:function(value, row, index){
                            if(value==null){
                                value="0"
                            }else {
                                value
                            }
                            return value;
                        },footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).otherCharges;
                            }
                            return count;
                        }
                    },
                {title: '学生就餐费(前台)',titleTooltip: "学生就餐费(前台)", field: 'foodTotal2', formatter:function(value, row, index){
                        if(value==null){
                            value="0"
                        }else {
                            value
                        }
                        return value;
                    },footerFormatter: function (value) {
                        var count =0;
                        for (var i =0;i<value.length;i++) {
                            count += JSON.parse(JSON.stringify( value[i])).foodTotal2;
                        }
                        return count;
                    }
                },
                    {title: '学生就餐人数',titleTooltip: "学生就餐人数", field: 'foodTotal', footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).foodTotal;
                            }
                            return count;
                        }
                    },
                    {title: '学生就餐费(统收)', titleTooltip: "学生就餐费(统收)",field: 'foodTotal4',
                        footerFormatter: function (value) {
                        var count =0;
                        for (var i =0;i<value.length;i++) {
                            count += JSON.parse(JSON.stringify( value[i])).foodTotal4;
                        }
                        return count;
                    }
                    },
                    {title: '实际学生就餐费',titleTooltip: "实际学生就餐费", field: 'studentDinding',
                        footerFormatter: function (value) {
                            var count =0;
                            for (var i =0;i<value.length;i++) {
                                count += JSON.parse(JSON.stringify( value[i])).studentDinding;
                            }
                            return count;
                        }
                    },
                {title: '实际教师就餐费', titleTooltip: "实际教师就餐费",field: 'teacherDinding', formatter:function(value, row, index){
                        if(value==null){
                            value="0"
                        }else {
                            value
                        }
                        return value;
                    },footerFormatter: function (value) {
                        var count =0;
                        for (var i =0;i<value.length;i++) {
                            count += JSON.parse(JSON.stringify( value[i])).teacherDinding;
                        }
                        return count;
                    }
                    },
                {title: '就餐结余',titleTooltip: "就餐结余", field: 'balance', footerFormatter: function (value) {
                        var count =0;
                        for (var i =0;i<value.length;i++) {
                            count += JSON.parse(JSON.stringify( value[i])).balance;
                        }
                        return count;
                    }}
                    ]
                ],//自定义方法，添加详情按钮
            onExpandRow : function(index, row, $detail) {
                oInit.InitSubTable(index, row, $detail);
            },
            onPostBody:function () {
                //合并页脚
                merge_footer();
            }
        });
    };

    //合并页脚
    function merge_footer() {
        //获取table表中footer 并获取到这一行的所有列
        var footer_tbody = $('.fixed-table-footer table tbody');
        var footer_tr = footer_tbody.find('>tr');
        var footer_td = footer_tr.find('>td');
        var footer_td_1 = footer_td.eq(0);
        // alert(footer_td.length)
        //由于我们这里做统计只需要两列，故可以将除第一列与最后一列的列全部隐藏，然后再设置第一列跨列
        //遍历隐藏中间的列 下标从1开始
        // for(var i=1;i<footer_td.length-1;i++) {
        //     footer_td.eq(0).hide();
            footer_td.eq(1).hide();
        // }
        //设置跨列
        footer_td_1.attr('colspan', 2).show();
        //这里可以根据自己的表格来设置列的宽度 使对齐
        footer_td_1.attr('width', "1199px").show();
    }

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber: params.pageNumber, //页码
            sortName: params.sort,	//排序列名
            sortOrder: params.order,	//排序方式
            searchText: params.search,//搜索框参数
            startStopTime:$("#startStopTime").val(),
            classNumber:$("#classNumber").val(),
            className:$("#className").val(),
            trainingType:$("#trainingType").val(),
            plan:$("#plan").val(),
            place: $("#place").val()=='全部区域'?'':$("#place").val()
        };
        return temp;
    };
    return oTableInit;
};

function search() {
    $("#table_stud_dining_record").bootstrapTable('refresh');
    $('#table_stud_dining_record').bootstrapTable('selectPage', 1);
}

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
    window.location.href=ctx +'/classInfo/form.action?query=query&&id='+id;
    // window.location.href=ctx +'/stuDining/form.action?studentId='+id;
}
//实到人数详情
function showPlan(id){
    // alert(id)
    window.location.href=ctx +'/queryStatistics/form.action?id='+id;
    // window.location.href=ctx +'/stuDining/form.action?studentId='+id;
}
function renderRegPlace() {
    $.ajax({
        url:ctx+'/commonApi/regAddressList.action',
        type:'get',
        dataType:'json',
        async:false,
        success:function (data) {
            //console.log(eval(data));
            var regPlaceArr = eval(data);
            var temp = regPlaceArr.map(function (value,index) {
                return "<option value='"+value+"'>"+value+"</option>";
            });
            temp.push("<option value='全部区域'>全部区域</option>");
            //console.log(temp);
            var sysUserType = sessionStorage.getItem("sysUserType");

            $("#place").html(temp);
            if (sysUserType=="\"一般\""){
                $("#place").select().val('全部区域');
            } else {
                $("#place").select().val(sysUserType.substring(1,sysUserType.length-1));
            }
        },
        error:function () {
            console.log("请求地区列表失败")
        }
    });
}
function loginUserType() {
    $.ajax({
        url:ctx+'/commonApi/sysUserType.action',
        type:'get',
        dataType:'json',
        async:false,
        success:function (data) {
            //console.log(data);
            sessionStorage.setItem("sysUserType",JSON.stringify(data));
        },
        error:function () {
            console.log("请求用户数据失败")
        }
    });
}
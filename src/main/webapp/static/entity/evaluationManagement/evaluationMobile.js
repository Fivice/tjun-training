var ctx;

$(function() {
    ctx = getRootPath();
    initLayuiDate(initClassFundsTable);
});
//年月范围选择
var initLayuiDate = function (callback) {
    callback();
    laydate.render({
        elem: '#startStopTime'
        ,type: 'month'
        ,theme: '#6CA6CD'
        ,done: function(value, date){
            $("#startStopTime").prop("value",value);
        }

    });

}
var initClassFundsTable = function () {

    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

}
function getRootPath()
{
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    return window.location.protocol + '//' + window.location.host + '/'+ webName;
}
//
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            /*url: 'findTable.action',*/
            url: ctx +'/evaluationManagement/findTable.action',  //请求后台的URL（*）
            method: 'post',                       	//请求方式（*）
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
            pageList: [10],        	//可供选择的每页的行数（*）

            showColumns: false,                  	//是否显示所有的列
            showRefresh: false,                  	//是否显示刷新按钮
            minimumCountColumns: 2,             	//最少允许的列数
            clickToSelect: true,                	//是否启用点击选中行
            //height: 500,                        	//行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                   //每一行的唯一标识，一般为主键列
            showToggle:false,                    	//是否显示详细视图和列表视图的切换按钮
            cardView: false,

            detailView: false,                  		//是否显示父子表
            showExport: false,  //是否显示导出按钮
            exportDataType : "basic", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
            buttonsAlign:"right",  //按钮位置

            exportTypes:['excel'],
            Icons:'glyphicon-export',
            exportOptions:{
                // ignoreColumn: [0,1],  //忽略某一列的索引
                fileName: '评价管理',  //文件名称设置
                worksheetName: 'sheet1',  //表格工作区名称
                tableName: '',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                /*onMsoNumberFormat: DoOnMsoNumberFormat  */
            },
            columns: [
                {title: '班级编号', field: 'classInfo.classNumber'},
                {title: '班级名称', field: 'classInfo.className', formatter:function(value, row, index){
                        var temp = '<a style="color: #6CA6CD" href="javascript:void(0)" onclick=showImg(' + row.classInfo.id + ');>' + value + '</a>';
                        return temp;
                    }},
                {title: '评价人数', field: 'evaluationStudentCount'},
                {title: '实到人数', field: 'studentCount'},
                {title: '班主任', field: 'classInfo.teacherName'},
                { field: 'Button', title: '学员评价情况',
                    formatter: operateFormatter//自定义方法，添加详情按钮
                },
                { field: 'Button', title: '课程评价结果',
                    formatter: operate//自定义方法，添加详情按钮
                },
                { field: 'Button', title: '教师评价结果',
                    formatter: operate2//自定义方法，添加详情按钮
                },
                { field: 'Button', title: '总体评价结果',
                    formatter: operate1//自定义方法，添加详情按钮
                },
                { field: 'Button', title: '总体评价结果建议',
                    formatter: operate3//自定义方法，添加详情按钮
                }
            ],//自定义方法，添加详情按钮
            // 注册加载子表的事件。注意下这里的三个参数！
            onExpandRow : function(index, row, $detail) {
                oInit.InitSubTable(index, row, $detail);
            },
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp1 = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            plan:$("#plan").val(),
            classNumber:$("#classNumber").val(),
            startStopTime:$("#startStopTime").val(),
            className:$("#className").val(),
            teacherName:$("#teacherName").val(),
        };
        return temp1;
    };

    return oTableInit;
};

Date.prototype.Format = function(format){
    var o = {
        "M+" : this.getMonth()+1, //month 
        "d+" : this.getDate(), //day 
        "h+" : this.getHours(), //hour 
        "m+" : this.getMinutes(), //minute 
        "s+" : this.getSeconds(), //second 
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
        "S" : this.getMilliseconds() //millisecond 
    }
    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
}
//学生评价情况
function operateFormatter (value, row, index) {
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"studentEvaluation('" + row.classInfo.id + "')\" title='学员评价情况'><i class=\"fa fa-external-link-square \"></i></button>";
    return result;
}

//课程评价结果
function operate (value, row, index) {
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"subjEvaluation('" + row.classInfo.id + "')\" title='课程评价结果'><i class=\"fa fa-send\"></i></button>";
    return result;
}

//教师评价结果
function operate2 (value, row, index) {
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"teacherEvaluation('" + row.classInfo.id + "')\" title='教师评价结果'><i class=\"fa fa-send-o (alias) [&#xf1d9;]\"></i></button>";
    return result;
}

//总体评价结果
function operate1 (value, row, index) {
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"resultEvaluation('" + row.classInfo.id + "')\" title='总体评价结果'><i class=\"fa fa-star\"></i></button>";
    return result;
}

//总体评价结果建议
function operate3 (value, row, index) {
    var result = "";
    result += "<button type=\"button\" class=\"btn btn-info\" onclick=\"resultEvaluation1('" + row.classInfo.id + "')\" title='总体评价结果'><i class=\"fa fa-star-half-empty (alias) [&#xf123;]\"></i></button>";
    return result;
}


//学生评价情况
function studentEvaluation(id) {
    window.location.href=ctx +'/evaluationManagement/findStudent.action?id='+id;
}

//课程评价结果
function subjEvaluation(id) {
    window.location.href=ctx +'/evaluationManagement/findSubjEvaluation.action?id='+id;
}

//教师评价结果
function teacherEvaluation(id) {
    window.location.href=ctx +'/evaluationManagement/findTeacherEvaluation.action?id='+id;
}


//总体评价结果
function resultEvaluation(id) {
    window.location.href=ctx +'/evaluationManagement/findResult.action?id='+id;
}

//总体评价结果建议
function resultEvaluation1(id) {
    window.location.href=ctx +'/evaluationManagement/findResult1.action?id='+id;
}
var ButtonInit = function() {
    var oInit = new Object();

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};
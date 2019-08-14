// $(function() {
//     var time = getyymm();
//     $("#month").prop("value", time);
// });
// //获取当前年月
// function getyymm(){
//     //创建日期对象
//     var date=new Date;
//     //获取年份
//     var yy=date.getFullYear();
//     //获取月份
//     var mm=date.getMonth()+1;
//     //如果月份小于10 前面加0
//     mm=(mm<10 ? "0"+mm:mm);
//     //返回日期
//     return (yy.toString()+"-"+mm.toString());
// }

//项目根目录
var path = $("#contextPath").val();
$(document).ready(function () {
    //初始化Table
    var oTable = new TableInit();
    oTable.Init();
});
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table_teach_dining').bootstrapTable({
            url: 'findTable1.action',  //请求后台的URL（*）
            method: 'get',                       	//请求方式（*）
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",						//数据类型
            toolbar: '#toolbar',                 	//工具按钮用哪个容器
            striped: true,                      	//是否显示行间隔色
            cache: false,                       	//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   	//是否显示分页（*）
            sortable: true,                    	    //是否启用排序
            sortOrder: "desc",                   	//排序方式
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
            //exportDataType : "selected", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项.
            buttonsAlign:"right",  //按钮位置
            //exportTypes:['excel','xlsx'],  //导出文件类型
            exportTypes:['excel'],
            Icons:'glyphicon-export',
            exportOptions:{
                ignoreColumn: [0,1],  //忽略某一列的索引
                fileName: '数据导出',  //文件名称设置
                worksheetName: 'sheet1',  //表格工作区名称
                tableName: '区域及教室资源',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                /*onMsoNumberFormat: DoOnMsoNumberFormat  */
            },
            columns: [
                {checkbox: true},
                {title: '流水号', field: 'number', sortable: false},
                {title: '教师姓名', field: 'teacherName', sortable: false},
                {title: '安排人姓名', field: 'arranger', sortable: false},
                {title: '班级名称', field: 'className',formatter:function(value, row, index){
                        var temp = '<a style="color: #6CA6CD" href="'+ctx+'/classInfo/form.action?query=query&&id='+row.classId+'">' + row.className + '</a>';
                        return temp;
                    }},
                {title: '就餐地点', field: 'area', sortable: false},
                { field: 'Button', title: '详情',
                      formatter: operate//自定义方法，添加详情按钮
                  }
            ],
            showExport: true,                     //是否显示导出
            exportDataType: "basic",              //basic', 'all', 'selected'.
            //exportTypes: ['excel', 'xlsx'],     //导出类型
            //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
            exportOptions: {
                ignoreColumn: [0, 0],            //忽略某一列的索引
                fileName: '教师就餐安排教师信息表',              //文件名称设置
                worksheetName: 'Sheet1',          //表格工作区名称
                tableName: '教师就餐安排教师信息表',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                //onMsoNumberFormat: DoOnMsoNumberFormat
            }
            //导出excel表格设置<<<<<<<<<<<<<<<<
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber: params.pageNumber, //页码
            sortName: params.sort,	//排序列名
            sortOrder: params.order,	//排序方式
            searchText: params.search,//搜索框参数
            schoolName : $("#schoolName").selected().val(),
            number : $("#number").val(),
            month : $("#month").val(),
            classId1:$("#classId1").val()
        };
        return temp;
    };
    return oTableInit;
};


function search() {
    $("#table_teach_dining").bootstrapTable('refresh');
    $('#table_teach_dining').bootstrapTable('selectPage', 1);
}

//删除
function teachDining_delete() {
    var rows = $('#table_teach_dining').bootstrapTable("getSelections")
    var ids = "";
    if (rows.length == 0) {
        layer.alert('请至少选择一条数据！', {
            icon: 5,
            title: "提示"
        });
    } else {
        for (var i = 0; i < rows.length; i++) {
            ids += rows[i].number.toString() + ","
            day = rows[i].day;
            time = rows[i].time;
        }
        ;
        layer.confirm('确认要删除吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    ids: ids
                },
                url: path + '/teachDin/delete.action?time='+time,
                success: function (result) {
                    if (result.code == 1) {
                        layer.msg('删除成功!', {
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
        });
    }
}


//修改
function teachDining_edit() {
    var rows = $('#table_teach_dining').bootstrapTable("getSelections")
    var value;
    if (rows.length == 0) {
        layer.alert('请选择一条数据！', {
            icon: 5,
            title: "提示"
        });
    } else if (rows.length > 1) {
        layer.alert('只能选择一条数据编辑！', {
            icon: 5,
            title: "提示"
        });
    } else {
        for (var i = 0; i < rows.length; i++) {
            value = rows[i].number;
            time = rows[i].time;
            layer_show('更新数据', path + '/teachDin/edit.action?number='+value+'&time='+time, 900, 480)
        }
    }
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
    window.location.href=ctx +'/teachDin/form.action?number='+number+'&time='+time+'&classId='+classId;
}

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
        $('#table_evaluate_project').bootstrapTable({
            url: path + '/evaluate/findTable.action',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            showPaginationSwitch: false,
            paginationPreText: "<",
            paginationNextText: ">",
            sortable: true,                     //是否启用排序
            sortName: "id",
            sortOrder: "desc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: 'limit',
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 20, 50],         //可供选择的每页的行数（*）
            //		search: true,                       //是否显示表格搜索
            //		strictSearch: true,					//设置为 true启用 全匹配搜索，否则为模糊搜索
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            contentType: "application/x-www-form-urlencoded", //解决POST提交问题
            columns: [
                // {checkbox: true},
                {title: '评价分类', field: 'type', sortable: true},
                {title: '评价项目', field: 'project', sortable: true},
                {title: '满分分值', field: 'score', sortable: true},
                {title: '备注', field: 'eRemark', sortable: true},
                {title: '大项', field: 'largeClass', visible:false}],
            showExport: false,                     //是否显示导出
            exportDataType: "basic",              //basic', 'all', 'selected'.
            exportTypes: ['excel', 'xlsx'],     //导出类型
            //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
            exportOptions: {
                ignoreColumn: [0, 0],            //忽略某一列的索引
                fileName: '培训类型列表',              //文件名称设置
                worksheetName: 'Sheet1',          //表格工作区名称
                tableName: '培训类型列表',
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
            largeClass: $("#sl").val()
        };
        return temp;
    };
    return oTableInit;
};

function search() {
    $("#table_evaluate_project").bootstrapTable('refresh');
    $('#table_evaluate_project').bootstrapTable('selectPage', 1);
}

//删除
function evaluate_delete() {
    var rows = $('#table_evaluate_project').bootstrapTable("getSelections")
    var ids = "";
    console.log(rows.length);
    console.log(rows);

    if (rows.length == 0) {
        layer.alert('请至少选择一条数据！', {
            icon: 5,
            title: "提示"
        });
    } else {
        for (var i = 0; i < rows.length; i++) {
            ids += rows[i].id.toString() + ","
        }
        ;
        console.log(ids);
        layer.confirm('确认要删除吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    ids: ids
                },
                url: path + '/evaluate/delete.action',
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
function evaluate_edit() {
    var rows = $('#table_evaluate_project').bootstrapTable("getSelections")
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
            value = rows[i].id;
            layer_show('更新数据', path + '/evaluate/' + value + '/edit.action', 900, 480)
        }
    }
}


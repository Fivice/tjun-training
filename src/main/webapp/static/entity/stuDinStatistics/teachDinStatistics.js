//项目根目录
var path = $("#contextPath").val();
var teacheDinTable=function () {
    var oTable = new TableInit();
    oTable.Init();
}
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#teachDingTable').bootstrapTable({
            url: 'findTeachTable.action',  //请求后台的URL（*）
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
            showColumns: false,                  	//是否显示所有的列
            showRefresh: true,                  	//是否显示刷新按钮
            minimumCountColumns: 2,             	//最少允许的列数
            clickToSelect: true,                	//是否启用点击选中行
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
                // ignoreColumn: [0,0],  //忽略某一列的索引
                fileName: '数据导出',  //文件名称设置
                worksheetName: 'sheet1',  //表格工作区名称
                tableName: '教师就餐统计',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                /*onMsoNumberFormat: DoOnMsoNumberFormat  */
            },
            columns: [
                // {checkbox: true},
              [  {title: '就餐地点', field: 'list.diningPlace',valign:"middle",align:"center",rowspan: 2},
                  {title: "早餐",valign:"middle",align:"center",colspan: 3,rowspan: 1},
                  {title: "中餐",valign:"middle",align:"center",colspan: 3,rowspan: 1},
                  {title: "晚餐",valign:"middle",align:"center",colspan: 3,rowspan: 1},
                  {title: '实到合计金额', field: 'list.rDinner', valign:"middle",align:"center",rowspan: 2,formatter:function(value, row){
                          var temp = (row.list.rBreakfast)*(row.eatStandard0)+(row.list.rLunch)*(row.eatStandard1)+(row.list.rDinner)*(row.eatStandard2);
                          return temp;
                      }},
                  {title: '应到合计金额', field: 'list.rDinner',valign:"middle",align:"center",rowspan: 2 ,formatter:function(value, row){
                          var temp = (row.list.dBreakfast)*(row.eatStandard0)+(row.list.dLunch)*(row.eatStandard1)+(row.list.dDinner)*(row.eatStandard2);
                          return temp;
                      }},
                  {title: '就餐率%', field: 'list.rDinner',valign:"middle",align:"center",rowspan: 2,formatter:function(value, row){
                          var temp = (((row.list.rBreakfast)+(row.list.rLunch)+(row.list.rDinner))/((row.list.dBreakfast)+(row.list.dLunch)+(row.list.dDinner)));
                          var temps =(Number(temp)*Number(100)).toFixed(2);
                          return temps;
                      }}],[
                {title: '应到人次', field: 'list.dBreakfast',valign:"middle",align:"center" ,formatter:function(value, row){
                        var temp = (row.list.dBreakfast);
                        return temp;
                    }},
                {title: '实到人次', field: 'list.rBreakfast',valign:"middle",align:"center" ,formatter:function(value){
            if(value==null){
                return "0";
            }else {
                return value;
            }
        }},
                {title: '实到金额', field: 'list.rBreakfast', valign:"middle",align:"center",formatter:function(value, row){
                        var temp = (row.list.rBreakfast)*(row.eatStandard0);
                        return temp;
                    }},
                {title: '应到人次', field: 'list.dLunch', valign:"middle",align:"center",formatter:function(value, row){
                        var temp = (row.list.dLunch);
                        return temp;
                    }},
                {title: '实到人次', field: 'list.rLunch',valign:"middle",align:"center" ,formatter:function(value){
                        if(value==null){
                            return "0";
                        }else {
                            return value;
                        }
                    }},
                {title: '实到金额', field: 'list.rLunch',valign:"middle",align:"center",formatter:function(value, row){
                        var temp = (row.list.rLunch)*(row.eatStandard1);
                        return temp;
                    }},
                {title: '应到人次', field: 'list.dDinner',valign:"middle",align:"center",formatter:function(value, row){
                        var temp = (row.list.dDinner);
                        return temp;
                    }},
                {title: '实到人次', field: 'list.rDinner',valign:"middle",align:"center" ,formatter:function(value){
                        if(value==null){
                            return "0";
                        }else {
                            return value;
                        }
                    }},
                {title: '实到金额', field: 'list.rDinner',valign:"middle",align:"center",formatter:function(value, row){
                        var temp = (row.list.rDinner)*(row.eatStandard2);
                        return temp;
                    }}
               ]
                ],//自定义方法，添加详情按钮
            // 注册加载子表的事件。注意下这里的三个参数！
            onExpandRow : function(index, row, $detail) {
                oInit.InitSubTable(index, row, $detail);
            }
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            // pageSize: params.limit,   //页面大小
            // pageNumber: params.pageNumber, //页码
            startStopTime:$("#startStopTime1").val()
        };
        return temp;
    };
    return oTableInit;
};


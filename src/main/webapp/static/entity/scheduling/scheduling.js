
$(function() {

    initScheduling();

});
var initScheduling = function () {
    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    // 2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
}
function date(){
    //时间格式转换
    var newDate = dateToStr(new Date());
    //获取当天是星期几
    var ji = "日一二三四五六".charAt(new Date().getDay());
    alert(newDate+" 星期"+ji);
}

function dateToStr(datetime){
    var year = datetime.getFullYear();
    var month = datetime.getMonth()+1;//js从0开始取
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minutes = datetime.getMinutes();
    var second = datetime.getSeconds();
    if(month<10){
        month = "0" + month;
    }
    if(date<10){
        date = "0" + date;
    }
    if(hour <10){
        hour = "0" + hour;
    }
    if(minutes <10){
        minutes = "0" + minutes;
    }
    if(second <10){
        second = "0" + second ;
    }

    var time = year+"-"+month+"-"+date+" "+hour+":"+minutes+":"+second;
    return time;
}

//
var TableInit = function() {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function() {
        $('#table_scheduling')
            .bootstrapTable(
                {
                    url : 'findTable.action', // 请求后台的URL（*）
                    method : 'get', // 请求方式（*）
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
                    pageList : [  10, 25, 50, 100 ], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : true, // 是否显示所有的列
                    showRefresh : true, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : true, // 是否启用点击选中行
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "schId", // 每一行的唯一标识，一般为主键列
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
                        fileName : '日程安排表', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '日程安排表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        {checkbox: true/*,formatter:stateFormatter*/},
                        {
                            field : 'day',
                            title : '日期',
                            sortable : true
                        },
                        {title: '星期', field: 'day',formatter : function(value, row, index) {
                                var d=new Date(value)
                                var weekday=new Array(7)
                                weekday[0]="星期天"
                                weekday[1]="星期一"
                                weekday[2]="星期二"
                                weekday[3]="星期三"
                                weekday[4]="星期四"
                                weekday[5]="星期五"
                                weekday[6]="星期六"
                                return weekday[d.getDay()];
                            }},
                        {
                            title: '时间段',
                            field: 'time',
                            sortable : true
                        },
                        {
                            title: '课程',
                            field: 'schContent',
                            sortable : true
                        },
                        {
                            title: '授课形式',
                            field: 'teachingForm',
                            sortable : true
                        },
                        {
                            title: '教师',
                            field: 'teacher',
                            sortable : true
                        },
                        {
                            title: '教师所属中心',
                            field: 'teacherDep',
                            sortable : true
                        },
                        {
                            title: '教室',
                            field: 'classroom',
                            sortable : true
                        },
                    /*    {title: '评测', field: 'evaluate',formatter : function(value, row, index) {
                                if (value == '0') {
                                    return "<span class='label label-warning'>否</span>";
                                }
                                if (value == '1') {
                                    return "<span class='label label-primary'>是</span>";
                                }
                                return "";
                            }},*/
                        {
                            title: '联系人',
                            field: 'classInfo.teacherName',
                            sortable : true
                        },
                        {field: 'evaluate', title: '是否参评', align:"center", valign:"middle",
                            formatter:function(value,row,index){
                                return "<span><input name='eva' type='hidden' inumber='"+row.schId+"' value='"+value+"'>" +"</span>" +
                                    "<span><input name='evaluate' type='checkbox' data-on-text='参评' data-off-text='不参评' data-on-color='success' data-off-color='warning'></span>";
                            }}
                        ],
                    onLoadSuccess:function(data){
                        renderSwitch();
                    },
                    onLoadError : function() {
                        layer.msg("日程数据加载失败！");
                    },

                    // 注册加载子表的事件。注意下这里的三个参数！
                    onExpandRow : function(index, row, $detail) {
                        oInit.InitSubTable(index, row, $detail);
                    },

                });
    };

/*    function stateFormatter(value, row, index) {
        var date = new Date();
        var time = date.Format("yyyy-MM-dd");
        // alert(time);
        if (row.day < time)
            return {
                disabled : true,
                //设置是否可用
                checked : false
                // 设置选中
            };
        return value;
    }
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
    }*/

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1,
            day: $("#day").val(),
            areaId:$("#areaId").val(),
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

//修改是否参评
function renderSwitch() {
    $("[name='eva']").each(function(i,item){
        var inumber=$(this).attr("inumber");
        console.log(inumber)
        /*alert(inumber);*/
        /*alert(typeof($(this).val()));*/
        if($(this).val()==1){
            $('[name="evaluate"]').eq(i).bootstrapSwitch('state',true);
            $('[name="evaluate"]').eq(i).on('switchChange.bootstrapSwitch', function (event,state) {

                    //改变是否参评
                $.ajax({
                    type : 'get',
                    url : ctx + '/scheduling/changeEva2.action',//发送请求
                    data : {
                        iNumber : inumber
                    },
                    dataType : "json",
                    success : function(result) {
                        /*alert(result.inventory.iState);*/
                        // $("#table_scheduling").bootstrapTable("destroy")
                        // initScheduling();
                         window.location.reload()//刷新当前页面.
                    },
                    error : function(error){
                        alert("加载失败");
                    }
                });

            });
        }
        if($(this).val()==0){
            $('[name="evaluate"]').eq(i).bootstrapSwitch('state',false);

            $('[name="evaluate"]').eq(i).on('switchChange.bootstrapSwitch', function (event,state) {

                //得到盘点数据
                $.ajax({
                    type : 'get',
                    url : ctx + '/scheduling/changeEva1.action',//发送请求
                    data : {
                        iNumber : inumber
                    },
                    dataType : "json",
                    success : function(result) {
                        /*alert(result.inventory.iState);*/
                        /*$("#table_scheduling").bootstrapTable("destroy")
                        initScheduling();*/
                         window.location.reload()//刷新当前页面.
                    },
                    error : function(error){
                        alert("加载失败");
                    }
                });

            });
        }

    })
    return(true);
}

//添加日程安排
function createSch(id){
    layer.open({
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        type : 2,
        title : '添加日程安排',
        skin: 'layui-layer-molv', //样式类名
        area : [ '900px', '700px' ],
        shade : 0,
        maxmin : true,
        content :  'add.action?id='+id,
        btn : [ '保存', '关闭' ] //只是为了演示
        ,
        yes : function(index,layero) {
            var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
            //进行表单验证
            yanzheng(inputForm);
            inputForm.bootstrapValidator('validate');
            var flag = inputForm.data('bootstrapValidator').isValid();
            if(flag) {
                inputForm.ajaxSubmit({
                    url:  'save.action',
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == 1) {
                            parent.layer.msg("添加成功!", {
                                shade: 0.3,
                                time: 1500
                            }, function () {
                                /*$("#table_scheduling").bootstrapTable("destroy")
                                initScheduling();*/
                                 window.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    }
                });
            }
        },
        btn2 : function() {
            layer.closeAll();
        },
        success : function(layero) {
            layer.setTop(layero); //重点2
        }
    });
}


// 编辑数据
function update(id) {
    var rows = $('#table_scheduling').bootstrapTable("getSelections")
    var value;
    console.log(rows)
    if (rows.length == 0) {
        layer.alert('请选择一条数据！', {
            icon : 5,
            title : "提示"
        });
    } else if (rows.length > 1) {
        layer.alert('只能选择一条数据编辑！', {
            icon : 5,
            title : "提示"
        });
    } else {
        for (var i = 0; i < rows.length; i++) {
            var schId = rows[0].schId;
            layer.open({
                id : 'LAY_layuipro', // 设定一个id，防止重复弹出
                type : 2,
                title : '编辑数据',
                skin : 'layui-layer-molv', // 样式类名
                area : [ '900px', '700px' ],
                shade : 0,
                maxmin : true,
                content : 'addUpdate.action?schId='+ schId +'&id='+id,
                btn : [ '保存', '关闭' ] // 只是为了演示
                ,
                yes : function(index, layero) {

                    var inputForm = $(window.frames["layui-layer-iframe" + index].document).contents().find("#form");
                    yanzheng(inputForm);
                    inputForm.bootstrapValidator('validate');
                    var flag = inputForm.data('bootstrapValidator').isValid();
                    if(flag) {
                        inputForm.ajaxSubmit({
                            url: 'update.action',
                            type: 'post',
                            dataType: 'json',
                            success: function (result) {
                                if (result.code == 1) {
                                    parent.layer.msg("更新成功!", {
                                        shade: 0.3,
                                        time: 1500
                                    }, function () {
                                        /*$("#table_scheduling").bootstrapTable("destroy")
                                        initScheduling();*/
                                         window.location.reload(); // 刷新父页面
                                    });
                                } else {
                                    layer.msg(result.message, {
                                        icon: 2,
                                        time: 1000
                                    });
                                }
                            }
                        });
                    }
                },
                btn2 : function() {
                    layer.closeAll();
                },
                success : function(layero) {
                    layer.setTop(layero); // 重点2
                }
            });

        }

    }

}
// 批量删除数据
function del1() {

    //使用getSelections即可获得，row是json格式的数据
    var getSelectRows = $("#table_scheduling").bootstrapTable('getSelections', function (row) {
        return row;
    });

    iNumbers = [];
    for (var i = 0; i < getSelectRows.length; i++) {
        iNumbers.push(getSelectRows[i].schId)
    }

    if (iNumbers.length == 0) {
        layer.alert('请至少选择一条数据！', {
            icon: 5,
            title: "提示"
        });
    } else {
        layer.confirm('您确定要删除数据吗？', {
            btn: ['确定', '关闭']
            //按钮
        }, function () {
            /*alert(iNumbers); */
            $.ajax({
                type: 'POST',
                url: 'delete.action',//发送请求
                data: {

                    iNumbers: iNumbers.toString()
                },
                /*  contentType:'application/json;charset=utf-8', */
                dataType: "json",
                success: function (result) {
                    if (result.code == 1) {
                        parent.layer.msg("删除数据成功!", {
                            shade: 0.3,
                            time: 1500
                        }, function () {
                            /*$("#table_scheduling").bootstrapTable("destroy")
                            initScheduling();*/
                             window.location.reload(); // 刷新父页面
                        });
                    } else {
                        layer.msg(result.message, {
                            icon: 2,
                            time: 1000
                        });
                    }

                }
            });

        });

    }
}


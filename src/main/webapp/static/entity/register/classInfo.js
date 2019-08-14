
$(function() {
    var now = new Date();

    /*   var year = now.getFullYear();       //年
       var month = now.getMonth() + 1;     //月
       var day = now.getDate();            //日
       var clock = year + "-";
       clock += month + "-";
       clock += day + " ";*/
    var date = new Date();//获取当前时间
    //date.setDate(date.getDate()-1);//设置天数 -1 天
    var time = date.Format("yyyy-MM-dd");
    $("#startStopTime").prop("value",time);
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
                    url: ctx + '/classInfo/findTable.action', // 请求后台的URL（*）
                    method: 'post', // 请求方式（*）
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType: "json", // 数据类型
                    toolbar: '#toolbar', // 工具按钮用哪个容器
                    striped: true, // 是否显示行间隔色
                    cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true, // 是否显示分页（*）
                    sortable: true, // 是否启用排序
                    sortOrder: "asc", // 排序方式
                    queryParamsType: 'limit',
                    queryParams: oTableInit.queryParams, // 传递参数（*）
                    sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber: 1, // 初始化加载第一页，默认第一页
                    pageSize: 5, // 每页的记录行数（*）
                    pageList: [5, 10,50], // 可供选择的每页的行数（*）
                    search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch: false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey: false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns: false, // 是否显示所有的列
                    showRefresh: true, // 是否显示刷新按钮
                    minimumCountColumns: 2, // 最少允许的列数
                    clickToSelect: true, // 是否启用点击选中行
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId: "", // 每一行的唯一标识，一般为主键列
                    showToggle: false, // 是否显示详细视图和列表视图的切换按钮
                    cardView: false, // 是否显示详细视图
                    detailView: false, // 是否显示父子表
                    showExport: false, // 是否显示导出按钮
                    buttonsAlign: "right", // 按钮位置

                    columns: [
                        //是否显示复选框
                        /* {checkbox: true, visible: true},*/
                        {title: '班级编号', field: 'classNumber',},
                        {title: '班级名称', field: 'className',},
                        {
                            title: '班主任', field: 'teacherName', /* formatter:function(value, row, index){
                           var obj =row.userId;
                                if (typeof obj == "undefined" || obj == null || obj == "") {
                                    var temp =row.teacherName;
                                }else{
                                    var temp = '<a style="color: #6CA6CD" onclick="searchTeacher('+obj+')">' + row.teacherName + '</a>';
                                }

                                return temp;
                            }*/
                        },
                        {title: '联系方式', field: 'phoneNumber',},
                    ],
                    onLoadSuccess: function (data) {

//								layer.msg("数据加载完成");
                        /*console.log(data)*/

                    },
                    onLoadError: function () {
                        /*layer.msg("数据加载失败！");*/
                    },

                    // 注册加载子表的事件。注意下这里的三个参数！
                    onExpandRow: function (index, row, $detail) {
                        oInit.InitSubTable(index, row, $detail);
                    },
                    onClickRow: function (row,$element) {
                        $('.info').removeClass('info');
                        $($element).addClass('info');
                        //就餐安排页面
                        /*var oTable = new TableInit1();
                        oTable.Init();*/
                        $("#id").prop("value",row.id);
                        $("#table_class_dining").bootstrapTable('refresh');
                        $("#repast").show();
                        $("#panel").css("display","block");//显示div


                 /*       $.ajax({
                            async:false,
                            type: 'get',
                            url: ctx+'/classDining/view2.action',//发送请求
                            data: {
                                id: row.id
                            },
                            success: function(result) {
                                $("#repast").html(result);
                                createTree(ctx+'/classInfo/findUnit.action',"haveclasstree");
                                formValidator();
                            }
                        });*/


                        //班级id
                        $("#classId").prop("value", row.id);
                        //培训费
                        if (row.trainingExpense != null) {
                            $("#trainingExpense").prop("value", row.trainingExpense);
                        } else {
                            $("#trainingExpense").prop("value", 0);
                        }
                        //其它费用
                        if (row.otherCharges != null) {
                            $("#otherCharges").prop("value", row.otherCharges);
                        } else {
                            $("#otherCharges").prop("value", 0);
                        }
                        //标间费用
                        if (row.interScaleFee != null) {
                            $("#interScaleFee").prop("value", row.interScaleFee);
                        } else {
                            $("#interScaleFee").prop("value", 0);
                        }
                        //单间费用
                        if (row.singleRoomCharge != null) {
                            $("#singleRoomCharge").prop("value", row.singleRoomCharge);
                        } else {
                            $("#singleRoomCharge").prop("value", 0);
                        }

                        //就餐状况
                        var s2 = $("#dining").val();
                        var total="0";
                        //就餐费用
                        var breakfast="0";
                        var lunch="0";
                        var dinner="0";
                        if(typeof row.breakfast != "undefined" && row.breakfast != null && row.breakfast != "") {
                            breakfast=row.breakfast;
                        }
                        if(typeof row.lunch != "undefined" && row.lunch != null && row.lunch != "") {
                            lunch=row.lunch;
                        }
                        if(typeof row.dinner != "undefined" && row.dinner != null && row.dinner != "") {
                            dinner=row.dinner;
                        }
                        //总就餐费用
                        var classNum=row.id;
                        if(typeof classNum != "undefined" || classNum != null || classNum != ""){
                            $.ajax({
                                type: 'POST',
                                dataType: 'json',
                                async: false,//设置为同步传输
                                data: {
                                    classNum: classNum,
                                },
                                url: ctx + '/register/findClassDiningList.action',
                                success: function (result) {
                                   /* console.log(result)*/
                                    //早中晚就餐次数
                                    var breakfastTotal= result.breakfastTotal;
                                    var lunchTotal= result.lunchTotal;
                                    var dinnerTotal= result.dinnerTotal;
                                    total=parseInt(breakfast)*breakfastTotal+parseInt(lunch)*lunchTotal+parseInt(dinner)*dinnerTotal;

                                    //查询报到集合
                                    $.ajax({
                                        type: 'POST',
                                        dataType: 'json',
                                        async: false,//设置为同步传输
                                        data: {
                                            classNum: classNum,
                                        },
                                        url: ctx + '/register/findRegisterList.action',
                                        success: function (data) {
                                            //console.log(data)

                                            //面板的详细信息
                                            //面板标题（班级名称）
                                            $("#title").html(row.className);
                                            //办班时间
                                            $("#classTime").html("办班时间："+row.startStopTime);
                                            //计划人数
                                            //报到人数
                                            //住宿人数
                                            //就餐人数
                                            //培训费
                                            //食宿费
                                            //其它费用
                                            $("#registerInfo").html(
                                                "<tr>" +
                                                "<td>"+row.plannedNumber+"</td>" +
                                                "<td>"+data.registerPeoples+"</td>" +
                                                "<td>"+data.hotelPeoples+"</td>" +
                                                "<td>"+data.diningPeoples+"</td>" +
                                                "<td>"+data.training_expense+"</td>" +
                                                "<td>"+data.scaleFee_total+"</td>" +
                                                "<td>"+data.other_charges+"</td></tr>"
                                            );
                                            //红枫路前台，默认单间
                                            if(data.hotelparam!=null){

                                                $("#hotel").html(
                                                    "<option value='1' data-options="+data.houseStandard1+">单间</option>" +
                                                    "<option value='0' data-options="+data.houseStandard0+">标间</option>" +
                                                    "<option value='2' data-options='0'>不住宿</option>"
                                                );

                                            }else{

                                                $("#hotel").html(
                                                    "<option value='0' data-options="+data.houseStandard0+">标间</option>" +
                                                    "<option value='1' data-options="+data.houseStandard1+">单间</option>" +
                                                    "<option value='2' data-options='0'>不住宿</option>"
                                                );

                                            }

                                        }
                                    });



                                }
                            });
                        }

                        //住宿状况
                        var s = $("#hotel").val();
                        if (s == "0") {
                            //标间住宿费用
                            if (row.interScaleFee != null) {
                                $("#scaleFee").prop("value", row.interScaleFee);
                            } else {
                                $("#scaleFee").prop("value", 0);
                            }
                            //住宿地点
                            if (row.hotelPlace != null) {
                                $("#hotelPlace").prop("value", row.hotelPlace);
                            } else {
                                $("#hotelPlace").prop("value", "");
                            }
                        } else if (s == "1") {
                            //单间住宿费用
                            if (row.singleRoomCharge != null) {
                                $("#scaleFee").prop("value", row.singleRoomCharge);
                            } else {
                                $("#scaleFee").prop("value", 0);
                            }
                            //住宿地点
                            if (row.hotelPlace != null) {
                                $("#hotelPlace").prop("value", row.hotelPlace);
                            } else {
                                $("#hotelPlace").prop("value", "");
                            }
                        }else{
                            $("#scaleFee").prop("value", 0);
                            //住宿地点
                            $("#hotelPlace").prop("value", "");
                        }

                        //就餐
                        if (s2 == "1") {
                            //就餐地点
                            if (row.diningPlace != null) {
                                $("#diningPlace").prop("value", row.diningPlace);
                            } else {
                                $("#diningPlace").prop("value", "");
                            }

                            var fee=parseInt(breakfast)+parseInt(lunch)+parseInt(dinner);
                            //就餐费用
                            $("#interScaleFee2").prop("value", fee);


                            //总就餐费用
                            $("#foodTotal").prop("value", total);

                            //不就餐
                        } else if (s2 == "2") {
                            //就餐费用(/天)
                            $("#interScaleFee2").prop("value", "0");
                            //总就餐费用
                            $("#foodTotal").prop("value", "0");
                            //就餐地点
                            $("#diningPlace").prop("value", "");
                        }

                        //总就餐费用隐藏域
                        $("#foodTotal2").prop("value", total);
                        //就餐地点隐藏域
                        $("#dPlace").prop("value", row.diningPlace);
                        //住宿地点隐藏域
                        $("#hPlace").prop("value", row.hotelPlace);

                        //培训天数
                        /*$("#dayNum").prop("value", row.dayNum);*/
                        $("#dNum").prop("value", row.dayNum);

                        //获取报名结束时间
                        var startStopTime = row.startStopTime.split("至")[1].trim();
                        //获取当前时间
                        var currentTime=new Date().Format("yyyy-MM-dd").toString().trim();
                        //console.log("日期差="+getDays(currentTime,startStopTime));
                        /*var tt = DateDiff(currentTime,startStopTime)*1;*/
                        /*var tt = getDays(currentTime,startStopTime);*/
                        var tt = row.dayNum-1;
                        //住宿天数
                        $("#dayNum").prop("value", tt);
                        //住宿天数隐藏域
                        $("#dayNum1").prop("value", tt);
                        //住宿增加天数
                        $("#increaseDay").prop("value", row.increaseDay);
                        /*var day=row.dayNum*1+row.increaseDay*1;*/
                        /*var day=(row.dayNum*1+row.increaseDay*1);*/
                        var day=(tt*1+row.increaseDay*1);

                        //实际住宿天数
                        $("#dayInfact").prop("value", day);

                        // （就餐费用+住宿费用）
                        /*var total=fee+parseInt($("#scaleFee").val());*/

                        var obj=row.otherCharges;
                        if(typeof obj == "undefined" || obj == null || obj == ""){
                            obj="0";
                        }
                        /*  // 总就餐费用+（住宿费用）*（培训天数+食宿增加天数）+其它费用+培训费
                          $("#chargesTotal").prop("value", total*1+parseInt($("#scaleFee").val())*day+parseInt(obj)*1+parseInt($("#trainingExpense").val())*1);*/

                        // 总就餐费用+总住宿费用+其它费用+培训费
                        $("#chargesTotal").prop("value", $("#foodTotal").val()*1+parseInt($("#scaleFee").val()*day)+parseInt(obj)*1+parseInt($("#trainingExpense").val())*1);
                        //报名地点
                        $("#place").prop("value", row.regPlace);

                        //总住宿费(住宿费用*天数)
                        $("#scaleFeeTotal").prop("value",  $("#scaleFee").val()*day);

                        //食宿费(总住宿费用+总就餐费用)
                        $("#ssf").prop("value",  $("#scaleFeeTotal").val()*1+$("#foodTotal").val()*1);


                    }



                });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            //报名时间
            time:$("#startStopTime").val(),
            //报名地点
            regPlace:$("#regPlace").val(),
            //搜索状态码
            searchStatus: $("#search-status").val(),
            //排序
            order:"order"
        };

        return temp;
    };

    return oTableInit;
};


function operateFormatter (value, row, index) {
    var id = value;
    var result = "";
    result += "<span class='label label-info' onclick=\"EditViewById('" + row.id + "')\" title='查看详情'><i class=\"fa fa-chevron-circle-right\"></i></span>";
    return result;
} ;



var ButtonInit = function() {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};

//查看教师
function searchTeacher(id){
    layer.open({
        skin: 'layui-layer-lan',
        area: ['520px', '500px'], //宽高
        type: 2,
        title: '教师详细信息',
        content: ctx+'/teacherInfo/addUpdate.action?tiId=' + id
    });

}

//详情
function EditViewById(id){
    window.location.href=ctx +'/classInfo/form.action?id='+id;
}


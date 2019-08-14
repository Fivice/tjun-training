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
                    url : ctx+'/signUpManager/signUpStudentList.action', // 请求后台的URL（*）
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
                    showExport : phoneOrPc(), // 是否显示导出按钮
                    // exportDataType : "selected", //basic'导出当前页,
                    // 'all'导出全部, 'selected'导出选中项.
                    buttonsAlign : "right", // 按钮位置
                    // exportTypes:['excel','xlsx'], //导出文件类型
                    exportTypes : [ 'excel' ],
                    Icons : 'glyphicon-export',
                    exportOptions : {
                        ignoreColumn : [ 0, 0 ], // 忽略某一列的索引
                        fileName : '学生信息表', // 文件名称设置
                        worksheetName : 'sheet1', // 表格工作区名称
                        tableName : '学生信息表',
                        excelstyles : [ 'background-color', 'color',
                            'font-size', 'font-weight' ],
                        /* onMsoNumberFormat: DoOnMsoNumberFormat */
                    },
                    columns : [
                        //是否显示复选框
                        //{checkbox: true, visible: true},
                        {title: '学生姓名', field: 'student.siName',formatter:function (value, row, index){
                                var temp = '<a style="color: #6CA6CD" href="../student/form.action?query=query&id='+row.student.siId+'">' + row.student.siName + '</a>';
                                return temp;
                            }},
                        {title: '流水号', field: 'number'},
                        {title: '单位', field: 'student.unitName'},
                        //{title: '工作部门', field: 'student.deparentmentName'},
                        {title: '手机', field: 'student.phoneNumber'},
                      //  {title: 'Email', field: 'student.email'},
                        {title: '身份证', field: 'student.siIDNumber',cellStyle:{
                                css:{"mso-number-format":"\\@"}
                            }},
                        {title: '就餐', field: 'dining',formatter:function (value, row, index) {
                                if (value == 1) {
                                    return "是";
                                }
                                if (value == 2) {
                                    return "否";
                                }
                            }},
                        {title: '就餐费', field: 'foodTotal'},
                        {title: '住宿', field: 'hotel',formatter:function (value, row, index) {
                                if (value == 0) {
                                    return "标间";
                                }
                                if (value == 1) {
                                    return "单间";
                                }
                                if (value == 2) {
                                    return "不住宿";
                                }
                            }},
                        // {title: '住宿地点', field: 'hotelPlace'},
                        {title: '住宿费', field: 'scaleFeeTotal'},
                        {title: '培训费', field: 'trainingExpense'},
                        {title: '其他费', field: 'otherCharges'},
                        {title: '前台收费', field: 'totalCost',formatter:function (value,row,index) {
                                var totalCost = parseFloat(row.foodTotal)+parseFloat(row.otherCharges)+parseFloat(row.trainingExpense)+parseFloat(row.scaleFeeTotal)
                                //console.log(totalCost)
                                return '<span class="font-bold text-danger">'+totalCost+'</span>'
                            }},
                        {title: '支付方式', field: 'pay',formatter:function (value, row, index) {
                                var temp;
                                switch (value) {
                                    case '1': temp = "<span class='label label-info'>前台缴费</span>";break;
                                    case '2': temp = "<span class='label label-warning'>未 缴 费</span>";break;
                                    case '3': temp = "<span class='label label-primary'>国网商旅</span>";break;
                                    case '4': temp = "<span class='label label-success'>统一转账</span>";break;
                                    default: temp = "<span class='label label-danger'>未  知</span>";break;
                                }
                                return temp;
                            }},
                        {title: '报到时间', field: 'reportTime'},
                        {title: '报到地点', field: 'place'},
                        // {title: '缴费', field: 'pay',formatter:function (value, row, index) {
                        //         var totalCost = parseFloat(row.foodTotal)+parseFloat(row.otherCharges)+parseFloat(row.trainingExpense)+parseFloat(row.scaleFeeTotal);
                        //         var studentId = row.student.siId;
                        //         var chargesInfo = new chargesManager(row.trainingExpense,row.scaleFeeTotal,row.foodTotal,row.otherCharges,row.dining,row.hotel,row.student.siId,value,row.number)
                        //         console.log(chargesInfo)
                        //         if (value == 1) {
                        //             var temp = "<button class='btn btn-green btn-sm' onclick='editPay(this)' >已缴</button>"
                        //             return temp;
                        //         }
                        //         if (value == 2) {
                        //             var temp = "<button class='btn btn-danger btn-sm' onclick='editPay(this)' >缴费</button>"
                        //             return temp;
                        //         }
                        //
                        //     }},
                        {field: 'Button', title: '补卡',
                            formatter: operateFormatter//自定义方法，添加详情按钮
                        },

                        {field: 'Button', title: '费用修改',formatter:function (value, row, index) {
                                return "<button class='btn btn-success btn-sm' onclick=\"modificatio_fee('" + row.student.siId + "'," + "'" + row.classInfo.id + "')\">费用修改</button>";
                            }
                            },

                        {title: '调班',
                            formatter: function (value,row,index) {
                                var temp ={
                                    siId:row.student.siId,
                                    name:row.student.siName
                                };
                                return "<button class='btn btn-success btn-sm' name='"+JSON.stringify(temp)+"' onclick='changeClass(this)'>调班</button>"
                            }
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
            siName: $("#siName").val(),
            unitName: $("#unitName").val(),
            pay: $("#payChose").select().val(),
            hotel: $("#hotel").select().val(),
            dining: $("#dining").select().val()
        };
        // console.log(temp)
        return temp;
    };

    return oTableInit;
};



var ButtonInit = function() {
    var oInit = {};
    var postdata = {};

    oInit.Init = function() {
        // 初始化页面上面的按钮事件
    };

    return oInit;
};

/*判断终端是手机还是电脑--用于判断文件是否导出(电脑需要导出)*/
function phoneOrPc(){
    var sUserAgent = navigator.userAgent.toLowerCase();
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
    var bIsAndroid = sUserAgent.match(/android/i) == "android";
    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
        return false;
    } else {
        return true;
    }
}

function payCost(studentId,cost) {
    //开启模态窗
    $("#payModal").modal("show");

    $("#payConfirmContext").html("<span>确认该学生已经缴纳&nbsp <p id='payNumbers' class='inline text-danger'>"+cost+"</p> &nbsp元 </span>"+"<input id='payStudentId' class='hidden' value='"+studentId+"'>");

}
function chargesManager(trainingExpense,scaleFeeTotal,foodTotal,otherCharges,dining,hotel,siId,pay,number)
{
    this.trainingExpense=trainingExpense;
    this.scaleFeeTotal=scaleFeeTotal;
    this.foodTotal=foodTotal;
    this.otherCharges=otherCharges;
    this.dining=dining;
    this.hotel=hotel;
    this.siId=siId;
    this.pay=pay;
    this.number = number;
}
function pushValueInPayFrom(value){
    var row = JSON.parse(value);
    sessionStorage.setItem("row",value);
    $("#stu-training-expense").val(row.classInfo.trainingExpense);
    $("#stu-hotel").val(row.scaleFeeTotal);
    $("#stu-dining").val(row.foodTotal);
    $("#stu-other").val(row.classInfo.otherCharges);
    $("#stu-total").val(row.trainingExpense+row.scaleFeeTotal+row.foodTotal+row.otherCharges);

    $("#stu-hotel-select").val(0);
    hotelAjax();
    $("#stu-dining-select").val(1);
    diningAjax();

    $("#stu-siId").val(row.student.siId);
    $("#stu-pay").val(row.pay);
    if (row.number!= null&&row.number!='') {
        $("#stu-series-number").val(row.number)
        $("#stu-series-number").attr("readonly","readonly")
    }


}
function cleanValueInPayFrom() {
    sessionStorage.removeItem("row");
    $("#stu-training-expense").val("");
    $("#stu-hotel").val("");
    $("#stu-dining").val("");
    $("#stu-other").val("");
    $("#stu-total").val("");
    $("#stu-series-number").removeAttr("readonly");
    $("#stu-series-number").val("");

    $("#stu-hotel-select").val("");
    $("#stu-dining-select").val("");

    $("#stu-siId").val("");
    $("#stu-pay").val("");

}

//补卡按钮
function operateFormatter (value, row, index) {
    return "<button class='btn btn-success btn-sm' onclick=\"cardReplacement('" + row.student.siId + "',"+"'"+row.classInfo.id+"','"+row.student.siName+"','"+row.number+"')\">补卡</button>";
} ;

//补卡
function cardReplacement(siId,classId,siName,number) {
    $("#student-id").prop("value",siId);
    $("#class-id").prop("value",classId);
    $("#modal-title").html("<i class=\"fa fa-asterisk\"></i>&nbsp;&nbsp;[ "+siName+" ]<span style=\"color: #6CA6CD;\">&nbsp;&nbsp;原流水号："+number+"</span>");
    $('#bukaModal').modal("show");
    //console.log("学员姓名="+siName,"流水号="+number);
    //console.log(siId,classId);
    //replacementCard(siId,classId);
}

//修改费用
function modificatio_fee(siId,classId) {
   /* $('#payModificatio').modal("show");*/
    window.location.href=ctx+"/signUpManager/modificatio_fee.action?classId="+classId+"&siId="+siId;
}


//根据学生id和班级id查询报到和学生流水号相关信息
function replacementCard(siId,classId) {
    var siId = $("#student-id").val();
    var classId = $("#class-id").val();
    var studentCardNumber = $("#stu-card").val();
    //流水号为空
    if (studentCardNumber == "") {
        $("#msg").html("流水号为空");
        $("#tsmsg").css("display","block");
        //流水号为非数字
    }else if(!/^[0-9]+$/.test(studentCardNumber.trim())){
        $("#msg").html("非法的流水号");
        $("#tsmsg").css("display","block");
    }else{
        $("#msg").html("");
        $("#tsmsg").css("display","none");

        layer.msg('确定后，原卡将无法继续使用', {
            time: 0 //不自动关闭
            ,btn: ['确定', '取消']
            ,yes: function(index){
                //补卡
                $.ajax({
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: ctx + "/signUpManager/cardReplacement.action",//url
                    data: {
                        siId: siId,
                        classId: classId,
                        studentCardNumber: studentCardNumber,
                    },
                    success: function (result) {
                        //console.log(result)
                        //不可以补卡的状态
                        if(result.status==-1){
                            $("#msg").html(result.message);
                            $("#tsmsg").css("display","block");
                        }
                        //可以补卡的状态
                        if(result.status==0){

                            $("#msg").html("");
                            $("#tsmsg").css("display","none");
                            $('#bukaModal').modal("hide");
                            layer.msg("补卡成功")
                        }

                    }
                });

                layer.close(index);
            }
        });


}

}

function editPay(obj){
    //获取表格数据
    var rows = $("#table").bootstrapTable("getData");
    //获取按钮点击时所在行的序号
    var index = obj.parentNode.parentNode.getAttribute("data-index");

    var row = rows[index];
    console.log(row);
    if(row.pay === "2"){
        //开启模态窗
        cleanValueInPayFrom();
        $("#payModal").modal("show");
        //此时学生交费状态为已缴费，下面将提示确认修改学生为已缴费

        pushValueInPayFrom(JSON.stringify(row));

    }
    if(row.pay === "1"){
        layer.msg("该学生已缴费");
        cleanValueInPayFrom();
        //$("#payModal").modal("show");
        //此时学生为已缴费，下面将提示改为未交费

        //pushValueInPayFrom(JSON.stringify(row));
    }

}
function addRegister() {
    //开启模态窗
    $("#signUpModal").modal("show");

}

$(document).ready(function(){
    $("#payConfirm").click(function(){
        var trainingExpense = $("#stu-training-expense").val()
        var other = $("#stu-other").val()
        var siId = $("#stu-siId").val()
        var pay = $("#stu-pay").val()
        var dining = $("#stu-dining-select").val()
        var hotel = $("#stu-hotel-select").val()
        var diningFee = $("#stu-dining").val()
        var hotelFee = $("#stu-hotel").val()
        var serialNumber = $("#stu-series-number").val()

        // console.log(trainingExpense+"/"+other+"/"+siId+"/"+pay+"/"+dining+"/"+hotel+"/"+diningFee+"/"+hotelFee+"/"+serialNumber)

        //就餐和住宿非空判断
        if (serialNumber == "") {
            // $("#stu-serial-number").css("border-color","red")
            layer.msg("流水号不能为空")
        }else if ($("#userInfo-supType").val()!="前台"||$("#userInfo-userType").val()!=$("#reportPlace").val()){
            layer.msg("对不起，您没有缴费操作权限，请到报到点前台完成缴费操作")
        } else{
            //请求缴纳费用
            $.ajax({
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: ctx+"/signUpManager/payConfirm.action" ,//url
                data: {"studentId": siId,"payStatus": pay,"trainingExpense": trainingExpense,"otherCharges": other,"dining": dining,"hotel": hotel,"diningFee":diningFee,"hotelFee":hotelFee,"serialNumber":serialNumber},
                success: function (result) {
                    // console.log("get :"+result.message)
                    switch (result.message) {
                        case 0:layer.msg("异常!报名信息为空");break;
                        case 1:layer.msg("异常!报名信息报名时间为空");break;
                        case 2:layer.msg("流水号不存在");break;
                        case 3:layer.msg("操作成功");break;
                        case 4:layer.msg("操作成功");break;
                        case 5:layer.msg("异常!流水号已经被他人占用");break;
                        case 6:layer.msg("异常!流水号表存在重复流水号,请联系管理员");break;
                        case 7:layer.msg("已经绑定过流水号");break;
                    }
                    //关闭模态窗
                    $("#payModal").modal("hide");
                    //清理模态框数据
                    cleanValueInPayFrom();
                    //刷新表单
                    refreshList();
                },
                error : function() {
                    //layer.alert("异常！请联系管理者");
                }
            });

        }
    });

});
var hotelAjax = function(){
    // console.log("住宿状态："+$("#stu-hotel-select").val())
    var row = JSON.parse(sessionStorage.getItem("row"));
    var hotelSelect = $("#stu-hotel-select").val();
    var diningSelect = $("#stu-dining-select").val();
    if (hotelSelect != ""){
        $.ajax({
            type:"GET",
            url:ctx+"/signUpManager/calculatePayInfo.action",
            dataType:"json",
            data:{"hotelSelect":hotelSelect,"diningSelect":diningSelect,"siId":row.student.siId},
            success:function (data) {
                // console.log(data)
                $("#stu-dining").val(data.payInfo.diningFee);
                $("#stu-hotel").val(data.payInfo.hotelFee);
                var trainingExpense = $("#stu-training-expense").val();
                var otherCharges = $("#stu-other").val();
                $("#stu-total").val(parseInt(trainingExpense)+parseInt(otherCharges)+data.payInfo.diningFee+data.payInfo.hotelFee)
            }
        })
    }
};
var diningAjax = function(){
    // console.log("就餐状态："+$("#stu-dining-select").val())
    var row = JSON.parse(sessionStorage.getItem("row"));
    var diningSelect = $("#stu-dining-select").val()
    var hotelSelect = $("#stu-hotel-select").val()
    if (diningSelect != ""){
        $.ajax({
            type:"GET",
            url:ctx+"/signUpManager/calculatePayInfo.action",
            dataType:"json",
            data:{"hotelSelect":hotelSelect,"diningSelect":diningSelect,"siId":row.student.siId},
            success:function (data) {
                // console.log(data)
                $("#stu-dining").val(data.payInfo.diningFee)
                $("#stu-hotel").val(data.payInfo.hotelFee)
                var trainingExpense = $("#stu-training-expense").val()
                var otherCharges = $("#stu-other").val()
                $("#stu-total").val(parseInt(trainingExpense)+parseInt(otherCharges)+data.payInfo.diningFee+data.payInfo.hotelFee)
            }
        })
    }
};
$(function () {
    //住宿改变事件触发

    $("#stu-hotel-select").click(function () {
        hotelAjax();
    });
    //就餐改变事件触发
    $("#stu-dining-select").click(function () {
        diningAjax();
    });
});
//刷新表单
function refreshList(){
    $("#table").bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
}
//清空input
function reset(){
    $("input").prop("value","");
    $("#hotel").select().val("-1");
    $("#dining").select().val("-1");
    $("#payChose").select().val("-1");
}
function changeClass(obj) {
    $("#changeClass").modal("show");
    $("#changeToClassList").bootstrapTable("destroy");
    initClassListTable.initTable("changeToClassList");
    console.log(obj.parentNode.parentNode);
    //获取点击调班行学员id,将其放入sessionStorage中
    var temp = JSON.parse(obj.getAttribute("name"));
    console.log(temp);
    $("#changeClassLabel").html("<i class='fa fa-refresh'></i> 调班 [ 学员：<span style='color: #0d6aad'>"+temp.name+"</span>, 原班级：<span style='color: #0d6aad'>"+$("#table").bootstrapTable("getData")[0].classInfo.className+"</span> ]");
    sessionStorage.setItem("selectedSiId",temp.siId);
}
function confirmChange() {
    var siId = sessionStorage.getItem("selectedSiId");
    var oldClassId = $("#table").bootstrapTable("getData")[0].classInfo.id;
    //console.log(oldClassId);
    var newClassId = sessionStorage.getItem("newClassId");
    console.log("学员id："+siId+";原班级id:"+oldClassId+";新班级id:"+newClassId);
    //学员id,原班级id和新班级id不能为空
    if (siId == null){
        layer.open({
            content:"没有获取到学员信息，请返回刷新"
        })
    } else if(oldClassId == null){
        layer.open({
            content:"没有获取到原班级信息，请返回刷新"
        })
    } else if (newClassId == null){
        layer.open({
            content:"请选择需要调至的班级！"
        })
    } else {
        console.log("调班确认");
        $.ajax({
            url:ctx+'/signUpManager/changeClass.action',
            type:'post',
            dataType:'json',
            data:{siId:siId,oldClassId:oldClassId,newClassId:newClassId},
            success:function (data) {
                console.log(data);
                if (data.msg == "SUCCESS") {
                    $("#changeClass").modal("hide");
                    $("#table").bootstrapTable("refresh");

                    layer.open({
                        content:"学员调班成功！"
                    })
                }
            },
            error:function (e) {
                layer.open({
                    content:'调班失败，请刷新页面重新操作！'
                })
            }
        })
    }
}
//当退出模态框时 移除 sessionStorage 里的selectedSiId、newClassId
$(function () {
    $('#changeClass').on('hidden.bs.modal', function (e) {
        sessionStorage.removeItem("selectedSiId");
        sessionStorage.removeItem("newClassId");
    });
});
function changeClassSearch() {
    $("#changeToClassList").bootstrapTable("destroy");
    initClassListTable.initTable("changeToClassList");
};
$(function () {
    initCondition();
    laydate.render({
        elem: '#changeClass-startTime'
        ,type: 'month'
        ,theme: '#6CA6CD'
        ,value: sessionStorage.getItem("startTime")
        ,isInitValue: true
        ,done: function(value, date, endDate){
            //点击事件 点选时间 后 将session中的时间更新
            sessionStorage.setItem("startTime",value);
            console.log(sessionStorage.getItem("startTime"));
            changeClassSearch();
        }
    });
});
//session存储查询条件
var initCondition = function () {
    //如果是首次加载则初始化session中的时间为当前日期
    if (sessionStorage.getItem("startTime") == null){
        sessionStorage.setItem("startTime",new Date().Format("yyyy-MM"));
        console.log(sessionStorage.getItem("startTime"))
    }
};
$(function () {
    $("#hotel").change(function () {
        $("#table").bootstrapTable("refresh");
    });
    $("#dining").change(function () {
        $("#table").bootstrapTable("refresh");
    });
    $("#payChose").change(function () {
        $("#table").bootstrapTable("refresh");
    });
});
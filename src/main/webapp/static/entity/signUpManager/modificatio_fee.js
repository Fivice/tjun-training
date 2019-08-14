$(function() {
    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

});
//
var TableInit = function() {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function() {
        $('#table')
            .bootstrapTable(
                {
                    url : ctx+'/signUpManager/findClassDiningTable.action', // 请求后台的URL（*）
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

                    sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, // 初始化加载第一页，默认第一页
                    pageSize : 10, // 每页的记录行数（*）
                    pageList: [10, 20, 50, 120], // 可供选择的每页的行数（*）
                    search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch : false, // 启用全匹配搜索，否则为模糊搜索
                    searchOnEnterKey : false, // 按回车触发搜索方法，否则自动触发搜索方法
                    showColumns : false, // 是否显示所有的列
                    showRefresh : true, // 是否显示刷新按钮
                    minimumCountColumns : 2, // 最少允许的列数
                    clickToSelect : true, // 是否启用点击选中行
                    // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "", // 每一行的唯一标识，一般为主键列
                    showToggle : false, // 是否显示详细视图和列表视图的切换按钮
                    cardView : false, // 是否显示详细视图
                    detailView : false, // 是否显示父子表
                    showExport : false, // 是否显示导出按钮
                    // exportDataType : "selected", //basic'导出当前页,
                    // 'all'导出全部, 'selected'导出选中项.
                    buttonsAlign : "right", // 按钮位置
                    columns : [
                        {title: '日期', field: 'day'},
                        {title: '<input type="checkbox" id="breakfast_allChecked">全选&nbsp;&nbsp;早餐', field: 'breakfast',formatter:function (value,row,index) {
                               /* return "<input value="+row.breakfast+" type='checkbox' name='breakfast'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.breakfast;*/
                                if(row.isbreakfast==1){
                                    return "<input checked value="+row.breakfast+" type='checkbox' name='breakfast'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.breakfast;
                                }else{
                                    return "<input value="+row.breakfast+" type='checkbox' name='breakfast'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.breakfast;
                                }
                        }},
                        {title: '<input type="checkbox" id="lunch_allChecked">全选&nbsp;&nbsp;中餐', field: 'lunch',formatter:function (value,row,index) {
                                if(row.islunch==1){
                                    return "<input checked value="+row.lunch+" type='checkbox' name='lunch'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.lunch;
                                }else{
                                    return "<input value="+row.lunch+" type='checkbox' name='lunch'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.lunch;
                                }
                            /*return "<input value="+row.lunch+" type='checkbox' name='lunch'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.lunch;*/
                        }},
                        {title: '<input type="checkbox" id="dinner_allChecked">全选&nbsp;&nbsp;晚餐', field: 'dinner',formatter:function (value,row,index) {
                                if(row.isdinner==1){
                                    return "<input checked value="+row.dinner+" type='checkbox' name='dinner'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.dinner;
                                }else{
                                    return "<input value="+row.dinner+" type='checkbox' name='dinner'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.dinner;
                                }
                                /*return "<input value="+row.dinner+" type='checkbox' name='dinner'>&nbsp;&nbsp;&nbsp;&nbsp;标准/"+row.dinner;*/
                        }},

                    ],
                    onLoadSuccess : function(data) {

//								layer.msg("数据加载完成");
                        
                        countFee();


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
            classId: $("#classId").val()
        };
        // console.log(temp)
        return temp;
    };

    return oTableInit;
};

//计算费用
function countFee() {
   /* //获取早餐次数
    var breakfast = document.getElementsByName("breakfast");
    //早餐次数
    var breakfast_counts = 0;
    for(var i=0;i<breakfast.length;i++){
        if(breakfast[i].checked){
            breakfast_counts++;
        }
    }*/

    /*//获取中餐次数
    var lunch = document.getElementsByName("lunch");
    //中餐次数
    var lunch_counts = 0;
    for(var i=0;i<lunch.length;i++){
        if(lunch[i].checked){
            lunch_counts++;
        }
    }*/

    /*//获取晚餐次数
    var dinner = document.getElementsByName("dinner");
    //晚餐次数
    var dinner_counts = 0;
    for(var i=0;i<dinner.length;i++){
        if(dinner[i].checked){
            dinner_counts++;
        }
    }*/
//查询报到登记的费用相关信息
    var siId = $("#siId").val();
    var classId = $("#classId").val();
    $.ajax({
        data: {siId:siId,classId:classId},
        dataType: 'json',
        type: 'post',
        url: ctx+"/signUpManager/findRegisterBysIdAndcId.action",
        success: function (result) {
          //console.log(result)
            //早餐和
            var breakfastSum = 0;
            $('input[name="breakfast"]:checked').each(function(){
                breakfastSum+=parseInt($(this).val());
            })
            //中餐和
            var lunchSum = 0;
            $('input[name="lunch"]:checked').each(function(){
                lunchSum+=parseInt($(this).val());
            })
            //晚餐和
            var dinnerSum = 0;
            $('input[name="dinner"]:checked').each(function(){
                dinnerSum+=parseInt($(this).val());
            })
            //设置就餐费
            $("#table_money").prop("value",breakfastSum+lunchSum+dinnerSum);

            //设置就餐费隐藏域
            $("#table_money2").prop("value",breakfastSum+lunchSum+dinnerSum);

            //住宿标准
            var houseStandard = $('#houseStandard') .val();
            //住宿天数
            var dayNum = $("#dayNum").val();

            //设置住宿费(住宿标准*住宿天数)
            if(typeof dayNum != "undefined" && dayNum != null && dayNum != ""){
                $("#accommodation").prop("value",parseInt(houseStandard)*parseInt(dayNum));
            }else{
                $("#accommodation").prop("value","0");
            }

            //住宿费
            var accommodation = $("#accommodation").val();
            //其它费用
            var other_fees = $("#other_fees").val();
            //培训费
            var groom_cost = $("#groom_cost").val();
            //就餐费
            var table_money = $("#table_money").val();

            //设置食宿合计(住宿费+就餐费)
            $("#expenses").prop("value",accommodation*1+table_money*1);
            //设置总费用(住宿费+就餐费+其它费用+培训费)
            $("#total_cost").prop("value",accommodation*1+table_money*1+other_fees*1+groom_cost*1);
            //需要补交(总费用-原报到登记费用)
            $("#after_payment").prop("value", $("#total_cost").val()-result.total);


            $("#result_total").prop("value",result.total);
        }
    })


}

//确认修改费用
function modification_fee() {

/*        layer.open({
            content: '确定修改该学生费用？',

            //确定修改费用
            yes: function(index, layero){*/


                //住宿费
                var accommodation = $("#accommodation").val();
                //住宿标准
                var houseStandard = $('#houseStandard') .val();
                //住宿天数
                var dayNum = $("#dayNum").val();

                if(Number(houseStandard)==0){

                    if((Number(accommodation)>0)){

                        layer.msg("请核对住宿费用(不住宿住宿费应为0)")
                    }else{
                        sureFee();
                    }

                }else{
                    //能被整除
                    if (Number(accommodation) % Number(houseStandard) === 0) {
                        //alert('accommodation能被houseStandard整除='+Number(accommodation) / Number(houseStandard));

                        //住宿天数*住宿标准 = 住宿费
                        if (Number(houseStandard)*Number(dayNum) == Number(accommodation)){
                            sureFee();
                        }else{
                            layer.msg("请核对住宿费用(住宿天数与住宿费核对不上)")
                        }




                    //不能被整除
                    } else {

                        //alert('accommodation不能被houseStandard整除='+Number(accommodation) / Number(houseStandard));
                       layer.msg("请核对住宿费用(住宿费无法和住宿标准核对上)")
                    }
                }





  /*          }

    })*/



}

//确定修改费用封装函数
function sureFee() {

    var rId = $("#rId").val();
    var classId = $("#classId").val();

    //其它费用
    var other_fees = $("#other_fees").val();
    //培训费
    var groom_cost = $("#groom_cost").val();
    //就餐费
    var table_money = $("#table_money").val();
    //住宿费
    var accommodation = $("#accommodation").val();

    //就餐费隐藏域
    var table_money2 = $("#table_money").val();


    //住宿方式
    var hotel = $('#houseStandard').find("option:selected").data("options");

    //1：已交，2:未交，3：国网商旅，4:统一转账
    layer.confirm('请确定缴费方式？', {
        btn: ['已缴费', '未缴费', '国网商旅', '统一转账'], //按钮
        area: '400px',
        skin: 'layui-layer-lan',
        //国网商旅
        btn3: function (index, layero) {
            $("#pay").prop("value", "3");

            //赋值
            $("#training").prop("value", $("#groom_cost").val());
            $("#other").prop("value", $("#other_fees").val());
            $("#scalefees").prop("value", $("#accommodation").val());
            $("#food").prop("value", $("#table_money").val());

            $("#myModal").modal();

            /*//缴费方式
            var pay = $("#pay").val();
            ajaxFee(rId,accommodation,other_fees,groom_cost,table_money,pay,hotel);*/
        },
        //统一转账
        btn4: function (index, layero) {
            $("#pay").prop("value", "4");


            //赋值
            $("#training").prop("value", $("#groom_cost").val());
            $("#other").prop("value", $("#other_fees").val());
            $("#scalefees").prop("value", $("#accommodation").val());
            $("#food").prop("value", $("#table_money").val());

            $("#myModal").modal();

            /* //缴费方式
             var pay = $("#pay").val();
             ajaxFee(rId,accommodation,other_fees,groom_cost,table_money,pay,hotel);*/
        }
        //已缴费
    }, function () {
        $("#pay").prop("value", "1");

        //缴费方式
        var pay = $("#pay").val();
        ajaxFee(rId,accommodation,other_fees,groom_cost,table_money,pay,hotel,table_money2);
        //未交费
    }, function () {
        $("#pay").prop("value", "2");

        //缴费方式
        var pay = $("#pay").val();
        ajaxFee(rId,accommodation,other_fees,groom_cost,table_money,pay,hotel,table_money2);

    });

}


//修改报到登记的请求
function ajaxFee(rId,accommodation,other_fees,groom_cost,table_money,pay,hotel,table_money2){
    var classId = $("#classId").val();
    var siId = $("#siId").val();
    $.ajax({
        data: {
            rId: rId,
            scaleFeeTotal: accommodation,
            otherCharges: other_fees,
            trainingExpense: groom_cost,
            foodTotal: table_money,
            pay: pay,
            hotel: hotel,
            table_money2:table_money2
        },
        dataType: 'json',
        type: 'post',
        url: ctx + "/signUpManager/feeRevision.action",
        success: function (result) {
            if (result.code == 1) {
                layer.msg('修改成功!', {
                    icon: 1,
                    time: 1000
                }, function () {
                    location.reload();
                });
               /* layer.msg("修改成功！");
                layer.close();*/
            }
        }

    })
}

// 复选框的全选和全不选
$(function() {

        // 获得复选框
        var $allChecked = $("#allChecked");
            $allChecked.click(function() {
            if ($allChecked.prop("checked") == true) {
                // 上面的复选框已被选中
                $(":checkbox[name='breakfast']").prop("checked", true);
                $(":checkbox[name='lunch']").prop("checked", true);
                $(":checkbox[name='dinner']").prop("checked", true);
            } else {
                // 上面的复选框没被选中
                //$(":checkbox[type='checkbox']").prop("checked", false);
                $(":checkbox[name='breakfast']").prop("checked", false);
                $(":checkbox[name='lunch']").prop("checked", false);
                $(":checkbox[name='dinner']").prop("checked", false);
            }
        });

    //早餐全选
    allChecked("#breakfast_allChecked","breakfast");

    //中餐全选
    allChecked("#lunch_allChecked","lunch");

    //晚餐全选
    allChecked("#dinner_allChecked","dinner");

});

//全选按钮函数封装
function allChecked(id,name) {
    // 获得复选框
    var $selectAll = $(id);
    $selectAll.click(function() {
        if ($selectAll.prop("checked") == true) {
            // 上面的复选框已被选中
            $(":checkbox[name="+name+"]").prop("checked", true);
        } else {
            // 上面的复选框没被选中
            $(":checkbox[name="+name+"]").prop("checked", false);
        }
    });

}


//模态框的计算费用按钮
function computationFee() {
    var total = 0;
    $('input[name="fee"]:checked').each(function(){
        total+=Number($(this).val());
    })
    //设置现有总费用(住宿费+就餐费+其它费用+培训费)
    $("#total_fee").prop("value",total);
    //需要补交(现有总费用-原报到登记费用)
    $("#payment").prop("value", Number($("#total_fee").val())- Number($("#result_total").val()));
}

//模态框确定按钮
function definition_fee() {

    //判断培训费是否选中(未选中则设置费用为0)
    if(!$('#training').is(':checked')) {
        $("#groom_cost").prop("value", 0);
    }
    //判断其它费用是否选中(未选中则设置费用为0)
    if(!$('#other').is(':checked')) {
        $("#other_fees").prop("value", 0);
    }
    //判断住宿费是否选中(未选中则设置费用为0)
    if(!$('#scalefees').is(':checked')) {
        $("#accommodation").prop("value", 0);
    }
    //判断就餐费是否选中(未选中则设置费用为0)
    if(!$('#food').is(':checked')) {
        $("#table_money").prop("value", 0);
    }
   /* $('#myModal').modal('hide');*/

    var rId = $("#rId").val();
    //住宿费
    var accommodation = $("#accommodation").val();
    //其它费用
    var other_fees = $("#other_fees").val();
    //培训费
    var groom_cost = $("#groom_cost").val();
    //就餐费
    var table_money = $("#table_money").val();

    //住宿方式
    var hotel = $('#houseStandard').find("option:selected").data("options");
    //缴费方式
    var pay = $("#pay").val();
    var table_money2 = $("#table_money2").val();

    ajaxFee(rId,accommodation,other_fees,groom_cost,table_money,pay,hotel,table_money2);

}
//初始化评价表格
$(function () {
    initEvaluationTable();
    getClassInfo();
});
//获取数据，拼接评价表格
var evaluationData;
var initEvaluationTable = function () {
    var classId = $("#classId").val();
    $.ajax({
        type: "get",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "findResultTableTest.action" ,//url
        data: {"classId":classId},
        success:function (result){
            evaluationData = result;
            // console.log("初始化表格...")
            formatEvaluationTable(result);
            initTreeGrid();
        }

    })
};
//获取班级信息
var classInfo;
var getClassInfo = function () {
    var classId = $("#classId").val();
    $.ajax({
        type: "get",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: ctx+"/commonApi/getClassInfoByClassId.action" ,//url
        data: {"classId":classId},
        success:function (result){
            classInfo = result;
        }

    })
};
//刷新表格
var refreshEvaluationTable = function () {
    // console.log("刷新表格中...");
    $("#table").empty();
    formatEvaluationTable(evaluationData);
    initTreeGrid();
    // window.location.reload();
};
//打印评价数据
var printEvaluationData = function () {
    // console.log("打印数据中...");
    var printWindows = window.open("打印评价","_blank");
    var html = '<html><head>' +
        '<style type="text/css" media="print">' +
        '  @page { size: auto;   margin: 25px 0 25px 0; }' +
        '</style>' +
        '<style type="text/css" media="all">' +
        'table{border-collapse: collapse; font-size: 12px; }\n' +
        'table, th, td {border: 1px solid grey}\n' +
        'th, td {text-align: center; vertical-align: middle;}\n' +
        'p {font-weight: bold; margin-left:8px }\n' +
        'table { width:94%; margin-left:3%; margin-right:3%}\n' +
        'div.bs-table-print { text-align:center;}\n' +
        '</style></head><title>Print Table</title><body>' +
        '<p>Printed on: ' + new Date + ' </p>' +
        '<div class="bs-table-print">' + formatEvaluationDataToTable() + "</div></body></html>"
    //替换原来的网页内容为需要打印的内容
    printWindows.document.write(html);
    printWindows.document.close();
    //打印当前网页
    printWindows.print();
    printWindows.close();
};
//对全局变量evaluationData进行数据转<table>
var formatEvaluationDataToTable = function () {
    var result = evaluationData;
    var temp = "<p class='col-sm-12 text-center font-bold ' style='font-size: large;margin-bottom: 10px'>[ "+classInfo.classInfo.className+" ] 总体评价结果建议</p><table class=\"tree table table-hover table-bordered\">";
    temp+="<tr><th>评价分类</th><th>评价项目</th><th>评价内容</th></tr>"
    if (result.length ==0){
        temp+="<tr><td>该班级没有评价记录</td><td></td></tr>";
    }
    for(i in result){
        var types = result[i];
        var n = 1;
        temp += "<tr><td |>"+types.type+"</td></tr>";
        for(j in types.project){
            var projects = types.project[j];
            var m =1;
            temp += "<tr><td $>"+types.project[j].project+"</td>&</tr>";
            n++;
            var usefulWorldCounts=0;
            for (k in projects.suggests){
                if (!uselessWorld(projects.suggests[k])){
                    temp += "<tr><td>"+projects.suggests[k]+"</td></tr>";
                    n++;
                    m++;
                    usefulWorldCounts++;
                }
            }
            var arrTemp0 = temp.split("&");
            if (usefulWorldCounts == 0){
                temp = arrTemp0[0]+"<td></td>"+arrTemp0[1];
            }else{
                temp = arrTemp0[0]+arrTemp0[1];
            }
            var arrTemp1 = temp.split("$");
            temp = arrTemp1[0]+"rowspan = '"+m+"'"+arrTemp1[1];
        }
        var arrTemp2 = temp.split("|");
        temp = arrTemp2[0]+"rowspan = '"+n+"'"+arrTemp2[1];
    }

    temp +="</table>";
    return temp;
};
//移除标签时触发
$(function () {
    $("#uselessWord").on('itemRemoved',function () {
        var tags = $("#uselessWord").tagsinput('items');
        // console.log(tags);
        $("#table").empty();
        formatEvaluationTable(evaluationData);
        initTreeGrid();
    })
});
//添加标签时触发
$(function () {
    $("#uselessWord").on('itemAdded',function () {
        var tags = $("#uselessWord").tagsinput('items');
        // console.log(tags);
        $("#table").empty();
        formatEvaluationTable(evaluationData);
        initTreeGrid();
    })
});
//初始化表格
var formatEvaluationTable =function (result) {
    // console.log("表格初始化")
    var temp = "<table class=\"tree table table-hover table-bordered\">";
    var n = 1;
    temp+="<tr><th>评价项目</th><th>评价内容</th></tr>"
    if (result.length ==0){
        temp+="<tr><td>该班级没有评价记录</td><td></td></tr>";
    }
    for(i in result){
        var types = result[i];
        var pn = n;
        temp += "<tr class='treegrid-"+(n++)+"'><td>"+types.type+"</td><td></td></tr>";
        for(j in types.project){
            var cn = n;
            var projects = types.project[j];
            temp += "<tr class='treegrid-"+(n++)+" treegrid-parent-"+pn+"'><td>"+types.project[j].project+"</td><td></td></tr>";
            for (k in projects.suggests){
                if (!uselessWorld(projects.suggests[k])){
                    temp += "<tr class='treegrid-"+(n++)+" treegrid-parent-"+cn+"'><td></td><td>"+projects.suggests[k]+"</td></tr>";
                }
            }
        }
    }

    temp +="</table>";
    $("#table").append(temp);

};
var initTreeGrid = function () {
    $(".tree").treegrid({
        'initialState': 'collapsed',
        'saveState': true
    });
}
//判断无用关键词
var uselessWorld = function (value) {
    var tags = $("#uselessWord").tagsinput('items');
    var flag = false;
    for (i in tags){
        if (value == tags[i]||value ==""){
            flag = true;
        }
    }
    return flag;
};



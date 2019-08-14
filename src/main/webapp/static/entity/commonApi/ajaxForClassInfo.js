//通过班级id请求班级信息
var getClassInfo = (function (classId) {
    var result;
    $.ajax({
        type:'get',
        url:ctx+'/commonApi/getClassInfoByClassId.action',
        dataType:'json',
        data:{"classId":classId},
        async:false,
        success:function (data) {
            result = data;
        }
    });
    return result;
})(classId);
getClassInfo(24);
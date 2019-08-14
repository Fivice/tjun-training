var path = $("#contextPath").val();
/**
 * 树形表格显示
 */
$(document).ready(function() {
    $("#treeTable").treeTable({
        theme : 'default',
        expandLevel : 2
    }).show();
});


/**
 * 删除区域
 */
function department_delete(index, value) {
    layer.confirm('确认要删除该部门吗？', {
        btn : [ '确定', '取消' ] //按钮
    }, function() {
        $.ajax({
            type : 'delete',
            dataType : 'json',
            url :path + '/department/' + value +'/delete.action',
            success : function(result) {
                if (result.code == 1) {
                    layer.msg('删除成功!', {
                        icon : 1,
                        time : 1000
                    }, function() {
                        window.location.reload(); // 刷新页面
                    });
                } if (result.code == 0) {
                    layer.msg('该部门已被使用，删除不成功!', {
                        icon : 5,
                        time : 3000
                    }, function() {
                        window.location.reload(); // 刷新页面
                    });
                }
                else {
                    layer.msg(result.message, {
                        icon : 2
                    });
                }
            }
        })
    });
}

//选择时点击文字，选择框未绑定事件
$(function () {
    document.onclick = function (e) {
        $("#info").hide(300);
    }

    $('#info').click(function (e) {

        e = e || event;
        stopFunc(e);
    });
    $('#ChanYeName').click(function (e) {

        e = e || event;
        stopFunc(e);
        $("#info").slideToggle(300)
    });

    document.onclick = function (e) {
        $("#info").hide(300);
    }
})

//阻止向上传递事件（阻止冒泡）
function stopFunc(e) {
    e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
}

//ztree的配置项
var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: 0
        }
    },
    callback: {
        onNodeCreated: onNodeCreated,
        onClick: function (e, treeId, treeNode, clickFlag) {

            clickNode(treeId,"unitId","ChanYeName");


        },
        onDblClick: function(treeId, treeNode) {
                $("#info").hide(300);
        },

    }

};

var dataMaker = function(count) {
    var nodes = [], pId = -1,
        min = 10, max = 90, level = 0, curLevel = [], prevLevel = [], levelCount,
        i = 0,j,k,l,m;

    while (i<count) {
        if (level == 0) {
            pId = -1;
            levelCount = Math.round(Math.random() * max) + min;
            for (j=0; j<levelCount && i<count; j++, i++) {
                var n = {id:i, pid:pId, name:"Big-" +i};
                nodes.push(n);
                curLevel.push(n);
            }
        } else {
            for (l=0, m=prevLevel.length; l<m && i<count; l++) {
                pId = prevLevel[l].id;
                levelCount = Math.round(Math.random() * max) + min;
                for (j=0; j<levelCount && i<count; j++, i++) {
                    var n = {id:i, pid:pId, name:"Big-" +i};
                    nodes.push(n);
                    curLevel.push(n);
                }
            }
        }
        prevLevel = curLevel;
        curLevel = [];
        level++;
    }
    return nodes;
}

var showNodeCount = 0;
function onNodeCreated(event, treeId, treeNode) {
    showNodeCount++;
}

function createTree (url,treeId) {
    var zNodes = [];
    $.ajax({
        url:url,
        type:'GET',
        dataType:'json',
        async: false,  //ajax设置为同步以将数据传到函数外调用
        success:function (data) {
            zNodes = data;
        }
    });
    showNodeCount = 0;
    $('#' + treeId).empty();
    setting.check.enable = false;
    $.fn.zTree.init($('#' + treeId), setting, zNodes);
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    var nodes = zTree.getNodes();
    /*chooseNode(nid,treeId)*/



}


$(document).ready(function(){
    createTree(ctx+'/classInfo/findUnit.action',"haveclasstree");
});

//选中节点
function chooseNode(nId,treeId) {

    var f = $('#' + nId).val();//所选的ID   例如 "12589"

    if(typeof f != "undefined" && f != null && f != ""){
        var treeObj = $.fn.zTree.getZTreeObj(treeId);//ztree树的ID
        var node = treeObj.getNodeByParam("id", f);//根据ID找到该节点
        treeObj.selectNode(node);//根据该节点选中
    }


}

function clickNode(treeId,nId,nName){

    var  zTree = $.fn.zTree.getZTreeObj(treeId);
    //zTree.checkNode(treeNode, !treeNode.checked, true);
    var nodes = zTree.getSelectedNodes();
    var node = nodes[0];
    //设置选择根父级节点时不可显示在input中
    $('#' + nName).val(nodes[0].name);
    $('#' + nId).val(nodes[0].id);



}

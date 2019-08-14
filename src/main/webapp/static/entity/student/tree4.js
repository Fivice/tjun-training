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

    $('#departInfo').click(function (e) {

        e = e || event;
        stopFunc(e);
    });
    $('#departName').click(function (e) {

        e = e || event;
        stopFunc(e);
        $("#departInfo").slideToggle(300)
    });

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
            /*var  zTree = $.fn.zTree.getZTreeObj("haveclasstree");
            //zTree.checkNode(treeNode, !treeNode.checked, true);
            var nodes = zTree.getSelectedNodes();
            var node = nodes[0];
            //设置选择根父级节点时不可显示在input中
            /!*if(node.node_flag != 1){*!/
            $("#ChanYeName").val(nodes[0].name);
            $("#cyids").val(nodes[0].id)
            /!*}*!/*/
            /*alert(treeId)*/
            if(treeId=="haveclasstree"){
                clickNode(treeId,"cyids","ChanYeName","unitName");
            }else if(treeId=="departmenTree"){
                clickNode(treeId,"departmentId","departName","deparentmentName");
            }

        },
        onDblClick: function(treeId, treeNode) {
           /* alert(treeNode)*/
            if(treeNode=="haveclasstree"){
                $("#info").slideToggle(300);
            }else if(treeNode=="departmenTree"){
                $("#departInfo").slideToggle(300);
            }

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

function createTree (url,treeId,nid) {
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
    //当根父节点没有子节点时，不进行子节点的赋值操作
    /*if(nodes[0].children !== undefined){
        zTree.selectNode(nodes[0].children[0]); //设置默认选中第一个子节点
        $("#ChanYeName").val(nodes[0].children[0].name);
        $("#cyids").val(nodes[0].children[0].id);
    }*/
    /*var f = $('#cyids').val();*/
   /* if(f == "name=\"unit.areaId\""){
        zTree.selectNode(nodes[0].children[0]); //设置默认选中第一个子节点
        $("#ChanYeName").val(nodes[0].children[0].name);
        $("#cyids").val(nodes[0].children[0].id);
    }else{
        chooseNode(nid,treeId);
    }*/

    chooseNode(nid,treeId)



}


$(document).ready(function(){
    createTree('/classInfo/findUnit.action',"haveclasstree","cyids");
    /*createTree(ctx+'/classInfo/findDepartment.action',"departmenTree","departmentId");*/
});

//选中节点
function chooseNode(nId,treeId) {

    var f = $('#' + nId).val();//所选的ID   例如 "12589"

    if(typeof f != "undefined" && f != null && f != ""){
        var treeObj = $.fn.zTree.getZTreeObj(treeId);//ztree树的ID
        var node = treeObj.getNodeByParam("id", f);//根据ID找到该节点
        treeObj.selectNode(node);//根据该节点选中
        /*treeObj.checkNode(node,true,false)//checkNode方法根据相应节点，进行勾选*/
    }


}

function clickNode(treeId,nId,nName,nName2){

    var  zTree = $.fn.zTree.getZTreeObj(treeId);
    //zTree.checkNode(treeNode, !treeNode.checked, true);
    var nodes = zTree.getSelectedNodes();
    var node = nodes[0];
    //设置选择根父级节点时不可显示在input中
    /*if(node.node_flag != 1){*/
    $('#' + nName).val(nodes[0].name);
    $('#' + nId).val(nodes[0].id);
    if(nName2!=null){
        $('#' + nName2).val(nodes[0].name);
    }
    /*}*/

}

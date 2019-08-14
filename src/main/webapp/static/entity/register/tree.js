
//创建树
function createTree(url, treeId,chk) {

	/*alert(chk==1);*/
	var style="checkbox";
	if(chk==1){
		style="radio";
	}
	
    var zTree; //用于保存创建的树节点
    var setting = { //设置
    	view: {
    		addHoverDom: addHoverDom,
    		removeHoverDom: removeHoverDom,
    		selectedMulti: false,        //禁止多点选中
    		},
        check: {
            enable: true,
            nocheckInherit: false,
            /*chkStyle: "radio"*/
            chkStyle: style
            
        },
        view: {
            showLine: true, //显示辅助线
            dblClickExpand: true,
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
		    /*onClick: function(treeId, treeNode) {
			    var treeObj = $.fn.zTree.getZTreeObj(treeNode);
                var selectedNode = treeObj.getSelectedNodes()[0];
                alert(treeObj)
                $("#txtId").val(selectedNode.id);
                $("#txtAddress").val(selectedNode.name);
		    }*/
            //回调函数，实现展开全部节点功能
           /* beforeAsync: beforeAsync,
            onAsyncSuccess: onAsyncSuccess,
            onAsyncError: onAsyncError,*/

            onCheck : testClick,
        }

    };
    $.ajax({ //请求数据,创建树
        type: 'GET',
        url: ctx+'/classInfo/'+url,
        dataType: "json", //返回的结果为json  
        success: function(data) {
            zTree = $.fn.zTree.init($(treeId), setting, data); //创建树
            /*expandAll();  //调用写好的展开全部节点方法*/
            //默认展开二级菜单
            treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }
            chooseNode();
        },
        error: function(data) {
            alert("创建树失败!");
        }
    });
}

//点击获取节点信息.
function testClick(e, treeId, treeNode) {
    //树对象
    treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes2 = treeObj.getCheckedNodes(true),
        v = "",
        ids = "";//获取所有选中的节点
    for (var i = 0; i < nodes2.length; i++) {
        //如果当前所有节点的id!=当前节点id
        if (nodes2[i].id != treeNode.id) {
            nodes2[i].checked = false; //取消选中效果
            treeObj.updateNode(nodes2[i]);//更新所有选中的节点..
        }
    }

}
//获取子节点，所有父节点的name的拼接字符串
/*function getFilePath(treeObj){
    if(treeObj==null)return "";
    var filename = treeObj.name;
    var pNode = treeObj.getParentNode();
    if(pNode!=null){
        filename = getFilePath(pNode) +">"+ filename;
    }
    return filename;
}*/

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};


//默认展开节点相关方法
function beforeAsync() {
    curAsyncCount++;
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
    curAsyncCount--;
    if (curStatus == "expand") {
        expandNodes(treeNode.children);
    } else if (curStatus == "async") {
        asyncNodes(treeNode.children);
    }
    if (curAsyncCount <= 0) {
        if (curStatus != "init" && curStatus != "") {
            asyncForAll = true;
        }
        curStatus = "";
    }
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    curAsyncCount--;
    if (curAsyncCount <= 0) {
        curStatus = "";
        if (treeNode!=null) asyncForAll = true;
    }
}
var curStatus = "init", curAsyncCount = 0, asyncForAll = false,
    goAsync = false;
function expandAll() {

    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    if (asyncForAll) {

        zTree.expandAll(true);
    } else {
        expandNodes(zTree.getNodes());
        if (!goAsync) {

            curStatus = "";
        }
    }
}
function expandNodes(nodes) {
    if (!nodes) return;
    curStatus = "expand";
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    for (var i=0, l=nodes.length; i<l; i++) {
        zTree.expandNode(nodes[i], true, false, false);
        if (nodes[i].isParent && nodes[i].zAsync) {
            expandNodes(nodes[i].children);
        } else {
            goAsync = true;
        }
    }
}


//查询单位数据
function findUnit() {

    createTree("findUnit.action","#treeDemo");

    $("#modal").modal();

    //关闭模态框
    $(function() {
        $('#modal').on('hide.bs.modal', function() {
        })
    });
}

//选中节点
function chooseNode() {

    //主办单位
    var f = $("#unitId").val();//所选的ID   例如 "12589"



}


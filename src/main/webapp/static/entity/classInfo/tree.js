
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
            chkStyle: style,
            /*chkboxType: { "Y": "s", "N": "ps" }*/
            chkboxType: { "Y": "", "N": "" }
            
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

           /* onCheck : testClick,*/
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
            /*treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }*/
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
function findUnit(element) {
    $("#un").prop("value",element.id);
    createTree("findUnit.action","#treeDemo",null);

    $("#modal").modal();

    //关闭模态框
    $(function() {
        $('#modal').on('hide.bs.modal', function() {
        })
    });
}

//清除单位数据
function resetUnit(element){
    var s=element.id;
    //清除主办单位
    if(s=="resetHostUnit"){
        //清除主办单位id
        $("#unitId").prop("value","");
        //清除主办单位名称
        $("#hostUnit").prop("value","");
    }
    //清除承办单位
    if(s=="resetOrganizerUnit"){
        //清除承办单位id
        $("#organizerUnitId").prop("value","");
        //清除承办单位名称
        $("#organizerUnit").prop("value","");
    }
}


//选中节点
function chooseNode() {
    var s=$("#un").val();
    if(s=="hostUnitButton"){
        //主办单位
        var f = $("#unitId").val();//所选的ID   例如 "12589"
    }else if(s=="organizerUnitButton"){
        //承办单位
        var f = $("#organizerUnitId").val();//所选的ID   例如 "12589"
    }


    if(typeof f != "undefined" && f != null && f != ""){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");//ztree树的ID
        /*var node = treeObj.getNodeByParam("id", f);//根据ID找到该节点
        treeObj.selectNode(node);//根据该节点选中
        treeObj.checkNode(node,true,false)//checkNode方法根据相应节点，进行勾选*/
        var arr=f.split(",");
        for (var i=0;i<arr.length;i++){
            var node = treeObj.getNodeByParam("id", arr[i]);//根据ID找到该节点
            treeObj.selectNode(node,true);//根据该节点选中
            treeObj.checkNode(node,true,false)//checkNode方法根据相应节点，进行勾选
        }
    }

}
//模态框确定按钮
function chooseUnit() {

    var s=$("#un").val();

    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");

    nodes = treeObj.getCheckedNodes(true);
    if(nodes.length==0){
        layer.alert("请选择一条数据")
    }else{
        var unit=[];
        var uId=[];
        if(s=="hostUnitButton"){
            for (var i=0;i<nodes.length;i++){
                unit.push(nodes[i].name);
                uId.push(nodes[i].id);
            }
            $("#hostUnit").prop("value",unit.toString());
            $("#unitId").prop("value",uId.toString());

        }else if(s=="organizerUnitButton"){
            for (var i=0;i<nodes.length;i++){
                unit.push(nodes[i].name);
                uId.push(nodes[i].id);
            }
            $("#organizerUnit").prop("value",unit.toString());
            $("#organizerUnitId").prop("value",uId.toString());

        }

        //关闭模态框
        $('#modal').modal('hide');
    }

}

//根据校区获取教室
function findClassroom(obj){
    var value = obj.options[obj.selectedIndex].value;
    $("#classroomNameId").prop("value","");
    if(typeof value != "undefined" && value != null && value != ""){
        //查询对应校区下的教室
        seachClassroom(value);
    }else{
        //清除教室信息
        $("#classroom").html("<option value=''>请选择教室</option>");
    }

}

//获取教室
function getClassroom(obj){
    var value = obj.options[obj.selectedIndex].value;
    var name =  $("#classroom").find("option:selected").text();
    $("#classroomNameId").prop("value",name);
}

//查询指定校区下的教室
function seachClassroom(value){
    //教室id
    var cRoomId=$("#cRoomId").val();
    var html="";
    $.ajax({
        type: 'POST',
        url: ctx + "/classInfo/findClassroom.action",// 发送请求
        data: {

            school: value
        },
        dataType: "json",
        success: function (data) {
            /*html+="<option value=''></option>";*/
            html+="";
            for (var i = 0; i < data.length; i++) {
                html+="<option value='"+data[i].id+"'>" + data[i].className + "</option>"
            }
            $("#classroom").html(html);

            if(typeof cRoomId != "undefined" && cRoomId != null && cRoomId != ""){
                //默认选中
                $("#classroom").find("option[value='"+cRoomId+"']").prop("selected",true);
            }
        }
    });
}

$(function() {
    //教室id
    var cRoomId=$("#cRoomId").val();
    //校区id
    var cAmId=$("#cAmId").val();
    //校区名称
    var schoolName=$("#schoolName").val();

    if(typeof cAmId != "undefined" && cAmId != null && cAmId != ""){
        //查询对应校区下的教室
        seachClassroom(schoolName);

    }


})
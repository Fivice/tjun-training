/**
 * @author wubin
 * 给指定手机号按班级发送短信
 */
(function ($) {
    'use strict';
    /**
     * 内部全局变量
     * @type {*|HTMLElement}
     */
    //获取id为table
    var $table = $("#table");
    //选中班级显示区
    var $selectClass = $("#message-class");
    //获取发送短信按钮
    var $sendMessage = $("#send-messages");
    //获取号码填写input
    var $phoneInput = $("#input-phone");
    //号码列表显示区域
    var $phoneList = $("#phone-to-send");
    //模态框
    var $phoneListModal = $("#phoneListModal");
    //待发送号码表
    var phoneList = [];
    //已选择班级id
    var classId = '';

    /**
     * 实时监听模块
     */
    (function () {

        //监听添加手机号码按钮
        $("#add-phone-to-send-list").click(function () {

            var phone = $phoneInput.val();
            var reg = /^1[3456789]\d{9}$/;
            var $phoneList = $("#phone-to-send");
            //验证添加号码格式
            var flag = validate.validatePhone(reg,phone,"号码格式不正确");
            if (flag){
                var exitValue = phoneList.find(function (value) {
                    return value === phone;
                });
                if (exitValue !== undefined){
                    console.log("已存在")
                }else {
                    phoneList.push(phone);
                    //渲染列表
                    render.renderPhoneList($phoneList);
                }

                //清空input
                $phoneInput.val('');
            }
        });
        //监听号码添加input值变化
        $phoneInput.change(function () {
           console.log($phoneInput.val());
            var phone = $phoneInput.val();
            var reg = /^1[3456789]\d{9}$/;
           validate.validatePhone(reg,phone,"号码格式不正确");
        });
        //监听短信发送按钮
        $sendMessage.click(function () {
            //验证选中班级不为空
            if (validate.validateSelectedClass()){
                //选中班级不空
                //修改父节点样式 提示没有选择班级
                //发送成功后取消父元素成功样式
                $selectClass.parent().removeClass("has-success");
                console.log("发送短信给：" + className);

                //验证待发送手机列表是否为空
                if (validate.validatePhoneList()){
                    layer.msg("发送中...");
                    // 将号码发回后台
                    ajax.postPhoneList();

                } else {
                    layer.msg("您还没有添加任何号码，请添加号码！");
                    $phoneList.addClass("bg-danger");
                }
            }else {
                //选中班级为空

                $selectClass.parent().addClass("has-error");

                layer.msg("没选班级");
                $selectClass.val('');
            }
            //验证待发送区号码表不空
        });

        //监听bootstrap table 表格点击事件
        $table.on('click-row.bs.table', function (row, $element, field) {
            //取消之前已选班级取消
            $table.bootstrapTable("uncheckAll");
            //将选中的班级名填入指定input
            $("#message-class").val($element.className);
            classId = $element.id;
            console.log("选中的班级id:"+classId);

            sessionStorage.setItem("selectClassId",classId);

            $selectClass.parent().removeClass("has-error");
            $selectClass.parent().addClass("has-success");
        });
        //监听模态框变化
        $phoneListModal.on('hidden.bs.modal', function (e) {
            console.log("批量添加模态框关闭");
            var phoneArrStr = sessionStorage.getItem("phoneList");
            console.log(phoneArrStr);
            if (phoneArrStr !==null){
                var phoneArr = JSON.parse(phoneArrStr);
                console.log(phoneArr);
                phoneList = phoneArr.slice();
                render.renderPhoneList($phoneList);
            }
        })
    })();
    /**
     * 验证对象模块
     * @type {{validatePhone: validatePhone, validateSelectedClass: validateSelectedClass, validatePhoneList: validatePhoneList}}
     */
    var validate = {
        //验证添加的号码格式
        validatePhone : function (reg,phone,msg) {
            if (reg.test(phone)){
                console.log(phone);
                $phoneInput.parent().removeClass("has-error");
                $phoneInput.parent().addClass("has-success");
                return true;
            }else {
                console.log(msg);
                layer.msg(msg);
                $phoneInput.parent().addClass("has-error");
                return false;
            }
        },
        //验证选择的班级 没选择班级则返回false
        validateSelectedClass : function () {
            //获取选中班级值
            var className = $selectClass.val();
            //判断选中的班级值是否为空
            return !(className === null || className === '' || typeof className === "undefined");

        },
        //验证待发送区号码表不为空
        validatePhoneList:function () {
            //待发送号码为空则返回false 不为空则返回true
            return phoneList.length !== 0;

        }
    };
    /**
     * 渲染模块
     * @type {{renderPhoneList: renderPhoneList}}
     */
    var render = {
        renderPhoneList : function ($element) {
            if (phoneList.length === 0){
                console.log("还没有号码缓存");
                layer.msg("还没有号码缓存");
            } else {
                var node = document.createElement('ul');
                node.setAttribute("class","list-group");
                node.setAttribute("id","phone-list");
                for (var i = 0; i < phoneList.length; i++) {
                    //<li></li>节点创建
                    var nodeLi = document.createElement('li');
                    nodeLi.setAttribute("class","list-group-item");
                    nodeLi.innerHTML = phoneList[i];

                    //<span></span>节点创建
                    var phoneNode = document.createElement("span");
                    phoneNode.setAttribute("class","badge");
                    phoneNode.style.color = "#ed5565";
                    phoneNode.style.cursor = "pointer";
                    //<i></i>节点创建
                    var nodeRemove = document.createElement("i");

                    nodeRemove.setAttribute("class","fa fa-trash");
                    nodeRemove.setAttribute('value',phoneList[i]);
                    //给<i></i>添加点击事件监听
                    nodeRemove.addEventListener('click',function (evt) {
                        //获取需要删除号码值
                        var del = this.getAttribute('value');
                        //在nodeList数组中找到这个值并删除
                        var tempArr = [];
                        for (var j = 0; j < phoneList.length; j++) {
                            if (phoneList[j] === del){
                                console.log("被删除号码"+phoneList[j]);
                            }else {
                                tempArr.push(phoneList[j]);
                            }
                        }
                        phoneList = tempArr;
                        //删除当前手机号显示节点
                        var nodeUl = this.parentNode.parentNode.parentNode;
                        var childNode = this.parentNode.parentNode;
                        nodeUl.removeChild(childNode);
                        if (phoneList.length === 0) {
                            //号码数组里没有号码了
                            nodeUl.appendChild(document.createTextNode("请添加手机号码"));
                        }
                        console.log(phoneList);

                    });
                    //
                    phoneNode.appendChild(nodeRemove);
                    nodeLi.appendChild(phoneNode);

                    node.appendChild(nodeLi);
                }
                //渲染新节点前移除样式和子节点
                $element.removeClass("bg-danger");
                $element.html('');
                //将节点追加到指定位置
                $element.append(node);
            }

        }
    };
    var ajax = {
        postPhoneList:function () {
            console.log(classId);
            console.log(JSON.stringify(phoneList));
            $.ajax({
                url:ctx+"/message/sendMessage.action",
                type:'post',
                dataType:'json',
                data:{classId:classId,phoneList:JSON.stringify(phoneList)},
                success:function (data) {
                    console.log(data);
                    var temp = "<ul style='max-height: 200px;text-align: center;'>" ;
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].returnCode > 0){
                            temp += "<li>"+data[i].phone+" [<span><i class='fa fa-check'></i></span>]</li>";
                        } else {
                            switch (data[i].returnCode) {
                                case "-1":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='没有该用户账户' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-2":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='接口密钥不正确' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-3":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='短信数量不足' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-4":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='手机号格式不正确' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-6":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='IP限制' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-11":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='该用户被禁用' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-14":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='短信内容出现非法字符' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-21":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='MD5接口密钥加密不正确' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-41":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='手机号码为空' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-42":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='短信内容为空' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-51":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='短信签名格式不正确' class='fa fa-exclamation-triangle'></i></li>";break;
                                case "-52":temp +="<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='短信签名太长' class='fa fa-exclamation-triangle'></i></li>";break;
                                default:temp += "<li>"+data[i].phone+" [<span><i class='fa fa-times'></i></span>] <i style='color: #f39c12' title='未知错误' class='fa fa-exclamation-triangle'></i></li>";break;
                            }
                        }
                    }
                    temp+="</ul>";
                    layer.open({
                       title:"短信发送结果",
                       content:temp
                    });
                    //发送成功后将$phoneList子节点更新 和 将classId置空
                    $phoneList.html("请添加手机号码");
                    classId = '';
                    phoneList = [];
                    $selectClass.val("");

                },
                error:function () {
                    console.log("发送失败");
                }
            })
        }
    }

})(jQuery);
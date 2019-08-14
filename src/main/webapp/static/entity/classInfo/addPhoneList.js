
var PhoneListData = function ($) {
    'use strict';

    //向外暴露的对象
    var _this = {};
    //内部对象
    var oPhoneList = {
        //是否校验过
        validateStatus:false,
        //检验后的所有号码符合条件
        allPhoneRight: false,
        //拿到的原始数组
        phoneArrOrigin:[],
        //去空后的数组
        phoneArrBefore:[],
        //去重后的数组
        phoneArrAfter:[]
    };

    /**
     * 验证目标区号码，并将结果渲染到目标区
     * @param target
     * @param result
     * @constructor
     */
    _this.Validate = function (target,result) {
        //console.log("校验号码");
        var targetElement = $("#"+target+"");
        var resultElement = $("#"+result+"");

        //通过id获取指定 <textarea> 的value值，并将值按照回车('\n')分割成数组，去空并去重
        oPhoneList.phoneArrOrigin = tool.strToArr(targetElement);
        //数组去空
        oPhoneList.phoneArrBefore = tool.removeArrBlank(oPhoneList.phoneArrOrigin);
        //数组去重
        oPhoneList.phoneArrAfter = tool.uniqueArr(oPhoneList.phoneArrBefore);
        //根据去空后的数组往指定id的<textarea>区域显示
        render.validateResult(resultElement,oPhoneList.phoneArrAfter);
        //修改校验状态为true
        oPhoneList.validateStatus = true;
        //console.log(oPhoneList);
    };
    /**
     * 将校验后的号码导入到待发送列表
     * @param target
     * @constructor
     */
    _this.Import = function (target) {
        //console.log("导入验证后的号码");
        var targetElement = $("#"+target+"");
        //导入前检查是否校验过
        if (oPhoneList.validateStatus){
            //检查校验后所有号码是否都是正确的格式
            if (oPhoneList.allPhoneRight){
                //检查输入区域内容是否有改动，如果没变化
                if (!tool.textareaChanged(targetElement)){
                    if (oPhoneList.phoneArrAfter.length!==0){
                        //console.log("导入");
                        //将校验后的数据
                        layer.msg("导入成功");
                        sessionStorage.setItem("phoneList",JSON.stringify(oPhoneList.phoneArrAfter));
                        $("#phoneListModal").modal("hide");
                    } else {
                        //console.log("导入数据为空")
                        layer.open({
                            content:"导入数据为空"
                        })
                    }
                }else {
                    //console.log("输入区发生变化，请先校验");
                    layer.open({
                        content:"输入区发生变化，请先校验"
                    })
                }
            }else {
                //console.log("校验结果中存在不符合条件的号码");
                layer.open({
                    content:"校验结果中存在不符合条件的号码"
                })
            }
        }else {
            //console.log("输入区号码没有校验");
            layer.open({
                content:"输入区号码没有校验"
            })
        }

    };
    /**
     * 将模态框界面 和 后台存储的数据清空为最初状态
     * @param target
     * @param result
     * @constructor
     */
    _this.Clear = function(target,result){
        var targetElement = $("#"+target+"");
        var resultElement = $("#"+result+"");
        // 初始化oPhoneList对象
        oPhoneList.phoneArrOrigin = [];
        oPhoneList.phoneArrBefore = [];
        oPhoneList.phoneArrAfter = [];
        oPhoneList.allPhoneRight = false;
        oPhoneList.validateStatus = false;

        //清空 号码填入区和验证后显示区的号码
        targetElement.val("");
        resultElement.val("");
        //清空
        sessionStorage.removeItem("phoneList");

    };
    //正则表达式类
    var reg = {
        phoneRex : function (phone) {
            var phoneReg = /^1[3456789]\d{9}$/;
            var flag = false;
            if (phone !== '' && typeof phone !== 'undefined') {
                flag = phoneReg.test(phone)
            }
            return flag;
        }
    };
    //工具类
    var tool = {
        strToArr : function ($element) {
            console.log('获取目标文本：'+$element.val());
            return $element.val().split('\n');
        },
        removeArrBlank :function(arr){
            var tempArr = arr.slice();
            for (var i = 0; i < tempArr.length; i++) {
                if(tempArr[i] == "" || typeof(tempArr[i]) == "undefined") {
                    tempArr.splice(i,1);
                    i = i - 1; // i - 1 ,因为空元素在数组下标 2 位置，删除空之后，后面的元素要向前补位
                }
            }
            return tempArr;
        },
        uniqueArr : function(arr){
            var tempArr =[];
            for (var i = 0; i < arr.length; i++) {
                if (tempArr.indexOf(arr[i])===-1){
                    tempArr.push(arr[i].trim());
                }
            }
            return tempArr;
        },
        textareaChanged : function($element){
            var tempArr = tool.strToArr($element);
            //如果俩数组值相等返回true，则说明没变法，即此时向外返回false表示没变化
            return !tool.arrEquals(oPhoneList.phoneArrOrigin,tempArr);
        },
        arrEquals : function (srcArr,desArr) {
            var len = 0;
            //数组长度不一致，说明俩数组肯定不一样，返回false
            if (srcArr.length === desArr.length){
                len = srcArr.length;
            } else {
                return false;
            }
            //对于长度一致的数组，进行内容比较
            for (var i = 0; i < len; i++) {
                //只要存在一项不一样都返回 false
                if (srcArr[i]!==desArr[i]){
                    return false;
                }
            }
            return true;
        }
    };
    //渲染类
    var render = {
        validateResult: function ($element,arr) {
            var temp = '';
            var flag = true;
            for (var i = 0; i < arr.length; i++) {
                temp+=arr[i];
                if (reg.phoneRex(arr[i])){
                    temp+=" √";
                }else {
                    temp+=" ×";
                    flag = false;
                }
                if (i!==arr.length-1){
                    temp+='\n';
                }
            }
            oPhoneList.allPhoneRight = flag;
            $element.val(temp);
        }
    };

    return _this;
}(jQuery);

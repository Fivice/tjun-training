/**
 * 定义基础路径
 */
var ctx = $("#contextPath").val();
/**
 * 初始化 BootStrap Table 的封装
 *
 * 约定：toolbar的id为 (bstableId + "Toolbar")
 *
 * @author CJ
 */
/*(function () {
    var BSTable = function (bstableId, url, columns) {
        this.btInstance = null;                 //jquery和BootStrapTable绑定的对象
        this.bstableId = bstableId;
        this.url = ctx + url;
        this.method = "post";
        this.paginationType = "server";         //默认分页方式是服务器分页,可选项"client"
        this.toolbarId = bstableId + "Toolbar";
        this.columns = columns;
        /!*this.height = 665;*!/                      //默认表格高度665
        this.data = {};
        this.queryParams = {}; // 向后台传递的自定义参数
    };

    BSTable.prototype = {
        /!**
         * 初始化bootstrap table
         *!/
        init: function () {
            var tableId = this.bstableId;
            this.btInstance =
                $('#' + tableId).bootstrapTable({
                    contentType: "application/x-www-form-urlencoded",
                    url: this.url,              //请求地址
                    method: this.method,        //ajax方式,post还是get
                    ajaxOptions: {              //ajax请求的附带参数
                        data: this.data
                    },
                    toolbar: "#" + this.toolbarId,//顶部工具条
                    striped: true,              //是否显示行间隔色
                    cache: false,               //是否使用缓存,默认为true
                    pagination: true,           //是否显示分页（*）
                    sortable: false,             //是否启用排序
                    sortOrder: "desc",          //排序方式
                    pageNumber: 1,                  //初始化加载第一页，默认第一页
                    pageSize: 10,               //每页的记录行数（*）
                    pageList: [10, 50, 100],    //可供选择的每页的行数（*）
                    queryParamsType: 'limit',   //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
                    queryParams: function (param) {
                       return $.extend(this.queryParams, param ,{
                               pageSize: param.limit,
                               pageNumber: param.offset / param.limit + 1
                       });
                   }, // 向后台传递的自定义参数
                   /!* queryParams: function (param) {
                        return $.extend(this.queryParams, param);
                    }, // 向后台传递的自定义参数
                   /!* queryParams : BSTable.queryParams, // 传递参数（*）*!/
                    sidePagination: this.paginationType,   //分页方式：client客户端分页，server服务端分页（*）
                    search: false,              //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    strictSearch: true,         //设置为 true启用 全匹配搜索，否则为模糊搜索
                    showColumns: true,          //是否显示所有的列
                    showRefresh: true,          //是否显示刷新按钮
                    minimumCountColumns: 2,     //最少允许的列数
                    clickToSelect: true,        //是否启用点击选中行
                    searchOnEnterKey: true,     //设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
                    columns: this.columns,      //列数组
                    pagination: true,           //是否显示分页条
                    height: this.height,
                    icons: {
                        refresh: 'glyphicon-repeat',
                        toggle: 'glyphicon-list-alt',
                        columns: 'glyphicon-list'
                    },
                    iconSize: 'outline'
                });
            return this;
        },
        /!**
         * 向后台传递的自定义参数
         * @param param
         *!/
        setQueryParams: function (param) {
            this.queryParams = param;
        },
        /!**
         * 设置分页方式：server 或者 client
         *!/
        setPaginationType: function (type) {
            this.paginationType = type;
        },

        /!**
         * 设置ajax post请求时候附带的参数
         *!/
        set: function (key, value) {
            if (typeof key == "object") {
                for (var i in key) {
                    if (typeof i == "function")
                        continue;
                    this.data[i] = key[i];
                }
            } else {
                this.data[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
            }
            return this;
        },

        /!**
         * 设置ajax post请求时候附带的参数
         *!/
        setData: function (data) {
            this.data = data;
            return this;
        },

        /!**
         * 清空ajax post请求参数
         *!/
        clear: function () {
            this.data = {};
            return this;
        },

        /!**
         * 刷新 bootstrap 表格
         * Refresh the remote server data,
         * you can set {silent: true} to refresh the data silently,
         * and set {url: newUrl} to change the url.
         * To supply query params specific to this request, set {query: {foo: 'bar'}}
         *!/
        refresh: function (parms) {
            if (typeof parms != "undefined") {
                this.btInstance.bootstrapTable('refresh', parms);
            } else {
                this.btInstance.bootstrapTable('refresh');
            }
        }
    };

    window.BSTable = BSTable;

}());*/


//编辑
function commonEdit(ids,url){
    if (ids.length == 0) {
        layer.alert('请选择一条数据！', {
            icon : 5,
            title : "提示"
        });
    } else if (ids.length > 1) {
        layer.alert('只能选择一条数据编辑！', {
            icon : 5,
            title : "提示"
        });
    } else {
        location = url;
    }
}

//删除
function commonDel(ids,url){
    if (ids.length == 0) {
        layer.alert('请至少选择一条数据！', {
            icon : 5,
            title : "提示"
        });
    } else {
        layer.confirm('您确定要删除数据吗？', {
            btn : [ '确定', '取消' ]
            // 按钮
        }, function() {
            $.ajax({
                type : 'POST',
                url : url,// 发送请求
                data : {

                    iNumbers : ids.toString()
                },
                dataType : "json",
                success : function(result) {
                    if (result.code == 1) {
                        parent.layer.msg("删除成功!", {
                            shade : 0.3,
                            time : 1500
                        }, function() {
                            window.location.reload(); // 刷新父页面
                        });
                    } else if(result.code == -1){
                        layer.alert('存在已经有学生报到过的班级！', {
                            icon : 5,
                            title : "提示"
                        });
                    } else {
                        layer.msg(result.message, {
                            icon : 2,
                            time : 1000
                        });
                    }

                }
            });

        });

    }
}

//查询
function seach(){
    $("#table").bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
}

//清空input
function reset(){
    $("input").prop("value","");
}

//计算两个日期天数差的函数，通用
////////////////////////////////////////////////////////////////////////////////////////////
function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是yyyy-MM-dd格式

    var date1=new Date(sDate1); //开始时间
    var date2=new Date(sDate2); //结束时间
    var date3=date2.getTime()-date1.getTime();//时间差的毫秒数

    //计算出相差天数
    var days=Math.floor(date3/(24*3600*1000));

    return days;  //返回相差天数
}

//根据指定的一个日期和相差的天数，获取另外一个日期
//dateParameter为指定已经存在的日期yyyy-MM-dd  num为相差天数为整型 
////////////////////////////////////////////////////////////////////////////////////////////
function addByTransDate(dateParameter, num) {

    var translateDate = "", dateString = "", monthString = "", dayString = "";
    translateDate = dateParameter.replace("-", "/").replace("-", "/");

    var newDate = new Date(translateDate);
    newDate = newDate.valueOf();
    newDate = newDate + num * 24 * 60 * 60 * 1000;  //备注 如果是往前计算日期则为减号 否则为加号
    newDate = new Date(newDate);

    //如果月份长度少于2，则前加 0 补位   
    if ((newDate.getMonth() + 1).toString().length == 1) {
        monthString = 0 + "" + (newDate.getMonth() + 1).toString();
    } else {
        monthString = (newDate.getMonth() + 1).toString();
    }

    //如果天数长度少于2，则前加 0 补位   
    if (newDate.getDate().toString().length == 1) {

        dayString = 0 + "" + newDate.getDate().toString();
    } else {

        dayString = newDate.getDate().toString();
    }

    dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
    return dateString;

}

//easyUpload文件上传
function uploadFile(ctrlName, url,fileId,downloadUrl){

    $('#' + ctrlName).easyUpload({
        allowFileTypes: '*.jpg;*.doc;*.pdf;*.zip;',//允许上传文件类型，格式';*.doc;*.pdf'
        allowFileSize: 100000,//允许上传文件大小(KB)
        selectText: '选择文件',//选择文件按钮文案
        multi: false,//是否允许多文件上传
        /*multiNum: 5,//多文件上传时允许的文件数*/
        showNote: true,//是否展示文件上传说明
        note: '提示：支持格式为doc、pdf、jpg、zip',//文件上传说明
        showPreview: true,//是否显示文件预览
        url: url,//上传文件地址
        fileName: 'file',//文件filename配置参数
        /*formParam: {
            token: $.cookie('token_cookie')//不需要验证token时可以去掉
        },//文件filename以外的配置参数，格式：{key1:value1,key2:value2}*/
        timeout: 30000,//请求超时时间
        okCode: 200,//与后端返回数据code值一致时执行成功回调，不配置默认200
        downloadUrl:downloadUrl,
        successFunc: function(res) {
            $('#' + fileId).attr("value",res.substring(1,res.length - 1));
            /* alert(res.substring(1,res.length - 1))*/
            console.log('成功回调', res);
        },//上传成功回调函数
        errorFunc: function(res) {
            console.log('失败回调', res);
            alert(res)
        },//上传失败回调函数
        deleteFunc: function(res) {
            console.log('删除回调', res);
        }//删除文件回调函数
    });
}

/*Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var date= new Date().Format("yyyy-MM-dd");//Format("输入你想要的时间格式:yyyy-MM-dd,yyyyMMdd")*/

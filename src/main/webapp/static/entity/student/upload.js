//初始化上传插件
menu={
    fileInput: function ()
    {
        var oFile = new Object();
        //初始化fileinput控件（第一次初始化）
        oFile.Init = function(ctrlName, uploadUrl,name,imgUrl,str) {
            var control = $('#' + ctrlName);
            var cname=$('#' + name).val();
            //初始化上传控件的样式
            control.fileinput({
                uploadUrl: uploadUrl, //上传的地址
                showUpload : false,
                showRemove : false,
                uploadAsync: true,
                dropZoneEnabled: false,//是否显示拖拽区域
                uploadLabel: "上传",//设置上传按钮的汉字
                uploadClass: "btn btn-primary",//设置上传按钮样式
                showCaption: true,//是否显示标题
                language: "zh",//配置语言
                maxFileSize : 0,
                maxFileCount: 0,/*允许最大上传数，可以多个，当前设置单个*/
                autoReplace:true,//是否自动替换当前图片
                enctype: 'multipart/form-data',
                //allowedPreviewTypes : [ 'image' ], //allowedFileTypes: ['image', 'video', 'flash'],
                allowedFileExtensions : ["jpg", 'jpeg', "png","gif"],/*上传文件格式*/
                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
                dropZoneTitle: "请通过拖拽图片文件放到这里",
                dropZoneClickTitle: "或者点击此区域添加图片",
                previewFileIconSettings: { // configure your icon file extensions
                    /*'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',*/
                    'jpg': '<i class="fa fa-file-photo-o text-danger"></i>',
                    'jpeg': '<i class="fa fa-file-photo-o text-primary"></i>',
                    'png': '<i class="fa fa-file-photo-o text-primary"></i>',
                    'gif': '<i class="fa fa-file-photo-o text-muted"></i>',
                },
                msgInvalidFileType: '不正确的类型({name})  只支持{types}类型的文件！',
                /* initialPreview: [
                     "<img width='225' src='"+cname+"' class='file-preview-image' />"
                 ],*/
                overwriteInitial:true,
                layoutTemplates:{
                    footer:'',        //预览底部
                    actionDelete:'', //去除上传预览的缩略图中的删除图标
                    actionUpload:'',//去除上传预览缩略图中的上传图片；
                    actionZoom:''   //去除上传预览缩略图中的查看详情预览的缩略图标。
                },
                //uploadExtraData: {"id": id},//这个是外带数据
                showBrowse: false,
                browseOnZoneClick: true,
                initialPreview: imgUrl,
                initialPreviewConfig: [{

                    caption: str,

                    width: '120px',

                    key: 100,

                    extra: {

                        id: 100

                    },

                }],
                slugCallback : function(filename) {
                    /* alert(filename)*/
                    return filename.replace('(', '_').replace(']', '_');
                }
            });

            //上传文件成功，回调函数
            control.on("fileuploaded", function(event, data, previewId, index) {
                var result = data.response; //后台返回的json
                att(name,result);
            });

        }

        return oFile;
    },

}

//设置文件路径
function att(name,result){
    $('#' + name).prop("value",result);
}


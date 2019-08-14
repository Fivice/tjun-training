$(function() {
	//在用圆环图
	 var myChart = echarts.init(document.getElementById('row4'));
	 // 显示标题，图例和空的坐标轴
     myChart.setOption({
    	 title: {
    	        text: '资产使用情况（统计累积量）',
//    	        subtext: 'From ExcelHome',
//    	        sublink: 'http://e.weibo.com/1341556070/AjQH99che'
    	    },
    	    /*legend: {
    	        orient : 'vertical',
    	        x : 'center',
    	        data:[{
    	        	name: '资产数量',
    	            icon: 'circle',//'image://../asset/ico/favicon.png',//标志图形类型，默认自动选择（8种类型循环使用，不显示标志图形可设为'none'），默认循环选择类型有：'circle' | 'rectangle' | 'triangle' | 'diamond' |'emptyCircle' | 'emptyRectangle' | 'emptyTriangle' | 'emptyDiamond'另外，还支持五种更特别的标志图形'heart'（心形）、'droplet'（水滴）、'pin'（标注）、'arrow'（箭头）和'star'（五角星），这并不出现在常规的8类图形中，但无论是在系列级还是数据级上你都可以指定使用，同时，'star' + n（n>=3)可变化出N角星，如指定为'star6'则可以显示6角星
    	            textStyle: {
    	            	fontWeight: 'bold', color: 'green'
    	            	}
    	            }]
    	    },*/
    	    tooltip : {
    	        trigger: 'axis',
    	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    	        },
    	        formatter: function (params) {
    	            var tar = params[0];
    	            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
    	        }
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true},
    	            /*dataView : {show: true, readOnly: false},
    	            restore : {show: true},
    	            saveAsImage : {show: true}*/
    	           /* myTool1: {
    	                show: true,
    	                title: '自定义扩展方法1',
    	                icon: 'path://M432.45,595.444c0,2.177-4.661,6.82-11.305,6.82c-6.475,0-11.306-4.567-11.306-6.82s4.852-6.812,11.306-6.812C427.841,588.632,432.452,593.191,432.45,595.444L432.45,595.444z M421.155,589.876c-3.009,0-5.448,2.495-5.448,5.572s2.439,5.572,5.448,5.572c3.01,0,5.449-2.495,5.449-5.572C426.604,592.371,424.165,589.876,421.155,589.876L421.155,589.876z M421.146,591.891c-1.916,0-3.47,1.589-3.47,3.549c0,1.959,1.554,3.548,3.47,3.548s3.469-1.589,3.469-3.548C424.614,593.479,423.062,591.891,421.146,591.891L421.146,591.891zM421.146,591.891',
    	                onclick: function (){
    	                    alert('myToolHandler1')
    	                }
    	            },
    	            myTool2: {
    	                show: true,
    	                title: '自定义扩展方法',
    	                icon: 'image://http://echarts.baidu.com/images/favicon.png',
    	                onclick: function (){
    	                    alert('myToolHandler2')
    	                }
    	            },*/
    	        }
    	    },
    	    xAxis : [
    	        {
    	            type : 'category',
    	            splitLine: {show:false},
    	            data : []
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value'
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'资产数量',
    	            type:'bar',
    	            stack: '总量',
    	            itemStyle:{
    	                normal:{
    	                    barBorderColor:'rgba(0,0,0,0)',
    	                    color:'rgba(0,0,0,0)'
    	                },
    	                emphasis:{
    	                    barBorderColor:'rgba(0,0,0,0)',
    	                    color:'rgba(0,0,0,0)'
    	                }
    	            },
    	            data:[]
    	        },
    	        {
    	            name:'资产数量',
    	            type:'bar',
    	            stack: '总量',
    	            itemStyle : { normal: {label : {show: true, position: 'inside'}}},
    	            data:[]
    	        }
    	    ]
     });
     
     myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
     
     var names=[];    //类别数组（实际用来盛放X轴坐标值）
     var nums=[];    //销量数组（实际用来盛放Y坐标值）
     
     $.ajax({
     type : "post",
     async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
     url : ctx + '/index/selectAsset.action',   //请求发送到TestServlet处
     data : {},
     dataType : "json",        //返回数据形式为json
     success : function(result) {
         //请求成功时执行该函数内容，result即为服务器返回的json对象
         if (result) {
                for(var i=0;i<result.length;i++){       
                   names.push(result[i].name);    //挨个取出类别并填入类别数组
                 }
                for(var i=0;i<result.length;i++){       
                    nums.push(result[i].num);    //挨个取出销量并填入销量数组
                  }
                myChart.hideLoading();    //隐藏加载动画
                myChart.setOption({        //加载数据图表
                    xAxis: {
                        data: names
                    },
                    series: [
                    	 
                    		 {name: "资产数量",data:nums},  
                    		 {name: "资产数量",data:nums},  
                    		
                    ]
                });
                
         }
     
    },
     error : function(errorMsg) {
         //请求失败时执行该函数
     alert("图表请求数据失败!");
     myChart.hideLoading();
     }
})



});

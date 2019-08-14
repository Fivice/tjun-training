$(function() {
	//在用圆环图
	 var myChart = echarts.init(document.getElementById('row3'));
	 // 显示标题，图例和空的坐标轴
     myChart.setOption({
         title: {
             text: '资产分类统计',
             /*subtext: 'From ExcelHome',
             sublink: 'http://e.weibo.com/1341556070/AjQH99che'*/
         },
         tooltip : {
             trigger: 'axis'
         },
         /*legend: {
             data:['蒸发量','降水量']
         },*/
         toolbox: {
             show : true,
             feature : {
                 mark : {show: true},
                 magicType : {show: true, type: ['line', 'bar']},
                /* dataView : {show: true, readOnly: false},
                 restore : {show: true},
                 saveAsImage : {show: true}*/
             }
         },
         calculable : true,
         xAxis : [
             {
            	 axisLabel : {//坐标轴刻度标签的相关设置。
                     interval:0,
                     rotate:"20"
                 },
                 type : 'category',
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
                 itemStyle:{
                     normal:{
                         color:'#FF7F50'
                     }
                 },
                 type:'bar',
                 data:[],
                 markPoint : {
                     data : [
                         {type : 'max', name: '最大值'},
                         {type : 'min', name: '最小值'}
                     ]
                 },
                 /*markLine : {
                     data : [
                         {type : 'average', name: '平均值'}
                     ]
                 }*/
                 
             },
             /*{
                 name:'降水量',
                 type:'bar',
                 data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
                 markPoint : {
                     data : [
                         {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                         {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                     ]
                 },
                 markLine : {
                     data : [
                         {type : 'average', name : '平均值'}
                     ]
                 }
             }*/
         ]
     });
     
     myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
     
     var names=[];    //类别数组（实际用来盛放X轴坐标值）
     var nums=[];    //销量数组（实际用来盛放Y坐标值）
     
     $.ajax({
     type : "post",
     async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
     url : ctx + '/index/selectType.action',    //请求发送到TestServlet处
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
                    series: [{
                    	 
                    		 name: "资产数量",
                    		 data: nums,  
                    		
                    }]
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

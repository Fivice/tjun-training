$(function() {
	//在用圆环图
	var myRightChart = echarts.init(document.getElementById('right'));
     // 显示标题，图例和空的坐标轴
	myRightChart.setOption({
		title : {
	        text: '资产状态对比',
	       /*  subtext: '纯属虚构', */
	       /* x:'center'*/
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'right',
	        data:['在用','闲置','维修','待报废']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            /*dataView : {show: true, readOnly: false},*/
	            magicType : {
	                show: true, 
	                type: ['pie', 'funnel'],
	                option: {
	                    funnel: {
	                        x: '25%',
	                        width: '50%',
	                        funnelAlign: 'left',
	                        max: 1548
	                    }
	                }
	            },
	           /* restore : {show: true},
	            saveAsImage : {show: true}*/
	        }
	    },
	    calculable : true,
	    series : [
	        {
	            name:'资产状态',
	            type:'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[]
	           /* data:[
	                {value:335, name:'闲置'},
	                {value:310, name:'在用'},
	                {value:234, name:'报废'},
	                {value:135, name:'调拨中'},
	            ]*/
	        }
	    ]
	});
     
	myRightChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
     
    /* var names=[];    //类别数组（实际用来盛放X轴坐标值）
     var nums=[];    //销量数组（实际用来盛放Y坐标值）
*/     
     $.ajax({
     type : "post",
     async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
     url :  ctx + '/index/selectRow.action',   //请求发送到TestServlet处
     data : {},
     dataType : "json",        //返回数据形式为json
     success : function(result) {
    	 /*alert(result.name)*/
         //请求成功时执行该函数内容，result即为服务器返回的json对象
         if (result) {
                /*for(var i=0;i<result.length;i++){       
                   names.push(result[i].name);    //挨个取出类别并填入类别数组
                 }
                for(var i=0;i<result.length;i++){       
                    nums.push(result[i].num);    //挨个取出销量并填入销量数组
                  }*/
        	 myRightChart.hideLoading();    //隐藏加载动画
        	 myRightChart.setOption({        //加载数据图表
                    series: [{
                    	data:[
                        // 根据名字对应到相应的系列
                    	{value:(result.num1)*100, name:result.name1},
                    	{value:(result.num2)*100, name:result.name2},
                    	{value:(result.num3)*100, name:result.name3},
                    	{value:(result.num4)*100, name:result.name4},
                    	]
                    }]
                	
                });
                
         }
     
    },
     error : function(errorMsg) {
         //请求失败时执行该函数
     alert("图表请求数据失败!");
     myRightChart.hideLoading();
     }
})



});

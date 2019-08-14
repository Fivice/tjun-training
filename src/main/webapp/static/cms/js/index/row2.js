$(function() {
	//在用圆环图
	 var myChart = echarts.init(document.getElementById('main'));
     // 显示标题，图例和空的坐标轴
     myChart.setOption({
    	            title: {//标题组件
    	                text: '资产概况',
    	                /*left:'50px',*///标题的位置 默认是left，其余还有center、right属性
    	                /*textStyle: {    
    	                color: "#436EEE",    
    	                fontSize: 17,   
    	                }*/
    	            },
    	            tooltip : { //提示框组件
    	                trigger: 'item', //触发类型(饼状图片就是用这个)
    	                formatter: "{a} <br/>{b} : {c} ({d}%)" //提示框浮层内容格式器
    	            },
    	            color:['#48cda6','#D3D3D3'],  //手动设置每个图例的颜色
    	            /*legend: {  //图例组件
    	                //right:100,  //图例组件离右边的距离
    	                orient : 'horizontal',  //布局  纵向布局 图例标记居文字的左边 vertical则反之
    	                width:40,      //图行例组件的宽度,默认自适应
    	                x : 'right',   //图例显示在右边
    	                y: 'center',   //图例在垂直方向上面显示居中
    	                itemWidth:10,  //图例标记的图形宽度
    	                itemHeight:10, //图例标记的图形高度
    	                data:['正常','一般','提示','较急','特急'],
    	                textStyle:{    //图例文字的样式
    	                    color:'#333',  //文字颜色
    	                    fontSize:12    //文字大小
    	                }
    	            },*/
    	            /*graphic:{
    	                type:'text',
    	                left:'center',
    	                top:'center',
    	                style:{
    	                    text:'在用',
    	                    textAlign:'left',
    	                    fill:'#000',
    	                    width:10,
    	                    height:30
    	                }
    	            },*/
    	            /*xAxis: {
    	                 data: []
    	             },
    	             yAxis: {},*/
    	            series : [ //系列列表
    	                {
    	                    name:'资产概况',  //系列名称
    	                    type:'pie',   //类型 pie表示饼图
    	                    center:['50%','50%'], //设置饼的原心坐标 不设置就会默认在中心的位置
    	                    radius : ['55%', '70%'],  //饼图的半径,第一项是内半径,第二项是外半径,内半径为0就是真的饼,不是环形
    	                    itemStyle : {  //图形样式
    	                        normal : { //normal 是图形在默认状态下的样式；emphasis 是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
    	                            label : {  //饼图图形上的文本标签
    	                                show : false  //平常不显示
    	                            },
    	                            labelLine : {     //标签的视觉引导线样式
    	                                show : false  //平常不显示
    	                            }
    	                        },
    	                        emphasis : {   //normal 是图形在默认状态下的样式；emphasis 是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
    	                            label : {  //饼图图形上的文本标签
    	                                show : true,
    	                                position : 'center',
    	                                textStyle : {
    	                                    fontSize : '10',
    	                                    fontWeight : 'bold'
    	                                }
    	                            }
    	                        }
    	                    },
    	                    data:[]
    	                    /*data:[
    	                        {value:80, name:'正常'},
    	                        {value:10, name:'一般'},
    	                        {value:30, name:'提示'},
    	                        {value:20, name:'较急'},
    	                        {value:25, name:'特急'}
    	                    ]*/
    	                }
    	            ]
     });
     
     myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
     
     /*var names=[];    //类别数组（实际用来盛放X轴坐标值）
     var nums=[];    //销量数组（实际用来盛放Y坐标值）
*/     
     $.ajax({
     type : "post",
     async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
     url :  ctx + '/index/selectRow2.action',   //请求发送到TestServlet处
     data : {},
     dataType : "json",        //返回数据形式为json
     success : function(result) {
    	 /*alert(result.num)*/
         //请求成功时执行该函数内容，result即为服务器返回的json对象
         if (result) {
                /*for(var i=0;i<result.length;i++){       
                   names.push(result[i].name);    //挨个取出类别并填入类别数组
                 }
                for(var i=0;i<result.length;i++){       
                    nums.push(result[i].num);    //挨个取出销量并填入销量数组
                  }*/
                myChart.hideLoading();    //隐藏加载动画
                myChart.setOption({        //加载数据图表
                    /*xAxis: {
                        data: names
                    },*/
                    series: [{
                    	data:[
                        // 根据名字对应到相应的系列
                    	{value:(result.num)*100, name:result.name},
                    	{value:100-((result.num)*100), name:'其余'},
                    	]
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

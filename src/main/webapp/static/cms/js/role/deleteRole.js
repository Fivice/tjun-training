
    //删除员工
     function del(){
        obj = document.getElementsByName("roleId");
        roleIds = [];
        for(k in obj){
            if(obj[k].checked)
            	roleIds.push(obj[k].value);
        }
         alert(roleIds); 
        if(roleIds.length==0){
        	/*alert("请至少选择一条数据！")*/
        	layer.alert('请至少选择一条数据！', {
        		icon: 5,
        		title: "提示"
        		});
        }else {
        	layer.confirm('您确定要删除数据吗？', {
        		btn: ['确定','取消'] //按钮
        		}, function()
        		{
        		
        		 //alert(roleIds); 
           	 $.ajax({  
                   type: 'POST',  
                   url: 'http://localhost:8080/task/role/delete.action',//发送请求  
                   data:{ 
                   	/* _method : "delete", */
                	   roleIds:roleIds.toString()
                   	},
                  /*  contentType:'application/json;charset=utf-8', */
                   dataType : "json",  
                   success: function(result) {  
                   	if (result.code == 1) {
   						parent.layer.msg("删除数据成功!", {
   							shade : 0.3,
   							time : 1500
   						}, function() {
   							window.location.reload(); // 刷新父页面
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
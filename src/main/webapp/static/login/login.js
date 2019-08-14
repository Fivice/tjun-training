$().ready(function() {
	/**登录验证**/
	$("#login_form").validate({
		rules: {
			loginAccount: "required",
			loginPass: {
				required: true,
				minlength: 5
			},
		},
		messages: {
			loginAccount: "请输入姓名",
			loginPass: {
				required: "请输入密码",
				minlength: jQuery.format("密码不能小于{0}个字 符")
			},
		},
		submitHandler:function(form){
            $.ajax({
        		dataType : "json",
        		url : "sysuser/login.action",  
        		type : "post", 
        		data : $("#login_form").serialize(), 
        		success : function(data) {
        			$.alert(data.message);
        			if(data.success){
        				window.location.href = 'sysuser/view.action';  
        							}
        		},
        		error : function (e){
        			var d = e.responseJSON;
        			if(d){
        				$.alert(d.message);
        			}
        		}
        	});
            return false; //阻止form提交
        }
	});
	/**注册验证**/
	$("#register_form").validate({
		rules: {
			loginAccount:{ 
				required:true,
				remote: {
	                url: "sysuser/getUserNameCount.action",
	                type: "post",
	                dataType: "json",
	                data: {
	                	loginAccount: function () {
	                        return $("#register_account").val();
	                    }
	                },
	                dataFilter: function (data) {　　　　//判断控制器返回的内容
	                	data = jQuery.parseJSON(data);
	                    return data.success;
	                }
	            }
			},
			loginPass: {
				required: true,
				minlength: 5,
				maxlength:20
			},
			rloginPass: {
				equalTo: "#register_password"
			},
			userEmail: {
				required: true,
				email: true,
				remote: {
	                url: "sysuser/getEMailCount.action",
	                type: "post",
	                dataType: "json",
	                data: {
	                	email: function () {
	                        return $("#register_email").val();
	                    }
	                },
	                dataFilter: function (data) {　　　　//判断控制器返回的内容
	                	data = jQuery.parseJSON(data);
	                    return data.success;
	                }
	            }
			}
		},
		messages: {
			loginAccount:{  
				required: "请输入姓名",
                remote: "用户名已存在"
            },
			loginPass: {
				required: "请输入密码",
				minlength: jQuery.format("密码不能小于{0}个字 符"),
				maxlength: jQuery.format("密码不能大于{0}个字 符"),
			},
			rloginPass: {
				required: "请输入确认密码",
				equalTo: "两次密码不一样"
			},
			userEmail: {
				required: "请输入邮箱",
				email: "请输入有效邮箱",
				remote: "邮箱已存在"
			}
		},
		submitHandler:function(form){
            $.ajax({
        		dataType : "json",
        		url : "sysuser/register.action",  
        		type : "post", 
        		data : $("#register_form").serialize(), 
        		success : function(data) {
        			$.alert(data.message);
        			if(data.success){
        				window.location.href = 'page/main.action';
        			}
        		},
        		error : function (e){
        			var d = e.responseJSON;
        			if(d){
        				$.alert(d.message);
        			}
        		}
        	});
            return false; //阻止form提交
        }
	});
	/**隐藏显示登录注册**/
	$("#register_btn").click(function() {
		$("#register_form").css("display", "block");
		$("#login_form").css("display", "none");
	});
	$("#back_btn").click(function() {
		$("#register_form").css("display", "none");
		$("#login_form").css("display", "block");
	});
});
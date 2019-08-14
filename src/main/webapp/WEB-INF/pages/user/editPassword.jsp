<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>修改密码</title>
</head>

<body>

<div class=".container" style="margin-top:100px;width:250%;">
	<form class="form-horizontal" id="form" method="post">
		<br><br><br><br>
	  <div class="form-group">
		  <label class="control-label col-sm-2" title="">
			  账户：<i class="fa icon-question hide"></i></label>
		  <div class="col-sm-8">
			  <input type="text"  id="username" name="loginAccount" value="${user.loginAccount}" readonly="readonly" class="form-control" style="width:250px;">
		  </div>
	  </div>
	  <div class="form-group">
		  <label class="control-label col-sm-2" title="">
			  <span class="required" style="color: #af0000" aria-required="true">*</span> 旧密码：<i class="fa icon-question hide"></i></label>
		  <div class="col-sm-8">
			  <input type="password"  id="oldpass" name="oldpass"  class="form-control" style="width:250px;">
		  </div>
	  </div>
	  <div class="form-group">
		  <label class="control-label col-sm-2" title="">
			  <span class="required" style="color: #af0000" aria-required="true">*</span> 新密码：<i class="fa icon-question hide"></i></label>
		  <div class="col-sm-8">
			  <input type="password" id="newpass" name="loginPass"  class="form-control" style="width:250px;">
		  </div>
	  </div>
	  <div class="form-group">
		  <label class="control-label col-sm-2" title="">
			  <span class="required" style="color: #af0000" aria-required="true">*</span> 确认新密码：<i class="fa icon-question hide"></i></label>
		  <div class="col-sm-8">
			  <input type="password" id="comfirmPassword" name="comfirmPassword"  class="form-control" style="width:250px;">
		  </div>
	  </div><br>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">  </label>&emsp; &emsp;&emsp;&emsp;&emsp;&emsp;
		  <button type="button" class="btn btn-sm btn-primary" onclick="subForm()" id="btnSubmit">确认修改</button>&nbsp;
	  </div>
	  </form>
</div>

</body>
<!-- layer弹出框js -->
    <script src="${ctxsta}/common/layer/layer.js"></script>
	<script src="${ctxsta }/entity/user/bootstrpValidator.js" type="text/javascript"></script>
<script>
    //提交表单
    function subForm(){

        //开启验证
        $('#form').data('bootstrapValidator').validate();
        setTimeout(function() {
            if($('#form').data('bootstrapValidator').isValid()){

                $.ajax({
                    dataType : 'json',
                    url:'${ctx}/sysuser/edit.action',
                    data:$('#form').serialize(),
                    type:"post",
                    success : function(data) {
                        //console.log(data);
                        //console.log(data.code);
                        if (data.code == 1) {
                            layer.msg('修改密码成功!', {
                                icon : 1,
                                time : 1000
                            }, function() {
                                window.location.reload(); // 刷新父页面
                            });
                        }  else {
                            layer.msg(result.message, {
                                icon: 2,
                                time: 1000
                            });
                        }
                    },
                });
            }
        },2500);

    }
</script>
<%--<script type="text/javascript">
$(document).ready(function(){
    var error = false;
	
	$("#oldpass").blur(function(){		
		var oldpass = $("#oldpass").val();
		if(oldpass =='') {
			showError('oldpass', '密码不能为空');
			error = true;
			return;
		}else {
            $("#oldpass").css({"border-color":"green"});
            $("#oldpassTip").css({"display":"none"});
        }

		/* $.post("modifyPassProcess.php", {flag:2, username:username, oldpass:oldpass}, function(data){
			if(data) {
				$("#oldpass").css({"border-color":"green"});
				$("#oldpassTip").css({"display":"none"});
			} else {
				showError('oldpass', '密码错误');
				error = true;
			}
		}); */
	});

	$("#newpass").blur(function(){
		var newpass = $("#newpass").val();
		if(newpass == '') {
			showError('newpass', '新密码不能为空');
			error = true;
		}
		else {
			$("#newpass").css({"border-color":"green"});
			$("#newpassTip").css({"display":"none"});
		}
	});

	$("#newpassAgain").blur(function(){
		var newpass = $("#newpass").val();
		if(newpass == '') {
			showError('newpass', '新密码不能为空');
			error = true;
			return;
		}

		var newpassAgain = $("#newpassAgain").val();
		if(newpassAgain != newpass) {
			showError('newpassAgain', '与输入的新密码不一致');
			error = true;
		}
		else {
			$("#newpassAgain").css({"border-color":"green"});
			$("#newpassAgainTip").css({"display":"none"});
		}
	});
	
	$("#savePass").click(function(event){
		$("#username").blur();
		$("#oldpass").blur();
		$("#newpass").blur();
		$("#newpassAgain").blur();

		if(!error) {
			var username = $("#username").val();
			var newpass = $("#newpass").val();
            $.ajax({
                dataType : 'json',
                url:'${ctx}/sysuser/edit.action',
                data:$('#form').serialize(),
                type:"post",
                success : function(data) {
                    //console.log(data);
                    //console.log(data.code);
                    if (data.code == 1) {
                        layer.msg('修改密码成功!', {
                            icon : 1,
                            time : 1000
                        }, function() {
                            window.location.reload(); // 刷新父页面
                        });
                    } else {
                        layer.msg('原密码错误，请重新输入!', {
                            icon : 2,
                            time : 1000
                        },function() {
                            window.location.reload(); // 刷新父页面
                        });
                    }
                },

            });
		}

		event.preventDefault();
		return false;
	});
});

function showError(formSpan, errorText) {
	$("#" + formSpan).css({"border-color":"red"});
	$("#" + formSpan + "Tip").empty();
	$("#" + formSpan + "Tip").append(errorText);;
	$("#" + formSpan + "Tip").css({"display":"inline"});
}
 
	    


</script>--%>
</html>

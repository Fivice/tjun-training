<%--
  Created by IntelliJ IDEA.
  User: Fivice
  Date: 2018/11/26
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/layouts/cms/default.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>用户信息</title>

</head>

<body class="gray-bg">
<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-lg12">
            <div class="layui-card">
                <div class="layui-card-header">
                    ${user.userName}个人资料
                </div>
                <div class="layui-card-body">

                    <form class="layui-form layui-row" action="" lay-filter="userInfo">

                        <div class="layui-form-item layui-col-lg6">
                            <label class="layui-form-label">账号</label>
                            <div class="layui-input-block ">
                                <input type="hidden" id="userId" value="${user.userId}">
                                <input type="hidden" id="loginAccount" value="${user.loginAccount}">
                                <input type="text" name="loginAccount" lay-verify="loginAccount" readonly autocomplete="off" placeholder="登录账号" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item layui-col-lg6">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-block">
                                <input type="hidden" id="username" value="${user.userName}">
                                <input type="text" name="username" lay-verify="username" autocomplete="off" placeholder="姓名" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item layui-col-lg6">
                            <label class="layui-form-label">手机号</label>
                            <div class="layui-input-block">
                                <input type="hidden" id="telephone" value="${user.telephone}">
                                <input type="text" name="telephone" lay-verify="telephone" autocomplete="off" placeholder="手机号" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item layui-col-lg6">
                            <label class="layui-form-label">邮箱</label>
                            <div class="layui-input-block">
                                <input type="hidden" id="email" value="${user.email}">
                                <input type="text" name="email" lay-verify="email" autocomplete="off" placeholder="邮箱" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item layui-col-lg6">
                            <label class="layui-form-label">部门</label>
                            <div class="layui-input-block">
                                <input type="hidden" id="department" value="${user.department.areaId}">
                                <select name="department" lay-filter="department">
                                    <c:forEach var="department" items="${departments}">
                                        <option value="${department.areaId}">${department.areaName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item layui-col-lg6">
                            <label class="layui-form-label">性别</label>
                            <div class="layui-input-block">
                                <input type="hidden" id="sex" value="${user.sex}">
                                <input type="radio" name="sex" value="1" title="男" checked="">
                                <input type="radio" name="sex" value="2" title="女">
                            </div>
                        </div>

                        <div class="layui-form-item layui-col-lg6">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit="" lay-filter="updateUserInfo">修改保存</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>

    </div>
</div>
<%--layui.js--%>
<script src="${ctxsta}/common/layui/layui/layui.js" charset="utf-8"></script>
<script>
    $(function () {
        //用户信息获取
        userInfoInit();
        //layui框架初始化
        layuiInit();
    })
    var userInfo = {
        userId:"",
        username:"",
        loginAccount:"",
        telephone:"",
        email:"",
        department:"",
        sex:""
    }

    function userInfoInit() {
        console.log("用户信息初始化")
        userInfo.username = $("#username").val()
        userInfo.loginAccount = $("#loginAccount").val()
        userInfo.telephone = $("#telephone").val()
        userInfo.email = $("#email").val()
        userInfo.department = $("#department").val()
        userInfo.sex = $("#sex").val()
    }
    function layuiInit() {
        console.log("layui初始化")
        layui.use('form', function(){
            var form = layui.form
                ,layer = layui.layer;


            //自定义验证规则
            form.verify({
                username: function(value){
                    if(value.length < 1){
                        return '标题至少得1个字符啊';
                    }
                }
                ,telephone: [/^[1][3,4,5,7,8][0-9]{9}$/, '手机格式不正确']
                ,email: [/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/, '邮箱格式不正确']

            });


            //监听提交
            form.on('submit(updateUserInfo)', function(data){
                console.log(JSON.parse(JSON.stringify(data.field)));
                $.ajax({
                    type: "get",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url:ctx+"/sysuser/updateUserInfo.action",
                    data:JSON.parse(JSON.stringify(data.field)),
                    success:function (result) {
                        console.log(result)
                        layer.msg(result.message =="SUCCESS"?"保存成功":"保存失败")
                    },
                    error:function () {
                        layer.msg("更新信息失败")
                    }
                })
                return false;
            });

            //表单初始赋值
            form.val('userInfo', {
                "username": userInfo.username// "name": "value"
                ,"loginAccount": userInfo.loginAccount
                ,"telephone": userInfo.telephone
                ,"email":userInfo.email
                ,"department": userInfo.department //复选框选中状态
                ,"sex": userInfo.sex
            })


        });
    }
</script>
</body>
</html>


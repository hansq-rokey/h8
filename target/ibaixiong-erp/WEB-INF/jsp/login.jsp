<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=Edge">
   <link href="../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
   <link href="../css/login.css" rel="stylesheet" type="text/css">
   <script src="<%=path %>/js/login.js" type="text/javascript"></script>
   <title>登录</title>
</head>
<body>
<div class="content">
    <form action="/dologin.html" onsubmit="return checkInput();" method="post">	
        <div class="loginbox">
            <div class="leftpart">
                <img src="../images/loginbg.png" class="loginbg">
                <div class="colorlayer"></div>
                <img src="../images/loginlogo.png" class="loginlogo">
                <p class="firstline">欢迎进入</p>
                <p class="secondline">白熊科技ERP系统</p>
                <p class="thirdline">白熊，温暖我的宝贝</p>
            </div>
            <div class="rightpart">
                <h3 class="logintitle">登录</h3>
                <input type="text" id="username" name="loginName" value="${admin.loginName }" placeholder="用户名" class="username">
                <input type="password" id="pwd" name="userPwd" value="" placeholder="密码" class="userpass">
                <p class="red">${msg }</p>
                <input type="submit" value="登    录" class="loginbtn">
            </div>
        </div>
    </form>
</div>
  </body>
</html>

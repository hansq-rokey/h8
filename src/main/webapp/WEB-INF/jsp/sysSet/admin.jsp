<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/persona-information.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>操作日志</title>
    <script type="text/javascript">
    	function updateMobile(mobile){
    		//alertLayel(mobile);
    		$.ajax({
   			   url: "/sysSet/admin/updatePhone.html?phone="+mobile,
   			   type: "post",
   			   dataType:"json",
   			   cache:false,
   			   success: function(obj){
   		  			if ( !checkCode( obj ) ) {
   						return;
   				    }
   				 	if ( obj.code == 0 ) {
   						//获取登陆用户成功之后给值
   						alertLayel("修改电话号码成功!");
   					}
   			   }
   			});
    	}
    	function updatePwd(){
       		//alertLayel(mobile);
       		var oldPwd = $("#oldPwd").val();
       		var newPwd1 = $("#newPwd1").val();
       		var newPwd2  = $("#newPwd2").val();
       		if(oldPwd == null || oldPwd ==undefined || oldPwd == ''){
       			alertLayel("原密码不可为空");
				return false;
			}
       		if(newPwd1 == null || newPwd1 ==undefined || newPwd1 == ''){
       			alertLayel("新密码不可为空");
				return false;
			}
       		if(newPwd2 == null || newPwd2 ==undefined || newPwd2 == ''){
       			alertLayel("确认新密码不可为空");
				return false;
			}
       		if(newPwd1 != newPwd2){
       			alertLayel("新密码与确认新密码不一致");
				return false;
       		}
       		$.ajax({
    			   url: "/sysSet/admin/updatePwd.html?oldPwd="+oldPwd+"&newPwd="+newPwd1,
    			   type: "post",
    			   dataType:"json",
    			   cache:false,
    			   success: function(obj){
    		  			if ( !checkCode( obj ) ) {
    						return;
    				    }
    				 	if ( obj.code == 0 ) {
    				 		alertLayel("修改密码成功!");
    						$(".updatePwdDiv").hide();
    					}
    			   }
    			});
    	}
    	function showPwdUp(){
    		$("#oldPwd").val("");
       		$("#newPwd1").val("");
       		$("#newPwd2").val("");
    		$(".updatePwdDiv").show();
    	}
    	function testTran(){
    		$.ajax({
 			   url: "/order/testTran.html",
 			   type: "post",
 			   dataType:"json",
 			   cache:false,
 			   success: function(obj){
 				  alertLayel(obj.code);
 				 alertLayel("测试成功!");
 			   }
 			});
    	}
    </script>
</head>
<body>
    <section>
            <div class="bigbox">
                <p class="information">登录账户：${admin.loginName }</p>
                <p class="information">姓       名：${admin.userName }</p>
                <p class="information">部       门：${admin.org.name }</p>
                <p class="information">手  机  号：${admin.phone }</p>
            </div>
            <input type="button" class="passwordbtn" onclick="showPwdUp()" value="修改密码"/>
            <!-- <input type="button" value="测试" onclick="testTran()" class="confirm"> -->
    </section>
<div class="pop updatePwdDiv">
    <div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">修改密码<i class="closeicon"></i> </h3>
        <div class="row">
            <span class="infortype tr" style="width: 100px;">原密码：</span>
            <input type="password" class="oldpass" id="oldPwd">
        </div>
        <div class="row">
            <span class="infortype tr" style="width: 100px;">新密码：</span>
            <input type="password" class="newpass" id="newPwd1">
        </div>
        <div class="row">
            <span class="infortype tr" style="width: 100px;">确认新密码：</span>
            <input type="password" class="connewpass" id="newPwd2">
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="updatePwd()" class="confirm">
        </div>
    </div>
</div>
</body>
</html>
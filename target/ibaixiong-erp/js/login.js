function checkInput(){
	//alert("checkInput()");
	var uname=$.trim($("#username").val());
	var pwd=$("#pwd").val();
	if(uname.length<=0||uname==null||uname==""){
		alertLayel("用户名不能为空！");
		$("#username").val("");
		$("#username").focus();
		return false;
	}else if(pwd.length<=0||pwd==null||pwd==""){
		alertLayel("密码不能为空！");
		$("#pwd").val("");
		$("#pwd").focus();
		return false;
	}else{
		return true;
	}
	
}
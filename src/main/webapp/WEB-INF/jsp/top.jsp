<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function logout(){
		location.href = "/logout.html";
	}
</script>
<header class="pubtop">
    <img src="../images/userimg.png" class="userimg fl">
    <span class="username fl">${sessionScope.admin.userName}<i class="arrow arrowdown"></i> <i onclick="logout()">注销</i></span>
</header>

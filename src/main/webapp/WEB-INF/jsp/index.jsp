<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" style="overflow:hidden;">
<link rel="icon" href="../../images/xLogin.ico"
	mce_href="../../images/xLogin.ico"
	type="image/x-icon">
<head>
    <jsp:include page="base.jsp"></jsp:include>
    <script src="../../js/index.js" type="text/javascript" ></script>
</head>

<body>
<jsp:include page="top.jsp"></jsp:include>
<aside class="publeftnav">
    <ul class="publeftnavul">
        <jsp:include page="left.jsp"></jsp:include>
    </ul>
</aside>
<div class="rightbox">
    <iframe id="rightFrame" name="rightFrame" width="100%" height="100%" src="">
    </iframe>
</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../../css/notice.css" rel="stylesheet" type="text/css">
    <script src="../../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../../js/public.js" type="text/javascript" ></script>
    <title>通知</title>
</head>
<body>
<div style="display: none;">
	<form action="/notice/list.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
        <table>
        	<thead>
        		<tr>
        			<td>通知</td>
        			<td>通知分类</td>
        			<td>发布时间</td>
        			<td>操作</td>
        		</tr>
        	</thead>
        	<tbody>
        	<c:forEach  items="${listErpNoticeInfo}" var="item">
        		<tr>
        			<td>${ item.name }</td>
        			<td>${ item.erpNoticeCategory.name }</td>
        			<td><fmt:formatDate value="${ item.createDateTime }" pattern="yyyy/MM/dd"/></td>
        			<td><a href="/notice/detail.html?id=${ item.id }" class="link" target="_self">详情</a></td>
        		</tr>
        	</c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="/plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="/css/stroage-operation.css" rel="stylesheet" type="text/css">
    <script src="/plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="/js/public.js" type="text/javascript" ></script>
    <title>入库</title>
</head>
<body>
<div style="display: none;">
	<form action="/ledger/storage/list/detail.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	 	<input type="hidden" name="formId" id="formId" value="${formId }">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
    	<div class="pathbox">
            <span class="light">库存列表</span>>
            <span class="darker">操作详情</span>
        </div>
        <table>
                	<thead>
                		<tr>
                			<td>序列号</td>
                			<td>唯一码</td>
                			<td>生产日期</td>
                			<td>操作</td>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${list }" var="item" varStatus="statusItem">
                			<tr>
	                			<td>${statusItem.count }</td>
	                			<td>${item.uniqueCode }</td>
	                			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY-MM-dd HH:mm"/></td>
	                			<td><a href="/ledger/trace.html?hardwareId=${item.id }" class="link">物联网详情</a></td>
	                		</tr>
                		</c:forEach>
                	</tbody>
                	</table>
                	<jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
</body>
</html>
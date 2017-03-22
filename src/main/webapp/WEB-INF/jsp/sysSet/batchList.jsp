<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/productmanage.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <title>产品批次管理</title>
</head>
<body>
<div style="display: none;">
	<form action="/sysSet/batch/list.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
        <table>
        	<thead>
        		<tr>
        			<td>产品名称</td>
        			<td>产品规格</td>
        			<td>产品编码</td>
        			<td>操作</td>
        		</tr>
        	</thead>
        	<tbody>
        	<c:forEach items="${list}" var="data" varStatus="st">
        		<tr>
        			<td>${data.category.name}</td>
                	<td>${data.categoryModel.name}</td>
        			<td>${data.categoryModelFormat.name}</td>
        			<td>
                       <a href="/sysSet/batch/detailsList.html?batchId=${data.id} " class="link">详情</a>
        			</td>
        		</tr>
        	</c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
</body>
</html>
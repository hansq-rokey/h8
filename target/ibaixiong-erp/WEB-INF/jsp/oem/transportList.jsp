<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/scantransport-scanrecord.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
	<script src="../../../plug/adddatetime.js" type="text/javascript" ></script>
    <title>扫描运输-扫描记录</title>
</head>
<body>
    <section>
        <span class="light"><a href="/oem/transport/put.html">扫描运输</a></span>>
        <span class="darker">扫描记录</span>
        <div class="content">
            <form action="/oem/transport/queryList.html" method="post">
            	<input type="hidden" name="pageNo" id="pageNo" value="">
	            <input type="text" class="product-name" name="queryStr" value="${queryStr }" placeholder="运输码、产品名称">
	            <input type="text" name="startDate" value="${startDate }" class="strat-time datetimepicker" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <span>至</span>
	            <input type="text" name="endDate" value="${endDate }" class="end-time datetimepicker" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <input type="submit" class="search" value="搜索">
            </form>
        </div>
        <table class="scan-recordtable">
        	<thead>
        		<tr>
        			<td>序号</td>
        			<td>运输码</td>
        			<td>产品名称</td>
        			<td>产品规格</td>
        			<td>产品数量</td>
        			<td>包装日期</td>
        			<td>操作</td>
        		</tr>
        	</thead>
        	<tbody>
        		<c:forEach  items="${list }" var="item" varStatus="itemStatus">
        		<tr>
        			<td>${itemStatus.index+1 }</td>
        			<td>${item.code }</td>
        			<td>${item.format.basicCategoryModel.name }</td>
        			<td>${item.format.name }</td>
        			<td>${item.countSum }</td>
        			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY/MM/dd"/></td>
        			<td><a href="/oem/transport/queryDetailList.html?codeId=${item.id }" class="link">详情</a></td>
        		</tr>
        		</c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
</body>
</html>
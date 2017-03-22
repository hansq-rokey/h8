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
    <link href="../../../../css/scanpackage-scanrecord.css" rel="stylesheet" type="text/css">
    <script src="../../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../../js/public.js" type="text/javascript" ></script>
	<script src="../../../../plug/adddatetime.js" type="text/javascript" ></script>
    <title>扫描包装-扫描记录</title>
    <script>
	$(document).ready(function(){
		$('.packing-code').bind('input propertychange', function() {  
			$('.datetimepicker').attr('value',''); 
		});
		$('.datetimepicker').on('click', function() {  
			$('.packing-code').attr('value',''); 
		});
	});
</script>
</head>
<body>
    <section>
    	<a href="/oem/scanpackage/selectformat.html" style="cursor: pointer;"> 
        <span class="light">OEM厂</span>>
        <span class="light">生成防伪码</span>></a>
        <span class="darker">扫描记录</span>
        <div class="content">
        	<form action="/oem/scanpackage/scanhistory.html" method="post">
		 		<input type="hidden" name="pageNo" id="pageNo" value="">
	            <input type="text" name="uniqueCode" class="packing-code" placeholder="唯一码" value="${ uniqueCode }">
	            <input type="text" name="startDate" class="strat-time datetimepicker"  value="${ startDate }" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <span>至</span>
	            <input type="text" name="endDate" class="end-time datetimepicker"  value="${ endDate }" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <input type="submit" class="search" value="搜索" >
            </form>
        </div>
        <table class="scan-recordtable">
        	<thead>
        		<tr>
        			<td>序号</td>
        			<td>唯一码</td>
        			<td>产品名称</td>
        			<td>产品规格</td>
        			<td>生产日期</td>
        		</tr>
        	</thead>
        	<tbody>
        	<c:forEach  items="${listErpHardwareProduct}" var="item">
        		<tr>
        			<td>${ item.id }</td>
        			<td>${ item.uniqueCode }</td>
        			<td>${ item.categoryModelFormat.basicCategoryModel.name }</td>
        			<td>${ item.categoryModelFormat.name }</td>
        			<td><fmt:formatDate value="${ item.mfgDateTime }" pattern="YYYY-MM-dd" /></td>
        		</tr>        		
        	</tbody>
            </c:forEach>
        </table>
        <jsp:include page="../../include/pages.jsp"></jsp:include>
	</section>
</body>
</html>

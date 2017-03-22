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
    <link href="../../../css/abnorma-list.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script type="text/javascript" charset="utf-8" src="../../../plug/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../../../plug/ueditor/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="../../../plug/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script src="../../../plug/adddatetime.js" type="text/javascript" ></script>
    <title>异常列表</title>
</head>
<body>
    <section>
        <p class="content">
            <form action="/oem/exception/queryList.html" method="post">
            	<input type="hidden" name="pageNo" id="pageNo" value="">
	            <input type="text" class="packing-code" name="keywords" value="${keywords }" placeholder="唯一码">
	            <input type="text" class="strat-time datetimepicker startdata" name="startTime" value="${startTime }" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <span>至</span>
	            <input type="text" class="end time datetimepicker startdata" name="endTime" value="${endTime }" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <input type="submit" class="search" value="搜索">
        	</form>
        </p>
        <table class="abnormaltable">
        	<thead>
        		<tr>
        			<td width="180">运输码</td>
        			<td width="240">唯一码</td>
        			<td width="140">产品名称</td>
        			<td width="140">产品规格</td>
        			<td width="215">厂商名称</td>
        			<td width="168">厂商批次</td>
        			<td width="170">异常类型</td>
        			<td width="266">异常原因</td>
        			<td>标记时间</td>
        		</tr>
        	</thead>
        	<tbody>
        		<c:forEach items="${list }" var="item">
           			<tr>
           				<td>${item.transportCode }</td>
		       			<td>${item.uniqeCode }</td>
		       			<td>${item.productName }</td>
		       			<td>${item.productFormat }</td>
		       			<td>${item.oemName }</td>
		       			<td>${item.batch }</td>
		       			<td>
		       				<c:if test="${item.exceptionType==1 }">
		       					损坏
		       				</c:if>
		       				<c:if test="${item.exceptionType==2 }">
		       					丢失
		       				</c:if>
		       				<c:if test="${item.exceptionType==3 }">
	        					仓损
	        				</c:if>
		       			</td>
		       			<td>${item.description }</td>
		       			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY/MM/dd"/></td>
		       		</tr>
	            </c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
</body>
</html>
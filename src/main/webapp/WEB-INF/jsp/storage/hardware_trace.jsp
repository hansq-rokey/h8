<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../css/ledger-storage.css" rel="stylesheet" type="text/css">
    <script src="../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../js/public.js" type="text/javascript" ></script>
    <title>台账</title>
</head>
<body>
    <section>
		<span class="light">入库记录</span>>
        <span class="darker">物联网详情</span>
        <table>
		<thead>
		    <tr>
		    	<td>唯一码</td>
		    	<td>产品名称</td>
		    	<td>产品规格</td>
		    	<td>生产时间</td>
		    	<td>入库时间</td>
		    </tr>
		</thead>
		<tbody>
			<c:forEach items="${putList }" var="item">
				<tr>
			    	<td>${item.uniqueCode }</td>
			    	<td>${item.productName }</td>
			    	<td>${item.formatName }</td>
			    	<td><fmt:formatDate value="${item.mfgDate }" pattern="YYYY/MM/dd"/></td>
			    	<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY/MM/dd"/></td>
			    </tr>
			</c:forEach>
		</tbody>
		</table>
		<div class="paragraph">
			<p class="information">物联跟踪：</p>
			<c:forEach items="${list }" var="item">
				<p><span><fmt:formatDate value="${item.createDateTime }" pattern="YYYY-MM-dd HH:mm"/></span><span>${item.userName }</span>${item.operateExplain }</p>
			</c:forEach>
		</div>
    </section>
</body>
</html>
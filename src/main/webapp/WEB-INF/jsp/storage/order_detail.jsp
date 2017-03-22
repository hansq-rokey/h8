<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/order-detail.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/aftersalemanage.js" type="text/javascript" ></script>
    <title>订单管理</title>
</head>
<body>
    <section>
        <a href="/order/list.html"><span class="light">订单管理</span>></a>
        <span class="darker">订单详情</span>
        <div class="content">
        	<span>订单号：</span>
        	<span class="text">${order.orderNumber}</span>
        	<span>客户：</span>
        	<span class="text">${reciver.reveiveUserName }</span>
        	<span>联系电话：</span>
        	<span class="text">${reciver.mobilePhone }</span>
        	<span>配送地址：</span>
        	<span class="text">${reciver.provinceName }${reciver.cityName }${reciver.districtName }${reciver.detailAddress }</span>
        </div>
        <div class="route">
        	<ul>
        		<c:forEach items="${orderStatusList }" var="item">
        		 <c:if test="${item.dictCodeValue!=80 }">
        			<li class="${item.flow ? 'orange':'' }">
						<span>${item.dictCodeName }</span>
						<c:if test="${item.flow }">
							<span class="time"><fmt:formatDate value="${item.orderHistory.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
						</c:if>
					</li>
				 </c:if>
				</c:forEach>
        	</ul>
        </div>
        <table>
        <thead>
        	<tr>
        		<td>序号</td>
        		<td>名称</td>
        		<td>规格</td>
        		<td>价格</td>
        		<td>数量</td>
        		<td>小计</td>
        	</tr>
        </thead>
        <tbody>
        	<c:forEach items="${order.orderItems }" var="item" varStatus="statusItem">
	        	<tr>
	        		<td>${statusItem.index}</td>
	        		<td>${item.productTitle }</td>
	        		<td>${item.productModelFormatName }</td>
	        		<td>${item.discountUnitPrice }</td>
	        		<td>${item.num }</td>
	        		<td><fmt:formatNumber type="number" value="${item.totalPrice }" pattern="0.00" maxFractionDigits="2"/></td>
	        	</tr>
        	</c:forEach>
        </tbody>
        </table>
    </section>
</body>
</html>

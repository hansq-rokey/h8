<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.ibaixiong.entity.MallOrder"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="/plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="/css/order-manage.css" rel="stylesheet" type="text/css">
    <script src="/plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../../js/public.js" type="text/javascript" ></script>
    <script src="/js/aftersalemanage.js" type="text/javascript" ></script>
    <script src="/plug/adddatetime.js" type="text/javascript" ></script>
    <script src="../../../../plug/jQuery/LodopFuncs.js" type="text/javascript"></script>
    <title>订单-Bom清单表</title>
    <style class="style1">
.code-input {
	width: 300px;
	height: 40px;
	border: 1px solid #dcdcdc;
	margin-top: 30px;
	margin-left: 10px;
}

.print-bigbox {
	width: 350px;
	height: 350px;
	display: inline-block;
}

.print-logo {
	float: left;
}

.print-box {
	border: 2px solid black;
	width: 348px;
	height: 340px;
	margin: 5px;
	margin-left:-5px;
	padding: 7px;
}

.header {
	padding-bottom: 5px;
	border-bottom: 1px solid black;
	overflow: hidden;
}

.print-title {
	font-weight: bold;
	font-size: 16px;
	float: right;
	vertical-align: middle;
	line-height: 75px;
	float: right;
}

.print-product {
	margin-top: 20px;
}

.print-product p, .text, .textname {
	vertical-align: middle;
	display: inline-block;
}

print-product p {
	margin-left: 10px;
}

.prod-name {
	float: left;
	display: inline-block;
}

.text {
	width: 240px;
	float: left;
}

.textname {
	vertical-align: top;
}

.print-code {
	margin-top: 15px;
	padding-bottom: 20px;
	border-bottom: 1px solid #000;
}

.two-dimension {
	width: 330px;
	height: 66px;
}

.prod-table {
	text-align: center;
	width: 300px;
}
.prod-table tr td{
	height:20px;
	padding:0 5px;
	font-size:12px;
}
.line {
	height: 0px;
	width: 90px;
	display: inline-block;
	margin-bottom: 5px;
	float: left;
}

.company-name {
	display: inline-block;
	width: 160px;
	background: #fff;
	padding: 0 10px;
	margin: 0 auto;
	font-size:12px;
}

.print-footer {
	line-height: 35px;
	height: 35px;
	margin-top: -17px;
	text-align: center;
}
.fail-remind{
	font-size:18px;
	line-height:20px;
	width:100%;
	text-align:center;
	position:absolute;
	bottom:10px;
	left:0;
}
.only-code{
	position:relative;
}
</style>
</head>
<body>
    <section>
        <div class="content" style="margin-top: 20px;">
        	<form action="/bom/list.html" method="post">
	          	<input type="text" name="keywords" class="packing-code" value="${keywords }" placeholder="订单号">
	            <input type="text" name="start" value="${start }"  class="strat-time datetimepicker startdata" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <span>至</span>
	           	<input type="text" name="end" value="${ end }"  class="end time datetimepicker startdata" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <input type="hidden" value="${status}" id="status" name="status">
	            <input type="hidden" name="pageNo" id="pageNo" value="">
	            <input type="submit" class="search" value="搜索">        	
        	</form>
        </div>	
     <%--    <div class="content" style="margin-top: 20px;">
        	<form action="/order/list.html" method="post" id="uniqueCodeForm">
	          	<input type="text" size="44" name="uniqueCode" class="packing-code" value="${ uniqueCode }" placeholder="扫描唯一码搜索私人定制订单">
        	</form>
        </div>	 --%>
   </section>
   
<table class="ordermanagetable refundlist">
   	<thead>
   		<tr>
			<td style="min-width:70px;" >订单号</td>
			<td style="min-width:70px;">产品名称</td>
			<td style="min-width:70px;">规格</td>
			<td style="min-width:60px;">数量</td>
			<!-- <td>用户名</td>
			<td>白熊号</td> -->
			<td style="min-width:160px;">下单日期</td>
			<!-- <td style="max-width:300px;">邮寄地址</td> -->
			<td style="max-width:200px;">备注</td>
			<!-- <td style="min-width:100px;">订单状态</td> -->
			<td style="min-width:160px;">操作</td>
		</tr>
   	</thead>
   	<tbody>
   		<c:forEach items="${orderList }" var="order">
   			<c:forEach  items="${order.orderItems }" var="item" varStatus="itemStatus">
   				<c:choose>
   					<c:when test="${fn:length(order.orderItems)==1 }">
   						<tr>
       						<td><c:if test="${order.isCustomMade==1 }"><img alt="" src="/images/custom.png"> </c:if> ${order.orderNumber }</td>
       						<td>${item.productTitle }</td>
       						<td>${item.productModelFormatName }</td>
       						<td>x${item.num }</td>
       						<%-- <td>
       							<c:choose>
       								<c:when test="${order.email!=null }">${order.email }</c:when>
       								<c:when test="${order.phone!=null }">${order.phone }</c:when>
       								<c:otherwise>${order.bxNum }</c:otherwise>
       							</c:choose>
       						</td>
       						<td>${order.bxNum }</td> --%>
       						<td><fm:formatDate value="${order.createDateTime }" pattern="YYYY/MM/dd HH:mm"/> </td>
       						<%-- <td style="max-width:300px;">
       							<p class="reasontd" title="${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }">
       							<c:if test="${order.information!=null }">
       								${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }
       							</c:if>
       							</p>
       						</td> --%>
							<td style="max-width:200px;min-width:100px;">${order.remark }</td>
       						<%-- <td>
       							<c:choose>
									<c:when test="${order.status==10 }">待付款</c:when>
									<c:when test="${order.status==20 }">已付款</c:when>
									<c:when test="${order.status==28 }">已入库</c:when>
									<c:when test="${order.status==30 }">配货中</c:when>
									<c:when test="${order.status==40 }">已发货</c:when>
									<c:when test="${order.status==50 }">已完成</c:when>
									<c:otherwise>已关闭</c:otherwise>
								</c:choose>
       						</td> --%>
       						<td>
       							<%-- <c:if test="${order.status==30 }">
	       							<a href="#" class="link deliver-goods" data-id="${order.orderNumber }">发货</a>
       							</c:if>
       							<c:if test="${order.status==20 || order.status==28}">
	       							<a href="#" data-id="${order.orderNumber }" class="link distribution">确认配货</a>
       							</c:if>
       							<c:if test="${order.status==40 }">
       								<a href="#" data-id="${order.orderNumber }" class="link printOrder">打印订单</a>
       							</c:if> --%>
       							<a href="/bom/bom_list.html?orderNumber=${order.orderNumber }" class="link">清单列表</a>
       						</td>
       					</tr>
   					</c:when>
   				<c:otherwise>
   					<c:if test="${itemStatus.first }">
   						<tr>
        					<td td rowspan="${fn:length(order.orderItems) }">${order.orderNumber }</td>
        					<td>${item.productTitle }</td>
        					<td>${item.productModelFormatName }</td>
        					<td>x${item.num }</td>
        					<%-- <td rowspan="${fn:length(order.orderItems) }">
	        					<c:choose>
	        						<c:when test="${order.email!=null }">${order.email }</c:when>
	        						<c:when test="${order.phone!=null }">${order.phone }</c:when>
	        						<c:otherwise></c:otherwise>
	        					</c:choose>
	        				</td> --%>
	        				<%-- <td rowspan="${fn:length(order.orderItems) }">${order.bxNum }</td> --%>
	        				<td rowspan="${fn:length(order.orderItems) }"><fm:formatDate value="${order.createDateTime }" pattern="YYYY/MM/dd HH:mm"/> </td>
	        				<%-- <td style="max-width:300px;" rowspan="${fn:length(order.orderItems) }">
								<p class="reasontd" title="${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }">	
								<c:if test="${order.information!=null }">
       								${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }
        						</c:if>
        						</p> 

							</td> --%>
							<td rowspan="${fn:length(order.orderItems) }" style="max-width:200px;min-width:100px;">${order.remark }</td>
        					<%-- <td rowspan="${fn:length(order.orderItems) }">
        						<c:choose>
									<c:when test="${order.status==10 }">待付款</c:when>
									<c:when test="${order.status==20 }">已付款</c:when>
									<c:when test="${order.status==30 }">配货中</c:when>
									<c:when test="${order.status==40 }">已发货</c:when>
									<c:when test="${order.status==50 }">已完成</c:when>
									<c:otherwise>已关闭</c:otherwise>
								</c:choose>
        					</td> --%>
        					<td rowspan="${fn:length(order.orderItems) }">
	        					<%-- <c:if test="${order.status==30 }">
	       							<a href="#" class="link deliver-goods" data-id="${order.orderNumber }">发货</a>
       							</c:if>
       							<c:if test="${order.status==20 }">
	       							<a href="#" data-id="${order.orderNumber }" class="link distribution">确认配货</a>
       							</c:if>
       							<c:if test="${order.status==40 }">
       								<a href="#" data-id="${order.orderNumber }" class="link printOrder">打印订单</a>
       							</c:if> --%>
	        					<a href="/bom/bom_list.html?orderNumber=${order.orderNumber }" class="link">清单列表</a>
	        				</td>
	        			</tr>
   					</c:if>
   					<c:if test="${itemStatus.index>0 }">
   						<tr>
	        				<td>${item.productTitle }</td>
	        				<td>${item.productModelFormatName }</td>
	        				<td>x${item.num }</td>
	        			</tr>
   					</c:if>
   				</c:otherwise>
   			</c:choose>
   		</c:forEach>
   	</c:forEach>
   </tbody>
</table>
<jsp:include page="../include/pagesService.jsp"></jsp:include>
<!-- <script type="text/javascript">
	//扫描查询
	$('.packing-code').change(function(){
		$('#uniqueCodeForm').submit();
	})
</script> -->
</body>
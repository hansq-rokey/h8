<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
   
    
    <link href="../../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../../css/order-manage.css" rel="stylesheet" type="text/css">
    <script src="../../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../../js/public.js" type="text/javascript" ></script>
	<script src="../../../../plug/adddatetime.js" type="text/javascript" ></script>
    <script src="../../../../plug/jQuery/LodopFuncs.js" type="text/javascript"></script>
    <title>订单管理</title>
</head>
<body>
    <section>
    	<form action="/oem/custommade/orderlist.html" method="post" >
    			订单号：<input type="text" name="orderNumber" />
    			<input type="hidden" name="status" value="${ status }" />	 
            	<input type="hidden" name="pageNo" id="pageNo" value="" />	           
	            <input type="submit" class="search" value="搜索" />
        </form>
        <ul class="partlist">
            <li>
          		<a href="/oem/custommade/orderlist.html?status=24"><span class="switch ${status=='24'?'switched':'' }">待处理</span></a>
              	<div class="inforbox selectinforbox">
              			<table class="ordermanagetable refundlist">
					   	<thead>
					   		<tr>
								<td style="min-width:70px;" >订单号</td>
								<td style="min-width:70px;">产品名称</td>
								<td style="min-width:70px;">规格</td>
								<td style="min-width:60px;">数量</td>
								<td style="min-width:160px;">下单日期</td>
								<td style="max-width:200px;">备注</td>
								<td style="min-width:100px;">订单状态</td>
								<td style="min-width:160px;">操作</td>
							</tr>
					   	</thead>
					   	<tbody>
					   		<c:forEach items="${ orderList }" var="order">
		   						<tr>
		       						<td><img alt="" src="../../images/custom.png"> ${order.orderNumber }</td>
		       						<td>${order.orderItems[0].productTitle }</td>
		       						<td>${order.orderItems[0].productModelFormatName }</td>
		       						<td>x${order.orderItems[0].num }</td>
		       						<td><fm:formatDate value="${order.createDateTime }" pattern="YYYY/MM/dd HH:mm"/> </td>
									<td style="max-width:200px;min-width:100px;">${order.remark }</td>
		       						<td>				       							
		       							<c:choose>
											<c:when test="${order.status==24 }">面板打印完</c:when>
											<c:when test="${order.status==26 }">定制中</c:when>
											<c:when test="${order.status==28 }">已入库</c:when>
											<c:when test="${order.status==30 }">配货中</c:when>
											<c:when test="${order.status==40 }">已发货</c:when>
											<c:when test="${order.status==50 }">已完成</c:when>
											<c:when test="${order.status==60 }">订单关闭</c:when>
											<c:otherwise>未知</c:otherwise>
										</c:choose>
		       						</td>
		       						<td>							       							
		       							<a href="/oem/custommade/scanqrcode_custommade.html?orderNumber=${order.orderNumber }" class="link">打印唯一码</a>
		       						</td>
		       					</tr>
					   	</c:forEach>
					   </tbody>    
					</table> 
              		<jsp:include page="../../include/pagesService.jsp"></jsp:include>
              	</div>
            </li>
             <li>
          		<a href="/oem/custommade/orderlist.html?status=26-28-30-40-50-60-70"><span class="switch ${status!='24'?'switched':'' }">已处理</span></a>
              	<div class="inforbox selectinforbox">
              			<table class="ordermanagetable refundlist">
					   	<thead>
					   		<tr>
								<td style="min-width:70px;" >订单号</td>
								<td style="min-width:70px;">产品名称</td>
								<td style="min-width:70px;">规格</td>
								<td style="min-width:60px;">数量</td>
								<td style="min-width:160px;">下单日期</td>
								<td style="max-width:200px;">备注</td>
								<td style="min-width:100px;">订单状态</td>
								<td style="min-width:160px;">操作</td>
							</tr>
					   	</thead>
					   	<tbody>
					   		<c:forEach items="${ orderList }" var="order">
		   						<tr>
		       						<td><img alt="" src="../../images/custom.png"> ${order.orderNumber }</td>
		       						<td>${order.orderItems[0].productTitle }</td>
		       						<td>${order.orderItems[0].productModelFormatName }</td>
		       						<td>x${order.orderItems[0].num }</td>
		       						<td><fm:formatDate value="${order.createDateTime }" pattern="YYYY/MM/dd HH:mm"/> </td>
									<td style="max-width:200px;min-width:100px;">${order.remark }</td>
		       						<td>				       							
		       							<c:choose>
											<c:when test="${order.status==24 }">面板打印中</c:when>
											<c:when test="${order.status==26 }">定制中</c:when>
											<c:when test="${order.status==28 }">已入库</c:when>
											<c:when test="${order.status==30 }">配货中</c:when>
											<c:when test="${order.status==40 }">已发货</c:when>
											<c:when test="${order.status==50 }">已完成</c:when>
											<c:when test="${order.status==60 }">订单关闭</c:when>
											<c:otherwise>未知</c:otherwise>
										</c:choose>
		       						</td>
		       						<td>							       							
		       							<a href="/oem/custommade/scanqrcode_custommade.html?retry=1&orderNumber=${order.orderNumber }" class="link">重新打印唯一码</a>
		       						</td>
		       					</tr>
					   	</c:forEach>
					   </tbody>    
					</table> 
              		<jsp:include page="../../include/pagesService.jsp"></jsp:include>
              	</div>
            </li>       
        </ul>
    </section>
</div>

<div class="pop pop6" style="display:none">
	<div class="popbg"></div>
	<div class="layel">
		<div class="right-part" id="page1" style="position:absolute;left=0;top:0;z-index:9999;background:#fff;">
			<div class="print-bigbox">
				<div class="print-box">
					<p class="print-header" style="height:55px;">
						<img src="../../images/logo.png" class="print-logo">
						<span class="print-title">熊爸爸，为温暖而生！</span>
					</p>
					<div class="print-product" style="height:80px;overflow:hidden;width:100%;">
						<table class="prod-table">
					    <thead>
					    	<tr>
					    		<td width="120px">产品名称</td>
					    		<td width="120px">产品规格</td>
					    		<td width="60px">产品数量</td>
					    	</tr>	
					    </thead>
					    <tbody>
					    </tbody>
					    </table>
					</div>						
						<div class="print-footer">
							<p class="company-name">杭州白熊科技有限公司</p>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>

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
    <link href="../../../css/order-detail.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/aftersalemanage.js" type="text/javascript" ></script>
    <title>售后管理</title>
    <script type="text/javascript">
    	function toList(type){
    		location.href = "/aftetSale/queryList.html?queryType="+type;
    	}
    </script>
</head>
<body>
    <section>
        <ul >
            <li>
                <div class="inforbox selectinforbox">
                    <div class="row">
                        <span class="light"><a href="#" onclick="toList(${queryType})">退货单/换货单</a>></span>详情
                    </div>
			        <div class="content">
			        	<span>订单号：</span>
			        	<span class="text">${order.orderNumber}</span>
			        	<span>客户：</span>
			        	<span class="text">${order.information.reveiveUserName }</span>
			        	<span>联系电话：</span>
			        	<span class="text">${order.information.mobilePhone }</span>
			        	<span>配送地址：</span>
			        	<span class="text">${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }</span>
			        </div>
			        <div class="route">
			        	<c:if test="${queryType == '1' }">
			        		<ul>
				        		<li class="orange">
									<span>提交申请</span>
									<span class="time"><fmt:formatDate value="${status10.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
								</li>
				        		<li class="${status20==null?'':'orange' }">
									<span>白熊签收</span>
									<c:if test="${status20!=null }"><span><fmt:formatDate value="${status20.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status30==null?'':'orange' }">
									<span>关闭</span>
									<c:if test="${status30!=null }"><span><fmt:formatDate value="${status30.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status41==null?'':'orange' }">
									<span>同意</span>
									<c:if test="${status41!=null }"><span><fmt:formatDate value="${status41.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status42==null?'':'orange' }">
									<span>拒绝</span>
									<c:if test="${status42!=null }"><span><fmt:formatDate value="${status42.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status50==null?'':'orange' }">
									<span>等待退款</span>
									<c:if test="${status50!=null }"><span><fmt:formatDate value="${status50.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status60==null?'':'orange' }">
									<span>已退款</span>
									<c:if test="${status50!=null }"><span><fmt:formatDate value="${status60.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
			        		</ul>
			        	</c:if>
			        	<c:if test="${queryType == '2' }">
			        		<ul>
				        		<li class="orange">
									<span>提交申请</span>
									<span class="time"><fmt:formatDate value="${status10.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
								</li>
				        		<li class="${status20==null?'':'orange' }">
									<span>白熊签收</span>
									<c:if test="${status20!=null }"><span><fmt:formatDate value="${status20.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status31==null?'':'orange' }">
									<span>配货中</span>
									<c:if test="${status31!=null }"><span><fmt:formatDate value="${status31.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status32==null?'':'orange' }">
									<span>拒绝</span>
									<c:if test="${status32!=null }"><span><fmt:formatDate value="${status32.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
								<li class="${status50==null?'':'orange' }">
									<span>已发货</span>
									<c:if test="${status50!=null }"><span><fmt:formatDate value="${status50.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></c:if>
								</li>
			        		</ul>
			        	</c:if>
			        </div>
			        <table>
			        <thead>
			        	<tr>
			        		<td>序号</td>
			        		<td>名称</td>
			        		<td>规格</td>
			        		<td>数量</td>
			        	</tr>
			        </thead>
			        <tbody>
			        	<c:forEach items="${listItem}" var="item" varStatus="statusItem">
				        	<tr>
				        		<td>${statusItem.index+1}</td>
				        		<td>${item.productTitle }</td>
				        		<td>${item.productModelFormatName }</td>
				        		<td>${item.num }</td>
				        	</tr>
			        	</c:forEach>
			        </tbody>
			        </table>
			        <c:if test="${status42!=null }">
			        <table>
			        	<tr>
			        		<td width="100">拒绝理由:</td>
			        		<td class="tl" style="text-indent:20px;">${order.refuseMemo }</td>
			        	</tr>
			        </table>
			        </c:if>
			        <c:if test="${status32!=null }">
			        <table>
			        	<tr>
			        		<td>拒绝理由</td>
			        		<td>${order.refuseMemo }</td>
			        	</tr>
			        </table>
			        </c:if>
                </div>
            </li>
        </ul>
    </section>
</body>
</html>
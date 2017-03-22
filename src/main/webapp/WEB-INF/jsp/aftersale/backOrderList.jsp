<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/aftersalemanage.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/aftersalemanage.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>售后管理</title>
    <script type="text/javascript">
    	function toTh(){
    		location.href = "/aftetSale/queryList.html?queryType=1";
    	}
    	function toHh(){
    		location.href = "/aftetSale/queryList.html?queryType=2";
    	}
    	function toWx(){
    		location.href = "/aftetSale/queryList.html?queryType=3";
    	}
    	function sj(id){
    		$('.sjWindow').show();
			$("#serviceId").val(id);
    	}
    	function sjSub(){
    		var serviceId = $("#serviceId").val();
    		var url = "/aftetSale/operateOrder.html?serviceId="+serviceId+"&status="+20;
    		sub(url);
    	}
    	function closeW(id){
    		$('.closeWindow').show();
			$("#serviceId").val(id);
    	}
    	function closeSub(){
    		var serviceId = $("#serviceId").val();
    		var url = "/aftetSale/operateOrder.html?serviceId="+serviceId+"&status="+30;
    		sub(url);
    	}
    	function agree(id){
    		$('.agreeWindow').show();
			$("#serviceId").val(id);
    	}
    	function agreeSub(){
    		var serviceId = $("#serviceId").val();
    		var url = "/aftetSale/operateOrder.html?serviceId="+serviceId+"&status="+41;
    		sub(url);
    	}
    	function refuse(id){
    		$('.refuseWindow').show();
			$("#serviceId").val(id);
    	}
    	function refuseSub(){
    		var serviceId = $("#serviceId").val();
    		var refuseMemo = $("#refuseMemo").val();
    		if( refuseMemo == null || refuseMemo  ==undefined || refuseMemo  == ''){
    			alertLayel("请输入拒绝理由");
        		return false;
        	}else{
        		var url = "/aftetSale/operateOrder.html?serviceId="+serviceId+"&status="+42+"&refuseMemo="+refuseMemo;
    			sub(url);
        	}
    	}
    	function sub(url){
    		$.ajax({
		 		   url: url,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  if ( !checkCode( obj ) ) {
						return;
		 			  }else{
		 				window.location.reload();//重载页面
		 			  }
		 		   }
	   		});
    	}
    </script>
</head>
<body>
<input type="hidden" id="serviceId" >
    <section>
        <ul class="partlist">
            <li>
                <span class="switch switched" onclick="toTh()">退货单</span>
                <div class="inforbox selectinforbox">
                	<form action="/aftetSale/queryList.html" method="post">
                    <div class="row">
                        <input type="text" class="personname" name="queryStr" value="${queryStr }" placeholder="订单号">
                        <input type="hidden" name="pageNo" id="pageNo" value="">
                        <input type="hidden" name="queryType" value="1">
                        <input type="submit" class="searchbtn search" value="搜索">
                    </div>
                    </form>
                    <div class="row">
                        <table class="refundlist">
                            <thead>
                            <tr>
                                <td>订单号</td>
                                <td>用户名</td>
                                <td>白熊号</td>
                                <td>时间</td>
                                <td>产品名称</td>
                                <td>规格</td>
                                <td>数量</td>
                                <td>状态</td>
                                <td>退货原因</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${dataList }" var="order">
						   			<c:forEach  items="${order.itemList }" var="item" varStatus="itemStatus">
						   				<c:choose>
						   					<c:when test="${fn:length(order.itemList)==1 }">
						   						<tr>
						       						<td>${order.orderNumber }</td>
					                                <td>${order.userName }</td>
					                                <td>${order.bxNum }</td>
					                                <td><fm:formatDate value="${order.createDateTime}" pattern="yyyy/MM/dd"/></td>
					                                <td>${item.productTitle }</td>
					                                <td>${item.productModelFormatName }</td>
					                                <td>${item.num }</td>
					                                <td>
					                                <c:if test="${order.status == 10 }">
					                                	未收件
					                                </c:if>
					                                <c:if test="${order.status == 20 }">
					                                	已签收
					                                </c:if>
					                                <c:if test="${order.status == 30 }">
					                                	关闭
					                                </c:if>
					                                <c:if test="${order.status == 41 }">
					                                	同意
					                                </c:if>
					                                <c:if test="${order.status == 42 }">
					                                	拒绝
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	已退款
					                                </c:if>
					                                <%-- <c:if test="${order.status == 60 }">
					                                	已退款
					                                </c:if> --%>
					                                </td>
					                                <td><p class="reasontd" title="${order.description }">${order.description }</p></td>
					                                <td>
					                                <c:if test="${order.status == 10 }">
					                                	<a href="#" class="link" onclick="sj(${order.id})">已收件</a>
					                                	<a href="#" class="link" onclick="closeW(${order.id})">关闭</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 20 }">
					                                	<a href="#" class="link" onclick="agree(${order.id})">同意</a>
					                                	<a href="#" class="link" onclick="refuse(${order.id})">拒绝</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 30 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 41 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 42 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <%-- <c:if test="${order.status == 60 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if> --%>
					                                </td>
						       					</tr>
						   					</c:when>
						   				<c:otherwise>
						   					<c:if test="${itemStatus.first }">
						   						<tr>
						        					<td rowspan="${fn:length(order.itemList) }">${order.orderNumber }</td>
					                                <td rowspan="${fn:length(order.itemList) }">${order.userName }</td>
					                                <td rowspan="${fn:length(order.itemList) }">${order.bxNum }</td>
					                                <td rowspan="${fn:length(order.itemList) }"><fm:formatDate value="${order.createDateTime}" pattern="yyyy/MM/dd"/></td>
					                                <td>${item.productTitle }</td>
					                                <td>${item.productModelFormatName }</td>
					                                <td>${item.num }</td>
					                                <td rowspan="${fn:length(order.itemList) }">
					                                <c:if test="${order.status == 10 }">
					                                	未收件
					                                </c:if>
					                                <c:if test="${order.status == 20 }">
					                                	已签收
					                                </c:if>
					                                <c:if test="${order.status == 30 }">
					                                	关闭
					                                </c:if>
					                                <c:if test="${order.status == 41 }">
					                                	同意
					                                </c:if>
					                                <c:if test="${order.status == 42 }">
					                                	拒绝
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	已退款
					                                </c:if>
					                                <%-- <c:if test="${order.status == 60 }">
					                                	已退款
					                                </c:if> --%>
					                                </td>
					                                <td rowspan="${fn:length(order.itemList) }"><p class="reasontd" title="${order.description }">${order.description }</p></td>
					                                <td rowspan="${fn:length(order.itemList) }">
					                                <c:if test="${order.status == 10 }">
					                                	<a href="#" class="link" onclick="sj(${order.id})">已收件</a>
					                                	<a href="#" class="link" onclick="closeW(${order.id})">关闭</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 20 }">
					                                	<a href="#" class="link" onclick="agree(${order.id})">同意</a>
					                                	<a href="#" class="link" onclick="refuse(${order.id})">拒绝</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 30 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 41 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 42 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if>
					                                <%-- <c:if test="${order.status == 60 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=1" class="link">详情</a>
					                                </c:if> --%>
					                                </td>
							        			</tr>
						   					</c:if>
						   					<c:if test="${itemStatus.index>0 }">
						   						<tr>
							        				<td>${item.productTitle }</td>
					                                <td>${item.productModelFormatName }</td>
					                                <td>${item.num }</td>
							        			</tr>
						   					</c:if>
						   				</c:otherwise>
						   			</c:choose>
						   		</c:forEach>
						   	</c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <jsp:include page="../include/pagesService.jsp"></jsp:include>
                </div>
            </li>
            <li>
                <span class="switch" onclick="toHh()">换货单</span>
            </li><!--
            <li>
                <span class="switch" onclick="toWx()">维修单</span>
            </li>-->
        </ul>
    </section>
<!--收件弹窗-->
<!-- 收件 -->
<div class="pop sjWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">已收件<i class="closeicon"></i> </h3>
        <div class="row">
            <p class="warningtext">确认收件后用户会收到收件记录，是否确认</p>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="sjSub()" class="confirm">
        </div>
    </div>
</div>
<!-- 关闭 -->
<div class="pop closeWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">关闭<i class="closeicon"></i> </h3>
        <div class="row">
            <p class="warningtext">确认关闭后用户会收到关闭记录，是否确认</p>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="closeSub()" class="confirm">
        </div>
    </div>
</div>
<!-- 同意 -->
<div class="pop agreeWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">同意<i class="closeicon"></i> </h3>
        <div class="row">
            <p class="warningtext">确认后用户会收到同意记录，是否确认</p>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="agreeSub()" class="confirm">
        </div>
    </div>
</div>
<!-- 拒绝 -->
<div class="pop refuseWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">拒绝<i class="closeicon"></i> </h3>
        <div class="row">
            <textarea class="refusetext" id="refuseMemo"></textarea>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="refuseSub()" class="confirm">
        </div>
    </div>
</div>
</body>
</html>
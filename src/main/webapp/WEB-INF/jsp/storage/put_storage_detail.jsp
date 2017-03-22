<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="/plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="/css/stroage-operation.css" rel="stylesheet" type="text/css">
    <script src="/plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="/js/public.js" type="text/javascript" ></script>
    <script type="text/javascript" charset="utf-8" src="/plug/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/plug/ueditor/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="/plug/ueditor/lang/zh-cn/zh-cn.js"></script>
    <title>入库</title>
</head>
<body>

    <section>
    	<div class="pathbox">
            <span class="light">设备列表</span>>
            <span class="darker">操作详情</span>
        </div>
    	<div class="product">
            <p class="product-number">产品数量：<span class="orangetext">${bean.count }台</span></p>
            <p class="transport-code">运 输 码：${bean.transportcode }</p>
            <p class="product-name">产品名称：${bean.productName }</p>
            <p class="Product-specifications">产品规格：${bean.productFormat }</p>
        </div>
        <table>
                	<thead>
                		<tr>
                			<td>序列号</td>
                			<td>唯一码</td>
                			<td>生产日期</td>
                			<td>操作</td>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${list }" var="item" varStatus="statusItem">
                			<tr>
	                			<td>${statusItem.count }</td>
	                			<td>${item.uniqueCode }</td>
	                			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY-MM-dd HH:mm"/></td>
	                			<td><a href="/ledger/trace.html?hardwareId=${item.hardwareProductId }" class="link">物联网详情</a></td>
	                		</tr>
                		</c:forEach>
                	</tbody>
                	</table>
	</section>
</body>
</html>
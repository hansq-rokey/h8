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
    <link href="../../../css/scantransport-transport.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script type="text/javascript" charset="utf-8" src="../../../plug/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../../../plug/ueditor/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="../../../plug/ueditor/lang/zh-cn/zh-cn.js"></script>
    <title>扫描运输-运输详情</title>
</head>
<body>
    <section>
        <span class="light"><a href="/oem/transport/queryList.html">扫描运输</a></span>>
        <span class="darker">运输详情</span>
        <div class="content">
        	<span>运输码：</span>
        	<span class="text">${transportCode.code }</span>
        	<span>产品名称：</span>
        	<span class="text">${transportCode.format.basicCategoryModel.name }</span>
        	<span>产品规格：</span>
        	<span class="text">${transportCode.format.name }</span>
        	<span>产品数量：</span>
        	<span class="text">${transportCode.countSum }</span>
        </div>
        <table>
        	<thead>
        		<tr>
        			<td width="153">序号</td>
        			<td width="350">包装码</td>
        			<td class="san">生产日期</td>
        		</tr>
        	</thead>
        	<tbody>
        		<c:forEach  items="${list }" var="item" varStatus="itemStatus">
        		<tr>
        			<td>${itemStatus.index+1 }</td>
        			<td>${item.erpHardwareProduct.uniqueCode }</td>
        			<td><fmt:formatDate value="${item.erpHardwareProduct.createDateTime }" pattern="YYYY/MM/dd" /></td>
        		</tr>
        		</c:forEach>
        	</tbody>
        </table>
	</section>
</body>
</html>
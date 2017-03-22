<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="/plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="/css/storage-record.css" rel="stylesheet" type="text/css">
    <script src="/plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="/js/public.js" type="text/javascript" ></script>
	<script src="/plug/adddatetime.js" type="text/javascript" ></script>
    <title>入库</title>
    <script type="text/javascript">
    	function toSearch(type){
    		$("#checkType").val(type);
    		$(".search").click();
    	}
    </script>
</head>
<body>
    <section>
        <div class="content">
        	<form action="/storage/put/record.html" method="post">
        		<input type="hidden" name="pageNo" id="pageNo" value="">
        		<input type="hidden" name="checkType" id="checkType" value="${checkType }">
	            <input type="text" class="packing-code" name="keywords" value="${keywords }" placeholder="${checkType=='2'?'唯一码':'运输码' }">
	            <input type="text" class="strat-time datetimepicker startdata" name="startTime" value="${startTime }" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <span>至</span>
	            <input type="text" class="end time datetimepicker startdata" name="endTime" value="${endTime }" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <input type="submit" class="search" value="搜索">
        	</form>
        </div>
         <ul class="partlist">
         	<c:if test="${checkType == '1' }">
            <li>
                <span class="switch switched" onclick="toSearch(1)">入库记录</span>
		        <div class="inforbox selectinforbox">
		            <table class="storagetable">
		        	<thead>
		        		<tr>
		        			<td>运输码</td>
		        			<td>产品名称</td>
		        			<td>产品批次</td>
		        			<td>产品规格</td>
		        			<td>厂商名称</td>
		        			<td>数量</td>
		        			<td>入库时间</td>
		        			<td>操作</td>
		        		</tr>
		        	</thead>
		        	<tbody>
		        		<c:forEach items="${putStorageList }" var="item">
			        		<tr>
			        			<td>${item.transportcode }</td>
			        			<td>${item.productName }</td>
			        			<td>${item.batch }</td>
			        			<td>${item.productFormat }</td>
			        			<td>${item.oemName }</td>
			        			<td>${item.count }</td>
			        			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY-MM-dd"/></td>
			        			<td><a href="/ledger/storage/put/detail.html?id=${item.id }" class="link">详情</a></td>
			        		</tr>
		        		</c:forEach>
		        	</tbody>
		        	</table>   
		        	<jsp:include page="../include/pages.jsp"></jsp:include> 
		        </div>
            </li>
             <li>
                <span class="switch" onclick="toSearch(2)">异常记录</span>
                <div class="inforbox">
                </div>
             </li>
            </c:if>
            <c:if test="${checkType == '2' }">
            <li>
                <span class="switch" onclick="toSearch(1)">入库记录</span>
		        <div class="inforbox">
		        </div>
		    </li>
            <li>
                <span class="switch switched" onclick="toSearch(2)">异常记录</span>
                <div class="inforbox selectinforbox">
                	<table  class="storagetable">
                	<thead>
                		<tr>
                			<td>运输码</td>
                			<td>唯一码</td>
                			<td>产品名称</td>
                			<td>产品规格</td>
                			<td>厂商名称</td>
                			<td>产品批次</td>
                			<td>异常类型</td>
                			<td>异常原因</td>
                			<td>标记时间</td>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${exceptionList }" var="item">
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
                </div>
            </li>
            </c:if>
        </ul>  
	</section>
</body>
</html>
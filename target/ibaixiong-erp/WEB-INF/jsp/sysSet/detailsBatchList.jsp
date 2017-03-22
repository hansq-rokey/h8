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
    <link href="../../../css/productmanage-detail.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <title>产品批次管理-产品详情</title>
</head>
<body>
<div style="display: none;">
	<form action="/sysSet/batch/detailsList.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	 	<input type="hidden" name="batchId" id="batchId" value="${batchId }">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
        <span class="light" style="margin-top: 10px;display:inline-block"><a href="/sysSet/batch/list.html">产品批次管理</a></span>>
        <span class="darker">产品详情</span>
        <div class="content">
        	<span class="product">产品名称：${batch.category.name }</span>
        	<span class="product">产品规格：${batch.categoryModel.name }</span>
        	<span class="product">产品编码：${batch.categoryModelFormat.name }</span>
        </div>
        <table>
        	<thead>
        		<tr>
        			<td>序列号</td>
        			<td>批次号</td>
        			<td>创建时间</td>
        			<td>订单号</td>
        			<td>批次描述</td>
        			<td>操作</td>
        		</tr>
        	</thead>
        	<tbody>
        	<c:forEach items="${list}" var="data" varStatus="st">
        		<tr>
        			<td>${st.index+1 }</td>
        			<td>${data.order.batch}</td>
        			<td><fmt:formatDate value="${data.createDateTime}" pattern="yyyy/MM/dd"/></td>
        			<td>${data.order.orderNumber}</td>
        			<td>${data.order.description}</td>
        			<td>
	        			<c:if test="${data.invalid == true }">
	        				<a href="#" class="link" onclick="toAble(${data.id})">解除禁用</a>
	                	</c:if>
	                	<c:if test="${data.invalid == false }">
                        	<a href="#" class="link disable" onclick="toDisable(${data.id})">禁用</a>
                       	</c:if>
        			</td>
        		</tr>
        	</c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
<form action="/sysSet/batch/updateInvalid.html" method="post">
<input type="hidden" name="detailsId" value="" id="detailsId">
<input type="hidden" name="invalid" value="1" id="invalid">
<input type="hidden" name="batchId" value="${batch.id }" id="batchId">
<div class="pop delreasonpop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel" style="height:260px;">
        <h3 class="poptitle">禁用<i class="closeicon"></i> </h3>
        <p class="text">禁用后相关员工将无法进行扫码入库等工作，是否禁用？</p>
        <div class="row tc">
            <input type="submit" value="禁用" class="disablebtn" style="margin-top: 40px;">
        </div>
    </div>
</div>
</form>
<form action="/sysSet/batch/updateInvalid.html" method="post">
<input type="hidden" name="detailsId" value="" id="detailsId1">
<input type="hidden" name="invalid" value="0" id="invalid">
<input type="hidden" name="batchId" value="${batch.id }" id="batchId">
<div class="pop2 delreasonpop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel" style="height:260px;">
        <h3 class="poptitle">解除禁用<i class="closeicon"></i> </h3>
        <p class="text">激活后相关员工将拥有进行扫码包装等权限，是否激活？</p>
        <div class="row tc">
            <input type="submit" value="解禁" class="ablebtn" style="margin-top: 40px;">
        </div>
    </div>
</div>
</form>
<script type="text/javascript">
function toDisable(id){
	$("#detailsId").val(id);
	$(".pop").show();
}
function toAble(id){
	$("#detailsId1").val(id);
	$(".pop2").show();
}
</script>
</body>
</html>
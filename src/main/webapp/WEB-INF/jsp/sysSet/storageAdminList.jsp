<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/warehouse-stafflist.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>仓库管理</title>
    <script type="text/javascript">
    	function toDisable(id){
    		$("#adminId").val(id);
    		$('.pop1').show();
    	}
    	function toAble(id){
    		$("#adminId").val(id);
    		$('.pop2').show();
    	}
    	function disable(id){
    		upAdmin(-2);
    	}
    	function able(id){
    		upAdmin(1);
    	}
    	function upAdmin(status){
    		var adminId = $("#adminId").val();
    		$.ajax({
 			   url: "/sysSet/admin/updateStatus.html?adminId="+adminId+"&status="+status,
 			   type: "post",
 			   dataType:"json",
 			   cache:false,
 			   success: function(obj){
 		  			if ( !checkCode( obj ) ) {
 						return;
 				    }
 				 	if ( obj.code == 0 ) {
 				 		window.location.reload();
 					}
 			   }
 			});
    	}
    </script>
</head>
<body>
<div style="display: none;">
	<form action="/sysSet/storage/adminList.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	 	<input type="hidden" name="storageId" id="storageId" value="${storageId }">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
<input type="hidden" id="adminId">
    <section>
        <span class="light" style="margin-top: 10px;display:inline-block"><a href="/sysSet/storage/list.html">仓库管理</a></span>>
        <span class="darker">员工列表</span>
        <table>
        	<thead>
        		<tr>
        			<td>序号</td>
        			<td>姓名</td>
        			<td>用户名</td>
        			<td>部门</td>
        			<td>联系方式</td>
        			<td>操作</td>
        		</tr>
        	</thead>
        	<tbody>
        		<c:forEach items="${list}" var="data" varStatus="st">
        		<tr>
        			<td>${st.index+1 }</td>
        			<td>${data.admin.userName }</td>
        			<td>${data.admin.loginName }</td>
        			<td>${data.admin.org.name }</td>
        			<td>${data.admin.phone }</td>
        			<td>
        				<c:if test="${data.admin.status == 1 }">
        				<a href="#" onclick="toDisable(${data.admin.id })" class="link">禁用</a>
        				</c:if>
        				<c:if test="${data.admin.status == -2 }">
        				<a href="#" onclick="toAble(${data.admin.id })" class="link">解除禁用</a>
        				</c:if>
                    </td>
        		</tr>
        		</c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
<div class="pop delreasonpop pop1" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">禁用账号<i class="closeicon"></i> </h3>
        <p class="text">禁用后相关员工将无法进行扫码入库等工作，是否禁用？</p>
        <div class="row tc">
            <input type="button" onclick="disable()" value="禁用" class="disablebtn">
        </div>
    </div>
</div>
<div class="pop delreasonpop pop2" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">解除禁用<i class="closeicon"></i> </h3>
        <p class="text">激活后相关员工将拥有进行扫码包装等权限，是否激活？</p>
        <div class="row tc">
            <input type="button" onclick="able()"  value="解禁" class="ablebtn">
        </div>
    </div>
</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/persona-information.css" rel="stylesheet" type="text/css">
    <link href="../../../css/warehouse-manage.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>仓库管理</title>
    <script type="text/javascript">
    	function check(){
    		var storageName = $("#storageName").val();
    		if(storageName == null || storageName ==undefined || storageName == ''){
    			alertLayel("仓库名称不可为空");
				return false;
			} 
    		var storageCode = $("#storageCode").val();
    		if(storageCode == null || storageCode ==undefined || storageCode == ''){
    			alertLayel("仓库代码不可为空");
				return false;
			} 
    		var storageAddress = $("#storageAddress").val();
    		if(storageAddress == null || storageAddress ==undefined || storageAddress == ''){
    			alertLayel("仓库地址不可为空");
				return false;
			} 
    		return true;
    	}
    	function showWin(){
    		$(".storageDiv").show();
    	}
    </script>
</head>
<body>
<div style="display: none;">
	<form action="/sysSet/storage/list.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
    	<input type="button" value="新增仓库" onclick="showWin()" class="searchbtn" style="margin-top: 20px;margin-left: 0px;">
        <table class="handleloglist">
            <thead>
            <tr>
                <td width="331px">仓库名称</td>
                <td width="222">仓库代码</td>
                <td width="439">仓库位置</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="data" varStatus="st">
            <tr>
                <td class="serial">${data.storageName }</td>
                <td class="serial">${data.storageCode }</td>
                <td class="serial">${data.storageAddress }</td>
                <td class="serial">
                <a href="/sysSet/storage/shelfList.html?storageId=${data.id }" class="link">货架管理</a>
                <a href="/sysSet/storage/adminList.html?storageId=${data.id }" class="link">员工列表</a>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
<form action="/sysSet/storage/save.html" method="post" onsubmit="return check()">
<div class="pop storageDiv">
    <div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">添加仓库<i class="closeicon"></i> </h3>
        <div class="row tc" style="margin-left: 0px;">
            <span class="infortype tr" style="width: 100px;">仓库名称：</span>
            <input type="text" class="oldpass" name="storageName" id="storageName">
        </div>
        <div class="row tc" style="margin-left: 0px;">
            <span class="infortype tr" style="width: 100px;">仓库代码：</span>
            <input type="text" class="newpass" name="storageCode" id="storageCode" maxlength="5">
        </div>
        <div class="row tc" style="margin-left: 0px;">
            <span class="infortype tr" style="width: 100px;">仓库位置：</span>
            <input type="text" class="connewpass" name="storageAddress" id="storageAddress">
        </div>
        <div class="row tc" style="margin-left: 0px;">
            <input type="submit" value="确认" class="confirm">
        </div>
    </div>
</div>
</form>
</body>
</html>
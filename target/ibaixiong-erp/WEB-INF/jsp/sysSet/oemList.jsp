<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/OEMmanage.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>OEM管理</title>
    <script type="text/javascript">
    	function check(){
    		var name = $("#name").val();
    		if(name == null || name ==undefined || name == ''){
    			alertLayel("名称不可为空");
				return false;
			} 
    		var oemCode = $("#oemCode").val();
    		if(oemCode == null || oemCode ==undefined || oemCode == ''){
    			alertLayel("代码不可为空");
				return false;
			} 
    		var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
    	    if(!reg.test(oemCode)){
    	    	alertLayel("只能输入数字");
				return false;
    	    }
    		var address = $("#address").val();
    		if(address == null || address ==undefined || address == ''){
    			alertLayel("地址不可为空");
				return false;
			} 
    		return true;
    	}
    	$(document).ready(function(){
    		$(document).on('click','.addbtn',function(){
    			$('.pop').show();
    		})
    	})
    </script>
</head>
<body>
<div style="display: none;">
	<form action="/sysSet/oem/list.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
        <div class="content">
            <input type="button" class="addbtn" value="新增" style="margin-top: 20px;"/>
        </div>
        <table>
        	<thead>
        		<tr>
        			<td>名称</td>
        			<td>代码</td>
        			<td>地址</td>
        			<td>操作</td>
        		</tr>
        	</thead>
        	<tbody>
        		<c:forEach items="${list}" var="data" varStatus="st">
	            <tr>
	                <td class="serial">${data.name }</td>
	                <td class="serial">${data.oemCode }</td>
	                <td class="serial">${data.address }</td>
	                <td class="serial">
	                <a href="/sysSet/oem/adminList.html?oemId=${data.id }" class="link">员工列表</a>
	                </td>
	            </tr>
	            </c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
<form action="/sysSet/oem/save.html" method="post" onsubmit="return check()">
<div class="pop delreasonpop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">新增OEM厂<i class="closeicon"></i> </h3>
        <div class="bigbox">
            <span class="bt">OEM厂名称：</span><input type="text" class="text" name="name" id="name" maxlength="10"/>
        </div>
        <div class="bigbox">
            <span class="bt">OEM厂代码：</span><input type="text" class="text" name="oemCode" id="oemCode" maxlength="2"/>
        </div>
        <div class="bigbox">
            <span class="bt">OEM厂地址：</span><input type="text" class="text" name="address" id="address" maxlength="200"/>
        </div>
        <div class="row tc">
            <input type="submit" value="保存" class="save">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
</form>
</body>
</html>
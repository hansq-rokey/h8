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
    <link href="../../../css/purchase-material.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../plug/adddatetime.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>物料</title>
    <script type="text/javascript">
	    function toAdd(){
	 		clean();
	 		$('.pop1').show();
	 		$('#winTitle').text("新增物料单");
	 	}
	    function toUpdate(id){
	 		clean();
	 		$('.pop1').show();
	 		$('#winTitle').text("修改物料单");
	 		$.ajax({
		 		   url: "/material/getMaterial.html?id="+id,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  	if ( !checkCode( obj ) ) {
		 					return;
		 			  	}
		 			  	var data = obj.result;
			     		$('#materialId').val(data.id); 
			     		$('#serialNumber').val(data.serialNumber);
			     		$('#name').val(data.name);
			     		$('#model').val(data.model);
			     		$('#num').val(data.num);
			     		$('#unit').val(data.unit);
			     		$('#price').val(data.price);
			     		$('#coefficient').val(data.coefficient);
 			     		$("#calculateModel").find("option[value='"+data.calculateModel+"']").attr("selected",true);
			     		$('#purchaseDateStr').val(data.purchaseDate);
			     		$('#arrivalDateStr').val(data.arrivalDate);
			     		$('#purchasePrice').val(data.purchasePrice);
			     		$('#factoryPrice').val(data.factoryPrice);
			     		$('#remark').val(data.remark);
		 		  }
	   		});
	 	}
	    function clean(){
     		$('#materialId').val(''); 
     		$('#serialNumber').val('');
     		$('#name').val('');
     		$('#model').val('');
     		$('#num').val('');
     		$('#purchaseDateStr').val('');
     		$('#arrivalDateStr').val('');
     		$('#remark').val('');
     		$('#unit').val('');
     		$('#price').val('');
     		$('#coefficient').val('');
     		$('#purchasePrice').val('');
     		$('#factoryPrice').val('');
     		$('#purchasePrice').val('');
     		$('#factoryPrice').val('');
	     	$("#calculateModel").find("option[value='1']").attr("selected",true);
     	}
	    function check(){
     		var serialNumber = $('#serialNumber').val(); 
        	if( serialNumber == null || serialNumber  ==undefined || serialNumber  == ''){
        		alertLayel("编号不可为空!");
        		return false;
        	}
        	var name = $('#name').val(); 
        	if( name == null || name  ==undefined || name  == ''){
        		alertLayel("产品名称不可为空!");
        		return false;
        	}
        	var model = $('#model').val(); 
        	if( model == null || model  ==undefined || model  == ''){
        		alertLayel("型号不可为空!");
        		return false;
        	}
        	var num = $('#num').val(); 
        	if( num == null || num  ==undefined || num  == ''){
        		alertLayel("数量不可为空!");
        		return false;
        	}
        	var purchaseDateStr = $('#purchaseDateStr').val();
        	var arrivalDateStr = $('#arrivalDateStr').val(); 
        	if(purchaseDateStr != null && purchaseDateStr != '' && arrivalDateStr!= null && arrivalDateStr != ''){
        		 var start = new Date(purchaseDateStr.replace("-", "/").replace("-", "/"));
                 var end = new Date(arrivalDateStr.replace("-", "/").replace("-", "/"));
                 if (end < start) {
                	 alertLayel('到库时间不能小于采购时间！');
                     return false;
                 }
        	}
        	return true;
	    }
	    function toDel(id){
	    	$('.pop2').show();
	    	$('#delmaterialId').val(id); 
	    }
	</script>
</head>
<body>
<div style="display: none;">
	<form action="/material/list.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
    	<input type="button" value="添加物料" onclick="toAdd()" class="searchbtn" style="margin-top: 15px;margin-left: 0px;">
        <table class="handleloglist">
            <thead>
            <tr>
                <td>序号</td>
                <td>编号</td>
                <td>产品名称</td>
                <td>型号</td>
                <td>数量</td>
                <td>采购时间</td>
                <td>到库时间</td>
                <td>零售价</td>
                <td>单位</td>
                <td>系数</td>
                <td>计算方式</td>
                <td>采购价格</td>
                <td>出厂价格</td>
                <td>备注</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="data" varStatus="st">
            <tr>
                <td>${st.index+1 }</td>
                <td>${data.serialNumber}</td>
                <td>${data.name}</td>
                <td>${data.model}</td>
                <td>${data.num}</td>
                <td><fmt:formatDate value="${data.purchaseDate}" pattern="yyyy/MM/dd"/></td>
                <td><fmt:formatDate value="${data.arrivalDate}" pattern="yyyy/MM/dd"/></td>
                <td>${data.price}</td>
                <td>${data.unit}</td>
                <td>${data.coefficient}</td>
                <td>
                	<c:if test="${data.calculateModel==1 }">发热墙布计算</c:if>
                	<c:if test="${data.calculateModel==2 }">一体膜计算</c:if>
                	<c:if test="${data.calculateModel==3 }">黄色耐高温墙纸胶计算</c:if>
                	<c:if test="${data.calculateModel==4 }">白色耐高温墙纸胶计算</c:if>
                	<c:if test="${data.calculateModel==5 }">智能温控器计算</c:if>
                	<c:if test="${data.calculateModel==6 }">T型连接线计算</c:if>
                	<c:if test="${data.calculateModel==7 }">快速接头计算</c:if>
                	<c:if test="${data.calculateModel==8 }">通用</c:if>
                </td>
                <td>${data.purchasePrice}</td>
                <td>${data.factoryPrice}</td>
                <td>${data.remark}</td>
                <td>
                	<a href="#" class="link editlink" onclick="toUpdate(${data.id})">修改</a>
                	<a href="#" class="link deletelink" onclick="toDel(${data.id})">删除</a>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
<form action="/material/save.html" onsubmit="return check()" method="post">
<input type="hidden" name="id" value="" id="materialId">
<div class="pop delreasonpop pop1" style="display: none;">
	<div class="popbg"></div>
    <div class="layel" style="height: 650px;">
        <h3 class="poptitle"><span id="winTitle">修改采购单</span><i class="closeicon"></i> </h3>
        <div class="bigbox">
            <span class="text" >编号：</span><input type="text" name="serialNumber" id="serialNumber"/>
        </div>
        <div class="bigbox">
            <span class="text" >名称：</span><input type="text" name="name" id="name"/>
        </div>
        <div class="bigbox">
            <span class="text" >型号：</span><input type="text" name="model" id="model"/>
        </div>
         <div class="bigbox">
            <span class="text" >数量：</span><input type="text" name="num" id="num"/>
        </div>
         <div class="bigbox">
            <span class="text" >零售价：</span><input type="text" name="price" id="price"/>
        </div>
         <div class="bigbox">
            <span class="text" >单位：</span><input type="text" name="unit" id="unit"/>
        </div>
         <div class="bigbox">
            <span class="text" >系数：</span><input type="text" name="coefficient" value="1" id="coefficient"/>
        </div>
         <div class="bigbox">
            <span class="text" >计算方式：</span>
            <select name="calculateModel" id="calculateModel">
            	<option value="1">发热墙布计算</option>
            	<option value="2">一体膜计算</option>
            	<option value="3">黄色耐高温墙纸胶计算</option>
            	<option value="4">白色耐高温墙纸胶计算</option>
            	<option value="5">智能温控器计算</option>
            	<option value="6">T型连接线计算</option>
            	<option value="7">快速接头计算</option>
            	<option value="8">通用</option>
            </select>
        </div>
        <div class="bigbox">
            <span class="text" >采购价格：</span><input type="text" name="purchasePrice" id="purchasePrice"/>
        </div>
        <div class="bigbox">
            <span class="text" >出厂价格：</span><input type="text" name="factoryPrice" id="factoryPrice"/>
        </div>
         <div class="bigbox">
            <span class="text" >采购时间：</span><input type="text" name="purchaseDateStr" id="purchaseDateStr" value="" placeholder="采购时间" class="datetimepicker startdata" onclick="SelectDate(this,'yyyy-MM-dd')">
        </div>
        <div class="bigbox">
            <span class="text" >到库时间：</span><input type="text" name="arrivalDateStr" id="arrivalDateStr" value="" placeholder="到库时间" class="datetimepicker startdata" onclick="SelectDate(this,'yyyy-MM-dd')">
        </div>
        <div class="bigbox">
            <span class="text remarks" >备注：</span>
            <textarea rows="3" cols="20" name="remark" id="remark"></textarea>
        </div>
        <div class="row tc">
            <input type="submit" value="确认" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
</form>
<form action="/material/del.html" method="post">
<input type="hidden" name="materialId" value="" id="delmaterialId">
<div class="pop delreasonpop pop2" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">删除记录<i class="closeicon"></i> </h3>
        <img src="../images/question.png" class="question">
        <p class="prompt">是否确认删除物料单？删除后无法恢复!</p>
        <div class="row tc">
            <input type="submit" value="确定" class="ok">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
</form>
</body>
</html>
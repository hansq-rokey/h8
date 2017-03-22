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
    <link href="../../../css/purchase.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../plug/adddatetime.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>采购</title>
    <script type="text/javascript">
     	$(document).ready(function(){
     		setCategory();
     		var msg = $("#msg").val();
     		if(msg != undefined && msg !=null && msg != ""){
     			alertLayel("删除失败,该数据已被关联使用!");
     		}
	    });
     	function setCategory(){
	    	$.ajax({
		 		   url: "/sysSet/batch/getCategoryList.html",
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  if ( !checkCode( obj ) ) {
		 					return;
		 			  }
		 			  var html = "<i class='arrowtop'></i>";
		 			  var data = obj.result.list;
		 			  for(var o in data){
		 				 html = html+ "<li onclick='setcategoryVal("+data[o].id+")' id='liu"+data[o].id+"' _name='"+data[o].name+"'>"+data[o].name+"</li>";
		 			  }
		 			  $("#categoryU").append(html);
		 		   }
	   		});
	    }
     	var setcategoryVal=function(id){
 	  	   var _name = $("#liu"+id).attr("_name");
 	  	   $("#categorySel").text(_name);
 	  	   $("#categoryId").val(id);
 	  	   $('#categoryU').fadeOut(10);
 	  	   //级联设置下一级别
 	  	   cleanLi(1);
 	  	   setCategoryModel(id);
 	    }
     	function cleanLi(type){
     		if(type == 1){
     		   $("#categoryModelU").find("li").remove();
     		   $("#categoryModelSel").text("-选择-");
     		   $("#categoryModelId").val("");
     		   $("#categoryModelFormatU").find("li").remove();
    		   $("#categoryModelFormatSel").text("-选择-");
    		   $("#categoryModelFormatId").val("");
     		}
     		if(type==2){
     		   $("#categoryModelFormatU").find("li").remove();
     		   $("#categoryModelFormatSel").text("-选择-");
     		   $("#categoryModelFormatId").val("");
     		}
     		$("#batch").val("");
     	}
     	function setCategoryModel(id){
	    	$.ajax({
		 		   url: "/sysSet/batch/getCategoryModelList.html?categoryId="+id,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  if ( !checkCode( obj ) ) {
		 					return;
		 			  }
		 			  var html = "<i class='arrowtop'></i>";
		 			  var data = obj.result.list;
		 			  for(var o in data){
		 				 html = html+ "<li onclick='setcategoryModelVal("+data[o].id+")' id='lium"+data[o].id+"' _name='"+data[o].name+"'>"+data[o].name+"</li>";
		 			  }
		 			  $("#categoryModelU").append(html);
		 		   }
	   		});
	    }
     	var setcategoryModelVal=function(id){
  	  	   var _name = $("#lium"+id).attr("_name");
  	  	   $("#categoryModelSel").text(_name);
  	  	   $("#categoryModelId").val(id);
  	  	   $('#categoryModelU').fadeOut(10);
  	  	   cleanLi(2);
  	  	   //级联设置下一级别
  	  	   setCategoryModelFormat(id);
  	    }
     	function setCategoryModelFormat(id){
	    	$.ajax({
		 		   url: "/sysSet/batch/getCategoryModelFormatList.html?categoryModelId="+id,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  if ( !checkCode( obj ) ) {
		 					return;
		 			  }
		 			  var html = "<i class='arrowtop'></i>";
		 			  var data = obj.result.list;
		 			  for(var o in data){
		 				 html = html+ "<li onclick='setcategoryModelFormatVal("+data[o].id+")' id='liumf"+data[o].id+"' _name='"+data[o].name+"'>"+data[o].name+"</li>";
		 			  }
		 			  $("#categoryModelFormatU").append(html);
		 		   }
	   		});
	    }
     	var setcategoryModelFormatVal=function(id){
   	  	   var _name = $("#liumf"+id).attr("_name");
   	  	   $("#categoryModelFormatSel").text(_name);
   	  	   $("#categoryModelFormatId").val(id);
   	  	   $('#categoryModelFormatU').fadeOut(10);
   	  	   //级联设置获取批次
   	  	   setbatch();
   	    }
     	function setbatch(){
     		var categoryId = $("#categoryId").val();
     		var categoryModelId = $("#categoryModelId").val();
     		var categoryModelFormatId = $("#categoryModelFormatId").val();
	    	$.ajax({
		 		   url: "/sysSet/batch/getBatchNum.html?categoryId="+categoryId+"&categoryModelId="+categoryModelId+"&categoryModelFormatId="+categoryModelFormatId,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  if ( !checkCode( obj ) ) {
		 					return;
		 			  }
		 			  var data = obj.result.num;
		 			  $("#batch").val(data);
		 		   }
	   		});
	    }
     	function check(){
     		var orderNumber = $('#orderNumber').val(); 
        	if( orderNumber == null || orderNumber  ==undefined || orderNumber  == ''){
        		alertLayel("订单编号不可为空!");
        		return false;
        	}
        	var categoryId = $('#categoryId').val(); 
        	if( categoryId == null || categoryId  ==undefined || categoryId  == ''){
        		alertLayel("产品品类不可为空!");
        		return false;
        	}
        	var categoryModelId = $('#categoryModelId').val(); 
        	if( categoryModelId == null || categoryModelId  ==undefined || categoryModelId  == ''){
        		alertLayel("产品名称不可为空!");
        		return false;
        	}
        	var categoryModelFormatId = $('#categoryModelFormatId').val(); 
        	if( categoryModelFormatId == null || categoryModelFormatId  ==undefined || categoryModelFormatId  == ''){
        		alertLayel("产品规格不可为空!");
        		return false;
        	}
        	var batch = $('#batch').val(); 
        	if( batch == null || batch  ==undefined || batch  == ''){
        		alertLayel("所属批次不可为空!");
        		return false;
        	}
        	var orderNum = $('#orderNum').val(); 
        	if(orderNum != null && orderNum != ''){
        		var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
        		if(!reg.test(orderNum)){  
        	        alert("产品数量只支持正整数!");
        	        return false;
        	    }  
        	}
        	//如果时间都不为空才比较
        	var transportTime = $('#transportTime').val();
        	var endTime = $('#endTime').val(); 
        	if(transportTime != null && transportTime != '' && endTime!= null && endTime != ''){
        		 var start = new Date(transportTime.replace("-", "/").replace("-", "/"));
                 var end = new Date(endTime.replace("-", "/").replace("-", "/"));
                 if (end < start) {
                	 alert('到货时间不能小于运输日期！');
                     return false;
                 }
        	}
        	return true;
        }
     	function toAdd(){
     		clean();
     		$('.pop1').show();
     		$('#categoryD').show();
     		$('#categoryModelD').show();
     		$('#categoryModelFormatD').show();
     		$('#categorys').hide();
     		$('#categoryModels').hide();
     		$('#categoryModelFormats').hide();
     		cleanLi(1);
     		$("#categorySel").text("-选择-");
     		$("#winTitle").text("新增采购订单");
     	}
     	function toUpdate(id){
     		clean();
     		$('.pop1').show();
     		$.ajax({
		 		   url: "/purchase/getPurchaseOrder.html?id="+id,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  	if ( !checkCode( obj ) ) {
		 					return;
		 			  	}
		 			  	var data = obj.result;
			     		$('#orderId').val(data.id); 
			     		$('#categoryId').val(data.categoryId);
			     		$('#categoryModelId').val(data.categoryModelId);
			     		$('#categoryModelFormatId').val(data.categoryModelFormatId);
			     		$('#orderNumber').val(data.orderNumber);
			     		$('#batch').val(data.batch);
			     		$('#description').val(data.description);
			     		$('#orderNum').val(data.orderNum);
			     		$('#transportTime').val(data.transportTime);
			     		$('#transportType').val(data.transportType);
			     		$('#endTime').val(data.endTime);
			     		$('#categorys').text(data.categoryName);
			     		$('#categoryModels').text(data.categoryModelName);
			     		$('#categoryModelFormats').text(data.categoryModelFormatName);
		 		  }
	   		});
     		$('#categoryD').hide();
     		$('#categoryModelD').hide();
     		$('#categoryModelFormatD').hide();
     		$('#categorys').show();
     		$('#categoryModels').show();
     		$('#categoryModelFormats').show();
     		$("#winTitle").text("修改采购订单");
     	}
     	function toDel(id){
     		$('.pop2').show();
     		$('#purchaseId').val(id); 
     	}
     	function clean(){
     		$('#orderId').val(''); 
     		$('#categoryId').val('');
     		$('#categoryModelId').val('');
     		$('#categoryModelFormatId').val('');
     		$('#orderNumber').val('');
     		$('#batch').val('');
     		$('#description').val('');
     		$('#orderNum').val('');
     		$('#transportTime').val('');
     		$('#transportType').val('');
     		$('#endTime').val('');
     	}
     </script>
</head>
<body>
<div style="display: none;">
	<form action="/purchase/list.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
    	<input type="button" value="添加采购" onclick="toAdd()" class="searchbtn" style="margin-top: 15px;margin-left: 0px;">
    	<input type="hidden" id="msg" value="${msg }">
        <table class="handleloglist">
            <thead>
            <tr>
                <td style="min-width:60px;">序号</td>
                <td style="min-width:60px;">订单号</td>
                <td style="min-width:70px;">产品品类</td>
                <td style="min-width:70px;">产品名称</td>
                <td style="min-width:70px;">产品规格</td>
                <td style="min-width:60px;">批次</td>
                <td style="min-width:70px;">批次描述</td>
                <td style="min-width:80px;">运输时间</td>
                <td style="min-width:70px;">运输方式</td>
                <td style="min-width:80px;">到货时间</td>
                <td style="min-width:70px;">订购数量</td>
                <td style="min-width:60px;">已入库</td>
                <td style="min-width:60px;">未入库</td>
                <td style="min-width:70px;">订单状态</td>
                <td style="min-width:60px;">操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="data" varStatus="st">
            <tr>
                <td>${st.index+1 }</td>
                <td>${data.orderNumber}</td>
                <td>${data.category.name}</td>
                <td>${data.categoryModel.name}</td>
                <td>${data.categoryModelFormat.name}</td>
                <td>${data.batch}</td>
                <td>${data.description}</td>
                <td><fmt:formatDate value="${data.transportTime}" pattern="yyyy/MM/dd"/></td>
                <td>${data.transportType}</td>
                <td><fmt:formatDate value="${data.endTime}" pattern="yyyy/MM/dd"/></td>
                <td>${data.orderNum}</td>
                <td>${data.putStorageNum}</td>
                <td>${data.surplusStorageNum}</td>
                <td>
                	<c:if test="${data.status == 10 }">待入库 
                	</c:if>
                	<c:if test="${data.status == 20 }">入库中
                	</c:if>
                	<c:if test="${data.status == 30 }">入库完成
                	</c:if>
                </td>
                <td>
                	<c:if test="${data.status == 10 }">
                		<a href="#" class="link editlink" onclick="toUpdate(${data.id})">修改</a>
                		<a href="#" class="link deletelink" onclick="toDel(${data.id})">删除</a>
                	</c:if>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
<form action="/purchase/save.html" onsubmit="return check()" method="post">
<input type="hidden" name="id" value="" id="orderId">
<input type="hidden" name="category.id" value="" id="categoryId">
<input type="hidden" name="categoryModel.id" value="" id="categoryModelId">
<input type="hidden" name="categoryModelFormat.id" value="" id="categoryModelFormatId">
<div class="pop delreasonpop pop1" style="display: none;">
	<div class="popbg"></div>
    <div class="layel layel1">
        <h3 class="poptitle"><span id="winTitle">修改采购单</span><i class="closeicon"></i> </h3>
        <div class="bigbox o_fh">
            <span class="text" >订单号：</span><input class="w1" type="text" name="orderNumber" id="orderNumber" maxlength="20"/>
        </div>
        <div class="bigbox clear">
            <span class="text" >产品品类：</span><span id="categorys"></span>
            <div class=" " id="categoryD">
                <span class="selectinput w1">
                <span class="selectvalue" id="categorySel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="bigbox clear">
            <span class="text" >产品名称：</span><span id="categoryModels"></span>
            <div class="" id="categoryModelD">
                <span class="selectinput w1">
                <span class="selectvalue" id="categoryModelSel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryModelU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="bigbox clear">
            <span class="text" >产品规格：</span><span id="categoryModelFormats"></span>
            <div class="" id="categoryModelFormatD">
                <span class="selectinput w1">
                <span class="selectvalue" id="categoryModelFormatSel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryModelFormatU">
                 </ul>
                </span>
            </div>
        </div>
         <div class="bigbox o_fh clear">
            <span class="text" >所属批次：</span><input class="w1" type="text" name="batch" id="batch" readonly="readonly"/>
        </div>
        <div class="bigbox o_fh">
            <span class="text description" >批次描述：</span><textarea class="w1" rows="3" cols="20" name="description" id="description"></textarea>
        </div>
        <div class="bigbox o_fh">
            <span class="text" >产品数量：</span><input class="w1" type="text" name="orderNum" id="orderNum" maxlength="8"/>
        </div>
        <div class="bigbox o_fh">
            <span class="text" >运输时间：</span><input class="w1" type="text" name="transportTimeStr" id="transportTime" placeholder="运输时间" class="datetimepicker startdata" onclick="SelectDate(this,'yyyy-MM-dd')"/>
        </div>
        <div class="bigbox o_fh">
            <span class="text" >运输方式：</span><input class="w1" type="text" name="transportType" id="transportType" maxlength="6"/>
        </div>
         <div class="bigbox o_fh">
            <span class="text" >到货时间：</span><input class="w1" type="text" value="" name="endTimeStr" id="endTime" placeholder="到货时间" class="datetimepicker startdata" onclick="SelectDate(this,'yyyy-MM-dd')">
        </div>
        <div class="row tc">
            <input type="submit" value="确认" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
</form>
<form action="/purchase/del.html" method="post">
<input type="hidden" name="purchaseId" value="" id="purchaseId">
<div class="pop delreasonpop pop2" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">删除记录<i class="closeicon"></i> </h3>
        <img src="../images/question.png" class="question">
        <p class="prompt">是否确认删除采购单？删除后无法恢复!</p>
        <div class="row tc">
            <input type="submit" value="确定" class="ok">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
</form>
</body>
</html>
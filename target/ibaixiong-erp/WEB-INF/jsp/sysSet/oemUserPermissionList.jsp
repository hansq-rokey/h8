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
    <title>OEM员工详情</title>
    <script type="text/javascript">
     	$(document).ready(function(){
     		setCategory();
	    });
     	function setCategory(){
	    	$.ajax({
		 		   url: "/sysSet/batch/getOemCategoryList.html",
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
    		   $("#batchU").find("li").remove();
    		   $("#batchSel").text("-选择-");
    		   $("#batchId").val("");
     		}
     		if(type==2){
     		   $("#categoryModelFormatU").find("li").remove();
     		   $("#categoryModelFormatSel").text("-选择-");
     		   $("#categoryModelFormatId").val("");
     		   $("#batchU").find("li").remove();
   		   	   $("#batchSel").text("-选择-");
   		       $("#batchId").val("");
     		}
     		if(type==3){
      		   $("#batchU").find("li").remove();
    		   $("#batchSel").text("-选择-");
    		   $("#batchId").val("");
      		}
     	}
     	function setCategoryModel(id){
	    	$.ajax({
		 		   url: "/sysSet/batch/getOemCategoryModelList.html?categoryId="+id,
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
		 		   url: "/sysSet/batch/getOemCategoryModelFormatList.html?categoryModelId="+id,
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
   	  	   cleanLi(3);
   	  	   setbatch();
   	    }
     	function setbatch(){
     		var categoryId = $("#categoryId").val();
     		var categoryModelId = $("#categoryModelId").val();
     		var categoryModelFormatId = $("#categoryModelFormatId").val();
	    	$.ajax({
		 		   url: "/sysSet/batch/getOemBatchList.html?categoryId="+categoryId+"&categoryModelId="+categoryModelId+"&categoryModelFormatId="+categoryModelFormatId,
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
		 				 html = html+ "<li onclick='setbatchVal("+data[o].id+")' id='libatch"+data[o].id+"' _name='"+data[o].name+"'>"+data[o].name+"</li>";
		 			  }
		 			  $("#batchU").append(html);
		 		   }
	   		});
	    }
     	var setbatchVal=function(id){
   	  	   var _name = $("#libatch"+id).attr("_name");
   	  	   $("#batchSel").text(_name);
   	  	   $("#batchId").val(id);
   	  	   $('#batchU').fadeOut(10);
    	}
     	
     	function check(){
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
        	var batch = $('#batchId').val(); 
        	if( batch == null || batch  ==undefined || batch  == ''){
        		alertLayel("所属批次不可为空!");
        		return false;
        	}
        	//检查是否允许该批次添加
        	var b = true;//页面是否发生跳转
        	var adminId = $('#adminId').val(); 
        	var batchId = $('#batchId').val();
        	$.ajax({
		 		   url: "/sysSet/oem/checkUserPermission.html?batchId="+batchId+"&adminId="+adminId,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   async: false,
		 		   success: function(obj){
		 			  if ( obj.code == 1 ) {
		 				  b = false;
		 			  }
		 		   }
	   		});
        	if(b==false){
        		alertLayel("所属批次在该人员下已存在!");
  			   return false;
  		    }
        	return true;
        }
     	function toAdd(){
     		clean();
     		$('.pop1').show();
     		cleanLi(1);
     		$("#categorySel").text("-选择-");
     	}
     	function clean(){
     		$('#categoryId').val('');
     		$('#categoryModelId').val('');
     		$('#categoryModelFormatId').val('');
     		$('#batchId').val('');
     	}
     	function toDisable(id){
     		$("#id1").val(id);
     		$(".pop2").show();
     	}
     	function toAble(id){
     		$("#id2").val(id);
     		$(".pop3").show();
     	}
     </script>
</head>
<body>
<div style="display: none;">
	<form action="/sysSet/oem/userPermissionList.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	 	<input type="hidden" name="adminId" id="adminIdPage" value="${adminId }">
		<input type="hidden" name="relationId" value="${relationId }">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
    	<span class="light"  style="margin-top: 10px;display:inline-block"><a href="/sysSet/oem/adminList.html?oemId=${oemId }">OEM厂管理</a></span>>
        <span class="darker">员工详情</span>
        <div class="content">
        	<span class="name" style="font-size: 16px;font-weight: 800;">姓名：${adminName }</span>
        	<span style="font-size: 16px;font-weight: 800;">工厂：${oemName }</span>
        	<input type="button" class="searchbtn" onclick="toAdd()" value="新增">
        </div>
        <table class="handleloglist">
            <thead>
            <tr>
                <td>序号</td>
                <td>产品品类</td>
                <td>产品名称</td>
                <td>产品规格</td>
                <td>批次</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="data" varStatus="st">
            <tr>
                <td>${st.index+1 }</td>
                <td>${data.category.name}</td>
                <td>${data.categoryModel.name}</td>
                <td>${data.categoryModelFormat.name}</td>
                <td>${data.batchDetail.batchNumber}</td>
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
<form action="/sysSet/oem/saveUserPermission.html" onsubmit="return check()" method="post">
<input type="hidden" name="category.id" value="" id="categoryId">
<input type="hidden" name="categoryModel.id" value="" id="categoryModelId">
<input type="hidden" name="categoryModelFormat.id" value="" id="categoryModelFormatId">
<input type="hidden" name="batchDetail.id" value="" id="batchId">
<input type="hidden" name="adminId" id="adminId" value="${adminId }">
<input type="hidden" name="relationId" value="${relationId }">
<div class="pop delreasonpop pop1" style="display: none;">
	<div class="popbg"></div>
    <div class="layel layel1" style="height: 350px;">
        <h3 class="poptitle"><span id="winTitle">新增批次</span><i class="closeicon"></i> </h3>
        <div class="bigbox">
            <span class="text" >产品品类：</span>
            <div class=" " id="categoryD">
                <span class="selectinput w1">
                <span class="selectvalue" id="categorySel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="bigbox">
            <span class="text" >产品名称：</span>
            <div class="" id="categoryModelD">
                <span class="selectinput w1">
                <span class="selectvalue" id="categoryModelSel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryModelU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="bigbox">
            <span class="text" >产品规格：</span>
            <div class="" id="categoryModelFormatD">
                <span class="selectinput w1">
                <span class="selectvalue" id="categoryModelFormatSel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryModelFormatU">
                 </ul>
                </span>
            </div>
        </div>
         <div class="bigbox">
            <span class="text" >所属批次：</span>
            <div class="" id="batchD">
                <span class="selectinput w1">
                <span class="selectvalue" id="batchSel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="batchU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="row tc">
            <input type="submit" value="确认" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
</form>

<form action="/sysSet/oem/updateUserPermissionInvalid.html" method="post">
<input type="hidden" name="id" value="" id="id1">
<input type="hidden" name="invalid" value="1" id="invalid">
<input type="hidden" name="adminId" value="${adminId }">
<input type="hidden" name="relationId" value="${relationId }">
<div class="pop delreasonpop pop2" style="display: none;">
	<div class="popbg"></div>
    <div class="layel" style="height:260px;">
        <h3 class="poptitle">禁用<i class="closeicon"></i> </h3>
        <p class="text" style="width:350px;">禁用后相关员工将无法进行扫码入库等工作，是否禁用？</p>
        <div class="bigbox tc" style="margin:0;">
            <input type="submit" value="禁用" style="margin-top:20px;" class="confirm">
        </div>
    </div>
</div>
</form>
<form action="/sysSet/oem/updateUserPermissionInvalid.html" method="post">
<input type="hidden" name="id" value="" id="id2">
<input type="hidden" name="invalid" value="0" id="invalid">
<input type="hidden" name="adminId" value="${adminId }">
<input type="hidden" name="relationId" value="${relationId }">
<div class="pop delreasonpop pop3" style="display: none;">
	<div class="popbg"></div>
    <div class="layel" style="height:260px;">
        <h3 class="poptitle">解除禁用<i class="closeicon"></i> </h3>
        <p class="text" style="width:350px;">激活后相关员工将拥有进行扫码包装等权限，是否激活？</p>
        <div class="bigbox tc" style="margin:0;">
            <input type="submit" value="解禁" style="margin-top:20px;" class="confirm">
        </div>
    </div>
</div>
</form>
</body>
</html>
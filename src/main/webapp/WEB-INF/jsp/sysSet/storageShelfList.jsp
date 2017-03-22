<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/warehouse-goodsshelves.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>仓库管理</title>
    <script type="text/javascript">
	    $(document).ready(function(){
	        $(document).on('click','.searchbtn',function(){
	         	$('.delreasonpop').show();
	        });
	        $(document).on('click','.searchbtn',function(){
	         	$('.delreasonpop').show();
	        });
	        setCategory();
	    });
	    function saveInfo(data){
	    	$.ajax({
		 		   url: "/sysSet/storage/saveshelfAjax.html",
		 		   data: data,//参数
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  if ( !checkCode( obj ) ) {
		 					return;
		 			  }else{
		 			  	//fn(obj); //异步处理成功后回调
		 			  }
		 		   }
		 	});
	    }
    	function check(){
    		var goodsShelfNumber = $("#goodsShelfNumber").val();
    		if(goodsShelfNumber == null || goodsShelfNumber ==undefined || goodsShelfNumber == ''){
    			alertLayel("货架编号不可为空");
				return false;
			} 
    		return true;
    	}
    	function checkChild(){
    		var goodsShelfNumber = $("#goodsShelfNumberChild").val();
    		if(goodsShelfNumber == null || goodsShelfNumber ==undefined || goodsShelfNumber == ''){
    			alertLayel("货架编号不可为空");
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
    		return true;
    	}
    	function toAddShelf(pid,pnum){
    		$("#childPid").val(pid);
    		$("#childTitle").text(pnum);
    		$("#childWin").text("添加");
    		$('.childShelf').show();
    	}
    	
    	//制作下拉联动
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
    		   //$("#batchU").find("li").remove();
    		   //$("#batchSel").text("-选择-");
    		   //$("#batchId").val("");
     		}
     		if(type==2){
     		   $("#categoryModelFormatU").find("li").remove();
     		   $("#categoryModelFormatSel").text("-选择-");
     		   $("#categoryModelFormatId").val("");
     		   //$("#batchU").find("li").remove();
   		   	   //$("#batchSel").text("-选择-");
   		       //$("#batchId").val("");
     		}
     		if(type==3){
      		   //$("#batchU").find("li").remove();
    		   //$("#batchSel").text("-选择-");
    		   //$("#batchId").val("");
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
   	  	   //setbatch();
   	    }
     	//toEdit(${child.id },${data.id },'${child.goodsShelfNumber }','${data.goodsShelfNumber }',${child.category.id },
     	//'${child.category.name }',${child.categoryModel.id },
     	//'${child.categoryModel.name }',${child.categoryModelFormat.id },'${child.categoryModelFormat.name }')
    	function toEdit(id,pid,childNumber,parentNumber,categoryId,categoryName,categoryModelId,categoryModelName,categoryModelFormatId,categoryModelFormatName){
    		//初始化页面相关数据
    		$("#childWin").text("修改");
    		$("#childPid").val(pid);
    		$("#childTitle").text(parentNumber);
    		$("#categoryId").val(categoryId);
    		$("#categorySel").text(categoryName);
    		$("#categoryModelId").val(categoryModelId);
    		$("#categoryModelSel").text(categoryModelName);
    		$("#categoryModelFormatId").val(categoryModelFormatId);
    		$("#categoryModelFormatSel").text(categoryModelFormatName);
    		$("#entryId").val(id);
    		$("#goodsShelfNumberChild").val(childNumber);
    		setCategoryModel(categoryId);
    		setCategoryModelFormat(categoryModelId);
    		$('.childShelf').show();
    	}
    	
    </script>
</head>
<body>
<div style="display: none;">
	<form action="/sysSet/storage/shelfList.html" method="post">
	 	<input type="hidden" name="pageNo" id="pageNo" value="">
	 	<input type="hidden" name="storageId" id="storageId" value="${storageId }">
	    <input type="submit" id="pageSubmit" class="search" value="搜索">        	
	</form>
</div>
    <section>
        <span class="light" style="margin-top: 10px;display:inline-block"><a href="/sysSet/storage/list.html">仓库管理</a></span>>
        <span class="darker">货架管理</span>
        <div class="content">
            <p class="warehouse-name">${storage.storageName }</p>
            <input type="button" class="searchbtn" value="添加大类"/>
        </div>
        <table>
        	<thead>
        		<tr>
        			<td>货架编号</td>
        			<td>产品品类</td>
        			<td>产品名称</td>
        			<td>产品规格</td>
        			<td>操作</td>
        		</tr>
        	</thead>
        	<tbody>
        	<c:forEach items="${list}" var="data" varStatus="st">
        		<tr>
        			<td colspan="5" >${data.goodsShelfNumber }</td>
        		</tr>
        		<c:if test="${data.childList != null}">
        			<c:forEach items="${data.childList}" var="child" varStatus="st">
	        		<tr>
	        			<td><span class="hidespan">${child.goodsShelfNumber }</span><input type="text" data-id="${child.id }" class="hideinput rank" maxlength="5"></td>
	        			<td><span class="hidespan">${child.category.name }</span></td>
	        			<td><span class="hidespan">${child.categoryModel.name }</span></td>
	        			<td><span class="hidespan">${child.categoryModelFormat.name }</span></td>
	        			<td>
	        				<a href="#" class="link editlink" onclick="toEdit(${child.id },${data.id },'${child.goodsShelfNumber }','${data.goodsShelfNumber }',${child.category.id },'${child.category.name }',${child.categoryModel.id },'${child.categoryModel.name }',${child.categoryModelFormat.id },'${child.categoryModelFormat.name }')">编辑</a>
	        			</td>
	        		</tr>
	        		</c:forEach>
        		</c:if>
        		<tr>
        		    <td colspan="5" class="add"><a href="#" onclick="toAddShelf(${data.id},'${data.goodsShelfNumber }')" class="link">添加“${data.goodsShelfNumber }”子货架</a></td>
        		</tr>
        	</c:forEach>
        	</tbody>
        </table>
        <jsp:include page="../include/pages.jsp"></jsp:include>
	</section>
<form action="/sysSet/storage/saveshelf.html" method="post" onsubmit="return check()">
<div class="pop delreasonpop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel" style="height: 280px;">
        <h3 class="poptitle">添加大类<i class="closeicon"></i> </h3>
        <div class="bigbox">
        	<input type="hidden" name="storageId" value="${storage.id }">
        	<input type="hidden" name="parent.id" value="0">
            <span class="text">大类编号：</span><input type="text" name="goodsShelfNumber" id="goodsShelfNumber" class="text" maxlength="5"/>
        </div>
        <div class="row tc">
            <input type="submit" value="保存" class="save" style="margin-top: 40px;">
        </div>
    </div>
</div>
</form>
<form action="/sysSet/storage/saveshelf.html" method="post" onsubmit="return checkChild()">
<input type="hidden" name="categoryId" value="" id="categoryId">
<input type="hidden" name="categoryModelId" value="" id="categoryModelId">
<input type="hidden" name="categoryModelFormatId" value="" id="categoryModelFormatId">
<input type="hidden" name="id" value="" id="entryId">
<input type="hidden" name="storageId" value="${storage.id }">
<input type="hidden" name="parent.id" id="childPid" value="">
<div class="pop childShelf" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle"><span id="childWin"></span><span id="childTitle"></span>的子货架<i class="closeicon"></i> </h3>
        <div class="bigbox">
            <span class="text">货架编号：</span><input type="text" name="goodsShelfNumber" id="goodsShelfNumberChild" class="text" maxlength="5"/>
        </div>
        <div class="bigbox">
            <span class="text" >产品品类：</span>
            <div class=" " style="width:240px;float:left;" id="categoryD">
                <span class="selectinput">
                <span class="selectvalue" id="categorySel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="bigbox">
            <span class="text" >产品名称：</span>
            <div class="" style="width:240px;float:left;" id="categoryModelD">
                <span class="selectinput">
                <span class="selectvalue" id="categoryModelSel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryModelU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="bigbox">
            <span class="text" >产品规格：</span>
            <div class="" style="width:240px;float:left;" id="categoryModelFormatD">
                <span class="selectinput">
                <span class="selectvalue" id="categoryModelFormatSel">-选择-</span>
                 <i class="arrow arrowright"></i>
                 <ul class="option" id="categoryModelFormatU">
                 </ul>
                </span>
            </div>
        </div>
        <div class="row tc">
            <input type="submit" value="保存" class="save" style="margin-top: 10px;">
        </div>
    </div>
</div>
</form>
</body>
</html>
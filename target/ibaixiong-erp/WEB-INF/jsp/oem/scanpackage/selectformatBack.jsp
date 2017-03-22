<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href=".././../../css/scan-package.css" rel="stylesheet" type="text/css">
    <script src="../../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../../js/public.js" type="text/javascript" ></script>
    <title>扫描包装</title>
</head>
<script type="text/javascript">
$(document).ready(function(){
    $(document).on('click','.confirm',function(){
    	if($('#categoryModelFormatSel').text()!='-选择-'&&$('#categoryModelFormatSel').text()!=''){
    	    $('.pop').show();
    	}
    });
    $('.determine').on('click',function(){
    	var id=$(this).attr('data-value');
    	window.location.href='/oem/scanpackage/scanqrcode.html?selectId='+id;
    })
})
//加载一级数据
var format_name='';
var setcategoryVal=function(id){
		$('.CategoryFormat,.CategoryBatch').text('');
	   var _name = $("#liu"+id).attr("_name");
	   $(".CategoryModel").text(_name);
	   format_name='';
	   format_name+=_name;
	   $("#categoryId").val(id);
	   $('#categoryU').fadeOut(10);
	   //级联设置下一级别
	   cleanLi(1);
	   setCategoryModel(id);
  }
  //清除数据
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
  //二级数据
	function setCategoryModel(id){
	  console.log(id)
	$.ajax({
 		   url: "/oem/scanpackage/selectformat_format.html?categoryModelId="+id,
 		   type: "POST",
 		   dataType:"json",
 		   cache:false,
 		   success: function(obj){
 			   console.log(obj);
 			  var html = "<i class='arrowtop'></i>";
 			  var data = obj.data;
 			  for(var o in data){
 				 html = html+ "<li onclick='setcategoryModelVal("+data[o].id+")' id='lium"+data[o].id+"' _name='"+data[o].name+"' data-img='"+data[o].pics[0].url+"'>"+data[o].name+"</li>";
 			  }
 			  $("#categoryModelU").append(html);
 		   }
		});
}
	var setcategoryModelVal=function(id){
	   var _name = $("#lium"+id).attr("_name");
	   $(".CategoryFormat,#categoryModelSel").text(_name);
	   format_name+=_name;
	   $("#categoryModelId").val(id);
	   $('#categoryModelU').fadeOut(10);
	   $('.formatImg').attr('src',$("#lium"+id).attr("data-img"));
	   cleanLi(2);
	   //级联设置下一级别
	   setCategoryModelFormat(id);
  }
	//三级数据
	function setCategoryModelFormat(id){
		console.log(id)
	$.ajax({
 		   url: "/oem/scanpackage/selectformat_format_batch.html?formatId="+id,
 		   type: "POST",
 		   dataType:"json",
 		   cache:false,
 		   success: function(obj){
 			   console.log(obj)
 			  var html = "<i class='arrowtop'></i>";
 			  var data = obj.data;
 			  for(var o in data){
 				 html = html+ "<li onclick='setcategoryModelFormatVal("+data[o].id+")' id='liumf"+data[o].id+"' _name='"+data[o].batchDetail.batchNumber+"'>"+data[o].batchDetail.batchNumber+"</li>";
 			  }
 			  $("#categoryModelFormatU").append(html);
 		   }
		});
}
	var setcategoryModelFormatVal=function(id){
 	   var _name = $("#liumf"+id).attr("_name");
 	   $(".CategoryBatch,#categoryModelFormatSel").text(_name);
 	   format_name+=_name;
 	   $('.format-name').text(format_name);
 	   $("#categoryModelFormatId").val(id);
	   $('.determine').attr('data-value',id);
 	   $('#categoryModelFormatU').fadeOut(10);
   }
</script>
<body>
    <section>
        <span class="select-specifica">选择产品：</span>
        <span class="selectinput">
            <span class="selectvalue">${listCategoryModel[0].name}</span>
            <i class="arrow arrowright"></i>
            <ul class="option">
            	<c:forEach items="${listCategoryModel}" var="item">
                	<li data-id="${ item.id }" onclick='setcategoryVal(${ item.id })' id='liu${ item.id }' _name='${ item.name }'>${item.name}</li>
                </c:forEach>
            </ul>
        </span>
        <span class="select-specifica">选择规格：</span>
        <span class="selectinput">
            <span class="selectvalue" id="categoryModelSel"></span>
            <i class="arrow arrowright"></i>
            <ul class="option" id="categoryModelU">
            	
            </ul>
        </span>
        <span class="select-specifica">选择批次：</span>
        <span class="selectinput">
            <span class="selectvalue" id="categoryModelFormatSel"></span>
            <i class="arrow arrowright"></i>
            <ul class="option" id="categoryModelFormatU">
            	
            </ul>
        </span>
        <input type="button" class="scan-record" value="扫描记录" onClick="window.location.href='/oem/scanpackage/scanhistory.html'"/>
        <span class="imagebox">
        	<img src="${ pic.url }" class="formatImg">
        </span>
        <span class="information">
        	<p>厂商：<span class="serRelation">${ erpOemUserRelation.oem.name }</span></p>
        	<p>产品名称：<span class="CategoryModel">${ listCategoryModel[0].name }</span></p>
        	<p>产品规格：<span class="CategoryFormat"></span></p>
        	<p>批次：<span class="CategoryBatch"></span></p>
        	<input type="button" class="confirm" value="确认"/>
        </span>
	</section>
<div class="pop delreasonpop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">确认规格<i class="closeicon"></i></h3>
        <p class="text">当前选择的规格为：<span class="format-name"></span><br>是否确认？</p>
        <div class="row tc">
            <input type="button" value="确定" class="determine">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
</body>
</html>
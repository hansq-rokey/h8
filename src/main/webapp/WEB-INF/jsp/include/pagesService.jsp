<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table style="width: 100%">
 <!-- 总页数 -->
 <input type="hidden" id="pages" value="${pageInfo.pages }">
 <!-- 当前页数 -->
 <input type="hidden" id="pageNumber" value="${pageInfo.pageNumber }">
<c:if test="${pageInfo.pages>1 }">
	<tr>
		<td class="tc" style="border: 0px;">
			 <div class="pages" style="margin:20px 0;">
				 	<c:if test="${!pageInfo.isFirstPage }">
	    			<span onclick="setPageNo(${pageInfo.prePage})" class="prev" style="cursor: pointer;">上一页</span>
					</c:if>
					<c:if test="${pageInfo.total>0 }">
					 <!-- 说明有记录 -->
						<span onclick="setPageNo(1)" style="cursor: pointer;float:none;" class="page-number ${pageInfo.pageNumber==1?'on':'' }">1<span class="left-dots">...</span></span>
					 	<div class="page-box">
	            			<div class="page-list">
						 	<c:forEach items="${pageInfo.navigatePageNumbers }" var="pageItem">
						 		<c:if test="${pageItem>1 && pageItem < pageInfo.pages }">
					            	<span onclick="setPageNo(${pageItem})" style="cursor: pointer;float:left;" class="page-number ${pageInfo.pageNumber==pageItem?'on':'' }">${pageItem }</span>			            	
					       		</c:if>
					       	</c:forEach>
				       		</div>
	        			</div>
	        			<c:if test="${pageInfo.pages>1 }">
				       	<span onclick="setPageNo(${pageInfo.pages })" style="cursor: pointer;float:none;" class="page-number ${pageInfo.pageNumber==pageInfo.pages?'on':'' }"><span class="right-dots">...</span>${pageInfo.pages }</span>
						</c:if>
					</c:if>
					<c:if test="${!pageInfo.isLastPage }">
				     <span onclick="setPageNo(${pageInfo.nextPage})" class="next" style="cursor: pointer;">下一页</span>
					</c:if>
			 </div>
		</td>
	</tr>
</c:if>
</table>
<script type="text/javascript">
	var num=$('#pages').val();
	var index=$('#pageNumber').val();
	function setPageNo(number){
		$('#pageNo').val(number);
		$('.search').click();
		//on_index(index);
	}
	
	function on_index(index){
	    if(num>7){
	        if(index==num){
	            $('.right-dots').hide();
	            $('.left-dots').show();
	            $('.page-list').css('left','-80px');
	        }
	        if(index>4&&index<num-2){
	            $('.left-dots').show();
	            $('.right-dots').show();
	            $('.page-list').css('left','-40px');
	        }
	        if(index>5&&index<num-2){
	            $('.left-dots').show();
	            $('.right-dots').show();
	            $('.page-list').css('left','-80px');
	        }
	        if(index>4&&index>=num-5){
	            $('.page-list').css('left','-80px');
	        }
	        if(index>4&&index>=num-3){
	            $('.right-dots').hide();
	        }
	        if(index<=4||$(this).hasClass('first-page')){
	            $('.page-list').css('left',0)
	            $('.left-dots').hide();
	            $('.right-dots').show();
	        }
	    }
	}
	$(document).ready(function(){
		//on_index(index);
		if(num<=7){
	        $('.page-box').css('width',(num-2)*40);
	        $('.right-dots,.left-dots').hide();
	    }
	    $('.page-list').css('width',(num-2)*40);
	})
</script>

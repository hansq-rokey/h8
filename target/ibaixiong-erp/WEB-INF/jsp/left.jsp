<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${sessionScope.models}" var="models">
	<li class="firstnavli">
	    <a href="#"> <span class="firstnav"><img class="marks prodicon" src="${models.imgUrl }">${models.name}<i class="arrow arrow-right"></i></span></a>
	    <ul>
	    	<c:if test="${models.childList != null }">
	    		<c:forEach items="${models.childList}" var="modelsChild">
		        	<a href="#"><li class="secondnavli" onclick="linkToMainFrame('${modelsChild.url }');"><span class="secondnav">${modelsChild.name}</span></li></a>
		        </c:forEach>
	        </c:if>
	    </ul>
	</li>
</c:forEach>
   
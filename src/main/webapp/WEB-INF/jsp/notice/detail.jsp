<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../../css/notice-detail.css" rel="stylesheet" type="text/css">
    <script src="../../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../../js/public.js" type="text/javascript" ></script>
    <script type="text/javascript" charset="utf-8" src="../../../../plug/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../../../../plug/ueditor/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="../../../../plug/ueditor/lang/zh-cn/zh-cn.js"></script>
    <title>通知详情</title>
</head>
<body>

        <div class="pathbox">
            <span class="light">通知列表</span>>
            <span class="darker">通知详情</span>
            <div class="main">
            	<p class="biaoti">${ erpNoticeInfo.name }</p>
            	<p class="neirong">${ erpNoticeInfo.description }</p>
            </div>
        </div>

</body>
<script>
$(document).ready(function(){

	console.log(${ erpNoticeInfo.description });
})
</script>
</html>
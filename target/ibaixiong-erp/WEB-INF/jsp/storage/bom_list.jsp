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
    <script type="text/javascript" src="../../../js/LodopFuncs.js"></script>
    <title>物料清单</title>
</head>
<style type="text/css">
    .box_tag{
        width: 100%;
    }
    .box_mian{
         width: 100%;
    }
    .box_mian>tbody>tr>td,.box_mian>tbody>tr>th{
        text-align:center;
        height: 40px;
    }
</style>
<body>
	<div class="box" id="box">
      <div id="title" style="LINE-HEIGHT:30px;width:100%;text-align:center;"><strong><span style="font-size:25px;color:#000;line-height: 60px;">熊爸爸石墨烯发热墙布配货清单</span></strong></div>  
      <table class="box_tag" id="box_mian" style="width:100%">
          <tbody>
              <tr>
                  <td>客户名称：${order.information.reveiveUserName }</td>
                  <td>联系方式：${order.information.mobilePhone }</td>
                  <td>收货地址：${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }</td>
              </tr>
              <tr>
                  <td>订单号：${order.orderNumber }</td>
                  <td>下单日期：<fmt:formatDate value="${order.createDateTime}" pattern="yyyy/MM/dd"/></td>
                  <td>订单备注：${order.remark }</td>
              </tr>
          </tbody>
      </table>
    </div>

    <div id="middle">
      <table   border="1" cellspacing="0" cellpadding="1" style="border-collapse:collapse;width:100%" bordercolor="#333333" class="box_mian" >
       <tbody>
         <tr>
         	<th>序号</th>
         	<th>物料编码</th>
         	<th>物料名称</th>
         	<th>规格型号</th>
         	<th>单位</th>
         	<th>配货量</th>
         	<th>备注</th>
         </tr>
         <c:forEach items="${bomList}" var="data" varStatus="st">
            <tr>
                <td>${st.index+1 }</td>
                <td>${data.serialNumber}</td>
                <td>${data.materialName}</td>
                <td>${data.materialModel}</td>
                <td>${data.unit}</td>
                <td>${data.num}</td>
               <%--  <td><fmt:formatDate value="${data.createDateTime}" pattern="yyyy/MM/dd"/></td>
                <td><fmt:formatDate value="${data.updateTime}" pattern="yyyy/MM/dd"/></td>
                <td>${data.bomNumber}</td> --%>
                <td>${data.remark}</td>
            </tr>
            </c:forEach>
      </table>
    <!--  
      -->
	</div>
	<div id="footer">
      <table  style="height:20px; width:100%">
          <tbody>
              <tr>
                  <td>批准：</td>
                  <td>审核：</td>
                  <td>发货人：</td>
              </tr>
          </tbody>
      </table>
	</div>
	<a href="javascript:PreviewMytable()" class="print_">打印</a>
</body>
<style>
 .print_{
	margin-left: 20px;
	background: rgb(255, 98, 0) none repeat scroll 0% 0%;
	color: white;
	width: 100px;
	height: 30px;
	display: inherit;
	text-align: center;
	line-height: 30px;
	margin-top: 20px;
 }
</style>
<script language="javascript" type="text/javascript"> 
	function PreviewMytable(){
        var LODOP=getLodop();  
        LODOP.PRINT_INIT("产品清单");
        var strStyle="<style>.box_tag>tbody>tr{width:30%;white-space:nowrap;vertical-align:top}</style>"
        LODOP.ADD_PRINT_HTM(10,"2%","93%",160,document.getElementById("box").innerHTML);


		var strStyle1="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>"
 
        LODOP.ADD_PRINT_HTM(150,"2%","93%",300,strStyle1+document.getElementById("middle").innerHTML);

        LODOP.ADD_PRINT_HTM(450,"2%","93%",20,document.getElementById("footer").innerHTML);

        LODOP.PREVIEW();    
    };  

</script>
</html>
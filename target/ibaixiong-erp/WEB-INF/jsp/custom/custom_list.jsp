 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
	<script src="../../../plug/jQuery/LodopFuncs.js" type="text/javascript"></script>
    <title>面板订制</title>
</head>
<style type="text/css">
.partlist{
    position: relative;
    border-bottom:1px solid #dcdcdc;
    width: 100%;
    height:40px;
}
.partlist li{
    width:100px;
    height:40px;
    float: left;
}
.partlist li span.switch{
    width: 85px;
    display: inline-block;
    height:40px;
    line-height:40px;
    margin:0 7px;
    text-align: center;
    cursor: pointer;
}
.partlist li span.switched{
    border-bottom:2px solid #03a9f4;
}
.inforbox{
    position: absolute;
    top:40px;
    left:0;
    display: none;
}
.selectinforbox{
    display: block;
}
.inforbox div table{
    width: 100%;
    text-align: center;
}
.inforbox div table thead{
    background:#fafafa;
}
.inforbox div table tr td {
    border: 1px solid #dcdcdc;
    height: 40px;
}
.sendpro{
	background:#ff6200;
	width:100px;
	height:32px;
	color:#fff;
	font-size:14px;
	margin-top:27px;
	margin-left:8px;
}
.sendlist{
	margin-top:10px;
}
.confirm{
	background:#ff6200;
	width:100px;
	height:32px;
	color:#fff;
	font-size:14px;
	margin-right:10px;
	margin-left:120px;
}
.okbtn{
	background:#ff6200;
	width:100px;
	height:32px;
	color:#fff;
	font-size:14px;
	margin-left:165px;
}
.cancel{
	background:none;
}
.leftword{
	width:95px;
	display:inline-block;
}
.express,.waybillnumber{
	width:250px;
}
.sendpop p{
	margin-left:40px;
}
.message{
	margin-left:500px;
	display:none;
}
.sendbtn{
	background:#dcdcdc;
}
</style>
<body>
<div style="display: none;">
	<form action="${url }" method="post">
		<div class="row">
		    <input type="hidden" name="pn" id="pageNo" value="">
		    <input type="submit" class="searchbtn search">
		</div>
	</form>
</div>
    <section>
        <ul class="partlist">
            <li>
            	<span class="switch ${status==0?'switched':'' }" onclick="toTh()"><a href="/u/custom/add/list.html">新订单</a></span>
                <div class="inforbox ${status==0?'selectinforbox':'' }">
                	<input type="button" class="sendpro sendbtn" value="发  货">
                	<span class="message"></span>
                	<div class="row">
                        <table class="sendlist">
                            <thead>
                            <tr>
                            	<td class="min-width:25px;"><input name="allcheck" type="checkbox" value="" class="allcheck"/></td>
                                <td class="min-width:133px;">订单号</td>
                                <td class="min-width:120px;">下单时间</td>
                                <td class="min-width:80px;">产品名称</td>
                                <td class="min-width:65px;">产品规格</td>
                                <td class="min-width:112px;">产品尺寸（厘米）</td>
                                <td class="min-width:35px;">数量</td>
                                <td class="min-width:65px;">发货时间</td>
                                <td class="min-width:65px;">操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${panelPrints }" var="item">
                            	<tr>
	                            	<td><input name="tdcheck" type="checkbox" value="${item.id }" /></td>
	                                <td>${item.orderNumber }</td>
	                                <td><fmt:formatDate value="${item.createDateTime }" pattern="yyyy.MM.dd HH:mm" /> </td>
	                                <td>${item.productName }</td>
	                                <td>${item.productFormat }</td>
	                                <td>${item.productLength }x${item.productLength }</td>
	                                <td>${item.num}</td>
	                                <td><fmt:formatDate value="${item.sendTime }" pattern="yyyy.MM.dd" /></td>
	                                <td><a class="link" href="/u/custom/download.html?orderNumber=${item.orderNumber}">下载图片 &nbsp;&nbsp;&nbsp;&nbsp;</a><a href="javascript:;" class="link sendLink"  data-id="${item.id }">发货</a></td>
	                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                	<jsp:include page="../include/pages.jsp"></jsp:include>
                </div>
                </div>
            </li>
            <li>
                <span class="switch ${status==1?'switched':'' }" onclick="toHh()"><a href="/u/custom/send/list.html">已发订单</a></span>
                <div class="inforbox ${status==1?'selectinforbox':'' }">
                	<div class="row">
                        <table class="sendlist">
                            <thead>
                            <tr>
                                <td class="min-width:133px;">订单号</td>
                                <td class="min-width:120px;">下单时间</td>
                                <td class="min-width:65px;">快递公司</td>
                                <td class="min-width:133px;">运单号</td>
                                <td class="min-width:80px;">产品名称</td>
                                <td class="min-width:65px;">产品规格</td>
                                <td class="min-width:112px;">产品尺寸（厘米）</td>
                                <td class="min-width:36px;">数量</td>
                                <td class="min-width:65px;">发货时间</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${panelPrints }" var="item">
                            	<tr>
	                                <td>${item.orderNumber }</td>
	                                <td><fmt:formatDate value="${item.createDateTime }" pattern="yyyy.MM.dd HH:mm" /> </td>
	                                <td>${item.logisticsCompany }</td>
	                                <td>${item.billNo }</td>
	                                <td>${item.productName }</td>
	                                <td>${item.productFormat }</td>
	                                <td>${item.productLength }x${item.productLength }</td>
	                                <td>${item.num}</td>
	                                <td><fmt:formatDate value="${item.sendTime }" pattern="yyyy.MM.dd" /></td>
	                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                	<jsp:include page="../include/pages.jsp"></jsp:include>
                </div>
                </div>
            </li>
         </ul>
    </section>
<div class="pop sendpop">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">确认发货<i class="closeicon"></i> </h3>
        <div class="row">
        	<p>
            	<span class="leftword" >快递公司：</span>
            	<input type="text" name="logisticsCompany" class="express">
            </p>
            <p style="margin-top:10px;">
            	<span class="leftword">运单号：</span>
            	<input type="text" name="billNo" class="waybillnumber">
            </p>
        </div>
        <div class="row">
            <input type="button" value="发货" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<script>
	$(document).ready(function(){
		var single=false;
		var ids=[];
		var $tdcheck=$('input[name="tdcheck"]');
		$('.sendLink').on('click',function(){
			ids=[];
			$('.sendpop').show(); 
			single=true;
			ids.push($(this).attr('data-id'));
		})
		$('.sendpro').on('click',function(){
			if(!$(this).hasClass('sendbtn')){
				$('.sendpop').show(); 
				single=false;
			}
		})
		$('.allcheck').on('click', function() {  
			var index=$('input[name="tdcheck"]').length;
			//console.log(index);
			if(index == 0){
				$(".sendpro").addClass(".sendbtn");
			}else{
				$('input[name="tdcheck"]').attr("checked",this.checked);
				if($(".allcheck").prop("checked")==true){
					$(".sendbtn").removeClass("sendbtn");
				}
				if($(".allcheck").prop("checked")==false){
					$(".sendpro").addClass("sendbtn");
				}
			}
		});
		$tdcheck.click(function(){
			if ($("input[name='tdcheck']:checked").length==0){
				$(".sendpro").addClass("sendbtn");
				//console.log(1);
			}
			if ($("input[name='tdcheck']:checked").length!=0){
				$(".sendbtn").removeClass("sendbtn");
			}
			if ($tdcheck.length != $("input[name='tdcheck']:checked").length){
				$(".allcheck").attr("checked",false);
			}
        }); 
		//发货信息提交
		$('.confirm').on('click',function(){
			var logisticsCompany=$('.express').val();
			var billNo=$('.waybillnumber').val();
			if(!single){
				ids=[]
				$tdcheck.each(function(){
					var id=$(this).val();
					if($(this).is(':checked')){
						ids.push(id);
					}
				});
			}
			if(!logisticsCompany){
				alertLayel("请输入快递公司名称");
				return false;
			}
			if(!logisticsCompany){
				alertLayel("请输入运单号");
				return false;
			}
			$.ajax({
                type: "post",
                url: "/u/custom/update.html",
                data:{logisticsCompany:logisticsCompany,billNo:billNo,ids:ids.join(",")},
                dataType: "json",
                success: function (data) {
                	var message=data.message;
                	$('.sendpop').hide();
					$('.message').show();
					$('.message').text(message);
					setTimeout('window.location.reload()', 2000);
                }
			})
		})
		
	});
</script>
</body>
</html>
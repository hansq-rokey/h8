<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/aftersalemanage.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
    <script src="../../../js/aftersalemanage.js" type="text/javascript" ></script>
    <script src="../../../js/base.js" type="text/javascript" ></script>
    <title>售后管理</title>
    <script type="text/javascript">
    	function toTh(){
    		location.href = "/aftetSale/queryList.html?queryType=1";
    	}
    	function toHh(){
    		location.href = "/aftetSale/queryList.html?queryType=2";
    	}
    	function toWx(){
    		location.href = "/aftetSale/queryList.html?queryType=3";
    	}
    	function agree(id){
    		$('.agreeWindow').show();
			$("#serviceId").val(id);
    	}
    	function agreeSub(){
    		var serviceId = $("#serviceId").val();
    		var url = "/aftetSale/operateOrder.html?serviceId="+serviceId+"&status="+31;
    		sub(url);
    	}
    	function refuse(id){
    		$('.refuseWindow').show();
			$("#serviceId").val(id);
    	}
    	function refuseSub(){
    		var serviceId = $("#serviceId").val();
    		var refuseMemo = $("#refuseMemo").val();
    		if( refuseMemo == null || refuseMemo  ==undefined || refuseMemo  == ''){
    			alertLayel("请输入拒绝理由");
        		return false;
        	}else{
        		var url = "/aftetSale/operateOrder.html?serviceId="+serviceId+"&status="+32+"&refuseMemo="+refuseMemo;
    			sub(url);
        	}
    	}
    	function sub(url){
    		$.ajax({
		 		   url: url,
		 		   type: "POST",
		 		   dataType:"json",
		 		   cache:false,
		 		   success: function(obj){
		 			  if ( !checkCode( obj ) ) {
						return;
		 			  }else{
		 				window.location.reload();//重载页面
		 			  }
		 		   }
	   		});
    	}
    	$(document).ready(function(){
        	var json=${json};
        	console.log(json);
        	data_list=json.result.orders;
        	$(document).on('click','.deliver-goods',function(){
                var data_id=$(this).attr('data-id');
                var html='';
                orderId='';
                hardwares=[];
                for(i=0;i<data_list.length;i++){
                    if(data_id==data_list[i].orderNumber){
                    	orderId=data_list[i].id;
                        for(j=0;j<data_list[i].itemList.length;j++){
                       		for(m=0;m<data_list[i].itemList[j].num;m++){
                       			html+='<div class="col-lg-6 "> <div class="pop-infor-box" data-value="'+data_list[i].itemList[j].modelFormatNumber+'" data-id="'+data_list[i].itemList[j].id+'" data-formatid="'+data_list[i].id+'"> <img src="'+data_list[i].itemList[j].pics[0].url+'" class="pop-prod-img"> <div class="information"> <p class="dark specifications">'+data_list[i].itemList[j].productTitle+'</p> <p class="light specifications">'+data_list[i].itemList[j].productModelFormatName+'</p> </div> </div> </div>';
                       		}
                        }
                        console.log(1);
                        $('.pop-prod-list').html(html);
                    }
                }
    			$('.sendpop').show();
    		});
        	/*确认配货*/
            var hardwares=[];
        	var orderId='';
            $('.code-text').change(function(){
                var code=$(this).val();
                $.ajax({
                    type: "GET",
                    url: "/order/hardware.html",
                    data:{uniqueCode: code},
                    dataType: "json",
                    success: function (data) {
                    	var Json=data.result.hardware;
                    	var FormatNumber=Json.categoryModelFormat.categoryModelFormatNumber;
    					$(this).hide().next('.deliver-goods').show();
    	                for(n=0;n<$('.pop-infor-box').length;n++){
    	                	console.log(2322)
    	                	var prod_code=$('.pop-infor-box').eq(n).attr('data-value');
    		                if(prod_code==FormatNumber){
    		                	var scaned=false;
    		                	console.log(hardwares);
    		                	for(m=0;m<hardwares.length;m++){
    		                		if(Json.id==hardwares[m]){
            		                	console.log(m);
    		                			scaned=true;
    		                			return false;
    		                		}
    		                	}
    		                	if(scaned){
    		                		return false;
    		                	}
    		                    hardwares.push(Json.id);
    		                    $('.pop-infor-box').eq(n).addClass('select').removeClass('pop-infor-box');
    			                return;
    		                }
    	                }
                    }
            	})
                $(this).attr('value', "");
            });
            
            $('.option li').on('click',function(){
            	var data_id=$(this).attr('data-id');
            	console.log(data_id);
            	$('#expressId').attr('data-id',data_id);
            });
            var expressId='';
            var mailNum='';
            $('.confirm-send').on('click',function(){
            	expressId=$('#expressId').attr("data-id");
            	mailNum=$('#expressId-num').val();
            	if(expressId == null || expressId ==undefined || expressId == ''){
            		alertLayel("请选择快递公司");
            		return ;
            	}
            	if(expressId != 1){
            		if(mailNum == null || mailNum ==undefined || mailNum == ''){
            			alertLayel("请输入快递运单号");
                		return ;
                	}
            	}
            	if(hardwares == null || hardwares ==undefined || hardwares == ''){
            		alertLayel("没有扫描放置任何硬件");
            		return ;
            	}
            	$('.pop4').show();
            	$('.sendpop').find('.layel').css('opacity',0);
            	console.log(expressId,mailNum,hardwares)
            })
            $('.sure-send').on('click',function(){
            	$('.pop').hide();
            	$.ajax({
                    type: "POST",
                    url: "/aftetSale/sendGoods.html",
                    data:{expressId:expressId,orderId:orderId,expressNumber:mailNum,hardwares:hardwares.join(',')},
                    dataType: "json",
                    async: false, 
                    success: function (data) {
                    	//$('.sendpop').hide();
                    	//{"code":0,"message":"","result":{"number":"R1077691446703649175"}}
                    	console.log(data);
                    	if(data.code ==0){
                    		$('.pop').hide();
                    		alertLayel("发货成功");
                    		var queryType = $('#queryType').val();
                    		var url="";
                    		if(queryType == null){
                    			url = "/aftetSale/queryList.html";
                    		}else{
                    			url = "/aftetSale/queryList.html?queryType="+queryType;
                    		}
                    		//发货打印
                    		$('.prod-codeurl').attr('src',data.result.orderCode);
                    		$('.prod-productCode').text("订单号："+data.result.number);
                    		print();
                    		myPreview4();
                    		location.href=url;
                    	}else{
                    		alertLayel(data.message);
                    	}
                    }
            	});
            });
            function print(){
               	var sendprod=[];    //发货打印开始
               	$('.select').each(function(){
               		var name=$(this).find('.dark').text();
               		var format=$(this).find('.light').text();
               		var sends={};
                   	//console.log(sendprod);
               		if(sendprod.length>0){
                   		for(i=0;i<sendprod.length;i++){
                           	sends.num=0;
                   			console.log(sendprod[i].name==name,111);
                   			if(sendprod[i].name==name&&sendprod[i].format==format){
                   				sendprod[i].num+=1;
                   			}else{
                    				sends.name=name;
                    				sends.format=format;
                    				sends.num=1;
                    				sendprod.push(sends);
                           	}
                   		}
                   	}else{
            				sends.name=name;
            				sends.format=format;
            				sends.num=1;
            				sendprod.push(sends);
                   	}
               	});
               	var html='';
               	if(sendprod.length>0){
               		for(i=0;i<sendprod.length;i++){
               			html+='<tr><td>'+sendprod[i].name+'</td><td>'+sendprod[i].format+'</td><td>'+sendprod[i].num+'</td></tr>'
               		}
               	}
               	$('.prod-table').find('tbody').append(html);    //发货打印结束
            };
            //打印
            var LODOP; //声明为全局变量
    		function myPreview4() {
    			LODOP = getLodop();
    			LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_全页排除按钮");
    			var strBodyStyle = "<style>" + $('.style1').html()
    					+ "</style>";
    			//strBodyStyle=strBodyStyle+"<style>"+$("#style1").html+"</style>";
    			var strBodyHtml = strBodyStyle + "<body>"
    					+ $('.right-part').html() + "</body>";
    			console.log(strBodyHtml)
    			LODOP.SET_PRINT_PAGESIZE(3, "20cm", "", "");
    			LODOP.ADD_PRINT_HTM("0.5cm", "0.5cm", "9cm",
    					"8.7cm", strBodyHtml);
    			LODOP.SET_PRINT_STYLEA(0, "AngleOfPageInside", 0);
    			LODOP.PRINT();
    			SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW", true);
    			SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW", true);
    		}
    	})
    </script>
</head>
<body>
<input type="hidden" id="serviceId" >
    <section>
        <ul class="partlist">
            <li>
                <span class="switch" onclick="toTh()">退货单</span>
                
            </li>
            <li>
                <span class="switch switched" onclick="toHh()">换货单</span>
                <div class="inforbox selectinforbox">
                	<form action="/aftetSale/queryList.html" method="post">
                    <div class="row">
                        <input type="text" class="personname" name="queryStr" value="${queryStr }" placeholder="订单号 ">
                        <input type="hidden" name="pageNo" id="pageNo" value="">
                        <input type="hidden" name="queryType" value="2">
                        <input type="submit" class="searchbtn search" value="搜索">
                    </div>
                    </form>
                    <div class="row">
                        <table class="refundlist">
                            <thead>
                            <tr>
                                <td style="min-width:100px;">订单号</td>
                                <td style="min-width:70px;">用户名</td>
                                <td style="min-width:70px;">白熊号</td>
                                <td style="min-width:160px;">时间</td>
                                <td style="min-width:80px;">联系方式</td>
                                <td style="max-width:300px;">收货地址</td>
                                <td style="min-width:70px;">产品名称</td>
                                <td style="min-width:70px;">规格</td>
                                <td style="min-width:70px;">数量</td>
                                <td style="min-width:70px;">状态</td>
                                <td style="min-width:100px;">换货原因</td>
                                <td style="min-width:100px;">操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${dataList }" var="order">
						   			<c:forEach  items="${order.itemList }" var="item" varStatus="itemStatus">
						   				<c:choose>
						   					<c:when test="${fn:length(order.itemList)==1 }">
						   						<tr>
						       						<td>${order.orderNumber }</td>
					                                <td>${order.userName }</td>
					                                <td>${order.bxNum }</td>
					                                <td><fm:formatDate value="${order.createDateTime}" pattern="yyyy/MM/dd"/></td>
					                                <td>${order.information.mobilePhone }</td>
					                                <td>
					                                <p class="reasontd" title="${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }">
					                                <c:if test="${order.information!=null }">
					                                ${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }
					                                </c:if>
					                                </p>
					                                </td>
					                                <td>${item.productTitle }</td>
					                                <td>${item.productModelFormatName }</td>
					                                <td>${item.num }</td>
					                                <td>
					                                 <c:if test="${order.status == 10 }">
					                                	审核
					                                </c:if>
					                                <c:if test="${order.status == 20 }">
					                                	白熊签收
					                                </c:if>
					                                <c:if test="${order.status == 31 }">
					                                	配货中
					                                </c:if>
					                                <c:if test="${order.status == 32 }">
					                                	拒绝
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	已发货
					                                </c:if>
					                                </td>
					                                <td><p class="reasontd" title="${order.description }">${order.description }</p></td>
					                                <td>
					                                <c:if test="${order.status == 10 }">
					                                	<a href="#" class="link" onclick="agree(${order.id})">同意</a>
					                                	<a href="#" class="link" onclick="refuse(${order.id})">拒绝</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 31 }">
					                                	<a href="#" class="link deliver-goods" data-id="${order.orderNumber}">发货</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 32 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 41 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 42 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 60 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                </td>
						       					</tr>
						   					</c:when>
						   				<c:otherwise>
						   					<c:if test="${itemStatus.first }">
						   						<tr>
						        					<td rowspan="${fn:length(order.itemList) }">${order.orderNumber }</td>
					                                <td rowspan="${fn:length(order.itemList) }">${order.userName }</td>
					                                <td rowspan="${fn:length(order.itemList) }">${order.bxNum }</td>
					                                <td rowspan="${fn:length(order.itemList) }"><fm:formatDate value="${order.createDateTime}" pattern="yyyy/MM/dd"/></td>
					                                <td rowspan="${fn:length(order.itemList) }">${order.information.mobilePhone }</td>
					                                <td rowspan="${fn:length(order.itemList) }">
					                                <p class="reasontd" title="${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }">
					                                <c:if test="${order.information!=null }">
					                                ${order.information.provinceName }${order.information.cityName }${order.information.districtName }${order.information.detailAddress }</td>
					                                </c:if>
					                                </p>
					                                </td>
					                                <td>${item.productTitle }</td>
					                                <td>${item.productModelFormatName }</td>
					                                <td>${item.num }</td>
					                                <td rowspan="${fn:length(order.itemList) }">
					                                <c:if test="${order.status == 10 }">
					                                	审核
					                                </c:if>
					                                <c:if test="${order.status == 20 }">
					                                	白熊签收
					                                </c:if>
					                                <c:if test="${order.status == 31 }">
					                                	配货中
					                                </c:if>
					                                <c:if test="${order.status == 32 }">
					                                	拒绝
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	已发货
					                                </c:if>
					                                <c:if test="${order.status == 60 }">
					                                	已退款
					                                </c:if>
					                                </td>
					                                <td rowspan="${fn:length(order.itemList) }"><p class="reasontd" title="${order.description }">${order.description }</p></td>
					                                <td rowspan="${fn:length(order.itemList) }">
					                                <c:if test="${order.status == 10 }">
					                                	<a href="#" class="link" onclick="agree(${order.id})">同意</a>
					                                	<a href="#" class="link" onclick="refuse(${order.id})">拒绝</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 31 }">
					                                	<a href="#" class="link deliver-goods" data-id="${order.orderNumber}">发货</a>
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 32 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 41 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 42 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 50 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                <c:if test="${order.status == 60 }">
					                                	<a href="/aftetSale/toOrderView.html?id=${order.id }&queryType=2" class="link">详情</a>
					                                </c:if>
					                                </td>
							        			</tr>
						   					</c:if>
						   					<c:if test="${itemStatus.index>0 }">
						   						<tr>
							        				<td>${item.productTitle }</td>
					                                <td>${item.productModelFormatName }</td>
					                                <td>${item.num }</td>
							        			</tr>
						   					</c:if>
						   				</c:otherwise>
						   			</c:choose>
						   		</c:forEach>
						   	</c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <jsp:include page="../include/pagesService.jsp"></jsp:include>
                </div>
            </li><!--
            <li>
                <span class="switch" onclick="toWx()">维修单</span>
            </li>-->
        </ul>
    </section>
<!--收件弹窗-->
<!-- 收件 -->
<div class="pop sjWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">已收件<i class="closeicon"></i> </h3>
        <div class="row">
            <p class="warningtext">确认收件后用户会收到收件记录，是否确认</p>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="sjSub()" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<!-- 关闭 -->
<div class="pop closeWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">关闭<i class="closeicon"></i> </h3>
        <div class="row">
            <p class="warningtext">确认关闭后用户会收到关闭记录，是否确认</p>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="closeSub()" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<!-- 同意 -->
<div class="pop agreeWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">同意<i class="closeicon"></i> </h3>
        <div class="row">
            <p class="warningtext">确认后用户会收到同意记录，是否确认</p>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="agreeSub()" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<!-- 拒绝 -->
<div class="pop refuseWindow">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">拒绝<i class="closeicon"></i> </h3>
        <div class="row">
            <textarea class="refusetext" id="refuseMemo"></textarea>
        </div>
        <div class="row">
            <input type="button" value="确认" onclick="refuseSub()" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<!--退货单弹窗结束-->
<div class="pop">
<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">已收件<i class="closeicon"></i> </h3>
        <div class="row">
            <span class="addtypename">快递公司:</span>
            <span class="selectinput sendselect">
                <span class="selectvalue">奇思妙想</span>
                <i class="arrow arrowright"></i>
                <ul class="option">
                    <li>上发生地</li>
                    <li>发的发鬼</li>
                    <li>个地方官</li>
                </ul>
            </span>
        </div>
        <div class="row">
            <span class="addtypename">运单号:</span>
            <input type="text" value="" placeholder="运单号" class="expnumber">
        </div>
        <div class="row">
            <input type="button" value="拒绝" class="confirm">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<!-- 发货 -->
<div class="pop sendpop" style="display: none;">
    <div class="popbg"></div>
    <div class="layel" style="width: 600px;height: 500px;">
        <h3 class="poptitle">发货<i class="closeicon"></i> </h3>
        <div class="row pop-prod-list" style="padding:0 -10px;max-height: 200px;overflow-y: auto;">
            <div class="col-lg-6">
                <div class="pop-infor-box select">
                    <img src="../images/product.jpg" class="pop-prod-img">
                    <div class="information">
                        <p class="dark specifications">光魔方MX5</p>
                        <p class="light specifications">1500瓦  黑色</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="pop-infor-box">
                    <img src="../images/product.jpg" class="pop-prod-img">
                    <div class="information">
                        <p class="dark specifications">光魔方MX5</p>
                        <p class="light specifications">1500瓦  黑色</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row tl" style="margin-top:20px;">
            <input type="text" class="code-text" placeholder="条码扫描" style="margin-left:100px;">
        </div>
        <div class="row">
            <span class="selectinput">
                <span class="selectvalue" id="expressId" data-id="1">德邦</span>
                <i class="arrow arrowright"></i>
                <ul class="option">
                	<li data-id="1">德邦</li>
                    <li data-id="2">圆通</li>
                    <li data-id="3">中通</li>
                </ul>
            </span>
            <input type="text" placeholder="快递单号" class="order-number" id="expressId-num"/>
        </div>
        <div class="row tc">
            <input type="button" value="确认发货" class="confirm-send">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<div class="pop pop4" style="display: none;">
	<div class="popbg"></div>
    <div class="layel" style="height:200px;">
        <h3 class="poptitle">是否确认发货?<i class="closeicon"></i> </h3>
        <div class="row tc">
            <input type="button" value="确定" class="sure-send">
            <input type="button" value="取消" class="cancel cancel-send">
        </div>
    </div>
</div>
<div class="pop pop6" style="display:none">
	<div class="popbg"></div>
	<div class="layel">
		<div class="right-part" id="page1" style="position:absolute;left=0;top:0;z-index:9999;background:#fff;">
			<div class="print-bigbox">
				<div class="print-box">
					<p class="print-header" style="height:55px;">
						<img src="../../images/logo.png" class="print-logo">
						<span class="print-title">熊爸爸，为温暖而生！</span>
					</p>
					<div class="print-product" style="height:80px;overflow:hidden;width:100%;">
						<table class="prod-table">
					    <thead>
					    	<tr>
					    		<td width="120px">产品名称</td>
					    		<td width="120px">产品规格</td>
					    		<td width="60px">产品数量</td>
					    	</tr>	
					    </thead>
					    <tbody>
					    </tbody>
					    </table>
					</div>
						<div class="print-code">
							<p class="prod-productCode">商品编码：</p>
							<img src="" class="prod-codeurl" style="display:block;margin:0 auto;width:100%"/>
						</div>
						<div class="print-footer">
							<p class="company-name">杭州白熊科技有限公司</p>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="/plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="/css/order-manage.css" rel="stylesheet" type="text/css">
    <script src="/plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../../js/public.js" type="text/javascript" ></script>
    <script src="/js/aftersalemanage.js" type="text/javascript" ></script>
    <script src="/plug/adddatetime.js" type="text/javascript" ></script>
    <script src="../../../../plug/jQuery/LodopFuncs.js" type="text/javascript"></script>
    <title>订单管理</title>
    <style class="style1">
.code-input {
	width: 300px;
	height: 40px;
	border: 1px solid #dcdcdc;
	margin-top: 30px;
	margin-left: 10px;
}

.print-bigbox {
	width: 350px;
	height: 350px;
	display: inline-block;
}

.print-logo {
	float: left;
}

.print-box {
	border: 2px solid black;
	width: 348px;
	height: 340px;
	margin: 5px;
	margin-left:-5px;
	padding: 7px;
}

.header {
	padding-bottom: 5px;
	border-bottom: 1px solid black;
	overflow: hidden;
}

.print-title {
	font-weight: bold;
	font-size: 16px;
	float: right;
	vertical-align: middle;
	line-height: 75px;
	float: right;
}

.print-product {
	margin-top: 20px;
}

.print-product p, .text, .textname {
	vertical-align: middle;
	display: inline-block;
}

print-product p {
	margin-left: 10px;
}

.prod-name {
	float: left;
	display: inline-block;
}

.text {
	width: 240px;
	float: left;
}

.textname {
	vertical-align: top;
}

.print-code {
	margin-top: 15px;
	padding-bottom: 20px;
	border-bottom: 1px solid #000;
}

.two-dimension {
	width: 330px;
	height: 66px;
}

.prod-table {
	text-align: center;
	width: 300px;
}
.prod-table tr td{
	height:20px;
	padding:0 5px;
	font-size:12px;
}
.line {
	height: 0px;
	width: 90px;
	display: inline-block;
	margin-bottom: 5px;
	float: left;
}

.company-name {
	display: inline-block;
	width: 160px;
	background: #fff;
	padding: 0 10px;
	margin: 0 auto;
	font-size:12px;
}

.print-footer {
	line-height: 35px;
	height: 35px;
	margin-top: -17px;
	text-align: center;
}
.fail-remind{
	font-size:18px;
	line-height:20px;
	width:100%;
	text-align:center;
	position:absolute;
	bottom:10px;
	left:0;
}
.only-code{
	position:relative;
}
</style>
</head>
<body>
    <section>
        <div class="content" style="margin-top: 20px;">
        	<form action="/order/list.html" method="post">
	          	<input type="text" name="keywords" class="packing-code" value="${keywords }" placeholder="订单号">
	            <input type="text" name="start" value="${start }"  class="strat-time datetimepicker startdata" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <span>至</span>
	           	<input type="text" name="end" value="${ end }"  class="end time datetimepicker startdata" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	            <input type="hidden" value="${status}" id="status" name="status">
	            <input type="hidden" name="pageNo" id="pageNo" value="">
	            <input type="submit" class="search" value="搜索">        	
        	</form>
        </div>	
        <div class="content" style="margin-top: 20px;">
        	<form action="/order/list.html" method="post" id="uniqueCodeForm">
	          	<input type="text" size="44" name="uniqueCode" class="packing-code" value="${ uniqueCode }" placeholder="扫描唯一码搜索私人定制订单">
        	</form>
        </div>	
        <ul class="partlist">
            <li>
                <a href="/order/list.html"><span class="switch <c:if test="${status==null}">switched</c:if>">全部</span></a>
                	<c:if test="${status==null}">
                		<div class="inforbox selectinforbox">
                			<jsp:include page="order_list_status.jsp"></jsp:include>
                			<jsp:include page="../include/pagesService.jsp"></jsp:include>
                		</div>
                	</c:if>
            </li>
            <li>
                <a href="/order/list.html?status=20"><span class="switch <c:if test="${status==20}">switched</c:if>" >待发货</span></a>
                	<c:if test="${status==20 }">
                		<div class="inforbox selectinforbox">
                			<jsp:include page="order_list_status.jsp"></jsp:include>
                			<jsp:include page="../include/pagesService.jsp"></jsp:include>
                		</div>
                	</c:if>
            </li>
            <li>
                <a href="/order/list.html?status=30"><span class="switch <c:if test="${status==30}">switched</c:if>" >配货中</span></a>
                	<c:if test="${status==30 }">
                		<div class="inforbox selectinforbox">
                			<jsp:include page="order_list_status.jsp"></jsp:include>
                			<jsp:include page="../include/pagesService.jsp"></jsp:include>
                		</div>
                	</c:if>
            </li>
            <li>
                <a href="/order/list.html?status=40"><span class="switch <c:if test="${status==40}">switched</c:if>" >已发货</span></a>
                	<c:if test="${status==40 }">
                		<div class="inforbox selectinforbox">
                			<jsp:include page="order_list_status.jsp"></jsp:include>
                			<jsp:include page="../include/pagesService.jsp"></jsp:include>
                		</div>
                	</c:if>
            </li>
            <li>
                <a href="/order/list.html?status=50"><span class="switch <c:if test="${status==50}">switched</c:if>" >已签收</span></a>
                	<c:if test="${status==50 }">
                		<div class="inforbox selectinforbox">
                			<jsp:include page="order_list_status.jsp"></jsp:include>
                			<jsp:include page="../include/pagesService.jsp"></jsp:include>
                		</div>
                	</c:if>
            </li>
        </ul>
    </section>
</div>
<div class="pop confirmpop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">确认配货<i class="closeicon"></i> </h3>
        <img src="../images/question.png" class="question">
        <p class="prompt
">确认配货后用户将收到配货中的反馈信息，是否确认？</p>
        <div class="row tc">
            <a href="" class="distribution-link"><input type="button" value="确定" class="distribution-ok"></a>
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<div class="pop sendpop" style="display: none;">
    <div class="popbg"></div>
    <div class="layel" style="width: 600px;height: 500px;">
        <h3 class="poptitle">发货<i class="closeicon"></i> </h3>
        <div class="row pop-prod-list" style="padding:0 -10px;height: 200px;overflow: auto;">
            <div class="col-lg-6 ">
                <div class="pop-infor-box deliver">
                    <img src="../images/product.jpg" class="pop-prod-img">
                    <div class="information">
                        <p class="dark specifications">光魔方MX5</p>
                        <p class="light specifications">1500瓦  黑色</p>
                    </div>
                    <img src="../images/fahuo.png" class="fahuo"/>
                </div>
            </div>
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
        <div class="row" style="margin-top:20px;">
            <input type="text" class="code-text" placeholder="条码扫描" style="margin-left:100px;">
        </div>
        <div class="row">
            <span class="selectinput">
                <span class="selectvalue" id="expressId" data-id="2">圆通</span>
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
							<img src="" class="prod-codeurl" style="display:block;margin:0 auto;"/>
						</div>
						<div class="print-footer">
							<p class="company-name">杭州白熊科技有限公司</p>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(document).on('click','.distribution',function(){
			var order_num=$(this).attr('data-id');
			$('.confirmpop').show().find('.distribution-link').attr('data-id',order_num);
		});
		$(document).on('click','.distribution-link',function(){
			var order_num=$(this).attr('data-id');
			$.ajax({
                type: "POST",
                url: '/order/update/status.html?orderNumber='+order_num+'&orderStatus=30',
                dataType: "json",
                success: function (data) {
                	//$('.sendpop').hide();
                	if(data.code ==1){
                		alertLayel("配货成功");
                		var status = $('#status').val();
                		var url="";
                		if(status == null){
                			url = "/order/list.html";
                		}else{
                			url = "/order/list.html?status="+status;
                		}
                		location.href=url;
                	}else{
                		alertLayel(data.message);
                	}
                }
        	});
			$('.confirmpop').hide();
		})
		//扫描查询
		$('.packing-code').change(function(){
			$('#uniqueCodeForm').submit();
		})
		$(document).on('click','.deliver-goods',function(){
            var data_id=$(this).attr('data-id');
            var html='';
            hardwares=[];
            orderItems=[];
            for(i=0;i<data_list.length;i++){
                if(data_id==data_list[i].orderNumber){
                    for(j=0;j<data_list[i].orderItems.length;j++){
//                        if(){ //已发货
//                            html+='<div class="col-lg-6 "> <div class="pop-infor-box deliver"  data-value="'+data_list[i].orderItems[j].productModelFormatName+'"> <img src="'+data_list[i].orderItems[j].pics[0].url+'" class="pop-prod-img"> <div class="information"> <p class="dark specifications">'+data_list[i].orderItems[j].productTitle+'</p> <p class="light specifications">'+data_list[i].orderItems[j].productModelFormatName+'</p> </div> <img src="../images/fahuo.png" class="fahuo"/> </div> </div>';
//                        }else{
//
//                        }
console.log(data_list[i].orderItems[j].sendedNum)
                        		for(m=0;m<data_list[i].orderItems[j].sendedNum;m++){
                        			console.log(m);
                        			html+='<div class="col-lg-6 "> <div class="deliver"  data-value="'+data_list[i].orderItems[j].categoryModelFormatNumber+'" data-id="'+data_list[i].orderItems[j].id+'" data-formatid="'+data_list[i].id+'"> <img src="'+data_list[i].orderItems[j].pics[0].url+'" class="pop-prod-img"> <div class="information"> <p class="dark specifications">'+data_list[i].orderItems[j].productTitle+'</p> <p class="light specifications">'+data_list[i].orderItems[j].productModelFormatName+'</p> </div> <img src="../images/fahuo.png" class="fahuo"/> </div> </div>';
                        		}
                        			for(L=0;L<data_list[i].orderItems[j].num-data_list[i].orderItems[j].sendedNum;L++){
                        			html+='<div class="col-lg-6 "> <div class="pop-infor-box" data-value="'+data_list[i].orderItems[j].categoryModelFormatNumber+'" data-id="'+data_list[i].orderItems[j].id+'" data-formatid="'+data_list[i].id+'"> <img src="'+data_list[i].orderItems[j].pics[0].url+'" class="pop-prod-img"> <div class="information"> <p class="dark specifications">'+data_list[i].orderItems[j].productTitle+'</p> <p class="light specifications">'+data_list[i].orderItems[j].productModelFormatName+'</p> </div> </div> </div>';
                        	}
                            
                        

                    }
                    $('.pop-prod-list').html(html);
                }
            }
			$('.sendpop').show();
		});
        var json=${json};
        data_list=json.result.orders;
        /*确认配货*/
        var hardwares=[];
        var orderItems=[];
        $('.code-text').change(function(){
            var code=$(this).val();
            $.ajax({
                type: "GET",
                url: "/order/hardware.html",
                data:{uniqueCode: code},
                dataType: "json",
                success: function (data) {
                	console.log(data);
                	var Json=data.result.hardware;
                	var FormatNumber=Json.categoryModelFormat.categoryModelFormatNumber;
	                console.log(FormatNumber)
					$(this).hide().next('.deliver-goods').show();
	                for(n=0;n<$('.pop-infor-box').length;n++){
	                	var prod_code=$('.pop-infor-box').eq(n).attr('data-value');
		                console.log(111)
		                console.log(prod_code)
		                if(prod_code==FormatNumber){
		                	var scaned=false;
		                	for(m=0;m<hardwares.length;m++){
		                		if(Json.id==hardwares[m]){
		                			scaned=true;
		                			return false;
		                		}
		                	}
		                	if(scaned){
		                		return false;
		                	}
		                    hardwares.push(Json.id);
		                    orderItems.push($('.pop-infor-box').eq(n).attr('data-id'));
		                    $('.pop-infor-box').eq(n).addClass('select').removeClass('pop-infor-box');
			                break;
		                }
	                }
                }
        	})
            $(this).attr('value', "");
        	
        })
        //确认发货
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
        	if(orderItems == null || orderItems ==undefined || orderItems == ''){
        		alertLayel("订单详情未选中");
        		return ;
        	}
        	$('.pop4').show();
        	$('.sendpop').find('.layel').css('opacity',0);
        	console.log(expressId,mailNum,hardwares,orderItems)
        })
        $('.sure-send').on('click',function(){
        	$('.pop').hide();
        	$.ajax({
                type: "POST",
                url: "/order/sendGoods.html",
                data:{expressId:expressId,expressNumber:mailNum,hardwares:hardwares.join(','),orderItems:orderItems.join(',')},
                dataType: "json",
                async: false, 
                success: function (data) {
                	//$('.sendpop').hide();
                	//{"code":0,"message":"","result":{"number":"R1077691446703649175"}}
                	console.log(data);
                	if(data.code ==0){
                		$('.pop').hide();
                		alertLayel("发货成功");
                		var status = $('#status').val();
                		var url="";
                		if(status == null){
                			url = "/order/list.html";
                		}else{
                			url = "/order/list.html?status="+status;
                		}
                		//发货打印
                		$('.prod-codeurl').attr('src',data.result.orderCode);
                		$('.prod-productCode').text("订单号："+data.result.number);
                		print();
                		//$('.pop6').show();
                		myPreview4();
                		location.href=url;
                	}else{
                		alertLayel(data.message);
                	}
                }
        	});
        });
        $('.printOrder').on('click',function(){
        	 var orderNumber=$(this).attr('data-id');
        	 //$('.pop6').show();
        	$.ajax({
                type: "POST",
                url: "/order/printOrder.html",
                data:{orderNumber:orderNumber},
                dataType: "json",
                async: false, 
                success: function (data) {
                	//{"code":0,"message":"","result":{"number":"R1077691446703649175","orderCode":"../../../imgs.png"}}
                	console.log(data);
                	if(data.code ==0){
                		//成功
                		var orders=data.result.order.orderItems;
                		var html='';
                		for(i=0;i<orders.length;i++){
                			html+='<tr><td>'+orders[i].productTitle+'</td><td>'+orders[i].productModelFormatName+'</td><td>'+orders[i].num+'</td></tr>'
                		}
                		$('.prod-table').find('tbody').append(html);    //发货打印结束
                		$('.prod-productCode').text("订单号："+data.result.order.orderNumber);
                		$('.prod-codeurl').attr('src',data.result.orderCode);
                		myPreview4();
                		//$('.pop').hide();
                	}else{
                		alertLayel(data.message);
                	}
                }
        	});
        });
        $('.cancel-send').on('click',function(){
        	$('.sendpop').find('.layel').css('opacity',1);
        })
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
    }
</script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link href="../../../../plug/bootstrap/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<link href="../../../../css/pubstyle.css" rel="stylesheet"
	type="text/css">
<link href="../../../../css/scanpackage-packagescan.css"
	rel="stylesheet" type="text/css">
<script src="../../../../plug/jQuery/jquery-1.8.3.min.js"
	type="text/javascript"></script>
<script src="../../../../js/public.js" type="text/javascript"></script>
<script src="../../../../plug/jQuery/LodopFuncs.js"
	type="text/javascript"></script>
<title>扫描生成防伪码</title>
</head>
<style class="style1">
.code-input {
	width: 300px;
	height: 40px;
	border: 1px solid #dcdcdc;
	margin-top: 30px;
	margin-left: 10px;
}

.print-bigbox {
	width: 90px;
	height: 100px;
	display: inline-block;
}
.barcode{
	width:76px;
	height:76px;
	margin:7px;
	margin-top:-20px;
}
.company-name{
	font-size:8px;
	text-align:center;
	font-weight:bold;
	font-family:"微软雅黑";
	background:#000;
	color:#fff;
	width: 80px;
	margin-left:10px;
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
<script language="javascript" type="text/javascript">
	$(document).ready(function() {
		$('.code-input').focus().change(function() {
			var code = $(this).val();
			console.log(code)
			//doPrint('打印预览...')
			$.ajax({
				type : "GET",
				url : "/oem/custommade/scanqrcode_custommade_ajax.html?orderNumber=${orderNumber}&isSmart=${isSmart}",
				data : {
					scanMacNumber : code
				},
				dataType : "json",
				success : function(data) {
					console.dir(data);
					var jsonData = data;
					var html='';
	                var mydate = new Date().toLocaleString( ); //获取日期与时间
	                if(jsonData.code==0){
	                    console.log(mydate)
	                    var x=$('.number').length+1;
	                    html='<li><span class="number">'+x+'</span> <span class="date">'+mydate+'</span> <span class="prod">'+jsonData.qrcode+'</span> <span class="success">扫描成功</span> </li>'
	                    $('.scan-list').find('ul').append(html);
	                    $('.barcode').attr('src',jsonData.codeUrl);
	                    //$('.only-code').html("扫描码:"+jsonData.qrcode);
	                    $('.only-code').css({opacity:'0'});
	                    setTimeout(function() {
	                        $('.only-code').css({fontSize:'32px',opacity:'1'}).html("扫描码:"+jsonData.qrcode);
	                    },300)
	                    myPreview4();
	                    //num();
	                }
	                if(jsonData.code==-1){
	                    //alertLayel("扫码错误");
	                    var x=$('.number').length+1;
	                    html='<li><span class="number">'+x+'</span> <span class="date">'+mydate+'</span> <span class="prod">'+code+'</span><span class="fail">扫描失败</span></li>';
	                    $('.scan-list').find('ul').append(html);											             
	                    $('.only-code').html("扫描码"+code+":扫描失败"+"<br><span class='fail-remind'>"+jsonData.msg+"</span>");
	                    //num();
	                }
				}
			});
			$(this).attr('value', "");
		}).blur(function() {
		$('.code-input').focus();
		});
		var LODOP; //声明为全局变量
		function myPreview4() {
			LODOP = getLodop();
			LODOP.PRINT_INIT("");
			//LODOP.PRINT_INITA(0,0,800,1600,"打印控件功能演示_Lodop功能_打印条码");
			var strBodyStyle = "<style>" + $('.style1').html()+ "</style>";
			strBodyStyle=strBodyStyle+"<style>"+$("#style1").html+"</style>";
			var strBodyHtml = "<!DOCTYPE html>"+strBodyStyle + "<body>"+ $('.print-bigbox').html() + "</body>";
			//console.log(strBodyHtml)
			LODOP.SET_PRINT_PAGESIZE(3, "20mm", "1mm", "");
			//ADD_PRINT_BARCODE('5','5','150','150',QRCode,DF32F5WA48T93FGA);
			LODOP.ADD_PRINT_HTM("0mm", "0mm", "20mm","20mm", strBodyHtml);
			//LODOP.SET_PRINT_STYLEA(0,"Angle",90);
			LODOP.SET_PRINT_STYLEA(0, "AngleOfPageInside", 0);
			LODOP.PRINT();
			//LODOP.PRINT_DESIGN;
			//LODOP.PREVIEW();
			//SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW", true);
			//SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW", true);
		};
		//myPreview4();
	})
</script>
<body>
		<section>
			<div class="pathbox">
				<span class="light">选择规格</span>> <span class="darker">扫描生成防伪码</span> <input
					type="button" class="scan-record" value="扫描记录" onClick="window.location.href='/oem/scanpackage/scanhistory.html'">
			</div>
			<div class="content">
				<div class="row">
					<span class="prod-attr">工厂：${ erpOemUserRelation.oem.name }</span>
					<span class="prod-attr">产品名称：${ orderItem.productTitle }</span>
					<span class="prod-attr">产品规格：${ orderItem.productModelFormatName }</span>
					<span class="prod-attr">批次：000</span>
				</div>
				<div class="left-part">
					<span class="imagebox"><img	src="${ orderItem.pics[0].url }"></span> 
					<span class="only-code">扫描码:${qrcode}</span> 
					<input type="text" class="code-input">
				</div>
				<div class="right-part" id="page1" style="width:150px;">
					<div class="print-bigbox">
						<p class="company-name">熊爸爸防伪</p>
                        <img src="" class="barcode"">
					</div>
					 <!-- 扫描纪录-->
	                <div class="scan-list">
	                    <ul>
	
	                    </ul>
                	</div>
                	<div>
                		非智能类型，请扫描下面条形码：
	                    <img src="${ notSmartPic }">
                	</div>
				</div>
			</div>
		</section>
</body>
</html>
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
<title>扫描包装</title>
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
	padding: 7px;
}

.header {
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


.print-product p, .text, .textname {
	vertical-align: middle;
	overflow:hidden;
	width:100%;
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

table {
	margin-top: 45px;
	text-align: center;
	width: 300px;
	margin-left: 20px;
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
	width: 180px;
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
<script language="javascript" type="text/javascript">
	$(document)
			.ready(
					function() {
						$('.code-input')
								.focus()
								.change(
										function() {
											var code = $(this).val();
											console.log(code)
											if(code.length==0||code.indexOf('=')==-1){
												return;
											}
											code=code.substring(code.indexOf('=')+1,code.length);
											//doPrint('打印预览...')
											$
													.ajax({
														type : "GET",
														url : "/oem/custommade/scanuniquecodeajax.html?orderNumber=${orderNumber}",
														data : {
															scanCode : code
														},
														dataType : "json",
														success : function(data) {
															console.dir(data);
															if(data.code==0){
																$('.prod-name,.prod-name-top').text("产品名称："+data.productName);
																$('.prod-format,.prod-format-top').text("产品规格："+data.format);
																$('.prod-batch-top').text("批次:"+data.batch);
																$('.prod-productCode').text("商品编码："+data.productCode);
																$('.only-code').text("唯一码："+data.uniqueCode);
																$('.prod-codeurl').attr('src',data.codeUrl);
																$('.formatPicUrl').attr('src',data.formatPicUrl);
																myPreview4();
															}
															if(data.code==-1){											                   								             
											                    $('.only-code').html("扫描码"+code+":扫描失败"+"<br><span class='fail-remind'>"+data.msg+"</span>");
											                    //num();
											                }
														}
													})											
											$(this).attr('value', "");
										}).blur(function() {
									$('.code-input').focus();
								});
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
						;
						//myPreview4();
						//console.log($('.navbox').html())
					})
	//console.log($('header').html)
	//    function doPrint(how) {
	//
	//        //打印文档对象
	//        var myDoc = {
	//            documents: document,    // 打印页面(div)们在本文档中
	//            copyrights: '杰创软件拥有版权  www.jatools.com'         // 版权声明必须
	//        };
	//
	//        // 调用打印方法
	//        if (how == '打印预览...')
	//            jatoolsPrinter.printPreview(myDoc);   // 打印预览
	//
	//        else if (how == '打印...')
	//            jatoolsPrinter.print(myDoc, true);   // 打印前弹出打印设置对话框
	//
	//        else
	//            jatoolsPrinter.print(myDoc, false);       // 不弹出对话框打印
	//    }
</script>
<body>
		<section>
			<div class="pathbox">
				<span class="light">选择规格</span>> <span class="darker">扫描生成唯一码</span>
			</div>
			<div class="content">
				<div class="row">
					<span class="prod-attr prod-name-top">产品名称：</span></span>
					<span class="prod-attr prod-format-top">产品规格：</span></span>
					<span class="prod-attr prod-batch-top">批次：</span>
				</div>
				<div class="left-part">
					<span class="imagebox"><img src="" class="formatPicUrl"></span> <span
						class="only-code">唯一码:</span> <input type="text"
						class="code-input">
				</div>
				<div class="right-part" id="page1">
					<div class="print-bigbox">
						<div class="print-box">
							<p class="print-header" style="height:55px;">
								<img src="../../images/logo.png" class="print-logo">
								<span class="print-title">熊爸爸，为温暖而生！</span>
							</p>
							<div class="print-product">
								<p>
									<span class="prod-name"> 产品名称：</span>
								</p>
								<p>
									<span class="prod-format">产品规格：</span>
								</p>
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
		</section>
</body>
</html>
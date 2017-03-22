<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../../../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/scan-transport.css" rel="stylesheet" type="text/css">
    <script src="../../../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../../../js/public.js" type="text/javascript" ></script>
	<script src="../../../plug/jQuery/LodopFuncs.js" type="text/javascript"></script>
    <title>扫描运输</title>
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
}
.print-product p{
	width:100%;
}
.print-product p, .text, .textname {
	vertical-align: middle;
	display: inline-block;
	font-size: 16px;
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
</style>
<body>
    <section>
    	<div class="row">
            <span class="productname"><span class="prod-name" style="float:none;">产品名称：</span></span>
            <span class="specifications"><span class="format-name">产品规格:</span></span>
            <span class="scan-number">已扫描数量：</span>
            <span class="number"></span>
            <input type="button" class="scan-record" value="扫描记录" onclick="toList()"/>
            <input type="button" class="transport-code" value="生成运输码"/>
        </div>
        <div class="row">
            <input type="text" class="code-input" >
        </div>
		<div class="bigbox">
        </div>
        
        <div class="right-part" id="page1" style="display:none;">
			<div class="print-bigbox">
				<div class="print-box">
					<p class="print-header"style="height:55px;">
						<img src="../../../../images/logo.png" class="print-logo">
						<span class="print-title">熊爸爸，为温暖而生！</span>
					</p>
					<div class="print-product">
						<p>
							<span class="prod-name-foot"> 产品名称：</span>
						</p>
						<p>
							<span class="prod-format-foot">产品规格：</span>
						</p>
						<p>
							<span class="prod-num-foot">产品数量：</span>
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
	</section>
<div class="pop pop1" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">产品规格不同<i class="closeicon"></i> </h3>
        <p class="text">对不起不允许一个运输码里放不同的产品规格，<br>请确认产品是否满足要求！</p>
        <div class="row tc">
            <input type="button" value="关闭" class="cancel">
        </div>
    </div>
</div>
<div class="pop pop2" style="display: none;">
    <div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">删除商品<i class="closeicon"></i> </h3>
        <p class="text">删除商品后，商品将从该运输列表中剔除，是否删除？</p>
        <div class="row tc">
            <input type="button" value="确定" class="determine">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<div class="pop pop3" style="display: none;">
    <div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">生成运输码<i class="closeicon"></i> </h3>
        <p class="text">生成运输码后，运输码对应的产品不能更换，<br>是否确认生成？</p>
        <div class="row tc">
            <input type="button" value="生成" class="generation">
            <input type="button" value="取消" class="cancel">
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {
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
			//SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW", true);
			//SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW", true);
			return;
		}
		;
		//myPreview4();
    	$('.cancel').on('click',function(){
    		console.log(111);
    		$(this).parent().parent().parent('.pop').hide();
            $('.code-input').focus();
    	});
        var productCode="";
        var tids = [];
        $('.code-input').focus().change(function () {
             var code = $(this).val();
	         $.ajax({
	            type: "GET",
	            url: "/oem/transport/out.html",
	            data: {code: code},
	            dataType: "json",
	            async: false,
	            success: function (data) {
	                console.dir(data);
	                var JsonData = data;
		            var html = '';
		            var mydate = new Date().toLocaleString(); //获取日期与时间
		            if (JsonData.code == 1) {  //扫描运输码
		                html = '<div class="medium"> <div class="smallbox"> <span class="serial-number"></span> <div class="box" id="' + JsonData.result.transport.id + '"> <span class="code">' + JsonData.result.transport.uniqueCode + '</span> <p class="marker-anomaly" data-id="' + JsonData.result.transport.id + '">删除</p> </div> </div> </div>';
		                /* if($('.bigbox').find('.medium').length==0){
		                    $('.prod-name').text("产品名称:"+JsonData.result.transport.productName);
		                    $('.format-name').text("规格名称:"+JsonData.result.transport.formatName);
		                    productCode=JsonData.result.transport.productCode;
		                    var productName=$('.prod-name').text();
		                    var formatName=$('.format-name').text();
		                    console.dir("3:");
		                }else {
		                    for (n = 0; n < $('.code').length; n++) {
		                        if (code == $('.code').eq(n).text()) {
		                        	console.dir("1:"+n);
		                        	$('.code-input').attr('value','');
		                            return false;
		                        }
		                    }
		                } 
		                if(JsonData.result.transport.productCode==productCode){
		                	console.dir("2:");
		                    $('.bigbox').append(html);
		                    serial();
		                }else{
		                    $('.pop1').show();
		                } */
		                //说明数组中没有结果集
		                if(tids.length == 0){
		                	$('.prod-name').text("产品名称:"+JsonData.result.transport.productName);
		                    $('.format-name').text("规格名称:"+JsonData.result.transport.formatName);
		                    productCode=JsonData.result.transport.productCode;//设置一个必须是同一批次的产品进行添加的比较值
		                    tids.push(JsonData.result.transport.id);//设置添加集合
		                    $('.bigbox').append(html);//将商品添加到页面
		                    serial();//设置顺序
		                }else{
		                	if(JsonData.result.transport.productCode!=productCode){
			                    $('.pop1').show();
		                	}else{
		                		console.log("add11:"+tids.indexOf(JsonData.result.transport.id)); 
			                	if(tids.indexOf(JsonData.result.transport.id)<0){
			                		//说明是没有相同的硬件存在里面
			                		$('.bigbox').append(html);
					                serial();
					                tids.push(JsonData.result.transport.id);//设置添加集合
			                	}
		                	}
		                }
		            }
		            console.log("add:"+tids); 
		            $('.code-input').attr('value','');
	        	}
	        });
	    }); 
        //序号
        function serial(){
            $('.medium').each(function(){
                var x=$(this).index();
                $(this).find('.serial-number').text(x+1);
                $('.number').text(x+1);
            })
        }
        //提交数据
        var data=[];
        $('.transport-code').on('click',function(){
            $('.medium').each(function(){
                var id=$(this).find('.box').attr('id');
                data.push(id);
            });
            if(tids.length>0){
            	$('.pop3').show();
            }else{
            	alertLayel("没有扫描任何产品!");
            }
        });
        $(document).on('click','.generation',function(){
            console.log(data);
            var strn=data.join(",");
            console.log(strn);
            $.ajax({
                url: "/oem/transport/put/save.html?normalPros="+strn,
                type: "POST",
                dataType: "json",
                success: function (data) {
                	if (data.code == 0) {   //添加完成
                		console.log(data);
	                	$('.prod-codeurl').attr('src',data.result.transportCode);
	                	$('.prod-name-foot').text($('.prod-name').text());
	                	$('.prod-format-foot').text($('.format-name').text());
	                	$('.prod-num-foot').text("产品数量:"+$('.number').text());
                		myPreview4();
                		window.location.href="/oem/transport/put.html";
                    }else{
                    	alertLayel(data.message);
                    }	
                }
            })
        })
        //删除
        var delId = 0;
        $(document).on('click','.marker-anomaly',function(){
            var n=$(this).parent().parent().parent('.medium').find('.serial-number').text();
            console.log(n)
        	delId = $(this).attr("data-id");
            $('.pop2').show().attr('data-id',n);
        });
        $('.determine').on('click',function(){
            var n=$(this).parent().parent().parent().attr('data-id');
            var num=$('.number').text();
            $('.number').text(num-1);
            $('.medium').eq(n-1).remove();
            serial();
            $('.pop2').hide();
            $('.code-input').focus();
            var j=0;
            var newDelAfter = [];
            for(var i=0;i<tids.length;i++){
	            if(tids[i]!=delId){
	               newDelAfter[j]=tids[i];
	               j=j+1;
	            }
            }
            tids=newDelAfter;
        })
    });
    function toList(){
    	window.location.href="/oem/transport/queryList.html";
    }
</script>
</body>
</html>

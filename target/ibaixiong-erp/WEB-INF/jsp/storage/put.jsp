<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="../plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="../../../css/storage-add.css" rel="stylesheet" type="text/css">
    <script src="../plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="../js/public.js" type="text/javascript" ></script>
    <title>入库</title>
</head>
<style>
.box-on{
	float: right;
	padding-left: 5px;
	padding-right: 5px;
	padding-top: 5px;
	padding-bottom: 5px;
	margin-top: 15px;
	margin-right: 15px;
	height: 50px;
	width: 100%;
	text-align: center;
	background: #fff;
	border:1px solid #ff6200;
}
</style>
<body>
     <section>
    	<div class="product">
            <p class="product-number">产品数量：<span class="orangetext amount"></span></p>
            <p class="transport-code">运 输 码：<span class="trans-code"> </span></p>
            <p class="product-name">产品名称：<span class="prod-name"></span></p>
            <p class="Product-specifications">
            	产品规格：<span class="standard"> </span>
            	<input type="button" class="sure-storage" value="确认入库"/>
                <input type="button" class="abnormal" value="入库异常/记录"/>
            </p>
			<img src="" class="prod-img">
        </div>
		<div class="row">
			<input type="text" class="scan-input">
		</div>
        <div class="bigbox">
        </div>
	</section>
<div class="pop checkProduct" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">库存已存在列表<i class="closeicon"></i> </h3>
        <p class="text" style="margin-left:0;"><span style="display:inline-block;width:90px;word-wrap:break-word;float:left;">产品唯一码：</span><span class="uniqueCodes" style="float:left;display:inline-block;width:340px;word-wrap:break-word;"></span></p>
        <div class="row tc">
        	<!-- 需求发生修改当产品有在库存中存在时不让入库 -->
        	<!-- <input type="button" value="下一步" class="next_btn" style="background-color: #ff6200;height:30px;width:80px;color:#fff;"> -->
            <input type="button" value="取消" class="cancel">
            <!-- <p class="end">以上产品在库存中已存在，点击“下一步”继续入库！</p> -->
        </div>
    </div>
</div>
<div class="pop surepop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">确认入库<i class="closeicon"></i> </h3>
        <p class="text">产品名称：<span class="prod-name"></span></p>
        <p class="text">产品规格：<span class="standard"> </span></p>
        <p class="text">产品数量：<span class="orangenumber amount"></span></p>
        <div class="row tc">
        	<input type="button" value="确定入库" class="sure-submit">
            <input type="button" value="取消" class="cancel">
        <p class="end">请确认数量等入库信息正确后才点击确认入库！</p>
        </div>
    </div>
</div>
<div class="pop unusualpop" style="display: none;">
	<div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">标记异常<i class="closeicon"></i> </h3>
        <p class="text">
        	异常类型：
        	<input type="radio" name="putaway" class="putaway" value="1" checked="checked">损坏
            <input type="radio" name="putaway" class="putaway" value="2">丢失
        </p>
        <p class="text">
        	异常说明：
        	<textarea style="width: 200px;height:80px;" class="description"></textarea>
        </p>
        <div class="row tc">
        	<input type="button" value="确定" class="determine">
        </div>
    </div>
</div>
<div class="pop pop4" style="display: none;">
    <div class="popbg"></div>
    <div class="layel">
        <h3 class="poptitle">产品不在列表<i class="closeicon"></i> </h3>
        <p class="text tc" style="margin-top:50px;margin-left:0;">
            扫描产品不在该托盘列表，请检查！
        </p>
        <div class="row tc">
            <input type="button" value="关闭" class="closebtn" style="margin-top:80px;background-color: #ff6200;height:30px;width:80px;color:#fff;">
        </div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    $('.closebtn').on('click',function(){
        $('.pop4').hide();
        $('.scan-input').focus();
    })
    $('.scan-input').focus();
    function scan() {
        var x = 10;
        var y = 1;
        //var rand =2;
        var rand = parseInt(Math.random() * (x - y + 1) + y);
        console.log(JsonData[rand]);
        if (!$('.box').eq(rand).hasClass('box-on'))
            $('.box').eq(rand).addClass('box-on');
        //$('.trans-code').html(JsonData[rand].code);
        $('.prod-name').html(JsonData[rand].name);
        $('.standard').html(JsonData[rand].standard);
        $('.prod-img').attr('src', JsonData[rand].src);
    }

    $('.scan-input').change(function () {
        var code = $(this).val();
        change(code);
        //scan();
        $(this).attr('value', "");
    });
    var submit_data={};//提交数据
    var normalPros=[];//正常产品
    var products=[]; //异常产品
    var JsonData='';
    var format='';
    //扫描条码
    function change(code) {
        console.log(code);
        //var code=123456;
        if(code.substring(0,1)=='U'&&$('.medium').length!=0){
        	var exist=true;
        	$('.code-on').each(function(){
                var text=$(this).html();
                if(text==code){
                	alertLayel("该商品已扫描");
                	exist=false;
                	return false;
                }
        	})
            $('.code').each(function(){
                var text=$(this).html();
                if(text==code){
                    $(this).addClass('code-on').removeClass('code').parent('.box').addClass('box-on').removeClass('box');
                    var pros_id=$(this).parent('.box-on').attr('id');
                    //normalPros.push(pros_id);
                    exist=false;
                    return false;
                }
            })
            if($('.code-no').length>0){
            	$('.code-no').each(function(){
            		if($('.code-no').html()==code){
            			exist=false;
            			return;
            		}
            	})	
            }
            if(exist==true){
                $('.pop4').show();
            }
        }else{
	        $.ajax({
	            type: "GET",
	            url: "/storage/put/transport.html",
	            data: {code: code},
	            dataType: "json",
	            success: function (data) {
	                console.dir(data);
	                JsonData = data;
	                if (JsonData.code == 1) {  //扫描运输码
	                    format = JsonData.result.transport.products;
	                    var html = '';
	                    for (i = 0; i < format.length; i++) {
	                        html += '<div class="medium"> <div class="smallbox"> <span class="serial-number">'+(i+1)+'.</span> <div class="box items" id="' + format[i].id + '"> <span class="code">' + format[i].uniqueCode + '</span> <p class="marker-anomaly" data-id="' + (i+1)+ '">标记异常</p> </div> </div> </div>'
	                        $('.bigbox').html(html);
	                        $('.bigbox').html(html);
	                        $('.prod-name').html(JsonData.result.transport.productName);
	                        $('.trans-code').html(JsonData.result.transport.code);
	                        $('.standard').html(JsonData.result.transport.formatName);
	                        $('.amount').html(JsonData.result.transport.num);
	                        if(JsonData.result.transport.pics){
	                        	if(JsonData.result.transport.pics[0] != undefined ){
	                            	$('.prod-img').attr('src', JsonData.result.transport.pics[0].url);
	                        	}
	                        }
	                    }
	                   
	                }
	                if (JsonData.code == 0) {   //扫描异常
	
	                }
	            }
	        })
        }
    }

    //标记异常
    var anomalyType='';
    $(document).on('click','.marker-anomaly',function(){
        if($(this).parent().hasClass('box')){
            anomalyType='1';
            var data_id=$(this).parent('.box').attr('id');
        }else{
            anomalyType='2';
            var data_id=$(this).parent('.box-on').attr('id');
        }
        $('.unusualpop').show().attr('data-id',data_id);
    })
    $(document).on('click','.determine',function(){
    	var n=$(this).parent().parent().parent().attr('data-id');
        var obj={};
        obj.hardwareProductId=$('#'+n).attr('id');
        obj.exceptionType=$('.putaway:checked').val();
        obj.description=$('.description').val();
        products.push(obj);
        var type=$('.putaway:checked').val();
        console.log(products,n)
        if(type==1){
            type='损坏'
        }
        if(type==2){
            type='丢失'
        }
        var id=$(this).parent().parent().parent().attr('data-id');
        $('.unusualpop').hide().removeAttr('data-id');
        $('.scan-input').focus();
        $('.description').attr('value','');
        $('.putaway').eq(0).attr('checked',true);
        console.log(anomalyType)
        if(anomalyType==2){
            $('.medium').find('#'+n).removeClass().addClass('box-no').find('.code-on').addClass('code-no').removeClass('code-on').append('<p class="small">已标记为  <span class="lose">'+type+'</span></p>');
        }else{
            $('.medium').find('#'+n).addClass('box-no').removeClass('items box').find('.code').addClass('code-no').removeClass('code').append('<p class="small">已标记为  <span class="lose">'+type+'</span></p>');
        }
        var amount = $('.amount').html();
        $('.amount').html(amount-1);
    });
    //确认入库
    $('.sure-storage').on('click',function(){
        submit_data.products=products;
        //submit_data.normalPros=normalPros;
        normalPros=[];
        $('.items').each(function(){
            var pros_id=$(this).attr('id');
            console.log(pros_id)
            normalPros.push(pros_id);
        });
        console.log(normalPros);
        var b = false;
        if(products.length>0){
        	b = true;
        }
        if(normalPros.length>0){
        	b = true;
        }
        if(!b){
        	alertLayel("没有任何产品为异常标记或可入库!");
        	return ;
        }
        //$('.surepop').show();
        //检测是否有存在已被入库了，如果有提示一下，如果没有直接显示
        var b = true;//页面不发生弹出
        var message = "";
        var strn=normalPros.join(",");
        $.ajax({
            url: "/storage/put/checkStorageProduct.html?normalPros="+strn,
            type: "POST",
            dataType: "json",
            async: false,
            success: function (data) {
            	if ( data.code == 1 ) {
 				  b = false;
 				  message = data.message;
 			    }
            }
    	})
    	if(b==false){
    		$('.uniqueCodes').text(message);
    		$('.checkProduct').show();
	    }else{
	    	//没有发现入过库的记录直接弹出
	    	$('.surepop').show();
	    }
    })
    //点击了下一步
    $('.next_btn').on('click',function(){
    	$('.checkProduct').hide();
    	$('.surepop').show();
    })
    //确认提交
    $('.sure-submit').on('click',function(){
        //submit_data.normalPros=normalPros.join(",");
        var strn=normalPros.join(",");
        //console.log(JSON.stringify(products));
        var i=0;
        var prs = "";
        products.forEach(function(e){  
        	prs=prs+"&products["+i+"].hardwareProductId="+e.hardwareProductId+"&products["+i+"].exceptionType="+e.exceptionType+"&products["+i+"].description="+e.description;  
        	i++;
        });
        var code = $('.trans-code').html();
        $.ajax({
            type: "POST",
            url: "http://erp.ibaixiong.com/storage/put/save.html?normalPros="+normalPros+prs+"&code="+code,
            //data: {normalPros: submit_data.normalPros,products[0].hardwareProductId:1},
            dataType: "json",
            //contentType:"application/json",               
            //data:JSON.stringify(products), 
            success: function (data) {
            	if (data.code == 0) {   //添加完成
            		alertLayel("入库成功!");
            		window.location.href="/storage/put.html";
                }else{
                	alertLayel(data.message);
                }
            }
    	}) 
    })
    
    //入库异常记录查询
    $(document).on('click','.abnormal',function(){
        window.location.href="/storage/put/record.html";
    })
})
</script>
</body>
</html>
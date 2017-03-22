$(function(){
    $("#addbutton,.updateUser,.uploadPho,.addbutton").click(function(){
        var CaName=$(".Role_wrap,.Pho_wrap,");
        var HiddenDivName= $(".hiddendiv");
        CaName.css("display","block");
        CaName.css("top",($(window).height()-330)/2);
        CaName.css("left",($(window).width()-504) /2);
        HiddenDivName.css("display","block");
        HiddenDivName.css("width",$(document.body).width()+17);
        HiddenDivName.css("height",$(document.body).height());
        $(document.body).css("overflow","hidden");
        $(".shutwindow").click(function(){
            CaName.css("display","none");
            HiddenDivName.css("display","none");
            $(document.body).css("overflow","auto");
        });
    });
})
$(function(){
    $(".addbutton").click(function(){
        var CaName=$(".wrap");
        var HiddenDivName= $(".hiddendiv");
        CaName.css("display","block");
        CaName.css("top",($(window).height()-423)/2);
        CaName.css("left",($(window).width()-755) /2);
        HiddenDivName.css("display","block");
        HiddenDivName.css("width",$(document.body).width()+17);
        HiddenDivName.css("height",$(document.body).height());
        $(document.body).css("overflow","hidden");
        $(".win_close_button").click(function(){
            CaName.css("display","none");
            HiddenDivName.css("display","none");
            $(document.body).css("overflow","auto");
        });
    });
})
$(function(){
    $("#addbutton").click(function(){
        var CaName=$(".BuM_wrap");
        var HiddenDivName= $(".hiddendiv");
        CaName.css("display","block");
        CaName.css("top",($(window).height()-153)/2);
        CaName.css("left",($(window).width()-300) /2);
        HiddenDivName.css("display","block");
        HiddenDivName.css("width",$(document.body).width()+17);
        HiddenDivName.css("height",$(document.body).height());
        $(document.body).css("overflow","hidden");
        $(".win_close_button").click(function(){
            CaName.css("display","none");
            HiddenDivName.css("display","none");
            $(document.body).css("overflow","auto");
        });
    });
})

function updatewzbg(id,secid){
	if(id=='5')
	{
		$('#wz_infor_two').css('display','');
		$('#wz_sfxx').css('display','');
		$('#bas_infor').css('color','#fff');
		$('#bas_infor').addClass('wzaddTabLink');
		
		$('#wz_photo_xs').css('display','none');
		$('#wz_photo').css('color','#000');
		$('#wz_photo').removeClass('wzaddTabLink');
		
		$('#checkoutInfo').css('display','none');
		$('#wz_check').css('color','#000');
		$('#wz_check').removeClass('wzaddTabLink');
		
		$('#HT_wz').css('display','');
		$('#ST_wz').css('display','');
	}
	if(id=='6')
	{
		$('#wz_infor_two').css('display','none');
		$('#wz_sfxx').css('display','none');
		$('#bas_infor').css('color','#000');
		$('#bas_infor').removeClass('wzaddTabLink');
		
		$('#wz_photo_xs').css('display','');
		$('#wz_photo').css('color','#fff');
		$('#wz_photo').addClass('wzaddTabLink');
		
		$('#checkoutInfo').css('display','none');
		$('#wz_check').css('color','#000');
		$('#wz_check').removeClass('wzaddTabLink');
		
		$('#HT_wz').css('display','none');
		$('#ST_wz').css('display','none');
	}
	if(id=='7')
	{
		
		$('#wz_infor_two').css('display','none');
		$('#wz_sfxx').css('display','none');
		$('#bas_infor').css('color','#000');
		$('#bas_infor').removeClass('wzaddTabLink');
		
		$('#wz_photo_xs').css('display','none');
		$('#wz_photo').css('color','#000');
		$('#wz_photo').removeClass('wzaddTabLink');
		
		$('#checkoutInfo').css('display','');
		$('#wz_check').css('color','#fff');
		$('#wz_check').addClass('wzaddTabLink');
		
		$('#HT_wz').css('display','none');
		$('#ST_wz').css('display','none');
	}
}
function updatewzbgold(id,secid){
	
	//一般物证
	if(id=='1')
	{
		$('#HT_wz').css('display','none');
		$('#ym_ST_wz').css('display','none');
		$('#wm_ST_wz').css('display','none');
		$('#wz_infor_two').css('display','');
		
		$('.wz_code').css('display','none');
		
		$('#bas_wz_link').css('color','#fff');
		$('#bas_wz_link').addClass('wzaddTabLink');
		
		$('#ys_wz_link').css('color','#000');
		$('#ys_wz_link').removeClass('wzaddTabLink');
		
		$('#ym_wz_link').removeClass('wzaddTabLink');
		$('#ym_wz_link').css('color','#000');
		
		$('#st_wz_link').css('color','#000');
		$('#st_wz_link').removeClass('wzaddTabLink');
		
	
		
		$('#ht_wz_link').css('color','#000');
		$('#ht_wz_link').removeClass('wzaddTabLink');
		$('#wz_photo_xs').css('display','none');
	}
	//衍生物证
	if(id=='2')
	{
		$('#HT_wz').css('display','none');
		$('#ym_ST_wz').css('display','none');
		$('#wm_ST_wz').css('display','none');
		$('#wz_infor_two').css('display','');
		$('.wz_code').css('display','');
		$('#bas_wz_link').css('color','#000');
		$('#bas_wz_link').removeClass('wzaddTabLink');
		
		$('#ys_wz_link').addClass('wzaddTabLink');
		$('#ys_wz_link').css('color','#fff');
		
		$('#ym_wz_link').removeClass('wzaddTabLink');
		$('#ym_wz_link').css('color','#000');
		
		
		$('#st_wz_link').css('color','#000');
		$('#st_wz_link').removeClass('wzaddTabLink');
		
		$('#ht_wz_link').css('color','#000');
		$('#ht_wz_link').removeClass('wzaddTabLink');
		$('#wz_photo_xs').css('display','none');
	}
	
	//有名尸体
	if(id=='3')
	{
		$('#HT_wz').css('display','none');
		$('#ym_ST_wz').css('display','');
		$('#wm_ST_wz').css('display','none');
		$('.wz_code').css('display','');
		
		$('#bas_wz_link').css('color','#000');
		$('#bas_wz_link').removeClass('wzaddTabLink');
		
		$('#ys_wz_link').removeClass('wzaddTabLink');
		$('#ys_wz_link').css('color','#000');
		
		$('#wm_wz_link').removeClass('wzaddTabLink');
		$('#wm_wz_link').css('color','#000');
		
		$('#ym_wz_link').css('color','#fff');
		$('#ym_wz_link').addClass('wzaddTabLink');
		
		$('#ht_wz_link').css('color','#000');
		$('#ht_wz_link').removeClass('wzaddTabLink');
		
		$('#wz_infor_two').css('display','none');
		$('#wz_photo_xs').css('display','none');
	}
	//活体物证
	if(id=='4')
	{
		$('#HT_wz').css('display','');
		$('#ym_ST_wz').css('display','none');
		$('#wm_ST_wz').css('display','none');
		$('.wz_code').css('display','');
		$('#bas_wz_link').css('color','#000');
		$('#bas_wz_link').removeClass('wzaddTabLink');
		
		$('#ys_wz_link').removeClass('wzaddTabLink');
		$('#ys_wz_link').css('color','#000');
		
		$('#wm_wz_link').removeClass('wzaddTabLink');
		$('#wm_wz_link').css('color','#000');
		
		$('#ym_wz_link').removeClass('wzaddTabLink');
		$('#ym_wz_link').css('color','#000');		

		
		$('#ht_wz_link').css('color','#fff');
		$('#ht_wz_link').addClass('wzaddTabLink');
		$('#wz_photo_xs').css('display','none');
		$('#wz_infor_two').css('display','');
	}
	
	//无名尸体
	if(id=='7')
	{
		$('#HT_wz').css('display','none');
		$('#ym_ST_wz').css('display','none');
		$('#wm_ST_wz').css('display','');
		$('.wz_code').css('display','');
		$('#bas_wz_link').css('color','#000');
		$('#bas_wz_link').removeClass('wzaddTabLink');
		
		$('#ys_wz_link').removeClass('wzaddTabLink');
		$('#ys_wz_link').css('color','#000');
		
		$('#ym_wz_link').removeClass('wzaddTabLink');
		$('#ym_wz_link').css('color','#000');
		
		$('#wm_wz_link').css('color','#fff');
		$('#wm_wz_link').addClass('wzaddTabLink');
		
		$('#ht_wz_link').css('color','#000');
		$('#ht_wz_link').removeClass('wzaddTabLink');
				
		$('#wz_infor_two').css('display','none');
		$('#wz_photo_xs').css('display','none');
	}
	if(id=='6')
	{
		
		$('#HT_wz').css('display','');
		$('#ym_ST_wz').css('display','none');
		$('#wm_ST_wz').css('display','none');
		$('.wz_code').css('display','');
		$('#bas_wz_link').css('color','#000');
		$('#bas_wz_link').removeClass('wzaddTabLink');
		
		$('#ys_wz_link').removeClass('wzaddTabLink');
		$('#ys_wz_link').css('color','#000');
		
		$('#ym_wz_link').removeClass('wzaddTabLink');
		$('#ym_wz_link').css('color','#000');
		
		$('#wm_wz_link').css('color','#000');
		$('#wm_wz_link').removeClass('wzaddTabLink');
		
		$('#ht_wz_link').css('color','#fff');
		$('#ht_wz_link').addClass('wzaddTabLink');
		$('#wz_photo_xs').css('display','none');
	
	}
}
/**
 * Created by junfei on 2015/9/1.
 */
$(document).ready(function(){
    var n;
    function slider(){
        //var index=$('.icon-circle').length;
        $('.icon-circle-thin').removeClass('icon-circle-thin');
        $('.icon-circle').eq(n).addClass('icon-circle-thin');
        $('.curslider').fadeOut(800).removeClass('curslider');
        console.log(n)
        $('.slider').eq(n).fadeIn(800).addClass('curslider');
    }
    $('.bullet li').click(function(){
        n=$(this).index();
        slider();
    });
    //自动轮播
    $.autoscroll=function (){
        var index=$('.icon-circle').length;
        var curindex=$('.icon-circle-thin').index();
        if(curindex==index-1){
            curindex=0;
        }else{
            curindex=curindex+1;
        }
        $('.icon-circle-thin').removeClass('icon-circle-thin');
        $('.icon-circle').eq(curindex).addClass('icon-circle-thin');
        $('.curslider').fadeOut(800).removeClass('curslider');
        $('.slider').eq(curindex).fadeIn(800).addClass('curslider');
    };
    setInterval(function(){
        $.autoscroll()
    },50000);
    //视频
    //loca();
    //function loca(){
    //    $('.popvideobox').each(function(){
    //        var left=$(this).prev('.videoimg').offset().left;
    //        var bottom=$(this).prev('.videoimg').offset().bottom;
    //        $(this).css({'left':left,'bottom':bottom});
    //    })
    //}
    //$('.videoimg1').on('click',function(){
    //    //console.log($(this.pageX));
    //    var width=$(window).width();
    //    var height=$(window).height();
    //    $(this).next('.popvideobox').css({'width':width,"height":height,'bottom':'0','left':'0'});
    //    //$('.videobox1').animate({'width':width,"height":height},2000).animate({'position':'fixed','left':'0','bottom':'0'},1000);
    //    $(this).next('.popvideobox').find('.videobox').animate({'top':'50%'},1000)
    //});
    //$('.closevideo').on('click',function(){
    //    $(this).parent('.videobox').animate({'top':'-220'},1000).parent('.popvideobox').css({'width':0,"height":0});
    //    //$(this).parent('.videobox').parent('.popvideobox').hide();
    //})
    //关闭弹窗
    $('.closeicon,.sendinfor').on('click',function(){
        $(this).parent().parent().parent('.pop').hide();
    });
    $('.feedback').on('click',function(){
        $('.pop').show();
    })
});
function linkToMainFrame(url){
	$("#rightFrame").attr('src',url);
}
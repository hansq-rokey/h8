/**
 * Created by junfei on 2015/8/26.
 */
$(document).ready(function(){
    $('.closeicon').on('click',function(){
        $(this).parent().parent().parent('.pop').hide();
        });
    $('.changepass').on('click',function(){
        $('.pop').show();
    })
});
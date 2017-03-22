/**
 * Created by junfei on 2015/8/28.
 */
$(document).ready(function(){
    var width=$('.partlist').width();
    console.log(width);
    $('.inforbox').css('width',width);
    $(document).on('click','.moduletr',function(e){
        $('.selectedtr').removeClass('selectedtr');
        $(this).addClass('selectedtr');
        e.stopPropagation();
    });
    //选中菜单
    $(document).on('click','.moduletr',function(e){
        $('.selectedtr').removeClass('selectedtr');
        $(this).addClass('selectedtr');
        e.stopPropagation();
    });
    icon();
    function icon(){
        $('.moduletr').each(function(){
            if($(this).next('tr').hasClass('secondtr')){
                $(this).find('i').addClass('showicon').removeClass('hideicon');
                console.log(2)
            }else{
                $(this).find('i').addClass('hideicon').removeClass('showicon');
            };
            console.log(1)
        });
    }
    $(document).on('click','.showicon',function(){
        $(this).parent().parent().next('.secondtr').toggle();
    });
    $('.addmodule').on('click',function(){
        var html='';
        var mod=$('.selectedtr').hasClass('secmoduletr');
        if(!mod){
            html='<tr class="moduletr"> <td><i class="hideicon"></i><input type="text" class="modulename" value=""> </td> <td>概要描述新增同级模块新增同级模块新增同级模块新增同级模块新增同级模块</td> <td><input type="text" class="moduleurl" value=""> </td> <td><input type="text" class="modulesort" value=""> </td> <td><a href="#" class="link" >修改权限</a> </td> </tr>';
            $('.selectedtr').after(html);
        }else{
            html='<tr class="moduletr"> <td><input type="text" class="modulename fn" value=""> </td> <td><input type="button" value="图标上传" class="uploadicon"></td> <td><input type="text" class="moduleurl" value=""> </td> <td><input type="text" class="modulesort" value=""> </td> <td><a href="#" class="link" >修改权限</a> </td> </tr>';
            $('.selectedtr').after(html);
        }
        icon();
    });
    $('.addsonmodule').on('click',function(){
        var html='';
        var mod=$('.selectedtr').hasClass('secmoduletr');
        if(!mod){
            html='<tr class="moduletr secondtr"> <td colspan="5" class="secondtd"> <table class="secondmodulelist"> <tbody><tr> <td><input type="text" class="modulename fn" value=""> </td> <td><input type="button" value="图标上传" class="uploadicon"></td> <td><input type="text" class="moduleurl" value=""> </td> <td><input type="text" class="modulesort" value=""> </td> <td><a href="#" class="link" >修改权限</a> </td> </tr> </tbody> </table> </td> </tr>';
            $('.selectedtr').after(html);
        }
        icon();
    })
});
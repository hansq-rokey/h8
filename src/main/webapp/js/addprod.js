/**
 * Created by junfei on 2015/8/25.
 */
$(document).ready(function(){
    //function serial(){
    //    $('.serial').each(function(){
    //        var index=$(this).index();
    //        $(this).html(index);
    //    })
    //}
    //serial();
    $('.addprodinfor').on('click',function(){
        var index=$(".addprodtable tbody").find('tr');
        var num=index.length;
        var html='<tr><td class="serial">'+num+'</td><td><div class="uploadbox"><input type="button" class="uploadimgbtn" value="上传"> <div class="barrier">重新上传</div> </div> </td> <td><input type="text" class="size" value=""> </td> <td><input type="text" class="perprice" value=""> </td><td><input type="text" class="actprice" value=""> </td> <td><input type="text" class="stock" value=""> </td> <td><input type="text" class="prodcodimg" value=""> </td> <td><input type="text" class="prodbarcoding" value=""> </td></tr>';
        $('.addprodtable tbody tr:last-child').after(html);
    })
});
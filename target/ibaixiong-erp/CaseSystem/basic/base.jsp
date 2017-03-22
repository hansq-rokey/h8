<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
 <title>白熊运管系统</title>
<base href="<%=basePath%>">
<link href="<%=path%>/CaseSystem/css/common.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/report.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.7.1.min.js"></script>
<script type=text/javascript src="<%=path%>/js/ajaxfileupload.js"></script>
<script type=text/javascript src="<%=path%>/js/json2.js"></script>

<script language="javascript" type="text/javascript"
	src="<%=path%>/My97DatePicker/WdatePicker.js"></script>

<!-- jquery easyUI  -->
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.3.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.3.5/themes/icon.css">
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.3.5/easyloader.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>

<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
function topMsgAlert(title,msg){
	$.messager.alert(title, msg); 
}
//easyUI默认出现滚动条 
function defaultHaveScroll(gridid){ 
    var opts=$('#'+gridid).datagrid('options'); 
   // alert(Ext.util.JSON.encode(opts.columns)); 
    var text='{'; 
    for(var i=0;i<opts.columns.length;i++){ 
       var inner_len=opts.columns[i].length; 
       for(var j=0;j<inner_len;j++){ 
           if((typeof opts.columns[i][j].field)=='undefined')break; 
            text+="'"+opts.columns[i][j].field+"':''"; 
            if(j!=inner_len-1){ 
                text+=","; 
            } 
       } 
    } 
    text+="}"; 
    text=eval("("+text+")"); 
    var data={"total":1,"rows":[text]}; 
    $('#'+gridid).datagrid('loadData',data); 
   // $('#grid').datagrid('appendRow',text); 
   $("tr[datagrid-row-index='0']").css({"visibility":"hidden"}); 
}
/** 
* 根据指定条件请求系统资源 
*1、 异步 
*2、返回格式为json 
* 
* @param type          //请求方式 
* @param url               //请求url 
* @param param     //请求参数 
* @param callback      //回调函数 
*/  
function ajaxExtend(type,url,param,callback){  
ajaxExtendBase(type,url,param,true,callback);  
}  

/** 
* ajax请求基础方法 
* @param type 
* @param url 
* @param param 
* @param async 
* @param callback 
*/  
function ajaxExtendBase(type,url,param,async,callback){  
$.ajax({  
       type: type,  
       url: url,  
       data:param,  
       dataType:"json",  
       success:function(result){  
           if(result.success){   //只有sql正确能获取相关列名后才再请求列表资源  
                callback(result.data);   //获取当前页信息  
           }  
           
       }  
});  
}  


/** 
* 将指定form参数转换为json对象 
*/  
function getQueryParams(conditionFormId){  
var searchCondition = getJqueryObjById(conditionFormId).serialize();  
var obj = {};  
var pairs = searchCondition.split('&');  
var name,value;  
  
$.each(pairs, function(i,pair) {  
 pair = pair.split('=');  
 name = decodeURIComponent(pair[0]);  
 value = decodeURIComponent(pair[1]);  
   
 obj[name] =  !obj[name] ? value :[].concat(obj[name]).concat(value);              //若有多个同名称的参数，则拼接  
});  
  
return obj;  
}  
function show_title(value,data){
	var html =null;
	if(value !=null &&value!= undefined && value!=''){
	  html = '<a title="'+value+'">'+value+'</a> ';
	}
	return html;
}
</script>

</head>
<div id="popup_first">

</div>
<div id="popup_second">

</div>
<div id="popup_third">

</div>
<div id="popup_fourth">

</div>
<div id="popup_fifth">

</div>

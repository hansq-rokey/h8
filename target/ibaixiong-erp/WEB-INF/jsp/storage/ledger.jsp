<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link href="/plug/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/pubstyle.css" rel="stylesheet" type="text/css">
    <link href="/css/ledger.css" rel="stylesheet" type="text/css">
    <script src="/plug/jQuery/jquery-1.8.3.min.js" type="text/javascript" ></script>
    <script src="/js/public.js" type="text/javascript" ></script>
    <script src="/plug/adddatetime.js" type="text/javascript" ></script>
    <title>台账</title>
</head>
<body>
    <section>
        <ul class="partlist">
            <li>
                <a href="/ledger/storage/list.html"><span class="switch ${flag==1?'switched':'' }">库存列表</span></a>
                <div class="inforbox ${flag==1?'selectinforbox':'' }">
                    <div class="content">
                   	 	<c:if test="${flag==1 }">
                    	<form action="/ledger/storage/list.html" method="post">
                    		<input type="hidden" name="pageNo" id="pageNo" value="">
	          			    <input type="text" class="packing-code" name="keywords" value="${keywords }" placeholder="产品名称">
	                        <input type="text" class="strat-time datetimepicker startdata" name="startTime" value="${startTime }" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <span>至</span>
	           			    <input type="text" class="end time datetimepicker startdata" name="endTime" value="${endTime }" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <input type="submit" class="search" value="搜索">
                        </form>
                        </c:if>
        			</div>
        			<table class="stocktable">
		        	<thead>
		        		<tr>
		        			<td>产品名称</td>
		        			<td>产品编号</td>
		        			<td>产品规格</td>
		        			<td>库存数量</td>
		        			<td>报警下线</td>
		        			<td>操作</td>
		        		</tr>
		        	</thead>
		        	<tbody>
		        	<c:if test="${flag==1 }">
		        	<c:forEach items="${list }" var="model">
			   			<c:forEach  items="${model.formats }" var="item" varStatus="itemStatus">
			   				<c:choose>
			   					<c:when test="${fn:length(model.formats)==1 }">
					        		<tr>
					        			<td >${model.name }</td>
					        			<td>${item.categoryModelFormatNumber }</td>
					        			<td>${item.name}</td>
					        			<td>${item.realStock}</td>
					        			<td><span class="hidespan">${item.warnCount}</span><input type="text" class="hideinput"></td>
					        			<td>
					        				<a href="/ledger/storage/list/detail.html?formId=${item.id }" class="link detaillink">详情</a>
					        				<a href="javascript:void(0);" class="link editlink">修改下线</a>
					        				<a href="javascript:void(0);" data-id="${item.id }" class="link savelink">保存</a>
			        						<a href="javascript:void(0);" class="link cancellink">取消</a> 
					        			</td>
					        		</tr>
		        				</c:when>
   								<c:otherwise>
   									<c:if test="${itemStatus.first }">
   										<tr>
						        			<td rowspan="${fn:length(model.formats) }">${model.name }</td>
						        			<td>${item.categoryModelFormatNumber }</td>
					        				<td>${item.name}</td>
					        				<td>${item.realStock}</td>
					        				<td><span class="hidespan">${item.warnCount}</span><input type="text" class="hideinput"></td>
						        			<td>
						        				<a href="/ledger/storage/list/detail.html?formId=${item.id }" class="link detaillink">详情</a>
						        				<a href="javascript:void(0);" class="link editlink">修改下线</a>
						        				<a href="javascript:void(0);" data-id="${item.id }" class="link savelink">保存</a>
				        						<a href="javascript:void(0);" class="link cancellink">取消</a> 
						        			</td>
						        		</tr>
   									</c:if>
   									<c:if test="${itemStatus.index>0 }">
   										<tr>
						        			<td>${item.categoryModelFormatNumber }</td>
					        				<td>${item.name}</td>
					        				<td>${item.realStock}</td>
					        				<td><span class="hidespan">${item.warnCount}</span><input type="text" class="hideinput"></td>
						        			<td>
						        				<a href="/ledger/storage/list/detail.html?formId=${item.id }" class="link detaillink">详情</a>
						        				<a href="javascript:void(0);" class="link editlink">修改下线</a>
						        				<a href="javascript:void(0);" data-id="${item.id }" class="link savelink">保存</a>
				        						<a href="javascript:void(0);" class="link cancellink">取消</a> 
						        			</td>
						        		</tr>
   									</c:if>
		        				</c:otherwise>
   							</c:choose>
   						</c:forEach>
   					</c:forEach>
   					</c:if>
		        	</tbody>
		        	</table>
		        	<c:if test="${flag==1 }">
		        	<jsp:include page="../include/pages.jsp"></jsp:include>
		        	</c:if>
                </div>
            </li>
            <li>
                <a href="/ledger/storage/out.html"><span class="switch ${flag==2?'switched':'' }">出库列表</span></a>
                <div class="inforbox ${flag==2?'selectinforbox':'' }">
                    <div class="content">
                    	<c:if test="${flag==2 }">
                    	<form action="/ledger/storage/out.html" method="post">
                    		<input type="hidden" name="pageNo" id="pageNo" value="">
	          			    <input type="text" class="packing-code" name="keywords" value="${keywords }" placeholder="产品唯一码">
	                        <input type="text" class="strat-time datetimepicker startdata" name="startTime" value="${startTime }" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <span>至</span>
	           			    <input type="text" class="end time datetimepicker startdata" name="endTime" value="${endTime }" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <input type="submit" class="search" value="搜索">
                    	</form>
                    	</c:if>
        			</div>
        			<table class="ledgertable">
		        	<thead>
		        		<tr>
		        			<td>产品唯一码</td>
		        			<td>产品名称</td>
		        			<td>产品规格</td>
		        			<td>订单号</td>
		        			<td>操作人</td>
		        			<td>日期</td>
		        			<td>操作</td>
		        		</tr>
		        	</thead>
		        	<tbody>
		        		<c:if test="${flag==2 }">
			        		<c:forEach items="${list }" var="item">
			        			<tr>
				        			<td>${item.uniqueCode }</td>
				        			<td>${item.productName }</td>
				        			<td>${item.formatName }</td>
				        			<td>${item.orderNumber }</td>
				        			<td>${item.admin.userName }</td>
				        			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY/MM/dd"/> </td>
				        			<td><a href="/ledger/trace.html?hardwareId=${item.hardwareProductId }" class="link">物联网详情</a></td>
				        		</tr>
			        		</c:forEach>
		        		</c:if>
		        	</tbody>
		        	</table>
		        	<c:if test="${flag==2 }">
		        	<jsp:include page="../include/pages.jsp"></jsp:include>
		        	</c:if>
                </div>
            </li>
            <li>
                <a href="/ledger/storage/put.html"><span class="switch ${flag==3?'switched':'' }">入库列表</span></a>
                <div class="inforbox ${flag==3?'selectinforbox':'' }">
                    <div class="content">
                    	<c:if test="${flag==3 }">
                    	<form action="/ledger/storage/put.html" method="post">
                    		<input type="hidden" name="pageNo" id="pageNo" value="">
	          			    <input type="text" class="packing-code" name="keywords" value="${keywords }" placeholder="运输码">
	                        <input type="text" class="strat-time datetimepicker startdata" name="startTime" value="${startTime }"  placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <span>至</span>
	           			    <input type="text" class="end time datetimepicker startdata" name="endTime" value="${endTime }"  placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <input type="submit" class="search" value="搜索">
                    	</form>
                    	</c:if>
        			</div>
        			<table class="ledgertable">
		        	<thead>
		        		<tr>
		        			<td>运输码</td>
		        			<td>产品名称</td>
		        			<td>产品规格</td>
		        			<td>数量</td>
		        			<td>操作人</td>
		        			<td>日期</td>
		        			<td>操作</td>
		        		</tr>
		        	</thead>
		        	<tbody>
		        		<c:if test="${flag==3 }">
			        		<c:forEach items="${list }" var="item">
			        			<tr>
				        			<td>${item.transportcode }</td>
				        			<td>${item.productName }</td>
				        			<td>${item.productFormat }</td>
				        			<td>${item.count }</td>
				        			<td>${item.admin.userName }</td>
				        			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY/MM/dd"/> </td>
				        			<td><a href="/ledger/storage/put/detail.html?id=${item.id }" class="link">详情</a></td>
				        		</tr>
			        		</c:forEach>
		        		</c:if>
		        	</tbody>
		        	</table>
		        	<c:if test="${flag==3 }">
		        	<jsp:include page="../include/pages.jsp"></jsp:include>
		        	</c:if>
                </div>
            </li>
            <li>
                <a href="/ledger/storage/excetion.html"><span class="switch ${flag==4?'switched':'' }">异常列表</span></a>
                <div class="inforbox ${flag==4?'selectinforbox':'' }">
                	<div class="content">
                		<c:if test="${flag==4 }">
                		<form action="/ledger/storage/excetion.html" method="post">
                			<input type="hidden" name="pageNo" id="pageNo" value="">
	          			    <input type="text" class="packing-code datetimepicker startdata" name="keywords" value="${keywords }" placeholder="唯一码">
	                        <input type="text" class="strat-time" placeholder="开始时间" name="startTime" value="${startTime }"  onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <span>至</span>
	           			    <input type="text" class="end time datetimepicker startdata" name="endTime" value="${endTime }"  placeholder="结束时间"  onclick="SelectDate(this,'yyyy-MM-dd')">
	                        <input type="submit" class="search" value="搜索">
							<input type="button" class="add-unusual" value="新增异常">                		
                		</form>
                		</c:if>
        			</div>
                    <table class="ledgertable">
		        	<thead>
		        		<tr>
		        			<td>唯一码</td>
		        			<td>产品名称</td>
		        			<td>产品规格</td>
		        			<td>操作人</td>
		        			<td>日期</td>
		        			<td>异常类型</td>
		        			<td>异常原因</td>
		        			<td>操作</td>
		        		</tr>
		        	</thead>
		        	<tbody>
		        		<c:if test="${flag==4}">
			        		<c:forEach items="${list }" var="item">
			        			<tr>
				        			<td>${item.uniqeCode }</td>
				        			<td>${item.productName }</td>
				        			<td>${item.productFormat }</td>
				        			<td>${item.admin.userName }</td>
				        			<td><fmt:formatDate value="${item.createDateTime }" pattern="YYYY/MM/dd"/></td>
				        			<td>
				        				<c:if test="${item.exceptionType==1 }">
				        					损坏
				        				</c:if>
				        				<c:if test="${item.exceptionType==2 }">
				        					丢失
				        				</c:if>
				        				<c:if test="${item.exceptionType==3 }">
				        					仓损
				        				</c:if>
				        			</td>
				        			<td>${item.description }</td>
				        			<td><a href="/ledger/trace.html?hardwareId=${item.hardwareProductId }" class="link">物联网详情</a></td>
				        		</tr>
			        		</c:forEach>
		        		</c:if>
		        	</tbody>
		        	</table>
		        	<c:if test="${flag==4 }">
		        	<jsp:include page="../include/pages.jsp"></jsp:include>
		        	</c:if>
                </div>
            </li>
            <!-- 
            <li style="display: none;">
                <span class="switch">运费统计</span>
                <div class="inforbox">
                    <div class="content">
          			    <input type="text" class="packing-code" placeholder="产品编号、产品名称">
                        <input type="text" class="strat-time datetimepicker startdata" placeholder="开始时间" onclick="SelectDate(this,'yyyy-MM-dd')">
                        <span>至</span>
           			    <input type="text" class="end time datetimepicker startdata" placeholder="结束时间" onclick="SelectDate(this,'yyyy-MM-dd')">
                        <input type="button" class="search" value="搜索">
                        <input type="button" class="settlementbtn" value="标为结算">
        			</div>
                    <table class="ledgertable">
		        	<thead>
		        		<tr>
		        			<td>序号</td>
		        			<td>订单号</td>
		        			<td>时间</td>
		        			<td>产品名称</td>
		        			<td>产品规格</td>
		        			<td>数量</td>
		        			<td>运费</td>
		        			<td>运费状态</td>
		        			<td>操作</td>
		        		</tr>
		        	</thead>
		        	<tbody>
		        		<tr>
		        			<td>1</td>
		        			<td>123456789123456789</td>
		        			<td>2015/05/16</td>
		        			<td>光魔方MX5</td>
		        			<td>黄色  1600瓦</td>
		        			<td>123456789</td>
		        			<td><input type="text"></td>
		        			<td>未结算</td>
		        			<td>标记结算</td>
		        		</tr>
		        		<tr>
		        			<td>2</td>
		        			<td>123456789123456789</td>
		        			<td>2015/05/16</td>
		        			<td>光魔方MX5</td>
		        			<td>黄色  1600瓦</td>
		        			<td>123456789</td>
		        			<td><input type="text"></td>
		        			<td>已结算</td>
		        			<td>标记结算</td>
		        		</tr>
		        	</tbody>
		        	</table>
                </div>
            </li> -->
        </ul>
    </section>
<div class="pop add-unusual-pop" style="display: none;">
	<div class="popbg"></div>
	<div class="layel" style="height: 300px;">
		<h3 class="poptitle">异常产品录入<i class="closeicon"></i> </h3>
		<div class="row">
			<span class="addtypename">唯一码：</span>
			<input type="text" class="urlvalue" value="" id="uniqueCode">
		</div>
		<div class="row">
			<span class="addtypename">异常原因：</span>
			<textarea class="unusual-text" id="exceptionDes">

			</textarea>
		</div>
		<div class="row tc">
			<input type="button" value="确认" class="surebtn">
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
    $(document).on('click','.editlink',function(){
        $(this).hide().siblings('.savelink,.cancellink').show().parent('td').siblings('td').find('.hideinput').show();
        $(this).parent().siblings('td').find('.hideinput').each(function(){
            $(this).prev('.hidespan').hide();
            if($(this).hasClass('selectinput')){
                $(this).css('display','block').find('.selectvalue').html($(this).prev('.hidespan').html());
            }else{
                $(this).val($(this).prev('.hidespan').html());
            }
        });
        $(this).parent().siblings('td').find('.uploadimgbox').each(function(){
            $(this).addClass('curuploadimgbox');
        })
    });
    $(document).on('click','.savelink',function(){
        $(this).hide().prev('.editlink').show().next('.cancellink').hide();
        $(this).next('.cancellink').hide();
        var selectValue=0;
        $(this).parent().siblings('td').find('.hidespan').each(function(){
        	$(this).next('.hideinput').hide();
            if($(this).next('.hideinput').hasClass('selectinput')){
                $(this).html($(this).next('.hideinput').find('.selectvalue').html()).show();
            }else{
            	selectValue = $(this).next('.hideinput').val();
            	$(this).text($(this).next('.hideinput').val()).show();
            }
        });
        $(this).parent().siblings('td').find('.uploadimgbox').each(function(){
            $(this).removeClass('curuploadimgbox');
        });
        var id = $(this).attr("data-id");
        //alertLayel("selectValue:"+selectValue+"id:"+id);
        if(selectValue != null && selectValue != ''){
    		var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
    		if(!reg.test(selectValue)){  
    			alertLayel("下限数量只支持正整数!");
    	        return false;
    	    }  
    	}
        saveWarnCount(id,selectValue);
    });
    $(document).on('click','.cancellink',function(){
        $(this).hide().siblings('.editlink').show();
        $(this).prev('.savelink').hide();
        $(this).parent().siblings('td').find('.hidespan').each(function(){
            $(this).show().next('.hideinput').hide();
        });
    });
    $(document).on('click','.searchbtn',function(){
     	$('.pop').show();
    });

	$('.add-unusual').on('click',function(){
		$('#uniqueCode').val("");
		$('#exceptionDes').val("");
		$('.add-unusual-pop').show();
	});
	$('.surebtn').on('click',function(){
		var uniqueCode = $('#uniqueCode').val();
		var exceptionDes = $('#exceptionDes').val();
		if(uniqueCode == null || uniqueCode == undefined || uniqueCode == ""){
			alertLayel("唯一码不可为空");
			return ;
		}
		$.ajax({
            type: "POST",
            url: "/ledger/storage/excetion/save.html",
            data:{uniqueCode:uniqueCode,exceptionDes:exceptionDes},
            dataType: "json",
            success: function (data) {
            	if(data.code==0){
            		//成功了
            		location.href="/ledger/storage/excetion.html";
            	}else if(data.code == 1){
            		alertLayel("唯一码格式不对");
            	}else if(data.code == 2){
            		alertLayel("未找到该产品");
            	}else{
            		alertLayel("该产品不在库存中");
            	}
            }
    	});
	});
});
//修改数量
function saveWarnCount(id,count){
	$.ajax({
        type: "POST",
        url: "/ledger/storage/saveWarnCount.html",
        data:{id:id,count:count},
        dataType: "json",
        success: function (data) {
        	if(data.code==0){
        		//成功了
        		alertLayel("修改成功");
        	}else{
        		alertLayel("修改失败");
        	}
        }
	});
}
</script>
</body>
</html>
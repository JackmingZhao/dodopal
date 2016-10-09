<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>新增折扣</title>
<script src="${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js"></script>
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var baseUrl = "${base}";
</script>
<script type="text/javascript" src="${base}/js/merchant/rebate/merDiscountAdd.js"></script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->

<script type="text/javascript">
  var childMerAr = new Array();
</script>
</head>
<body>
<#include "../../header.ftl"/>
<input type="hidden" name="merType" id="merType" value="${merType!}"></input>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">新增折扣</h3>
		<div class="com-bor-box com-bor-box01">
			<form action="/">
				<table class="base-table">
					<col width="160" />
					<tr>
						<th><span class="red">*</span>折扣：</th>
						<td><input type="text" id="discountAdd" class="com-input-txt w250" required="true" />&nbsp;折
							<div class="tip-error tip-red-error">
								<p class="error"> </p>
							</div></td>
						
					<tr>
						<th><span class="red">*</span>启用标识：</th>
						<td><ul class="ipt-list clearfix">
								<li>
									<label>
										<input type="radio" name="a1" checked="checked" value="0"/>
										启用</label>
								</li>
								<li>
									<label>
										<input  name="a1" type="radio" value="1" />
										停用</label>
								</li>
							</ul>
							<div class="tip-error tip-red-error"></div></td>
					</tr>
						<th><span class="red">*</span>排序号：</th>
						<td><input type="text" id="sortAdd" class="com-input-txt w250" />
							<div class="tip-error tip-red-error"></div></td>
					</tr>
					 <#if merType =='12'>
					<tr>
						<th><span class="red">*</span>连锁直营网点：</th>
						<td><div class="input-box"><input type="text" id="collections" disabled="true" readOnly="true" value="" name="" class="com-input-txt w250" /><a href="javascript:;" class="link-btn"></a></div><p class="notes-txt"><i></i>请选择网点名称</p></td>
					</tr>
					<#else>
					</#if>
                    <tr>
                    	<th></th>
						<td><div class="btn-box"><input type="button" class="orange-btn-h32" onclick="saveDiscount()" value="保存" />
							<input type="button" value="返回" onclick="btnReturn()"  class="orange-btn-text32" /></div></td>
					</tr>
				</table>
			</form>
		</div>
	<!-- InstanceEndEditable --> </div>
<#include "../../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" -->
<script type="text/javascript">
$(document).ready(function(e) {
	$('.link-btn').click(function(){
		$('[js="pop"]').show();
	});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="折扣管理"){
			$(this).addClass('cur');
		}
	});

});
</script>
<div class="pop-win pop-bill" js="pop" style="display:none;">
	<div class="bg-win" ></div>
	<div class="pop-bor h380"></div>
	<div class="pop-box h370">
		<h3>直营网点信息</h3>
		<div class="innerBox">
			<div class="pop-search posr"><input type="text" id="childMerQuery" value="" name=""  /><button class="pop-search-btn" onclick="queryDiscountPage()" value=""></button></div>
			<div class="shop">
             <table cellpadding="0" cellspacing="0" class="pop-table table-title01">
            	<col width="6%" />
                <col width="32%" />
                <col width="38%" />
				<tr>
                	<th></th>
					<th class="a-left">网点名称</th>
					<th>额度（元）</th>
				</tr>
            </table>
            <table cellpadding="0" cellspacing="0" class="pop-table bd0" id="dirMerShow">
				<col width="47%" />
				<thead>
				<tr>
					<th>网点名称</th>
					<th>额度（元）</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table></div>
			<ul class="ul-btn" style="padding:15px 0 ; margin-left:95px;">
				<li><a href="javascript:;" class="pop-borange"  onclick="transferPopclo();popclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div> 
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
	//顶部提示信息
	var timer= setInterval(function(){
		$('.warning span').animate({'left':-230+'px'},10000,"linear").animate({'left':$('.warning').width()+'px'},0);
		},500);
	
	var setcity=0;
	$('[js="setCity"]').click(function(event){
		  event.stopPropagation();
		$('.set-city').show();
		setcity=1;
	});
	
	$('body').click(function(){
		if(setcity=1){
			$('.set-city').hide();
		}
	});
	
	$('[js="qurren"]').click(function(){
		$('[js="qurrenjiner"]').show();
		
	});
	

	$('.set-city-list li').click(function(event){
		event.stopPropagation();
		var i=$(this).index();
		$('.set-city-list li').find('a').removeClass('on');
		$(this).find('a').addClass('on');
		$('.set-city-dl').eq(i).show().siblings('.set-city-dl').hide();
	});
	$('.set-city-dl li a').click(function(event){
		event.stopPropagation();
	});
	
	
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
	$('.header-nav ul li').click(function(){
		var i=$(this).index();
		$('.header-nav ul li a').removeClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show();
		$(this).find('a').addClass('on');
	});
	if($('.header-inr-nav ul li a').hasClass('cur')){
		var i=$('.cur').closest('ul').index();
		$('.header-nav ul li a').removeClass('on').eq(i).addClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show()
	};
});

function popclo(obj){
	$(obj).closest('.pop-win').hide();
}


</script>
</body>
<!-- InstanceEnd --></html>

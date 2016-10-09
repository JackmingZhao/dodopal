<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>折扣管理</title>
<script src="${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js"></script>
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script type="text/javascript">
var baseUrl = "${base}";
</script>
<script type="text/javascript" src="${base}/js/merchant/rebate/merDiscount.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
</head>
<body>
<#include "../../header.ftl"/>
<div class="con-main" id="merDiscountMain"> <!-- InstanceBeginEditable name="main" -->
	<div class="seach-top-box com-top-bor">
		<div class="seach-top-inner">
			<form id="queryForm" action="/">
				<ul class="clearfix">
					<li>
						<span>启用标识：</span>
							<select id="fag" name="fag">
								<option value="0" selected>启用</option>
								<option value="1">停用</option>
								<option value="" >全部</option>
							</select>
						
					</li>
					<li class="btn-list">
					<@sec.accessControl permission="merchant.rebate.find">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findMerDiscountList()"/>
					</@sec.accessControl>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryForm')"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box ">
		<ul class="navi-ul clearfix">
		<li>
				<@sec.accessControl permission="merchant.rebate.add">
					<a href="${base}/merchant/merDiscountAdd" class="orange-btn-h22">新增</a>
				</@sec.accessControl>
			</li>
			<li>
				<@sec.accessControl permission="merchant.rebate.activate">
					<a href="javascript:void(0);" onclick="startOrStopUser(true);" id="qiyong"  disabled="disabled" class="gray-btn-h22">启用</a>
				</@sec.accessControl>
			</li>
			<li>
				<@sec.accessControl permission="merchant.rebate.inactivate">
					<a href="javascript:void(0);" onclick="startOrStopUser(false);" id="tingyong"  disabled="disabled" class="gray-btn-h22">停用</a>
				</@sec.accessControl>
			</li>
			
		</ul>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id="displayTbl">
			<col width="2%" />
			<col width="4%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
			<col width="18%" />
			<col width="10%" />
			<col width="2%" />
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center"><input type="checkbox" onclick="toggle(this,'merDiscount');toggleActivateBtn('merDiscount');" /></th>
				<th>序号</th>
				<th>折扣(%)</th>
				<th>排序号</th>
				<th>启用标识</th>
				<th>创建时间</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<p class="page-navi" id="merDiscountPaginator"></p>
	</div>
	<!-- InstanceEndEditable --> </div>
	<#include "merDiscountDetail.ftl"/>
<#include "../../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('[js="qiyong"]').click(function(){
		$('[js="qiyongBox"]').show();
	});

	$('[js="tingyong"]').click(function(){
		$('[js="tingyongBox"]').show();
	});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="折扣管理"){
			$(this).addClass('cur');
		}
	});
	if($('.com-table01 :checked').length>0){
		$('[js="qiyong"]').attr('class','orange-btn-h22');
		$('[js="tingyong"]').attr('class','orange-btn-h22');
	};
	$('.com-table01 [type="checkbox"]').click(function(){
		if($('.com-table01 :checked').length>0){
			$('[js="qiyong"]').attr('class','orange-btn-h22');
			$('[js="tingyong"]').attr('class','orange-btn-h22');
		}else{
			$('[js="qiyong"]').attr('class','gray-btn-h22');
			$('[js="tingyong"]').attr('class','gray-btn-h22');
		};
	});

});
</script>
<div class="pop-win" js="tingyongBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确认停用？</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="pop-win" js="qiyongBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确启用？</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
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

<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>子商户交易记录--详情</title>
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script src="${base}/js/ddp/recharge.js"></script>
<script src="${base}/js/portalUtil.js"></script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
<h3 class="tit-h3">交易详情</h3>
	<div class="com-bor-box com-bor-box01">
		
			<h4 class="times">${(payTraTransaction.tranOutStatus)!}</h4>
			<ul class="times-info">
            	<li>订单时间：
            	<#if payTraTransaction.orderDate??>
				${payTraTransaction.orderDate?string("yyyy-MM-dd HH:mm:ss")}
				<#else>
				&nbsp;
				</#if></li>
				<li>交易时间：
				<#if payTraTransaction.createDate??>
				${payTraTransaction.createDate?string("yyyy-MM-dd HH:mm:ss")}
				<#else>
				&nbsp;
				</#if></li>
            </ul>
			<form action="/">
			<div class="w890 mb0">
			<table cellspacing="0" cellpadding="0" border="0" class="com-table01">
			<colgroup>
            <col width="19%">
			<col width="19%">
			<col width="8%">
            <col width="8%">
            <col width="8%">
			<col width="8%">
			<col width="8%">
			</colgroup>
            <tbody>
            <tr>				
				<th>交易流水号</th>
				<th>订单编号</th>
				<th>业务名称</th>
                <th>交易类型</th>
				<th>应付金额（元）</th>
                <th>实付金额（元）</th>
			</tr>
			<tr>
				<td>${(payTraTransaction.tranCode)!''}</td>
                <td>${(payTraTransaction.orderNumber)!''}</td>
				<td>${(payTraTransaction.businessType)!''}</td>
                <td>${(payTraTransaction.tranType)!''}</td>
                <td>${(payTraTransaction.amountMoney)!''}</td>
				<td>${(payTraTransaction.realTranMoney)!''}</td>
			</tr>
		</tbody></table>
		<div class="null-box"></div>
		
			<div class="a-center btn-box"><a href="javascript:history.go(-1);" class="orange-btn-h32">返回</a></div>
			</div>
			
		</form>
	</div>
	
	<!-- InstanceEndEditable --> </div>
<#include "../../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="子商户管理"){
			$(this).addClass('cur');
		}
	});
	
});
</script>

<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
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

$('.footer-navi .more a').click(function(){
	if($(this).hasClass('open')){
		$(this).removeClass('open');
		$('.footer-navi ul').height(60);
	}else{
		$(this).addClass('open');
		$('.footer-navi ul').removeAttr('style');
	};

});


</script>
<script src="js/select.js" type="text/javascript"></script>
</body>
<!-- InstanceEnd --></html>

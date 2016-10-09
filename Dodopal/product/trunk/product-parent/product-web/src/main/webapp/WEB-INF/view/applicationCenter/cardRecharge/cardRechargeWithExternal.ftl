<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>一卡通充值</title>
<#include "../../include.ftl"/>
<!-- InstanceEndEditable -->
<script>
var operatorId = '${externalOperatorId}';
window.history.forward(1);
</script>
<script src="${base}/js/applicationCenter/ocxCommon.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/cardRechargeWithExternal.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/AreaWordCreate.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/JsonMapForDllWithExternal.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />

<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="application-center clearfix">
		<div class="app-right">
	<div class="app-pay-way tit-h3">
	<a   href="javascript:;" class="blue">一卡通充值</a>
	</div>
			<dl class="app-dl clearfix com-bor-box com-bor-box01 app-dl-s app-dl-charge">
				<dt>所在城市：</dt>
				<#include "../ddArea.ftl"/>
				<dt>充值金额：</dt>
				<dd class="recharge-amount" id="proPrice"></dd>
				<div   id="posSpan" style="display:none">
				<dt>充值订单：</dt>
				<dd class="recharge-amount" id="orderSpan"></dd>
				</div>
				<dt>卡号：</dt>
				<dd class="clearfix">
					<p id="cardNumWarn" class="er-set"><i></i><span id="cardNumMessage">请正确接入设备</span></p>
					<p ><i></i><span id="cardNum"></span></p>
				</dd>
				<dt>卡余额：</dt>
				<dd>
				<p id="cardMoneyP" style="display:none"><i></i><span id="cardMoney"></span><span>元</span></p>
				</dd>
				<dt></dt>
				<dd class="btn-recharge"><a href="javascript:checkWarnDiv();" style="background-color:#666666;" class="orange-btn-h32" >立即支付</a></dd>
			</dl>
			<dl class="app-dl02">
				<dt>充值提示：</dt>
				<dd>1.在整个充值过程中，请不要移动卡片；<br />
					2.在显示充值结果前，请不要关闭、刷新或后退浏览器窗口；<br />
					3.对于未提示充值成功的交易，请再次查询余额，通过对比卡内金额是否增加判断最终结果。</dd>
			</dl>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>

<!-- InstanceBeginEditable name="pop" --> 
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
	var timer=null;
	


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
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="应用产品"){
			$(this).addClass('cur');
		}
	});
	
	
	$('[js="pay-list"] li:gt(4)').hide();
	$('[js="pay-list"] li.only').show();
	var a=0;
	$('[js="pay-list"] li.only').click(function(){
		if(a==0){
			$('[js="pay-list"] li').show();
			a=1;
		}else{
			$('[js="pay-list"] li:gt(4)').hide();
			$('[js="pay-list"] li.only').show();
			a=0;
		}		
	});
$('.recharge-amount a').click(function(){
	$(this).addClass('a-click').siblings("a").removeClass("a-click");
});
$('.payway-ul input').click(function(){
	if(!$(this).attr('checked')){
		$(this).closest('.payway-ul').addClass('payway-ul-click');
	}else{
		$(this).closest('.payway-ul').removeClass('payway-ul-click');
	}
	
})
  $(".pay-navi-ul li").each(function(i){
  $(".pay-navi-ul li").eq(i).click(function(){
  $(this).addClass("on").siblings("li").removeClass("on");
  $(".pay-way-box ul").eq(i).show().siblings().hide();
  });
 });
 
 
});



</script>
<div style="display: none;" js="qurrenjiner" class="pop-win" id="formRecharge">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:310px; width: 410px; margin-top: -160px;"></div>
	<div class="pop-box" style="height:300px; width: 400px; margin-top: -155px;">
		<h3>支付确认</h3>
		<div class="popConts">
			<ul class="jige-ul mb20">
				<li><em>商品名称：</em><span id="commodityName"></span></li>
				<li> <em>充值金额：</em><span class="orange" id="rechargeMoney"></span>元</li>
				<div id="realMoneyDiv">
				<li><em>实付金额：</em><span class="orange" id="realMoneyRes">0</span>元</li>
				</div>
				<li> <em>卡内余额：</em><span class="orange" id="balance"></span>元</li>
			</ul>
			<p class="mb10">
			</p>
		</div>
		<ul class="ul-btn ul-btn01 ul-btn02">
			<li><a onclick="createLagOrder();" class="pop-borange" href="javascript:;">确认</a></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:;">取消</a></li>
		</ul>
	</div>
</div>
<div style="display: none;" js="qurrenok" class="pop-win" id="resultDiv">
	<div class="bg-win"></div>
<div class="pop-bor" style="height:360px; width: 490px; margin-top: -205px;"></div>
	<div class="pop-box" style="height:350px; width: 480px; margin-top: -200px;">
		<h3>充值结果</h3>
		<div class="popConts" style="padding:20px 0px 0 80px;">
			<ul class="jige-ul mb20">
				<li><em>订单编号：</em><span id="orderNumRes"></span></li>
				<li><em>卡号：</em><span id="cardNumRes"></span></li>
				<li><em>充值前卡内余额：</em><span class="orange" id="befCardMoneyRes"></span>元</li>
				<li><em>充值后卡内余额：</em><span class="orange" id="behCardMoneyRes"></span>元</li>
				<li><em>充值金额：</em><span class="orange" id="rechargeMoneyRes"></span>元</li>
				<#--<li><em>订单应收金额：</em><span class="orange" id="realMoneyRes">0</span>元</li>-->
			</ul>
			<div class="wait-result" style="display:none;padding-right: 50px;"><i></i></div>
			<div class="ok-tips mb20" style="display:none;margin-left:75px;" id="rSuccessMassage"><i></i>一卡通充值成功！</div>
			<div class="err-tips mb20" style="display:none;margin-left:75px;" id="rFailMassage"><i></i>一卡通充值失败！</div>

		</div>
		<ul class="ul-btn ul-btn02" style="display:none;margin-left:140px;">
			<li><a onclick="popclo(this)" class="pop-borange" href="javascript:;">确认</a></li>
			<li><a class="pop-bgrange" onclick="toPrinter()" id="printButton" href="javascript:;">打印</a></li>
		</ul>
	</div>
</div>
<div id="" style="display:none;">
	<ul class="jige-ul mb20" id="printSpan">
		<li >商户名称：<span id="merName"></span></li>
		<li>城卡公司名称：<span id="printCompanyName"></span></li>
		<li>订单号：<span id="printOrderNum"></span></li>
		<li>充值设备号：<span id="printPosCode"></span></li>
		<li>充值前金额：<span class="orange" id="printBefCardMoney"></span>元</li>
		<li>充值后金额：<span class="orange" id="printBehCardMoney"></span>元</li>
		<li>充值金额：<span class="orange" id="printRechargeMoney"></span>元</li>
		<li>实付金额：<span class="orange" id="printRealPayMoney"></span>元</li>
		<li>卡    号：<span id="printCardCode"></span></li>
		<li>充值时间：<span id="printConsumeTime"></span></li>
	</ul>
</div>

<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('[js="clopop"]').click(function(){
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

</body>
<!-- InstanceEnd --></html>

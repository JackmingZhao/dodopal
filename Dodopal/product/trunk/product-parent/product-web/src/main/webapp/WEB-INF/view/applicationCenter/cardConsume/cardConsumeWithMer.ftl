<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<title>一卡通消费</title>
<#include "../../include.ftl"/>
<!-- InstanceEndEditable -->
<script type='text/javascript' src='${scriptUrl}/util/moneyFormatter.js'></script>
<script src="${base}/js/applicationCenter/cardConsume/cardConsume.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/ocxCommon.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/AreaWordCreate.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardConsume/consumeJsonMap.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wd-header">
	<div class="w1030 clearfix"> <a class="dodopal-logo" href="#"></a>
		<div class="dodopal-title">
			<p>一卡通消费</p>
		</div>
	</div>
</div>
<div class="con-main">
<div class="application-center clearfix">
	  <div class="app-right">
		<#include "../consumeNav.ftl"/>
		  <dl class="app-dl clearfix com-bor-box com-bor-box01 app-dl-s app-dl-charge">
			  <dt>所在城市：</dt>
			 <#include "../ddArea.ftl"/>
			 <div style='display:none;'>
			 <dt>商户折扣：</dt>
			  <dd>
			  <select id="discount"  name="discount" onchange="showRealMoney();">
			  </select>&nbsp;折
			  </dd>
			  </div>
			  <dt>刷卡金额：</dt>
			  <dd class="recharge-amount">
			  <label for="">
			  <input type="text" id="moneyInput" style="width:260px" onclick="initMoney(this,showRealMoney);"  style="text-align:right"/>&nbsp;元</label>
			  </dd>
			  <dt>用户折扣：</dt>
			  <dd>
			  <span id='ddpDiscount'></span>
			  &nbsp;折
			  </dd>
			  <dt>实付金额：</dt>
			  <dd class="recharge-amount" >
			  <p><i></i><span id="keyDownShow" class='orange'></span><span id="keyDownShowSpan" style="display:none">元</span></p>
			  </dd>
			  <div style="display:none;">
				  <dt>卡号：</dt>
				  	<dd class="clearfix">
						<p idas="cardNumWarn" style="display:none" class="er-set"><i></i><span id="cardNumMessage">请正确接入设备</span></p>
						<p ><i></i><span></span></p>
					</dd>
				  	<dt>卡余额：</dt>
				  	<dd>
				  	<p id="cardMoneyP" style="display:none"><i></i><span id="cardMoney"></span><span>元</span></p>
				  	</dd>
				  	
			  </div>
			  <dt></dt>
			 
			  <dd class="btn-recharge"><a href="javascript:;" class="orange-btn-h32" js="qurren" onclick="toConsume();" >刷卡消费</a></dd>
		  </dl>
		  <dl class="app-dl02">
			  <dt>消费说明：</dt>
			 <dd>1.在整个消费过程中，请不要移动卡片；<br />
					2.在显示消费结果前，请不要关闭、刷新或后退浏览器窗口；<br />
					3.对于未提示消费成功的交易，请再次查询余额，通过对比卡内金额是否减少判断最终结果。</dd>
			</dl>
	  </div>
	</div>
	</div>
<#include "../../footLog.ftl"/>

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
<!-- 余额不足信息提示开始 -->
<div class="pop-win" id="warnConsumeDiv" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons2"></div>
            <span>卡内余额不足，无法完成支付！</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="cancelWarnDiv();">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange" onclick="cancelWarnDiv();">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- 余额不足信息提示结束 -->
<div style="display: none;" id="infoWarnDiv" class="pop-win">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:310px; width: 410px; margin-top: -160px;"></div>
	<div class="pop-box" style="height:300px; width: 400px; margin-top: -155px;">
		<h3>提示信息</h3>
		<div class="popConts popConts01">
			<ul class="jige-ul mb20">
				<li> <em>支付方式：</em><span id="payWayName">一卡通消费</span></li>
				<li> <em>应付金额：</em><span id="sPayMoney">一卡通消费</span></li>
				<li> <em>折扣：</em><span class="orange" id="payDiscount"></span>折</li>
				<li> <em>实付金额：</em><span class="orange" id="payMoney"></span>元</li>
			</ul>
			<p class="mb10">
				<label style="padding-left:50px;">
					<input type="checkbox" id="payWarnFlag"/>
					不再提示此消息</label>
			</p>
		</div>
		<ul class="ul-btn ul-btn01 ul-btn02">
			<li><a  class="pop-borange" href="javascript:;" onclick="sureConsume();">确认</a></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:cancelOrder();">取消</a></li>
		</ul>
	</div>
</div>
<div style="display: none;"  id="resultDiv" class="pop-win">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:360px; width: 490px; margin-top: -205px;"></div>
	<div class="pop-box" style="height:350px; width: 480px; margin-top: -200px;">
		<h3>支付结果</h3>
		<div class="popConts popConts01" style="padding:20px 0px 0 80px;">
			<ul class="jige-ul mb20">
				<li><em>订单编号：</em><span id="orderNumRes"></span></li>
				<li><em>卡号：</em><span id="cardNum"></span></li>
				<li><em>支付金额：</em><span class="orange" id="payMoneySpan"></span>元</li>
				<li><em>订单应付金额：</em><span class="orange" id="sMoneySpan"></span>元</li>
				<li><em>订单实付金额：</em><span class="orange" id="rMoneySpan"></span>元</li>
				<li><em>支付前卡内余额：</em><span id="beforCardUnit" style="display:none;" ><span class="orange" id="beforCardMoneySpan"></span>元</span></li>
				<li id="afterMoneyLi"><em>支付后卡内余额：</em><span id="afterCardUnit" style="display:none;"  ><span class="orange" id="afterCardMoneySpan"></span>元</span></li>
			</ul>
			<div class="wait-result wait-result01" style="display:none;padding-right:95px;"><i></i>
			</div>
				<div class="ok-tips mb20" style="display:none;margin-left:75px;" id="rSuccessMassage"><i></i>一卡通支付成功！</div>
				<div class="err-tips mb20" style="display:none;margin-left:25px;" id="rFailMassage"><i></i><li id="failMessage"></li></div>
		</div>
		<ul class="ul-btn ul-btn02" style="display:none;margin-left:140px;">
			<li><a onclick="popclo()" class="pop-borange" href="javascript:;">确认</a></li>
			<li ><a class="pop-bgrange" onclick="toPrinter()" id="toPrinter"   href="javascript:;">打印</a></li>
		</ul>
	</div>
</div>
<div style="display:none;">
<#--	<li><em>消费金额：</em><span class="orange" id="printPayMoneySpan"></span>元</li>-->
	<li><em>折    扣：</em><span id="printPayDiscountSpan">无</span></li>
	<ul class="jige-ul mb20" id="printSpan">
		<li style="display:none">商户名称：<span id="merName"></span></li>
		<li><em>订单号：</em><span id="printOrderNumRes"></span></li>
		<li><em>折    扣：</em><span id="printDdpDiscountSpan">无</span></li>
		<li><em>应付金额：</em><span class="orange" id="printSMoneySpan"></span>元</li>
		<li><em>实付金额：</em><span class="orange" id="printRMoneySpan"></span>元</li>
		<li><em>原有金额：</em><span class="orange" id="printBeforCardMoneySpan"></span>元</li>
		<li><em>卡 余 额：</em><span class="orange" id="printCardMoneySpan"></span>元</li>
		<li>POS   号：<span id="printPosCode"></span></li>
		<li>卡    号：<span id="printCardCode"></span></li>
		<li>交易时间：<span id="printConsumeTime"></span></li>
	</ul>
</div>
<script type="text/javascript">
$(document).ready(function(e) {
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
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
</html>

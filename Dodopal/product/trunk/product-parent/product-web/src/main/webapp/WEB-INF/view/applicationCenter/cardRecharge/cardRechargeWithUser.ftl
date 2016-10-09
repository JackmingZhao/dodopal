<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>一卡通充值</title>
<#include "../../include.ftl"/>
<!-- InstanceEndEditable -->
<script src="${base}/js/applicationCenter/ocxCommon.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/cardRechargeWithUser.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/AreaWordCreate.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/JsonMapForDll.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />

<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../../headTitle.ftl"/>
<input id="recResult" value="false" type="hidden"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="application-center clearfix">
		<div class="app-right">
			<#include "../appNav.ftl"/>
			<dl class="app-dl clearfix com-bor-box com-bor-box01 app-dl-s pd2070">
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
			  	<#--圈存不显示区域-->
			  	<div id="payWayDiv">
			  	<dt class="pt10">充值方式：</dt>
				<dd>
				  <div class="pay-way">
				  <div id="accountDiv" style="display:none">
				  <label>
					  <ul class="payway-ul clearfix">
						  <li class="fl">
								  <span id="accountIdDiv">
								  <input type="radio" id="accountId" onclick="checkAccount();" name="payWayRadio" value="" />
								  </span>
								  账户余额：<span id="accountMoney"></span> 元
						  </li>
						  <span id="needPaySpan" style="display:none">
						  <li class="fr">－<i class="orange" id="needPayMoney"></i> 元</li>
						  </span>
						  <s></s>
					  </ul>
					  </label>
					  </div>
					  <div class="pay-line"></div>
					 <ul class="clearfix">
					 	<div id="payWayCommonDiv" style="display:none;">
                    	<li class="fl" js="pay-way-common" style="cursor:pointer;">常用支付方式</li>
                        <li class="fl">&nbsp;|&nbsp;</li>
                        </div>
					    <li class="fl blue" js="pay-way-other" style="cursor:pointer;">其他支付方式</li>
					    <div id="payAgainDiv" style="display:none">
					    	<li class="fr">还需支付 <i class="orange" id="payAgain"></i> 元</li>
					    </div>
				    </ul>
                    <div class="pay-way-box2 current" id="payCommonDiv" js="pay-list1" style="dispaly:none; border:1px solid #ccc;margin-top:10px">
                    <p class="recharge_mode"></p>
                    <ul class="pay-way-list clearfix" id="payCommonUl">
                    </ul>
                    </div>
				    <ul class="pay-navi-ul clearfix" js="pay-way-ul" id="otherPayNav">
					    <li class="on"><a href="javascript:void(0);">网银支付</a></li>
					    <li><a href="javascript:void(0);">第三方支付</a></li>
				    </ul>
				    <div class="pay-way-box" id="payWayBox">
					    <ul class="pay-way-list clearfix" id="payBankList" js="pay-list">
						    
					    </ul>
					    <ul class="pay-way-list clearfix" id="payOnLineList" style="display:none;">
					    
					    </ul>
				    </div>
			    </div>
                </dd>
                </div>
                <#--圈存不显示结束-->
				  <dt></dt>
			  <dd class="btn-recharge"><a href="javascript:checkWarnDiv();" style="background-color:#666666;" class="orange-btn-h32" js="qurren">立即充值</a></dd>
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

<#include "../../footLog.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
function fnokbtn(obj){
	var timer=null;
	$(obj).closest('[js="qurrenjiner"]').hide();
	$('[js="qurrenok"]').show();
	/*$('.wait-result').show();
	clearTimeout(timer);
	$('.ul-btn,.ok-tips,.err-tips').hide();
	timer=setTimeout(function(){
	$('.wait-result').slideUp(500);
	$('.ul-btn').slideDown();
	$('.ok-tips').slideDown(500);
}, 5000);*/
		
}
function fnokbtn1(obj){
	var timer=null;
	$(obj).closest('[js="qurrenok"]').hide();
	$('[js="qurrenok1"]').show();
	$('.wait-result').show();
	clearTimeout(timer);
	$('.ul-btn,.ok-tips,.err-tips').hide();
	timer=setTimeout(function(){
	$('.wait-result').slideUp(500);
	$('.ul-btn').slideDown();
	$('.ok-tips').slideDown(500);
}, 5000);
		
}



</script>
<div style="display: none;"  class="pop-win" id="formRecharge" js="qurrenjiner">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:310px; width: 410px; margin-top: -160px;"></div>
	<div class="pop-box" style="height:300px; width: 400px; margin-top: -155px;">
		<h3>支付确认</h3>
		<div class="popConts">
			<ul class="jige-ul mb20">
				<li><em>商品名称：</em><span id="commodityName"></span></li>
				<li> <em>支付方式：</em><span class="orange" id="paySureTitle"></span></li>
				<li> <em>支付金额：</em><span class="orange" id="paySureMoney"></span>元</li>
				<li style="display:none" id="payServiceRateMoneyLi"> <em>支付服务费：</em><span class="orange" id="payServiceRateMoney"></span>元</li>
			</ul>
			<p class="mb10">
				<label style="padding-left:50px;">
					<input type="checkbox" id="payWarnFlag" value="1"/>
					不再提示此消息</label>
			</p>
		</div>
		<ul class="ul-btn ul-btn01 ul-btn02">
			<li><a onclick="toBankGateway();" class="pop-borange" href="javascript:;">确认订单</a></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:;">取消</a></li>
		</ul>
	</div>
</div>
<div style="display: none;" id="waitResult" class="pop-win">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:260px; width: 390px; margin-top: -205px;"></div>
	<div class="pop-box" style="height:250px; width: 380px; margin-top: -200px;">
		<h3>支付提示</h3>
        <h4 style="padding-left:100px;">请您在新打开的页面上完成支付</h4>
		<p style="padding-left:90px;">支付完成前，请不要关闭窗口。</p>
        <p style="padding-left:90px;">支付完成后，请根据支付情况点击下面的按钮。</p>
		<ul class="ul-btn ul-btn02" style="margin-left:90px;margin-top:30px;">
			<li><a  class="pop-borange" href="javascript:;" id="regDoneButton" >支付完成</a></li>
			<li><a class="pop-bgrange" href="javascript:;" onclick="cancelBindCard()">支付遇到问题</a></li>
		</ul>
	</div>
</div>
<div class="pop-win" id="bindCardDiv" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:270px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px;height:260px;">
		<h3>绑卡提示信息</h3>
		<div class="innerBox">
			<h4>您可以绑定该卡(绑卡后可获积分)</h4>
			<form action="/">
				<table  class="base-table">
					<col width="105" />
					<tr>
						<th class="w128">卡号：</th>
						<td>
							<span id="bindCardCode"></span>
						</td>
					</tr>
                    <tr>
						<th>备注：</th>
						<td><input name=ye id="bindCardRemarks" class="com-input-txt w260 area-w268-s" onkeydown="checkMaxlength(this,200);" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200">
						</td>
					</tr>
					<tr>
						<td colspan="2" class="a-center"><input type="button" onclick="bindCard();" id="bindButton" class="pop-borange mr20" value="绑定">
							<a href="javascript:;" onclick="cancelBindCard();" class="pop-bgrange" >取消</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">

$('[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
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
</body>
</html>

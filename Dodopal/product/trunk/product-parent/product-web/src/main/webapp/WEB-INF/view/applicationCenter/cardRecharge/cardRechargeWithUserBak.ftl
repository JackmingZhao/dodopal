<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>应用中心</title>
<!-- InstanceEndEditable -->
<script type='text/javascript' src='${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js'></script>
<script src="${base}/js/applicationCenter/cardRecharge/cardRechargeWithMer.js" type="text/javascript"></script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<div class="header-top">
	<div class="w1030 clearfix">
		<p class="header-top-left" title="都都宝-24小时免费服务电话"><i class="phone-icon"></i>400-817-1000</p>
		<ul class="header-top-right clearfix">
			<li><a href="javascript:void(0);">你好，漕河泾<i class="ico_name"></i></a></li>
			<li><a href="javascript:void(0);">退出</a></li>
			<li><a href="javascript:void(0);">帮助中心</a></li>
		</ul>
	</div>
</div>
<div class="wd-header">
	<div class="w1030 clearfix"> <a class="dodopal-logo" href="/"></a>
		<div class="header-nav">
			<ul class="clearfix">
				<li><a href="账户管理.html">我的都都宝<i></i></a></li>
				<li><a href="交易记录.html">交易记录<i></i></a></li>
			<li><a href="${base}/cardRecharge/toCardRecharge">应用中心<i></i></a></li>
				<li><a href="用户管理.html" class="on">商户管理<i></i></a></li>
			</ul>
		</div>
	</div>
</div>
<div class="header-inr-nav">
	<div class="w1030">
		<ul class="clearfix" style="display:none;">
			<li><a href="账户管理.html">首页</a></li>
			<li><a href="账户充值.html">账户充值</a></li>
			<li><a href="转账.html">转账</a></li>
			<li><a href="账户设置.html">账户设置</a></li>
			<li><a href="资金变更记录.html">资金变更记录</a></li>
			<li><a href="分润查询.html">分润查询</a></li>
		</ul>
		<ul class="clearfix" style="display:none;">
			<li><a href="#">交易记录</a></li>
		</ul>
		<ul class="clearfix" style="display:none;">
			<!-- InstanceBeginEditable name="inr-nav" -->
			<li><a href="一卡通充值-应用产品.html">应用产品</a></li>
			<li><a href="一卡通充值-订单查询.html">订单查询</a></li>
			<li><a href="一卡通充值-产品管理.html">产品管理</a></li>
			<!-- InstanceEndEditable -->
		</ul>
		<ul class="clearfix">
			<li><a href="用户管理.html">用户管理</a></li>
			<li><a href="角色管理.html">角色管理</a></li>
			<li><a href="POS管理.html">POS管理</a></li>
			<li><a href="子商户信息.html">子商户信息</a></li>
			<li><a href="部门管理.html">部门管理</a></li>
			<li><a href="人员管理.html">人员管理</a></li>
			<li><a href="商户信息-只读.html">商户信息</a></li>
		</ul>
	</div>
</div>
<div class="header-banner">
	<div class="w1030 clearfix">
		<div class="header-photo">
			<div class="bg01"></div>
			<img src="css/images/img_photo_little.png" alt="" /></div>
		<div class="header-txt-box">
			<p><i>上午好，漕河泾</i></p>
			<p><span>商户名称：漕河泾全家便利</span><i class="i-line">|</i><span>业务所在城市：南昌</span><i class="i-line">|</i><span class="span01">上次登录时间：2015-04-24  10：05：56</span></p>
		</div>
	</div>
</div>

<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="application-center clearfix">
	
		<div class="app-right">
			<dl class="app-dl clearfix">
				<dt>所在城市：</dt>
				<dd class="clearfix" id="city">
				</dd>
				<dt>充值金额：</dt>
				<dd class="recharge-amount" id="proPrice">
			
				</dd>
				<dt>卡号：</dt>
				<dd class="clearfix">
					<p class="er-set"><i></i>请正确接入设备</p>
				</dd>
				<dt>卡余额：</dt>
				<dd id="cardMoney"></dd>
				<dt>支付金额：</dt>
				<dd><i class="orange" id="payMoney"></i> 元</dd>
				<dt>支付方式：</dt>
				<dd>
					<div class="pay-way">
						<ul class="payway-ul clearfix">
							<li class="fl">
								<label>
									<input type="radio" name="" value="" />
									账户余额：15.00 元</label>
							</li>
							<li class="fr">－<i class="orange">30.00</i> 元</li>
							<s></s>
						</ul>
						<div class="pay-line"></div>
						<ul class="clearfix">
							<li class="fl blue">其他支付方式</li>
							<li class="fr">还需支付 <i class="orange">20.00</i> 元</li>
						</ul>
						<ul class="pay-navi-ul clearfix">
							<li class="on"><a href="javascript:void(0);">网银支付</a></li>
							<li><a href="javascript:void(0);">第三方支付</a></li>
							<li><a href="javascript:void(0);">预付费支付</a></li>
						</ul>
						<div class="pay-way-box">
							<ul class="pay-way-list clearfix" js="pay-list">
								<li>
									<label><span class="prompt-span01">充值享9.5折优惠</span>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label><span class="prompt-span01">充值享9.5折优惠</span>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label><span class="prompt-span02">企<br />
										业</span>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li class="more-link only"><a href="javascript:;"><i class="i-more"></i>更多</a></li>
							</ul>
							<ul class="pay-way-list clearfix" style="display:none;">
								<li>
									<label><span class="prompt-span01">充值享8.5折优惠</span>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
							</ul>
							<ul class="pay-way-list clearfix" style="display:none;">
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label><span class="prompt-span01">充值享9.5折优惠</span>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
								<li>
									<label>
										<input type="radio" name="pay01" value="" />
										<span class="span-img"><img src="css/images/img_pay01.png" alt="" /></span></label>
								</li>
							</ul>
						</div>
					</div>
					<a href="javascript:;" class="orange-btn-h32" js="qurren" >立即充值</a> </dd>
			</dl>
			<dl class="app-dl02">
				<dt>充值提示：</dt>
				<dd>1、充值充值提示提示<br />
					2、充值充值充值<br />
					3、提示提示</dd>
			</dl>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<#include "../../footer.ftl"/>
<script type="text/javascript">
function fnokbtn(obj){
	$(obj).closest('[js="qurrenjiner"]').hide();
	$('[js="qurrenok"]').show();
}
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
<!-- InstanceBeginEditable name="pop" --> 
<div style="display: none;" js="qurrenjiner" class="pop-win">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:310px; width: 410px; margin-top: -160px;"></div>
	<div class="pop-box" style="height:300px; width: 400px; margin-top: -155px;">
		<h3>提示信息</h3>
		<div class="popConts">
			<p class="f18 a-center mb20">请确认以下信息是否正确！</p>
			<ul class="jige-ul mb20">
				<li><em>商品名称：</em>一卡通充值</li>
				<li> <em>充值金额：</em><span class="orange">50</span>元</li>
				<li> <em>卡内余额：</em><span class="orange">65</span>元</li>
			</ul>
			<p class="mb10">
				<label>
					<input type="checkbox" />
					不再提示此消息</label>
			</p>
		</div>
		<ul class="ul-btn ul-btn01">
			<li><a onclick="fnokbtn(this)" class="pop-borange" href="javascript:;">确认</a></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:;">取消</a></li>
		</ul>
	</div>
</div>
<div style="display: none;" js="qurrenok" class="pop-win">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:410px; width: 410px; margin-top: -205px;"></div>
	<div class="pop-box" style="height:400px; width: 400px; margin-top: -200px;">
		<h3>充值结果</h3>
		<div class="popConts">
			<div class="ok-tips"><i></i>一卡通充值成功！</div>
			<div class="err-tips" style="display:none"><i></i>一卡通充值失败！</div>
			<dl>
				<dt>原因：</dt>
				<dd>原因原因原因原因原因原因
					原因原因原因原因原因原因。</dd>
			</dl>
			<ul class="jige-ul mb20">
				<li><em>订单编号：</em>XXXXXXXXXX</li>
				<li><em>充值金额：</em><span class="orange">50</span>元</li>
				<li><em>充值前卡内余额：</em><span class="orange">65</span>元</li>
				<li><em>充值后卡内余额：</em><span class="orange">65</span>元</li>
				<li><em>订单应收金额：</em><span class="orange">65</span>元</li>
				<li><em>订单实收金额：</em><span class="orange">65</span>元</li>
			</ul>
		</div>
		<ul class="ul-btn ul-btn01">
			<li><a onclick="popclo(this)" class="pop-borange" href="javascript:;">确认</a></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:;">取消</a></li>
		</ul>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(e) {
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
<!-- InstanceEndEditable --> 
</body>
<!-- InstanceEnd --></html>

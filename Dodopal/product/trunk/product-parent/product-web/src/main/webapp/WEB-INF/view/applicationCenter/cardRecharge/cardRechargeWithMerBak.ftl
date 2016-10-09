<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>应用中心</title>
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>

<script src="${base}/js/applicationCenter/cardRecharge/cardRechargeWithMer.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/JsonMapForDll.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/index.css" rel="stylesheet" type="text/css" />
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
		<div class="app-left">
			<ul class="clearfix">
				<li class="snavi01"><a href="一卡通充值-应用产品.html" class="on"><span><i></i>一卡通充值</span></a></li>
				<li class="snavi02"><a href="批量充值-订单查询.html"><span><i></i>批量充值</span></a></li>
				<li class="snavi03"><a href="#"><span><i></i>手机话费</span></a></li>
				<li class="snavi04"><a href="#"><span><i></i>水费缴纳</span></a></li>
				<li class="snavi05"><a href="#"><span><i></i>电费缴纳</span></a></li>
				<li class="snavi06"><a href="#"><span><i></i>天然气缴纳</span></a></li>
				<li class="snavi07"><a href="#"><span><i></i>有线电视费</span></a></li>
			</ul>
		</div>
		<div class="app-right">
			<dl class="app-dl clearfix">
				<dt>所在城市：</dt>
				<dd class="clearfix" id="city">
				</dd>
				<dt>充值金额：</dt>
				<dd class="recharge-amount" id="proPrice">
				</dd>
				<div   id="posSpan" style="display:none">
				<dt>充值订单：</dt>
				 <dd  style="position:relative;">
				  <div class="recharge-order">
                  <table cellpadding="0" cellspacing="0" summary="" style="position:absolute;left:1px;*left:2px;left:2px\0;top:1px;background:#fff;width:481px;">
                      	<col width="10%" />
                        <col width="50%" />
                        <col width="20%" />
                        <col width="20%" />
						  <tr>
							  <th></th>
							  <th>订单号</th>
							  <th>充值金额（元）</th>
							  <th>来源</th>
						  </tr>
                     </table>
					  <table cellpadding="0" cellspacing="0" summary="" id="proList">
                      	<col width="10%" />
                        <col width="50%" />
                        <col width="20%" />
                        <col width="20%" />
                          <tr >
							  <th></th>
							  <th>订单号</th>
							  <th>充值金额（元）</th>
							  <th>来源</th>
						  </tr>
						  <tbody>
						  </tbody>
					  </table>
				  </div>
			  	</dd>
			    </div>
				<dt>卡号：</dt>
				<dd class="clearfix">
					<p id="cardNumWarn" class="er-set"><i></i><span id="cardNumMessage">请正确接入设备</span></p>
					<p ><i></i><span id="cardNum"></span></p>
				</dd>
				<div id="cardSpan" style="display:none">
				<dt>卡余额：</dt>
				<dd >
					<#--<p id="cardMoneyWarn" class="er-set"><i></i><span >请正确接入设备</span></p>-->
					<p id="cardMoneyP" style="display:none"><i></i><span id="cardMoney"></span><span>元</span></p>
				</dd>
				</div>
				<dt>
				</dt>
				<#--<dd class="btn-recharge"><a href="javascript:forCreateOrder();" class="orange-btn-h32" js="qurren">100笔充值</a>
				</dd>-->
				<dd class="btn-recharge"><a href="javascript:tocardRecharge();" class="orange-btn-h32" js="qurren">立即充值</a>
				</dd>
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
<div style="display: none;" js="qurrenjiner" id="formRecharge" class="pop-win">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:310px; width: 410px; margin-top: -160px;"></div>
	<div class="pop-box" style="height:300px; width: 400px; margin-top: -155px;">
		<h3>提示信息</h3>
		<div class="popConts">
			<p class="f18 a-center mb20">请确认以下信息是否正确！</p>
			<ul class="jige-ul mb20">
				<li><em>商品名称：</em>一卡通充值</li>
				<li> <em>充值金额：</em><span class="orange" id="rechargeMoney"></span>元</li>
				<li> <em>卡内余额：</em><span class="orange" id="balance"></span>元</li>
			</ul>
			<p class="mb10">
				<label>
					<input type="checkbox" />
					不再提示此消息</label>
			</p>
		</div>
		<ul class="ul-btn ul-btn01">
			<li><a onclick="createOrder()" class="pop-borange" href="javascript:;">确认</a></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:;">取消</a></li>
		</ul>
	</div>
</div>
<div style="display: none;" js="qurrenok" class="pop-win" id="resultDiv">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:410px; width: 410px; margin-top: -205px;"></div>
	<div class="pop-box" style="height:400px; width: 400px; margin-top: -200px;">
		<h3>充值结果</h3>
		<div class="popConts">
			<#--<div id="failDivRe" style="display:none">
				
				<dl>
					<dt>原因：</dt>
					<dd id="failMessage"> </dd>
				</dl>
			</div>-->
			<div id="successDivRe" >
				<div class="ok-tips" style="display:none" id="rSuccessMassage"><i></i>一卡通充值成功！</div>
				<div class="err-tips" style="display:none" id="rFailMassage"><i></i>一卡通充值失败！</div>
				<ul class="jige-ul mb20">
					<li><em>订单编号：</em><span id="orderNumRes"></span></li>
					<li><em>充值金额：</em><span class="orange" id="rechargeMoneyRes"></span>元</li>
					<li><em>充值前卡内余额：</em><span class="orange" id="befCardMoneyRes"></span>元</li>
					<li><em>充值后卡内余额：</em><span class="orange" id="hidCardMoneyRes"></span>元</li>
					<li><em>订单应收金额：</em><span class="orange" id="orderMoneyRes"></span>元</li>
					<li><em>订单实收金额：</em><span class="orange" id="realOrderMoneyRes"></span>元</li>
				</ul>
			</div>
		</div>
		<ul class="ul-btn ul-btn01">
			<li><a onclick="popclo(this)" class="pop-borange" href="javascript:;">确认</a></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:;">取消</a></li>
		</ul>
	</div>
</div>
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
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
<!-- InstanceEnd --></html>

<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<meta http-equiv="Pragma" contect="no-cache">
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<!-- InstanceBeginEditable name="doctitle" -->
<title>账户管理 | 账户充值</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<script type="text/javascript">
var payimg = "${styleUrl}/portal/images/";
var commonHidden = "false";
var payInfoFlag = "${payInfoFlag}";
var checkFlag = "false";
</script>
<script src="${base}/js/ddp/recharge.js"></script>
<script src="${base}/js/portalUtil.js"></script>
<script src="${base}/js/common/select.js" type="text/javascript"></script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->


</head>
<body>
<#include "../header.ftl"/>
<input type="hidden" name="merClassify" id="merClassify" value="${merClassify!}"></input>
<input type="hidden" name="merExternal" id="merExternal" value="${merExternal!}"></input>
<input type="hidden" name="merType" id="merType" value="${merType!}"></input>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
<form action = "accountRecharge" target="_blank" id="rechargeForm">
<input type="hidden" name="realMoney" id="realMoney" value=""></input>
	<div class="com-gray-box">
		<div class="com-ttl-box">账户充值<span class="span-border">|</span><a href="${base}/tran/record?merCode=${merCode}" class="blue">充值记录</a></div>
		<dl class="recharge-dl clearfix">
			<dt>充值金额</dt>
			<dd>
				<input type="text" id="money" name="money"  value="0.00" style="text-align:right;"  class="com-input-txt mr10" />
				元          <i id="msg" style="display:none;" class="red">请输入充值金额</i>
				<p>充值金额范围：<i class="red">0.01</i>～<i class="red">9999999.99</i>元</p>
			</dd>
		</dl>
	</div>
	<div class="com-gray-box02 mb0">
		<div class="com-ttl-box clearfix">
			<ul class="pay-navi-ul pay-navi-ul1 clearfix">
				<li class="on"><a href="javascript:void(0);">第三方支付</a></li>
				<li id="bankOn"><a href="javascript:void(0);">网银支付</a></li>
				 <#if merClassify??>
				  <#if merClassify=='1'>
				  <li><a href="javascript:void(0);">一卡通支付</a></li>
				  <#elseif merClassify=="0">
				 </#if>
				</#if>
			</ul>
			<div class="common current" id='commonDiv'>常用支付方式</div><span id='spanDiv' class="span-border2 span-border">|</span><div id="moreDiv" class="more" >更多支付方式</div>
		</div>
		<div class="pay-way-box pay-way-box1" id="payMore"></div>
		<div class="pay-way-box2 current" id="payCommon"></div>
		<div class="taCenter mt30" id="rechargeBtn" ><a href="javascript:;" js="del" class="orange-btn-h32">充值</a></div>
	</div>
	
	<div class="pop-win payment-message" js="delBox" style="display:none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>支付提示信息</h3>
		<div class="innerBox">
			<p class="taCenter pop-ttl">请您在新打开的页面上完成支付！</p>
			<div class="wait-box"></div>
			<p class="pop-txt"><span>支付完成前，请不要关闭此窗口<br />支付完成后，请根据支付情况点击下面的按钮</span></p>
			<ul class="ul-btn">
				<li><a href="${base}/index;" class="pop-borange"  onclick="popclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop1"  class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>

<div style="display: none;" js="qurrenjiner" class="pop-win" id="payInfo">
	<div class="bg-win"></div>
	<div class="pop-bor" style="height:310px; width: 410px; margin-top: -160px;"></div>
	<div class="pop-box" style="height:300px; width: 400px; margin-top: -155px;">
		<h3>账户充值</h3>
		<div class="popConts">
			<ul class="jige-ul mb20" id="rechargeDetail">
			</ul>
			<p class="mb10">
				<label style="padding-left:50px;">
					<input type="checkbox" />
					不再提示此消息</label>
			</p>
		</div>
		<ul class="ul-btn ul-btn01 ul-btn02">		
			<li><input type="submit" name="" value="确认"  js="delBox1" style="cursor: pointer;"  class="pop-borange"></li>
			<li><a class="pop-bgrange" js="clopop" href="javascript:;">取消</a></li>
		</ul>
	</div>
</div>
</form>
	<dl class="problem-list">
		<dt>常见问题列表：</dt>
		<dd>
			<ul>
				<li>1.在显示充值结果前，请不要关闭、刷新或后退浏览器窗口；</li>
				<li>2.对于未提示充值成功的交易，请再次查询余额，通过对比卡内金额是否增加判断最终结果；</li>
				<li>3.无法使用账户支付方式和一卡通支付方式给账户进行充值；</li>
				<li>4.因为银行提供的接口不同，不一定所有的银行都会提供信用卡充值通道。</li>
			</ul>
		</dd>
	</dl>
	<!-- InstanceEndEditable --> </div>
<#include "../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="账户充值"){
			$(this).addClass('cur');
		}
	});
  
});

$().ready(function(){
  $(".pay-navi-ul li").each(function(i){
  $(".pay-navi-ul li").eq(i).click(function(){
  $(this).addClass("on").siblings("li").removeClass("on");
  $(".pay-way-box ul").eq(i).show().siblings().hide();
  });
 });
});

</script>
<script type="text/javascript">
$(document).ready(function(e) {
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
	
	$('.bg-win,[js="clopop1"]').click(function(){
	    window.location.reload();
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


function reflush(){
   window.location.reload();
}

</script>
</body>
<!-- InstanceEnd --></html>
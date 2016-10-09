<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>账户管理 | 账户充值</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<script src="${base}/js/ddp/recharge.js"></script>
<script src="${base}/js/portalUtil.js"></script>
<script src="${base}/js/common/select.js" type="text/javascript"></script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../header.ftl"/>
<input type="hidden" name="merClassify" id="merClassify" value="${merClassify}"></input>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
<form action = "accountRecharge" target="_blank">
	<div class="com-gray-box">
		<div class="com-ttl-box">账户充值<span class="span-border">|</span><a href="#" class="blue">充值记录</a></div>
		<dl class="recharge-dl clearfix">
			<dt>充值金额</dt>
			<dd>
				<input type="text" id="money" name="money"  value="0.00" style="text-align:right;"  class="com-input-txt mr10" />
				元          <lable id="msg" style="display:none;" class="red">请输入充值金额</lable>
				<p>充值金额范围：<i class="red">0.01</i>～<i class="red">9999999.99</i>元</p>
			</dd>
		</dl>
	</div>
	<div class="com-gray-box02 mb0">
		<div class="com-ttl-box clearfix">
			<ul class="pay-navi-ul pay-navi-ul1 clearfix">
				<li class="on"><a href="javascript:void(0);">第三方支付</a></li>
				<li><a href="javascript:void(0);">网银支付</a></li>
				 <#if merClassify??>
				  <#if merClassify=='1'>
				  <li><a href="javascript:void(0);">一卡通支付</a></li>
				  <#elseif merClassify=="0">
				 </#if>
				</#if>
			</ul>
			<div class="common current">常用支付方式</div><span class="span-border2 span-border">|</span><div  class="more" >更多支付方式</div>
		</div>
		<div class="pay-way-box pay-way-box1" id="payMore"></div>
		<div class="pay-way-box2 current" id="payCommon"></div>
		<div class="taCenter mt30" id="rechargeBtn" ><input type="submit" name="" value="充值" js="del"  class="orange-btn-h32"></div>
	</div>
	</form>
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
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
	<dl class="problem-list">
		<dt>常见问题列表：</dt>
		<dd>
			<ul>
				<li>1、为什么默认支付方式选中了洪城一卡通，但是充值的时候没有这一项？<br />答：对不起，充账户时不允许实用账户支付方式和一卡通支付方式。</li>
				<li>2、没有开通银行卡网上支付，怎么用银行卡充值？<br />答：请联系相应银行，开通网上支付业务即可。</li>
				<li>3、其他问题。</li>
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

$('[js="del"]').click(function(){
       var money = $('#money').val();
       if(money=='0.00'|| money=='0' || money=='0.0'|| money==null || money==''){
        $('#msg').show();
        return false;
       }
       var payId1= $("#payMore input[name='payId']:checked").val();
       var payId2= $("#payCommon input[name='payId']:checked").val();
       if((payId1==null ||payId1=='')&&(payId2==null ||payId2=='')){
        $('#msg').text("  请选择支付方式");
        $('#msg').show();
         return false;
       }
        $('#msg').hide();
		$('[js="delBox"]').show();
});
$('.com-ttl-box .common').click(function(event) {
		$(this).addClass('current');
		$('.pay-way-box2').addClass('current');
		$('.pay-navi-ul1').removeClass('current');
		$('.pay-way-box1').removeClass('current');
		$('.com-ttl-box .more').removeClass('current');
	});
	$('.com-ttl-box .more').click(function(event) {
		$(this).addClass('current');
		$('.pay-navi-ul1').addClass('current');
		$('.pay-way-box1').addClass('current');
		$('.pay-way-box2').removeClass('current');
		$('.com-ttl-box .common').removeClass('current');
		
		$('[js="pay-list"] li:gt(6)').hide();
	    $('[js="pay-list"] li.only').show();
	    var a=0;
	    $('[js="pay-list"] li.only').click(function(){
		if(a==0){
			$('[js="pay-list"] li').show();
			a=1;
		}else{
			$('[js="pay-list"] li:gt(6)').hide();
			$('[js="pay-list"] li.only').show();
			a=0;
		}		
	})
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
</body>
<!-- InstanceEnd --></html>
<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>账户首页</title>
<!-- InstanceEndEditable -->
<#include "include.ftl"/>
<script type="text/javascript">
var baseUrl = "${base}/tran/tranDetail?";
</script>
<script src="${base}/js/index.js"></script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<table cellpadding="0" cellspacing="0" class="com-table02 mb20">
		<col width="75%" />
		<tr>
			<td>
				<div class="balance-div clearfix">
					<div class="fl">
						<p class="ttl01">账户可用余额：</p>
						<div class="price-div clearfix"><span>&yen;<i> ${availableMoney}</i></span>
						<@sec.accessControl permission="ddp.recharge"> 
			            <a href="${base}/ddp/recharge" id="rechargeA" class="mr20 orange-btn-h26">充值</a>
			       </@sec.accessControl><@sec.accessControl permission="ddp.transfer"><a href="${base}/ddp/transfer" id="transferA"  class="bule-btn-h26">转账</a></@sec.accessControl></div>
					</div>
					<div class="fr frozen-div">
						<#if frozenMoney == '0.00'>
						<p>
						<#else>
						<p title="咨询电话 400-817-1000">
						</#if>
						<span class="span-txt01">冻结</span><i>&yen; ${frozenMoney}</i></p>
						<div class="link-div">
						
						<#if sessionUser.merType =='10'>
						<@sec.accessControl permission="ddp.amt">
						<a href="${base}/ddp/showAccountChange" class="blue">资金变更记录</a></div>
						</@sec.accessControl>
						<#else>
						<a href="${base}/tran/record" class="blue">交易记录</a><span class="span-border">|</span><a href="${base}/ddp/showAccountChange" class="blue">资金变更记录</a></div>
						</#if>
					</div>
					
				</div>
			
			</td>
			<td class="bor-td-left"><dl>
					<dt>我的资产</dt>
					<dd>资金可用余额：<i class="blue">${accountMoney}</i> 元<br />
						资金冻结金额：<i class="blue">${accountFrozenAmount}</i> 元<br />
			<#if sessionUser.merType =='99'>
						已绑卡数：<i class="blue">${bindCardCount!0}</i> 张<br />
			<#else>
			       <#if fundType?? >
			           <#if fundType=='1' >
						授信可用余额：<i class="blue">${accountFuntMoney}</i> 元<br />
						授信冻结金额：<i class="blue">${accountFundFrozenAmount}</i> 元</dd>
				       <#elseif fundType=='0'>
				        &nbsp;
				      </#if>
				    <#else>
				        &nbsp;
				    </#if>
			</#if>
				</dl></td>
		</tr>
	</table>
	
    <div class="seach-top-box">	
	<div style="overflow:hidden;">
	<h3 class="tit-h3 pr"><span class="fl">交易记录</span><span class="fr yuan-font">金额单位(元)</span></h3>
    
	</div>
	<table id="displayTbl"  cellpadding="0" cellspacing="0" class="com-table01 mb20">
		<col width="2%" />
		<col width="3%" />
		<col width="21%" />
		<col width="9%" />
		<col width="10%" />
		<col width="9%" />
		<col width="8%" />
		<col width="10%" />
		<col width="8%" />
		<col width="14%" />
		<col width="5%" />
		<thead>
		<tr>
			<th></th>
			<th>序号</th>
			<th>交易流水号</th>
			<th>交易金额</th>
			<th>实际交易金额</th>
			<th>业务类型</th>
			<th>交易类型</th>
			<th>支付方式</th>
			<th>交易状态</th>
			<th>交易时间</th>
			<th style="text-align:center;">操作</th>
			<th></th>
		</tr>
	     </thead>
		<tbody>
		<!--<tr>
			<td colspan="10" class="no-data"><p>您当前没有交易记录明细</p></td>
		</tr>-->
		</tbody>
	</table>
	<div>
    	<p class="checking-all"><#if sessionUser.merType =='10'><#else><a id="lookInfo" href="${base}/tran/record" class="blue">查看所有交易记录</a></#if></p>
    </div>
	<!-- InstanceEndEditable --> </div>
	</div>
<#include "footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="首页"){
			$(this).addClass('cur');
		}
	});
});
</script>
<script type="text/javascript">
$(document).ready(function(e) {
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
	$('.header-nav ul li').click(function(){
		var i=$(this).index();
		$('.header-nav ul li a').removeClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show();
		$(this).find('a').addClass('on');
		var href = $('.header-inr-nav ul').eq(i).find('li').first().find('a').attr('href');
		if(href!='')
		window.location = href;
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

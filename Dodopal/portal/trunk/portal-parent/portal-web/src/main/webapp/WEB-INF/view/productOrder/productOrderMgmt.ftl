<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>应用中心</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<script type="text/javascript" src="../js/productOrder/productOrder.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<!-- InstanceEndEditable -->
<script src="${base}/js/applicationCenter/cardRecharge/cardRechargeWithMer.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#include "../headTitle.ftl"/>
<div class="con-main"  id="productOrderMain"> <!-- InstanceBeginEditable name="main" -->
	<div class="application-center clearfix">
			<div class="app-left">
			<ul class="clearfix">
				<li class="snavi01"><a href="${base}/productOrder/productOrderMgr" class="on"><span><i></i>一卡通充值</span></a></li>
				<li class="snavi02"><a href="#"><span><i></i>批量充值</span></a></li>
				<li class="snavi03"><a href="#"><span><i></i>手机话费</span></a></li>
				<li class="snavi04"><a href="#"><span><i></i>水费缴纳</span></a></li>
				<li class="snavi05"><a href="#"><span><i></i>电费缴纳</span></a></li>
				<li class="snavi06"><a href="#"><span><i></i>天然气缴纳</span></a></li>
				<li class="snavi07"><a href="#"><span><i></i>有线电视费</span></a></li>
			</ul>
		</div>
		<div class="app-right">
			<div class="seach-top-inner">
				<form id="queryForm" action="#">
					<ul class="clearfix">
						<li class="w310"><span class="w100">订单创建时间：</span>
							<input name="startday" type="text" class="com-input-txt w74"  placeholder="起始时间" id="sdate" readonly="true" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="startday" type="text" class="com-input-txt w74"  placeholder="结束时间" id="sdate" readonly="true" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						</li>
						<li>
							<label><span class="w80">订单编号：</span>
								<input type="text" class="com-input-txt w88"  id="proOrderNum" name="proOrderNum"/>
							</label>
						</li>
						<li><span class="w80">城市：</span>
							<input type="text" class="com-input-txt w88"  id="cityName" name="cityName"/>
						</li>
						<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findProductOrderByPage();"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryForm');"/>
					</li>
					</ul>
					<ul class="clearfix">
						<li class="w310 yuan"><span class="w100">充值金额范围：</span>
							<input type="text" class="com-input-txt w74"  id="txnAmtStart" name="txnAmtStart"/>
							-
							<input type="text" class="com-input-txt w74"  id="txnAmtEnd" name="txnAmtEnd"/>
						</li>
						<li>
							<label><span class="w80">POS号：</span>
								<input type="text" class="com-input-txt w88" id="posCode" name="posCode"/>
							</label>
						</li>
						<li>
							<label><span class="w80">卡号：</span>
								<input type="text" class="com-input-txt w88" id="orderCardno" name="orderCardno"/>
							</label>
						</li>
					</ul>
					<ul class="clearfix">
						<li><span class="w100">订单状态：</span>
							<select name="proOrderState" id="proOrderState">
							</select>
						</li>
					</ul>
				</form>
			</div>
			<div class="com-bor-box02">
				<ul class="navi-ul01 clearfix">
					<li class="fr">金额单位(元)</li>
				</ul>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id= "productOrderTbl">
				<colgroup>
				<col width="2%" />
				<col width="11%" />
				<col width="7%" />
				<col width="7%" />
				<col width="7%" />
				<col width="9%" />
				<col width="9%" />
				<col width="5%" />
				<col width="7%" />
				<col width="7%" />
				<col width="7%" />
				<col width="16%" />
				<col width="4%" />
				</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>订单编号</th>
					<th>城市</th>
					<th>充值金额</th>
					<th>实付金额</th>
					<th>充值前金额</th>
					<th>充值后金额</th>
					<th>利润</th>
					<th>POS号</th>
					<th>卡号</th>
					<th>订单状态</th>
					<th>订单创建时间</th>
					<th class="a-center">操作</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			</table>
			<div class="null-box" style="display:none;"></div>
			<p class="page-navi"><span class="fl">订单下载：<a js="dingdan" href="javascript:;">Excel格式</a></span>
			</p>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>

<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
	$(document).ready(function(e) {
		
		$('[js="dingdan"]').click(function(){
			$('[js="dingdanBox"]').show();
		});
		
		$('.header-inr-nav ul li a').each(function(){
			if($.trim($(this).text())=="订单查询"){
				$(this).addClass('cur');
			}
		});
		
	 
	});
</script>

<div class="pop-win" js="dingdanBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:180px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px;height:170px;">
		<h3>产品下载（Excel格式）</h3>
		<div class="innerBox">
			<form action="/">
				<table  class="base-table">
					<col width="105" />
					<tr>
						<th>下载到：</th>
						<td><input type=file name="j" style="display: none;" onchange="ye.value=value">
							<input name=ye class="com-input-txt w260">
							<input type=button value="浏览" onclick="j.click()" class="btn-orange"></td>
					</tr>
					<tr>
						<td colspan="2" class="a-center"><input type="submit" class="pop-borange mr20" value="确认">
							<a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></td>
					</tr>
				</table>
			</form>
		</div>
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
<#include "productOrderView.ftl"/>
<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

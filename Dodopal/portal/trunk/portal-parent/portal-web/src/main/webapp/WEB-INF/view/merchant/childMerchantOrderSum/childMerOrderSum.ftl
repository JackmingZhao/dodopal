<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>业务订单汇总</title>
<!-- InstanceEndEditable -->
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<#include "../../include.ftl"/>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript">
var baseUrl = "${base}";
</script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/js/merchant/childMerchantOrderSum/childMerOrderSum.js"></script>
<style>
.deal li{float:none!important;height:24px;}
</style>
</head>
<body>

<#include "../../header.ftl"/>
<div class="con-main" id="childMerRechargeOrderSumMain"> <!-- InstanceBeginEditable name="main" -->
    <#include "../../childrenPosNav.ftl"/>
	<ul class="tab-list01 clearfix">
	   <@sec.accessControl permission="merchant.child.ordersum.recharge">
		  <li><a href="${base}/childMerProductOrder/childMerProductOrderSum" class="cur">一卡通充值</a></li>
		</@sec.accessControl>
	   <@sec.accessControl permission="merchant.child.ordersum.purchase">
		  <li><a href="${base}/prvd/cardConsumCollect">一卡通消费</a></li>
	   </@sec.accessControl>
	</ul>
	<div class="seach-top-box pb0">
		<div class="seach-top-inner">
			<form action="exportRechargeForChildOrderSum" id="childMerRechargeOrderSumForm">
				<ul class="clearfix">
					<li class="w130">
						<label><span>商户名称：</span>
							<input type="text" id="merName" name="merName" class="com-input-txt w88" />
						</label>
					</li>
<!--					<li class="w130">
						<label><span>POS号：</span>
							<input type="text" id="posCode" name="posCode" class="com-input-txt w88" />
						</label>
					</li> -->
					
					<li><span class="w80">订单状态：</span>
						<select name="proOrderState" id="proOrderState">
                            <option selected="selected" value="4">成功</option>
                            <option  value="2">失败</option>
                            <option  value="5">可疑</option>
						</select>
					</li> 
					<li class="w350"><span class="w130">交易日期：</span>
						  <input name="startDate" readonly="true" type="text" class="com-input-txt w74" value="${orderDateStart!}" placeholder="日历" id="startDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  -
						  <input name="endDate" readonly="true" type="text" class="com-input-txt w74" value="${orderDateEnd!}" placeholder="日历" id="endDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					  </li>
					<li class="btn-list">
					   <@sec.accessControl permission="merchant.child.ordersum.recharge.find">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findChildMerRechargeOrderSum()" />
					   </@sec.accessControl>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('childMerRechargeOrderSumForm');" />
					</li>
				</ul>
			</form>
		</div>
        <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
	<table id="childMerRechargeOrderSumTb" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
		<colgroup>
			<col width="2%" />
			<col width="8%" />
			<!--<col width="10%" />
			<col width="9%" />
			<col width="6%" />
			<col width="12%" />-->
			<col width="25%" />
			<col width="25%" />
			<col width="25%" />
			<col width="8%" />
			<col width="2%" />
		  </colgroup>
		  <thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center">序号</th>
			<!--<th>交易日期</th> -->
				<th>商户名称</th>
			<!--<th>城市</th>
				<th>POS号</th>-->
				<th>充值笔数</th>
				<th>充值金额</th>
			<!--<th>POS备注</th> -->
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
           </thead>
		  <tbody></tbody>
		</table>
		<div class="null-box"></div>
		 <p class="page-navi" id="childMerRechargeOrderSumPaginator">
		  <@sec.accessControl permission="merchant.child.ordersum.recharge.export">
		   <span  class="fl"> 账单下载：<a  href="#"  onclick="exportExcel('exportRechargeForChildOrderSum','childMerRechargeOrderSumForm')">Excel格式</a></span>
		  </@sec.accessControl>
		</p>
	</div>
	<!-- InstanceEndEditable --> </div>
	<#include "childMerOrderSumDetail.ftl"/>
<#include "../../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('[js="zhangdan"]').click(function(){
		$('[js="zhangdanBox"]').show();
	});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="子商户管理"){
			$(this).addClass('cur');
		}
	});
    $('.tit-h3 a').each(function(){
		if($.trim($(this).text())=="业务订单汇总"){
			$(this).addClass('cur');
		}
	});
	$('.tab-list01 a').each(function(){
		if($.trim($(this).text())=="一卡通充值"){
			$(this).addClass('cur');
		}
	});
});
</script>
<!-- InstanceEndEditable --> 
</body>
<!-- InstanceEnd --></html>
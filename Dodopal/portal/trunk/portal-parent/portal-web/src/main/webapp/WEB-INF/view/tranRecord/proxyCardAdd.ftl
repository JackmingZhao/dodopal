<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>历史交易记录-网点</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/tranRecord/proxyCardAdd.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/common/ocxCommon.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main" id="proxyCardAddMain"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">
			<a href="${base}/tran/cardTradeList" class = "cur">一卡通充值</a>
			<a href="${base}/tran/offLineTradeList">一卡通消费</a>
	</h3>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form action="proxyCardAddExport" id="proxyCardAddForm">
			<input type="hidden" id="proxyid" name="proxyid"  value="${proxyid!}">
				<ul class="clearfix">
					<li>
						<label><span>订单号：</span>
							<input type="text" id="orderNo" name="orderNo" class="com-input-txt w88" style="width:75px;">
						</label>
					</li>
					<li>
						<label><span>POS号：</span>
							<input type="text" id="posId" name="posId" class="com-input-txt w88">
						</label>
					</li>
					<li class="w210">
						<label><span>卡号：</span>
							<input type="text" id="cardNo" name="cardNo" class="com-input-txt w88">
						</label>
					</li>
					<!--t.status,'0','记账成功','1','余额不足','2','扣款成功','3','充值成功','4','充值成功','5','充值失败,已处理','6','扣款成功','7',decode(t.yktid,'310000','充值成功','可疑交易扣额度'),'8','充值失败','9','充值成功','10','充值失败')-->
                     <li class="w210"><span>订单状态：</span>
						<select id="orderState" name="orderState">
							<option selected="selected" value="">--请选择--</option>
							<option value="2">扣款成功</option>
							<option value="3">充值成功</option>
							<option value="5">充值失败</option>
							<option value="7">可疑交易</option>
						</select>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" class="orange-btn-h26" onclick="findProxyCardAdd();"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearTranRecord('proxyCardAddForm')"/>
					</li>				
				</ul>
				<ul class = "clearfix">
					<li class="w320">
						<span>起止日期：</span>
							<input name="createDateStart" type="text" class="com-input-txt w74" readonly="true"   id="createDateStart" onfocus="c.showMoreDay = false;c.show(this,'');c.clearRadio();"/>
							-
							<input name="createDateEnd" type="text" class="com-input-txt w74"  readonly="true" id="createDateEnd" onfocus="c.showMoreDay = false;c.show(this,'');c.clearRadio();"/>
					</li>
					<li class="time" id="timeRadio">
						<label>
							<input type="radio" name="time" onfocus="clearDate();" value="7"/><em>近一周</em>
						</label>
						<label>
							<input type="radio" name="time" onfocus="clearDate();" value="30"/><em>近一个月</em>
						</label>
						<label>
							<input type="radio" name="time" onfocus="clearDate();" value="90"/><em>近三个月</em>
						</label>
					</li>
				</ul>
				
			</form>
		</div>
	 <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
        	<li class="fl">查询结果</li>
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
		<table cellpadding="0" cellspacing="0" class="com-table01 mb20" id="proxyCardAddTb">
	    <colgroup>
	    <col width="3%" />
	    <col width="6%" />
	    <col width="6%" />
	    <col width="6%" />
	    <col width="5%" />
	    <col width="7%" />
	    <col width="7%" />
	   <!--  <col width="6%" />
         <col width="5%" />-->
        <col width="6%" />
        <col width="5%" />
        <col width="5%" />
        <col width="5%" />
        <col width="5%" />
        <col width="6%" />
        <col width="5%" />
        <col width="5%" />
        <col width="3%" />
        </colgroup>
        <thead>
        <tr>
          <th>序号</th>
	      <th>订单号</th>
	      <th>网点名称</th>
	      <th>卡号</th>
	      <th>POS号</th>
	      <th>交易日期</th>
	      <th>交易时间</th>
	     <!--  <th>活动用户手机号</th>
	       <th>活动返利</th>-->
	      <th>交易前剩余额度</th>
	      <th>交易金额</th>
	      <th>优惠券使用金额</th>
	      <th>实收金额</th>
	      <th>返利金额</th>
	      <th>交易后剩余额度</th>
	      <th>订单状态</th>
	      <th>POS备注</th>
	      <th class="a-center">操作</th>
	      </tr>
	      </thead>
	      <tbody></tbody>
		</table>
		<div class="null-box"></div>
		<p class="page-navi clearfix" id="proxyCardAddPage"><!--<span class="fl fls">交易记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportProxyCardAdd"
		       onclick="exportExcel('proxyCardAddExport','proxyCardAddForm')">导出Excel</a></span>--></p>
		<div class="com-bor-box02">
	        <ul class="navi-ul01 clearfix">
	        	<li class="fl">统计结果</li>
				<li class="fr">金额单位(元)</li>
			</ul>
        </div>
	<table cellpadding="0" cellspacing="0" class="com-table01 mb20 tdthCenter" id="proxyCardAddTbCount">
	   <colgroup>
	    <col width="4%" />
	    <col width="30%" />
	    <col width="30%" />
	    <col width="20%" />
	    <col width="4%" />
	   </colgroup>
	   <thead>
        <tr>
          <th></th>
	      <th>合计</th>
	      <th>笔数</th>
	      <th>金额</th>
	      <th></th>
	     </tr>
	      </thead>
	      <tbody></tbody>
		</table>
	</div>
</div>
<div style="display:none;">
	<ul class="jige-ul mb20" id="printSpan">
		<li style="display:none">商户名称：<span id="merName"></span></li>
		<li><em>城卡公司名称：</em><span id="printCompany"></span></li>
		<li><em>订单号：</em><span id="printOrderNumRes"></span></li>
		<li><em>充值设备号：</em><span id="printTool"></span></li>
		<li><em>卡号：</em><span id="printCardCode"></span></li>
		<li><em>充值前卡余额：</em><span class="orange" id="printSMoneySpan"></span>元</li>
		<li><em>充值余额：</em><span class="orange" id="printCardMoneySpan"></span>元</li>
		<li><em>充值后卡余额：</em><span class="orange" id="printRMoneySpan"></span>元</li>
		<li><em>充值时间：</em><span id="printRechargeTime"></span></li>
		<li><em>状态：</em><span id="printRechargestate"></span></li>
	</ul>
</div>
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="历史交易记录"){
			$(this).addClass('cur');
		}
	});
	$('[js="jiaoyi"]').click(function(){
		$('[js="jiaoyiBox"]').show();
	});

});
</script>
<#include "../footer.ftl"/>
	
</body>
<!-- InstanceEnd --></html>

<#assign sec=JspTaglibs["/WEB-INF/tld/productTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>订单查询</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<script type="text/javascript">
</script>
<!-- InstanceEndEditable -->
<script src="${base}/js/jquery-1.9.1.min.js"></script>
<script src="${base}/js/calendar.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/portal.css" rel="stylesheet" type="text/css">
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/applicationCenter/ocxCommon.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${base}/js/productOrder/productOrder.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
$.merType='${(loginMerType)!}';
</script>
</head>
<body>
<#include "../headTitle.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="application-center clearfix"  id="productOrderMain">
		<div class="app-right">
			<#include "../appNav.ftl"/>
		<div class="app-right com-bor-box" style="padding:0;width:1028px;margin-top:-1px;">
		  <div class="seach-top-inner seach-top-inners">
				<form id="queryProductOrderForm" action="excelProductOrder" method="post">
					 <ul class="clearfix">
						<li class="w310"><span class="w100">日期范围：</span>
							<input name="orderDateStart" readonly="readonly" id = "orderDateStart" type="text" class="com-input-txt w74"  placeholder="起始时间" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="orderDateEnd" readonly="readonly" id = "orderDateEnd" type="text" class="com-input-txt w74"  placeholder="结束时间" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						</li>
						<li>
							<label><span class="w80">订单编号：</span>
								<input type="text" class="com-input-txt w88"  id="proOrderNum" name="proOrderNum"/>
							</label>
						</li>
						<li><span class="w80">城市：</span>
							<input type="text" class="com-input-txt w88"  id="cityName" name="cityName"/>
						</li>
						<li>
						<ul class="clearfix">
						<li><span class="w100">订单状态：</span>
							<select name="proOrderState" id="proOrderState">
                                <option selected="selected" value="">--请选择--</option>
							</select>
						</li>
						</ul>
						</li>
						 <li style="padding-left:20px;">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findProductOrderByPage();"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryProductOrderForm');"/>
					</li>
					</ul>
					  <ul class="clearfix" style="padding-top:14px;">
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
						<#if loginMerType??>
						<#if loginMerType =='18'>
						<ul class="clearfix">
						<li>
							<label><span  class="w100">外部订单号：</span>
								<input type="text" class="com-input-txt w88" id="merOrderNum" name="merOrderNum"/>
							</label>
						</li>
						</ul>
						</#if>
						</#if>
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
				<col width="4%" />
				<col width="8%" />
				<#if loginMerType??>
				<#if loginMerType =='18'>
				<col width="8%" />
				</#if>
				</#if>
				<col width="5%" />
				<col width="7%" />
				<col width="7%" />
				<col width="7%" />
				<col width="7%" />
				<col width="5%" />
				<col width="7%" />
				<col width="7%" />
				<col width="7%" />
				<col width="8%" />
				<col width="5%" />
				<col width="5%" />
				<col width="2%" />
				</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>订单编号</th>
					<#if loginMerType??>
					<#if loginMerType =='18'>
					<th>外部订单编号</th>
					</#if>
					</#if>
					<th>城市</th>
					<th>充值前金额</th>
					<th>充值金额</th>
					<th>实付金额</th>
					<th>充值后金额</th>
					<th>利润</th>
					<th>POS号</th>
					<th>卡号</th>
					<th>订单状态</th>
					<th>订单创建时间</th>
					<th>POS备注</th>
					<th>操作</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			</table>
			<div class="null-box" style="display:none;"></div>
			<p class="page-navi"><span class="fl">订单下载：<a  href="#"  onclick="exportExcel('excelProductOrder','queryProductOrderForm')" >Excel格式</a></span>
			</p>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<div style="display:none;">
	<ul class="jige-ul mb20" id="printSpan">
		<li><em>商 户名 称：</em><span id="merNameRes"></span></li>
		<li><em>通 卡名 称：</em><span class="orange" id="yktNameRes"></span></li>
		<li><em>订单号：</em><span id="proOrderNumRes"></span></li>
		<li><em>充值前金额：</em><span id="showBefbalRes"></span>元</li>
		<li><em>充 值金 额：</em><span class="orange" id="showTxnAmtRes"></span>元</li>
		<li><em>实 付金 额：</em><span class="orange" id="receivedPrice"></span>元</li>
		<li><em>充值后金额：</em><span class="orange" id="showBlackAmtRes"></span>元</li>
		<li><em>POS号：</em><span id="posCodeRes"></span></li>
		<li><em>卡   号：</em><span id="orderCardnoRes"></span></li>
		<li><em>订单时间：</em><span id="proOrderDateRes"></span></li>
		<li><em>交易状态：</em><span id="proOrderStateRes"></span></li>
	</ul>
</div>
<#include "productOrderView.ftl"/>
<#include "../footLog.ftl"/>
</body>
<!-- InstanceEnd --></html>

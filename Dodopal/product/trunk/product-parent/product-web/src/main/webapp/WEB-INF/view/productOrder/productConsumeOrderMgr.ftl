<#assign sec=JspTaglibs["/WEB-INF/tld/productTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>消费记录</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
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
<script type="text/javascript" src="${base}/js/productOrder/productConsumeOrder.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../headTitleConsume.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="application-center clearfix"  id="productConsumeOrderMain">
		<div class="app-right">
			<#include "../consumeNav.ftl"/>
		<div class="app-right com-bor-box" style="padding:0;width:1028px;margin-top:-1px;">
		  <div class="seach-top-inner seach-top-inners">
				<form id="queryproductConsumeOrderForm" action="excelproductConsumeOrder" method="post">
					 <ul class="clearfix">
						<li class="w310"><span class="w100">日期范围：</span>
							<input name="orderDateStart" readonly="readonly" id = "orderDateStart" type="text" class="com-input-txt w74"  onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="orderDateEnd" readonly="readonly" id = "orderDateEnd" type="text" class="com-input-txt w74"  onfocus="c.showMoreDay = false;c.show(this,'');"/>
						</li>
						<li>
							<label><span class="w80">订单编号：</span>
								<input type="text" class="com-input-txt w88"  id="orderNum" name="orderNum"/>
							</label>
						</li>
						<li><span class="w80">城市：</span>
							<input type="text" class="com-input-txt w88"  id="cityName" name="cityName"/>
						</li>
						<li>
						<ul class="clearfix">
						<li><span class="w100">订单状态：</span>
							<select name="states" id="states" style="width:150px;">
                                <option selected="selected" value="">--请选择--</option>
							</select>
						</li>
						</ul>
						</li>
						 <li style="padding-left:20px;">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findProductConsumeOrder();"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryproductConsumeOrderForm');"/>
					</li>
					</ul>
					  <ul class="clearfix" style="padding-top:14px;">
						<li class="w310 yuan"><span class="w100">实付金额范围：</span>
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
								<input type="text" class="com-input-txt w88" id="cardNum" name="cardNum"/>
							</label>
						</li>
					</ul>
					
				</form>
			</div>
			<div class="com-bor-box02">
				<ul class="navi-ul01 clearfix">
					<li class="fr">金额单位(元)</li>
				</ul>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id= "productConsumeOrderTbl">
			  <colgroup>
			    <col width="1%" />
			    <col width="3%" />
			    <col width="11%" />
			    <col width="5%" />
			    <col width="11%" />
			    <col width="11%" />
			    <col width="7%" />
			    <col width="7%" />
			    <col width="7%" />
			    <col width="7%" />
			    <col width="7%" />
			    <col width="8%" />
			    <col width="8%" />
                <col width="8%" />
                <col width="1%" />
			  </colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>订单编号</th>
					<th>城市</th>
					<th>卡号</th>
					<th>POS号</th>
					<th>应付金额</th>
					<th>实付金额</th>
					<th>消费前金额</th>
					<th>消费后金额</th>
					<th>状态</th>
				    <th>订单创建时间</th>
				    <th>POS备注</th>
					<th class="a-center">操作</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			</table>
			<div class="null-box" ></div>
			<p class="page-navi"><span class="fl">订单下载：<a  href="#"  onclick="exportExcel('excelproductConsumeOrder','queryproductConsumeOrderForm')" >Excel格式</a></span>
			</p>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<div style="display:none;">
	<ul class="jige-ul mb20" id="printSpan">
		<li style="display:none">商户名称：<span id="merName"></span></li>
		<li><em>订单号：</em><span id="printOrderNumRes"></span></li>
		<li><em>折    扣：</em><span id="printPayDiscountSpan">无</span></li>
		<li><em>应付金额：</em><span class="orange" id="printSMoneySpan"></span>元</li>
		<li><em>实付金额：</em><span class="orange" id="printRMoneySpan"></span>元</li>
		<li><em>原有金额：</em><span class="orange" id="printBeforCardMoneySpan"></span>元</li>
		<li><em>卡 余 额：</em><span class="orange" id="printCardMoneySpan"></span>元</li>
		<li>POS   号：<span id="printPosCode"></span></li>
		<li>卡    号：<span id="printCardCode"></span></li>
		<li>交易时间：<span id="printConsumeTime"></span></li>
		<li>订单状态：<span id="printConsumestate"></span></li>
	</ul>
</div>
<#include "productConsumeOrderView.ftl"/>
<#include "../footLog.ftl"/>
</body>
<!-- InstanceEnd --></html>

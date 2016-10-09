<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../include.ftl"/>
<script type="text/javascript" src="${base}/js/thirdlyView/thirdly.js"></script>
<title>产品订单</title>
</head>
<body>
<div class="con-main" id="ThirdlyProductView">
	<h3 class="tit-h3">一卡通充值订单</h3>
	<div class="com-bor-box com-bor-box01">
			<table class="base-table base-table01" id="ThirdlyProductTable">
				<col width="140" />
				<col width="330" />
				<col width="120" />
				<tr>
					<th>都都宝平台订单号：</th>
					<td><span id="prdordernumSpan">${thirdlyProduct.prdordernum}</span></td>
				</tr>
				<tr>
				<th>商户订单号：</th>
				<td><span id="extordernumSpan">${thirdlyProduct.extordernum}</span></td>
				</tr>
				<tr>
					<th>产品编号：</th>
					<td><span id="productcodeSpan">${thirdlyProduct.productcode}</span></td>
				</tr>
				<tr>
				<th>产品名称：</th>
					<td><span id="productnameSpan">${thirdlyProduct.productname}</span></td>
				</tr>
				<tr>
					<th>卡号：</th>
					<td><span id="tradecardSpan">${thirdlyProduct.tradecard}</span></td>
				</tr>
				<tr>
				<th>POS号：</th>
					<td><span id="posidSpan">${thirdlyProduct.posid}</span></td>
				</tr>
				<tr>
					<th>卡余额：</th>
					<td><span id="befbalSpan">${thirdlyProduct.befbal}</span>元</td>
				</tr>
				<tr>
					<th>充值金额：</th>
					<td><span id="tranamtSpan">${thirdlyProduct.tranamt}</span>元</td>
				</tr>
				<tr>
					<th>订单时间：</th>
					<td><span id="returndateSpan">${thirdlyProduct.returndate}</span></td>
				</tr>
			</table>
	</div>
</div>
</body>
</html>


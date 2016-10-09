<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "../../include.ftl"/>
<script>
var merUserCode ='${userCode}';
var orderNum = '${orderNum}';
var txnAmt = '${txnAmt/100}';
var befbal = '${befbal/100}';
window.history.forward(1);
</script>
<script src="${base}/js/applicationCenter/cardRecharge/cardRechargeResultBack.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收银台</title>
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="wd-header">
	<div class="w1030 clearfix"> <a class="dodopal-logo" href="/"></a>
	</div>
</div>
<div class="clear"></div>
<div class="com-bor-box com-bor-box1 pay-way-result">
	<h3 class="tit-h3 pay-result">充值结果</h3>
		<div class="popConts pay-way-conr">
			<ul class="jige-ul mb20">
				<li><em>订单编号：</em><span id="orderNum">${orderNum}</span></li>
				<li><em>充值金额：</em><span class="orange">#{(txnAmt/100);m2M2}</span>元</li>
				<li><em>订单应收金额：</em><span class="orange">#{(txnAmt/100);m2M2}</span>元</li>
				<#--<li><em>订单实收金额：</em><span class="orange" id="realMoney">${receivedPrice/100}</span>元</li>-->
				<li><em>充值前卡内余额：</em><span class="orange">#{(befbal/100);m2M2}</span>元</li>
				<li ><em>充值后卡内余额：</em><span class="orange" id="rebehindMoney">#{(befbal/100);m2M2}</span>元</li>
			</ul>
				<div class="wait-result">
            	<div class="bar-box"><img alt="" src="${styleUrl}/product/css/icons/loading01.gif"></div>
				<h3>一卡通充值中...</h3>
			</div>
            <div class="top-up-results">
				<div class="ok-tips mb20" id="successMassage" style="display:none;"><i></i>一卡通充值成功！</div>
				<div class="err-tips mb20" id="failMessage"  style="display:none;"><i></i>一卡通充值失败！</div>

				<ul class="ul-btn ul-btn01" id="successButton" style="display:none;">
					<li><a class="orange-btn-h32" onclick="closeWebPage();">关闭此页</a></li>
					<#--<li><a class="orange-btn-text32" js="clopop" href="/">返回商户</a></li>-->
				</ul>
        </div>
	</div>
</div>
<div class="clear"></div>
<#include "../../footLog.ftl"/>
<OBJECT ID="OCXFAPAY"  name="OCXFAPAY" CLASSID="CLSID:${(ocxClassId)!}" 
HEIGHT=0 WIDTH=0 codebase="${ocxUrl}/${(yktCode)!}.CAB#version=${(ocxVersion)!}}" viewastext>
<SPAN STYLE="color:red">控件加载失败! -- 请检查浏览器的安全级别设置.</SPAN></OBJECT>
</body>
</html>


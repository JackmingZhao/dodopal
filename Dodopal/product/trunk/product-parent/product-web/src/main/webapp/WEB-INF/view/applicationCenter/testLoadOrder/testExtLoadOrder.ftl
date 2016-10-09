<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>外接商户圈存下单测试</title>
<#include "../../include.ftl"/>
<script src="${base}/js/applicationCenter/testLoadOrder/testExtLoadOrder.js" type="text/javascript"></script>
<script src="${base}/js/applicationCenter/cardRecharge/AreaWordCreate.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wd-header">
	<div class="w1030 clearfix"> <a class="dodopal-logo" href="#"></a>
		<div class="dodopal-title" style="width:400px;">
			<p>外接商户book圈存订单测试</p>
		</div>
	</div>
</div>
<div class="con-main">
<div class="application-center clearfix">
	  <div class="app-right pr">
	  	<img class="tip" src="${styleUrl}/product/css/images/tip.png" width="300">
		  <dl class="app-dl clearfix com-bor-box com-bor-box01 app-dl-s pd2070">
			  <dt>所在城市：</dt>
			  <#include "../ddArea.ftl"/>
			  <dt>产品金额：</dt>
			  <dd class="recharge-amount" id="proPrice"><a href="javascript:;">50<s></s></a><a href="javascript:;">100<s></s></a><a href="javascript:;">200<s></s></a></dd>
			  <dt><input type="checkbox" id="chooseLoadAmount" onclick="chooseLoadAmount();"></input>自定义金额：</dt>
			  <dd class="clearfix">
				  <input type="text" class="com-input-txt w260" id="loadAmount"/>元
				  <i class="red" >（自定义圈存金额）</i>
			  </dd>
              <dt>一卡通卡号：</dt>
			  <dd class="clearfix">
				  <input type="text" class="com-input-txt w260" id="cardNo"/>
			  </dd>
			  <dt>外接商户编号：</dt>
			  <dd class="clearfix">
				  <input type="text" class="com-input-txt w260" id="extMerCode"/>
				  <i class="red" >（方便测试，提供输入框）</i>
			  </dd>
			  <dt>外部订单编号：</dt>
			  <dd class="clearfix">
				  <input type="text" class="com-input-txt w260" id="extOrderNum"/>
				  <i class="red" >（方便测试，提供输入框）</i>
			  </dd>
			  <dt>外部订单时间：</dt>
			  <dd class="clearfix">
				  <input type="text" class="com-input-txt w260" id="extOrderTime"/>
				  <i class="red" >（方便测试，提供输入框）</i>
			  </dd>
			  <dt></dt>
			  <dd class="btn-recharge"><a href="javascript:createOrder();"  class="orange-btn-h32" js="qurren">立即充值</a></dd>
		  </dl>
		  <dl class="app-dl02">
			  <dt>下单结果：</dt>
			  <dd id="resultXml"></dd>
		  </dl>
	  </div>
	</div>
	</div>
<#include "../../footLog.ftl"/>
</body>
</html>

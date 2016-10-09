<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "../../include.ftl"/>
<script>
var resultCode ='${resultCode}';
window.history.forward(1);
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收银台</title>
<script src="${base}/js/applicationCenter/loadOrder/loadOrderResultBack.js" type="text/javascript"></script>

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
	<h3 class="tit-h3 pay-result">圈存结果</h3>
		<div class="popConts pay-way-conr">
			
            <div class="top-up-results">
				<div class="ok-tips mb20" id="successMassage" style="display:none;"><i></i>一卡通圈存成功！</div>
				<div class="err-tips mb20" id="failMessage"  style="display:none;"><i></i>一卡通圈存失败！</div>

				<ul class="ul-btn ul-btn01" id="successButton" >
					<li><a class="orange-btn-h32" onclick="closeWebPage();">关闭此页</a></li>
					<#--<li><a class="orange-btn-text32" js="clopop" href="/">返回商户</a></li>-->
				</ul>
				</div>
        </div>
	</div>
</div>
<div class="clear"></div>
<#include "../../footLog.ftl"/>
</body>
</html>


<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "../include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>支付方式</title>
<!-- InstanceEndEditable -->
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'></script>
<script type="text/javascript" src="../js/ddp/accountPayway.js"></script>
<script type="text/javascript" src="../js/portalValidationHandler.js"></script>
</head>

<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
<div class="account-setup mb30">
	<#include "../accountNav.ftl"/>
	<div class="account-box" id="accountSetPayWay">
			<h4>常用支付方式（最多可选择5种支付方式）</h4>
			<ul class="pay-way-list pay-way-list01 clearfix" id="accountCommon"></ul>
			<dl class="pay-box-dl">
				<dt></dt>
				<dd>
					<input id="findMorePayWay" type="submit" value="修改" class="orange-btn-h32" onclick="findMorePayWay()"/>
				</dd>
			</dl>
	</div>
	<div class="account-box" id="accountMorePayWay" style="display:none">
			<h4 class="mb20">常用支付方式（最多可选择5种支付方式）<span class="bor-yellow-notes">请设置您的常用支付方式</span></h4>
			<dl class="pay-box-dl">
				<dt>网银支付</dt>
				<dd class="pay-way-list01">
					<p class="tit">选择发卡银行：</p>
					<ul class="pay-way-list clearfix" js="pay-list" id="internetBank">
					</ul>
				</dd>
			</dl>
			<dl class="pay-box-dl">
				<dt>第三方支付</dt>
				<dd class="pay-way-list01"><p>选择第三方平台：</p>
						<ul class="pay-way-list clearfix" id="thrPayment"></ul>
				</dd>
			</dl>
			<dl class="pay-box-dl mb0">
				<dt></dt>
				<dd>
					<input type="button" value="保存" class="orange-btn-h32" onclick="insertCommon()"/>
					<input type="submit" value="取消" class="orange-btn-h32"  onclick="closeMore()"/>
				</dd>
			</dl>
		</div>
	</div>
</div>
<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd -->
</html>
		
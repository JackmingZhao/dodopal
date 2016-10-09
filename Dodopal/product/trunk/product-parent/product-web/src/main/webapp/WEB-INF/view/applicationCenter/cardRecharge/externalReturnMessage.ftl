<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "../../include.ftl"/>
<script src="js/jquery-1.9.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提示信息</title>
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/checkstand.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="clear"></div>
<div class="box">
	<div class="inBox">
		<div class="p1 ps3">
			<i></i><h2>跳转失败</h2>
		</div>
		<div class="p2 ps4">
			<p>原因提示：<span>${(externalMessage)!}</span></p>
			<p>请拨打客服电话400-817-1000了解失败详情</p>
		</div>
	</div>
</div>
</body>
</html>

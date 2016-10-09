<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="js/jquery-1.9.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>收银台</title>
    <link href="${styleUrl}/payment/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${styleUrl}/payment/css/checkstand.css" rel="stylesheet" type="text/css"/>

    <link href="./css/base.css" rel="stylesheet" type="text/css"/>
    <link href="./css/checkstand.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wd-header wd-header-index wd-header-check">
    <div class="w1030">
        <a class="dodopal-logo" href="http://sh.dodopal.com"></a>

        <div class="phone">
            <i></i><span title="都都宝-24小时免费服务电话">400-817-1000</span>
        </div>
    </div>
</div>
<div class="clear"></div>
<div class="box">
    <div class="inBox">
        <div class="p1 ps1">
            <i></i>

            <h2> 账户充值成功！</h2>
        </div>
    <#if message??>
        <div class="p2 ps2">
            原因提示：<br/>
        ${(message)!}<br/>
            请拨打客服电话400-817-1000了解失败详情
        </div>
    </#if>
    </div>
</div>
<div class="clear"></div>
<div class="wd-footer">
    <div class="container footer-p">
        <p><a href="http://www.dodopal.com">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="#">http://weibo.com/dodopal</a></p>

        <p>都都宝版权所有 COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
    </div>
</div>
<#if redirectHtml??>
${(redirectHtml)!}
<script language=javascript>setTimeout("document.forms[0].submit()", 5000)</script>
</#if>
</body>
</html>


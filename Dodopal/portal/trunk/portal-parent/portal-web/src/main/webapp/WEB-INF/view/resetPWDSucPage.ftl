<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>

<script src="${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js"></script>
<script src="${base}/js/merchant/merchantUser/resetPWD.js"></script>
<title>找回密码</title>
<link href="${styleUrl}/portal/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/reset.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wd-top">
	<div class="container">
		<p class="wd-top-left" title="都都宝-24小时免费服务电话"><img class="phone-icon" src="${styleUrl}/portal/css/icons/phone-icon.png" />400-817-1000</p>
        <ul class="wd-top-right">
        	<li><span>|</span>&nbsp;<a>帮助中心</a></li>
        </ul>
    </div>
</div>
<div class="clear"></div>
<div class="wd-header">
	<div class="container">
    	<a class="dodopal-logo" href="login"></a>
        <div class="dodopal-title">
        	<p>找回密码</p>
        </div>       
    </div>
</div>
<div class="clear"></div>
<div class="wd-main wd-main-min">
	<div class="container" style="height:410px;">
    	<div class="tab-contain">
        <p style="font-size:14px; margin-bottom:10px; width:824px;">您正在找回的都都宝账户是：${(RESETNAME)!}<span style="float:right;">已有账户，马上<a href="login" class="wd-orage1">&nbsp;登录</a></span></p>
          <div class="tabs-body tabs-body1">       		
           <div>
              <h2 class="wd-reg-title wd-reg-title1"><div class="wd-icon wd-icon1"></div>${(info)!}</h2>
              <div style="margin:10px 0px;text-align:center;"><input id="btn-p" type="button" class="wd-btn wd-btn-1" style="width:140px;" value="直接登录" onClick="window.location.href='login';"/></div>
            </div>
            </div>
        </div>        	
    </div>
</div>
<div class="clear"></div>
<div class="wd-footer">
	<div class="container footer-p">
    	<p><a href="#">关于都都宝</a>&nbsp;|&nbsp;<a href="#">系统公告</a>&nbsp;|&nbsp;<a href="#">帮助中心</a>&nbsp;|&nbsp;官方微博 <a href="#">http://weibo.com/dodopal</a></p>
        <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
    </div>
</div>
</body>
</html>


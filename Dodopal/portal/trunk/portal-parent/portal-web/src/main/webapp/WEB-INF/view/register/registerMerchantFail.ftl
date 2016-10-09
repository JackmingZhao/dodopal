<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册_个人</title>
<link href="${styleUrl}/portal/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/reg.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'></script>
<script type='text/javascript'>
	var intervalid; 
	var i = 3;
	$(function(){
		intervalid = setInterval("forwardPage()", 1000); 
	});
	function forwardPage(){
		if (i == 0) {
			window.location.href = "${base}/register/registerPage";
			clearInterval(intervalid);
		}else{
			$('#seconds').html(i);
			i--;
		}
}
</script>
</head>

<body>
<#include "registerHeader.ftl" />
<div class="clear"></div>
<div class="wd-main wd-main-min">
	<div class="container">
   		<div class="wd-reg-main">
        	<div class="wd-reg-up">				
                <h2 class="wd-reg-title"><div class="wd-icon wd-icon2"></div>很抱歉，您的注册信息未提交成功！</h2>
                <p class="wd-reg-title"><span>${DODOPAL_RESPONSE}<span></p>
				<p><span id="seconds">3</span>秒后为您跳转至&nbsp;<a class="wd-blue" href="${base}/register/registerPage"><strong>注册</strong></a>&nbsp;页面，请重新注册。</p>
            </div>
            <div class="wd-reg-down">
            	<p><strong>请返回&nbsp;<a class="wd-orage1" href="${base}/register/registerPage"><strong>注册</strong></a>&nbsp;页面重新注册！</strong>
                <br />您也可以直接拨打&nbsp;<span>400-817-1000</span>&nbsp;进行快速注册。</p>
            </div>
        </div> 	
    </div>
</div>
<div class="clear"></div>
<#include "../portalFooter.ftl"/>

</body>
</html>

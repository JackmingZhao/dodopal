<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册_个人</title>
<link href="${base}/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/reg.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${base}/js/jquery-1.9.1.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'></script>
<script type='text/javascript'>
	var intervalid; 
	var i = 5;
	$(function(){
		intervalid = setInterval("forwardPage()", 1000); 
	});
	function forwardPage(){
		if (i == 0) {
			window.location.href = "${base}/index";
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
                <h1 class="wd-reg-title"><div class="wd-icon wd-icon1"></div>恭喜您，已注册成功！</h1>
				<p><span id="seconds">5</span>秒后将自动登陆</p>
            </div>
            <div class="wd-reg-down">
            	<p><strong>您的都都宝账号是&nbsp;<span>${(LoginName!"")}</span>&nbsp;请牢记！</strong>
                <br />您还可以使用用户名&nbsp;<span>${(LoginName!"")}</span>&nbsp;登陆您的都都宝&nbsp;<a class="wd-orage1" href="#"><strong>账户中心</strong></a></p>
            </div>
        </div> 	
    </div>
</div>
<div class="clear"></div>
<#include "../portalFooter.ftl"/>

</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册_商户</title>
<link href="${styleUrl}/portal/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/reg.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js'></script>
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
                <h2 class="wd-reg-title"><div class="wd-icon wd-icon1"></div>恭喜，您的注册信息已成功提交！</h2>
				<p  style="line-height:20px;">请您耐心等待我们的审核，审核通过后我们将以<span style="font-color:red;">电话</span>形式告知。</p><p style="line-height:20px;"><span id="seconds">5</span>秒后为您跳转至&nbsp;<a class="wd-blue" href="${base}/index"><strong>都都宝首页</strong></a></p>
            </div>
            <div class="wd-reg-down">
            	<p><strong>您的都都宝账号是&nbsp;<span>${(LoginName!"")}</span>&nbsp;审核周期为1~2个工作日！</strong>
                <br />您也可以直接拨打&nbsp;<span>400-817-1000</span>&nbsp;进行快速审核。</p>
            </div>
        </div> 	
    </div>
</div>
<div class="clear"></div>
<#include "../portalFooter.ftl"/>

</body>
</html>





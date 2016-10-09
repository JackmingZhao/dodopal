<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<title>500页面</title>
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/reg.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wd-header">
	<div class="container">
    	<a class="dodopal-logo" href="/"></a>
        <div class="dodopal-title">
        	<p>错误页</p>
        </div>       
    </div>
</div>
<div class="con-main">
<div style="margin:0 auto;width:427px;padding:0px 0px 120px 0px;">
	<div><img src="${styleUrl}/portal/css/images/500_2.png" />
    </div>
    <div style="padding-top:30px; font-weight:bold;">
        	<#--<input type="submit" class="orange-btn-h32" value="回到首页" />
    		<p style="padding-top:10px;font-size:14px;">您还可以进行以下操作：<br />
				1、返回&nbsp;<b class="wd-orage1">上一个页面</b><br />
				2、尝试&nbsp;<b class="wd-orage1">刷新页面</b></p>-->
   		</div>
		
</div>
</div>
<#include "footLog.ftl"/>
<script type="text/javascript">
$(document).ready(function(e) {
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
	$('.header-nav ul li').click(function(){
		var i=$(this).index();
		$('.header-nav ul li a').removeClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show();
		$(this).find('a').addClass('on');
	});
	if($('.header-inr-nav ul li a').hasClass('cur')){
		var i=$('.cur').closest('ul').index();
		$('.header-nav ul li a').removeClass('on').eq(i).addClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show()
	};
});

function popclo(obj){
	$(obj).closest('.pop-win').hide();
}

$('.footer-navi .more a').click(function(){
	if($(this).hasClass('open')){
		$(this).removeClass('open');
		$('.footer-navi ul').height(60);
	}else{
		$(this).addClass('open');
		$('.footer-navi ul').removeAttr('style');
	};

});
</script>
</body>
</html>

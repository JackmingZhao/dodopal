<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<title>应用中心</title>
<#include "../include.ftl"/>
<script src="${base}/js/calendar.js" type="text/javascript"></script>
<script type='text/javascript' src='${base}/js/common/placeholders.js'></script>
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css" />

<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function yw(flag){
if(flag == true){
		return;
	}else{
		$.messagerBox({type: 'warn', title:"温馨提示", msg: "你所点击的业务尚未开通，请耐心等候......"});
		return;
	}
}
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main">
<div class="application-center clearfix mb30">
	  <div class="app-left">
		  <ul class="clearfix">
		  	 <@sec.accessControl permission="app.product.recharge">
			  <li class="snavi01"><a href="#" onclick="openNewPage('01')" class="on"><span><i></i>一卡通充值</span></a></li>
			  </@sec.accessControl>
		     <@sec.accessControl permission="app.product.purchase">
		       <li class="snavi001"><a href="#" onclick="openNewPage('03')"><span><i></i>一卡通消费</span></a></li>
			 </@sec.accessControl>
			 <@sec.accessControl permission="app.product.load">
			 <li class="snavi08"><a href="#" onclick="openNewPage('04')"><span><i></i>一卡通圈存</span></a></li>
			 </@sec.accessControl>
			  <li class="snavi03"><a  href="javascript:void(0);" onclick="yw(false)"><span><i></i>手机话费</span></a></li>
			  <li class="snavi04"><a  href="javascript:void(0);" onclick="yw(false)"><span><i></i>水费缴纳</span></a></li>
			  <li class="snavi05"><a  href="javascript:void(0);" onclick="yw(false)"><span><i></i>电费缴纳</span></a></li>
			  <li class="snavi06"><a  href="javascript:void(0);" onclick="yw(false)"><span><i></i>天然气缴纳</span></a></li>
			  <li class="snavi07"><a  href="javascript:void(0);" onclick="yw(false)"><span><i></i>有线电视费</span></a></li>
		  </ul>
	  </div>
	  <div class="app-right pb">
		  <div class="app-dl" style="margin-left:10px;border-bottom:none;"><img src="${styleUrl}/portal/css/images/app-banner_03.png"/></div>
		  <div class="app-all">
          	<h3>全部应用</h3>
            <div class="app-con clearfix">
            	<ul>
            	<@sec.accessControl permission="app.product.recharge">
                	<li>
                    	<div class="cons">
                    		<a onclick="openNewPage('01')">
	                    		<i class="hot"></i>
	                    		<i class="app-icon01"></i>
	                      	 		<div class="title"> <strong>一卡通充值</strong>
	                        		<p>无需排队，充值便利</p>
	                        		</div>
                        	</a>
                        </div.cons>
                    </li>
                     </@sec.accessControl>
                    <@sec.accessControl permission="app.product.purchase">
                     <li>
                    	<div class="cons">
                    		<a href="#" onclick="openNewPage('03')">
		                    	<i class="hot"></i>
		                    	<i class="app-icon001"></i>
                      			<div class="title"> <strong>一卡通消费</strong>
                       				 <p>安全、便利</p>
                       			</div>
                        	</a>
                        </div.cons>
                    </li>
                    </@sec.accessControl>
                    
                     <@sec.accessControl permission="app.product.load">
                    <li>
                    	<div class="cons">
                    		<a href="#" onclick="openNewPage('04')">
		                    	<i class="hot"></i>
		                    	<i class="app-icon07"></i>
                      			<div class="title"> <strong>一卡通圈存</strong>
                       				 <p>安全、便利</p>
                       			</div>
                        	</a>
                        </div.cons>
                    </li>
                     </@sec.accessControl>
                    <li>
                    <a href="#" onclick="yw(false)">
                    	<div class="cons"><i class="app-icon02"></i>
                        <div class="title"><strong>手机充值</strong>
                        <p>简单快捷，到账速度快</p></div></div.cons>
                     </a>
                    </li>
                    <li>
                    <a href="#" onclick="yw(false)">
                    	<div class="cons"><i class="app-icon03"></i>
                       	<div class="title"> <strong>水费缴纳</strong>
                        <p>24小时服务，0手续费</p></div></div.cons>
                     </a>
                    </li>
                    <li>
                     <a href="#" onclick="yw(false)">
                    	<div class="cons"><i class="app-icon04"></i>
                       <div class="title"> <strong>电费缴纳</strong>
                        <p>24小时服务，0手续费</p></div></div.cons>
                        </a>
                    </li>
                    <li>
                     <a href="#" onclick="yw(false)">
                    	<div class="cons"><i class="app-icon05"></i>
                        <div class="title"><strong>天然气缴纳</strong>
                        <p>24小时服务，0手续费</p></div></div.cons>
                        </a>
                    </li>
                    <li>
                     <a href="#" onclick="yw(false)">
                    	<div class="cons"><i class="app-icon06"></i>
                        <div class="title"><strong>有线电视费缴纳</strong>
                        <p>24小时服务，0手续费</p></div></div.cons>
                        </a>
                    </li>
                </ul>
            </div>
          </div>
	  </div>
	</div>
	</div>
<div class="footer-copy">
	<div class="w1030 footer-p">
		<p><a href="http://www.dodopal.com/" target="_blank">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="http://weibo.com/dodopal" target="_blank">http://weibo.com/dodopal</a></p>
		<p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
		<p class="orange">使用完毕请安全退出，请勿泄露自己的登录名和密码</p>
	</div>
</div>
<script type="text/javascript">
function fnokbtn(obj){
	$(obj).closest('[js="qurrenjiner"]').hide();
	$('[js="qurrenok"]').show();
}
$(document).ready(function(e) {
	//应用中心
	/*$('.app-con li').hover(function(e){
			$(this).css({'background-color':'#F0F2F5'}).children('.cons').css({'border':'solid 1px #EE0C09','margin':'-1px 0 0 -1px'});
			
		},function(e){
				$(this).css({'background-color':'#fff'}).children('.cons').css({'border':'0','margin':'0'});
			})*/
			$('.app-con li').hover(function(e){
			$(this).addClass('bgc').children('.cons').addClass('redWrap');
			
		},function(e){
				$(this).removeClass('bgc').children('.cons').removeClass('redWrap');
			});
	var setcity=0;
	$('[js="setCity"]').click(function(event){
		  event.stopPropagation();
		$('.set-city').show();
		setcity=1;
	});
	
	$('body').click(function(){
		if(setcity=1){
			$('.set-city').hide();
		}
	});
	
	$('[js="qurren"]').click(function(){
		$('[js="qurrenjiner"]').show();
		
	});
	


	$('.set-city-list li').click(function(event){
		event.stopPropagation();
		var i=$(this).index();
		$('.set-city-list li').find('a').removeClass('on');
		$(this).find('a').addClass('on');
		$('.set-city-dl').eq(i).show().siblings('.set-city-dl').hide();
	});
	$('.set-city-dl li a').click(function(event){
		event.stopPropagation();
	});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="应用产品"){
			$(this).addClass('cur');
		}
	});
	
	
	$('[js="pay-list"] li:gt(4)').hide();
	$('[js="pay-list"] li.only').show();
	var a=0;
	$('[js="pay-list"] li.only').click(function(){
		if(a==0){
			$('[js="pay-list"] li').show();
			a=1;
		}else{
			$('[js="pay-list"] li:gt(4)').hide();
			$('[js="pay-list"] li.only').show();
			a=0;
		}		
	});
$('.recharge-amount a').click(function(){
	$(this).addClass('a-click').siblings("a").removeClass("a-click");
});
$('.payway-ul input').click(function(){
	if(!$(this).attr('checked')){
		$(this).closest('.payway-ul').addClass('payway-ul-click');
	}else{
		$(this).closest('.payway-ul').removeClass('payway-ul-click');
	}
	
})
  $(".pay-navi-ul li").each(function(i){
  $(".pay-navi-ul li").eq(i).click(function(){
  $(this).addClass("on").siblings("li").removeClass("on");
  $(".pay-way-box ul").eq(i).show().siblings().hide();
  });
 });
 
 
});



</script>
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
<script src="js/select.js" type="text/javascript"></script>
</body>
</html>

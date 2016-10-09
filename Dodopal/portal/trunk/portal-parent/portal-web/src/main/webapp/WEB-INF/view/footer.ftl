<div class="foot">
<div class="footer-navi">
	<div class="foot-bg">
		<div class="foot-inner">
			<div class="home"><i class="san"></i><a href="#"><i></i>应用产品</a></div>
			<ul style="height:60px;">
				<@sec.accessControl permission="app.product.recharge">
				<li class="navi"><a href="javascript:footNewPage('01');" onclick="yw(true)" ><i class="one"></i>一卡通充值</a></li>
				</@sec.accessControl>
				<@sec.accessControl permission="app.product.purchase">
				<li class="navi"><a href="javascript:footNewPage('03')"><i class="eight"></i>一卡通消费</a><li>
				 </@sec.accessControl>
				<li class="navi"><a href="javascript:void(0);" onclick="yw(false)"><i class="two"></i>手机充值</a></li>
				<li class="navi"><a href="javascript:void(0);" onclick="yw(false)"><i class="three"></i>水费缴纳</a></li>
				<li class="navi"><a href="javascript:void(0);" onclick="yw(false)"><i class="four"></i>电费缴纳</a></li>
				<li class="navi"><a href="javascript:void(0);" onclick="yw(false)"><i class="five"></i>天然气缴纳</a></li>
				<li class="navi"><a href="javascript:void(0);" onclick="yw(false)"><i class="six"></i>有线电视费</a></li>
			</ul>
			<#if sessionUser.merType =='99' || sessionUser.merType=='12' || sessionUser.merType=='16' || sessionUser.merType=='17'>
			<#else>
			<div class="more"><a href="javascript:;"></a></div>
			</#if>
			<div class="erweima"><img src="${styleUrl}/portal/images/img_code.png" alt="" /></div>
		</div>
	</div>
</div>
<div class="footer-copy">
	<div class="w1030 footer-p">
		<p><a href="http://www.dodopal.com/" target="_blank">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="http://weibo.com/dodopal" target="_blank">http://weibo.com/dodopal</a></p>
		<p>都都宝版权所有&nbsp;COPYRIGHT (C) 2015 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
		<p class="orange">使用完毕请安全退出，请勿泄露自己的登录名和密码</p>
	</div>
</div>
<script src="${base}/js/common/footPageEven.js"></script>
<script type="text/javascript">
/*
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="首页"){
			$(this).addClass('cur');
		}
	});
});*/

$(document).ready(function(e) {
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
</script>
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$('.footer-navi .more a').click(function(){
	if($(this).hasClass('open')){
		$(this).removeClass('open');
		$('.footer-navi ul').height(60);
	}else{
		$(this).addClass('open');
		$('.footer-navi ul').removeAttr('style');
	};
});

function footNewPage(businesscode){
	ddpAjaxCall({
				url : $.base+"/application/checkAppStatus",
				data : businesscode,
				successFn : function(data) {
					if(data.code=="000000"){
						window.open($.base+"/application/toApplicationPage?businesscode="+businesscode,"app","","true");
					}else{
						$.messagerBox({type: 'warn', title:"温馨提示", msg: data.message});
					}
				}
				});
}

function yw(flag){
if(flag == true){
		return;
	}else{
		$.messagerBox({type: 'warn', title:"温馨提示", msg: "你所点击的业务尚未开通，请耐心等候......"});
		return;
	}
}
</script>
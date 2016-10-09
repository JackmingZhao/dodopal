<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>一卡通消费</title>
<#include "../../../include.ftl"/>
<!-- InstanceEndEditable -->
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/calendar/calendar.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/common/footPageEven.js" ></script>
<script type="text/javascript" src="${base}/js/tranRecord/historyTran/merchant/consumerOrderQuery.js" ></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../../../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">
	<#include "../historyNav.ftl"/>
	</h3>
	<div class="seach-top-box">
		<div class="data-tit">
			<#include "consumerOrderNav.ftl"/>
		</div>
		<div class="seach-top-inner">
			<form action="/" id="queryForm">
				
				<ul class="clearfix">
					<li>
						<label><span>订单号：</span>
							<input type="text" id="transactionidQuery" class="com-input-txt w88" id=''>
						</label>
					</li>
                     <li class="w210"><span>订单状态：</span>
						<select id="statusQuery" name="statusQuery">
							<option value='' selected="selected">--请选择--</option>
							<option value='0'>订单记账</option>
							<option value='1'>成功订单</option>
							<option value='2'>失败订单</option>
							<option value='3'>可疑订单</option>
						</select>
					</li>	
					<li>
						<label><span>POS号：</span>
							<input id="posidQuery" type="text" class="com-input-txt w88">
						</label>
					</li>
					<li class="w210">
						<label><span>卡号：</span>
							<input id="cardnoQuery" type="text" class="com-input-txt w88">
						</label>
					</li>
					<li class="btn-list">
						<input type="button" onclick='findConsumptionOrderList();' value="查询" class="orange-btn-h26" />
						<input type="button" value="清空" class="orange-btn-text26" onclick="myClearQueryForm();"/>
					</li>				
				</ul>
				<ul class = "clearfix">
					<li>
						<label><span>用户名称：</span>
						<input id="username" type="text" class="com-input-txt w88">
						</label>
					</li>
					<li class="w320">
						<span>起止日期：</span>
						<input id="startdate" name="startday" type="text" class="com-input-txt w74"  placeholder="日历"  onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input id="enddate" name="startday" type="text" class="com-input-txt w74"  placeholder="日历"  onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="time">
						<label>
							<input type="radio" name="time" value="1W"/><em>近一周</em>
						</label>
						<label>
							<input type="radio" name="time" value="1M"/><em>近一个月</em>
						</label>
						<label>
							<input type="radio" name="time" value="3M"/><em>近三个月</em>
						</label>
					</li>
				</ul>
				
			</form>
			</div>
		</div>
	 <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
       <div class="com-bor-box">
		<table id='displayTbl' cellpadding="0" cellspacing="0" class="com-table01 mb20">
		<col width="1%" />
	    <col width="7%" />
	    <col width="6%" />
	    <col width="6%" />
	    <col width="5%" />
	    <col width="5%" />
	    <col width="7%" />
	    <col width="6%" />
        <col width="6%" />
	    <col width="6%" />
        <col width="6%" />
        <col width="7%" />
	    <col width="6%" />
        <col width="6%" />
        <col width="7%" />
        <col width="6%" />
        <col width="1%" />
        <thead>
        <tr>
	      <th></th>
	      <th>订单号</th>
	      <th>商户名称</th>
	      <th>用户名称</th>
	      <th>卡号</th>
	      <th>POS号</th>
	      <th>交易前金额</th>
	      <th>应收金额</th>
	      <th>用户折扣</th>
	      <th>实收金额</th>
	      <th>交易后余额</th>
	      <th>结算折扣</th>
	      <th>结算金额</th>
	      <th>结算手续费</th>
	      <th>交易时间</th>
	      <th>订单状态</th>
	      <th></th>
	      </tr>
	      </thead>
	      <tbody>
	      </tbody>
		</table>
		<div class="null-box"></div>
		<p class="page-navi"></p>
	</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<#include "../../../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="历史交易记录"){
			$(this).addClass('cur');
		}
	});
	$('[js="jiaoyi"]').click(function(){
		$('[js="jiaoyiBox"]').show();
	});
	$('.data-tit a').click(function(event) {
		if($.trim($(this).text()) =="消费统计"){
			$(this).addClass('currents').siblings().removeClass();
		}else{
			$(this).addClass('current').siblings().removeClass();
		}
	});

});
</script>
<div class="pop-win" js="jiaoyiBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:180px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px;height:170px;">
		<h3>交易记录下载</h3>
		<div class="innerBox">
			<form action="/">
				<table  class="base-table">
					<col width="105" />
					<tr>
						<th>下载到：</th>
						<td><input type=file name="j" style="display: none;" onchange="ye.value=value">
							<input name=ye class="com-input-txt w260">
							<input type=button value="浏览" onclick="j.click()" class="btn-orange"></td>
					</tr>
					<tr>
						<td colspan="2" class="a-center"><input type="submit" class="pop-borange mr20" value="确认">
							<a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<!-- InstanceEndEditable --> 
<script type="text/javascript">
$(document).ready(function(e) {
	//顶部提示信息
	var timer= setInterval(function(){
		$('.warning span').animate({'left':-230+'px'},10000,"linear").animate({'left':$('.warning').width()+'px'},0);
		},500);
	
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
<script src="${base}/js/common/select.js"></script>
</body>
<!-- InstanceEnd --></html>

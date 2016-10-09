<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>手持POS机充值</title>
<#include "../../../include.ftl"/>
<!-- InstanceEndEditable -->
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${base}/js/select.js"></script>
<script type="text/javascript" src="${base}/js/common/footPageEven.js" ></script>
<script type="text/javascript" src="${base}/js/tranRecord/historyTran/merchant/ccOrderPosCount.js" ></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
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
	<#include "../historyNav.ftl"/>
	<div class="seach-top-box">
		<div class="data-tit">
					<#include "ccOrderPosNav.ftl"/>
		</div>
		<div class="seach-top-inner">
			<form action="/" id="queryForm">
				<ul class = "clearfix">
					
					<li class="w320">
						<span>起止日期：</span>
							<input name="startdate" id="startdate" type="text" class="com-input-txt w74"  placeholder="日历" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="enddate"  id="enddate" type="text" class="com-input-txt w74"  placeholder="日历"  onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="btn-list">
						<input type="button" value="查询"  onclick='findCzOrderByPosByPage();'class="orange-btn-h26" id=/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="myClearQueryForm()" />
					</li>				
				</ul>
				
			</form>
		</div>
	</div>
	 <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
        	<li class="fl">统计结果</li>
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
        <div class="com-bor-box">
		<table cellpadding="0" cellspacing="0" class="com-table01 mb20" id="displayTbl">
		<col width="1%" />
	    <col width="7%" />
	    <col width="6%" />
	    <col width="6%" />
	    <col width="5%" />
	    <col width="5%" />
	    <col width="6%" />
	    <col width="6%" />
        <col width="6%" />
	    <col width="6%" />
        <col width="6%" />
        <col width="1%" />
            <thead>
        <tr>
	      <th></th>
	      <th>POS号</th>
	      <th>用户名称</th>
	      <th>交易总笔数</th>
	      <th>交易总金额</th>
	      <th>交易成功笔数</th>
	      <th>交易成功金额</th>
	      <th>交易失败笔数</th>
	      <th>交易失败金额</th>
	      <th>可疑交易笔数</th>
	      <th>可疑交易金额</th>
	      <th></th>
	      </tr>
		</table>
		<div class="null-box"></div>
		<p class="page-navi clearfix"><span class="fl fls"></span></p>
			<div class="com-bor-box02">
	        <ul class="navi-ul01 clearfix">
	        	<li class="fl">合计结果</li>
				<li class="fr">金额单位(元)</li>
			</ul>
        </div>
	<table cellpadding="0" cellspacing="0" class="com-table01 mb20 tdthCenter" id="displayTblCount">
	    <col width="4%" />
	    <col width="30%" />
	    <col width="30%" />
	    <col width="20%" />
	    <col width="4%" />
	       <thead>
        <tr>
          <th></th>
	      <th>合计</th>
	      <th>笔数</th>
	      <th>金额</th>
	      <th></th>
	     </tr>
		</table>
	</div>
 </div>
	
	<!-- InstanceEndEditable --> </div>
<#include "../../../footer.ftl"/> 
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
		if($.trim($(this).text()) =="充值订单统计"){
			$(this).addClass('currents').siblings().removeClass();
		}else{
			$(this).addClass('current').siblings().removeClass();
		}
	});

});
</script>

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
</body>
<!-- InstanceEnd --></html>

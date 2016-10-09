<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>一卡通支付充值页面</title>
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<link rel='stylesheet' type='text/css' href='${scriptUrl}/common/datepicker/skin/default/datepicker.css' />
<script type="text/javascript" src="../js/prvd/merchant/transactionDetails.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript">
var baseUrl = "${base}";
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>

<#include "../../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3"><a href="javascript:void(0)" class="cur" onclick="searchData(1,'cz')" id="cz">一卡通充值</a><i class="i-line"></i><a href="javascript:void(0)" id="xf" onclick="searchData('2','xf')">一卡通消费</a></h3>
	<div class="com-bor-box com-bor-box01">
		<div class="deal">
		<div class="seach-top-inner">
			<form action="/" id="frm">
				<ul class="clearfix">
					<li>
					<input type='hidden' id="merName" name="merName" value="${merName}">
					<input type='hidden' id="merCode" name="merCode" value="${merCode}">
					<input type='hidden' id="yktCode" name="yktCode" value="${yktCode}">
						<h3 class="pdf10">交易明细</h3>
						<label><span class="w70">商户名称：</span>
							<span class="a-left w300">${merName}</span>
						</label>
					</li>
				</ul>
				<ul class="clearfix">
					<li>
						<label><span class="w70">订单编号：</span>
							<input type="text" class="com-input-txt w88" name="proOrderNum" id="proOrderNum"/>
						</label>
					</li>
					<li class="w350"><span class="w130"></span>
						
						  <input type="text" class="com-input-txt w74"  placeholder="日历" id="orderDateStart" name="orderDateStart" value="${orderDateStart}" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  -
						  <input type="text" class="com-input-txt w74"  placeholder="日历" id="orderDateEnd" name="orderDateEnd" value="${orderDateEnd}" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						
					<!--
					
						  <input type="text" readOnly="true" class="com-input-txt w74" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'orderDateEnd\')||\'2050-10-01\'}'})"  placeholder="日历" id="orderDateStart" name="orderDateStart" value="${orderDateStart}"/>
						  -
						  <input type="text" readOnly="true" class="com-input-txt w74"  placeholder="日历" id="orderDateEnd" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'orderDateStart\')}',maxDate:'2050-10-01'})" name="orderDateEnd" value="${orderDateEnd}"/>
					  -->
					  </li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findTransaction()"/>
						<input type="reset" value="清空" class="orange-btn-text26" />
					</li>
				</ul>
			</form>
		</div>
	</div><input type="hidden" id="num" value='1'/>
			<form action="/">
			<div class="w890 mb0">
			<table cellspacing="0" cellpadding="0" border="0" class="com-table01" id= "productOrderTbl">
			<colgroup>
			<col width="2%">
			<col width="13%">
			<col width="7%">
			<col width="13%">
			<col width="9%">
			<col width="5%">
			<col width="10%">
			<col width="2%">
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th>订单编号</th>
				<th id="txnAmt"></th>
				<th>卡号</th>
				<th id="blackAmt"></th>
				<th>订单状态</th>
				<th id="proOrderDate"></th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</tbody></table>
		<div class="null-box" style="display:none;"></div>
		<p class="page-navi"><span class="fl">信息下载：<a href="javascript:;" id="_excelDownLoad"  >导出Excel</a></span></p>
		</div>
		</form>
		<div class="a-center btn-box">
				<a href="javascript:void(0)" onclick="jumpPage()" class="orange-btn-h32">返回</a>
			<!--	
				<a href="${base}/prvd/merchant?merName=${mmerName}&merUserName=${merUserName}&merUserMobile=${merUserMobile}&_pageSize=${_pageSize}&_pageNo=${_pageNo}&flag=1" class="orange-btn-h32">返回</a>
			-->
			
			</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<form action="${base}/prvd/merchant" method="post" id="_form">
  <input type="hidden" name="merName" value="${mmerName}"/>
  <input type="hidden" name="merUserName" value="${merUserName}"/>
  <input type="hidden" name="merUserMobile" value="${merUserMobile}"/>
  <input type="hidden" name="_pageSize" value="${_pageSize}"/>
  <input type="hidden" name="_pageNo" value="${_pageNo}"/>
  <input type="hidden" name="flag" value="1"/>
</form>
<div class="foot">
	<!-- <div class="footer-navi">
	<div class="foot-bg">
		<div class="foot-inner">
			<div class="home"><i class="san"></i><a href="#"><i></i>应用产品</a></div>
			<ul style="height:60px;">
				<li class="navi"><a href="#"><i class="one"></i>一卡通充值</a></li>
                <li class="navi"><a href="#"><i class="eight"></i>一卡通消费</a></li>
				<li class="navi"><a href="#"><i class="two"></i>手机充值</a></li>
				<li class="navi"><a href="#"><i class="three"></i>水费缴纳</a></li>
				<li class="navi"><a href="#"><i class="four"></i>电费缴纳</a></li>
				<li class="navi"><a href="#"><i class="five"></i>天然气缴纳</a></li>
				<li class="navi"><a href="#"><i class="six"></i>有线电视费</a></li>
			</ul>
			<div class="more"><a href="javascript:;"></a></div>
			<div class="erweima"><img src="css/images/img_code.png" alt="" /></div>
		</div>
	</div>
    </div> -->
    <div class="footer-copy">
        <div class="w1030 footer-p">
        	<p><a href="http://www.dodopal.com/" target="_blank">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="http://weibo.com/dodopal" target="_blank">http://weibo.com/dodopal</a></p>
            <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
            <p class="orange">使用完毕请安全退出，请勿泄露自己的登录名和密码</p>
        </div>
    </div>
</div>

<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="商户信息"){
			$(this).addClass('cur');
		}
	});
});
</script> 
<div class="pop-win" js="mobanBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:180px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px;height:170px;">
		<h3>导出（Excel格式）</h3>
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
	$('[js="moban"]').click(function(){
		$('[js="mobanBox"]').show();
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
function jumpPage(){
	$("#_form").submit();
}
</script>
</body>

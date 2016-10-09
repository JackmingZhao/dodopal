<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>一卡通消费-交易明细</title>
<#include "../include.ftl"/>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../js/prvd/cardConsumDetailForSupplier.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
var baseUrl = "${base}";

</script>
 <link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">一卡通消费-交易明细</h3>
	<div class="com-bor-box com-bor-box01">
		<div class="deal">
		<div class="seach-top-inner">
			<form action="/" id="frm">
				<ul class="clearfix">
					<li>
						<label><span class="w70">POS号：</span>
							<span class="a-left">${proCode}</span>
							<input type="hidden" id="proCode" name="proCode" value="${proCode}"/>
						</label>
					</li>
				</ul>
				<ul class="clearfix">
					
					<li>
						<label><span class="w70">订单编号：</span>
							<input type="text" id="proOrderNum" name="proOrderNum"  class="com-input-txt w88" />
						</label>
					</li>
					<li>
						<label><span class="w130">商户名称：</span>
							<input type="text" id="merName" name="merName" class="com-input-txt w88" />
						</label>
					</li>
					<li class="w350"><span class="w130">交易日期：</span>
						  <input  value=${orderDateStart} name="orderDateStart" type="text" class="com-input-txt w74" readonly="true"    id="orderDateStart" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  -
						  <input value=${orderDateEnd} name="orderDateEnd" type="text" class="com-input-txt w74" readonly="true"    id="orderDateEnd" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					  </li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findCardconsumForDetails()"/>
						<input type="reset" value="清空" class="orange-btn-text26"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
			<form action="/">
			<div class="w890 mb0">
			<table cellspacing="0" id="supplierDetailsTb" cellpadding="0" border="0" class="com-table01">
			<colgroup>
			<col width="1%">
			<col width="15%">
			<col width="7%">
			<col width="7%">
			<col width="11%">
			<col width="10%">
			<col width="5%">
			<col width="12%">
			<col width="1%">
			</colgroup><thead>
			<tr>
				<th>&nbsp;</th>
				<th>订单编号</th>
				<th>商户名称</th>
				<th>消费金额（元）</th>
				<th>卡号</th>
				<th>消费后卡内余额（元）</th>
				<th>订单状态</th>
				<th>消费时间</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div class="null-box" style="display:none;"></div>
		<p class="page-navi" id="cardConsumDetailForSupplier"><span class="fl">交易记录下载：                                                                                     
		<a href="javascript:;"  class="easyui-linkbutton" iconCls="icon-import" plain="true"  id="_excelDownLoad">导出Excel</a></span></p>
			<div class="a-center btn-box">
				<a href="javascript:void(0)" onclick="jumpPage()" class="orange-btn-h32">返回</a>
			</div>
		</div>
		</form>
	</div>
</div>
<form action="${base}/prvd/cardConsumForSupplier" method="post" id="_form">
  <input type="hidden" name="merName" value="${merName}"/>
  <input type="hidden" name="bind" value="${bind}"/>
  <input type="hidden" name="proCode" value="${proCode}"/>
  <input type="hidden" name="_pageSize" value="${_pageSize}"/>
  <input type="hidden" name="_pageNo" value="${_pageNo}"/>
  <input type="hidden" name="flag" value="1"/>
</form>
<div class="foot">
    <div class="footer-copy">
        <div class="w1030 footer-p">
            <p><a href="http://www.dodopal.com/">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="#">http://weibo.com/dodopal</a></p>
            <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
            <p class="orange">使用完毕请安全退出，请勿泄露自己的登录名和密码</p>
        </div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="POS信息管理"){
			$(this).addClass('cur');
		}
	});
});
</script> 
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
$("proOrderNum").val('');
$("merName").val('');
$("#_form").submit();
}
</script>
</body>
<!-- InstanceEnd --></html>

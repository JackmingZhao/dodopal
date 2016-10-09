<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>账户余额</title>
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantAccount/chlidAccountBalance.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<script type="text/javascript">
var baseUrl = "${base}";
</script>
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<#include "../../childrenPosNav.ftl"/>
	<ul class="tab-list01 clearfix">
		<li><a href="${base}/merchantChildAct/amt" class="cur">账户余额</a></li>
		<li><a href="${base}/merchantChildAct/list">资金变更记录</a></li>
	</ul>
	<div class="seach-top-box pb0">
		<div class="seach-top-inner">
			<form action="balanceFormExport" id="balanceForm">
				<ul class="clearfix">
					<li class="w240">
						<label><span>商户名称：</span>
							<input type="text" id="merName" name="merName" class="com-input-txt w88" />
						</label>
					</li>
					<li class="w240">
						<label><span>资金类型：</span>
							<select style="width:108px;padding:3px 5px" id="fundType" name="fundType">
								<option value="0" selected="selected">资金账户</option>
								<option value="1">授信账户</option>
							</select>
						</label>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findChlidAccountBalance()"/>
						<input type="reset" value="清空" class="orange-btn-text26" onclick="clearTranRecord('balanceForm')"/>
					</li>
				</ul>
			</form>
		</div>
        <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
		<table id="accountBalanceTb" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<col width="2%" />
			<col width="3%" />
			<col width="16%" />
			<col width="16%" />
			<col width="16%" />
			<col width="16%" />
			<col width="16%" />
			<col width="16%" />
			<col width="2%" />
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>商户名称</th>
					<th>账户余额</th>
					<th>冻结金额</th>
					<th>可用金额</th>
					<th>资金变更明细</th>
					<th>交易记录</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<p class="page-navi"><span class="fl">账户余额下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportChildified"
		       onclick="exportChildified()">导出Excel</a></span></p>
	</div>
	<form id="listForm" action="balanceFormExport" method="post"></form>
</div>
<#include "../../footer.ftl"/>
<script type="text/javascript">
$(document).ready(function(e) {
	$('[js="zhangdan"]').click(function(){
		$('[js="zhangdanBox"]').show();
	});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="子商户管理"){
			$(this).addClass('cur');
		}
	});
	$('.tit-h3 a').each(function(){
		if($.trim($(this).text())=="子商户账户信息"){
			$(this).addClass('cur');
		}
	});
	$('.tab-list01 a').each(function(){
		if($.trim($(this).text())=="账户余额"){
			$(this).addClass('cur');
		}
	});

});
</script>
<script type="text/javascript">
$(document).ready(function(e) {
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

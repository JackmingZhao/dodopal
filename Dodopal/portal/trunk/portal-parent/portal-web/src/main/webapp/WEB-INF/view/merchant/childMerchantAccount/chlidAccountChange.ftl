<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>子商户资金变更记录</title>
<#include "../../include.ftl"/>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantAccount/chlidAccountChange.js"></script>
<script src="${base}/js/common/select.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">
</head>
<body>
<#include "../../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<#include "../../childrenPosNav.ftl"/>
	<ul class="tab-list01 clearfix">
		<li><a href="${base}/merchantChildAct/amt">账户余额</a></li>
		<li><a href="${base}/merchantChildAct/list" class="cur">资金变更记录</a></li>
	</ul>
	<div class="seach-top-box pb0">
		<div class="seach-top-inner">
			<form action="accountActListFormExport" id="changeForm">
				<input type="hidden" id="merCode" name="merCode" value="${merCode!}"/>
				<ul class="clearfix">
					<li class="w210">
						<label><span>商户名称：</span>
							<input type="text" class="com-input-txt w88" id="merName" name="merName" value="${merName!}"/>
						</label>
					</li>
					<li class="w300">
						<span>日期范围：</span>
							<input name="startDate" type="text" class="com-input-txt w74" readonly="true"  id="startDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="endDate" type="text" class="com-input-txt w74"  readonly="true" id="endDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="w300"><span>变动金额范围：</span>
						<input type="text" class="com-input-txt w74" id="changeAmountMin"  name="changeAmountMin"/>
						-
						<input type="text" class="com-input-txt w74" id="changeAmountMax" name="changeAmountMax"/>
						元</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findAccountChange()"/>
						<input type="button" value="清空" class="orange-btn-text26"onclick="clearTranRecord('changeForm')" />
					</li>
				</ul>
				<ul class="clearfix">
					<li class="w240">
						<label><span>资金流向：</span>
							<select id="changeType" name="changeType">
								<option selected="selected" value="">--请选择--</option>
								<option value="0">充值</option>
								<option value="1">冻结</option>
								<option value="2">解冻</option>
								<option value="3">扣款</option>
								<option value="4">转入</option>
								<option value="5">转出</option>
								<option value="6">退款</option>
								<option value="7">正调账</option>
								<option value="8">反调账</option>
							</select>
						</label>
					</li>
				</ul>
			</form>
		</div>
         <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
		<table id="accountChangeTb" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<col width="2%" />
			<col width="3%" />
			<col width="13%" />
			<col width="13%" />
			<col width="13%" />
			<col width="13%" />
			<col width="13%" />
			<col width="9%" />
			<col width="17%" />
			<col width="2%" />
			<thead>
				<tr>
					<th>&nbsp;</th>
					 <th>序号</th>
					<th>商户名称</th>
					<th>账户类别</th>
					<th>变动前账户总余额</th>
					<th>变动金额</th>
					<th>变更前账户可用余额</th>
					<th>资金流向</th>
					<th>资金变更时间</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<p class="page-navi"><span class="fl">子商户资金变更记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportChildified" onclick="exportExcel('accountActListFormExport','changeForm')">导出Excel</a></span></p>
	</div>
	<form id="listForm" action="changeFormExport" method="post"></form>
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
		if($.trim($(this).text())=="资金变更记录"){
			$(this).addClass('cur');
		}
	});

});
</script>
<!-- InstanceEndEditable --> 
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

<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>历史交易记录-网点</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/tranRecord/offLineTradeList.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/common/ocxCommon.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>

<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">
			<a href="${base}/tran/cardTradeList">一卡通充值</a>
			<a href="${base}/tran/offLineTradeList" class = "cur">一卡通消费</a>
	</h3>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form action="offLineTradeListExport" id="offLineTradeListForm">
			<input type="hidden" id="proxyid" name="proxyid"  value="${proxyid!}">
				<ul class="clearfix">
					<li>
						<label><span>POS号：</span>
							<input type="text" id="posId" name="posId" class="com-input-txt w88" style="width:75px;">
						</label>
					</li>
                     <li class="w490"><span>订单状态：</span>
						<select id="status" name="status">
							<option selected="selected" value="">--请选择--</option>
							<option value="1000000001">未支付</option>
							<option value="1000000007">支付成功</option>
							<option value="1000000011">支付失败</option>
							<option value="1000000005">支付可疑</option>
							<option value="1000000008">可以进行补充值</option>
							<option value="1000000009">补充值完成</option>
						</select>
					</li>	
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findOffLineTradeList()"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearTranRecord('offLineTradeListForm')" />
					</li>				
				</ul>
				<ul class = "clearfix">
					<li class="w320">
						<span>起止日期：</span>
							<input name="createDateStart" type="text" class="com-input-txt w74" readonly="true"   id="createDateStart" onfocus="c.showMoreDay = false;c.show(this,'');c.clearRadio();"/>
							-
							<input name="createDateEnd" type="text" class="com-input-txt w74"  readonly="true" id="createDateEnd" onfocus="c.showMoreDay = false;c.show(this,'');c.clearRadio();"/>
					</li>
					<li class="time" id="timeRadio">
						<label>
							<input type="radio" name="time" onfocus="clearDate();" value="7"/><em>近一周</em>
						</label>
						<label>
							<input type="radio" name="time" onfocus="clearDate();" value="30"/><em>近一个月</em>
						</label>
						<label>
							<input type="radio" name="time" onfocus="clearDate();" value="90"/><em>近三个月</em>
						</label>
					</li>
				</ul>
				
			</form>
		</div>
	 <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
        	<li class="fl">查询结果</li>
			<li class="fr">金额单位(元)</li>
		</ul>
     </div>
		<table cellpadding="0" cellspacing="0" class="com-table01 mb20" id="offLineTradeListTb">
		<colgroup>
	    <col width="2%" />
	    <col width="3%" />
	    <col width="8%" />
	    <col width="8%" />
	    <col width="8%" />
	    <col width="5%" />
	    <col width="5%" />
	    <col width="5%" />
	    <col width="5%" />
        <col width="5%" />
        <col width="5%" />
        <col width="6%" />
        <col width="5%" />
        <col width="4%" />
        </colgroup>
        <thead>
        <tr>
          <th></th>
          <th>序号</th>
	      <th>订单号</th>
	      <th>卡号</th>
	      <th>POS号</th>
	      <th>消费金额</th>
	      <th>折扣</th>
		  <th>实付金额</th>
	      <th>原有金额</th>
	      <th>卡余额</th>
	      <th>POS备注</th>
	      <th>交易时间</th>
	      <th>订单状态</th>
	      <th class="a-center">操作</th>
	      </tr>
	      </thead>
	      <tbody></tbody>
		</table>
		
		<div class="null-box"></div>
		<p class="page-navi clearfix" id="offLineTradeListPage"><!--<span class="fl">	交易记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportOffLineTradeList"
		       onclick="exportExcel('offLineTradeListExport','offLineTradeListForm')">导出Excel</a></span>--></p>
		<div class="com-bor-box02">
	        <ul class="navi-ul01 clearfix">
	        	<li class="fl">统计结果</li>
				<li class="fr">金额单位(元)</li>
			</ul>
        </div>
        <table cellpadding="0" cellspacing="0" class="com-table01 mb20 tdthCenter" id="offLineTradeListTbCount">
	   <colgroup>
	    <col width="4%" />
	    <col width="30%" />
	    <col width="30%" />
	    <col width="20%" />
	    <col width="4%" />
	    </colgroup>
	    <thead>
        <tr>
          <th></th>
	      <th>合计</th>
	      <th>笔数</th>
	      <th>金额</th>
	      <th></th>
	     </tr>
	      </thead>
	      <tbody></tbody>
		</table>
	</div>
	
	<!-- InstanceEndEditable --> </div>
<div style="display:none;">
	<ul class="jige-ul mb20" id="printSpan">
		<li style="display:none">商户名称：<span id="merName"></span></li>
		<li><em>订单号：</em><span id="printOrderNumRes"></span></li>
		<li><em>终端流水号：</em><span id="printTranCode"></span></li>
		<li><em>卡号：</em><span id="printCardCode"></span></li>
		<li><em>消费金额：</em><span class="orange" id="printConsumeMoneySpan"></span>元</li>
		<li><em>折扣：</em><span class="orange" id="printDiscountSpan"></span></li>
		<li><em>实付金额：</em><span class="orange" id="printMoneySpan"></span>元</li>
		<li><em>原有金额：</em><span class="orange" id="printSMoneySpan"></span>元</li>
		<li><em>卡余额：</em><span class="orange" id="printCardMoneySpan"></span>元</li>
		<li><em>POS号：</em><span id="printPosCode"></span></li>
		<li><em>交易时间：</em><span id="printConsumeTime"></span></li>
		<li><em>状态：</em><span id="printConsumestate"></span></li>
	</ul>
</div>
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

});
</script>
<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

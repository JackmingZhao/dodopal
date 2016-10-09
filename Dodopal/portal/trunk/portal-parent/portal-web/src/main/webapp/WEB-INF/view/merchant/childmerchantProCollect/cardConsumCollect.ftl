<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>业务订单汇总</title>
<!-- InstanceEndEditable -->
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<#include "../../include.ftl"/>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript">
var baseUrl = "${base}";
</script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/merchant/childmerchantProCollect/cardConsumCollect.js"></script>
<style>
.deal li{float:none!important;height:24px;}
.w100{width:100%;}
</style>
</head>
<body>

<#include "../../header.ftl"/>
<div class="con-main" id="cardConsumeCollectForm"> <!-- InstanceBeginEditable name="main" -->
    <#include "../../childrenPosNav.ftl"/>
	<ul class="tab-list01 clearfix">
	   <@sec.accessControl permission="merchant.child.ordersum.recharge">
		  <li><a href="${base}/childMerProductOrder/childMerProductOrderSum" >一卡通充值</a></li>
		</@sec.accessControl>
	   <@sec.accessControl permission="merchant.child.ordersum.purchase">
		  <li><a href="${base}/prvd/cardConsumCollect" class="cur">一卡通消费</a></li>
	   </@sec.accessControl>
	</ul>
	<div class="seach-top-box pb0">
		<div class="seach-top-inner">
			<form action="exportCardConsumCollect" id="cityMerchantPosForm">
				<ul class="clearfix">
					<li class="w130">
						<label><span>商户名称：</span>
							<input type="text" class="com-input-txt w88" id="merName" name="merName"/>
						</label>
					</li>
				<!--	<li>
						<label><span class="w70">POS号：</span>
							<input type="text" class="com-input-txt w88" id="proCode" name="proCode"/>
						</label>
					</li> -->
					
					<li><span class="w80">订单状态：</span>
						<select name="proOrderState" id="proOrderState">
                            <option selected="selected" value="2">成功</option>
                            <option  value="1">失败</option>
                       <!--     <option  value="3">支付可疑</option> -->
                            <option  value="3">可疑</option>
						</select>
					</li> 
					<li class="w350"><span class="w70">交易日期：</span>
						  <input name="startDate" type="text" readonly="true" value="${orderDateStart!}" class="com-input-txt w74"  id="startDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  -
						  <input name="endDate" type="text" readonly="true" value="${orderDateEnd!}" class="com-input-txt w74"  id="endDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					  </li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findCardConsumCollect()"/>
						<input type="button" value="清空" class="orange-btn-text26"  onclick="clearConsumeCollect('cityMerchantPosForm')"/>
					</li>
				</ul>
			</form>
		</div>
        <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
	<table id="supplierTb" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
		<colgroup>
				<col width="2%" />
				<col width="8%" />
			<!--<col width="15%" />
				<col width="10%" />
				<col width="10%" />-->
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
				<col width="2%" />
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th class="a-center">序号</th>
				<!--<th>交易日期</th>-->
					<th>商户名称</th>
				<!--<th>业务城市</th>
			        <th>POS号</th>-->
					<th>消费笔数</th>
					<th>消费应付金额</th>
					<th>消费实付金额</th>
					<th>折扣金额</th>
				<!--<th>POS备注</th>-->
					<th class="a-center">操作</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
				<tbody></tbody>
		</table>
		<div class="null-box"></div>
		 <p class="page-navi" id="cardConsumCollect">
		   <span  class="fl"> 账单下载：<a  href="#"  onclick="exportExcel('exportCardConsumCollect','cityMerchantPosForm')">Excel格式</a></span>
		</p>
	</div>
</div>
	<#include "cardConsumCollectDetails.ftl"/>
<#include "../../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<!-- InstanceEndEditable --> 
</body>
<!-- InstanceEnd --></html>
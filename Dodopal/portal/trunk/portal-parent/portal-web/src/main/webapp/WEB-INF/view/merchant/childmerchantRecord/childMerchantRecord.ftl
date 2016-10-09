<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>子商户交易记录</title>
<#include "../../include.ftl"/>
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../js/merchant/childmerchantRecord/childmerchantRecord.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../../header.ftl"/>
<div class="con-main" id="childTranRecordMain"> <!-- InstanceBeginEditable name="main" -->
<#include "../../childrenPosNav.ftl"/>
<div class="seach-top-box pb0" id="tranRecordList">
		<div class="seach-top-inner">
			<form action="merChildTanlistExport" id="recordForm">
			<input type="hidden" id="merCode" name="merCode" value="${merCode!}"/>
				<ul class="clearfix">
					<li class="w310">
						<span class="w100">日期范围：</span>
							<input name="createDateStart" type="text" class="com-input-txt w74"  id="createDateStart" readonly="true" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="createDateEnd" type="text" class="com-input-txt w74"  id="createDateEnd" readonly="true" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="w190">
						<span class="w80">商户名称：</span>
							<input type="text" name="merOrUserName" id="merOrUserName" class="com-input-txt w88" value="${merName!}"/>
					</li>
                    <li class="w190"><span class="w80">交易类型：</span>
						<select name="tranType" id="tranType">
							<option selected="selected" value="">--请选择--</option>
							<option value="1">账户充值</option>
							<option value="3">账户消费</option>
							<option value="5">退款</option>
							<option value="9">转入</option>
							<option value="7">转出</option>
						</select>
					</li>
					<li class="w190"><span class="w80">订单号：</span>
					<input type="text" class="com-input-txt w88"  id="orderNumber" name="orderNumber"/>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findMerTranRecord()"/>
						<input type="reset" value="清空" class="orange-btn-text26" onclick="clearTranRecord('recordForm')"/>
					</li>
				</ul>
				<ul class="clearfix">
					<li class="w310"><span class="w100">交易金额范围：</span>
						<input type="text" class="com-input-txt w74" id="realMinTranMoney" name="realMinTranMoney"/>
						-
						<input type="text" class="com-input-txt w74" id="realMaxTranMoney" name="realMaxTranMoney"/>
						元</li>
					<li class="w190">
						<span class="w80">交易流水号：</span>
							<input type="text" class="com-input-txt w88" id="tranCode" name="tranCode"/>
					</li>
						
						<li class="w190"><span class="w80">交易状态：</span>
						<select id="tranOutStatus" name="tranOutStatus">
							<option selected="selected" value="">--请选择--</option>
						</select>
					</li>	
                    				
				</ul>
			</form>
		</div>
	 <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
		<table cellpadding="0" cellspacing="0" class="com-table01 mb20" id="transactionRecordTb">
				<col width="2%" />
				<col width="3%" />
				<col width="12%" />
				<col width="20%" />
				<col width="9%" />
				<col width="9%" />
				<col width="10%" />
				<col width="9%" />
				<col width="9%" />
				<col width="8%" />
				<col width="5%" />
				<col width="2%" />
			<thead>
	        	<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>商户名称</th>
					<th>交易流水号</th>
					<th>业务名称</th>
					<th>交易金额</th>
					<th>支付方式</th>
					<th>交易类型</th>
					<th>交易状态</th>
					<th>交易时间</th>
					<th class="a-center">操作</th>
					<th>&nbsp;</th>
				</tr>
		    </thead>
		    <tbody></tbody>
		</table>
		<@sec.accessControl permission="merchant.child.tranlist.export">
		   <p class="page-navi"><span class="fl">子商户交易记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportChildified"
		       onclick="exportExcel('merChildTanlistExport','recordForm')">导出Excel</a></span></p>
        </@sec.accessControl>
	</div>
 </div>
 <#include "childRecordView.ftl"/>
<#include "../../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

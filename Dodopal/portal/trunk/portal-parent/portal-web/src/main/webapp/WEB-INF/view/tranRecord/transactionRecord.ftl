<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>交易记录</title>
<#include "../include.ftl"/>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/tranRecord/transactionRecord.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main" id="tranRecordMain"> <!-- InstanceBeginEditable name="main" -->
<div class="seach-top-box com-top-bor" id="tranRecordList">
		<div class="seach-top-inner">
			<form action="tranRecordExport" id="recordForm">
			<input type="hidden" id="userCode" name="userCode" value="${userCode!}"/>
			<input type="hidden" id="userType" name="userType" value="${userType!}"/>
			<input type="hidden" id="tranTypeRecharge" name="tranTypeRecharge" value="${tranType!}"/>
				<ul class="clearfix">
					<li class="w320">
						<span class="w100">日期范围：</span>
							<input name="createDateStart" type="text" class="com-input-txt w74" readonly="true"   id="createDateStart" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="createDateEnd" type="text" class="com-input-txt w74"  readonly="true" id="createDateEnd" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="w210"><span>支付方式：</span>
					<input type="text" class="com-input-txt w88"  id="payWay" name="payWay"/>
					</li>
                    <li class="w210"><span>交易类型：</span>
						<select name="tranType" id="tranType">
							<option selected="selected" value="">--请选择--</option>
						</select>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findTranRecord()"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearTranRecord('recordForm')"/>
					</li>
				</ul>
				<ul class="clearfix">
					<li class="w320"><span class="w100">交易金额范围：</span>
						<input type="text" class="com-input-txt w74" id="realMinTranMoney" name="realMinTranMoney"/>
						-
						<input type="text" class="com-input-txt w74" id="realMaxTranMoney" name="realMaxTranMoney"/>
						元</li>
                     <li class="w210"><span>交易状态：</span>
						<select id="tranOutStatus" name="tranOutStatus">
							<option selected="selected" value="">--请选择--</option>
						</select>
					</li>	
					<li class="w210"><span>订单号：</span>
					<input type="text" class="com-input-txt w88"  id="orderNumber" name="orderNumber"/>
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
			<colgroup>
			    <col width="2%" />
			    <col width="3%" />
			    <col width="20%" />
			    <col width="10%" />
			    <col width="10%" />
			    <col width="10%" />
			    <col width="10%" />
			    <col width="10%" />
			    <col width="15%" />
		        <col width="5%" />
		        <col width="2%" />
	        </colgroup>
			<thead>
	        	<tr>
			      <th></th>
			      <th>序号</th>
			      <th>交易流水号</th>
			      <th>业务名称</th>
			      <th>交易金额</th>
			      <th>支付方式</th>
			      <th>交易类型</th>
			      <th>交易状态</th>
			      <th>交易时间</th>
			      <th class="a-center">操作</th>
			      <th></th>
		       </tr>
		    </thead>
		    <tbody></tbody>
		</table>
		<div class="null-box" style="display:none;"></div>
		<p class="page-navi" id="transactionRecord">
		<@sec.accessControl permission="tran.record.export">
		   <span class="fl">交易记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportTraified"
		       onclick="exportExcel('tranRecordExport','recordForm')">导出Excel</a></span>
		</@sec.accessControl>
		</p>
	</div>
	<form id="submitForm" action="tranRecordExport" method="post"></form>
</div>
	<#include "transactionRecordView.ftl"/>
	<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

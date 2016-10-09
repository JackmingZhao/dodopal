<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>卡片交易记录</title>
<#include "../include.ftl"/>
<!--<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />-->
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/tranRecord/cardRecord.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main" id="cardRecordMain"> <!-- InstanceBeginEditable name="main" -->
	<div class="seach-top-box com-top-bor" id="cardRecordList">
		<div class="seach-top-inner">
			<form action="cardRecordExport" id="cardRecordForm">
				<ul class="clearfix">
					<li class="w320">
						<span class="w100">日期范围：</span>
							<input name="startDate" type="text" class="com-input-txt w74" readonly="true"   id="startDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="endDate" type="text" class="com-input-txt w74"  readonly="true" id="endDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findCardRecord()"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearCardRecord('cardRecordForm')"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box">
		<table cellpadding="0" cellspacing="0" class="com-table01 mb20" id="cardRecordTb">
			<colgroup>
			    <col width="3%" />
			    <col width="5%"	/>
			    <col width="15%" />
			    <col width="13%" />
			    <col width="15%" /><!-- 50 -->
			    <col width="15%" />
			    <col width="15%" />
			    <col width="15%" />
		        <col width="3%" />
	        </colgroup>
			<thead>
	        	<tr>
			    	<th>&nbsp;</th>
			      	<th>序号</th>
			      	<th>交易时间</th>
			      	<th>交易金额（元）</th>
			      	<th>商户名称</th>
			      	<th>交易前卡余额（元）</th>
			      	<th>交易后卡余额（元）</th>
			      	<th>卡号</th>
			      	<th>操作</th>
			      	<th>&nbsp;</th>
		       	</tr>
		    </thead>
		    <tbody></tbody>
		</table>
		<div class="null-box" style="display:none;"></div>
		<#--<@sec.accessControl permission="tran.card.export">-->
		   <p class="page-navi"><span class="fl">交易记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportTraified"
		       onclick="exportExcel('cardRecordExport', 'cardRecordForm')">导出Excel</a></span></p>
		<#--</@sec.accessControl>-->
	</div>
	<form id="submitForm" action="cardRecordFormatExport" method="post"></form>
</div>
	<#include "cardRecordView.ftl"/>
	<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

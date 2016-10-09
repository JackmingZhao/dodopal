<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>资金变更记录</title>
<#include "../include.ftl"/>
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../js/ddp/accountChange.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="seach-top-box com-top-bor">
		<div class="seach-top-inner">
			<form action="ddpAmtExport" id="accountForm">
				<ul class="clearfix">
					<li class="w320">
						<span class="w100">日期范围：</span>
							<input type="text" id="startDate" name="startDate" readonly="true" class="com-input-txt w74"  onfocus="c.showMoreDay = false;c.show(this,'');" />
							-
							<input type="text" id="endDate" name="endDate" readonly="true" class="com-input-txt w74"  onfocus="c.showMoreDay = false;c.show(this,'');" />
					</li>
                    <li>
						<span class="w70">变动类型：</span>
							<select name="changeType" id="changeType">
								<option selected="selected" value="">--请选择--</option>
							</select>
					</li>

					<li class="w320"><span class="w100">变动金额范围：</span>
						<input type="text" class="com-input-txt w74" id="changeAmountMin"  name="changeAmountMin"/>
						-
						<input type="text" class="com-input-txt w74" id="changeAmountMax" name="changeAmountMax"/>
						元</li>                   		
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findChangeRecord()"/>
						<input type="reset" value="清空" class="orange-btn-text26" onclick="clearAccountRecord('accountForm')"/>
					</li>
				</ul>
			</form>
		</div>
        <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id="accountChangeTB">
				<col width="2%" />
				<col width="3%" />
				<col width="20%" />
				<col width="8%" />
				<col width="７%" />
				<col width="8%" />
				<col width="11%" />
				<col width="11%" />
				<col width="11%" />
                <col width="11%" />
				<col width="8%" />
				<thead>
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>交易流水号</th>
					<th>账户类别</th>
					<th>变动类型</th>
					<th>变动金额</th>
					<th>变动前可用余额</th>
					<th>变动前冻结金额</th>
                    <th>变动后可用余额</th>
                    <th>变动后冻结金额</th>
					<th>资金变更时间</th>
					<th>&nbsp;</th>
				</tr>
				</thead>
		    	<tbody></tbody>
			</table>
			<@sec.accessControl permission="ddp.amt.export">
			   <p class="page-navi" id="accountChange"><span class="fl">资金变更记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportAmtfiled"
			       onclick="exportExcel('ddpAmtExport','accountForm')">导出Excel</a></span></p>
			</@sec.accessControl>
	</div>
	<form id="recordForm" action="ddpAmtExport" method="post"></form>
</div>
	<#include "../footer.ftl"/>
</body>
</html>

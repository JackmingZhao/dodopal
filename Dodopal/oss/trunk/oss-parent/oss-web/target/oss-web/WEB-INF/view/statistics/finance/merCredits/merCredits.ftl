<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<meta http-equiv="Pragma" contect="no-cache">
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0"> 
	<#include "../../../include.ftl"/>
	    <script type="text/javascript" src="${base}/js/common/exportExcelUtil.js"></script>
	    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="${base}/js/statistics/finance/merCredits/merCredits.js"></script>
	<title>OSS交易流水</title>
</head>

<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:75px;overflow: hidden;">
    <form id="traForm" action="exportTransaction" method="post">  
		<table id="traQueryCondition" class="viewtable">
			<tr>
				<th>客户名称:</th>
				<td><input type="text" id="customerNameQuery" name="customerNameQuery" maxlength="40" style="width: 177px;"></td>
				<th>支付类型:</th>
				<td>
					<select id="payTypeQuery" name="payTypeQuery"  class="easyui-combobox" style="width: 180px;" panelHeight="auto" panelMaxHeight="75" editable="false" panelHeight="100%;">
						<option value='' selected>--请选择--</option>
						<option value="0">账户支付</option>
						<option value="1">一卡通支付</option>
						<option value="2">在线支付</option>
						<option value="3">银行支付</option>
					</select>
				</td>
				<th></th>
				<td>
					<@sec.accessControl permission="finance.merCredits.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findData(1);">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearQuery();">重置</a>
				</td>
			</tr>
			<tr>
			<th>客户分类:</th>
				<td>
				<select id="customerTypeQuery" name="customerTypeQuery"  class="easyui-combobox" style="width: 180px;" panelHeight="auto" panelMaxHeight="75" editable="false" panelHeight="100%;">
						<option value='' selected>--请选择--</option>
						<option value="0">个人</option>
						<option value="1">企业</option>
					</select>
				</td>
				<th>充值日期:</th>
				<td>
					<input type="text"  id="createDateStart" name="createDateStart" style="width:80px;" />
					   ~
	               	<input type="text"  id="createDateEnd" name="createDateEnd"  style="width:80px;" />
			 	</td> 
			 	<th></th>
				<td>
			 	</td>
			</tr>
		</table>
		</form>
	</div>
	<div region="center" border="false">
		<table id="traFlowTbl" fit="true"></table>
	</div>
	<div id="traFlowTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="finance.merCredits.export">
			<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportTra" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="traFlowTblPagination"></div>
	
</body>
</html>
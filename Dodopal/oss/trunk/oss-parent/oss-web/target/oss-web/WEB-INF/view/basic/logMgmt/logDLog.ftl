<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/basic/logMgmt/logDLog.js"></script>
	
</head>
<title>OCX日志管理</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="logDLogTblTable" class="viewtable">
			<tr>
				<th>产品库主订单号:</th>
					<td><input type="text" id="dlogPrdorderNumQuery"></td>
				<th>卡号:</th>
					<td><input type="text" id="dlogTradeCardQuery"></td>
				<th>创建日期:</th>
					<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#createDateEnd').val()});" id="createDateStart" name="createDateStart" style="width:80px;" />
					   ~
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#createDateStart').val()});" id="createDateEnd" name="createDateEnd"  style="width:80px;" />
				</td>
				<td style="width:20px;"></td>
				<td>
					<@sec.accessControl permission="logMgmt.ocxLog.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findOcxLog(1);">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('logDLogTblTable');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div region="center" border="false">
		<table id="logDLogTbl" fit="true"></table>
	</div>
	<div id="logDLogTblPagination"></div>
</body>
</html>
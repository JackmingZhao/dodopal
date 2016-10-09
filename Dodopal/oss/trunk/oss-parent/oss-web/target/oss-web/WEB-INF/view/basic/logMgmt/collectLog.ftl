<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/basic/logMgmt/collectLog.js"></script>
</head>
<title>OCX日志管理</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:75px;overflow: hidden;">
		<table id="collectLogTable" class="viewtable">
		<tr>
				<th>订单号:</th>
					<td><input type="text" id="orderNumQuery" ></td>
				<th>交易流水号:</th>
					<td><input type="text" id="tranNumQuery"></td>
				     <th>服务名称:</th>
				<td><input type="text" id="serverNameQuery"></td>
				<th>交易状态:</th>
					<td>
						<select id="tradeTypeQuery"  class="easyui-combobox" editable="false" panelHeight="70" style="width: 133px;">
							<option value='' selected>-请选择-</option>
							<option value="0">成功</option>
							<option value="1">失败</option>
						</select>
					</td>
				<td style="width:20px;"></td>
				<td>
					<@sec.accessControl permission="logMgmt.collectLog.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findCollectLog(1);">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('collectLogTable');">重置</a>
				</td>
			</tr>
			<tr>
				<th>来源:</th>
					<td><input type="text" id="sourceQuery"></td>
			</tr>
		</table>
	</div>
	
	<div region="center" border="false">
		<table id="collectLogTbl" fit="true"></table>
	</div>
	<div id="collectLogTblPagination"></div>
</body>
</html>
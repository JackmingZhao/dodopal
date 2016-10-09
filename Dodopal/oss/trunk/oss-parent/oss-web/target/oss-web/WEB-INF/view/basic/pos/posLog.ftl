<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../js/basic/pos/posLog.js"></script>
</head>
<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="posLogQueryCondition" class="viewtable">
			<tr>
				<th>商户名称:</th>
					<td><input type="text" id="merNameQuery"></td>
				<th>POS编号:</th>
					<td><input type="text" id="posCodeQuery"></td>
				<th>操作人:</th>
					<td><input type="text" id="operNameQuery"></td>
				<th>操作来源:</th>
					<td><select id="sourceQuery" panelHeight="70"></select></td>
				<td style="width:20px;"></td>
				<td>
					<@sec.accessControl permission="pos.log.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPosLog(1);">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('posLogQueryCondition');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div region="center" border="false">
		<table id="posLogTbl" fit="true"></table>
	</div>
	
	<div id="posLogTblToolbar" class="tableToolbar">
	  <@sec.accessControl permission="pos.log.export">
           <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnExportPosLog" >导出Excel</a>
	  </@sec.accessControl>
	</div>
	<div id="posLogTblPagination"></div>
</body>
</html>
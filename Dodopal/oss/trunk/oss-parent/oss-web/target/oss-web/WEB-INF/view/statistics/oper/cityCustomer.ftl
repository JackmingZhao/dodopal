<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>各城市客户群统计</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	
    <script type="text/javascript" src="../js/statistics/oper/cityCustomer.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../js/common/exportExcelUtil.js"></script>
    
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
	    <table id="queryCondition" class="viewtable">
	        <tr>
				<th>城市:</th>
				<td>
					<select id="activeCityQuery" name="activeCityQuery" class="easyui-combobox" editable="false" panelHeight="120" style="width: 133px;">
					</select>
				</td>
				
				<th>年份:</th>
				<td>
				<input type="text" style="width:120px"  id="queryDate" name="queryDate" required="true" />
				</td>
				<th></th>
				<td>
				<@sec.accessControl permission="operationalReport.cityCustomerStatistics.query">
                    <a href="#" id="queryBtn" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
				</@sec.accessControl>
				<a href="#" id="cancelBtn" class="easyui-linkbutton" iconCls="icon-eraser" >重置</a>
                </td>
			</tr>
	    </table>
	</div>
	
	<div region="center" border="false">
		<table id="statisticsDataTbl" fit="true" ></table>
	</div>
	<div id="statisticsDataToolbar" class="tableToolbar">
		<@sec.accessControl permission="operationalReport.cityCustomerStatistics.export">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="statisticsDataTblPagination"></div>

</body>
</html>


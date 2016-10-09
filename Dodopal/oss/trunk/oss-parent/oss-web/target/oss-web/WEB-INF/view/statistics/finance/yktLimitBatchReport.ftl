<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>购通卡额度</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="../../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/statistics/finance/yktLimitBatchReport.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		<form id="listForm" action="exportYktLimitBatchInfo" method="post">  
		    <table id="queryCondition" class="viewtable">
		        <tr>
		            <th>通卡公司名称:</th>
		            <td><input type="text" id="yktNameQuery" name = "yktNameQuery" style="width:175px"/></td>
		            <th>打款起止日期:</th>
		            <td>
						<input type="text" style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#payDateEndQuery').val()});" id="payDateStartQuery" name="payDateStartQuery"/>
						-
						<input type="text"  style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#payDateStartQuery').val()});" id="payDateEndQuery" name="payDateEndQuery"/>
					</td>
            		<th>通卡加款日期:</th>
		            <td>
						<input type="text" style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#addLimitDateEndQuery').val()});" id="addLimitDateStartQuery" name="addLimitDateStartQuery"/>
						-
						<input type="text"  style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#addLimitDateStartQuery').val()});" id="addLimitDateEndQuery" name="addLimitDateEndQuery"/>
					</td>
					<th></th>
		            <td>
		            	<@sec.accessControl permission="finance.tongCardCredits.query">
		                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="limitBatchQuery(1);">查询</a>
		                </@sec.accessControl>
		                &nbsp;&nbsp;
		                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('queryCondition')">重置</a>
		            </td>
		        </tr>
		    </table>
	    </form>
	</div>
	<div region="center" border="false"><table id="icdcLimitBatchInfoTbl" fit="true" ></table></div>
    <div id="icdcLimitBatchInfoTblPagination"></div>
	<div id="icdcLimitBatchInfoTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="finance.tongCardCredits.export">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnSelExcCol" >导出Excel</a>
		</@sec.accessControl>
	</div>
</body>
</html>
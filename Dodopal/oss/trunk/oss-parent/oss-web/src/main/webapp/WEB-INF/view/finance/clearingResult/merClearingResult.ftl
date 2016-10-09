<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>商户清算</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/finance/clearingResult/merClearingResult.js"></script>
    <script type="text/javascript" src="../../../js/common/exportExcelUtil.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		<form id="merlistForm" action="exportMerClearingResult" method="post">  
	    <table id="queryCondition" class="viewtable">
	        <tr>
				<th>清算日期:</th>
				<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#clearingDayEnd').val()});" id="clearingDayStart" name="clearingDayStart" style="width:80px;" />
					   -
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#clearingDayStart').val()});" id="clearingDayEnd" name="clearingDayEnd"  style="width:80px;" />
			 	</td>
	            <th></th>
	            <td>
	            	<@sec.accessControl permission="clearingResultManage.merchant.query">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMerClearingResult(1);">查询</a>
	                </@sec.accessControl>
	                &nbsp;&nbsp;
	                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('queryCondition')">重置</a>
	            </td>
	        </tr>
	    </table>
	    </form>
	</div>
	<div region="center" border="false"><table id="clearingResultTbl" fit="true" ></table></div>
    <div id="clearingResultTblPagination"></div>
	<div id="clearingResultTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="clearingResultManage.merchant.export">
		    <a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>

</body>
</html>
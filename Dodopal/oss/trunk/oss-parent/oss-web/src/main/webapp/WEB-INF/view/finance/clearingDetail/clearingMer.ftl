<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>商户清分</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="../../../js/common/exportExceClearing.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/finance/clearingDetail/clearingMer.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		<form id="merForm" action="exportMer" method="post">
	    <table id="queryCondition" class="viewtable">
	        <tr>
	        	<th>清分状态:</th>
   	            <td>
	                <select id="clearingFlag" name="clearingFlag" panelHeight="100" class="easyui-combobox" editable="false">
							<option value=''>--请选择--</option>
							<option value='0'>不清分</option>
							<option value='1'>已清分</option>
							<option value='2'>待确认</option>
					</select>
            	</td>
				<th>清分周期:</th>
				<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#clearingDayTo').val()});" id="clearingDayFrom" name="clearingDayFrom" style="width:80px;" />
					   -
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#clearingDayFrom').val()});" id="clearingDayTo" name="clearingDayTo" style="width:80px;" />
			 	</td>
	            <th></th>
	            <td>
	            	<@sec.accessControl permission="clearingDetailManage.merchant.query">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryClearingData(1);">查询</a>
	                </@sec.accessControl>
	                &nbsp;
	                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('queryCondition')">重置</a>
	            </td>
	        </tr>
	    </table>
	    </form>
	</div>
	<div region="center" border="false">
		<table id="clearingTbl" fit="true" ></table>
	</div>
    <div id="clearingTblPagination"></div>
    <div id="clearingTblToolbar" class="tableToolbar">
    	<@sec.accessControl permission="clearingDetailManage.merchant.export">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
		
	</div>
</body>
</html>
<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../js/finance/profitManage/profitCollect.js"></script>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<title>分润管理</title>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="findTB"  class="viewtable">
			<tr>
				<th>客户名称:</th>
				<td>
					<input type="text" id="merCode" name ="merCode" />
				</td>
				<th>汇总日期:</th>
				<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#endDate').val()});" id="startDate" name="startDate" style="width:80px;" />
					   ~
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#startDate').val()});" id="endDate" name="endDate"  style="width:80px;" />
			 	</td>
			 	<th></th>
				<td>
				<@sec.accessControl permission="profitManage.profitInfo.query">
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findData(1);">查询</a>
				</@sec.accessControl>
				&nbsp;
					<a href="#" class="easyui-linkbutton"  iconCls="icon-eraser" onclick="clearAllText('findTB');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	
	
	<div region="center" border="false">
		<table id="dataTbl" fit="true"></table>
	</div>
	<div id="dataTblToolbar">	
	<@sec.accessControl permission="profitManage.profitInfo.view">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editor" onclick="showDataDialog()">查看</a>
	</@sec.accessControl>
	<@sec.accessControl permission="profitManage.profitInfo.export">
	<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
	</@sec.accessControl>	
	</div>
	<div id="dataTblPagination"></div>
	
	<div id="profitDetailsSearchDialog" title="分润明细" draggable="true" resizable="false">
		 <div class="easyui-layout" fit="true">  
	        <!--分润明细显示 开始 -->
			<div region="center" border="false" style="overflow: hidden;">
				<table id="profitDetailsTbl"></table>
			</div>
			<div id="profitDetailsTblPagination"></div>
			<!-- 分润明细显示 结束 -->
    	 </div>  
	</div>
</html>
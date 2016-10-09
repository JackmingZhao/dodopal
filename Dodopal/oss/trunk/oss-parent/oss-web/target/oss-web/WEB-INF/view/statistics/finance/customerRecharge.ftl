<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户充值交易统计</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/statistics/finance/customerRecharge.js"></script>
    <script type="text/javascript">
    var st = '${startDate}';
    var se = '${endDate}';
    function clearIcdcAllText(){
		$("#merName").val('');
		$("#cityName").val('');
		$("#startDate").val(st);
		$("#endDate").val(se);
	}
    </script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		<form id="listForm" action="exportIcdcOrder" method="post">  
    		<table id="queryCondition" class="viewtable">
        <tr>
			<th>客户名称:</th>
           	 <td><input type="text" id="merName" name="merName"></td>
            <th>城市:</th>
            <td>
            
            <select id="activeCityQuery" name="activeCityQuery" class="easyui-combobox" editable="false" panelHeight="120" style="width: 133px;">
			</select>
            
            </td>
            <th>交易时间:</th>
      		 <td>
				<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#endDate').val()});" id="startDate" name="startDate" value="${startDate}"/>
				-
				<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#startDate').val()});" id="endDate" name="endDate" value="${endDate}"/>
			</td>
            <th></th>
	        <td>
				<@sec.accessControl permission="finance.customerRecharge.query">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findCardRecharge(1);">查询</a>&nbsp;
				</@sec.accessControl>
				<a href="#" id="cancelBtn" class="easyui-linkbutton" iconCls="icon-eraser" >重置</a>
	        </td>
        </tr>
    </table>
    </form>
	</div>
	<div region="center" border="false"><table id="cardRechargeTbl" fit="true" ></table></div>
    <div id="cardRechargeTblPagination"></div>									
	<div id="cardRechargeTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="finance.customerRecharge.export">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
</body>
</html>
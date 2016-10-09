<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>商户消费</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="../../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/statistics/finance/merchantConsumInfo.js"></script>
    <script type="text/javascript">
    var st = '${startDate}';
    var se = '${endDate}';
    function clearIcdcAllText(){
		clearAllText('queryCondition');
		$("#startDate").val(st);
		$("#endDate").val(se);
	}
    </script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		<form id="listForm" action="exportMerchantConsumInfo" method="post">  
		    <table id="queryCondition" class="viewtable">
		        <tr>
		       	    <th>客户名称:</th>
		            <td><input type="text" id="merNameQuery" name = "merNameQuery" style="width:105px"/></td>
		            <th>通卡公司名称:</th>
		            <td>
		            <input id="yktQuery" name="yktQuery" class="easyui-combobox" editable="false" panelHeight="120" style="width: 133px;"/>
		            </td>
		           <th>订单类型:</th>
	                <td>
	                	<select id="bussinessType" name="bussinessType" class="easyui-combobox" editable="false" panelHeight="50" style="width: 95px;">
							<option value='' selected>-请选择-</option>
							<option value="0" >一卡通消费</option>
						</select>
	                </td>
            		<th>交易日期:</th>
		            <td>
						<input value=${startDate} type="text" style="width:90px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#endDate').val()});" id="startDate" name="startDate"/>
						-
						<input value=${endDate} type="text"  style="width:90px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#startDate').val()});" id="endDate" name="endDate" />
					</td>
					<th></th>
		            <td>
		            	<@sec.accessControl permission="finance.tongCardCredits.query">
		                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMerchantConsumInfo(1);">查询</a>
		                </@sec.accessControl>
		                &nbsp;&nbsp;
		                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="resetQuery()">重置</a>
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
<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/basic/logMgmt/crdSysLog.js"></script>
</head>
<title>卡服务日志管理</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:75px;overflow: hidden;">
		<table id="crdSysLogTable" class="viewtable">
			<tr>
				<th>卡服务订单号:</th>
					<td><input type="text" id="crdOrderNumQuery"  style="width: 180px;"></td>
				<th>产品库订单号:</th>
					<td><input type="text" id="proOrderNumQuery"></td>
				<th>业务类型:</th>
					<td>
						<select id="txnTypeQuery"  class="easyui-combobox" editable="false" panelHeight="70" style="width: 133px;">
							<option value='' selected>-请选择-</option>
							<option value="1">一卡通充值</option>
							<option value="2">一卡通消费</option>
						</select>
					</td>
				<td style="width:20px;"></td>
				<td>
					<@sec.accessControl permission="logMgmt.cardLog.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findCrdSysLog(1);">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('crdSysLogTable');">重置</a>
				</td>
			</tr>
			<tr>
				<th>创建日期:</th>
					<td>
						<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#createDateEnd').val()});" id="createDateStart" name="createDateStart" style="width:80px;" />
					   ~&nbsp;
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#createDateStart').val()});" id="createDateEnd" name="createDateEnd"  style="width:80px;" />
				</td>
			</tr>
		</table>
	</div>
	
	<div region="center" border="false">
		<table id="crdSysLogTbl" fit="true"></table>
	</div>
	<div id="crdSysLogTblPagination"></div>
</body>
</html>
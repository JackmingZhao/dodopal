<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>资金账户风控默认值管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <script type="text/javascript" src="../../js/account/riskController/acctDefault.js"></script>
    
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="center" border="false">
		<table id="acctDefaultTbl" fit="true" ></table>
	</div>
	<div id="acctDefaultToolbar" class="tableToolbar">
		<@sec.accessControl permission="riskController.acctDefault.modify">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAcctDefault();">编辑</a>
		</@sec.accessControl>
	</div>
	<div id="acctDefaultTblPagination"></div>
	<div id="acctDefaultDialog" title="" draggable="false" maximized="true" toolbar="#acctDefaultDialogToolbar">
		<form id="acctDefaultDialogForm">
			<input type="hidden" name="acctDefaultId" id="acctDefaultId"></input>
			<fieldset>
        	<legend>资金账户风控默认值管理</legend>
			<table  class="edittable">
				<tr>
					<th>类型:</th>
					<td><span name="customerTypeName" id="customerTypeName" /></td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>日消费交易单笔限额:</th>
					<td><input name="dayConsumeSingleLimit" id="dayConsumeSingleLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;<font color="red">*</font> </td>
					<th>日消费交易累计限额:</th>
					<td><input name="dayConsumeSumLimit" id="dayConsumeSumLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;<font color="red">*</font> </td>
				</tr>
				<tr>
					<th>日充值交易单笔限额:</th>
					<td><input name="dayRechargeSingleLimit" id="dayRechargeSingleLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;<font color="red">*</font> </td>
					<th>日充值交易累计限额:</th>
					<td><input name="dayRechargeSumLimit" id="dayRechargeSumLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;<font color="red">*</font> </td>
				</tr>
				<tr>
					<th>日转账交易最大次数:</th>
					<td><input name="dayTransferMax" id="dayTransferMax" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" maxlength="5"  data-options="required:true,validType:'numberMaxLength[1,5]'" />&nbsp;<font color="red">*</font> </td>
					<th>日转账交易单笔限额:</th>
					<td><input name="dayTransferSingleLimit" id="dayTransferSingleLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;<font color="red">*</font> </td>
				</tr>
				<tr>
					<th>日转账交易累计限额:</th>
					<td><input name="dayTransferSumLimit" id="dayTransferSumLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;<font color="red">*</font> </td>
					<th>默认授信额度阈值</th>
					<td><input name="creditAmt" id="creditAmt" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;<font color="red">*</font> </td>
				</tr>
			</table>
			</fieldset>
			<div id="acctDefaultDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="updateAcctDefault" onclick="updateAcctDefault()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('acctDefaultDialog')">取消</a>
			</div>
		</form>
	</div>
</body>
</html>
<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>资金账户风控管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <script type="text/javascript" src="../../js/account/riskController/account.js"></script>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
	    <table id="queryCondition" class="viewtable">
	        <tr>
	        <th>客户名称:</th>
				<td><input type="text" id="customerNameQuery" name="customerNameQuery" maxlength="128"></td>
				<th>客户号:</th>
				<td><input type="text" id="customerNoQuery" name="customerNoQuery" maxlength="40"></td>
				
				<th>客户类型:</th>
				<td>
                	<select id="customerTypeQuery" name="customerTypeQuery" panelHeight="75" class="easyui-combobox" editable="false">
                		<option value=''>--请选择--</option>
						<option value='0'>个人</option>
						<option value='1'>企业</option>
					</select>
            	</td>
				<td>
					<@sec.accessControl permission="riskController.account.query">
	                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAccounts(1);">查询</a>
					</@sec.accessControl>
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAccountQuery();">重置</a>
				</td>
			</tr>
	    </table>
	</div>
	<div region="center" border="false">
		<table id="accountTbl" fit="true" ></table>
	</div>
	<div id="accountToolbar" class="tableToolbar">
		<@sec.accessControl permission="riskController.account.modify">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAccount();">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="riskController.account.export">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="accountTblPagination"></div>
	<div id="accountDialog" title="" draggable="false" maximized="true" toolbar="#accountDialogToolbar">
		<form id="accountDialogForm">
			<input type="hidden" name="accountControllerId" id="accountControllerId"></input>
			<fieldset>
        	<legend>资金账户风控管理</legend>
			<table  class="edittable">
				<tr>
					<th>客户号:</th>
					<td>
						<input name="customerNo" id="customerNo" type="text" disabled="true" style="width:188px;"/>
					</td>
					<th>客户名称:</th>
					<td>
						<input name="customerName" id="customerName" type="text" disabled="true" style="width:188px;"/>
					</td>
				</tr>
				<tr>
					<th>客户类型:</th>
					<td><input name="customerType" id="customerType" type="text" disabled="true" style="width:188px;"/></td>
					<th>账户类别:</th>
					<td><input name="fundType" id="fundType" type="text" disabled="true" style="width:188px;"/></td>
				</tr>
				<tr>
					<th>资金授信账户号:</th>
					<td>
						<input name="fundAccountCode" id="fundAccountCode" type="text" disabled="true" style="width:188px;"/>
					</td>
					<th>日消费交易单笔限额:</th>
					<td>
						<input name="dayConsumeSingleLimit" id="dayConsumeSingleLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'" />&nbsp;元 
					</td>
				</tr>
				<tr>
					<th>日消费交易累计限额:</th>
					<td><input name="dayConsumeSumLimit" id="dayConsumeSumLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;元 </td>
					<th>日充值交易单笔限额:</th>
					<td><input name="dayRechargeSingleLimit" id="dayRechargeSingleLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;元 </td>
				</tr>
				<tr>
					<th>日充值交易累计限额:</th>
					<td><input name="dayRechargeSumLimit" id="dayRechargeSumLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;元 </td>
					<th>日转账交易最大次数:</th>
					<td><input name="dayTransferMax" id="dayTransferMax" maxlength="5" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'number'"/> </td>
				</tr>
				<tr>
					<th>日转账交易单笔限额:</th>
					<td><input name="dayTransferSingleLimit" id="dayTransferSingleLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;元 </td>
					<th>日转账交易累计限额:</th>
					<td><input name="dayTransferSumLimit" id="dayTransferSumLimit" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;元 </td>
				</tr>
				<tr id="creditAmtSpan">
				    <th>授信额度</th>
				    <td><input name="creditAmt" id="creditAmt" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;元 </td>
				</tr>
			</table>
			</fieldset>
			<div id="accountDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="updateAccount" onclick="updateAccount()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('accountDialog')">取消</a>
			</div>
		</form>
	</div>

</body>
</html>
<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <meta http-equiv="Pragma" contect="no-cache">
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0"> 
	<#include "../../include.ftl"/>
    <script type="text/javascript" src="../../js/account/accountInfo/accountMerInfo.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <title>企业账户信息查询</title>
</head>
<body class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:60px; overflow: hidden;">
    <table id="accountInfoQueryCondition" class="viewtable">
        <tr>
        	 <th>商户名称:</th>
            <td><input type="text" id="custName" name="custName" style="width:118px;" maxlength="12"></td>
            <th>商户号:</th>
            <td><input type="text" id="custNum" name="custNum" style="width:118px;" maxlength="16"></td>
            <th>账户类型:</th>
            <td>
                <select id="fundType" name="fundType" panelHeight="75" class="easyui-combobox" editable="false">
						<option value=''>--请选择--</option>
						<option value='0'>资金</option>
						<option value='1'>授信</option>
				</select>
            </td>
            <th>状态:</th>
            <td>
                <select id="states" name="states" panelHeight="75" class="easyui-combobox" editable="false">
                		<option value=''>--请选择--</option>
						<option value='0'>启用</option>
						<option value='1'>停用</option>
				</select>
            </td>
            <th></th>
            <td>
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAccountInfo(1);">查询</a>
                &nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('accountInfoQueryCondition');">重置</a>
            </td>
        </tr>
    </table>
</div>
<div region="center" border="false">
	<input type="hidden" id="merType" name="merType" value="1"/>
    <table id="accountInfoTbl" fit="true"></table>
</div>
<div id="accountInfoTblToolbar" class="tableToolbar">
	<@sec.accessControl permission="accountInfo.merInfo.view">
	<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="queryVerified" onclick="findAccountInfoByCode()">查看</a>
	</@sec.accessControl>
	<@sec.accessControl permission="accountInfo.merInfo.enable">
	<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="enable" onclick="operateAccountById(0)">启用</a>
	</@sec.accessControl>
	<@sec.accessControl permission="accountInfo.merInfo.disable">
	<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="disable" onclick="operateAccountById(1)">停用</a>
	</@sec.accessControl>
	<@sec.accessControl permission="accountInfo.merInfo.viewChange">	
	<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="viewChange" onclick="viewChangeById()">查看资金变动记录</a>
	</@sec.accessControl>
	<@sec.accessControl permission="accountInfo.merInfo.export">
	<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
	</@sec.accessControl>
</div>
<!--账户详情start-->
<div id="viewAccountInfoDialogToolbar" style="text-align:right;">
    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeViewAccountInfo()">返回</a>
</div>
<div id="viewAccountInfoDialog" style="align:center;" class="easyui-dialog" closable="false" title="" closed="true" draggable="false" maximized="true" toolbar="#viewAccountInfoDialogToolbar">
    <fieldset>
        <legend>主账户信息</legend>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">账户编号:</th>
                <td id="accountCodeView"></td>
                <th style="width:77px;">账户类型:</th>
                <td id="fundTypeView"></td>
            </tr>
            <tr>
                <th>客户类型:</th>
                <td id="customerTypeView"></td>
                <th>客户号:</th>
                <td id="customerNoView"></td>
            </tr>
            <tr>
                <th>创建人:</th>
                <td id="accCreateUserView"></td>
                <th>创建时间:</th>
                <td id="accCreateDateView"></td>
            </tr>
            <tr>
                <th>编辑人:</th>
                <td id="accUpdateUserView"></td>
                <th>编辑时间:</th>
                <td id="accUpdateDateView"></td>
            </tr>
        </table>
	</fieldset>
        
	<fieldset>
        <legend>资金账户信息</legend>
        <table class="viewOnly" id="displayFundTbl">
        </table>
	</fieldset>
	
	<fieldset>
        <legend>资金账户风控信息</legend>
        <table class="viewOnly" id="displayCtrlTbl">
        </table>
	</fieldset>
</div>
<!--账户详情end-->
<div id="accountInfoTblPagination"></div>
<!-- 变动记录start -->
<div id="accountChangeDialog" title="账户资金变动记录" draggable="false" resizable="true"  toolbar="accountChangeDialogToolbar">
	<div class="easyui-layout" fit="true">  
        <!--变动记录查询条件start-->
		<div region="north" border="false" style="height:60px; overflow: hidden;">
			<table id="merchantQueryCondition" class="viewtable" style="padding:8px;">
				<tr>
		            <input type="hidden" id="changefundType" name="changefundType"/>
	            	<th>变动类型:</th>
					<td>
		                <select id="changeType" name="changeType" panelHeight="75" class="easyui-combobox" editable="false" style="width: 130px;">
						</select>
		            </td>
					<th>起止时间:</th>
					<td>
						<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#createDateEnd').val()});" id="createDateStart" name="createDateStart" style="width:80px;" />
						   -
		               	<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#createDateStart').val()});" id="createDateEnd" name="createDateEnd"  style="width:80px;" />
					</td>
					<th>交易金额范围(元):</th>
					<td>
						<input type="text" id="amtFrom" name="amtFrom" style="text-align:right;width:80px;" class="easyui-validatebox" data-options="validType:'ddpAmount'"/>
						   -
		               	<input type="text" id="amtTo" name="amtTo" style="text-align:right;width:80px;" class="easyui-validatebox" data-options="validType:'ddpAmount'" />
					</td>
		            <td>
		                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAccountChange(1);">查询</a>
		                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('merchantQueryCondition');">重置</a>
		            </td>
				</tr>
				<tr>
				</tr>
			</table>
		</div>
		<!-- 变动记录查询条件end -->
        <!--变动记录列表显示start -->
		<div region="center" border="false" style="overflow: hidden;">
		    <input type="hidden" id="acid" name="acid"/>
			<table id="accountChangeListTbl"></table>
		</div>
		<div id="accountChangeListTblPagination"></div>
		<!--变动记录列表显示end -->
	</div>
</div>
<!-- 变动记录end -->
</body>
</html>
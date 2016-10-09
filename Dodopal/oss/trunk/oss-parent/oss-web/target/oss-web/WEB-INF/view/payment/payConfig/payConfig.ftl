<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<meta http-equiv="Pragma" contect="no-cache">
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0"> 
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../js/payment/payConfig/payConfig.js"></script>
	<title>支付方式配置</title>
</head>

<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:55px;overflow: hidden;">
		<table id="payQueryCondition" class="viewtable">
			<tr>
				<th>支付类型:</th>
				<td>
				<select id="payTypeQuery" name="payTypeQuery" panelHeight="120" class="easyui-combobox" editable="false" style="width: 128px;">
						<option value='' selected>--请选择--</option>
						<option value='0'>账户支付</option>
						<option value='1'>一卡通支付</option>
						<option value='2'>在线支付</option>
						<option value='3'>银行支付</option>
				</select>
				</td>
				<th>支付方式名称:</th>
				<td><input type="text" id="payNameQuery" maxlength="40"></td>
				<th>启用标识:</th>
				<td>
				<select id="activateQuery" name="activateQuery" panelHeight="75" class="easyui-combobox" editable="false" style="width: 128px;">
						<option value='0'>启用</option>
						<option value='1'>停用</option>
						<option value=''>全部</option>
				</select>
				</td>
				<th>后手续费生效时间:</th>
				<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#afterProceRateDateEnd').val()});" id="afterProceRateDateStart" name="createDateStart" style="width:80px;" />
					   ~
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#afterProceRateDateStart').val()});" id="afterProceRateDateEnd" name="createDateEnd"  style="width:80px;" />
				</td>
				<th></th>
				<td>
					<@sec.accessControl permission="pay.base.way.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPayConfig(1);">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('payQueryCondition');">重置</a>
				</td>
		</table>
	</div>
	<div region="center" border="false">
		<table id="payConfigTbl" fit="true"></table>
	</div>
	<div id="payConfigToolbar" class="tableToolbar">
		<@sec.accessControl permission="pay.base.way.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="toEditPayConfig()">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.base.way.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" onclick="toStart()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.base.way.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true"  onclick="toStop()">停用</a>
		</@sec.accessControl>
	
		<@sec.accessControl permission="pay.base.way.change">
			<a href="#" class="easyui-linkbutton" iconCls="icon-unbind" plain="true"  onclick="changeGateway()">银行网关切换</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.base.way.view">
			<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true"  onclick="viewPayConfig()">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.base.way.export">
			<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="payConfigPagination"></div>
	
	
	<div id="viewPayConfigDialogToolbar" style="text-align:right;">
		<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeViewPayConfig()">返回</a>
	</div>

	<div id="viewPayConfigDialog" style="align:center;" class="easyui-dialog" closable="false" title="" closed="true" draggable="false" maximized="true" toolbar="#viewPayConfigDialogToolbar">
		<input type="hidden" name="userId" id="userId"></input>
			<fieldset>
				<legend>支付方式配置详情</legend>
				<table class="viewOnly">
					<tr>
						<th style="width:77px;">支付方式:</th> 
						<td id="payWayNameView"></td>
						<th style="width:77px;">支付类型:</th>
						<td id="payTypeNameView"></td>
					</tr>
					<tr>
					<th>手续费率:</th>
						<td id="proceRateView"></td>
					<th>启用标识:</th>
						<td id="activateView"></td>
					
					</tr>
					<tr>
						
						<th>后手续费率:</th>
						<td id="afterProceRateView"></td>
						<th>后手续费率生效时间:</th>
						<td id="afterProceRateDateView"></td>
					</tr>
					<tr>
						<th>图标名称:</th>
						<td id="imageNameView"></td>
						<th>银行网关类型:</th>
						<td id="bankGatewayTypeView"></td>
					</tr>
					<tr>
						<th>提供给外接商户的网关号:</th>
						<td id="gatewayNumberView"></td>
					</tr>
					<tr>
						<th>创建人:</th>
						<td id="createUserView"></td>
						<th>创建时间:</th>
						<td id="createDateView"></td>
					</tr>
					<tr>
						<th>编辑人:</th>
						<td id="updateUserView"></td>
						<th>编辑时间:</th>
						<td id="updateDateView"></td>
					</tr>
				</table>
				<br/>
			</fieldset>
			<br/>
	</div>
	
	<div id="editPayConfigDialogToolbar" style="text-align:right;">
	<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="editPayConfig()">保存</a>
		<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelEditPayConfig()">取消</a>
	</div>

	<div id="editPayConfigDialog" style="align:center;" class="easyui-dialog" closable="false" title="" closed="true" draggable="false" maximized="true" toolbar="#editPayConfigDialogToolbar">
		<input type="hidden" name="payId" id="payId"></input>
			<fieldset>
				<legend>支付方式配置修改</legend>
				<table class="edittable">
					<tr>
						<th style="width:77px;">支付方式名称:</th> 
						<td ><input id="payWayNameEdit" maxlength="50" type="text" class="easyui-validatebox" data-options="required:true,validType:'cn[0,50]'"/><font color="red">*</font></td>
						<th style="width:77px;">支付类型:</th>
						<td id="payTypeNameEdit"></td>
					</tr>
					<tr>
						<th>手续费率:</th>
						<td id="proceRateEdit"></td>
						<th>启用标识:</th>
						<td>
							<input name="activateEdit" type="radio" value="0" disabled/>启用
							<input name="activateEdit" type="radio" value="1" disabled/>停用
						</td>
					</tr>
					<tr>
						<th>后手续费率:</th>
						<td >
						<input id="afterProceRateEdit" class="easyui-validatebox" maxlength="7" data-options="required:true,validType:'testNumber'" style="text-align:right;"/>
						‰<font color="red">*</font>
						</td>
						<th>后手续费率生效时间:</th>
						<td >
						<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}'});" id="afterProceRateDateEdit" class="easyui-validatebox" maxlength="7" data-options="required:true" name="afterProceRateDateEdit"  />
						<font color="red">*</font>
						</td>
					</tr>
					<tr>
					<th>图标名称:</th>
						<td id="imageNameEdit"></td>
						<th>银行网关类型:</th>
						<td id="bankGatewayTypeEdit"></td>
					</tr>
					<tr>
					<th>提供给外接商户的网关号:</th>
						<td id="gatewayNumberEdit"></td>
						
					</tr>
				</table>
				<br/>
			</fieldset>
			<br/>
	</div>
	
	<div id="payConfigBankDialog" title="银行网关切换" draggable="false">
		<form id="departmentDialogForm">
				<input name="id" id="id" type="hidden"/>
				<table class="edittable">
					<tr>
						<th valign="top">切换银行网关:</th>
						<td >
						 <input type="radio" name="GatewayType" checked id="GatewayType"/>
						 <span id="GatewayTypeName"></span>
						</td>
					</tr>
				</table>
		</form>
	</div>
</body>
</html>
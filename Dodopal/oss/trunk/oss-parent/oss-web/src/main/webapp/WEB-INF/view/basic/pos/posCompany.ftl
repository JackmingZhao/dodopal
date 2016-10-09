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
	<script type="text/javascript" src="../../js/basic/pos/posCompany.js"></script>
	<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table class="viewtable" id="posCompanyQueryCondition">
			<tr>
				<th>厂商编号:</th>
				<td><input type="text" id="companyCodeQuery"></td>
				<th>厂商名称:</th>
				<td><input type="text" id="companyNameQuery"></td>
				<th>厂商负责人:</th>
				<td><input type="text" id="companyChargerQuery"></td>
				<#--
				<th>启用标识:</th>
				<td>
				<select id="activateFind"  class="easyui-combobox" editable="false" panelHeight="70" style="width: 100px;">
					<option value=''>全部</option>
					<option value="0" selected >启用</option>
					<option value="1">停用</option>
				</select>
				</td>-->
				<td style="width:20px;"></td>
				<td>
					<@sec.accessControl permission="pos.company.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPosCompany();">查询</a>
					</@sec.accessControl>
				</td>
					<td style="width:10px;"></td>
				<td>
					<a href="#" class="easyui-linkbutton"  iconCls="icon-eraser" onclick="clearAllText('posCompanyQueryCondition');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="companyTbl" fit="true"></table>
	</div>
	<div id="companyTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="pos.company.add">
			<a href="#" class="easyui-linkbutton"  style="height:200px;" iconCls="icon-add" plain="true" id="addPosCompany" onclick="addPosCompany()">添加</a>
		</@sec.accessControl>	
		<@sec.accessControl permission="pos.company.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editPosCompany" onclick="editPosCompany()">编辑</a> 
		</@sec.accessControl>
		<@sec.accessControl permission="pos.company.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deletePosCompany" onclick="deletePosCompany()">删除</a> 
		</@sec.accessControl>	
		<@sec.accessControl permission="pos.company.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activatePosCompany" onclick="activatePosCompany()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.company.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivatePosCompany" onclick="inactivatePosCompany()">停用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.company.view">
			<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="viewPosCompany" onclick="viewPosCompany()">查看</a>
		</@sec.accessControl>
	</div>
	<div id="companyTblPagination"></div>
	<div id="companyDialog" title="POS厂商" draggable="false" toolbar="#companyDialogToolbar">
		<form id="companyDialogForm">
			<input type="hidden" name="companyId" id="companyId"></input>
				<table  class="edittable">
					<tr>
						<th>厂商编号:</th>
						<td><input name="code" id="code" type="text" class="easyui-validatebox" data-options="required:true,validType:'enNoUs[1,32]'" maxLength="32" /><font color="red">*</font></td>
						<th>厂商名称:</th>
						<td><input name="name" id="name" type="text" class="easyui-validatebox" data-options="required:true,validType:'cnEnNo[0,20]'"  maxLength="20"/><font color="red">*</font></td>
					</tr>
					<tr>
						<th>厂商负责人:</th>
						<td><input name="charger" id="charger" type="text" class="easyui-validatebox" data-options="required:true,validType:'enCn[2,20]'"  maxLength="20"/><font color="red">*</font></td>
						<th>联系电话:</th>
						<td><input name="phone" id="phone" type="text" class="easyui-validatebox" data-options="required:true,validType:'mobileAndTel'"  maxLength="20"/><font color="red">*</font></td>
					</tr>
					<tr>
					<#--
						<th>启用标识:</th>
						<td>
							<label><input  name="activate" type="radio" value="0" checked="checked"/>启用</label> 
							<label><input  name="activate" type="radio" value="1" />停用 </label><font color="red">*</font> 
						</td>-->
						<th>邮编:</th>
						<td><input name="zipCode" id="zipCode" type="text"  class="easyui-validatebox" data-options="validType:'zip'"   maxLength="6"/></td>
						<th>传真:</th>
						<td><input name="fax" id="fax" type="text" class="easyui-validatebox" data-options="validType:'fax'"  maxLength="20"/></td>
					</tr>
					<tr>
						<th>电子邮箱:</th>
						<td><input name="mail" id="mail" type="text" class="easyui-validatebox" data-options="validType:'email'"  maxLength="60"/></td>
						<th>厂商地址:</th>
						<td><input name="address" id="address" type="text" maxLength="200"/></td>
					</tr>
					<tr>
						
					</tr>
				</table>
			<div id="companyDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePosCompany()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('companyDialog')">取消</a>
			</div>
		</form>
	</div>
	<#include "posCompanyView.ftl"/>

</body>
</html>
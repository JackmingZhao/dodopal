<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/systems/system/departmentMgmt.js"></script>
</head>
<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="departmentQueryCondition" class="viewtable">
			<tr>
				<th>部门名称:</th>
				<td><input type="text" id="depNameQuery"></td>
				<#--
				<th>启用标识:</th>
				<td>
				<select id="activateQuery"  class="easyui-combobox" editable="false" style="width: 120px;">
					<option value='0'  selected = "selected">启用</option>
					<option value='1'>停用</option>
					<option value=''>全部</option>
				</select>
				</td>
				-->
				<td style="width:20px;"></td>
				
				<@sec.accessControl permission="system.department.query">
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findDeprment(1);">查询</a>
				</td>
			</@sec.accessControl>
					<td style="width:10px;"></td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('departmentQueryCondition');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="departmentTbl" fit="true"></table>
	</div>
	<div id="departmentTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="system.department.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addDepartment" onclick="addDepartment()">添加</a>
		</@sec.accessControl>	
		<@sec.accessControl permission="system.department.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editDepartment" onclick="editDepartment()">编辑</a> 
		</@sec.accessControl>	
		<@sec.accessControl permission="system.department.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteDepartment" onclick="deleteDepartment()">删除</a>
		</@sec.accessControl>
		<#--
		<@sec.accessControl permission="system.department.start">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="startDepartment" onclick="startDepartment()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="system.department.disable">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate"  plain="true" id="disableDepartment" onclick="disableDepartment()">停用</a>
		</@sec.accessControl>
		-->
	</div>
	<div id="departmentTblPagination"></div>
	<div id="departmentDialog" title="部门信息" draggable="false" toolbar="#departmentDialogToolbar">
		<form id="departmentDialogForm">
				<input name="id" id="id" type="hidden"/>
				<table class="edittable">
					<tr>
						<th>部门编码:</th>
						<td><input name="depCode" id="depCode" type="text" class="easyui-validatebox" maxlength="10" data-options="required:true,validType:'enNoUs[0,10]'"/><font color="red">*</font></td>
						<th>部门名称:</th>
						<td><input name="depName" id="depName" type="text" class="easyui-validatebox" maxlength="20" data-options="required:true,validType:'cnEnNo[2,20]'"/><font color="red">*</font></td>
					</tr>
					<tr>
						<th>部门负责人:</th>
						<td>
						<select id="chargeId" panelHeight="110"></select>
						</td>
						<#--
						<th>启用标识:</th>
						<td>
							<label><input  name="activate" type="radio" value="0" checked="checked"/>启用</label> 
							<label><input  name="activate" type="radio" value="1" />停用 </label><font color="red">*</font> 
						</td>
						-->
					</tr>
					<tr>
						<th valign="top">备注:</th>
						<td colspan="4" style="word-break:break-all"><textarea rows="6" maxlength="200" name="remark" class="easyui-validatebox"  data-options="validType:'lengthRange[0,200]'" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);" id="remark" style="width: 415px;" ></textarea></td>
					</tr>
				</table>
			<div id="departmentDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveDepartment()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('departmentDialog')">取消</a>
			</div>
		</form>
	</div>
</body>
</html>
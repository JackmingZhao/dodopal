<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/systems/system/roleMgmt.js"></script>
	<script type="text/javascript" src="../../js/utils/jsFilterUtil.js"></script>
	<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="roleQueryCondition" class="viewtable">
			<tr>
				<th>角色名:</th>
				<td><input type="text" id="roleNameQuery"></td>
				<td style="width:20px;"></td>
				<@sec.accessControl permission="system.role.query">
			<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findRole(1);">查询</a>
				</td>
			</@sec.accessControl>
				
					<td style="width:10px;"></td>
				<td>
					<a href="#" class="easyui-linkbutton"  iconCls="icon-eraser" onclick="clearAllText('roleQueryCondition');">重置</a>
				</td>
			</tr>
			
		</table>
	</div>
	<div region="center" border="false">
		<table id="roleTbl" fit="true"></table>
	</div>
	<div id="roleTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="system.role.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addRole" onclick="addRole()">添加</a>
		</@sec.accessControl>	
		<@sec.accessControl permission="system.role.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editRole" onclick="editRole()">编辑</a> 
		</@sec.accessControl>
		<@sec.accessControl permission="system.role.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteRole" onclick="deleteRole()">删除</a>
		</@sec.accessControl>
	</div>
	<div id="roleTblPagination"></div>
	<div id="roleDialog" title="" draggable="false" maximized="true" toolbar="#roleDialogToolbar">
		<form id="roleDialogForm" name="test2">
			<table class="layout-table">
				<tr>
				<input name="rid" id="rid" type="hidden" />
					<th>角色名:</th>
					<td><input name="name" id="name" type="text" maxlength="20" class="easyui-validatebox"  data-options="required:true,validType:'numberEnCn[2,20]'"/><font size="1" color='red'>*</font></td>
				</tr>
				<tr>
					<th>描述:</th>
					<td>
					<textarea name="description" id="description"  class="easyui-validatebox"  data-options="validType:'lengthRange[0,200]'"  style="width: 500px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);" maxlength="200">
					</textarea>
					</td>
				</tr>
			</table>
			<fieldset>
				<legend>角色权限</legend>
				<div id="roleList">
				</div>
				<ul id='rolePermissionTree' style='margin:5px;overflow:auto;height:300px;'></ul>
			</fieldset>
			<div id="roleDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveRole();">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('roleDialog')">取消</a>
			</div>
		</form>
	</div>
</body>
</html>
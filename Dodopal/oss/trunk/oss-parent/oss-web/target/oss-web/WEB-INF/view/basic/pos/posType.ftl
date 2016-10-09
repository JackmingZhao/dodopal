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
	<script type="text/javascript" src="../../js/basic/pos/posType.js"></script>
	<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="posTypeQueryCondition" class="viewtable">
			<tr>
				<th>型号编码:</th>
				<td><input type="text" id="typeCodeQuery"></td>
				<th>型号名称:</th>
				<td><input type="text" id="typeNameQuery"></td>
				<#--
				<th>启用标识:</th>
				<td>
				<select id="activateFind"  class="easyui-combobox" editable="false" style="width: 100px;" panelHeight="70">
					<option value=''>全部</option>
					<option value="0" selected>启用</option>
					<option value="1">停用</option>
				</select>
				</td>-->
				<td style="width:20px;"></td>
				<td>
					<@sec.accessControl permission="pos.type.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPosType(1);">查询</a>
					</@sec.accessControl>
				</td>
					<td style="width:10px;"></td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('posTypeQueryCondition');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="typeTbl" fit="true"></table>
	</div>
	<div id="typeTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="pos.type.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addPosType" onclick="addPosType()">添加</a>
		</@sec.accessControl>	
		<@sec.accessControl permission="pos.type.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editPosType" onclick="editPosType()">编辑</a> 
		</@sec.accessControl>	
		<@sec.accessControl permission="pos.type.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deletePosType" onclick="deletePosType()">删除</a> 
		</@sec.accessControl>	
		<@sec.accessControl permission="pos.type.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activatePosType" onclick="activatePosType()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.type.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivatePosType" onclick="inactivatePosType()">停用</a>
		</@sec.accessControl>
		<!-- 
		<@sec.accessControl permission="pos.type.view">-->
		<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="viewPosType" onclick="viewPosType()">查看</a>
		</@sec.accessControl>
		-->
	</div>
	<div id="typeTblPagination"></div>
	<div id="typeDialog" title="POS型号" draggable="false" toolbar="#typeDialogToolbar">
		<form id="typeDialogForm">
			<input type="hidden" name="typeId" id="typeId"></input>
				<table class="edittable">
					<tr>
						<th>型号编码:</th>
						<td><input name="code" id="code" type="text" maxlength="32" class="easyui-validatebox" data-options="required:true,validType:'enNoUs[1,32]'" /><font color="red">*</font></td>
						<th>型号名称:</th>
						<td><input name="name" id="name" type="text" maxlength="20"  class="easyui-validatebox" data-options="required:true,validType:'cnEnNo[1,20]'" /><font color="red">*</font></td>
					</tr>
					<#--
					<tr>
						<th>启用标识:</th>
						<td>
							<label><input  name="activate" type="radio" value="0" checked="checked"/>启用</label> 
							<label><input  name="activate" type="radio" value="1" />停用 </label><font color="red">*</font> 
						</td>
					</tr> -->
					<tr>
						<th valign="top">备注:</th>
						<td colspan="4"><textarea rows="6" name="comments" id="comments" style="width: 409px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea></td>
					</tr>
				</table>
			<div id="typeDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePosType()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('typeDialog')">取消</a>
			</div>
		</form>
	</div>
	
	<#include "posTypeView.ftl"/>
</body>
</html>
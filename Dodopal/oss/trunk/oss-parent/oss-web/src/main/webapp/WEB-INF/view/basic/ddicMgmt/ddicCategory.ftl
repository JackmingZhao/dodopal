<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/basic/ddicMgmt/ddicCategory.js"></script>
	<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table class="viewtable" id="ddicCategoryQueryTB">
			<tr>
				<th>分类编码:</th>
				<td><input type="text" id="ddicCategoryCodeQuery"></td>
				<th>分类名称:</th>
				<td><input type="text" id="ddicCategoryNameQuery"></td>
				<td style="width:20px;"></td>
				<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findDdicCategory();">查询</a>
				</td>
				<td style="width:10px;"></td>
				<td><a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('ddicCategoryQueryTB');">重置</a></td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="ddicCategoryTbl" fit="true"></table>
	</div>
	<div id="ddicCategoryTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="ddicMgmt.ddicCategory.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addDdicCategory" onclick="addDdicCategory()">添加</a>
		</@sec.accessControl>	
		<@sec.accessControl permission="ddicMgmt.ddicCategory.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editDdicCategory" onclick="editDdicCategory()">编辑</a> 
		</@sec.accessControl>	
		<@sec.accessControl permission="ddicMgmt.ddicCategory.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteDdicCategory" onclick="deleteDdicCategory()">删除</a>
		</@sec.accessControl>
		<#--<@sec.accessControl permission="ddicMgmt.ddicCategory.activate">-->
			<#--<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activatePosType" onclick="activateDdicCategory()">启用</a>-->
		<#--</@sec.accessControl>-->
		<#--<@sec.accessControl permission="ddicMgmt.ddicCategory.inactivate">-->
			<#--<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivatePosType" onclick="inactivateDdicCategory()">停用</a>-->
		<#--</@sec.accessControl>-->
	</div>
	<div id="ddicCategoryTblPagination"></div>
	<div id="ddicCategoryDialog" title="数据字典分类" draggable="false" toolbar="#ddicCategoryDialogToolbar">
		<form id="ddicCategoryDialogForm">
			<input type="hidden" name="ddicCategoryId" id="ddicCategoryId"></input>
				<table class="edittable">
					<tr>
						<th>分类编码:</th>
						<td><input name="categoryCode" id="categoryCode" type="text" class="easyui-validatebox" data-options="required:true,validType:'enNoUs[1,64]'" maxlength=64 /><font color="red">*</font></td>
						<th>分类名称:</th>
						<td><input name="categoryName" id="categoryName" type="text" class="easyui-validatebox" data-options="required:true,validType:'cnEnNo[0,50]'" maxlength=50 /><font color="red">*</font></td>
					</tr>
					<tr>
						<th>描述:</th>
						<td colspan="3"><textarea rows="6" name="description" id="description" style="width: 409px;" class="easyui-validatebox" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea></td>
					</tr>
				</table>
			<div id="ddicCategoryDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveDdicCategory()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('ddicCategoryDialog')">取消</a>
			</div>
		</form>
	</div>
</body>
</html>
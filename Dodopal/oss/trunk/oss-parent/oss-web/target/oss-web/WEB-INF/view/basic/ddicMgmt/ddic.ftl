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
	<script type="text/javascript" src="../../js/basic/ddicMgmt/ddic.js"></script>
	<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="ddicQueryCondition" class="viewtable">
			<tr>
				<th>字典编码:</th>
				<td><input type="text" id="ddicCodeQuery"></td>
				<th>字典名称:</th>
				<td><input type="text" id="ddicNameQuery"></td>
				<th>分类编码:</th>
				<td><select id="ddicCategoryCodeQuery" class="easyui-combobox" editable="false"></select></td>
				<th>启用标识:</th>
				<td>
				<select id="activateQuery"  class="easyui-combobox" editable="false" panelHeight="70" style="width: 100px;">
					<option value=''>全部</option>
					<option value="0" selected>启用</option>
					<option value="1">停用</option>
				</select>
				</td>
				<td style="width:20px;"></td>
				<td>
				<@sec.accessControl permission="ddicMgmt.ddic.query">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findDdic();">查询</a>
				</@sec.accessControl>
				</td>
				<td style="width:10px;"></td>
				<td><a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('ddicQueryCondition');">重置</a></td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="ddicTbl" fit="true"></table>
	</div>
	<div id="ddicTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="ddicMgmt.ddic.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addDdic" onclick="addDdic()">添加</a>
		</@sec.accessControl>	
		<@sec.accessControl permission="ddicMgmt.ddic.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editDdic" onclick="editDdic()">编辑</a> 
		</@sec.accessControl>	
		<@sec.accessControl permission="ddicMgmt.ddic.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteDdic" onclick="deleteDdic()">删除</a>
		</@sec.accessControl>
		<@sec.accessControl permission="ddicMgmt.ddic.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activateDdic" onclick="activateDdic()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="ddicMgmt.ddic.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivateDdic" onclick="inactivateDdic()">停用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="ddicMgmt.ddic.view">
			<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="viewDdic" onclick="viewDdic()">查看</a>
		</@sec.accessControl>
	</div>
	<div id="ddicTblPagination"></div>
	<div id="ddicDialog" title="数据字典" draggable="false" toolbar="#ddicDialogToolbar">
		<form id="ddicDialogForm">
			<input type="hidden" name="ddicId" id="ddicId"></input>
				<table class="edittable">
					<tr>
						<th>字典编码:</th>
						<td><input name="code" id="code" type="text" class="easyui-validatebox" data-options="required:true,validType:'enNoUs[1,64]'" maxlength=64></input><font color="red">*</font></td>
						<th>字典名称:</th>
						<td><input name="name" id="name" style="width: 133px;" type="text" class="easyui-validatebox"  maxlength=50/><font color="red">*</font></td>
					</tr>
					<tr>
						<th>优先级(排序):</th>
						<td><input name="orderList"  id="orderList" type="text" class="easyui-validatebox" data-options="validType:'number'" maxlength=3/></td>
						<th>分类编码:</th>
						<td><select id="categoryCode" class="easyui-combobox" style="width:110px;"></select><font color="red">*</font></td>
					</tr>
					<tr>
						<th>启用标识:</th>
						<td>
							<label><input  name="activate" type="radio" value="0" checked="checked"/>启用</label> 
							<label><input  name="activate" type="radio" value="1" />停用 </label>
							<font color="red">*</font> 
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th >描述:</th>
						<td colspan="6"><textarea rows="6" name="description" id="description" style="width: 407px;" class="easyui-validatebox" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea></td>
					</tr>
				</table>
			<div id="ddicDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveDdic()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('ddicDialog')">取消</a>
			</div>
		</form>
	</div>

	<div id="viewDdicDialogToolbar" style="text-align:right;">
		<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeViewDdic()">返回</a>
	</div>
	<div id="viewDdicDialog" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewDdicDialogToolbar">
		<fieldset>
			<legend>数据字典</legend>
			<table class="viewOnly">
				<tr>
					<th style="width:77px;">分类编码:</th> 
					<td id="viewCategoryCode"></td>
					<th>字典编码:</th>
					<td id="viewDdicCode"></td>
				</tr>
				<tr>
					<th>字典名称:</th> 
					<td id="viewDdicName"></td>
					<th>上级字典名称:</th>
					<td id="viewDdicParentCode"></td>
				</tr>
				<tr>
					<th>启用标识:</th> 
					<td id="viewActivate"></td>
					<th>优先级:</th>
					<td id="viewOrderList"></td>
				</tr>
				<tr >
					<th valign="top">描述:</th>
					<td colspan="3" id="viewDescription" style="word-break:break-all"></td>
					</tr>
				</tr>
				<tr>
					<th>创建人:</th>
					<td id="viewCreateUser"></td>
					<th>创建时间:</th>
					<td id="viewCreateDate"></td>
				</tr>
				<tr>
					<th>编辑人:</th>
					<td id="viewUpdateUser"></td>
					<th>编辑时间:</th>
					<td id="viewUpdateDate"></td>
				</tr>
			</table>
		</fieldset>
	</div>
</body>
</html>
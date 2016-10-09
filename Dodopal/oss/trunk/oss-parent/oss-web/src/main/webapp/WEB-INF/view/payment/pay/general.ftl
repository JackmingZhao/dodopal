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
	<script type="text/javascript" src="../../js/payment/pay/general.js"></script>
	<title>通用支付方式</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
   <div region="north" border="false" style="height:50px;overflow: hidden;">
		<table id="generalQueryCondition" class="viewtable">
			<tr>
			    <th>支付类型:</th>
				<td>
					<select id="payTypeQuery"  panelHeight="90" class="easyui-combobox" editable="false" style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="0">账户支付</option>
						<option value="1">一卡通支付</option>
						<option value="2">在线支付</option>
						<option value="3">银行支付</option>
					</select>
				</td>
				<th></th>
				<th>支付方式名称:</th>
				<td><input type="text" id="payWayNameQuery" ></td>
				<th>启用标识:</th>
				<td>
					<select id="activateQuery"  class="easyui-combobox" panelHeight="75" editable="false" style="width: 133px;">
						<option value="0" selected>启用</option>
						<option value="1">停用</option>
						<option value=''>全部</option>
					</select>
				</td>
				<th></th>
				<td>
					<@sec.accessControl permission="pay.payMgmt.common.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findGeneral();">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('generalQueryCondition');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="generalTbl" fit="true"></table>
	</div>
	<div id="generalTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="pay.payMgmt.common.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="showAddGeneral" onclick="showAddGeneral()">添加</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.common.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editGeneral" onclick="editGeneral()">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.common.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="removeGeneral" onclick="deletePayGeneral()">删除</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.common.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activateGeneral" onclick="startUser()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.common.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivateGeneral" onclick="stopUser()">停用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.common.export">
			<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	 </div>
	<div id="generalTblPagination"></div>
	
	<div id="viewPayWayGeneralDialogToolbar" style="text-align:right;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveOrUpdateGeneral()">保存</a>
			<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('generalDialog')">取消</a>
	</div>
		  
	<div id="generalDialog"  title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewPayWayGeneralDialogToolbar">
	 <fieldset>
		<legend>通用支付方式信息</legend>
			<input type="hidden" name="id" id="id"></input>
			<table class="edittable">
			<tr>
					<th >支付类型:</th>
				    <td>
						<select id="payType" style="width:210px"  name="payType" class="easyui-combobox" editable="false" panelHeight="130"></select>
						<font color="red">*</font>
					</td>
					<th >支付方式名称:</th>
					  <td>
						<select id="payWayName" name="payWayName" class="easyui-combobox" editable="false" panelHeight="130"></select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th style="width:93px;">启用标识:</th>
                   <td>
                    <label><input type="radio" id="activate" name="activate" value="0"/>启用</label>
                    <label><input type="radio" id="activate" name="activate" value="1"/>停用</label><font color="red">*</font>
					<th style="width:93px;">排序号:</th>
					<td><input name="sort" id="sort" type="text" maxlength="5" style="text-align:right;width:210px;" class="easyui-validatebox" data-options="required:true,validType:'testNumber'"/><font color="red">*</font></td>
                   </td>
				</tr>
				<tr>
					<th valign="top" style="width:93px;">备注:</th>
                    <td colspan="6">
                        <textarea rows="6" name="comments" id="comments" style="width: 554px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
                    </td>
				</tr>
			</table>
			</fieldset>
	</div>
</body>
</html>
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
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="../../js/basic/ddicMgmt/ddicResponseMsg.js"></script>
	<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="ddicResMsgQueryCondition" class="viewtable">
			<tr>
				<th>消息编码:</th>
				<td><input type="text" id="ddicResMsgCodeQuery"></td>
				<th>消息类型:</th>
				<td><input type="text" id="ddicResMsgTypeQuery"></td>
				
				<td>
				<@sec.accessControl permission="ddicMgmt.ddicReturn.query">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findDdicResMsg();">查询</a>
				</@sec.accessControl>
				</td>
				<td style="width:10px;"></td>
				<td><a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('ddicResMsgQueryCondition');">重置</a></td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="ddicResMsgTbl" fit="true"></table>
	</div>
	<div id="ddicResMsgTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="ddicMgmt.ddicReturn.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addDdic" onclick="addDdicResMsg()">添加</a>
		</@sec.accessControl>	
		<@sec.accessControl permission="ddicMgmt.ddicReturn.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editDdic" onclick="editDdicResMsg()">编辑</a> 
		</@sec.accessControl>	
		<#--
		<@sec.accessControl permission="ddicMgmt.ddicReturn.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteDdic" onclick="deleteDdicResMsg()">删除</a>
		</@sec.accessControl>
		<@sec.accessControl permission="ddicMgmt.ddicReturn.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activateDdic" onclick="activateDdicResMsg()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="ddicMgmt.ddicReturn.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivateDdic" onclick="inactivateDdicResMsg()">停用</a>
		</@sec.accessControl>-->
		<@sec.accessControl permission="ddicMgmt.ddicReturn.view">
			<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="viewDdicResMsg" onclick="viewDdicResMsg()">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="ddicMgmt.ddicReturn.export">
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportDdicResMsg" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="ddicResMsgTblPagination"></div>
	
	<div id="ddicResMsgDialog" title="消息返回字典添加" draggable="false" toolbar="#ddicResMsgDialogToolbar">
		<form id="ddicResMsgDialogForm">
			<input type="hidden" name="ddicResMsgId" id="ddicResMsgId"></input>
			<table class="edittable">
				<tr>
					<th>消息编码:</th>
					<td><input name="msgCode" id="msgCode" type="text" class="easyui-validatebox" data-options="required:true,validType:'number'" maxlength=6></input><font color="red">*</font></td>
					<th>消息类型:</th>
					<td><input name="msgType" id="msgType" type="text" class="easyui-validatebox" data-options="required:true,validType:'number'" maxlength=6 style="width: 133px;" /><font color="red">*</font></td>
				</tr>
				<tr>
					<th>描述:</th>
					<td colspan="6"><textarea rows="6" name="message" id="message" style="width: 407px;" class="easyui-validatebox" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea></td>
				</tr>
			</table>
			<div id="ddicResMsgDialogToolbar" style="text-align:right;" class="button">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveDdicResMsg()">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('ddicResMsgDialog')">取消</a>
			</div>
		</form>
	</div>

	<div id="viewDdicResMsgDialogToolbar" style="text-align:right;">
		<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeViewDdicResMsg()">返回</a>
	</div>
	<div id="viewDdicResMsgDialog" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewDdicResMsgDialogToolbar">
		<fieldset>
			<legend>数据字典</legend>
			<table class="viewOnly">
				<tr>
					<th style="width:77px;">消息编码:</th> 
					<td id="viewMsgCode"></td>
					<th>消息类型:</th>
					<td id="viewMsgType"></td>
				</tr>

				<tr >
					<th valign="top">消息内容:</th>
					<td colspan="3" id="viewMessage" style="word-break:break-all"></td>
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
	
	<div id="selExpColDRMDia" title="选择消息返回字典导出列" draggable="false" toolbar="#selExpColDRMDiaToolbar">
		<div id="selExpColDRMDiaToolbar" style="text-align:right;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="choseSelExpColDRMDia()">确定</a>
			<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('selExpColDRMDia')">取消</a>
		</div>
	</div>
	
</body>
</html>

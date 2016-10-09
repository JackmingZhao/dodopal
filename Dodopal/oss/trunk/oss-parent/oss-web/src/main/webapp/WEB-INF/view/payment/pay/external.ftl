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
	<script type="text/javascript" src="../../js/payment/pay/external.js"></script>
	<#include "../../common/merchantSearchModel.ftl"/>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../js/payment/pay/merchantSearchModel1.js"></script>
	<title>外接支付方式</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
   <div region="north" border="false" style="height:50px;overflow: hidden;">
		<table id="externalQueryCondition" class="viewtable">
			<tr>
				<th>商户名称:</th>
				<td><input type="text" id="merNameQuery" maxlength="40"></td>
			
			    <th>支付类型:</th>
				<td>
					<select id="payTypeQuery"  class="easyui-combobox" editable="false" panelHeight="auto" panelMaxHeight="120"  style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="0">账户支付</option>
						<option value="1">一卡通支付</option>
						<option value="2">在线支付</option>
						<option value="3">银行支付</option>
					</select>
				</td>
				<th>支付方式名称:</th>
				<td><input type="text" id="payWayNameQuery" ></td>
				<th>启用标识:</th>
				<td>
					<select id="activateQuery"  class="easyui-combobox" editable="false" panelHeight="auto" panelMaxHeight="75" style="width: 133px;">
						<option value="0" selected>启用</option>
						<option value="1">停用</option>
						<option value='' >全部</option>
					</select>
				</td>
				<th></th>
				<td>
					<@sec.accessControl permission="pay.payMgmt.external.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findExternal();">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('externalQueryCondition');">重置</a>
				</td>
				</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="externalTbl" fit="true"></table>
	</div>
	
	<div id="externalTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="pay.payMgmt.external.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addExternal" onclick="addExternalType()">添加</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.external.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editExternal" onclick="editExternal()">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.external.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="removeExternal" onclick="deletePayExternal()">删除</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.external.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activateExternal" onclick="enablePayExternal()">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.external.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivateExternal" onclick="disablePayExternal()">停用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.external.inactivate">
			<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	 </div>
	 
	<div id="externalPagination"></div>
	
	<div id="externalDialog" title="" draggable="false" maximized="true" toolbar="#externalDialogToolbar">
		<input type="hidden" name="id" id="id"></input>
		<fieldset>
		 <legend>外接支付方式信息</legend>
			<table class="edittable">
				<tr>
					<th style="width:93px;">商户名称:</th>
					<td>
					 <input name="merName" id="merName" class="easyui-validatebox" style=" width:195px;" disabled/>
                    <input name="merCode" id="merCode" type="hidden"/>
					<input type="button" onclick="findMerName();" id="finMER" style="height:24px;" value="选择"/>
						<font color="red">*</font>
					</td>
					  <th style="width:93px;">支付类型:</th>
				    <td>
						<select id="payType"  class="easyui-combobox" panelHeight="120" editable="false"></select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th style="width:93px;">支付方式名称:</th>
					  <td>
						<select id="payWayName" name="payWayName" class="easyui-combobox" editable="false" panelHeight="120"  data-options="valueField:'id',textField:'text'"></select>
						<font color="red">*</font>
					</td>
				    <th style="width:93px;">支付服务费率:</th>
					<td><input name="payServiceRate" id="payServiceRate" type="text"  maxlength="7" style="text-align:right;width:195px;" class="easyui-validatebox" data-options="required:true,validType:'testNumber'" /> ‰<font color="red">*</font></td>
				</tr>
				<tr>
					<th style="width:93px;">排序号:</th>
					<td><input name="sort" id="sort" type="text" maxlength="5" style="text-align:right;width:195px;" class="easyui-validatebox" data-options="required:true,validType:'testNumber'"/><font color="red">*</font></td>
					<th style="width:93px;">启用标识:</th>
                   <td>
                    <label><input type="radio" name="activate" value="0" checked="checked"/>启用</label>
                    <label><input type="radio" name="activate" value="1"/>停用</label><font color="red">*</font>
                   </td>
				</tr>
				<tr>
					<th valign="top" style="width:93px;">备注:</th>
                    <td colspan="6">
                        <textarea rows="6" name="comments" id="comments" style="width: 567px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
                    </td>
				</tr>
			 </table>
			</fieldset>
			
		    <div id="externalDialogToolbar" style="text-align:right;">
			  <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveExternal()">保存</a>
			  <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('externalDialog')">取消</a>
            </div>
	</div>
</body>
</html>
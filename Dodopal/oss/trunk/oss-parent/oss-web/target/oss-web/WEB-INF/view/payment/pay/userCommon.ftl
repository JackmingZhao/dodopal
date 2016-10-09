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
	<script type="text/javascript" src="../../js/payment/pay/userCommon.js"></script>
	<title>用户常用支付方式</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
   <div region="north" border="false" style="height:50px;overflow: hidden;">
		<table id="userCommonQueryCondition" class="viewtable">
			<tr>
				<th>用户名:</th>
				<td><input type="text" id="userNameQuery" maxlength="40"></td>
			
			    <th>支付类型:</th>
				<td>
					<select id="payTypeQuery"  class="easyui-combobox" panelHeight="auto" panelMaxHeight="120" editable="false" style="width: 133px;">
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
					<select id="activateQuery"  class="easyui-combobox" panelHeight="auto" panelMaxHeight="75" editable="false" style="width: 133px;">
						<option value="0" selected>启用</option>
						<option value="1">停用</option>
					    <option value='' >全部</option>
					</select>
				</td>
				<th></th>
				<td>
					<@sec.accessControl permission="pay.payMgmt.user.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findUserCommon();">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('userCommonQueryCondition');">重置</a>
				</td>
				</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="userCommonTbl" fit="true"></table>
	</div>
	<div id="userCommonTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="pay.payMgmt.user.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="removeUserCommon" onclick="deletePayExternal()">删除</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.payMgmt.user.export">
			<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	 </div>
	<div id="userCommonTblPagination"></div>
</body>
</html>
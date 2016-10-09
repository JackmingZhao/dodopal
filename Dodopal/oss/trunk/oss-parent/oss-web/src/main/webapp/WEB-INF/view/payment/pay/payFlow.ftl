<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../js/payment/pay/payFlow.js"></script>
	<title>pay</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:50px;overflow: hidden;">
		<table id="payFlowQueryCondition" class="viewtable">
			<tr>
			<th>交易流水号:</th>
				<td><input type="text" id="tranCode" ></td>
				<th>支付类型:</th>
				<td>
					<select id="payType" panelHeight="100" class="easyui-combobox" editable="false" style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="0">账户支付</option>
						<option value="1">一卡通支付</option>
						<option value="2">在线支付</option>
						<option value="3">银行卡支付</option>
					</select>
				</td>
				<th>支付方式名称:</th>
				<td><input type="text" id="payWayName" ></td>
			    <th>支付状态:</th>
				<td>
					<select id="payStatus"  panelHeight="100" class="easyui-combobox" editable="false" style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="0">待支付</option>
						<option value="1">已取消</option>
						<option value="2">支付中</option>
						<option value="3">支付成功</option>
						<option value="4">支付失败</option>
					</select>
				</td>
				<th></th>
				<td>
					<@sec.accessControl permission="pay.flow.query">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPayFlow();">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('payFlowQueryCondition');">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="payFlowTbl" fit="true"></table>
	</div>
	<div id="payFlowTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="pay.flow.view">
			<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="viewPayFlow" onclick="viewPayFlow()">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pay.flow.export">
			<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="payFlowTblPagination"></div>
	
	
	<div id="viewPayFlowDialogToolbar" style="text-align:right;">
	<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewPayFlowDialog')">返回</a>
	</div>
	<div id="viewPayFlowDialog" style="align:center;" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewPayFlowDialogToolbar">
		<fieldset>
			<legend>支付流水详情</legend>
			<table  class="viewOnly">
				<tr>
					<th style="width:77px;">交易流水号:</th>
					<td name="view_tranCode" id="view_tranCode"></td>
					<th>支付类型:</th>
					<td name="view_payType" id="view_payType"></td>
				</tr>
				<tr>
					<th>支付状态:</th>
					<td name="view_payStatus" id="view_payStatus" ></td>
					<th>支付方式分类:</th>
					<td name="view_payWayKind" id="view_payWayKind">
				</tr>
				<tr>
					</td>
					<th>支付方式:</th>
					<td name="view_payWayName" id="view_payWayName"></td>
					<th>支付服务费率:</th>
					<td name="view_payServiceRate" id="view_payServiceRate"></td>
				</tr>
				<tr>
					<th>支付服务费:</th>
					<td name="view_payServiceFee" id="view_payServiceFee"/></td>
					<th>支付金额:</th>
					<td name="view_payMoney" id="view_payMoney"></td>
				</tr>
				<tr>
					<th>页面回调支付状态:</th>
					<td name="view_pageCallbackStatus" id="view_pageCallbackStatus"></td>
					<th>页面回调时间:</th>
					<td name="view_pageCallbackDate" id="view_pageCallbackDate"></td>
				</tr>
				<tr>
					<th>服务器端通知支付状态:</th>
					<td name="view_serviceNoticeStatus" id="view_serviceNoticeStatus"></td>
					<th>服务器端通知时间:</th>
					<td name="view_serviceNoticeDate" id="view_serviceNoticeDate"></td>
				</tr>
				<tr>
					<th>发送支付密文:</th>
					<td name="view_sendCiphertext" id="view_sendCiphertext"></td>
					<th>接收支付密文:</th>
					<td name="view_acceptCiphertext" id="view_acceptCiphertext"></td>
				</tr>
				<tr>
					<th>创建时间:</th>
					<td name="view_createDate" id="view_createDate"></td>
				</tr>
		</table>
		</fieldset>
	</div>
	
</body>
</html>
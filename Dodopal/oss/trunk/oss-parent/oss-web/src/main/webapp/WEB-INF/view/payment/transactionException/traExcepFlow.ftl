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
	<script type="text/javascript" src="../../js/payment/transactionException/traExcepFlow.js"></script>
	<title>OSS交易流水</title>
</head>

<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:85px;overflow: hidden;">
    <form id="traForm" action="exportTransaction" method="post">  
		<table id="traQueryCondition" class="viewtable">
			<tr>
				<th>交易流水号:</th>
				<td><input type="text" id="tranCodeQuery" name="tranCodeQuery" maxlength="40" style="width: 177px;"></td>
				<th>订单号:</th>
				<td><input type="text" id="orderNumQuery" name="orderNumQuery" maxlength="40" style="width: 177px;"></td>
				<th>用户名/商户名:</th>
				<td><input type="text" id="merOrUserNameQuery" name="merOrUserNameQuery" maxlength="40" style="width: 177px;"></td>
				
				<th></th>
				<td>
					<@sec.accessControl permission="transaction.excepFlow.query">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findTraFlow(1);">查询</a>
					</@sec.accessControl>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('traQueryCondition');">重置</a>
				</td>
			</tr>
			<#--
			<tr>
			<th>业务类型:</th>
				<td>
					<select id="businessTypeQuery" name="businessTypeQuery"  class="easyui-combobox" disabled="disabled" style="width: 180px;" panelHeight="auto" panelMaxHeight="75" editable="false" panelHeight="100%;">
						<option value=''>--请选择--</option>
						<option value="01">一卡通充值</option>
						<option value="02">生活缴费</option>
						<option value="98">转账</option>
						<option value="99" selected>账户充值</option>
					</select>
				</td>
				<th>内部交易状态:</th>
				<td>
					<select id="tranInStatusQuery"  name="tranInStatusQuery" class="easyui-combobox" disabled="disabled" panelHeight="auto" style="width: 180px;"  panelMaxHeight="75" editable="false" panelHeight="100%;">
						<option value=''>--请选择--</option>
						<option value="0">待处理</option>
						<option value="1">处理中</option>
						<option value="3">账户冻结成功</option>
						<option value="4">账户冻结失败</option>
						<option value="5">账户解冻成功</option>
						<option value="6">账户解冻失败</option>
						<option value="7">账户扣款成功</option>
						<option value="8">账户扣款失败</option>
						<option value="10">账户加值成功</option>
						<option value="11" selected>账户加值失败</option>
					</select>
				</td>
				<th>外部交易状态:</th>
				<td>
					<select id="tranOutStatusQuery" name="tranOutStatusQuery" class="easyui-combobox" disabled="disabled" panelHeight="auto" style="width: 180px;"  panelMaxHeight="75" editable="false" panelHeight="100%;">
						<option value=''>--请选择--</option>
						<option value="0">待支付</option>
						<option value="1">已取消</option>
						<option value="2">支付中</option>
						<option value="3" selected>已支付</option>
						<option value="4">待退款</option>
						<option value="5">已退款</option>
						<option value="6">待转账</option>
						<option value="7">转账成功</option>
						<option value="8">关闭</option>
					</select>
				</td>
				</tr>-->
				<tr>
				 	<th>实付金额范围</th>
			 	<td>
			 	  <input type="text" id="realMinTranMoneyQuery" name="realMinTranMoneyQuery" maxlength="9" style="text-align:right;width: 80px;">
			 	   -
			 	  <input type="text" id="realMaxTranMoneyQuery" name="realMaxTranMoneyQuery" maxlength="9" style="text-align:right;width: 80px;">
			 	</td>
			 	
				<th>创建时间:</th>
				<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#createDateEnd').val()});" id="createDateStart" name="createDateStart" style="width:80px;" />
					   -
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#createDateStart').val()});" id="createDateEnd" name="createDateEnd"  style="width:80px;" />
			 	</td>
			 	<th></th>
			 	<td>&nbsp;</td>
			 	<th></th>
			 	<td>&nbsp;</td>
			</tr>
		</table>
		</form>
	</div>
	<div region="center" border="false">
		<table id="traFlowTbl" fit="true"></table>
	</div>
	<div id="traFlowTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="transaction.excepFlow.view">
			<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="viewTraFlow" onclick="viewTraFlow()">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="transaction.excepFlow.handle">
           <a href="#" class="easyui-linkbutton" iconCls="icon-user" plain="true" id="handleException" onclick="handleException()">异常处理</a>
        </@sec.accessControl>
        <@sec.accessControl permission="transaction.excepFlow.export">
           <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportExceptionTra" >导出Excel</a>
        </@sec.accessControl>
	</div>
	<div id="traFlowTblPagination"></div>
	
	
	<div id="viewTraFlowDialogToolbar" style="text-align:right;">
		<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeViewTraFlow()">返回</a>
	</div>

	<div id="viewTraFlowDialog" style="align:center;" class="easyui-dialog" closable="false" title="" closed="true" draggable="false" maximized="true" toolbar="#viewTraFlowDialogToolbar">
		<input type="hidden" name="userId" id="userId"></input>
			<fieldset>
				<legend>交易流水详情</legend>
				<table class="viewOnly">
					<tr>
						<th style="width:77px;">交易流水号:</th> 
						<td id="tranCodeView"></td>
						<th>订单号:</th>
						<td id="orderNumberView"></td>
					</tr>
					<#--<tr>					
					<th style="width:77px;">原交易流水号:</th> 
						<td id="oldTranCodeView"></td>
					<th style="width:77px;">交易名称:</th>
						<td id="tranNameView"></td>
					</tr>-->
					<tr>
						<th>用户类型:</th>
						<td id="userTypeView"></td>
						<th>用户号|商户号:</th>
						<td id="merOrUserCodeView"></td>
					</tr>
					<tr>
						<th>交易类型:</th>
						<td id="tranTypeView"></td>
						<th>交易金额:</th>
						<td id="amountMoneyView"></td>
					</tr>
					
					<tr>
						<th>外部交易状态:</th>
						<td id="tranOutStatusView"></td>
						<th>内部交易状态:</th>
						<td id="tranInStatusView"></td>
					</tr>
					<tr>
						<th>商品名称:</th>
						<td id="commodityView"></td>
						<th>支付方式:</th>
						<td id="payWayView"></td>
					</tr>
					<tr>
						<th>支付类型:</th>
						<td id="payTypeView"></td>
						<th>业务类型:</th>
						<td id="businessTypeView"></td>
					</tr>
					<tr>
					    <th>支付服务费率类型:</th>
						<td id="payServiceTypeView"></td>
						<th>支付服务费率:</th>
						<td id="payServiceRateView"></td>
					</tr>
					<tr>
						<th>支付服务费:</th>
						<td id="payServiceFeeView"></td>
					</tr>
					<tr>
						<th>支付手续费率:</th>
						<td id="payProceRateView"></td>
						<th>支付手续费:</th>
						<td id="payProceFeeView"></td>
					</tr>
					<tr>
					    <th>应付金额:</th>
						<td id="payMoneyView"></td>
						<th>实际交易金额:</th>
						<td id="realTranMoneyView"></td>
					</tr>
					<#--
					<tr>
						<th>转出商户:</th>
						<td id="turnOutMerCodeView"></td>
						<th>转入商户:</th>
						<td id="turnIntoMerCodeView"></td>
					</tr>
					<tr>
						<th>页面回调地址:</th>
						<td id="pageCallbackUrlView"></td>
						<th>服务器端通知地址:</th>
						<td id="serviceNoticeUrlView"></td>
					</tr>
					-->
					<tr>
						<th>清分标记位:</th>
						<td id="clearFlagView"></td>
						<th>交易时间:</th>
						<td id="finishDateView"></td>
					</tr>
					<tr>
						<th>来源:</th>
						<td id="sourceView"></td>
						<th valign="top">备注:</th>
						<td id="commentsView" style="word-break:break-all"></td>
				    </tr>
				</table>
				<br/>
			</fieldset>
			<br/>
	</div>
</body>
</html>
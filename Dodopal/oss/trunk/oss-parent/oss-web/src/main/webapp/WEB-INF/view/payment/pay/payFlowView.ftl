<div id="viewPayFlowDialogToolbar" style="text-align:right;">
	<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewPayFlowDialog')">返回</a>
</div>
<div id="viewPayFlowDialog" style="align:center;" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewPayFlowDialogToolbar">
	<fieldset>
		<legend>POS厂商</legend>
		<table  class="viewOnly">
			<tr>
				<th style="width:77px;">Id</th>
				<td name="view_id" id="view_id"></td>
				<th style="width:77px;">交易流水号:</th>
				<td name="view_tranCode" id="view_tranCode"></td>
			</tr>
			<tr>
				<th>支付状态:</th>
				<td name="view_payStatus" id="view_payStatus" ></td>
				<th>支付类型:</th>
				<td name="view_payType" id="view_payType"></td>
			</tr>
			<tr>
				<th>支付方式分类:</th>
				<td name="view_payWayKind" id="view_payWayKind">
				</td>
				<th>支付方式ID:</th>
				<td name="view_payWayId" id="view_payWayId"></td>
			</tr>
			<tr>
				<th>支付服务费率:</th>
				<td name="view_payServiceRate" id="view_payServiceRate"></td>
				<th>支付服务费:</th>
				<td name="view_payServiceFee" id="view_payServiceFee"/></td>
			</tr>
			<tr>
				<th>支付金额:</th>
				<td name="view_payMoney" id="view_payMoney"></td>
				<th>页面回调支付状态:</th>
				<td name="view_pageCallbackStatus" id="view_pageCallbackStatus"></td>
			</tr>
			<tr>
				<th>页面回调时间:</th>
				<td name="view_pageCallbackDate" id="view_pageCallbackDate"></td>
				<th>服务器端通知支付状态:</th>
				<td name="view_serviceNoticeStatus" id="view_serviceNoticeStatus"></td>
			</tr>
			<tr>
				<th>服务器端通知时间:</th>
				<td name="view_serviceNoticeDate" id="view_serviceNoticeDate"></td>
				<th>发送支付密文:</th>
				<td name="view_sendCiphertext" id="view_sendCiphertext"></td>
			</tr>
			<tr>
				<th>接收支付密文:</th>
				<td name="view_acceptCiphertext" id="view_acceptCiphertext"></td>
				<th>创建时间:</th>
				<td name="view_createDate" id="view_createDate"></td>
			</tr>
	</table>
	</fieldset>
</div>
	
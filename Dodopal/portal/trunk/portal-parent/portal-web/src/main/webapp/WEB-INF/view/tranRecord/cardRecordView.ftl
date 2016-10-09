<div class="con-main" id="cardRecordView"> <!-- InstanceBeginEditable name="main" -->
<h3 class="tit-h3">卡交易详情</h3>
	<div class="com-bor-box com-bor-box01">
		
			<h4 class="times" id="cardOrderStatus"></h4>
			<ul class="times-info">
            	<li id="type"></li>
            	<li id="orderDate"></li>
            </ul>
			<form action="/">
			<div class="w890 mb0">
			<table cellspacing="0" cellpadding="0" border="0" class="com-table01" id="cardRecordViewTable">
			<colgroup>
            <col width="15%">
			<col width="11%">
			<col width="10%">
            <col width="13%">
            <col width="13%">
			<col width="8%">
			</colgroup>
            <tbody>
            <tr>
				<th>订单编号</th>
				<th>交易金额（元）</th>
				<th>商户名称</th>
                <th>交易前卡余额（元）</th>
				<th>交易后卡余额（元）</th>
                <th>卡号</th>
			</tr>
			<tr>
				<td id="orderNum"></td>
                <td id="txnAmt"></td>
				<td id="merName"></td>
                <td id="befBal"></td>
                <td id="blackAmt"></td>
				<td id="cardFaceNo"></td>
			</tr>
		</tbody></table>
		<div class="null-box"></div>
			<div class="a-center btn-box"><a href="#" class="orange-btn-h32" onclick="clearView()">返回</a></div>
			</div>
			
		</form>
	</div>
</div>
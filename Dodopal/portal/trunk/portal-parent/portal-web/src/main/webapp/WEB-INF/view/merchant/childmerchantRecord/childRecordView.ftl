<div class="con-main"  id="childRecordView"> 
<h3 class="tit-h3">交易详情</h3>
	<div class="com-bor-box com-bor-box01">
		
			<h4 class="times" id="tranOutStatusDetail"></h4>
			<ul class="times-info">
            	<li id="orderDate">订单时间:</li>
				<li id="createDate">交易时间:</li>
				<li id="comments">备注:</li>
            </ul>
			<form action="/">
			<div class="w890 mb0">
			<table cellspacing="0" cellpadding="0" border="0" class="com-table01" id="childTranRecordViewTable">
			<colgroup>
            <col width="19%">
			<col width="19%">
			<col width="8%">
            <col width="8%">
            <col width="8%">
			<col width="8%">
			<col width="8%">
			</colgroup>
            <tbody>
            <tr>				
				<th>交易流水号</th>
				<th>订单编号</th>
				<th>业务名称</th>
                <th>交易类型</th>
				<th>应付金额（元）</th>
                <th>实付金额（元）</th>
			</tr>
			<tr>
				<td id="tranCodeView"></td>
                <td id="orderNumberView"></td>
				<td id="businessTypeView"></td>
                <td id="tranTypeView"></td>
                <td id="amountMoneyView"></td>
				<td id="realTranMoneyView"></td>
			</tr>
		</tbody></table>
		<div class="null-box"></div>
			<div class="a-center btn-box"><a href="#" onclick="clearView()" class="orange-btn-h32">返回</a></div>
			</div>
			
		</form>
	</div>
</div>
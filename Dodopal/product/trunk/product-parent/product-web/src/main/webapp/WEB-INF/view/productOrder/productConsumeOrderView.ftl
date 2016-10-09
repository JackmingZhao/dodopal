<div class="con-main" id="productConsumeOrderView" > <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">一卡通消费订单详情</h3>
	<div class="com-bor-box com-bor-box01">
			<table class="base-table  base-table01" id="productOrderViewTable">
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<tr>
					<th>订单编号：</th>
					<td><span id="orderNumSpan"></span></td>
					<th>商户名称：</th>
					<td><span id="merNameSpan"></span></td>
				</tr>
				<tr>
					<th>城市：</th>
					<td><span id="cityNameSpan"></span></td>
					<th>通卡公司名称：</th>
					<td><span id="yktNameSpan"></span></td>
				</tr>
				<tr>
				   <th>POS号：</th>
					<td><span id="posCodeSpan"></span></td>
				   <th>卡号：</th>
					<td><span id="cardNumSpan"></span></td>
				</tr>
				<tr>
				    <th>应付金额（元）：</th>
					<td><span id="originalPriceSpan"></span></td>
					<th>实付金额（元）：</th>
					<td><span id="receivedPriceSpan"></span></td>
				</tr>
				<tr>
					<th>消费前金额（元）：</th>
					<td><span id="befbalSpan"></span></td>
					<th>消费后金额（元）：</th>
					<td><span id="blackAmtSpan"></span></td>
				</tr>
				<tr>
					<th>商户服务费率类型：</th>
					<td><span id="serviceRateTypeSpan"></span></td>
					 <th>商户服务费率：</th>
					<td><span id="serviceRateSpan"></span></td>
				</tr>
				<tr>
					<th>折扣：</th>
					<td><span id="merDiscountSpan"></span></td>
					<th>订单状态：</th>
					<td><span id="stateSpan"></span></td>
				</tr>
				<tr>
				<th>操作员：</th>
					<td><span id="userNameSpan"></span></td>
				<th>订单时间：</th>
					<td><span id="proOrderDateSpan"></span></td>
				</tr>
				<tr>
				<th>POS备注：</th>
					<td><span id="posCommentsSpan"></span></td>
				<th></th>
				<td></td>
				</tr>
				<tr>
					<td colspan="4" class="a-center"><div class="btn-box"><a href="#" onclick="clearView('productConsumeOrder')" class="orange-btn-h32">返回</a></div></td>
				</tr>
			</table>
		</div>
	</div>
</div>
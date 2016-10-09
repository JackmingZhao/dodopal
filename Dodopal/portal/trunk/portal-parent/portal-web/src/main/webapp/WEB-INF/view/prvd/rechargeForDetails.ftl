<div class="con-main" id="rechargeForSupplierDetailsForm"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">一卡通充值-交易明细</h3>
	<div class="com-bor-box com-bor-box01">
		<div class="deal">
		<div class="seach-top-inner">
			<form action="exportCardRechargeDetails" id="recordDetailForm">
				<ul class="clearfix">
					<li>
						<label><span class="w80">POS号：</span>
							<span class="a-left" id="posCodeSpan"></span>
							<input type="hidden" id="posCodeDetail" name="posCodeDetail" value=""/>
						</label>
					</li>
				</ul>
				<ul class="clearfix">
					
					<li>
						<label><span class="w80">订单编号：</span>
							<input type="text" id="proOrderNumDetail" name="proOrderNumDetail" class="com-input-txt w88" />
						</label>
					</li>
					<li>
						<label><span class="w130">商户名称：</span>
							<input type="text" id="merNameDetail"  name="merNameDetail" class="com-input-txt w88" />
						</label>
					</li>
					<li class="w350"><span class="w130">交易日期：</span>
						  <input value=${orderDateStart} readonly="true" name="orderDateStart" type="text" class="com-input-txt w74"   id="orderDateStart" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  -
						  <input value=${orderDateEnd} readonly="true" name="orderDateEnd" type="text" class="com-input-txt w74"   id="orderDateEnd" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					  </li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findrechargeForDetails()"/>
						<input type="reset" value="清空" class="orange-btn-text26"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
			<form action="/">
			<div class="w890 mb0">
			<table cellspacing="0" id="supplierDetailsTb" cellpadding="0" border="0" class="com-table01">
			<colgroup>
			<col width="1%">
			<col width="13%">
			<col width="7%">
			<col width="7%">
			<col width="11%">
			<col width="10%">
			<col width="5%">
			<col width="12%">
			<col width="1%">
			</colgroup><thead>
			<tr>
				<th>&nbsp;</th>
				<th>订单编号</th>
				<th>商户名称</th>
				<th>充值金额（元）</th>
				<th>卡号</th>
				<th>充值后卡内余额（元）</th>
				<th>订单状态</th>
				<th>充值时间</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div class="null-box" style="display:none;"></div>
		<p class="page-navi" id="rechargeForDetails"><span class="fl">交易记录下载：
		<a href="#" class="easyui-linkbutton" id="exportSupplierDetails" onclick="exportDetailExcel()">导出Excel</a></span></p>
			<div class="a-center btn-box">
				<a href="#" onclick="returnSum();" class="orange-btn-h32">返回</a>
			</div>
		</div>
		</form>
	</div>
</div>


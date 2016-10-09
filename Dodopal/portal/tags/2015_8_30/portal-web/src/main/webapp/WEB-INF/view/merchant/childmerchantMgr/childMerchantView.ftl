<div class="con-main" id="childMerchantDetailView"  style="display:none;">
	<h3 class="tit-h3">查看子商户信息</h3>
	<div class="com-bor-box com-bor-box01">
			<h4 class="tit-h4">注册信息</h4>
			<input type="hidden" id="merCodeSpan" >
			<table class="base-table base-table01" id="childMerchantViewTable">
				<col width="140" />
				<col width="330" />
				<col width="120" />
				<tr>
					<th>商户类型：</th>
					<td><span id="merTypeSpan"></span></td>
					<th>审核状态：</th>
					<td><span id="merStateSpan"></span></td>
				</tr>
				<tr>
					<th>商户名称：</th>
					<td><span id="merNameSpan"></span></td>
					<th>用户名：</th>
					<td><span id="merUserNameSpan"></span></td>
				</tr>
				<tr>
					<th>联系人：</th>
					<td><span id="merLinkUserSpan"></span></td>
					<th>手机号码：</th>
					<td><span id="merLinkUserMobileSpan"></span></td>
				</tr>
				<tr>
					<th>详细地址：</th>
					<td><span id="merAddsSpan"></span></td>
				</tr>
				<tr>
					<th>通卡公司：</th>
					<td colspan="3" id=""><span id="yktinfoViewSpan"></span>
							<div class="tit-pop tit-pop02" style="display: block;" id="yktDIVView">
								<table id="yktTableView" width="100%" border="0" class="">
											<colgroup>
											<col width="2%" />
											<col width="8%" />
											<col width="8%" />
											<col width="8%" />
											<col width="7%" />
											<col width="2%" />
											</colgroup>
											<thead>
											<tr>
												<th>&nbsp;</th>
												<th>通卡公司名称</th>
												<th>业务名称</th>
												<th>费率分类</th>
												<th>数值</th>
												<th>&nbsp;</th>
											</tr>
											</thead>
											<tbody id="yktTableTbodyView">
											</tbody>
										</table>
							</div>
							<div class="tip-error"></div></td>
				</tr>
			</table>
			<div class="des-line"></div>
			<h4 class="tit-h4">补充信息</h4>
			<table class="base-table">
				<col width="140" />
				<col width="330" />
				<col width="120" />
				<tr>
					<th>经营范围：</th>
					<td><span id="merBusinessScopeIdSpan"></span></td>
					<th>电子邮箱：</th>
					<td><span id="merEmailSpan"></span></td>
				</tr>
				<tr>
					<th>固定电话：</th>
					<td><span id="merTelephoneSpan"></span></td>
					<th>传真：</th>
					<td><span id="merFaxSpan"></span></td>
				</tr>
				<tr>
					<th>证件类型：</th>
					<td><span id="merUserIdentityTypeSpan"></span></td>
					<th>证件号码：</th>
					<td><span id="merUserIdentityNumberSpan"></span></td>
				</tr>
				<tr>
					<th>邮编：</th>
					<td><span id="merZipSpan"></span></td>
					<th>开户银行：</th>
					<td><span id="merBankNameSpan"></span></td>
				</tr>
				<tr>
					<th>开户行账号：</th>
					<td><span id="merBankAccountSpan"></span></td>
					<th>开户名称：</th>
					<td><span id="merBankUserNameSpan"></span></td>
				</tr>
				<tr>
					<th>备注：</th>
					<td colspan="4" class="remark" id="merUserRemarkSpan" style="word-break:break-all"></td>
				</tr>
				<tr>
					<td colspan="4" class="a-center"><a href="#" onclick="clearView('childMerchant')" class="orange-btn-h32">返回</a></td>
				</tr>
			</table>
	</div>
	</div>
</div>




<div class="con-main" id="childMerchantEdit" style="display:none;"> <!-- InstanceBeginEditable name="main" -->
	<div class="new-peo">
		<h3 class="tit-h3">编辑已审核子商户信息</h3>
		<div class="com-bor-box com-bor-box01">
			<h4 class="tit-h4">注册信息</h4>
			<form action="/" id="childMerchantVerEditForm">
				<table class="base-table base-table01">
					<col width="120" />
					<col width="330" />
					<col width="120" />
					<input type="hidden"  id="merCodeVer" name="merCodeVer" />
					<tr>
						<th>商户类型：</th>
						<td><span id="merTypeVer"></span></td>
						<th>审核状态：</th>
						<td><span id="merStateVer"></span></td>
					</tr>
					<tr>
						<th>商户名称：</th>
						<td><span id="merNameVer"></span></td>
						<th>用户名：</th>
						<td><span id="merUserNameVer"></span></td>
					</tr>
					
					<tr>
						<th>联系人：</th>
						<td><span id="merLinkUserVer"></span></td>
						<th>手机号码：</th>
						<td><span id="merLinkUserMobileVer"></span></td>
					</tr>
					<tr>
						<th>详细地址：</th>
						<td><span id="merAddsVer"></span></td>
					</tr>
					<tr>
					<th>通卡公司：</th>
					<td colspan="3"><span id="yktinfoVerViewSpan"></span>
							<div class="tit-pop tit-pop02" style="display:block;position:static; width: 640px;max-height: 290px; overflow-x:hidden; overflow: auto;" id="yktDivView">
								<table id="yktTableVer" width="100%" border="0" class="">
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
												<th>业务</th>
												<th>费率分类</th>
												<th>数值</th>
												<th>&nbsp;</th>
											</tr>
											</thead>
											<tbody id="yktinfoVerView">
											</tbody>
										</table>
							</div>
							<div class="tip-error"></div></td>
				</tr>
				</table>
				<div class="des-line"></div>
				<h4 class="tit-h4">补充信息</h4>
				<table class="base-table" id="childMerchantVerTable">
					<col width="120" />
					<col width="330" />
					<col width="120" />
					<tr>
						<th>经营范围：</th>
						<td><select  id="merBusinessScopeIdVer" name="merBusinessScopeIdVer">
							</select>
							<div class="tip-error"> </div></td>
						<th>电子邮箱： </th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="输入您的电子邮箱地址" id="merEmailVer" name="merEmailVer" maxlength="20" onfocus="validateMerEmailVer(false);"/>
							<div class="tip-error" id="merEmailVerERR"></div></td>
					</tr>
					<tr>
						<th>固定电话：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="如：021-62382888"  id="merTelephoneVer" name="merTelephoneVer"  maxlength="20" onfocus="validateMerTelephoneVer(false);"/>
							<div class="tip-error" id="merTelephoneVerERR"> </div></td>
						<th>传真：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="如：021-62382888"  id="merFaxVer" name="merFaxVer"  maxlength="20" onfocus="validateMerFaxVer(false);"/>
							<div class="tip-error" id="merFaxVerERR"></div></td>
					</tr>
					<tr>
						<th> 证件类型：</th>
						<td><select  id="merUserIdentityTypeVer" name="merUserIdentityTypeVer">
							</select>
							<div class="tip-error"> </div></td>
						<th> 证件号码：</th>
						<td><input type="text" class="com-input-txt w260"  myPlaceholder="请正确输入证件号码" id="merUserIdentityNumberVer" name="merUserIdentityNumberVer" maxlength="20" onfocus="checkIdentityNumber(false,'merUserIdentityTypeVer','merUserIdentityNumberVer','merUserIdentityNumberVerERR');"/>
							<div class="tip-error" id="merUserIdentityNumberVerERR"></div></td>
					</tr>
					<tr>
						<th>邮编： </th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="请正确输入邮编"  id="merZipVer" name="merZipVer" maxlength="6" onfocus="validateMerZipVer(false);"/>
							<div class="tip-error" id="merZipVerERR"></div></td>
						<th>开户银行：</th>
						<td><select  id="merBankNameVer" name="merBankNameVer">
								<option value ="" selected>－－ 请选择 －－</option>
								<option value="1">工商银行</option>
								<option value="2">交通银行</option>
								<option value="3">建设银行</option>
								<option value="4">农业银行</option>
								<option value="5">中国银行</option>
							</select>
							<div class="tip-error"></div></td>
					</tr>
					<tr>
						<th>开户行账号：</th>
						<td ><input type="text" class="com-input-txt w260" myPlaceholder="请输入开户行账号"  id="merBankAccountVer" name="merBankAccountVer" maxlength="19" onfocus="checkMerBankAccount(false,'merBankNameVer','merBankAccountVer','merBankAccountVerERR');" />
							<div class="tip-error" id="merBankAccountVerERR"> </div></td>
						<th>开户名称：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="输入开户名称" id="merBankUserNameVer" name="merBankUserNameVer" maxlength="50" onfocus="validateMerBankUserNameVer(false);"/>
							<div class="tip-error" id="merBankUserNameVerERR"></div></td>
					</tr>
					<tr>
						<th>备注：</th>
						<td colspan="3"><textarea  class="area-w268" id="merUserRemarkVer" name="merUserRemarkVer" maxlength="200" onkeydown="checkMaxlength(this,200);" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"></textarea>
							<div class="tip-error"></div></td>
					</tr>
					<tr>
						<th></th>
						<td colspan="4" class="a-center"><div class="btn-box">
						<input type="submit" class="orange-btn-h32" value="保存"  onclick="return saveChildMerchantVer();" />
						<input type="reset" class="orange-btn-text32" onclick="clearChileMerchantView('childMerchant');" value="取消"></div></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>
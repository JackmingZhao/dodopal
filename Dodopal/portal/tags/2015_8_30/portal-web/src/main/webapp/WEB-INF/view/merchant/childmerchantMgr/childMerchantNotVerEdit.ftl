<div class="con-main" id="childMerchantNotVerEdit" style="display:none;"> 
	<div class="new-peo">
		<h3 class="tit-h3">编辑审核不通过子商户信息</h3>
		<div class="com-bor-box com-bor-box01">
			<div>
			<h4 class="tit-h4">注册信息</h4>
			<div class="tip-no-pass" style="display:;"><strong>审核未通过原因：</strong>
			<p id="merRejectReasonNotVer"></p>
			</div>
            </div>
			<input type="hidden" id="merProNotVerType" name="merProNotVerType"  value="${(sessionUser.merType)!}"/> 
			<form action="/" id="childMerchantNotVerForm">
				<table class="base-table">
					<col width="120" />
					<col width="330" />
					<col width="120" />
					<input type="hidden"  id="merCodeNotVer" name="merCodeNotVer" />
					<input type="hidden"  id="userCodeNotVer" name="userCodeNotVer" />
					<#if sessionUser.merType =='12'>
					<tr>
						<th><span class="red">*</span>商户类型：</th>
						<td><select id="merTypeNotVer" name="merTypeNotVer" onChange="checkNotVerMerType()">
								<option value="" selected>－－ 请选择 －－</option>
								<option value="13">直营</option>
								<option value="14">加盟</option>
							</select>
							<div class="tip-error tip-red-error" id="merTypeNotVerERR"> </div></td>
						<th>启用标识：</th>
						<td><ul class="ipt-list clearfix">
								<li>
									<label>
										<input type="radio" name="activateNotVer" checked="checked" value="0">
										启用</label>
								</li>
								<li>
									<label>
										<input type="radio" name="activateNotVer" value="1" />
										停用</label>
								</li>
							</ul>
							<div class="tip-error tip-red-error"></div></td>
					</tr>
					</#if>
					<tr>
						<th><span class="red">*</span>商户名称：</th>
						<td><input type="text" class="com-input-txt w260" id="merNameNotVer" name="merNameNotVer"  onfocus="validateMerNameNotVer(false)" myPlaceholder="支持中文、数字及字母" maxlength="50"/>
							<div class="tip-error tip-red-error" id="merNameNotVerERR"></div>
							<div class="tip-error tip-red-error" id="merNameNotVerERR2" style="display:none"></div>
							</td>
						<th><span class="red">*</span>用户名：</th>
						<td><input type="text" class="com-input-txt w260" id="merUserNameNotVer" name="merUserNameNotVer" onfocus="validateMerUserNameNotVer(false)" myPlaceholder='4-20位字符，支持字母、数字、“_”，首位为字母' maxlength="20" />
							<div class="tip-error tip-red-error" id="merUserNameNotVerERR"></div>
							<div class="tip-error tip-red-error" id="merUserNameNotVerERR2" style="display:none"></div>
							</td>
					</tr>
					<tr>
						<th><span class="red">*</span>密码：</th>
						<td><input type="password" class="com-input-txt w260" placeholder="6-20位字符，支持数字、字母及符号" id="merUserPWDNotVer" onfocus="validateMerUserPWDNotVer(false)" name="merUserPWDNotVer" maxlength="20"/>
							<div class="tip-error tip-red-error" id="merUserPWDNotVerERR"></div>
							<div class="tip-error tip-red-error" id="merUserPWDNotVerERR2" style="display:none"></div>
							</td>
						<th><span class="red">*</span>确认密码：</th>
						<td><input type="password" class="com-input-txt w260" myPlaceholder="请再次输入密码" id="merUserPWDTwoNotVer" onfocus="validateMerUserPWDTwoNotVer(false)" name="merUserPWDTwoNotVer" maxlength="20"/>
							<div class="tip-error tip-red-error" id="merUserPWDTwoNotVerERR"></div>
							<div class="tip-error tip-red-error" id="merUserPWDTwoNotVerERR2" style="display:none"></div>
							</td>
					</tr>
					<tr>
						<th><span class="red">*</span>联系人：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="2-20字符，支持中文、英文" id="merLinkUserNotVer" onfocus="validateMerLinkUserNotVer(false)" name="merLinkUserNotVer" maxlength="20"/>
							<div class="tip-error tip-red-error" id="merLinkUserNotVerERR"></div></td>
						<th><span class="red">*</span>手机号码：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="输入的手机号可作为登录名" id="merLinkUserMobileNotVer" onfocus="validateMerLinkUserMobileNotVer(false)" name="merLinkUserMobileNotVer" maxlength="11"/>
							<div class="tip-error tip-red-error" id="merLinkUserMobileNotVerERR"></div>
							<div class="tip-error tip-red-error" id="merLinkUserMobileNotVerERR2" style="display:none"></div>
							</td>
					</tr>
					<tr>
						<th><span class="red">*</span>详细地址：</th>
						<td colspan="3" class="select-wid03">
							<select  id="provinceNotVer" name="provinceNotVer">
							</select>
							<select  id="cityNotVer" name="cityNotVer">
							</select>
							<input type="tel" class="com-input-txt w490" myPlaceholder="例如：XX市XX区XX街XX号XX室" id="merAddsNotVer" name="merAddsNotVer" maxlength="200" onfocus="validateMerAddsNotVer(false);"/>
							<div class="tip-error tip-red-error" id="merAddsNotVerERR" style = "margin-left:224px;"></div></td>
					</tr>
				</table>
				<table class="base-table" width="100%"><col width="120"/>
					<tr id="notYktLine">
						<th>通卡公司：</th>
						<td colspan="3"><input type="text" class="com-input-txt w714"  disabled id="notVer_yktNameSpan"/>
							<a href="javascript:;" class="blue-link" id="xuanzheNotVer">选择</a>
							<div class="pop-win" id="notVer_yktNameView"  style="display: none;">
								<div class="bg-win"></div>
								<div class="bg-outbox" style="background: #f6f6f6;opacity: 0;"></div>
								<div class="pop-box" style="background: #f6f6f6;">
									<div class="innerBox">
										<table width="100%" border="0" class="base-table base-table01" id="notVer_yktCheckBox">
										</table>
                                        <div class="btn-box" style="margin: 85px 0px 0px 0px;">
											<ul class="ul-btn">
												<li><a href="javascript:;" class="pop-borange" onclick="notVer_yktNameCheckBox();">确认</a></li>
												<li><a href="javascript:;" onclick="$(this).closest('.pop-win').hide();" class="pop-bgrange">取消</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="tit-pop tit-pop02" js="okbox"style="display: none;" id="notVer_busRateTypeView">
									<table id="notVer_busRateTable"  width="100%" border="0" class="">
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
													<th class="a-center"><input type="checkbox" onclick="toggle(this,'checkbox_notVer');"/></th>
													<th>通卡公司名称</th>
													<th>业务名称</th>
													<th>费率分类</th>
													<th>数值</th>
													<th>&nbsp;</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
							</div>
							<div class="tip-error tip-red-error"></div></td>
					</tr>
				</table>
				<div class="des-line"></div>
				<h4 class="tit-h4">补充信息</h4>
				<table class="base-table">
					<col width="120" />
					<col width="330" />
					<col width="120" />
					<tr>
						<th>经营范围：</th>
						<td><select id="merBusinessScopeIdNotVer"   name="merBusinessScopeIdNotVer">
							</select>
							<div class="tip-error tip-red-error"> </div></td>
						<th>电子邮箱： </th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="格式：yyyy@xxx.com"  onfocus="validateMerEmailNotVer(false)" id="merEmailNotVer" name="merEmailNotVer" maxlength="60"/>
							<div class="tip-error tip-red-error" id="merEmailNotVerERR"></div></td>
					</tr>
					<tr>
						<th>固定电话：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="如：021-62382888" onfocus="validateMerTelephoneNotVer(false)"  id="merTelephoneNotVer" name="merTelephoneNotVer"  maxlength="20"/>
							<div class="tip-error tip-red-error" id="merTelephoneNotVerERR"> </div></td>
						<th>传真：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="如：021-62382888"  onfocus="validateMerFaxNotVer(false)" id="merFaxNotVer" name="merFaxNotVer"  maxlength="20"/>
							<div class="tip-error tip-red-error" id="merFaxNotVerERR"></div></td>
					</tr>
					<tr>
						<th> 证件类型：</th>
						<td><select id="merUserIdentityTypeNotVer" name="merUserIdentityTypeNotVer">
							</select>
							<div class="tip-error tip-red-error"> </div></td>
						<th> 证件号码：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="请正确输入证件号码"  id="merUserIdentityNumberNotVer" name="merUserIdentityNumberNotVer" maxlength="20" onfocus="checkIdentityNumber(false,'merUserIdentityTypeNotVer','merUserIdentityNumberNotVer','merUserIdentityNumberNotVerERR');"/>
							<div class="tip-error tip-red-error" id="merUserIdentityNumberNotVerERR"></div></td>
					</tr>
					<tr>
						<th>邮编： </th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="如：210000"  onfocus="validateMerZipNotVer(false)"  id="merZipNotVer" name="merZipNotVer" maxlength="6"/>
							<div class="tip-error tip-red-error" id="merZipNotVerERR"></div></td>
						<th>开户银行：</th>
						<td><select id="merBankNameNotVer" name="merBankNameNotVer">
								<option value ="" selected>－－ 请选择 －－</option>
								<option value="1">工商银行</option>
								<option value="2">交通银行</option>
								<option value="3">建设银行</option>
								<option value="4">农业银行</option>
								<option value="5">中国银行</option>
							</select>
							<div class="tip-error tip-red-error"></div></td>
					</tr>
					<tr>
						<th>开户行账号：</th>
						<td ><input type="text" class="com-input-txt w260" myPlaceholder="请输入开户行账号"  id="merBankAccountNotVer" name="merBankAccountNotVer" maxlength="19" onfocus="checkMerBankAccount(false,'merBankNameNotVer','merBankAccountNotVer','merBankAccountNotVerERR');" />
							<div class="tip-error tip-red-error" id="merBankAccountNotVerERR"> </div></td>
						<th> 开户名称：</th>
						<td><input type="text" class="com-input-txt w260" myPlaceholder="输入开户名称" onfocus="validateMerBankUserNameNotVer(false)" id="merBankUserNameNotVer" name="merBankUserNameNotVer" maxlength="50"/>
							<div class="tip-error tip-red-error" id="merBankUserNameNotVerERR"></div></td>
					</tr>
					<tr>
						<th>备注：</th>
						<td colspan="3"><textarea  class="area-w268 " id="merUserRemarkNotVer" onkeydown="checkMaxlength(this,200);" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
							<div class="tip-error tip-red-error" id="merUserRemarkNotVerERR"></div></td>
					</tr>
					<tr>
						<th></th>
						<td colspan="4" class="a-center">
						<div class="btn-box">
						<input type="submit" class="orange-btn-h32" value="保存"  onclick="return saveChildMerchantNotVer();" />
						<input type="reset" class="orange-btn-text32" onclick="clearChileMerchantView('childMerchant');" value="取消"/>
						</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	</div>
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
					<#if sessionUser.merType =='12'>
					<tr id="isAutoEditShow">
						<th><span class="red">*</span>是否自动分配：</th>
						<td><ul class="ipt-list clearfix">
							    <li><label><input type="radio" name="isAutoEditVer" value="0" onclick="isAutoEditClick('true')" />是</label></li>
							    <li><label><input type="radio" name="isAutoEditVer" value="1" onclick="isAutoEditClick('false')"/>否</label></li>
                            	<li><label><input type="radio" name="isAutoEditVer" value="2" onclick="isAutoEditClick('false')"/>共享额度</label></li>
						    </ul>
						</td>
					</tr>
					<tr id="thresholdEditShow">
					   <th><span class="red">*</span>额度阀值：</th>
					   <td><input type="text" class="com-input-txt w260" myPlaceholder="数字，整数6位，两位小数"  id="thresholdEditVer" name="thresholdEditVer" maxlength="20" onfocus="validateThresholdEditVer(false);" />
					   <div class="tip-error" id="thresholdEditVerERR"></div></td>

					   <th><span class="red">*</span>自动分配额度：</th>
					   <td><input type="text" class="com-input-txt w260" myPlaceholder="数字，整数6位，两位小数" id="limitEditVer" name="limitEditVer" maxlength="20"  onfocus="validateLimitEditVer(false);"/>
					   <div class="tip-error" id="limitEditVerERR"></div></td>
					</tr>
				 <!--   <tr id="isAutoJoinEditShow">
						<th><span class="red">*</span>额度来源：</th>
						<td><ul class="ipt-list clearfix">
								<li><label><input type="radio"  name="isAutoJoinEditVer" value="0"  disabled="disabled"/>自己购买</label></li>
								<li><label><input type="radio" name="isAutoJoinEditVer" value="1" disabled="disabled"/>上级分配</label></li>
							</ul>
						</td>
					</tr> -->
					</#if>
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
						<th>业务类型：</th>
						<td><span id="rateCodeView"></span></td>
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
						<td><select  id="merBankNameVer" name="merBankNameVer"></select>
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
						<th>学历：</th>
						<td>
							<select id="merUserEducationVer" name="merUserEducationVer"></select>
							<div class="tip-error" id="educationVal"></div>
						</td>
						<th>年收入(元)：</th>
						<td>
							<input type="text" id="merUserIncomeVer"  style="text-align: right;" class="com-input-txt w260" myPlaceholder="数字，整数8位，两位小数 ， 单位：元"  maxlength="60" onfocus="validateMerUserIncomeVer(false);"/>
							<div class="tip-error" id="merUserIncomeVerVal"></div>
						</td>
					</tr>
					<tr>
						<th>出生年月：</th>
						<td>
							<input type="text" id="merUserBirthdayVer" class="com-input-txt w260" myPlaceholder="请选择生日"  readonly="readonly" onfocus="c.showMoreDay = false;c.timeOver=new Date();c.show(this,'');"  maxlength="20" />
							<div class="tip-error" id="birthdayVal"></div>
						</td>
						<th>婚姻状况：</th>
						<td>
							<select id="merUserIsMarriedVer" name="merUserIsMarriedVer"></select>
							<div class="tip-error" id="isMarriedVal"></div>
						</td>
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
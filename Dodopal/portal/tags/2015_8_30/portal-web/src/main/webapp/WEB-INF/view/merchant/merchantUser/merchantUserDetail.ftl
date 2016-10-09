<div class="con-main" id="merchantUserDetail" style="display:none;"> 
	<div class="new-peo">
		<h3 class="tit-h3">创建新用户</h3>
		<div class="com-bor-box com-bor-box01">
			<h4 class="tit-h4">用户基本资料</h4>
			<form id="detailForm" action="#">
				<input type="hidden" id="merchantUserId" >
				<table class="base-table">
					<col width="120" />
					<col width="330" />
					<col width="120" />
					<tr>
						<th><span class="red">*</span>用户名：</th>
						<td>
							<input id="merUserName" type="text" class="com-input-txt w260" myPlaceholder="4-20位字符，支持字母、数字、“_”，首位为字母"  maxlength="20" /><br>
							<div class="tip-error" id="merUserNameVal" ></div>
						</td>
						<th>启用标识：</th>
						<td><ul class="ipt-list">
								<li>
									<label>
										<input type="radio" value="0" name="activate" checked="checked"  disabled/>
										启用</label>
								</li>
								<li>
									<label>
										<input  name="activate" value="1" type="radio"  disabled/>
										停用</label>
								</li>
							</ul></td>
					</tr>
					<tr id="passwordTR">
						<th><span class="red">*</span>密码：</th>
						<td>
							<input type="password" id="merUserPWD" class="com-input-txt w260" autocomplete="off" myPlaceholder="6-20位字符，支持数字、字母及符号"   maxlength="20"/>
							<div class="tip-error" id="merUserPWDVal"></div>
						</td>
						<th><span class="red">*</span>确认密码：</th>
						<td>
							<input type="password" id="merUserPWDConfirm" class="com-input-txt w260" autocomplete="off" myPlaceholder="请再次输入密码"  maxlength="20" />
							<div class="tip-error" id="merUserPWDConfirmVal"></div>
						</td>
					</tr>
					<tr>
						<th><span class="red">*</span>手机号码：</th>
						<td>
							<input type="text" id="merUserMobile" class="com-input-txt w260" myPlaceholder="输入的手机号码可作为登录名"  maxlength="11" />
							<div class="tip-error" id="merUserMobileVal"></div>
						</td>
						<th><span class="red">*</span>真实姓名：</th>
						<td>
							<input type="text" id="merUserNickName" class="com-input-txt w260" myPlaceholder="2-20字符，支持中文、英文"  maxlength="20" />
							<div class="tip-error" id="merUserNickNameVal"></div>
						</td>
					</tr>
					<tr>
						<th>证件类型：</th>
						<td>
							<select id="merUserIdentityType" name="merUserIdentityType"></select>
							<div class="tip-error" id="merUserIdentityTypeVal"></div>
						</td>
						<th>证件号码：</th>
						<td>
							<input id="merUserIdentityNumber" type="text" class="com-input-txt w260" myPlaceholder="请正确输入证件号码"  maxlength="20"/>
							<div class="tip-error" id="merUserIdentityNumberVal"></div>
						</td>
					</tr>
					<tr>
						<th>固定电话：</th>
						<td>
							<input type="tel" id="merUserTelephone" class="com-input-txt w260" myPlaceholder="如：021-62382888"  maxlength="20" />
							<div class="tip-error" id="merUserTelephoneVal"></div>
						</td>
						<th>电子邮箱：</th>
						<td>
							<input type="text" id="merUserEmail" class="com-input-txt w260" myPlaceholder="格式：yyyy@xxx.com"  maxlength="60" />
							<div class="tip-error" id="merUserEmailVal"></div>
						</td>
					</tr>
					<tr>
						<th>备注：</th>
						<td>
							<textarea id="merUserRemark" class="area-w268" maxlength="200" onkeydown="checkMaxlength(this,200);" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"></textarea>
						</td>
						<th></th>
						<td></td>
					</tr>
				</table>
				
				<div class="des-line" style="margin-top:30px;"></div>
				
				<table class="base-table">
					<col width="120" />
					<#if sessionUser.merType == '16'>
					<tr>
						<th>管辖部门</th>
						<td>
						<ul class="bumen-list" id="departmentList">
								<li>
									<label>
										<input type="checkbox" />
										技术部</label>
								</li>
								<li>
									<label>
										<input type="checkbox" />
										财务部</label>
								</li>
							</ul>
							</td>
					</tr>
					</#if>
					<tr>
						<th></th>
						<td class="a-center">
						<div class="btn-box">
							<input type="submit" class="orange-btn-h32" onclick="return saveMerchantUser();" value="保存" />
							<input type="button" value="取消" class="orange-btn-text32" onclick="cancelEdit('merchantUserDetail');" />
						</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	</div>
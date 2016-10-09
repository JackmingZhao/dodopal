<div class="con-main" id="merchantUserDetailView"  style="display:none;">
	<h3 class="tit-h3">用户详情</h3>
	<div class="com-bor-box com-bor-box01">
			<h4 class="tit-h4">用户基本资料</h4>
			<table class="base-table">
				<col width="140" />
				<col width="330" />
				<col width="120" />
				<tr>
					<th>用户名：</th>
					<td><span id="merUserNameSpan"></span></td>
					<th>启用标识：</th>
					<td><span id="activateSpan"></span></td>
				</tr>
				<tr>
					<th>手机号码：</th>
					<td><span id="merUserMobileSpan"></span></td>
					<th>真实姓名：</th>
					<td><span id="merUserNickNameSpan"></span></td>
				</tr>
				<tr>
					<th>证件类型：</th>
					<td><span id="merUserIdentityTypeSpan"></span></td>
					<th>证件号码：</th>
					<td><span id="merUserIdentityNumberSpan"></span></td>
				</tr>
				<tr>
					<th>固定电话：</th>
					<td><span id="merUserTelephoneSpan"></span></td>
					<th>电子邮箱：</th>
					<td><span id="merUserEmailSpan"></span></td>
				</tr>
				<#if sessionUser.merType == '16'>
				<tr>
					<th>管辖部门：</th>
					<td><span id="merGroupDeptNameListSpan"></span></td>
				</tr>
				</#if>
				<tr>
					<th>备注：</th>
					<td style="word-break:break-all"><span class="remark" id="merUserRemarkSpan"></span></td>
				</tr>
				<tr>
					<td colspan="4" class="a-center"><a href="javascript:void(0);" id="cancelViewBtn" onclick="cancelView('merchantUser');" class="orange-btn-h32">返回</a></td>
				</tr>
			</table>
			<div class="des-line" id="resetTableDivider"></div>
			
			<form id="merUserResetForm">
			<table class="base-table" id="resetTable">
				<col width="45%" />
				<col width="55%" />
				<tr>
					<th>新密码：</th>
					<td>
						<input type="password" id="newPassword" value="" class="com-input-txt w145" autocomplete="off" maxlength="20" required="true" myPlaceholder="6-20位字符，支持数字、字母及符号" /><br>
						<div class="tip-error" id="newPasswordVal" ></div>	
					</td>
				</tr>
				<tr>
					<th>确认密码：</th>
					<td>
						<input type="password" value="" id="newPasswordConfirm" class="com-input-txt w145" autocomplete="off" maxlength="20" required="true" myPlaceholder="请再次输入密码"  /><br>
						<div class="tip-error" id="newPasswordConfirmVal" ></div>	
					</td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td>
					<div class="btn-box">
						<input type="submit" class="orange-btn-h32" value="保存"  onclick="return saveResetPassword();" maxlength="20" />
						<input type="button" value="取消" class="orange-btn-text32" onclick="cancelResetPwd();" maxlength="20" />
					</div>
					</td>
				</tr>
			</table>
			<input type="hidden" id="merchantUserSpanId" >
			</form>
			
	</div>
	</div>
</div>


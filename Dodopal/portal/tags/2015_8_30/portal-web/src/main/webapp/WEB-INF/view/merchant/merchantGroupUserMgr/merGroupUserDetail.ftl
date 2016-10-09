<div class="con-main" id="merGroupUserDetail" style="display:none;">
	<h3 class="tit-h3">人员信息</h3>
	<div class="com-bor-box com-bor-box01">
	<form id="detailForm" action="#">
		<input type="hidden" id="merGroupUserId" >
		
		<table class="base-table">
			<col width="158" />
			<tr>
				<th><span class="red">*</span>部门名称：</th>
				<td>
					<select id="depId" name="depId" class="com-input-txt w260"></select>
					<div class="tip-error" id="depIdVal"></div>
				</td>
				<th><span class="red">*</span>姓名：</th>
				<td>
					<input type="text" value="" class="com-input-txt w260" myPlaceholder="支持中英文，长度2-20个字符" id="gpUserName" maxlength="20" />
					<div class="tip-error" id="gpUserNameVal"></div>
				</td>
			</tr>
			<tr>
				<th>公交卡号：</th>
				<td>
					<input type="text" value="" class="com-input-txt w260" myPlaceholder="只允许输入数字，长度小于20个字符"  required="true" id="cardCode" maxlength="20" />
					<a  href="#" class="blue-link">获取</a>
					<div class="tip-error" id="cardCodeVal"></div>
				</td>
				<th>卡类型：</th>
				<td><input type="text" value="" class="com-input-txt w260" required="true" id="cardType" disabled /></td>
			</tr>
			<tr>
				<th><span class="red">*</span>充值金额：</th>
				<td>
					<input type="text" value="" class="com-input-txt w260" myPlaceholder="必须是10的倍数" maxlength="4" required="true" id="rechargeAmount" />
					<div class="tip-error" id="rechargeAmountVal"></div>
				</td>
				<th>充值方式：</th>
				<td>
					<select id="rechargeWay" name="rechargeWay" disabled >
						<option value="0">固定充钱包</option>
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>手机号码：</th>
				<td>
					<input type="text" value="" class="com-input-txt w260" myPlaceholder="输入手机号" required="true" id="mobiltle" maxlength="11" />
					<div class="tip-error" id="mobiltleVal"></div>
				</td>
				<th>电话：</th>
				<td>
					<input type="text" value="" class="com-input-txt w260" myPlaceholder="输入电话"   id="phone" maxlength="20" />
					<div class="tip-error" id="phoneVal"></div>
				</td>
			</tr>
			<tr>
						<th>身份证号：</th>
						<td>
							<input type="text" value="" class="com-input-txt w260" myPlaceholder="人员身份证号"  id="identityNum"  maxlength="18"/>
							<div class="tip-error" id="identityNumVal"></div>
						</td>
						<th>入职日期：</th>
						<td>
							<input type="text" value="" class="com-input-txt w260"  id="employeeDate" readonly="readonly" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							<div class="tip-error" id="employeeDateVal"></div>
						</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>
				<textarea class="area-w268" id="remark" maxlength="200" onkeydown="checkMaxlength(this,200);" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"></textarea>
				</td>
				<th id="statusTH">状态：</th>
				<td id="statusTD">
					<ul class="ipt-list">
						<li>
							<label>
								<input type="radio" value="0" name="empType" checked="checked" />
								在职</label>
						</li>
						<li>
							<label>
								<input  name="empType" value="1" type="radio" />
								离职</label>
						</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="a-center">
					<div class="btn-box">
						<input type="submit" class="orange-btn-h32" value="保存"  onclick="return saveMerGroupUser();"/>
						<input type="button" value="取消" class="orange-btn-text32" onclick="cancelEdit('merGroupUser');" />
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
	</div>
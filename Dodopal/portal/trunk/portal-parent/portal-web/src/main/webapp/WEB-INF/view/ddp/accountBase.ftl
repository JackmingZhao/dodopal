<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>基本信息</title>
<#include "../include.ftl"/>
<!-- InstanceEndEditable -->
<!--<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" /> -->
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<!-- Mydate 97 css-->
<link rel='stylesheet' type='text/css' href='${scriptUrl}/common/datepicker/skin/default/datepicker.css' />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'></script>
<!-- datepicker-->
<script type="text/javascript" src="${scriptUrl}/common/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="../js/ddp/accountSet.js"></script>
<script type="text/javascript" src="../js/portalValidationHandler.js"></script>

</head>

<body>

<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
<div class="account-setup mb30">
	<#include "../accountNav.ftl"/>
	<div class="account-box" id="accountBasicInfo">
		<div class="clearfix" id="viewAccountSetInfo">
		<h4>账户资料</h4>
		<input type="hidden" value="${(account.id)!}" id="id" />
		<ul class="account-info">
			<li>账户类型：${(fundType)!}</li>
		</ul>
		<#if "99" != merType>
			<h4 >商户资料</h4>
			<ul class="account-info">
				<li>业务城市：${(sessionUser.cityName)!}</li>
				<input id="sessionCity" name="sessionCity" value="${(sessionUser.cityName)!}" type="hidden">
			</ul>
			<#else>
			<h4 >个人资料</h4>
			<ul class="account-info">
				<li id="sessionCity" name="sessionCity">业务城市：${(sessionUser.cityName)!}</li>
				<input id="sessionCity" name="sessionCity" value="${(sessionUser.cityName)!}" type="hidden">
			</ul>
		</#if>
		<#if account.merUserNickName?? && account.merUserEmail?? && account.merUserSexView?? && account.merUserIdentityTypeView??  && account.merUserAdds?? && account.merUserIdentityNumber??>
			<#else>
			<div class="notes-box"><span class="bor-yellow-notes">请完善您的个人资料</span></div>
		</#if>
		<h4>联系人资料</h4>
			<form>
				<table cellpadding="0" cellspacing="0" summary="" class="account-table">
					<col width="30%" />
					<tr>
						<th>真实姓名：</th>
						<td>
							<span id="merUserNickNameSpan">${(account.merUserNickName)!}</span>
						</td>
					</tr>
					<tr>
						<th>出生年月：</th>
						<td>
							<span id="merUserNickNameSpan">${(account.birthday)!}</span>
						</td>
					</tr>
					<tr>
						<th>学历：</th>
						<td>
							<span id="merUserNickNameSpan">${(account.educationView)!}</span>
						</td>
					</tr>
					<tr>
						<th>婚否：</th>
						<td>
							<span id="merUserNickNameSpan">${(account.isMarriedView)!}</span>
						</td>
					</tr>
					<tr>
						<th>年收入：</th>
						<td>
							<span id="merUserNickNameSpan">
								<#if (account.incomeView) != ''>
									${account.incomeView}元
								</#if>
							</span>
						</td>
					</tr>
					<tr>
						<th>电子邮箱：</th>
						<td>
							<span id="merUserEmailSpan">${(account.merUserEmail)!}</span>
						</td>
					</tr>
					<tr>
						<th>性别：</th>
						<td>
							<span id="merUserSexSpan">${(account.merUserSexView)!}</span>
						</td>
					</tr>
					<tr>
						<th>证件类型：</th>
						<td>
							<span id="merUserIdentityTypeSpan">${(account.merUserIdentityTypeView)!}</span>
						</td>
					</tr>
					<tr>
						<th>证件号码：</th>
						<td>
							<span id="merUserIdentityNumberSpan">${(account.merUserIdentityNumber)!}</span>
						</td>
					</tr>
					<tr>
	                <th>省市：</th>
	                <td>
	                	<span id="merUserProNameSpan">
	                	<#if (account.merUserPro)??>
									${account.merUserProName}
						</#if>
	                	</span>
	                	<span id="merUserCityNameSpan">
	                	<#if (account.merUserCity)?? >
									${account.merUserCityName}
						</#if>
	                	</span>
	                </td>
                </tr>
					<tr>
						<th>详细地址：</th>
						<td>
							<span id="merUserAddsSpan">${(account.merUserAdds?html)!}</span>
						</td>
					</tr>
					<tr>
				<td id="area">
				</td>
				</tr>
				<tr>
					<td class="a-center" colspan="4">
	  						<div id="editButton">
	  							<input value="编辑" class="orange-btn-h32" style="color:#fff;" type="button" onclick="toEdit();">
	  						</div>
  						</td>
				</tr>
				</table>
				<img class="safe-img" src="${styleUrl}/portal/css/images/safe_img.png" />
			</form>
		</div>
		<div id="editAccountSetInfo" style="display:none" class="clearfix">
				<form>
				<table cellpadding="0" cellspacing="0" summary="" class="account-table">
					<col width="30%" />
					<#if "99" != merType>
					<tr>
						<th>业务城市：</th>
						<td><select name="merChantCity" id="merChantCity">
						</select>
						<div class="tip-error" id="merChantCityWarn"></div>
						</td>
					</tr>
					<#else>
						<tr>
							<th>业务城市：</th>
							<td><select name="merChantCity" id="merChantCity">
							</select>
							<div class="tip-error" id="merChantCityWarn"></div>
							</td>
					</tr>
					</#if>
					<tr>
						<th>真实姓名：</th>
						<td>
						<input type="text" maxlength="20" class="com-input-txt w260" onfocus="checkLinkUser(false);" id="merUserNickName" value="${(account.merUserNickName)!}" myPlaceholder="2-20位字符，可由中文或英文组成"/>
						<div class="tip-error" id="merUserNickNameWarn" ></div>
						</td>
					</tr>
					<tr>
						<th>出生年月：</th>
						<td>
							<input id="birthday" name="birthday" class="com-input-txt w260" type="text" onfocus="WdatePicker({skin:'whyGreen',minDate:'1900-01-01',maxDate:'%y-%M-%d',readOnly:true})"/>
						</td>
					</tr>
					<tr>
						<th>学历：</th>
						<td>
							<select name="education" id="education">
								<option>－－ 选择学历 －－</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>婚否：</th>
						<td>
							<select name="isMarried" id="isMarried">
								<option>－－ 选择婚姻 －－</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>年收入：</th>
						<td>
							<input type='text' name="income" id="income" class="com-input-txt w260" />元
						</td>
					</tr>
					<tr>
						<th>电子邮箱：</th>
						<td>
						<input type="text" maxlength="60" class="com-input-txt w260" onfocus="checkEmail(false);" id="merUserEmail" value="${(account.merUserEmail)!}" myPlaceholder="请正确输入邮箱格式"/>
						<div class="tip-error" id="merUserEmailWarn" ></div>
						</td>
					</tr>
					<tr>
						<th>性别：</th>
						<td><label class="mr10">
								<input type="radio" name="merUserSex" id="merUserSex" value="0"/>男</label>
							<label>
								<input type="radio" name="merUserSex" id="merUserSex" value="1"/>女</label>
						</td>
					</tr>
					<tr>
						<th>证件类型：</th>
						<td><select name="merUserIdentityType" id="merUserIdentityType">
							<option>－－ 证件类型 －－</option>
						</select>
						<div class="tip-error" id="merUserIdentityTypeWarn"></div>
						</td>
					</tr>
					<tr>
						<th>证件号码：</th>
						<td>
						<input type="text" maxlength="20" class="com-input-txt w260" id="merUserIdentityNumber" onfocus="checkIdentityNumber(false,'merUserIdentityType','merUserIdentityNumber','merUserIdentityNumberWarn');" name="merUserIdentityNumber" value="${(account.merUserIdentityNumber)!}" myPlaceholder="请输入正确的证件号码"/>
						<div class="tip-error" id="merUserIdentityNumberWarn"></div>
						</td>
					</tr>
					<tr>
	                <th>省市：</th>
	                <td>
	                	<span id="merUserProNameSpanG">
	                	<#if (account.merUserProName)??>
									${account.merUserProName}
						</#if>
	                	</span>
	                	<span id="merUserCityNameSpanG">
	                	<#if (account.merUserCityName)??>
									${account.merUserCityName}
						</#if>
	                	</span>
	                </td>
                </tr>
					<tr>
						<th>详细地址：</th>
						<td>
						<input type="text" maxlength="200" id="merUserAdds" name="merUserAdds" class="com-input-txt w260" value="${(account.merUserAdds)!}" myPlaceholder="请输入正确的地址"/>
						<div class="tip-error" id="merUserAddsWarn"></div>
						</td>
					</tr>
					<tr>
					<td id="area">
					</td>
					</tr>
					<tr>
						<td class="a-center" colspan="4">
								<input type="button" onclick="updateInfo();" style="color:#fff;" value="保存" class="orange-btn-h32">
								<input type="button" onclick="closeEdit();" style="color:#E47F11;" value="取消"  class="orange-btn-text32">
	  					</td>
					</tr>
				</table>
				<dl class="safe-explain">
					<img class="safe-img" src="${styleUrl}/portal/css/images/safe_img.png" />
				</dl>
			</form>
		</div>
	</div>
  </div>
</div>
<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd -->
</html>

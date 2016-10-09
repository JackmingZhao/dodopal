<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "../include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>基本信息</title>
<!-- InstanceEndEditable -->
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'></script>
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
			<li>账户类型：资金账户</li>
		</ul>
		<#if "99" != merType>
			<h4 >商户资料</h4>
			<ul class="account-info">
				<li>业务城市：${merCity}</li>
			</ul>
		</#if>
		<div class="notes-box"><span class="bor-yellow-notes">请完善您的个人资料</span></div>
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
						<th>详细地址：</th>
						<td>
							<span id="merUserAddsSpan">${(account.merUserAdds)!}</span>
						</td>
					</tr>
					<tr>
				<td id="area">
				</td>
				</tr>
				<tr>
					<td class="a-center" colspan="4">
	  						<div id="editButton">
	  							<input value="编辑" class="orange-btn-h32"  type="button" onclick="toEdit();">
	  						</div>
  						</td>
				</tr>
				</table>
				<dl class="safe-explain">
					<dt>完善个人资料	保护账户安全</dt>
					<dd>
						<ul>
							<li>1. 分配额度时，可以选择多个网点进行分配操作。</li>
							<li>2. 点击按钮，弹出页面上显示的网点信息。<br />该部分内容只显示处于启动状态的直营连锁网点。</li>
						</ul>
					</dd>
				</dl>
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
					</#if>
					<tr>
						<th>真实姓名：</th>
						<td>
						<input type="text" class="com-input-txt w260" onfocus="checkLinkUser(false);" id="merUserNickName" value="${(account.merUserNickName)!}" myPlaceholder="2-20位字符，可由中文或英文组成"/>
						<div class="tip-error" id="merUserNickNameWarn" ></div>
						</td>
					</tr>
					<tr>
						<th>电子邮箱：</th>
						<td>
						<input type="text" class="com-input-txt w260" onfocus="checkEmail(false);" id="merUserEmail" value="${(account.merUserEmail)!}" myPlaceholder="请正确输入邮箱格式"/>
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
						<input type="text" class="com-input-txt w260" id="merUserIdentityNumber" onfocus="checkIdentityNumber(false,'merUserIdentityType','merUserIdentityNumber','merUserIdentityNumberWarn');" name="merUserIdentityNumber" value="${(account.merUserIdentityNumber)!}" myPlaceholder="请输入正确的证件号码"/>
						<div class="tip-error" id="merUserIdentityNumberWarn"></div>
						</td>
					</tr>
					<tr>
						<th>详细地址：</th>
						<td>
						<p><input type="text" id="merUserAdds" name="merUserAdds" class="com-input-txt w260" value="${(account.merUserAdds)!}" myPlaceholder="请输入正确的地址"/></p>
						<div class="tip-error" id="merUserAddsWarn"></div>
						</td>
					</tr>
					<tr>
					<td id="area">
					</td>
					</tr>
					<tr>
						<td class="a-center" colspan="4">
								<input type="button" onclick="updateInfo();" value="保存" class="orange-btn-h32">
								<input type="button" onclick="closeEdit();" value="取消"  class="orange-btn-h32">
	  					</td>
					</tr>
				</table>
				<dl class="safe-explain">
					<dt>完善个人资料	保护账户安全</dt>
					<dd>
						<ul>
							<li>1. 分配额度时，可以选择多个网点进行分配操作。</li>
							<li>2. 点击按钮，弹出页面上显示的网点信息。<br />该部分内容只显示处于启动状态的直营连锁网点。</li>
						</ul>
					</dd>
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

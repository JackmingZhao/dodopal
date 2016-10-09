<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<#include "../include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>安全设置</title>
<#include "../header.ftl"/>
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='${base}/js/common/placeholders.js'></script>
<script src="../js/prvd/supplierSecure.js" type="text/javascript"></script>
</head>
<body>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="account-setup mb30">
		<div class="account-box">
			<div class="ehs-box" >
				<div class="boxl" id="editMobileImg">
					<p>手机号码<a href="javascript:;" class="img01" js="editImg" id="img01"></a></p>
				</div>
				<div class="boxr"><a href="javascript:void(0);"  js="editOne" onClick="editMobile()" class="orange-btn-h26" id="editMobileButton">修改</a>
					<form action="/" id='merUserMobileForm'>
						<input type="hidden" id="merUserMobile" name="merUserMobile" value="${(sessionUser.merUserMobile)!}"/>
						<table class="base-table" style="display: none;" id="todayMobileView">
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>当前手机号码：</th>
									<td><input type="text" class="com-input-txt w250" myPlaceholder="输入您的当前手机号码" id="todayMobile" name="todayMobile"  maxlength="11"  onKeyup="phoneKeyup();"  autocomplete="off"/>
										<div class="tip-error tip-red-error" id="todayMobileERR"></div>
										</td>
								</tr>
								<tr>
									<th>验证码：</th>
									<td>
										<input type="text"  class="com-input-txt w145"  style="width:124px;" id="todayMobileVerificationCode" name="todayMobileVerificationCode"  maxlength="8"/>&nbsp;
										<input id="todayMobileButton" onclick="todayMobileVerificationCodeButton();" name="todayMobileButton" type="button" class="wd-orageBtn2" value="获取验证码"  style=" cursor:pointer;vertical-align: middle;" />
										<span id="todayMobileSerialNumber" style="display: none;"></span>
										<span id="todayMobileSerialNumber2" style="display: none;"></span>
										<div class="tip-error tip-red-error"></div></td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="下一步" class="orange-btn-h32" onclick="showNewestMobileView()">
										</div></td>
								</tr>
							</tbody>
						</table>
						<table class="base-table" style="display: none;" id ="newestMobileView" >
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>新手机号码：</th>
									<td><input type="text" class="com-input-txt w250" placeholder="输入您的新手机号码" id="newestMobile" name="newestMobile"  maxlength="11"  onKeyup="newPhoneKeyup();"  autocomplete="off">
										<div class="tip-error tip-red-error" id="newestMobileERR"></div></td>
								</tr>
								<tr>
									<th>验证码：</th>
									<td>
										<input type="text"  class="com-input-txt w145"  style="width:124px;" id="newestMobileVerificationCode" name="newestMobileVerificationCode"  maxlength="8"/>&nbsp;
										<input id="newMerUserMobileButton" onclick="newestMobileVerificationCodeButton();" name="button" type="button" class="wd-orageBtn2" value="获取验证码"  style=" cursor:pointer;vertical-align: middle;" />
										<span id="serialNumberNewestMobile" style="display: none;"></span>
										<span id="serialNumberNewestMobile2" style="display: none;"></span>
										<div class="tip-error tip-red-error"></div></td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="确认" class="orange-btn-h32" onclick="newestMobileButton()">
										</div></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<div class="ehs-box">
				<div class="boxl">
					<p>登录密码<a href="javascript:;" class="img02" js="editImg" id="img02"></a></p>
				</div>
				<div class="boxr"><a href="javascript:;" js="editOne" class="orange-btn-h26" onClick="editMerUserPwdView()" id="editMerUserPwdViewButton">设置</a>
					<form action="/">
						<table class="base-table" style="display:none;" id="editMerUserPwdTable">
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>当前密码：</th>
									<td><input type="password" class="com-input-txt w250" myPlaceholder="输入您的当前密码" id="merUserPWD" name="merUserPWD"  maxlength="20"  onfocus="validateMerUserPWD(false);">
										<div class="tip-error tip-red-error" id="merUserPWDERR"></div>
										<div class="tip-error tip-red-error" id="merUserPWDERR2"></div>
										</td>
								</tr>
								<tr>
									<th>新密码：</th>
									<td><input type="password" class="com-input-txt w250" myPlaceholder="设置您的新密码" id="merUserUpPWD" name="merUserUpPWD" maxlength="20" onfocus="validateMerUserUpPWD(false);">
										<div class="tip-error tip-red-error" id="merUserUpPWDERR"></div>
										</td>
								</tr>
								<tr>
									<th>确认新密码：</th>
									<td><input type="password" class="com-input-txt w250" myPlaceholder="请再输一次您的新密码" id="merUserUpPWDTwo" name="merUserUpPWDTwo" maxlength="20" onfocus="validateMerUserUpPWDTwo(false);">
										<div class="tip-error tip-red-error" id="merUserUpPWDTwoERR"></div>
										</td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="确认" class="orange-btn-h32" onclick="updatemerUserUpPWD()">
										</div></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<#if sessionUser.merType =='18'>
			<div class="ehs-box">
				<div class="boxl">
					<p>签名密钥<a href="javascript:;" class="img03" js="editImg" id="img03"></a></p>
				</div>
				<div class="boxr"><a href="javascript:;" js="editOne" class="orange-btn-h26" id="merMD5PayPwdViewButton" onclick="merMD5PayPwdView()">设置</a>
					<form action="/">
						<table class="base-table" style="display: none;" id="merMD5PayPwdTable"> 
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>原签名密钥：</th>
									<td><span id="merMD5PayPwdSpan"></span></td>
								</tr>
								<tr>
									<th>签名密钥：</th>
									<td><input type="text" class="com-input-txt w250" placeholder="签名密钥" id="merMD5PayPwd">
										<div class="tip-error tip-red-error"></div></td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="确认" class="orange-btn-h32" onclick="upMerMD5PayPwd()">
										</div></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<div class="ehs-box">
				<div class="boxl">
					<p>验签密钥<a href="javascript:;" class="img03" js="editImg" id="img031"></a></p>
				</div>
				<div class="boxr"><a href="javascript:;" js="editOne" class="orange-btn-h26" id="merMD5BackPayPWDViewButton" onclick="merMD5BackPayPWDView()">设置</a>
					<form action="/">
						<table class="base-table" style="display: none;" id="merMD5BackPayPWDTable">
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>原先验签密钥：</th>
									<td><span id="merMD5BackPayPWDSpan"></span></td>
								</tr>
								<tr>
									<th>验签密钥：</th>
									<td><input type="text" class="com-input-txt w250" placeholder="验签密钥" id="merMD5BackPayPWD"> 
										<div class="tip-error tip-red-error"></div></td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="确认" class="orange-btn-h32" onclick="upmerMD5BackPayPWD()">
										</div></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			</#if>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

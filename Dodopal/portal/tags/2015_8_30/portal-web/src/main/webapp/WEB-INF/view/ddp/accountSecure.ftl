<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<#include "../include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>安全设置</title>
<!-- InstanceEndEditable -->
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<script src="../js/ddp/accountSecure.js" type="text/javascript"></script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="account-setup mb30">
		<#include "../accountNav.ftl"/>
		<div class="account-box">
			<div class="ehs-box" >
				<div class="boxl" id="editMobileImg">
					<p>手机号码<a href="javascript:;" class="img01" js="editImg" id="img01"></a></p>
				</div>
				<div class="boxr"><a href="javascript:void(0);"  js="editOne" onClick="editMobile()" class="gray-btn-h26" id="editMobileButton">修改</a>
					<form action="/">
						<table class="base-table" style="display: none;" id="todayMobileView">
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>当前手机号码：</th>
									<td><input type="text" class="com-input-txt w250" placeholder="输入您的新手机号码" id="todayMobile" name="todayMobile"  maxlength="11" onfocus="validateTodayMobile(false);">
										<div class="tip-error tip-red-error" id="todayMobileERR"></div></td>
								</tr>
								<tr>
									<th>验证码：</th>
									<td><input type="text" class="com-input-txt w145" placeholder="输入验证码" id="todayMobileVerificationCode" name="todayMobileVerificationCode" maxlength="8">
										<input type="button" class="btn-orange"  value="获取验证码" onclick="todayMobileVerificationCodeButton()"><span id="serialNumberTodayMobile"></span>
										<div class="tip-error tip-red-error"></div></td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="下一步" class="pop-borange mr20" onclick="showNewestMobileView()">
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
									<td><input type="text" class="com-input-txt w250" placeholder="输入您的新手机号码" id="newestMobile" name="newestMobile"  maxlength="11" onfocus="validateNewestMobile(false);">
										<div class="tip-error tip-red-error" id="newestMobileERR"></div></td>
								</tr>
								<tr>
									<th>验证码：</th>
									<td><input type="text" class="com-input-txt w145" placeholder="输入验证码" id="newestMobileVerificationCode" name="newestMobileVerificationCode" maxlength="8">
										<input type="button" class="btn-orange"  value="获取验证码" onclick="newestMobileVerificationCodeButton()"><span id="serialNumberNewestMobile"></span>
										<div class="tip-error tip-red-error"></div></td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="确认" class="pop-borange mr20" onclick="newestMobileButton()">
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
				<div class="boxr"><a href="javascript:;" js="editOne" class="gray-btn-h26" onClick="editMerUserPwdView()" id="editMerUserPwdViewButton">设置</a>
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
											<input type="button" value="确认" class="pop-borange mr20" onclick="updatemerUserUpPWD()">
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
				<div class="boxr"><a href="javascript:;" js="editOne" class="gray-btn-h26" id="merMD5PayPwdViewButton" onclick="merMD5PayPwdView()">设置</a>
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
											<input type="button" value="确认" class="pop-borange mr20" onclick="upMerMD5PayPwd()">
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
				<div class="boxr"><a href="javascript:;" js="editOne" class="gray-btn-h26" id="merMD5BackPayPWDViewButton" onclick="merMD5BackPayPWDView()">设置</a>
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
											<input type="button" value="确认" class="pop-borange mr20" onclick="upmerMD5BackPayPWD()">
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
<script type="text/javascript">
$(document).ready(function(e) {
		$('[js="editOne"]').hover(function(){
			$(this).attr('class','orange-btn-h26');
			$(this).closest('.ehs-box').addClass('ehs-box-hover');
		},function(){
			$(this).attr('class','gray-btn-h26');
			$(this).closest('.ehs-box').removeClass('ehs-box-hover')
		});
		
			$('[js="editOne"]').click(function(){
			$(this).hide();	
			$(this).closest('.ehs-box').addClass('ehs-box-click');
		});
});
</script> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
	$('.header-nav ul li').click(function(){
		var i=$(this).index();
		$('.header-nav ul li a').removeClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show();
		$(this).find('a').addClass('on');
	});
	if($('.header-inr-nav ul li a').hasClass('cur')){
		var i=$('.cur').closest('ul').index();
		$('.header-nav ul li a').removeClass('on').eq(i).addClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show()
	};
});

function popclo(obj){
	$(obj).closest('.pop-win').hide();
}

$('.footer-navi .more a').click(function(){
	if($(this).hasClass('open')){
		$(this).removeClass('open');
		$('.footer-navi ul').height(60);
	}else{
		$(this).addClass('open');
		$('.footer-navi ul').removeAttr('style');
	};

});
</script>
</body>
<!-- InstanceEnd --></html>

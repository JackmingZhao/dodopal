//个人注册控制变量
var isMobile = false;
var isMobileCode = false;
var isPersonalUserValid = false;
var isPersonalUserPwdValid = false;
var isPersonalUserPwd2Valid = false;

// 商户注册 控制变量
var isMerNameValid = false;
var isMerLinkUserValid = false;
var isMerMobileValid = false;
var isMerKapValid = false;
var isMerMobileCodeValid = false;
var isAddressValid = false;
var isCityValid = false;
var isMerchantUserNameValid = false;
var isMerchantUserPwdValid = false;
var isMerchantUserPwd2Valid = false;

// 验证码倒计时控制变量
var intervalid; 
var waitingTime = 120;
$(function() {
	
	$("#tabs-up").click(function(){
		var flag1Display = $('#flag-1').css('display');
		var flag2Display = $('#flag-2').css('display');
		if(!(flag1Display == 'block' || flag2Display == 'block')) {
			$(".flag").css("display","none");
			$("#flag-1").css("display","block");
			$(this).css({'background-position':'-90px 0px','color':'#333'});
			$('.tab-nav').css({'background-position':'-166px 0px','color':'inherit'});
			$("#flag-1").find('input[type != "button"]').val('');
			$("#flag-2").find('input[type != "button"]').val('');
			
			$('.flag').find('span').hide();
			$('.img-code').show();
			
			$('#merUserCaptchaDiv').show();
			$('#merUserMobileCaptchaDiv').hide();
			$('#merUserSerialNumber').html("").hide();
			
			$("#requestAuthCodeBtn").removeAttr("disabled");
			$('#merUserMobile').removeAttr("disabled");
			$('#requestAuthCodeBtn').val("获取验证码");
			clearInterval(intervalid);
			
			isMobile = false;
			isMobileCode = false;
			isPersonalUserValid = false;
			isPersonalUserPwdValid = false;
			isPersonalUserPwd2Valid = false;
		}
	});
	$("#tabs-down").click(function(){
		var flag3Display = $('#flag-3').css('display');
		var flag4Display = $('#flag-4').css('display');
		if(!(flag3Display == 'block' || flag4Display == 'block')) {
			$(".flag").css("display","none");
			$("#flag-3").css("display","block");
			$(this).css({'background-position':'-242px 0px','color':'#333'});
			$('.tab-nav-action').css({'background-position':'-14px 0px','color':'inherit'});
			$("#flag-3").find('input[type != "button"]').val('');
			$("#flag-4").find('input[type != "button"]').val('');
			$('.flag').find('span').hide();
			$('.img-code').show();
			
			$('#merCaptchaDiv').show();
			$('#merMobileCaptchaDiv').hide();
			$('#merSerialNumber').html('').hide();
			
			$("#requestAuthCodeBtn2").removeAttr("disabled");
			$('#merLinkUserMobile').removeAttr("disabled");
			$('#requestAuthCodeBtn2').val("获取验证码");
			clearInterval(intervalid);
			
			isMerNameValid = false;
			isMerLinkUserValid = false;
			isMerMobileValid = false;
			isMerKapValid = false;
			isMerMobileCodeValid = false;
			isAddressValid = false;
//			isCityValid = false;
			isMerchantUserNameValid = false;
			isMerchantUserPwdValid = false;
			isMerchantUserPwd2Valid = false;
		}
	});
	$('#merUserCaptcha').on("keyup", function(event) {
		var captcha = $('#merUserCaptcha').val();
		if(captcha.length == 4) {
			validateMerUserCaptcha();
		}/* else if(captcha.length > 0){
			$.validationHandler(false, 'merUserCaptchaValidation', "验证码不正确，请重新输入");
		}*/
	}); 

	$('#merCaptcha').on("keyup", function(event) {
		var captcha = $('#merCaptcha').val();
		if(captcha.length == 4) {
			validateMerCaptcha();
		}/* else if(captcha.length > 0){
			$.validationHandler(false, 'merKaptchaValidation', "验证码不正确，请重新输入");
		}*/
	});  
	
	createAreaSelectWithSelectId("province","city", "validateProvince()");
	
	
	createAreaSelectWithSelectId("provinceG","cityG", "validateProvinceG()");
	
//	$('input').focusin(function(){
//		$(this).nextAll('span').hide();
//		$(this).nextAll('span.img-code').show();
//		$(this).nextAll('span:first').show();
//	});
//	$('input').focusout(function(){
//		$(this).nextAll('span:first').hide();
//		$(this).nextAll('span.img-code').show();
//	});
	
	
	// 个人注册第一步
	$('#merUserMobile').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merUserMobile').focusout(function(){
		$(this).nextAll('span:first').hide();
		validateMerUserMobile();
	});
	
	$('#merUserCaptcha').focusin(function(){
		$('#merUserCaptchaValidation').hide();
	});
	$('#merUserCaptcha').focusout(function(){
		validateMerUserCaptcha();
	});
	
	$('#mobileCheckCode').focusin(function(){
		$('#requestAuthCodeBtnValidation').hide();
	});
	$('#mobileCheckCode').focusout(function(){
		validateMerUserAuthCode();
	});
	
	
	// 个人注册第二步
	$('#merUserName').focusin(function(){
		$('#merUserNameTips').show();
		$('#merUserNameValidation').hide();
	});
	$('#merUserName').focusout(function(){
		$('#merUserNameTips').hide();
		validateMerUserName();
	});
	
	$('#merUserPWD').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merUserPWD').focusout(function(){
		$(this).nextAll('span:first').hide();
		validateMerUserPWD();
	});
	
	$('#merUserPWD2').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merUserPWD2').focusout(function(){
		$(this).nextAll('span:first').hide();
		validateMerUserPWD2();
	});
	
	// 商户注册第一步
	$('#merName').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merName').focusout(function(){
		validateMerName();
		$(this).nextAll('span:first').hide();
	});
	
	$('#merLinkUser').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merLinkUser').focusout(function(){
		validateMerLinkUser();
		$(this).nextAll('span:first').hide();
	});
	
	$('#merLinkUserMobile').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merLinkUserMobile').focusout(function(){
		validateMerLinkUserMobile();
		$(this).nextAll('span:first').hide();
	});
	
	$('#merCaptcha').focusin(function(){
		$('#merKaptchaValidation').hide();
	});
	$('#merCaptcha').focusout(function(){
		validateMerCaptcha();
	});
	
	$('#merLinkUserMobileCheckCode').focusin(function(){
		$('#merMobileCodeValidation').hide();
	});
	$('#merLinkUserMobileCheckCode').focusout(function(){
		validateMerLinkUserMobileCheckCode();
	});
	
	$('#address').focusin(function(){
		$('#addressValidation').hide();
		$("#addressTips").show();
	});
	$('#address').focusout(function(){
		validateAddress();
		$("#addressTips").hide();
	});
	
	// 商户注册第二步
	
	$('#merchantUserName').focusin(function(){
		$('#merchantUserNameValidation').hide();
		$('#merchantUserNameTips').show();
	});
	$('#merchantUserName').focusout(function(){
		validateMerchantUserName();
		$('#merchantUserNameTips').hide();
	});
	
	$('#merchantUserPWD').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merchantUserPWD').focusout(function(){
		validateMerchantUserPWD();
		$(this).nextAll('span:first').hide();
	});
	
	$('#merchantUserPWD2').focusin(function(){
		$(this).nextAll('span').hide();
		$(this).nextAll('span:first').show();
	});
	$('#merchantUserPWD2').focusout(function(){
		validateMerchantUserPWD2();
		$(this).nextAll('span:first').hide();
	});
});


/////////////////////////////////////////////////////// 个人注册开始 ////////////////////////////

//function synRegisterUserStep1Btn() {
//	var  registerUserStep1Check = $('#registerUserStep1Check').is(':checked');
//	if(registerUserStep1Check && isMobile && isMobileCode) {
//		$('#btn-p').removeClass().addClass('wd-btn wd-btn-1');
//	} else {
//		$('#btn-p').removeClass().addClass('wd-btn wd-btn-3');
//	}
//}

//function synRegisterUserStep2Btn() {
//	if(isPersonalUserValid && isPersonalUserPwdValid && isPersonalUserPwd2Valid) {
//		$('#personalRegisterBtn').removeClass().addClass('wd-btn wd-btn-1');
//	} else {
//		$('#personalRegisterBtn').removeClass().addClass('wd-btn wd-btn-3');
//	}
//}

function validateMerUserMobile() {
	// 校验手机号是否正确
	var mobile = getTrimValue($('#merUserMobile').val());
	if(isBlank(mobile)) {
		$('#requestAuthCodeBtn').removeClass().addClass('wd-orageBtn2');
		isMobile = false;
//		synRegisterUserStep1Btn();
		return isMobile;
	}
	isMobile = $.mobile($('#merUserMobile').val());
	if(isMobile) {
		// 检验是否唯一
		ddpAjaxCall({
			url : "checkMobileExist",
			data : mobile,
			successFn : function(data) {
				if(data.code == "000000" && (data.responseEntity == 'false' || data.responseEntity == false)) {
					$.validationHandler(true, 'merUserMobileValidation', "");
					$('#requestAuthCodeBtn').removeClass().addClass('wd-orageBtn');
					isMobile = true;
//					synRegisterUserStep1Btn();
				} else {
					isMobile = false;
//					synRegisterUserStep1Btn();
					$.validationHandler(false, 'merUserMobileValidation', data.message);
					$('#requestAuthCodeBtn').removeClass().addClass('wd-orageBtn2');
				}
			}
		});
	} else {
//		synRegisterUserStep1Btn();
		$.validationHandler(isMobile, 'merUserMobileValidation', '输入的手机号码格式有误');
		$('#requestAuthCodeBtn').removeClass().addClass('wd-orageBtn2');
	}
	return isMobile;
}


function validateMerUserAuthCode() {
	isMobileCode = $.validationHandler(isNotBlank($('#mobileCheckCode').val()), 'requestAuthCodeBtnValidation', '');
	if(isMobileCode) {
		var mobileData = {
				moblieNum : getTrimValue($('#merUserMobile').val()),
				dypwd : getTrimValue($('#mobileCheckCode').val())
		};
		ddpAjaxCall({
			url : "moblieCodeCheck",
			data : mobileData,
			successFn : function(data) {
				if(data.code == "000000") {
					isMobileCode = true;
//					synRegisterUserStep1Btn();
				} else {
					isMobileCode = false;
//					synRegisterUserStep1Btn();
					$.validationHandler(false, 'requestAuthCodeBtnValidation', generateResponseMsg(data));
				}
			}
		});
	} else {
//		synRegisterUserStep1Btn();
	}
}

function validateMerUserCaptcha() {
	var captcha =getTrimValue($('#merUserCaptcha').val());
	var isValid = $.validationHandler(isNotBlank(captcha), 'merUserCaptchaValidation', '');
	if(isValid) {
		isValid = $.validationHandler($.enNoKap(captcha), 'merUserCaptchaValidation', '验证码不正确，请重新输入');
	}
	if(isValid) {
		
		var url = $.base + "/checkCaptcha?captcha=" + captcha + "&captchaType=personRegister";
		ddpAjaxCall({
			async : false,
			url : url,
			successFn : function(data) {
				if(data.code == "000000" && (data.responseEntity == 'true' || data.responseEntity == true)) {
					$('#merUserCaptchaDiv').hide();
					$('#merUserMobileCaptchaDiv').show();
					$.validationHandler(true, 'merUserCaptchaValidation', '');
				} else {
					$.validationHandler(false, 'merUserCaptchaValidation', "验证码不正确，请重新输入");
					isValid = false;
				}
			}
		});
	}
}

function validateMerUserName() {
	var merUserName = getTrimValue($('#merUserName').val());
	isPersonalUserValid = isNotBlank(merUserName);
	if(isPersonalUserValid) {
		isPersonalUserValid = $.validationHandler($.azEnNoUs(merUserName, 4, 20), 'merUserNameValidation', "输入的用户名格式不正确");
		if(isPersonalUserValid) {
			// 检验用户名是否唯一
			ddpAjaxCall({
				url : "checkUserNameExist",
				data : merUserName,
				successFn : function(data) {
					if(data.code == "000000") {
						isPersonalUserValid = true;
//						synRegisterUserStep2Btn();
						$.validationHandler(true, 'merUserNameValidation', "");
					} else {
						$.validationHandler(false, 'merUserNameValidation', generateResponseMsg(data));
						isPersonalUserValid = false;
//						synRegisterUserStep2Btn();
					}
				}
			});
		}
	} else {
		isPersonalUserValid = false;
//		synRegisterUserStep2Btn();
		$.validationHandler(false, 'merUserNameValidation', '');
	}
}

function validateMerUserPWD() {
	var pwd = $('#merUserPWD').val();
	if(isBlank(pwd)) {
		isPersonalUserPwdValid = false;
		$.validationHandler(false, 'merUserPWDValidation', '');
	} else {
		var weakPassword = jQuery.isNumeric(pwd);
		if(weakPassword) {
			isPersonalUserPwdValid = false;
			$.validationHandler(false, 'merUserPWDValidation', '密码不支持纯数字，请您更换');
		} else {
			isPersonalUserPwdValid = $.password(pwd);
			if(isPersonalUserPwdValid) {
				validateMerUserPWD2();
				$.validationHandler(true, 'merUserPWDValidation', '');
			} else {
				$.validationHandler(false, 'merUserPWDValidation', '输入的密码格式不正确');
			}
		}
	}
//	synRegisterUserStep2Btn();	
}

function validateMerUserPWD2() {
	if(isNotBlank($('#merUserPWD2').val())) {
		isPersonalUserPwd2Valid = $.validationHandler(($('#merUserPWD2').val() == $('#merUserPWD').val()), 'merUserPWD2Validation', '两次输入密码不一致');
	} else {
		isPersonalUserPwd2Valid = false;
	}
//	synRegisterUserStep2Btn();
}

function registerUserStep1CheckClick() {
	var  registerUserStep1Check = $('#registerUserStep1Check').is(':checked');
	if(registerUserStep1Check) {
		$('#registerUserStep1CheckValidation').hide();
	} else {
		$('#registerUserStep1CheckValidation').show();
	}
//	synRegisterUserStep1Btn();
}

function registerUserStep1(){
	var  registerUserStep1Check = $('#registerUserStep1Check').is(':checked');
	if(registerUserStep1Check && isMobile && isMobileCode) {
		$(".flag").css("display","none");
		$("#flag-2").css("display","block");
	} else {
		if(!registerUserStep1Check) {
			$('#registerUserStep1CheckValidation').show();
		}
		if(!isMobile) {
			var mobile = getTrimValue($('#merUserMobile').val());
			if(isBlank(mobile)) {
				$.validationHandler(false, 'merUserMobileValidation', "请输入手机号码");
			}
		}
		
		var merUserCaptcha = getTrimValue($('#merUserCaptcha').val());
		if(isBlank(merUserCaptcha)) {
			$.validationHandler(false, 'merUserCaptchaValidation', '请输入验证码');
		} else if(!isMobileCode) {
			var mobileCode = getTrimValue($('#mobileCheckCode').val());
			if(isBlank(mobileCode)) {
				$.validationHandler(false, 'requestAuthCodeBtnValidation', "请输入短信验证码");
			}
		}
	}
}
//个人注册 
function registerUserStep2() {
	if(isPersonalUserValid && isPersonalUserPwdValid && isPersonalUserPwd2Valid) {
		var provinceG = getProvinceValue('provinceG');
		var cityG = getCityValue('cityG');
		var addressG = $('#addressG').val();
		if(isBlank(provinceG)&&isBlank(cityG)){
			$.validationHandler(false, 'addressCityValidationG', "请选择省份,城市");
			return;
		}
		if(isBlank(addressG)){
			$.validationHandler(false, 'addressTipsG', "请输入详细地址");
			return;
		}
		var psssWd = $('#merUserPWD').val();
		$('#merUserPWD').val(md5(md5(psssWd)));
		$('#merUserMobile2').val($('#merUserMobile').val());
		$('#userRegisterForm').submit();
	} else {
		if(!isPersonalUserValid) {
			var merUserName = getTrimValue($('#merUserName').val());
			if(isBlank(merUserName)) {
				$.validationHandler(false, 'merUserNameValidation', "请输入用户名");
			}
		}
		if(!isPersonalUserPwdValid) {
			var pwd = getTrimValue($('#merUserPWD').val());
			if(isBlank(pwd)) {
				$.validationHandler(false, 'merUserPWDValidation', "请输入密码");
			}
		}
		if(!isPersonalUserPwd2Valid) {
			var pwd2 = getTrimValue($('#merUserPWD2').val());
			if(isBlank(pwd2)) {
				$.validationHandler(false, 'merUserPWD2Validation', "请再次输入密码");
			}
		}
	}
}

function requestAuthCode() {
	if (isMobile) {
		var mobile = $.trim($("#merUserMobile").val());
		$("#requestAuthCodeBtn").attr("disabled", true);
		$("#merUserMobile").attr("disabled", true);
		waitingTime=120;
		intervalid = setInterval("isAuthCoded('merUserMobile', 'requestAuthCodeBtn')", 1000); 
		ddpAjaxCall({
			url : "sendNoCheck",
			data : mobile,
			successFn : function(data) {
				if (data.code == "000000") {
					$('#merUserSerialNumber').html("序号【" + data.responseEntity.pwdseq + "】").show();
				}
			}
		});
	} else {
		var mobile = getTrimValue($('#merUserMobile').val());
		if(isBlank(mobile)) {
			$.validationHandler(isMobile, 'merUserMobileValidation', '请输入手机号');
		}
	}
}

/////////////////////////////////////////////////////// 个人注册结束 ////////////////////////////


/////////////////////////////////////////////////////// 商户注册开始 ////////////////////////////
//function synRegisterMerStep1Btn() {
//	var  merRegisterStep1Check = $('#merRegisterStep1Check').is(':checked');
//	if(merRegisterStep1Check && isMerNameValid && isMerLinkUserValid && isMerMobileValid && isMerMobileCodeValid && isAddressValid) {
//		$('#btn-b').removeClass().addClass('wd-btn wd-btn-1');
//	} else {
//		$('#btn-b').removeClass().addClass('wd-btn wd-btn-3');
//	}
//}

//function synRegisterMerStep2Btn() {
//	if(isMerchantUserNameValid && isMerchantUserPwdValid && isMerchantUserPwd2Valid) {
//		$('#registerMerStep2Btn').removeClass().addClass('wd-btn wd-btn-1');
//	} else {
//		$('#registerMerStep2Btn').removeClass().addClass('wd-btn wd-btn-3');
//	}
//}


function validateMerName() {
	// 校验商户名称是否为空
	var merName = getTrimValue($('#merName').val());
	isMerNameValid = isNotBlank(merName);
	if(isMerNameValid) {
		isMerNameValid = $.validationHandler($.cnEnNo(merName, 1, 50), 'merNameValidation', "输入的商户名称格式不正确");
		if(isMerNameValid) {
			// 检验商户名称是否唯一
			ddpAjaxCall({
				url : "checkMerchantNameExist",
				data : merName,
				successFn : function(data) {
					if(data.code == "000000") {
						isMerNameValid = true;
						$.validationHandler(true, 'merNameValidation', "");
//						synRegisterMerStep1Btn();
					} else {
						$.validationHandler(false, 'merNameValidation', generateResponseMsg(data));
						isMerNameValid = false;
//						synRegisterMerStep1Btn();
					}
				}
			});
		}
	} else {
		$.validationHandler(false, 'merNameValidation', '');
	}
//	synRegisterMerStep1Btn();
}

function validateMerLinkUser() { // 可由2-20字符，可由中文或英文组成
	var linkUser = getTrimValue($('#merLinkUser').val());
	isMerLinkUserValid = $.validationHandler(isNotBlank(linkUser), 'merLinkUserValidation', '');
	if(isMerLinkUserValid) {
		isMerLinkUserValid = $.validationHandler($.enCn(linkUser, 2, 20), 'merLinkUserValidation', '输入的联系人格式不正确');
	}
//	synRegisterMerStep1Btn();
}

function validateMerLinkUserMobile() {
	// 校验手机号是否正确
	var mobile = getTrimValue($('#merLinkUserMobile').val());
	isMerMobileValid = isNotBlank(mobile);
	if(isMerMobileValid) {
		isMerMobileValid = $.validationHandler($.mobile(mobile), 'merLinkUserMobileValidation', "输入的手机号码格式不正确");
		// 检验是否唯一
		if(isMerMobileValid) {
			ddpAjaxCall({
				url : "checkMobileExist",
				data : mobile,
				successFn : function(data) {
					if(data.code == "000000") {
						isMerMobileValid = true;
						$.validationHandler(true, 'merLinkUserMobileValidation', "");
						$('#requestAuthCodeBtn2').removeClass().addClass('wd-orageBtn');
//						synRegisterMerStep1Btn();
					} else {
						isMerMobileValid = false;
						$.validationHandler(false, 'merLinkUserMobileValidation', generateResponseMsg(data));
						$('#requestAuthCodeBtn2').removeClass().addClass('wd-orageBtn3');
//						synRegisterMerStep1Btn();
					}
				}
			});
		} else {
			$('#requestAuthCodeBtn2').removeClass().addClass('wd-orageBtn3');
		}
	} else {
		$.validationHandler(isMobile, 'merLinkUserMobileValidation', '');
		$('#requestAuthCodeBtn2').removeClass().addClass('wd-orageBtn3');
	}
//	synRegisterMerStep1Btn();
}

function validateMerCaptcha(){
	var captcha =getTrimValue($('#merCaptcha').val());
	isMerKapValid = isNotBlank(captcha);
	if(isMerKapValid) {
		isMerKapValid = $.validationHandler($.enNoKap(captcha), 'merKaptchaValidation', '验证码不正确，请重新输入');
	}
	if(isMerKapValid) {
		var url = $.base + "/checkCaptcha?captcha=" + captcha + "&captchaType=merRegister";
		ddpAjaxCall({
			async : false,
			url : url,
			successFn : function(data) {
				if(data.code == "000000" && (data.responseEntity == 'true' || data.responseEntity == true)) {
					$('#merCaptchaDiv').hide();
					$('#merMobileCaptchaDiv').show();
					$.validationHandler(true, 'merKaptchaValidation', "");
				} else {
					$.validationHandler(false, 'merKaptchaValidation', generateResponseMsg(data));
					isMerKapValid = false;
				}
			}
		});
	}
}

function validateMerLinkUserMobileCheckCode(){
	var merMobileCode = getTrimValue($('#merLinkUserMobileCheckCode').val());
	isMerMobileCodeValid = $.validationHandler(isNotBlank(merMobileCode), 'merMobileCodeValidation', '');
	if(isMerMobileCodeValid) {
		var mobileData = {
				moblieNum : getTrimValue($('#merLinkUserMobile').val()),
				dypwd : merMobileCode
		};
		ddpAjaxCall({
			url : "moblieCodeCheck",
			data : mobileData,
			successFn : function(data) {
				if(data.code == "000000") {
					isMerMobileCodeValid = true;
					$.validationHandler(false, 'merMobileCodeValidation', '');
//					synRegisterMerStep1Btn();
				} else {
					isMerMobileCodeValid = false;
					$.validationHandler(false, 'merMobileCodeValidation', generateResponseMsg(data));
//					synRegisterMerStep1Btn();
				}
			}
		});
	}
//	synRegisterMerStep1Btn();
}

function validateAddress(){
	var address = getTrimValue($('#address').val());
	isAddressValid = $.validationHandler((isNotBlank(address)), 'addressValidation', '');
//	synRegisterMerStep1Btn();
}

function validateMerchantUserName() {
	// 校验用户名是否为空
	var merchantUserName = getTrimValue($('#merchantUserName').val());
	isMerchantUserNameValid = isNotBlank(merchantUserName);
	if(isMerchantUserNameValid) {
		isMerchantUserNameValid = $.validationHandler($.azEnNoUs(merchantUserName, 4, 20), 'merchantUserNameValidation', "输入的用户名格式不正确");
		if(isMerchantUserNameValid) {
			// 检验用户名是否唯一
			ddpAjaxCall({
				url : "checkUserNameExist",
				data : merchantUserName,
				successFn : function(data) {
					if(data.code == "000000") {
						isMerchantUserNameValid = true;
						$.validationHandler(true, 'merchantUserNameValidation', "");
//						synRegisterMerStep2Btn();
					} else {
						$.validationHandler(false, 'merchantUserNameValidation', generateResponseMsg(data));
						isMerchantUserNameValid = false;
//						synRegisterMerStep2Btn();
					}
				}
			});
		}
	}
//	synRegisterMerStep2Btn();
}

function validateMerchantUserPWD() {
	var pwd = $('#merchantUserPWD').val();
	if(isBlank(pwd)) {
		isMerchantUserPwdValid = false;
		$.validationHandler(false, 'merUserPWDValidation', '');
	} else {
		var weakPassword = jQuery.isNumeric(pwd);
		if(weakPassword) {
			isPersonalUserPwdValid = false;
			$.validationHandler(false, 'merchantUserPWDValidation', '密码不支持纯数字，请您更换');
		} else {
			isMerchantUserPwdValid = $.password(pwd);
			if(isMerchantUserPwdValid) {
				$.validationHandler(true, 'merchantUserPWDValidation', '');
				validateMerchantUserPWD2();
			} else {
				$.validationHandler(false, 'merchantUserPWDValidation', '输入的密码格式不正确');
			}
		}
	}
//	synRegisterMerStep2Btn();
}

function validateMerchantUserPWD2() {
	if(isNotBlank($('#merchantUserPWD2').val())) {
		isMerchantUserPwd2Valid = $.validationHandler(($('#merchantUserPWD').val() == $('#merchantUserPWD2').val()), 'merchantUserPWD2Validation', '两次输入密码不一致');
	} else {
		isMerchantUserPwd2Valid = false;
	}
//	synRegisterMerStep2Btn();
}

function merRegisterStep1CheckClick() {
	var  merRegisterStep1Check = $('#merRegisterStep1Check').is(':checked');
	if(merRegisterStep1Check) {
		$('#merRegisterStep1CheckValidation').hide();
	} else {
		$('#merRegisterStep1CheckValidation').show();
	}
//	synRegisterMerStep1Btn();
}

function registerMerStep1() {
	var  merRegisterStep1Check = $('#merRegisterStep1Check').is(':checked');
	if(merRegisterStep1Check && isMerNameValid && isMerLinkUserValid && isMerMobileValid && isMerMobileCodeValid && isAddressValid && isCityValid) {
		$(".flag").css("display", "none");
		$("#flag-4").css("display", "block");
	} else {
		
		if(!merRegisterStep1Check) {
			$('#merRegisterStep1CheckValidation').show();
		}
		if(!isMerNameValid) {
			var merName = getTrimValue($('#merName').val());
			if(isBlank(merName)) {
				$.validationHandler(false, 'merNameValidation', "请输入商户名称");
			}
		}
		if(!isMerLinkUserValid) {
			var linkUser = getTrimValue($('#merLinkUser').val());
			if(isBlank(linkUser)) {
				$.validationHandler(false, 'merLinkUserValidation', "请输入联系人");
			}
		}
			
		if(!isMerMobileValid) {
			var mobile = getTrimValue($('#merLinkUserMobile').val());
			if(isBlank(mobile)) {
				$.validationHandler(false, 'merLinkUserMobileValidation', "请输入手机号码");
			}
		}
		
		var merCaptcha = getTrimValue($('#merCaptcha').val());
		if(isBlank(merCaptcha)) {
			$.validationHandler(false, 'merKaptchaValidation', '请输入验证码');
		} else if(!isMerMobileCodeValid) {
			var mobileCode = getTrimValue($('#merLinkUserMobileCheckCode').val());
			if(isBlank(mobileCode)) {
				$.validationHandler(false, 'merMobileCodeValidation', "请输入短信验证码");
			}
		}

		if(!isAddressValid) {
			var address = getTrimValue($('#address').val());
			if(isBlank(address)) {
				$.validationHandler(false, 'addressValidation', "请输入详细地址");
			}
		}
		if(!isCityValid) {
			var city = getCityValue('city');
			if(isBlank(city)) {
				$('#addressCityValidation').html("请选择省市").show();
			}
		}
	}
}

function validateProvince() {
	$.validationHandler(true, 'addressCityValidation', "");
	isCityValid = true;
}
function validateProvinceG() {
	$.validationHandler(true, 'addressCityValidationG', "");
	isCityValid = true;
}

function registerMerStep2() {
	if(isMerchantUserNameValid && isMerchantUserPwdValid && isMerchantUserPwd2Valid) {
		var psssWd = $('#merchantUserPWD').val();
		$('#merchantUserPWD').val(md5(md5(psssWd)));
		$('#merLinkUserMobile2').val($('#merLinkUserMobile').val());
		$('#merRegisterForm').submit();
	} else {
		if(!isMerchantUserNameValid) {
			var merchantUserName = getTrimValue($('#merchantUserName').val());
			if(isBlank(merchantUserName)) {
				$.validationHandler(false, 'merchantUserNameValidation', "请输入用户名");
			}
		}
		if(!isMerchantUserPwdValid) {
			var pwd = getTrimValue($('#merchantUserPWD').val());
			if(isBlank(pwd)) {
				$.validationHandler(false, 'merchantUserPWDValidation', "请输入密码");
			}
		}
		if(!isMerchantUserPwd2Valid) {
			var pwd2 = getTrimValue($('#merchantUserPWD2').val());
			if(isBlank(pwd2)) {
				$.validationHandler(false, 'merchantUserPWD2Validation', "请再次输入密码");
			}
		}
	}
}


function requestAuthCode2() {
	var mobile = getTrimValue($("#merLinkUserMobile").val());
	if (isMerMobileValid) {
		$("#requestAuthCodeBtn2").attr("disabled", true);
		$("#merLinkUserMobile").attr("disabled", true);
		waitingTime=120;
		intervalid = setInterval("isAuthCoded('merLinkUserMobile', 'requestAuthCodeBtn2')", 1000); 
		ddpAjaxCall({
			url : "sendNoCheck",
			data : mobile,
			successFn : function(data) {
				if (data.code == "000000") {
					$('#merSerialNumber').html("序号【" + data.responseEntity.pwdseq + "】").show();
				}
			}
		});
	} else {
		if(isBlank(mobile)) {
			$.validationHandler(isMobile, 'merLinkUserMobileValidation', '请输入手机号');
		}
	}
}

/**
 * 更改手机获取验证码的样式
 */
function isAuthCoded(mobileInput, authCodeSendBtn) {
		if (waitingTime == 0) {
			$('#' + authCodeSendBtn).removeAttr("disabled");
			$('#' + authCodeSendBtn).val("获取验证码");
			clearInterval(intervalid);
		}else{
			$('#' + authCodeSendBtn).val(waitingTime + "秒");
			waitingTime--;
		}
}


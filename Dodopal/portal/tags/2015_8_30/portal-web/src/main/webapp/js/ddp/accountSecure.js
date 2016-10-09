$(function() {
			highlightTitle();
			$('.header-inr-nav ul li a').each(function() {
						if ($.trim($(this).text()) == "账户设置") {
							$(this).addClass('cur').siblings("a")
									.removeClass("cur");
						}
					});
			// 效验当前手机号是否正确
			$('#todayMobile').focusout(function() {
						validateTodayMobile(true);
					});
			// 效验新手机号码是否正确
			$('#newestMobile').focusout(function() {
						validateNewestMobile(true);
					});
			// 效验当前输入的密码是否正确
			$('#merUserPWD').focusout(function() {
						validateMerUserPWD(true);
					});
			// 效验输入的新密码是否正确
			$('#merUserUpPWD').focusout(function() {
						validateMerUserUpPWD(true);
					});
			// 效验输入的两次密码是否一致
			$('#merUserUpPWDTwo').focusout(function() {
						validateMerUserUpPWDTwo(true);
					});
		});

// 验证当前手机号码
function validateTodayMobile(state) {
	$("#todayMobileERR").hide();
	return $.warnHandler(state, false, $.mobile, "todayMobile",
			"todayMobileERR", "输入的手机格式不正确");
}
// 验证最新手机号码
function validateNewestMobile(state) {
	$("#newestMobileERR").hide();
	return $.warnHandler(state, false, $.mobile, "newestMobile",
			"newestMobileERR", "输入的手机格式不正确");
}

// 清空所有手机号码输入框
function clearMobile() {
	$("#todayMobile").val('');
	$("#todayMobileVerificationCode").val('');
	$("#newestMobile").val('');
	$("#newestMobileVerificationCode").val('');
	$("#serialNumberSerialNumber").html('');
	$("#serialNumberNewestMobile").html('');
}
// 点击修改展示当前手机号码
function editMobile() {
	clearMobile();
	$('#editMobileButton').hide();
	$('#todayMobileView').show();
	$('#newestMobileView').hide();
}

// 获取当前手机号码验证码
function todayMobileVerificationCodeButton() {
	var todayMobile = $("#todayMobile").val();
	ddpAjaxCall({
				url : "accountSend",
				data : todayMobile,
				successFn : function(data) {
					if (data.code == "000000") {
						$("#serialNumberTodayMobile")
								.html(data.responseEntity.pwdseq);
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : "发送成功"
								});
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
					}
				}
			});
}
// 下一步
function showNewestMobileView() {
	var mobileBean = {
		moblieNum : $("#todayMobile").val(),
		dypwd : $("#todayMobileVerificationCode").val(),
		serialNum : $("#serialNumberTodayMobile").text()

	}
	ddpAjaxCall({
				url : "accountMoblieCodeCheck",
				data : mobileBean,
				successFn : function(data) {
					if (data.code == "000000") {
						clearMobile();
						$('#editMobileButton').hide();
						$('#todayMobileView').hide();
						$('#newestMobileView').show();
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						clearMobile();
						$('#editMobileButton').hide();
						$('#todayMobileView').show();
						$('#newestMobileView').hide();
					}
				}
			});
}
// 获取当前手机号码验证码
function newestMobileVerificationCodeButton() {
	var newestMobile = $("#newestMobile").val();
	ddpAjaxCall({
		url : "accountSend",
		data : newestMobile,
		successFn : function(data) {
			if (data.code == "000000") {
				$("#serialNumberNewestMobile").html(data.responseEntity.pwdseq);
				$.messagerBox({
							type : 'warn',
							title : "信息提示",
							msg : "发送成功"
						});
			} else {
				$.messagerBox({
							type : 'warn',
							title : "信息提示",
							msg : data.message
						});
			}
		}
	});
}

// 点击确认保存新手机号
function newestMobileButton() {
	var mobileBean = {
		moblieNum : $("#newestMobile").val(),
		dypwd : $("#newestMobileVerificationCode").val(),
		serialNum : $("#serialNumberNewestMobile").text()
	}
	ddpAjaxCall({
				url : "saveAccountMoblie",
				data : mobileBean,
				successFn : function(data) {
					if (data.code == "000000") {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						clearMobile();
						$('#editMobileButton').show();
						$('#todayMobileView').hide();
						$('#newestMobileView').hide();
						$("#img01").closest('.ehs-box')
								.removeClass('ehs-box-click');
						$("#img01").closest('.base-table').hide();
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						clearMobile();
						$('#editMobileButton').hide();
						$('#newestMobileView').show();
						$('#todayMobileView').hide();
					}
				}
			});
}

/** ********************************************************更改用户密码*************************** */
// 效验当前输入的密码是否正确
var isMerUserPWD = false;
function validateMerUserPWD(state) {
	$("#merUserPWDERR2").hide();
	var merUserPWD = getTrimValue($('#merUserPWD').val());
	console.log(md5(md5(merUserPWD)));
	isMerUserPWD = $.warnHandler(state, true, $.password, "merUserPWD",
			"merUserPWDERR", "输入的密码格式不正确", 0, 50);
	var merchantUserUpBean = {
		merUserPWD : md5(md5(merUserPWD))
	}
	if (isMerUserPWD) {
		// 检验商户名称是否唯一
		ddpAjaxCall({
			url : "findMerUserPWD",
			data : merchantUserUpBean,
			successFn : function(data) {
				if (data.code == "000000") {
					$("#merUserPWDERR2").hide();
					isMerUserPWD = true;
				} else {
					$("#merUserPWDERR").hide();
					$("#merUserPWDERR2").html(generateResponseMsg(data)).show();
					isMerUserPWD = false;
				}
			}
		});
	} else {
		isMerUserPWD = false;
	}
	return isMerUserPWD;
}
// 效验当前新输入的密码是否正确
function validateMerUserUpPWD(state) {
	var weakPassword = function(value) {
		return !jQuery.isNumeric(value);
	}

	var checktwo = function() {
		var valid2 = $.warnHandler(true, true, $.password, "merUserUpPWD",
				"merUserUpPWDERR", "输入的密码格式不正确", 6, 20);
		return valid2;
	};

	var data = {
		submitState : state,
		required : true,
		warnCheckFn : weakPassword,
		valiDatInpId : "merUserUpPWD",
		warnMessageElement : "merUserUpPWDERR",
		msg : "密码不支持纯数字，请您更换",
		ajaxMethod : checktwo
	};

	return $.warnDataFnHandler(data);

}
// 對比密碼
function validateMerUserUpPWDTwo(state) {
	var validateFn = function(password) {
		return $('#merUserUpPWDTwo').val() == $('#merUserUpPWD').val();
	}
	return $.warnHandler(state, true, validateFn, "merUserUpPWDTwo",
			"merUserUpPWDTwoERR", "两次输入密码不一致", 6, 20);
}
// 清空输入框
function clearMerUserPwd() {

	$('#merUserPWD').val('');
	$('#merUserUpPWD').val('');
	$('#merUserUpPWDTwo').val('');
}
// 打开输入密码界面
function editMerUserPwdView() {
	clearMerUserPwd();
	$("#merUserPWDERR2").hide();
	$('#editMerUserPwdView').hide();
	$('#editMerUserPwdTable').show();
}

function checkValue() {
	var isMerUserPWDERRCheck = $("#merUserPWDERR2").text();
	var isMerUserPwdCheck = validateMerUserUpPWD(true);
	var isMerUserPwdTwoCheck = validateMerUserUpPWDTwo(true);
	var isWeiYi = false;
	if (isBlank(isMerUserPWDERRCheck)) {
		isWeiYi = true;
	}
	return isWeiYi && isMerUserPwdCheck && isMerUserPwdTwoCheck;
}
// 保存更改密码
function updatemerUserUpPWD() {
	// 效验正确
	if (!checkValue()) {
		return false;
	}
	var merUserPWD = $('#merUserPWD').val();
	var merUserUpPWD = $('#merUserUpPWD').val();
	var merchantUserUpBean = {
		merUserPWD : md5(md5(merUserPWD)),
		merUserUpPWD : md5(md5(merUserUpPWD))
	}
	ddpAjaxCall({
				url : "updateLogPwd",
				data : merchantUserUpBean,
				successFn : function(data) {
					if (data.code == "000000") {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						$('#editMerUserPwdViewButton').show();
						$('#editMerUserPwdTable').hide();
						$("#img02").closest('.ehs-box')
								.removeClass('ehs-box-click');
						$("#img02").closest('.base-table').hide();
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						$('#editMerUserPwdViewButton').hide();
						$('#editMerUserPwdTable').show();
					}
				}
			});
}
/** ********************************************验签和签名************************* */
function clearMerMD5PayPwd(){
	$("#merMD5PayPwdSpan").html('');
	$("#merMD5PayPwd").val('');
}

// 打开签名界面
function merMD5PayPwdView() {
	clearMerMD5PayPwd();
	$('#merMD5PayPwdViewButton').hide();
	$('#merMD5PayPwdTable').show();
	ddpAjaxCall({
				url : "findMerMD5Pay",
				successFn : function(data) {
					if (data.code == "000000") {
						$("#merMD5PayPwdSpan")
								.html(data.responseEntity.merMD5PayPwd);
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
					}
				}
			});
}

function upMerMD5PayPwd() {
	var merchantUserUpBean = {
		merMD5PayPwd : $("#merMD5PayPwdSpan").text(),
		upmerMD5PayPwd : $("#merMD5PayPwd").val()
	}
	ddpAjaxCall({
				url : "upmerMD5PayPwd",
				data : merchantUserUpBean,
				successFn : function(data) {
					if (data.code == "000000") {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						$('#merMD5PayPwdTable').hide();
						$('#merMD5PayPwdViewButton').show();
						$("#img03").closest('.ehs-box')
								.removeClass('ehs-box-click');
						$("#img03").closest('.base-table').hide();
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						clearMerMD5PayPwd();
						$('#merMD5PayPwdTable').show();
						$('#merMD5PayPwdViewButton').hide();
					}
				}
			});
}

function clearMerMD5BackPayPWD(){
	$("#merMD5BackPayPWDSpan").html('');
	$("#merMD5BackPayPWD").val('');
}
// 打开验签密钥界面
function merMD5BackPayPWDView() {
	clearMerMD5BackPayPWD();
	$('#merMD5BackPayPWDViewButton').hide();
	$('#merMD5BackPayPWDTable').show();
	ddpAjaxCall({
				url : "findMD5BackPayPWD",
				successFn : function(data) {
					if (data.code == "000000") {
						$("#merMD5BackPayPWDSpan")
								.html(data.responseEntity.merMD5BackPayPWD);
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
					}
				}
			});
}

function upmerMD5BackPayPWD() {
	var merchantUserUpBean = {
		merMD5BackPayPWD : $("#merMD5BackPayPWDSpan").text(),
		upmerMD5BackPayPWD : $("#merMD5BackPayPWD").val()
	}
	ddpAjaxCall({
				url : "upmerMD5BackPayPWD",
				data : merchantUserUpBean,
				successFn : function(data) {
					if (data.code == "000000") {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						$('#merMD5BackPayPWDTable').hide();
						$('#merMD5BackPayPWDViewButton').show();
						$("#img031").closest('.ehs-box')
								.removeClass('ehs-box-click');
						$("#img031").closest('.base-table').hide();
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						clearMerMD5BackPayPWD();
						$('#merMD5BackPayPWDTable').show();
						$('#merMD5BackPayPWDViewButton').hide();
					}
				}
			});
}
/*********************************个性化设置***************************/
//打开设置
function payInfoFlgView(){
$('#payInfoFlgViewButton').hide();
$('#payInfoFlgTable').show();
ddpAjaxCall({
				url : "findModifyPayInfoFlg",
				successFn : function(data) {
					if (data.code == "000000") {
						$("input[type='radio'][name='payinfoFlg'][value='"+data.responseEntity.payInfoFlg+"']").attr("checked",true);   
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
					}
				}
			});
}

function upPayInfoFlg(){
var payInfoFlg = $('input[name="payinfoFlg"]:checked ').val(); 
	ddpAjaxCall({
				url : "upModifyPayInfoFlg",
				data : payInfoFlg,
				successFn : function(data) {
					if (data.code == "000000") {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						$('#payInfoFlgTable').hide();
						$('#payInfoFlgViewButton').show();
						$("#img032").closest('.ehs-box')
								.removeClass('ehs-box-click');
						$("#img032").closest('.base-table').hide();
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						$('#payInfoFlgTable').show();
						$('#payInfoFlgViewButton').hide();
					}
				}
			});
}
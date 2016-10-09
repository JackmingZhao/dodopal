$(function() {
			highlightTitle();
			$('.header-inr-nav ul li a').each(function() {
						if ($.trim($(this).text()) == "安全设置") {
							$(this).addClass('cur').siblings("a")
									.removeClass("cur");
						}
					});
					
			// 效验当前手机号是否正确
			$('#todayMobile').focusout(function() {
						phoneFocus(true);
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
		
// 验证码倒计时控制变量
var intervalid; 
var waitingTime = 120;
//**************************************************当前手机号码****************************************
//当前手机号码鼠标移出事件
function phoneKeyup(){
//获取当前登录的手机号码
var merUserMobile = $.trim($("#merUserMobile").val());
//输入的当前输入手机号码
var todayMobile = $.trim($("#todayMobile").val());
//判断输入的手机号码和当前登录的手机号码是否一致
if(todayMobile.length==11){
if(merUserMobile != todayMobile){
		$("#todayMobileERR").html('');
		$("#todayMobileERR").removeClass().addClass('tip-red-error').html("输入的当前手机号码不正确");
		$('#todayMobileButton').attr("disabled",true);
		$('#todayMobileButton').css('color','#c0c0c0');
		$('#todayMobileButton').css('border','1px solid #c0c0c0');
	}else{
		//初始化验证码为禁用
		$('#todayMobileButton').attr("disabled",false);
		$('#todayMobileButton').css('color','#e47f12');
		$('#todayMobileButton').css('border','1px solid #e47f12');
		$("#todayMobileERR").html('');
	}
}
}
// 验证当前手机号码
function phoneFocus(state) {
	$("#todayMobileERR").hide();
	//获取当前登录的手机号码
	var merUserMobile = $.trim($("#merUserMobile").val());
	//输入的当前输入手机号码
	var todayMobile = $.trim($("#todayMobile").val());
	var checkTodayMobile= $.warnHandler(state, false, $.mobile, "todayMobile","todayMobileERR", "输入的手机格式不正确");
	//第二步判断两个手机号码是否相等
	if(checkTodayMobile){
			if(merUserMobile != todayMobile){
					$("#todayMobileERR").html('');
					$("#todayMobileERR").removeClass().addClass('tip-red-error').html("输入的当前手机号码不正确");
					$('#todayMobileButton').attr("disabled",true);
					$('#todayMobileButton').css('color','#c0c0c0');
					$('#todayMobileButton').css('border','1px solid #c0c0c0');
					checkTodayMobile =false;
				}
	}else{
		$('#todayMobileButton').attr("disabled",true);
		$('#todayMobileButton').css('color','#c0c0c0');
		$('#todayMobileButton').css('border','1px solid #c0c0c0');
		checkTodayMobile =false;
	}
	return checkTodayMobile;
			

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
	$('#todayMobileButton').attr("disabled",true);
	}

// 获取当前手机号码验证码
function todayMobileVerificationCodeButton() {
	//获取当前输入的手机号码
	var todayMobile = $.trim($("#todayMobile").val());
	$("#todayMobileButton").attr("disabled", true); //按钮不可编辑
	$("#todayMobile").attr("disabled", true);//验证码输入框不可编辑
	waitingTime=120;
	intervalid = setInterval("isAuthCoded('todayMobileVerificationCode', 'todayMobileButton')", 1000); 
	ddpAjaxCall({
			url : "accountSend",
			data : todayMobile,
			successFn : function(data) {
				if (data.code == "000000") {
					$('#todayMobileSerialNumber2').html("序号【" + data.responseEntity.pwdseq + "】").show();
					$('#todayMobileSerialNumber').html(data.responseEntity.pwdseq).hide();
				}else {
				$.messagerBox({
							type : 'warn',
							title : "信息提示",
							msg : data.message
						});
				}
			}
		});
}

//********************************************************当前手机号码*****************************************
// 下一步
function showNewestMobileView() {
	var mobileBean = {
		moblieNum : $("#todayMobile").val(),
		dypwd : $("#todayMobileVerificationCode").val(),
		serialNum : $("#todayMobileSerialNumber").text()

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
						$('#newMerUserMobileButton').attr("disabled",true);
						$('#newMerUserMobileButton').css('color','#c0c0c0');
						$('#newMerUserMobileButton').css('border','1px solid #c0c0c0');
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						//clearMobile();
						$('#editMobileButton').hide();
						$('#todayMobileView').show();
						$('#newestMobileView').hide();
					}
				}
			});
}
//------------------------------------------------------新手机号码-------------------------------

//第一步判断当前手机号码是否是自己的当前的手机号码
//第二步判断当前新手机号码是否存在
//第三步判断
function newPhoneKeyup(){
//获取当前登录的手机号码
var merUserMobile = $("#merUserMobile").val(); 
//输入信息
var newMerUserMobile = $.trim($("#newestMobile").val()); 
if(newMerUserMobile.length==11){
	if(merUserMobile == newMerUserMobile){
		$("#newestMobileERR").html('');
		$("#newestMobileERR").removeClass().addClass('tip-red-error').html("新手机号码和当前手机号码一致");
		$('#newMerUserMobileButton').attr("disabled",true);
		$('#newMerUserMobileButton').css('color','#c0c0c0');
		$('#newMerUserMobileButton').css('border','1px solid #c0c0c0');
	}else{
		// 检验是否唯一
		ddpAjaxCall({
			url : "checkMobileExist",
			data : newMerUserMobile,
			successFn : function(data) {
				if(data.code == "000000") {
					$("#newestMobileERR").html("").show();
					$('#newMerUserMobileButton').attr("disabled",false);
					$('#newMerUserMobileButton').css('color','#e47f12');
					$('#newMerUserMobileButton').css('border','1px solid #e47f12');
				} else {
					$("#newestMobileERR").html('');
					$("#newestMobileERR").removeClass().addClass('tip-red-error').html(generateResponseMsg(data));
					$('#newMerUserMobileButton').attr("disabled",true);
					$('#newMerUserMobileButton').css('color','#c0c0c0');
					$('#newMerUserMobileButton').css('border','1px solid #c0c0c0');
				}
			}
		});
	}
}
}

// 验证最新手机号码
function validateNewestMobile(state) {
	$("#newestMobileERR").hide();
	//获取当前登录的手机号码
	var merUserMobile = $("#merUserMobile").val(); 
	//输入信息
	var newMerUserMobile = $.trim($("#newestMobile").val()); 
	var checkNewMerUserMobile= $.warnHandler(state, false, $.mobile, "newestMobile",
			"newestMobileERR", "输入的手机格式不正确");
		//第二步判断两个手机号码是否相等
	if(checkNewMerUserMobile){
		if(newMerUserMobile.length==11){
	if(merUserMobile == newMerUserMobile){
		$("#newestMobileERR").html('');
		$("#newestMobileERR").removeClass().addClass('tip-red-error').html("新手机号码和当前手机号码一致");
		$('#newMerUserMobileButton').attr("disabled",true);
		$('#newMerUserMobileButton').css('color','#c0c0c0');
		$('#newMerUserMobileButton').css('border','1px solid #c0c0c0');
	}else{
		// 检验是否唯一
	}
}
	}else{
		$('#newMerUserMobileButton').attr("disabled",true);
		$('#newMerUserMobileButton').css('color','#c0c0c0');
		$('#newMerUserMobileButton').css('border','1px solid #c0c0c0');
		checkNewMerUserMobile =false;
	}	
	return checkNewMerUserMobile;
}
// 获取新手机号码验证码
function newestMobileVerificationCodeButton() {
	//获取当前输入的手机号码
	var newMerUserMobile = $.trim($("#newestMobile").val()); 
	$("#newMerUserMobileButton").attr("disabled", true); //按钮不可编辑
	$("#newestMobile").attr("disabled", true);//手机号码输入框不可编辑
	waitingTime=120;
	intervalid = setInterval("isAuthCoded('newestMobileVerificationCode', 'newMerUserMobileButton')", 1000); 
	ddpAjaxCall({
			url : "accountSend",
			data : newMerUserMobile,
			successFn : function(data) {
				if (data.code == "000000") {
					$('#serialNumberNewestMobile').html(data.responseEntity.pwdseq).hide();
					$('#serialNumberNewestMobile2').html("序号【" + data.responseEntity.pwdseq + "】").show();
				}else {
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
						$("#merUserMobileForm").empty().append(data);
						window.location.reload();
					} else {
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
						$('#editMobileButton').hide();
						$('#newestMobileView').show();
						$('#todayMobileView').hide();
					}
				}
			});
}

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
//------------------------------------------------------新手机号码-------------------------------
/** ********************************************************更改用户密码*************************** */
// 效验当前输入的密码是否正确
var isMerUserPWD = false;
function validateMerUserPWD(state) {
	$("#merUserPWDERR2").hide();
	var merUserPWD = getTrimValue($('#merUserPWD').val());
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
	$("#editMerUserPwdViewButton").hide();
}

function checkValue() {
	//var isMerUserPWDERRCheck = $("#merUserPWDERR2").text();
	var isMerUserPwd = validateMerUserPWD(true);
	var isMerUserPwdCheck = validateMerUserUpPWD(true);
	var isMerUserPwdTwoCheck = validateMerUserUpPWDTwo(true);
	//var isWeiYi = false;  
//	if (isBlank(isMerUserPWDERRCheck)) {
//		isWeiYi = true; 
//	}
	//isWeiYi && 
	return isMerUserPwd && isMerUserPwdCheck && isMerUserPwdTwoCheck;
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
	var upmerMD5PayPwd =$.trim($("#merMD5PayPwd").val());
	if(upmerMD5PayPwd==""){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请输入签名密钥"});
		return;
	}
	var merchantUserUpBean = {
		merMD5PayPwd : $("#merMD5PayPwdSpan").text(),
		upmerMD5PayPwd : upmerMD5PayPwd
	};
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
						$("#merMD5PayPwd").val('');
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
	var upmerMD5BackPayPWD = $.trim($("#merMD5BackPayPWD").val());
	if(upmerMD5BackPayPWD==""){
	$.messagerBox({type: 'warn', title:"信息提示", msg: "请输入验签密钥"});
	return;
	}
	var merchantUserUpBean = {
		merMD5BackPayPWD : $("#merMD5BackPayPWDSpan").text(),
		upmerMD5BackPayPWD : upmerMD5BackPayPWD
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
						$("#merMD5BackPayPWD").val('');
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
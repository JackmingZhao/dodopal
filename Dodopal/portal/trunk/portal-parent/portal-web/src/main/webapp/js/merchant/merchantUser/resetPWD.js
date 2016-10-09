var intervalid; 
var i = 120;
var reseq=0;
var state = false;

var pwdMessageWarn = "6-20位字符，支持数字、字母、及符号";
var pwdMessageError = "输入的密码格式不正确";
var userNameWarn = "请输入手机号或用户名";
var pwdMessageAgainWarn = "请再次输入密码";
var mobileMessage = "短信验证码不正确，请重新输入";
var simplePwdWarn = "密码不支持纯数字,请您更换";
var pwdAgainError = "两次输入密码不一致";
var authCodeError = "验证码不正确，请重新输入";
$(function(){
	$('#mobileCode').focus(function(){
		var mobile = $.trim($('#mobileCode').val());
		if(mobile==""){
			$('#mobileMessage').removeAttr("style").html(userNameWarn);
		}
	});
	$('#authCodeInput').focus(function(){
			$('#authCodeErrorMessage').html("").hide();
			$("#authCodeMessage").show();
	});
	$('#pwd').focus(function(){
		$('#pwdMassage').removeAttr("style").html(pwdMessageWarn);
	});
	$('#pwd2').focus(function(){
		$('#againMessage').removeAttr("style").html(pwdMessageAgainWarn);
	});
	$('#yzm').focusin(function(){
		$('#yzmMessage').html('');
	});
	
	$('#yzm').focusout(function(){
		checkCode(true);
	});
	
});
function sendAuthCode(){
	if(state){
		return;
	}
	state = true;
	var yzm = $.trim($('#yzm').val());
	if(authCode==null||authCode==""){
		$.messagerBox({type:'warn', title:"信息提示", msg: "验证码为空"});
		return;
	}
	$("#sendCode").removeAttr("onclick");
	//window.location.href ="sendAuthCode.htm?mobile="+mobile;
	intervalid = setInterval("againSend()", 1000); 
	ddpAjaxCall({
		url : "sendAuthCode",
		data : yzm,
		successFn : function(data) {
			if (data.code == "000000") {
				//TODO 错误ui展示信息位置等。
				document.getElementById("sendCode").setAttribute("disabled", true);
				//$("#mobileMessage").html("发送成功请注意查收短信");
				reseq=data.responseEntity.PWDSEQ;
				$("#authCodeMessage").html("【序号:"+data.responseEntity.PWDSEQ+"】");
			}else if(data.code == "120006"){
				$('#authCode').show();
				$('#sendAuthCode').hide();
			}else{
				$("#mobileMessage").html(data.message);
			}	
		}
	});
}

/**
 * 更改button样式
 */
function againSend(){
		if (i == 0) {
			i=120;
			$("#sendCode").attr("onclick","sendAuthCode();");
			state = false;
			document.getElementById("sendCode").removeAttribute("disabled");
			document.getElementById("sendCode").value = "获取验证码";
			clearInterval(intervalid);
		}else{
			document.getElementById("sendCode").value = i+"秒";
			i--;
		}
}

function checkAuthCode(){
	var authCode = $.trim($("#authCodeInput").val());
	var map = {
		"authCode":authCode,
		"PWDSEQ":reseq
	};
	$("#authCodeErrorMessage").html("");
	if(authCode==""){
		$("#authCodeMessage").hide();
		$("#authCodeErrorMessage").css("color","red").html("请输入手机验证码").show();
		return;
	}
	ddpAjaxCall({
		url : "checkAuthCode",
		data : map,
		successFn : function(data) {
			if (data.code == "000000") {
				$("#sendAuthCode").hide();
				$("#newPWD").show();
			}else{
				//alert("验证码有误");
				$("#authCodeMessage").hide();
				$("#authCodeErrorMessage").html(mobileMessage).show();
			}	
		}
	});
}

function checkPwd(v){
	var pwd = $.trim($("#pwd").val());
	$("#pwdMassage").css("color","red");
	$("#againMessage").css("color","red");
	var pwd2 = $.trim($("#pwd2").val());
	if(v==0){
		if(pwd==""){
			$("#pwdMassage").html("");
			return;
		}
		if(pwd.length<6||pwd.length>20){
			$("#pwdMassage").html(pwdMessageError);
		}else{
			if($.pwdNumber(pwd)){
				$("#pwdMassage").html(simplePwdWarn);
			}else{
				$("#pwdMassage").html("");
			}
		}
	}else{
		if(pwd2==""){
			$("#againMessage").html("");
			return;
		}
		if(pwd==""){
			$("#pwdMassage").html("请输入密码");
		}else{
			if(pwd!=pwd2){
				$("#againMessage").html(pwdAgainError);
			}else{
				$("#againMessage").html("");
			}
		}
	}
}

/**
 * 修改密码
 */
function modifyPWD(){
	var pwd = $.trim($("#pwd").val());
	var pwd1 = $.trim($("#pwd2").val());
	if(pwd==""){
		$("#pwdMassage").html("请输入您要修改的密码");
		return false;
	}else if(pwd.length<6||pwd.length>20){
		$("#pwdMassage").html(pwdMessageError);
		return false;
	}else if(pwd1==""){
		$("#againMessage").html(pwdMessageAgainWarn);
		return false;
	}else if(pwd!=pwd1){
		$("#againMessage").html(pwdAgainError);
		return false;
	}
	if($.pwdNumber(pwd)){
		$("#pwdMassage").html(simplePwdWarn);
		return false;
	}
	
	clearMessage();
	var md5pwd = md5(md5(pwd));
	ddpAjaxCall({
		url : "modifyNewPWD",
		data : md5pwd,
		successFn : function(data) {
			if (data.code == "000000") {
				window.location.href ="resetSuccess?status=true";
			}else{
				//alert("更新失败");
				window.location.href ="resetSuccess?status=false";
			}	
		}
	});
}
function clearMessage(){
	$("#pwdMassage").html("");
	$("#pwd2Massage").html("");
}
var mobileCheckFlag = false;
function checkMobile(){
	var mobileOrUserName = $.trim($('#mobileCode').val());
	$("#mobileMessage").html("");
	$("#merUserName").html("");
	$("#userMobile").html("");
	if(mobileOrUserName==""){
		return;
	}
	ddpAjaxCall({
		url : "checkMobileOrUserName",
		data : mobileOrUserName,
		successFn : function(data) {
			if (data.code == "000000") {
				var user = data.responseEntity;
				$("#userMobile").html("");
				if(null!=user){
					$("#userMobile").html(user.merUserMobile);
					$("#merUserName").html(user.merUserName);
					$("#mobileMessage").html("");
					//$("sendAuthCode").show();
					mobileCheckFlag = true;
				}
			}else{
				mobileCheckFlag = false;
				$("#merUserName").html("");
				$("#userMobile").html("");
				var reg = /^1\d{10}$/;  
//			    if(reg.test(mobileOrUserName)){
//			    	$("#mobileMessage").css("color","red").html("手机号尚未注册");
//			    }else{
			    	$("#mobileMessage").css("color","red").html("账号尚未注册");
//			    }
			}	
		}
	});
}

function checkCode(state){
	var captcha =$.trim($('#yzm').val());
	var mobile = $.trim($('#mobileCode').val());
	if(mobile==""){
		$('#mobileMessage').css("color","red").html(userNameWarn);
		return;
	}
	if(state){
		if(captcha.length<4){
			$('#yzmMessage').html(authCodeError);
		}
	}
	if(!mobileCheckFlag){
		checkMobile();
		return;
	}
	var isValid;
	if(captcha.length==4) {
		var url = "checkCaptcha?captcha=" + captcha + "&captchaType=personResetPwd";
		ddpAjaxCall({
			async : false,
			url : url,
			successFn : function(data) {
				if(data.code == "000000") {
					$('#authCode').hide();
					$('#sendAuthCode').show();
//					$('#merCaptchaDiv').hide();
//					$('#merMobileCaptchaDiv').show();
				} else {
					$('#yzmMessage').html(authCodeError);
				//$.validationHandler(false, 'merCaptchaValidation', generateResponseMsg(data));
					isValid = false;
				}
			}
		});
	}
	
	return isValid;
}

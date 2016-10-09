var userNameDuplicated = false;
function validateMerUserName(merchantUser,state){
	var ajaxMethod = function(){
		console.log('merchantUser.merUserName:' + $('#merUserName').val());
		if(!merchantUser.id){
			ddpAjaxCall({
				url : $.base +"/register/checkUserNameExist",
				data : $('#merUserName').val(),
				successFn : function(data) {
					if(data.code == "000000" && (data.responseEntity == 'false' || data.responseEntity == false)) {
						$.validationHandler(true, 'merUserNameVal',null,true);
						userNameDuplicated = false;
					} else {
						$.validationHandler(false, 'merUserNameVal', "用户名已注册");
						userNameDuplicated = true;
					}
				}
			});
		}
		return true;
	}
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:$.username,
			valiDatInpId:"merUserName",
			warnMessageElement:"merUserNameVal",
			msg:"输入的用户名格式不正确",
			minlength:"4",
			maxlength:"20",
			ajaxMethod:ajaxMethod
		};
	
	return $.warnDataFnHandler(data);
}

function validateMerUserPWD(merchantUser,state){
	var weakPassword = function(value){
		return !jQuery.isNumeric(value);
	}
	
	var checktwo = function(){
		var valid2 = $.warnHandler(true,true,$.password,"merUserPWD","merUserPWDVal", "输入的密码格式不正确",6,20) ;
		return valid2;
	};
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:weakPassword,
			valiDatInpId:"merUserPWD",
			warnMessageElement:"merUserPWDVal",
			msg:"密码不支持纯数字，请您更换",
			ajaxMethod:checktwo
		};
	
	return $.warnDataFnHandler(data);
}


function validateMerUserPWDConfirm(merchantUser,state){
	var validateFn = function(password){
		return $('#merUserPWD').val() == $('#merUserPWDConfirm').val();
	}
	return $.warnHandler(state,true,validateFn,"merUserPWDConfirm","merUserPWDConfirmVal", "两次输入密码不一致",6,20); 
}

var mobileDuplicated = false;
function validateMerUserMobile(merchantUser,state){
	var ajaxMethod = function(){
		if(!merchantUser.id){
			ddpAjaxCall({
				url : $.base +"/register/checkMobileExist",
				data : $('#merUserMobile').val(),
				successFn : function(data) {
					if(data.code == "000000" && (data.responseEntity == 'false' || data.responseEntity == false)) {
						$.validationHandler(true, 'merUserMobileVal',null,true);
						mobileDuplicated = false;
					} else {
						$.validationHandler(false, 'merUserMobileVal', "手机号码已注册");
						mobileDuplicated = true;
					}
				}
			});
		}
		return true;
	}
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:$.mobile,
			valiDatInpId:"merUserMobile",
			warnMessageElement:"merUserMobileVal",
			msg:"输入的手机号码格式不正确",
			ajaxMethod:ajaxMethod
		};
	
	return $.warnDataFnHandler(data); 
}

function validateMerUserNickName(merchantUser,state){
	return $.warnHandler(state,true,$.enCn,"merUserNickName","merUserNickNameVal", "输入的真实姓名格式不正确",2,20); 
}

function validatemerUserIdentityNumber(merchantUser,state){
	return checkIdentityNumber(state,'merUserIdentityType','merUserIdentityNumber','merUserIdentityNumberVal');
}

function validatemerMerUserTelephone(merchantUser,state){
	return $.warnHandler(state,false,$.phone,"merUserTelephone","merUserTelephoneVal", "输入的固定电话格式不正确"); 
}

function validatemerMerUserEmail(merchantUser,state){
	return $.warnHandler(state,false,$.email,"merUserEmail","merUserEmailVal", "输入的邮箱地址格式不正确",1,60); 
}

function validateMerchantUser(merchantUser,state){
	var formValid = true;
	
	if(!merchantUser.id){
		formValid = validateMerUserPWD(merchantUser,true) && formValid;
		formValid = validateMerUserPWDConfirm(merchantUser,true) && formValid;
		formValid = validateMerUserMobile(merchantUser,true) && formValid;
		formValid = validateMerUserName(merchantUser,true) && formValid;
	}
	
	formValid = validateMerUserNickName(merchantUser,true) && formValid;
	formValid = validatemerUserIdentityNumber(merchantUser,true) && formValid;
	formValid = validatemerMerUserTelephone(merchantUser,true) && formValid;
	formValid = validatemerMerUserEmail(merchantUser,true) && formValid;
	formValid = validateRemark(merchantUser,true) && formValid;
	
	formValid = formValid && !userNameDuplicated && !mobileDuplicated;
	
	return formValid;
}


function addFocusEvent(){
	$('#merUserName').focus(function(){
		var merchantUser = getFormValues();
		validateMerUserName(merchantUser,false);
	});
	
	$('#merUserPWD').focus(function(){
		var merchantUser = getFormValues();
		validateMerUserPWD(merchantUser,false);
	});
	
	$('#merUserPWDConfirm').focus(function(){
		var merchantUser = getFormValues();
		validateMerUserPWDConfirm(merchantUser,false);
	});
	
	$('#merUserMobile').focus(function(){
		var merchantUser = getFormValues();
		validateMerUserMobile(merchantUser,false);
	});
	
	$('#merUserNickName').focus(function(){
		var merchantUser = getFormValues();
		validateMerUserNickName(merchantUser,false);
	});
	
	$('#merUserIdentityNumber').focus(function(){
		var merchantUser = getFormValues();
		validatemerUserIdentityNumber(merchantUser,false);
	});
	
	$('#merUserTelephone').focus(function(){
		var merchantUser = getFormValues();
		validatemerMerUserTelephone(merchantUser,false);
	});
	
	$('#merUserEmail').focus(function(){
		var merchantUser = getFormValues();
		validatemerMerUserEmail(merchantUser,false);
	});
	
	$('#merUserTelephone').focus(function(){
		validatemerMerUserTelephone(false);
	});
	
	$('#newPassword').focus(function(){
		validateResetMerUserPWD(false);
	});
	
	$('#newPasswordConfirm').focus(function(){
		validateResetMerUserPWDConfirm(false);
	});
}

function validateResetMerUserPWD(state){
	var weakPassword = function(value){
		return !jQuery.isNumeric(value);
	}
	
	var checktwo = function(){
		var valid2 = $.warnHandler(true,true,$.password,"newPassword","newPasswordVal", "输入的密码格式不正确",6,20) ;
		return valid2;
	};
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:weakPassword,
			valiDatInpId:"newPassword",
			warnMessageElement:"newPasswordVal",
			msg:"密码不支持纯数字，请您更换",
			ajaxMethod:checktwo
		};
	
	return $.warnDataFnHandler(data);
}

function validateResetMerUserPWDConfirm(state){
	var validateFn = function(password){
		return $('#newPassword').val() == $('#newPasswordConfirm').val();
	}
	return $.warnHandler(state,true,validateFn,"newPasswordConfirm","newPasswordConfirmVal", "两次输入密码不一致",6,20); 
}

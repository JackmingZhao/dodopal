$(function() {
	//商户类型
	$('#merTypeNotVer').focusout(function(){
		validateMetTypeNotVer(true);
	});
	// 验证商户名称
	$('#merNameNotVer').focusout(function(){
		validateMerNameNotVer(true);
	});
	//验证用户名
	$('#merUserNameNotVer').focusout(function(){
		validateMerUserNameNotVer(true);
	});
	//验证用户密码
	$('#merUserPWDNotVer').focusout(function(){
		validateMerUserPWDNotVer(true);
	});
	//验证用户密码
	$('#merUserPWDTwoNotVer').focusout(function(){
		validateMerUserPWDTwoNotVer(true);
	});
	//验证联系人
	$('#merLinkUserNotVer').focusout(function(){
		validateMerLinkUserNotVer(true);
	});
	//验证手机号码
	$('#merLinkUserMobileNotVer').focusout(function(){
		validateMerLinkUserMobileNotVer(true);
	});
	//验证详细地址
	$('#merAddsNotVer').focusout(function(){
		validateMerAddsNotVer(true);
	});
//	//验证邮箱
//	$('#merEmailNotVer').focusout(function(){
//		validateMerEmailNotVer();
//	});
//	//验证固定电话格式
//	$('#merTelephoneNotVer').focusout(function(){
//		validateMerTelephoneNotVer();
//	});
//	//验证传真
//	$('#merFaxNotVer').focusout(function(){
//		validateMerFaxNotVer();
//	});
//	
//	//验证证件号码
//	$('#merUserIdentityNumberNotVer').focusout(function(){
//		validateMerUserIdentityNumberNotVer();
//	});
//	//验证邮编
//	$('#merZipNotVer').focusout(function(){
//		validateMerZipNotVer();
//	});
//	//验证开户银行账号
//	$('#merBankAccountNotVer').focusout(function(){
//		validateMerBankAccountNotVer();
//	});
//	//验证开户名称
//	$('#merBankUserNameNotVer').focusout(function(){
//		validateMerBankUserNameNotVer();
//	});
});
//----------------------------------------效验审核不通过子商户输入框的所有输入项开始------------------------------
function validateMetTypeNotVer(state){
	var merProNotVerType=$('#merProNotVerType').val();
	//判断是否取到商户类型
	$("#merTypeNotVerERR").hide();
	if(merProNotVerType=='12'){
		var merTypeNotVer = getTrimValue($('#merTypeNotVer').val());
			if(merTypeNotVer==null||merTypeNotVer==""){
				$("#merTypeNotVerERR").html("请选择商户类型").show();
				return false;
			}else {
				$("#merTypeNotVerERR").html("").hide();
				return true;
			}
	}else{
		return true;
	}
}

//效验商户名称
//全局变量
var ismerNameNot = false;
function validateMerNameNotVer(state){
	$("#merNameNotVerERR2").hide();
	var merName = getTrimValue($('#merNameNotVer').val()); 
	ismerNameNot= $.warnHandler(state,true,$.cnEnNo,"merNameNotVer","merNameNotVerERR","输入的店面名称格式不正确",0,50);
	var merCode =$('#merCodeNotVer').val();
	if(ismerNameNot) {
		// 检验是否唯一
		ddpAjaxCall({
			url : "notcheckMerchantNameExist?merCode="+merCode,
			data : merName,
			successFn : function(data) {
				if(data.code == "000000") {
					$("#merNameNotVerERR").hide();
					$("#merNameNotVerERR2").html("").show();
					ismerNameNot=true;
				} else {
					$("#merNameNotVerERR").hide();
					$("#merNameNotVerERR2").html(generateResponseMsg(data)).show();
					ismerNameNot = false;
				}
			}
		});
	} else {
		ismerNameNot=false;
	}
	return ismerNameNot;
}
//验证用户名
var ismerUserNameNot =false;
function validateMerUserNameNotVer(state){
		$("#merUserNameNotVerERR2").hide();
		// 校验用户名是否正确
		var merUserName = getTrimValue($('#merUserNameNotVer').val());
		ismerUserNameNot = $.warnHandler(state,true,$.azNumber,"merUserNameNotVer","merUserNameNotVerERR","输入的用户名格式不正确",3,20)
		var merCode =$('#merCodeNotVer').val();
		if(ismerUserNameNot)  {
			//用户名
			ddpAjaxCall({
				url : "notcheckUserNameExist?merCode="+merCode,
				data : merUserName,
				successFn : function(data) {
					if(data.code == "000000") {
						$("#merUserNameNotVerERR").hide();
						$("#merUserNameNotVerERR2").html("").show();
						ismerUserNameNot =	true;
					} else {
						$("#merUserNameNotVerERR").hide();
						$("#merUserNameNotVerERR2").html(generateResponseMsg(data)).show();
						ismerUserNameNot = false;
					}
				}
			});
		} else {
			ismerUserNameNot=false;
		}
		return ismerUserNameNot;
}
//验证密码
function validateMerUserPWDNotVer(state){
	$("#merUserPWDNotVerERR2").hide();
	var weakPassword = function(value){
		return !jQuery.isNumeric(value);
	}
	
	var checktwo = function(){
		var valid2 = $.warnHandler(true,true,$.password,"merUserPWDNotVer","merUserPWDNotVerERR", "输入的密码格式不正确",6,20) ;
		return valid2;
	};
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:weakPassword,
			valiDatInpId:"merUserPWDNotVer",
			warnMessageElement:"merUserPWDNotVerERR",
			msg:"密码不支持纯数字，请您更换",
			ajaxMethod:checktwo
		};
	
	return $.warnDataFnHandler(data);
	
}
//對比密碼
function validateMerUserPWDTwoNotVer(state) {
	$("#merUserPWDTwoNotVerERR2").hide();
	var validateFn = function(password){
		return $('#merUserPWDNotVer').val() == $('#merUserPWDTwoNotVer').val();
	}
	return $.warnHandler(state,true,validateFn,"merUserPWDTwoNotVer","merUserPWDTwoNotVerERR", "两次输入密码不一致",6,20); 
}

//验证联系人
function validateMerLinkUserNotVer(state){
	return	$.warnHandler(state,true,$.enCn,"merLinkUserNotVer","merLinkUserNotVerERR", "输入的联系人格式不正确",2,20);
}
//验证手机号码
var ismerLinkUserMobileNot=false;
function validateMerLinkUserMobileNotVer(state) {
	$("#merLinkUserMobileNotVerERR2").hide();
	// 校验手机号是否正确 TODO 接口提供修复
	var mobile = getTrimValue($('#merLinkUserMobileNotVer').val());
	ismerLinkUserMobileNot = $.warnHandler(state,true,$.mobile,"merLinkUserMobileNotVer","merLinkUserMobileNotVerERR","输入的手机号码格式不正确");
	var merCode =$('#merCodeNotVer').val();
	 if(ismerLinkUserMobileNot) {
		// 检验是否唯一
		ddpAjaxCall({
			url : "notcheckMobileExist?merCode="+merCode,
			data : mobile,
			successFn : function(data) {
				if(data.code == "000000") {
					$("#merLinkUserMobileNotVerERR").hide();
					$("#merLinkUserMobileNotVerERR2").html("").show();
					ismerLinkUserMobileNot = true;
				} else {
					$("#merLinkUserMobileNotVerERR").hide();
					$("#merLinkUserMobileNotVerERR2").html(generateResponseMsg(data)).show();
					ismerLinkUserMobileNot = false;
				}
			}
		});
	} else {
		ismerLinkUserMobileNot =false;
	}
	return ismerLinkUserMobileNot;
}
//验证地址
function validateMerAddsNotVer(state){
	return $.warnHandler(state,true,$.cnEnNo,"merAddsNotVer","merAddsNotVerERR", "输入的详细地址格式不正确",0,200);
}
//电子邮箱
function validateMerEmailNotVer(state){
	return $.warnHandler(state,false,$.email,"merEmailNotVer","merEmailNotVerERR", "输入的电子邮箱格式不正确");
}
//固定电话
function validateMerTelephoneNotVer(state){
	return $.warnHandler(state,false,$.phone,"merTelephoneNotVer","merTelephoneNotVerERR", "输入的固定电话格式不正确",20);
}
//传真
function validateMerFaxNotVer(state){
	return $.warnHandler(state,false,$.fax,"merFaxNotVer","merFaxNotVerERR", "输入的传真号码格式不正确");
}
//邮编
function validateMerZipNotVer(state){
	return $.warnHandler(state,false,$.zip,"merZipNotVer","merZipNotVerERR", "输入的邮政编码格式不正确");
}
//开户名称
function validateMerBankUserNameNotVer(state){
	return $.warnHandler(state,false,$.enCn,"merBankUserNameNotVer","merBankUserNameNotVerERR", "输入的银行账户名称格式不正确",1,50);
}
//----------------------------------------效验审核不通过子商户输入框的所有输入项结束------------------------------
$(function() {
	//商户类型
	$('#merType').focusout(function(){
		validateMerType(true);
	});
	// 验证商户名称
	$('#merName').focusout(function(){
		validateChildMerName(true);
	});
	//验证用户名
	$('#merUserName').focusout(function(){
		validateChildMerUserName(true);
	});
	//验证用户密码
	$('#merUserPWD').focusout(function(){
		validateMerUserPWD(true);
	});
	//验证用户密码
	$('#merUserPWDTwo').focusout(function(){
		validateMerUserPWD2(true);
	});
	//验证联系人
	$('#merLinkUser').focusout(function(){
		validateMerLinkUser(true);
	});
	//验证手机号码
	$('#merLinkUserMobile').focusout(function(){
		validateChildMerUserMobile(true);
	});
	//验证详细地址
	$('#merAdds').focusout(function(){
		validateMerAdds(true);
	});
});
//----------------------------------------效验创建子商户输入框的所有输入项开始------------------------------
//商户类型
function validateMerType(state){
	var merProType=$('#merProType').val();
	if(merProType=='12'){
		var merType = getTrimValue($('#merType').val());
			if(merType==null||merType==""){
				$("#merTypeERR").html("请选择商户类型").show();
				return  false;
			}else{
				$("#merTypeERR").hide();
				return  true;
			}
	}else{
		return true;
	}
}


//效验商户名称
var isMerName=false;
function validateChildMerName(state){
	$("#merNameERR2").hide();
	var merName = getTrimValue($('#merName').val());
	isMerName= $.warnHandler(state,true,$.cnEnNo,"merName","merNameERR","输入的商户名称格式不正确",0,50);
	if(isMerName) {
		//检验商户名称是否唯一
		ddpAjaxCall({
			url : "checkMerchantNameExist",
			data : merName,
			successFn : function(data) {
				if(data.code == "000000") {
					$("#merNameERR").hide();
					$("#merNameERR2").html("").show();
					isMerName=true;
					
				} else {
					$("#merNameERR").hide();
					$("#merNameERR2").html(generateResponseMsg(data)).show();
					isMerName = false;
				}
			}
		});
	} else {
		isMerName=false;
	}
	return isMerName;
}
//验证用户名
var ismerUserName =false;
function validateChildMerUserName(state){
	$("#merUserNameERR2").hide();
	// 校验用户名是否正确
	var merUserName = getTrimValue($('#merUserName').val());
	ismerUserName= $.warnHandler(state,true,$.azNumber,"merUserName","merUserNameERR","输入的用户名格式不正确",3,20)
	if(ismerUserName)  {
		//用户名
		ddpAjaxCall({
			url : "checkUserNameExist",
			data : merUserName,
			successFn : function(data) {
				if(data.code == "000000" && (data.responseEntity == 'false' || data.responseEntity == false)) {
					$("#merUserNameERR").hide();
					$("#merUserNameERR2").html("").show();
					ismerUserName =	true;
				} else {
					$("#merUserNameERR").hide();
					$("#merUserNameERR2").html(generateResponseMsg(data)).show();
					ismerUserName = false;
				}
			}
		});
	} else {
		ismerUserName=false;
	}
	return ismerUserName;
}
//验证密码
function validateMerUserPWD(state){
	$("#merUserPWDERR2").hide();
	var weakPassword = function(value){
		return !jQuery.isNumeric(value);
	}
	
	var checktwo = function(){
		var valid2 = $.warnHandler(true,true,$.password,"merUserPWD","merUserPWDERR", "输入的密码格式不正确",6,20) ;
		return valid2;
	};
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:weakPassword,
			valiDatInpId:"merUserPWD",
			warnMessageElement:"merUserPWDERR",
			msg:"密码不支持纯数字，请您更换",
			ajaxMethod:checktwo
		};
	
	return $.warnDataFnHandler(data);
	
}
//對比密碼
function validateMerUserPWD2(state) {
	$("#merUserPWDTwoERR2").hide();
	var validateFn = function(password){
		return $('#merUserPWDTwo').val() == $('#merUserPWD').val();
	}
	return $.warnHandler(state,true,validateFn,"merUserPWDTwo","merUserPWDTwoERR", "两次输入密码不一致",6,20); 
}
//验证联系人
function validateMerLinkUser(state){
	return	$.warnHandler(state,true,$.enCn,"merLinkUser","merLinkUserERR", "输入的联系人格式不正确",2,20);
}
//验证手机号码
var isMobile=false;
function validateChildMerUserMobile(state) {
	$("#merLinkUserMobileERR2").hide();
	// 校验手机号是否正确 TODO 接口提供修复
	var mobile = getTrimValue($('#merLinkUserMobile').val());
	 isMobile = $.warnHandler(state,true,$.mobile,"merLinkUserMobile","merLinkUserMobileERR","输入的手机号码格式不正确");
	if(isMobile) {
		// 检验是否唯一
		ddpAjaxCall({
			url : "checkMobileExist",
			data : mobile,
			successFn : function(data) {
				if(data.code == "000000") {
					$("#merLinkUserMobileERR").hide();
					$("#merLinkUserMobileERR2").html("").show();
					isMobile = true;
				} else {
					$("#merLinkUserMobileERR").hide();
					$("#merLinkUserMobileERR2").html(generateResponseMsg(data)).show();
					isMobile = false;
				}
			}
		});
	} else {
		 isMobile =false;
	}
	return isMobile;
	
}
//验证地址
function validateMerAdds(state){
	return $.warnHandler(state,true,$.cnEnNo,"merAdds","merAddsERR", "输入的详细地址格式不正确",0,200);
}
//电子邮箱
function validateMerEmail(state){
	return $.warnHandler(state,false,$.email,"merEmail","emailERR", "输入的邮箱格式不正确");
}
//固定电话
function validateMerTelephone(state){
	return $.warnHandler(state,false,$.phone,"merTelephone","merTelephoneERR", "输入的固定电话格式不正确",20);
}
//传真
function validateMerFax(state){
	return $.warnHandler(state,false,$.fax,"merFax","merFaxERR", "输入的传真号码格式不正确");
}
//邮编
function validateMerZip(state){
	return $.warnHandler(state,false,$.zip,"merZip","merZipERR", "输入的邮政编码格式不正确");
}
//开户名称
function validateMerBankUserName(state){
	return $.warnHandler(state,false,$.enCn,"merBankUserName","merBankUserNameERR", "输入的银行账户名称格式不正确",1,50);
}

//----------------------------------------效验创建子商户输入框的所有输入项结束------------------------------
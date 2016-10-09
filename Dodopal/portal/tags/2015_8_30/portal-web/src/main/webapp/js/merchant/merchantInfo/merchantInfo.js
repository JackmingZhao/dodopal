$(function(){
	//createAreaSelectWithSelectId("sheng","shi");
	createDdicSelectWithSelectId("merBusinessScopeId","BUSINESS_SCOPE");
	createDdicSelectWithSelectId("merUserIdentityType","IDENTITY_TYPE");
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="商户信息"){
			$(this).addClass('cur');
		}
	});
	
	$('.blue-link').hover(function(){
		$('.tit-pop').show();
	},function(){
		$('.tit-pop').hide();
	});
});

function toEdit(){
	showLine();
	$(".error").empty();
	var merCode =  $.trim($("#merCode").val());
	ddpAjaxCall({
		url : "toUpdateMerchantInfo",
		data : merCode,
		successFn : getUpdateInfo
	});
}
function getUpdateInfo(data){
	if("000000"==data.code){
		var merchant = data.responseEntity;
		$("#merLinkUser").val(merchant.merLinkUser)
		$("#merTelephone").val(merchant.merTelephone);
		$("#merFax").val(merchant.merFax);
		$("#merEmail").val(merchant.merEmail);
		var identType = merchant.merchantUserBean.merUserIdentityType==null?"":merchant.merchantUserBean.merUserIdentityType;
		var merBankName = merchant.merBankName==null?"":merchant.merBankName;
		//$("#merUserIdentityType option[value='"+merchant.merchantUserBean.merUserIdentityType+"']").attr("selected",true);
		$("#merUserIdentityNumber").val(merchant.merchantUserBean.merUserIdentityNumber);
		$("#merBankName option[value='"+merBankName+"']").attr("selected",true);
		$("#merBankAccount").val(merchant.merBankAccount);
		$("#merBankUserName").val(merchant.merBankUserName);
		//$("#merBusinessScopeId option[value='"+merchant.merBusinessScopeId+"']").attr("selected",true);
		setDdicCode("merUserIdentityType",merchant.merchantUserBean.merUserIdentityType);
		setDdicCode("merBusinessScopeId",merchant.merBusinessScopeId);
	}
	
}
function showLine(){
	$("#editMerchantInfo").show();
	$("#viewMerchantInfo").hide();

}

function hideLine(){
	$("#editMerchantInfo").hide();
	$("#viewMerchantInfo").show();

}
/**
 * 取消
 */
function cancelEdit(){
	hideLine();
	$(".error").empty();
	$("#merTelephoneWarn").empty();
	$("#merLinkUserWarn").empty();
	$("#merFaxSpanWarn").empty();
	$("#merEmailWarn").empty();
	$("#merUserIdentityNumberWarn").empty();
	$("#merUserIdentityTypeWarn").empty();
	$("#merBankNameWarn").empty();
	$("#merBankAccountWarn").empty();
	$("#merBankUserNameWarn").empty();
	
	
	
}

/**
 * 检查联系人
 */
function checkLinkUser(state){
	var data ={submitState:state,
			required:true,
			warnCheckFn:$.enCn,
			valiDatInpId:"merLinkUser",
			warnMessageElement:"merLinkUserWarn",
			msg:"输入的联系人格式不正确",
			mixlength:"2",
			maxlength:"20"
		};
	return $.warnDataFnHandler(data);
	//return $.warnHandler(state,true,$.enCn,"merLinkUser","merLinkUserWarn", "长度在2-20个字节,由中英文组成",2,20);
//	var merLinkUser=$.trim($("#merLinkUser").val());
//	if(merLinkUser==""){
//		$.validationHandler(false, 'merLinkUserWarn', '请输入此项');
//		return;
//	}
//	//联系人校验
//	var islength = $.enCn(merLinkUser,2,20);
//	$.validationHandler(islength, 'merLinkUserWarn', '长度在2-20个字节,由中英文组成');
//	return islength;
}

/**
 * 检查固定电话
 */
function checkPhone(state){
	return $.warnHandler(state,false,$.phone,"merTelephone","merTelephoneWarn", "输入的固定电话格式不正确");
//	var merTelephone=$.trim($("#merTelephone").val());
//	//固定电话不为空的话校验格式
//	if(isNotBlank(merTelephone)){
//		var isPhone = $.phone(merTelephone);
//		$.validationHandler(isPhone, 'merTelephoneWarn', '固定电话号码格式有误，例：021-8888888');
//		return isPhone;
//	}else{
//		$("#merTelephoneWarn").empty();
//		return true;
//	}
}

function checkZip(state){
	return $.warnHandler(state,false,$.zip,"merZip","merZipWarn", "输入的邮编格式不正确");
}
/**
 * 检查传真
 */
function checkFax(state){
	return $.warnHandler(state,false,$.fax,"merFax","merFaxSpanWarn", "输入的传真号码格式不正确");
//	var merFax=$.trim($("#merFax").val());
//	if(isNotBlank(merFax)){
//		var isMerFax = $.fax(merFax);
//		$.validationHandler(isMerFax, 'merFaxSpanWarn', '传真号码格式有误，例：021-8888888');
//		return isMerFax;
//	}else{
//		$("#merFaxSpanWarn").empty();
//		return true;
//	}
}
/**
 * email
 */
function checkEmail(state){
	return $.warnHandler(state,false,$.email,"merEmail","merEmailWarn", "输入的电子邮箱格式不正确");
//	var merEmail=$.trim($("#merEmail").val());
//	if(isNotBlank(merEmail)){
//		var isMerEmail = $.email(merEmail);
//		$.validationHandler(isMerEmail, 'merEmailWarn', '电子邮箱格式有误，例：xxxx@xx.com');
//		return isMerEmail;
//	}else{
//		$("#merEmailWarn").empty();
//		return true;
//	}
}



/**
 * 开户名称
 */
function checkMerBankUserName(state){
	return $.warnHandler(state,false,$.enCn,"merBankUserName","merBankUserNameWarn", "输入的开户名称格式不正确",0,50);
//	var merBankUserName=$.trim($("#merBankUserName").val());
//	if(isNotBlank(merBankUserName)){
//		var isBankUserName = $.cn(merBankUserName,0,50);	
//		$.validationHandler(isBankUserName, 'merBankUserNameWarn', '开户名称仅限汉字,长度小于50字');
//		return isBankUserName;
//	}else{
//		$("#merBankUserNameWarn").empty();
//		return true;
//	}
}
function updateInfo(){
	if(!checkValue()){
		return;
	}
	//加入权限校验
	if(!hasPermission('merchant.info.modify')){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "你没有权限进行该操作"});
		return;
	}
	var merLinkUser=$.trim($("#merLinkUser").val());
	var merTelephone=$.trim($("#merTelephone").val());
	var merFax=$.trim($("#merFax").val());
	var merEmail=$.trim($("#merEmail").val());
	var merUserIdentityType=$.trim($("#merUserIdentityType").val());
	var merUserIdentityNumber=$.trim($("#merUserIdentityNumber").val());
	var merBankName=$.trim($("#merBankName").val());
	var merBankAccount=$.trim($("#merBankAccount").val());
	var merBankUserName=$.trim($("#merBankUserName").val());
	var merBusinessScopeId=$.trim($("#merBusinessScopeId").val());
	var id = $.trim($("#id").val());
	var merCode= $.trim($("#merCode").val());
	var merZip= $.trim($("#merZip").val());
	
	if(checkMerLinkUser(merLinkUser)){
		return;
	}
	var map = {
			id:id,
			merLinkUser: merLinkUser,
			merTelephone : merTelephone,
			merFax : merFax,
			merEmail : merEmail,
			merUserIdentityType:merUserIdentityType,
			merUserIdentityNumber:merUserIdentityNumber,
			merBankName:merBankName,
			merBankAccount:merBankAccount,
			merBankUserName:merBankUserName,
			merBusinessScopeId:merBusinessScopeId,
			merCode:merCode,
			merZip:merZip
	};
	$.messagerBox({type:"confirm", title:"信息提示", msg: "确认要保存此次编辑信息吗？", confirmOnClick: updateCallBack, param:map});
}
function updateCallBack(map){
	ddpAjaxCall({
		url : "updateMerchantInfo",
		data : map,
		successFn : afertUpdate
	});
}


function checkValue(){
	return checkLinkUser(true)&&checkPhone(true)
	&&checkFax(true)
	&&checkEmail(true)&&checkZip(true)
	&&checkIdentityNumber(true,'merUserIdentityType','merUserIdentityNumber','merUserIdentityNumberWarn')
	&&checkMerBankAccount(true,'merBankName','merBankAccount','merBankAccountWarn')&&checkMerBankUserName(true);
}

function checkMerLinkUser(merLinkUser){
	if(merLinkUser==""){
		$("#merLinkUserWarn").html("请输入此项");
		return true;
	}
	$("#merLinkUserWarn").empty();
	return false;
}

function afertUpdate(data){
	if(data.code == '000000') {
		window.location.href ="showMerchantInfo";
	} else {
		$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
	}
}
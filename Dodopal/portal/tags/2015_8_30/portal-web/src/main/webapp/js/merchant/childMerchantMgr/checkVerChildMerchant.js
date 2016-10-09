//$(function() {
//	//验证邮箱地址
//	$('#merEmailVer').focusout(function(){
//		validateMerEmailVer();
//	});
//	//验证固定电话格式
//	$('#merTelephoneVer').focusout(function(){
//		validateMerTelephoneVer();
//	});
//	//验证传真
//	$('#merFaxVer').focusout(function(){
//		validateMerFaxVer();
//	});
//	//验证邮编
//	$('#merZipVer').focusout(function(){
//		validateMerZipVer();
//	});
//	//验证开户名称
//	$('#merBankUserNameVer').focusout(function(){
//		validateMerBankUserNameVer();
//	});
//});
//----------------------------------------效验已审核子商户输入框的所有输入项开始------------------------------
//电子邮箱
function validateMerEmailVer(state){
	return $.warnHandler(state,false,$.email,"merEmailVer","merEmailVerERR", "输入的账户名称格式不正确");
}
//固定电话
function validateMerTelephoneVer(state){
	return $.warnHandler(state,false,$.phone,"merTelephoneVer","merTelephoneVerERR", "输入的固定电话格式不正确",20);
}
//传真
function validateMerFaxVer(state){
	return $.warnHandler(state,false,$.fax,"merFaxVer","merFaxVerERR", "输入的传真号码格式不正确");
}
//邮编
function validateMerZipVer(state){
	return $.warnHandler(state,false,$.zip,"merZipVer","merZipVerERR", "输入的邮政格式不正确");
}
//开户名称
function validateMerBankUserNameVer(state){
	return $.warnHandler(state,false,$.enCn,"merBankUserNameVer","merBankUserNameVerERR", "输入的银行卡名称格式不正确",1,50);
}
//----------------------------------------效验已审核子商户输入框的所有输入项结束------------------------------
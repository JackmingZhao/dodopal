
/**
 * 检查身份证信息
 * 
 * state(必传)提交状态true代表表单提交，false代表焦点触发
 * identiTypeId：证件类型的id
 * identiNumId：证件号码的id
 * msgId：错误提示的id
 */
var carErrorMessage = "输入的驾照格式不正确";
var pleaseMessageIdentNum = "请选择证件类型";
var idCardErrorMessage = "输入的身份证号码格式不正确";
var otherIdentErrorMessage = "输入的护照格式不正确";

var pleaseBankChooseMessage = "请选择一个开户银行";
var accountErrorMessage = "输入的银行卡号格式不正确";

function checkIdentityNumber(state,identiTypeId,identiNumId,msgId){
	identiTypeId = "#"+identiTypeId;
	identiNumId = "#"+identiNumId;
	msgId = "#"+msgId;
	var merUserIdentityType=$.trim($(identiTypeId).val());
	var merUserIdentityNumber=$.trim($(identiNumId).val());
	if(state){
	  return checkIdentiNum(identiTypeId,identiNumId,msgId);
	}
	$(identiNumId).blur(function (){
		if($.trim($(identiNumId).val())!=""&&$.trim($(identiTypeId).val())==""){
			//证件号码输入，证件类型未选择
			$(msgId).removeClass().addClass('tip-red-error').html(pleaseMessageIdentNum);
			return false;
		}
		if($.trim($(identiNumId).val())==""){
			//身份证为空不校验
			$(msgId).removeClass().addClass('tip-error').html('');
			return true;
		}else{
			if($.trim($(identiTypeId).val())=="0"){//身份证
				var isMerUserIdentityNumber = $.identityNum($.trim($(identiNumId).val()));
				if(!isMerUserIdentityNumber){
					$(msgId).removeClass().addClass('tip-red-error').html(idCardErrorMessage);
				}else{
					$(msgId).removeClass().addClass('tip-error').html('');
				}
				return isMerUserIdentityNumber;
			}else if($.trim($(identiTypeId).val())=="1"){//驾驶证
				var isMerUserIdentityNumber = $.identityNum($.trim($(identiNumId).val()));
				if(!isMerUserIdentityNumber){
					$(msgId).removeClass().addClass('tip-red-error').html(carErrorMessage);
				}else{
					$(msgId).removeClass().addClass('tip-error').html('');
				}
				return isMerUserIdentityNumber;
			}else{//护照
				var isMerUserIdentityNumber = $.zjNumber($.trim($(identiNumId).val()));
				if(!isMerUserIdentityNumber){
					$(msgId).removeClass().addClass('tip-red-error').html(otherIdentErrorMessage);
				}else{
					$(msgId).removeClass().addClass('tip-error').html('');
				}
				return isMerUserIdentityNumber;
			}
		}
	});
	if(isNotBlank(merUserIdentityType)){//如果证件类型不为空
			$(msgId).removeClass().addClass('tip-error').html($(identiNumId).attr("myPlaceholder"));
			return false;			
	}else if($.trim($(identiTypeId).val())==""){//证件类型为空
		if(isNotBlank(merUserIdentityNumber)){
			$(msgId).removeClass().addClass('tip-error').html(pleaseMessageIdentNum);
			return false;
		}else{
			$(msgId).empty();
			$(msgId).removeClass().addClass('tip-error').html(pleaseMessageIdentNum);
			return false;
		}
	}
}


function checkIdentiNum(identiTypeId,identiNumId,msgId){
	if($.trim($(identiNumId).val())!=""&&$.trim($(identiTypeId).val())==""){
		//只写了证件号码，没有选择证件类型
		$(msgId).removeClass().addClass('tip-red-error').html(pleaseMessageIdentNum);
		return false;
	}
	if($.trim($(identiNumId).val())!=""){
		if($.trim($(identiTypeId).val())=="0"){//身份证
				var isMerUserIdentityNumber = $.identityNum($.trim($(identiNumId).val()));
				if(!isMerUserIdentityNumber){
					$(msgId).removeClass().addClass('tip-red-error').html(idCardErrorMessage);
				}else{
					$(msgId).removeClass().addClass('tip-error').html('');
				}
				return isMerUserIdentityNumber;
			}else	if($.trim($(identiTypeId).val())=="1"){//驾驶证
				if($.trim($(identiNumId).val())==""){
					//身份证为空不校验
					$(msgId).removeClass().addClass('tip-error').html('');
					return true;
				}
				var isMerUserIdentityNumber = $.identityNum($.trim($(identiNumId).val()));
				if(!isMerUserIdentityNumber){
					$(msgId).removeClass().addClass('tip-red-error').html(carErrorMessage);
				}else{
					$(msgId).removeClass().addClass('tip-error').html('');
				}
				return isMerUserIdentityNumber;
			}else{//护照
				var isMerUserIdentityNumber = $.zjNumber($.trim($(identiNumId).val()));
				//中英文校验通过true
				if(!isMerUserIdentityNumber){
					//!false不通过时
					$(msgId).removeClass().addClass('tip-red-error').html(otherIdentErrorMessage);
				}else{
					$(msgId).removeClass().addClass('tip-error').html('');
				}
				return isMerUserIdentityNumber;
			}
	}else{
		$(msgId).removeClass().addClass('tip-error').html('');
		return true;
	}
}
/**
 * 银行账号
 *  state(必传)提交状态true代表表单提交，false代表焦点触发
 * merBankNameId：银行类型的id
 * merBankAccountId：银行号码的id
 * msgId：错误提示的id
 * 
 */
function checkMerBankAccount(state,merBankNameId,merBankAccountId,msgId){
	merBankAccountId = "#"+merBankAccountId;
	merBankNameId = "#"+merBankNameId;
	msgId="#"+msgId;
	var merBankAccount=$.trim($(merBankAccountId).val());
	var merBankName=$.trim($(merBankNameId).val());
	if(state){
		if(isNotBlank($.trim($(merBankAccountId).val()))){//账号输入
			$(msgId).empty();
			if($.trim($(merBankNameId).val())==""){
				$(msgId).removeClass().addClass('tip-red-error').html(pleaseBankChooseMessage);
				return false;
			}
			var isBanknumber = $.banknumber($.trim($(merBankAccountId).val()));
			if(!isBanknumber){
				$(msgId).removeClass().addClass('tip-red-error').html(accountErrorMessage);
			}else{
				$(msgId).removeClass().addClass('tip-error').html('');
			}
			return isBanknumber;
		}else{
			$(msgId).empty();
			return true;
		}
	}
	$(merBankAccountId).blur(function (){
		if(isNotBlank($.trim($(merBankAccountId).val()))){//账号输入
			$(msgId).empty();
			if($.trim($(merBankNameId).val())==""){
				$(msgId).removeClass().addClass('tip-red-error').html(pleaseBankChooseMessage);
				return false;
			}
			var isBanknumber = $.banknumber($.trim($(merBankAccountId).val()));
			if(!isBanknumber){
				$(msgId).removeClass().addClass('tip-red-error').html(accountErrorMessage);
			}else{
				$(msgId).removeClass().addClass('tip-error').html('');
			}
			return isBanknumber;
		}else{
			$(msgId).empty();
			return true;
		}
	});
	if(isNotBlank(merBankName)){//开户行选择
			$(msgId).removeClass().addClass('tip-error').html($(merBankAccountId).attr("myPlaceholder"));
			return false;
	}else{//开户行没选
//		if(isNotBlank(merBankAccount)){//银行账号不为空
//			$(msgId).removeClass().addClass('tip-error').html('请选择一个开户银行');
//			return false;
//		}else{
			$(msgId).empty();
			$(msgId).removeClass().addClass('tip-error').html(pleaseBankChooseMessage);
			return false;
//		}
	}
}
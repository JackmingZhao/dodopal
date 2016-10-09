function validateDepId(merGroupUser,state){
	return $.warnHandler(state,true,$.numberEnCn,"depId","depIdVal", "请选择部门",1,30);
}

function validateGpUserName(merGroupUser,state){
	return $.warnHandler(state,true,$.enCn,"gpUserName","gpUserNameVal", "输入的姓名格式不正确",2,20);
}

function validateMobiltle(merGroupUser,state){
	return $.warnHandler(state,true,$.mobile,"mobiltle","mobiltleVal", "输入的手机号码格式不正确",11,11);
}

var cardCodeDuplicated = false;
function validateCardCode(merGroupUser,state){
	var emp = $('input[name=empType]:checked').val();
	
	var ajaxMethod = function(){
		if(emp==1){
			cardCodeDuplicated = false;
			return true;
		}
		var merGroupUser = {
				id : $('#merGroupUserId').val(),
				cardCode : $.trim($('#cardCode').val())
		}
		
		ddpAjaxCall({
			async : false,
			url : "checkMerGroupUserCardExist",
			data : merGroupUser,
			successFn : function(data) {
				console.log(data);
				if(data.code == "120040") {
					$.validationHandler(true, 'cardCodeVal');
					$.validationHandler(false, 'cardCodeVal', "卡号已被使用");
					cardCodeDuplicated = true;
				} else {
					cardCodeDuplicated = false;
				}
			}
		});
		return true;
	}
	
	var data ={submitState:state,
			required:false,
			warnCheckFn:jQuery.isNumeric,
			valiDatInpId:"cardCode",
			warnMessageElement:"cardCodeVal",
			msg:"输入的公交卡号格式不正确",
			mixlength:"1",
			maxlength:"20",
			ajaxMethod:ajaxMethod
		};
	
	return $.warnDataFnHandler(data);
	
}

function validatePhone(merGroupUser,state){
	return $.warnHandler(state,false,$.phone,"phone","phoneVal", "输入的固定电话格式不正确",1,20);
}

function validateRechargeAmount(merGroupUser,state){
	return $.warnHandler(state,true,$.rechargeAmt,"rechargeAmount","rechargeAmountVal", "输入的充值金额格式不正确",1,20);
}

function validateEmployeeDate(state){
	return $.warnHandler(state,false,$.date,"employeeDate","employeeDateVal", "非法日期格式",1,20);
}

function validateIdentityNum(merGroupUser,state){
	return $.warnHandler(state,false,$.identityNum,"identityNum","identityNumVal", "输入的身份证号码格式不正确",1,18);
}

function validateMerGroupUser(merGroupUser){
	var formValid = true;
	formValid = validateDepId(merGroupUser,true) && formValid;
	formValid = validateGpUserName(merGroupUser,true) && formValid;
	formValid = validateMobiltle(merGroupUser,true) && formValid;
	formValid = validateCardCode(merGroupUser,true) && formValid;
	formValid = validateRechargeAmount(merGroupUser,true) && formValid;
	formValid = validateEmployeeDate(true) && formValid;
	formValid = validateIdentityNum(merGroupUser,true) && formValid;
	formValid = validatePhone(merGroupUser,true) && formValid;
	formValid = !cardCodeDuplicated && formValid;
	return formValid;
}


function addFocusoutEvent(){
	$('#depId').focus(function(){
		var merGroupUser = getFormValues();
		validateDepId(merGroupUser,false);
	});
	
	$('#gpUserName').focus(function(){
		var merGroupUser = getFormValues();
		validateGpUserName(merGroupUser,false);
	});
	
	$('#mobiltle').focus(function(){
		var merGroupUser = getFormValues();
		validateMobiltle(merGroupUser,false);
	});
	
	$('#cardCode').focus(function(){
		var merGroupUser = getFormValues();
		validateCardCode(merGroupUser,false);
	});
	
	$('#rechargeAmount').focus(function(){
		var merGroupUser = getFormValues();
		validateRechargeAmount(merGroupUser,false);
	});
	
	$('#identityNum').focus(function(){
		var merGroupUser = getFormValues();
		validateIdentityNum(merGroupUser,false);
	});
	
	$('#employeeDate').focus(function(){
		validateEmployeeDate(false);
	});
	
	$('#phone').focus(function(){
		var merGroupUser = getFormValues();
		validatePhone(merGroupUser,false);
	});
	
}
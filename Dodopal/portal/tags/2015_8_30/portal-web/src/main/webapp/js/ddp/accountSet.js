$(function(){
	createDdicSelectWithSelectId("merUserIdentityType","IDENTITY_TYPE");
	highlightTitle();
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="账户设置"){
			$(this).addClass('cur').siblings("a").removeClass("cur");
		}
	});
	findMerCitys();
});



function toEdit(){
	showLine();
	$(".error").empty();
	var id =  $.trim($("#id").val());
	ddpAjaxCall({
		url : "toUpdateAccountSetInfo",
		data : id,
		successFn : getUpdateInfo
	});
}

function getUpdateInfo(data){
	if("000000"==data.code){
		var accountSet = data.responseEntity;
		$("#merUserNickName").val(accountSet.merUserNickName);
		$("#merUserEmail").val(accountSet.merUserEmail);
		$("input[name='merUserSex'][value="+accountSet.merUserSex+"]").attr("checked",true);
		//$("input[name='merUserSex']:checked").val(accountSet.merUserSex);
		var identType = accountSet.merUserIdentityType==null?"":accountSet.merUserIdentityType;
		$("#merUserIdentityNumber").val(accountSet.merUserIdentityNumber);
		$("#merUserAdds").val(accountSet.merUserAdds);
		setDdicCode("merUserIdentityType",accountSet.merUserIdentityType);
	}
}

function showLine(){
	$("#editAccountSetInfo").show();
	$("#viewAccountSetInfo").hide();

}

function hideLine(){
	$("#viewAccountSetInfo").show();
	$("#editAccountSetInfo").hide();

}

function closeEdit(){
	hideLine();
	$(".error").empty();
	$("#merUserNickNameWarn").empty();
	$("#merUserEmailWarn").empty();
	$("#merUserSexWarn").empty();
	$("#merEmailWarnWarn").empty();
	$("#merUserIdentityTypeWarn").empty();
	$("#merUserIdentityNumber").empty();
	$("#merUserAdds").empty();
}

function updateInfo(){
	if(!checkValue()){
		return;
	}
	//加入权限校验
	if(!hasPermission('merchant.user.modify')){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "你没有权限进行该操作"});
		return;
	}
	var merUserNickName=$.trim($("#merUserNickName").val());
	var merUserEmail=$.trim($("#merUserEmail").val());
	var merUserSex=$.trim($("input[name='merUserSex']:checked").val());
	var merUserIdentityType=$.trim($("#merUserIdentityType").val());
	var merUserIdentityNumber=$.trim($("#merUserIdentityNumber").val());
	var merUserAdds=$.trim($("#merUserAdds").val());
	var id = $.trim($("#id").val());
	var map = {
			id:id,
			merUserNickName: merUserNickName,
			merUserEmail : merUserEmail,
			merUserSex:merUserSex,
			merUserIdentityType:merUserIdentityType,
			merUserIdentityNumber:merUserIdentityNumber,
			merUserAdds:merUserAdds,
	};
	$.messagerBox({type:"confirm", title:"信息提示", msg: "确认要保存此次编辑信息吗？", confirmOnClick: updateCallBack, param:map});
}

function updateCallBack(map){
//	updateMerUserBusCity();
	ddpAjaxCall({
		url : "updateAccountSetInfo",
		data : map,
		successFn : afertUpdate
	});
}
function updateMerUserBusCity(){
	var cityCode = $.trim($("#merChantCity").val());
	ddpAjaxCall({
		url : "updateMerUserBusCity?cityCode="+cityCode,
		data : '',
		successFn : function(data){
			window.location.href ="showAccountSetInfo?flag=1&cityCode="+cityCode;
		}
	});
}

function afertUpdate(data){
	if(data.code == '000000') {
		updateMerUserBusCity();
	} else {
		$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
	}
}

/**
 * 检查联系人
 */
function checkLinkUser(state){
	var data ={submitState:state,
			required:true,
			warnCheckFn:$.enCn,
			valiDatInpId:"merUserNickName",
			warnMessageElement:"merUserNickNameWarn",
			msg:"输入的联系人格式不正确",
			mixlength:"2",
			maxlength:"20"
		};
	return $.warnDataFnHandler(data);
}

/**
 * 检查地址
 */
function checkAdds(state){
	var data ={submitState:state,
			required:true,
			warnCheckFn:$.enCn,
			valiDatInpId:"merUserAdds",
			warnMessageElement:"merUserAddsWarn",
			msg:"输入的地址格式不正确",
			mixlength:"1",
			maxlength:"20"
		};
	return $.warnDataFnHandler(data);
}

/**
 * email
 */
function checkEmail(state){
	return $.warnHandler(state,false,$.email,"merUserEmail","merUserEmailWarn", "输入的电子邮箱格式不正确");
}

function checkValue(){
	return checkLinkUser(true)
	&&checkEmail(true)&&checkAdds(true)
	&&checkIdentityNumber(true,'merUserIdentityType','merUserIdentityNumber','merUserIdentityNumberWarn')
}

//初始化业务城市
function findMerCitys() {
	ddpAjaxCall({
				url : "findMerCitys",
				data : '',
				successFn : function(data) {
					$.each(data.responseEntity, function(key, value) {
								$('#merChantCity').append($("<option/>", {
											value : value.cityCode,
											text : value.cityName
										}));
							});

				}
			});
}

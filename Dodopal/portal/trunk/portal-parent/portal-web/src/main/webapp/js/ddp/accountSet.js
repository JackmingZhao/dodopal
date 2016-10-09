$(function(){
	createDdicSelectWithSelectId("merUserIdentityType","IDENTITY_TYPE");
	createDdicSelectWithSelectId("education","EDUCATION_TYPE");
	createDdicSelectWithSelectId("isMarried","IS_MARRIED_TYPE");
	
	highlightTitle();
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="账户设置"){
			$(this).addClass('cur').siblings("a").removeClass("cur");
		}
	});
	findMerCitys();
	
	$("#income").bind('keyup', function() {
		clearNoNum($(this));
	});
	
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
	findMerCitys();
	if("000000"==data.code){
		var accountSet = data.responseEntity;
		$("#merUserNickName").val(accountSet.merUserNickName);
		$("#merUserEmail").val(accountSet.merUserEmail);
		$("input[name='merUserSex'][value="+accountSet.merUserSex+"]").attr("checked",true);
		//$("input[name='merUserSex']:checked").val(accountSet.merUserSex);
		var identType = accountSet.merUserIdentityType==null?"":accountSet.merUserIdentityType;
		$("#merUserIdentityNumber").val(accountSet.merUserIdentityNumber);
		$("#merUserAdds").val(accountSet.merUserAdds);
		
		setDdicCode("education",accountSet.education);
		setDdicCode("isMarried",accountSet.isMarried);
		$("#birthday").val(accountSet.birthday);
		$("#income").val(accountSet.incomeView);
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
	 location.reload();
}

function updateInfo(){
	if(!checkValue()){
		return;
	}
	//加入权限校验
	if(!hasPermission('ddp.acct.base')){
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
	
	var birthday = $("#birthday").val();
	var education = $("#education").val();
	var isMarried = $("#isMarried").val();
	var v = $("#income").val();
	var income = '';
	if(v != ''){
		 income = v* 100;
	}
	var map = {
			id:id,
			merUserNickName: merUserNickName,
			merUserEmail : merUserEmail,
			merUserSex:merUserSex,
			merUserIdentityType:merUserIdentityType,
			merUserIdentityNumber:merUserIdentityNumber,
			merUserAdds:merUserAdds,
			birthday:birthday,
			education:education,
			isMarried:isMarried,
			income:income
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
	var cityName = $.trim($("#merChantCity").find("option:selected").text());
	var city = {
			cityCode : cityCode,
			cityName : cityName
	};
	ddpAjaxCall({
		url : "updateMerUserBusCity",
		data : city,
		successFn : function(data){
			window.location.href ="showAccountSetInfo?flag=1&cityCode="+cityCode+"&cityName="+cityName;
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
//function checkAdds(state){
//	return $.warnHandler(state,true,$.cnEnNo,"merUserAdds","merUserAddsWarn", "输入的地址格式不正确",0,200);
//}

/**
 * email
 */
function checkEmail(state){
	return $.warnHandler(state,false,$.email,"merUserEmail","merUserEmailWarn", "输入的电子邮箱格式不正确");
}

function checkValue(){
	return checkLinkUser(true)
	&&checkEmail(true)
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
					var street = $("input[name='sessionCity']").val();
					if(street!=null){
						$('#merChantCity option:contains(' + street + ')').each(function(){
							if ($(this).text() == street) {
								$(this).attr('selected', true);
							}
						});
					}
				}
			});
	selectUiInit();
}

/*校验金额*/
function clearNoNum(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 7  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,6)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 6) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 9) {
			text = "";
		}
		obj.val(text);
	}
}

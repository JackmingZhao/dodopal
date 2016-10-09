// ---------------------------------------------子商户开户保存--------------------------
function checkValue() {
	// 商户名称
	var isMerName = validateChildMerName(true);
	// 用户名称
	var ismerUserName = validateChildMerUserName(true);
	
	//当创建的是连锁直营网点 且 是自动转账 额度和阀值是必填的
	var bitian = false;
	var isAuto = '';
	$('input[name=isAuto]').each(function(i,v){
		if($(v).is(':checked')){
			isAuto=$(v).val();
		}
	});
	
	if(isAuto=='0'){
		//额度阀值
		var isThreshold = validateChildThreshold(true);
		//额度
		var isLimit = validateChildLimit(true);
		if(isThreshold && isLimit){
			bitian= true;
		}
	}else{
		bitian= true;
	}

	
	// 手机号码
	var isMobile = validateChildMerUserMobile(true);
	var isChildMerName = $('#merNameERR2').html();
	var isChildMerUserName = $('#merUserNameERR2').html();
	var isChildMerUserMobile = $('#merLinkUserMobileERR2').html();
	var isWeiYi = false;
	if (isBlank(isChildMerName) && isBlank(isChildMerUserName)
			&& isBlank(isChildMerUserMobile)) {
		isWeiYi = true;
	}
	var isWeiYiAjax = false;
	if (isMerName && ismerUserName && isMobile) {
		isWeiYiAjax = true;
	}
	var isMerType = validateMerType(true);
	var isMerUserPWD = validateMerUserPWD(true);
	var isMerUserPWD2 = validateMerUserPWD2(true);
	var isMerLinkUser = validateMerLinkUser(true);
	var isMerAdds = validateMerAdds(true);
	return isMerType && isMerUserPWD && isMerUserPWD2 && isMerLinkUser
			&& isWeiYi && isWeiYiAjax && isMerAdds && bitian;

}
function checkNotRequired() {
	var isMerEmail = validateMerEmail(true);
	var isMerTelephone = validateMerTelephone(true);
	var isMerFax = validateMerFax(true);
	var isMerZip = validateMerZip(true);
	var isMerBankUserName = validateMerBankUserName(true);
	var isIdentityNumber = checkIdentityNumber(true, 'merUserIdentityType',
			'merUserIdentityNumber', 'merUserIdentityNumberERR');
	var isMerBankAccount = checkMerBankAccount(true, 'merBankName',
			'merBankAccount', 'merBankAccountERR');;
	return isMerEmail && isMerTelephone && isMerFax && isMerZip
			&& isMerBankUserName && isIdentityNumber && isMerBankAccount;
}
// 根据商户类型显示商户费率
function checkMerType() {
	var merType = $('#merType').val();
	if (merType != null || merType != "") {
		$("#merTypeERR").html('').hide();
	}

	findPrdRateType();
	if (merType == '13') {
		
		$('#isAutoShow').show();
		//$('#thresholdShow').hide();
        //$('#isAutoJoinShow').hide();
    	//$('input[name=isAuto][value=1').prop('checked', true);
        
		var prdRateTypeAll = $('#PrdRateType').find("input[type=checkbox]");
		$.each(prdRateTypeAll, function(index, element) {
		        $('#' + element.id).attr("disabled", false);
		        $('#' + element.id).attr("checked",'true');
		        $('#' + element.id).attr("disabled", true);
		});
//		$("#PrdRateType").find("input[type=checkbox]").attr("disabled", false);
//		$("#PrdRateType").find("input[type=checkbox]").attr("checked","checked");
//		$("#PrdRateType").find("input[type=checkbox]").attr("disabled", true);
		$.ajax({
					async : false,
					dataType : "json",
					type : 'post',
					url : "findProductList?merType=" + merType,
					success : function(data) {
						addMerTypeRateType(data);
					}
				});
		$("#xuanzhe").attr("disabled", true);
		$("#xuanzhe").unbind('click');
	} else {
		$('#isAutoShow').show();
		//$('#thresholdShow').hide();
       // $('#isAutoJoinShow').show();
        
		$("#PrdRateType").find("input[type=checkbox]").attr("checked", false);
		$("#PrdRateType").find("input[type=checkbox]").attr("disabled", false);
		$("#xuanzhe").attr("disabled", false);
		$('#YKTCodeADD').val('');
		$('#yktRateTypeTable tbody').empty();
		$('#yktRateTypeTable').hide();
		$("#yktTableLine").hide();
		$('#rateTypeView').hide();
		$("#xuanzhe").bind("click", function() {
					yktInfo();
				});
	}
}

//新增子商户页面  连锁商户直营网点和 加盟网点  自动转账的额度和阀值- 切换
function isAutoClick(flag){
	if(flag=='true'){
		$('#thresholdShow').show();
	}else{
		$('#thresholdShow').hide();
	}
	
}


// 保存子商户信息
function saveChildMerchant() {
	// 效验必填项
	if (!checkValue()) {
		return false;
	}
	// 效验非必填项
	if (!checkNotRequired()) {
		return false;
	}
	// 商户业务信息
	var merRateSpmtList = new Array();
	// 业务类型
	var prdRateTypeAll = $('#PrdRateType').find("input:checkbox:checked");
	$.each(prdRateTypeAll, function(index, element) {
				var rateCode = $('#' + element.id).val();
				var merRateSpmtBean = {
					rateCode : rateCode
				}
				merRateSpmtList.push(merRateSpmtBean);
			});

	var pw1 = $('#merUserPWD').val();
	var merBusRateBeanList = new Array();
	var merPro = getProvinceValue('provinceAdd');
	var merCity = getCityValue('cityAdd');
	var addCheck = $("input[name=addCheckbox]").val();
	if (addCheck) {
		$("#yktRateTypeTable  tbody tr").each(function() {
					var proCode = $(this).find("input[name=proCode]").val();
					var rateCode = $(this).find("input[name=rateCode]").val();
					var rateType = $(this).find("input[name=rateType]").val();
					var rate = $(this).find("input[name=rateChild]").val();
					var merBusRateBean = {
						rateType : rateType,
						rate : parseFloat(rate),
						proCode : proCode,
						rateCode : rateCode
					}
					merBusRateBeanList.push(merBusRateBean);
				});
	}
	
	var isAuto = '';
	$('input[name=isAuto]').each(function(i,v){
		if($(v).is(':checked')){
			isAuto=$(v).val();
		}
	});
	
//	var isAutoJoin ='';  
//	$('input[name=isAutoJoin]').each(function(i,v){
//		if($(v).is(':checked')){
//			isAutoJoin=$(v).val();
//		}
//	});
//	
	//商户补充信息
	var merDdpInfoBean ={
		isAutoDistribute: isAuto
	//	limitSource : isAutoJoin
	};
	
	var merAutoAmtBean = null;
	
	if(isAuto=='0'){
		 //自动转账额度和阀值
		 merAutoAmtBean = {
				limitThreshold: $('#threshold').val(),
				autoLimitThreshold:$('#limit').val()
		};
	}

	var merchantUserBean = {
		merUserName : $('#merUserName').val(),
		merUserNickName : $('#merLinkUser').val(),
		merUserMobile : $('#merLinkUserMobile').val(),
		merUserAdds : $('#merAdds').val(),
		merUserPWD : md5(md5(pw1)),
		merUserTelephone : $('#merTelephone').val(),
		merUserIdentityType : $('#merUserIdentityType').val(),
		merUserIdentityNumber : $('#merUserIdentityNumber').val(),
		merUserEmail : $('#merEmail').val(),
		education : $('#education').val(),
		income : $('#income').val()==''? '' : Number($('#income').val())*100,
		birthday : $('#birthday').val(),
		isMarried : $('#isMarried').val(),
		merUserRemark : $('#merUserRemark').val()
	};
	var merchantBeans = {
		merCode : $('#merCode').val(),
		merName :$.trim($('#merName').val()),
		merType : $('#merType').val(),
		activate : '0',
		merPro : merPro,
		merCity : merCity,
		merLinkUser : $('#merLinkUser').val(),
		merLinkUserMobile : $('#merLinkUserMobile').val(),
		merAdds : $('#merAdds').val(),
		merBusinessScopeId : $('#merBusinessScopeId').val(),
		merFax : $('#merFax').val(),
		merZip : $('#merZip').val(),
		merEmail : $('#merEmail').val(),
		merTelephone : $('#merTelephone').val(),
		merBankName : $('#merBankName').val(),
		merBankAccount : $('#merBankAccount').val(),
		merBankUserName : $('#merBankUserName').val(),
		merRateSpmtList : merRateSpmtList,
		merDdpInfoBean : merDdpInfoBean,
		merAutoAmtBean : merAutoAmtBean,
		merBusRateBeanList : merBusRateBeanList,
		merchantUserBean : merchantUserBean
	};
	ddpAjaxCall({
				url : "saveAndUpdataChildMerchant",
				data : merchantBeans,
				successFn : function(data) {
					if (data.code == "000000") {
						openChildMerchantView(0);
						$('#childMerchantMain').show();
						findChildMerchant($('.page-navi').paginator('getPage'));
					} else {
						// TODO error handler
						$.messagerBox({
									type : 'warn',
									title : "信息提示",
									msg : data.message
								});
					}
				}
			});

	return false;
}
// ---------------------------------------------子商户开户保存--------------------------

// -------------------------------------------------商户开户加载通卡公司开始---------------------------------------
// 加载通卡公司名称
function yktInfo() {

	yktBusRateBeanList = new Array()
	if ($('xuanzhe').disabled) {
		return false;
	} else {
		$.ajax({
					async : true,
					dataType : "json",
					type : 'post',
					url : "findProductList",
					success : function(data) {
						if (data.code == "000000") {
							createYKTCheckBox("yktViewCheckBox",
									data.responseEntity);
						}
					}
				});
	}
}

// 业务费率表里面多选
var yktBusRateBeanList;
function createYKTCheckBox(elementStr, data) {
	var element = $('#' + elementStr);
	element.html('');
	element.empty();
	var yktInfo = data;
	var col = 1;
	var line = '<tr class=" clearfix pl0">';
	var proNameArray = new Array();
	for (var i = 0; i < yktInfo.length; i++) {
		var isFound = false;
		for(var j = 0;i<proNameArray.length;j++) {
			if(yktInfo[i].proName == proNameArray[j]) {
				isFound = true;
			}
		}
		if(!isFound) {
			proNameArray.push(yktInfo[i].proName);
		} else {
			continue;
		}
		if (col == 5) {
			line += '</tr>';
			element.append(line);
			line = '<tr><td class="taLeft"><input type="checkbox" id="'
					+ yktInfo[i].proCode
					+ '"  value="'
					+ yktInfo[i].proName
					+ '"';
			if (yktInfo[i].checked == 'flase' || yktInfo[i].checked == false) {
				line += 'checked="' + yktInfo[i].checked + '"';
			}
			line += '/><label title="'
					+ yktInfo[i].rateName + '">'
					+ formatYKTName(yktInfo[i].proName) + '</th></label></td>';
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_ct"  value="' + yktInfo[i].rateCode + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_code"  value="' + yktInfo[i].rateName + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_type"  value="' + yktInfo[i].rateType + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_check"  value="' + yktInfo[i].checked + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_view"  value="' + yktInfo[i].rateTypeView + '"/>'
			col = 2;
		} else {
			col++;
			line += '<td class="taLeft"><input type="checkbox" id="'
					+ yktInfo[i].proCode
					+ '"  value="'
					+ yktInfo[i].proName
					+ '" ';
			if (yktInfo[i].checked == 'flase' || yktInfo[i].checked == false) {
				line += 'checked="' + yktInfo[i].checked + '"';
			}
			line += '/><label title="'
					+ yktInfo[i].proName + '">'
					+ formatYKTName(yktInfo[i].proName) + '</label></td>';
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_ct"  value="' + yktInfo[i].rateCode + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_code"  value="' + yktInfo[i].rateName + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_type"  value="' + yktInfo[i].rateType + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_check"  value="' + yktInfo[i].checked + '"/>'
			line += '<input type="hidden" id="' + yktInfo[i].proCode
					+ '_view"  value="' + yktInfo[i].rateTypeView + '"/>'		
		}

	}
	if (line.length >= 4) {
		line += '</tr>';
		element.append(line);
	}
	var proCode;
	var rateCode;
	var rateType;
	var rate;
	$("#yktRateTypeTable tbody tr").each(function() {
		var addCheck = $(this).find("input[name='addCheckbox']").is(':checked');
		if (addCheck) {
			proCode = $(this).find("input[name=proCode]").val(); // 通卡公司code
			rate = $(this).find("input[name=rateChild]").val();
			var merBusRateBean = {
				rate : rate,
				proCode : proCode

			}
			yktBusRateBeanList.push(merBusRateBean);
		}
	});
	// 从通卡公司名称获取所有的集合
	var editYKTSelectAll = $('#yktViewCheckBox').find("input[type=checkbox]");
	$.each(editYKTSelectAll, function(index, elementls) {
				if (yktBusRateBeanList) {
					for (var i = 0; i < yktBusRateBeanList.length; i++) {
						// 获取业务费率表选中的code
						var proCode = yktBusRateBeanList[i].proCode;
						if (elementls.id == proCode) {
							$('#' + elementls.id).attr('checked', true);
							break;
						}
					}
				}
			});
	$('#yktViewCheckBox').show();
	$('#yktView').show();
}
// 加载费率表
function findRateType() {
	$('#yktView').hide();
	if (isNotBlank(yktBusRateBeanList)) {
		findyktBusRateBeanList();
	} else {
		var selected = "";
		var html = '';
		var selectedArray = new Array();
		$('#yktRateTypeTable tbody').empty();
		var $selectAll = $('#yktViewCheckBox').find("input:checkbox:checked");
		$.each($selectAll, function(index, element) {
			var proName = $('#' + element.id).val();
			var proCode = element.id;
			var rateName = $('#' + element.id + '_code').val();
			var rateCode = $('#' + element.id + '_ct').val();
			var rateType = $('#' + element.id + '_type').val();
			var checked = $('#' + element.id + '_check').val();
			var rateTypeView =$('#' + element.id + '_view').val();
			if (selected != "") {
				selected += ",";
			}
			selected += proName;
			selectedArray.push(proCode);

			// 加载通卡公司
			$('.null-box').hide();
			html += '<tr>';
			html += '<td class="nobor">'
			html += '<input type="checkbox"  name="addCheckbox" checked='
					+ checked + ' ></input>'
			html += '</td>';

			html += '<td>'
			html += '<input name="proCode" id="' + proCode + '" value="'
					+ proCode + '" type="hidden"></input>'
			html += proName;
			html += '</td>';

			html += '<td>'
			html += '<input name="rateCode" id="' + rateCode + '" value="'
					+ rateCode + '" type="hidden"></input>'
			html += rateName;
			html += '</td>';

			html += '<td>'
			html += '<input name="rateType" id="' + rateType + '" value="'
					+ rateType + '" type="hidden"></input>'
			html += rateTypeView;
			html += '</td>';

			html += '<td>'
			html += '<input type="text" class="com-input-txt w74" name="rateChild" id="rateChild" onkeyup="clearNoNum(this)" onkeydown="checkDecimal(this,1,1,9,0,2)" onblur="clearNoNumOnBlur(this)" /> '
			html += '</td>';

			html += '<td class="nobor">&nbsp;</td>';
			html += '</tr>';
		});
		if (selectedArray.length != 0) {
			$('#rateTitleCheckId').attr('checked', true);
			$('#yktRateTypeTable').show();
			$('#rateTypeView').show();
		} else {
			$('#rateTitleCheckId').attr('checked', false);
			$('#yktRateTypeTable').hide();
			$('#rateTypeView').hide();
		}

		$('#yktRateTypeTable').append(html);
		$('#YKTCodeADD').val(selected);
	}
	$("#yktRateTypeTable").find("input[type=checkbox]").attr("checked", true);
	$("#yktRateTypeTable").find("input[type=checkbox]").attr("disabled", true);
}
//取消通卡公司表
function closeRateType(){
$("#yktView").hide();
}
// --------------------鼠标点击选择通卡公司触及事件--------------------
function findyktBusRateBeanList() {
	var selected = "";
	var html = '';
	$('#yktRateTypeTable tbody').empty();
	$('#YKTCodeADD').val('');
	var selectedArray = new Array();
	var $selectAll = $('#yktViewCheckBox').find("input:checkbox:checked");
	$.each($selectAll, function(index, element) {

		var proName = $('#' + element.id).val();
		var proCode = element.id;
		var rateName = $('#' + element.id + '_code').val();
		var rateCode = $('#' + element.id + '_ct').val();
		var rateType = $('#' + element.id + '_type').val();
		var rateTypeView =$('#' + element.id + '_view').val();
		var checked = $('#' + element.id + '_check').val();

		var rateNUM = "";
		for (var i = 0; i < yktBusRateBeanList.length; i++) {
			if (yktBusRateBeanList[i].proCode == proCode) {
				rateNUM = yktBusRateBeanList[i].rate;
			}
		}

		if (selected != "") {
			selected += ",";
		}
		selected += proName;
		selectedArray.push(proCode);

		// 加载通卡公司
		$('.null-box').hide();
		html += '<tr>';
		html += '<td class="nobor">'
		html += '<input type="checkbox"  name="addCheckbox" checked=' + checked
				+ ' onclick="addCheckBox(\'addCheckbox\')"></input>'
		html += '</td>';

		html += '<td>'
		html += '<input name="proCode" id="' + proCode + '" value="' + proCode
				+ '" type="hidden"></input>'
		html += proName;
		html += '</td>';

		html += '<td>'
		html += '<input name="rateCode" id="' + rateCode + '" value="'
				+ rateCode + '" type="hidden"></input>'
		html += rateName;
		html += '</td>';

		html += '<td>'
		html += '<input name="rateType" id="' + rateType + '" value="'
				+ rateType + '" type="hidden"></input>'
		html += rateTypeView;
		html += '</td>';

		html += '<td>'
		html += '<input type="text" class="com-input-txt w74" name="rateChild" value="'
				+ rateNUM + '"/>'
		html += '</td>';

		html += '<td class="nobor">&nbsp;</td>';
		html += '</tr>';

	});
	if (selectedArray.length != 0) {
		$('#rateTitleCheckId').attr('checked', true);
		$('#yktRateTypeTable').show();
		$('#rateTypeView').show();
	} else {
		$('#rateTitleCheckId').attr('checked', false);
		$('#yktRateTypeTable').hide();
		$('#rateTypeView').hide();
	}

	$('#yktRateTypeTable').append(html);
	$('#YKTCodeADD').val(selected);

	$("#yktRateTypeTable").find("input[type=checkbox]").attr("checked", true);
	$("#yktRateTypeTable").find("input[type=checkbox]").attr("disabled", true);
}
// ---------------------------------停启用选择事件开始----------------------
function addCheckBox(addName) {
	var ids = [];
	$('input[name=' + addName + ']').each(function(i, v) {
				if ($(v).is(':checked')) {
					ids.push(v);
				}
			});
}

// ---------------------------------停启用选择事件结束----------------------

// --------------------------限制用户只能输入小数点后两位开始--------
// obj dec小数位
function checkDecimal(obj, posOrNeg, startWhole, endWhole, startDec, endDec) {
	var re;
	var posNeg;
	if (posOrNeg == 1 || posOrNeg == '1') {
		re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
				+ startDec + "," + endDec + "})?$");
		posNeg = /^[+]?]*$/;
	} else if (posOrNeg == 2 || posOrNeg == '2') {
		re = new RegExp("^[-]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
				+ startDec + "," + endDec + "})?$");
		posNeg = /^[-]?]*$/;
	} else {
		re = new RegExp("^[+-]?\\d{" + startWhole + "," + endWhole
				+ "}(\\.\\d{" + startDec + "," + endDec + "})?$");
		posNeg = /^[+-]?]*$/;
	}
	if (!re.test(obj.value) && !posNeg.test(obj.value)) {
		obj.value = "";
		obj.focus();
		return false;
	}
}

// obj
function checkPlusMinus(obj) {
	posNeg1 = /^[+]?]*$/;
	posNeg2 = /^[-]?]*$/;
	if (posNeg1.test(obj.value) || posNeg2.test(obj.value)) {
		obj.value = "";
		// obj.focus();
		return false;
	}
}
function clearNoNum(obj) {
	if (obj.value != "") {
		obj.value = obj.value.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
		obj.value = obj.value.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
		obj.value = obj.value.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace(
				"$#$", ".");
		obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');// 只能输入两个小数
		var text = obj.value;
		if (text.indexOf(".") < 0) {
			if (text.length >= 4) {
				obj.value = text.substring(0, 3);
			}
		} else {
			var text01 = text.substring(0, text.indexOf("."));
			var text02 = text.substring(text.indexOf("."), text.length);
			if (text01.length > 3) {
				text01 = text01.substring(0, text01.length - 1);
			}
			var text = text01 + text02;
			if (text.length > 6) {
				text = "";
			}
			obj.value = text;
		}
	}
}

function clearNoNumOnBlur(obj) {
	if (obj.value != "") {
		var rate = obj.value;
		rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
		rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
		rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
		rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
		obj.value = rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');// 只能输入两个小数
		var text = obj.value;
		if (text.indexOf(".") < 0) {
			if (text.length >= 4) {
				obj.value = text.substring(0, 3);
			}
		} else {
			var text01 = text.substring(0, text.indexOf("."));
			var text02 = text.substring(text.indexOf("."), text.length);
			if (text01.length > 3) {
				text01 = text01.substring(0, text01.length - 1);
			}
			var text = text01 + text02;
			if (text.length > 6) {
				text = "";
			} else {
				var textChar = text.charAt(text.length - 1);
				if (textChar == ".") {
					text = text.substring(0, text.length - 1);
				}
			}
			obj.value = text;
		}
	}
}
// --------------------------限制用户只能输入小数点后两位结束--------

// 加载费率表直营的网点
function addMerTypeRateType(data) {
	var selected = "";
	var html = '';
	$('#yktRateTypeTable tbody').empty();
	if (data.responseEntity && data.responseEntity.length > 0) {
		var merBusRateRecharge = new Array();
		var merBusRatePayment = new Array();
		var proNameArray = new Array();
		$(data.responseEntity).each(function(i, v) {
			var rateCode = v.rateCode;
			if(rateCode=='01') {
				merBusRateRecharge.push(v);
			}else if(rateCode=='03') {
				merBusRatePayment.push(v);
			}
		});

		$(data.responseEntity).each(function(i, v) {
			var isFound = false;
			for(var j=0;j<proNameArray.length;j++) {
				if(v.proName == proNameArray[j]) {
					isFound = true;
					break;
				}
			}
			if(!isFound) {
				if (selected != "") {
					selected += ",";
				}
				selected += v.proName;
				proNameArray.push(v.proName);
			}
			
			$('.null-box').hide();
			html += '<tr>';
			html += '<td class="nobor">'
			html += '<input type="checkbox" name="checkbox" checked="'
					+ v.checked + '"></input>'
			html += '</td>';

			html += '<td>'
			html += '<input name="proCode" value="' + v.proCode
					+ '" type="hidden"></input>'
			html += v.proName;
			html += '</td>';

			html += '<td>'
			html += '<input name="rateCode" value="' + v.rateCode
					+ '" type="hidden"></input>'
			html += v.rateName;
			html += '</td>';

			html += '<td>'
			html += '<input name="rateType" value="' + v.rateType
					+ '" type="hidden"></input>'
			html += v.rateTypeView;
			html += '</td>';

			html += '<td>'
			html += v.rate
			html += '</td>';

			html += '<td class="nobor">&nbsp;</td>';
			html += '</tr>';

		});
		$('#YKTCodeADD').val(selected);
		$('#yktRateTypeTable').append(html);
		$("#yktTableLine").show();
		$('#yktRateTypeTable').show();

		$('#rateTypeView').show();

		$("#yktRateTypeTable").find("input[type=checkbox]").attr("checked",
				true);
		$("#yktRateTypeTable").find("input[type=checkbox]").attr("disabled",
				true);
	}
}
// -------------------------------------------------开户加载通卡公司结束---------------------------------------
// ------------------------------------新增业务类型-----------------------------------
function findPrdRateType() {
	$('#PrdRateType').html('');
	$("#yktTableLine").hide();
	var merType = $('#merType').val();
	ddpAjaxCall({
				async: false,
				url : "findMerRateSupplementByCode?merType=" + merType,
				successFn : loadPrdRate
			});
}
function loadPrdRate(data) {
	if (data.code = '000000') {
		// 获取加载的值
		var prdRateBean = data.responseEntity;
		var line = '';
		if(prdRateBean!=null){
			for (var i = 0; i < prdRateBean.length; i++) {
				line = '<td><input type="checkbox" id="' + prdRateBean[i].rateCode
						+ '" name="prdRateName" style="width:20px;border:0;" ';
				line += 'value= "' + prdRateBean[i].rateCode
						+ '" onclick="prdRateTypeSysChild(this)"/>&nbsp;&nbsp;'
						+ prdRateBean[i].rateCodeView + '</td>';
				$('#PrdRateType').append(line);
			}
		}else{
				$('#PrdRateType').html('');
				$("#yktTableLine").hide();
		}
		

	}
}

// 勾选业务类型
function prdRateTypeSysChild(obj) {
	var checked = $("input[id=" + obj.id + "][type='checkbox']").is(':checked');
	if (checked) {
		if ($('#' + obj.id + '').val() == '01') {
			$("#yktTableLine").show();
		}
	} else {
		if ($('#' + obj.id + '').val() == '01') {
			$("#YKTCodeADD").val('');
			$("#yktTableLine").hide();
			$("#rateTypeView").hide();
			$('#yktRateTypeTable tbody').empty();
		}
	}
}
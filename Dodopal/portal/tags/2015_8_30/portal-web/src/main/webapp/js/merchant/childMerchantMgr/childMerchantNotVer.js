function checkNotVerValue(){
	//商户类型
	var isMetTypeNotVer = validateMetTypeNotVer(true);
	//商户名称
	var isMerNameNotVer =validateMerNameNotVer(true);
	//用户名称
	var isMerUserNameNotVer=validateMerUserNameNotVer(true);
	//手机号码
	var isMerLinkUserMobileNotVer =validateMerLinkUserMobileNotVer(true);
	var isChildMerName =$('#merNameNotVerERR2').html();
	var isChildMerUserName =$('#merUserNameNotVerERR2').html();
	var isChildMerUserMobile =$('#merLinkUserMobileNotVerERR2').html();
	var isWeiYi=false;
	if(isBlank(isChildMerName)&&isBlank(isChildMerUserName)&&isBlank(isChildMerUserMobile)){
		isWeiYi =true;
	}
	var isWeiYiAjax=false;
	if(isMerNameNotVer&&isMerUserNameNotVer&&isMerLinkUserMobileNotVer){
		isWeiYiAjax =true;
	}
	var isMerUserPWD = validateMerUserPWDNotVer(true);
	var isMerUserPWD2 = validateMerUserPWDTwoNotVer(true);
	var isMerLinkUser = validateMerLinkUserNotVer(true);
	var isMerAdds = validateMerAddsNotVer(true);
	return isWeiYi
	&&isWeiYiAjax
	&&isMerUserPWD
	&&isMerUserPWD2
	&&isMerLinkUser
	&&isMetTypeNotVer
	&&isMerAdds;
}

function checkNotVerRequired(){
	
	var isMerEmail =  validateMerEmailNotVer(true);
	var isMerTelephone =  validateMerTelephoneNotVer(true);
	var isMerFax =  validateMerFaxNotVer(true);
	var isMerZip =  validateMerZipNotVer(true);
	var isMerBankUserName = validateMerBankUserNameNotVer(true);
	var isIdentityNumber = checkIdentityNumber(true,'merUserIdentityTypeNotVer','merUserIdentityNumberNotVer','merUserIdentityNumberNotVerERR');
	var isMerBankAccount = checkMerBankAccount(true,'merBankNameNotVer','merBankAccountNotVer','merBankAccountNotVerERR');
	return isMerEmail
	&&isMerTelephone
	&&isMerFax
	&&isMerZip
	&&isMerBankUserName
	&&isIdentityNumber
	&&isMerBankAccount;
}

function checkNotVerMerType(){
	var merTypeNotVer =$('#merTypeNotVer').val();
	if(merTypeNotVer!=null||merTypeNotVer!=""){
		$("#merTypeNotVerERR").html('').hide();
	}
	if(merTypeNotVer=='13'){
		$.ajax({
			async : true,
			dataType : "json",
			type : 'post',
			url : "findProductList",
			success : function(data) {
				notVerYktType(data);
			}
		});
		$("#xuanzheNotVer").attr("disabled",true);
		$("#xuanzheNotVer").unbind('click');
		
	}else{
		$("#xuanzheNotVer").attr("disabled",false);
		$('#notVer_yktNameSpan').val('');
		$('#notVer_busRateTable tbody').empty();
		$('#notVer_busRateTable').hide();
		$('#notVer_busRateTypeView').hide();
		$("#xuanzheNotVer").bind("click",function(){
			notVer_yktName();
		});
	}
}



//清空审核编辑界面
function clearChildMerchantNotVerView(){
	//清空查看子商户详情界面上的通卡公司费率
	$('#notVer_busRateTable tbody').empty();
}

//审核通过编辑加载数据
function loadChildMerchantNotVerEdit(data){
	var merchantUserBeans = data.merchantUserBean;
	var merBusRateBeanList = data.merBusRateBeanList;
	var merPro = getProvinceValue('provinceAdd');
	var merCity = getProvinceValue('cityAdd');
		clearChildMerchantNotVerView();
		//加载完毕打开界面
		openChildMerchantView(3);
		//商户编码
		$('#merCodeNotVer').val(data.merCode);
		//商户类型
		$('#merTypeNotVer').val(data.merType);
		//停启用标识
		$("input[name='activateNotVer'][value="+data.activate+"]").attr("checked",true); 
		//审核状态
		$('#merStateNotVer').val(data.merState);
		//商户名称
		$('#merNameNotVer').val(data.merName);
		//用户编号
		$('#userCodeNotVer').val(merchantUserBeans.userCode);
		//用户名
		$('#merUserNameNotVer').val(merchantUserBeans.merUserName);
		//联系人
		$('#merLinkUserNotVer').val(data.merLinkUser);
		//手机号码
		$('#merLinkUserMobileNotVer').val(data.merLinkUserMobile);
		//省市
		setProvinceCity('provinceNotVer','cityNotVer',data.merPro,data.merCity)
		//地址
		$('#merAddsNotVer').val(data.merAdds);
		//通卡公司费率信息
		if(merBusRateBeanList!=null && merBusRateBeanList!=''){
			notVer_yktBusRateTypeView(merBusRateBeanList);
			$('#notVer_busRateTypeView').show();
			$('#notVer_busRateTable').show();
		}else{
			$('#notVer_busRateTypeView').hide();
			$('#notVer_busRateTable').hide();
		}
		//经营范围
		$('#merBusinessScopeIdNotVer').val(data.merBusinessScopeId);
		//邮箱
		$('#merEmailNotVer').val(data.merEmail);
		//固定电话
		$('#merTelephoneNotVer').val(data.merTelephone);
		//传真
		$('#merFaxNotVer').val(data.merFax);
		//证件类型
		$('#merUserIdentityTypeNotVer').val(merchantUserBeans.merUserIdentityType);
		//证件号码
		$('#merUserIdentityNumberNotVer').val(merchantUserBeans.merUserIdentityNumber);
		//邮编
		$('#merZipNotVer').val(data.merZip);
		//开户银行
		$('#merBankNameNotVer').val(data.merBankName);
		//开户行账号
		$('#merBankAccountNotVer').val(data.merBankAccount);
		//开户名称
		$('#merBankUserNameNotVer').val(data.merBankUserName);
		//备注
		$('#merUserRemarkNotVer').val(htmlTagFormat(merchantUserBeans.merUserRemark));
		//审核不通过原因
		$('#merRejectReasonNotVer').html(htmlTagFormat(data.merRejectReason));
		selectUiInit();
		//打开界面
		$('#childMerchantNotVerEdit').show();
}
//---------------------------------------------审核不通过编辑保存开始--------------------------------------------
//保存子商户信息
function saveChildMerchantNotVer(){
	if(!checkNotVerValue()){
		return false;
	}
	if(!checkNotVerRequired()){
		return false;
	}
	var activate = $('input[name="activateNotVer"]:checked').val(); 
	var pw1 = $('#merUserPWDNotVer').val();
	var merBusRateBeanList= new Array();
	var merPro = getProvinceValue('provinceNotVer');
	var merCity = getCityValue('cityNotVer');

		$("#notVer_busRateTable tbody tr").each(function(){
						var addCheck= $(this).find("input[name='checkbox_notVer']").is(':checked');
						if(addCheck){
							var proCode = $(this).find("input[name=proCode_notVer]").val();
							var rateCode = $(this).find("input[name=rateCode_notVer]").val();
							var rateType = $(this).find("input[name=rateType_notVer]").val();
							var rate = $(this).find("input[name=rateChild_notVer]").val();
				            var merBusRateBean = {
				        		    rateType : rateType,
				        		    rate : parseFloat(rate),
				        		    proCode : proCode,
				        		    rateCode : rateCode
				        	}
				            merBusRateBeanList.push(merBusRateBean);
						}
						}); 
			
	  var merchantUserBean = {
			  	userCode : $('#userCodeNotVer').val(),
				merUserName: $('#merUserNameNotVer').val(),
				merUserNickName: $('#merLinkUserNotVer').val(),
				merUserMobile: $('#merLinkUserMobileNotVer').val(),
				merUserAdds:$('#merAddsNotVer').val(),
				merUserPWD: md5(md5(pw1)),
				merUserTelephone:$('#merTelephoneNotVer').val(),
				merUserIdentityType:$('#merUserIdentityTypeNotVer').val(),
				merUserIdentityNumber:$('#merUserIdentityNumberNotVer').val(),
				merUserEmail:$('#merEmailNotVer').val(),
				merUserRemark:$('#merUserRemarkNotVer').val()
		};
	  var merchantBeans = {
				merCode:$('#merCodeNotVer').val(),
				merName:$('#merNameNotVer').val(),
				merType:$('#merTypeNotVer').val(), 
				activate: '0',
				merPro:merPro, 
				merCity:merCity,
				merLinkUser:$('#merLinkUserNotVer').val(),
				merLinkUserMobile:$('#merLinkUserMobileNotVer').val(),
				merAdds:$('#merAddsNotVer').val(),
				merBusinessScopeId:$('#merBusinessScopeIdNotVer').val(),
				merFax:$('#merFaxNotVer').val(),
				merZip:$('#merZipNotVer').val(),
				merBankName:$('#merBankNameNotVer').val(),
				merBankAccount:$('#merBankAccountNotVer').val(),
				merBankUserName:$('#merBankUserNameNotVer').val(),
				merBusRateBeanList:merBusRateBeanList,
				merchantUserBean:merchantUserBean
		};
	ddpAjaxCall({
		url : "saveAndUpdataChildMerchant",
		data : merchantBeans,
		successFn : function(data) {
			if (data.code == "000000") {
				openChildMerchantView(0);
				$('#childMerchantMain').show();
				findChildMerchant($('.page-navi').paginator('getPage'));
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}
//---------------------------------------------审核不通过编辑保存结束--------------------------------------------
//---------------------------------停启用选择事件开始----------------------
function notVerCheckBox(addName){
	var ids = [];
	$('input[name='+addName+']').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}
	});
}

//---------------------------------停启用选择事件结束----------------------


//-------------------------------------------------审核不通过开户编辑通卡公司开始---------------------------------------
//业务费率表里面多选
var yktmerBusRateBeanList;
//加载通卡公司名称
function notVer_yktName(){
	//选择
	$.ajax({
		async : true,
		dataType : "json",
		type : 'post',
		url : "findProductList",
		success : function(data) {
			yktNotVerCheckBox("notVer_yktCheckBox", data);
		}
	});
}
function yktNotVerCheckBox(elementStr, data) {
	yktmerBusRateBeanList= new Array()
	var element = $('#' + elementStr);
	var sed = element.find("input[type=checkbox]:checked");	
	element.html('');
	$('#notVer_yktCheckBox').html('');
	if(data.code == "000000") {
		var yktInfo = data.responseEntity;
		var col = 1;
		var line = '<tr>';
		for(var i=0; i<yktInfo.length; i++) {
			if(col == 5) {
				line += '</tr>';
				element.append(line);
				line = '<tr><td style="vertical-align: middle"><label><input type="checkbox" id="' + yktInfo[i].proCode + '" value="' +yktInfo[i].proName + '"';
				if(yktInfo[i].checked == 'flase' || yktInfo[i].checked == false) {
					line += 'checked="' + yktInfo[i].checked + '"'; 
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'">' +formatYKTName(yktInfo[i].proName) + '</th>';
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_notct"  value="' + yktInfo[i].rateCode+'"/>'
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_notcode"  value="' + yktInfo[i].rateName+'"/>'
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_nottype"  value="' + yktInfo[i].rateType+'"/>'
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_check"  value="' + yktInfo[i].checked+'"/>'
				col = 2;
			} else {
				col++;
				line += '<td style="vertical-align: middle"><label><input type="checkbox" id="' + yktInfo[i].proCode + '" value="' + yktInfo[i].proName+ '" ';
				if(yktInfo[i].checked == 'flase' || yktInfo[i].checked == false) {
				line += 'checked="' + yktInfo[i].checked 	+ '"';
				}
				line += '/></label></td><th style="text-align:left;" title="'+yktInfo[i].proName+'">' + formatYKTName(yktInfo[i].proName) + '</th>';
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_notct"  value="' + yktInfo[i].rateCode+'"/>'
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_notcode"  value="' + yktInfo[i].rateName+'"/>'
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_nottype"  value="' + yktInfo[i].rateType+'"/>'
//				line +='<input type="hidden" id="' + yktInfo[i].proCode + '_check"  value="' + yktInfo[i].checked+'"/>'
			}
		}
		if(line.length >= 4) {
			line += '</tr>';
			element.append(line);
		}
		
		var proCode;
		var rateCode;
		var rateType;
		var rate;
		$("#notVer_busRateTable tbody tr").each(function(){
			var addCheck= $(this).find("input[name='checkbox_notVer']").is(':checked');
			if(addCheck){
				var proCode = $(this).find("input[name=proCode_notVer]").val();
				var rateCode = $(this).find("input[name=rateCode_notVer]").val();
				var rateType = $(this).find("input[name=rateType_notVer]").val();
				var rate = $(this).find("input[name=rateChild_notVer]").val();
	            var merBusRateBean = {
	        		    rateType : rateType,
	        		    rate : parseFloat(rate),
	        		    proCode : proCode,
	        		    rateCode : rateCode
	        	}
	            yktmerBusRateBeanList.push(merBusRateBean);
			}
			});
			//从通卡公司名称获取所有的集合
			var editYKTSelectAll = $('#notVer_yktCheckBox').find("input[type=checkbox]");
			//初始化清空选择界面所有checkbox勾选框
			$.each(editYKTSelectAll, function(index, elementls) {
				if (yktmerBusRateBeanList) {
					for(var i=0; i<yktmerBusRateBeanList.length; i++) {
						//获取业务费率表选中的code
							var proCode = yktmerBusRateBeanList[i].proCode;
							if(elementls.id==proCode){
								$('#'+elementls.id).attr('checked',true);
								break;
							}
						}
					}
			});
		
	//显示选择框
	$('#notVer_yktNameView').show();
 }
}


//勾选商户名称点击确认保存
function notVer_yktNameCheckBox(){
	//关闭选择框
	$('#notVer_yktNameView').hide();
	var html ='';
	var selectedArray = new Array();
	var $selectAll = $('#notVer_yktCheckBox').find("input:checkbox:checked");
	var selected = "";
	$.each($selectAll, function(index, element) {
		var proName = $('#' + element.id).val();
		if(selected != "") {
			selected += ",";
		}
		selected += proName;
		selectedArray.push(element.id);
	});
	//判断是否有值
	if(selectedArray.length != 0){
		$('#notVer_busRateTable').show();
	}else{
		$('#notVer_busRateTable').hide();
	}
	$('#notVer_yktNameSpan').val(selected);
		ddpAjaxCall({
			url : "loadNotYktInfo",
			data : selectedArray,
			successFn : function(data) {
				if (data.code == "000000") {
					notVer_yktBusRateTypeView(data.responseEntity);
					$('#notVer_busRateTypeView').show();
					$('#notVer_busRateTable').show();
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}		
			}
		});
}
//-----------------------------------------------审核不通过初始化加载通卡公司费率开始-----------------------------

//加载通卡公司费率
function notVer_yktBusRateTypeView(merBusRateBeanList) {
	var selected = '';
	var html='';
	$('#notVer_busRateTable tbody').empty();
	if(merBusRateBeanList!=null && merBusRateBeanList!=''){
		$(merBusRateBeanList).each(function(i,v){
			if(isNotBlank(yktmerBusRateBeanList)){
				for (var i = 0; i < yktmerBusRateBeanList.length; i++) {
					if (yktmerBusRateBeanList[i].proCode == v.proCode) {
						v.rate = yktmerBusRateBeanList[i].rate;
					}
				}
			}
			if(selected != '') {
				selected += ',';
			}
			selected += v.proName;
			
			$('.null-box').hide();
			//-------------------------------------初始化费率信息--------------
			html = '<tr>';
			html +='<td class="nobor">'
			html +='<input type="checkbox" name="checkbox_notVer" id='+v.proCode+'  checked='+v.checked+' onclick="notVerCheckBox(\'checkbox_notVer\')"></input>'
			html +='</td>';
			
			html += '<td>'
			html +='<input name="proCode_notVer" id="'+v.proCode+'" value="'+v.proCode+'" type="hidden"></input>'
			html += v.proName;
			html += '</td>';
			
			html += '<td>'
			html +='<input name="rateCode_notVer" id="'+v.rateCode+'" value="'+v.rateCode+'" type="hidden"></input>'
			html += v.rateName;
			html += '</td>';
			
			html += '<td>'
			html +='<input name="rateType_notVer" id="'+v.rateType+'" value="'+v.rateType+'" type="hidden"></input>'
			html += v.rateType == null ? '' : busRateType(v.rateType);
			html += '</td>';
			
			html += '<td>'
			html += '<input type="text" class="com-input-txt w74" name="rateChild_notVer"  value="'+v.rate+'"  onkeyup="clearNoNum(this)" onkeydown="checkDecimal(this,1,1,9,0,2)" onblur="clearNoNumOnBlur(this)"/>'
			html += '</td>';
				
			html +='<td class="nobor">&nbsp;</td>';
			html += '</tr>';
			
			
			$('#notVer_busRateTable').append(html);
		});
		$('#notVer_yktNameSpan').val(selected);
		$("#notVer_busRateTable").find("input[type=checkbox]").attr("checked",true);
		$("#notVer_busRateTable").find("input[type=checkbox]").attr("disabled",true);
	}
}
//-----------------------------------------------审核不通过初始化加载通卡公司费率结束-----------------------------
//审核不通过直营网点加载费率表
function notVerYktType(data){
	var html ='';
	var selected = "";
	$('#notVer_busRateTable tbody').empty();
	if(data.responseEntity && data.responseEntity.length > 0){
		$(data.responseEntity).each(function(i,v){
			if(selected != "") {
				selected += ",";
			}
			selected += v.proName;
			$('.null-box').hide();
			html += '<tr>';
			html +='<td class="nobor">'
			html +='<input type="checkbox" name="checkbox_notVer" id="'+v.proCode+'"  checked="'+v.checked+'"></input>'
			html +='</td>';
			
			html += '<td>'
			html += v.proName;
			html += '</td>';
			
			html += '<td>'
			html += v.rateName;
			html += '</td>';
			
			html += '<td>'
			html += v.rateType == null ? '' : busRateType(v.rateType);
			html += '</td>';
			
			html += '<td>'
			html += v.rate
			html += '</td>';
				
			html +='<td class="nobor">&nbsp;</td>';
			html += '</tr>';
		});
	$('#notVer_yktNameSpan').val(selected);
	$('#notVer_busRateTable').append(html);
	$('#notVer_busRateTypeView').show();
	$('#notVer_busRateTable').show();
	$("#notVer_busRateTable").find("input[type=checkbox]").attr("checked",true);
	$("#notVer_busRateTable").find("input[type=checkbox]").attr("disabled",true);
	}
}
//-------------------------------------------------审核不通过编辑通卡公司结束---------------------------------------


//--------------------------限制用户只能输入小数点后两位开始----------------------------------------------------------
//obj dec小数位
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


//obj
function checkPlusMinus(obj) {
	posNeg1 = /^[+]?]*$/;
	posNeg2 = /^[-]?]*$/;
	if (posNeg1.test(obj.value) || posNeg2.test(obj.value)) {
		obj.value = "";
		//obj.focus();
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
			if (text.length >= 5) {
				obj.value = text.substring(0, 4);
			}
		} else {
			var text01 = text.substring(0, text.indexOf("."));
			var text02 = text.substring(text.indexOf("."), text.length);
			if (text01.length > 4) {
				text01 = text01.substring(0, text01.length - 1);
			}
			var text = text01 + text02;
			if (text.length > 7) {
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
			if (text.length >= 5) {
				obj.value = text.substring(0, 4);
			}
		} else {
			var text01 = text.substring(0, text.indexOf("."));
			var text02 = text.substring(text.indexOf("."), text.length);
			if (text01.length > 4) {
				text01 = text01.substring(0, text01.length - 1);
			}
			var text = text01 + text02;
			if (text.length > 7) {
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
//--------------------------限制用户只能输入小数点后两位结束--------

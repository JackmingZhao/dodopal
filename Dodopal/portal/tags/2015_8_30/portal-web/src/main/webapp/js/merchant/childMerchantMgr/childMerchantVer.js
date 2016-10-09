//效验补充信息
function checkVerValue(){
	var isMerEmailVer = validateMerEmailVer(true);
	var isMerTelephoneVer = validateMerTelephoneVer(true);
	var isMerFaxVer = validateMerFaxVer(true);
	var isMerUserIdentityNumberVer =checkIdentityNumber(true,'merUserIdentityTypeVer','merUserIdentityNumberVer','merUserIdentityNumberVerERR');
	var isMerZipVer = validateMerZipVer(true);
	var isMerBankAccountVer = checkMerBankAccount(true,'merBankNameVer','merBankAccountVer','merBankAccountVerERR');
	var isMerBankUserNameVer = validateMerBankUserNameVer(true);
	return isMerEmailVer
	&&isMerTelephoneVer
	&&isMerFaxVer
	&&isMerZipVer
	&&isMerBankAccountVer
	&&isMerBankUserNameVer
	&&isMerUserIdentityNumberVer;
}


//清空审核编辑界面
function clearChildMerchantVerView(){
	//清空查看子商户详情界面上的通卡公司费率
	$('#yktTableVer tbody').empty();
}

//审核通过编辑加载数据
function loadChildMerchantVerEdit(data){
	var merchantUserBeans = data.merchantUserBean;
	var merBusRateBeanList = data.merBusRateBeanList;
		clearChildMerchantVerView();
		//加载完毕打开界面
		openChildMerchantView(2);
		//---------------------不可编辑的审核条件--------------
		//商户编码
		$('#merCodeVer').val(data.merCode);
		//商户类型
		$('#merTypeVer').html(findmerType(data.merType));
		//审核状态
		$('#merStateVer').html(findmerState(data.merState));
		//商户名称
		$('#merNameVer').html(data.merName);
		//用户名
		$('#merUserNameVer').html(merchantUserBeans.merUserName);
		//联系人
		$('#merLinkUserVer').html(data.merLinkUser);
		//手机号码
		$('#merLinkUserMobileVer').html(data.merLinkUserMobile);
		//地址
		$('#merAddsVer').html(data.merProName+data.merCityName+data.merAdds);
		//通卡公司费率信息
		if(merBusRateBeanList!=null && merBusRateBeanList!=''){
			loadYktVerView(merBusRateBeanList);
			$('#yktDivView').show();
			$("#yktTableVer").show();
		}else{
			$('#yktDivView').hide();
			$("#yktTableVer").hide();
		}
		//---------------------可编辑的审核条件--------------
		//经营范围
		$('#merBusinessScopeIdVer').val(data.merBusinessScopeId);
		//邮箱
		$('#merEmailVer').val(data.merEmail);
		//固定电话
		$('#merTelephoneVer').val(data.merTelephone);
		//传真
		$('#merFaxVer').val(data.merFax);
		//证件类型
		$('#merUserIdentityTypeVer').val(merchantUserBeans.merUserIdentityType);
		//证件号码
		$('#merUserIdentityNumberVer').val(merchantUserBeans.merUserIdentityNumber);
		//邮编
		$('#merZipVer').val(data.merZip);
		//开户银行
		$('#merBankNameVer').val(data.merBankName);
		//开户行账号
		$('#merBankAccountVer').val(data.merBankAccount);
		//开户名称
		$('#merBankUserNameVer').val(data.merBankUserName);
		//备注
		$('#merUserRemarkVer').val(merchantUserBeans.merUserRemark);
		selectUiInit();
		//显示审核通过编辑界面
		$('#childMerchantEdit').show();
}
//-----------------------------------审核通过编辑业务费率加载开始------------------------------------------
//加载通卡公司费率
function loadYktVerView(merBusRateBeanList) {
	var selected = '';
	if(merBusRateBeanList && merBusRateBeanList.length > 0){
		$(merBusRateBeanList).each(function(i,v){
			if(selected != '') {
				selected += ',';
			}
			selected += v.proName;
			
			$('.null-box').hide();
			html = '<tr>';
			html +='<td class="nobor">&nbsp;</td>';
			
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
			html += v.rate;
			html += '</td>';
				
			html +='<td class="nobor">&nbsp;</td>';
			html += '</tr>';
			//业务费率表 tbody
			$('#yktinfoVerView').append(html);
		});
		//城卡公司名称
		$('#yktinfoVerViewSpan').html(selected);
	}
}
//-----------------------------------审核通过编辑业务费率加载结束------------------------------------------
//---------------------------------------------已审核编辑保存开始--------------------------------------------
//保存子商户信息
function saveChildMerchantVer(){
	if(!checkVerValue()){
		return false;
	}
	var merchantUserBean = {
						merUserName : $('#merUserNameVer').html(),
						merUserTelephone:$('#merTelephoneVer').val(),
						merUserIdentityType:$('#merUserIdentityTypeVer').val(),
						merUserIdentityNumber:$('#merUserIdentityNumberVer').val(),
						merUserEmail:$('#merEmailVer').val(),
						merUserRemark:$('#merUserRemarkVer').val()
				};
	var merchantBeans = {
						merCode:$('#merCodeVer').val(),
						merBusinessScopeId:$('#merBusinessScopeIdVer').val(),
						merFax:$('#merFaxVer').val(),
						merZip:$('#merZipVer').val(),
						merEmail:$('#merEmailVer').val(),
						merTelephone:$('#merTelephoneVer').val(),
						merBankName:$('#merBankNameVer').val(),
						merBankAccount:$('#merBankAccountVer').val(),
						merBankUserName:$('#merBankUserNameVer').val(),
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
				//TODO error handler
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}
//---------------------------------------------已审核编辑保存结束--------------------------------------------

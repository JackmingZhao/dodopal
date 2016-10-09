//初始化加载数据字典
function initUnverMerchantDdicComponent() {
	loadMerchantType();
	loadChildMerParentType();
	loadMerUserIdentityType();
	loadMerBusinessScopeId();
	loadMerBankName();
		//----------------------新需求 2015-12-05---------------
	//结算类型
	loadSettlementType();
	//是否结婚
	loadIsMarried();
	//学历
	loadEducation();
}
//加载商户类型数据字典
function loadMerchantType(){
	$('#merType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200,
		onSelect : function(record){
			findMerTypeByCode(record.code);
		}
	});
		var ddic = {
			categoryCode : 'MER_TYPE_REG'
		}
	loadDdics(ddic, loadMerTypes);
}
/**
 * 开户时选择商户类型变动的界面效果 10 代理商户 11 代理商户自由网点 12 连锁商户 13 连锁直营网点 14 连锁加盟网点 15 都都宝自由网点
 * 16 集团商户 17 供应商 18 外接商户 99 个人
 * 
 * @param {}
 *            code
 */
//商户类型变动的界面效果
function findMerTypeByCode(code){
	if(code=='10'){
	//	$('#td03').hide();
		$('#providerCodePayment').val("");
		$('#TKNameLinePayment').hide();
		$('#yktTblLinePayment').hide();
		$('#TKNameVerLinePayment').hide();
		$('#yktTblPayment').clearGridData();
	}else if(code != '13'){
		//$('#td03').show();
	}
}

function loadChildMerParentType(){
	$('#merParentTypes').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200
	});
	var ddic = {
			categoryCode : 'MER_TYPE_REG'
	}
	loadDdics(ddic, loadChildMerParentTypes);
}


//初始化加载上级商户类型
function loadChildMerParentTypes(data){
	if (data.code == "000000") {
		if(typeof(data.responseEntity) !="undefined" && data.responseEntity.length > 0) {
			reLoadComboboxData($('#merParentTypes'), data.responseEntity)
		} 
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


//上级商户类型
function handleMerParentLine(data){
	if (data.code == "000000") {
		if(typeof(data.responseEntity) !="undefined" && data.responseEntity.length > 0) {
			$("#merTypeParentLine").show();
			reLoadComboboxData($('#merParentTypes'), data.responseEntity)
		} 
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function loadMerTypes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merType'), data.responseEntity);
		$('#merType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


//加载证件类型
function loadMerUserIdentityType(){
	$('#merUserIdentityType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200
	});
	var ddic = {
			categoryCode : 'IDENTITY_TYPE'
	}
	loadDdics(ddic, loadMerUserIdentityTypes);
}
function loadMerUserIdentityTypes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merUserIdentityType'), data.responseEntity)
		$('#merUserIdentityType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

//加载经营范围
function loadMerBusinessScopeId(){
	$('#merBusinessScopeId').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200
	});
	var ddic = {
			categoryCode : 'BUSINESS_SCOPE'
	}
	loadDdics(ddic, loadMerBusinessScopeIds);
}
function loadMerBusinessScopeIds(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merBusinessScopeId'), data.responseEntity)
		$('#merBusinessScopeId').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


//加载开户银行数据字典
function loadMerBankName(){
	$('#merBankName').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200
	});
	var ddic = {
			categoryCode : 'MER_BANK'
	}
	loadDdics(ddic, loadMerBankNames);
}
function loadMerBankNames(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merBankName'), data.responseEntity)
		$('#merBankName').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


// ----------------------------新需求变动 2015-12-03--------------------
//结算类型
function loadSettlementType(){
	$('#settlementType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200,
		onSelect: function(data){
			var type = data.code;
			if (type== '0') {
				$("#settlementThreshold").val('');
				$("#settlementThresholdTypeFont").text("天");
			} else if (type == '1') {
				$("#settlementThreshold").val('');
				$("#settlementThresholdTypeFont").text("笔");
			} else if (type == '2') {
				$("#settlementThreshold").val('');
				$("#settlementThresholdTypeFont").text("元");
			} else {
				$("#settlementThresholdTypeFont").text("");
			}
			$('#settlementThresholdTypeFont').show();
		}
	});
	var ddic = {
			categoryCode : 'SETTLEMENT_TYPE'
	}
	loadDdics(ddic, DataSettlementType);
}
function DataSettlementType(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#settlementType'), data.responseEntity)
		$('#settlementType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

//是否已婚
function loadIsMarried(){
	$('#isMarried').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200
	});
	var ddic = {
			categoryCode : 'IS_MARRIED_TYPE'
	}
	loadDdics(ddic, DataIsMarriedType);
}
function DataIsMarriedType(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#isMarried'), data.responseEntity)
		$('#isMarried').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


// 学历
function loadEducation(){
	$('#education').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200
	});
	var ddic = {
			categoryCode : 'EDUCATION_TYPE'
	}
	loadDdics(ddic, DataEducationType);
}
function DataEducationType(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#education'), data.responseEntity)
		$('#education').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
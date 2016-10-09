// 初始化加载数据字典
function initMerchantDdicComponent() {
	loadMerchantType();
	loadMerchantClassify();
	loadMerProperty();
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
// 加载商户类型数据字典
function loadMerchantType(){
	$('#merTypeQuery').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 120
	});
	//加载上级商户数据
	$('#merParentType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200,
		onSelect : function(record){			
					
			if($('#flag').val()=='0'){
				$('#merParentName').val("");
				$('#merParentCode').val("");
				// 清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
				if(!(isBlank($('#merPT').val()) && $('#merParentType').combobox("getValue")=="null")){
					clearPrdRateType();
				}
				
				if($('#merParentType').combobox("getValue")=="null") {
					// $("#ykt").show();
					$('#ykt').removeAttr("disabled");
					$('#yktPayment').removeAttr("disabled");
				}

				// Add
				$("#yktDialog").find("input[type=checkbox]").attr("checked",false);
				$("#yktDialogPayment").find("input[type=checkbox]").attr("checked",false);
				if($('#merParentType').combobox('getValue')=='null'||$('#merParentType').combobox('getValue')==null){
					// $("#finMER").hide();
					$("#finMER").attr("disabled",true);
				}else{
					// $("#finMER").show();
					$("#finMER").removeAttr("disabled");
				}
			}
			
			$('#merPT').val($('#merParentType').combobox("getValue"));	
		}
	});
	
	$('#merType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200, 
		onSelect : function(record){
			var categoryCode = 'MER_TYPE_' + record.code;
			var ddic = {
					categoryCode : categoryCode
			}
			// $("#finMER").show();
			$("#finMER").removeAttr("disabled");
			loadDdics(ddic, handleMerParentLine);
			findmerTypeByCode(record.code);
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
function findmerTypeByCode(code){
	if(code==''|| code=='10'|| code=='11'||code=='12'||code=='13'||code=='14'||code=='15'||code=='16'){
		//$('#encryptionLine').hide(); // 商户签名秘钥,商户验签秘钥
		$('#ddpIpLine').hide(); // 都都宝固定IP 输入框 merDdpLinkIp
		$('#ddpIpLable').hide(); // 都都宝固定IP
		//是否自动分配额度
		$('#isAutoDistibuteLine').hide();
	}else if(code=='18'){
		//$('#encryptionLine').show();
		$('#ddpIpLine').show();
		$('#ddpIpLable').show();
	}
	//初始化开户时界面的属性
	clearPrdRateType();
	$('#merParentName').val("");
	$('#merParentCode').val("");
	// 清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
	$('#providerCode').val("");
	$('#providerCodePayment').val("");
	//---------------------新需求更新 2015-12-03--------------
	//merType 13 连锁直营网点
	if(code=='13') {
		$('#ykt').attr("disabled",true);
		$('#yktPayment').attr("disabled",true);
	} else {
		$('#ykt').removeAttr("disabled");
		$('#yktPayment').removeAttr("disabled");
	}
	//merType 14 连锁加盟网点
	if(code == '14' || code=='13'){
		$("input[name='isAutoDistibute'][value=0]").attr("checked", true);
		//连锁直营网点时是否自动分配额度
		$('#isAutoDistibuteOne').show();
		$('#isAutoDistibuteTwo').show();
		// 额度阀值
		$("#limitThreshold").val('');
		// 自动分配额度阀值
		$("#selfMotionLimitThreshold").val('');
		// 当选择是自动分配额度的时候展现
		$("#isAutoDistibuteLine").show();
	}else{
		$('#isAutoDistibuteOne').hide();
		$('#isAutoDistibuteTwo').hide();
		// 额度阀值
		$("#limitThreshold").val('');
		// 自动分配额度阀值
		$("#selfMotionLimitThreshold").val('');
		// 当选择否自动分配额度的时候展现
		$("#isAutoDistibuteLine").hide();
	}
	// Add
	$("#yktDialog").find("input[type=checkbox]").attr("checked",false);
	$("#yktDialogPayment").find("input[type=checkbox]").attr("checked",false);
	if($('#flag').val()=='0'){
		 // 账户类型
		$("input[name='fundType'][value=0]").attr("checked", true);
    	// 商户属性
    	$("input[name='merProperty'][value=0]").attr("checked",true);
    	// 清空保存的自定义功能数据
    	$('#dataflag').val('');
    	// 账户类型
    	$("input[name='fundType'][value=0]").attr("checked",true);
    	$("input[name='fundType']:eq(0)").removeAttr("disabled", 'disabled');
		$("input[name='fundType']:eq(1)").removeAttr("disabled", 'disabled');
    	// 停用，启用
    	$("input[name='activate'][value=0]").attr("checked",true);
    	// 性别
    	$("input[name='merUserSex'][value=0]").attr("checked",true);
    	merPropertyClick('stander');
	}
}
// 上级商户类型
function handleMerParentLine(data){
	if (data.code == "000000") {
		if(typeof(data.responseEntity) !="undefined" && data.responseEntity.length > 0) {
			$("#merTypeParentLine").show();
			reLoadComboboxData($('#merParentType'), data.responseEntity)
		} else {
			$('#merTypeParentLine').hide();
		}
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
function loadMerTypes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merTypeQuery'), data.responseEntity)
		reLoadComboboxData($('#merType'), data.responseEntity);
		$('#merTypeQuery').combobox('select','');
		$('#merType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


// 加载商户分类数据字典
function loadMerchantClassify(){
	$('#merClassifyQuery').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 120
	});
	var ddic = {
			categoryCode : 'MER_CLASSIFY'
	}
	loadDdics(ddic, loadMerchantClassifys);
}
function loadMerchantClassifys(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merClassifyQuery'), data.responseEntity)
		$('#merClassifyQuery').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


// 加载商户属性数据字典
function loadMerProperty(){
	$('#merPropertyQuery').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 120
	});
	var ddic = {
			categoryCode : 'MER_PROPERTY'
	}
	loadDdics(ddic, loadMerPropertys);
}
function loadMerPropertys(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merPropertyQuery'), data.responseEntity)
		$('#merPropertyQuery').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

// 加载证件类型
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

// 加载经营范围
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


// 加载开户银行数据字典
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

/**
 * 打开开户界面是初始化各个属性
 */
function clearPrdRateType(){
// 业务类型 初始化可选 关禁用效果
$('#PrdRateType').find("input[type=checkbox]").removeAttr("checked");
$('#PrdRateType').find("input[type=checkbox]").removeAttr("disabled");
//业务类型默认清空
$('#PrdRateType').html('');
// 通卡公司(充值) 名称加载框默认为空
$('#providerCode').val("");
// 通卡公司(消费) 名称加载框默认为空
$('#providerCodePayment').val("");
// 通卡公司(充值) 费率框默认隐藏
$('#yktTblLine').hide();
// 通卡公司(消费) 费率框默认隐藏
$('#yktTblLinePayment').hide();
// 通卡公司(充值) 名称加载框和费率框默认隐藏
$('#TKNameVerLine').hide();
// 通卡公司(消费) 名称加载框和费率框默认隐藏
$('#TKNameVerLinePayment').hide();
// 通卡公司(圈存) 名称加载框和费率框默认隐藏
$('#IcLoadLine').hide();
$('#yktIcLoadTblLine').hide();
// 外接商户的三个URL 回调时用的 默认隐藏
$('#yktInfoUrlLine').hide();
$('#pageCheckCallbackUrlLine').hide();
//通卡公司(充值) 费率默认清空
$('#yktTbl').clearGridData();
//通卡公司(消费) 费率默认清空
$('#yktTblPayment').clearGridData();
// 初始化清空的时候加载业务类型
findPrdRateType();
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
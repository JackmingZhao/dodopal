/**
	 * 从门户注册过来的商户 1.商户类型可选，上级商户类型和上级商户名称隐藏，通卡公司显示。
	 * 2.当代理商，代理商自由网点，连锁商，连锁商加盟，连锁商自由网点，上级商户类型和上级商户名称可选， 通卡公司根据上级商户名称做展现。
	 * 3.当商户类型是连锁商直营网点的时候，通卡公司选择按钮隐藏，通卡公司费率展现出来，默认不可编辑。
	 * 4.当商户类型为代理商，代理商自由网点，连锁商，连锁商加盟时，上级商户名称选择有费率的时候，通卡 公司名称展示，否则通卡公司这一行隐藏。
	 */
	/**
	 * 从门户创建过来的子商户 1.当上级商户为连锁商的时候，子商户为连锁加盟网点的试试，商户类型，上级商户类型，上级商户名称不可编辑，
	 * 上级商户名称按钮隐藏，如果上级商户无费率，通卡公司一栏隐藏。有费率的时候通卡公司费率可编辑。
	 * 2.当上级商户为代理商的时候，商户类型只显示代理商自由网点，连锁商，代理商这三个商户类型。
	 * 上级商户类型和上级商户名称不可编辑，上级商户名称按钮隐藏。若上级商户为费率，则通卡公司这一栏隐藏，如果有，则可编辑。。
	 */
var merParentBusRateBeanList ;//上级通卡公司是否存在费率
function loadUnVerInfo(data) {
	var merchantBeans = data.responseEntity; // 加载通卡公司类型
	var jsonData = {
			merParentCode : merchantBeans.merParentCode
		};
	
	var merBusRateBeanList = merchantBeans.merBusRateBeanList; // 加载通卡公司费率
	$.ajax({
		async : false,
		type : 'post',
		url : 'findYktInfo',
		dataType : "json",
		data : merchantBeans.merParentCode,
		success : function(data) {
			if (data.code == "000000") {
				merParentBusRateBeanList=data.responseEntity;
			}
		}
	});
	// 1.判断是否有上级名称
	if (isNotBlank(merchantBeans.merParentCode)) {
		// 2.上级商户名称存在的话判断是商户类型和上级商户类型
		checkMerParentTypeAndCode(merchantBeans);
		// 3.上级商户名称存在的话判断是否有上级商户费率 
		if (isNotBlank(merParentBusRateBeanList)) {
			var merBusRateBeanListRecharge = new Array();
			var merBusRateBeanListPayment = new Array();
			for(var i=0;i<merBusRateBeanList.length;i++) {
				var rateCode = merBusRateBeanList[i].rateCode;
				if(rateCode == '01') {
					merBusRateBeanListRecharge.push(merBusRateBeanList[i]);
				} else if(rateCode == '03') {
					merBusRateBeanListPayment.push(merBusRateBeanList[i])
				}
			}
			
			// 4.子级商户费率存在的话，根据上级商户类型和子级商户类型进行判断
			if (isNotBlank(merBusRateBeanListRecharge)) { // 商户费率不能为空
				$('#TKNameLine').show(); // 显示通卡公司名称选择按钮
				$('#ykt').removeAttr("disabled");

				$('#yktTblLine').show(); // 显示通卡公司费率信息
				$('#yktTbl').show(); // 显示通卡公司费率信息
				$('#yktTbl').setColProp('rateType', {
					editable : false
				});
				$('#yktTbl').setColProp('rate', {
					editable : true
				});
				$('#yktTbl').setColProp('rateType',{editable: false});
				loadJqGridData($('#yktTbl'), merBusRateBeanListRecharge); // 加载费率表
				$('#yktTbl').jqGrid('setGridHeight',
						25 * merBusRateBeanListRecharge.length);
				var selected = '';
				$.each(merBusRateBeanListRecharge, function(index, element) {
					if (selected != '') {
						selected += ',';
					}
					selected += element.proName;
				});
				$('#providerCode').val(selected); // 加载通卡公司名称
				selectAllLines('yktTbl'); // 显示通卡公司勾选情况
				$('#cb_yktTbl').attr('checked','checked');
				$('#cb_yktTbl').attr('disabled','disabled');
				//$('#encryptionLine').hide();
				$('#ddpIpLine').hide();
				$('#ddpIpLable').hide();
			} 
			
			if (isNotBlank(merBusRateBeanListPayment)) { // 商户费率不能为空
				$('#TKNameLinePayment').show(); // 显示通卡公司名称选择按钮
				$('#yktPayment').removeAttr("disabled");

				$('#yktTblLinePayment').show(); // 显示通卡公司费率信息
				$('#yktTblPayment').show(); // 显示通卡公司费率信息
				$('#yktTblPayment').setColProp('rateType', {
					editable : false
				});
				$('#yktTblPayment').setColProp('rate', {
					editable : true
				});
				$('#yktTblPayment').setColProp('rateType',{editable: false});
				loadJqGridData($('#yktTblPayment'), merBusRateBeanListPayment); // 加载费率表
				$('#yktTblPayment').jqGrid('setGridHeight',
						25 * merBusRateBeanListPayment.length);
				var selectedPayment = '';
				$.each(merBusRateBeanListPayment, function(index, element) {
					if (selectedPayment != '') {
						selectedPayment += ',';
					}
					selectedPayment += element.proName;
				});
				$('#providerCodePayment').val(selectedPayment); // 加载通卡公司名称
				selectAllLines('yktTblPayment'); // 显示通卡公司勾选情况
				$('#cb_yktTblPayment').attr('checked','checked');
				$('#cb_yktTblPayment').attr('disabled','disabled');
			} 
		}else{
			//上级无费率的通卡公司
			$('#TKNameLine').hide(); // 显示通卡公司名称选择按钮
			$('#ykt').attr("disabled", true);
			$('#yktTblLine').hide(); // 显示通卡公司费率信息
			$('#yktTbl').hide(); // 显示通卡公司费率信息
			
			$('#TKNameLinePayment').hide();
			$('#yktPayment').attr("disabled", true);
			$('#yktTblLinePayment').hide(); 
			$('#yktTblPayment').hide();
		}
	} else{
		// 2.上级商户名称为空
		//$('#TKNameLine').show(); // 显示通卡公司按钮
		$('#ykt').removeAttr("disabled");
		$('#yktTblLine').hide(); // 初始化隐藏通卡公司费率信息
		$('#yktPayment').removeAttr("disabled");
		$('#yktTblLinePayment').hide();

		$("#merParentTypes").combobox({
			disabled : false
		});

		// 初始化加载数据字典
		loadUnVerMerchantType();
	}

}


/***
* 审核时初始化判断上级商户是否有费率
*/
function findMerParentCode(merParentCode){
	if(isNotBlank(merParentCode)) {
		ddpAjaxCall({
			url : "findYktInfo",
			data : merParentCode,
			successFn : loadMerParentCode
		});
		
	}
}


function loadMerParentCode(data){
	var merParentBusRateBeanList ;//上级通卡公司是否存在费率
	if(data.code=='000000'){
		merParentBusRateBeanList=data.responseEntity;
		
	}
	 
}


/**
 * 1.从门户创建子商户
 * 2.在OSS审核子商户时判断上级商户CODE
 * 3.判断情况是代理商和连锁商户
 */
function checkMerParentTypeAndCode(merchantBeans){
		//1.在上级商户的情况下判断商户类型和费率信息
		if(merchantBeans.merParentType=='12'&&merchantBeans.merParentCode!=null){ //如果上级商户类型是连锁商商
			$("#merTypeParentLine").show(); //显示上级商户类型和上级商户名称
			//$('#finMER').hide();//上级商户名称按钮隐藏
			$('#finMER').attr("disabled", true);
			$("#merParentTypes").combobox({  
	            disabled:true  
	        }); //上级商户类型禁用
			//上级商户名称和上级商户类型显示
			$('#merParentTypes').combobox('select',merchantBeans.merParentType==null?"null":merchantBeans.merParentType);
			//商户类型
			setTimeout(function() {
				$('#merType').combobox('select',merchantBeans.merType);
			}, 400);
			//$('#encryptionLine').hide();
			$('#ddpIpLine').hide();
			$('#ddpIpLable').hide();
		}else if(merchantBeans.merParentType=='10'&&merchantBeans.merParentCode!=null){
			$("#merTypeParentLine").show(); //显示上级商户类型和上级商户名称
			//$('#finMER').hide();//上级商户名称按钮隐藏
			$('#finMER').attr("disabled", true);
			$("#merParentTypes").combobox({  
	            disabled:true  
	        }); 
			//上级商户名称和上级商户类型显示
			$('#merParentTypes').combobox('select',merchantBeans.merParentType==null?"null":merchantBeans.merParentType);
			//商户类型
			loadMerParentType(merchantBeans.merParentType);
			//$('#encryptionLine').hide();
			$('#ddpIpLine').hide();
			$('#ddpIpLable').hide();
		}else{
			$("#merTypeParentLine").hide(); //显示上级商户类型和上级商户名称
			//$('#finMER').show();//上级商户名称按钮隐藏
			$('#finMER').removeAttr("disabled");
			$("#merParentTypes").combobox({  
	            disabled:false  
	        });
		}
			
}


/**
 * 有上级商户名称的时候初始化加载上级商户类型和商户类型 如上级商户为代理商和连锁商的时候
 * @param merParentType
 */
function loadMerParentType(merParentType){
	$('#merType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200,
		onSelect : function(record){
//			$('#PrdRateType').html('');
//			//初始化业务类型
//			findPrdRateType();
			clearPrdRateType();
	}
	});
	var ddic = {
			categoryCode : 'MER_TYPE_CHILD_OSS_' + merParentType
	}
	loadDdics(ddic, loadMerParentTypes);
}
function loadMerParentTypes(data) {
	if (data.code == "000000") {
	    reLoadComboboxData($('#merType'), data.responseEntity);
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

/**
 * 1.从门户注册过来的商户
 * 2.商户类型的变动清空上级商户类型，上级商户名称，通卡公司名称，通卡公司费率信息
 */
function loadUnVerMerchantType(){
	$('#merParentTypes').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 200,
		onSelect : function(record){
//				$('#merParentName').val("");
//				$('#merParentCode').val("");
				//清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率				
				if(!(isBlank($('#merPT').val()) && $('#merParentTypes').combobox("getValue")=="null")){
					clearPrdRateType();
				}
				$("#ykt").show();
				$("#yktPayment").show();
				// Add
				$("#yktDialog").find("input[type=checkbox]").attr("checked",false);
				$("#yktDialogPayment").find("input[type=checkbox]").attr("checked",false);
				if($('#merParentTypes').combobox('getValue')=='null'||$('#merParentTypes').combobox('getValue')==null){
					//$("#finMER").hide();
					$("#finMER").attr("disabled",true);
				}else{
					//$("#finMER").show();
					$("#finMER").removeAttr("disabled");
				}
				
				$('#merPT').val($('#merParentTypes').combobox("getValue"));	
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
				loadDdics(ddic, handleMerParentLine);
				findmerTypeByCode(record.code);
		}
	});
	var ddic = {
			categoryCode : 'MER_TYPE_REG'
	}
	loadDdics(ddic, loadUnVerMerchantTypes);
}

//商户类型
function loadUnVerMerchantTypes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merType'), data.responseEntity);
		$('#merType').combobox('select','');
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

/**
 * 开户时选择商户类型变动的界面效果 10 代理商户 11 代理商户自由网点 12 连锁商户 13 连锁直营网点 14 连锁加盟网点 15 都都宝自由网点
 * 16 集团商户 17 供应商 18 外接商户 99 个人
 * 
 * @param {}
 *            code
 */
function findmerTypeByCode(code){
	if(code==''|| code=='10'|| code=='11'||code=='12'||code=='13'||code=='14'||code=='15'||code=='16'){
		//$('#encryptionLine').hide();
		$('#ddpIpLine').hide();
		$('#ddpIpLable').hide();
	}else if(code=='18'){
		//$('#encryptionLine').show();
		$('#ddpIpLine').show();
		$('#ddpIpLable').show();
	}
	
	//---------------------新需求更新 2015-12-15--------------
	//merType 13 连锁直营网点
	if(code=='13') {
		//连锁直营网点时是否自动分配额度
//		$('#isAutoDistibuteOne').show();
//		$('#isAutoDistibuteTwo').show();
		$('#ykt').attr("disabled",true);
		$('#yktPayment').attr("disabled",true);
	} else {
//		$('#isAutoDistibuteOne').hide();
//		$('#isAutoDistibuteTwo').hide();
		$('#ykt').removeAttr("disabled");
		$('#yktPayment').removeAttr("disabled");
	}
	//merType 14 连锁加盟网点
//	if(code == '14'){
//		$('#limitSourceOne').show();
//		$('#limitSourceTwo').show();
//	}else{
//		$('#limitSourceOne').hide();
//		$('#limitSourceTwo').hide();
//	}
	
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
	clearPrdRateType();
	$('#merParentName').val("");
	$('#merParentCode').val("");
	//清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
	$('#providerCode').val("");
	$('#providerCodePayment').val("");

	//$("#TKNameLine").show();
	//$("#ykt").show();
	$('#ykt').removeAttr("disabled");
	$('#yktPayment').removeAttr("disabled");
	 // 账户类型
	$("input[name='fundType'][value=0]").attr("checked", true);
	$("input[name='fundType']:eq(0)").removeAttr("disabled", 'disabled');
	$("input[name='fundType']:eq(1)").removeAttr("disabled", 'disabled');
	
	
	$('#yktTblLine').hide();
	$('#yktTbl').clearGridData();
	//$("#finMER").show();
	$('#ykt').removeAttr("disabled");
	$('#yktTblLinePayment').hide();
	$('#yktTblPayment').clearGridData();
	//$("#finMER").show();
	$('#yktPayment').removeAttr("disabled");
	//上级商户类型隐藏
	$("#merTypeParentLine").hide();
	// Add
	$("#yktDialog").find("input[type=checkbox]").attr("checked",false);
	$("#yktDialogPayment").find("input[type=checkbox]").attr("checked",false);
	//商户属性
	$("input[name='merProperty'][value=0]").attr("checked",true);
	//清空保存的自定义功能数据
	$('#dataflag').val('');
	//账户类型
	$("input[name='accountType'][value=0]").attr("checked",true);
	//停用，启用
	$("input[name='activate'][value=0]").attr("checked",true);
	$("input[name='activate']:eq(0)").attr("disabled","disabled"); 
	$("input[name='activate']:eq(1)").attr("disabled","disabled"); 
	//性别
	$("input[name='merUserSex'][value=0]").attr("checked",true);
	merPropertyClick('stander');
}

//初始化清空业务类型
function clearPrdRateType(){
//业务类型取消默认选择
$('#PrdRateType').find("input[type=checkbox]").removeAttr("checked");
$('#PrdRateType').find("input[type=checkbox]").removeAttr("disabled");
//通卡公司(充值)
$('#providerCode').val("");
$('#yktTblLine').hide(); //充值列表
$('#TKNameLine').hide();//隐藏通卡公司
$('#yktTbl').clearGridData();
//外接商户URL
$('#yktInfoUrlLine').hide();
$('#pageCheckCallbackUrlLine').hide();
//通卡公司(消费)
$('#providerCodePayment').val("");
$('#yktTblLinePayment').hide();
$('#TKNameLinePayment').hide();
//$('#yktInfoUrlLinePayment').hide();
$('#yktTblPayment').clearGridData();


// 通卡公司(圈存) 名称加载框和费率框默认隐藏 ---2016-01-05---
$('#icLoad').val("");
$('#IcLoadLine').hide();
$('#yktIcLoadTblLine').hide();
$('#yktIcLoadTbl').clearGridData();
//初始化业务类型
$('#PrdRateType').html('');
findPrdRateType();
}
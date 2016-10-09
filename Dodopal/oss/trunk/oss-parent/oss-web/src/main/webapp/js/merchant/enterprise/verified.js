$(function() {
			initMainComponent();
			initDetailComponent();
			initIsNotNumber();
			//导出列表 2015-12-21
			expExcBySelColTools.initSelectExportDia(expConfInfo);
			parent.closeGlobalWaitingDialog();
		});

function initDetailComponent() {
	$('#merAddressLine').prepend(createAreaComponent2('merAddress'));
	initAreaComponent('merAddress', globalAreaComboboxWidth);
	initYktLoadInfo();
	initMerchantDialog();
	$('#customerDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 300,
		height : 350,
		/*
		 * onClose:function(){ alert(123); $('#customerProperty').tree({ checked :
		 * false }); },
		 */
		buttons : [{
					text : '确定',
					handler : function() {
						// 拿到所选商户类型
						var merType = $('#merType').combobox('getValue');
						// 拿到自定义商户所选选项值
						var idList = '';
						var $selectAll = $('#customerProperty')
								.tree('getChecked');
						var selected = "";
						$.each($selectAll, function(index, element) {
									var merFunCode = element.id;
									var merFunName = element.text;
									idList += merFunCode + '|';
								});
						$('#dataflag').val(idList);
						// return
						// $('#customerDialog').dialog().removeEventListener("onClose");
						hideDialog('customerDialog');
					}
				}]
	});
	$('#merDisCountDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 1050,
		height : 370
	});
	
	$('#merDisCountAllDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 1050,
		height : 350
	});
	initMerchantSearchModel();
}

function initMainComponent() {
	initMerchantDdicComponent();
//	initChangeListTbl();
//	initAllTranDiscountTbl();
	$('#proCityQuery').append(createAreaComponent2('proCityQuery'));
	initAreaComponent('proCityQuery', globalAreaComboboxWidth);
	autoResize({
				dataGrid : {
					selector : '#merchantUserTbl',
					offsetHeight : 100,
					offsetWidth : 0
				},
				callback : initMerchantTbl,
				toolbar : [true, 'top'],
				dialogs : ['OpenMerchantDialog']
			});

}
//监听输入值是否为数字或者带小数点后两位的
function initIsNotNumber(){
	//年收入
$("#income").bind('keyup', function() {
		notNumber($(this));
	});
//额度阀值
$("#limitThreshold").bind('keyup', function() {
		clearlimitThresholdNoNum($(this));
	});
//自动分配额度阀值			
$("#selfMotionLimitThreshold").bind('keyup', function() {
		clearAutoLimitThresholdNoNum($(this));
	});
}
// 初始化开户所有radio属性
function radioMerchant() {
	// 商户分类
	$("input[name='merClassify'][value=0]").attr("checked", true);
	// 商户属性
	$("input[name='merProperty'][value=0]").attr("checked", true);
	// 账户类型
	$("input[name='fundType'][value=0]").attr("checked", true);
	$("input[name='fundType']:eq(0)").removeAttr("disabled", 'disabled');
	$("input[name='fundType']:eq(1)").removeAttr("disabled", 'disabled');
	// 停用，启用
	$("input[name='activate'][value=0]").attr("checked", true);

	$("input[name='activate']:eq(0)").removeAttr("disabled", 'disabled');
	$("input[name='activate']:eq(1)").removeAttr("disabled", 'disabled');
	// 性别
	$("input[name='merUserSex'][value=0]").attr("checked", true);
	// 是否弹出错误信息
	$("input[name='isShowErrorMsg'][value=0]").attr("checked", true);
	// 是否自动分配额度  默认是分配额度
	$("input[name='isAutoDistibute'][value=0]").attr("checked", true);
	// 额度来源
	//$("input[name='limitSource'][value=0]").attr("checked", true);
	// 隐藏自定义商户查看
	merPropertyClick('stander');
}

// 上级商户名称
function findMerParentName() {
	if (isNotSelected($('#merParentType').combobox('getValue'))) {
		msgShow(systemWarnLabel, "请先选择上级商户类型!", 'warning');
	} else {
		$('#merchantQuery').val('');
		// 清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
		$('#providerCode').val("");
		$('#yktTblLine').hide();
		$('#yktTbl').clearGridData();
		$('#providerCodePayment').val("");
		$('#yktTblLinePayment').hide();
		$('#yktTblPayment').clearGridData();
		findMerchantName();
		showDialog('merchantSearchDialog');
	}
}

// 初始化加载已审核商户信息列表
function initMerchantTbl(size) {
	var option = {
		datatype : function(pdata) {
			findMerchantUsers();
		},
		colNames : ['ID', '商户名称', '商户编码', '商户类型', '商户分类', '商户属性', '上级商户名称',
				'手机号码', 'merPro', 'merCity', '省份', '城市', '来源', '启用标识'],
		colModel : [{
					name : 'id',
					hidden : true
				}, {
					name : 'merName',
					index : 'merName',
					width : 130,
					align : 'left',
					sortable : false
				}, {
					name : 'merCode',
					index : 'merCode',
					width : 130,
					align : 'left',
					sortable : false
				}, {
					name : 'merTypeView',
					index : 'merTypeView',
					width : 100,
					align : 'left',
					sortable : false
				}, {
					name : 'merClassifyView',
					index : 'merClassifyView',
					width : 100,
					align : 'left',
					sortable : false
				}, {
					name : 'merPropertyView',
					index : 'merPropertyView',
					width : 100,
					align : 'left',
					sortable : false
				}, {
					name : 'merParentName',
					index : 'merParentName',
					width : 100,
					align : 'left',
					sortable : false
				}, {
					name : 'merLinkUserMobile',
					index : 'merLinkUserMobile',
					width : 100,
					align : 'left',
					sortable : false
				}, {
					name : 'merPro',
					index : 'merPro',
					hidden : true
				}, {
					name : 'merCity',
					index : 'merCity',
					hidden : true
				}, {
					name : 'merProName',
					index : 'merProName',
					width : 70,
					align : 'left',
					sortable : false
				}, {
					name : 'merCityName',
					index : 'merCityName',
					width : 60,
					align : 'left',
					sortable : false
				}, {
					name : 'sourceView',
					index : 'sourceView',
					width : 70,
					align : 'left',
					sortable : false
				}, {
					name : 'activateView',
					index : 'activateView',
					width : 70,
					align : 'left',
					sortable : false
				}],
		// caption : "已审核商户信息列表",
		// sortname : 'merCode',
		height : size.height - 100,
		width : size.width,
		multiselect : true,
		pager : '#merchantUserTblPagination',
		toolbar : [true, "top"]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#merchantUserTbl').jqGrid(option);
	$("#t_merchantUserTbl").append($('#merchantUserTblToolbar'));
}

function initMerchantDialog() {
	$('#OpenMerchantDialog').dialog({
				collapsible : true,
				modal : true,
				closed : true,
				closable : false
			});
}

function showUserDialog() {
	$('#OpenMerchantDialog').show().dialog('open');
}

function hideUserDialog() {
	clearForm();
	$('#OpenMerchantDialog').hide().dialog('close');
}
function afterAddMerchant(data) {
	if (data.code == "000000") {
		$('#merCode').val('');
		clearForm();
		$('#OpenMerchantDialog').hide().dialog('close');
		findMerchantUsers();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function afterUpdateMerchant(data) {
	if (data.code == "000000") {
		findMerchantUsers();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

// 初始化和查询商户信息列表
function findMerchantUsers(defaultPageNo) {
	// 商户名称
	var merNameQuery = $('#merNameQuery').val();
	// 商户编码
	var merCodeQuery = $('#merCodeQuery').val();
	// 上级商户名称
	var merParentNameQuery = $('#merParentNameQuery').val();
	// 商户类型
	var merTypeQuery = $('#merTypeQuery').combobox('getValue');
	// 商户分类
	var merClassifyQuery = $('#merClassifyQuery').combobox('getValue');
	// 商户属性
	var merPropertyQuery = $('#merPropertyQuery').combobox('getValue');
	// 省份
	var merProQuery = getProvinceCodeFromCompoent('proCityQuery');
	// 城市
	var merCityQuery = getCityCodeFromCompoent('proCityQuery');
	// 启用标识
	var activateQuery = $('#activateQuery').combobox('getValue');
	// 来源
	var sourceQuery = $('#sourceQuery').combobox('getValue');

	var bussQuery = $('#bussQuery').combobox('getValue');
	ddpAjaxCall({
				url : "findMerchantsPage",
				data : {
					merName : merNameQuery,
					merCode : merCodeQuery,
					merParentName : merParentNameQuery,
					merType : merTypeQuery,
					merClassify : merClassifyQuery,
					merProperty : merPropertyQuery,
					merCity : merCityQuery,
					merPro : merProQuery,
					activate : activateQuery,
					merState : '1',
					source : sourceQuery,
					bussQuery : bussQuery,
					page : getJqgridPage('merchantUserTbl', defaultPageNo)
				},
				successFn : function(data) {
					if (data.code == "000000") {
						loadJqGridPageData($('#merchantUserTbl'),
								data.responseEntity);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
}

// 打开开户界面
function auditVerified() {
	clearForm();
	$('#merTypeParentLine').hide();
	// 恢复所有radio标签的属性值
	radioMerchant();

	// 点击开户禁用的字段改为启用
	$('#merName').attr('disabled', false);
	$('#merUserName').attr('disabled', false);
	// 商户类型不能修改，上级商户类型不能修改，选择置灰不能点击。
	$('#merType').combobox('enable');
	$('#merParentType').combobox('enable');
	// $('#finMER').show();
	$('#finMER').removeAttr("disabled");
	// 备注信息清空
	$('#merUserRemark').val('');
	// 商户分类不能修改
	$("input[name='merClassify']").attr("disabled", false);
	//额度来源初始化不能修改
	//$("input[name='limitSource']").attr("disabled", false);
	// 省市禁用
	startAreaComponent('merAddress');
	// 地址禁用
	$('#merAddressName').attr('disabled', false);
	// 开户时重新加载商户业务类型
	$('#PrdRateType').html('');
	findPrdRateType();
	// 业务类型
	$('#PrdRateType').find("input[type=checkbox]").removeAttr("checked");
	$('#TKNameVerLine').hide();
	$('#TKNameVerLinePayment').hide();
	
	// 外接商户
	wjMerchant();
	$('#flag').val('0');
	
	//自动分配额度和额度来源
	$('#isAutoDistibuteOne').hide();
	$('#isAutoDistibuteTwo').hide();
//	$('#limitSourceOne').hide();
//	$('#limitSourceTwo').hide();
	$('#isAutoDistibuteLine').hide();
	showDialog('OpenMerchantDialog');
}
// 外接商户隐藏和情况
function wjMerchant() {
	$('#ddpIpLable').hide();
	$('#ddpIpLine').hide();
	//$('#encryptionLine').hide();
	$('#merDdpLinkIp').val('');
	$('#merMd5paypwd').val('');
	$('#merMd5Backpaypwd').val('');
	// ---新增字段 2015-12-05---
	$('#yktInfoUrlLine').hide();
	$('#pageCheckCallbackUrlLine').hide();
	$('#YktPageCallbackUrl').val('');
	$('#YktServiceNoticeUrl').val('');
	$('#pageCheckCallbackUrl').val('');
	//-----清空圈存-------
	$('#IcLoadLine').hide();
	$('#icLoad').val('');
}

// 效验开户信息
function openMerchantUser() {
	$('#dataflag').val('');
	var merType = $('#merType').combobox('getValue');
	 if (isValidate('OpenMerchantDialog')) {
	$.messager.confirm(systemConfirmLabel, "确定要保存商户信息吗？", function(r) {
				if (r) {
					var selarrrow = $("#yktTbl").getGridParam('selarrrow');// 业务费率多选
					var selarrrowPayment = $("#yktTblPayment")
							.getGridParam('selarrrow');// 消费业务费率多选
					var checkedRecharge = $("input[id=01][type='checkbox']")
							.is(':checked');
					var checkedPayment = $("input[id=03][type='checkbox']")
							.is(':checked');

					if (checkedRecharge && selarrrow && selarrrow.length == 0) {
						msgShow(systemWarnLabel, '请选择通卡公司(充值)', 'warning');
						return;
					}

					if (checkedPayment && selarrrowPayment
							&& selarrrowPayment.length == 0) {
						msgShow(systemWarnLabel, '请选择通卡公司(消费)', 'warning');
						return;
					}
					// 获取费率信息
					var merBusRateBeanList = new Array();
					if (selarrrow || selarrrowPayment) {
						for (var i = 0; i < selarrrow.length; i++) {
							var rowData = $("#yktTbl").getRowData(selarrrow[i]);
							if (isBlank(rowData.rate)) {
								msgShow(systemWarnLabel, "充值费率不能为空.", 'warning');
								return;
							}
							var rateType;
							if (rowData.rateType == '单笔返点金额(元)') {
								rateType = 1;
							} else if (rowData.rateType == '千分比(‰)') {
								rateType = 2;
							}
							var merBusRateBean = {
								rateType : rateType,
								rate : parseFloat(rowData.rate),
								proCode : rowData.proCode,
								rateCode : rowData.rateCode
							}
							merBusRateBeanList.push(merBusRateBean);
						}
						for (var i = 0; i < selarrrowPayment.length; i++) {
							var rowData = $("#yktTblPayment")
									.getRowData(selarrrowPayment[i]);
							if (isBlank(rowData.rate)) {
								msgShow(systemWarnLabel, "消费费率不能为空.", 'warning');
								return;
							}
							var rateType;
							if (rowData.rateType == '单笔返点金额(元)') {
								rateType = 1;
							} else if (rowData.rateType == '千分比(‰)') {
								rateType = 2;
							}
							var merBusRateBean = {
								rateType : rateType,
								rate : parseFloat(rowData.rate),
								proCode : rowData.proCode,
								rateCode : rowData.rateCode
							}
							merBusRateBeanList.push(merBusRateBean);
						}
					} else {
						msgShow(systemWarnLabel, unSelectLabel, 'warning');
					};

					// 判断上级商户类型
					var merParentType = $('#merParentType')
							.combobox('getValue');
					// 判断商户类型
					if (!isBlank(merType)) {
						if (merType == "10" || merType == "12"
								|| merType == '11' || merType == '13'
								|| merType == '14') {
							if (merParentType == '10' || merParentType == '12') {
								if ($('#merParentName').val() == "") {
									msgShow(systemWarnLabel, "请选择上级商户名称!",
											'warning');
									return false;
								}
							} else if (merParentType != "null") {
								msgShow(systemWarnLabel, "请选择上级商户类型!",
										'warning');
								return false;
							}
						}
					} else {
						msgShow(systemWarnLabel, "请选择商户类型!", 'warning');
						return false;
					}
					
					//-------------判断结算类型-----------
					// 结算类型
					var settlementType = $('#settlementType').combobox('getValue');
					if(isBlank(settlementType)){
						msgShow(systemWarnLabel, "请先选择结算类型!", 'warning');
						return false;
					}
					// 结算阀值
					var settlementThreshold = $('#settlementThreshold').val();
					if(isBlank(settlementThreshold)){
						msgShow(systemWarnLabel, "请先输入结算阀值!", 'warning');
						return false;
					}
					
					
					// 判断省市没有选择的应该为请选择
					var merPro = getProvinceCodeFromCompoent('merAddress');
					var merCity = getCityCodeFromCompoent('merAddress');
					if (isBlank(merPro)) {
						msgShow(systemWarnLabel, "请先选择省份!", 'warning');
						return false;
					} else if (isBlank(merCity)) {
						msgShow(systemWarnLabel, "请先选择市区!", 'warning');
						return false;
					}
					//-----判断连锁直营网点是否自动分配额度-------------
					var isAutoDistibute = $('input[name="isAutoDistibute"]:checked').val();
					var limitThreshold = $('#limitThreshold').val();
					var selfMotionLimitThreshold = $('#selfMotionLimitThreshold').val();
					if(isAutoDistibute=="0"){
						if (merType == '13' || merType == '14'){
							if(isBlank(limitThreshold)){
								msgShow(systemWarnLabel, "请输入额度阀值!", 'warning');
								return false;
							}
							if(isBlank(selfMotionLimitThreshold)){
								msgShow(systemWarnLabel, "请输入自动分配额度到阀值!", 'warning');
								return false;
							}
						}
					}
					
					var merchantBean = {
						merCode : $('#merCode').val(),
						merBusRateBeanList : merBusRateBeanList
					};
					ddpAjaxCall({
								url : "checkRelationMerchantProviderAndRateType",
								data : merchantBean,
								successFn : afterCheckRelationMerchant
							});
				}
			});
	 }
}
// 效验费率信息
function afterCheckRelationMerchant(data) {
	if (data.code != "000000") {
		$.messager.confirm(systemConfirmLabel, data.message, function(r) {
					if (r) {
						saveMerchant();
					}
				});
	} else {
		saveMerchant();
	}
}
// 保存开户的信息 2015-12-05
function saveMerchant() {
	var activate = $('input[name="activate"]:checked').val();
	var merClassify = $('input[name="merClassify"]:checked').val();
	var merProperty = $('input[name="merProperty"]:checked').val();
	var merUserSex = $('input[name="merUserSex"]:checked').val();
	var merType = $('#merType').combobox('getValue');
	var fundType = $('input[name="fundType"]:checked').val();
	// 经营范围
	var merBusinessScopeId = $('#merBusinessScopeId').combobox('getValue');
	// 证件类型
	var merUserIdentityType = $('#merUserIdentityType').combobox('getValue');
	// 开户银行
	var merBankName = $('#merBankName').combobox('getValue');

	var selarrrow = $("#yktTbl").getGridParam('selarrrow');// 业务费率多选
	var selarrrowPayment = $("#yktTblPayment").getGridParam('selarrrow');// 消费业务费率多选
	var selarrrowIcLoad = $("#yktIcLoadTbl").getGridParam('selarrrow');// 圈存业务费率多选
	
	var checkedRecharge = $("input[id=01][type='checkbox']").is(':checked');
	var checkedPayment = $("input[id=03][type='checkbox']").is(':checked');
	var checkedIcLoad = $("input[id=04][type='checkbox']").is(':checked');
	
	if (checkedRecharge && selarrrow && selarrrow.length == 0) {
		msgShow(systemWarnLabel, '请选择通卡公司(充值)', 'warning');
		return;
	}

	if (checkedPayment && selarrrowPayment && selarrrowPayment.length == 0) {
		msgShow(systemWarnLabel, '请选择通卡公司(消费)', 'warning');
		return;
	}

	if (checkedIcLoad && selarrrowIcLoad && selarrrowIcLoad.length == 0) {
		msgShow(systemWarnLabel, '请选择通卡公司(圈存)', 'warning');
		return;
	}
	
	var merBusRateBeanList = new Array();
	if (selarrrow || selarrrowPayment || selarrrowIcLoad) {
		//一卡通充值
		for (var i = 0; i < selarrrow.length; i++) {
			var rowData = $("#yktTbl").getRowData(selarrrow[i]);
			if (isBlank(rowData.rate)) {
				msgShow(systemWarnLabel, "充值费率不能为空.", 'warning');
				return;
			}
			var rateType;
			if (rowData.rateType == '单笔返点金额(元)') {
				rateType = 1;
			} else if (rowData.rateType == '千分比(‰)') {
				rateType = 2;
			}
			var merBusRateBean = {
				rateType : rateType,
				rate : parseFloat(rowData.rate),
				proCode : rowData.proCode,
				rateCode : rowData.rateCode
			};
			merBusRateBeanList.push(merBusRateBean);
		}
		//一卡通消费
		for (var i = 0; i < selarrrowPayment.length; i++) {
			var rowData = $("#yktTblPayment").getRowData(selarrrowPayment[i]);
			if (isBlank(rowData.rate)) {
				msgShow(systemWarnLabel, "消费费率不能为空.", 'warning');
				return;
			}
			var rateType;
			if (rowData.rateType == '单笔返点金额(元)') {
				rateType = 1;
			} else if (rowData.rateType == '千分比(‰)') {
				rateType = 2;
			}
			var merBusRateBean = {
				rateType : rateType,
				rate : parseFloat(rowData.rate),
				proCode : rowData.proCode,
				rateCode : rowData.rateCode
			};
			merBusRateBeanList.push(merBusRateBean);
		}
		//圈存
		for (var i = 0; i < selarrrowIcLoad.length; i++) {
			var rowData = $("#yktIcLoadTbl").getRowData(selarrrowIcLoad[i]);
			if (isBlank(rowData.rate)) {
				msgShow(systemWarnLabel, "圈存费率不能为空.", 'warning');
				return;
			}
			var rateType;
			if (rowData.rateType == '单笔返点金额(元)') {
				rateType = 1;
			} else if (rowData.rateType == '千分比(‰)') {
				rateType = 2;
			}
			var merBusRateBean = {
				rateType : rateType,
				rate : parseFloat(rowData.rate),
				proCode : rowData.proCode,
				rateCode : rowData.rateCode
			};
			merBusRateBeanList.push(merBusRateBean);
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	};
	// 添加自定义商户功能
	var merDefineFunBeanList = new Array();

	var $selectAll = $('#customerProperty').tree('getChecked');
	var selected = "";
	$.each($selectAll, function(index, element) {
				var merFunCode = element.id;
				var merFunName = element.text;
				var merDefineFunBean = {
					merFunCode : merFunCode,
					merFunName : merFunName
				};
				merDefineFunBeanList.push(merDefineFunBean);
			});
	// // 判断上级商户类型
	var merParentType = $('#merParentType').combobox('getValue');
	
	// // 判断省市没有选择的应该为请选择
	var merPro = getProvinceCodeFromCompoent('merAddress');
	var merCity = getCityCodeFromCompoent('merAddress');
	
	// 商户业务信息
	var merRateSpmtList = new Array();
	// 现在判断是否勾选当前业务类型
	var pageCallbackUrl = "";// 页面回调URL
	var serviceNoticeUrl = "";// 服务器回调URL
	var pageCheckCallbackUrl = "";// 服务器验卡URL
	// 外接商户是否弹出商户信息
	var isShowErrorMsg = "";
	var prdRateTypeAll = $('#PrdRateType').find("input[type=checkbox]:checked");
	$.each(prdRateTypeAll, function(index, element) {
				var rateCode = $('#' + element.id).val();
				if (rateCode == '01') {
					pageCallbackUrl = $('#YktPageCallbackUrl').val();
					serviceNoticeUrl = $('#YktServiceNoticeUrl').val();
					pageCheckCallbackUrl = $('#pageCheckCallbackUrl').val();
					isShowErrorMsg = $('input[name="isShowErrorMsg"]:checked')
							.val()
				}
				var merRateSpmtBean = {
					rateCode : rateCode,
					pageCallbackUrl : pageCallbackUrl,
					serviceNoticeUrl : serviceNoticeUrl,
					pageCheckCallbackUrl : pageCheckCallbackUrl,
					isShowErrorMsg : isShowErrorMsg
				}
				merRateSpmtList.push(merRateSpmtBean);
			});

	// ------------------新需求新增字段 2015-12-05---------------
	// 结算类型
	var settlementType = $('#settlementType').combobox('getValue');
	// 结算阀值
	var settlementThreshold = $('#settlementThreshold').val();
	if (settlementType == '2') {
		settlementThreshold = settlementThreshold * 100;
	}
	// 商圈
	var tradeArea = $('#tradeArea').val();
	// 学历
	var education = $('#education').combobox('getValue');
	// 收入
	var income = "";
	if($('#income').val()!=null&&$('#income').val()!=""){
		income =$('#income').val()*100;
	}
	// 出生年月
	var birthday = $('#birthday').val();
	// 婚姻状况
	var isMarried = $('#isMarried').combobox('getValue');
	

	var merchantUserBean = {
		userCode : $('#userCode').val(),
		merUserName : $('#merUserName').val(),
		merUserNickName : $('#merLinkUser').val(),
		merUserMobile : $('#merLinkUserMobile').val(),
		merUserAdds : $('#merAddressName').val(),
		merUserSex : merUserSex,
		merUserTelephone : $('#merUserTelephone').val(),
		merUserIdentityType : merUserIdentityType,
		merUserIdentityNumber : $('#merUserIdentityNumber').val(),
		merUserEmail : $('#merUserEmail').val(),
		merUserRemark : $('#merUserRemark').val(),
		tradeArea : tradeArea,
		education : education,
		income : income,
		birthday : birthday,
		isMarried : isMarried
	};
	//--------------判断连锁直营网点直营网点--------------------
		var isAutoDistibute = $('input[name="isAutoDistibute"]:checked').val();
		var limitThreshold = $('#limitThreshold').val()*100;
		var selfMotionLimitThreshold = $('#selfMotionLimitThreshold').val()*100;
		var merAutoAmtBean = {
		 limitThreshold:limitThreshold,
		 autoLimitThreshold: selfMotionLimitThreshold
		};
	//------连锁加盟网点
		//var limitSource = $('input[name="limitSource"]:checked').val();
	// 商户签名密钥
	var merKeyTypeBean = {
		merMD5PayPwd : $('#merMd5paypwd').val(),
		merMD5BackPayPWD : $('#merMd5Backpaypwd').val()
	};

	// 商户补充信息
	var merDdpInfoBean = {
		merDdpLinkIp : $('#merDdpLinkIp').val(),
		merDdpLinkUser : $('#merDdpLinkUser').val(),
		merDdpLinkUserMobile : $('#merDdpLinkUserMobile').val(),
		settlementType : settlementType,
		settlementThreshold : settlementThreshold,
		tradeArea : tradeArea,
		isAutoDistribute : isAutoDistibute
		//limitSource : limitSource
	};

	var merchantBeans = {
		fundType : fundType,
		merCode : $('#merCode').val(),
		merName : $('#merName').val(),
		merType : merType,
		merClassify : merClassify,
		merProperty : merProperty,
		activate : activate,
		merPro : merPro,
		merCity : merCity,
		merLinkUser : $('#merLinkUser').val(),
		merLinkUserMobile : $('#merLinkUserMobile').val(),
		merAdds : $('#merAddressName').val(),
		merBusinessScopeId : merBusinessScopeId,
		merFax : $('#merFax').val(),
		merZip : $('#merZip').val(),
		merBankName : merBankName,
		merBankAccount : $('#merBankAccount').val(),
		merBankUserName : $('#merBankUserName').val(),
		merState : $('#merState').val(),
		merParentCode : $('#merParentCode').val(),
		merPayMoneyUser : $('#merPayMoneyUser').val(),
		merBusRateBeanList : merBusRateBeanList,
		merRateSpmtList : merRateSpmtList,
		merDdpInfoBean : merDdpInfoBean,
		merchantUserBean : merchantUserBean,
		merAutoAmtBean : merAutoAmtBean,
		merKeyTypeBean : merKeyTypeBean,
		merDefineFunBeanList : merDefineFunBeanList
	};
	ddpAjaxCall({
				url : "saveMerchantUserBusRate",
				data : merchantBeans,
				successFn : afterAddMerchant
			});
}
// --------------------------------------------查询商户详细信息------------------------
function findVerified() {
	var selarrrow = $("#merchantUserTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#merchantUserTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#merchantUserTbl').getRowData(selrow);
		var merCode = rowData.merCode;
		ddpAjaxCall({
					url : "findMerchantByCode",
					data : merCode,
					successFn : loadViewMerchants
				});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function showViewMerchant() {
	$('#viewMerchantDialog').show().dialog('open');
}
function closeViewMerchant() {
	$('#viewMerchantDialog').show().dialog('close');
}

function loadViewMerchants(data) {
	if (data.code == "000000") {

		var merchantBeans = data.responseEntity;
		//用户信息
		var merchantUserBean = merchantBeans.merchantUserBean;
		//费率信息
		var merRateSpmtList = merchantBeans.merRateSpmtList;
		// 商户补充信息表
		var merDdpInfoBean = merchantBeans.merDdpInfoBean;
		  /*自动分配额度*/
		var merAutoAmtBean = merchantBeans.merAutoAmtBean;
		
		$('#merNameView').html(merchantBeans.merName);
		$('#merTypeView').html(merchantBeans.merTypeView);
		$('#merClassifyView').html(merchantBeans.merClassifyView);
		$('#merPropertyView').html(merchantBeans.merPropertyView);
		$('#fundTypeView').html(merchantBeans.fundTypeView);
		$('#merLinkUserView').html(merchantBeans.merLinkUser);
		$('#merUserNameView').html(merchantUserBean.merUserName);
		$('#merLinkUserMobileView').html(merchantBeans.merLinkUserMobile);
		$('#activateView').html(merchantBeans.activateView);
		$('#merAddsView').html(merchantBeans.merProName
				+ merchantBeans.merCityName + "市" + merchantBeans.merAdds);
		$('#merBusinessScopeNameView').html(merchantBeans.merBusinessScopeIdView);
		$('#merUserSexView')
				.html(merchantUserBean.merUserSexView);
		$('#merUserTelephoneView').html(merchantBeans.merTelephone);
		$('#merFaxView').html(merchantBeans.merFax);
		$('#merUserIdentityTypeView')
				.html(merchantUserBean.merUserIdentityTypeView);
		$('#merUserIdentityNumberView')
				.html(merchantUserBean.merUserIdentityNumber);
		$('#merUserEmailView').html(merchantBeans.merEmail);
		$('#merZipView').html(merchantBeans.merZip);

		$('#merBankNameView').html(merchantBeans.merBankNameView);
		$('#merBankAccountView').html(merchantBeans.merBankAccount);
		$('#merBankUserNameView').html(merchantBeans.merBankUserName);
		$('#merDdpLinkIpView').html(merchantBeans.merDdpLinkIp);

		$('#merParentTypeView').html(merchantBeans.merParentTypeView);
		$('#merParentNameView').html(merchantBeans.merParentName);

		$('#merUserRemarkView')
				.html(htmlTagFormat(merchantUserBean.merUserRemark));
		$('#merUserRemarkView')
				.text(merchantUserBean.merUserRemark == null
						? ""
						: merchantUserBean.merUserRemark);

		loadYkt2(merchantBeans.merBusRateBeanList);

		$('#sourceView').html(merchantBeans.sourceView);
		$('#createUserView').html(merchantBeans.createUser);
		$('#createDateView').html(merchantBeans.createDateView);
		$('#merStateUserView').html(merchantBeans.merStateUser);
		$('#merStateDateView')
				.html(ddpDateFormatter(merchantBeans.merStateDate));
		$('#updateUserView').html(merchantBeans.updateUser);
		$('#updateDateView').html(merchantBeans.updateDateView);
		
		$('#merPayMoneyUserView').html(merchantBeans.merPayMoneyUser);
		// ----------------新增字段---------------
		// 商户补充信息表
		/* 都都宝固定IP */
		$('#merDdpLinkIpView').html(merDdpInfoBean.merDdpLinkIp);
		/* 都都宝联系人 */
		$('#merDdpLinkUserView').html(merDdpInfoBean.merDdpLinkUser);
		/* 都都宝联系人电话 */
		$('#merDdpLinkUserMobileView').html(merDdpInfoBean.merDdpLinkUserMobile);
		/* 结算类型 */
		$('#settlementTypeView').html(merDdpInfoBean.settlementTypeView);
		var settlementThresholdView = "";
		if(merDdpInfoBean.settlementType=='0'){
			settlementThresholdView = merDdpInfoBean.settlementThreshold+'天';
		}else if(merDdpInfoBean.settlementType=='1'){
			settlementThresholdView = merDdpInfoBean.settlementThreshold+'笔';
		}else if(merDdpInfoBean.settlementType=='2'){
			settlementThresholdView = merDdpInfoBean.settlementThreshold/100+'元';
		}
		/* 结算阀值 */
		$('#settlementThresholdView').html(settlementThresholdView);
		// 商户用户表
		/* 学历 */
		$('#educationView').html(merchantUserBean.educationView);
		/* 收入 */
		if(merchantUserBean.income!=null){
			$('#incomeView').html(Number(merchantUserBean.income/100)+"元");
		}else{
			$('#incomeView').html(merchantUserBean.income);
		}
		
		/* 生日，格式：1990-09-10 */
		$('#birthdayView').html(merchantUserBean.birthday);
		/* 是否已婚，0是，1否 */
		$('#isMarriedView').html(merchantUserBean.isMarriedView);
		/* 商圈 */
		$('#tradeAreaView').html(merDdpInfoBean.tradeArea);
		//商户业务信息表
		/*是否弹出错误信息,0是，1否，默认是*/
		//$('#isShowErrorMsgView').html(merchantBeans.isShowErrorMsgView);
		//---------------------------------修改最新查看展示-----------
		// 业务类型
		var rateCodeView = '';
		$(merRateSpmtList).each(function(i, v) {
					if (rateCodeView != '') {
						rateCodeView += ',';
					} else {
						rateCodeView = '';
					}
					rateCodeView += v.rateCodeView;
				});
		if (rateCodeView != null && rateCodeView != 'undefined') {
			$('#prdRateTypeView').html(rateCodeView);
		}
		// 业务费率信息
		if (merchantBeans.merType == '13' || merchantBeans.merType == '14') {
				//是否自动分配额度
			$('#isAutoDistibuteOneView').show();
			$('#isAutoDistibuteTwoView').show();
			//是否是否自动分配额度
			$('#isAutoDistibuteTwoView').html(merDdpInfoBean.isAutoDistributeView);
			if(merDdpInfoBean.isAutoDistribute=='0'){
				$('#isAutoDistibuteLineView').show();
				// 额度阀值
				$("#limitThresholdView").html(Number(merAutoAmtBean.limitThreshold)/100);
				// 自动分配额度阀值
				$("#selfMotionLimitThresholdView").html(Number(merAutoAmtBean.autoLimitThreshold)/100);
			}else{
				$('#isAutoDistibuteLineView').hide();
			}
		} else {
				//是否自动分配额度
			$('#isAutoDistibuteOneView').hide();
			$('#isAutoDistibuteTwoView').hide();
			$('#isAutoDistibuteLineView').hide();
		
		}
//		if (merchantBeans.merType == '14') {
//			if(merDdpInfoBean.limitSourceView!=null){
//				//额度来源
//			$('#limitSourceOneView').show();
//			$('#limitSourceTwoView').show();
//			$('#limitSourceTwoView').html(merDdpInfoBean.limitSourceView);
//			}
//			else{
//			$('#limitSourceOneView').hide();
//			$('#limitSourceTwoView').hide();
//			}
//		}else{
//			//是否自动分配额度
//			$('#limitSourceOneView').hide();
//			$('#limitSourceTwoView').hide();
//		}
		
		// 勾选业务类型
		if (merchantBeans.merParentCode != null) {
			merPrdRateType(merchantBeans);
		} else {
			$.each(merRateSpmtList, function(index, element) {
						if (element.rateCode != null) {
							$('#PrdRateType').find("input[id="
									+ element.rateCode + "][type=checkbox]")
									.attr("checked", "checked");
						}
					});
			var checked = $("input[id='01'][type='checkbox']").is(':checked');
			if (checked) {
				$('#TKNameVerLine').show();
				if (merchantBeans.merType == '18') {
					$('#yktInfoUrlLine').show();
					$('#pageCheckCallbackUrlLine').show();
					$.each(merRateSpmtList, function(index, element) {
								if (element.rateCode != null) {
									$('#YktPageCallbackUrl')
											.val(element.pageCallbackUrl);
									$('#YktServiceNoticeUrl')
											.val(element.serviceNoticeUrl);
									$('#pageCheckCallbackUrl')
											.val(element.pageCheckCallbackUrl);
									$("input[name='isShowErrorMsg'][value="
											+ element.isShowErrorMsg + "]")
											.attr("checked", "checked");
								}
							});
				}
			}
			var checked03 = $("input[id='03'][type='checkbox']").is(':checked');
			if (checked03) {
				$('#TKNameVerLinePayment').show();
			}
		}
		showViewMerchant();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

// 停用商户
function disableMerchant() {
	var selarrrow = $("#merchantUserTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用商户信息吗？", function(r) {
					if (r) {
						var merCodes = new Array();
						for (var i = 0; i < selarrrow.length; i++) {
							var rowData = $("#merchantUserTbl")
									.getRowData(selarrrow[i]);
							merCodes.push(rowData.merCode);
						}
						ddpAjaxCall({
									url : "startAndDisableMerchant?activate=1",
									data : merCodes,
									successFn : afterUpdateMerchant
								});
					}
				});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
// 启用商户
function startMerchant() {
	var selarrrow = $("#merchantUserTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用商户信息吗？", function(r) {
					if (r) {
						var merCodes = new Array();
						for (var i = 0; i < selarrrow.length; i++) {
							var rowData = $("#merchantUserTbl")
									.getRowData(selarrrow[i]);
							merCodes.push(rowData.merCode);
						}
						ddpAjaxCall({
									url : "startAndDisableMerchant?activate=0",
									data : merCodes,
									successFn : afterUpdateMerchant
								});

					}
				});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
// ---------------------------------------------------勾选通卡公司点击编辑-------------------------------
function editorVerified() {
	clearForm();
	$('#merTypeParentLine').hide();
	var rowData = getSelectedRowDataFromMultiples('merchantUserTbl');
	if (rowData != null) {
		var merCode = rowData.merCode;
		ddpAjaxCall({
					url : "findMerchantByCode",
					data : merCode,
					successFn : loadMerchantInfo
				});

	}
	$('#merName').attr('disabled', true);
	$('#merUserName').attr('disabled', true);
	// 商户类型不能修改，上级商户类型不能修改，选择按钮隐藏。
	$('#merType').combobox('disable');
	$('#merParentType').combobox('disable');
	// 商户分类不能修改
	$("input[name='merClassify']").attr("disabled", true);
	// 省市禁用
	diableAreaComponent('merAddress');
	// 地址禁用
	$('#merAddressName').attr('disabled', true);
	// 额度来源不能编辑
//	$("input[name='limitSource']").attr("disabled", true);
}

// 加载已审核商户信息
function loadMerchantInfo(data) {
	if (data.code == "000000") {
		clearForm();
		$('#flag').val('1');
		var merchantBeans = data.responseEntity;
		var merRateSpmtList = merchantBeans.merRateSpmtList; // 业务类型
		var merDdpInfoBean = merchantBeans.merDdpInfoBean; // 商户补充信息
		var merAutoAmtBean =  merchantBeans.merAutoAmtBean; //自动分配额度
		// ------------商户用户信息表 商户用户信息 start--------------
		var merchantUserBeans = merchantBeans.merchantUserBean;
		$('#merUserName').val(merchantUserBeans.merUserName);
		$('#merLinkUser').val(merchantUserBeans.merUserNickName);
		$('#merLinkUserMobile').val(merchantUserBeans.merUserMobile);
		$('#merAdds').val(merchantUserBeans.merUserAdds);
		$('#merUserTelephone').val(merchantUserBeans.merUserTelephone);
		$('#merUserIdentityNumber')
				.val(merchantUserBeans.merUserIdentityNumber);
		$('#merUserEmail').val(merchantUserBeans.merUserEmail);
		$('#merUserRemark').val(merchantUserBeans.merUserRemark);
		$('#userCode').val(merchantUserBeans.userCode);
		// 证件类型
		$('#merUserIdentityType').combobox(
				'select',
				merchantUserBeans.merUserIdentityType == null
						? ""
						: merchantUserBeans.merUserIdentityType);

		// -------------补充字段----------
								// 证件类型
		$('#education').combobox(
				'select',
				merchantUserBeans.education == null
						? ""
						: merchantUserBeans.education);// 学历
			if(merchantUserBeans.income!=null && merchantUserBeans.income!=""){
				$('#income').val(merchantUserBeans.income/100);}else{
				$('#income').val(merchantUserBeans.income);
				}// 收入
		$('#birthday').val(merchantUserBeans.birthday != null
				? merchantUserBeans.birthday.substr(0, 11)
				: "");// 出生年月
		$('#isMarried').combobox(
				'select',
				merchantUserBeans.isMarried == null
						? ""
						: merchantUserBeans.isMarried);// 婚否
		// $('#tradeArea').val(merchantUserBeans.tradeArea);//商圈
		// ------------商户用户信息表 商户用户信息 end--------------

		// -------------商户补充信息表 商户补充信息 start -----------
		$('#merDdpLinkIp').val(merDdpInfoBean.merDdpLinkIp);
		$('#merDdpLinkUser').val(merDdpInfoBean.merDdpLinkUser);
		$('#merDdpLinkUserMobile').val(merDdpInfoBean.merDdpLinkUserMobile);
		$('#settlementType').combobox(
				'select',
				merDdpInfoBean.settlementType == null
						? ""
						: merDdpInfoBean.settlementType);// 结算类型
		var settlementThreshold = merDdpInfoBean.settlementThreshold;
		if ($("#settlementType").combobox('getValue') == '2') {
			settlementThreshold = settlementThreshold / 100;
		}
		$('#settlementThreshold').val(settlementThreshold);// 结算阀值
		$('#tradeArea').val(merDdpInfoBean.tradeArea);// 商圈
		// ------商户补充信息表 商户补充信息 end ------

		// 验签密钥信息
		var merKeyTypeBeans = merchantBeans.merKeyTypeBean;
		if (merKeyTypeBeans != null) {
			$('#merMd5paypwd').val(merKeyTypeBeans.merMD5PayPwd);
			$('#merMd5Backpaypwd').val(merKeyTypeBeans.merMD5BackPayPWD);
		}

		// 加载商户信息
		$('#merCode').val(merchantBeans.merCode);
		$('#merState').val(merchantBeans.merState);
		$('#merName').val(merchantBeans.merName);
		$('#merLinkUser').val(merchantBeans.merLinkUser);
		$('#merLinkUserMobile').val(merchantBeans.merLinkUserMobile);
		$('#merFax').val(merchantBeans.merFax);
		$('#merZip').val(merchantBeans.merZip);
		$('#merBankName').val(merchantBeans.merBankName);
		$('#merBankAccount').val(merchantBeans.merBankAccount);
		$('#merBankUserName').val(merchantBeans.merBankUserName);
		//商户打款人
		$('#merPayMoneyUser').val(merchantBeans.merPayMoneyUser);
		
		
		$("input[name='merUserSex'][value=" + merchantUserBeans.merUserSex
				+ "]").attr("checked", true);
		$("input[name='activate'][value=" + merchantBeans.activate + "]").attr(
				"checked", true);
		$("input[name='activate']:eq(0)").attr("disabled", "disabled");
		$("input[name='activate']:eq(1)").attr("disabled", "disabled");
		$("input[name='merClassify'][value=" + merchantBeans.merClassify + "]")
				.attr("checked", true);
		$("input[name='merProperty'][value=" + merchantBeans.merProperty + "]")
				.attr("checked", true);
		// 商户属性的自定义商户权限
		// 资金类型
		$("input[name='fundType'][value=" + merchantBeans.fundType + "]").attr(
				"checked", "checked");
		$("input[name='fundType']:eq(0)").attr("disabled", "disabled");
		$("input[name='fundType']:eq(1)").attr("disabled", "disabled");
		if (merchantBeans.merProperty == '1') {
			$('#viewMerProperty').show();
			merPropertyClick('editzdy');
		} else {
			$('#viewMerProperty').hide();
			// merPropertyClick('stander');
		}

		// 经营范围
		$('#merBusinessScopeId').combobox(
				'select',
				merchantBeans.merBusinessScopeId == null
						? ""
						: merchantBeans.merBusinessScopeId);
		// 开户银行
		$('#merBankName').combobox(
				'select',
				merchantBeans.merBankName == null
						? ""
						: merchantBeans.merBankName);
		// 商户类型
		$('#merType').combobox('select', merchantBeans.merType);
		// 上级商户类型
		setTimeout(function() {
					$('#merParentType').combobox(
							'select',
							merchantBeans.merParentType == null
									? "null"
									: merchantBeans.merParentType);
				}, 500);
		// 上级名称选择隐藏
		// $('#finMER').hide();
		$('#finMER').attr("disabled", true);
		// 上级商户名称
		$('#merParentName').val(merchantBeans.merParentName);
		$('#merParentCode').val(merchantBeans.merParentCode);
		// 详细地址
		setAreaComponent('merAddress', merchantBeans.merPro,
				merchantBeans.merCity);
		// 地址
		$('#merAddressName').val(merchantBeans.merAdds);
		
//		if (merchantBeans.merType == '14') {
//			//额度来源
//			$('#limitSourceOne').show();
//			$('#limitSourceTwo').show();
//			$("input[name='limitSource'][value=" + merDdpInfoBean.limitSource + "]").attr("checked", true);
//		}else{
//			//额度来源
//			$('#limitSourceOne').hide();
//			$('#limitSourceTwo').hide();
//			$("input[name='limitSource'][value=0]").attr("checked", true);
//		}
		
		// 勾选业务类型
		if (merchantBeans.merParentCode != null) {
			merPrdRateType(merchantBeans);
		} else {
			$.each(merRateSpmtList, function(index, element) {
						if (element.rateCode != null) {
							$('#PrdRateType').find("input[id="
									+ element.rateCode + "][type=checkbox]")
									.attr("checked", "checked");
						}
					});
			var checked = $("input[id='01'][type='checkbox']").is(':checked');
			if (checked) {
				$('#TKNameVerLine').show();
				if (merchantBeans.merType == '18') {
					$('#yktInfoUrlLine').show();
					$('#pageCheckCallbackUrlLine').show();
					$.each(merRateSpmtList, function(index, element) {
								if (element.rateCode != null) {
									$('#YktPageCallbackUrl')
											.val(element.pageCallbackUrl);
									$('#YktServiceNoticeUrl')
											.val(element.serviceNoticeUrl);
									$('#pageCheckCallbackUrl')
											.val(element.pageCheckCallbackUrl);
									$("input[name='isShowErrorMsg'][value="
											+ element.isShowErrorMsg + "]")
											.attr("checked", "checked");
								}
							});
				}
			}
			var checked03 = $("input[id='03'][type='checkbox']").is(':checked');
			if (checked03) {
				$('#TKNameVerLinePayment').show();
			}
			//圈存订单  2015-12-29  JOE
			var checkedIcLoad = $("input[id='04'][type='checkbox']").is(':checked');
			if (checkedIcLoad) {
				$('#IcLoadLine').show();
			}
		}

		// 业务费率信息 如果是直营网点
		if (merchantBeans.merType == '13') {
			loadYKTzywd(merchantBeans.merBusRateBeanList);
			$('#ykt').attr("disabled", true);
			$('#yktPayment').attr("disabled", true);
				//是否自动分配额度
			$('#isAutoDistibuteOne').show();
			$('#isAutoDistibuteTwo').show();
		
			
			//是否是否自动分配额度
			$("input[name='isAutoDistibute'][value=" + merDdpInfoBean.isAutoDistribute + "]").attr("checked", true);
			if(merDdpInfoBean.isAutoDistribute=='0'){
				$('#isAutoDistibuteLine').show();
				merTypeMerAutoAmtBean(merAutoAmtBean);
			}else{
				$('#isAutoDistibuteLine').hide();
				$("#limitThreshold").val('');
				$("#selfMotionLimitThreshold").val('');
			}
		} 
		else if(merchantBeans.merType == '14'){
				// 业务费率信息 如果是加盟网点
				$('#ykt').removeAttr("disabled");
				$('#yktPayment').removeAttr("disabled");
				loadYkt(merchantBeans);
				//是否是否自动分配额度
				$("input[name='isAutoDistibute'][value=" + merDdpInfoBean.isAutoDistribute + "]").attr("checked", true);
				if(merDdpInfoBean.isAutoDistribute=='0'){
					$('#isAutoDistibuteLine').show();
					merTypeMerAutoAmtBean(merAutoAmtBean);
				}else{
					$('#isAutoDistibuteLine').hide();
					$("#limitThreshold").val('');
					$("#selfMotionLimitThreshold").val('');
				}
		}else {
				//是否自动分配额度
			$('#isAutoDistibuteOne').hide();
			$('#isAutoDistibuteTwo').hide();
			$('#isAutoDistibuteLine').hide();
			$("#limitThreshold").val('');
			$("#selfMotionLimitThreshold").val('');
			// 是否自动分配额度
			$("input[name='isAutoDistibute'][value=1]").attr("checked", true);
			$('#ykt').removeAttr("disabled");
			$('#yktPayment').removeAttr("disabled");
			loadYkt(merchantBeans);
		}
		showUserDialog();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

// 有上级商户时加载业务类型
function merPrdRateType(merchantBeans) {
	$('#PrdRateType').html('');
	var merParentCode = merchantBeans.merParentCode;
	var merRateSpmtList = merchantBeans.merRateSpmtList;
	// 初始化加载费率类型
	ddpAjaxCall({
				async : false,
				url : "findMerRateSupplementByCode?merType="+merchantBeans.merType,
				data : merParentCode,
				successFn : function(data) {
					if (data.code == '000000') {
						loadEditPrdRateModel(data);
						if (data.responseEntity != null) {
							$.each(merRateSpmtList, function(index, element) {
										if (element.rateCode != null) {
											$('#PrdRateType').find("input[id="
													+ element.rateCode
													+ "][type=checkbox]").attr(
													"checked", "checked");
										}
									});
							var checked = $("input[id='01'][type='checkbox']")
									.is(':checked');
							if (checked) {
								$('#TKNameVerLine').show();
							}
							var checkedPayment = $("input[id='03'][type='checkbox']")
									.is(':checked');
							if (checkedPayment) {
								$('#TKNameVerLinePayment').show();
							}
							// 先判断商户类型是否连锁商直营网点
							var merType = $('#merType').combobox('getValue');
							if (merType == '13') {
								$('#yktInfoUrlLine').hide();
								$('#pageCheckCallbackUrlLine').hide();
								$('#PrdRateType').find("input[type=checkbox]")
										.attr("checked", "checked");
								$('#PrdRateType').find("input[type=checkbox]")
										.attr("disabled", "disabled");
							}
						} else {
							$('#PrdRateType').html('');
						}
					}

				}
			});
}

// 当上级商户
function loadEditPrdRateModel(data) {
	// 获取加载的值
	var prdRateBean = data.responseEntity;
	var line = '';
	if (prdRateBean != null) {
		for (var i = 0; i < prdRateBean.length; i++) {
			line = '<td><input type="checkbox" id="' + prdRateBean[i].rateCode
					+ '" name="prdRateName" style="width:20px;border:0;" ';
			line += 'value= "' + prdRateBean[i].rateCode
					+ '" onclick="prdRateTypeSys(this)"  />&nbsp;&nbsp;'
					+ prdRateBean[i].rateCodeView + '</td>';
			$('#PrdRateType').append(line);
		}
	} else {
		$('#PrdRateType').html('');
	}

}

// 非直营网点加载通卡公司费率
var merParentBusRateBeanList;// 上级通卡公司是否存在费率
function loadYkt(merchantBeans) {
	var merBusRateBeanList = merchantBeans.merBusRateBeanList;
	if(isNotBlank(merchantBeans.merParentCode)){
		$.ajax({
				async : false,
				type : 'post',
				url : 'findYktInfo',
				dataType : "json",
				data : merchantBeans.merParentCode,
				success : function(data) {
					if (data.code == "000000") {
						merParentBusRateBeanList = data.responseEntity;
					}
				}
			});
	}
	if (merchantBeans.merParentType != null
			&& merchantBeans.merParentType != "null") {
		if (isNotBlank(merchantBeans.merParentCode)) {
			if (isNotBlank(merParentBusRateBeanList)
					&& merParentBusRateBeanList.length != 0) {
				if (isNotBlank(merBusRateBeanList)
						&& merBusRateBeanList.length != 0) {
					var merBusRateBeanListRecharge = new Array();
					var merBusRateBeanListPayment = new Array();
					var merBusRateBeanListIcLoad = new Array();
					for (var i = 0; i < merBusRateBeanList.length; i++) {
						var rateCode = merBusRateBeanList[i].rateCode;
						if (rateCode == '01') {
							merBusRateBeanListRecharge
									.push(merBusRateBeanList[i]);
						} else if (rateCode == '03') {
							merBusRateBeanListPayment
									.push(merBusRateBeanList[i])
						}else if (rateCode == '04') {
							merBusRateBeanListIcLoad
									.push(merBusRateBeanList[i])
						}
					}

					if (isNotBlank(merBusRateBeanListRecharge)) {
						$('#TKNameVerLine').show();
						$('#ykt').removeAttr("disabled");
						$('#yktTblLine').show();
						loadJqGridData($('#yktTbl'), merBusRateBeanListRecharge);
						$('#yktTbl').jqGrid('setGridHeight',
								27 * merBusRateBeanListRecharge.length);
						var selected = '';
						// $("#checkbox_id").attr("checked",true);
						$.each(merBusRateBeanListRecharge, function(index,
										element) {
									if (selected != '') {
										selected += ',';
									}
									selected += element.proName;
								});
						$('#providerCode').val(selected);
						selectAllLines('yktTbl');
						$('#cb_yktTbl').attr('checked', 'checked');
						$('#cb_yktTbl').attr('disabled', 'disabled');
					}
					if (isNotBlank(merBusRateBeanListPayment)) {
						//$('#TKNameVerLinePayment').show();
						$('#yktPayment').removeAttr("disabled");
						$('#yktTblLinePayment').show();
						loadJqGridData($('#yktTblPayment'),
								merBusRateBeanListPayment);
						$('#yktTblPayment').jqGrid('setGridHeight',
								27 * merBusRateBeanListPayment.length);
						var selected = '';
						// $("#checkbox_id").attr("checked",true);
						$.each(merBusRateBeanListPayment, function(index,
										element) {
									if (selected != '') {
										selected += ',';
									}
									selected += element.proName;
								});
						$('#providerCodePayment').val(selected);
						selectAllLines('yktTblPayment');
						$('#cb_yktTblPayment').attr('checked', 'checked');
						$('#cb_yktTblPayment').attr('disabled', 'disabled');
					}
					//圈存费率 2015-12-29 JOE
					if (isNotBlank(merBusRateBeanListIcLoad)) {
						//$('#TKNameVerLinePayment').show();
						$('#icLoadBtn').removeAttr("disabled");
						$('#yktIcLoadTblLine').show();
						loadJqGridData($('#yktIcLoadTbl'),
								merBusRateBeanListPayment);
						$('#yktIcLoadTbl').jqGrid('setGridHeight',
								27 * merBusRateBeanListPayment.length);
						var selected = '';
						$.each(merBusRateBeanListPayment, function(index,
										element) {
									if (selected != '') {
										selected += ',';
									}
									selected += element.proName;
								});
						$('#icLoad').val(selected);
						selectAllLines('yktIcLoadTbl');
						$('#cb_yktIcLoadTbl').attr('checked', 'checked');
						$('#cb_yktIcLoadTbl').attr('disabled', 'disabled');
					}
				} else {
					$('#ykt').removeAttr("disabled");
					$('#yktTblLine').hide();
					//一卡通消费
					$('#yktPayment').removeAttr("disabled");
					$('#yktTblLinePayment').hide();
					//圈存  2015-12-29 JOE
					$('#icLoadBtn').removeAttr("disabled");
					$('#yktIcLoadTblLine').hide();
					
				}
			} else {
				// $('#TKNameVerLine').hide();
				$('#ykt').attr("disabled", true);
				$('#yktTblLine').hide();
				$('#yktPayment').attr("disabled", true);
				$('#yktTblLinePayment').hide();
				//圈存  2015-12-29 JOE
				$('#icLoadBtn').attr("disabled", true);
				$('#yktIcLoadTblLine').hide();
			}
		} else {
			$('#ykt').attr("disabled", true);
			$('#yktTblLine').hide();
		}
	} else {
		if (isNotBlank(merBusRateBeanList) && merBusRateBeanList.length != 0) {
			
			var merBusRateBeanListRecharge = new Array();
			var merBusRateBeanListPayment = new Array();
			var merBusRateBeanListIcLoad = new Array();
			
			for (var i = 0; i < merBusRateBeanList.length; i++) {
				var rateCode = merBusRateBeanList[i].rateCode;
				if (rateCode == '01') {
					merBusRateBeanListRecharge.push(merBusRateBeanList[i]);
				} else if (rateCode == '03') {
					merBusRateBeanListPayment.push(merBusRateBeanList[i])
				}else if (rateCode == '04') {
					merBusRateBeanListIcLoad.push(merBusRateBeanList[i])
				}
			}

			if (isNotBlank(merBusRateBeanListRecharge)) {
				$('#TKNameVerLine').show();
				$('#yktTblLine').show();
				loadJqGridData($('#yktTbl'), merBusRateBeanListRecharge);
				$('#yktTbl').jqGrid('setGridHeight',
						27 * merBusRateBeanListRecharge.length);
				var selected = '';
				// $("#checkbox_id").attr("checked",true);
				$.each(merBusRateBeanListRecharge, function(index, element) {
							if (selected != '') {
								selected += ',';
							}
							selected += element.proName;
						});
				$('#providerCode').val(selected);
				selectAllLines('yktTbl');
				$('#cb_yktTbl').attr('checked', 'checked');
				$('#cb_yktTbl').attr('disabled', 'disabled');
			}
			if (isNotBlank(merBusRateBeanListPayment)) {
				$('#TKNameVerLinePayment').show();
				$('#yktTblLinePayment').show();
				loadJqGridData($('#yktTblPayment'), merBusRateBeanListPayment);
				$('#yktTblPayment').jqGrid('setGridHeight',
						27 * merBusRateBeanListPayment.length);
				var selected = '';
				// $("#checkbox_id").attr("checked",true);
				$.each(merBusRateBeanListPayment, function(index, element) {
							if (selected != '') {
								selected += ',';
							}
							selected += element.proName;
						});
				$('#providerCodePayment').val(selected);
				selectAllLines('yktTblPayment');
				$('#cb_yktTblPayment').attr('checked', 'checked');
				$('#cb_yktTblPayment').attr('disabled', 'disabled');
			}
			//圈存费率 2015-12-29 JOE
			if (isNotBlank(merBusRateBeanListIcLoad)) {
						//$('#TKNameVerLinePayment').show();
						$('#icLoadBtn').removeAttr("disabled");
						$('#yktIcLoadTblLine').show();
						loadJqGridData($('#yktIcLoadTbl'),
								merBusRateBeanListIcLoad);
						$('#yktIcLoadTbl').jqGrid('setGridHeight',
								27 * merBusRateBeanListIcLoad.length);
						var selected = '';
						$.each(merBusRateBeanListIcLoad, function(index,
										element) {
									if (selected != '') {
										selected += ',';
									}
									selected += element.proName;
								});
						$('#icLoad').val(selected);
						selectAllLines('yktIcLoadTbl');
						$('#cb_yktIcLoadTbl').attr('checked', 'checked');
						$('#cb_yktIcLoadTbl').attr('disabled', 'disabled');
					}
		}
	}

}
// 直营网点特殊处理
function loadYKTzywd(merBusRateBeanList) {
	if (isNotBlank(merBusRateBeanList)) {
		var merBusRateBeanListRecharge = new Array();
		var merBusRateBeanListPayment = new Array();
		for (var i = 0; i < merBusRateBeanList.length; i++) {
			var rateCode = merBusRateBeanList[i].rateCode;
			if (rateCode == '01') {
				merBusRateBeanListRecharge.push(merBusRateBeanList[i]);
			} else if (rateCode == '03') {
				merBusRateBeanListPayment.push(merBusRateBeanList[i])
			}
		}

		if (isNotBlank(merBusRateBeanListRecharge)) {
			$('#TKNameVerLine').show();
			$('#yktTblLine').show();
			$('#yktTbl').setColProp('rateType', {
						editable : false
					});
			$('#yktTbl').setColProp('rate', {
						editable : false
					});
			loadJqGridData($('#yktTbl'), merBusRateBeanListRecharge);
			$('#yktTbl').jqGrid('setGridHeight',
					27 * merBusRateBeanListRecharge.length);
			var selected = '';
			// $("#checkbox_id").attr("checked",true);
			$.each(merBusRateBeanListRecharge, function(index, element) {
						if (selected != '') {
							selected += ',';
						}
						selected += element.proName;
					});
			$('#providerCode').val(selected);
			selectAllLines('yktTbl');
			$('#cb_yktTbl').attr('checked', 'checked');
			$('#cb_yktTbl').attr('disabled', 'disabled');
		}
		if (isNotBlank(merBusRateBeanListPayment)) {
			$('#TKNameVerLinePayment').show();
			$('#yktTblLinePayment').show();
			$('#yktTblPayment').setColProp('rateType', {
						editable : false
					});
			$('#yktTblPayment').setColProp('rate', {
						editable : false
					});
			loadJqGridData($('#yktTblPayment'), merBusRateBeanListPayment);
			$('#yktTblPayment').jqGrid('setGridHeight',
					27 * merBusRateBeanListPayment.length);
			var selected = '';
			// $("#checkbox_id").attr("checked",true);
			$.each(merBusRateBeanListPayment, function(index, element) {
						if (selected != '') {
							selected += ',';
						}
						selected += element.proName;
					});
			$('#providerCodePayment').val(selected);
			selectAllLines('yktTblPayment');
			$('#cb_yktTblPayment').attr('checked', 'checked');
			$('#cb_yktTblPayment').attr('disabled', 'disabled');
		}

	} else {
		$('#yktTblLine').hide();
		$('#yktTbl').setColProp('rateType', {
					editable : true
				});
		$('#yktTbl').setColProp('rate', {
					editable : true
				});

		$('#yktTblLinePayment').hide();
		$('#yktTblPayment').setColProp('rateType', {
					editable : true
				});
		$('#yktTblPayment').setColProp('rate', {
					editable : true
				});
	}
}
// 加载通卡公司费率
function loadYkt2(merBusRateBeanList) {
	var tkgsNameView = '';
	$('#yktTableTbodyView').html('');
	$('#tkgsNameView').html('');
	var html = '';
	if (merBusRateBeanList && merBusRateBeanList.length > 0) {
		$('#yktView').show();
		var proNameArray = new Array();
		$(merBusRateBeanList).each(function(i, v) {
					html += '<tr>';
					html += '<td class="nobor">&nbsp;</td>';

					html += '<td>';
					html += v.proName;

					var isFound = false;
					for (var i = 0; i < proNameArray.length; i++) {
						if (proNameArray[i] == v.proName) {
							isFound = true;
							break;
						}
					}
					if (!isFound) {
						tkgsNameView += v.proName;
						tkgsNameView += ',';
						proNameArray.push(v.proName);
					}

					html += '</td>';
					html += '<td>';
					html += v.rateName;
					html += '</td>';

					html += '<td>';
					html += v.rateTypeView;
					html += '</td>';

					html += '<td>';
					html += v.rate;
					html += '</td>';

					html += '<td class="nobor">&nbsp;</td>';
					html += '</tr>';

				});
		$('#yktTableTbodyView').html(html);
		tkgsNameView = tkgsNameView.substring(0, tkgsNameView.length - 1);
		$('#tkgsNameView').html(tkgsNameView);
	} else {
		$('#yktView').hide();
	}
}

// 清空界面数据
function clearForm() {
	clearAreaComponent('merAddress');
	clearAllText('OpenMerchantDialog');
	clearAllCombobox('OpenMerchantDialog');
	clearAllText('customerDialog');
	$('#yktTblLine').hide();
	$('#yktTbl').clearGridData();
	$('#yktTblLinePayment').hide();
	$('#yktTblPayment').clearGridData();
	//圈存充值
	$('#yktIcLoadTblLine').hide();
	$('#yktIcLoadTbl').clearGridData();
}
// 加载上级通卡公司
function setSelectedMerchant(merchant) {
	if (typeof(merchant) != 'undefined') {
		$('#merParentName').val(merchant.merName);
		$('#merParentCode').val(merchant.merCode);
	}
}
//------------------------------------------开户时初始化加载业务类型--------------
// 初始化加载业务类型
function findPrdRateType() {
	var merType = $('#merType').combobox('getValue');
	ddpAjaxCall({
				async : false,
				url : "findPrdRateBean?merType=" + merType,
				successFn : loadPrdRate
			});
}
function loadPrdRate(data) {
	if (data.code = '000000') {
		// 获取加载的值
		var prdRateBean = data.responseEntity;
		var line = '';
		for (var i = 0; i < prdRateBean.length; i++) {
			line = '<td><input type="checkbox" id="' + prdRateBean[i].rateCode
					+ '" name="prdRateName" style="width:20px;border:0;" ';
			line += 'value= "' + prdRateBean[i].rateCode
					+ '" onclick="prdRateTypeSys(this)"/>&nbsp;&nbsp;'
					+ prdRateBean[i].rateName + '</td>';
			$('#PrdRateType').append(line);
		}

	}
}

// 勾选业务类型
function prdRateTypeSys(obj) {
	var checked = $("input[id=" + obj.id + "][type='checkbox']").is(':checked');
	var merType = $('#merType').combobox('getValue');
	if (checked) {
		if ($('#' + obj.id + '').val() == '01') {
			if (merType == '18') {
				$("#yktInfoUrlLine").show();
				$('#pageCheckCallbackUrlLine').show();
			}
			$("#TKNameVerLine").show();
		}
		if ($('#' + obj.id + '').val() == '03') {
			$("#TKNameVerLinePayment").show();
		}
		if ($('#' + obj.id + '').val() == '04') {
			$("#IcLoadLine").show();
		}
	} else {
		if ($('#' + obj.id + '').val() == '01') {
			$("#YktPageCallbackUrl").val('');
			$("#YktServiceNoticeUrl").val('');
			$("#yktInfoUrlLine").hide();
			$('#pageCheckCallbackUrlLine').hide();
			$('#yktInfoContent').find("input[type=checkbox]")
					.removeAttr("checked");
			yktHandler();
			$("#yktTblLine").hide();
			$("#TKNameVerLine").hide();
		}
		if ($('#' + obj.id + '').val() == '03') {
			$('#yktInfoContentPayment').find("input[type=checkbox]")
					.removeAttr("checked");
			yktHandlerPayment();
			$("#yktTblLinePayment").hide();
			$("#TKNameVerLinePayment").hide();
		}
		if ($('#' + obj.id + '').val() == '04') {
			$('#yktIcLoad').find("input[type=checkbox]")
					.removeAttr("checked");
			yktIcLoad();
			$("#yktIcLoadTblLine").hide();
			$("#IcLoadLine").hide();
		}
	}
}
// --------------------------------------------新需求变更修改 2015-12-03
// -------------------------------、
// 当商户类型为直营网点的时候
function isAutoDistibuteCheck() {
	// 额度阀值
	$("#limitThreshold").val('');
	// 自动分配额度阀值
	$("#selfMotionLimitThreshold").val('');
	// 当选择是自动分配额度的时候展现
	$("#isAutoDistibuteLine").show();
}
function notIsAutoDistibuteCheck() {
	// 额度阀值
	$("#limitThreshold").val('');
	// 自动分配额度阀值
	$("#selfMotionLimitThreshold").val('');
	// 当选择否自动分配额度的时候展现
	$("#isAutoDistibuteLine").hide();
}

//---编辑时商户类型为直营网点
function  merTypeMerAutoAmtBean(merAutoAmtBean){
// 额度阀值
$("#limitThreshold").val(Number(merAutoAmtBean.limitThreshold)/100);
// 自动分配额度阀值
$("#selfMotionLimitThreshold").val(Number(merAutoAmtBean.autoLimitThreshold)/100);
}


/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "exportVerifieMgmt", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "MerchantExcelBean", 	/*must*/// type of export
	"toUrl"			: "exportExcelVerified", 			/*must*/// the export url
	"parDiaHeight"  : "400"
};
var filterConStr = [	// filter the result by query condition
		{'name':'merNameQuery', 	'value': "escapePeculiar($.trim($('#merNameQuery').val()))"			},	// eval
		{'name':'merCodeQuery',	'value': "escapePeculiar($.trim($('#merCodeQuery').val()))"		},
		{'name':'merParentNameQuery',	'value': "escapePeculiar($.trim($('#merParentNameQuery').val()))"		},
		{'name':'merTypeQuery',	'value': "escapePeculiar($('#merTypeQuery').combobox('getValue'))"		},
		{'name':'merClassifyQuery',	'value': "escapePeculiar($('#merClassifyQuery').combobox('getValue'))"		},
		{'name':'merPropertyQuery',	'value': "escapePeculiar($('#merPropertyQuery').combobox('getValue'))"		},
		{'name':'merProQuery',	'value': "escapePeculiar(getProvinceCodeFromCompoent('proCityQuery'))"		},
		{'name':'merCityQuery',	'value': "escapePeculiar(getCityCodeFromCompoent('proCityQuery'))"		},
		{'name':'activateQuery',	'value': "escapePeculiar($('#activateQuery').combobox('getValue'))"		},
		{'name':'sourceQuery',	'value': "escapePeculiar($('#sourceQuery').combobox('getValue'))"		},
		{'name':'bussQuery',	'value': "escapePeculiar($('#bussQuery').combobox('getValue'))"		},
		{'name':'merState',	'value': "escapePeculiar('1')"		}
	];




/*************************************************  折扣入口  *****************************************************************/
$(function() {
	function initBindedDiscountTbl() {
		$('#merDisCountListTbl').jqGrid($.extend({
			datatype : function(pdata) {
				findMerDisCount();
			},
			colNames : [ 'id', '商户号', '用户折扣', '开始日期', '结束日期', 			'星期', '开始时间', '结束时间' ],
			colModel : [ { name : 'dateId', hidden : true, key : true }, 
		             { name : 'merCode', hidden : true },
		             { name : 'discountThreshold', index : 'discountThreshold', width : 80, align : 'left', sortable : false, formatter: ddpMoneyFenToYuan }, 
		             { name : 'beginDate', index : 'beginDate', width : 80, align : 'left', sortable : false, formatter:function(cellval, el, rowData) { return formatDate(cellval,'yyyy-MM-dd');}	}, 
		             { name : 'endDate', index : 'endDate', width : 80, align : 'left', sortable : false, formatter:function(cellval, el, rowData) { return formatDate(cellval,'yyyy-MM-dd');}}, 
		             { name : 'week', index : 'week', width : 60, align : 'left', sortable : false }, 
		             { name : 'beginTime', index : 'beginTime', width : 80, align : 'left', sortable : false }, 
		             { name : 'endTime', index : 'endTime', width : 80, align : 'left', sortable : false }
		    ],
		    width : $('#merDisCountDialog').width() -170,
		    height : 230,
		    multiselect : true,
		    pager : '#merDisCountListTblPagination'
		}, jqgrid_server_opts));
		$("#t_merDisCountListTbl").append($('#merDisCountDialogToolbar'));
	}
	initBindedDiscountTbl();
});
function findMerDisCount(defaultPageNo) {
	ddpAjaxCall({
		url : "findMerDiscountList",
		data : { merCode : $('#merCode').val(), page : getJqgridPage('merDisCountListTbl', defaultPageNo) },
		successFn : function(data) {
			if ("000000" === data.code) {
				loadJqGridPageData($('#merDisCountListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}
function editorMerDisCount() {
	var selarrrow = $("#merchantUserTbl").getGridParam('selarrrow');// 多选
	if(selarrrow != null && selarrrow.length > 1) { 	// 不可以多选的提示
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#merchantUserTbl").getGridParam('selrow'); 	// 取到单选的数据
	if(selrow) {
		var rowData = $('#merchantUserTbl').getRowData(selrow);
		var merCode = rowData.merCode;
		$('#merCode').val(merCode);
		$('#merDisCountDialog').show().dialog('open');
		findMerDisCount();
	}else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

/*************************************************  编辑折扣  *****************************************************************/
$(function() {
	function initSelTbl() {
		$('#merDisCountAllListTbl').jqGrid($.extend({
			datatype : function(pdata) {
			findAllTranDiscount();
		},
		colNames : [ 'id','商户号', '用户折扣', '开始日期', '结束日期', '星期', '开始时间', '结束时间' ],
		colModel : [ 
		    { name: 'id', hidden: true, key: true }, 
		    { name: 'merCode', hidden : true },
		    { name: 'discountThreshold', index: 'discountThreshold', width: 80, align: 'left', sortable: false, formatter: ddpMoneyFenToYuan }, 
		    { name: 'beginDate', index: 'beginDate', width: 80, align: 'left', sortable: false, formatter :function(cellval, el, rowData){ return formatDate(cellval,'yyyy-MM-dd'); } }, 
		    { name: 'endDate', index: 'endDate', width: 80, align: 'left', sortable: false, formatter :function(cellval, el, rowData){ return formatDate(cellval,'yyyy-MM-dd'); } }, 
		    { name: 'week', index: 'week', width: 60, align: 'left', sortable: false }, 
		    { name: 'beginTime', index: 'beginTime', width: 80, align: 'left', sortable: false }, 
		    { name: 'endTime', index: 'endTime', width: 80, align: 'left', sortable: false }
		],
		width : $('#merDisCountAllDialog').width() -170,
		height : 230,
		multiselect : true,
		pager : '#merDisCountAllListTblPagination'
		}, jqgrid_server_opts));
		$("#t_merDisCountAllListTbl").append($('#merDisCountAllDialogToolbar'));
	}
	initSelTbl();
});
function findAllTranDiscount(defaultPageNo) {
	var obj = {
		merId: $("#merchantUserTbl").getGridParam('selrow'),
		page : getJqgridPage('merDisCountAllListTbl', defaultPageNo)
	};
	ddpAjaxCall({
		url : "findAllTranDiscount",
		data : obj,
		successFn : function(data) {
			if ("000000" === data.code) {
				loadJqGridPageData($('#merDisCountAllListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}
function showAllTranDiscount() {
	findAllTranDiscount();
	$('#merDisCountAllDialog').show().dialog('open');
}
function operateAccountById() {
	var selarrrow = $("#merDisCountListTbl").getGridParam('selarrrow');// 多选

	var id = $("#merDisCountListTbl").jqGrid('getGridParam', 'selarrrow');

	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要进行解绑操作吗？", function(r) {
			if(r) {
				var discountIds = new Array();
				var merCode = new Array();
				if (id.length>0) {
			    	for (var i = 0; i < id.length; i++) {
			    		var ret = $("#merDisCountListTbl").jqGrid('getRowData', id[i]);
			    		discountIds.push(ret.dateId);
			    		merCode.push(ret.merCode)
			    	}
			    }
				var operation = {
					merCode : merCode,
					discountIds : discountIds
				}
				ddpAjaxCall({
					url : "unbind",
					data : operation,
					successFn : function(data){
						if("000000" === data.code) {
							findMerDisCount();
						}else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	}else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

/*************************************************  折扣选单  *****************************************************************/
function insertTranDiscount() {
	var selarrrow = $("#merDisCountAllListTbl").getGridParam('selarrrow');// 多选
	if(selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "将绑定折扣: "+ selarrrow.length+"条!", function(r) {
			if(r) {
				ddpAjaxCall({
					url: "bind",
					data: { merId: $("#merchantUserTbl").getGridParam('selrow'), ids: selarrrow },
					successFn: function(data) {
						if (data.code == "000000") {
							$('#merDisCountAllDialog').hide().dialog('close');
							clearAllText('merchantUserTbl');
							findMerDisCount();
						} else {
							msgShow(systemWarnLabel, "将绑定的折扣有时间段冲突: <br />"+data.message, 'warning');
						}
					}
				});
			}
		});
	}else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}







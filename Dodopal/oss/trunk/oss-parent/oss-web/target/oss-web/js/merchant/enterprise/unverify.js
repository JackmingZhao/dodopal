$(function() {
			initMainComponent();
			initDetailComponent();
			parent.closeGlobalWaitingDialog();
			//导出列表 2015-12-21
			expExcBySelColTools.initSelectExportDia(expConfInfo);
			initIsNotNumber();
		});

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

function initMainComponent() {
	initUnverMerchantDdicComponent();
	$('#proCityQuery').append(createAreaComponent2('proCityQuery'));
	initAreaComponent('proCityQuery', globalAreaComboboxWidth);
	autoResize({
				dataGrid : {
					selector : '#unverifyTbl',
					offsetHeight : 90,
					offsetWidth : 0
				},
				callback : initMerchantTbl,
				toolbar : [true, 'top'],
				dialogs : ['unverifyMerchantDialog']
			});

}
function initDetailComponent() {
	$('#merAddressLine').prepend(createAreaComponent2('merAddress'));
	initAreaComponent('merAddress', globalAreaComboboxWidth);
	initYktLoadInfo();
	initUnverifyMerchantDialog();
	$('#customerDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 300,
		height : 350,
		/*
		 * onClose:function(){ $('#customerProperty').tree({ checked : false }); },
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
	$('#rejectReasonDialog').dialog({
				collapsible : false,
				modal : true,
				closed : true,
				closable : false,
				width : 450,
				height : 240
			});
	initMerUnverSearchModel();
	// 重置商户属性
	$('#merType').combobox({
				onChange : function(record) {
					$("input[name='merProperty'][value=0]").attr("checked",
							true);
					merPropertyClick('stander');
				}
			});

}

function initUnverifyMerchantDialog() {
	$('#unverifyMerchantDialog').dialog({
				collapsible : true,
				modal : true,
				closed : true,
				closable : false
			});
}

function showUserDialog() {
	$('#unverifyMerchantDialog').show().dialog('open');
}

function hideUserDialog() {
	clearForm();
	$('#unverifyMerchantDialog').hide().dialog('close');
}
// 加载上级商户名称
function findMerParentName() {
	if (isNotSelected($('#merParentTypes').combobox('getValue'))) {
		msgShow(systemWarnLabel, "请先选择上级商户类型!", 'warning');
	} else {
		$('#merchantQuery').val('');
		findMerchantName();
		showDialog('merchantSearchDialog');
	}
}

// 初始化加载未审核商户信息列表
function initMerchantTbl(size) {
	var option = {
		datatype : function(pdata) {
			findUnvenrifyMerchants();
		},
		colNames : ['ID', '商户编码', '商户名称', '上级商户名称', '联系人', '手机号码', 'merPro',
				'merCity', '省份', '城市'],
		colModel : [{
					name : 'id',
					hidden : true
				}, {
					name : 'merCode',
					hidden : true
				}, {
					name : 'merName',
					index : 'merName',
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
					name : 'merLinkUser',
					index : 'merLinkUser',
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
					width : 100,
					align : 'left',
					sortable : false
				}, {
					name : 'merCityName',
					index : 'merCityName',
					width : 100,
					align : 'left',
					sortable : false
				}],
		// caption : "未审核商户信息列表",
		// sortname : 'merCode',
		height : size.height - 90,
		width : size.width,
		multiselect : false,
		pager : '#unverifyMerchantTblPagination',
		toolbar : [true, "top"]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#unverifyTbl').jqGrid(option);
	$("#t_unverifyTbl").append($('#unverifyMerchantTblToolbar'));
}

// 审核通过和审核不通过刷新界面
function afterAddMerchant(data) {
	if (data.code == "000000") {
		hideUserDialog();
		hideDialog('rejectReasonDialog');
		findUnvenrifyMerchants();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
//*************************************第一步  点击打开未审核商户信息  加载未审核商户信息列表 start*********************
// 初始化查询未审核商户信息
function findUnvenrifyMerchants(defaultPageNo) {
	// 省份
	var merProQuery = getProvinceCodeFromCompoent('proCityQuery');
	// 城市
	var merCityQuery = getCityCodeFromCompoent('proCityQuery');
	ddpAjaxCall({
				url : "findMerchantsPage",
				data : {
					merName : escapePeculiar($.trim($('#merNameQuery').val())),
					merParentName : escapePeculiar($
							.trim($('#merParentNameQuery').val())),
					merLinkUser : escapePeculiar($.trim($('#merLinkUserQuery')
							.val())),
					merLinkUserMobile : escapePeculiar($
							.trim($('#merLinkUserMobileQuery').val())),
					merCity : merCityQuery,
					merPro : merProQuery,
					merState : '0',
					page : getJqgridPage('unverifyTbl', defaultPageNo)
				},
				successFn : function(data) {
					if (data.code == "000000") {
						loadJqGridPageData($('#unverifyTbl'),
								data.responseEntity);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
}
//*************************************第一步  点击打开未审核商户信息  加载未审核商户信息列表 end*********************

//********************************************第三步 保存审核商户信息 start***************************
function unverifyMerchant(numberNo) {
	var activate = $('input[name="activate"]:checked').val();
	var merClassify = $('input[name="merClassify"]:checked').val();
	var merProperty = $('input[name="merProperty"]:checked').val();
	var merUserSex = $('input[name="merUserSex"]:checked').val();
	// 资金类型
	var fundType = $('input[name="fundType"]:checked').val();
	var merType = $('#merType').combobox('getValue');
	var merParentTypes = $('#merParentTypes').combobox('getValue');
	// 经营范围
	var merBusinessScopeId = $('#merBusinessScopeId').combobox('getValue');
	// 证件类型
	var merUserIdentityType = $('#merUserIdentityType').combobox('getValue');
	// 开户银行
	var merBankName = $('#merBankName').combobox('getValue');
	if (isValidate('unverifyMerchantDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存商户信息吗？", function(r) {
					if (r) {
						var selarrrow = $("#yktTbl").getGridParam('selarrrow');// 充值业务费率多选
						var selarrrowPayment = $("#yktTblPayment").getGridParam('selarrrow');// 消费业务费率多选
						var selarrrowIcLoad = $("#yktIcLoadTbl").getGridParam('selarrrow');// 圈存业务费率多选
						
						
						var checkedRecharge = $("input[id=01][type='checkbox']").is(':checked');
						var checkedPayment = $("input[id=03][type='checkbox']").is(':checked');
						var checkedIcLoad = $("input[id=04][type='checkbox']").is(':checked');
						
						if(checkedRecharge && selarrrow && selarrrow.length == 0) {
							msgShow(systemWarnLabel, '请选择通卡公司(充值)', 'warning');
							return;
						}
						if(checkedPayment && selarrrowPayment && selarrrowPayment.length == 0) {
							msgShow(systemWarnLabel, '请选择通卡公司(消费)', 'warning');
							return;
						}
						
						if (checkedIcLoad && selarrrowIcLoad && selarrrowIcLoad.length == 0) {
							msgShow(systemWarnLabel, '请选择通卡公司(圈存)', 'warning');
							return;
						}
						//未审核商户信息一卡通充值
						var merBusRateBeanList = new Array();
						if (selarrrow || selarrrowPayment) {
							for (var i = 0; i < selarrrow.length; i++) {
								var rowData = $("#yktTbl")
										.getRowData(selarrrow[i]);
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
							//未审核商户信息一卡通消费
							for (var i = 0; i < selarrrowPayment.length; i++) {
								var rowData = $("#yktTblPayment")
										.getRowData(selarrrowPayment[i]);
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
								//未审核商户信息圈存充值
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
						var $selectAll = $('#customerProperty')
								.tree('getChecked');
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
						// 判断商户类型
						if (!isBlank(merType)) {
							if (merType == "10" || merType == "12"
									|| merType == '11' || merType == '13'
									|| merType == '14') {

								if (merParentTypes == '10'
										|| merParentTypes == '12') {
									if ($('#merParentName').val() == "") {
										msgShow(systemWarnLabel, "请选择上级商户名称!",
												'warning');
										return false;
									}
								} else if (merParentTypes != "null") {
									msgShow(systemWarnLabel, "请选择上级商户类型!",
											'warning');
									return false;
								}
							}
						} else {
							msgShow(systemWarnLabel, "请选择商户类型!", 'warning');
							return false;
						}
						// 商户业务信息
						var merRateSpmtList = new Array();
						// 现在判断是否勾选当前业务类型
						var pageCallbackUrl = "";// 页面回调URL
						var serviceNoticeUrl = "";// 服务器回调URL
						var pageCheckCallbackUrl = "";// 服务器验卡URL
						// 外接商户是否弹出商户信息
						var isShowErrorMsg = "";
						var prdRateTypeAll = $('#PrdRateType')
								.find("input[type=checkbox]:checked");
						$.each(prdRateTypeAll, function(index, element) {
									var rateCode = $('#' + element.id).val();
									if (rateCode == '01') {
										pageCallbackUrl = $('#YktPageCallbackUrl')
												.val();
										serviceNoticeUrl = $('#YktServiceNoticeUrl')
												.val();
										pageCheckCallbackUrl = $('#pageCheckCallbackUrl')
												.val();
										isShowErrorMsg = $('input[name="isShowErrorMsg"]:checked').val()
									}
									var merRateSpmtBean = {
										rateCode : rateCode,
										pageCallbackUrl : pageCallbackUrl,
										serviceNoticeUrl : serviceNoticeUrl,
										pageCheckCallbackUrl:pageCheckCallbackUrl,
										isShowErrorMsg : isShowErrorMsg
									}
									merRateSpmtList.push(merRateSpmtBean);
								});
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
						
						
						// ------------------新需求新增字段 2015-12-05---------------
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
							merUserIdentityNumber : $('#merUserIdentityNumber')
									.val(),
							merUserEmail : $('#merUserEmail').val(),
							merUserRemark : $('#merUserRemark').val(),
							tradeArea : tradeArea,
							education : education,
							merUserPro : getProvinceCodeFromCompoent('merAddress'),
							merUserCity : getCityCodeFromCompoent('merAddress'),
							income : income,
							birthday : birthday,
							isMarried : isMarried
						};
						//--------------判断连着直营网点
						//-----判断连锁直营网点是否自动分配额度-------------
						var isAutoDistibute = $('input[name="isAutoDistibute"]:checked').val();
						var limitThreshold = $('#limitThreshold').val()*100;
						var selfMotionLimitThreshold = $('#selfMotionLimitThreshold').val()*100;
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
						var merAutoAmtBean = {
						 limitThreshold:limitThreshold,
						 autoLimitThreshold: selfMotionLimitThreshold
						};
						//------连锁加盟网点
						//var limitSource = $('input[name="limitSource"]:checked').val();
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
						
						
						var merKeyTypeBean = {
							merMD5PayPwd : $('#merMd5paypwd').val(),
							merMD5BackPayPWD : $('#merMd5Backpaypwd').val()
						};
						var merchantBeans = {
							merCode : $('#merCode').val(),
							merName : $('#merName').val(),
							merType : merType,
							merClassify : merClassify,
							merProperty : merProperty,
							activate : activate,
							merPro : getProvinceCodeFromCompoent('merAddress'),
							merCity : getCityCodeFromCompoent('merAddress'),
							merLinkUser : $('#merLinkUser').val(),
							merLinkUserMobile : $('#merLinkUserMobile').val(),
							merAdds : $('#merAddressName').val(),
							merBusinessScopeId : merBusinessScopeId,
							merFax : $('#merFax').val(),
							merZip : $('#merZip').val(),
							merBankName : merBankName,
							merBankAccount : $('#merBankAccount').val(),
							merBankUserName : $('#merBankUserName').val(),
							merParentCode : $('#merParentCode').val(),
							merPayMoneyUser : $('#merPayMoneyUser').val(),
							merState : numberNo,
							fundType : fundType,
							merBusRateBeanList : merBusRateBeanList,
							merDdpInfoBean : merDdpInfoBean,
							merAutoAmtBean : merAutoAmtBean,
							merRateSpmtList : merRateSpmtList,
							merchantUserBean : merchantUserBean,
							merKeyTypeBean : merKeyTypeBean,
							merDefineFunBeanList : merDefineFunBeanList
						};
						ddpAjaxCall({
									url : "saveMerchantUserBusRate",
									data : merchantBeans,
									successFn : afterAddMerchant
								});
					}
				});
	}
}
//********************************************第二步保存审核商户信息end***************************
// 保存审核不通过
function openRejectReasonDialog() {
	$('#merRejectReason').val('');
	showDialog('rejectReasonDialog');
}
function unverifyNotMerchant(numberNo) {
	if (isValidate('rejectReasonDialog')) {
		var merchantUserBean = {
			merUserName : $('#merUserName').val(),
			merUserRemark : $('#merUserRemark').val()
		};
		var merchantBeans = {
			merCode : $('#merCode').val(),
			merState : numberNo,
			merRejectReason : $('#merRejectReason').val(),
			merchantUserBean : merchantUserBean
		};
		ddpAjaxCall({
					url : "saveMerchantUserBusRate",
					data : merchantBeans,
					successFn : afterAddMerchant
				});
	}
}
// 查看详情
function findVerified() {
	var selrow = $("#unverifyTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#unverifyTbl').getRowData(selrow);
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
//*********************************** 选择查看未审核详细信息 start ***************************
// 加载未审核商户详情
function loadViewMerchants(data) {
	if (data.code == "000000") {
		showViewMerchant();
		var merchantBeans = data.responseEntity;
		//商户用户信息表
		var merchantUserBean =  merchantBeans.merchantUserBean;
		// 费率信息表
		var merRateSpmtList = merchantBeans.merRateSpmtList;
		// 商户补充信息表
		var merDdpInfoBean = merchantBeans.merDdpInfoBean;
		
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
				+ merchantBeans.merCityName + '市' + merchantBeans.merAdds);
				
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

		$('#providerNameView')
				.html(merchantUserBean.providerNameView);
		$('#merBusinessScopeNameView').html(merchantBeans.merBusinessScopeIdView);
		$('#merUserSexView')
				.html(merchantUserBean.merUserSexView);
		$('#merUserTelephoneView')
				.html(merchantUserBean.merUserTelephone);
		$('#merFaxView').html(merchantBeans.merFax);
		$('#merUserIdentityTypeView')
				.html(merchantUserBean.merUserIdentityTypeView);
		$('#merUserIdentityNumberView')
				.html(merchantUserBean.merUserIdentityNumber);
		$('#merUserEmailView')
				.html(merchantUserBean.merUserEmail);
		$('#merZipView').html(merchantBeans.merZip);

		$('#merBankNameView').html(merchantBeans.merBankNameView);
		$('#merBankAccountView').html(merchantBeans.merBankAccount);
		$('#merBankUserNameView').html(merchantBeans.merBankUserName);
		$('#merUserRemarkView')
				.html(htmlTagFormat(merchantUserBean.merUserRemark));
		$('#merUserRemarkView')
				.text(merchantUserBean.merUserRemark == null
						? ""
						: merchantUserBean.merUserRemark);
		$('#merDdpLinkIpView').html(merchantBeans.merDdpLinkIp);
		loadYkt2(merchantBeans.merBusRateBeanList);
		$('#sourceView').html(merchantBeans.sourceView);
		$('#createUserView').html(merchantBeans.createUser);
		$('#createDateView').html(merchantBeans.createDateView);
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
			$('#incomeView').html(Number(merchantUserBean.income/100)+'元');
		}else{
			$('#incomeView').html(merchantUserBean.income);
		}
		
		/* 生日，格式：1990-09-10 */
		$('#birthdayView').html(merchantUserBean.birthday);
		/* 是否已婚，0是，1否 */
		$('#isMarriedView').html(merchantUserBean.isMarriedView);
		/* 商圈 */
		$('#tradeAreaView').html(merDdpInfoBean.tradeArea);
		/*商户打款人 */
		$('#merPayMoneyUserView').html(merchantBeans.merPayMoneyUser);		//商户业务信息表
		/*是否弹出错误信息,0是，1否，默认是*/
		//$('#isShowErrorMsgView').html(merchantBeans.isShowErrorMsgView);
		//---------------------------------修改最新查看展示-----------
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
//*********************************** 选择查看未审核详细信息  end ***************************


//******************************************第二步 点击审核商户信息 start************************
// 点击审核不通过商户信息
function editorVerified() {
	clearForm();
	$('#merUserName').attr('disabled', true);
	var selrow = $("#unverifyTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#unverifyTbl').getRowData(selrow);
		var merCode = rowData.merCode;
		ddpAjaxCall({
					url : "findMerchantByCode",
					data : merCode,
					successFn : loadMerchantInfo
				});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

// 加载未审核商户信息
function loadMerchantInfo(data) {
	if (data.code == "000000") {
		clearForm();
		var merchantBeans = data.responseEntity;
		//	费率信息
		var merRateSpmtList = merchantBeans.merRateSpmtList;
		// 商户用户信息
		var merchantUserBeans = merchantBeans.merchantUserBean;
		// 验签密钥信息
		var merKeyTypeBeans = merchantBeans.merKeyTypeBean;
		//-----------新需求变更-------------------------------------
		var merDdpInfoBean = merchantBeans.merDdpInfoBean; // 商户补充信息
		var merAutoAmtBean =  merchantBeans.merAutoAmtBean; //自动分配额度
		
		//----------------------商户用户信息start-------------------
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
		// ------------商户用户信息表 商户用户信息 end--------------
						
	
		
		// -------------商户补充信息表 商户补充信息 start -----------

		if(merDdpInfoBean!=null && typeof(merDdpInfoBean) == "undefined"){
//		$('#merDdpLinkIp').val(merDdpInfoBean.merDdpLinkIp);
//		$('#merDdpLinkUser').val(merDdpInfoBean.merDdpLinkUser);
//		$('#merDdpLinkUserMobile').val(merDdpInfoBean.merDdpLinkUserMobile);
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
		}
		
		// ------商户补充信息表 商户补充信息 end ------
		
		// --------验签密钥信息start--------------
		if (merKeyTypeBeans != null && typeof(merKeyTypeBeans) == "undefined") {
			$('#merMd5paypwd').val(merKeyTypeBeans.merMD5PayPwd);
			$('#merMd5Backpaypwd').val(merKeyTypeBeans.merMD5BackPayPWD);
		}
		// --------验签密钥信息end--------------
		//------------------- 加载商户信息start------------------------
		if (merchantBeans.merType == '13' || merchantBeans.merType == '14') {
			//是否自动分配额度
			$('#isAutoDistibuteOne').show();
			$('#isAutoDistibuteTwo').show();
			
			//是否是否自动分配额度
			$("input[name='isAutoDistibute'][value=" + merDdpInfoBean.isAutoDistribute + "]").attr("checked", true);
			if(merDdpInfoBean.isAutoDistribute=='0'){
				$('#isAutoDistibuteLine').show();
			// 额度阀值
			$("#limitThreshold").val(Number(merAutoAmtBean.limitThreshold)/100);
			// 自动分配额度阀值
			$("#selfMotionLimitThreshold").val(Number(merAutoAmtBean.autoLimitThreshold)/100);
			}else{
				$('#isAutoDistibuteLine').hide();
				$("#limitThreshold").val('');
				$("#selfMotionLimitThreshold").val('');
			}
		} else {
				//是否自动分配额度
			$('#isAutoDistibuteOne').hide();
			$('#isAutoDistibuteTwo').hide();
			$('#isAutoDistibuteLine').hide();
			$("#limitThreshold").val('');
			$("#selfMotionLimitThreshold").val('');
			// 是否自动分配额度
			$("input[name='isAutoDistibute'][value=0]").attr("checked", true);
		
		}
		
		if (merchantBeans.merType == '14') {
			$('#merType').combobox('disable');
//			//额度来源
//			$('#limitSourceOne').show();
//			$('#limitSourceTwo').show();
//			$("input[name='limitSource'][value=" + merDdpInfoBean.limitSource + "]").attr("checked", true);
//			//额度来源初始化不能修改
//			$("input[name='limitSource']").attr("disabled", true);
		}else{
//			//额度来源
//			$('#limitSourceOne').hide();
//			$('#limitSourceTwo').hide();
//			$("input[name='limitSource'][value=0]").attr("checked", true);
//			//额度来源初始化不能修改
//			$("input[name='limitSource']").attr("disabled", false);
		}
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
		// 上级商户名称
		$('#merParentName').val(merchantBeans.merParentName);
		$('#merParentCode').val(merchantBeans.merParentCode);
		// 判断上级商户费率
		// findMerParentCode(merchantBeans.merParentCode);
		// 详细地址
		setAreaComponent('merAddress', merchantBeans.merPro,
				merchantBeans.merCity);
		// 地址
		$('#merAddressName').val(merchantBeans.merAdds);
		// 判断未审核
		$('#merUnVer').val('0');
		// 业务费率信息
		loadUnVerInfo(data);
		// 勾选业务类型
		if (merchantBeans.merParentCode != null) {
			merPrdRateUnverType(merchantBeans);
		} else {
			$.each(merRateSpmtList, function(index, element) {
							$('#PrdRateType').find("input[id="
									+ element.rateCode + "][type=checkbox]")
									.attr("checked", "checked");
					});
			var checked = $("input[id='01'][type='checkbox']").is(':checked');
			if (checked) {
				$('#TKNameLine').show();
			}
			var checkedPayment = $("input[id='03'][type='checkbox']").is(':checked');
			if (checkedPayment) {
				$('#TKNameLinePayment').show();
			}
		}
		showUserDialog();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
//******************************************第二步 点击审核商户信息 start************************

// 有上级商户时加载业务类型
function merPrdRateUnverType(merchantBeans) {
	$('#PrdRateType').html('');
	var merParentCode = merchantBeans.merParentCode;
	var merRateSpmtList = merchantBeans.merRateSpmtList;
	// 初始化加载费率类型
	ddpAjaxCall({
				async : false,
				url : "findMerRateSupplementByCode",
				data : merParentCode,
				successFn : function(data) {
					if (data.code = '000000') {
						loadUnverEditPrdRateModel(data);
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
								$('#TKNameLine').show();
							}
							
							var checkedPayment = $("input[id='03'][type='checkbox']")
									.is(':checked');
							if (checkedPayment) {
								$('#TKNameLinePayment').show();
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
function loadUnverEditPrdRateModel(data) {
	// 获取加载的值
	var prdRateBean = data.responseEntity;
	var line = '';
	if (prdRateBean != null) {
		for (var i = 0; i < prdRateBean.length; i++) {
			line = '<td id="'+'td'+prdRateBean[i].rateCode+'"><input type="checkbox" id="' + prdRateBean[i].rateCode
					+ '" name="prdRateName" style="width:20px;border:0;" ';
			line += 'value= "' + prdRateBean[i].rateCode
					+ '" onclick="prdRateTypeSys(this)"/>&nbsp;&nbsp;'
					+ prdRateBean[i].rateCodeView + '</td>';
			$('#PrdRateType').append(line);
		}
	} else {
		$('#PrdRateType').html('');
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
					for(var i = 0; i< proNameArray.length; i++) {
						if(proNameArray[i]==v.proName) {
							isFound = true;
							break;
						}
					}
					if(!isFound) {
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
	// 账户类型
	$("input[name='fundType'][value=0]").attr("checked", true);
	$("input[name='fundType']:eq(0)").removeAttr("disabled", 'disabled');
	$("input[name='fundType']:eq(1)").removeAttr("disabled", 'disabled');
	clearAreaComponent('merAddress');
	clearAllText('unverifyMerchantDialog');
	clearAllCombobox('unverifyMerchantDialog');
	clearAllText('customerDialog');
	$('#yktTblLine').hide();
	$('#yktTbl').clearGridData();
	$('#yktTblLinePayment').hide();
	$('#yktTblPayment').clearGridData();
	//圈存充值
	$('#yktIcLoadTblLine').hide();
	$('#yktIcLoadTbl').clearGridData();
}

function setSelectedMerchant(merchant) {
	if (typeof(merchant) != 'undefined') {
		$('#merParentName').val(merchant.merName);
		$('#merParentCode').val(merchant.merCode);
	}
}

// 业务费率名称
function busRateType(rateType) {
	if (rateType == '1') {
		return '单笔返点金额(元)';
	}
	if (rateType == '2') {
		return '千分比(‰)';
	}
}

// 初始化加载费率类型
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
			$("#TKNameLine").show();
		}
		if ($('#' + obj.id + '').val() == '03') {
			$("#TKNameLinePayment").show();
		}
		if ($('#' + obj.id + '').val() == '04') {
			$("#IcLoadLine").show();
		}
	} else {
		if ($('#' + obj.id + '').val() == '01') {
			$('#yktInfoContent').find("input[type=checkbox]")
					.removeAttr("checked");
			yktHandler();
			$("#yktTblLine").hide();
			$("#TKNameLine").hide();
			$("#yktInfoUrlLine").hide();
			$('#pageCheckCallbackUrlLine').hide()
			$("#YktPageCallbackUrl").val('');
			$("#YktServiceNoticeUrl").val('');
		}
		if ($('#' + obj.id + '').val() == '03') {
			$('#yktInfoContentPayment').find("input[type=checkbox]")
					.removeAttr("checked");
			yktHandlerPayment();
			$("#yktTblLinePayment").hide();
			$("#TKNameLinePayment").hide();
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

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "exportUnverifyMgmt", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "MerchantUnauditedExpBean", 	/*must*/// type of export
	"toUrl"			: "exportExcelUnverify", 			/*must*/// the export url
	"parDiaHeight"  : "400"
};
var filterConStr = [	// filter the result by query condition
		{'name':'merNameQuery', 	'value': "escapePeculiar($.trim($('#merNameQuery').val()))"			},	// eval
		{'name':'merParentNameQuery',	'value': "escapePeculiar($.trim($('#merParentNameQuery').val()))"		},
		{'name':'merLinkUser',	'value': "escapePeculiar($.trim($('#merLinkUserQuery').val()))"		},
		{'name':'merLinkUserMobile',	'value': "escapePeculiar($.trim($('#merLinkUserMobileQuery').val()))"		},
		{'name':'merProQuery',	'value': "escapePeculiar(getProvinceCodeFromCompoent('proCityQuery'))"		},
		{'name':'merCityQuery',	'value': "escapePeculiar(getCityCodeFromCompoent('proCityQuery'))"		},
		{'name':'merState',	'value': "escapePeculiar('0')"		}
	];

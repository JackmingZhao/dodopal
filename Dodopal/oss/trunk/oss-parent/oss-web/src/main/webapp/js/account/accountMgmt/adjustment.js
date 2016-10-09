$(function () {
	initMainComponent();
	initDetailComponent();
	initMerchantSearchModel();
	initMerchantUserSearchModel();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	$("input[name='customerType']").click(function() {
		clearAllText('adjustmentDialog');
		$("input[name='fundType']").attr("checked",false);
		if ($(this).val() == "0") { // 个人
			clearAllText('merchantUserQueryCondition');
			findMerchantUser();
			showDialog('merchantUserSearchDialog');
		} else if ($(this).val() == "1") { // 企业
			clearAllText('merchantQueryCondition');
			findMerchantName();
			showDialog('merchantSearchDialog');
		}
	});
	
	

	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#adjustmentTbl',
            offsetHeight: 90,
            offsetWidth: 0
        },
        callback: initAdjustmentTbl,
        toolbar: [true, 'top']
    });
}

function initDetailComponent() {
	initAdjustmentDialog();
	initApproveDialog();
}

function clearAdjustment() {
	clearAllText('adjustmentDialog');
	$("input[name='accountAdjustType']").removeAttr("disabled"); 
	$("input[name='customerType']").removeAttr("disabled"); 
	$("input[name='customerType']").attr("checked",false);
	$("input[name='fundType']").attr("checked",false);
	$("input[name='accountAdjustType']").attr("checked",false);
	$('#fundAcctCode').val("");
	$('#authorizedAcctCode').val("");
	$('#customerNo').val('');
	$('#customerName').val('');
	$('#fundAccountCode').val('');
	$('#updateAdjustment').hide();
	$('#saveAdjustment').show();
	$('#approveAdjustmentBtn').hide();
	$('#rejectAdjustmentBtn').hide();
	$('#auditLine').hide();
	$('#fundTypeAuthorized').hide();
}

function setSelectedMerchant(merchant) {
	if (typeof(merchant) != 'undefined') {
		$('#customerNo').val(merchant.customerNo); 
		$('#customerName').val(merchant.customerName);
		var queryData = {};
		queryData.customerNo = merchant.customerNo;
		queryData.customerType = merchant.customerType;
		ddpAjaxCall({
			url : "findFundAccountInfo",
			data : queryData,
			successFn : function(data) {
				if (data.code == "000000") {
					var fundAcctInfo = data.responseEntity;
					// 如果是个人，自动关联账户类型（资金账户）和账户号（资金账户号）；
					$('#fundAcctCode').val(fundAcctInfo.fundAccountCode);
					$('#authorizedAcctCode').val(fundAcctInfo.authorizedAccountCode);
					if( merchant.customerType == '0') { // 个人
						$("input[name='fundType']").attr("disabled","disabled"); 
						$("input[name='fundType'][value="+ fundAcctInfo.fundType +"]").attr("checked",true); 
						$('#fundTypeAuthorized').hide();
						$('#fundAccountCode').val(fundAcctInfo.fundAccountCode);
						changeCreditV();
					} else if(merchant.customerType == '1') { // 企业
						if(fundAcctInfo.fundType == '0') { // 如果是企业，且主账户上的资金类别字段为“资金”， 自动关联账户类型（资金账户）和账户号（资金账户号）；
							// 复制个人情况下的代码，这里重复代码为了以后 业务变更时方便维护
							$("input[name='fundType']").attr("disabled","disabled"); 
							$("input[name='fundType'][value="+ fundAcctInfo.fundType +"]").attr("checked",true); 
							$('#fundTypeAuthorized').hide();
							$('#fundAccountCode').val(fundAcctInfo.fundAccountCode);
						} else if(fundAcctInfo.fundType == '1') { // 如果是企业，且主账户上的资金类别为“授信”，则显示账户类型给用户选择。
							$("input[name='fundType']").removeAttr("disabled"); 
							$("input[name='fundType']").attr("checked",false); 
							$('#fundTypeAuthorized').show();
							$('#fundAccountCode').val("");
							$('#creditAmt').val(ddpMoneyFenToYuan(fundAcctInfo.creditAmt));
							$('#availableBalance').val(ddpMoneyFenToYuan(fundAcctInfo.availableBalance));
						}
						changeCreditV();
					}
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

function saveAdjustment() {
	if(isValidate('adjustmentDialog') && valiDateCreditVal() ) { 	// Mika
		var accountAdjustType = $("input[name='accountAdjustType']:checked").val();
		var customerType= $("input[name='customerType']:checked").val();
		var fundType = $("input[name='fundType']:checked").val();
		if(isBlank(accountAdjustType)) {
			msgShow(systemWarnLabel, "调账方式不能为空", 'warning');
			return;
		}
		if(isBlank(customerType)) {
			msgShow(systemWarnLabel, "客户类型不能为空", 'warning');
			return;
		}
		if(isBlank(fundType)) {
			msgShow(systemWarnLabel, "账户类型不能为空", 'warning');
			return;
		}
		$.messager.confirm(systemConfirmLabel, "确定要保存调账申请吗？", function(r) {
			if (r) {
				var adjustment = {
						id : $('#adjustmentId').val(),
						accountAdjustType : $("input[name='accountAdjustType']:checked").val(),
						customerType : $("input[name='customerType']:checked").val(),
						customerNo : $('#customerNo').val(),
						customerName : $('#customerName').val(),
						fundType : $("input[name='fundType']:checked").val(),
						fundAccountCode : getTrimValue($('#fundAccountCode').val()),
						accountAdjustAmount : $('#accountAdjustAmount').val()*100,
						accountAdjustReason : $('#accountAdjustReason').val()
				};
				ddpAjaxCall({
					url : "applyAccountAdjustment",
					data : adjustment,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('adjustmentDialog');
							if(isBlank(adjustment.id)) {
								findAdjustments(1);
							} else {
								findAdjustments();
							}
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	}
}

function updateAdjustment() {
	if(isValidate('adjustmentDialog') && valiDateCreditVal()) {
		$.messager.confirm(systemConfirmLabel, "确定要修改调账申请吗？", function(r) {
			if (r) {
				var adjustment = {
						id : $('#adjustmentId').val(),
						accountAdjustType : $("input[name='accountAdjustType']:checked").val(),
						customerType : $("input[name='customerType']:checked").val(),
						customerNo : $('#customerNo').val(),
						customerName : $('#customerName').val(),
						fundType : $("input[name='fundType']:checked").val(),
						fundAccountCode : getTrimValue($('#fundAccountCode').val()),
						accountAdjustAmount : $('#accountAdjustAmount').val()*100,
						accountAdjustReason : $('#accountAdjustReason').val()
				};
				ddpAjaxCall({
					url : "updatetAccountAdjustments",
					data : adjustment,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('adjustmentDialog');
							if(isBlank(adjustment.id)) {
								findAdjustments(1);
							} else {
								findAdjustments();
							}
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	}
}

function editAdjustment() {
	clearAdjustment();
	var rowData = getSelectedRowDataFromMultiples('adjustmentTbl');
	if (rowData != null) {
		ddpAjaxCall({
			url : "viewAccountAdjustment",
			data : rowData.adjustmentId,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var entity = data.responseEntity;console.log(entity);// mika
					if(entity.accountAdjustState == '0' || entity.accountAdjustState == '3') {
						$("input[name='accountAdjustType']").attr("disabled","disabled"); 
						$("input[name='customerType']").attr("disabled","disabled"); 
						$("input[name='fundType']").attr("disabled","disabled"); 
						$('#adjustmentId').val(entity.id);
						$("input[name='accountAdjustType'][value="+entity.accountAdjustType+"]").attr("checked",true); 
						$("input[name='customerType'][value="+entity.customerType+"]").attr("checked",true); 
						$('#customerNo').val(entity.customerNo);
						$('#customerName').val(entity.customerName);
						$("input[name='fundType'][value="+entity.fundType+"]").attr("checked",true); 
						$('#accountAdjustAmount').val(entity.accountAdjustAmount/100);
						$('#accountAdjustReason').val(entity.accountAdjustReason);
						
						$('#updateAdjustment').show();
						$('#saveAdjustment').hide();
						
						if(entity.fundType == '1') {
							$('#fundTypeAuthorized').show();
						} else {
							$('#fundTypeAuthorized').hide();
						}
						ddpAjaxCall({ 	// Mika
							url : "findFundAccountInfo",
							data : {"customerNo" : entity.customerNo, "customerType" : entity.customerType},
							successFn : function(data) {
								if (data.code == "000000") {
									var fundAcctInfo = data.responseEntity;
									var customerType = $("input[name='customerType']:checked").val();
									var fundType = $("input[name='fundType']:checked").val();
									if(customerType == 1 && fundType == 1) {
										$('#availableBalance').val(ddpMoneyFenToYuan(fundAcctInfo.availableBalance));
										$('#creditAmt').val(ddpMoneyFenToYuan(fundAcctInfo.creditAmt));
									}
									setCreditVal();
								}
							}
						});
						isCreditVal(); 	// Mika
						$('#fundAccountCode').val(entity.fundAccountCode); 	// Mika
						showDialog('adjustmentDialog');
					} else {
						msgShow(systemWarnLabel, "此状态的调账申请单不允许修改", 'warning');
					}
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

function isCreditVal() { // By Mika
	changeCreditV();
}

function deleteAdjustment() {
	var selarrrow = $("#adjustmentTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "调账信息一旦删除将无法恢复, 确定要删除吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#adjustmentTbl").getRowData(selarrrow[i]);
					if(rowData.accountAdjustState != '0') {
						msgShow(systemWarnLabel, "只能删除未审批的申请单", 'warning');
						return;
					}
					ids.push(rowData.adjustmentId);
				}
				
				ddpAjaxCall({
					url : "deleteAccountAdjustment",
					data : ids,
					successFn : function(data) {
						if(data.code == '000000') {
							findAdjustments();
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function approveAdjustmentSingleHandler(rowData) {
	if (rowData != null) {
		ddpAjaxCall({
			url : "viewAccountAdjustment",
			data : rowData.adjustmentId,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var entity = data.responseEntity;
					if(entity.accountAdjustState != '0') {
						msgShow(systemWarnLabel, "此状态不能进行审核", 'warning');
					} else {
						$("input[name='accountAdjustType']").attr("disabled","disabled"); 
						$("input[name='customerType']").attr("disabled","disabled"); 
						$("input[name='fundType']").attr("disabled","disabled"); 

						$('#accountAdjustAmount').attr('disabled', true);
						$('#accountAdjustReason').attr('disabled', true);
						$('#auditLine').show();
						
						$('#adjustmentId').val(entity.id);
						$('#accountAdjustCode').val(entity.accountAdjustCode);
						$("input[name='accountAdjustType'][value="+entity.accountAdjustType+"]").attr("checked",true); 
						$("input[name='customerType'][value="+entity.customerType+"]").attr("checked",true); 
						$('#customerNo').val(entity.customerNo);
						$('#customerName').val(entity.customerName);
						$("input[name='fundType'][value="+entity.fundType+"]").attr("checked",true); 
						$('#fundAccountCode').val(entity.fundAccountCode);
						$('#accountAdjustAmount').val(entity.accountAdjustAmount/100);
						$('#accountAdjustReason').val(entity.accountAdjustReason);
						$('#updateAdjustment').hide();
						$('#saveAdjustment').hide();
						$('#approveAdjustmentBtn').show();
						$('#rejectAdjustmentBtn').show();
						$('#accountAdjustAuditExplain').val("");
						if (entity.fundType == '0') {
							$('#fundTypeFund').show();
							$('#fundTypeAuthorized').hide();
						} else if (entity.fundType == '1') {
							$('#fundTypeFund').hide();
							$('#fundTypeAuthorized').show();
						}
						showDialog('adjustmentDialog');
					}
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

function approveAdjustmentMultipleHandler(selarrrow) {
	var adjustments = "";
	for (var i = 0; i < selarrrow.length; i++) {
		var rowData = $("#adjustmentTbl").getRowData(selarrrow[i]);
		if(rowData.accountAdjustState != '0') {
			msgShow(systemWarnLabel, "请选择未审批的申请单", 'warning');
			return;
		}
		if(i != 0) {
			adjustments = adjustments + ',';
		}
		adjustments = adjustments + rowData.adjustmentId;
	}
	$('#adjustmentIds').val(adjustments);
	$('#auditExplain').val("");
	showDialog('approveDialog');
}

function approveAdjustment() {
	var selarrrow = $('#adjustmentTbl').getGridParam('selarrrow');// 多选
	if (selarrrow) {
		 if (selarrrow.length == 0) {
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		} else if (selarrrow.length == 1) {
			var rowData = $('#adjustmentTbl').getRowData(selarrrow[0]);
			approveAdjustmentSingleHandler(rowData);
		} else {
			approveAdjustmentMultipleHandler(selarrrow);
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

// 执行 (单个)
function approveAdjustmentAction() {
	var adjustment = {
		id : $('#adjustmentId').val(),
		accountAdjustAuditExplain : $('#accountAdjustAuditExplain').val()
	};
	var adjustments = new Array();
	adjustments.push(adjustment);
	ddpAjaxCall({
		url : "approveAccountAdjustment",
		data : adjustments,
		successFn : function(data) {
			if (data.code == "000000") {
				hideDialog('adjustmentDialog');
				if (isBlank(adjustment.id)) {
					findAdjustments(1);
				} else {
					findAdjustments();
				}
			} else if(data.code == '171002' || data.code == '171003') {
				$.messager.alert(systemWarnLabel, data.message, 'warning', function() {
					hideDialog('adjustmentDialog');
					if (isBlank(adjustment.id)) {
						findAdjustments(1);
					} else {
						findAdjustments();
					}
					$('.panel-tool').show();
				});
				$('.panel-tool').hide();
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

//拒绝  (单个)
function rejectAdjustment() {
	var explain = getTrimValue($('#accountAdjustAuditExplain').val())
	if (isBlank(explain)) {
		msgShow(systemWarnLabel, '必须填写审核说明', 'warning');
	} else {
		var adjustment = {
			id : $('#adjustmentId').val(),
			accountAdjustAuditExplain : $('#accountAdjustAuditExplain').val()
		};
		var adjustments = new Array();
		adjustments.push(adjustment);
		ddpAjaxCall({
			url : "rejectAccountAdjustment",
			data : adjustments,
			successFn : function(data) {
				if (data.code == "000000") {
					hideDialog('adjustmentDialog');
					if (isBlank(adjustment.id)) {
						findAdjustments(1);
					} else {
						findAdjustments();
					}
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

// 批量执行
function approveAdjustmentList() {
	var explain = getTrimValue($('#auditExplain').val());
	var adjustmentIds = $('#adjustmentIds').val();
	var adjustmentIdArray = adjustmentIds.split(',');
	var adjustmentArray = new Array();
	for(var i=0; i<adjustmentIdArray.length; i++) {
		var adjustment = {
				id : adjustmentIdArray[i],
				accountAdjustAuditExplain: explain
		}
		adjustmentArray.push(adjustment);
	}
	ddpAjaxCall({
		url : "approveAccountAdjustment",
		data : adjustmentArray,
		successFn : function(data) {
			if (data.code == "000000") {
				hideDialog('approveDialog');
				if (isBlank(adjustment.id)) {
					findAdjustments(1);
				} else {
					findAdjustments();
				}
			} else if(data.code == '171002' || data.code == '171003') {
				$.messager.alert(systemWarnLabel, data.message, 'warning', function() {
					hideDialog('approveDialog');
					if (isBlank(adjustment.id)) {
						findAdjustments(1);
					} else {
						findAdjustments();
					}
					$('.panel-tool').show();
				});
				$('.panel-tool').hide();
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

// 批量拒绝
function rejectAdjustmentList() {
	var explain = getTrimValue($('#auditExplain').val());
	if (isBlank(explain)) {
		msgShow(systemWarnLabel, '必须填写审核说明', 'warning');
	} else {
		var adjustmentIds = $('#adjustmentIds').val();
		var adjustmentIdArray = adjustmentIds.split(',');
		var adjustmentArray = new Array();
		for(var i=0; i<adjustmentIdArray.length; i++) {
			var adjustment = {
					id : adjustmentIdArray[i],
					accountAdjustAuditExplain: explain
			}
			adjustmentArray.push(adjustment);
		}
		ddpAjaxCall({
			url : "rejectAccountAdjustment",
			data : adjustmentArray,
			successFn : function(data) {
				if (data.code == "000000") {
					hideDialog('approveDialog');
					if (isBlank(adjustment.id)) {
						findAdjustments(1);
					} else {
						findAdjustments();
					}
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

function initAdjustmentDialog() {
	$('#adjustmentDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function initApproveDialog() {
	$('#approveDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 300
	});
}

function initAdjustmentTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findAdjustments();
        },
        colNames: ['ID', 'accountAdjustState', '客户名称', '客户号', '账户类型', '调账类型', '调账金额(元) ','申请人','申请时间', '审核人','审核时间','状态'],
        colModel: [
            {name: 'adjustmentId', hidden: true},
            {name: 'accountAdjustState', hidden: true},
            {name: 'customerName', index: 'customerName', width: 150, align: 'left', sortable: false, formatter: htmlFormatter},
            {name: 'customerNo', index: 'customerNo', width: 140, align: 'left', sortable: false},
            {name: 'fundTypeStr', index: 'fundTypeStr', width: 60, align: 'center', sortable: false},
            {name: 'accountAdjustTypeStr', index: 'accountAdjustTypeStr', width: 60, align: 'center', sortable: false},
            {name: 'accountAdjustAmountStr', index: 'accountAdjustAmountStr', width: 100, align: 'center', sortable: false},
            {name: 'accountAdjustApplyUser', index: 'accountAdjustApplyUser', width: 60, align: 'center', sortable: false},
            {name: 'accountAdjustApplyDate', index: 'accountAdjustApplyDate', width: 140, align: 'center', sortable: false, formatter: cellDateFormatter},
            {name: 'accountAdjustAuditUser', index: 'accountAdjustAuditUser', width: 60, align: 'center', sortable: false },
            {name: 'accountAdjustAuditDate', index: 'accountAdjustAuditDate', width: 140, align: 'center', sortable: false , formatter: cellDateFormatter},
            {name: 'accountAdjustStateStr', index: 'accountAdjustStateStr', width: 80, align: 'center', sortable: false}
        ],
        height: size.height - 100,
        width: size.width,
        multiselect: true,
        forceFit:true,
        pager: '#adjustmentTblPagination',
        shrinkToFit : false,
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#adjustmentTbl').jqGrid(option);
    $("#t_adjustmentTbl").append($('#adjustmentToolbar'));
}

function findAdjustments(defaultPageNo) {
    ddpAjaxCall({
        url: "findAdjustments",
        data: {
        	customerNo: escapePeculiar($.trim($('#customerNoQuery').val())),
        	customerName: escapePeculiar($.trim($('#customerNameQuery').val())),
        	accountAdjustApplyStartDate: $('#accountAdjustApplyStartDate').val(),
        	accountAdjustApplyEndDate : $('#accountAdjustApplyEndDate').val(),
        	accountAdjustType : $('#accountAdjustTypeQuery').combobox('getValue'),
        	fundType : $('#fundTypeQuery').combobox('getValue'),
        	accountAdjustAuditStartDate: $('#accountAdjustAuditStartDate').val(),
        	accountAdjustAuditEndDate : $('#accountAdjustAuditEndDate').val(),
        	accountAdjustState : $('#accountAdjustStateQuery').combobox('getValue'),
        	accountAdjustAuditUser : escapePeculiar($.trim($("#accountAdjustAuditUserQuery").val())),
        	accountAdjustApplyUser : escapePeculiar($.trim($("#accountAdjustApplyUserQuery").val())),
            page: getJqgridPage('adjustmentTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#adjustmentTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function clearAdjustmentQuery() {
	clearAllText('queryCondition');
	$('#accountAdjustTypeQuery').combobox('select', '');
	$('#fundTypeQuery').combobox('select', '');
	$('#accountAdjustStateQuery').combobox('select', '');
}

function applyAdjustment() {
	clearAdjustment();
	$("input[name='fundType']").attr("disabled","disabled"); 
	$('#accountAdjustAmount').val('');
	$('#accountAdjustReason').val('');
	$('#accountAdjustAmount').attr('disabled', false);
	$('#accountAdjustReason').attr('disabled', false);
	$('#fundTypeFund').show();
	showDialog('adjustmentDialog');
}

function adjustmentView() {
	var rowData = getSelectedRowDataFromMultiples('adjustmentTbl');
	if (rowData != null) {
		ddpAjaxCall({
			url : "viewAccountAdjustment",
			data: rowData.adjustmentId,
			successFn : function(response) {
				showDialog('viewAdjustmentDialog');
				var htmlKeys = ['accountAdjustReason','accountAdjustAuditExplain'];
				loadJsonData2ViewPageWithHtmlFormat('viewAdjustmentDialog', response, htmlKeys, setViewPage);
			}
		});
	}
}

function setViewPage(data) {
	$('#accountAdjustApplyDate').html(ddpDateFormatter(data.accountAdjustApplyDate));
	$('#accountAdjustAuditDate').html(ddpDateFormatter(data.accountAdjustAuditDate));
	$('#completeDate').html(ddpDateFormatter(data.completeDate));
}

function changeCreditV() { 	// By Mika
	var fundType = $("input[name='fundType']:checked").val();
	if(fundType == '1') {
		$('#fundAccountCode').val($('#authorizedAcctCode').val());
		$('#creditAmtTh').show();
		$('#creditAmtTd').show();
		$('#creditAmtTr').show();
	} else { // 授信
		$('#fundAccountCode').val($('#fundAcctCode').val());
		$('#creditAmtTh').hide();
		$('#creditAmtTd').hide();
		$('#creditAmtTr').hide();
	}
}
function setCreditVal() { 	// By Mika
	var fundType = $("input[name='fundType']:checked").val(); 	// 帐户类型: 0.资金帐户 1.授信帐户
	var accountAdjustType = $("input[name='accountAdjustType']:checked").val(); 	// 调帐方式: 7.正调帐 8.反调帐
	
	var creditAmt = $('#creditAmt').val(); 	// 授信
	var availableBalance = $('#availableBalance').val(); 	// 可用
	var accountAdjustAmount = $('#accountAdjustAmount').val(); 	// 调帐
	if(fundType == '0') { 	// 帐户类型为: 资金帐户 保持输入, 并不验证
		return true;
	}
	if(isNaN(accountAdjustAmount*1)) {
		$('#newAvailableBalance').val('');
		msgShow(systemWarnLabel, "请重新输入调帐金额!", 'warning');
		return false;
	}
	if(accountAdjustType == '7') {
		var stillA = creditAmt*1 - availableBalance*1 - accountAdjustAmount*1;
		if(stillA < 0 && accountAdjustAmount*1 > 0) {
			$('#newAvailableBalance').val('');
			msgShow(systemWarnLabel, "请重新输入调帐金额!", 'warning');
			return false;
		}
		if(stillA >= 0 && accountAdjustAmount*1 > 0) {
			$('#newAvailableBalance').val(Math.round((availableBalance*1 + accountAdjustAmount*1)*100)/100);
		}
	}else if(accountAdjustType == '8') {
		var stillA = availableBalance*1 - accountAdjustAmount*1;
		if(stillA < 0 && accountAdjustAmount*1 > 0) {
			$('#newAvailableBalance').val('');
			msgShow(systemWarnLabel, "请重新输入调帐金额!", 'warning');
			return false;
		}
		if(stillA >= 0 && accountAdjustAmount*1 > 0) {
			$('#newAvailableBalance').val('');
			$('#newAvailableBalance').val(Math.round((availableBalance*1 - accountAdjustAmount*1)*100)/100);
		}
	}else {
		$('#newAvailableBalance').val('');
		msgShow(systemWarnLabel, "请选择调帐方式!", 'warning');
		return false;
	}
	return true;
}

$(function() { 	// By Mika event
	changeCreditV();
	$("input[name='fundType']").click(function() {
		changeCreditV();
	});
	
	$("#accountAdjustAmount").blur(function() { 	
		var fundType = $("input[name='fundType']:checked").val();
		var accountAdjustType = $("input[name='accountAdjustType']:checked").val();
		if(fundType == "1") {
			setCreditVal();
		}
	});
	$("input[name='accountAdjustType']").click(function() { 	// 调帐方式 正调帐 反调帐
		var fundType = $("input[name='fundType']:checked").val();
		if(fundType == "1") {
			setCreditVal();
		}
	});
});

function valiDateCreditVal() { 	// Mika
	var fundType = $("input[name='fundType']:checked").val();
	if(fundType == "1") {
		return setCreditVal();
	}
	if(fundType == "0") {
		return true;
	}
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "adjustment",
	"toUrl"			: "exportExcelAdjustment"
};
var filterConStr = [
		{'name':'customerNo', 					'value': "escapePeculiar($.trim($('#customerNoQuery').val()))"				},	// eval
		{'name':'customerName',					'value': "escapePeculiar($.trim($('#customerNameQuery').val()))"			},
		{'name':'accountAdjustApplyStartDate',	'value': "$('#accountAdjustApplyStartDate').val()"							},
		{'name':'accountAdjustApplyEndDate',	'value': "$('#accountAdjustApplyEndDate').val()"							},
		{'name':'accountAdjustType',			'value': "$('#accountAdjustTypeQuery').combobox('getValue')"				},
		{'name':'fundType',						'value': "$('#fundTypeQuery').combobox('getValue')"							},
		{'name':'accountAdjustAuditStartDate',	'value': "$('#accountAdjustAuditStartDate').val()"							},
		{'name':'accountAdjustAuditEndDate',	'value': "$('#accountAdjustAuditEndDate').val()"							},
		{'name':'accountAdjustState',			'value': "$('#accountAdjustStateQuery').combobox('getValue')"				},
		{'name':'accountAdjustAuditUser',		'value': "escapePeculiar($.trim($('#accountAdjustAuditUserQuery').val()))"	},
		{'name':'accountAdjustApplyUser',		'value': "escapePeculiar($.trim($('#accountAdjustApplyUserQuery').val()))"	}
];



$(function () {
	initMainComponent();
	initDetailComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();

});
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#accountTbl',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initAccountTbl,
        toolbar: [true, 'top']
    });
}

function initDetailComponent() {
	initAccountDialog();
}

function clearAccount() {
	clearAllText('accountDialog');
}

function initAccountDialog() {
	$('#accountDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function initAccountTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findAccounts();
        },
        colNames: ['accountControllerId','客户名称', '客户号',  '客户类型', '账户类别', '资金授信号', '日消费交易单笔限额(元)', '日消费交易累计限额(元)', '日充值交易单笔限额(元)', '日充值交易累计限额(元)', '日转账交易最大次数', '日转账交易单笔限额(元)', '日转账交易累计限额(元)','授信额度'],
        colModel: [
            {name: 'accountControllerId',index:'accountControllerId',  hidden: true,frozen:true},
            {name: 'customerName', index: 'customerName', width : 180, align: 'left', sortable: false, formatter: htmlFormatter,frozen:true},
            {name: 'customerNo', index: 'customerNo',  align: 'left', sortable: false},
            {name: 'customerTypeStr', index: 'customerType',  align: 'center', sortable: false},
            {name: 'fundTypeStr', index: 'fundType',  align: 'center', sortable: false},
            {name: 'fundAccountCode', index: 'fundAccountCode',width : 210,  align: 'center', sortable: false},
            {name: 'dayConsumeSingleLimitStr', index: 'dayConsumeSingleLimitStr',  align: 'center', sortable: false},
            {name: 'dayConsumeSumLimitStr', index: 'dayConsumeSumLimitStr',  align: 'center', sortable: false},
            {name: 'dayRechargeSingleLimitStr', index: 'dayRechargeSingleLimitStr',  align: 'center', sortable: false},
            {name: 'dayRechargeSumLimitStr', index: 'dayRechargeSumLimitStr',  align: 'center', sortable: false},
            {name: 'dayTransferMax', index: 'dayTransferMax',  align: 'center', sortable: false},
            {name: 'dayTransferSingleLimitStr', index: 'dayTransferSingleLimitStr',  align: 'center', sortable: false},
            {name: 'dayTransferSumLimitStr', index: 'dayTransferSumLimitStr',  align: 'center', sortable: false},
            {name: 'creditAmtStr', index: 'creditAmtStr',  align: 'center', sortable: false}
        ],
        height: size.height - 50,
        width: size.width,
//        multiselect: true,
        pager: '#accountTblPagination',
        autowidth : true,
        shrinkToFit : false,
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#accountTbl').jqGrid(option);
    $("#t_accountTbl").append($('#accountToolbar'));
    $("#accountTbl").jqGrid('setFrozenColumns');
}

function findAccounts(defaultPageNo) {
    ddpAjaxCall({
        url: "findAccounts",
        data: {
        	custNum: escapePeculiar($.trim($('#customerNoQuery').val())),
        	custName: escapePeculiar($.trim($('#customerNameQuery').val())),
        	customerType: escapePeculiar($('#customerTypeQuery').combobox('getValue')),
            page: getJqgridPage('accountTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
            	loadJqGridPageData($('#accountTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function clearAccountQuery() {
	clearAllText('queryCondition');
}

function editAccount() {
	clearAccount();
	var selrow = $('#accountTbl').getGridParam('selrow');
	if (selrow) {
		var entity = $('#accountTbl').getRowData(selrow);
		$('#accountControllerId').val(entity.accountControllerId);
		$('#customerNo').val(entity.customerNo);
		$('#customerName').val(entity.customerName);
		$('#customerType').val(entity.customerTypeStr);
		$('#fundType').val(entity.fundTypeStr);
		$('#fundAccountCode').val(entity.fundAccountCode);
		$('#dayConsumeSingleLimit').val(entity.dayConsumeSingleLimitStr);
		$('#dayConsumeSumLimit').val(entity.dayConsumeSumLimitStr);
		$('#dayRechargeSingleLimit').val(entity.dayRechargeSingleLimitStr);
		$('#dayRechargeSumLimit').val(entity.dayRechargeSumLimitStr);
		$('#dayTransferMax').val(entity.dayTransferMax);
		$('#dayTransferSingleLimit').val(entity.dayTransferSingleLimitStr);
		$('#dayTransferSumLimit').val(entity.dayTransferSumLimitStr);
		$('#creditAmt').val(entity.creditAmtStr);
		showDialog('accountDialog');
		
		if(entity.fundTypeStr=='资金账户'){
			$('#creditAmtSpan').hide();
		}else{
			$('#creditAmtSpan').show();
		}
	}
}

function updateAccount() {
	if(isValidate('accountDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要修改资金账户风控吗？", function(r) {
			if (r) {
			  		var acctController = {
			  				accountControllerId : $('#accountControllerId').val(),
			  				dayConsumeSingleLimit : $('#dayConsumeSingleLimit').val()*100,
			  				dayConsumeSumLimit : $('#dayConsumeSumLimit').val()*100,
			  				dayRechargeSingleLimit : $('#dayRechargeSingleLimit').val()*100,
			  				dayRechargeSumLimit : $('#dayRechargeSumLimit').val()*100,
			  				dayTransferMax : $('#dayTransferMax').val(),
			  				dayTransferSingleLimit : $('#dayTransferSingleLimit').val()*100,
			  				dayTransferSumLimit : $('#dayTransferSumLimit').val()*100,
			  				fundAccountCode : $('#fundAccountCode').val(),
			  				creditAmt :$('#creditAmt').val()*100
			  			};
				ddpAjaxCall({
					url : "updateAccountRiskControllerItem",
					data : acctController,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('accountDialog');
							if(isBlank(acctController.accountControllerId)) {
								findAccounts(1);
							} else {
								findAccounts();
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

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "AccountControllerCustomerDTO", 	/*must*/// type of export
	"toUrl"			: "exportExcelAccControl" 			/*must*/// the export url
};
var filterConStr = [	// filter the result by query condition
		{'name':'customerNo', 	'value': "escapePeculiar($.trim($('#customerNoQuery').val()))"			},	// eval
		{'name':'customerName',	'value': "escapePeculiar($.trim($('#customerNameQuery').val()))"		},
		{'name':'customerType',	'value': "escapePeculiar($('#customerTypeQuery').combobox('getValue'))"		}
	];

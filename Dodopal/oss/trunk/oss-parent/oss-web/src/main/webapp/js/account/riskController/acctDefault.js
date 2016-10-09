$(function () {
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
});
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#acctDefaultTbl',
            offsetHeight: 0,
            offsetWidth: 0
        },
        callback: initAcctDefaultTbl,
        toolbar: [true, 'top']
    });
}

function initDetailComponent() {
	initAcctDefaultDialog();
}

function clearAcctDefault() {
	clearAllText('acctDefaultDialog');
}

function updateAcctDefault() {
	if(isValidate('acctDefaultDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要修改资金账户风控默认值吗？", function(r) {
			if (r) {
				var acctDefault = {
						id : $('#acctDefaultId').val(),
						dayConsumeSumLimit : $("#dayConsumeSumLimit").val()*100,
						dayConsumeSingleLimit : $("#dayConsumeSingleLimit").val()*100,
						dayRechargeSingleLimit : $('#dayRechargeSingleLimit').val()*100,
						dayRechargeSumLimit : $('#dayRechargeSumLimit').val()*100,
						dayTransferMax : $("#dayTransferMax").val(),
						dayTransferSingleLimit : $('#dayTransferSingleLimit').val()*100,
						dayTransferSumLimit : $('#dayTransferSumLimit').val()*100,
						creditAmt : $('#creditAmt').val()*100
				};
				ddpAjaxCall({
					url : "updateAccountRiskControllerDefaultItem",
					data : acctDefault,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('acctDefaultDialog');
							findAcctDefaults();
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	}
}

function editAcctDefault() {
	clearAcctDefault();
	var selrow = $('#acctDefaultTbl').getGridParam('selrow');
	if (selrow) {
		var rowData = $('#acctDefaultTbl').getRowData(selrow);
		ddpAjaxCall({
			url : "findAccountRiskControllerDefaultById",
			data : rowData.acctDefaultId,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var entity = data.responseEntity;
					$('#acctDefaultId').val(entity.acctDefaultId);
					$('#customerTypeName').html(entity.customerTypeName);
					$("#dayConsumeSumLimit").val(entity.dayConsumeSumLimit/100);
					$("#dayConsumeSingleLimit").val(entity.dayConsumeSingleLimit/100);
					$('#dayRechargeSingleLimit').val(entity.dayRechargeSingleLimit/100);
					$('#dayRechargeSumLimit').val(entity.dayRechargeSumLimit/100);
					$("#dayTransferMax").val(entity.dayTransferMax);
					$('#dayTransferSingleLimit').val(entity.dayTransferSingleLimit/100);
					$('#dayTransferSumLimit').val(entity.dayTransferSumLimit/100);
					$('#creditAmt').val(entity.creditAmt/100);
					showDialog('acctDefaultDialog');
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
		
	}
}

function initAcctDefaultDialog() {
	$('#acctDefaultDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function initAcctDefaultTbl(size) {
    var option = {
        datatype: 'local',
        colNames: ['ID', 'customerType', '类型', /*'默认授信额度阈值',*/ '日消费交易单笔限额(元)', '日消费交易累计限额(元)', '日充值交易单笔限额(元)', '日充值交易累计限额(元)', '日转账交易最大次数', '日转账交易单笔限额(元)', '日转账交易累计限额(元)','默认授信额度阈值(元)'],
        colModel: [
            {name: 'acctDefaultId', hidden: true},
            {name: 'customerType', hidden: true},
//            {name 'creditAmt' index ''},
            {name: 'customerTypeName', index: 'customerTypeName', width: 80, align: 'left', sortable: false},
            {name: 'dayConsumeSingleLimitStr', index: 'dayConsumeSingleLimitStr', width: 100, align: 'center', sortable: false},
            {name: 'dayConsumeSumLimitStr', index: 'dayConsumeSumLimitStr', width: 100, align: 'center', sortable: false},
            {name: 'dayRechargeSingleLimitStr', index: 'dayRechargeSingleLimitStr', width: 100, align: 'center', sortable: false},
            {name: 'dayRechargeSumLimitStr', index: 'dayRechargeSumLimitStr', width: 100, align: 'center', sortable: false},
            {name: 'dayTransferMax', index: 'dayTransferMax', width: 100, align: 'center', sortable: false},
            {name: 'dayTransferSingleLimitStr', index: 'dayTransferSingleLimitStr', width: 100, align: 'center', sortable: false},
            {name: 'dayTransferSumLimitStr', index: 'dayTransferSumLimitStr', width: 100, align: 'center', sortable: false},
            {name: 'creditAmtStr', index: 'creditAmtStr', width: 100, align: 'center', sortable: false}
        ],
        height: size.height+10,
        width: size.width,
//        multiselect: true,
        forceFit:true,
        pager: '#acctDefaultTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#acctDefaultTbl').jqGrid(option);
    $("#t_acctDefaultTbl").append($('#acctDefaultToolbar'));
    
    findAcctDefaults();
}

function findAcctDefaults(defaultPageNo) {
    ddpAjaxCall({
        url: "findAccountRiskControllerDefaultItemListByPage",
        successFn: function (data) {
            if (data.code == "000000") {
            	loadJqGridData($('#acctDefaultTbl'), data.responseEntity, 1000);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

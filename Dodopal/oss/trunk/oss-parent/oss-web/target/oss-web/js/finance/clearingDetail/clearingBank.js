$(function() {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

var expConfInfo = {
		"btnId"			: "btnSelExcCol", 				/*must*/// the id of export btn in ftl 
		"typeSelStr"	: 	"BankClearingBasic", 		/*must*/// type of export
		"toUrl"			: "exportExcelBank", 			/*must*/// the export url
//		"customShow"    : "true"
	};
	var filterConStr = [	// filter the result by query condition
		{'name':'clearingFlag', 	'value': "escapePeculiar($.trim($('#clearingFlag').combobox('getValue')))"			},	// eval
		{'name':'clearingDayFrom',	'value': "escapePeculiar($.trim($('#clearingDayFrom').val()))"		},
		{'name':'clearingDayTo',	'value': "escapePeculiar($.trim($('#clearingDayTo').val()))"		}
	];

// 初始化
function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#clearingTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initClearingTbl,
		toolbar : [ true, 'top' ]
	});
}

// 银行清分列表
function initClearingTbl(size) {
	var option = {
		datatype : function(pdata) {
			queryClearingData();
		},
		colNames : [ 'ID', '订单交易号', '业务类型','支付类型', '支付方式', '支付方式名称', 'DDP与银行的手续费率(‰)', 'DDP支付给银行的手续费(元)', 'DDP从银行应收业务费用(元)',
						'DDP从银行实收业务费用(元)', '与银行清分状态', '与银行清分时间',
						'是否圈存订单',  '订单来源' ,'订单时间'],
		colModel : [ {
			name : 'id',
			hidden : true
			,frozen:true
		}, {
			name : 'orderNo',
			index : 'orderNo',
			width : 200,
			align : 'left',
			sortable : false
			,frozen:true
		},  {
			name : 'businessType',
			index : 'businessType',
			width : 90,
			align : 'left',
			sortable : false
		},{
			name : 'payType',
			index : 'payType',
			width : 90,
			align : 'left',
			sortable : false
		},{
			name : 'payWay',
			index : 'payWay',
			width : 90,
			align : 'left',
			sortable : false,
			hidden : true
		},  {
			name : 'payWayName',
			index : 'payWayName',
			width : 90,
			align : 'left',
			sortable : false
		}, {
			name : 'ddpBankRate',
			index : 'ddpBankRate',
			width : 160,
			align : 'left',
			sortable : false
		}, {
			name : 'ddpToBankFee',
			index : 'ddpToBankFee',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'ddpFromBankShouldFee',
			index : 'ddpFromBankShouldFee',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'ddpFromBankRealFee',
			index : 'ddpFromBankRealFee',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'bankClearingFlag',
			index : 'bankClearingFlag',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'bankClearingTime',
			index : 'bankClearingTime',
			width : 140,
			align : 'left',
			sortable : false
		} , {
			name : 'orderCircle',
			index : 'orderCircle',
			width : 90,
			align : 'left',
			sortable : false
		}, {
			name : 'orderFrom',
			index : 'orderFrom',
			width : 90,
			align : 'left',
			sortable : false
		},{
			name : 'orderDate',
			index : 'orderDate',
			width : 140,
			align : 'left',
			sortable : false
		}],
		height : size.height - 50,
		width : size.width,
		autowidth : true,
		shrinkToFit : false,
		pager : '#clearingTblPagination',
		toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#clearingTbl').jqGrid(option);
	$("#t_clearingTbl").append($('#clearingTblToolbar'));
	 $("#clearingTbl").jqGrid('setFrozenColumns');
}

function queryClearingData(defaultPageNo) {
	var obj = {
		clearingDayFrom : $('#clearingDayFrom').val(),
		clearingDayTo : $('#clearingDayTo').val(),
		clearingFlag : $('#clearingFlag').combobox('getValue'),
		page : getJqgridPage('clearingTbl', defaultPageNo)
	};
	ddpAjaxCall({
		url : "findBankClearingByPage",
		data : obj,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#clearingTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

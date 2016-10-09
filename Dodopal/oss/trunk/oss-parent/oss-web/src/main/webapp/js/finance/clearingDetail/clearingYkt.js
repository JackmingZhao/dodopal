$(function() {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

var expConfInfo = {
		"btnId"			: "btnSelExcCol", 				/*must*/// the id of export btn in ftl 
		"typeSelStr"	: 	"YktClearingBasic", 		/*must*/// type of export
		"toUrl"			: "exportExcelYkt" 			/*must*/// the export url
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

// 商户清分列表
function initClearingTbl(size) {
	var option = {
		datatype : function(pdata) {
			queryClearingData();
		},
		colNames : [ 'ID', '订单交易号', '业务类型', '支付类型', '支付方式','支付方式名称', '供应商', '供应商名称', 'DDP应支付供应商本金(元)', 'DDP实际支付供应商本金(元)',
						'DDP与供应商之间的费率(‰)', '供应商应支付DDP返点(元)', '供应商实际支付DDP返点(元)', '与供应商清分状态',
						'与供应商清分时间' ,'是否圈存订单', '订单来源', '订单时间'
				],
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
		}, {
			name : 'businessType',
			index : 'businessType',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'payType',
			index : 'payType',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'payWay',
			index : 'payWay',
			width : 80,
			align : 'left',
			sortable : false,
			hidden : true
		},{
			name : 'payWayName',
			index : 'payWayName',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'supplierCode',
			hidden : true
		}, {
			name : 'supplierName',
			index : 'supplierName',
			width : 140,
			align : 'left',
			sortable : false
		}, {
			name : 'ddpToSupplierShouldAmount',
			index : 'ddpToSupplierShouldAmount',
			width : 160,
			align : 'left',
			sortable : false
		}, {
			name : 'ddpToSupplierRealAmount',
			index : 'ddpToSupplierRealAmount',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'ddpSupplierRate',
			index : 'ddpSupplierRate',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'supplierToDdpShouldRebate',
			index : 'supplierToDdpShouldRebate',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'supplierToDdpRealRebate',
			index : 'supplierToDdpRealRebate',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'supplierClearingFlag',
			index : 'supplierClearingFlag',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'supplierClearingTime',
			index : 'supplierClearingTime',
			width : 140,
			align : 'left',
			sortable : false
		} , {
			name : 'orderCircle',
			index : 'orderCircle',
			width : 80,
			align : 'left',
			sortable : false
		},   {
			name : 'orderFrom',
			index : 'orderFrom',
			width : 80,
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
		url : "findYktClearingByPage",
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

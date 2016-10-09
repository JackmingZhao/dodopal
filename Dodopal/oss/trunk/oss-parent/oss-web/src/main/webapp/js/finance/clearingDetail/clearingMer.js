$(function() {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	
//	$('body').data('orderNo','订单交易号');
//	$('body').data('orderDate','订单时间');
//	$('body').data('businessType','业务类型');
//	$('body').data('payType','支付类型');
//	$('body').data('payWay','支付方式');
//	$('body').data('payWayName','支付方式名称');
//	$('body').data('orderFrom','订单来源');
//	$('body').data('orderCircle','是否圈存订单');
//	$('body').data('customerNo','商户编号');
//	$('body').data('customerName','商户名称');
//	$('body').data('customerType','商户类型');
//	$('body').data('customerRateType','商户业务费率类型');
//	$('body').data('customerRate','商户业务费率');
//	$('body').data('custoerRealPayAmount','商户实际支付金额(元)');
//	$('body').data('customerShouldProfit','商户应得分润(元)');
//	$('body').data('customerRealProfit','商户实际分润(元)');
//	$('body').data('customerAccountShouldAmount','商户账户应加值(元)');
//	$('body').data('customerAccountRealAmount','商户账户实际加值(元)');
//	$('body').data('customerClearingFlag','与客户清分状态');
//	$('body').data('customerClearingTime','与客户清分时间');
//	$('body').data('ddpGetMerchantPayFee','DDP应收商户支付方式服务费(元)');
//	$('body').data('ddpToCustomerRealFee','DDP实际转给商户业务费用(元)');
});

var expConfInfo = {
		"btnId"			: "btnSelExcCol", 				/*must*/// the id of export btn in ftl 
		"typeSelStr"	: 	"MerClearingBasic", 		/*must*/// type of export
		"toUrl"			: "exportExcelMer", 			/*must*/// the export url
//		"customShow"    : "true"
	};
	var filterConStr = [	// filter the result by query condition
		{'name':'clearingFlag', 	'value': "escapePeculiar($.trim($('#clearingFlag').combobox('getValue')))"		},	// eval
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
		colNames : [ 'ID', '订单交易号', '商户名称', '商户类型', '业务类型', 
		             '支付类型', '支付方式', '支付方式名称', '商户业务费率类型', '商户业务费率', 
		             '商户实际支付金额(元)', '商户应得分润(元)', '商户实际分润(元)', '商户账户应加值(元)', '商户账户实际加值(元)',
		             'DDP应收商户支付方式服务费(元)', 'DDP实际转给商户业务费用(元)', '与客户清分状态', '与客户清分时间', '是否圈存订单',
		             '订单来源', '商户编号','订单时间' ],
		colModel : [ { name : 'id', hidden : true, frozen:true },
		             { name : 'orderNo', index : 'orderNo', width : 200, align : 'left', sortable : false, frozen:true },
		             { name : 'customerName', index : 'customerName', width : 140, align : 'left', sortable : false },
		             { name : 'customerType', index : 'customerType', width : 140, align : 'left', sortable : false },
		             { name : 'businessType', index : 'businessType', width : 80, align : 'left', sortable : false },
		             
		             { name : 'payType', index : 'payType', width : 80, align : 'left', sortable : false },
		             { name : 'payWay', index : 'payWay', width : 80, align : 'left', sortable : false, hidden : true },
		             { name : 'payWayName', index : 'payWayName', width : 80, align : 'left', sortable : false },
		             { name : 'customerRateType', index : 'customerRateType', width : 140, align : 'left', sortable : false }, 
		             { name : 'customerRate', index : 'customerRate', width : 110, align : 'left', sortable : false }, 

		             { name : 'custoerRealPayAmount', index : 'custoerRealPayAmount', width : 160, align : 'left', sortable : false }, 
		             { name : 'customerShouldProfit', index : 'customerShouldProfit', width : 160, align : 'left', sortable : false },
		             { name : 'customerRealProfit', index : 'customerRealProfit', width : 160, align : 'left', sortable : false },
		             { name : 'customerAccountShouldAmount', index : 'customerAccountShouldAmount', width : 160, align : 'left', sortable : false },
		             { name : 'customerAccountRealAmount', index : 'customerAccountRealAmount', width : 160, align : 'left', sortable : false },

		             { name : 'ddpGetMerchantPayFee', index : 'ddpGetMerchantPayFee', width : 200, align : 'left', sortable : false }, 
		             { name : 'ddpToCustomerRealFee', index : 'ddpToCustomerRealFee', width : 180, align : 'left', sortable : false },
		             { name : 'customerClearingFlag', index : 'customerClearingFlag', width : 120, align : 'left', sortable : false }, 
		             { name : 'customerClearingTime', index : 'customerClearingTime', width : 140, align : 'left', sortable : false },
		             { name : 'orderCircle', index : 'orderCircle', width : 80, align : 'left', sortable : false },
		             
		             { name : 'orderFrom', index : 'orderFrom', width : 80, align : 'left', sortable : false },
		             { name : 'customerNo', hidden : true },
		             { name : 'orderDate', index : 'orderDate', width : 140, align : 'left', sortable : false }
		           ],
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
		url : "findMerClearingByPage",
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

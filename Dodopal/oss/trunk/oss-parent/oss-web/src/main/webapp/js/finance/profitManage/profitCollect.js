$(function() {
	initMainComponent();
	initDetailComponent();
	//findData();
//	initProfitDetailsSearchModel();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});
function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#dataTbl',
			offsetHeight :50,
			offsetWidth : 0
		},
		callback : initDataTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'dataDialog' ]
	});
}
function initDetailComponent() {
	initDataDialog();
}

function initDataTbl(size) {
	var option = {
			datatype : function (pdata) {
				findData();
		    },
		colNames : [ 'Id','汇总日期', 'collectDate', '客户名称', '交易类型', 
		             '交易笔数', "交易金额(元)", '分润(元)', '汇总时间', "customerCode"],
		colModel : [
		    { name : 'id', index : 'id', width : 100, align : 'left', hidden : true },
		  	{ name : 'collectDate', index : 'collectDate', width : 100, align : 'left', sortable : false,formatter:ddpDateForMatterForLine},
		    { name : 'collectDate', index : 'myCollectDate', width : 100, align : 'left', hidden : true},
			{ name : 'customerName', index : 'customerName', width : 100, align : 'left', sortable : false },
			{ name : 'bussinessTypeView', index : 'bussinessTypeView', width : 100, align : 'left', sortable : false },

			{ name : 'tradeCount', index : 'tradeCount', width : 100, align : 'left', sortable : false },
			{ name : 'tradeAmount', index : 'tradeAmount', width : 100, align : 'left', sortable : false, formatter: ddpMoneyFenToYuan},
			{ name : 'profit', index : 'profit', width : 100, align : 'left', sortable : false , formatter: ddpMoneyFenToYuan},
			{ name : 'collectTime', index : 'collectTime', width : 100, align : 'left', sortable : false ,formatter: cellDateFormatter},
			{ name : 'customerCode', index : 'customerCode', width : 100, align : 'left', hidden : true}
		],
		//caption : "用户列表",
		//sortname : 'name',
		height : size.height -50,
		width : size.width,
		autowidth : true,
		pager : '#dataTblPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#dataTbl').jqGrid(option);
	$("#t_dataTbl").append($('#dataTblToolbar'));
}
function initDataDialog() {
	$('#dataDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function showDataDialog() {
	
    var selrow = $("#dataTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#dataTbl').getRowData(selrow);
		initProfitDetailsSearchModel();
		findProfitDetails(1);
		showDialog('profitDetailsSearchDialog');
		//window.location.href ="viewMerUser.htm?userId="+rowData.userId;
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function hideDataDialog() {
	clearForm();
	clearAllText('editMerchantUser');
	$('#dataDialog').hide().dialog('close');
}
function findData(defaultPageNo) {
	var query = {
		page : getJqgridPage('dataTbl', defaultPageNo),
		merName :  $.trim($('#merCode').val()),
		startDate : $.trim($('#startDate').val()),
		endDate : $.trim($('#endDate').val()),
		collectDate:""
	}
	
	ddpAjaxCall({
		url : "findProfitCollectListByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#dataTbl'), data.responseEntity);
			}else{
				msgShow(systemWarnLabel, data.message, 'warning');
			}	
		}
	});
}
/**
 * 分润明细开始
 */
function initProfitDetailsSearchModel() {
	initProfitDetailsSearchDialog();
	initProfitDetailsListTbl();
}


function initProfitDetailsSearchDialog() {
	$('#profitDetailsSearchDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 1000,
		height :  400
	});
}
function initProfitDetailsListTbl() {
	var option = {
		datatype : function(pdata) {
			findProfitDetails();
		},
		colNames : ['订单号', '订单时间', '客户编码', '客户名称', '交易类型',"商户类型","来源","是否圈存","商户业务费率类型","customerRateType",
		            "商户业务费率","订单金额(元)","商户应得分润(元)","商户实际分润(元)",
		            "下级商户编号","下级商户名称","下级商户应得分润(元)","与客户结算时间","分润日期"],
		colModel : [
{ name : 'orderNo', index : 'orderNo', width : 180, align : 'left', sortable : false },
{ name : 'orderTime', index : 'orderTime', width : 140, align : 'left', sortable : false ,formatter: cellDateFormatter},
{ name : 'customerCode', index : 'customerCode', width : 130, align : 'left', sortable : false },
{ name : 'customerName', index : 'customerName', width : 100, align : 'left', sortable : false },
{ name : 'bussinessTypeView', index : 'bussinessType', width : 80, align : 'left', sortable : false },
{ name : 'customerTypeView', index : 'customerType', width : 100, align : 'left', sortable : false },
{ name : 'source', index : 'source', width : 80, align : 'left', sortable : false ,formatter: sourceFormatter},
{ name : 'iscircleView', index : 'iscircle', width : 80, align : 'left', sortable : false },
{ name : 'customerRateTypeView', index : 'customerRateType', width : 100, align : 'left', sortable : false },
{ name : 'customerRateType', index : 'customerRateType', width : 100, align : 'left', hidden : true },
{ name : 'customerRate', index : 'customerRate', width : 100, align : 'left', sortable : false ,formatter : function(cellval, el, rowData) {
	if(rowData.customerRateType=="1"){
		if (cellval != null && $.trim(cellval) != '') {
			return cellval/100;
		} else {
			return '';
		}
	}else{
		return cellval;
	}
}},
{ name : 'orderAmount', index : 'orderAmount', width : 100, align : 'left', sortable : false ,formatter: ddpMoneyFenToYuan},
{ name : 'customerShouldProfit', index : 'customerShouldProfit', width : 100, align : 'left', sortable : false  ,formatter: ddpMoneyFenToYuan},
{ name : 'customerRealProfit', index : 'customerRealProfit', width : 100, align : 'left', sortable : false  ,formatter: ddpMoneyFenToYuan},
{ name : 'subCustomerCode', index : 'subCustomerCode', width : 180, align : 'left', sortable : false },
{ name : 'subCustomerName', index : 'subCustomerName', width : 160, align : 'left', sortable : false },
{ name : 'subCustomerShouldProfit', index : 'subCustomerShouldProfit', width : 160, align : 'left', sortable : false  ,formatter: ddpMoneyFenToYuan},
{ name : 'customerSettlementTime', index : 'customerSettlementTime', width : 100, align : 'left', sortable : false,formatter: cellDateFormatter },
{ name : 'profitDate', index : 'profitDate', width : 100, align : 'left', sortable : false ,formatter:ddpDateForMatterForLine}

		            ],
		width : $('#profitDetailsSearchDialog').width() - 2,
		height : $('#profitDetailsSearchDialog').height() - 50,
		shrinkToFit : false,
		pager : '#profitDetailsTblPagination'
//		ondblClickRow : function(rowid, iRow, iCol, e) {
//			if (rowid) {
//				var merchant = $('#profitDetailsTbl').getRowData(rowid);
//				setSelectedMerchant(merchant);
//				hideDialog('merchantSearchDialog');
//			}
//		}
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#profitDetailsTbl').jqGrid(option);
	$("#t_profitDetailsTbl").append($('#merchantListTblToolbar'));
}

function findProfitDetails(defaultPageNo) {
	var selrow =  $("#dataTbl").getGridParam('selrow');
	var rowData = $('#dataTbl').getRowData(selrow);
	var query = {
			page : getJqgridPage('profitDetailsTbl', defaultPageNo),
			merCode : rowData.customerCode,
			collectDate: rowData.collectDate
		}
	ddpAjaxCall({
		url : "findProfitDetailsListByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#profitDetailsTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

/**
 * 分润明细结束
 */

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "profitInfo",
	"toUrl"			: "exportExcelInfo"
};
var filterConStr = [
	{'name':'merName', 		'value': "$.trim($('#merCode').val())"			},	// eval
	{'name':'startDate',	'value': "$.trim($('#startDate').val())"		},
	{'name':'endDate',		'value': "$.trim($('#endDate').val())"			}
];

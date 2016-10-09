$(function() {
	initMainComponent();
	initDetailComponent();
	findPayFlow();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#payFlowTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initpayTypeTbl,
		toolbar : [ true, 'top' ],
	});
}

function initDetailComponent() {
}

function initpayTypeTbl(size) {
	var option = {
		datatype : function (pdata) {
			findPayFlow();
	    },
		colNames : [ '交易流水号','支付流水号','支付类型','支付方式名称','支付状态','支付金额(元)','支付服务费率(‰)','支付服务费(元)','支付时间'],
		colModel : [ 
		             { name : 'tranCode', index : 'tranCode', width : 210, align : 'left',sortable : false,frozen:true}, 
		             { name : 'id', index : 'id', width : 170, align : 'left',sortable : false },
		             { name : 'payType', index : 'payType', width : 80, align : 'left',sortable : false}, 
		             { name : 'payWayName', index : 'payWayName', width : 100, align : 'left',sortable : false },
		             { name : 'payStatus', index : 'payStatus', width : 80, align : 'left',sortable : false}, 
		             { name : 'payMoney', index : 'payMoney', width : 80, align : 'right',sortable : false},
		             { name : 'payServiceRate', index : 'payServiceRate', width : 120, align : 'right',sortable : false},
		             { name : 'payServiceFee', index : 'payServiceFee', width : 100, align : 'right',sortable : false},
		             { name : 'createDate', index : 'createDate', width : 140, align : 'left',sortable : false ,formatter: cellDateFormatter}
		            ],
		height : size.height - 50,
		width : size.width,
		multiselect: false,
		shrinkToFit : false,
		pager : '#payFlowTblPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#payFlowTbl').jqGrid(option);
	 $("#payFlowTbl").jqGrid('setFrozenColumns'); 
	$("#t_payFlowTbl").append($('#payFlowTblToolbar'));
}
function findPayFlow(defaultPageNo) {
	ddpAjaxCall({
		url : "findPayFlow",
		data : {
			payWayName : $('#payWayName').val(),
			payStatus : $('#payStatus').combobox('getValue'),
			tranCode : $('#tranCode').val(),
			payType : $('#payType').combobox('getValue'),
			page : getJqgridPage('payFlowTbl', defaultPageNo)
		},
		successFn : function(data) {
			console.log("=================",data);
			if (data.code == "000000") {
				loadJqGridPageData($('#payFlowTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}
//查看详情
function viewPayFlow() {
	var selarrrow = $("#payFlowTbl").getGridParam('selarrrow');
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#payFlowTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#payFlowTbl').getRowData(selrow);
		ddpAjaxCall({
			url : "findPayFlowById",
			data : rowData.id,
			successFn : loadViewPayFlow
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function loadViewPayFlow(data){
	if(data.code=="000000"){
		showViewTraFlow();
		var paymentBean = data.responseEntity;
		$('#view_id').html(paymentBean.id);
		$('#view_tranCode').html(paymentBean.tranCode);
		$('#view_payStatus').html(paymentBean.payStatus);
		$('#view_payType').html(paymentBean.payType);
		$('#view_payWayKind').html(paymentBean.payWayKind);
		$('#view_payWayName').html(paymentBean.payWayName);
		$('#view_payServiceRate').html(paymentBean.payServiceRate+"‰");
		$('#view_payServiceFee').html(paymentBean.payServiceFee+"元");
		$('#view_payMoney').html(paymentBean.payMoney+"元");
		$('#view_pageCallbackStatus').html(paymentBean.pageCallbackStatus);
		$('#view_pageCallbackDate').html(ddpDateFormatter(paymentBean.pageCallbackDate));
		$('#view_serviceNoticeStatus').html(paymentBean.serviceNoticeStatus);
		$('#view_serviceNoticeDate').html(ddpDateFormatter(paymentBean.serviceNoticeDate));
		$('#view_sendCiphertext').html(paymentBean.sendCiphertext);
		$('#view_acceptCiphertext').html(paymentBean.acceptCiphertext);
		$('#view_createDate').html(ddpDateFormatter(paymentBean.createDate));
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function showViewTraFlow(){
	$('#viewPayFlowDialog').show().dialog('open');
}

function closeViewTraFlow(){
	$('#viewPayFlowDialog').show().dialog('close');
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PayFlowBean", 	/*must*/// type of export
	"toUrl"			: "exportPayFlow" 			/*must*/// the export url
};
var filterConStr = [	// filter the result by query condition
		{'name':'payWayName',	'value': "escapePeculiar($.trim($('#payWayName').val()))"},
		{'name':'payStatus', 	'value': "escapePeculiar($.trim($('#payStatus').combobox('getValue')))"			},
		{'name':'tranCode',	'value': "escapePeculiar($.trim($('#tranCode').val()))"},// eval
		{'name':'payType',	'value': "escapePeculiar($('#payType').combobox('getValue'))"		}
	];
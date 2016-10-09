$(function () {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	
//	$('body').data('orderDate','订单时间');
//	$('body').data('orderNo','订单号');
//	$('body').data('orderAmount','订单金额(元)');
//	$('body').data('proOrderStates','订单状态');
//	$('body').data('proInnerStates','订单内部状态');
//	$('body').data('customerName','客户名称');
//	$('body').data("supplierName","通卡名称");
});

var expConfInfo = {
		"btnId"			: "btnSelExcCol", 				/*must*/// the id of export btn in ftl 
		"typeSelStr"	: 	"CardRechargeDTO", 		/*must*/// type of export
		"toUrl"			: "exportExcelCardRecharge", 			/*must*/// the export url
//		"customShow"    : "true"
	};
	var filterConStr = [	// filter the result by query condition
		{'name':'sDate', 	'value': "escapePeculiar($.trim($('#sDate').val()))"			},	// eval
		{'name':'eDate',	'value': "escapePeculiar($.trim($('#eDate').val()))"		},
		{'name':'customerName',	'value': "escapePeculiar($.trim($('#customerName').val()))"		},
		{'name':'orderNo',	'value': "escapePeculiar($.trim($('#orderNo').val()))"		}
	];


function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#cardRechargeTbl',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initCardRechargeTbl,
        toolbar: [true, 'top'],
        dialogs: ['viewCardRechargeDialog']
    });
}

function initCardRechargeTbl(size) {
	var option = {
	        datatype: function (pdata) {
	        	findCardRecharge();
	        },
            colNames: [
                '', '订单号', '客户名称', '通卡公司', '订单金额(元)', 
                '订单状态', '订单内部状态', '订单时间'
            ],
            colModel: [
                {name: 'id', index:'id', hidden: true},
                {name: 'orderNo', index: 'orderNo', width: 200, align: 'left', sortable: false},
                {name: 'customerName', index: 'customerName', width: 140, align: 'left', sortable: false},
                {name: 'supplierName', index: 'supplierName', width: 140, align: 'left', sortable: false},
                {name: 'orderAmountView', index: 'orderAmountView', width: 160, align: 'left', sortable: false},
                
                {name: 'proOrderStatesView', index: 'proOrderStatesView', width: 100, align: 'left', sortable: false},
                {name: 'proInnerStatesView', index: 'proInnerStatesView', width: 100, align: 'left', sortable: false},
                {name: 'orderDate', index: 'orderDate', width: 140, align: 'left', sortable: false}
            ],
            height: size.height - 50,
            width: size.width,
    		autowidth : true,
    		shrinkToFit : false,
            pager: '#cardRechargeTblPagination',
            toolbar: [true, "top"]
        };
		option = $.extend(option, jqgrid_server_opts);
	    $('#cardRechargeTbl').jqGrid(option);
	    $("#t_cardRechargeTbl").append($('#cardRechargeTblToolbar'));
}


function findCardRecharge(defaultPageNo) {
	var query= {
        	customerName: escapePeculiar($.trim($('#customerName').val())),
        	orderNo: $.trim($('#orderNo').val()),
        	sDate: $('#sDate').val(),
        	eDate: $('#eDate').val(),
        	page: getJqgridPage('cardRechargeTbl', defaultPageNo)
        };
	ddpAjaxCall({
		url: "findCardRechargeByPage",
        data: query,
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#cardRechargeTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function clearIcdcAllText(){
	clearAllText('queryCondition');
}

function viewCardRechargeDetails() {
	var selarrrow = $('#cardRechargeTbl').getGridParam('selrow');
	if (selarrrow) {
		var rowData = $('#cardRechargeTbl').getRowData(selarrrow);
		ddpAjaxCall({
			url : "findCardRecharge",
			 data: rowData.id,
			 successFn : loadViewCardRechargeDetail
		});
	} else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    } 
}
function loadViewCardRechargeDetail(data) {
	if(data.code=="000000"){
		$('#viewCardRechargeDialog').show().dialog('open');
		var cardRecharge = data.responseEntity;
		$('#orderNoView').html(cardRecharge.orderNo);
		$("#orderDateView").html(cardRecharge.orderDate);
		$("#customerNoView").html(cardRecharge.customerNo);
		$("#customerNameView").html(cardRecharge.customerName);
		$("#customerTypeView").html(cardRecharge.customerTypeView);
		$("#orderFromView").html(cardRecharge.orderFromView);
		$("#orderAmountView").html(cardRecharge.orderAmountView);
		$("#customerRateTypeView").html(cardRecharge.customerRateTypeView);
		$("#customerRateView").html(cardRecharge.customerRateView);
		$("#custoerRealPayAmountView").html(cardRecharge.custoerRealPayAmountView);
		$("#customerShouldProfitView").html(cardRecharge.customerShouldProfitView);
		$("#ddpGetMerchantPayFeeView").html(cardRecharge.ddpGetMerchantPayFeeView);
		$("#serviceRateTypeView").html(cardRecharge.serviceRateTypeView);
		$("#serviceRateView").html(cardRecharge.serviceRateView);
		$("#payGatewayView").html(cardRecharge.payGatewayView);
		$("#payTypeView").html(cardRecharge.payTypeView);
		$("#payWayNameView").html(cardRecharge.payWayName);
		$("#ddpSupplierRateView").html(cardRecharge.ddpSupplierRateView);
		$("#supplierToDdpShouldRebateView").html(cardRecharge.supplierToDdpShouldRebateView);
		$("#supplierToDdpRealRebateView").html(cardRecharge.supplierToDdpRealRebateView);
		$("#ddpBankRateView").html(cardRecharge.ddpBankRate+"‰");
		$("#ddpToBankFeeView").html(cardRecharge.ddpToBankFeeView);
		
		
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


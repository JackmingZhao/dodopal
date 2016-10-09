$(function () {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
//	
//	$('body').data('orderDate','订单时间');
//	$('body').data('orderNo','订单号');
//	$('body').data('orderAmount','收单金额(元)');
//	$('body').data('tranOutStatus','订单状态');
//	$('body').data('tranInStatus','订单内部状态');
//	$('body').data('customerName','客户名称');
//	$('body').data('supplierName','通卡公司');
});

var expConfInfo = {
		"btnId"			: "btnSelExcCol", 				/*must*/// the id of export btn in ftl 
		"typeSelStr"	: 	"CardConsumeClearingDTO", 		/*must*/// type of export
		"toUrl"			: "exportCardConsumeClearingExcel"//, 			/*must*/// the export url
//		"customShow"    : "true"
	};
	var filterConStr = [	// filter the result by query condition
		{'name':'orderDateStartQuery', 	'value': "escapePeculiar($.trim($('#orderDateStartQuery').val()))"			},	// eval
		{'name':'orderDateEndQuery',	'value': "escapePeculiar($.trim($('#orderDateEndQuery').val()))"		},
		{'name':'customerName',	'value': "escapePeculiar($.trim($('#customerName').val()))"		},
		{'name':'orderNo',	'value': "escapePeculiar($.trim($('#orderNo').val()))"		}
	];


function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#icdcOrderTbl',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initIcdcOrderTbl,
        toolbar: [true, 'top'],
        dialogs: ['viewIcdcOrderDialog']
    });
}

function initIcdcOrderTbl(size) {
	var option = {
	        datatype: function (pdata) {
	        	findProductOrder();
	        },
            colNames: [
                'ID', '订单号', '客户名称', '通卡公司', '收单金额(元)', '订单状态', 
                '订单内部状态', '订单时间'
            ],
            colModel: [
                {name: 'id', hidden: true},
                {name: 'orderNo', index: 'orderNo', width: 200, align: 'left', sortable: false},
                {name: 'customerName', index: 'customerName', width: 140, align: 'left', sortable: false},
                {name: 'supplierName', index: 'supplierName', width: 140, align: 'left', sortable: false},
                {name: 'orderAmount', index: 'orderAmount', width: 160, align: 'left', sortable: false},
                
                {name: 'tranOutStatusView', index: 'tranOutStatusView', width: 100, align: 'left', sortable: false},
                {name: 'tranInStatusView', index: 'tranInStatusView', width: 100, align: 'left', sortable: false},
                {name: 'orderDate', index: 'orderDate', width: 140, align: 'left', sortable: false, formatter: cellDateFormatter}
            ],
            height: size.height - 50,
            width: size.width,
            // forceFit:true,
    		autowidth : true,
            shrinkToFit : false,
            pager: '#icdcOrderTblPagination',
            toolbar: [true, "top"]
        };
		option = $.extend(option, jqgrid_server_opts);
	    $('#icdcOrderTbl').jqGrid(option);
	    $("#t_icdcOrderTbl").append($('#icdcOrderTblToolbar'));
}


function findProductOrder(defaultPageNo) {
	var query= {
        	customerName: escapePeculiar($.trim($('#customerName').val())),
        	orderNo: $.trim($('#orderNo').val()),
        	clearingStartDate: $('#orderDateStartQuery').val(),
        	clearingEndDate: $('#orderDateEndQuery').val(),
        	page: getJqgridPage('icdcOrderTbl', defaultPageNo)
    };
	ddpAjaxCall({
		url: "queryCardConsumeClearingPage",
        data: query,
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcOrderTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function clearIcdcAllText(){
	clearAllText('queryCondition');
}

function viewProductOrderDetails() {
	var selarrrow = $('#icdcOrderTbl').getGridParam('selrow');
	if (selarrrow) {
		var rowData = $('#icdcOrderTbl').getRowData(selarrrow);
		ddpAjaxCall({
			url : "queryCardConsumeClearingDetails",
			 data: rowData.id,
			 successFn : loadViewClearingDetail
		});
	} else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}
function loadViewClearingDetail(data) {
	if(data.code=="000000"){
		$('#viewIcdcOrderDialog').show().dialog('open');
		var yktBean = data.responseEntity;
		$('#orderNoView').html(yktBean.orderNo);
		$('#orderDateView').html(formatDate(yktBean.orderDate,'yyyy-MM-dd HH:mm:ss'));
		$('#customerNoView').html(yktBean.customerNo);
		$('#customerNameView').html(yktBean.customerName);
		$('#customerTypeView').html(yktBean.customerTypeView);
		$('#sourceView').html(htmlTagFormat(yktBean.orderFromView));
		$('#tradeAmtView').html(yktBean.orderAmount+"元");
		$('#customerRealPayView').html(yktBean.dDPToCustomerRealFee+"元");
		$('#DDPGetMerchantPayFeeView').html(yktBean.dDPGetMerchantPayFee+"元");
		$('#serviceRateTypeView').html(yktBean.serviceRateTypeView);
		$('#serviceFeeView').html(yktBean.serviceFee);
		$('#payGateWay').html(yktBean.payGateWayView);
		$('#payTypeView').html(yktBean.payTypeView);
		$('#payWayView').html(yktBean.payWayName);
		$('#bankFeeView').html(yktBean.dDPToBankFee+"元");
		$('#bankRateView').html(yktBean.dDPBankRate);
		$('#DDPFromBankShouldFeeView').html(yktBean.dDPFromBankShouldFee+"元");
		$('#DDPFromBankRealFeeView').html(yktBean.dDPFromBankRealFee+"元");
		
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


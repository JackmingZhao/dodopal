
var expConfInfo = {
        "btnId"         : "btnSelExcCol",                  
        "typeSelStr"    : "ProductOrderForExport",             
        "toUrl"         : "exportIcdcOrder",
        "parDiaHeight"  : "300"
};
var filterConStr = [
    {'name':'proOrderNumQuery',   'value': "escapePeculiar($.trim($('#proOrderNumQuery').val()))"},
    {'name':'merOrderNumQuery',   'value': "escapePeculiar($.trim($('#merOrderNumQuery').val()))"},
    {'name':'orderDateStartQuery','value': "escapePeculiar($('#orderDateStartQuery').val())"},
    {'name':'orderDateEndQuery',  'value': "escapePeculiar($('#orderDateEndQuery').val())"},
    {'name':'merNameQuery',       'value': "escapePeculiar($.trim($('#merNameQuery').val()))"},
    {'name':'merTypeQuery',       'value': "$('#merType').combobox('getValue')"},
    {'name':'proOrderStateQuery', 'value': "escapePeculiar($('#proOrderStateQuery').combobox('getValue'))"},
    {'name':'proInnerStateQuery', 'value': "$('#proInnerStateQuery').combobox('getValue')"},
    {'name':'txnAmtStartQuery',   'value': "escapePeculiar($('#txnAmtStartQuery').val())"},
    {'name':'txnAmtEndQuery',     'value': "escapePeculiar($('#txnAmtEndQuery').val())"},
    {'name':'payTypeQuery',       'value': "$('#payTypeQuery').combobox('getValue')"},
    {'name':'orderCardnoQuery',   'value': "escapePeculiar($('#orderCardnoQuery').val())"},
    {'name':'posCodeQuery',       'value': "escapePeculiar($('#posCodeQuery').val())"},
    {'name':'yktCode',            'value': "myEscapePeculiar($('#yktName').combobox('getValue'))"},
    {'name':'suspiciouStates',    'value': "$('#suspiciouStates').combobox('getValue')"}
];

function myEscapePeculiar(yktCode){
    if (yktCode === '--请选择--') {
    	yktCode = '';
    }
    return yktCode;
}

$(function () {
	initDateQuery();
	initMainComponent();
	
	// 初始化通卡公司选择框
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
	
	// 充值金额输入框限定
	$('#txnAmtStartQuery').focusout(function(){
		if(isNaN($('#txnAmtStartQuery').val())) {
			$('#txnAmtStartQuery').val('');
		}
	});
	
	$('#txnAmtEndQuery').focusout(function(){
		if(isNaN($('#txnAmtEndQuery').val())) {
			$('#txnAmtEndQuery').val('');
		}
	});
	
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
	
});
function initDateQuery(){
	$("#orderDateStartQuery").val(formatDate(new Date(),"yyyy-MM-dd"));
	$("#orderDateEndQuery").val(formatDate(new Date(),"yyyy-MM-dd"));
}
function clearIcdcAllText(){
	clearAllText('queryCondition');
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
    initDateQuery();
}
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#icdcOrderTbl',
            offsetHeight: 120,
            offsetWidth: 0
        },
        callback: initIcdcOrderTbl,
        toolbar: [true, 'top']
    });
}

function initIcdcOrderTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findProductOrder();
        },
        colNames: ['ID','客户名称','客户类型', '订单编号', '外部订单号', '城市','POS号','卡号','充值金额(元)','实收金额(元)', '支付类型', '订单状态','内部状态','上级商户名称','商圈','经营范围','星期','时间段', '订单时间'],
        colModel: [
            {name: 'id', hidden: true, frozen:true},
            {name: 'merName', index: 'merName', width: 150, align: 'center', frozen:true, sortable: false},
            {name: 'merUserType', index: 'merUserType', width: 150, align: 'center', sortable: false},
            {name: 'proOrderNum', index: 'proOrderNum', width: 180, align: 'center', sortable: false},
            {name: 'merOrderNum', index: 'merOrderNum', width: 120, align: 'left', sortable: false, formatter: htmlFormatter},
            {name: 'cityName', index: 'cityName', width: 80, align: 'center', sortable: false},
            {name: 'posCode', index: 'posCode', width: 110, align: 'center', sortable: false},
            {name: 'orderCardno', index: 'orderCardno', width: 150, align: 'center', sortable: false},
            {name: 'showTxnAmt', index: 'showTxnAmt', width: 80, align: 'center', sortable: false},
            {name: 'showReceivedPrice', index: 'showReceivedPrice', width: 80, align: 'center', sortable: false },
            {name: 'payTypeStr', index: 'payTypeStr', width: 100, align: 'center', sortable: false},
            {name: 'proOrderStateView', index: 'proOrderStateView', width: 100, align: 'center', sortable: false},
            {name: 'proInnerStateStr', index: 'proInnerStateStr', width: 100, align: 'center', sortable: false},
            {name: 'merchantbean.merParentName', index: 'merParentName', width: 100, align: 'center', sortable: false},
            {name: 'merchantbean.merDdpInfo.tradeArea', index: 'tradeArea', width: 100, align: 'center', sortable: false},
            {name: 'merchantbean.merBusinessScopeIdView', index: 'merBusinessScopeIdView', width: 100, align: 'center', sortable: false},
            {name: 'weekDay', index: 'weekDay', width: 100, align: 'center', sortable: false},
            {name: 'proOrderDate', index: 'proOrderDate', width: 80, align: 'center', sortable: false,
            	formatter:function(cellval, el, rowData){
            		return formatDate(cellval,'HH')+"-"+formatDate(cellval+ 60*60*1000,'HH');
            	}
            },
            {name: 'proOrderDate', index: 'proOrderDate', width: 150, align: 'center', sortable: false, formatter: cellDateFormatter}
        ],
        height: size.height - 120,
        width: size.width,
		multiselect : false,
		autowidth : true,
        shrinkToFit : false,
        pager: '#icdcOrderTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#icdcOrderTbl').jqGrid(option);
    $("#icdcOrderTbl").jqGrid('setFrozenColumns');
    $("#t_icdcOrderTbl").append($('#icdcOrderTblToolbar'));
}

function findProductOrder(defaultPageNo) {
	var txnAmtStart = $('#txnAmtStartQuery').val();
    var txnAmtEnd = $('#txnAmtEndQuery').val();
    if(txnAmtStart) {
    	txnAmtStart = txnAmtStart*100;
    }
    if(txnAmtEnd) {
    	txnAmtEnd = txnAmtEnd*100;
    }
    
    yktCodeQuery = $('#yktName').combobox('getValue');
    if (yktCodeQuery === '--请选择--') {
    	yktCodeQuery = '';
    }
    
    ddpAjaxCall({
        url: "findProductOrders",
        data: {
        	proOrderNum: escapePeculiar($.trim($('#proOrderNumQuery').val())),
        	proOrderState : $('#proOrderStateQuery').combobox('getValue'),
        	orderDateStart : $('#orderDateStartQuery').val(),
        	orderDateEnd : $('#orderDateEndQuery').val(),
        	orderCardno : escapePeculiar($('#orderCardnoQuery').val()),
        	yktCode:yktCodeQuery,
        	txnAmtStart : txnAmtStart,
        	txnAmtEnd : txnAmtEnd,
        	posCode : escapePeculiar($('#posCodeQuery').val()),
        	merOrderNum : escapePeculiar($.trim($('#merOrderNumQuery').val())),
        	proInnerState : $('#proInnerStateQuery').combobox('getValue'),
        	merName : escapePeculiar($.trim($('#merNameQuery').val())),
        	merType : $('#merType').combobox('getValue'),
        	payType : $('#payTypeQuery').combobox('getValue'),
        	suspiciouStates:$('#suspiciouStates').combobox('getValue'),
        	source : "OSS",
            page: getJqgridPage('icdcOrderTbl', defaultPageNo)
        },
        
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcOrderTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
        
    });
}

function viewProductOrderDetails() {
	var selarrrow = $('#icdcOrderTbl').getGridParam('selrow');
	if (selarrrow) {
		var rowData = $('#icdcOrderTbl').getRowData(selarrrow);
		ddpAjaxCall({
			url : "viewProductOrderDetails",
			 data: rowData.proOrderNum,
			successFn : function(response) {
				showDialog('viewIcdcOrderDialog');
				var htmlKeys = ['comments'];
				loadJsonData2ViewPageWithHtmlFormat('viewIcdcOrderDialog', response, htmlKeys, function(jsonData) {
					$('#view_proOrderDateStr').html(ddpDateFormatter(jsonData.proOrderDate));
				});
			}
		});
	} else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}

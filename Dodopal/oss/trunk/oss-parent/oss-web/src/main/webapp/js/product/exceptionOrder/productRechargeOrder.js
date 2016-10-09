var expConfInfo = {
        "btnId"            : "btnSelExcCol",                 /*must*/// the id of export btn in ftl 
        "typeSelStr"       : "ProductOrderDTO",              /*must*/// type of export
        "toUrl"            : "exportPrdExpOrder"             /*must*/// the export url
    };
var filterConStr = [    // filter the result by query condition
    {'name':'proOrderNum',   'value': "escapePeculiar($.trim($('#proOrderNumQuery').val()))"},
    {'name':'proOrderState', 'value': "$('#proOrderStateQuery').combobox('getValue')"},
    {'name':'orderDateStart','value': "$('#orderDateStartQuery').val()"},
    {'name':'orderDateEnd',  'value': "$('#orderDateEndQuery').val()"},
    {'name':'orderCardno',   'value': "escapePeculiar($('#orderCardnoQuery').val())"},
    {'name':'yktCode',       'value': "myEscapePeculiar($.trim($('#yktName').combobox('getValue')))"},
    {'name':'txnAmtStart',   'value': "$('#txnAmtStartQuery').val()"},
    {'name':'txnAmtEnd',     'value': "$('#txnAmtEndQuery').val()"},
    {'name':'posCode',       'value': "escapePeculiar($('#posCodeQuery').val())"},
    {'name':'merOrderNum',   'value': "escapePeculiar($.trim($('#merOrderNumQuery').val()))"},
    {'name':'proInnerState', 'value': "$('#proInnerStateQuery').combobox('getValue')"},
    {'name':'merName',       'value': "escapePeculiar($.trim($('#merNameQuery').val()))"},
    {'name':'merType',       'value': "$('#merType').combobox('getValue')"},
    {'name':'payType',       'value': "$('#payTypeQuery').combobox('getValue')"}
];
function myEscapePeculiar(yktCode){
    if (yktCode === '--请选择--') {
    	yktCode = '';
    }
    return yktCode;
}

function clearIcdcAllText(){
	clearAllText('queryCondition');
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
}

$(function () {
	initMainComponent();
	
	// 初始化通卡公司选择框
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
    
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
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#icdcOrderTbl',
            offsetHeight: 95,
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
        colNames: ['ID','proOrderState','proInnerState', '客户名称','客户类型','订单编号', '外部订单号','城市','POS号','卡号','充值金额(元)','实收金额(元)', '支付类型', '订单状态','内部状态',  '订单时间'],
        colModel: [
            {name: 'id', hidden: true,frozen:true},
            {name: 'proOrderState', hidden: true,frozen:true},
            {name: 'proInnerState', hidden: true,frozen:true},
            {name: 'merName', index: 'merName', width: 150, align: 'center', sortable: false,frozen:true},
            {name: 'merUserType', index: 'merUserType', width: 120, align: 'center', sortable: false},
            {name: 'proOrderNum', index: 'proOrderNum', width: 180, align: 'center', sortable: false},
            {name: 'merOrderNum', index: 'merOrderNum', width: 120, align: 'left', sortable: false},
            {name: 'cityName', index: 'cityName', width: 80, align: 'center', sortable: false},
            {name: 'posCode', index: 'posCode', width: 110, align: 'center', sortable: false},
            {name: 'orderCardno', index: 'orderCardno', width: 150, align: 'center', sortable: false},
            {name: 'showTxnAmt', index: 'showTxnAmt', width: 80, align: 'center', sortable: false},
            {name: 'showReceivedPrice', index: 'showReceivedPrice', width: 80, align: 'center', sortable: false },
            {name: 'payTypeStr', index: 'payTypeStr', width: 100, align: 'center', sortable: false},
            {name: 'proOrderStateView', index: 'proOrderStateView', width: 100, align: 'center', sortable: false},
            {name: 'proInnerStateStr', index: 'proInnerStateStr', width: 130, align: 'center', sortable: false},
            {name: 'proOrderDate', index: 'proOrderDate', width: 150, align: 'center', sortable: false, formatter: cellDateFormatter}
        ],
        height: size.height - 95,
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
        url: "findProductOrderByPage",
        data: {
        	proOrderNum: escapePeculiar($.trim($('#proOrderNumQuery').val())),
        	proOrderState : $('#proOrderStateQuery').combobox('getValue'),
        	orderDateStart : $('#orderDateStartQuery').val(),
        	orderDateEnd : $('#orderDateEndQuery').val(),
        	orderCardno : escapePeculiar($('#orderCardnoQuery').val()),
        	yktCode : yktCodeQuery,
        	txnAmtStart : txnAmtStart,
        	txnAmtEnd : txnAmtEnd,
        	posCode : escapePeculiar($('#posCodeQuery').val()),
        	merOrderNum : escapePeculiar($.trim($('#merOrderNumQuery').val())),
        	proInnerState : $('#proInnerStateQuery').combobox('getValue'),
        	merName : escapePeculiar($.trim($('#merNameQuery').val())),
        	merType : $('#merType').combobox('getValue'),
        	payType : $('#payTypeQuery').combobox('getValue'),
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

function exceptionHandle(){
	
	var selarrrow = $('#icdcOrderTbl').getGridParam('selrow');
	if (selarrrow) {
		var rowData = $('#icdcOrderTbl').getRowData(selarrrow);
		var html = "订单号："+rowData.proOrderNum+"；<br><br>";
		    html += "订单主状态："+rowData.proOrderStateView+"；订单内部状态："+rowData.proInnerStateStr+"；<br><br>";
		if (rowData.proOrderState === '3' && rowData.proInnerState ==='31') {
			html += "<span style='color:red'>该订单为可疑订单！请确认后，选择以下一种处理方式：</span>";
			$("#messageDiv").html(html);
			$("#radioDiv").show();
		} else if (rowData.proOrderState === '5' && rowData.proInnerState ==='50'){
			html += "<span style='color:red'>该订单为可疑订单！请确认后，选择以下一种处理方式：</span>";
			$("#messageDiv").html(html);
			$("#radioDiv").show();
		} else if (rowData.proOrderState === '4' && rowData.proInnerState ==='40'
			|| rowData.proOrderState === '4' && rowData.proInnerState ==='42'){
			html += "<span style='color:red'>系统判定该订单：充值成功（资金扣款）；您确认处理吗？</span>";
			$("#messageDiv").html(html);
			$("#radioDiv").hide();
		} else if(rowData.proOrderState === '1' && rowData.proInnerState ==='11') {
			html += "<span style='color:red'>系统判定该订单：充值失败（账户加值）；您确认处理吗？</span>";
			$("#messageDiv").html(html);
			$("#radioDiv").hide();
		} else {
			html += "<span style='color:red'>系统判定该订单：充值失败（资金解冻）；您确认处理吗？</span>";
			$("#messageDiv").html(html);
			$("#radioDiv").hide();
		}
		$('#exceptionHandleDialog').show().dialog({
			buttons : [{
				text:'确定',
				handler:function(){
					var judge = '';
					if (rowData.proOrderState === '3' && rowData.proInnerState ==='31'
						|| rowData.proOrderState === '5' && rowData.proInnerState ==='50') {
						judge = $("input[name='handleSign']:checked").val();
						if (!judge || judge == '') {
							msgShow(systemWarnLabel, "可疑订单，请明确选择一种处理方式", 'warning');
							return;
						}
					} else if (rowData.proOrderState === '4' && rowData.proInnerState ==='40'
						|| rowData.proOrderState === '4' && rowData.proInnerState ==='42'){
						judge = '1';
					} else {
						judge = '0';
					}
					ddpAjaxCall({
						url : "exceptionHandle",
				        data: {
				        	orderNum: rowData.proOrderNum,
				        	judgeResult : judge
				        },
						successFn : function(data) {
				            if (data.code == "000000") {
				            	$('#exceptionHandleDialog').hide().dialog('close');
				            	findProductOrder();
				            } else {
				            	msgShow(systemWarnLabel, data.message, 'warning');
				            }
				        }	
					});
				}
			},{
				text:'取消',
				handler:function(){
					$('#exceptionHandleDialog').hide().dialog('close');				
				}
			}]
		});
		$("input[name='handleSign']:checked").attr("checked",false);
		showDialog("exceptionHandleDialog");
		} else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}



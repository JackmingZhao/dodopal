$(function () {
	initMainComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();

});
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#icdcChargeTbl',
            offsetHeight: 75,
            offsetWidth: 0
        },
        callback: initIcdcChargeTbl,
        toolbar: [true, 'top']
    });
    $('#supplierQuery').combobox('reload', $.base+'/product/buscardbusiness/getIcdcNames');
    $('#supplierQuery').combobox({
        panelHeight: "auto",
        onLoadSuccess: function (data) {
        	  $('#supplierQuery').combobox('setText', '--请选择--');
        }
        });
}

function initIcdcChargeTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findLoadOrders();
        },
        colNames: ['ID', '客户名称','订单编号', '外部订单号', '产品名称 ', 'cityCode', '城市', '卡号','充值金额（元）','圈存状态', '外部下单时间'],
        colModel: [
            {name: 'id', hidden: true,frozen:true},
            {name: 'customerName', index: 'merchantName', width: 200, align: 'center', sortable: false,frozen:true},
            {name: 'orderNum', index: 'orderNum', width: 200, align: 'left', sortable: false},
            {name: 'extMerOrderNum', index: 'sourceOrderNum', width: 200, align: 'left', sortable: false, formatter: htmlFormatter},
           // '客户类型'{name: 'customerTypeView', index: 'customerTypeView', width: 80, align: 'center', sortable: false},
            {name: 'productName', index: 'productCode', width: 100, align: 'center', sortable: false},
            {name: 'cityCode', index: 'cityCode', width: 100, align: 'center', sortable: false, hidden: true},
            {name: 'cityName', index: 'cityName', width: 100, align: 'center', sortable: false},
            {name: 'cardFaceNum', index: 'cardNum', width: 140, align: 'center', sortable: false},
            {name: 'loadAmtView', index: 'chargeAmtView', width: 100, align: 'center', sortable: false },
            {name: 'orderStatusStr', index: 'orderStatusStr', width: 100, align: 'center', sortable: false},
            {name: 'extMerOrderTime', index: 'sourceOrderTime', width: 130, align: 'center', sortable: false, formatter: cellDateFormatter}
        ],
        height: size.height - 75,
        width: size.width,
        autowidth : true,
        shrinkToFit : false,
        forceFit:true,
        pager: '#icdcChargeTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#icdcChargeTbl').jqGrid(option);
    $("#t_icdcChargeTbl").append($('#icdcChargeTblToolbar'));
    $("#icdcChargeTbl").jqGrid('setFrozenColumns');
}

function findLoadOrders(defaultPageNo) {
    ddpAjaxCall({
        url: "findLoadOrders",
        data: {
        	orderNum: escapePeculiar($.trim($('#orderNumQuery').val())),
        	sourceOrderNum: escapePeculiar($.trim($('#sourceOrderNumQuery').val())),
        	cardNum: escapePeculiar($.trim($('#cardNumQuery').val())),
        	orderStatus : $('#orderStatusQuery').combobox('getValue'),
        	merchantName: escapePeculiar($.trim($('#merchantNameQuery').val())),
        	yktCode:$('#supplierQuery').combobox('getValue'),
            page: getJqgridPage('icdcChargeTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcChargeTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function clearIcdcAllText(){
	clearAllText('queryCondition');
	$('#orderStatusQuery').combobox('select','');
}

function icdcChargeView() {
	var selrow = $('#icdcChargeTbl').getGridParam('selrow');
	if (selrow) {
		var rowData = $('#icdcChargeTbl').getRowData(selrow);
		ddpAjaxCall({
			url : "viewLoadOrder",
			data: escapePeculiar(rowData.orderNum),
			successFn : function(response) {
				showDialog('viewIcdcDialog');
				var htmlKeys = ['comments'];
				loadJsonData2ViewPageWithHtmlFormat('viewIcdcDialog', response, htmlKeys);
				if(response.code == "000000") {
					var order = response.responseEntity;
					$("#view_loadAmt").html(order.loadAmtView);
					$("#view_status").html(order.orderStatusStr);
				}
			}
		});
	} else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}
/**
 * ***********************导出**************************
 */
var expConfInfo = {
		"btnId"			: "exportLoadOrder", 					/*must*/// the id of export btn in ftl 
		"typeSelStr"	: "LoadOrder", 	/*must*/// type of export
		"toUrl"			: "exportLoadOrder", 			/*must*/// the export url
		"parDiaHeight"	: "150"
	};

	var filterConStr = [	// filter the result by query condition
			{'name':'orderNum',	'value': "escapePeculiar($.trim($('#orderNumQuery').val()))"   },
			{'name':'sourceOrderNum',	'value': "escapePeculiar($.trim($('#sourceOrderNumQuery').val()))"	},
			{'name':'cardNum',	'value': "escapePeculiar($.trim($('#cardNumQuery').val()))"	},
			{'name':'merchantName',	'value': "escapePeculiar($.trim($('#merchantNameQuery').val()))"	},
			{'name':'yktCode',	'value': "$('#supplierQuery').combobox('getValue')"	},
			{'name':'orderStatus',	'value': "$('#orderStatusQuery').combobox('getValue')"	}
		];

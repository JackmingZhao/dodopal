$(function () {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	$('#cancelBtn').on('click', function() {
		clearIcdcAllText();
		$('#activeCityQuery').combobox('setValue', '-1');
	});
});

var expConfInfo = {
		"btnId"			: "btnSelExcCol", 				/*must*/// the id of export btn in ftl 
		"typeSelStr"	: 	"customerRecharge", 		/*must*/// type of export
		"toUrl"			: "exportExcelCustomerRecharge", 			/*must*/// the export url
		"parDiaHeight"  : "150"
	};
	var filterConStr = [	// filter the result by query condition
		{'name':'merName', 	'value': "escapePeculiar($.trim($('#merName').val()))"			},	// eval
		{'name':'cityCode',	'value': "$('#activeCityQuery').combobox('getValue')"		},
		{'name':'startDate',	'value': "escapePeculiar($.trim($('#startDate').val()))"		},
		{'name':'endDate',	'value': "escapePeculiar($.trim($('#endDate').val()))"		}
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
    $('#activeCityQuery').combobox({
		valueField : 'cityCode',
		textField : 'cityName',
		editable : false
	});
    ddpAjaxCall({
		url : "getCityInfo",
		successFn : function(data) {
			if (data.code == "000000") {
				data.responseEntity.unshift({"cityCode": "-1", "cityName": "--请选择--"});
				addTipsOption(data.responseEntity);
				reLoadComboboxData($('#activeCityQuery'), data.responseEntity);
				$('#activeCityQuery').combobox('select', '-1');
			}else { msgShow(systemWarnLabel, data.message, 'warning'); }
		}
	});
}

function initCardRechargeTbl(size) {
	var option = {
	        datatype: function (pdata) {
	        	findCardRecharge();
	        },
            colNames: [
                '交易日期', '城市', '订单类型', '客户编码', '客户名称', '客户类型', '充值终端类型','充值笔数(笔)','充值总额度(元)'
            ],
            colModel: [
                {name: 'prdOrderDate', index: 'prdOrderDate', width: 100, align: 'left', sortable: false},
                {name: 'cityName', index: 'cityName', width: 80, align: 'left', sortable: false},
                {name: 'orderType', index: 'orderType', width: 100, align: 'left', sortable: false},
                {name: 'merCode', index: 'merCode', width: 130, align: 'left', sortable: false},
                {name: 'merName', index: 'merName', width: 150, align: 'left', sortable: false},
                {name: 'merUerType', index: 'merUerType', width: 100, align: 'left', sortable: false},
                {name: 'source', index: 'source', width: 100, align: 'left', sortable: false},
                {name: 'rechargeCount', index: 'rechargeCount', width: 100, align: 'left', sortable: false},
                {name: 'receivedPrice', index: 'receivedPrice', width: 100, align: 'left', sortable: false}
            ],
            height: size.height - 50,
            width: size.width,
            pager: '#cardRechargeTblPagination',
            toolbar: [true, "top"]
        };
		option = $.extend(option, jqgrid_server_opts);
	    $('#cardRechargeTbl').jqGrid(option);
	    $("#t_cardRechargeTbl").append($('#cardRechargeTblToolbar'));
}


function findCardRecharge(defaultPageNo) {
	var query= {
			merName:$("#merName").val(),
			cityCode:$('#activeCityQuery').combobox('getValue'),
			startDate:$("#startDate").val(),
			endDate:$("#endDate").val(),
        	page: getJqgridPage('cardRechargeTbl', defaultPageNo)
        };
	ddpAjaxCall({
		url: "findCustomerRechargeByPage",
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





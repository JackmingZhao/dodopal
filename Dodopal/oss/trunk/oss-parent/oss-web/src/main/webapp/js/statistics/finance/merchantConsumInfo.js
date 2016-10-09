
/*************************************************  数据导出 start *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "MerchantConsumBean",
	"toUrl"			: "exportMerchantConsumInfo",
	"parDiaHeight"  : "150"
}; 
var filterConStr = [
		{'name':'merName', 	             'value': "escapePeculiar($.trim($('#merNameQuery').val()))"	        },
		{'name':'yktCode', 				 'value': "$('#yktQuery').combobox('getValue')"		},
		{'name':'bussinessType', 	     'value': "$('#bussinessType').combobox('getValue')"			},
		{'name':'startDate',             'value': "escapePeculiar($.trim($('#startDate').val()))"	},
		{'name':'endDate', 	             'value': "escapePeculiar($.trim($('#endDate').val()))"	}
];
/*************************************************  数据导出 end *****************************************************************/

$(function () {
    initMainComponent();
    expExcBySelColTools.initSelectExportDia(expConfInfo);
    parent.closeGlobalWaitingDialog();
});

function resetQuery() {
	clearIcdcAllText();
	$('#yktQuery').combobox('setText', '--请选择--');
}

function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#icdcLimitBatchInfoTbl',
            offsetHeight: 60,
            offsetWidth: 0
        },
        callback: initYktLimitBatchInfoTbl,
        toolbar: [true, 'top']
    });
    
    $('#yktQuery').combobox({   
        url:'getIcdcNames',   
        valueField:'yktCode',   
        textField:'yktName',
        onLoadSuccess: function (data) {
            if (!(typeof(data) == "undefined") & data.length > 0) {
                $('#yktQuery').combobox('setValue', '').combobox('setText', '--请选择--');
            } else {
                $('#yktQuery').combobox('setValue', '').combobox('setText', '--请选择--');
            }
        },
        onLoadError: function () {
            $("#yktQuery").combobox('setValue', '').combobox('setText', '--请选择--');
        }
    });  
 
}

function initYktLimitBatchInfoTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findMerchantConsumInfo();
        },
        colNames: ['ID','交易日期','订单类型','一卡通名称', '商户代码','商户名称','商户类型','消费终端类型','交易金额（元）'],
        colModel: [
            {name: 'id', hidden: true},
            {name: 'orderDate', index: 'orderDate', width: 90, align: 'center', sortable: false},
            {name: 'businessType', index: 'businessType', width: 90, align: 'center', sortable: false},
            {name: 'yktName', index: 'yktName', width: 90, align: 'center', sortable: false},
            {name: 'customerCode', index: 'customerCode', width: 90, align: 'center', sortable: false},
            {name: 'customerName', index: 'customerName', width: 90, align: 'center', sortable: false},
            {name: 'customerType', index: 'customerType', width: 90, align: 'center', sortable: false},
            {name: 'source', index: 'source', width: 75, align: 'center', sortable: false},
            {name: 'receivedPrice', index: 'receivedPrice', width: 90, align: 'center', sortable: false}
        ],
        height: size.height - 60,
        width: size.width,
        multiselect: false,
        forceFit:true,
        pager: '#icdcLimitBatchInfoTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#icdcLimitBatchInfoTbl').jqGrid(option);
    $("#t_icdcLimitBatchInfoTbl").append($('#icdcLimitBatchInfoTblToolbar'));
}

function findMerchantConsumInfo(defaultPageNo) {
    ddpAjaxCall({
        url: "findMerchantConsumInfoByPage",
        data: {
        	merName: escapePeculiar($.trim($('#merNameQuery').val())),
        	yktCode : $('#yktQuery').combobox('getValue'),
        	bussinessType:$('#bussinessType').combobox('getValue'),
        	startDate : $('#startDate').val(),
        	endDate : $('#endDate').val(),
            page: getJqgridPage('icdcLimitBatchInfoTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcLimitBatchInfoTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}



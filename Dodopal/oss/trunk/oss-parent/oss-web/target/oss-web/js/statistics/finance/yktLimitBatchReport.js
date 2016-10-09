
/*************************************************  数据导出 start *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "YktLimitBatchReportForExport",
	"toUrl"			: "exportYktLimitBatchReport",
	"parDiaHeight"  : "150"
}; 
var filterConStr = [
		{'name':'yktName', 	             'value': "escapePeculiar($.trim($('#yktNameQuery').val()))"	        },
		{'name':'financialPayDateStart', 'value': "escapePeculiar($.trim($('#payDateStartQuery').val()))"		},
		{'name':'financialPayDateEnd', 	 'value': "escapePeculiar($.trim($('#payDateEndQuery').val()))"			},
		{'name':'yktAddLimitDateStart',   'value': "escapePeculiar($.trim($('#addLimitDateStartQuery').val()))"	},
		{'name':'yktAddLimitDateEnd', 	 'value': "escapePeculiar($.trim($('#addLimitDateEndQuery').val()))"	}
];
/*************************************************  数据导出 end *****************************************************************/

$(function () {
    initMainComponent();
    expExcBySelColTools.initSelectExportDia(expConfInfo);
    parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	
	// 额度批次申请单信息对话框
	$('#limitBatchDialog').show().dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false
	});
	
    autoResize({
        dataGrid: {
            selector: '#icdcLimitBatchInfoTbl',
            offsetHeight: 60,
            offsetWidth: 0
        },
        callback: initYktLimitBatchInfoTbl,
        toolbar: [true, 'top']
    });

}

function initYktLimitBatchInfoTbl(size) {
    var option = {
        datatype: function (pdata) {
        	limitBatchQuery();
        },
        colNames: ['ID','通卡编码','通卡名称','财务打款额度(元)', '付款渠道','通卡增加额度(元)', '通卡加款日期','通卡未增加额度(元)','财务打款日期','财务打款手续费(元)'],
        colModel: [
            {name: 'id', hidden: true},
            {name: 'yktCode', index: 'yktCode', width: 70, align: 'center', sortable: false},
            {name: 'yktName', index: 'yktName', width: 140, align: 'left', sortable: false, formatter: htmlFormatter},
            {name: 'financialPayAmt', index: 'financialPayAmt', width: 100, align: 'right', sortable: false},
            {name: 'paymentChannel', index: 'paymentChannel', width: 120, align: 'left', sortable: false},
            {name: 'yktAddLimit', index: 'yktAddLimit', width: 100, align: 'right', sortable: false},
            {name: 'yktAddLimitDate', index: 'yktAddLimitDate', width: 70, align: 'center', sortable: false},
            {name: 'yktUnaddLimit', index: 'yktUnaddLimit', width: 100, align: 'right', sortable: false},
            {name: 'financialPayDate', index: 'financialPayDate', width: 70, align: 'center', sortable: false},
            {name: 'financialPlayFee', index: 'financialPlayFee', width: 100, align: 'right', sortable: false}
        ],
        height: size.height - 60,
        width: size.width,
        multiselect: false,
        forceFit:true,
		//autowidth : true,
        //shrinkToFit : false,
        pager: '#icdcLimitBatchInfoTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#icdcLimitBatchInfoTbl').jqGrid(option);
    $("#t_icdcLimitBatchInfoTbl").append($('#icdcLimitBatchInfoTblToolbar'));
}

function limitBatchQuery(defaultPageNo) {
    ddpAjaxCall({
        url: "findYktLimitBatchReportByPage",
        data: {
        	yktName: escapePeculiar($.trim($('#yktNameQuery').val())),
        	financialPayDateStart : $('#payDateStartQuery').val(),
        	financialPayDateEnd : $('#payDateEndQuery').val(),
        	yktAddLimitDateStart : $('#addLimitDateStartQuery').val(),
        	yktAddLimitDateEnd : $('#addLimitDateEndQuery').val(),
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



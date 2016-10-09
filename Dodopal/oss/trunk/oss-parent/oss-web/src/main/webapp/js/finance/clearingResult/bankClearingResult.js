$(function () {
	initMainComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#clearingResultTbl',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initClearingResultTbl,
        toolbar: [true, 'top']
    });
}

var myjqgrid_server_opts = {
		jsonReader: {
		      root: "records",
		      page: "pageNo",
		      total: "totalPages",
		      records: "rows",
		      repeatitems: false
		},
		rowNum : 20,
		rowList : [20,50,100],
		viewrecords : true,
		gridview : false,
		pagerpos :'left',
		onSelectRow : function(rowid, status) {
			if (status == true) {
				var tableId = $(this).attr('id');
				var headerCheckbox = 'cb_' + tableId;
				var selarrrow = $(this).getGridParam('selarrrow');
				var allData = $(this).jqGrid('getDataIDs');
				if (selarrrow != null && selarrrow.length == allData.length) {
					$('#' + headerCheckbox).attr('checked', 'checked');
				}
			}
		},
        afterInsertRow : function(rowid, rowdata, rowelem) {
        		$.each($("td[aria-describedby='clearingResultTbl_rn']").next(),function(){ 
        			if ($(this).html()=="undefined") {
        				$(this).remove();
        			}
        		}) 
        }
	};

function myCellDateFormatterYYYYMMdd(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '' && cellval.length == 8) {
		var day = cellval.substring(0,4)+'-'+cellval.substring(4,6)+'-'+cellval.substring(6,8);
		return day;
	} else {
		return cellval;
	}
}

function initClearingResultTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findBankClearingResult();
        },
        colNames: ['payGateway', '清算日期','支付网关', '交易笔数 ','交易金额（元） ', '银行手续费（元）', '转账金额（元）','清算时间'],
        colModel: [
            {name: 'payGateway', index: 'payGateway', hidden: true},
            {name: 'clearingDay', index: 'clearingDay', width: 120, align: 'center', sortable: false, formatter: myCellDateFormatterYYYYMMdd},
            {name: 'payGatewayView', index: 'payGatewayView', width: 180, align: 'left', sortable: false},
            {name: 'tradeCount', index: 'tradeCount', width: 120, align: 'right', sortable: false},
            {name: 'tradeAmount', index: 'tradeAmount', width: 120, align: 'right', sortable: false},
            {name: 'bankFee', index: 'bankFee', width: 120, align: 'right', sortable: false},
            {name: 'transferAmount', index: 'transferAmount', width: 120, align: 'right', sortable: false },
            {name: 'clearingDate', index: 'clearingDate', width: 180, align: 'center', sortable: false, formatter: cellDateFormatter}
        ],
        height: size.height - 50,
        width: size.width,
        forceFit:true,
        multiselect : false, 
        pager: '#clearingResultTblPagination',
        toolbar: [true, "top"],
        subGrid: true,  // (1)开启子表格支持  
        subGridOptions: { 
        	plusicon : "ui-icon-triangle-1-e", 
        	minusicon : "ui-icon-triangle-1-s", 
        	openicon : "ui-icon-arrowreturn-1-e",
        	expandOnLoad: false,
        	selectOnExpand : true,
        	reloadOnExpand : true},
        subGridRowExpanded: function(subgrid_id, row_id) {  // (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数  
        	var subgrid_table_id = subgrid_id + "_t";
        	var payGateway = $("#clearingResultTbl").jqGrid("getCell",row_id,"payGateway");
        	var clearingDay = $("#clearingResultTbl").jqGrid("getCell",row_id,"clearingDay");
        	
        	var colModel = $("#clearingResultTbl").jqGrid("getGridParam","colModel");
            var clearingDayWidth=colModel[3].width;
            var payGatewayWidth=colModel[4].width;
            var tradeCountWidth=colModel[5].width;
            var tradeAmountWidth=colModel[6].width;
            var bankFeeWidth=colModel[7].width;
            var transferAmountWidth=colModel[8].width;
            var clearingDateWidth=colModel[9].width;
            
            ddpAjaxCall({
                url: "getBankTxnClearingResult",
                data: {
                    clearingDayStart: clearingDay,
                    clearingDayEnd: clearingDay,
                    payGateway:payGateway
                },
                successFn: function (data) {
                    if (data.code == "000000") {
                        var html = "<div id='"+subgrid_table_id+"'><table>";
                        var list = data.responseEntity;
                        for (var j = 0; j < list.length; j++) {
                    		html += '<tr><td width='+clearingDayWidth+'>&nbsp</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+payGatewayWidth+' title='+list[j].txnTypeView+'>'+list[j].txnTypeView+'</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+tradeCountWidth+' title='+list[j].tradeCount+'>'+list[j].tradeCount+'</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+tradeAmountWidth+' title='+list[j].tradeAmount+'>'+list[j].tradeAmount+'</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+bankFeeWidth+' title='+list[j].bankFee+'>'+list[j].bankFee+'</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+transferAmountWidth+' title='+list[j].transferAmount+'>'+list[j].transferAmount+'</td>';
                			html += '<td width='+clearingDateWidth+'>&nbsp</td></tr>';
                        }
                        html += "</table></div>";
                        $("#" + subgrid_id).html(html);
                    } else {
                        msgShow(systemWarnLabel, data.message, 'warning');
                    }
                }
            });
      }  
    };
    option = $.extend(option, myjqgrid_server_opts);
    $('#clearingResultTbl').jqGrid(option);
    $("#t_clearingResultTbl").append($('#clearingResultTblToolbar'));
}

function findBankClearingResult(defaultPageNo) {
    ddpAjaxCall({
        url: "findBankClearingResultByPage",
        data: {
            clearingDayStart: $('#clearingDayStart').val(),
            clearingDayEnd: $('#clearingDayEnd').val(),
            payGateway:"",
            page: getJqgridPage('clearingResultTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#clearingResultTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "bankCleaning",
	"toUrl"			: "exportExcelInfo"
};
var filterConStr = [
	{'name':'clearingDayStart', 	'value': "$('#clearingDayStart').val()"			},	// eval
	{'name':'clearingDayEnd',		'value': "$('#clearingDayEnd').val()"		},
	{'name':'payGateway',			'value': ""			}
];



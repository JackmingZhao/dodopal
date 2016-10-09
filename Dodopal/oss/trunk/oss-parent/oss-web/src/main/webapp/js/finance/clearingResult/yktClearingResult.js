$(function () {
	initMainComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();

});
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#yktClearingResultTbl',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initYktClearingResultTbl,
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
        		$.each($("td[aria-describedby='yktClearingResultTbl_rn']").next(),function(){ 
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

function initYktClearingResultTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findYktClearingResult();
        },
        colNames: ['ID', '清算日期', '通卡公司编号', '通卡公司名称 ', '交易笔数', '交易金额（元）', '手续费（元）', '转账金额（元）', '清算时间'],
        colModel: [
            {name: 'id', hidden: true},
            {name: 'clearingDay', index: 'clearingDay', width: 100, align: 'center', sortable: false, formatter: myCellDateFormatterYYYYMMdd},
            {name: 'yktCode', index: 'yktCode', width: 100, align: 'left', sortable: false},
            {name: 'yktName', index: 'yktName', width: 140, align: 'left', sortable: false},
            {name: 'tradeCount', index: 'tradeCount', width: 100, align: 'right', sortable: false},
            {name: 'tradeAmount', index: 'tradeAmount', width: 100, align: 'right', sortable: false},
            {name: 'yktFee', index: 'yktFee', width: 100, align: 'right', sortable: false},
            {name: 'transferAmount', index: 'transferAmount', width: 100, align: 'right', sortable: false},
            {name: 'clearingDate', index: 'clearingDate', width: 140, align: 'center', sortable: false, formatter: cellDateFormatter}
        ],
        height: size.height - 50,
        width: size.width,
        forceFit:true,
        pager: '#yktClearingResultTblPagination',
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
        	var yktCode = $("#yktClearingResultTbl").jqGrid("getCell",row_id,"yktCode");
        	var clearingDay = $("#yktClearingResultTbl").jqGrid("getCell",row_id,"clearingDay");
        	
        	var colModel = $("#yktClearingResultTbl").jqGrid("getGridParam","colModel");
            var clearingDayWidth=colModel[3].width;
            var yktCodeWidth=colModel[4].width;
            var yktNameWidth=colModel[5].width;
            var tradeCountWidth=colModel[6].width;
            var tradeAmountWidth=colModel[7].width;
            var yktFeeWidth=colModel[8].width;
            var transferAmountWidth=colModel[9].width;
            var clearingDateWidth=colModel[10].width;
            
            ddpAjaxCall({
                url: "getYktCityClearingResult",
                data: {
                    clearingDayStart: clearingDay,
                    clearingDayEnd: clearingDay,
                    yktCode:yktCode
                },
                successFn: function (data) {
                    if (data.code == "000000") {
                        var html = "<div id='"+subgrid_table_id+"'><table>";
                        var list = data.responseEntity;
                        for (var j = 0; j < list.length; j++) {
                    		html += '<tr><td width='+clearingDayWidth+'>&nbsp</td>';
                    		html += '<td width='+yktCodeWidth+'>&nbsp</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+yktNameWidth+' title='+list[j].cityName+'>'+list[j].cityName+'</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+tradeCountWidth+' title='+list[j].tradeCount+'>'+list[j].tradeCount+'</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+tradeAmountWidth+' title='+list[j].tradeAmount+'>'+list[j].tradeAmount+'</td>';
                			html += '<td style="text-align:right;color:#F14E43;border-bottom:1px #D7EBF9 solid;border-top:1px #D7EBF9 solid;border-left:1px #D7EBF9 solid;border-right:1px #D7EBF9 solid" width='+yktFeeWidth+' title='+list[j].yktFee+'>'+list[j].yktFee+'</td>';
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
    $('#yktClearingResultTbl').jqGrid(option);
    $("#t_yktClearingResultTbl").append($('#yktClearingResultTblToolbar'));
}

function findYktClearingResult(defaultPageNo) {	
	
	var data={
	      	clearingDayStart:$('#clearingDayStart').val(),
            clearingDayEnd:$('#clearingDayEnd').val(),
            yktCode:"",
            page:getJqgridPage('yktClearingResultTbl', defaultPageNo)
	};
    ddpAjaxCall({
        url: "findYktClearingResultByPage",
        data: data,
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#yktClearingResultTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "yktClearing",
	"toUrl"			: "exportExcelInfo"
};
var filterConStr = [
	{'name':'clearingDayStart', 	'value': "$('#clearingDayStart').val()"		},	// eval
	{'name':'clearingDayEnd',		'value': "$('#clearingDayEnd').val()"		},
	{'name':'yktCode',				'value': ""									}
];


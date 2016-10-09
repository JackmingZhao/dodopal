
/*************************************************  数据导出 start *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					 
	"typeSelStr"	: "ProductYktLimitInfoForExport", 	
	"toUrl"			: "exportExcelProductYktLimit",
	"parDiaHeight"  : "210"
};
var filterConStr = [
	{'name':'yktName', 	'value': "escapePeculiar($.trim($('#yktNameQuery').val()))"}
];
/*************************************************  数据导出 end *****************************************************************/

$(function () {
    initMainComponent();
    expExcBySelColTools.initSelectExportDia(expConfInfo);
    parent.closeGlobalWaitingDialog();

});

function initMainComponent() {
	
	// 购买额度对话框
	$('#buyLimitDialog').show().dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 550,
		height : 300
	});
	
	// 修改额度信息对话框
	$('#modifyLimitDialog').show().dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false
	});
	
    autoResize({
        dataGrid: {
            selector: '#icdcLimitInfoTbl',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initTkgaBasicInfoTbl,
        toolbar: [true, 'top']
    });
    
}

var dateformatterYYYYMMdd = 'yyyy-MM-dd';
function cellDateFormatterYYYYMMdd(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return formatDate(cellval, dateformatterYYYYMMdd);
	} else {
		return '';
	}
}

function formatFloat(src, pos)
{
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
}
function cellDateFormatterYUAN(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return (cellval/100).toFixed(2);
	} else {
		return '';
	}
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
        	var surPlusLimit= rowdata.surPlusLimit;
        	var yktWarnLimit= rowdata.yktWarnLimit;
        	if (surPlusLimit <= yktWarnLimit) {   		
        		$('#icdcLimitInfoTbl').jqGrid('setCell',rowid,'yktName','',{'color':'red'},'');
        		$('#icdcLimitInfoTbl').jqGrid('setCell',rowid,'surPlusLimit','',{'color':'red'},'');
        	}
        }
	};
function initTkgaBasicInfoTbl(size) { 
    var option = {
        datatype: function (pdata) {
        	limitQuery();
        },
        colNames: ['ID', '通卡公司名称', '购买总金额（元）', '已用额度（元）', '剩余额度（元）','报警额度（元）', '终止充值额度（元）', '有效期'],
        colModel: [
            {name: 'id', hidden: true},
            {name: 'yktName', index: 'yktName', width: 200, align: 'left', sortable: false, formatter: htmlFormatter},
            {name: 'totalAmtLimit', index: 'totalAmtLimit', width: 100, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'usedLimit', index: 'usedLimit', width: 100, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'surPlusLimit', index: 'surPlusLimit', width: 100, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'yktWarnLimit', index: 'yktWarnLimit', width: 100, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'yktStopLimit', index: 'yktStopLimit', width: 100, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'yktExpireDate', index: 'yktExpireDate', width: 100, align: 'center', sortable: false, formatter: cellDateFormatterYYYYMMdd},
        ],
        height: size.height - 50,
        width: size.width,
        multiselect: false,
        forceFit:true,
        pager: '#icdcLimitInfoTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, myjqgrid_server_opts);
    $('#icdcLimitInfoTbl').jqGrid(option);
    $("#t_icdcLimitInfoTbl").append($('#icdcLimitInfoTblToolbar'));
}
//加载额度信息
function limitQuery(defaultPageNo) {
    ddpAjaxCall({
        url: "findProductYktLimitInfoByPage",
        data: {
        	yktName: escapePeculiar($.trim($('#yktNameQuery').val())),
            page: getJqgridPage('icdcLimitInfoTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcLimitInfoTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function saveLimit(){
	if(isValidate('modifyLimitDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存额度信息吗？", function(r) {
			if (r) {
				var productYktLimitInfoBean = {
						id : $('#limitId').val(),
						yktExpireDate:$('#yktExpireDate').val(),
						yktWarnLimit:$("#yktWarnLimit").val()*100,
						yktStopLimit:$("#yktStopLimit").val()*100,
						remark:$("#remarkShow").val()
				};
			    ddpAjaxCall({
			        url: "saveProductYktLimitInfo",
			        data: productYktLimitInfoBean,
			        successFn: function (data) {
			        	if(data.code == "000000") {
			        		hideDialog('modifyLimitDialog');
			        		limitQuery();
			        	} else {
			        		msgShow(systemWarnLabel, data.message, 'warning');
			        	}
			        }
			    });
			}
		});
	}
}
function modifyLimit() {
	destroyValidate('modifyLimitDialog');
	
	var selrow = $("#icdcLimitInfoTbl").getGridParam('selrow');
	if (!selrow) {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var rowData = $('#icdcLimitInfoTbl').getRowData(selrow);
	
	if(rowData != null) {
		ddpAjaxCall({
			url : "findProductYktLimitInfoById",
			data : rowData.id,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var limitBean = data.responseEntity;
					$('#limitId').val(limitBean.id);
					$('#yktName').val(limitBean.yktName);
					$('#yktExpireDate').val(formatDate(limitBean.yktExpireDate, dateformatterYYYYMMdd));
					$('#yktWarnLimit').val(limitBean.yktWarnLimit/100);
					$('#yktStopLimit').val(limitBean.yktStopLimit/100);
					$('#yktTotalAmtLimit').val((limitBean.totalAmtLimit/100).toFixed(2));
					$('#yktSurPlusLimit').val((limitBean.surPlusLimit/100).toFixed(2));
					$('#remarkShow').val(limitBean.remark);
					$('#createDateShow').html(ddpDateFormatter(limitBean.createDate));
					$('#updateDateShow').html(ddpDateFormatter(limitBean.updateDate));
					$('#createUserShow').html(limitBean.createUser);
					$('#updateUserShow').html(limitBean.updateUser);
					showDialog('modifyLimitDialog');
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

function viewLimit() {
	var selrow = $("#icdcLimitInfoTbl").getGridParam('selrow');
	if (!selrow) {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var rowData = $('#icdcLimitInfoTbl').getRowData(selrow);
	if (rowData != null) {
		ddpAjaxCall({
			url : "findProductYktLimitInfoById",
			data : rowData.id,
			successFn : loadViewLimit
		});
	}
}
function loadViewLimit(data) {
	if(data.code=="000000"){
		var limitBean = data.responseEntity;
		$('#view_yktName').html(limitBean.yktName);
		$('#view_yktExpireDate').html(formatDate(limitBean.yktExpireDate, dateformatterYYYYMMdd));
		$('#view_yktWarnLimit').html((limitBean.yktWarnLimit/100).toFixed(2) +"&nbsp;元");
		$('#view_yktStopLimit').html((limitBean.yktStopLimit/100).toFixed(2) +"&nbsp;元");
		$('#view_totalAmtLimit').html((limitBean.totalAmtLimit/100).toFixed(2)+"&nbsp;元");
		$('#view_surPlusLimit').html((limitBean.surPlusLimit/100).toFixed(2)+"&nbsp;元");
		$('#view_remark').html(htmlTagFormat(limitBean.remark));
		$('#view_createDate').html(formatDate(limitBean.createDate, dateformatterStr));
		$('#view_updateDate').html(formatDate(limitBean.updateDate, dateformatterStr));
		$('#view_createUser').html(limitBean.createUser);
		$('#view_updateUser').html(limitBean.updateUser);
		showDialog('viewLimitDialog');
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}



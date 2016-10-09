$(function() {
	initMainComponent();
	// findData();
});
function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#logDLogTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initDataTbl,
		toolbar : [ true, 'top' ]
	// dialogs : [ 'dataDialog' ]
	});
}

function initDetailComponent() {
	initDataDialog();
}

function initDataTbl(size) {
	var option = {
		datatype : function(pdata) {
			findOcxLog();
		},
		colNames : [ 'id', '产品库主订单号', '卡号', '错误码', '错误信息', '日志所处阶段',
				'城市前置返回APDU指令', "执行APDU指令响应数据", "执行APDU指令响应码", "创建时间", "时间" ],
		colModel : [ {
			name : 'id',
			index : 'id',
			hidden : true,
			frozen:true
		}, {
			name : 'dlogPrdorderNum',
			index : 'dlogPrdorderNum',
			width : 160,
			align : 'left',
			sortable : false,
			frozen:true
		}, {
			name : 'dlogTradeCard',
			index : 'dlogTradeCard',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'dlogCode',
			index : 'dlogCode',
			width : 65,
			align : 'left',
			sortable : false
		}, {
			name : 'dlogMessage',
			index : 'dlogMessage',
			width : 280,
			align : 'left',
			sortable : false,
		}, {
			name : 'dlogStage',
			index : 'dlogStage',
			width : 280,
			align : 'left',
			sortable : false,
		}, {
			name : 'dlogApdu',
			index : 'dlogApdu',
			width : 350,
			align : 'left',
			sortable : false
		}, {
			name : 'dlogApduData',
			index : 'dlogApduData',
			width : 140,
			align : 'left',
			sortable : false
		}, {
			name : 'dlogStateCode',
			index : 'dlogStateCode',
			width : 160,
			align : 'left',
			sortable : false
		}, {
			name : 'createDate',
			index : 'createDate',
			width : 170,
			align : 'left',
			sortable : false,
			formatter : cellDateFormatter

		}, {
			name : 'dlogSystemDateTime',
			index : 'dlogSystemDateTime',
			width : 140,
			align : 'left',
			sortable : false
		} ],
		// caption : "用户列表",
		// sortname : 'name',
		height : size.height - 50,
		width : size.width,
		autowidth : true,
		shrinkToFit : false,
		pager : '#logDLogTblPagination',
		toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#logDLogTbl').jqGrid(option);
	$("#logDLogTbl").jqGrid('setFrozenColumns');
}
function findOcxLog(defaultPageNo) {
	var query = {
		dlogPrdorderNum : escapePeculiar($.trim($('#dlogPrdorderNumQuery').val())),
		dlogTradeCard : escapePeculiar($.trim($('#dlogTradeCardQuery').val())),
		createDateStart : $('#createDateStart').val(),
		createDateEnd : $('#createDateEnd').val(),
		page : getJqgridPage('logDLogTbl', defaultPageNo)
	}

	ddpAjaxCall({
		url : "findLogDLogByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#logDLogTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});

}
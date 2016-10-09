$(function() {
	initSourceCombobox();
	initMainComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});
function initSourceCombobox() {
	
	$('#sourceQuery').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 120
	});
	
	var ddic = {
			categoryCode : 'SOURCE'
	}
	loadDdics(ddic, loadSource);
}
function loadSource(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#sourceQuery'), data.responseEntity)
		$('#sourceQuery').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
function initMainComponent() {
	
	autoResize({
		dataGrid : {
			selector : '#posLogTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initPosLogTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ null ]
	});
}

function initPosLogTbl(size) {
	var option = {
		datatype : function (pdata) {
			findPosLog();
	    },
		colNames : [ '商户名称', 'POS编码', '操作动作', '操作人', '操作来源', '操作时间'],
		colModel : [ 
		             { name : 'merName', index : 'merName', width : 100, align : 'left',sortable : false }, 
		             { name : 'code', index : 'code', width : 100, align : 'left',sortable : false }, 
		             { name : 'operStatusView', index : 'operStatusView', width : 100, align : 'left',sortable : false}, 
		             { name : 'operName', index : 'operName', width : 100, align : 'left',sortable : false },
		             { name : 'sourceView', index : 'sourceView', width : 100, align : 'left',sortable : false},
		             { name : 'createDate', index : 'createDate', width : 100, align : 'left',sortable : false, formatter: cellDateFormatter }
		            ],
		height : size.height - 50,
		width : size.width,
		pager : '#posLogTblPagination',
		toolbar : [ true, "top" ]
	};
	
	 option = $.extend(option, jqgrid_server_opts);
	 $('#posLogTbl').jqGrid(option);
	 $("#t_posLogTbl").append($('#posLogTblToolbar'));
}

function findPosLog(defaultPageNo) {
	ddpAjaxCall({
		url : "findPosLog",
		data : {
			merName : escapePeculiar($.trim($('#merNameQuery').val())),
			code : escapePeculiar($.trim($('#posCodeQuery').val())), 
			operName : escapePeculiar($.trim($('#operNameQuery').val())),
			page : getJqgridPage('posLogTbl', defaultPageNo),
			source : $('#sourceQuery').combobox('getValue')
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#posLogTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}


/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnExportPosLog", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PosLogDTO", 	                        /*must*/// type of export
	"toUrl"			: "exportPosLog" 			            /*must*/// the export url
};

var filterConStr = [	// filter the result by query condition
		{'name':'merName', 	'value': "escapePeculiar($.trim($('#merNameQuery').val()))"			},	// eval
		{'name':'code',	'value': "escapePeculiar($.trim($('#posCodeQuery').val()))"		},
		{'name':'operName',	'value': "escapePeculiar($.trim($('#operNameQuery').val()))"   },
		{'name':'source',	'value': "escapePeculiar($('#sourceQuery').combobox('getValue'))"	}
		
	];


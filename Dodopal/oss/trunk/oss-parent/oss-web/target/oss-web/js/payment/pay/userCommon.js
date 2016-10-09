$(function() {
	
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#userCommonTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initUserCommonTbl,
		toolbar : [ true, 'top' ]
	});
}





function initDetailComponent() {
}

function initUserCommonTbl(size) {
	var option = {
		datatype : function(pdata) {
			findUserCommon();
		},
		colNames : [ 'id','用户名', '姓名',  '支付类型', '支付方式名称', '服务费率(‰)', '启用标识'  ],
		colModel : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'userCode',
			index : 'userCode',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'userName',
			index : 'userName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'payType',
			index : 'payType',
			width : 100,
			align : 'left',
			sortable : false,
            formatter: function(cellval, el, rowData) {
				if(cellval == '0') {return '账户支付'} 
				else if(cellval == '1') {return '一卡通支付'	} 
				else if(cellval == '2') {return '在线支付'	} 
				else if(cellval == '3') {return '银行支付'	} 
				else { return ''};
			}
		}, {
			name : 'payWayName',
			index : 'payWayName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'payServiceRate',
			index : 'payServiceRate',
			width : 100,
			align : 'right',
			sortable : false
		}, {
			name : 'activate',
			index : 'activate',
			width : 60,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
				if(cellval == '0') {return '启用'} 
				else if(cellval == '1') {return '停用'	} 
				else { return ''};
			}
		}
		],
		height : size.height - 50,
		width : size.width,
		multiselect : true,
		pager : '#userCommonTblPagination',
		toolbar : [ true, "top" ]
	};

	option = $.extend(option, jqgrid_server_opts);
	$('#userCommonTbl').jqGrid(option);
	$('#t_userCommonTbl').append($('#userCommonTblToolbar'));
}

// 查询
function findUserCommon(defaultPageNo) {
	var CommonQuery = {
		userName : escapePeculiar($.trim($('#userNameQuery').val())),
		userCode : '',
		payType : $('#payTypeQuery').combobox('getValue'),
		payWayName : escapePeculiar($.trim($('#payWayNameQuery').val())),
		activate : $('#activateQuery').combobox('getValue'),
		page : getJqgridPage('userCommonTbl', defaultPageNo)
	}
	ddpAjaxCall({
		url : "findPayAwayCommon",
		data : CommonQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#userCommonTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}


// 删除
function deletePayExternal() {
	var selarrrow = $("#userCommonTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要删除吗？",
				function(r) {
					if (r) {
						var id = new Array();
						for (var i = 0; i < selarrrow.length; i++) {
							var rowData = $("#userCommonTbl").getRowData(
									selarrrow[i]);
							id.push(rowData.id);
						}
						ddpAjaxCall({
							url : "deleteUserCommon",
							data : id,
							successFn : function(data) {
								if (data.code == "000000") {
									findUserCommon();
								} else {
									msgShow(systemWarnLabel, data.message,
											'warning');
								}
							}
						});
					}
				});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}

}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PayAwayCommonBean", 			/*must*/// type of export
	"toUrl"			: "exportPayAwayCommon" 			/*must*/// the export url
};
var filterConStr = [	// filter the result by query condition
		{'name':'userNameQuery',	'value': "escapePeculiar($.trim($('#userNameQuery').val()))"		},
		{'name':'payTypeQuery', 	'value': "escapePeculiar($.trim($('#payTypeQuery').combobox('getValue')))"			},	// eval
		{'name':'payWayNameQuery',	'value': "escapePeculiar($.trim($('#payWayNameQuery').val()))"		},
		{'name':'activateQuery',	'value': "escapePeculiar($('#activateQuery').combobox('getValue'))"		}
	];


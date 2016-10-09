function initMerchantUserSearchModel() {
	initMerchantUserSearchDialog();
	initMerchantUserListTbl();
}

function initMerchantUserSearchDialog() {
	$('#merchantUserSearchDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 700,
		height : 380
	});
}

function initMerchantUserListTbl() {
	var option = {
		datatype : function(pdata) {
			findMerchantUser();
		},
		colNames : ['customerId', 'customerType', '用户编码', '用户姓名'],
		colModel : [{name : 'customerId', index : 'customerId',hidden : true}, 
		            {name : 'customerType', index : 'customerType',hidden : true}, 
		            {name : 'customerNo',index : 'customerNo',width : 100,align : 'center',sortable : false}, 
		            {name : 'customerName',index : 'customerName',width : 100,align : 'center',sortable : false}
				],
		width : $('#merchantUserSearchDialog').width() - 2,
		height : $('#merchantUserSearchDialog').height() - 111,
		pager : '#merchantUserListTblPagination',
		ondblClickRow : function(rowid, iRow, iCol, e) {
			if (rowid) {
				var merchant = $('#merchantUserListTbl').getRowData(rowid);
				setSelectedMerchant(merchant);
				hideDialog('merchantUserSearchDialog');
			}
		}
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#merchantUserListTbl').jqGrid(option);
	$("#t_merchantUserListTbl").append($('#merchantUserListTblToolbar'));
}

function findMerchantUser() {
	var queryData = {};
	var queryType = $('#userQueryType').combobox('getValue');
	if ('custNo' == queryType) {
		queryData.customerNo = $('#merchantUserQuery').val();
	} else if ('custName' == queryType) {
		queryData.customerName = $('#merchantUserQuery').val();
	}
	queryData.customerType = '0';
	queryData.page = getJqgridPage('merchantUserListTbl');

	ddpAjaxCall({
		url : $.base + "/account/accountMgmt/findCustomerAccount",
		data : queryData,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#merchantUserListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function selectMerchatUser() {
	var selrow = $("#merchantUserListTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#merchantUserListTbl').getRowData(selrow);
		setSelectedMerchant(rowData);
		hideDialog('merchantUserSearchDialog');
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

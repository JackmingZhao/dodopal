function initMerchantSearchModel() {
	initMerchantSearchDialog();
	initMerchantListTbl();
}

function initMerchantSearchDialog() {
	$('#merchantSearchDialog').dialog({
				collapsible : false,
				modal : true,
				closed : true,
				closable : true,
				width : 700,
				height : 380
			});
}

function initMerchantListTbl() {
	var option = {
		datatype : function(pdata) {
			findMerchantName();
		},
		colNames : ['customerId', 'customerType', '商户编码', '商户名称', '商户类型'],
		colModel : [{name : 'customerId', index : 'customerId',hidden : true}, 
		            {name : 'customerType', index : 'customerType',hidden : true}, 
		            {name : 'customerNo',index : 'customerNo',width : 100,align : 'center',sortable : false}, 
		            {name : 'customerName',index : 'customerName',width : 100,align : 'center',sortable : false},
		            {
					name : 'merType',
					index : 'merType',
					width : 100,
					align : 'left',
					hidden : true,
					sortable : false,
					formatter : function(cellval, el, rowData) {
						if (cellval == 10) {
							return '代理商户';
						} else if (cellval == 11) {
							return '代理商户自有网点';
						} else if (cellval == 12) {
							return '连锁商户';
						} else if (cellval == 13) {
							return '连锁直营网点';
						} else if (cellval == 14) {
							return '连锁加盟网点';
						} else if (cellval == 15) {
							return '都都宝自有网点';
						} else if (cellval == 16) {
							return '集团商户';
						} else if (cellval == 18) {
							return '外接商户';
						} else {
							return '';
						}
					}
				}],
		width : $('#merchantSearchDialog').width() - 2,
		height : $('#merchantSearchDialog').height() - 111,
		pager : '#merchantListTblPagination',
		ondblClickRow : function(rowid, iRow, iCol, e) {
			if (rowid) {
				var merchant = $('#merchantListTbl').getRowData(rowid);
				setSelectedMerchant(merchant);
				hideDialog('merchantSearchDialog');
			}
		}
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#merchantListTbl').jqGrid(option);
	$("#t_merchantListTbl").append($('#merchantListTblToolbar'));
}

function findMerchantName() {
	var queryData = {};
	var queryType = $('#queryType').combobox('getValue');
	if ('code' == queryType) {
		queryData.customerNo = $('#merchantQuery').val();
	} else if ('name' == queryType) {
		queryData.customerName = $('#merchantQuery').val();
	}
	queryData.customerType = '1';
	queryData.page = getJqgridPage('merchantListTbl');

	ddpAjaxCall({
		url : $.base + "/account/accountMgmt/findCustomerAccount",
		data : queryData,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#merchantListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function selectMerchants() {
	var selrow = $("#merchantListTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#merchantListTbl').getRowData(selrow);
		setSelectedMerchant(rowData);
		hideDialog('merchantSearchDialog');
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

// 商户快速查询模块
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
	$("#merchantSearchDialog").panel({title:'商户名称'});
}

function initMerchantListTbl() {
		var option = {
				datatype : function (pdata) {
					findMerchantName();
			    },
		colNames : [ 'merchantId', '商户编码', '商户名称', '商户类型' ],
		colModel : [ { name : 'merchantId', index : 'merchantId', hidden : true }, 
		             { name : 'merCode', index : 'merCode', width : 100, align : 'center', sortable : false }, 
		             { name : 'merName', index : 'merName', width : 300, align : 'center', sortable : false }, 
		             { name : 'merType', index : 'merType', width : 100, align : 'left', sortable : false ,
				            formatter: function(cellval, el, rowData) {
				    				if(cellval == 10) {
				    					return '代理商户';
				    				} else if(cellval == 11){
				    					return '代理商户自有网点';
				    				}else if(cellval == 12){
				    					return '连锁商户';
				    				}else if(cellval == 13){
				    					return '连锁直营网点';
				    				}else if(cellval == 14){
				    					return '连锁加盟网点';
				    				}else if(cellval == 15){
				    					return '都都宝自有网点';
				    				}else if(cellval == 16){
				    					return '集团商户';
				    				}else if(cellval == 18){
				    					return '外接商户';
				    				}else{
				    					return '';
				    				}
				    			}	
							}
		            ],
		//sortname : 'merCode',
		width : $('#merchantSearchDialog').width() -2,
		height : $('#merchantSearchDialog').height() -111,
		pager : '#merchantListTblPagination',
//		rowNum : 30,
//		onSortCol : jqGridClientSort,
//		viewrecords : true,
		ondblClickRow : function(rowid, iRow, iCol,e) {
			if(rowid) {
				var merchant = $('#merchantListTbl').getRowData(rowid) ;
				setSelectedMerchant(merchant);
				//判断在OSS开户时是否为直营网点
				//findMerParentType(merchant);
				hideDialog('merchantSearchDialog');
			}
		}
	};
	//$('#t_merchantListTbl').append($('#merchantListTblToolbar'));
	 option = $.extend(option, jqgrid_server_opts);
	 $('#merchantListTbl').jqGrid(option);
	 $("#t_merchantListTbl").append($('#merchantListTblToolbar'));
}

function selectMerchat() {
	var selrow = $("#merchantListTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#merchantListTbl').getRowData(selrow);
		setSelectedMerchant(rowData);
		//判断在OSS开户时是否为直营网点
		//findMerParentType(rowData);
		hideDialog('merchantSearchDialog');
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}


//判断已审核的外接商户的商户名称
function findMerchantName() {
	var queryData = {};
	var queryType = $('#queryType').combobox('getValue');
	if ('code' == queryType) {
		queryData.merCode =  escapePeculiar($.trim($('#merchantQuery').val()));
	} else if ('name' == queryType) {
		queryData.merName =  escapePeculiar($.trim($('#merchantQuery').val()));
	}
	queryData.page = getJqgridPage('merchantListTbl');
	
	queryData.merType = '18';
	queryData.merState = '1';
	queryData.activate = '0';

	ddpAjaxCall({
		url : $.base + "/merchant/enterprise/findMerchantsPage",
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


/**
 * Title:判断连锁商是否是直营网点
 * Time:2015-07-18
 * Name:qjc
*/
function findMerParentType(merchant){
	var merParentCode ='';
	merParentCode =merchant.merCode;
	ddpAjaxCall({
					url : "findYktInfo",
					data : merParentCode,
					successFn : function(data) {
						if (data.code == "000000") {
							loadMerYKTinfo(data.responseEntity);
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}		
					}
				});
}

function loadMerYKTinfo(merBusRateBeanList){
	//先判断商户类型是否连锁商直营网点
	var merType =$('#merType').combobox('getValue');
	if(isNotBlank(merBusRateBeanList)) {
		if(merType =='13'){
			$('#yktTblLine').show();
			$('#yktTbl').setColProp('rate',{editable: false});
			$('#yktTbl').setColProp('rateType',{editable: false});
			loadJqGridData($('#yktTbl'), merBusRateBeanList);
			var selected = '';
			$.each(merBusRateBeanList, function(index, element) {
				if(selected != '') {
					selected += ',';
				}
				selected += element.proName;
			});
			$('#ykt').hide();
			$('#TKNameVerLine').show();
			$('#providerCode').val(selected);
			selectAllLines('yktTbl');
		}else{
			$('#TKNameVerLine').show();
			$('#ykt').show();
		}
	} else {
		$('#TKNameVerLine').hide();
		$('#ykt').hide();
		$('#yktTblLine').hide();
	}
}

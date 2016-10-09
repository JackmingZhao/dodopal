// 商户快速查询模块
function initposSearchModel() {
	initposSearchDialog();
	initposListTbl();
	
}

function initposSearchDialog() {
	$('#posSearchDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 700,
		height : 380
	});
	$("#posSearchDialog").panel({title:'pos编号'});
}

function initposListTbl() {
		var option = {
				datatype : function (pdata) {
					findpos();
			    },
		colNames : [ 'id', 'pos编码', '是否绑定', 'pos状态' ],
		colModel : [ { name : 'id', index : 'id', hidden : true }, 
		             { name : 'code', index : 'code', width : 100, align : 'center', sortable : false }, 
		             { name : 'bind', index : 'bind', width : 300, align : 'center', sortable : false,
		            	 formatter: function(cellval, el, rowData) {
		    				if(cellval == 0) {
		    					return '绑定';
		    				} else if(cellval == 1){
		    					return '未绑定';
		    				}else{
		    					return '';
		    				}
		    			}}, 
		             { name : 'status', index : 'status', width : 100, align : 'left', sortable : false ,
				            formatter: function(cellval, el, rowData) {
				    				if(cellval == 0) {
				    					return '启用';
				    				} else if(cellval == 1){
				    					return '停用';
				    				}else if(cellval == 2){
				    					return '作废';
				    				}else if(cellval == 3){
				    					return '消费封锁';
				    				}else if(cellval == 4){
				    					return '充值封锁';
				    				}else{
				    					return '';
				    				}
				    			}	
							}
		            ],
		//sortname : 'merCode',
		width : $('#posSearchDialog').width() -2,
		height : $('#posSearchDialog').height() -111,
		pager : '#posListTblPagination',
//		rowNum : 30,
//		onSortCol : jqGridClientSort,
//		viewrecords : true,
		ondblClickRow : function(rowid, iRow, iCol,e) {
			if(rowid) {
				var pos = $('#posListTbl').getRowData(rowid) ;
				setSelectedpos(pos);
				//判断在OSS开户时是否为直营网点
				//findMerParentType(merchant);
				hideDialog('posSearchDialog');
			}
		}
	};
	//$('#t_merchantListTbl').append($('#merchantListTblToolbar'));
	 option = $.extend(option, jqgrid_server_opts);
	 $('#posListTbl').jqGrid(option);
	 $("#t_posListTbl").append($('#posListTblToolbar'));
}

function selectPos() {
	var selrow = $("#posListTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#posListTbl').getRowData(selrow);
		setSelectedpos(rowData);
		//判断在OSS开户时是否为直营网点
		//findMerParentType(rowData);
		hideDialog('posSearchDialog');
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}


//查询非绑定状态的pos
function findpos() {
	var queryData = {};
	//var queryType = $('#queryType').combobox('getValue');
	//if ('code' == queryType) {
		//queryData.merCode =  escapePeculiar($.trim($('#posQuery').val()));
	//} else if ('name' == queryType) {
		//queryData.merName =  escapePeculiar($.trim($('#posQuery').val()));
	//}
	var merCode = $('#merCode').val();
	if(merCode==''){
		merCode = 'XXXXXXXXXXXXXXXX';
	}
	
	queryData.page = getJqgridPage('posListTbl');
	queryData.code = escapePeculiar($.trim($('#posQuery').val()));
	queryData.merCode = merCode;
	queryData.bind = '0';
	queryData.status = '';

	ddpAjaxCall({
		url : $.base + "/basic/pos/findPos",
		data : queryData,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#posListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}



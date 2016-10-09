$(function() {
	initMainComponent();
});
function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#crdSysLogTbl',
			offsetHeight :75,
			offsetWidth : 0
		},
		callback : initDataTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'dataDialog' ]
	});
}

function initDetailComponent() {
	initDataDialog();
}


function initDataTbl(size) {
	var option = {
			datatype : function (pdata) {
				findCrdSysLog();
		    },
		colNames : [ 'id','卡服务订单号', '产品库订单号', '交易状态', '业务类型', 
		             '类名称','方法名称','方法描述',"入参",
		             "出参","响应码","响应码描述","日志创建日期"],
		colModel : [ 
		{
			name : 'id',
			index : 'id',
			hidden : true,
			frozen:true
		}, {
			name : 'crdOrderNum',
			index : 'crdOrderNum',
			width : 160,
			align : 'left',
			sortable : false,
			frozen:true
		},{
			name : 'proOrderNum',
			index : 'proOrderNum',
			width : 160,
			align : 'left',
			sortable : false
		}, 
		{
			name : 'tradeType',
			index : 'tradeType',
			width : 100,
			align : 'left',
			sortable : false,
			formatter:function(cellval, el, rowData) {
 				if(cellval == '1') {return '失败'} 
 				else if(cellval == '0') {return '成功'	} 
 				else { return ''};
 			}
		}, {
			name : 'txnType',
			index : 'txnType',
			width : 100,
			align : 'left',
			sortable : false,
			formatter:function(cellval, el, rowData) {
 				if(cellval == '1') {return '一卡通充值'} 
 				else if(cellval == '2') {return '一卡通消费'	} 
 				else { return ''};
 			}
		}, {
			name : 'className',
			index : 'className',
			width : 250,
			align : 'left',
			sortable : false
		},{
			name : 'methodName',
			index : 'methodName',
			width : 150,
			align : 'left',
			sortable : false
		}, {
			name : 'description',
			index : 'description',
			width : 200,
			align : 'left',
			sortable : false
		},{
			name : 'inParas',
			index : 'inParas',
			width : 200,
			align : 'left',
			sortable : false
		
		},  {
			name : 'outParas',
			index : 'outParas',
			width : 180,
			align : 'left',
			sortable : false
		},  {
			name : 'respCode',
			index : 'respCode',
			width : 180,
			align : 'left',
			sortable : false
		},  {
			name : 'respExplain',
			index : 'respExplain',
			width : 150,
			align : 'left',
			sortable : false
		}, {
			name : 'createDate',
			index : 'createDate',
			width : 170,
			align : 'left',
			sortable : false,
			formatter: cellDateFormatter
		
		}],
		//caption : "用户列表",
		//sortname : 'name',
		height : size.height -75,
		width : size.width,
		nowarp : false,
		autowidth : true,
		shrinkToFit : false,
		pager : '#crdSysLogTblPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#crdSysLogTbl').jqGrid(option);
	 $("#crdSysLogTbl").jqGrid('setFrozenColumns');
}
function findCrdSysLog(defaultPageNo){
	var query = {
			crdOrderNum : escapePeculiar($.trim($('#crdOrderNumQuery').val())),
			proOrderNum : escapePeculiar($.trim($('#proOrderNumQuery').val())),
			txnType:$('#txnTypeQuery').combobox('getValue'),
			page : getJqgridPage('crdSysLogTbl', defaultPageNo),
			createDateStart : $('#createDateStart').val(),
			createDateEnd : $('#createDateEnd').val()
		}

		ddpAjaxCall({
			url : "findCrdSysLogByPage",
			data : query,
			successFn : function(data) {
				if (data.code == "000000") {
					loadJqGridPageData($('#crdSysLogTbl'), data.responseEntity);
				}else{
					msgShow(systemWarnLabel, data.message, 'warning');
				}	
			}
		});
	
}
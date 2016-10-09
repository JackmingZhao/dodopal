$(function() {
	initMainComponent();
});
function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#collectLogTbl',
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
				findCollectLog();
		    },
		colNames : [ 'id','订单号', '交易流水号', '服务名称', '日期', 
		             '线程名','类名',"异常堆栈",
		             "消息体","响应码","响应码描述","唯一标识","入参","出参",
		             "交易开始时间","交易结束时间","方法","方法描述","交易状态","来源","Ip地址"
		             ],
		colModel : [ 
		{
			name : 'id',
			index : 'id',
			hidden : true,
			frozen:true
		}, {
			name : 'orderNum',
			index : 'orderNum',
			width : 150,
			align : 'left',
			sortable : false,
			frozen:true
		},{
			name : 'tranNum',
			index : 'tranNum',
			width : 150,
			align : 'left',
			sortable : false
		}, 
		{
			name : 'serverName',
			index : 'serverName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'logDate',
			index : 'logDate',
			width : 140,
			align : 'left',
			sortable : false,
			formatter: cellDateFormatter
		}, {
			name : 'threadName',
			index : 'threadName',
			width : 250,
			align : 'left',
			sortable : false,
		},{
			name : 'className',
			index : 'className',
			width : 500,
			align : 'left',
			sortable : false
		}, {
			name : 'stackTrace',
			index : 'stackTrace',
			width : 800,
			align : 'left',
			sortable : false
		
		},  {
			name : 'message',
			index : 'message',
			width : 220,
			align : 'left',
			sortable : false
		},  {
			name : 'repCode',
			index : 'repCode',
			width : 70,
			align : 'left',
			sortable : false
		
		},  {
			name : 'repMessage',
			index : 'repMessage',
			width : 150,
			align : 'left',
			sortable : false
		},{
			name : 'uniqueCode',
			index : 'uniqueCode',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'inParas',
			index : 'inParas',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'outParas',
			index : 'outParas',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'tradeStart',
			index : 'tradeStart',
			width : 140,
			align : 'left',
			sortable : false
		},{
			name : 'tradeEnd',
			index : 'tradeEnd',
			width : 140,
			align : 'left',
			sortable : false
		},{
			name : 'methodName',
			index : 'methodName',
			width : 180,
			align : 'left',
			sortable : false
		},{
			name : 'methodDes',
			index : 'methodDes',
			width : 180,
			align : 'left',
			sortable : false
		},{
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
		},{
			name : 'source',
			index : 'source',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'ipAddr',
			index : 'ipAddr',
			width : 100,
			align : 'left',
			sortable : false
		}
		
		],
		//caption : "用户列表",
		//sortname : 'name',
		height : size.height - 75,
		width : size.width,
		autowidth : true,
		shrinkToFit : false,
		striped:true,
		pager : '#collectLogTblPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#collectLogTbl').jqGrid(option);
	// $("#collectLogTbl").jqGrid('setFrozenColumns');
}
function findCollectLog(defaultPageNo){
	var query = {
			orderNum : escapePeculiar($.trim($('#orderNumQuery').val())),
			serverName : escapePeculiar($.trim($('#serverNameQuery').val())),
			source : escapePeculiar($.trim($('#sourceQuery').val())),
			tranNum : escapePeculiar($.trim($('#tranNumQuery').val())),
			tradeType:$('#tradeTypeQuery').combobox('getValue'),
			page : getJqgridPage('collectLogTbl', defaultPageNo),
		}

		ddpAjaxCall({
			url : "findLogInfoByPage",
			data : query,
			successFn : function(data) {
				if (data.code == "000000") {
					loadJqGridPageData($('#collectLogTbl'), data.responseEntity);
				}else{
					msgShow(systemWarnLabel, data.message, 'warning');
				}	
			}
		});
	
}
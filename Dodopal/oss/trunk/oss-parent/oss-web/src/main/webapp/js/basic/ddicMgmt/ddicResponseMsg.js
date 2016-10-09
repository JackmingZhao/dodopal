$(function() {
	initMainComponent();
	initDetailComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});

var ddicResMsgData = {
		colNames : ['ddicResMsgId', '消息编码', '消息类型', '消息內容', '创建用户','创建时间', '更新用户',  '更新时间'],
		colModel : [
		            {name : 'ddicResMsgId', hidden : true},
		            {name : 'msgCode', index : 'msgCode', width : 100, align : 'left', sortable : false},
		            {name : 'msgType', index : 'msgType', width : 60, align : 'left', sortable : false},
		            {name : 'message', index : 'message', width : 140, align : 'left', sortable : false},
		            {name : 'createUser', index : 'createUser', width : 100, align : 'left', sortable : false},
		            {name : 'createDate', index : 'createDate', width : 100, align : 'left', sortable : false, formatter: cellDateFormatter},
		            {name : 'updateUser', index : 'updateUser', width : 100, align : 'left', sortable : false},
		            {name : 'updateDate', index : 'updateDate', width : 100, align : 'left', sortable : false, formatter: cellDateFormatter}
		            ]
};

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#ddicResMsgTbl',
			offsetHeight : 90,
			offsetWidth : 0
		},
		callback : initDdicResMsgTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'ddicResMsgDialog' ]
	});
}

function initDetailComponent() {
	initDdicResMsgDialog();
}

function initDdicResMsgTbl(size) {
	var option = {
		datatype : function (pdata) {
			findDdicResMsg();
	    },
	    colNames : ddicResMsgData.colNames,
		colModel : ddicResMsgData.colModel,
		height : size.height - 70,
		width : size.width,
		multiselect: false,
		pager : '#ddicResMsgTblPagination',
		toolbar : [ true, "top" ]
	};

	option = $.extend(option, jqgrid_server_opts);
	$('#ddicResMsgTbl').jqGrid(option);

	$("#t_ddicResMsgTbl").append($('#ddicResMsgTblToolbar'));
}

/** 查询 按页 */
function findDdicResMsg(defaultPageNo) {
	var ddicResMsg = {
		msgCode : escapePeculiar($.trim($('#ddicResMsgCodeQuery').val())),
		msgType : escapePeculiar($.trim($('#ddicResMsgTypeQuery').val())),

		page : getJqgridPage('ddicResMsgTbl', defaultPageNo)
	};
	
	ddpAjaxCall({
		url : "findDdicResMsgByPage",
		data : ddicResMsg,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#ddicResMsgTbl'), data.responseEntity);
			}else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}		
		}
	});
}

function initDdicResMsgDialog() {
	$('#ddicResMsgDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 300
	});
}

function addDdicResMsg() {
	clearForm();
	$("#msgCode").attr({
        disabled:false  
    });

	showDdicResMsgDialog();
}

/** 新增后的保存 */
function saveDdicResMsg() {
	if(isValidate('ddicResMsgDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存数据字典信息吗？", function(r) {
			if (r) {
				var ddicResMsg = {
					id : $('#ddicResMsgId').val(),
					msgCode : $('#msgCode').val(),
					msgType : $('#msgType').val(),
					message : $('#message').val()
				};
				ddpAjaxCall({
					url : "saveDdicResMsg",
					data : ddicResMsg,
					successFn : afterSaveDdicResMsg
				});
			}
		});
	}
}

/** 批量删除  需要在页面上关掉 在用的时候再在JS里打开 */
function deleteDdicResMsg() {
	var selarrrow = $("#ddicResMsgTbl").getGridParam('selarrrow');
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "您确认此字典未被引用，并进行删除吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#ddicResMsgTbl").getRowData(selarrrow[i]);
					ids.push(rowData.ddicResMsgId);
				}
				ddpAjaxCall({
					url : "deleteDdicResMsg",
					data : ids,
					successFn : afterDeleteDdicResMsg
				});
			}
		});
	}else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

/** 编辑修改 */
function editDdicResMsg() {
	var selarrrow = $("#ddicResMsgTbl").getGridParam('selarrrow');
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#ddicResMsgTbl").getGridParam('selrow');
	if(selrow) {
		var rowData = $('#ddicResMsgTbl').getRowData(selrow);
		$('#ddicResMsgId').val(rowData.ddicResMsgId);
		$('#msgCode').val(rowData.msgCode);
		//$('#code').attr('disable',true);
		$("#msgCode").attr({  
            disabled:true  
        });
		$('#msgType').val(rowData.msgType);
		$('#createUser').val(rowData.createUser);
		$('#createDate').val(rowData.createDate);
		$('#updateUser').val(rowData.updateUser);
		$('#updateDate').val(rowData.updateDate);
		$('#message').val(rowData.message);

		showDdicResMsgDialog();
	}else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

//查看数据字典详情
function viewDdicResMsg(){
	var selarrrow = $("#ddicResMsgTbl").getGridParam('selarrrow');
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#ddicResMsgTbl").getGridParam('selrow');
	if(selrow) {
		var rowData = $('#ddicResMsgTbl').getRowData(selrow);

		ddpAjaxCall({
			url : "viewDdicResMsg",
			data : rowData.ddicResMsgId,
			successFn : loadViewDdicResMsg
		});
	}else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function clearForm() {
	clearAllText('ddicResMsgDialog');
	$('#msgCode').attr('readonly',false);
	$('#msgCode').attr('disable',false);
	$('#ddicResMsgId').val('');
	$('#msgCode').val('');
	$('#msgType').val('');
	$('#message').val('');
}

function loadViewDdicResMsg(data){
	if(data.code=="000000"){
		showViewDdicResMsg();
		var ddicResMsgBean = data.responseEntity;

		$('#viewMsgCode').html(ddicResMsgBean.msgCode);
		$('#viewMsgType').html(ddicResMsgBean.msgType);
		$('#viewMessage').html(ddicResMsgBean.message);
		$('#viewCreateUser').html(ddicResMsgBean.createUser);
		$('#viewCreateDate').html(ddicResMsgBean.viewCreateDate);
		$('#viewUpdateUser').html(ddicBean.updateUser);
		$('#viewUpdateDate').html(ddicBean.viewUpdateDate);
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function afterSaveDdicResMsg(data) {
	if (data.code == "000000") {
		hideDdicResMsgDialog();
		findDdicResMsg();
	}else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function afterDeleteDdicResMsg(data) {
	if(data.code == "000000") {
		findDdicResMsg();
	}else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function showDdicResMsgDialog() {
	$('#ddicResMsgDialog').show().dialog('open');
}

function hideDdicResMsgDialog() {
	clearForm();
	$('#ddicResMsgDialog').hide().dialog('close');
}

function showViewDdicResMsg(){
	$('#viewDdicResMsgDialog').show().dialog('open');
}

function closeViewDdicResMsg(){
	$('#viewDdicResMsgDialog').show().dialog('close');
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "exportDdicResMsg", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "ddicResponseMsg", 	/*must*/// type of export
	"toUrl"			: "exportExcelDdicResMsg" 			/*must*/// the export url
};
var filterConStr = [	// filter the result by query condition
		{'name':'ddicMsgCode', 	'value': "escapePeculiar($.trim($('#ddicResMsgCodeQuery').val()))"			},	// eval
		{'name':'ddicMsgType',	'value': "escapePeculiar($.trim($('#ddicResMsgTypeQuery').val()))"		}
];


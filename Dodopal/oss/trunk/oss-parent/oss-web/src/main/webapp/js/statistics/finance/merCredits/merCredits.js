$(function() {
	initMainComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#traFlowTbl',
			offsetHeight : 100,
			offsetWidth : 0
		},
		callback : initTraFlowTbl,
		toolbar : [ true, 'top' ],
	});
	$("#createDateStart").click(function (){
		WdatePicker({dateFmt:'yyyy-MM-dd'})//,maxDate:'%y-%M-#{%d}',minDate:'%y-%M-#{%d-7}'
	});
	$("#createDateEnd").click(function (){
		WdatePicker({dateFmt:'yyyy-MM-dd'})//,maxDate:'%y-%M-#{%d}',minDate:'%y-%M-#{%d-7}' 
	});
}

function clearQuery(){
	clearAllText('traQueryCondition');
	initDateQuery();
}
function initDateQuery(){
	$("#createDateStart").val(formatDate(new Date(new Date()-24*60*60*1000*7),"yyyy-MM-dd"));
	$("#createDateEnd").val(formatDate(new Date(),"yyyy-MM-dd"));
}

function initTraFlowTbl(size) {
		initDateQuery();
		var option = {
		datatype : function(pdata) {
			findData();
		},
//		收款日期（交易创建时间）、通道分类（支付类型）、收款通道（支付方式）、
//		客户分类、客户编码、客户名称、打款人（关联商户表打款人）、上级商户、客户城市、购额度金额（交易金额）、通道手续费、应收金额
		colNames : [ 'id', '收款日期', '通道分类', '收款通道', '客户分类',
				'客户编码', '客户名称','打款人', '上级商户', '客户城市', '购额度金额（元）','通道手续费（元）','应收金额（元）' ],
		colModel : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'createDate',
			index : 'createDate',
			width : 140,
			align : 'left',
			sortable : false,
			formatter : cellDateFormatter
		}, {
			name : 'payType',
			index : 'payType',
			width : 80,
			align : 'left',
			sortable : false
		},{
			name : 'payWayName',
			index : 'payWayName',
			width : 80,
			align : 'left',
			sortable : false
		},{
			name : 'userType',
			index : 'userType',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'merOrUserCode',
			index : 'merOrUserCode',
			width : 150,
			align : 'left',
			sortable : false
		}, {
			name : 'merOrUserName',
			index : 'merOrUserName',
			width : 120,
			align : 'left',
			sortable : false
		}, {
//	打款人（关联商户表打款人）、上级商户、客户城市、购额度金额（交易金额）、通道手续费、应收金额
			name : 'merPayMoneyUser',
			index : 'merPayMoneyUser',
			width : 70,
			align : 'left',
			sortable : false
		}, {
			name : 'merParentName',
			index : 'merParentName',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'merCityName',
			index : 'merCityName',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'realTranMoney',
			index : 'realTranMoney',
			width : 110,
			align : 'left',
			sortable : false
		},{
			name : 'payProceRate',
			index : 'payProceRate',
			width : 110,
			align : 'right',
			sortable : false
		} , {
			name : 'amountMoney',
			index : 'amountMoney',
			width : 110,
			align : 'left',
			sortable : false
			
		}],
		height : size.height - 100,
		width : size.width,
		multiselect : false,
		shrinkToFit : false,
		pager : '#traFlowTblPagination',
		toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#traFlowTbl').jqGrid(option);
	$("#t_traFlowTbl").append($('#traFlowTblToolbar'));
}

function findData(defaultPageNo) {
	var payTypeQuery = $('#payTypeQuery').combobox('getValue');
	if($("#createDateStart").val()==""||$("#createDateEnd").val()==""){
		msgShow(systemWarnLabel, "请选择充值日期", 'info');
		return;
	}
	
	if(compareDates($("#createDateStart").val(),"yyyy-MM-dd",$("#createDateEnd").val(),"yyyy-MM-dd")==1){
		var temp = $("#createDateStart").val();
		$("#createDateStart").val($("#createDateEnd").val());
		$("#createDateEnd").val(temp);
	}
	
	var traQuery = {
		merOrUserName : escapePeculiar($.trim($('#customerNameQuery').val())),
		userType : $('#customerTypeQuery').combobox('getValue'),
		payType : payTypeQuery,
		createDateStart : $('#createDateStart').val(),
		createDateEnd : $('#createDateEnd').val(),
		page : getJqgridPage('traFlowTbl', defaultPageNo)
	}
	
	ddpAjaxCall({
		url : "findMerCredits",
		data : traQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#traFlowTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}



/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "exportTra", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "MerCredits", 	/*must*/// type of export
	"toUrl"			: "exportMerCredits", 			/*must*/// the export url
	"parDiaHeight"	: "310"
};

var filterConStr = [	// filter the result by query condition
		{'name':'merOrUserName',	'value': "escapePeculiar($.trim($('#customerNameQuery').val()))"   },
		{'name':'userType',	'value': "$('#customerTypeQuery').combobox('getValue')"	},
		{'name':'createDateStart',	'value': "escapePeculiar($.trim($('#createDateStart').val()))"	},
		{'name':'createDateEnd',	'value': "escapePeculiar($.trim($('#createDateEnd').val()))"	}
	];

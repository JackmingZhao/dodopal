$(function() {
	initMainComponent();
	initDetailComponent();
	// findTraFlow();
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
}

function initDetailComponent() {
	// showViewTraFlow();
	initMoney();
}

function initMoney() {
	$("#realMinTranMoneyQuery").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#realMinTranMoneyQuery").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#realMinTranMoneyQuery").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	
	
	$("#realMaxTranMoneyQuery").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#realMaxTranMoneyQuery").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#realMaxTranMoneyQuery").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	
}


function initTraFlowTbl(size) {
	var option = {
		datatype : function(pdata) {
			findTraFlow();
		},
		colNames : [ 'id', '交易流水号', '订单号', '商户名|用户名', '业务类型',
				'交易类型', '应付金额（元）','实付金额（元）', '外部交易状态', '内部交易状态', '创建时间' ],
		colModel : [ {name : 'id',hidden : true,frozen:true}, 
		             {name : 'tranCode',index : 'tranCode',width : 210,align : 'left',sortable : false,frozen:true}, 
		             {name : 'orderNumber',index : 'orderNumber',width : 210,align : 'left',sortable : false},
		             {name : 'merOrUserName',index : 'merOrUserName',width : 150,align : 'left',sortable : false},
		             {name : 'businessType',index : 'businessType',width : 80,align : 'left',sortable : false},
		             {name : 'tranType',index : 'tranType',width : 80,align : 'left',sortable : false},  
		             {name : 'amountMoney',index : 'amountMoney',width : 100,align : 'right',sortable : false}, 
		             {name : 'realTranMoney',index : 'realTranMoney',width : 100,align : 'right',sortable : false}, 
					 {name : 'tranOutStatus',index : 'tranOutStatus',width : 90,align : 'left',sortable : false}, 
					 {name : 'tranInStatus',index : 'tranInStatus',width : 100,align : 'left',sortable : false},
					 {name : 'createDate',index : 'createDate',width : 140,align : 'left',sortable : false,formatter : cellDateFormatter} 
		],
		height : size.height - 100,
		width : size.width,
		multiselect : false,
		shrinkToFit : false,
		pager : '#traFlowTblPagination',
		toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#traFlowTbl').jqGrid(option);
	$("#traFlowTbl").jqGrid('setFrozenColumns');  
	$("#t_traFlowTbl").append($('#traFlowTblToolbar'));
}

function findTraFlow(defaultPageNo) {
	var tranOutStatusQuery = $('#tranOutStatusQuery').combobox('getValue');
	var tranInStatusQuery = $('#tranInStatusQuery').combobox('getValue');
	var businessTypeQuery = $('#businessTypeQuery').combobox('getValue');
	var traQuery = {
		tranOutStatus : tranOutStatusQuery,
		tranInStatus : tranInStatusQuery,
		merOrUserName : escapePeculiar($.trim($('#merOrUserNameQuery').val())),
		tranCode : escapePeculiar($.trim($('#tranCodeQuery').val())),
		orderNumber : escapePeculiar($.trim($('#orderNumQuery').val())),
		businessType : businessTypeQuery,
		realMinTranMoney : $('#realMinTranMoneyQuery').val(),
		realMaxTranMoney : $('#realMaxTranMoneyQuery').val(),
		createDateStart : $('#createDateStart').val(),
		createDateEnd : $('#createDateEnd').val(),
		page : getJqgridPage('traFlowTbl', defaultPageNo)
	}
	var beginDate = traQuery.createDateStart;
	var endDate = traQuery.createDateEnd;
	var min = traQuery.realMinTranMoney;
	var max = traQuery.realMaxTranMoney;
	//console.log("min="+min+"max="+max);
	compareTime(traQuery,beginDate,endDate);
	compareMoney(traQuery,min,max);
	
	ddpAjaxCall({
		url : "findTraFlow",
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

function compareTime(query,beginDate,endDate){
	 var d1 = new Date(beginDate.replace(/\-/g, "\/")); 
	 var d2 = new Date(endDate.replace(/\-/g, "\/")); 

	  if(beginDate!=""&&endDate!=""&&d1 >=d2) 
	 { 
	  var temp = endDate;
	  endDate = beginDate;
	  beginDate = temp;
	  $('#createDateStart').val(beginDate)
	  $('#createDateEnd').val(endDate)
	  query.createDateStart = beginDate;
	  query.createDateEnd = endDate;
	 }
}

function compareMoney(query,min,max){
	if(min != "" && max != "" && parseFloat(min)>parseFloat(max)){
		var temp = max;
		max = min;
		min = temp;
		//console.log("=========min="+min+"max="+max);
		$('#realMinTranMoney').val(min);
		$('#realMaxTranMoney').val(max);
		query.realMinTranMoney = min;
		query.realMaxTranMoney = max;
	}
}

// 查看用户详情
function viewTraFlow() {
	var selarrrow = $("#traFlowTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#traFlowTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#traFlowTbl').getRowData(selrow);
		ddpAjaxCall({
			url : "findTraFlowByTraCode",
			data : rowData.id,
			successFn : loadViewTraFlow
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function loadViewTraFlow(data) {
	if (data.code == "000000") {
		showViewTraFlow();
		var TraTransactionBean = data.responseEntity;
		$('#oldTranCodeView').html(TraTransactionBean.oldTranCode);
		$('#tranCodeView').html(TraTransactionBean.tranCode);
		$('#tranNameView').html(TraTransactionBean.tranName);
		 $('#tranOutStatusView').html(TraTransactionBean.tranOutStatus);
	
		 $('#tranInStatusView').html(TraTransactionBean.tranInStatus);
		
		$('#pageCallbackUrlView').html(TraTransactionBean.pageCallbackUrl);
		$('#serviceNoticeUrlView').html(TraTransactionBean.serviceNoticeUrl);
		$('#merOrUserCodeView').html(TraTransactionBean.merOrUserCode);
		$('#userTypeView').html(TraTransactionBean.userType);
		
		$('#tranTypeView').html(TraTransactionBean.tranType);
		
		$('#amountMoneyView').html(TraTransactionBean.amountMoney+"元");
		$('#payMoneyView').html(TraTransactionBean.amountMoney+"元");
		$('#commentsView').html(TraTransactionBean.comments);
		$('#sourceView').html(TraTransactionBean.source);

		$('#orderNumberView').html(TraTransactionBean.orderNumber);
		$('#commodityView').html(TraTransactionBean.commodity);
		 $('#businessTypeView').html(TraTransactionBean.businessType);
		
		$('#orderDateView')
				.html(ddpDateFormatter(TraTransactionBean.orderDate));

		 $('#payTypeView').html(TraTransactionBean.payType);
		

		$('#payWayView').html(TraTransactionBean.payWayName);
		
		$('#payServiceTypeView').html(TraTransactionBean.payServiceType);
		$('#payServiceRateView').html(TraTransactionBean.payServiceRate);

		$('#payServiceFeeView').html(TraTransactionBean.payServiceFee+"元");
		$('#payProceRateView').html(TraTransactionBean.payProceRate+"‰");
		$('#payProceFeeView').html(TraTransactionBean.payProceFee+"元");
		$('#realTranMoneyView').html(TraTransactionBean.realTranMoney+"元");
		$('#clearFlagView').html(TraTransactionBean.clearFlagView);
		
		$('#turnOutMerCodeView').html(TraTransactionBean.turnOutMerCode);
		$('#turnIntoMerCodeView').html(TraTransactionBean.turnIntoMerCode);
		$('#createDateView').html(
				ddpDateFormatter(TraTransactionBean.createDate));
		
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

//导出
//function exportTra() {
//	$.fileDownload('exportTransaction', {
//		data: $('#traForm').serialize(),
//		failCallback: function() {
//			msgShow(systemWarnLabel, "文件导出出错", 'warning');
//		}
//	})
//}

function showViewTraFlow() {
	$('#viewTraFlowDialog').show().dialog('open');
}

function closeViewTraFlow() {
	$('#viewTraFlowDialog').show().dialog('close');
}



/* 以下为校验费率js */
function clearNoNumOnBlur(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 9 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		} else {
			var textChar = text.charAt(text.length - 1);
			if (textChar == ".") {
				text = text.substring(0, text.length - 1);
			}
		}
		obj.val(text);
	}
}

function checkDecimal(obj, startWhole, endWhole, startDec, endDec) {
	var rate = obj.val();
	var re;
	var posNeg;
	re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
			+ startDec + "," + endDec + "})?$");
	posNeg = /^[+]?]*$/;

	if (!re.test(rate) && !posNeg.test(rate)) {
		rate = "";
		obj.focus();
		return false;
	}
}

function clearNoNum(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		}
		obj.val(text);
	}
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "exportTra", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PayTraTransactionDTO", 	/*must*/// type of export
	"toUrl"			: "exportTransaction", 			/*must*/// the export url
	"parDiaHeight"	: "410"
};
var filterConStr = [	// filter the result by query condition
		{'name':'tranOutStatus', 	'value': "escapePeculiar($('#tranOutStatusQuery').combobox('getValue'))"			},	// eval
		{'name':'tranInStatus',	'value': "escapePeculiar($('#tranInStatusQuery').combobox('getValue'))"		},
		{'name':'merOrUserName',	'value': "escapePeculiar($.trim($('#merOrUserNameQuery').val()))"   },
		{'name':'tranCode',	'value': "escapePeculiar($.trim($('#tranCodeQuery').val()))"	},
		{'name':'orderNumber',	'value': "escapePeculiar($.trim($('#orderNumQuery').val()))"	},
		{'name':'businessType',	'value': "escapePeculiar($('#businessTypeQuery').combobox('getValue'))"	},
		{'name':'realMinTranMoney',	'value': "escapePeculiar($.trim($('#realMinTranMoneyQuery').val()))"	},
		{'name':'realMaxTranMoney',	'value': "escapePeculiar($.trim($('#realMaxTranMoneyQuery').val()))"	},
		{'name':'createDateStart',	'value': "escapePeculiar($.trim($('#createDateStart').val()))"	},
		{'name':'createDateEnd',	'value': "escapePeculiar($.trim($('#createDateEnd').val()))"	}
	];










function initDateQuery() {
	$("#afterProceRateDateStart").val(formatDate(new Date(), "yyyy-MM-dd"));
	$("#afterProceRateDateEnd").val(formatDate(new Date(), "yyyy-MM-dd"));
}
$(function() {
	initDateQuery();
	initMainComponent();

	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function clearCrdAllText() {
	clearAllText('crdSysOrderQueryCondition');
	initDateQuery();
}

function initMainComponent() {
	findCardOrderStates();
	findCardType();
	findCrdBeforeorderStates();
	checkTxnAmt();
	autoResize({
		dataGrid : {
			selector : '#crdConsumeOrderTbl',
			offsetHeight : 100,
			offsetWidth : 0
		},
		callback : initCrdConsumeOrderTbl,
		toolbar : [ true, 'top' ]
	});

	$('#crdOrderStates').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 150
	});
	$('#crdBeforeorderStates').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 150
	});
	$('#cardType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 150
	});
}

function initCrdConsumeOrderTbl(size) {
	var option = {
		datatype : function(pdata) {
			findCrdSysOrder();
		},
		colNames : [ 'ID', '卡服务订单号', '产品库主订单号', 'POS号', '卡号', '卡类型', '交易金额(元)',
				'交易前金额(元)', '交易后金额(元)', '订单状态', '订单前状态', '交易时间' ],
		colModel : [ {
			name : 'id',
			hidden : true,
			frozen : true
		}, {
			name : 'crdOrderNum',
			index : 'crdOrderNum',
			width : 180,
			align : 'left',
			sortable : false,
			frozen : true
		}, {
			name : 'proOrderNum',
			index : 'proOrderNum',
			width : 180,
			align : 'left',
			sortable : false
		}, {
			name : 'posCode',
			index : 'posCode',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'checkCardNo',
			index : 'checkCardNo',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'cardTypeView',
			index : 'cardTypeView',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'txnAmtView',
			index : 'txnAmtView',
			width : 160,
			align : 'left',
			sortable : false
		}, {
			name : 'befbalView',
			index : 'befbalView',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'blackAmtView',
			index : 'blackAmtView',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'crdOrderStatesView',
			index : 'crdOrderStatesView',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'crdBeforeorderStatesView',
			index : 'crdBeforeorderStatesView',
			width : 160,
			align : 'left',
			sortable : false
		}, {
			name : 'txnDateTime',
			index : 'txnDateTime',
			width : 180,
			align : 'left',
			sortable : false,
			formatter : cellDateFormatter
		} ],
		height : size.height - 100,
		width : size.width,
		multiselect : false,
		autowidth : true,
		shrinkToFit : false,
		pager : '#crdSysOrderTblPagination',
		toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#crdConsumeOrderTbl').jqGrid(option);
	$("#t_crdConsumeOrderTbl").append($('#crdConsumeOrderTblToolbar'));
	$("#crdConsumeOrderTbl").jqGrid('setFrozenColumns');
}

// 查询卡服务订单
function findCrdSysOrder(defaultPageNo) {
	var crdOrderStates = $('#crdOrderStates').combobox('getValue');
	var cardType = $('#cardType').combobox('getValue');
	var crdBeforeorderStates = $('#crdBeforeorderStates').combobox('getValue');
	var txnAmtStart = $('#txnAmtStart').val();
	var txnAmtEnd = $('#txnAmtEnd').val();
	if (txnAmtStart) {
		txnAmtStart = txnAmtStart * 100;
	}
	if (txnAmtEnd) {
		txnAmtEnd = txnAmtEnd * 100;
	}
	var dataQuery = {
		crdOrderNum : escapePeculiar($.trim($('#crdOrderNum').val())), // 卡服务订单号
		proOrderNum : escapePeculiar($.trim($('#proOrderNum').val())), // 产品库主订单号
		crdOrderStates : crdOrderStates, // 卡服务订单交易状态
		crdBeforeorderStates : crdBeforeorderStates, // 卡服务订单前交易状态
		posCode : escapePeculiar($.trim($('#posCode').val())), // POS号
		checkCardNo : escapePeculiar($.trim($('#checkCardNo').val())), // 卡号(验卡卡号)
		// cardFaceNo : $('#cardFaceNo').val(), //卡面号
		cardType : cardType, // 卡类型
		txnDataTimEnd : $.trim($("#afterProceRateDateEnd").val()),
		txnDataTimStart : $.trim($("#afterProceRateDateStart").val()),
		txnAmtStart : txnAmtStart,
		txnAmtEnd : txnAmtEnd,
		page : getJqgridPage('crdConsumeOrderTbl', defaultPageNo)
	}
	// 充值金额
	// console.log("txnAmtStart===="+dataQuery.txnAmtStart+"txnAmtEnd=="+dataQuery.txnAmtEnd)
	var min = dataQuery.txnAmtStart;
	var max = dataQuery.txnAmtEnd;
	compareMoney(dataQuery, min, max);
	ddpAjaxCall({
		url : "findCrdSysConsOrderByPage",
		data : dataQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#crdConsumeOrderTbl'),
						data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

// 初始化加载卡服务订单状态
function findCardOrderStates() {
	ddpAjaxCall({
		url : "findConsumeStates",
		successFn : function(data) {
			addTipsOption(data);
			reLoadComboboxData($('#crdOrderStates'), data);
			$("#crdOrderStates").next("span").find("input.combo-value").val("");
			$("#crdOrderStates").next("span").find("input.combo-text").val(
					"--请选择--");
		}
	});
}

var expConfInfo = {
	"btnId" : "btnSelExcCol", /* must */// the id of export btn in ftl
	"typeSelStr" : "CrdSysConsOrderBean", /* must */// type of export
	"toUrl" : "exportCrdConsumeOrder" /* must */// the export url
};
var filterConStr = [ // filter the result by query condition
{
	'name' : 'crdOrderNum',
	'value' : "$.trim($('#crdOrderNum').val())"
},// 卡服务订单号
{
	'name' : 'proOrderNum',
	'value' : "$.trim($('#proOrderNum').val())"
},// 产品库主订单号
{
	'name' : 'crdOrderStates',
	'value' : "$('#crdOrderStates').combobox('getValue')"
},// 卡服务订单交易状态
{
	'name' : 'crdBeforeorderStates',
	'value' : "$('#crdBeforeorderStates').combobox('getValue')"
},// 卡服务订单交易状态
{
	'name' : 'posCode',
	'value' : "$.trim($('#posCode').val())"
},// POS号
{
	'name' : 'checkCardNo',
	'value' : "$.trim($('#checkCardNo').val())"
},// 卡号(验卡卡号)
{
	'name' : 'cardType',
	'value' : "$('#cardType').combobox('getValue')"
},// 卡类型
{
	'name' : 'afterProceRateDateStart',
	'value' : "$('#afterProceRateDateStart').val()"
}, {
	'name' : 'afterProceRateDateEnd',
	'value' : "$('#afterProceRateDateEnd').val()"
}, {
	'name' : 'txnAmtStart',
	'value' : "$.trim($('#txnAmtStart').val())"
}, {
	'name' : 'txnAmtEnd',
	'value' : "$.trim($('#txnAmtEnd').val())"
} ];
// //导出
// function exportVerified() {
// $.fileDownload('exportCrdConsumeOrder', {
// data: $('#listForm').serialize(),
// failCallback: function() {
// msgShow(systemWarnLabel, "文件导出出错", 'warning');
// }
// })
// }

// 查询卡服务订单详情
function findCrdSysOrderByCode() {
	var selrow = $("#crdConsumeOrderTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#crdConsumeOrderTbl').getRowData(selrow);
		var crdOrderNum = rowData.crdOrderNum;
		ddpAjaxCall({
			url : "findCrdConsumeOrderByCode",
			data : crdOrderNum,
			successFn : loadCrdSysOrder
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function loadCrdSysOrder(data) {
	var crdSysOrderBean = data.responseEntity;
	console.log(crdSysOrderBean);
	$('#crdOrderNumView').html(crdSysOrderBean.crdOrderNum);
	$('#proOrderNumView').html(crdSysOrderBean.proOrderNum);
	$('#proCodeView').html(crdSysOrderBean.proCode);
	$('#crdOrderStatesView').html(crdSysOrderBean.crdOrderStatesView);
	$('#crdBeforeorderStatesView').html(
			crdSysOrderBean.crdBeforeorderStatesView);
	$('#respCodeView').html(crdSysOrderBean.respCode);
	$('#userCodeView').html(crdSysOrderBean.userCode);
	$('#cityCodeView').html(crdSysOrderBean.cityCode);
	$('#yktCodeView').html(crdSysOrderBean.yktName);
	$('#cardInnerNoView').html(crdSysOrderBean.cardInnerNo);
	$('#cardTypeView').html(crdSysOrderBean.cardTypeView);
	$('#cardFaceNoView').html(crdSysOrderBean.cardFaceNo);
	$('#orderCardNoView').html(crdSysOrderBean.orderCardNo);
	$('#posTypeView').html(crdSysOrderBean.posTypeView);
	$('#posCodeView').html(crdSysOrderBean.posCode);
	$('#posSeqView').html(crdSysOrderBean.posSeq);
	$('#befbalView').html(crdSysOrderBean.befbal / 100 + "元");
	$('#blackAmtView').html(crdSysOrderBean.blackAmt / 100 + "元");
	$('#txnAmtView').html(crdSysOrderBean.txnAmt / 100 + "元");
	$('#cardLimitView').html(crdSysOrderBean.cardLimit / 100 + "元");
	$('#sourceView').html(crdSysOrderBean.sourceView);
	$('#txnTypeView').html(crdSysOrderBean.txnTypeView);
	$('#txnSeqView').html(crdSysOrderBean.txnSeq);
	// $('#txnDateView').html(crdSysOrderBean.txnDate);
	// $('#txnTimeView').html(crdSysOrderBean.txnTime);
	$('#txnDateView').html(
			formatDate(crdSysOrderBean.txnDateTime, 'yyyy-MM-dd HH:mm:ss'));
	$('#checkCardNoView').html(crdSysOrderBean.checkCardNo);
	$('#checkSendCardDateView')
			.html(
					formatDate(crdSysOrderBean.checkSendCardDate,
							'yyyy-MM-dd HH:mm:ss'));
	$('#checkResCardDateView')
			.html(
					formatDate(crdSysOrderBean.checkResCardDate,
							'yyyy-MM-dd HH:mm:ss'));
	$('#checkCardPosCodeView').html(crdSysOrderBean.checkCardPosCode);
	$('#consumeCardNoView').html(crdSysOrderBean.consumeCardNo);
	$('#consumeCardPosCodeView').html(crdSysOrderBean.consumeCardPosCode);
	$('#consumeSendCardDateView').html(
			formatDate(crdSysOrderBean.consumeSendCardDate,
					'yyyy-MM-dd HH:mm:ss'));
	$('#consumeResCardDateView').html(
			formatDate(crdSysOrderBean.consumeResCardDate,
					'yyyy-MM-dd HH:mm:ss'));
	$('#resultSendCardDateView').html(
			formatDate(crdSysOrderBean.resultSendCardDate,
					'yyyy-MM-dd HH:mm:ss'));
	$('#resultResCardDateView')
			.html(
					formatDate(crdSysOrderBean.resultResCardDate,
							'yyyy-MM-dd HH:mm:ss'));
	$('#chargeTypeView').html(crdSysOrderBean.chargeTypeView);
	$('#metropassTypeView').html(crdSysOrderBean.metropassType);
	$('#metropassChargeStartDateView').html(
			formatDate(crdSysOrderBean.metropassChargeStartDate, 'yyyy-MM-dd'));
	$('#metropassChargeEndDateView').html(
			formatDate(crdSysOrderBean.metropassChargeEndDate, 'yyyy-MM-dd'));
	$('#dounknowFlagView').html(crdSysOrderBean.dounknowFlag);
	$('#cardCounterView').html(crdSysOrderBean.cardCounter);
	$('#afterCardNotesView').html(crdSysOrderBean.afterCardNotes);
	$('#beforeCardNotesView').html(crdSysOrderBean.beforeCardNotes);
	$('#tradeStepView').html(crdSysOrderBean.tradeStep);
	$('#tradeStepVerView').html(crdSysOrderBean.tradeStepVer);
	$('#tradeEndFlagView').html(crdSysOrderBean.tradeEndFlagView);
	$('#createUserView').html(crdSysOrderBean.createUser);
	$('#createDateView').html(
			formatDate(crdSysOrderBean.createDate, 'yyyy-MM-dd HH:mm:ss'));
	$('#updateUserView').html(crdSysOrderBean.updateUser);
	$('#updateDateView').html(
			formatDate(crdSysOrderBean.updateDate, 'yyyy-MM-dd HH:mm:ss'));
	$('#viewCrdSysOrderDialog').show().dialog('open');
}

function closeViewCrdConsumeOrder() {
	$('#viewCrdSysOrderDialog').show().dialog('close');
}
// 初始化加载卡服务订单前状态
function findCrdBeforeorderStates() {
	ddpAjaxCall({
		url : "findConsumeStates",
		successFn : function(data) {
			addTipsOption(data);
			reLoadComboboxData($('#crdBeforeorderStates'), data);
			$("#crdBeforeorderStates").next("span").find("input.combo-value")
					.val("");
			$("#crdBeforeorderStates").next("span").find("input.combo-text")
					.val("--请选择--");
		}
	});
}
// 初始化加载卡服务卡片类型状态
function findCardType() {
	ddpAjaxCall({
		url : "findCardType",
		successFn : function(data) {
			addTipsOption(data);
			reLoadComboboxData($('#cardType'), data);
			$("#cardType").next("span").find("input.combo-value").val("");
			$("#cardType").next("span").find("input.combo-text").val("--请选择--");
		}
	});
}
function checkTxnAmt() {
	$("#txnAmtStart").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtStart").bind('keydown', function() {
		checkDecimal($(this), 1, 1, 9, 0, 2);
	});
	$("#txnAmtStart").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});

	$("#txnAmtEnd").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtEnd").bind('keydown', function() {
		checkDecimal($(this), 1, 1, 9, 0, 2);
	});
	$("#txnAmtEnd").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
}

function compareMoney(query, min, max) {
	if (min != "" && max != "" && parseFloat(min) > parseFloat(max)) {
		var temp = max;
		max = min;
		min = temp;
		$('#txnAmtStart').val(min);
		$('#txnAmtEnd').val(max);
		query.txnAmtStart = min * 100;
		query.txnAmtEnd = max * 100;
	}
}

// --------------------------限制用户只能输入小数点后两位开始--------
// obj dec小数位
function checkDecimal(obj, posOrNeg, startWhole, endWhole, startDec, endDec) {
	var rate = obj.val();
	var re;
	var posNeg;
	if (posOrNeg == 1 || posOrNeg == '1') {
		re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
				+ startDec + "," + endDec + "})?$");
		posNeg = /^[+]?]*$/;
	} else if (posOrNeg == 2 || posOrNeg == '2') {
		re = new RegExp("^[-]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
				+ startDec + "," + endDec + "})?$");
		posNeg = /^[-]?]*$/;
	} else {
		re = new RegExp("^[+-]?\\d{" + startWhole + "," + endWhole
				+ "}(\\.\\d{" + startDec + "," + endDec + "})?$");
		posNeg = /^[+-]?]*$/;
	}
	if (!re.test(rate) && !posNeg.test(rate)) {
		rate = "";
		obj.focus();
		return false;
	}
}

// obj
function checkPlusMinus(obj) {
	var rate = obj.val();
	posNeg1 = /^[+]?]*$/;
	posNeg2 = /^[-]?]*$/;
	if (posNeg1.test(rate) || posNeg2.test(rate)) {
		obj.val = "";
		// obj.focus();
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
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 5 && textChar != ".") {
			obj.val(text.substring(0, 4));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if (text01.length > 4) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 7) {
			text = "";
		}
		obj.val(text);
	}
}
function clearNoNumOnBlur(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 5 && textChar != ".") {
			obj.val(text.substring(0, 4));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if (text01.length > 4) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 7) {
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
//--------------------------限制用户只能输入小数点后两位结束--------
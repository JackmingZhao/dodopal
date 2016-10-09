function initDateQuery() {
	$("#afterProceRateDateStart").val(formatDate(new Date(), "yyyy-MM-dd"));
	$("#afterProceRateDateEnd").val(formatDate(new Date(), "yyyy-MM-dd"));
}
function clearCrdAllText() {
	clearAllText('crdSysOrderQueryCondition');
	initDateQuery();
}
$(function() {
	initDateQuery();
	initMainComponent();

	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
	findCardOrderStates();
	findCardType();
	findCrdBeforeorderStates();
	checkTxnAmt();
	autoResize({
		dataGrid : {
			selector : '#crdSysOrderTbl',
			offsetHeight : 100,
			offsetWidth : 0
		},
		callback : initCrdSysOrderTbl,
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

function checkTxnAmt() {
	$("#txnAmtStart", "#crdSysOrderQueryCondition").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtStart", "#crdSysOrderQueryCondition").bind('keydown', function() {
		checkDecimal($(this), 1, 1, 9, 0, 2);
	});
	$("#txnAmtStart", "#crdSysOrderQueryCondition").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});

	$("#txnAmtEnd", "#crdSysOrderQueryCondition").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtEnd", "#crdSysOrderQueryCondition").bind('keydown', function() {
		checkDecimal($(this), 1, 1, 9, 0, 2);
	});
	$("#txnAmtEnd", "#crdSysOrderQueryCondition").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
}

function initCrdSysOrderTbl(size) {
	var option = {
		datatype : function(pdata) {
			findCrdSysOrder();
		},

		// colNames : ['ID','卡服务订单号', '产品库主订单号',
		// '产品编号','通卡公司名称','城市名称','卡号','卡类型','POS号','用户姓名','交易前金额(元)','交易金额(元)','交易后金额(元)','订单状态',
		// '订单前状态','交易时间'],

		colNames : [ 'ID', '卡服务订单号', '产品库主订单号', '商户名称', '通卡公司名称', '城市', 'POS号',
				'卡号', '卡类型', '产品名称', '交易金额(元)', '交易前金额(元)', '交易后金额(元)', '订单状态',
				'订单前状态', '交易时间' ],

		colModel : [ {
			name : 'id',
			hidden : true,
			frozen : true
		}, {
			name : 'crdOrderNum',
			index : 'crdOrderNum',
			width : 170,
			align : 'left',
			sortable : false,
			frozen : true
		}, {
			name : 'proOrderNum',
			index : 'proOrderNum',
			width : 170,
			align : 'left',
			sortable : false
		}, {
			name : 'merName',
			index : 'merName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'yktName',
			index : 'yktName',
			width : 110,
			align : 'left',
			sortable : false
		}, {
			name : 'cityName',
			index : 'cityName',
			width : 60,
			align : 'left',
			sortable : false
		}, {
			name : 'posCode',
			index : 'posCode',
			width : 110,
			align : 'left',
			sortable : false
		}, {
			name : 'checkCardNo',
			index : 'checkCardNo',
			width : 160,
			align : 'left',
			sortable : false
		}, {
			name : 'cardTypeView',
			index : 'cardTypeView',
			width : 60,
			align : 'left',
			sortable : false
		}, {
			name : 'proName',
			index : 'proName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'txnAmtView',
			index : 'txnAmtView',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'befbalView',
			index : 'befbalView',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'blackAmtView',
			index : 'blackAmtView',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'crdOrderStatesView',
			index : 'crdOrderStatesView',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'crdBeforeorderStatesView',
			index : 'crdBeforeorderStatesView',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'txnDateTime',
			index : 'txnDateTime',
			width : 140,
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
	$('#crdSysOrderTbl').jqGrid(option);
	$("#t_crdSysOrderTbl").append($('#crdSysOrderTblToolbar'));

	$("#crdSysOrderTbl").jqGrid('setFrozenColumns');
}
// 查询卡服务订单
function findCrdSysOrder(defaultPageNo) {
	var crdOrderStates = $('#crdOrderStates').combobox('getValue');
	var cardType = $('#cardType').combobox('getValue');
	var crdBeforeorderStates = $('#crdBeforeorderStates').combobox('getValue');
	var dataQuery = {
		crdOrderNum : $('#crdOrderNum').val(), // 卡服务订单号
		proOrderNum : $('#proOrderNum').val(), // 产品库主订单号
		crdOrderStates : crdOrderStates, // 卡服务订单交易状态
		crdBeforeorderStates : crdBeforeorderStates, // 卡服务订单前交易状态
		proName : $('#proName').val(), // 产品编号
		merCode : $('#merCode').val(), // 商户编号
		posCode : $('#posCode').val(), // POS号
		checkCardNo : $('#checkCardNo').val(), // 卡号(验卡卡号)
		cardFaceNo : $('#cardFaceNo').val(), // 卡面号
		cardType : cardType, // 卡类型
		txnDataTimEnd : $.trim($("#afterProceRateDateEnd").val()),
		txnDataTimStart : $.trim($("#afterProceRateDateStart").val()),
		txnAmtStart : $.trim($('#txnAmtStart').val()),
		txnAmtEnd : $.trim($('#txnAmtEnd').val()),
		page : getJqgridPage('crdSysOrderTbl', defaultPageNo)
	}
	// 充值金额
	var min = dataQuery.txnAmtStart;
	var max = dataQuery.txnAmtEnd;
	compareMoney(dataQuery, min, max);
	ddpAjaxCall({
		url : "findCrdSysOrderByPage",
		data : dataQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#crdSysOrderTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
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
	if (min != "" && max != "") {
		query.txnAmtStart = min * 100;
		query.txnAmtEnd = max * 100;
	}
	if (min != "") {
		query.txnAmtStart = min * 100;
	}
	if (max != "") {
		query.txnAmtEnd = max * 100;
	}
}
// 查询卡服务订单详情
function findCrdSysOrderByCode() {
	var selrow = $("#crdSysOrderTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#crdSysOrderTbl').getRowData(selrow);
		var crdOrderNum = rowData.crdOrderNum;
		ddpAjaxCall({
			url : "findCrdSysOrderByCode",
			data : crdOrderNum,
			successFn : loadCrdSysOrder
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function closeViewCrdSysOrder() {
	$('#viewCrdSysOrderDialog').show().dialog('close');
}
function loadCrdSysOrder(data) {
	var crdSysOrderBean = data.responseEntity;
	$('#crdOrderNumView').html(crdSysOrderBean.crdOrderNum);
	$('#proOrderNumView').html(crdSysOrderBean.proOrderNum);
	$('#proCodeView').html(crdSysOrderBean.proCode);
	$('#crdOrderStatesView').html(crdSysOrderBean.crdOrderStatesView);
	$('#crdBeforeorderStatesView').html(
			crdSysOrderBean.crdBeforeorderStatesView);
	$('#respCodeView').html(crdSysOrderBean.respCode);
	$('#userCodeView').html(crdSysOrderBean.userCode);
	$('#cityCodeView').html(crdSysOrderBean.cityCode);
	console.log(crdSysOrderBean);
	$('#yktNameView').html(crdSysOrderBean.yktName);
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
	$('#chargeCardNoView').html(crdSysOrderBean.chargeCardNo);
	$('#chargeCardPosCodeView').html(crdSysOrderBean.chargeCardPosCode);
	$('#chargeSendCardDateView').html(
			formatDate(crdSysOrderBean.chargeSendCardDate,
					'yyyy-MM-dd HH:mm:ss'));
	$('#chargeResCardDateView')
			.html(
					formatDate(crdSysOrderBean.chargeResCardDate,
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
	$('#createUserView').html(crdSysOrderBean.createUserName);
	$('#createDateView').html(
			formatDate(crdSysOrderBean.createDate, 'yyyy-MM-dd HH:mm:ss'));
	$('#updateUserView').html(crdSysOrderBean.updateUserName);
	$('#updateDateView').html(
			formatDate(crdSysOrderBean.updateDate, 'yyyy-MM-dd HH:mm:ss'));

	// // 卡服务补充信息
	// var crdSysSupplementBean = crdSysOrderBean.crdSysSupplementBean;
	// // 交易类型 1-充值 TXN_TYPE
	// //$('#txnTypeView').html(crdSysSupplementBean.txnType);
	// // M1卡申请读卡密钥前上传的数据/CPU卡验卡时上传的特殊域 CHECK_YKTMW
	// $('#checkYktmwView').html(htmlTagFormat(crdSysSupplementBean.checkYktmw));
	// // M1卡返回读卡密钥/CPU卡验卡时返回APDU指令集 CHECK_YKTKEY
	// $('#checkYktkeyView').html(htmlTagFormat(crdSysSupplementBean.checkYktkey));
	// // M1卡申请充值密钥前的上传的数据/CPU卡圈存申请时上传的特殊域 CHARGE_YKTMW
	// $('#chargeYktmwView').html(htmlTagFormat(crdSysSupplementBean.chargeYktmw));
	// // M1卡返回充值密钥/CPU卡圈存申请时返回APDU指令集 CHARGE_KEY
	// $('#chargeKeyView').html(htmlTagFormat(crdSysSupplementBean.chargeKey));
	// // M1卡结果上传的卡数据/CPU卡结果上传的特殊域 RESULT_YKTMW
	// $('#resultYktmwView').html(htmlTagFormat(crdSysSupplementBean.resultYktmw));
	// // 交易日期 TXN_DATE
	// $('#txnDateView').html(crdSysSupplementBean.txnDate);
	// // 交易时间 TXN_TIME
	// // $('#txnTimeView').html(crdSysOrderBean.txnTime);
	// // 最近一次申请读卡密钥时间 LAST_READ_KEY_TIME
	// $('#lastReadKeyTimeView').html(crdSysSupplementBean.lastReadKeyTime);
	// // 最近一次申请充消密钥时间 LAST_RESULT_TIME
	// $('#lastChargeKeyTimeView').html(crdSysSupplementBean.lastChargeKeyTime);
	// // 最近一次上传结果时间 LAST_RESULT_TIME
	// $('#lastResultTimeView').html(crdSysSupplementBean.lastResultTime);
	// // 申请读卡密钥次数 REQUEST_READ_KEY_COUNT
	// $('#requestReadKeyCountCountView')
	// .html(crdSysSupplementBean.requestReadKeyCount);
	// // 申请充消密钥次数 REQUEST_CHARGE_KEY_COUNT
	// $('#requestChargeKeyCountView')
	// .html(crdSysSupplementBean.requestChargeKeyCount);
	// // 上传结果次数 SEND_RESULT_COUNT
	// $('#sendResultCountView').html(crdSysSupplementBean.sendResultCount);

	$('#viewCrdSysOrderDialog').show().dialog('open');
}

var expConfInfo = {
	"btnId" : "btnSelExcCol", /* must */// the id of export btn in ftl
	"typeSelStr" : "CrdSysOrderBean", /* must */// type of export
	"toUrl" : "exportCrdSysOrder" /* must */// the export url
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
	'name' : 'proName',
	'value' : "$.trim($('#proName').val())"
},// 产品名称
{
	'name' : 'merCode',
	'value' : "$.trim($('#merCode').val())"
},// 商户编号
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
// 导出
// function exportVerified() {
// $.fileDownload('exportCrdSysOrder', {
// data: $('#listForm').serialize(),
// failCallback: function() {
// msgShow(systemWarnLabel, "文件导出出错", 'warning');
// }
// })
// }
// 初始化加载卡服务订单状态
function findCardOrderStates() {
	ddpAjaxCall({
		url : "findCardOrderStates",
		successFn : function(data) {
			addTipsOption(data);
			reLoadComboboxData($('#crdOrderStates'), data);
			$("#crdOrderStates").next("span").find("input.combo-value").val("");
			$("#crdOrderStates").next("span").find("input.combo-text").val(
					"--请选择--");
		}
	});
}

// 初始化加载卡服务订单前状态
function findCrdBeforeorderStates() {
	ddpAjaxCall({
		url : "findCardOrderStates",
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

$(function() {
	
	initMainComponent();
	initDetailComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#externalTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initExternalTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'externalDialog' ]
	});
	loadPayWayNameSource();
}



function loadPayWayNameSource(){
	$("#payType").combobox({
		valueField: 'payType',
		textField: 'text',
		width : 199,
		data: [{payType: '',text: '--请选择--'},
		       {payType: '0',text: '账户支付'},
		       {payType: '1',text: '一卡通支付'},
		       {payType: '2',text: '在线支付'},
		       {payType: '3',text: '银行支付'}],
		editable : false,
		onSelect:function(recode){
			var payType = recode.payType; 
			$("#payWayName").combobox({
				valueField : 'id',
				textField : 'payWayName',
				editable : false,
				width : 199,
				url : $.base + "/payment/pay/findExternalPayWayName?payType="+payType,
			}).combobox("clear");
		}
	});
}


function initDetailComponent() {
	initExternalDialog();
	initMerchantSearchModel();
}

function initExternalTbl(size) {
	var option = {
		datatype : function(pdata) {
			findExternal();
		},
		colNames : [ 'id', '商户名称', '商户编号', '支付类型', '支付方式名称', '银行网关类型', '服务费率(‰)',
		             '网关号', '排序号', '启用标识', '图标名称', '备注' ],
		colModel : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'merName',
			index : 'merName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'merCode',
			index : 'merCode',
			width : 140,
			align : 'left',
			sortable : false
		}, {
			name : 'payType',
			index : 'payType',
			width : 100,
			align : 'left',
			sortable : false,
            formatter: function(cellval, el, rowData) {
				if(cellval == '0') {return '账户支付'} 
				else if(cellval == '1') {return '一卡通支付'	} 
				else if(cellval == '2') {return '在线支付'	} 
				else if(cellval == '3') {return '银行支付'	} 
				else { return ''};
			}
		}, {
			name : 'payWayName',
			index : 'payWayName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'bankGateWayType',
			index : 'bankGateWayType',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
					if(cellval == '0') {return '支付宝'} 
					else if(cellval == '1') {return '财付通'} 
					else if(cellval == '2') {return '都都宝钱包'} 
					else { return ''};
			}
		}, {
			name : 'payServiceRate',
			index : 'payServiceRate',
			width : 100,
			align : 'right',
			sortable : false
		}, {
			name : 'gateWayNum',
			index : 'gateWayNum',
			width : 90,
			align : 'left',
			sortable : false
		}, {
			name : 'sort',
			index : 'sort',
			width : 70,
			align : 'left',
			sortable : false
		}, {
			name : 'activate',
			index : 'activate',
			width : 60,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
				if(cellval == '0') {return '启用'} 
				else if(cellval == '1') {return '停用'	} 
				else { return ''};
			}
		}, {
			name : 'imageName',
			index : 'imageName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'comments',
			index : 'comments',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: htmlFormatter
		}
		],
		height : size.height - 50,
		width : size.width,
		multiselect : true,
		pager : '#externalPagination',
		toolbar : [ true, "top" ]
	};

	option = $.extend(option, jqgrid_server_opts);
	$('#externalTbl').jqGrid(option);
	$('#t_externalTbl').append($('#externalTblToolbar'));
}

// 查询
function findExternal(defaultPageNo) {
	var externalQuery = {
		merName : escapePeculiar($.trim($('#merNameQuery').val())),
		merCode : '',
		payType : $('#payTypeQuery').combobox('getValue'),
		payWayName : escapePeculiar($.trim($('#payWayNameQuery').val())),
		activate : $('#activateQuery').combobox('getValue'),
		payAwayType : "1",
		page : getJqgridPage('externalTbl', defaultPageNo)
	}
	ddpAjaxCall({
		url : "findExternalPay",
		data : externalQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#externalTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function initExternalDialog() {
	$('#externalDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
	$("#payServiceRate").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#payServiceRate").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#payServiceRate").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	$("#sort").bind('keyup', function() {
		clearNoNum1($(this));
	});
	
}

// 添加
function addExternalType() {
	destroyValidate('externalDialog');
	clearAllText('externalDialog');
	 $("#id").val('');
	 $("#merName").val('');
	 $("#merCode").val('');
	 $("#finMER").removeAttr("disabled","disabled");
	 $("#payType").combobox("select","" );
	 $("#payType").combobox("enable");
	 $("#payWayName").combobox("select","");
	 $("#payWayName").combobox("enable");
	 $("#payServiceRate").val('0.00');
	 $("#sort").val('');
	//停用，启用
	 $("input[name='activate'][value=0]").attr("checked",true);
	 $("input[name='activate']").removeAttr("disabled","disabled");
	 $("#comments").val('');
	showDialog('externalDialog');
}

// 商户名称
function findMerName() {
	$('#merchantQuery').val('');
	// 清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
	findMerchantName();
	showDialog('merchantSearchDialog');

}
// 加载商户
function setSelectedMerchant(merchant) {
	if (typeof (merchant) != 'undefined') {
		$('#merName').val(merchant.merName);
		$('#merCode').val(merchant.merCode);
	}
}

// 保存
function saveExternal() {
	var merName1 = $('#merName').val();
	if(isBlank(merName1)) {
		msgShow(systemWarnLabel, "请选择商户名称", 'warning');
		return;
	}
	var payType1 = $('#payType').combobox("getValue");
	if(isBlank(payType1)) {
		msgShow(systemWarnLabel, "请选择支付类型", 'warning');
		return;
	}
	var payWayName1 = $('#payWayName').combobox("getValue");
	if(isBlank(payWayName1)) {
		msgShow(systemWarnLabel, "请选择支付方式名称", 'warning');
		return;
	}
	if (isValidate('externalDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存外接支付方式信息吗？", function(r) {
			if (r) {
				var activate = $("input[name='activate']:checked").val();
				var payExternal = {
					id : $('#id').val(),
					merCode : $('#merCode').val(),
					merName : $('#merName').val(),
					payType : $('#payType').combobox("getValue"),
					payConfigId : $('#payWayName').combobox("getValue"),
					payServiceRate : $('#payServiceRate').val(),
					sort : $('#sort').val(),
					comments : $('#comments').val(),
					activate : activate,
					payAwayType : "1"

				};
				ddpAjaxCall({
					url : "savePayAwayExternal",
					data : payExternal,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('externalDialog');
							if (isBlank(payExternal.id)) {
								findExternal(1);
							} else {
								findExternal();
							}
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	}
}

// 删除
function deletePayExternal() {
	var selarrrow = $("#externalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要删除所选择的记录吗？",
				function(r) {
					if (r) {
						var id = new Array();
						for (var i = 0; i < selarrrow.length; i++) {
							var rowData = $("#externalTbl").getRowData(
									selarrrow[i]);
							id.push(rowData.id);
						}
						ddpAjaxCall({
							url : "deletePayAwayExternal",
							data : id,
							successFn : function(data) {
								if (data.code == "000000") {
									findExternal();
								} else {
									msgShow(systemWarnLabel, data.message,
											'warning');
								}
							}
						});
					}
				});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}

}

// 修改
function editExternal() {
	var selarrrow = $("#externalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow) {
		if (selarrrow.length == 1) {
			var rowData = $('#externalTbl').getRowData(selarrrow[0]);
			clearAllText('externalDialog');
			ddpAjaxCall({
				url : "findPayExternalById",
				data : rowData.id,
				successFn : loadPayExternal
			});
			
		} else if (selarrrow.length == 0) {
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		} else {
			msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function loadPayExternal(data){
	if(data.code=="000000"){
		var payAwayBean = data.responseEntity;
		 showDialog('externalDialog');
		 $("#id").val(payAwayBean.id);
		 $("#merName").val(payAwayBean.merName);
		 $("#merCode").val(payAwayBean.merCode);
		 $("#finMER").attr("disabled",true);
		 $("#payType").combobox("select",payAwayBean.payType==null? "" : payAwayBean.payType);
		 $('#payType').combobox('disable');
		 $("#payWayName").combobox("select",payAwayBean.payWayName==null? "" : payAwayBean.payWayName);
		 $('#payWayName').combobox('disable');
		 $("#payServiceRate").val(payAwayBean.payServiceRate);
		 $("#sort").val(payAwayBean.sort);
		//停用，启用
		 if(payAwayBean.activate=="0"){
			 $("input[name='activate'][value=0]").attr("checked",true);
		 }else{
			 $("input[name='activate'][value=1]").attr("checked",true);
		 }
		 $("input[name='activate'][value=0]").attr("disabled","disabled");
		 $("input[name='activate'][value=1]").attr("disabled","disabled");
		 $("#comments").val(payAwayBean.comments);
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


// 启用
function enablePayExternal() {
	var selarrrow = $("#externalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用外接支付方式吗？", function(r) {
			if (r) {
				var id = new Array();
				for (var i = 0; i < selarrrow.length; i++) {
					var rowData = $("#externalTbl").getRowData(selarrrow[i]);
					id.push(rowData.id);
				}
				ddpAjaxCall({
					url : "enbalePayAwayExternal?activate=0",
					data : id,
					successFn : function(data) {
						if (data.code == "000000") {
							findExternal();
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}

}
// 停用
function disablePayExternal() {
	var selarrrow = $("#externalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用外接支付方式吗？", function(r) {
			if (r) {
				var id = new Array();
				for (var i = 0; i < selarrrow.length; i++) {
					var rowData = $("#externalTbl").getRowData(selarrrow[i]);
					id.push(rowData.id);
				}
				ddpAjaxCall({
					url : "enbalePayAwayExternal?activate=1",
					data : id,
					successFn : function(data) {
						if (data.code == "000000") {
							findExternal();
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}

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
		if (text.length >= 4 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 3)));
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
		if (text01.length > 3) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 6) {
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
		if (text.length >= 4 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 3)));
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
		if (text01.length > 3) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 6) {
			text = "";
		}
		obj.val(text);
	}
	
}
function clearNoNum1(obj) { 
	var rate = obj.val();
	rate = rate.replace(/\D/g ,"");  //清除“数字”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	var text = rate;
	obj.val(rate);
	if (text.indexOf(".") < 0) {
		if (text.length >5) {
			obj.val(text.substring(0,5));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		obj.val(text01);
	}
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PayWayExternal", 			/*must*/// type of export
	"toUrl"			: "exportExternalPayWay" 			/*must*/// the export url
};
var filterConStr = [	// filter the result by query condition
		{'name':'merNameQuery',	'value': "escapePeculiar($.trim($('#merNameQuery').val()))"		},
		{'name':'payTypeQuery', 	'value': "escapePeculiar($.trim($('#payTypeQuery').combobox('getValue')))"			},	// eval
		{'name':'payWayNameQuery',	'value': "escapePeculiar($.trim($('#payWayNameQuery').val()))"		},
		{'name':'activateQuery',	'value': "escapePeculiar($('#activateQuery').combobox('getValue'))"		}
	];

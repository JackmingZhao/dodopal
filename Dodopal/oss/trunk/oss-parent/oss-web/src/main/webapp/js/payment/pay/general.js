$(function() {
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#generalTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initGeneralTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'generalDialog' ]
	});
	loadPayWayNameSource();
}

function loadPayWayNameSource(){
	$("#payType").combobox({
		valueField: 'payType',
		textField: 'text',
		width : 214,
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
				width : 214,
				url : $.base + "/payment/pay/findGeneralPayWayName?payType="+payType,
			}).combobox("clear");
		}
	});
}

function initDetailComponent() {
	initGeneralDialog();
}


function initGeneralTbl(size) {
	var option = {
		datatype : function(pdata) {
			findGeneral();
		},
		colNames : [ 'id', '支付类型', '支付方式名称', '银行网关类型', '排序号',
				  '图标名称','启用标识', '备注' ],
		colModel : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'payTypeView',
			index : 'payTypeView',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'payWayName',
			index : 'payWayName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'bankGateWayTypeView',
			index : 'bankGateWayTypeView',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'sort',
			index : 'sort',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'imageName',
			index : 'imageName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'activate',
			index : 'activate',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: activateFormatter
		}, {
			name : 'comments',
			index : 'comments',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: htmlFormatter
		} ],
		height : size.height - 50,
		width : size.width,
		multiselect : true,
		pager : '#generalTblPagination',
		toolbar : [ true, "top" ]
	};

	option = $.extend(option, jqgrid_server_opts);
	$('#generalTbl').jqGrid(option);
	$('#t_generalTbl').append($('#generalTblToolbar'));
}

function findGeneral(defaultPageNo) {
	var generalQuery = {
		payType : $('#payTypeQuery').combobox('getValue'),
		payWayName : escapePeculiar($.trim($('#payWayNameQuery').val())),
		activate : $('#activateQuery').combobox('getValue'),
		payAwayType: "0",
		page : getJqgridPage('generalTbl', defaultPageNo)
	}
	ddpAjaxCall({
		url : "findGeneralPay",
		data : generalQuery,
		successFn : function(data) {
			console.log("========success======",data);
			if (data.code == "000000") {
				loadJqGridPageData($('#generalTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function initGeneralDialog() {
	$('#generalDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 620,
		height : 300
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
	$("#sort").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
}

function startUser() {
	var selarrrow = $("#generalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定启用通用支付方式吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#generalTbl").getRowData(selarrrow[i]);
					ids.push(rowData.id);
				}
				ddpAjaxCall({
					url : "startOrStopGeneral?activate=0",
					data : ids,
					successFn : function(data){
						if(data.code == "000000") {
							findGeneral();
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
function stopUser() {
	var selarrrow = $("#generalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定停用通用支付方式吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#generalTbl").getRowData(selarrrow[i]);
					ids.push(rowData.id);
				}
				ddpAjaxCall({
					url : "startOrStopGeneral?activate=1",
					data : ids,
					successFn : function(data){
						if(data.code == "000000") {
							findGeneral();
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

function deletePayGeneral() {
	var selarrrow = $("#generalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "一旦删除将无法恢复, 确定要删除吗？", function(r) {
			if (r) {
				var id = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#generalTbl").getRowData(selarrrow[i]);
					id.push(rowData.id);
				}
				ddpAjaxCall({
					url : "deletePayAwayGeneral",
					data : id,
					successFn : function(data){
						if(data.code == "000000") {
							findGeneral();
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
function addGeneralType(){
	destroyValidate('generalDialog');
	clearAllText('generalDialog')
	showDialog('generalDialog');
}
function editGeneral(){
	var selarrrow = $("#generalTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	
	var selrow = $("#generalTbl").getGridParam('selrow');
	if (selrow) {
		var general = $('#generalTbl').getRowData(selrow);
		console.log(general.id);
		$('#generalDialog').show().dialog('open');
		var id=general.id;
		ddpAjaxCall({
			url : "findPayGeneralById",
			data :id,
			successFn : loadGeneralCode
		});
	}else{
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
	
	
}

function loadGeneralCode(data){
	if("000000" == data.code){
		$('#generalDialog').show().dialog('open');
		console.log(data.responseEntity);
		var payGeneral = data.responseEntity;
		 $("#payType").combobox("select",payGeneral.payType==null? "" : payGeneral.payType);
		 $('#payType').combobox('disable');
		 $("#payWayName").combobox("select",payGeneral.payWayName==null? "" : payGeneral.payWayName);
		 $('#payWayName').combobox('disable');
		$('#payServiceRate').val(payGeneral.payServiceRate);
		$('#sort').val(payGeneral.sort);
		$('#activate').val(payGeneral.activate);
		if(payGeneral.activate=="0"){
			$("input[name=activate]:eq(0)").attr("checked",'checked'); 
		}
		else{
			$("input[name=activate]:eq(1)").attr("checked",'checked');
		}
		 $("input[name=activate]:eq(0)").attr("disabled",'disabled');
		 $("input[name=activate]:eq(1)").attr("disabled",'disabled'); 
		 $('#id').val(payGeneral.id);
		$('#comments').val(payGeneral.comments);
	}else{
		msgShow(systemWarnLabel, "查询出错", 'warning');
	}
	
}
function closeEdit(){
	$('#generalDialog').show().dialog('close');
}

//更新用户
function updateGeneral(){
	if(isValidate('editGeneral')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存通用支付方式信息吗？", function(r) {
			if (r) {
				var generalBean = {
						id:$('#id').val(),
						activate: $("input[name='activate']:checked").val(),
						payServiceRate: $.trim($('#payServiceRate').val()),
						sort: $.trim($('#sort').val()),
						comments:$.trim($('#comments').val()),
				};
				ddpAjaxCall({
					url : "updatePayGeneral",
					data : generalBean,
					successFn : afterUpdate
				});
			}
		});
	}
}
function afterUpdate(data){
	if(data.code == "000000") {
		closeEdit();
		findGeneral();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function showAddGeneral(){
	destroyValidate('generalDialog');
	clearAllText('generalDialog')
	$("#id").val('');
	$("#payType").combobox("select","" );
	$("#payType").combobox("enable");
	$("#payWayName").combobox("select","");
	$("#payWayName").combobox("enable");
	//$("#payServiceRate").val('0.00');
	$("#sort").val('');
	$("input[name='activate'][value=0]").attr("checked",true);
	$("input[name='activate']").removeAttr("disabled","disabled");
	$("#comments").val('');
	showDialog('generalDialog');
}

function saveOrUpdateGeneral(){
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
	if (isValidate('generalDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存通用支付方式信息吗？", function(r) {
			if (r) {
				var activate = $("input[name='activate']:checked").val();
				var payGeneral = {
					id : $('#id').val(),
					payType : $('#payType').combobox("getValue"),
					payConfigId : $('#payWayName').combobox("getValue"),
					payServiceRate : $('#payServiceRate').val(),
					sort : $('#sort').val(),
					comments : $('#comments').val(),
					activate : activate,
				};
				ddpAjaxCall({
					url : "saveGeneralPayWay",
					data : payGeneral,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('generalDialog');
							if (isBlank(payGeneral.id)) {
								findGeneral(1);
							} else {
								findGeneral();
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
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 5  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,4)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 4) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 7) {
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
/*以下为校验费率js*/
function clearNoNumOnBlur(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 5  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,4)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 4) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 7) {
			text = "";
		} else {
			var textChar = text.charAt(text.length - 1);
			if (textChar ==".") {
				text = text.substring(0,text.length-1);  
			}
		}
		obj.val(text);
	}
}  
/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PayGeneralBean", 	/*must*/// type of export
	"toUrl"			: "exportExcelGeneral" 			/*must*/// the export url
};
var filterConStr = [	// filter the result by query condition
		{'name':'payTypeQuery', 	'value': "escapePeculiar($.trim($('#payTypeQuery').combobox('getValue')))"			},	// eval
		{'name':'payWayNameQuery',	'value': "escapePeculiar($.trim($('#payWayNameQuery').val()))"		},
		{'name':'activateQuery',	'value': "escapePeculiar($('#activateQuery').combobox('getValue'))"		}
	];
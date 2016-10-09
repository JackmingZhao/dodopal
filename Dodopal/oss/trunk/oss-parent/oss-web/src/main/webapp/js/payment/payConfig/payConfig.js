$(function() {
	
	initMainComponent();
	initDetailComponent();
	//findPayConfig();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	initEditComponent();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#payConfigTbl',
			offsetHeight :50,
			offsetWidth : 0
		},
		callback : initDataTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'dataDialog' ]
	});
}
function initDetailComponent() {
	initDataDialog();
	initTypeDialog();
	
	$('#payConfigBankDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 300,
		height : 150,
		buttons : [ {
			text : '确定',
			handler : function() {
				saveChangeGateway();
				hideDialog('payConfigBankDialog');
			}
		}, {
			text : '取消',
			handler : function() {
				hideDialog('payConfigBankDialog');
			}
		}]
	});
}
//初始化加载添加界面
function initTypeDialog() {
	$('#payConfigBankDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 380,
		height : 120
	});
}


function initDataTbl(size) {
	var option = {
			datatype : function (pdata) {
				findPayConfig();
		    },
		colNames : [ 'id','支付类型','支付方式名称','payType','银行网关类型','bankGatewayType','手续费率(‰)','后手续费率(‰)','启用标识','后手续费率生效时间'],
		colModel : [ 
		{
			name : 'id',
			index : 'id',
			width : 100,
			hidden:true
		},{
			name : 'payTypeName',
			index : 'payTypeName',
			width : 70,
			align : 'left',
			sortable : false
		},
		
		{
			name : 'payWayName',
			index : 'payWayName',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'payType',
			index : 'payType',
			hidden:true
		},
		{
			name : 'bankGatewayName',
			index : 'bankGatewayName',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'bankGatewayType',
			index : 'bankGatewayType',
			hidden:true
		},
		{
			name : 'proceRate',
			index : 'proceRate',
			width : 70,
			align : 'right',
			sortable : false
		},
		{
			name : 'afterProceRate',
			index : 'afterProceRate',
			width : 70,
			align : 'right',
			sortable : false
		},  {
			name : 'activate',
			index : 'activate',
			width : 50,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
				
				if(cellval == '0') {return '启用'} 
				else if(cellval == '1') {return '停用'	} 
				else { return ''};
			}
		},
		{
			name : 'afterProceRateDate',
			index : 'afterProceRateDate',
			width : 100,
			align : 'left',
			sortable : false
			
		}
		],
		//caption : "用户列表",
		//sortname : 'name',
		height : size.height - 50,
		multiselect: true,
		width : size.width,
		pager : '#payConfigPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#payConfigTbl').jqGrid(option);
	$("#t_payConfigTbl").append($('#payConfigToolbar'));
}
function initDataDialog() {
	$('#viewTraFlowDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}


function findPayConfig(defaultPageNo) {
	var payConfigQuery = {
		page : getJqgridPage('payConfigTbl', defaultPageNo),
		payType:$("#payTypeQuery").combobox('getValue'),
		payWayName:$.trim($("#payNameQuery").val()),
		activate:$("#activateQuery").combobox('getValue'),
		afterProceRateDateEnd:$.trim($("#afterProceRateDateEnd").val()),
		afterProceRateDateStart:$.trim($("#afterProceRateDateStart").val())
	}

	ddpAjaxCall({
		url : "findPayConfigByPage",
		data : payConfigQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#payConfigTbl'), data.responseEntity);
			}else{
				msgShow(systemWarnLabel, data.message, 'warning');
			}	
		}
	});
}
function showViewMerchant(){
	$('#viewMerchantUserDialog').show().dialog('open');
}
function initEditComponent(){
	$("#afterProceRateEdit").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#afterProceRateEdit").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#afterProceRateEdit").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
}
//查看用户详情
function viewPayConfig(){
	var selarrrow = $("#payConfigTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
    var selrow = $("#payConfigTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#payConfigTbl').getRowData(selrow);
		ddpAjaxCall({
			url : "viewPayConfig",
			data : rowData.id,
			successFn : loadPayConfigView
		});
		//window.location.href ="viewMerUser.htm?userId="+rowData.userId;
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function showViewPayConfig(){
	$('#viewPayConfigDialog').show().dialog('open');
}
function closeViewPayConfig(){
	clearViewPayConfig();
	$('#viewPayConfigDialog').show().dialog('close');
}

function clearViewPayConfig(){
	$('#payWayNameView').html("");
	$('#payTypeNameView').html("");
	
	$('#bankGatewayTypeView').html("");
	$('#gatewayNumberView').html("");
	$('#proceRateView').html("");
	$('#afterProceRateView').html("");
	$('#afterProceRateDateView').html("");
	$('#activateView').html("");
	$('#imageNameView').html("");
	
	$('#createUserView').html("");
	$('#createDateView').html("");
	$('#updateUserView').html("");
	$('#updateDateView').html("");
}
function loadPayConfigView(data){
	if(data.code=="000000"){
		showViewPayConfig();
		var payConfig = data.responseEntity;
	
		$('#payWayNameView').html(payConfig.payWayName);
		$('#payTypeNameView').html(payConfig.payTypeName);
		
		$('#bankGatewayTypeView').html(payConfig.bankGatewayName);
		$('#gatewayNumberView').html(payConfig.gatewayNumber);
		$('#proceRateView').html(payConfig.proceRate+"‰");
		$('#afterProceRateView').html(payConfig.afterProceRate+"‰");
		$('#afterProceRateDateView').html(payConfig.afterProceRateDate);
		$('#activateView').html(payConfig.activate==0?"启用":"禁用");
		$('#imageNameView').html(payConfig.imageName);
		
		
		$('#createUserView').html(payConfig.createUser);
		$('#createDateView').html(payConfig.createDateView);
		$('#updateUserView').html(payConfig.updateUser);
		$('#updateDateView').html(payConfig.updateDateView);
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
function showEditPayConfig(){
	$('#editPayConfigDialog').show().dialog('open');
}
function closeEditPayConfig(){
			$('#payWayName').val("");
			$('#afterProceRate').val("");
			$('#afterProceRateDateEdit').val("");
			$('#editPayConfigDialog').show().dialog('close');
}
function cancelEditPayConfig(){
	$.messager.confirm(systemConfirmLabel, "确定放弃当前所做的操作?", function(r) {
		if (r) {
			$('#payWayName').val("");
			$('#afterProceRate').val("");
			$('#afterProceRateDateEdit').val("");
			$('#editPayConfigDialog').show().dialog('close');
		}
	});
}
function toEditPayConfig(){
	var selarrrow = $("#payConfigTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
    var selrow = $("#payConfigTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#payConfigTbl').getRowData(selrow);
		clearAllText('editPayConfigDialog');
		ddpAjaxCall({
			url : "viewPayConfig",
			data : rowData.id,
			successFn : loadPayConfigEdit
		});
		//window.location.href ="viewMerUser.htm?userId="+rowData.userId;
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function loadPayConfigEdit(data){
	if(data.code=="000000"){
		showEditPayConfig();
		var payConfig = data.responseEntity;
		$('#payId').val(payConfig.id);
		$('#payWayNameEdit').val(payConfig.payWayName);
		$('#payTypeNameEdit').html(payConfig.payTypeName);
		
		$('#bankGatewayTypeEdit').html(payConfig.bankGatewayName);
		$('#gatewayNumberEdit').html(payConfig.gatewayNumber);
		$('#proceRateEdit').html(payConfig.proceRate+"‰");
		$('#afterProceRateEdit').val(payConfig.afterProceRate);
		$('#afterProceRateDateEdit').val(payConfig.afterProceRateDate!=null?payConfig.afterProceRateDate.substr(0,11):"");
		
		$("input[name='activateEdit'][value="+payConfig.activate+"]").attr("checked",true);

		$('#imageNameEdit').html(payConfig.imageName);
		
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function editPayConfig(){
	if(isValidate('editPayConfigDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存支付配置信息吗？", function(r) {
			if (r) {
				var bean = {
						id:$('#payId').val(),
						payWayName:$.trim($('#payWayNameEdit').val()),
						afterProceRate:$('#afterProceRateEdit').val(),
						afterProceRateDate:$('#afterProceRateDateEdit').val()
				};
				ddpAjaxCall({
					url : "editPayConfig",
					data : bean,
					successFn : afterUpdate
				});
			}
		});
	}
}
function afterUpdate(data){
	if(data.code=="000000"){
		findPayConfig();
		closeEditPayConfig();
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
function toStart(){
	var selarrrow = $("#payConfigTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用支付配置信息吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#payConfigTbl").getRowData(selarrrow[i]);
					ids.push(rowData.id);
				}
				ddpAjaxCall({
					url : "startPayConfig",
					data : ids,
					successFn : afterStopOrStop
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function  afterStopOrStop(){
	findPayConfig();
}
function toStop(){
	var selarrrow = $("#payConfigTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用支付配置信息吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#payConfigTbl").getRowData(selarrrow[i]);
					ids.push(rowData.id);
				}
				ddpAjaxCall({
					url : "stopPayConfig",
					data : ids,
					successFn : afterStopOrStop
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

/**
 * 银行网关切换页面打开
 */
function changeGateway(){
	$("#GatewayTypeName").html('');
	$("#GatewayType").val('');
	var selarrrow = $("#payConfigTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		var gateType="0";
		for(var i=0; i<selarrrow.length; i++) {
			//var user = $('#dataTbl').getRowData(selrow);
			var rowData = $("#payConfigTbl").getRowData(selarrrow[i]);
			if(rowData.payType!=3){
				msgShow(systemWarnLabel, '请选择银行支付方式进行切换', 'warning');
				return;
			}
			if(i==0&&rowData.payType!=gateType){
				gateType=rowData.bankGatewayType;
			}else if(rowData.bankGatewayType!=gateType){
				msgShow(systemWarnLabel, '请选择同一种银行网关类型', 'warning');
				return;
			}
			$("#GatewayTypeName").html(gateType=="0"?"财付通":"支付宝");
			$("#GatewayType").val(gateType=="0"?"1":"0");
		}
		clearAllText('payConfigBankDialog');
		showDialog('payConfigBankDialog');
		
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function saveChangeGateway(){
	var selarrrow = $("#payConfigTbl").getGridParam('selarrrow');// 多选
	//TODO 同类型
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定进行银行网关切换吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#payConfigTbl").getRowData(selarrrow[i]);
					ids.push(rowData.id);
				}
				console.log(ids);
				console.log($("#GatewayType").val());
				var map={
						"ids":ids,
						"GatewayTypeId":$("#GatewayType").val()
				}
				ddpAjaxCall({
					url : "changeGateway",
					data : map,
					successFn : afterChange
				});
			}
		});
		
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function  afterChange(data){
	if(data.code=="000000"){
		findPayConfig();
		hideDialog('payConfigBankDialog');
		$("#GatewayTypeName").html('');
		$("#GatewayType").val('');
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
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

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PayConfigBean", 	/*must*/// type of export
	"toUrl"			: "exportExcelPayConfig" 			/*must*/// the export url
};
var filterConStr = [	// filter the result by query condition
		{'name':'payTypeQuery', 	'value': "escapePeculiar($.trim($('#payTypeQuery').combobox('getValue')))"			},	// eval
		{'name':'payNameQuery',	'value': "escapePeculiar($.trim($('#payNameQuery').val()))"		},
		{'name':'activateQuery',	'value': "escapePeculiar($('#activateQuery').combobox('getValue'))"		},
		{'name':'payNameQuery',	'value': "escapePeculiar($.trim($('#afterProceRateDateStart').val()))"		},
		{'name':'payNameQuery',	'value': "escapePeculiar($.trim($('#afterProceRateDateEnd').val()))"		}
	];
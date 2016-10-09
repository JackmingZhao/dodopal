
/*************************************************  数据导出 start *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "ProductYktInfoForExport", 	/*must*/// type of export
	"toUrl"			: "exportExcelProductYkt", 			/*must*/// the export url
	"parDiaHeight"  : "360"
};
var filterConStr = [	// filter the result by query condition
		{'name':'yktName', 	'value': "escapePeculiar($.trim($('#icdcNameQuery').val()))"			},
		{'name':'activate',	'value': "escapePeculiar($('#icdcActiveQuery').combobox('getValue'))"		}
	];
/*************************************************  数据导出 end *****************************************************************/

$(function () {
	
	initActiveCombobox();
	initYktRechargeSetTypeCombobox();
	initYktPaySetTypeCombobox();
    initMainComponent();
    expExcBySelColTools.initSelectExportDia(expConfInfo);
    parent.closeGlobalWaitingDialog();

});

function initActiveCombobox() {
	
	$('#icdcActiveQuery').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 120
	});
	
	var ddic = {
			categoryCode : 'ACTIVATE'
	}
	ddpAjaxCall({
		url : $.base + "/basic/ddicMgmt/findDdicByCategoryCode",
		data : ddic.categoryCode,
		async : false,
		successFn : loadActive
	});
}
function loadActive(data) {
	if (data.code == "000000") {
		if(isNotBlank(data.responseEntity)) {
			data.responseEntity.push({code:'',name:'全部'});
		}
		reLoadComboboxData($('#icdcActiveQuery'), data.responseEntity)
		$('#icdcActiveQuery').combobox('select','0');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
function initMainComponent() {
	hideDialog('viewIcdcDialog');
	$('#yktProvinceCity').append(createAreaComponent2('yktProvinceCity'));
	initAreaComponent('yktProvinceCity', 150);
	
	$('#icdcDialog').show().dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
	
    autoResize({
        dataGrid: {
            selector: '#icdcBasicInfoTbl',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initTkgaBasicInfoTbl,
        toolbar: [true, 'top']
    });
    
	$("#yktRechargeRate").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#yktRechargeRate").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#yktRechargeRate").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	
	$("#yktPayRate").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#yktPayRate").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#yktPayRate").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	$("#yktCardMaxLimit").bind('keyup', function() {
		keepPositiveIntegerMoney($(this));
	});
	$("#yktCardMaxLimit").bind('blur', function() {
		keepPositiveIntegerMoney($(this));
	});
}
function keepPositiveIntegerMoney(obj) { 
	var money = obj.val();
	money = money.replace(/[^\d]/g,"");  
	if (money !="" && !isNaN(money)) {
		obj.val(parseInt(money));
	}
}
function initTkgaBasicInfoTbl(size) {
    var option = {
        datatype: function (pdata) {
        	icdcQuery();
        },
        colNames: ['ID', '一卡通代码', '通卡公司名称', '省份', '城市', '业务城市', '充值业务', '充值费率(‰)', '支付业务', '支付费率(‰)','启用标识'],
        colModel: [
            {name: 'id', hidden: true},
            {name: 'yktCode', index: 'yktCode', width: 200, align: 'left', sortable: false},
            {name: 'yktName', index: 'yktName', width: 200, align: 'left', sortable: false, formatter: htmlFormatter},
            {name: 'provinceName', index: 'provinceName', width: 120, align: 'left', sortable: false},
            {name: 'cityName', index: 'cityName', width: 120, align: 'left', sortable: false},
            {name: 'businessCityName', index: 'businessCityName', width: 160, align: 'left', sortable: false},
            {name: 'yktIsRechargeView', index: 'yktIsRechargeView', width: 100, align: 'center', sortable: false},
            {name: 'yktRechargeRate', index: 'yktRechargeRate', width: 100, align: 'right', sortable: false},
            {name: 'yktIsPayView', index: 'yktIsPayView', width: 100, align: 'center', sortable: false},
            {name: 'yktPayRate', index: 'yktPayRate', width: 100, align: 'right', sortable: false},
            {name: 'activateView', index: 'activateView', width: 100, align: 'center', sortable: false}
        ],
        height: size.height - 50,
        width: size.width,
        multiselect: true,
        forceFit:true,
        pager: '#icdcBasicInfoTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#icdcBasicInfoTbl').jqGrid(option);
    $("#t_icdcBasicInfoTbl").append($('#icdcBasicInfoTblToolbar'));
}
//加载通卡公司基本信息
function icdcQuery(defaultPageNo) {
    ddpAjaxCall({
        url: "findProductYktByPage",
        data: {
        	yktName: escapePeculiar($.trim($('#icdcNameQuery').val())),
        	activate : $('#icdcActiveQuery').combobox('getValue'),
            page: getJqgridPage('icdcBasicInfoTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcBasicInfoTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function clearIcdcAllText(){
	clearAllText('queryCondition');
	$('#icdcActiveQuery').combobox('select','0');
}
function cancelSaveIcdcAction(){
	$.messager.confirm("确定",'确定放弃当前所做的操作?',function(r){  
	    if (r){
	    	$('#icdcDialog').hide().dialog('close');  
	    	clearHiddenValue();
	    }  
	});
}
function clearHiddenValue(){
	$("#chooseCityName").val("");
	$('#icdcCityName').val("");
	$("#icdcCityId").val("");
	$("#icdcId").val("");
	$("#icdcCode").val("");
	$("#icdcCityId").val("");
	$("#chooseCity").val("");
	// 记录总共被选择的城市code
	chooseCityCodeArray = new Array();
	// 记录通卡公司本身已关联的城市code
	oldchooseCityCodeArray = new Array();
}
function icdcAdd() {
	destroyValidate('icdcInfoTable');
	//页面初期花
	clearAllText('icdcInfoTable');
	clearHiddenValue();
	$("#yktAddress").val('');
	$("#remark").val('');
	$("#merCode").val('');
	$("#yktCode").removeAttr('disabled');
	$("input[name='activate']").removeAttr("disabled");
	$("input[name='yktPayFlag'][value=0]").attr("checked",true); 
	$("input[name='activate'][value=0]").attr("checked",true);
	$("#yktIsRecharge").attr("checked",true);
	$("#yktRechargeRate").removeAttr('disabled');
	$('#yktRechargeSetType').combobox('enable');
	$('#yktRechargeSetPara').removeAttr('disabled');
	$("#yktIsPay").attr("checked",false);
	$("#yktPayRate").attr('disabled','true');
	$('#yktPaysetType').combobox('disable');
	$('#yktPaySetPara').attr('disabled','true');
	$("#yktRechargeRateFont").show();
	$('#yktRechargeSetTypeFont').show();
	$('#yktRechargeSetParaFont').show();
	$('#yktRechargeSetParaTypeFont').hide();
	
	$("#yktPayRateFont").attr("style","visibility:hidden");
	$('#yktPaysetTypeFont').attr("style","visibility:hidden");
	$('#yktPaySetParaFont').attr("style","visibility:hidden");
	$('#yktPaySetParaTypeFont').attr("style","visibility:hidden");
	
	showDialog('icdcDialog');
}
function initYktRechargeSetTypeCombobox(){
	$('#yktRechargeSetType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 300,
		onSelect: function(data){
			var type = $("#yktRechargeSetType").combobox('getValue');
			$("#yktRechargeSetPara").val("");
			if (type == '0') {
				$("#yktRechargeSetParaTypeFont").text("天");
			} else if (type == '1') {
				$("#yktRechargeSetParaTypeFont").text("笔");
			} else if (type == '2') {
				$("#yktRechargeSetParaTypeFont").text("元");
			} else {
				$("#yktRechargeSetParaTypeFont").text("");
			}
			$('#yktRechargeSetParaTypeFont').show();
		}
	});
	var ddic = {
			categoryCode : 'BUSINESS_SETTLEMENT_TYPE'
	}
	loadDdics(ddic, function(data){
		if (data.code == "000000") {
			addTipsOption(data.responseEntity);
			reLoadComboboxData($('#yktRechargeSetType'), data.responseEntity);
			$('#yktRechargeSetType').combobox('select','');
		} else {
			msgShow(systemWarnLabel, data.message, 'warning');
		}
	});
}
function initYktPaySetTypeCombobox(){
	$('#yktPaysetType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 300,
		onSelect: function(data){
			var type = $("#yktPaysetType").combobox('getValue');
			$("#yktPaySetPara").val("");
			if (type == '0') {
				$("#yktPaySetParaTypeFont").text("天");
			} else if (type == '1') {
				$("#yktPaySetParaTypeFont").text("笔");
			} else if (type == '2') {
				$("#yktPaySetParaTypeFont").text("元");
			} else {
				$("#yktPaySetParaTypeFont").text("");
			}
			$('#yktPaySetParaTypeFont').attr("style","visibility:visible");
		}
	});
	var ddic = {
			categoryCode : 'BUSINESS_SETTLEMENT_TYPE'
	}
	loadDdics(ddic, function(data){
		if (data.code == "000000") {
			addTipsOption(data.responseEntity);
			reLoadComboboxData($('#yktPaysetType'), data.responseEntity);
			$('#yktPaysetType').combobox('select','');
		} else {
			msgShow(systemWarnLabel, data.message, 'warning');
		}
	});
}

function validateSaveIcdc(){
	var provinceId = getProvinceCodeFromCompoent('yktProvinceCity');
	if (provinceId == '' || provinceId == null) {
		msgShow(systemWarnLabel, '请选择所在省份', 'warning');
		return false;
	}
	var cityId = getCityCodeFromCompoent('yktProvinceCity');
	if (cityId == '' || cityId == null) {
		msgShow(systemWarnLabel, '请选择所在城市', 'warning');
		return false;
	}
	if($("#chooseCity").val() == ''){
		msgShow(systemWarnLabel, '请选择业务城市', 'warning');
		return false;
	}
	if ($("#yktIsRecharge").is(":checked")) {
		if ($("#yktRechargeRate").val() =='') {
			msgShow(systemWarnLabel, '请填写有效的一卡通充值费率', 'warning');
			return false;
		}
		if ($("#yktRechargeSetType").combobox('getValue') =='') {
			msgShow(systemWarnLabel, '请设置一卡通充值业务结算类型', 'warning');
			return false;
		}
		if ($("#yktRechargeSetPara").val() =='' || parseFloat($("#yktRechargeSetPara").val()) ==0) {
			msgShow(systemWarnLabel, '请填写有效的一卡通充值业务结算类型值', 'warning');
			return false;
		}
	}

	if ($("#yktIsPay").is(":checked")) {
		if ($("#yktPayRate").val() =='') {
			msgShow(systemWarnLabel, '请填写有效的一卡通支付费率', 'warning');
			return false;
		}
		if ($("#yktPaysetType").combobox('getValue') =='') {
			msgShow(systemWarnLabel, '请设置一卡通支付业务结算类型', 'warning');
			return false;
		}
		if ($("#yktPaySetPara").val() =='' || parseFloat($("#yktPaySetPara").val()) ==0) {
			msgShow(systemWarnLabel, '请填写有效的一卡通支付业务结算类型值', 'warning');
			return false;
		}
	}
	
	if (!$("#yktIsRecharge").is(":checked") && !$("#yktIsPay").is(":checked")) {
		msgShow(systemWarnLabel, '请至少选择一项业务', 'warning');
		return false;
	}

	return true;
}
function saveIcdc(){
	if(isValidate('icdcDialog') && validateSaveIcdc()) {
		$.messager.confirm(systemConfirmLabel, "确定要保存通卡公司信息吗？", function(r) {
			if (r) {
				// 对一卡通公司消费和充值  时间区间限制 ;开始时间和结束时间必须同时存在 要么同时都不存在
				var yktRechargeLimitStartTime = $("#yktRechargeLimitStartTime").val();
				var yktRechargeLimitEndTime = $("#yktRechargeLimitEndTime").val();  
				var yktConsumeLimitStartTime = $("#yktConsumeLimitStartTime").val(); 
				var yktConsumeLimitEndTime = $("#yktConsumeLimitEndTime").val();   
				if(yktRechargeLimitStartTime == '' && yktRechargeLimitEndTime != ''){
					msgShow(systemWarnLabel, '请选择充值限制开始时间', 'warning');
					return false;
				}else if(yktRechargeLimitStartTime != '' && yktRechargeLimitEndTime == ''){
					msgShow(systemWarnLabel, '请选择充值限制结束时间', 'warning');
					return false;
				}
				
				if(yktConsumeLimitStartTime == '' && yktConsumeLimitEndTime != ''){
					msgShow(systemWarnLabel, '请选择消费限制开始时间', 'warning');
					return false;
				}else if(yktConsumeLimitStartTime != '' && yktConsumeLimitEndTime == ''){
					msgShow(systemWarnLabel, '请选择消费限制结束时间', 'warning');
					return false;
				}
				
				var isRecharge = "1";
				if ($("#yktIsRecharge").is(":checked")) {
					isRecharge = "0";
				}
				var isPay = "1";
				if ($("#yktIsPay").is(":checked")) {
					isPay = "0";
				}
				var businesscitys = '';
				if ($("#chooseCity").val() != '' && $("#chooseCity").val().indexOf(",")==0) {
					businesscitys = $("#chooseCity").val().substring(1);
				} else {
					businesscitys = $("#chooseCity").val();
				}
				var rechargeSetType = $("#yktRechargeSetType").combobox('getValue');
				var rechargeSetPara = $("#yktRechargeSetPara").val();
				if (rechargeSetType == '2') {
					rechargeSetPara = rechargeSetPara*100;
				}
				var rePaySetPara = $("#yktPaysetType").combobox('getValue');
				var paySetPara = $("#yktPaySetPara").val();
				if (rePaySetPara == '2') {
					paySetPara = paySetPara*100;
				}
				var productYKTBDBean = {
						id : $('#icdcId').val(),
						yktCode:$('#yktCode').val(),
						yktName:$('#yktName').val(),
						cityId : getCityCodeFromCompoent('yktProvinceCity'),
						cityName : getCityNameFromCompoent('yktProvinceCity'),
						provinceId : getProvinceCodeFromCompoent('yktProvinceCity'),
						provinceName : getProvinceNameFromCompoent('yktProvinceCity'),
						yktAddress:$('#yktAddress').val(),
						businessCityId:businesscitys,
						yktPayFlag:$("input[name='yktPayFlag']:checked").val(),
						activate:$("input[name='activate']:checked").val(),
						yktIsRecharge:isRecharge,
						yktRechargeRate:$("#yktRechargeRate").val(),
						yktRechargeSetType:rechargeSetType,
						yktRechargeSetPara:rechargeSetPara,
						yktIsPay:isPay,
						yktPayRate:$("#yktPayRate").val(),
						yktPaysetType:rePaySetPara,
						yktPaySetPara:paySetPara,
						yktCardMaxLimit:$("#yktCardMaxLimit").val()*100,
						yktIpAddress:$("#yktIpAddress").val(),
						yktPort:$("#yktPort").val(),
						yktLinkUser:$("#yktLinkUser").val(),
						yktMobile:$("#yktMobile").val(),
						yktTel:$("#yktTel").val(),
						remark:$("#remark").val(),
						merCode:$("#merCode").val(),
						yktRechargeLimitStartTime:yktRechargeLimitStartTime,
						yktRechargeLimitEndTime:yktRechargeLimitEndTime,  
						yktConsumeLimitStartTime:yktConsumeLimitStartTime, 
						yktConsumeLimitEndTime:yktConsumeLimitEndTime 
				};
			    ddpAjaxCall({
			        url: "saveProductYkt",
			        data: productYKTBDBean,
			        successFn: function (data) {
			        	if(data.code == "000000") {
			        		clearHiddenValue();
			        		hideDialog('icdcDialog');
			        		icdcQuery(1);
			        	} else {
			        		msgShow(systemWarnLabel, data.message, 'warning');
			        	}
			        }
			    });
			}
		});
	}
}

function icdcModify() {
	//页面初期花
	destroyValidate('icdcInfoTable');
	$("#yktCode").attr("disabled",true);
	$("input[name='activate']").attr("disabled",true);
	
	var rowData = getSelectedRowDataFromMultiples('icdcBasicInfoTbl');
	if(rowData != null) {
		ddpAjaxCall({
			url : "findProductYktById",
			data : rowData.id,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					setAreaComponent('yktProvinceCity', data.responseEntity.provinceId, data.responseEntity.cityId);
					$('#icdcCode').val(data.responseEntity.yktCode);
					$('#yktRechargeSetType').combobox('select', data.responseEntity.yktRechargeSetType);
					$('#yktPaysetType').combobox('select', data.responseEntity.yktPaysetType);
					$('#yktCode').val(data.responseEntity.yktCode);
					$('#yktName').val(data.responseEntity.yktName);
					$('#yktAddress').val(data.responseEntity.yktAddress);
					$('#yktRechargeRate').val(data.responseEntity.yktRechargeRate);
					$('#yktPayRate').val(data.responseEntity.yktPayRate);
					var yktRechargeSetPara = data.responseEntity.yktRechargeSetPara;
					if ($("#yktRechargeSetType").combobox('getValue')=='2'){
						yktRechargeSetPara = yktRechargeSetPara/100;
					}
					$('#yktRechargeSetPara').val(yktRechargeSetPara);
					
					var yktPaySetPara = data.responseEntity.yktPaySetPara;
					if ($("#yktPaysetType").combobox('getValue')=='2') {
						yktPaySetPara = yktPaySetPara/100;
					}
					$('#yktPaySetPara').val(yktPaySetPara);
					$('#yktIpAddress').val(data.responseEntity.yktIpAddress);
					$('#yktPort').val(data.responseEntity.yktPort);
					$('#yktCardMaxLimit').val(data.responseEntity.yktCardMaxLimit/100);
					$('#yktLinkUser').val(data.responseEntity.yktLinkUser);
					$('#yktMobile').val(data.responseEntity.yktMobile);
					$('#yktTel').val(data.responseEntity.yktTel);
					$('#remark').val(data.responseEntity.remark);
					
					$("input[name='yktPayFlag'][value="+data.responseEntity.yktPayFlag+"]").attr("checked",true); 
					
					$("input[name='activate'][value="+data.responseEntity.activate+"]").attr("checked",true); 
					$("input[name='activate']:eq(0)").attr("disabled","disabled"); 
					$("input[name='activate']:eq(1)").attr("disabled","disabled"); 
					
					//　修改时间
					$("#yktRechargeLimitStartTime").val(data.responseEntity.yktRechargeLimitStartTime);
					$("#yktRechargeLimitEndTime").val(data.responseEntity.yktRechargeLimitEndTime);
					$("#yktConsumeLimitStartTime").val(data.responseEntity.yktConsumeLimitStartTime);
					$("#yktConsumeLimitEndTime").val(data.responseEntity.yktConsumeLimitEndTime);
					
					if (data.responseEntity.yktIsRecharge == '0') {
						$('#yktIsRecharge').attr("checked","checked");
						$("#yktRechargeRate").removeAttr('disabled');
						$('#yktRechargeSetType').combobox('enable');
						$('#yktRechargeSetPara').removeAttr('disabled');
						$("#yktRechargeRateFont").show();
						$('#yktRechargeSetTypeFont').show();
						$('#yktRechargeSetParaFont').show();
						$('#yktRechargeSetParaTypeFont').show();
					} else {
						$('#yktIsRecharge').removeAttr("checked");
						$("#yktRechargeRate").attr('disabled','true');
						$('#yktRechargeSetType').combobox('disable');  
						$('#yktRechargeSetPara').attr('disabled','true');
						$("#yktRechargeRateFont").hide();
						$('#yktRechargeSetTypeFont').hide();
						$('#yktRechargeSetParaFont').hide();
						$('#yktRechargeSetParaTypeFont').hide();
					}
					if (data.responseEntity.yktIsPay== '0') {
						$('#yktIsPay').attr("checked","checked");
						$("#yktPayRate").removeAttr('disabled');
						$('#yktPaysetType').combobox('enable');
						$('#yktPaySetPara').removeAttr('disabled');
						$("#yktPayRateFont").attr("style","visibility:visible");
						$('#yktPaysetTypeFont').attr("style","visibility:visible");
						$('#yktPaySetParaFont').attr("style","visibility:visible");
						$('#yktPaySetParaTypeFont').attr("style","visibility:visible");
					} else {
						$('#yktIsPay').removeAttr("checked");
						$("#yktPayRate").attr('disabled','true');
						$('#yktPaysetType').combobox('disable');
						$('#yktPaySetPara').attr('disabled','true');
						$("#yktPayRateFont").attr("style","visibility:hidden");
						$('#yktPaysetTypeFont').attr("style","visibility:hidden");
						$('#yktPaySetParaFont').attr("style","visibility:hidden");
						$('#yktPaySetParaTypeFont').attr("style","visibility:hidden");
					}
					
					// 画面隐藏值
					$('#icdcId').val(data.responseEntity.id);
					$('#icdcCityId').val(data.responseEntity.businessCityId);
					$('#icdcCityName').val(data.responseEntity.businessCityName);
					$("#chooseCity").val(data.responseEntity.businessCityId);
					$("#businessCity").val(data.responseEntity.businessCityName);
					$('#merCode').val(data.responseEntity.merCode);
					showDialog('icdcDialog');
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

//停用通卡公司
function icdcDisable() {
	var selarrrow = $("#icdcBasicInfoTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用通卡公司吗？", function(r) {
			if (r) {
				var yktCodes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#icdcBasicInfoTbl").getRowData(selarrrow[i]);
					yktCodes.push(rowData.yktCode);
				}
				ddpAjaxCall({
					url : "startOrStopYkt?activate=1",
					data : yktCodes,
					successFn : afterUpdateIcdc
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
//启用通卡公司
function icdcEnable() {
	var selarrrow = $("#icdcBasicInfoTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用通卡公司吗？", function(r) {
			if (r) {
				var yktCodes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#icdcBasicInfoTbl").getRowData(selarrrow[i]);
					yktCodes.push(rowData.yktCode);
				}
				ddpAjaxCall({
					url : "startOrStopYkt?activate=0",
					data : yktCodes,
					successFn : afterUpdateIcdc
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function afterUpdateIcdc(data) {
	if(data.code == "000000") {
		icdcQuery();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function closeIcdcView() {
	hideDialog('viewIcdcDialog');
}
function icdcView() {
	var rowData = getSelectedRowDataFromMultiples('icdcBasicInfoTbl');
	if (rowData != null) {
		ddpAjaxCall({
			url : "findProductYktById",
			data : rowData.id,
			successFn : loadViewIcdc
		});
	}
}
function loadViewIcdc(data) {
	if(data.code=="000000"){
		$('#viewIcdcDialog').show().dialog('open');
		var yktBean = data.responseEntity;
		$('#view_yktCode').html(yktBean.yktCode);
		$('#view_yktName').html(yktBean.yktName);
		$('#view_provinceName').html(yktBean.provinceName);
		$('#view_cityName').html(yktBean.cityName);
		$('#view_yktBusinessCity').html(yktBean.businessCityName);
		$('#view_yktAddress').html(htmlTagFormat(yktBean.yktAddress));
		$('#view_yktPayFlag').html(yktBean.yktPayFlagView);
		$('#view_activate').html(yktBean.activateView);
		
		if(yktBean.yktRechargeLimitStartTime != null && yktBean.yktRechargeLimitEndTime != null){
			$("#view_yktRechargeLimitTime").html(yktBean.yktRechargeLimitStartTime+" --  "+yktBean.yktRechargeLimitEndTime);
		}
		if(yktBean.yktConsumeLimitStartTime !=null && yktBean.yktConsumeLimitEndTime !=null){
			$("#view_yktConsumeLimitTime").html(yktBean.yktConsumeLimitStartTime+" --  "+yktBean.yktConsumeLimitEndTime);
		}
		
		if (yktBean.yktRechargeRate !=null) {
			$('#view_yktRechargeRate').html(yktBean.yktRechargeRate+"‰");
		} else {
			$('#view_yktRechargeRate').html("");
		}
		if (yktBean.yktPayRate !=null) {
			$('#view_yktPayRate').html(yktBean.yktPayRate+"‰");
		} else {
			$('#view_yktPayRate').html("");
		}
		
		var setType = "";
		var yktRechargeSetType = yktBean.yktRechargeSetType;
		if (yktRechargeSetType == "0") {
			setType = "天";
		} else if (yktRechargeSetType == "1") {
			setType = "笔";
		} else if (yktRechargeSetType == "2") {
			setType = "元";
		}
		$('#view_yktRechargeSetType').html(yktBean.yktRechargeSetTypeView);
		
		var yktRechargeSetPara = yktBean.yktRechargeSetPara;
		if (yktRechargeSetPara!=''&& yktRechargeSetPara!=null) {
			if (yktRechargeSetType == "2") {
				yktRechargeSetPara = yktRechargeSetPara/100;
			}
			$('#view_yktRechargeSetPara').html(yktRechargeSetPara + setType);
		} else {
			$('#view_yktRechargeSetPara').html("");
		}

		var yktPaysetType = yktBean.yktPaysetType;
		if (yktPaysetType == "0") {
			setType = "天";
		} else if (yktPaysetType == "1") {
			setType = "笔";
		} else if (yktPaysetType == "2") {
			setType = "元";
		}
		$('#view_yktPaysetType').html(yktBean.yktPaysetTypeView);
		
		var yktPaySetPara = yktBean.yktPaySetPara;
		if (yktPaySetPara!=''&& yktPaySetPara!=null) {
			if (yktPaysetType == "2") {
				yktPaySetPara = yktPaySetPara/100;
			}
			$('#view_yktPaySetPara').html(yktPaySetPara + setType);
		} else {
			$('#view_yktPaySetPara').html("");
		}
		
		$('#view_yktIpAddress').html(yktBean.yktIpAddress);
		$('#view_yktPort').html(yktBean.yktPort);
		var yktCardMaxLimit = yktBean.yktCardMaxLimit;
		if (yktCardMaxLimit!=''&& yktCardMaxLimit!=null) {
			$('#view_yktCardMaxLimit').html(yktBean.yktCardMaxLimit/100+"元");
		} else {
			$('#view_yktCardMaxLimit').html("");
		}
		$('#view_yktTel').html(yktBean.yktTel);
		$('#view_yktLinkUser').html(yktBean.yktLinkUser);
		$('#view_yktMobile').html(yktBean.yktMobile);
		$('#view_remark').html(htmlTagFormat(yktBean.remark));
		$('#view_createDate').html(ddpDateFormatter(yktBean.createDate));
		$('#view_updateDate').html(ddpDateFormatter(yktBean.updateDate));
		$('#view_createUser').html(yktBean.createUser);
		$('#view_updateUser').html(yktBean.updateUser);
		
		var yktIsRecharge =yktBean.yktIsRecharge;
		if (yktIsRecharge == "0") {
			$("#view_yktIsRecharge").attr("checked","checked");
		} else if (yktIsRecharge == "1") {
			$("#view_yktIsRecharge").removeAttr("checked");
		}
		var yktIsPay =yktBean.yktIsPay;
		if (yktIsPay == "0") {
			$("#view_yktIsPay").attr("checked","checked");
		} else if (yktIsPay == "1") {
			$("#view_yktIsPay").removeAttr("checked");
		}
		
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}

	
}
function chooseBussiness(bussiness) {
	if (bussiness=='yktIsRecharge') {
		if ($("#yktIsRecharge").is(":checked")) {
			$("#yktRechargeRate").removeAttr('disabled');
			$('#yktRechargeSetType').combobox('enable');
			$('#yktRechargeSetPara').removeAttr('disabled');
			$("#yktRechargeRateFont").show();
			$('#yktRechargeSetTypeFont').show();
			$('#yktRechargeSetParaFont').show();
			$('#yktRechargeSetParaTypeFont').hide();
		} else {
			$("#yktRechargeRate").val('');
			$("#yktRechargeSetPara").val('');
			$('#yktRechargeSetType').combobox('select', '');
			$("#yktRechargeRate").attr('disabled','true');
			$('#yktRechargeSetType').combobox('disable');  
			$('#yktRechargeSetPara').attr('disabled','true');
			$("#yktRechargeRateFont").hide();
			$('#yktRechargeSetTypeFont').hide();
			$('#yktRechargeSetParaFont').hide();
			$('#yktRechargeSetParaTypeFont').hide();
		}
	}else if (bussiness=='yktIsPay') {
		if ($("#yktIsPay").is(":checked")) {
			$("#yktPayRate").removeAttr('disabled');
			$('#yktPaysetType').combobox('enable');
			$('#yktPaySetPara').removeAttr('disabled');
			$("#yktPayRateFont").attr("style","visibility:visible");
			$('#yktPaysetTypeFont').attr("style","visibility:visible");
			$('#yktPaySetParaFont').attr("style","visibility:visible");
			$('#yktPaySetParaTypeFont').attr("style","visibility:hidden");
		} else {
			$("#yktPayRate").val('');
			$('#yktPaySetPara').val('');
			$("#yktPaysetType").combobox('select', '');
			$("#yktPayRate").attr('disabled','true');
			$('#yktPaysetType').combobox('disable');
			$('#yktPaySetPara').attr('disabled','true');
			$("#yktPayRateFont").attr("style","visibility:hidden");
			$('#yktPaysetTypeFont').attr("style","visibility:hidden");
			$('#yktPaySetParaFont').attr("style","visibility:hidden");
			$('#yktPaySetParaTypeFont').attr("style","visibility:hidden");
		}
	}

}

//-------------------------------------费率校验 start-------------------------------------//

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
		if (text.length >= 4  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,3)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 3) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 6) {
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
		if (text.length >= 4  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,3)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 3) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 6) {
			text = "";
		}
		obj.val(text);
	}
}

//-------------------------------------费率校验 end-------------------------------------//
//----------------- 重构JS数组的remove方法 start-------------------------//
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
};
//-------------------重构JS数组的remove方法  end------------------------------//
// -------------------------------------业务城市选项卡-------------------------------------//

// 记录总共被选择的城市code
var chooseCityCodeArray = new Array();
// 记录通卡公司本身已关联的城市code
var oldchooseCityCodeArray = new Array();

// 业务城市选择按钮按下
function showBussinessCity(){
	if ($("#chooseCity").val() != '') {
		chooseCityCodeArray = $("#chooseCity").val().split(",");
	}
	// 记录通卡公司已关联的城市code
	var icdcCityId = $("#icdcCityId").val();
	if (icdcCityId != '') {
		oldchooseCityCodeArray = $("#icdcCityId").val().split(","); 
		chooseCityCodeArray.concat($("#icdcCityId").val().split(","));
	}
	
	initCityTabs();
	initCitySearchbox();
	var oldChooseCityName = $('#icdcCityName').val();
	$("#chooseCityName").val(oldChooseCityName);
	$(".searchbox-text").val(oldChooseCityName);
	
	// 弹出选择Dialog
	showDialog("dd");
}

// 选项卡中点击城市触发
function chooseCity(element){
	
	// 画面显示选中的业务城市名称
	var cityNameStr = $("#chooseCityName").val();
	if ($.trim(cityNameStr)=='') {
		$("#chooseCityName").val($(element).text());
		$(".searchbox-text").val($(element).text());
	} else {
		$("#chooseCityName").val(cityNameStr+","+$(element).text());
		$(".searchbox-text").val(cityNameStr+","+$(element).text());
	}
	
	// 记录总共被选择的城市code
	chooseCityCodeArray.push($(element).attr("id"));
	$("#chooseCity").val(chooseCityCodeArray.join(","));
	
	$(element).attr("style","color:blue");  
	//$(element).removeAttr('href');
	$(element).attr("onclick","removeCity("+$(element).attr("id")+");");
}

function removeCity(removrCityId){
	if ($.inArray(removrCityId, chooseCityCodeArray) == -1) {
		chooseCityCodeArray.remove(removrCityId);
	}
	$('#'+removrCityId).removeAttr("style");
	$('#'+removrCityId).attr('onclick',"chooseCity(this);return false;");
		
	// 记录总共被选择的城市code
	$("#chooseCity").val(chooseCityCodeArray.join(","));
	
	// 画面显示选中的业务城市名称
	var cityNameStr = $("#chooseCityName").val();
	if ($.trim(cityNameStr)!='') {
		var chooseCityNameArray = $("#chooseCityName").val().split(",");
		chooseCityNameArray.remove($('#'+removrCityId).text());
		$("#chooseCityName").val(chooseCityNameArray.join(","));
		$(".searchbox-text").val(chooseCityNameArray.join(","));
	}
}
// 选项卡中确定按钮按下
function sureCity(){
	
	// 设置画面显示的被选择城市的名称数组
	var businessCityArrays = new Array();
	var oldChooseCityName = $('#icdcCityName').val().split(",");
	var chooseCityNameArrays= $("#chooseCityName").val().split(",");

	for(var i = 0; i < chooseCityNameArrays.length; i ++){ 
		if ($.inArray(chooseCityNameArrays[i], businessCityArrays) == -1) {
			businessCityArrays.push(chooseCityNameArrays[i]);
		}
	} 
	if (!(oldChooseCityName.length =1 && oldChooseCityName[0] == "")) {
		for(var i = 0; i < oldChooseCityName.length; i ++){ 
			if ($.inArray(oldChooseCityName[i], businessCityArrays) == -1) {
				businessCityArrays.push(oldChooseCityName[i]);
			}
		}
	}
	$("#businessCity").val(businessCityArrays.join(","));
	$('#icdcCityName').val($("#businessCity").val());
	// 设置画面隐藏值被选中城市的code数组
	$("#chooseCity").val(chooseCityCodeArray.join(","));
	hideDialog("dd");
	
}
// 选项卡中清空按钮按下
function cancelCity(){
	// 1.除去关联的城市code不让选择的限制
	var chooseCity = $("#chooseCity").val();
	if (chooseCity != "") {
		chooseCityCodeArray=$("#chooseCity").val().split(",");
	}
	if (chooseCityCodeArray.length > 0) {
		for (var i =0;i<chooseCityCodeArray.length;i++) {
			$('#'+chooseCityCodeArray[i]).attr("href","#");
			$('#'+chooseCityCodeArray[i]).removeAttr("style");
			$('#'+chooseCityCodeArray[i]).attr('onclick',"chooseCity(this);return false;");
		}
	}
	// 2，清空画面隐藏值早先关联的城市code与name
	$("#icdcCityId").val("");
	$('#icdcCityName').val("");
	// 3.清空全部被选中城市code与name
	$("#businessCity").val("");
	$("#chooseCityName").val("");
	$(".searchbox-text").val("");
	// 4.重新生成选中城市code的数组
	chooseCityCodeArray = new Array();
	// 5.清空画面隐藏值被选中城市的code
	$("#chooseCity").val("");
}

function initCityTabs(){
	$('#tt').tabs("close","newTab");
	$('#tt').tabs({    
	    border:false,    
	    onSelect:function(title){
	    	if (title != "newTab") {
	    		$('#' + title).empty();
	    		if ($('#tt').tabs('exists', "newTab")) {
	    			$('#tt').tabs('close', "newTab");
	    		}
		    	var array = title.split("");
		    	var arrayParam = title.toLowerCase().split("");
		    	ddpAjaxCall({
		    		url : "showBussinessCity",
		    		data : arrayParam,
		    		async : false,
		    		successFn : function(data) {
		    			if (data.code == "000000") {
		    				$('#' + title).empty();
		    				var list = data.responseEntity.list;
		    				newCityTab(title, array, list);
		    			} else {
		    				msgShow(systemWarnLabel, data.message, 'warning');
		    			}
		    		}
		    	});
	    	}
	    }    
	}); 
	
}

function initCitySearchbox(){
	$(".searchbox-text").attr("id","searchbox-text");
	$(".searchbox-text").attr('style','color:blue;width:298px;height:25px;vertical-align:middle;line-height:25px;');
    $(".searchbox-button").parent().remove();
	$(".searchbox-text").val($("#chooseCityName").val());
	
	$('#chooseCityName').searchbox('setValue', '');
    $('#chooseCityName').searchbox({
    	searcher:function(){ 
    		var citySearch = $(".searchbox-text").val();
    		if ($.trim(citySearch) == "") {
    			return;
    		} else {
    			$('#newTab').empty();
    	    	ddpAjaxCall({
    	    		url : "showBussinessCityByName",
    	    		data : citySearch,
    	    		async : false,
    	    		successFn : function(data) {
    	    			if (data.code == "000000") {
    	    				var list = data.responseEntity.list;
    	    				newCityTabByCityName(list);
    	    			} else {
    	    				msgShow(systemWarnLabel, data.message, 'warning');
    	    			}
    	    		}
    	    	});
    		} 
    	}
    }); 
}
function newCityTabByCityName(list) {
	
	var yktCode = $("#icdcCode").val();
	var m = 0;
	var html = "";
	html += '<div style="padding:20px;"><table>';
	for (var j = 0; j < list.length; j++) {
		if (m % 5 == 0) {
			html += '<tr>';
		}
		// 未被占用
		if (("0" == list[j].states || (yktCode !='' && "1" == list[j].states && $.inArray(list[j].cityCode, oldchooseCityCodeArray) > -1 )) 
				&& $.inArray(list[j].cityCode, chooseCityCodeArray) == -1) {
			html += '<td style="width:80px;height:30px"><a href="#" onclick="chooseCity(this);return false;" id="'
					+ list[j].cityCode + '">';
			html += list[j].cityName;
			html += '</a></td>';
		}  else if (yktCode != '' && list[j].yktCode == yktCode) {
			html += '<td style="width:80px;height:30px;"><a href="#" style="color:blue;" onclick="removeCity(' + list[j].cityCode + ');return false;" id="' + list[j].cityCode + '">';
			html += list[j].cityName;
			html += '</a></td>';
		} else if ("1" == list[j].states) {
			html += '<td style="width:80px;height:30px;"><span style="color:#C0C0C0;">';
			html += list[j].cityName;
			html += '</span></td>';
		} else {
			html += '<td style="width:80px;height:30px;"><a href="#" style="color:blue;" onclick="removeCity(' + list[j].cityCode + ');return false;" id="' + list[j].cityCode + '">';
			html += list[j].cityName;
			html += '</a></td>';
		}
		if (m % 5 == 4) {
			html += '</tr>';
		}
		m++;
	}
	if (m < 4 && m != 0) {
		for (var z = 0; z < 5 - m; z++) {
			html += '<td style="width:80px;height:30px"></td>';
		}
		html += '</tr>';
	}
	html += '</table></div>';
	if ($('#tt').tabs('exists', "newTab")) {
		$('#tt').tabs('select', "newTab");
		var tab = $('#tt').tabs('getSelected');
		$('#tt').tabs('update', {
			tab : tab,
			options : {
				content : html
			}
		});
	} else {
		$("#tt").tabs('add', {
			title : "newTab",
			selected : true,
			content : html,
			closable : true
		});
	}
}
function newCityTab(title,array,list){

	// 取编辑通卡公司时的通卡公司的code
	var yktCode = $("#icdcCode").val();
	for (var i=0;i<array.length;i++) {
		var pa = array[i];
		var html ="";
		html += '<div style="float:left;" id="'+pa+'DIV">';
		html += '<div style="float:left;width:30px;padding-top:7px;">'+pa+'</div>';
		html += '<div style="float:left;width:350px;"><table id="'+pa+'"></table></div>';
		html += '</div>';
		$('#'+title).append(html);
		var html ="";
		var m=0;
		for (var j=0;j<list.length;j++) {
			if (pa == list[j].sort) {
				if (m%5==0) {
					html += '<tr style="height:30px">';
				}
				// 未被占用
				if (("0" == list[j].states || (yktCode !='' && $.inArray(list[j].cityCode, oldchooseCityCodeArray) > -1 && "1" == list[j].states)) 
						&& $.inArray(list[j].cityCode, chooseCityCodeArray) == -1) {
					html += '<td style="width:80px;height:15px"><a href="#" onclick="chooseCity(this);return false;" id="'+list[j].cityCode+'">';
					html += list[j].cityName;
					html += '</a></td>';
				} else  if (yktCode != '' && list[j].yktCode == yktCode) {
					html += '<td style="width:80px;height:15px;"><a href="#" style="color:blue;" onclick="removeCity(' + list[j].cityCode + ');return false;" id="'+list[j].cityCode+'">';
					html += list[j].cityName;
					html += '</a></td>';
				} else if ("1" == list[j].states){
					html += '<td style="width:80px;height:15px;"><span style="color:#C0C0C0;">';
					html += list[j].cityName;
					html += '</span></td>';
				} else {
					html += '<td style="width:80px;height:15px;"><a href="#" style="color:blue;" onclick="removeCity(' + list[j].cityCode + ');return false;" id="'+list[j].cityCode+'">';
					html += list[j].cityName;
					html += '</a></td>';
				}
				if (m%5==4) {
					html += '</tr>';
				}
				m++;
			}
		}
		if (m<4 && m!=0) {
			for (var z=0;z<5-m;z++){
				html += '<td style="width:80px;height:15px"></td>';
			}
			html += '</tr>';
		}
		$('#'+pa).append(html);
		if (m==0) {
			$('#'+pa+'DIV').remove();
		}
	}
}

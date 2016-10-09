
/*************************************************  数据导出 start *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "ProductYktLimitBatchInfoForExport",
	"toUrl"			: "exportYktLimitBatchInfo",
	"parDiaHeight"  : "310"
}; 
var filterConStr = [
		{'name':'yktName', 	        'value': "escapePeculiar($.trim($('#yktNameQuery').val()))"			        },
		{'name':'applyDateStart', 	'value': "escapePeculiar($.trim($('#applyDateStartQuery').val()))"			},
		{'name':'applyDateEnd', 	'value': "escapePeculiar($.trim($('#applyDateEndQuery').val()))"			},
		{'name':'auditDateStart', 	'value': "escapePeculiar($.trim($('#auditDateStartQuery').val()))"			},
		{'name':'auditDateEnd', 	'value': "escapePeculiar($.trim($('#auditDateEndQuery').val()))"			},
		{'name':'yktAddLimitStart', 'value': "escapePeculiar($.trim($('#yktAddLimitStartQuery').val()))"	    },
		{'name':'yktAddLimitEnd', 	'value': "escapePeculiar($.trim($('#yktAddLimitEndQuery').val()))"			},
		{'name':'applyAmtStart', 	'value': "escapePeculiar($.trim($('#applyAmtStartQuery').val()))"			},
		{'name':'applyAmtEnd', 	    'value': "escapePeculiar($.trim($('#applyAmtEndQuery').val()))"			    },
		{'name':'applyUserName', 	'value': "escapePeculiar($.trim($('#applyUserNameQuery').val()))"			},
		{'name':'auditUserName', 	'value': "escapePeculiar($.trim($('#auditUserNameQuery').val()))"			},
		{'name':'auditState',	    'value': "escapePeculiar($('#auditStateQuery').combobox('getValue'))"	    },
		
		{'name':'financialPayAmtStart', 'value': "escapePeculiar($.trim($('#financialPayAmtStartQuery').val()))"	    },
		{'name':'financialPayAmtEnd', 	'value': "escapePeculiar($.trim($('#financialPayAmtEndQuery').val()))"			},
		{'name':'checkUserName', 	'value': "escapePeculiar($.trim($('#checkUserNameQuery').val()))"			},
		{'name':'checkDateStart', 	'value': "escapePeculiar($.trim($('#checkDateStartQuery').val()))"			},
		{'name':'checkDateEnd', 	'value': "escapePeculiar($.trim($('#checkDateEndQuery').val()))"			},
		{'name':'checkState',	    'value': "escapePeculiar($('#checkStateQuery').combobox('getValue'))"	    }
];
/*************************************************  数据导出 end *****************************************************************/

$(function () {
    initMainComponent();
    initCheckNumber();
    expExcBySelColTools.initSelectExportDia(expConfInfo);
    parent.closeGlobalWaitingDialog();
});

function initCheckNumber(){
	$('#yktAddLimitStartQuery').focusout(function(){
		if(isNaN($('#yktAddLimitStartQuery').val())) {
			$('#yktAddLimitStartQuery').val('');
		}
	});
	
	$('#yktAddLimitEndQuery').focusout(function(){
		if(isNaN($('#yktAddLimitEndQuery').val())) {
			$('#yktAddLimitEndQuery').val('');
		}
	});
	$('#applyAmtStartQuery').focusout(function(){
		if(isNaN($('#applyAmtStartQuery').val())) {
			$('#applyAmtStartQuery').val('');
		}
	});
	
	$('#applyAmtEndQuery').focusout(function(){
		if(isNaN($('#applyAmtEndQuery').val())) {
			$('#applyAmtEndQuery').val('');
		}
	});
	$('#financialPayAmtStartQuery').focusout(function(){
		if(isNaN($('#financialPayAmtStartQuery').val())) {
			$('#financialPayAmtStartQuery').val('');
		}
	});
	
	$('#financialPayAmtEndQuery').focusout(function(){
		if(isNaN($('#financialPayAmtEndQuery').val())) {
			$('#financialPayAmtEndQuery').val('');
		}
	});
}
function initMainComponent() {
	
	// 额度批次申请单信息对话框
	$('#limitBatchDialog').show().dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false
	});
	
    autoResize({
        dataGrid: {
            selector: '#icdcLimitBatchInfoTbl',
            offsetHeight: 90,
            offsetWidth: 0
        },
        callback: initYktLimitBatchInfoTbl,
        toolbar: [true, 'top']
    });

	// 申请额度
	$("#applyAmtLimit").bind('keyup', function() {
		clearNoNum($(this),8);
	});
	$("#applyAmtLimit").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#applyAmtLimit").bind('blur', function() {
		clearNoNumOnBlur($(this),8);
	});
	
	// 财务打款额度
	$("#financialPayAmt").bind('keyup', function() {
		clearNoNum($(this),8);
	});
	$("#financialPayAmt").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#financialPayAmt").bind('blur', function() {
		clearNoNumOnBlur($(this),8);
	});
	
	// 通卡新加额度
	$("#newYktAddLimit").bind('keyup', function() {
		clearNoNum($(this),8);
	});
	$("#newYktAddLimit").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#newYktAddLimit").bind('blur', function() {
		clearNoNumOnBlur($(this),8);
	});
	
	// 打款手续费
	$("#financialPayFee").bind('keyup', function() {
		clearNoNum($(this),6);
	});
	$("#financialPayFee").bind('keydown', function() {
		checkDecimal($(this),1,9,0,2);
	});
	$("#financialPayFee").bind('blur', function() {
		clearNoNumOnBlur($(this),6);
	});
}

//-------------------------------------金额校验 start-------------------------------------//
function clearNoNum(obj, bit) {	
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= (bit+1)  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,bit)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > (bit+2)) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > (bit+3)) {
			text = "";
		}
		obj.val(text);
	}
}
function clearNoNumOnBlur(obj, bit) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= (bit+1)  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,bit)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > (bit+2)) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > (bit+3)) {
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


//-------------------------------------金额校验 end-------------------------------------//

var dateformatterYYYYMMdd = 'yyyy-MM-dd';
function cellDateFormatterYYYYMMdd(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return formatDate(cellval, dateformatterYYYYMMdd);
	} else {
		return '';
	}
}
function cellDateFormatterYUAN(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return (cellval/100).toFixed(2);
	} else {
		return '';
	}
}
var myjqgrid_server_opts = {
		jsonReader: {
		      root: "records",
		      page: "pageNo",
		      total: "totalPages",
		      records: "rows",
		      repeatitems: false
		},
		rowNum : 20,
		rowList : [20,50,100],
		viewrecords : true,
		gridview : false,
		pagerpos :'left',
		onSelectRow : function(rowid, status) {
			if (status == true) {
				var tableId = $(this).attr('id');
				var headerCheckbox = 'cb_' + tableId;
				var selarrrow = $(this).getGridParam('selarrrow');
				var allData = $(this).jqGrid('getDataIDs');
				if (selarrrow != null && selarrrow.length == allData.length) {
					$('#' + headerCheckbox).attr('checked', 'checked');
				}
			}
		},
        afterInsertRow : function(rowid, rowdata, rowelem) {
        	var auditState= rowdata.auditState;
        	var checkState= rowdata.checkState;
        	if (auditState == '0') {   		
        		$('#icdcLimitBatchInfoTbl').jqGrid('setCell',rowid,'yktName','',{'color':'red'},'');
        		$('#icdcLimitBatchInfoTbl').jqGrid('setCell',rowid,'auditStateView','',{'color':'red'},'');
        	}
        	if (auditState == '1' && (checkState =='0'|| checkState =='1')) {
        		$('#icdcLimitBatchInfoTbl').jqGrid('setCell',rowid,'yktName','',{'color':'red'},'');
        		$('#icdcLimitBatchInfoTbl').jqGrid('setCell',rowid,'checkStateView','',{'color':'red'},'');
        	}
        }
	};

//**************************************************	额度购买批次申请单信息列表		**********************************************//
function initYktLimitBatchInfoTbl(size) {
    var option = {
        datatype: function (pdata) {
        	limitBatchQuery();
        },
        colNames: ['ID','auditState','checkState', 'yktCode','通卡公司名称', '批次','申请额度（元）', '打款额度（元）','打款手续费（元）','通卡增加额度（元）',
                   '审核状态', '复核状态','申请人', '申请日期', '审核人', '审核日期', '打款日期', '复核人', '复核日期','通卡加款日期'],
        colModel: [
            {name: 'id', hidden: true, frozen:true},
            {name: 'auditState', hidden: true, frozen:true},
            {name: 'checkState', hidden: true, frozen:true},
            {name: 'yktCode', hidden: true, frozen:true},
            {name: 'yktName', index: 'yktName', width: 200, align: 'left', sortable: false, formatter: htmlFormatter, frozen:true},
            {name: 'batchCode', index: 'batchCode', width: 60, align: 'center', sortable: false, frozen:true},
            {name: 'applyAmtLimit', index: 'applyAmtLimit', width: 120, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'financialPayAmt', index: 'financialPayAmt', width: 120, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'financialPayFee', index: 'financialPayFee', width: 120, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'yktAddLimit', index: 'yktAddLimit', width: 140, align: 'right', sortable: false, formatter: cellDateFormatterYUAN},
            {name: 'auditStateView', index: 'auditStateView', width: 80, align: 'center', sortable: false},
            {name: 'checkStateView', index: 'checkStateView', width: 80, align: 'center', sortable: false},
            {name: 'applyUserName', index: 'applyUserName', width: 90, align: 'center', sortable: false},
            {name: 'applyDate', index: 'applyDate', width: 90, align: 'center', sortable: false, formatter: cellDateFormatterYYYYMMdd},
            {name: 'auditUserName', index: 'auditUserName', width: 90, align: 'center', sortable: false},
            {name: 'auditDate', index: 'auditDate', width: 90, align: 'center', sortable: false, formatter: cellDateFormatterYYYYMMdd},
            {name: 'financialPayDate', index: 'financialPayDate', width: 90, align: 'center', sortable: false, formatter: cellDateFormatterYYYYMMdd},
            {name: 'checkUserName', index: 'checkUserName', width: 90, align: 'center', sortable: false},
            {name: 'checkDate', index: 'checkDate', width: 90, align: 'center', sortable: false, formatter: cellDateFormatterYYYYMMdd},
            {name: 'yktAddLimitDate', index: 'yktAddLimitDate', width: 90, align: 'center', sortable: false, formatter: cellDateFormatterYYYYMMdd}
        ],
        height: size.height - 90,
        width: size.width,
        multiselect: false,
        //forceFit:true,
		autowidth : true,
        shrinkToFit : false,
        pager: '#icdcLimitBatchInfoTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, myjqgrid_server_opts);
    $('#icdcLimitBatchInfoTbl').jqGrid(option);
    $("#icdcLimitBatchInfoTbl").jqGrid('setFrozenColumns');
    $("#t_icdcLimitBatchInfoTbl").append($('#icdcLimitBatchInfoTblToolbar'));
}


//**************************************************	分页查询额度购买批次申请单信息列表		**********************************************//
function limitBatchQuery(defaultPageNo) {
    ddpAjaxCall({
        url: "findYktLimitBatchInfoByPage",
        data: {
        	yktName: escapePeculiar($.trim($('#yktNameQuery').val())),
        	applyDateStart : $('#applyDateStartQuery').val(),
        	applyDateEnd : $('#applyDateEndQuery').val(),
        	auditDateStart : $('#auditDateStartQuery').val(),
        	auditDateEnd : $('#auditDateEndQuery').val(),
        	financialPayAmtStart : $('#financialPayAmtStartQuery').val(),
        	financialPayAmtEnd : $('#financialPayAmtEndQuery').val(),
        	applyAmtStart : $('#applyAmtStartQuery').val(),
        	applyAmtEnd : $('#applyAmtEndQuery').val(),
        	applyUserName : escapePeculiar($.trim($('#applyUserNameQuery').val())),
        	auditUserName : escapePeculiar($.trim($('#auditUserNameQuery').val())),
        	auditState : $('#auditStateQuery').combobox('getValue'),
        	yktAddLimitStart : $('#yktAddLimitStartQuery').val(),
        	yktAddLimitEnd : $('#yktAddLimitEndQuery').val(),
        	checkUserName : escapePeculiar($.trim($('#checkUserNameQuery').val())),
        	checkDateStart : $('#checkDateStartQuery').val(),
        	checkDateEnd : $('#checkDateEndQuery').val(),
        	checkState : $('#checkStateQuery').combobox('getValue'),
            page: getJqgridPage('icdcLimitBatchInfoTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcLimitBatchInfoTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}


//**************************************************	申请购买额度		**********************************************//
function applyBuyLimit(){
	
	destroyValidate('limitBatchDialog');
	
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
	$('#applyAmtLimit').val("");
	$('#applyAmtLimit').removeAttr('disabled');
	$('#applyUserName').val($("#userNameHidden").val());
    var now = new Date();
    var time =now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
	$('#applyDate').val(time);
	$('#applyDate').removeAttr('disabled');
	$('#applyExplaination').val("");
	$('#applyExplaination').removeAttr('disabled');
	$('#yktAddLimit').val("");
	$('#yktAddLimit').attr('disabled','true');
	$('#auditUserName').val("");
	$('#auditUserName').attr('disabled','true');
	$('#auditDate').val("");
	$('#auditDate').attr('disabled','true');
	$('#auditExplaination').val("");
	$('#auditExplaination').attr('disabled','true');
	$('#financialPayDate').val("");
	$('#financialPayDate').attr('disabled','true');
	$('#financialPayFee').val("");
	$('#financialPayFee').attr('disabled','true');
	$('#paymentChannel').val("");
	$('#paymentChannel').attr('disabled','true');
	
	$('#applyButton').show();
	$('#passApplyButton').hide();
	$('#refuseApplyButton').hide();
	$('#saveButton').hide();
	$('#checkButton').hide();
	
	$('#auditLimitBatchTable').hide();
	$('#checkLimitBatchTable').hide();
	showDialog('limitBatchDialog');
}
function validateSaveApply(){
    if ($('#yktName').combobox('getText') == '--请选择--') {
    	msgShow(systemWarnLabel, '请选择通卡公司！', 'warning');
    	return false;
    }
    if ($('#applyAmtLimit').val() == '') {
    	msgShow(systemWarnLabel, '请填写申请额度！', 'warning');
    	return false;
    }
    if ($('#applyDate').val() == '') {
    	msgShow(systemWarnLabel, '请填写申请日期！', 'warning');
    	return false;
    }
	return true;
}
function applyBuyLimitClick(){
	if(validateSaveApply()) {
		$.messager.confirm(systemConfirmLabel, "确定要申请购买额度吗？", function(r) {
			if (r) {
				var productYktLimitBatchInfoBean = {
						yktCode:$('#yktName').combobox('getValue'),
						yktName:$('#yktName').combobox('getText'),
						applyAmtLimit:$('#applyAmtLimit').val()*100,
						applyUser:$('#userIdHidden').val(),
						applyUserName:$('#userNameHidden').val(),
						applyDate:$("#applyDate").val(),
						applyExplaination:$("#applyExplaination").val(),
						auditState:'0'
				};
			    ddpAjaxCall({
			        url: "applyAddYktLimitBatchInfo",
			        data: productYktLimitBatchInfoBean,
			        successFn: function (data) {
			        	if(data.code == "000000") {
			        		hideDialog('limitBatchDialog');
			        		$('#batchCodeHidden').val('');
			        		limitBatchQuery(1);
			        	} else {
			        		msgShow(systemWarnLabel, data.message, 'warning');
			        	}
			        }
			    });
			}
		});
	}
}
//**************************************************	删除购买额度申请单		**********************************************//
function deleteLimitbatch(){
	
	var selrow = $("#icdcLimitBatchInfoTbl").getGridParam('selrow');
	if (!selrow) {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var rowData = $('#icdcLimitBatchInfoTbl').getRowData(selrow);
	var auditState = rowData.auditState;
	if (auditState != '0') {
		msgShow(systemWarnLabel, '只能删除未审核的申请单！', 'warning');
		return;
	}
	
	$.messager.confirm(systemConfirmLabel, "确定要删除额度购买申请单吗？", function(r) {
		if (r) {
		    ddpAjaxCall({
		        url: "deleteYktLimitBatchInfo",
		        data: rowData.id,
		        successFn: function (data) {
		        	if(data.code == "000000") {
		        		limitBatchQuery(1);
		        	} else {
		        		msgShow(systemWarnLabel, data.message, 'warning');
		        	}
		        }
		    });
		}
	});
	
}
//**************************************************	编辑额度购买批次申请单		**********************************************//
function modifyLimitBatch(){
	
	destroyValidate('limitBatchDialog');

	var selrow = $("#icdcLimitBatchInfoTbl").getGridParam('selrow');
	if (!selrow) {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var rowData = $('#icdcLimitBatchInfoTbl').getRowData(selrow);
	var auditState = rowData.auditState;
	if (auditState != '0') {
		msgShow(systemWarnLabel, '只能编辑未审核的申请单！', 'warning');
		return;
	}
	if(rowData != null) {
		ddpAjaxCall({
			url : "findYktLimitBatchInfoById",
			data : rowData.id,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var limitBatchBean = data.responseEntity;
				    $('#yktName').combobox('clear');
				    $('#yktName').combobox('setValue', limitBatchBean.yktName);
				    $('#yktName').combobox('reload', 'findAllYktNames');
				    $('#yktName').combobox('disable');
					$('#applyAmtLimit').val((limitBatchBean.applyAmtLimit/100).toFixed(2));
					$('#applyAmtLimit').removeAttr('disabled');
					$('#applyUserName').val(limitBatchBean.applyUserName);
					$('#applyDate').val(formatDate(limitBatchBean.applyDate, dateformatterYYYYMMdd));
					$('#applyDate').removeAttr('disabled');
					$('#applyExplaination').val(limitBatchBean.applyExplaination);
					$('#applyExplaination').removeAttr('disabled');
					$('#yktAddLimit').val('');
					$('#yktAddLimit').attr('disabled','true');
					$('#auditUserName').val('');
					$('#yktAddLimit').attr('disabled','true');
					$('#auditDate').val('');
					$('#auditDate').attr('disabled','true');
					$('#auditExplaination').val('');
					$('#auditExplaination').attr('disabled','true');
					$('#financialPayDate').val('');
					$('#financialPayDate').attr('disabled','true');
					$('#financialPayFee').val('');
					$('#financialPayFee').attr('disabled','true');
					$('#paymentChannel').val("");
					$('#paymentChannel').attr('disabled','true');
					
					$('#applyButton').hide();
					$('#passApplyButton').hide();
					$('#refuseApplyButton').hide();
					$('#saveButton').show();
					$('#checkButton').hide();
					
					$('#auditLimitBatchTable').hide();
					$('#checkLimitBatchTable').hide();
					showDialog('limitBatchDialog');
					$('#idHidden').val(rowData.id);
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}
function validateSaveUpdate(){
    if ($('#applyAmtLimit').val() == '') {
    	msgShow(systemWarnLabel, '请填写申请额度！', 'warning');
    	return false;
    }
    if ($('#applyDate').val() == '') {
    	msgShow(systemWarnLabel, '请填写申请日期！', 'warning');
    	return false;
    }
	return true;
}
function saveLimitBatchClick(){
	
	if (validateSaveUpdate()) {
		$.messager.confirm(systemConfirmLabel, "确定要保存本次修改吗？", function(r) {
			if (r) {
				var productYktLimitBatchInfoBean = {
						id:$('#idHidden').val(),
						applyAmtLimit:$('#applyAmtLimit').val()*100,
						applyDate:$("#applyDate").val(),
						applyExplaination:$("#applyExplaination").val()
				};
			    ddpAjaxCall({
			        url: "saveYktLimitBatchInfo",
			        data: productYktLimitBatchInfoBean,
			        successFn: function (data) {
			        	if(data.code == "000000") {
			        		$('#idHidden').val('');
			        		hideDialog('limitBatchDialog');
			        		limitBatchQuery(1);
			        	} else {
			        		msgShow(systemWarnLabel, data.message, 'warning');
			        	}
			        }
			    });
			}
		});
	}
	
}

//**************************************************	审核额度购买批次申请单		**********************************************//
function auditLimitbatch(){
	
	destroyValidate('limitBatchDialog');

	var selrow = $("#icdcLimitBatchInfoTbl").getGridParam('selrow');
	if (!selrow) {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var rowData = $('#icdcLimitBatchInfoTbl').getRowData(selrow);
	var auditState = rowData.auditState;
	if (auditState != '0') {
		msgShow(systemWarnLabel, '只能审核未审核的申请单！', 'warning');
		return;
	}
	if(rowData != null) {
		ddpAjaxCall({
			url : "findYktLimitBatchInfoById",
			data : rowData.id,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var limitBatchBean = data.responseEntity;
				    $('#yktName').combobox('clear');
				    $('#yktName').combobox('setValue', limitBatchBean.yktName);
				    $('#yktName').combobox('reload', 'findAllYktNames');
				    $('#yktName').combobox('disable');
					$('#applyAmtLimit').val((limitBatchBean.applyAmtLimit/100).toFixed(2));
					$('#applyAmtLimit').attr('disabled','true');
					$('#applyUserName').val(limitBatchBean.applyUserName);
					$('#applyAmtLimit').attr('disabled','true');
					$('#applyDate').val(formatDate(limitBatchBean.applyDate, dateformatterYYYYMMdd));
					$('#applyDate').attr('disabled','true');
					$('#applyExplaination').val(limitBatchBean.applyExplaination);
					$('#applyExplaination').attr('disabled','true');
					$('#financialPayAmt').val('');
					$('#financialPayAmt').removeAttr('disabled');
					$('#auditUserName').val($("#userNameHidden").val());
				    var now = new Date();
				    var time =now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
					$('#auditDate').val(time);
					$('#auditDate').removeAttr('disabled');					
					$('#auditExplaination').val('');
					$('#auditExplaination').removeAttr('disabled');
					$('#financialPayDate').val('');
					$('#financialPayDate').removeAttr('disabled');
					$('#financialPayFee').val('');
					$('#financialPayFee').removeAttr('disabled');
					$('#paymentChannel').val("");
					$('#paymentChannel').removeAttr('disabled');
					$('#applyButton').hide();
					$('#passApplyButton').show();
					$('#refuseApplyButton').show();
					$('#saveButton').hide();
					$('#checkButton').hide();
					
					$('#auditLimitBatchTable').show();
					$('#checkLimitBatchTable').hide();
					showDialog('limitBatchDialog');
					$('#idHidden').val(rowData.id);
					$('#yktCodeHidden').val(rowData.yktCode);
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

function validateSaveAudit(flg){
	
    if (flg == '1') {
        if ($('#financialPayAmt').val() == '') {
        	msgShow(systemWarnLabel, '请填写财务加款额度！', 'warning');
        	return false;
        }
        if ($('#auditDate').val() == '') {
        	msgShow(systemWarnLabel, '请填写审核日期！', 'warning');
        	return false;
        }
        if ($('#financialPayDate').val() == '') {
        	msgShow(systemWarnLabel, '请填写打款日期！', 'warning');
        	return false;
        }
    } else if (flg == '2'){
        if ($('#auditDate').val() == '') {
        	msgShow(systemWarnLabel, '请填写审核日期！', 'warning');
        	return false;
        }
    }
	return true;
}
function passApplyClick(){
	
	if (validateSaveAudit('1')) {
		$.messager.confirm(systemConfirmLabel, "确定审核通过此次申请吗？", function(r) {
			if (r) {
				var productYktLimitBatchInfoBean = {
						id:$('#idHidden').val(),
						yktCode:$('#yktCodeHidden').val(),
						auditUser:$('#userIdHidden').val(),
						auditUserName:$('#userNameHidden').val(),
						auditDate:$("#auditDate").val(),
						auditExplaination:$("#auditExplaination").val(),
						auditState:'1',
						paymentChannel:$("#paymentChannel").val(),
						financialPayAmt:$('#financialPayAmt').val()*100,
						financialPayDate:$('#financialPayDate').val(),
						financialPayFee:$('#financialPayFee').val()*100
				};
			    ddpAjaxCall({
			        url: "aduitYktLimitBatchInfo",
			        data: productYktLimitBatchInfoBean,
			        successFn: function (data) {
			        	if(data.code == "000000") {
			        		$('#idHidden').val('');
			        		$('#yktCodeHidden').val('');
			        		hideDialog('limitBatchDialog');
			        		limitBatchQuery(1);
			        	} else {
			        		msgShow(systemWarnLabel, data.message, 'warning');
			        	}
			        }
			    });
			}
		});
	}
	
}
function refuseApplyClick(){
	
	if (validateSaveAudit('2')) {
		$.messager.confirm(systemConfirmLabel, "确定审核拒绝此次申请吗？", function(r) {
			if (r) {
				var productYktLimitBatchInfoBean = {
						id:$('#idHidden').val(),
						yktCode:$('#yktCodeHidden').val(),
						auditUser:$('#userIdHidden').val(),
						auditUserName:$('#userNameHidden').val(),
						auditDate:$("#auditDate").val(),
						auditExplaination:$("#auditExplaination").val(),
						auditState:'2',
						paymentChannel:$("#paymentChannel").val(),
						yktAddLimit:$('#yktAddLimit').val()*100,
						financialPayDate:$('#financialPayDate').val(),
						financialPayFee:$('#financialPayFee').val()*100
				};
			    ddpAjaxCall({
			        url: "aduitYktLimitBatchInfo",
			        data: productYktLimitBatchInfoBean,
			        successFn: function (data) {
			        	if(data.code == "000000") {
			        		$('#idHidden').val('');
			        		$('#yktCodeHidden').val('');
			        		hideDialog('limitBatchDialog');
			        		limitBatchQuery(1);
			        	} else {
			        		msgShow(systemWarnLabel, data.message, 'warning');
			        	}
			        }
			    });
			}
		});
	}
	
}


//**************************************************	复核额度购买批次申请单		**********************************************//
function checkLimitbatch(){
	
	destroyValidate('limitBatchDialog');

	var selrow = $("#icdcLimitBatchInfoTbl").getGridParam('selrow');
	if (!selrow) {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var rowData = $('#icdcLimitBatchInfoTbl').getRowData(selrow);
	var auditState = rowData.auditState;
	var checkState = rowData.checkState;
	if (auditState != '1') {
		msgShow(systemWarnLabel, '只能复核审核通过的申请单！', 'warning');
		return;
	}
	if (checkState == '2') {
		msgShow(systemWarnLabel, '该申请单已经复核完了！', 'warning');
		return;
	}
	if(rowData != null) {
		ddpAjaxCall({
			url : "findYktLimitBatchInfoById",
			data : rowData.id,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var limitBatchBean = data.responseEntity;
				    $('#yktName').combobox('clear');
				    $('#yktName').combobox('setValue', limitBatchBean.yktName);
				    $('#yktName').combobox('reload', 'findAllYktNames');
				    $('#yktName').combobox('disable');
					$('#applyAmtLimit').val((limitBatchBean.applyAmtLimit/100).toFixed(2));
					$('#applyAmtLimit').attr('disabled','true');
					$('#applyUserName').val(limitBatchBean.applyUserName);
					$('#applyAmtLimit').attr('disabled','true');
					$('#applyDate').val(formatDate(limitBatchBean.applyDate, dateformatterYYYYMMdd));
					$('#applyDate').attr('disabled','true');
					$('#applyExplaination').val(limitBatchBean.applyExplaination);
					$('#applyExplaination').attr('disabled','true');
					$('#financialPayAmt').val((limitBatchBean.financialPayAmt/100).toFixed(2));
					$('#financialPayAmt').attr('disabled','true');
					$('#financialPayFee').val((limitBatchBean.financialPayFee/100).toFixed(2));
					$('#financialPayFee').attr('disabled','true');
					$('#financialPayDate').val(formatDate(limitBatchBean.financialPayDate, dateformatterYYYYMMdd));
					$('#financialPayDate').attr('disabled','true');
					$('#paymentChannel').val(limitBatchBean.paymentChannel);
					$('#paymentChannel').attr('disabled','true');
					$('#auditUserName').val(limitBatchBean.auditUserName);
					$('#auditDate').val(formatDate(limitBatchBean.auditDate, dateformatterYYYYMMdd));
					$('#auditDate').attr('disabled','true');					
					$('#auditExplaination').val(limitBatchBean.auditExplaination);
					$('#auditExplaination').attr('disabled','true');

					$('#oldYktAddLimit').val((limitBatchBean.yktAddLimit/100).toFixed(2));
					$('#newYktAddLimit').val('');
					$('#yktAddLimitDate').val(formatDate(limitBatchBean.yktAddLimitDate, dateformatterYYYYMMdd));
					$('#checkExplaination').val(limitBatchBean.checkExplaination);
				    var now = new Date();
				    var time =now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
				    $('#checkDate').val(time);
					$('#checkUserName').val($("#userNameHidden").val());
					
					$('#applyButton').hide();
					$('#passApplyButton').hide();
					$('#refuseApplyButton').hide();
					$('#saveButton').hide();
					$('#checkButton').show();
					
					$('#auditLimitBatchTable').show();
					$('#checkLimitBatchTable').show();
					showDialog('limitBatchDialog');
					$('#idHidden').val(rowData.id);
					$('#yktCodeHidden').val(rowData.yktCode);
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

function validateSaveCheck(){
	
    if ($('#newYktAddLimit').val() == '') {
    	msgShow(systemWarnLabel, '请填写通卡新加额度！', 'warning');
    	return false;
    }
    if (parseFloat($('#newYktAddLimit').val())+parseFloat($('#oldYktAddLimit').val()) > parseFloat($('#financialPayAmt').val())) {
    	msgShow(systemWarnLabel, '通卡新加额度与通卡已加额度之和不得大于财务打款额度！', 'warning');
    	return false;
    }
    if ($('#yktAddLimitDate').val() == '') {
    	msgShow(systemWarnLabel, '请填写通卡加款日期！', 'warning');
    	return false;
    }
    if ($('#checkDate').val() == '') {
    	msgShow(systemWarnLabel, '请填写复核日期！', 'warning');
    	return false;
    }

	return true;
}
function checkLimitBatchClick(){
	
	if (validateSaveCheck()) {
		
		$.messager.confirm(systemConfirmLabel, "确定复核保存此次信息吗？", function(r) {
			if (r) {
				var productYktLimitBatchInfoBean = {
						id:$('#idHidden').val(),
						yktCode:$('#yktCodeHidden').val(),
						checkUser:$('#userIdHidden').val(),
						checkUserName:$('#userNameHidden').val(),
						checkDate:$("#checkDate").val(),
						checkExplaination:$("#checkExplaination").val(),
						oldYktAddLimit:parseFloat($('#oldYktAddLimit').val())*100,
						newYktAddLimit:parseFloat($('#newYktAddLimit').val())*100,
						yktAddLimitDate:$('#yktAddLimitDate').val()
				};
			    ddpAjaxCall({
			        url: "checkYktLimitBatchInfo",
			        data: productYktLimitBatchInfoBean,
			        successFn: function (data) {
			        	if(data.code == "000000") {
			        		$('#idHidden').val('');
			        		$('#yktCodeHidden').val('');
			        		hideDialog('limitBatchDialog');
			        		limitBatchQuery(1);
			        	} else {
			        		msgShow(systemWarnLabel, data.message, 'warning');
			        	}
			        }
			    });
			}
		});
	}
	
}
// **************************************************	查看购买额度申请单详细信息	**********************************************//
function viewBatchInfo() {
	var selrow = $("#icdcLimitBatchInfoTbl").getGridParam('selrow');
	if (!selrow) {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var rowData = $('#icdcLimitBatchInfoTbl').getRowData(selrow);
	if (rowData != null) {
		ddpAjaxCall({
			url : "findYktLimitBatchInfoById",
			data : rowData.id,
			successFn : function(data) {
				if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
					var limitBatchBean = data.responseEntity;
					$('#view_yktName').html(limitBatchBean.yktName);
					if (limitBatchBean.batchCode == null) {
						$('#view_batchCode').html("");
					} else {
						$('#view_batchCode').html("第&nbsp"+limitBatchBean.batchCode +"&nbsp批");
					}
					$('#view_applyAmtLimit').html((limitBatchBean.applyAmtLimit/100).toFixed(2) +"&nbsp元");
					$('#view_applyUserName').html(limitBatchBean.applyUserName);
					$('#view_applyDate').html(formatDate(limitBatchBean.applyDate, dateformatterYYYYMMdd));
					$('#view_applyExplaination').html(htmlTagFormat(limitBatchBean.applyExplaination));
					
					
					var financialPayAmt=limitBatchBean.financialPayAmt;
					if (limitBatchBean.financialPayAmt == null) {
						$('#view_financialPayAmt').html('');
					} else {
						$('#view_financialPayAmt').html((financialPayAmt/100).toFixed(2) +"&nbsp元");
					}
					var financialPayFee=limitBatchBean.financialPayFee;
					if (limitBatchBean.financialPayFee == null) {
						$('#view_financialPayFee').html('');
					} else {
						$('#view_financialPayFee').html((financialPayFee/100).toFixed(2) +"&nbsp元");
					}
					$('#view_financialPayDate').html(formatDate(limitBatchBean.financialPayDate, dateformatterYYYYMMdd));
					$('#view_paymentChannel').html(htmlTagFormat(limitBatchBean.paymentChannel));
					$('#view_auditUserName').html(limitBatchBean.auditUserName);
					$('#view_auditStateView').html(limitBatchBean.auditStateView);
					$('#view_auditDate').html(formatDate(limitBatchBean.auditDate, dateformatterYYYYMMdd));
					$('#view_auditExplaination').html(htmlTagFormat(limitBatchBean.auditExplaination));
					
					var yktAddLimit=limitBatchBean.yktAddLimit;
					if (limitBatchBean.yktAddLimit == null || limitBatchBean.yktAddLimit == '0') {
						$('#view_yktAddLimit').html('');
					} else {
						$('#view_yktAddLimit').html((yktAddLimit/100).toFixed(2) +"&nbsp元");
					}
					$('#view_yktAddLimitDate').html(formatDate(limitBatchBean.yktAddLimitDate, dateformatterYYYYMMdd));
					$('#view_checkUserName').html(limitBatchBean.checkUserName);
					$('#view_checkStateView').html(limitBatchBean.checkStateView);
					$('#view_checkDate').html(formatDate(limitBatchBean.checkDate, dateformatterYYYYMMdd));
					$('#view_checkExplaination').html(htmlTagFormat(limitBatchBean.checkExplaination));
					
					
					$('#view_createDate').html(formatDate(limitBatchBean.createDate, dateformatterStr));
					$('#view_updateDate').html(formatDate(limitBatchBean.updateDate, dateformatterStr));
					$('#view_createUser').html(limitBatchBean.createUser);
					$('#view_updateUser').html(limitBatchBean.updateUser);
					showDialog('viewLimitBatchDialog');
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}




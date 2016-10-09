var expConfInfo = {
        "btnId"         : "btnSelExcCol",                  
        "typeSelStr"    : "ProductConsumerOrderForExport",
        "toUrl"         : "excelProductConsumeOrder",
        "parDiaHeight"  : "300"
};
var filterConStr = [
    {'name':'orderNum',       'value': "escapePeculiar($.trim($('#orderNum').val()))"},
    {'name':'states',         'value': "$('#queryStates').combobox('getValue')"},
    {'name':'innerStates',    'value': "$('#innerQueryStates').combobox('getValue')"},
    {'name':'orderDateStart', 'value': "escapePeculiar($('#orderDateStart').val())"},
    {'name':'orderDateEnd',   'value': "escapePeculiar($('#orderDateEnd').val())"},
    {'name':'CardNum',        'value': "escapePeculiar($.trim($('#cardNum').val()))"},
    {'name':'merName',        'value': "escapePeculiar($.trim($('#merName').val()))"},
    {'name':'merType',        'value': "$('#merUserType').combobox('getValue')"},
    {'name':'yktCode',        'value': "myEscapePeculiar($.trim($('#yktName').combobox('getValue')))"},
    {'name':'posCode',        'value': "escapePeculiar($('#posCode').val())"},
    {'name':'txnAmtStart',    'value': "escapePeculiar($('#txnAmtStart').val())"},
    {'name':'txnAmtEnd',      'value': "escapePeculiar($('#txnAmtEnd').val())"},
    {'name':'suspiciouStates','value': "$('#suspiciouStates').combobox('getValue')"}
];

function myEscapePeculiar(yktCode){
    if (yktCode === '--请选择--') {
    	yktCode = '';
    }
    return yktCode;
}

function clearIcdcAllText(){
	clearAllText('queryCondition');
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
    initDateQuery();
}

function initDateQuery(){
	$("#orderDateStart").val(formatDate(new Date(),"yyyy-MM-dd"));
	$("#orderDateEnd").val(formatDate(new Date(),"yyyy-MM-dd"));
}
$(function () {
	initDateQuery();
	initMainComponent();
	initMoney();
	
	// 初始化通卡公司选择框
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
    
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {

   autoResize({
       dataGrid: {
           selector: '#icdcAcqTbl',
           offsetHeight: 95,
           offsetWidth: 0
       },
       callback: initIcdcAcqTbl,
       toolbar: [true, 'top']
   });
}

function initIcdcAcqTbl(size) {
    var option = {
        datatype: function (pdata) {
        	findProductConsumeOrder();
        },
        colNames: ['ID','客户名称','客户类型','订单编号', '城市','通卡优惠','POS号','卡号','应收金额(元)','实收金额(元)', '消费前金额(元)', '消费后金额(元)', '订单状态','内部订单状态','上级商户名称','商圈','经营范围','星期','时间段','订单时间'],
        colModel: [
            {name: 'id', hidden: true,frozen:true},
            {name: 'merName', index: 'merName', width: 150, align: 'center', sortable: false,frozen:true},
            {name: 'merTypeView', index: 'merTypeView', width: 120, align: 'center', sortable: false},
            {name: 'orderNum', index: 'orderNum', width: 180, align: 'center', sortable: false},
            {name: 'cityName', index: 'cityName', width: 80, align: 'center', sortable: false},
            {name: 'yktDisCountSign', index: 'yktDisCountSign', width: 90, align: 'center', sortable: false,
            	formatter:function(cellval, el, rowData){if (cellval =='1') {return '有';} else {return '无';}}
            },
//            {name: 'merDiscount', index: 'merDiscount', width: 80, align: 'center', sortable: false,
//            	formatter:function(cellval, el, rowData){if (cellval == 0 || cellval == '' || cellval == '10' || cellval == 10) {return '无';} else {return cellval;}}
//            },
            {name: 'posCode', index: 'posCode', width: 120, align: 'center', sortable: false},
            {name: 'cardNum', index: 'cardNum', width: 150, align: 'center', sortable: false},
            {name: 'originalPrice', index: 'originalPrice', width: 110, align: 'center', sortable: false },
            {name: 'receivedPrice', index: 'receivedPrice', width: 110, align: 'center', sortable: false},
            {name: 'befbal', index: 'befbal', width: 110, align: 'center', sortable: false},
            {name: 'blackAmt', index: 'blackAmt', width: 110, align: 'center', sortable: false},
            {name: 'states', index: 'states', width: 120, align: 'center', sortable: false},
            {name: 'innerStates', index: 'innerStates', width: 120, align: 'center', sortable: false},
            {name: 'merchantbean.merParentName', index: 'merParentName', width: 120, align: 'center', sortable: false},
            {name: 'merchantbean.merDdpInfo.tradeArea', index: 'tradeArea', width: 120, align: 'center', sortable: false},
            {name: 'merchantbean.merBusinessScopeIdView', index: 'merBusinessScopeIdView', width: 120, align: 'center', sortable: false},
            {name: 'weekDay', index: 'weekDay', width: 100, align: 'center', sortable: false},
            {name: 'proOrderDate', index: 'proOrderDate', width: 80, align: 'center', sortable: false,
            	formatter:function(cellval, el, rowData){
            		return formatDate(cellval,'HH')+"-"+formatDate(cellval+ 60*60*1000,'HH');
            	}
            },
            {name: 'proOrderDate', index: 'proOrderDate', width: 150, align: 'center', sortable: false, formatter: cellDateFormatter}
        ],
        height: size.height - 95,
        width: size.width,
        multiselect : false,
		autowidth : true,
        shrinkToFit : false,
        pager: '#icdcAcqTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#icdcAcqTbl').jqGrid(option);
    $("#icdcAcqTbl").jqGrid('setFrozenColumns');
    $("#t_icdcAcqTbl").append($('#icdcAcqTblToolbar'));
}

function findProductConsumeOrder(defaultPageNo) {
	var txnAmtStart = $('#txnAmtStart').val();
    var txnAmtEnd = $('#txnAmtEnd').val();
    if(txnAmtStart) {
    	txnAmtStart = txnAmtStart*100;
    }
    if(txnAmtEnd) {
    	txnAmtEnd = txnAmtEnd*100;
    }
    yktCodeQuery = $('#yktName').combobox('getValue');
    if (yktCodeQuery === '--请选择--') {
    	yktCodeQuery = '';
    }
    var ConsumeOrderQuery ={
  		  	orderNum: escapePeculiar($.trim($('#orderNum').val())),
        	orderDateStart : $('#orderDateStart').val(),
        	orderDateEnd : $('#orderDateEnd').val(),
        	cardNum : escapePeculiar($.trim($('#cardNum').val())),
        	yktCode : yktCodeQuery,
        	merName : escapePeculiar($.trim($('#merName').val())),
        	customerType : $('#merUserType').combobox('getValue'),
        	txnAmtStart : $.trim(txnAmtStart),
        	txnAmtEnd : $.trim(txnAmtEnd),
        	states : $('#queryStates').combobox('getValue'),
        	innerStates : $('#innerQueryStates').combobox('getValue'),
        	suspiciouStates:$('#suspiciouStates').combobox('getValue'),
        	posCode : escapePeculiar($.trim($('#posCode').val())),
            page: getJqgridPage('icdcAcqTbl', defaultPageNo)
        }
    console.log(ConsumeOrderQuery.states);
    var min = ConsumeOrderQuery.txnAmtStart;
	var max = ConsumeOrderQuery.txnAmtEnd;
    compareMoney(ConsumeOrderQuery,min,max);
    ddpAjaxCall({
    	async : true,
        url: "findProductConsumeOrder",
        data: ConsumeOrderQuery,
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#icdcAcqTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}


function viewProductConsumeDetails() {
	var selarrrow = $('#icdcAcqTbl').getGridParam('selrow');
	if (selarrrow) {
		var rowData = $('#icdcAcqTbl').getRowData(selarrrow);
		ddpAjaxCall({
			url : "findProductConsumeOrderByCode",
			 data: rowData.orderNum,
			successFn : function(response) {
				showDialog('viewIcdcConsumeDialog');
				var htmlKeys = ['comments'];
				loadJsonData2ViewPageWithHtmlFormat('viewIcdcConsumeDialog', response, htmlKeys, function(jsonData) {
					$('#view_proOrderDate').html(ddpDateFormatter(jsonData.proOrderDate));
				});
			}
		});
	} else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}

// 导出
function exportVerified() {
	$.fileDownload('excelproductConsumeOrder', {
		data: $('#listForm').serialize(),
		failCallback: function() {
			msgShow(systemWarnLabel, "文件导出出错", 'warning');
		}
	})
}

function initMoney() {
	$("#txnAmtStart").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtStart").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#txnAmtStart").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	
	
	$("#txnAmtEnd").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtEnd").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#txnAmtEnd").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	
}


function compareMoney(query,min,max){
	if(min != "" && max != "" && parseFloat(min)>parseFloat(max)){
		var temp = max;
		max = min;
		min = temp;
		$('#txnAmtStart').val(min/100);
		$('#txnAmtEnd').val(max/100);
		query.txnAmtStart = min;
		query.txnAmtEnd = max;
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


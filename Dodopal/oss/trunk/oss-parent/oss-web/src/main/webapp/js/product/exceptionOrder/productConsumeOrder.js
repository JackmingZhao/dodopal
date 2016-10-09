var expConfInfo = {
        "btnId"            : "btnSelExcCol",                 /*must*/// the id of export btn in ftl 
        "typeSelStr"    : "ProductConsumeOrder",             /*must*/// type of export
        "toUrl"            : "exportPrdExpOrder"             /*must*/// the export url
    };
var filterConStr = [    // filter the result by query condition
    {'name':'orderNum',       'value': "escapePeculiar($.trim($('#orderNum').val()))"},
    {'name':'states',         'value': "$('#queryStates').combobox('getValue')"},
//    {'name':'innerStates',    'value': "$('#innerQueryStates').combobox('getValue')"},
    {'name':'orderDateStart', 'value': "$('#orderDateStart').val()"},
    {'name':'orderDateEnd',   'value': "$('#orderDateEnd').val()"},
    {'name':'cardNum',        'value': "escapePeculiar($.trim($('#cardNum').val()))"},
    {'name':'yktCode',        'value': "myEscapePeculiar($.trim($('#yktName').combobox('getValue')))"},
    {'name':'merName',        'value': "escapePeculiar($.trim($('#merName').val()))"},
//    {'name':'merType',        'value': "$('#merUserType').combobox('getValue')"},
    {'name':'txnAmtStart',    'value': "$('#txnAmtStart').val()"},
    {'name':'txnAmtEnd',      'value': "$('#txnAmtEnd').val()"},
    {'name':'states',         'value': "$('#queryStates').combobox('getValue')"},
    {'name':'posCode',        'value': "escapePeculiar($.trim($('#posCode').val()))"}
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
}

$(function () {
	initMainComponent();
	initMoney();
	// 初始化通卡公司选择框
    $('#yktName').combobox('clear');
    $('#yktName').combobox('setValue', '--请选择--');
    $('#yktName').combobox('reload', 'findAllYktNames');
    $('#yktName').combobox('enable');
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
   autoResize({
       dataGrid: {
           selector: '#icdcAcqTbl',
           offsetHeight: 75,
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
        colNames: ['ID', '商户号', 
                   '客户类型', '客户名称', '订单编号',
                   '城市','折扣','POS号','卡号','应收金额(元)',
                   '实收金额(元)', '消费前金额(元)', '消费后金额(元)', '订单状态','内部状态','订单时间'],
        colModel: [
            {name: 'id', hidden: true, frozen:true},
            {name: 'customerCode', index: 'customerCode',hidden: true, frozen:true},

            {name: 'merTypeView', index: 'merTypeView', width: 120, align: 'center', sortable: false, frozen: true},
            {name: 'merName', index: 'merName', width: 150, align: 'center', sortable: false},
            {name: 'orderNum', index: 'orderNum', width: 180, align: 'center', sortable: false},
            
            {name: 'cityName', index: 'cityName', width: 100, align: 'center', sortable: false},
            {name: 'merDiscount', index: 'merDiscount', width: 80, align: 'center', sortable: false,
            	formatter:function(cellval, el, rowData){if (cellval == 0 || cellval == '' || cellval == '10' || cellval == 10) {return '无';} else {return cellval;}}
            },
            {name: 'posCode', index: 'posCode', width: 150, align: 'center', sortable: false},
            {name: 'cardNum', index: 'cardNum', width: 150, align: 'center', sortable: false},
            {name: 'originalPrice', index: 'originalPrice', width: 100, align: 'center', sortable: false },
            
            {name: 'receivedPrice', index: 'receivedPrice', width: 100, align: 'center', sortable: false},
            {name: 'befbal', index: 'befbal', width: 120, align: 'center', sortable: false},
            {name: 'blackAmt', index: 'blackAmt', width: 120, align: 'center', sortable: false},
            {name: 'states', index: 'states', width: 100, align: 'center', sortable: false},
            {name: 'innerStates', index: 'innerStates', width: 130, align: 'center', sortable: false},
            
            {name: 'proOrderDate', index: 'proOrderDate', width: 150, align: 'center', sortable: false, formatter: cellDateFormatter}
        ],
        height: size.height - 75,
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
//        	customerType : $('#merUserType').combobox('getValue'),
        	txnAmtStart : $.trim(txnAmtStart),
        	txnAmtEnd : $.trim(txnAmtEnd),
        	states : $('#queryStates').combobox('getValue'),
//        	innerStates : $('#innerQueryStates').combobox('getValue'),
        	posCode : escapePeculiar($.trim($('#posCode').val())),
            page: getJqgridPage('icdcAcqTbl', defaultPageNo)
        }
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

function exceptionHandle(){
	var selarrrow = $('#icdcAcqTbl').getGridParam('selrow');
	if (selarrrow) {
		var rowData = $('#icdcAcqTbl').getRowData(selarrrow);
		var html = "订单号："+rowData.orderNum+"<br><br>";
		    html += "订单主状态：<span style='color:red'>"+rowData.states+"</span>；订单内部状态：<span style='color:red'>"+rowData.innerStates+"</span>；<br><br>";
		    html+=$("#radioDiv").html();
			$("#messageDiv").html(html);
			$("#radioDiv").hide();
		$('#exceptionHandleDialog').show().dialog({
			buttons : [{
				text:'确定',
				handler:function(){
					var judge = $("input[name='handleSign']:checked").val();
					if(judge == undefined){
						msgShow("提示", "请选择处理方式", 'warning');
						return false;
					}
					ddpAjaxCall({
						url : "exceptionHandle",
				        data: {
				        	orderNo: rowData.orderNum,
				        	customerNo:rowData.customerCode,
				        	states: judge
				        },
						successFn : function(data) {
				            if (data.code == "000000") {
				            	$('#exceptionHandleDialog').hide().dialog('close');
				            	findProductConsumeOrder();
				            } else {
				            	msgShow(systemWarnLabel, data.message, 'warning');
				            }
				        }	
					});
				}
			},{
				text:'取消',
				handler:function(){
					$('#exceptionHandleDialog').hide().dialog('close');				
				}
			}]
		});
		$("input[name='handleSign']:checked").attr("checked",false);
		showDialog("exceptionHandleDialog");
		} else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}


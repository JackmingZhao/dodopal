

/*************************************************  数据导出 start *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "IcdcPrductInfoForExport",
	"toUrl"			: "exportIcdcPrductInfo",
	"parDiaHeight"  : "200"
}; 
var filterConStr = [
		{'name':'nameQuery', 	     'value': "escapePeculiar($.trim($('#nameQuery').val()))"			    },
		{'name':'cityQuery',	     'value': "escapePeculiar($('#cityQuery').combobox('getValue'))"	        },
		{'name':'supplierQuery',	 'value': "escapePeculiar($('#supplierQuery').combobox('getValue'))"	    },
		{'name':'valueQuery', 	     'value': "escapePeculiar($.trim($('#valueQuery').val()))"			},
		{'name':'saleUporDownQuery', 'value': "escapePeculiar($('#saleUporDownQuery').combobox('getValue'))"    }
];
/*************************************************  数据导出 end *****************************************************************/

$(function () {
    initMainComponent();
    initAddDialog();
    initQeuryPanel();
    expExcBySelColTools.initSelectExportDia(expConfInfo);
    parent.closeGlobalWaitingDialog();
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////主界面初始化
function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#dataTable',
            offsetHeight: 50,
            offsetWidth: 0
        },
        callback: initIcdcPrdDataTbl,
        toolbar: [true, 'top'],
        dialogs: ['icdcPrdAddDialog', 'icdcPrdEditDialog', 'icdcPrdViewDialog']
    });
}
//初始化通卡公司基本信息面板
function initIcdcPrdDataTbl(size) {
    var option = {
        datatype: function (pdata) {
            loadIcdcPrdDataTableData();
        },
        colNames: [
            'ID', '产品编码', '产品名称', '通卡公司', '业务城市', '费率（‰）', '面价（元）', '成本价（元）', '状态'
        ],
        colModel: [
            {name: 'id', hidden: true},
            {name: 'code', index: 'code', width: 100, align: 'left', sortable: false},
            {name: 'name', index: 'name', width: 100, align: 'left', sortable: false,formatter:htmlFormatter},
            {name: 'supplier', index: 'supplier', width: 100, align: 'left', sortable: false,formatter:htmlFormatter},
            {name: 'city', index: 'city', width: 100, align: 'left', sortable: false,formatter:htmlFormatter},
            {name: 'rate', index: 'rate', width: 100, align: 'right', sortable: false},
            {name: 'valuePrice', index: 'valuePrice', width: 100, align: 'right', sortable: false},
            {name: 'costPrice', index: 'costPrice', width: 100, align: 'right', sortable: false},
            {name: 'saleUporDown', index: 'saleUporDown', width: 100, align: 'left', sortable: false}
        ],
        height: size.height - 50,
        width: size.width,
        multiselect: true,
        pager: '#dataTablePagination',
        toolbar: [true, "top"]
        //gridComplete: afterCompleteFunction
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#dataTable').jqGrid(option);
    $("#t_dataTable").append($('#dataTableToolbar'));
}

function initQeuryPanel() {
	$('#saleUporDownQuery').combobox({
		panelHeight: "auto"
	});
    $('#supplierQuery').combobox('reload', 'getIcdcNames');
    initCombobox('#supplierQuery', '#cityQuery');
}

function initAddDialog() {
	$('#statusA').combobox({
		panelHeight: "auto"
	});
    initCombobox('#icdcProA', '#cityA');
}

///////////////////////////////查询按钮
function icdcPrdQuery() {
    loadIcdcPrdDataTableData(1);
}

///////////////////////////////重置按钮
function resetPrdQuery() {
	clearAllText('queryCondition');
	$('#supplierQuery').combobox('setText', '--请选择--');
	$('#cityQuery').combobox('setText', '--无--');
}

///////////////////////////////添加按钮
function addIcdcPrd() {
    clearAllText('icdcPrdAddDialog');
    $('#icdcProA').combobox('clear');
    $('#cityA').combobox('clear');
    $('#icdcProA').combobox('setValue', '--请选择--');
    $('#cityA').combobox('setValue', '--无--');
    $('#commentsA').val('');
    $('#icdcProA').combobox('reload', 'getIcdcNames');
    $('#cityA').combobox({
        editable:false
    });
    showDialog('icdcPrdAddDialog');
}

///////////////////////////////修改按钮
function modifyIcdcPrd() {
    var selarrrow = $("#dataTable").getGridParam('selarrrow');// 多选
    if (selarrrow != null && selarrrow.length > 1) {
        msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
        return;
    }
    var selrow = $("#dataTable").getGridParam('selrow');
    if (selrow) {
        var rowData = $('#dataTable').getRowData(selrow);
        var code = rowData.code;
        ddpAjaxCall({
            url: "rendingIcdcPrdEditDialog",
            data: code,
            successFn: showIcdcPrdEditView
        });
    } else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}

///////////////////////////////上架按钮
function putAwayIcdcPrd() {
    //上架
    var selarrrow = $("#dataTable").getGridParam('selarrrow');// 多选
    if (selarrrow != null && selarrrow.length > 0) {
        $.messager.confirm(systemConfirmLabel, "确定要上架一卡通产品吗？", function (r) {
            if (r) {
                var icdcCodes = new Array();
                for (var i = 0; i < selarrrow.length; i++) {
                    var rowData = $("#dataTable").getRowData(selarrrow[i]);
                    icdcCodes.push(rowData.code);
                }
                ddpAjaxCall({
                    url: "putAwayOrSoltOutIcdcPrd?activate=0",
                    data: icdcCodes,
                    successFn: function (data) {
                    	if (data.code == "000000") {
                            loadIcdcPrdDataTableData();
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

///////////////////////////////下架按钮
function soltOutIcdcPrd() {
    //下架
    var selarrrow = $("#dataTable").getGridParam('selarrrow');// 多选
    if (selarrrow != null && selarrrow.length > 0) {
        $.messager.confirm(systemConfirmLabel, "确定要下架一卡通产品吗？", function (r) {
            if (r) {
                var icdcCodes = new Array();
                for (var i = 0; i < selarrrow.length; i++) {
                    var rowData = $("#dataTable").getRowData(selarrrow[i]);
                    icdcCodes.push(rowData.code);
                }
                ddpAjaxCall({
                    url: "putAwayOrSoltOutIcdcPrd?activate=1",
                    data: icdcCodes,
                    successFn: function (data) {
                    	if (data.code == "000000") {
                            loadIcdcPrdDataTableData();
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

///////////////////////////////查看按钮
function viewIcdcPrd() {
    var selarrrow = $("#dataTable").getGridParam('selarrrow');// 多选
    if (selarrrow != null && selarrrow.length > 1) {
        msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
        return;
    }
    var selrow = $("#dataTable").getGridParam('selrow');
    if (selrow) {
        var rowData = $('#dataTable').getRowData(selrow);
        var code = rowData.code;
        ddpAjaxCall({
            url: "queryIcdcPrdByCode",
            data: code,
            successFn: showIcdcPrdViewData
        });
    } else {
        msgShow(systemWarnLabel, unSelectLabel, 'warning');
    }
}

//查看界面返回按钮
function closeViewIcdcPrd(){
	$('#icdcPrdViewDialog').show().dialog('close');
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////主界面结束

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////对话框功能
//新增界面对话框保存按钮
function saveIcdcPrd() {
    var errorMsg = '';
    if (isValidate('icdcPrdAddDialog')) {
        if ($('#icdcProA').combobox('getText') == '--请选择--' || $('#icdcProA').combobox('getText') == '--无--'|| $('#icdcProA').combobox('getText') == '') {
            errorMsg += '请选择通卡公司！';
        }
        if ($('#cityA').combobox('getText') == '--请选择--' || $('#cityA').combobox('getText') == '--无--'|| $('#cityA').combobox('getText') == '') {
            errorMsg += '请选择通卡公司对应城市！';
        }
        if (errorMsg != '') {
            msgShow(systemWarnLabel, errorMsg, 'warning');
            return;
        }
        $.messager.confirm(systemConfirmLabel, "确定要保存商户信息吗？", function (r) {
            if (r) {
                var saveDataAry = [];
                var bean = {
                    name: $('#nameA').val(),
                    code: $('#codeA').val(),
                    city: $('#cityA').combobox('getText'),
                    cityId: $('#cityA').combobox('getValue'),
                    supplier: $('#icdcProA').combobox('getText'),
                    supplierCode: $('#icdcProA').combobox('getValue'),
                    valuePrice: $('#priceValueA').val(),
                    saleUporDown: $('#statusA').combobox('getValue'),
                    comments: $('#commentsA').val()
                };
                saveDataAry.push(bean);
                ddpAjaxCall({
                    url: "saveIcdcPrd",
                    data: saveDataAry,
                    successFn: function (data) {
                        if (data.code == "000000") {
                            hideDialog('icdcPrdAddDialog');
                            loadIcdcPrdDataTableData(1);
                        } else {
                            msgShow(systemWarnLabel, data.message, 'warning');
                        }
                    },
                    failureFn: function (data) {
                        msgShow(systemWarnLabel, data.message, 'warning');
                    }
                });
            }
        });
    }

}

//编辑界面对话框保存按钮
function updateIcdcPrd() {
	$.messager.confirm(systemConfirmLabel, "确定要保存商户信息吗？", function (r) {
		if (r) {
		    var saveDataAry = [];
		    var bean = {
		        code: $('#codeE').val(),
		        comments: $('#commentsE').val()
		    };
		    saveDataAry.push(bean);
		    ddpAjaxCall({
		        url: "updateIcdcPrd",
		        data: saveDataAry,
		        successFn: function (data) {
		            if (data.code == "000000") {
		                hideDialog('icdcPrdEditDialog');
		                loadIcdcPrdDataTableData();
		            } else {
		                msgShow(systemWarnLabel, data.message, 'warning');
		            }
		        },
		        failureFn: function (data) {
		            msgShow(systemWarnLabel, data.message, 'warning');
		        }
		    });
		}
	});
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////对话框功能结束

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////页面数据加载和显示
//主界面加载公交卡充值产品详细信息
function loadIcdcPrdDataTableData(defaultPageNo) {
    if(!isBlank($('#valueQuery').val())){
        if(isNaN($('#valueQuery').val()) == true){
            $('#valueQuery').val('');
        }
    }
    ddpAjaxCall({
        url: "queryIcdcPrdPageByCondition",
        data: {
            nameQuery: escapePeculiar($.trim($('#nameQuery').val())),
            cityQuery: $('#cityQuery').combobox('getValue'),
            supplierQuery: $('#supplierQuery').combobox('getValue'),
            valueQuery: $('#valueQuery').val(),
            saleUporDownQuery: $('#saleUporDownQuery').combobox('getValue'),
            page: getJqgridPage('dataTable', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#dataTable'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        },
        failureFn: function (data) {
            msgShow(systemWarnLabel, data.message, 'warning');
        }
    });
}

function showIcdcPrdEditView(data) {
    clearAllText('icdcPrdEditDialog');
    $('#icdcProE').combobox('disable');
    $('#cityE').combobox('disable');
    $('#priceValueE').attr('disabled', 'disabled');
    $('#statusE').combobox('disable');
    $('#codeE').attr('disabled', 'disabled');
    $('#nameE').attr('disabled', 'disabled');
    showDialog('icdcPrdEditDialog');
    if (data.code == "000000") {
        var icdcBean = data.responseEntity;
        $('#viewTypeE').val(icdcBean.viewType);
        $('#codeE').val(icdcBean.code);
        $('#nameE').val(icdcBean.name);
        $('#icdcProE').combobox('select', icdcBean.supplier == null ? "" : icdcBean.supplier);
        $('#icdcProE').combobox('disable');
        $('#cityE').combobox('select', icdcBean.city == null ? "" : icdcBean.city);
        $('#cityE').combobox('disable');
        $('#priceValueE').val(icdcBean.valuePrice);
        $('#statusE').combobox('select', icdcBean.saleUporDown == null ? "" : icdcBean.saleUporDown);
        $('#statusE').combobox('disable');
        $('#commentsE').val(icdcBean.comments);

    } else {
        msgShow(systemWarnLabel, data.message, 'warning');
    }
}

//显示一卡通充值产品详情
function showIcdcPrdViewData(data) {
    showDialog('icdcPrdViewDialog');
    if (data.code == "000000") {
        var icdcBean = data.responseEntity;
        var elementsId = ['codeView', 'nameView', 'icdcProView', 'cityView', 'valuePriceView', 'statusView',
            'commentsView', 'rateView', 'costPriceView', 'createUserView', 'createDateView', 'updateUserView', 'updateDateView'
        ];
        var params = [icdcBean.code, htmlTagFormat(icdcBean.name), icdcBean.supplier, icdcBean.city, icdcBean.valuePrice+'元', icdcBean.saleUporDown,
            htmlTagFormat(icdcBean.comments), icdcBean.rate+'‰', icdcBean.costPrice+'元', htmlTagFormat(icdcBean.createUser), icdcBean.createDate, icdcBean.updateUser, icdcBean.updateDate
        ];
        var methods = ['html'];
        batchMethodRunner(elementsId, methods, params, null);
    } else {
        msgShow(systemWarnLabel, data.message, 'warning');
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////页面数据加载结束

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////辅助函数
function afterCompleteFunction(){

    var strIds=  $("#dataTable").jqGrid("getDataIDs");
    var csspropDown = {
        color:"#FF0000"
    };
    var csspropUp = {
        color:"#339900"
    };
    for(i =0; i < strIds.length; i++){
        var rowid = strIds[i];
        var rowData = $( "#dataTable" ).getRowData(rowid);
        if(rowData.saleUporDown=='下架') {
            $("#dataTable").jqGrid('setRowData', rowid, false, csspropDown);
        }else if(rowData.saleUporDown=='上架'){
            $("#dataTable").jqGrid('setRowData', rowid, false, csspropUp);
        }
    }
    return true;
}

//多级下拉框联动
var orgCountA = 0;
function initCombobox(elementA, elementB) {
    $(elementA).combobox({
        panelHeight: "auto",
        panelMaxHeight:200,
        onLoadSuccess: function (data) {
            if (!(typeof(data) == "undefined") & data.length > 0) {
                $(elementA).combobox('setValue', '').combobox('setText', '--请选择--');
                $(elementB).combobox('setValue', '').combobox('setText', '--无--');
                orgCountA = data.length;
            } else {
                $(elementA).combobox('setValue', '').combobox('setText', '--无--');
            }
        },
        onShowPanel: function () {
            if (orgCountA > 6) {
                $(this).combobox('panel').height(200);
            }
        },
        onSelect: function (rec) {
            $(elementB).combobox('reload', 'getIcdcBusiCities?code=' + rec.id);
        },
        onHidePanel: function () {
        },
        onLoadError: function () {
            $(elementA).combobox('setValue', '').combobox('setText', '--无--');
        }
    });
    $(elementB).combobox({
        panelHeight: "auto",
        panelMaxHeight:200,
        onLoadSuccess: function (data) {
            if (!(typeof(data) == "undefined") & data.length > 0) {
                $(elementB).combobox('setValue', '').combobox('setText', '--请选择--');
            } else {
                $(elementB).combobox('setValue', '').combobox('setText', '--无--');
            }
        },
        onShowPanel: function () {
        },
        onSelect: function (rec) {
        },
        onHidePanel: function () {
        },
        onLoadError: function () {
            $(elementB).combobox('setValue', '').combobox('setText', '--无--');
        }
    });
    checkComboBoxInput(elementA,elementB);
    checkComboBoxInput(elementB);

}

//校验下拉框
function checkComboBoxInput(element,elementsub) {
    var defaultData = [];
    $(element).next('span').find('input').blur(function () {
            var data = $(element).combobox('getData');
            var inputV = $(element).combobox('getValue');
            var inputT = $(element).combobox('getText');
            if (!(typeof(data) == "undefined") && data.length > 0) {
                var flag = false;
                if (isBlank(inputV) || isBlank(inputT)) {
                    flag = true;
                    $(element).combobox('setValue', '');
                    $(element).combobox('setText', '');
                    $(elementsub).combobox('loadData',defaultData);
                    $(elementsub).combobox('setValue', '');
                    $(elementsub).combobox('setText', '--无--');
                } else {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].id == inputV) {
                            flag = true;
                            $(element).combobox('setText', data[i].name);
                            break;
                        }
                        if (data[i].name == inputT) {
                            flag = true;
                            $(element).combobox('setValue', data[i].id);
                            break;
                        }
                    }
                }
                if (!flag) {
                    $(element).combobox('setValue', '');
                    $(element).combobox('setText', '');
                    $(elementsub).combobox('loadData',defaultData);
                    $(elementsub).combobox('setValue', '');
                    $(elementsub).combobox('setText', '--无--');
                }
            }

        }
    )
}

//批量更新页面 所有元素必须同一动作
function batchMethodRunner(element, method, param, value) {
    if (param != null && value != null) {
        if (param.length != value.length) {
            alert('js 代码错误！参数值与参数名数量不一致！');
            return;
        }
        for (var i = 0; i < element.length; i++) {
            var id = '#' + element[i];
            for (var j = 0; j < method.length; j++) {
                var mtd = method[j];
                if (mtd == 'attr') {
                    for (var k = 0; k < param.length; k++) {
                        var pn = param[k];
                        var pv = value[k];
                        $(id).attr(pn, pv);
                    }
                } else if (mtd == 'css') {
                    for (var k = 0; k < param.length; k++) {
                        var pn = param[k];
                        var pv = value[k];
                        $(id).css(pn, pv);
                    }
                }
            }
        }
    } else if (param != null && value == null) {
        for (var i = 0; i < element.length; i++) {
            var id = '#' + element[i];
            for (var j = 0; j < method.length; j++) {
                var mtd = method[j];
                if (mtd == 'html') {
                    $(id).html(param[i]);
                } else if (mtd == 'removeAttr') {
                    $(id).removeAttr(param[i]);
                }
            }
        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////辅助函数结束
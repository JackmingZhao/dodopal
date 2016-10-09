$(function() {
	initMainComponent();
	initDetailComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
	// initCombobox('#icdcProA', '#cityA');
});

function initMainComponent() {
	
	$('#importPsamDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 520,
		height :300,
		buttons : [ {
			text : '确定',
			handler : function() {
				importPsam();
				hideDialog('importPsamDialog');
			}
		}, {
			text : '取消',
			handler : function() {
				hideDialog('importPsamDialog');
			}
		}]
	});
	
	autoResize({
		dataGrid : {
			selector : '#posTbl',
			offsetHeight : 75,
			offsetWidth : 0
		},
		callback : initPsamTbl,
		toolbar : [ true, 'top' ]
	});
}

function importPsam() {
	if(isBlank($('#psamFile').val())) {
		msgShow(systemWarnLabel, "未选择任何文件", 'warning');
	} else {
		$.ajaxFileUpload({
			url : "importPsam",
			secureuri : false,
			fileElementId : "psamFile",
			dataType : "json",
			success : function(data) {
				if(data.code == "100037" || data.code == "000000") {
					findPsam();
					
					if(data.code == "100037") {
						msgShow(systemWarnLabel, data.message, 'warning');
					} 
				} else {
					if(isNotBlank(data.message)) {
						msgShow(systemWarnLabel, $('<div>').html(data.message).html(), 'warning');
					} else {
						msgShow(systemWarnLabel, "错误码：" + data.code, 'warning');
					}
				}
			},
			error : function(data) {
				msgShow(systemWarnLabel, "导入出错", 'warning');
			}
		});
	}
}

function initDetailComponent() {
	initPsamDialog();
	initAddPanel();
	initMerchantSearchModel();
	initposSearchModel();
}

function initPsamTbl(size) {
	var option = {
		datatype : function(pdata) {
			findPsam();
		},
		colNames : [ 'id', 'samSignId', 'posMertbId', 'posSeqId', 'Psam卡',
				'通卡公司编号', '通卡公司名称', '城市编号', '城市名称', '商户号', '商户名称', '通卡商户号',
				' POS号', 'POS类型', '批次号', '授权截止时间', '终端IC交易流水号', '终端账户交易流水号',
				'通讯流水号', '签到标志', '签到时间', '签退标志', '签退时间', '结算日期', '具体参数下载标志',
				'Psam参数下载标志', '日切步骤控制' ],
		colModel : [ {
			name : 'id',
			hidden : true,
			frozen : true
		}, {
			name : 'samSignId',
			hidden : true,
			frozen : true
		}, {
			name : 'posMertbId',
			hidden : true,
			frozen : true
		}, {
			name : 'posSeqId',
			hidden : true,
			frozen : true
		}, {
			name : 'samNo',
			index : 'samNo',
			width : 130,
			align : 'left',
			sortable : false,
			frozen : true
		}, {
			name : 'yktCode',
			index : 'yktCode',
			width : 130,
			align : 'left',
			sortable : false,
			frozen : true
		}, {
			name : 'yktName',
			index : 'yktName',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'cityCode',
			index : 'cityCode',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'cityName',
			index : 'cityName',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'merCode',
			index : 'merCode',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'merName',
			index : 'merName',
			width : 140,
			align : 'left',
			sortable : false
		}, {
			name : 'mchntid',
			index : 'mchntid',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'posId',
			index : 'posId',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'posType',
			index : 'posType',
			width : 130,
			align : 'left',
			sortable : false,
			formatter : function(cellval, el, rowData) {
				if (cellval == 0) {
					return 'web(家用机)';
				} else if (cellval == 1) {
					return '手持机(商用机)';
				} else {
					return '';
				}
			}
		}, {
			name : 'batchNo',
			index : 'batchNo',
			width : 130,
			align : 'left',
			sortable : false,
			formatter : htmlFormatter
		}, {
			name : 'limitTime',
			index : 'limitTime',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'posIcSeq',
			index : 'posIcSeq',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'posAccSeq',
			index : 'posAccSeq',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'posCommSeq',
			index : 'posCommSeq',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'signinFlag',
			index : 'signinFlag',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'signinDate',
			index : 'signinDate',
			width : 140,
			align : 'left',
			sortable : false
		}, {
			name : 'signoffFlag',
			index : 'signoffFlag',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'signoffDate',
			index : 'signoffDate',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'settDate',
			index : 'settDate',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'downLoadFlag',
			index : 'downLoadFlag',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'needDownLoad',
			index : 'needDownLoad',
			width : 130,
			align : 'left',
			sortable : false
		}, {
			name : 'batchStep',
			index : 'batchStep',
			width : 130,
			align : 'left',
			sortable : false
		} ],
		height : size.height - 75,
		width : size.width,
		multiselect : true,
		shrinkToFit : false,
		pager : '#psamTblPagination',
		toolbar : [ true, "top" ]
	};

	option = $.extend(option, jqgrid_server_opts);
	$('#psamTbl').jqGrid(option);
	$("#psamTbl").jqGrid('setFrozenColumns');
	$("#t_psamTbl").append($('#psamTblToolbar'));
}

// 商户名称
function findMerName() {
	$('#merchantQuery').val('');
	// 清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
	findMerchantName();
	showDialog('merchantSearchDialog');

}

// pos
function findPosName() {
	$('#posQuery').val('');
	// 清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
	findpos();
	showDialog('posSearchDialog');
}

// 加载pos
function setSelectedpos(pos) {
	if (typeof (pos) != 'undefined') {
		$('#posCode').val(pos.code);
	}
}

// 加载商户
function setSelectedMerchant(merchant) {
	if (typeof (merchant) != 'undefined') {
		$('#merName').val(merchant.merName);
		$('#merCode').val(merchant.merCode);
		$('#posCode').val('');
	}
}

// 查询
function findPsam(defaultPageNo) {
	ddpAjaxCall({
		url : "findYktPsamByPage",
		data : {
			samNo : escapePeculiar($.trim($('#samNoQuery').val())),
			yktCode : escapePeculiar($.trim($('#yktCodeQuery').val())),
			yktName : escapePeculiar($.trim($('#yktNameQuery').val())),
			mchntid : escapePeculiar($.trim($('#mchntidQuery').val())),
			posId : escapePeculiar($.trim($('#posIdQuery').val())),
			page : getJqgridPage('psamTbl', defaultPageNo)
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#psamTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function initPsamDialog() {
	$('#psamDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

// 添加
function addPsam() {
	clearAllText('psamDialog');
	$('#icdcProA').combobox('enable');
	$('#cityA').combobox('enable');
	$('#icdcProA').combobox('clear');
	$('#cityA').combobox('clear');
	$('#icdcProA').combobox('setValue', '--请选择--');
	$('#cityA').combobox('setValue', '--无--');
	$('#icdcProA').combobox('reload', 'getIcdcNames');
	$('#cityA').combobox({
		editable : false
	});
	$('#psamCode').removeAttr('disabled');
	$("#psamCode").val('');
	$("#merId").val('');

	$("#posCode").val('');
	$("#merName").val('');
	$("#merCode").val('');
	$("#finMER").removeAttr("disabled", "disabled");
	showDialog('psamDialog');
}

function initAddPanel() {
	initCombobox('#icdcProA', '#cityA');
}

// 新增界面对话框保存按钮
function savePsam() {
	var errorMsg = '';
	if (isValidate('psamDialog')) {
		if ($('#icdcProA').combobox('getText') == '--请选择--'
				|| $('#icdcProA').combobox('getText') == '--无--'
				|| $('#icdcProA').combobox('getText') == '') {
			errorMsg += '请选择通卡公司！';
		}
		if ($('#cityA').combobox('getText') == '--请选择--'
				|| $('#cityA').combobox('getText') == '--无--'
				|| $('#cityA').combobox('getText') == '') {
			errorMsg += '请选择通卡公司对应城市！';
		}

		if (errorMsg != '') {
			msgShow(systemWarnLabel, errorMsg, 'warning');
			return;
		}

		var yktCode = $('#icdcProA').combobox('getValue');
		var yktName = $('#icdcProA').combobox('getText');
		var cityCode = $('#cityA').combobox('getValue');
		var cityName = $('#cityA').combobox('getText');

		var merCode = $('#merCode').val();
		var posId = $('#posCode').val();
		var posType = $('#posType').combobox('getValue');

		if (merCode != '' && posId == '') {
			msgShow(systemWarnLabel, "请选择所选商户绑定的POS", 'warning');
			return;
		}

		if (posId != '' && posType == '') {
			msgShow(systemWarnLabel, "请选择所选的POS的pos类型", 'warning');
			return;
		}

		if (yktCode == yktName) {
			yktCode = $('#icdcProACode').val();
		}
		if (cityCode == cityName) {
			cityCode = $('#cityACode').val();
		}

		$.messager.confirm(systemConfirmLabel, "确定要保存psam卡信息吗？", function(r) {
			if (r) {
				var yktPsamBean = {
					id : $('#id').val(),
					samSignId : $('#samSignId').val(),
					posSeqId : $('#posSeqId').val(),
					posMertbId : $('#posMertbId').val(),
					samNo : $('#psamCode').val(),
					mchntid : $('#merId').val(),
					cityName : cityName,
					cityCode : cityCode,
					yktName : yktName,
					yktCode : yktCode,
					merCode : $('#merCode').val(),
					merName : $('#merName').val(),
					posId : $('#posCode').val(),
					posType : posType
				};

				ddpAjaxCall({
					url : "addYktPsam",
					data : yktPsamBean,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('psamDialog');
							findPsam();
							// loadIcdcPrdDataTableData(1);
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					},
					failureFn : function(data) {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				});
			}
		});
	}

}
// /////////////////////////////修改按钮
function editPsam() {
	var selarrrow = $("#psamTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#psamTbl").getGridParam('selrow');
	// $('#icdcProA').combobox('reload', 'getIcdcNames');
	// $('#cityA').combobox({
	// editable:false
	// });
	$('#icdcProA').combobox('disable');
	$('#cityA').combobox('disable');
	$('#psamCode').attr('disabled', true);
	if (selrow) {
		var rowData = $('#psamTbl').getRowData(selrow);
		var id = rowData.id;
		ddpAjaxCall({
			url : "findPsamById",
			data : id,
			successFn : showPsamEditView
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function showPsamEditView(data) {
	clearAllText('psamDialog');
	showDialog('psamDialog');
	if (data.code == "000000") {
		var yktPsamBean = data.responseEntity;
		$('#icdcProA').combobox('select',
				yktPsamBean.yktName == null ? "" : yktPsamBean.yktName);
		$('#icdcProACode').val(yktPsamBean.yktCode);

		$('#cityA').combobox('select',
				yktPsamBean.cityName == null ? "" : yktPsamBean.cityName);
		$('#cityACode').val(yktPsamBean.cityCode);

		$('#id').val(yktPsamBean.id);
		$('#samSignId').val(yktPsamBean.samSignId);
		$('#posMertbId').val(yktPsamBean.posMertbId);
		$('#posSeqId').val(yktPsamBean.posSeqId);
		$('#psamCode').val(yktPsamBean.samNo);
		$('#merCode').val(yktPsamBean.merCode);
		$('#merName').val(yktPsamBean.merName);
		$('#posCode').val(yktPsamBean.posId);
		// $('#posTypeE').val(yktPsamBean.posType);
		$('#posType').combobox('select',
				yktPsamBean.posType == null ? "" : yktPsamBean.posType);
		$('#merId').val(yktPsamBean.mchntid);

	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

// 删除
function deletePsam() {
	var selarrrow = $("#psamTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确认要删除吗？", function(r) {
			if (r) {
				var ids = new Array();
				for (var i = 0; i < selarrrow.length; i++) {
					var rowData = $("#psamTbl").getRowData(selarrrow[i]);
					ids.push(rowData.id);
				}
				ddpAjaxCall({
					url : "batchDeleteYktPsamByIds",
					data : ids,
					successFn : function(data) {
						if (data.code == "000000") {
							findPsam();
							msgShow(systemWarnLabel, "删除成功", 'warning');
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

// 启用商户
function activateMer() {
	var selarrrow = $("#psamTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确认要所选都启用吗？", function(r) {
			if (r) {
				var ids = new Array();
				for (var i = 0; i < selarrrow.length; i++) {
					var rowData = $("#psamTbl").getRowData(selarrrow[i]);
					ids.push(rowData.samNo);
				}
				ddpAjaxCall({
					url : "batchActivateMerByIds",
					data : ids,
					successFn : function(data) {
						if (data.code == "000000") {
							findPsam();
							msgShow(systemWarnLabel, "启用成功", 'warning');
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

// 修改重新下载参数
function modifyPara() {
	var selarrrow = $("#psamTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确认要所选都修改吗？", function(r) {
			if (r) {
				var ids = new Array();
				for (var i = 0; i < selarrrow.length; i++) {
					var rowData = $("#psamTbl").getRowData(selarrrow[i]);
					ids.push(rowData.samNo);
				}
				ddpAjaxCall({
					url : "batchNeedDownLoadPsamByIds",
					data : ids,
					successFn : function(data) {
						if (data.code == "000000") {
							findPsam();
							msgShow(systemWarnLabel, "修改成功", 'warning');
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

// 签到
function signin() {
	var selarrrow = $("#psamTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#psamTbl").getGridParam('selrow');
	if (selrow) {

		var rowData = $('#psamTbl').getRowData(selrow);
		var posType = rowData.posType;
		if (posType == '手持机(商用机)') {
			msgShow(systemWarnLabel, "此POS类型不支持手动签到", 'warning');
			return;
		} else if (posType != 'web(家用机)') {
			msgShow(systemWarnLabel, "此Psam不支持手动签到", 'warning');
			return;
		}

		$.messager.confirm(systemConfirmLabel, "确认要所选签到吗？", function(r) {
			if (r) {

				var posSignInOutDTO = {
					messagetype : '5005',// 签到
					yktcode : rowData.yktCode,
					citycode : rowData.cityCode,
					posid : rowData.posId,
					samno : rowData.samNo,
					ver : '01' // 版本号
				};

				// var id = rowData.id;
				ddpAjaxCall({
					url : "posSignInOutForV61",
					data : posSignInOutDTO,
					successFn : function(data) {
						if (data.code == "000000") {
							findPsam();
							msgShow(systemWarnLabel, "签到成功", 'warning');
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

// 签退
function signoff() {
	var selarrrow = $("#psamTbl").getGridParam('selarrrow');// 多选
	if (selarrrow != null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#psamTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#psamTbl').getRowData(selrow);
		var posType = rowData.posType;
		if (posType == '手持机(商用机)') {
			msgShow(systemWarnLabel, "此POS类型不支持手动签退", 'warning');
			return;
		} else if (posType != 'web(家用机)') {
			msgShow(systemWarnLabel, "此Psam不支持手动签退", 'warning');
			return;
		}
		$.messager.confirm(systemConfirmLabel, "确认要所选签退吗？", function(r) {
			if (r) {
				var posSignInOutDTO = {
					messagetype : '5007',// 签到
					yktcode : rowData.yktCode,
					citycode : rowData.cityCode,
					posid : rowData.posId,
					samno : rowData.samNo,
					ver : '01' // 版本号
				};
				// var id = rowData.id;
				ddpAjaxCall({
					url : "posSignInOutForV61",
					data : posSignInOutDTO,
					successFn : function(data) {
						if (data.code == "000000") {
							findPsam();
							msgShow(systemWarnLabel, "签退成功", 'warning');
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

// 多级下拉框联动
var orgCountA = 0;
function initCombobox(elementA, elementB) {
	$(elementA).combobox(
			{
				panelHeight : "auto",
				panelMaxHeight : 200,
				onLoadSuccess : function(data) {
					if (!(typeof (data) == "undefined") & data.length > 0) {
						$(elementA).combobox('setValue', '').combobox(
								'setText', '--请选择--');
						$(elementB).combobox('setValue', '').combobox(
								'setText', '--无--');
						orgCountA = data.length;
					} else {
						$(elementA).combobox('setValue', '').combobox(
								'setText', '--无--');
					}
				},
				onShowPanel : function() {
					if (orgCountA > 6) {
						$(this).combobox('panel').height(200);
					}
				},
				onSelect : function(rec) {
					$(elementB).combobox('reload',
							'getIcdcBusiCities?code=' + rec.id);
				},
				onHidePanel : function() {
				},
				onLoadError : function() {
					$(elementA).combobox('setValue', '').combobox('setText',
							'--无--');
				}
			});
	$(elementB).combobox(
			{
				panelHeight : "auto",
				panelMaxHeight : 200,
				onLoadSuccess : function(data) {
					if (!(typeof (data) == "undefined") & data.length > 0) {
						$(elementB).combobox('setValue', '').combobox(
								'setText', '--请选择--');
					} else {
						$(elementB).combobox('setValue', '').combobox(
								'setText', '--无--');
					}
				},
				onShowPanel : function() {
				},
				onSelect : function(rec) {
				},
				onHidePanel : function() {
				},
				onLoadError : function() {
					$(elementB).combobox('setValue', '').combobox('setText',
							'--无--');
				}
			});
	checkComboBoxInput(elementA, elementB);
	checkComboBoxInput(elementB);

}

// 校验下拉框
function checkComboBoxInput(element, elementsub) {
	var defaultData = [];
	$(element).next('span').find('input').blur(function() {
		var data = $(element).combobox('getData');
		var inputV = $(element).combobox('getValue');
		var inputT = $(element).combobox('getText');
		if (!(typeof (data) == "undefined") && data.length > 0) {
			var flag = false;
			if (isBlank(inputV) || isBlank(inputT)) {
				flag = true;
				$(element).combobox('setValue', '');
				$(element).combobox('setText', '');
				$(elementsub).combobox('loadData', defaultData);
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
				$(elementsub).combobox('loadData', defaultData);
				$(elementsub).combobox('setValue', '');
				$(elementsub).combobox('setText', '--无--');
			}
		}

	})
}

// 批量更新页面 所有元素必须同一动作
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

/**
 * *********************************************** 数据导出
 * ****************************************************************
 */
var expConfInfo = {
	"btnId" : "btnExportPsam", /* must */// the id of export btn in ftl
	"typeSelStr" : "YktPsamBean", /* must */// type of export
	"toUrl" : "exportPsam" /* must */// the export url
};

var filterConStr = [ // filter the result by query condition
{
	'name' : 'samNo',
	'value' : "escapePeculiar($.trim($('#samNoQuery').val()))"
}, // eval
{
	'name' : 'yktCode',
	'value' : "escapePeculiar($.trim($('#yktCodeQuery').val()))"
}, {
	'name' : 'yktName',
	'value' : "escapePeculiar($.trim($('#yktNameQuery').val()))"
}, {
	'name' : 'mchntid',
	'value' : "escapePeculiar($.trim($('#mchntidQuery').val()))"
}, {
	'name' : 'posId',
	'value' : "escapePeculiar($.trim($('#posIdQuery').val()))"
} ];

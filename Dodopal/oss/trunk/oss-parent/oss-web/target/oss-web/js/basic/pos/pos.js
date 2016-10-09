$(function() {
	initMainComponent();
	initDetailComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
//	findPos();
});

function initMainComponent() {
//	$('#versionQuery').combobox({
//		valueField : 'code',
//		textField : 'name',
//		editable : false,
//		width : globalComboboxWidth
//	});
	
//	$('#merTypeQuery').combobox({
//		valueField : 'code',
//		textField : 'name',
//		editable : false,
//		width : globalComboboxWidth
//	});
	merParentType();
	$('#cityQuery').append(createAreaComponent2('cityQuery'));
	initAreaComponent('cityQuery', globalAreaComboboxWidth);
	
	autoResize({
		dataGrid : {
			selector : '#posTbl',
			offsetHeight : 75,
			offsetWidth : 0
		},
		callback : initPosTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'posDialog' ]
	});
}


function merParentType(){
$('#merParentType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : globalComboboxWidth, 
		onSelect : function(record){
			$('#merCode').val('');
		},
		onChange : function(){
			$('#merCode').val('');
			$('#merName').val('');
		}
	});
	
	var ddic = {
			categoryCode : 'MER_TYPE_POS'
	}
	loadDdics(ddic, loadMerTypes);}

function initDetailComponent() {
	$('#posCompanyCode').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 202
	});
	
	$('#posTypeCode').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 202
	});
	
	$('#bindDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 500,
		height : 250,
		buttons : [ {
			text : '确定',
			handler : function() {
				if(isBlank($('#merCode').val())) {
					msgShow(systemWarnLabel, "请选择商户", 'warning');
				} else {
					doOpsOperation('OPER_BUNDLING');
					hideDialog('bindDialog');
				}
			}
		}, {
			text : '取消',
			handler : function() {
				hideDialog('bindDialog');
			}
		}]
	});
	
	$('#posTypeNameQuery').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 130
	});
	loadPosType(null, function(data){
		loadCombobox(data, 'posTypeNameQuery');
	});
	
	$('#modifyCityDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 300,
		height : 150,
		buttons : [ {
			text : '确定',
			handler : function() {
				modifyCity();
				hideDialog('modifyCityDialog');
			}
		}, {
			text : '取消',
			handler : function() {
				hideDialog('modifyCityDialog');
			}
		}]
	});
	
	$('#modifyVersionDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 300,
		height : 150,
		buttons : [ {
			text : '确定',
			handler : function() {
				modifyVersion();
				hideDialog('modifyVersionDialog');
			}
		}, {
			text : '取消',
			handler : function() {
				hideDialog('modifyVersionDialog');
			}
		}]
	});
	
	$('#modifyLimitationDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 300,
		height : 150,
		buttons : [ {
			text : '确定',
			handler : function() {
				modifyLimitation();
				hideDialog('modifyLimitationDialog');
			}
		}, {
			text : '取消',
			handler : function() {
				hideDialog('modifyLimitationDialog');
			}
		}]
	});
	
	$('#importPosDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 520,
		height :300,
		buttons : [ {
			text : '确定',
			handler : function() {
				importPOS();
				hideDialog('importPosDialog');
			}
		}, {
			text : '取消',
			handler : function() {
				hideDialog('importPosDialog');
			}
		}]
	});
	
	$('#modifyCity').append(createAreaComponent2('modifyCity'));
	initAreaComponent('modifyCity', globalAreaComboboxWidth);
	
	$('#cityCode').append(createAreaComponent2('cityCode'));
	initAreaComponent('cityCode', globalAreaComboboxWidth);
	
	initPosDialog();
	loadPosType(null, function(data){
		loadCombobox(data, 'posTypeCode');
	});
	loadPosCompany(null, function(data){
		loadCombobox(data, 'posCompanyCode');
	});
	initMerchantSearchModel();
}

function modifyCity() {
	var selarrrow = $("#posTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要修改城市吗？", function(r) {
			if (r) {
				var codes = "";
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#posTbl").getRowData(selarrrow[i]);
					if(i != 0 ) {
						codes +=",";
					}
					codes += rowData.code;
				}
				var poses = {
					code : codes,
					cityCode : getCityCodeFromCompoent('modifyCity'),
			    	cityName : getCityNameFromCompoent('modifyCity'),
			    	provinceCode : getProvinceCodeFromCompoent('modifyCity'),
			    	provinceName : getProvinceNameFromCompoent('modifyCity')
				}
				ddpAjaxCall({
					url : "modityCity",
					data : poses,
					successFn : function(data){
						if(data.code == "000000") {
							findPos();
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

function modifyLimitation() {
	var modifyLimitation = $('#modifyLimitation').val();
	if(modifyLimitation < 0  ||  modifyLimitation!=parseInt(modifyLimitation) ){
		msgShow(systemWarnLabel, "只能输入正整数", 'warning');
		return;
	}
	var selarrrow = $("#posTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要修改限制笔数吗？", function(r) {
			if (r) {
				var codes = "";
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#posTbl").getRowData(selarrrow[i]);
					if(i != 0 ) {
						codes += ",";
					}
					codes += rowData.code;
				}
				var poses = {
					code : codes,
					maxTimes : $('#modifyLimitation').val()
				}
				ddpAjaxCall({
					url : "modifyLimitation",
					data : poses,
					successFn : function(data){
						if(data.code == "000000") {
							findPos();
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

function modifyVersion() {
	if(isBlank($('#modifyVersion').val())){
		msgShow(systemWarnLabel, "POS版本号不能为空", 'warning');
		return;
	}
	var selarrrow = $("#posTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要修改版本吗？", function(r) {
			if (r) {
				var codes = "";
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#posTbl").getRowData(selarrrow[i]);
					if(i != 0 ) {
						codes +=",";
					}
					codes += rowData.code;
				}
				var poses = {
					code : codes,
					version : $('#modifyVersion').val()
				}
				ddpAjaxCall({
					url : "modifyVersion",
					data : poses,
					successFn : function(data){
						if(data.code == "000000") {
							findPos();
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

function loadMerTypes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
//		reLoadComboboxData($('#merTypeQuery'), data.responseEntity)
		reLoadComboboxData($('#merParentType'), data.responseEntity)
//		$('#merTypeQuery').combobox('select','');
		$('#merParentType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function initPosTbl(size) {
	var option = {
			datatype : function (pdata) {
				findPos();
		    },
		    colNames : [ 'posId', 'serialNo', 'comments', 'cityCode', 'POS编号', 'POS型号', 'POS厂商', '所属商户', '商户类型', '省份', '城市', '版本', 'POS批次','限制笔数','POS成本','是否绑定', 'POS状态', '绑定时间'],
		    colModel : [ 
			             { name : 'posId', hidden : true ,frozen:true}, 
			             { name : 'serialNo', hidden : true,frozen:true },
			             { name : 'comments', hidden : true, formatter: htmlFormatter,frozen:true },
			             { name : 'cityCode', hidden : true,frozen:true },
			             { name : 'code', index : 'code', width : 130, align : 'left', sortable : false,frozen:true }, 
			             { name : 'posTypeName', index : 'posTypeName', width : 80, align : 'left', sortable : false }, 
			             { name : 'posCompanyName', index : 'posCompanyName', width : 80, align : 'left', sortable : false }, 
			             { name : 'merchantName', index : 'merchantName', width : 140, align : 'left', sortable : false }, 
			             {
			     			name : 'merchantType', index : 'merchantType', width : 120, align : 'left', sortable : false,
			     			formatter: function(cellval, el, rowData) {
			     				if(cellval == '99') {return '个人'} 
			     				else if(cellval == '10') {return '代理商户'	} 
			     				else if(cellval == '11') {return '代理商自有网点'	} 
			     				else if(cellval == '12') {return '连锁商户'	} 
			     				else if(cellval == '13') {return '连锁直营网点'	} 
			     				else if(cellval == '14') {return '连锁加盟网点'	} 
			     				else if(cellval == '15') {return '都都宝自有网点'	} 
			     				else if(cellval == '16') {return '集团商户'	} 
			     				else if(cellval == '17') {return '供应商'	} 
			     				else if(cellval == '18') {return '外接商户'	} 
			     				else { return ''};
			     			}
			     		 },
			     		{ name : 'provinceName', index : 'provinceName', width : 60, align : 'left', sortable : false },
			             { name : 'cityName', index : 'cityName', width : 80, align : 'left', sortable : false },
			             { name : 'version', index : 'version', width : 50, align : 'left', sortable : false,formatter: htmlFormatter },
			             { name : 'serialNo', index : 'serialNo', width : 120, align : 'left', sortable : false},
			             { name : 'maxTimes', index : 'maxTimes', width : 80, align : 'left', sortable : false },
			             { name : 'unitCost', index : 'unitCost', width : 80, align : 'left', sortable : false},
			             { name : 'bind', index : 'bind', width : 60, align : 'left', sortable : false, formatter: bindFormatter},
			             { name : 'status', index : 'status', width : 60, align : 'left', sortable : false , formatter: posStatusfomatter},
			             { name : 'bundlingDate', index : 'bundlingDate', width : 140, align : 'left', sortable : false, formatter: cellDateFormatter},
			             
			            
			            ],
//			caption : "POS",
//			sortname : 'code',
			height : size.height - 75,
			width : size.width,
			multiselect: true,
			shrinkToFit : false,
			pager : '#posTblPagination',
			toolbar : [ true, "top" ]
		};
		
		 option = $.extend(option, jqgrid_server_opts);
		 $('#posTbl').jqGrid(option);
		 $("#posTbl").jqGrid('setFrozenColumns');  
		$("#t_posTbl").append($('#posTblToolbar'));
}
function initPosDialog() {
	$('#posDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function addPos() {
	clearAllText('posDialog');
	$('#posCodes').show();
	$('#code').removeAttr('disabled');
	$('#serialNo').removeAttr('disabled');
	$('#comments').val('');
	$('#posCompanyCode').combobox('select', '');
	$('#posTypeCode').combobox('select', '');
	$('#status').combobox('select', '0');
	$('#maxTimes').val('0');
	$('#unitCost').val('0');
	
	showDialog('posDialog');
}

function editPos() {
	$('#posCodes').hide();
	clearAllText('posDialog');
	$('#posCodes').linkbutton({text:"号段录入"});
	$('#code').attr('disabled', true);
	$('#serialNo').attr('disabled', true);
	$('#code').show();
	$('#codeStart').hide();
	$('#codeEnd').hide();
	$('#score').hide();
	
	var rowData = getSelectedRowDataFromMultiples('posTbl');
	if (rowData != null) {
		if(rowData != null) {
			var	merCode=rowData.merCode;
			ddpAjaxCall({
				url : "findPosById",
				data : rowData.posId,
				successFn : function(data) {
					if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
						loadJsonData2Page('posDialog', data.responseEntity);
						$('#unitCost').val(data.responseEntity.unitCost/100);
						$('#maxTimes').val(data.responseEntity.maxTimes);
						$('#posTypeCode').combobox('select', data.responseEntity.posTypeCode);
						$('#posCompanyCode').combobox('select', data.responseEntity.posCompanyCode);
						$('#status').combobox('select', data.responseEntity.status);
						setAreaComponent('cityCode', data.responseEntity.provinceCode, data.responseEntity.cityCode);
						showDialog('posDialog');
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
		}
	}
}

function savePos() {
	if(isValidate('posDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存POS信息吗？", function(r) {
			if (r) {
				var posCodesValue = $('#posCodes').text();
				var code = "";
				var codeStart = "";
				var codeEnd = "";
				if(posCodesValue == "号段录入") {
					code = $('#code').val();
				} else {
					codeStart = $('#codeStart').val();
					codeEnd = $('#codeEnd').val();
				}
				var pos = {
						id : $('#posId').val(),
						code : code,
						codeStart : codeStart,
						codeEnd : codeEnd,
						posTypeCode : $('#posTypeCode').combobox('getValue'),
						posCompanyCode : $('#posCompanyCode').combobox('getValue'),
						status : $('#status').combobox('getValue'),
						version : getTrimValue($('#version').val()),
						serialNo : $('#serialNo').val(),
						unitCost : $('#unitCost').val()*100,
						maxTimes : $('#maxTimes').val(),
						comments : $('#comments').val(),
						cityCode : getCityCodeFromCompoent('cityCode'),
						cityName : getCityNameFromCompoent('cityCode'),
						provinceCode : getProvinceCodeFromCompoent('cityCode'),
						provinceName : getProvinceNameFromCompoent('cityCode')
				};
				ddpAjaxCall({
					url : "savePos",
					data : pos,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('posDialog');
							if(isBlank(pos.id)) {
								findPos(1);
							} else {
								findPos();
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

function deletePos() {
	var selarrrow = $("#posTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "POS信息一旦删除将无法恢复, 确定要删除吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#posTbl").getRowData(selarrrow[i]);
					ids.push(rowData.code);
				}
				ddpAjaxCall({
					url : "deletePos",
					data : ids,
					successFn : function(data){
						if(data.code == "000000") {
							findPos();
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

function afterDeletePos(data) {
	if(data.code == "000000") {
		findPos();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function findPos(defaultPageNo) {
	ddpAjaxCall({
		url : "findPos",
		data : {
			code : escapePeculiar($.trim($('#posCodeQuery').val())),
			posCompanyName : escapePeculiar($.trim($('#posCompanyNameQuery').val())),
			posTypeName :  $('#posTypeNameQuery').combobox('getValue'),
			version : escapePeculiar($.trim($('#versionQuery').val())),
			bind : $('#bindFind').combobox('getValue'),
			provinceCode : getProvinceCodeFromCompoent('cityQuery'),
			cityCode : getCityCodeFromCompoent('cityQuery'), 
			status : $('#statusQuery').combobox('getValue'),
			page : getJqgridPage('posTbl', defaultPageNo)
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#posTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function viewPos() {
	var rowData = getSelectedRowDataFromMultiples('posTbl');
	if (rowData != null) {
		ddpAjaxCall({
			url : "findPosById",
			data : rowData.posId,
			successFn : loadViewPos
//			successFn : function(response) {
//				showDialog('viewPosDialog');
//				var htmlKeys = ['version','comments'];
//				loadJsonData2ViewPageWithHtmlFormat('viewPosDialog', response, htmlKeys, viewPosCallback);
//			}
		});
	}
}

function loadViewPos(data) {
	if(data.code=="000000"){
		$('#viewPosDialog').show().dialog('open');
		var posBean = data.responseEntity;
		$('#view_code').html(posBean.code);
		$('#view_posCompanyName').html(posBean.posCompanyName);
		$('#view_posTypeName').html(posBean.posTypeName);
		$('#view_version').html(htmlTagFormat(posBean.version));
		$('#view_serialNo').html(posBean.serialNo);
		$('#view_merchantName').html(posBean.merchantName);
		$('#view_provinceName').html(posBean.provinceName);
		$('#view_cityName').html(posBean.cityName);
		$('#view_unitCost').html(posBean.unitCost/100);
		$('#view_bundlingDate').html(ddpDateFormatter(posBean.bundlingDate));
		$('#view_maxTimes').html(posBean.maxTimes);
		$('#view_comments').html(htmlTagFormat(posBean.comments));
		$('#view_createDate').html(ddpDateFormatter(posBean.createDate));
		$('#view_updateDate').html(ddpDateFormatter(posBean.updateDate));
		$('#view_createUser').html(posBean.createUser);
		$('#view_updateUser').html(posBean.updateUser);
		loadViewPosSelectList(posBean);
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}

	
}
function loadViewPosSelectList(data) {
	var status = data.status;
	var statusStr = status;
	if ("0" == status) {
		statusStr = "启用";
	} else if ("1" == status) {
		statusStr = "停用";
	} else if ("2" == status) {
		statusStr = "作废";
	} else if ("3" == status) {
		statusStr = "消费封锁";
	} else if ("4" == status) {
		statusStr = "充值封锁";
	}
	$('#view_status').html(statusStr);

	var merchantType = data.merchantType;

	var merchantTypeStr = merchantType;
	if (merchantType == '99') {
		merchantTypeStr = '个人';
	} else if (merchantType == '10') {
		merchantTypeStr = '代理商户';
	} else if (merchantType == '11') {
		merchantTypeStr = '代理商自有网点';
	} else if (merchantType == '12') {
		merchantTypeStr = '连锁商户';
	} else if (merchantType == '13') {
		merchantTypeStr = '连锁直营网点';
	} else if (merchantType == '14') {
		merchantTypeStr = '连锁加盟网点';
	} else if (merchantType == '15') {
		merchantTypeStr = '都都宝自有网点';
	} else if (merchantType == '16') {
		merchantTypeStr = '集团商户';
	} else if (merchantType == '17') {
		merchantTypeStr = '供应商';
	} else if (merchantType == '18') {
		merchantTypeStr = '外接商户';
	}
	$('#view_merchantType').html(merchantTypeStr);
	
	var bind = data.bind;
	var bindStr = bind;
	if ("0" == bind) {
		bindStr = "绑定";
	} else if ("1" == bind) {
		bindStr = "未绑定";
	}
	$('#view_bind').html(bindStr);
}

function setSelectedMerchant(merchant) {
	if(typeof(merchant) != 'undefined') {
		$('#merCode').val(merchant.merCode);
		$('#merName').val(merchant.merName);
	}
}

function resetPosQuery() {
	clearAllText('posQueryCondition');
}

function posOperation(operation) {
	var selarrrow = $("#posTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		if(operation == 'OPER_BUNDLING') {
			merParentType();
			$('#merCode').val('');
			$('#merName').val('');
			showDialog('bindDialog');
		} else {
			doOpsOperation(operation);
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function doOpsOperation(operation) {
	var selarrrow = $("#posTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要执行操作吗？", function(r) {
			if (r) {
				var posCodes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#posTbl").getRowData(selarrrow[i]);
					posCodes.push(rowData.code);
				}
				var oper = {
					operation : operation,
					merCode : $('#merCode').val(), // TODO 除绑定之, 是否需要这个参数
					pos : posCodes
				}
				ddpAjaxCall({
					url : "posOperation",
					data : oper,
					successFn : function(data) {
						handleResponse(data, findPos);
					}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function modifyPosByBatch(dialogName) {
	var selarrrow = $("#posTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		showDialog(dialogName);
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}


function posCodes() {
	var posCodesValue = $('#posCodes').text();
	if(posCodesValue == "号段录入") {
		$('#posCodes').linkbutton({text:"单个录入"});		
		$('#code').hide();
		$('#code').removeClass('validatebox-invalid');
		$('#codeStart').removeClass('validatebox-invalid');
		$('#codeEnd').removeClass('validatebox-invalid');
		$('#codeStart').val('');
		$('#codeEnd').val('');
		$('#code').val('');		
		$('#codeStart').show();
		$('#codeEnd').show();
		$('#score').show();
		
	} else {
		$('#posCodes').linkbutton({text:"号段录入"});
		$('#codeStart').val('');
		$('#codeEnd').val('');
		$('#code').val('');		
		$('#code').removeClass('validatebox-invalid');
		$('#codeStart').removeClass('validatebox-invalid');
		$('#codeEnd').removeClass('validatebox-invalid');
		$('#code').show();
		$('#codeStart').hide();
		$('#codeEnd').hide();
		$('#score').hide();
	}
}

function importPOS() {
	if(isBlank($('#posFile').val())) {
		msgShow(systemWarnLabel, "未选择任何文件", 'warning');
	} else {
		$.ajaxFileUpload({
			url : "importPos",
			secureuri : false,
			fileElementId : "posFile",
			dataType : "json",
			success : function(data) {
				if(data.code == "100037" || data.code == "000000") {
					findPos();
					
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

function showMerchantSearchDialog() {
	if(isBlank($('#merParentType').combobox('getValue'))) {
		return;
	}
	
	$("#merchantSearchDialog").panel({title:'商户名称'});
	$('#merchantSearchDialog').show().dialog('open');
	
	findMerchantName();
}




/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnExportPos", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "PosInfo", 	                        /*must*/// type of export
	"toUrl"			: "exportPos" 			            /*must*/// the export url
};

var filterConStr = [	// filter the result by query condition
		{'name':'code', 	'value': "escapePeculiar($.trim($('#posCodeQuery').val()))"			},	// eval
		{'name':'posCompanyName',	'value': "escapePeculiar($.trim($('#posCompanyNameQuery').val()))"		},
		{'name':'posTypeName',	'value': "escapePeculiar($('#posTypeNameQuery').combobox('getValue'))"	},
		{'name':'version',	'value': "escapePeculiar($.trim($('#versionQuery').val()))"   },
		{'name':'bind',	'value': "escapePeculiar($('#bindFind').combobox('getValue'))"   },
		{'name':'provinceCode',	'value': " getProvinceCodeFromCompoent('cityQuery')"   },
		{'name':'cityCode',	'value': " getCityCodeFromCompoent('cityQuery')"   },
		{'name':'status',	'value': "escapePeculiar($('#statusQuery').combobox('getValue'))"   }
		
	];

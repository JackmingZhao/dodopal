// 商户快速查询模块
function initMerchantSearchModel() {
	initMerchantSearchDialog();
	initMerchantListTbl();
}

function initMerchantSearchDialog() {
	$('#merchantSearchDialog').dialog({
				collapsible : false,
				modal : true,
				closed : true,
				closable : true,
				width : 700,
				height : 380
			});
}

function initMerchantListTbl() {
	var option = {
		datatype : function(pdata) {
			findMerchantName();
		},
		colNames : ['merchantId', '商户编码', '商户名称', '商户类型'],
		colModel : [{
					name : 'merchantId',
					index : 'merchantId',
					hidden : true
				}, {
					name : 'merCode',
					index : 'merCode',
					width : 100,
					align : 'center',
					sortable : false
				}, {
					name : 'merName',
					index : 'merName',
					width : 300,
					align : 'center',
					sortable : false
				}, {
					name : 'merTypeView',
					index : 'merTypeView',
					width : 100,
					align : 'left',
					sortable : false
				}],
		// sortname : 'merCode',
		width : $('#merchantSearchDialog').width() - 2,
		height : $('#merchantSearchDialog').height() - 111,
		pager : '#merchantListTblPagination',
		// rowNum : 30,
		// onSortCol : jqGridClientSort,
		// viewrecords : true,
		ondblClickRow : function(rowid, iRow, iCol, e) {
			if (rowid) {
				var merchant = $('#merchantListTbl').getRowData(rowid);
				setSelectedMerchant(merchant);
				var merType = document.getElementById('merType');
				if(merType){
					// 判断在OSS开户时是否为直营网点
					findMerParentType(merchant);
					//判断在OSS开户时是否上级商户类型
					findMerRateSupplement(merchant);
				}
				hideDialog('merchantSearchDialog');
			}
		}
	};
	// $('#t_merchantListTbl').append($('#merchantListTblToolbar'));
	option = $.extend(option, jqgrid_server_opts);
	$('#merchantListTbl').jqGrid(option);
	$("#t_merchantListTbl").append($('#merchantListTblToolbar'));
}

function selectMerchat() {
	var selrow = $("#merchantListTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#merchantListTbl').getRowData(selrow);
		setSelectedMerchant(rowData);
		var merType = document.getElementById('merType');
		if(merType){
			// 判断在OSS开户时是否为直营网点
			findMerParentType(rowData);
			//判断在OSS开户时是否上级商户类型
			findMerRateSupplement(rowData);
		}
		hideDialog('merchantSearchDialog');
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
// 判断已审核商户的上级商户名称
function findMerchantName() {
	if (isBlank($('#merParentType').combobox('getValue'))) {
		return;
	}
	var queryData = {};
	var queryType = $('#queryType').combobox('getValue');
	if ('code' == queryType) {
		queryData.merCode = $('#merchantQuery').val();
	} else if ('name' == queryType) {
		queryData.merName = $('#merchantQuery').val();
	}
	queryData.page = getJqgridPage('merchantListTbl');

	queryData.merType = $('#merParentType').combobox('getValue');
	queryData.merState = '1';
	queryData.activate = '0';

	ddpAjaxCall({
				url : $.base + "/merchant/enterprise/findMerchantsPage",
				data : queryData,
				successFn : function(data) {
					if (data.code == "000000") {
						loadJqGridPageData($('#merchantListTbl'),
								data.responseEntity);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
}
// 判断已审核商户的上级商户名称
function findUnverMerchantName() {
	if (isBlank($('#merParentTypes').combobox('getValue'))) {
		return;
	}
	var queryData = {};
	var queryType = $('#queryType').combobox('getValue');
	if ('code' == queryType) {
		queryData.merCode = $('#merchantQuery').val();
	} else if ('name' == queryType) {
		queryData.merName = $('#merchantQuery').val();
	}
	queryData.page = getJqgridPage('merchantListTbl');

	queryData.merType = $('#merParentTypes').combobox('getValue');
	queryData.merState = '1';
	queryData.activate = '0';

	ddpAjaxCall({
				url : $.base + "/merchant/enterprise/findMerchantsPage",
				data : queryData,
				successFn : function(data) {
					if (data.code == "000000") {
						loadJqGridPageData($('#merchantListTbl'),
								data.responseEntity);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
}

/**
 * Title:判断连锁商是否是直营网点 Time:2015-07-18 Name:qjc
 */
function findMerParentType(merchant) {
	var merCode = '';
	merCode = merchant.merCode;
	ddpAjaxCall({
				url : "findMerchantByCode",
				data : merCode,
				successFn : function(data) {
					if (data.code == "000000") {
						loadMerYKTinfo(data);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
}
function loadMerYKTinfo(data) {
	// 先判断商户类型是否连锁商直营网点
	var merBusRateBeanList = data.responseEntity.merBusRateBeanList;
	// 充值业务
	var merBusRateBeanListRecharge = new Array();
	// 消费业务
	var merBusRateBeanListPayment = new Array();
	//账户类型
	var fundType = data.responseEntity.fundType;
	var merType = $('#merType').combobox('getValue');
	if (isNotBlank(merBusRateBeanList)) {
		if (merType == '13') {
			$.each(merBusRateBeanList, function(index, element) {
						if(element.rateCode=='01') {
							merBusRateBeanListRecharge.push(element);
						} else if(element.rateCode=='03') {
							merBusRateBeanListPayment.push(element);
						}
					});
			// 账户类型
			$("input[name='fundType'][value="+fundType+"]").attr("checked", true);
			$("input[name='fundType']:eq(0)").attr("disabled", 'disabled');
			$("input[name='fundType']:eq(1)").attr("disabled", 'disabled');
			
			if(isNotBlank(merBusRateBeanListRecharge)) {
				$('#yktTblLine').show();
				$('#yktTbl').setColProp('rate', {
						editable : false
					});
					
				$('#yktTbl').setColProp('rateType', {
						editable : false
					});
				loadJqGridData($('#yktTbl'), merBusRateBeanListRecharge);
				var selected = '';
				$.each(merBusRateBeanListRecharge, function(index, element) {
						if (selected != '') {
							selected += ',';
						}
						selected += element.proName;
					});
				// $('#ykt').hide();
				$('#ykt').attr("disabled", true);
				$('#TKNameVerLine').show();
				$('#providerCode').val(selected);
				selectAllLines('yktTbl');
				$('#cb_yktTbl').attr('checked','checked');
				$('#cb_yktTbl').attr('disabled','disabled');
			}
			if(isNotBlank(merBusRateBeanListPayment)) {
				$('#yktTblLinePayment').show();
				$('#yktTblPayment').setColProp('rate', {
						editable : false
					});
					
				$('#yktTblPayment').setColProp('rateType', {
						editable : false
					});
				loadJqGridData($('#yktTblPayment'), merBusRateBeanListPayment);
				var selectedPayment = '';
				$.each(merBusRateBeanListPayment, function(index, element) {
						if (selectedPayment != '') {
							selectedPayment += ',';
						}
						selectedPayment += element.proName;
					});
				$('#yktPayment').attr("disabled", true);
				$('#TKNameVerLinePayment').show();
				$('#providerCodePayment').val(selectedPayment);
				selectAllLines('yktTblPayment');
				$('#cb_yktTblPayment').attr('checked','checked');
				$('#cb_yktTblPayment').attr('disabled','disabled');
			}

		} else {
			// $('#TKNameVerLine').show();
			// $('#ykt').show();
			$('#ykt').removeAttr("disabled");
			$('#yktPayment').removeAttr("disabled");
		}
	} else {
		// $('#TKNameVerLine').hide();
		// $('#ykt').hide();
		$('#ykt').attr("disabled", true);
		$('#yktTblLine').hide();
		$('#yktPayment').attr("disabled", true);
		$('#yktTblLinePayment').hide();
	}
}


/**
 * 判读是否为连锁商和代理商的上级商户
 * @param {} data
 */
function findMerRateSupplement(merchant){
	var merParentCode = '';
	merParentCode = merchant.merCode;
	var merType = $('#merType').combobox('getValue');
	// 初始化加载费率类型
	ddpAjaxCall({
				async: false,
				url : "findMerRateSupplementByCode?merType=" + merType,
				data : merParentCode,
				successFn : function(data) {
						loadPrdRateModel(data);
				}
			});
}
//当上级商户
function loadPrdRateModel(data){
$('#PrdRateType').html('');
if (data.code = '000000') {
		// 获取加载的值
		var prdRateBean = data.responseEntity;
		var line = '';
		if(prdRateBean!=null){
			for (var i = 0; i < prdRateBean.length; i++) {
				line = '<td><input type="checkbox" id="'
						+ prdRateBean[i].rateCode
						+ '" name="prdRateName" style="width:20px;border:0;" ';
				line += 'value= "' + prdRateBean[i].rateCode
						+ '" onclick="prdRateTypeSys(this)" />&nbsp;&nbsp;'
						+ prdRateBean[i].rateCodeView + '</td>';
				$('#PrdRateType').append(line);
			}
			//先判断商户类型是否连锁商直营网点
			var merType =$('#merType').combobox('getValue');
			if(merType =='13'){
				$('#PrdRateType').find("input[type=checkbox]").attr("checked","checked");
				$('#PrdRateType').find("input[type=checkbox]").attr("disabled","disabled");
			}
		}else{
			$('#PrdRateType').html('');
		}
		
	}else{
		$('#PrdRateType').html('');
	}
}

// 勾选业务类型
function prdRateTypeSys(obj) {
	var checked = $("input[id=" + obj.id + "][type='checkbox']").is(':checked');
	var merType = $('#merType').combobox('getValue');
	if (checked) {
		if ($('#' + obj.id + '').val() == '01') {
			if (merType == '18') {
				$("#yktInfoUrlLine").show();
				$('#pageCheckCallbackUrlLine').show();
			}
			$("#TKNameVerLinePayment").show();
		}
		if ($('#' + obj.id + '').val() == '03') {
			$("#TKNameVerLinePayment").show();
		}
	} else {
		if ($('#' + obj.id + '').val() == '01') {
			$("#YktPageCallbackUrl").val('');
			$("#YktServiceNoticeUrl").val('');
			$("#pageCheckCallbackUrl").val('');
			$("#yktInfoUrlLine").hide();
			$('#pageCheckCallbackUrlLine').hide();
			$('#yktInfoContent').find("input[type=checkbox]").removeAttr("checked");
			yktHandler();
			$("#yktTblLine").hide();
			$("#TKNameVerLine").hide();
		}
		if ($('#' + obj.id + '').val() == '03') {
			$('#yktInfoContentPayment').find("input[type=checkbox]").removeAttr("checked");
			yktHandlerPayment();
			$("#yktTblLinePayment").hide();
			$("#TKNameVerLinePayment").hide();
		}
	}
}
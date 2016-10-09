// 商户快速查询模块
function initMerUnverSearchModel() {
	initMerUnverSearchDialog();
	initMerUnverListTbl();
}

function initMerUnverSearchDialog() {
	$('#merchantSearchDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 700,
		height : 380
	});
}

function initMerUnverListTbl() {
		var option = {
				datatype : function (pdata) {
					findMerchantName();
			    },
		colNames : [ 'merchantId', '商户编码', '商户名称', '商户类型' ],
		colModel : [ { name : 'merchantId', index : 'merchantId', hidden : true }, 
		             { name : 'merCode', index : 'merCode', width : 100, align : 'center', sortable : false }, 
		             { name : 'merName', index : 'merName', width : 300, align : 'center', sortable : false }, 
		             { name : 'merTypeView', index : 'merTypeView', width : 100, align : 'left', sortable : false}
		            ],
		//sortname : 'merCode',
		width : $('#merchantSearchDialog').width() -2,
		height : $('#merchantSearchDialog').height() -111,
		pager : '#merchantListTblPagination',
//		rowNum : 30,
//		onSortCol : jqGridClientSort,
//		viewrecords : true,
		ondblClickRow : function(rowid, iRow, iCol,e) {
			if(rowid) {
				var merchant = $('#merchantListTbl').getRowData(rowid) ;
				//判断在OSS开户时是否为直营网点
				findMerParentType(merchant);
				//判断在OSS开户时是否上级商户类型
				findMerRateSupplement(merchant);
				setSelectedMerchant(merchant);
				hideDialog('merchantSearchDialog');
			}
		}
	};
	//$('#t_merchantListTbl').append($('#merchantListTblToolbar'));
	 option = $.extend(option, jqgrid_server_opts);
	 $('#merchantListTbl').jqGrid(option);
	 $("#t_merchantListTbl").append($('#merchantListTblToolbar'));
}

function selectMerchat() {
	var selrow = $("#merchantListTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#merchantListTbl').getRowData(selrow);		
		//判断在OSS开户时是否为直营网点
		findMerParentType(rowData);
		//判断在OSS开户时是否上级商户类型
		findMerRateSupplement(rowData);
		setSelectedMerchant(rowData);
		hideDialog('merchantSearchDialog');
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
//判断已审核商户的上级商户名称
function findMerchantName() {
	if(isBlank($('#merParentTypes').combobox('getValue'))) {
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
				loadJqGridPageData($('#merchantListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}



//审核时每次选择不同的上级商户名称,清空通卡公司名称，费率，选择通卡公司数据
function clearMerPartypeName(){
	//清空通卡公司名称，隐藏通卡公司费率选择，清空通卡公司费率
	$('#providerCode').val("");
	$('#yktTbl').clearGridData();
	$('#yktTblLine').hide();
	$('#yktInfoContent').html('');

	$('#providerCodePayment').val("");
	$('#yktTblPayment').clearGridData();
	$('#yktTblLinePayment').hide();
	$('#yktInfoContentPayment').html('');
}


/**
 * Title:判断连锁商是否是直营网点
 * Time:2015-07-18
 * Name:qjc
*/
function findMerParentType(merchant){
	var merCode = merchant.merCode;
//	var merParCode = $('#merParentCode').val();
//	
//	if (merParentCode==merParCode){
//		return;
//	} 
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

function loadMerYKTinfo(data){
	clearMerPartypeName();
		// 先判断商户类型是否连锁商直营网点
	var merBusRateBeanList = data.responseEntity.merBusRateBeanList;
	// 充值业务
	var merBusRateBeanListRecharge = new Array();
	// 消费业务
	var merBusRateBeanListPayment = new Array();
	//账户类型
	var fundType = data.responseEntity.fundType;
	//先判断商户类型是否连锁商直营网点
	var merType =$('#merType').combobox('getValue');
	if(isNotBlank(merBusRateBeanList)) {
		if(merType =='13'){
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
				$('#yktTbl').setColProp('rate',{editable: false});
				$('#yktTbl').setColProp('rateType',{editable: false});
				loadJqGridData($('#yktTbl'), merBusRateBeanListRecharge);
				var selected = '';
				$.each(merBusRateBeanListRecharge, function(index, element) {
					if(selected != '') {
						selected += ',';
					}
					selected += element.proName;
				});
				$('#ykt').attr("disabled", true);
				$('#TKNameLine').show();
				$('#providerCode').val(selected);
				selectAllLines('yktTbl');
				$('#cb_yktTbl').attr('checked','checked');
				$('#cb_yktTbl').attr('disabled','disabled');
			}
			
			if(isNotBlank(merBusRateBeanListPayment)) {
				$('#yktTblLinePayment').show();
				$('#yktTblPayment').setColProp('rate',{editable: false});
				$('#yktTblPayment').setColProp('rateType',{editable: false});
				loadJqGridData($('#yktTblPayment'), merBusRateBeanListPayment);
				var selectedPayment = '';
				$.each(merBusRateBeanListPayment, function(index, element) {
					if(selectedPayment != '') {
						selectedPayment += ',';
					}
					selectedPayment += element.proName;
				});
				$('#yktPayment').attr("disabled", true);
				$('#TKNameLinePayment').show();
				$('#providerCodePayment').val(selectedPayment);
				selectAllLines('yktTblPayment');
				$('#cb_yktTblPayment').attr('checked','checked');
				$('#cb_yktTblPayment').attr('disabled','disabled');
			}
		}else{
			$('#ykt').removeAttr("disabled");
			$('#yktPayment').removeAttr("disabled");
		}
	} else {
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
	var merParentCode = merchant.merCode;
	var merParCode = $('#merParentCode').val();
	if (merParentCode==merParCode){
		return;
	} 
	var merType = $('#merType').combobox('getValue');
	// 初始化加载业务类型
	ddpAjaxCall({
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
			$("#TKNameLine").show();
		}
		
		if ($('#' + obj.id + '').val() == '03') {
			$("#TKNameLinePayment").show();
		}
	} else {
		if ($('#' + obj.id + '').val() == '01') {
			$("#YktPageCallbackUrl").val('');
			$("#YktServiceNoticeUrl").val('');
			$("#yktInfoUrlLine").hide();
			$('#pageCheckCallbackUrlLine').hide();
			$('#yktInfoContent').find("input[type=checkbox]").removeAttr("checked");
			yktHandler();
			$("#yktTblLine").hide();
			$("#TKNameLine").hide();
		}
		if ($('#' + obj.id + '').val() == '03') {
			$('#yktInfoContentPayment').find("input[type=checkbox]").removeAttr("checked");
			yktHandlerPayment();
			$("#yktTblLinePayment").hide();
			$("#TKNameLinePayment").hide();
		}
	}
}
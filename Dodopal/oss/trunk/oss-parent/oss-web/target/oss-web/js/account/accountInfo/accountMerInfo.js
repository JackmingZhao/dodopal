$(function() {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

//初始化
function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#accountInfoTbl',
			offsetHeight : 90,
			offsetWidth : 0
		},
		callback : initAccountInfoTbl,
		toolbar : [ true, 'top' ]
	});
	initChangeDialog();
	initChangeListTbl();
	findchangeType();
}

//账户列表
function initAccountInfoTbl(size) {
	var merType = $('#merType').val()
	var customerNoColName = '客户号';
	var cuNameColName = '客户名称';
	if('1' === merType){//商户
		customerNoColName = '商户号';
		cuNameColName = '商户名称';
	}
	var option = {
		datatype : function(pdata) {
			findAccountInfo();
		},
		colNames : [ 'ID', 'ACID' ,cuNameColName, customerNoColName,  'FUNDTYPE', '账户类型', '可用金额(元)', '冻结金额(元)', '累计总金额(元)', '总余额(元)', 'STATE', '状态' ],
		colModel : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'acid',
			hidden : true
		}, {
			name : 'cuName',
			index : 'cuName',
			width : 200,
			align : 'left',
			sortable : false
		}, {
			name : 'customerNo',
			index : 'customerNo',
			width : 140,
			align : 'left',
			sortable : false
		}, {
			name : 'fundType',
			hidden : true
		}, {
			name : 'fundTypeView',
			index : 'fundTypeView',
			width : 100,
			align : 'left',
			sortable : false
		},  {
			name : 'availableBalance',
			index : 'availableBalance',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'frozenAmount',
			index : 'frozenAmount',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'sumTotalAmount',
			index : 'sumTotalAmount',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'totalBalance',
			index : 'totalBalance',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'state',
			hidden : true
		}, {
			name : 'stateView',
			index : 'stateView',
			width : 100,
			align : 'left',
			sortable : false
		} ],
		height : size.height - 50,
		width : size.width,
		multiselect : true,
		shrinkToFit : false,
		pager : '#accountInfoTblPagination',
		toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#accountInfoTbl').jqGrid(option);
	$("#t_accountInfoTbl").append($('#accountInfoTblToolbar'));
}

//查询账户列表
function findAccountInfo(defaultPageNo) {
	var aType = $('#merType').val();
	var fundType = '';
	fundType = $('#fundType').combobox('getValue');
	expConfInfo.typeSelStr = "AccountMerInfo";//导出对象
	var obj = {
		custNum : $('#custNum').val(),
		custName : $('#custName').val(),
		aType : aType,//商户类型0-个人;1-企业
		status : $('#states').combobox('getValue'),
		fundType : fundType,
		page : getJqgridPage('accountInfoTbl', defaultPageNo)
	};
	ddpAjaxCall({
		url : "findAccountInfoByPage",
		data : obj,
		successFn : function(data) {
			if ("000000" === data.code) {
				loadJqGridPageData($('#accountInfoTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

//查看详细
function findAccountInfoByCode() {
	var rowData = getSelectedRowDataFromMultiples('accountInfoTbl');
	if (rowData != null) {
		var acid = rowData.acid;
		ddpAjaxCall({
			url : "findAccountInfoByCode",
			data : acid,
			successFn : function(data){
				if ("000000" === data.code) {
					loadAccountInfo(data);
				} else {
					msgShow(systemWarnLabel, data.message, 'warning');
				}
			}
		});
	}
}

//停用、启用
function operateAccountById(oper) {
	var selarrrow = $("#accountInfoTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
//		//校验状态
//		for ( var i = 0; i < selarrrow.length; i++) {
//			var rowState = $('#accountInfoTbl').getRowData(selarrrow[i]).state;
//			if(1 === oper){//停用操作
//				if('1' === rowState){
//					msgShow(systemWarnLabel, '已停用的账户无法停用', 'warning');
//					return;
//				}
//			}else if(0 === oper){
//				if('0' === rowState){
//					msgShow(systemWarnLabel, '已启用的账户无法启用', 'warning');
//					return;
//				}
//			}
//		}
		$.messager.confirm(systemConfirmLabel, "确定要对这" + selarrrow.length + "条进行操作吗？", function(r) {
			if(r) {
				var operation = {
					oper : oper,
					fundAccountIds : selarrrow
				}
				ddpAjaxCall({
					url : "operateAccountById",
					data : operation,
					successFn : function(data){
						if("000000" === data.code) {
							findAccountInfo();
						} else {
							msgShow(systemWarnLabel, data.message, 'warning');
						}
					}
				});
			}
		});
	}else{
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

//查看资金变动记录
function viewChangeById() {
	var rowData = getSelectedRowDataFromMultiples('accountInfoTbl');
	if(rowData){
		var acid = rowData.acid;
		var changefundType = rowData.fundType;
		showChange(acid,changefundType);
	}
}

//初始化账户变动记录界面
function initChangeDialog() {
	$('#accountChangeDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 1050,
		height : 380
	});
	
	$('#changeType').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false
	});
}

function showChange(acid,changefundType) {
	clearAllText('merchantQueryCondition')
	$('#acid').val(acid);
	$('#changefundType').val(changefundType);
	showDialog('accountChangeDialog');
	findAccountChange();
}

//变动类型下拉框ajax
function findchangeType() {
	ddpAjaxCall({
		url : "findchangeType",
		successFn : function(data) {
				addTipsOption(data);
				reLoadComboboxData($('#changeType'),data);
				$('#changeType').combobox('select', '');
		}
	});
}

//变动记录jqgrid
function initChangeListTbl() {
	var option = {
		datatype : function(pdata) {
			findAccountChange();
		},
		//初始化不加载数据,设置jqgrid数据类型为local
//		datatype : 'local',
		colNames : [ '资金账户号', '资金类别', '时间戳', '交易流水号', '变动类型', '变动金额(元)', '变动前账户总余额(元)', '变动前可用余额(元)','变动前冻结金额(元)', '变动日期' ],
		colModel : [ {
			name : 'fundAccountCode',
			hidden : true
		}, {
			name : 'fundType',
			index : 'fundType',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'accountChangeTime',
			index : 'accountChangeTime',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'tranCode',
			index : 'tranCode',
			width : 160,
			align : 'left',
			sortable : false,
			key : true
		}, {
			name : 'changeType',
			index : 'changeType',
			width : 60,
			align : 'left',
			sortable : false
		}, {
			name : 'changeAmount',
			index : 'changeAmount',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'beforeChangeAmount',
			index : 'beforeChangeAmount',
			width : 120,
			align : 'left',
			sortable : false
		}, {
			name : 'beforeChangeAvailableAmount',
			index : 'beforeChangeAvailableAmount',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'beforeChangeFrozenAmount',
			index : 'beforeChangeFrozenAmount',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'changeDate',
			hidden : true
		} ],
		width : $('#accountChangeDialog').width() - 2,
		height : $('#accountChangeDialog').height() - 108,
		pager : '#accountChangeListTblPagination'
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#accountChangeListTbl').jqGrid(option);
	$("#t_accountChangeListTbl").append($('#accountChangeDialogToolbar'));
}

//查询变动列表
function findAccountChange(defaultPageNo) {
	var acid = $('#acid').val();
	if (acid != '' && acid) {
		if(isValidate('accountChangeDialog')){
			var amtMin = parseInt(parseFloat($('#amtFrom').val()) * 100);
			var amtMax = parseInt(parseFloat($('#amtTo').val()) * 100);
			if(amtMin > amtMax){
				msgShow(systemWarnLabel, '交易金额最小值不能大于交易金额最大值', 'warning');
				return;
			}
			var metType = $('#merType').val();
			var fundType = '';
			if('1' === metType){
				fundType = $('#changefundType').val();
			}
			var obj = {
					acid : $('#acid').val(),
					fundType : fundType,
					changeType : $('#changeType').combobox('getValue'),
					startDate : $('#createDateStart').val(),
					endDate : $('#createDateEnd').val(),
					changeAmountMin : amtMin,
					changeAmountMax : amtMax,
					page : getJqgridPage('accountChangeListTbl', defaultPageNo)
			};
			ddpAjaxCall({
				url : "findFundsChangeRecordsByPage",
				data : obj,
				successFn : function(data) {
					if ("000000" === data.code) {
					//设置jggrid数据类型为json
//					$("#accountChangeListTbl").jqGrid('setGridParam',{datatype:'json',page:defaultPageNo}).trigger("reloadGrid");
						loadJqGridPageData($('#accountChangeListTbl'), data.responseEntity);
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
		}
	}
}

//展示详情
function loadAccountInfo(data) {
	var entity = data.responseEntity;
	var accountBean = entity.accountBean;
	var accountInfoFundBeans = entity.accountInfoFundBeans;
	var accountControlBeans = entity.accountControlBeans;
	
	//主账户信息start
	var fundType = '';
	if('0' === accountBean.fundType){
		fundType = '资金账户';
	} else if ('1' === accountBean.fundType){
		fundType = '授信账户';
	}
	var customerType = '';
	if('0' === accountBean.customerType){
		customerType = '个人';
	} else if ('1' === accountBean.customerType){
		customerType = '商户';
	}
	$('#accountCodeView').html(accountBean.accountCode);
	$('#fundTypeView').html(fundType);
	$('#customerTypeView').html(customerType);
	$('#customerNoView').html(accountBean.customerNo);
	$('#accCreateUserView').html(accountBean.createUser);
	$('#accCreateDateView').html(formatDate(accountBean.createDate,'yyyy-MM-dd HH:mm:ss'));
	$('#accUpdateUserView').html(accountBean.updateUser);
	$('#accUpdateDateView').html(formatDate(accountBean.updateDate,'yyyy-MM-dd HH:mm:ss'));
	//主账户信息end
	
	//资产授信start
	if(accountInfoFundBeans != null){
		$("#displayFundTbl tr").remove();
		var lines = "";
		$.each(accountInfoFundBeans, function(index, fundBean) {
			var fType = '';
			if('0' === fundBean.fundType){
				fType = '资金账户';
			} else if ('1' === fundBean.fundType){
				fType = '授信账户';
			}
			var state = '';
			if('0' === fundBean.state){
				state = '正常';
			} else if ('1' === fundBean.state){
				state = '停用';
			}
			lines += "<tr><th>编号:</th>"
			lines += "<td>" + fundBean.fundAccountCode + "</td>";
			lines += "<th>资金类别:</th>"
			lines += "<td>" + fType + "</td></tr>";
			lines += "<tr><th>主账户编号:</th>"
			lines += "<td>" + fundBean.accountCode + "</td>";
			lines += "<th>累计总金额:</th>"
			lines += "<td>" + fundBean.sumTotalAmount + "</td></tr>";
			lines += "<tr><th>总余额:</th>"
			lines += "<td>" + fundBean.totalBalance + "</td>";
			lines += "<th>可用金额:</th>"
			lines += "<td>" + fundBean.availableBalance + "</td></tr>";
			lines += "<tr><th>冻结金额:</th>"
			lines += "<td>" + fundBean.frozenAmount + "</td>";
			lines += "<th>最近一次变动金额:</th>"
			lines += "<td>" + fundBean.lastChangeAmount + "</td></tr>";
			lines += "<tr><th>变动前账户总余额:</th>"
			lines += "<td>" + fundBean.beforeChangeTotalAmount + "</td>";
			lines += "<th>变动前可用余额:</th>"
			lines += "<td>" + fundBean.beforeChangeAvailableAmount + "</td></tr>";
			lines += "<tr><th>变动前冻结金额:</th>"
			lines += "<td>" + fundBean.beforeChangeFrozenAmount + "</td>";
			lines += "<th>状态:</th>"
			lines += "<td>" + state + "</td></tr>";
			lines += "<tr><th>创建人:</th>"
			lines += "<td>" + fundBean.createUser + "</td>";
			lines += "<th>创建时间:</th>"
			lines += "<td>" + formatDate(fundBean.createDate,'yyyy-MM-dd HH:mm:ss') + "</td></tr>";
			lines += "<tr><th>编辑人:</th>"
			lines += "<td>" + fundBean.updateUser + "</td>";
			lines += "<th>编辑时间:</th>"
			lines += "<td>" + formatDate(fundBean.updateDate,'yyyy-MM-dd HH:mm:ss') + "</td></tr>";
			lines += "<tr><th>&nbsp</th></tr>";
		});
		$('#displayFundTbl').append(lines);
	}
	//资产授信end
	
	//资金账户风控start
	if(accountControlBeans != null){
		$("#displayCtrlTbl tr").remove();
		var lines = "";
		$.each(accountControlBeans, function(index, ctrlBean) {
			lines += "<tr><th>资金授信账户号:</th>"
			lines += "<td>" + ctrlBean.fundAccountCode + "</td>";
			lines += "<th>日消费交易单笔限额(元):</th>"
			lines += "<td>" + ctrlBean.dayConsumeSingleLimit + "</td></tr>";
			lines += "<tr><th>日消费交易累计限额(元):</th>"
			lines += "<td>" + ctrlBean.dayConsumeSumLimit + "</td>";
			lines += "<th>日充值交易单笔限额(元):</th>"
			lines += "<td>" + ctrlBean.dayRechargeSingleLimit + "</td></tr>";
			lines += "<tr><th>日充值交易累计限额(元):</th>"
			lines += "<td>" + ctrlBean.dayRechargeSumLimit + "</td>";
			lines += "<th>日转账交易最大次数:</th>"
			lines += "<td>" + ctrlBean.dayTransferMax + "</td></tr>";
			lines += "<tr><th>日转账交易单笔限额(元):</th>"
			lines += "<td>" + ctrlBean.dayTransferSingleLimit + "</td>";
			lines += "<th>日转账交易累计限额(元):</th>"
			lines += "<td>" + ctrlBean.dayTransferSumLimit + "</td></tr>";
			lines += "<tr><th>创建人:</th>"
			lines += "<td>" + ctrlBean.createUser + "</td>";
			lines += "<th>创建时间:</th>"
			lines += "<td>" + formatDate(ctrlBean.createDate,'yyyy-MM-dd HH:mm:ss') + "</td></tr>";
			lines += "<tr><th>编辑人:</th>"
			lines += "<td>" + ctrlBean.updateUser + "</td>";
			lines += "<th>编辑时间:</th>"
			lines += "<td>" + formatDate(ctrlBean.updateDate,'yyyy-MM-dd HH:mm:ss') + "</td></tr>";
			lines += "<tr><th>&nbsp</th></tr>";
		});
		$('#displayCtrlTbl').append(lines);
	}
	//资金账户风控end
	$('#viewAccountInfoDialog').show().dialog('open');
}

function closeViewAccountInfo(){
	$('#viewAccountInfoDialog').show().dialog('close');
}

var expConfInfo = {
	"btnId" : "btnSelExcCol", /* must */// the id of export btn in ftl
	"toUrl" : "exportAccountInfo" /* must */// the export url
};
var filterConStr = [    // filter the result by query condition\
    {'name':'custNum','value': "$('#custNum').val()"},
    {'name':'custName','value': "$('#custName').val()"},
    {'name':'aType','value': "$('#merType').val()"},
    {'name':'status','value': "$('#states').combobox('getValue')"},
    {'name':'fundType','value': "$('#fundType').combobox('getValue')"}
];
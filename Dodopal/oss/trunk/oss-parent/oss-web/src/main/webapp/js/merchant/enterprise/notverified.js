$(function() {
	initMainComponent();
	parent.closeGlobalWaitingDialog();
	//导出列表 2015-12-21
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
	$('#proCityQuery').append(createAreaComponent2('proCityQuery'));
	initAreaComponent('proCityQuery', globalAreaComboboxWidth);
	autoResize({
		dataGrid : {
			selector : '#merNotverifiedTbl',
			offsetHeight : 90,
			offsetWidth : 0
		},
		callback : initMerNotverifiedTbl,
		toolbar : [ true, 'top' ]
	});
}

function initMerNotverifiedTbl(size) {
	var option = {
			datatype : function (pdata) {
				findNotverifiedMerchantUsers();
		    },
	    colNames : ['ID','商户编码','商户名称','上级商户名称','联系人','手机号码','merPro', 'merCity', '省份','城市'],
		colModel : [ { name : 'id', hidden : true},
		             { name : 'merCode', hidden : true},
		             { name : 'merName', index : 'merName', width : 100, align : 'left', sortable : false },
		             { name : 'merParentName', index : 'merParentName', width : 100, align : 'left', sortable : false },	
		             { name : 'merLinkUser', index : 'merLinkUser', width : 100, align : 'left', sortable : false },
		             { name : 'merLinkUserMobile', index : 'merLinkUserMobile', width : 100, align : 'left', sortable : false },
		             { name : 'merPro', index : 'merPro', hidden:true },
		             { name : 'merCity', index : 'merCity', hidden:true},
		             { name : 'merProName', index : 'merProName', width : 100, align : 'left', sortable : false },
		             { name : 'merCityName', index : 'merCityName', width : 100, align : 'left', sortable : false }
				],
		height : size.height - 70,
		width : size.width,
		multiselect: true,
		pager : '#merNotverifiedTblPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#merNotverifiedTbl').jqGrid(option);
	 $("#t_merNotverifiedTbl").append($('#merNotverifiedTblToolbar'));
}





function clearForm() {
	$('#userId').val('');
	$('#activate').removeAttr("checked");
	$('#name').val('');
	$('#loginName').val('');
	$('#password').val('');
	$('#password2').val('');
	$('#roleTree').empty();
	showViewUserDialog();
	$('#merName').html("123");
}
//审核不通过商户信息
function findNotverifiedMerchantUsers(defaultPageNo) {
	//省份
	var merProQuery = getProvinceCodeFromCompoent('proCityQuery'); 
	//城市
	var merCityQuery = getCityCodeFromCompoent('proCityQuery'); 
	ddpAjaxCall({
		url : "findMerchantsPage",
		data : {
			merName : escapePeculiar($.trim($('#merNameQuery').val())),
			merParentName : escapePeculiar($.trim($('#merParentNameQuery').val())),
			merLinkUserMobile : escapePeculiar($.trim($('#merLinkUserMobileQuery').val())),
			merCity : merCityQuery,
			merPro : merProQuery,
			merState : '2',
			page : getJqgridPage('merNotverifiedTbl', defaultPageNo)
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#merNotverifiedTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

//操作成功后返回界面
function afterUpNotMerchant(data) {
	if(data.code == "000000") {
		findNotverifiedMerchantUsers();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
//删除审核不通过商户信息详情
function delNotverifMerchant(){
	var selarrrow = $("#merNotverifiedTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要删除商户信息吗？", function(r) {
			if (r) {
				var merCodes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#merNotverifiedTbl").getRowData(selarrrow[i]);
					merCodes.push(rowData.merCode);
				}
				ddpAjaxCall({
					url : "delNotverifMerchant",
					data : merCodes,
					successFn : afterUpNotMerchant
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}	
}

function afterDeleteUser(data) {
	if(data.code == "000000") {
		findMerchantUsers();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

//查看详情
function viewNotverifiedMerchantUser(){
	var selarrrow = $("#merNotverifiedTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#merNotverifiedTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#merNotverifiedTbl').getRowData(selrow);
		var merCode = rowData.merCode;
		ddpAjaxCall({
			url : "findMerchantByCode",
			data : merCode,
			successFn : loadViewMerchants
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function showViewMerchant(){
	$('#viewMerchantDialog').show().dialog('open');
}
function clearTableData(){
	$('#merNameView').html("");
	$('#merTypeView').html("");
	$('#merClassifyView').html("");
	$('#merPropertyView').html("");
	$('#fundTypeView').html("");
	$('#merLinkUserView').html("");
	$('#merUserNameView').html("");
	$('#merLinkUserMobileView').html("");
	$('#activateView').html("");
	$('#prdRateTypeView').html("");
	$('#merBusinessScopeNameView').html("");
	
	$('#merUserSexView').html("");
	$('#merUserTelephoneView').html("");
	$('#merFaxView').html("");
	$('#merUserIdentityTypeView').html("");
	$('#merUserIdentityNumberView').html("");
	$('#merUserEmailView').html('');
	$('#merZipView').html('');
	$('#merBankNameView').html('');
	$('#merBankAccountView').html('');
	$('#merBankUserNameView').html('');
	$('#merDdpLinkIpView').html('');
	$('#merParentTypeView').html('');
	$('#merParentNameView').html('');
	$('#merDdpLinkUserView').html('');
	$('#merDdpLinkUserMobileView').html('');
	
	$('#merUserRemarkView').html('');
	$('#merDdpLinkIpView').html('');
	$('#sourceView').html('');
	$('#createUserView').html('');
	$('#createDateView').html('');
	$('#merStateUserView').html('');
	$('#merStateDateView').html('');
	$('#updateUserView').html('');
	$('#updateDateView').html('');
}
function closeViewMerchant(){
	$('#viewMerchantDialog').show().dialog('close');
}

function loadViewMerchants(data){
	if(data.code=="000000"){
		showViewMerchant();
		clearTableData();
		var merchantBeans = data.responseEntity;
			//用户信息
		var merchantUserBean = merchantBeans.merchantUserBean;
		//费率信息
		var merRateSpmtList = merchantBeans.merRateSpmtList;
		// 商户补充信息表
		var merDdpInfoBean = merchantBeans.merDdpInfoBean;
		  /*自动分配额度*/
		var merAutoAmtBean = merchantBeans.merAutoAmtBean;
		
		$('#merNameView').html(merchantBeans.merName);
		$('#merTypeView').html(merchantBeans.merTypeView);
		$('#merClassifyView').html(merchantBeans.merClassifyView);
		$('#merPropertyView').html(merchantBeans.merPropertyView);
		$('#fundTypeView').html(merchantBeans.fundTypeView);
		$('#merLinkUserView').html(merchantBeans.merLinkUser);
		$('#merUserNameView').html(merchantUserBean.merUserName);
		$('#merLinkUserMobileView').html(merchantBeans.merLinkUserMobile);
		$('#activateView').html(merchantBeans.activateView);
		$('#merAddsView').html(merchantBeans.merProName+merchantBeans.merCityName+"市"+merchantBeans.merAdds);
			//业务类型
		var rateCodeView ='';
		$(merRateSpmtList).each(function(i,v){
		if(rateCodeView != '') {
				rateCodeView += ',';
			}else{
				rateCodeView='';
			}
			rateCodeView += v.rateCodeView;
		});
		if(rateCodeView!=null && rateCodeView!='undefined'){
			$('#prdRateTypeView').html(rateCodeView);
		}
		
		$('#merBusinessScopeNameView').html(merchantBeans.merBusinessScopeIdView);
		$('#merUserSexView').html(merchantUserBean.merUserSexView);
		$('#merUserTelephoneView').html(merchantBeans.merTelephone);
		$('#merFaxView').html(merchantBeans.merFax);
		$('#merUserIdentityTypeView').html(merchantUserBean.merUserIdentityTypeView);
		$('#merUserIdentityNumberView').html(merchantUserBean.merUserIdentityNumber);
		$('#merUserEmailView').html(merchantBeans.merEmail);
		$('#merZipView').html(merchantBeans.merZip);

		$('#merBankNameView').html(merchantBeans.merBankNameView);
		$('#merBankAccountView').html(merchantBeans.merBankAccount);
		$('#merBankUserNameView').html(merchantBeans.merBankUserName);
		$('#merDdpLinkIpView').html(merchantBeans.merDdpLinkIp);
		
		$('#merParentTypeView').html(merchantBeans.merParentTypeView);
		$('#merParentNameView').html(merchantBeans.merParentName);
		
		$('#merDdpLinkUserView').html(merchantBeans.merDdpLinkUser);
		$('#merDdpLinkUserMobileView').html(merchantBeans.merDdpLinkUserMobile);
		$('#merDdpLinkIpView').html(merchantBeans.merDdpLinkIp);
		loadYkt(merchantBeans.merBusRateBeanList);
		$('#sourceView').html(merchantBeans.sourceView);
		$('#createUserView').html(merchantBeans.createUser);
		$('#createDateView').html(merchantBeans.createDateView);
		$('#merStateUserView').html(merchantBeans.merStateUser);
		$('#merStateDateView').html(ddpDateFormatter(merchantBeans.merStateDate));
		$('#updateUserView').html(merchantBeans.updateUser);
		$('#updateDateView').html(merchantBeans.updateDateView);
		// ----------------新增字段---------------
		// 商户补充信息表
		/* 都都宝固定IP */
		$('#merDdpLinkIpView').html(merDdpInfoBean.merDdpLinkIp);
		/* 都都宝联系人 */
		$('#merDdpLinkUserView').html(merDdpInfoBean.merDdpLinkUser);
		/* 都都宝联系人电话 */
		$('#merDdpLinkUserMobileView').html(merDdpInfoBean.merDdpLinkUserMobile);
		/* 结算类型 */
		$('#settlementTypeView').html(merDdpInfoBean.settlementTypeView);
		var settlementThresholdView = "";
		if(merDdpInfoBean.settlementType=='0'){
			settlementThresholdView = merDdpInfoBean.settlementThreshold+'天';
		}else if(merDdpInfoBean.settlementType=='1'){
			settlementThresholdView = merDdpInfoBean.settlementThreshold+'笔';
		}else if(merDdpInfoBean.settlementType=='2'){
			settlementThresholdView = merDdpInfoBean.settlementThreshold/100+'元';
		}
		/* 结算阀值 */
		$('#settlementThresholdView').html(settlementThresholdView);
		// 商户用户表
		/* 学历 */
		$('#educationView').html(merchantUserBean.educationView);
		/* 收入 */
		if(merchantUserBean.income!=null){
			$('#incomeView').html(Number(merchantUserBean.income/100)+"元");
		}else{
			$('#incomeView').html(merchantUserBean.income);
		}
		
		/* 生日，格式：1990-09-10 */
		$('#birthdayView').html(merchantUserBean.birthday);
		/* 是否已婚，0是，1否 */
		$('#isMarriedView').html(merchantUserBean.isMarriedView);
		/* 商圈 */
		$('#tradeAreaView').html(merDdpInfoBean.tradeArea);
		
		
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

//加载通卡公司费率
function loadYkt(merBusRateBeanList) {
	var tkgsNameView = '';
	$('#yktTableTbodyView').html('');
	$('#tkgsNameView').html('');
	var html = '';
	if(merBusRateBeanList && merBusRateBeanList.length > 0){
		$('#yktView').show();
		var proNameArray = new Array();
		$(merBusRateBeanList).each(function(i,v){
			html += '<tr>';
			html +='<td class="nobor">&nbsp;</td>';

			html += '<td>';
			html += v.proName;

			var isFound = false;
			for (var i = 0; i < proNameArray.length; i++) {
				if (proNameArray[i] == v.proName) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				tkgsNameView += v.proName;
				tkgsNameView += ',';
				proNameArray.push(v.proName);
			}

			html += '</td>';

			html += '<td>';
			html += v.rateName;
			html += '</td>';

			html += '<td>';
			html += v.rateTypeView;
			html += '</td>';

			html += '<td>';
			html += v.rate;
			html += '</td>';

			html +='<td class="nobor">&nbsp;</td>';
			html += '</tr>';

		});
		$('#yktTableTbodyView').html(html);
		tkgsNameView=tkgsNameView.substring(0,tkgsNameView.length-1);
		$('#tkgsNameView').html(tkgsNameView);
	}else{
		$('#yktView').hide();
	}
}

//业务费率名称
function busRateType(rateType){
	if(rateType=='1'){
		return '单笔返点金额(元)';
	}
	if(rateType=='2'){
		return '千分比(‰)';
	}
}
/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "exportNotverifiedMgmt", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "MerchantNotUnauditedExpBean", 	/*must*/// type of export
	"toUrl"			: "exportExcelNotverified", 			/*must*/// the export url
	"parDiaHeight"  : "400"
};
var filterConStr = [	// filter the result by query condition
		{'name':'merNameQuery', 	'value': "escapePeculiar($.trim($('#merNameQuery').val()))"			},	// eval
		{'name':'merParentNameQuery',	'value': "escapePeculiar($.trim($('#merParentNameQuery').val()))"		},
		{'name':'merLinkUserMobile',	'value': "escapePeculiar($.trim($('#merLinkUserMobileQuery').val()))"		},
		{'name':'merProQuery',	'value': "escapePeculiar(getProvinceCodeFromCompoent('proCityQuery'))"		},
		{'name':'merCityQuery',	'value': "escapePeculiar(getCityCodeFromCompoent('proCityQuery'))"		},
		{'name':'merState',	'value': "escapePeculiar('2')"		}
	];


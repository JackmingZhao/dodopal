$(function() {
	initMainComponent();
	initDetailComponent();
	//导出列表 2015-12-24 JOE
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	parent.closeGlobalWaitingDialog();
});
var notToStopMessage="商户用户为管理员用户，无法停启用";
function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#dataTbl',
			offsetHeight :90,
			offsetWidth : 0
		},
		callback : initDataTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'dataDialog' ]
	});
	loadMerUserIdentityType();
	loadEducation();
	loadIsMarried();
	loadMerchantType();
}
function closeEdit(){
	cancelAction('editMerchantUser');
}
function initDetailComponent() {
	initDataDialog();
	initIsNotNumber();
}
//监听输入值是否为数字或者带小数点后两位的
function initIsNotNumber(){
	//年收入
$("#edIncome").bind('keyup', function() {
		notNumber($(this));
	});
}
//加载证件类型
function loadMerUserIdentityType(){
	$('#edMerUserIdentityType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 137
	});
	var ddic = {
			categoryCode : 'IDENTITY_TYPE'
	}
	loadDdics(ddic, loadMerUserIdentityTypes);
}
function loadMerUserIdentityTypes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#edMerUserIdentityType'), data.responseEntity)
		$('#edMerUserIdentityType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


//加载商户类型
function loadMerchantType(){
	$('#merchantType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
	});
	var ddic = {
			categoryCode : 'MER_TYPE'
	}
	loadDdics(ddic, loadMerchantTypeEntity);
}
function loadMerchantTypeEntity(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#merchantType'), data.responseEntity)
		$('#merchantType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


//加载学历
function loadEducation(){
	$('#edEducation').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 137
	});
	var ddic = {
			categoryCode : 'EDUCATION_TYPE'
	}
	loadDdics(ddic, loadEducations);
}
function loadEducations(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#edEducation'), data.responseEntity)
		$('#edEducation').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

//加载婚否
function loadIsMarried(){
	$('#edIsMarried').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 137
	});
	var ddic = {
			categoryCode : 'IS_MARRIED_TYPE'
	}
	loadDdics(ddic, loadIsMarrieds);
}
function loadIsMarrieds(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#edIsMarried'), data.responseEntity)
		$('#edIsMarried').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function initDataTbl(size) {
	var option = {
			datatype : function (pdata) {
				findData();
		    },
		colNames : [ 'userId','用户名', '联系人', '所属商户名称','手机号码', '商户类型', "省份","城市",'用户标志',"merUserFlagHide","审核状态","启用标识","来源","开户时间"],
		colModel : [ 
		{
			name : 'userId',
			index : 'id',
			hidden : true,
			frozen:true
		}, {
			name : 'merUserName',
			index : 'merUserName',
			width : 150,
			align : 'left',
			sortable : false,
			frozen:true
		},{
			name : 'merUserNickName',
			index : 'merUserNickName',
			width : 150,
			align : 'left',
			sortable : false
		}, 
		{
			name : 'merchantName',
			index : 'merchantName',
			width : 200,
			align : 'left',
			sortable : false
		},{
			name : 'merUserMobile',
			index : 'merUserMobile',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'merchantTypeView',
			index : 'merchantTypeView',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'merUserProName',
			index : 'merUserProName',
			width : 70,
			align : 'left',
			sortable : false
			},{
			name : 'merUserCityName',
			index : 'merUserCityName',
			width : 70,
			align : 'left',
			sortable : false}
		, {
			name : 'merUserFlagView',
			index : 'merUserFlagView',
			width : 80,
			align : 'left',
			sortable : false
		},{
			name : 'merUserFlag',
			index : 'merUserFlagHide',
			hidden : true
		},{
			name : 'merchantStateView',
			index : 'merchantStateView',
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'activateView',
			index : 'activateView',
			width : 50,
			align : 'left',
			sortable : false
		}, {
			name : 'merUserSourceView',
			index : 'merUserSourceView',
			width : 70,
			align : 'left',
			sortable : false
		},{
			name : 'createDate',
			index : 'createDate',
			width : 150,
			align : 'left',
			sortable : false,
			formatter: cellDateFormatter
		
		}],
		//caption : "用户列表",
		//sortname : 'name',
		height : size.height - 100,
		multiselect: true,
		width : size.width,
		autowidth : true,
        shrinkToFit : false,
		pager : '#dataTblPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#dataTbl').jqGrid(option);
	 $("#dataTbl").jqGrid('setFrozenColumns');
	$("#t_dataTbl").append($('#dataTblToolbar'));
}
function initDataDialog() {
	$('#dataDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function showDataDialog() {
	$('#dataDialog').show().dialog('open');
}

function hideDataDialog() {
	clearForm();
	clearAllText('editMerchantUser');
	$('#dataDialog').hide().dialog('close');
}
function showViewMerchant(){
	$('#viewMerchantUserDialog').show().dialog('open');
}
function closeViewMerchant(){
	$('#viewMerchantUserDialog').show().dialog('close');
}

//查看用户详情
function viewMerchantUser(){
	var selarrrow = $("#dataTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
    var selrow = $("#dataTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#dataTbl').getRowData(selrow);
		ddpAjaxCall({
			url : "viewMerUser",
			data : rowData.userId,
			successFn : loadViewMerchants
		});
		//window.location.href ="viewMerUser.htm?userId="+rowData.userId;
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function loadViewMerchants(data){
	if(data.code=="000000"){
		showViewMerchant();
		var merchantBeans = data.responseEntity;
		$('#merchantNameView').html(merchantBeans.merchantName);
		$('#merUserNameView').html(merchantBeans.merUserName);
		$('#merUserNickNameView').html(merchantBeans.merUserNickName);
		$('#merUserMobileView').html(merchantBeans.merUserMobile);
		$('#activateView').html(merchantBeans.activateView);
		$('#merUserFlagView').html(merchantBeans.merUserFlagView);
		$('#merUserIdentityTypeView').html(merchantBeans.merUserIdentityTypeView);
		$('#merUserIdentityNumberView').html(merchantBeans.merUserIdentityNumber);
		$('#merUserEmailView').html(merchantBeans.merUserEmail);
		//$('#merUserRemarkView').html(htmlTagFormat(merchantBeans.merUserRemark));
		$('#merUserRemarkView').text(merchantBeans.merUserRemark==null ? "" : merchantBeans.merUserRemark);
		$('#merUserSexView').html(merchantBeans.merUserSexView);
		$('#merUserSourceView').html(merchantBeans.merUserSourceView);
		
		$('#educationView').html(merchantBeans.educationView);
		$('#isMarriedView').html(merchantBeans.isMarriedView);
		$('#birthdayView').html(merchantBeans.birthday);
		//-----省市---
		$('#MerUserProName').html(merchantBeans.merUserProName);
		$('#MerUserCityName').html(merchantBeans.merUserCityName);
		if(isBlank(merchantBeans.income)){
			$('#incomeView').html("");
		}else{
			$('#incomeView').html(Number(merchantBeans.income/100)+"元");
		}
		
		$('#merUserAddsView').html(merchantBeans.merUserAdds);
		
		$('#createDateView').html(merchantBeans.createDateView);
		$('#updateUserView').html(merchantBeans.updateUser);
		$('#updateDateView').html(merchantBeans.updateDateView);
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
//重置密码
function resetPwd(){
    var selrow = $("#dataTbl").getGridParam('selrow');
    var selarrrow = $("#dataTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel,onlyAllowOneSelectLabel, 'warning');
		return;
	}
	if (selrow) {
		var rowData = $('#dataTbl').getRowData(selrow);
		if(rowData.merchantStateView!="审核通过"){
			msgShow(systemWarnLabel,"该用户审核不通过或未审核", 'warning');
			return;
		}
		$.messager.confirm(systemConfirmLabel,"系统将随机生成一个密码，重置后将以短信形式发送到该用户手机中，您确定重置此用户密码吗？<table><tr><th>用户名</th><td>"+rowData.merUserName+"</td></tr><tr><th>手机号码</th><td>"+rowData.merUserMobile+"</td></tr></table>", function(r) {
			if (r) {
				var userId = rowData.userId;
			ddpAjaxCall({
				url : "resetPwd",
				data : userId,
				successFn : function(data) {
					if (data.code == "000000") {
						msgShow(systemWarnLabel, "操作成功！", 'warning');//TODO msg data.message
					}else{
						msgShow(systemWarnLabel, data.message , 'warning');  //TODO msg data.message
					}	
				}
			});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function afterAddUser(data) {
	if(data.code == "000000") {
		hideDataDialog();
		findData(1);
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function afterDeleteUser(data) {
	if(data.code == "000000") {
		msgShow(systemWarnLabel, "操作成功", 'warning');
		findData();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function findData(defaultPageNo) {
	var merUser = {
	    merUserType : '1',
	    merUserName : escapePeculiar($.trim($('#merUserName').val())),
		merUserMobile : escapePeculiar($.trim($('#merUserMobile').val())),
		merUserNickName : escapePeculiar($.trim($('#merUserNickName').val())),
		merUserSex : $('#merUserSex').val(),
		merUserFlag : $('#merUserFlag').combobox('getValue'),
		merchantName:escapePeculiar($.trim($('#merchantName').val())),
		merchantState:$('#merchantState').combobox('getValue'),
		merchantType:$('#merchantType').combobox('getValue'),
		activate:$('#activate').combobox('getValue'),
		page : getJqgridPage('dataTbl', defaultPageNo),
		createDateStart : $('#createDateStart').val(),
		createDateEnd : $('#createDateEnd').val(),
		merUserSource : $('#merUserSource').combobox('getValue')
	}

	ddpAjaxCall({
		url : "findMerUsersByPage",
		data : merUser,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#dataTbl'), data.responseEntity);
			}else{
				msgShow(systemWarnLabel, data.message, 'warning');
			}	
		}
	});
}

function resetDataQuery() {	
	$('#findTB').find('input').each(function(){
      $(this).val('');
    });
	$('#findTB').find('select').each(function(){
	      $(this).find('option:first').attr('selected','selected');
	});
}

function disableUser() {
	var selrow = $("#dataTbl").getGridParam('selrow');
	if (selrow) {
		var user = $('#dataTbl').getRowData(selrow);
		$.messager.confirm(systemConfirmLabel, "确定要停用用户信息吗？", function(r) {
			if (r) {
				ddpAjaxCall({
					url : "disableUser",
					data : user.userId,
					successFn : afterDeleteUser
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function startUser() {
	var selarrrow = $("#dataTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		for(var i=0; i<selarrrow.length; i++) {
			//var user = $('#dataTbl').getRowData(selrow);
			var rowData = $("#dataTbl").getRowData(selarrrow[i]);
			if(rowData.merUserFlag==1000){
				msgShow(systemWarnLabel, notToStopMessage, 'warning');
				return;
			}
		}
		$.messager.confirm(systemConfirmLabel, "确定要启用用户信息吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					//var user = $('#dataTbl').getRowData(selrow);
					var rowData = $("#dataTbl").getRowData(selarrrow[i]);
					ids.push(rowData.userId);
				}
				ddpAjaxCall({
					url : "startOrStopUser?activate=0",
					data : ids,
					successFn : afterDeleteUser
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function stopUser() {
	var selarrrow = $("#dataTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		for(var i=0; i<selarrrow.length; i++) {
			//var user = $('#dataTbl').getRowData(selrow);
			var rowData = $("#dataTbl").getRowData(selarrrow[i]);
			if(rowData.merUserFlag==1000){
				msgShow(systemWarnLabel, notToStopMessage, 'warning');
				return;
			}
		}
		$.messager.confirm(systemConfirmLabel, "确定要停用用户信息吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#dataTbl").getRowData(selarrrow[i]);
					ids.push(rowData.userId);
				}
				ddpAjaxCall({
					url : "startOrStopUser?activate=1",
					data : ids,
					successFn : afterDeleteUser
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function editor(){
    var selrow = $("#dataTbl").getGridParam('selrow');
	if (selrow) {
		var user = $('#dataTbl').getRowData(selrow);
		$.messager.confirm(systemConfirmLabel, "确定要启用用户信息吗？", function(r) {
			if (r) {
				ddpAjaxCall({
					url : "startUser",
					data : user.userId,
					successFn : findData()
				});
				    editMerchantUser();
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
//获取待编辑用户信息
function editMerchantUser() {
	clearAllText('editMerchantUser');
	if($.browser.version == '8.0'){
		$('#edMerUserRemark').css("width","404px");
	}else{
		$('#edMerUserRemark').css("width","410px");
	}
    var selarrrow = $("#dataTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#dataTbl").getGridParam('selrow');
	if (selrow) {
		var user = $('#dataTbl').getRowData(selrow);
		$('#editMerchantUser').show().dialog('open');
		var userId = user.userId;
		ddpAjaxCall({
			url : "editMerUser.htm?userId="+userId,
			data :"",
			successFn : loadMerchantUserCode
		});
		
		$('#userId').val(userId);
		$('#edUserName').val(user.merUserName);
		$('#edmerName').val(user.merchantName);
		if(user.activate=="启用"){
			$("input[name=edActivate]:eq(0)").attr("checked",'checked'); 
		}
		else{
			$("input[name=edActivate]:eq(1)").attr("checked",'checked');
		}
		 $("input[name=edActivate]:eq(0)").attr("disabled",'disabled');
		 $("input[name=edActivate]:eq(1)").attr("disabled",'disabled'); 
		$('#edMerUserFlag').val(user.merUserFlag=='1000'?'管理员':'普通操作员');
		$('#edMerUserNickName').val(user.merUserNickName);
		$('#edMerUserMobile').val(user.merUserMobile);
	}else{
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}


function loadMerchantUserCode(data){
	if("000000" == data.code){
		var merUser = data.responseEntity;
		$('#edMerUserIdentityType').combobox('select',merUser.merUserIdentityType==null?"":merUser.merUserIdentityType);
		
		$('#edEducation').combobox('select',merUser.education==null?"":merUser.education);
		$('#edIsMarried').combobox('select',merUser.isMarried==null?"":merUser.isMarried);
		$('#edBirthday').val(merUser.birthday);
		if(merUser.income!=null&&merUser.income!=""){
		$('#edIncome').val(merUser.income/100);}else{
		$('#edIncome').val(merUser.income);
		}
		
		//$("#edMerUserIdentityType option[value='"+merUser.merUserIdentityType+"']").attr("selected",true);
		$('#edMerUserIdentityNumber').val(merUser.merUserIdentityNumber);
		if("0"==merUser.merUserSex)
			$("input[name=edMerUserSex]:eq(0)").attr("checked",'checked'); 
		else
			$("input[name=edMerUserSex]:eq(1)").attr("checked",'checked');
		
		
		if("0"==merUser.activate){$("input[name=edActivate]:eq(0)").attr("checked",'checked'); }
		else{
			$("input[name=edActivate]:eq(1)").attr("checked",'checked');
		}
			
		$('#edMerUserEmail').val(merUser.merUserEmail);
		$('#edMerUserRemark').val(merUser.merUserRemark);
	}else{
		msgShow(systemWarnLabel, "查询出错", 'warning');
	}
	
}
function mytrims(str)
{
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

//更新用户
function updateUser(){
//	if(mytrims($.trim($('#edMerUserNickName').val()))==""){
//		msgShow(systemWarnLabel, "联系人为必填", 'warning');
//		return ;
//	}
//	if(mytrims($('#edMerUserMobile').val())==""){
//		msgShow(systemWarnLabel, "手机号为必填", 'warning');
//		return ;
//	}
//	if(!testModelValue($('#edMerUserMobile').val())){
//		msgShow(systemWarnLabel, "手机号格式不正确", 'warning');
//		return;
//	}
//	var szReg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ ;
//	if($('#edMerUserEmail').val()!=""&&!szReg.test($('#edMerUserEmail').val())){
//		msgShow(systemWarnLabel, "请输入正确的邮箱！", 'warning');
//		return;
//	}
//	if($('#edMerUserIdentityType').combobox('getValue')!=""){
//		if($.trim($("#edMerUserIdentityNumber").val())==""){
//			msgShow(systemWarnLabel, "请根据所选证件类型填入证件号码！", 'warning');
//			return;
//		}else{
//			
//		}
//	}
	if(isValidate('editMerchantUser')) {
		var income = "";
		if($("#edIncome").val()!=null&&$("#edIncome").val()!=""){
			income =$("#edIncome").val()*100;
		}
		$.messager.confirm(systemConfirmLabel, "确定要保存商户信息吗？", function(r) {
			if (r) {
				var merUserBean = {
						id:$('#userId').val(),
						activate: $("input[name='edActivate']:checked").val(),
						merUserName: $.trim($('#edUserName').val()),
						merUserNickName: $.trim($('#edMerUserNickName').val()),
						merUserMobile:$.trim($('#edMerUserMobile').val()),
						merUserSex:$("input[name='edMerUserSex']:checked").val(),
						merUserEmail:$.trim($('#edMerUserEmail').val()),
						merUserIdentityType:$('#edMerUserIdentityType').combobox('getValue'),
						merUserIdentityNumber:$.trim($('#edMerUserIdentityNumber').val()),
						merUserEmail:$.trim($('#edMerUserEmail').val()),
						merUserRemark:$.trim($('#edMerUserRemark').val()),
						education:$('#edEducation').combobox('getValue'),
						isMarried:$('#edIsMarried').combobox('getValue'),
						birthday:$('#edBirthday').val(),
						income:income
				};
				ddpAjaxCall({
					url : "updateMerUser",
					data : merUserBean,
					successFn : afterUpdate
				});
			}
		});
	}
}
function afterUpdate(data){
	if(data.code == "000000") {
		$('#editMerchantUser').hide().dialog('close');
		findData();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


function toUpdatePwd() {
	
	 var selrow = $("#dataTbl").getGridParam('selrow');
		if (selrow) {
			$('#updatePWDDialog').show().dialog('open');
			var user = $('#dataTbl').getRowData(selrow);
			$('#merNameUp').val(user.merchantName);
			$('#merUserNameUp').val(user.merUserName);
		} else {
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		}
}



function retFindJosn(){
    var str = "";     
    $('#findTB').find('input,select').each(function(){
      $this = $(this);      
      var name = $this.attr('name');
      str = str + name + ' : "' + $this.val() + '",'     
    });
    str = str.substr(0,str.length-1);
    var retJson = '[{ ' + str + ' }]';
    return retJson;
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "exportMerchantList", 					/*must*/// the id of export btn in ftl 
	"typeSelStr"	: "MerchantUserExpBean", 	/*must*/// type of export
	"toUrl"			: "exportExcelMerchantUser", 			/*must*/// the export url
	"parDiaHeight"  : "400"
};
var filterConStr = [	// filter the result by query condition
		{'name':'merUserType', 	'value': "escapePeculiar('1')"			},	// eval
		{'name':'merUserName', 	'value': "escapePeculiar($.trim($('#merUserName').val()))"			},	// eval
		{'name':'merUserMobile',	'value': "escapePeculiar($.trim($('#merUserMobile').val()))"		},
		{'name':'merUserNickName',	'value': "escapePeculiar($.trim($('#merUserNickName').val()))"		},
		{'name':'merUserFlag',	'value': "escapePeculiar($('#merUserFlag').combobox('getValue'))"		},
		{'name':'merchantName',	'value': "escapePeculiar($.trim($('#merchantName').val()))"		},
		{'name':'merchantState',	'value': "escapePeculiar($('#merchantState').combobox('getValue'))"		},
		{'name':'merchantType',	'value': "escapePeculiar($('#merchantType').combobox('getValue'))"		},
		{'name':'activate',	'value': "escapePeculiar($('#activate').combobox('getValue'))"		},
		{'name':'createDateStart',	'value': "escapePeculiar($('#createDateStart').val())"		},
		{'name':'createDateEnd',	'value': "escapePeculiar($('#createDateEnd').val())"		},
		{'name':'merUserSource',	'value': "escapePeculiar($('#merUserSource').combobox('getValue'))"		}
	];

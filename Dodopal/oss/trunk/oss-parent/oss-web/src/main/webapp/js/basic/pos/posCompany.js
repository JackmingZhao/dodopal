$(function() {
	initMainComponent();
	initDetailComponent();
	findPosCompany();
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#companyTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initCompanyTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'companyDialog' ]
	});
}

function initDetailComponent() {
	initCompanyDialog();
}

function initCompanyTbl(size) {
	var option = {
			datatype : function (pdata) {
				findPosCompany();
		    },
		    colNames : [ 'companyId', '厂商编号', '厂商名称', '厂商负责人', '联系电话', '传真', '电子邮箱'],
			colModel : [ 
			             { name : 'companyId', hidden : true }, 
			             { name : 'code', index : 'code', width : 100, align : 'left',sortable : false }, 
			             { name : 'name', index : 'name', width : 100, align : 'left',sortable : false }, 
			             { name : 'charger', index : 'charger', width : 100, align : 'left',sortable : false }, 
			             { name : 'phone', index : 'phone', width : 100, align : 'left',sortable : false }, 
			             { name : 'fax', index : 'fax', width : 100, align : 'left',sortable : false },
			             { name : 'mail', index : 'mail', width : 100, align : 'left',sortable : false }
			             //{ name : 'activate', index : 'activate', width : 100, align : 'left',sortable : false, formatter: activateFormatter }
			            ],
//			caption : "POS厂商",
//			sortname : 'code',
			height : size.height - 50,
			width : size.width,
			multiselect: true,
			pager : '#companyTblPagination',
			toolbar : [ true, "top" ]
		};
		 option = $.extend(option, jqgrid_server_opts);
		 $('#companyTbl').jqGrid(option);
	$("#t_companyTbl").append($('#companyTblToolbar'));
}
function initCompanyDialog() {
	$('#companyDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 300
	});
}

function addPosCompany() {
	destroyValidate('companyDialog');
//	$("input[name=activate]:eq(0)").attr("checked",'checked'); 
//	$("input[name=activate]:eq(0)").removeAttr("disabled",'disabled'); 
//	$("input[name=activate]:eq(1)").removeAttr("disabled",'disabled'); 
	$('#code').removeAttr('disabled');
	clearAllText('companyDialog')
	showDialog('companyDialog');
}

function editPosCompany() {
	$('#code').attr('disabled', true);
	var selarrrow = $("#companyTbl").getGridParam('selarrrow');// 多选
	if (selarrrow) {
		if (selarrrow.length == 1) {
			var rowData = $('#companyTbl').getRowData(selarrrow[0]);
			
			ddpAjaxCall({
				url : "findPosCompanyById",
				data : rowData.companyId,
				successFn : function(data) {
					if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
						loadJsonData2Page('companyDialog', data.responseEntity);
//						if(data.responseEntity.activate=="0") {
//							$("input[name=activate]:eq(0)").attr("checked",'checked'); 
//						} else {
//							$("input[name=activate]:eq(1)").attr("checked",'checked'); 
//						}
//						 $("input[name=activate]:eq(0)").attr("disabled",'disabled');
//						 $("input[name=activate]:eq(1)").attr("disabled",'disabled');
						showDialog('companyDialog');
					} else {
						msgShow(systemWarnLabel, data.message, 'warning');
					}
				}
			});
		} else if (selarrrow.length == 0){
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		} else {
			msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function savePosCompany() {
	if(isValidate('companyDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存厂商信息吗？", function(r) {
			if (r) {
				//var activate = $("input[name='activate']:checked").val();
				var company = {
						id : $('#companyId').val(),
						code : $('#code').val(),
						name : $('#name').val(),
						charger : $('#charger').val(),
						phone : $('#phone').val(),
						fax : $('#fax').val(),
						mail : $('#mail').val(),
						address : $('#address').val(),
						zipCode : $('#zipCode').val(),
						comments : $('#comments').val(),
						activate : 0
				};
				ddpAjaxCall({
					url : "savePosCompany",
					data : company,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('companyDialog');
							if(isBlank(company.id)) {
								findPosCompany(1);
							} else {
								findPosCompany();
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

function deletePosCompany() {
	var selarrrow = $("#companyTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "POS厂商一旦删除将无法恢复, 确定要删除吗？", function(r) {
			if (r) {
				var companys = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#companyTbl").getRowData(selarrrow[i]);
					companys.push(rowData.companyId+","+rowData.code+","+rowData.name);
				}
				ddpAjaxCall({
					url : "deletePosCompany",
					data : companys,
					successFn : function(data){
						if(data.code == "000000") {
							findPosCompany();
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


function findPosCompany(defaultPageNo) {
	ddpAjaxCall({
		url : "findPosCompany",
		data : {
			code : escapePeculiar($('#companyCodeQuery').val()),
			name : escapePeculiar($('#companyNameQuery').val()),
			charger : escapePeculiar($('#companyChargerQuery').val()),
			activate : 0,
			page : getJqgridPage('companyTbl', defaultPageNo)
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#companyTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function activatePosCompany() {
	var selarrrow = $("#companyTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用POS厂商吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#companyTbl").getRowData(selarrrow[i]);
					ids.push(rowData.companyId);
				}
				ddpAjaxCall({
					url : "activatePosCompany",
					data : ids,
					successFn : function(){findPosCompany();}
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function inactivatePosCompany()	{
	var selarrrow = $("#companyTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用POS厂商吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#companyTbl").getRowData(selarrrow[i]);
					ids.push(rowData.companyId);
				}
				ddpAjaxCall({
					url : "inactivatePosCompany",
					data : ids,
					successFn : function(){findPosCompany();}
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

//查看详情
function viewPosCompany() {
	var rowData = getSelectedRowDataFromMultiples('companyTbl');
	if (rowData != null) {
		ddpAjaxCall({
			url : "findPosCompanyById",
			data : rowData.companyId,
			successFn : function(response) {
				showDialog('viewCompanyDialog');
				var htmlKeys = ['address'];
				loadJsonData2ViewPageWithHtmlFormat('viewCompanyDialog', response, htmlKeys);
			}
		});
	}
}

function resetPosCompanyQuery() {
	destroyValidate('companyDialog');
	clearAllText('posCompanyQueryCondition');
}
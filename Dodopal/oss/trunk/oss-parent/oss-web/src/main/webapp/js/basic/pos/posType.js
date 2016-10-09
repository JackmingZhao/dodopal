$(function() {
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#typeTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initTypeTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'typeDialog' ]
	});
}

function initDetailComponent() {
	initTypeDialog();
}

function initTypeTbl(size) {
	var option = {
		datatype : function (pdata) {
			findPosType();
	    },
		colNames : [ 'typeId', '型号编码', '型号名称', '备注', '创建人', '创建时间','编辑人', '编辑时间'],
		colModel : [ 
		             { name : 'typeId', hidden : true }, 
		             { name : 'code', index : 'code', width : 100, align : 'left',sortable : false }, 
		             { name : 'name', index : 'name', width : 100, align : 'left',sortable : false }, 
		            // { name : 'activate', index : 'activate', width : 100, align : 'left',sortable : false, formatter: activateFormatter }, 
		             { name : 'comments', index : 'comments', width : 100, align : 'left',sortable : false,formatter: htmlFormatter },
		             { name : 'createUser', index : 'createUser', width : 100, align : 'left',sortable : false },
		             { name : 'createDate', index : 'createDate', width : 100, align : 'left',sortable : false, formatter: cellDateFormatter },
		             { name : 'updateUser', index : 'updateUser', width : 100, align : 'left',sortable : false },
		             { name : 'updateDate', index : 'updateDate', width : 100, align : 'left',sortable : false, formatter: cellDateFormatter }
		            ],
//		caption : "POS型号",
//		sortname : 'code',
		height : size.height - 50,
		width : size.width,
		multiselect: true,
		pager : '#typeTblPagination',
		toolbar : [ true, "top" ]
	};
	
	 option = $.extend(option, jqgrid_server_opts);
	 $('#typeTbl').jqGrid(option);
	    
	$("#t_typeTbl").append($('#typeTblToolbar'));
}
function initTypeDialog() {
	$('#typeDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 300
	});
}

function addPosType() {
//	$("input[name=activate]:eq(0)").attr("checked",'checked'); 
//	$("input[name=activate]:eq(0)").removeAttr("disabled",'disabled'); 
//	$("input[name=activate]:eq(1)").removeAttr("disabled",'disabled');
	
	$('#code').removeAttr('disabled');
	clearAllText('typeDialog');
	$('#comments').val('');
	showDialog('typeDialog');
}

function editPosType() {
	$('#code').attr('disabled', true);
	var selarrrow = $("#typeTbl").getGridParam('selarrrow');// 多选
	if (selarrrow) {
		if (selarrrow.length == 1) {
			var rowData = $('#typeTbl').getRowData(selarrrow[0]);
			
			ddpAjaxCall({
				url : "findPosTypeById",
				data : rowData.typeId,
				successFn : function(data) {
					if(data.code == '000000' && typeof(data.responseEntity) != 'undefined') {
						loadJsonData2Page('typeDialog', data.responseEntity);
//						if(data.responseEntity.activate=="0") {
//							$("input[name=activate]:eq(0)").attr("checked",'checked'); 
//						} else {
//							$("input[name=activate]:eq(1)").attr("checked",'checked'); 
//						}
//						 $("input[name=activate]:eq(0)").attr("disabled",'disabled');
//						 $("input[name=activate]:eq(1)").attr("disabled",'disabled');
						showDialog('typeDialog');
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

function savePosType() {
	if(isValidate('typeDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存型号信息吗？", function(r) {
			if (r) {
				//var activate = $("input[name='activate']:checked").val();
				var type = {
						id : $('#typeId').val(),
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
					url : "savePosType",
					data : type,
					successFn : function(data) {
						if (data.code == "000000") {
							hideDialog('typeDialog');
							if(isBlank(type.id)) {
								findPosType(1);
							} else {
								findPosType();
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

function deletePosType() {
	var selarrrow = $("#typeTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "您确定删除已选中POS型号？", function(r) {
			if (r) {
				var codes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#typeTbl").getRowData(selarrrow[i]);
					codes.push(rowData.code);
				}
				ddpAjaxCall({
					url : "deletePosType",
					data : codes,
					successFn : function(data){
						if(data.code == "000000") {
							findPosType();
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

function afterDeleteType(data) {
	if(data.code == "000000") {
		findPosType();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function findPosType(defaultPageNo) {
	ddpAjaxCall({
		url : "findPosType",
		data : {
			code : escapePeculiar($('#typeCodeQuery').val()),
			name : escapePeculiar($('#typeNameQuery').val()), 
			page : getJqgridPage('typeTbl', defaultPageNo),
			activate : 0
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#typeTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}

function activatePosType() {
	var selarrrow = $("#typeTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用POS型号吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#typeTbl").getRowData(selarrrow[i]);
					ids.push(rowData.typeId);
				}
				ddpAjaxCall({
					url : "activatePosType",
					data : ids,
					successFn : function(){findPosType();}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function inactivatePosType()	{
	var selarrrow = $("#typeTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用POS型号吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#typeTbl").getRowData(selarrrow[i]);
					ids.push(rowData.typeId);
				}
				ddpAjaxCall({
					url : "inactivatePosType",
					data : ids,
					successFn : function(){findPosType();}
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function viewPosType() {
	var rowData = getSelectedRowDataFromMultiples('typeTbl');
	if (rowData != null) {
		ddpAjaxCall({
			url : "findPosTypeById",
			data : rowData.typeId,
			successFn : function(response) {
				showDialog('viewPosTypeDialog');
				var htmlKeys = ['comments'];
				loadJsonData2ViewPageWithHtmlFormat('viewPosTypeDialog', response, htmlKeys);
			}
		});
	}
}

function resetPosTypeQuery() {
	clearAllText('posTypeQueryCondition');
}
$(function() {
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#ddicCategoryTbl',
			offsetHeight : 90,
			offsetWidth : 0
		},
		callback : initDdicCategoryTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'ddicCategoryDialog' ]
	});
}

function initDetailComponent() {
	initDdicCategoryDialog();
}

function initDdicCategoryTbl(size) {
	var option = {
		datatype : function (pdata) {
			findDdicCategory();
	    },
		colNames : [ 'ddicCategoryId', '分类编码', '分类名称', '描述', '创建人', '创建时间','编辑人', '编辑时间'],
		colModel : [
		            {name : 'ddicCategoryId', hidden : true},
		            {name : 'categoryCode', index : 'categoryCode', width : 100, align : 'left', sortable : false },
		            {name : 'categoryName', index : 'categoryName', width : 100, align : 'left', sortable : false },
		            {name : 'description', index : 'description', width : 100, align : 'left', sortable : false ,formatter: htmlFormatter},
		            {name : 'createUser', index : 'createUser', width : 100, align : 'left', sortable : false },
		            {name : 'createDate', index : 'createDate', width : 100, align : 'left', sortable : false, formatter: cellDateFormatter },
			        {name : 'updateUser', index : 'updateUser', width : 100, align : 'left', sortable : false },
			        {name : 'updateDate', index : 'updateDate', width : 100, align : 'left', sortable : false, formatter: cellDateFormatter }
		           ],
		//caption : "数据字典分类",
		//sortname : 'name',
		height : size.height - 50,
		width : size.width,
		multiselect: false,
		pager : '#ddicCategoryTblPagination',
		toolbar : [ true, "top" ]
	};
	
	 option = $.extend(option, jqgrid_server_opts);
	 $('#ddicCategoryTbl').jqGrid(option);

	 $("#t_ddicCategoryTbl").append($('#ddicCategoryTblToolbar'));
}
function initDdicCategoryDialog() {
	$('#ddicCategoryDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 300
	});
}

function showDdicCategoryDialog() {
	$('#ddicCategoryDialog').show().dialog('open');
}

function hideDdicCategoryDialog() {
	clearForm();
	$('#ddicCategoryDialog').hide().dialog('close');
}

function addDdicCategory() {
	clearForm();
	showDdicCategoryDialog();
}

function editDdicCategory() {
	clearForm();

	var selarrrow = $("#ddicCategoryTbl").getGridParam('selrow');

	if (selarrrow) {
		if (selarrrow.length == 0){
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		}else {
			var rowData = $('#ddicCategoryTbl').getRowData(selarrrow);
			$('#categoryCode').attr('disabled',true);
			$('#ddicCategoryId').val(rowData.ddicCategoryId);
			$('#categoryCode').val(rowData.categoryCode);
			$('#categoryName').val(rowData.categoryName);
			$('#description').val(rowData.description);
			showDdicCategoryDialog();
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function clearForm() {
	clearAllText('ddicCategoryDialog');
	$('#categoryCode').attr('readonly',false);
	$('#categoryCode').attr('disabled',false);
	$('#ddicCategoryId').val('');
	$('#description').val('');
	$('#categoryCode').val('');
	$('#categoryName').val('');
}

function saveDdicCategory() {
	if(isValidate('ddicCategoryDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存分类字典信息吗？", function(r) {
			if (r) {
				var ddicCategory = {
						id:  $('#ddicCategoryId').val(),
						categoryCode :  $('#categoryCode').val(),
						categoryName :  $('#categoryName').val(),
						description :  $('#description').val()
				};
				ddpAjaxCall({
					url : "saveDdicCategory",
					data : ddicCategory,
					successFn : afterAddDdicCategory
				});
			}
		});
	}
}

function afterAddDdicCategory(data) {
	if(data.code == "000000") {
		hideDdicCategoryDialog();
		findDdicCategory();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function deleteDdicCategory() {
	var selarrrow = $("#ddicCategoryTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "您确认此字典未被引用，并进行删除吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#ddicCategoryTbl").getRowData(selarrrow[i]);
					ids.push(rowData.ddicCategoryId);
				}
				ddpAjaxCall({
					url : "deleteDdicCategory",
					data : ids,
					successFn : afterDeleteDdicCategory
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function afterDeleteDdicCategory(data) {
	if(data.code == "000000") {
		findDdicCategory();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function findDdicCategory(defaultPageNo) {
	var ddicCategory = {
		categoryCode : escapePeculiar($.trim($('#ddicCategoryCodeQuery').val())),
		categoryName : escapePeculiar($.trim($('#ddicCategoryNameQuery').val())),
		page : getJqgridPage('ddicCategoryTbl', defaultPageNo)
	};
	ddpAjaxCall({
		url : "findDdicCategory",
		data : ddicCategory,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#ddicCategoryTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}		
		}
	});
}

function resetDdicCategoryQuery() {
	$('#ddicCategoryCodeQuery').val('');
	$('#ddicCategoryNameQuery').val('');
}

function activateDdicCategory() {
	var selarrrow = $("#ddicCategoryTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用数据字典分类吗？", function(r) {
			if (r) {
				var codes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#ddicCategoryTbl").getRowData(selarrrow[i]);
					codes.push(rowData.categoryCode);
				}
				ddpAjaxCall({
					url : "activateDdicCategory",
					data : codes,
					successFn : function(){findDdicCategory();}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function inactivateDdicCategory() {
	var selarrrow = $("#ddicCategoryTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用数据字典分类吗？", function(r) {
			if (r) {
				var codes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#ddicCategoryTbl").getRowData(selarrrow[i]);
					codes.push(rowData.categoryCode);
				}
				ddpAjaxCall({
					url : "inactivateDdicCategory",
					data : codes,
					successFn : function(){findDdicCategory();}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
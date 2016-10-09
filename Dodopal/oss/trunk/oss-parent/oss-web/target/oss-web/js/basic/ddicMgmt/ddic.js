$(function() {
	findDdic();
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#ddicTbl',
			offsetHeight : 90,
			offsetWidth : 0
		},
		callback : initDdicTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'ddicDialog' ]
	});
	
	$('#ddicCategoryCodeQuery').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 200
	});
	
	$('#categoryCode').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 150
	});
	ddpAjaxCall({
		url : "loadCategoryCodes",
		successFn : loadCategoryCodes
	});
}

function loadCategoryCodes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#ddicCategoryCodeQuery'), data.responseEntity);
		$("#ddicCategoryCodeQuery").next("span").find("input.combo-value").val("");
		$("#ddicCategoryCodeQuery").next("span").find("input.combo-text").val("--请选择--");
		reLoadComboboxData($('#categoryCode'), data.responseEntity);
		//$('#categoryCode').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function initDetailComponent() {
	initDdicDialog();
}

function initDdicTbl(size) {
	var option = {
		datatype : function (pdata) {
			findDdic();
	    },
	    colNames : [ 'ddicId', '字典编码', '字典名称',  '分类编码', '分类名称', '优先级', '启用标识', '描述'],
		colModel : [ 
		            {name : 'ddicId',index : 'ddicId', hidden : true},
		            {name : 'code', index : 'code', width : 100, align : 'left', sortable : false},
		            {name : 'name', index : 'name', width : 100, align : 'left', sortable : false},
		            {name : 'categoryCode', index : 'categoryCode', width : 100, align : 'left', sortable : false},
		            {name : 'categoryName', index : 'categoryName', width : 100, align : 'left', sortable : false},
		            {name : 'orderList', index : 'orderList', width : 100, align : 'left', sortable : false},
		            {name : 'activate', index : 'activate', width : 100, align : 'left', sortable : false, formatter: activateFormatter},
		            {name : 'description', index : 'description', width : 100, align : 'left', sortable : false,formatter: htmlFormatter}
		            ],
		//caption : "数据字典",
		//sortname : 'name',
		height : size.height - 70,
		width : size.width,
		multiselect: false,
		pager : '#ddicTblPagination',
		toolbar : [ true, "top" ]
	};
	
	 option = $.extend(option, jqgrid_server_opts);
	 $('#ddicTbl').jqGrid(option);

	$("#t_ddicTbl").append($('#ddicTblToolbar'));
}
function initDdicDialog() {
	$('#ddicDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 380
	});
}

function showDdicDialog() {
	$('#ddicDialog').show().dialog('open');
}

function hideDdicDialog() {
	clearForm();
	$('#ddicDialog').hide().dialog('close');
}

function addDdic() {
	clearForm();
	$("#code").attr({  
        disabled:false  
    });
	$('#categoryCode').combobox('enable');
	$("#categoryCode").next("span").find("input.combo-value").val("");
	$("#categoryCode").next("span").find("input.combo-text").val("--请选择--");
	
	$("input[name=activate]:eq(0)").removeAttr("disabled",'disabled'); 
	$("input[name=activate]:eq(1)").removeAttr("disabled",'disabled');

	$(".combo").css("width","134px");
	$(".combo-text").css("width","112px");
	showDdicDialog();
}

function editDdic() {
	//var selarrrow = $("#ddicTbl").getGridParam('selarrrow');
	var selarrrow = $("#ddicTbl").jqGrid('getGridParam','selrow');

	if (selarrrow) {
		if (selarrrow.length == 0){
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		}  else {
			var rowData = $('#ddicTbl').getRowData(selarrrow);
			$('#ddicId').val(rowData.ddicId);
			$('#code').val(rowData.code);
			//$('#code').attr('disable',true);
			$("#code").attr({  
	            disabled:true  
	        });
			$('#name').val(rowData.name);
			$('#orderList').val(rowData.orderList);
			$('#description').val(rowData.description);
			$('#categoryCode').combobox('select', rowData.categoryCode);
			$('#categoryCode').combobox('disable');
			if(rowData.activate=="启用") {
				$("input[name=activate]:eq(0)").attr("checked",'checked'); 
			} else {
				$("input[name=activate]:eq(1)").attr("checked",'checked'); 
			}
			 $("input[name=activate]:eq(0)").attr("disabled",'disabled');
			 $("input[name=activate]:eq(1)").attr("disabled",'disabled'); 
			showDdicDialog();
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function clearForm() {
	clearAllText('ddicDialog');
	$('#code').attr('readonly',false);
	$('#code').attr('disable',false);
	$('#ddicId').val('');
	$('#code').val('');
	$('#name').val('');
	$('#orderList').val('');
	$('#description').val('');
	$('#categoryCode').combobox('clear');
	$("input[name=activate]:eq(0)").attr("checked",'checked');
}

function saveDdic() {
	var categoryCode = $('#categoryCode').combobox('getValue');
	if(isBlank(categoryCode)) {
		msgShow(systemWarnLabel, "分类编码不能为空", 'warning');
		return;
	}
	if(isValidate('ddicDialog')) {
		$.messager.confirm(systemConfirmLabel, "确定要保存数据字典信息吗？", function(r) {
			if (r) {
				var activate = $("input[name='activate']:checked").val();
				var ddic = {
					id : $('#ddicId').val(),
					code : $('#code').val(),
					name : $('#name').val(),
					orderList : $('#orderList').val(),
					description : $('#description').val(),
					categoryCode : $('#categoryCode').combobox('getValue'),
					activate : activate
				};
				ddpAjaxCall({
					url : "saveDdic",
					data : ddic,
					successFn : afterSaveDdic
				});
			}
		});
	}
}

function afterSaveDdic(data) {
	if (data.code == "000000") {
		hideDdicDialog();
		findDdic();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function deleteDdic() {
	var selarrrow = $("#ddicTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "您确认此字典未被引用，并进行删除吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#ddicTbl").getRowData(selarrrow[i]);
					ids.push(rowData.ddicId);
				}
				ddpAjaxCall({
					url : "deleteDdic",
					data : ids,
					successFn : afterDeleteDdic
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function afterDeleteDdic(data) {
	if(data.code == "000000") {
		findDdic();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function findDdic(defaultPageNo) {
	var ddic = {
		code : escapePeculiar($.trim($('#ddicCodeQuery').val())),
		name : escapePeculiar($.trim($('#ddicNameQuery').val())),
		categoryCode : $('#ddicCategoryCodeQuery').combobox('getValue'),
		activate : $('#activateQuery').combobox('getValue'),
		page : getJqgridPage('ddicTbl', defaultPageNo)
	};
	ddpAjaxCall({
		url : "findDdicByPage",
		data : ddic,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#ddicTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}		
		}
	});
}

function resetDdicQuery() {
	$('#ddicCodeQuery').val('');
	$('#ddicNameQuery').val('');
	$('#ddicCategoryCodeQuery').combobox('clear');
}

function activateDdic() {
	var selarrrow = $("#ddicTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用数据字典吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#ddicTbl").getRowData(selarrrow[i]);
					ids.push(rowData.ddicId);
				}
				ddpAjaxCall({
					url : "activateDdic",
					data : ids,
					successFn : function(){findDdic();}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function inactivateDdic() {
	var selarrrow = $("#ddicTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用数据字典吗？", function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#ddicTbl").getRowData(selarrrow[i]);
					ids.push(rowData.ddicId);
				}
				ddpAjaxCall({
					url : "inactivateDdic",
					data : ids,
					successFn : function(){findDdic();}
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

// 查看数据字典详情
function viewDdic(){
	var selarrrow = $("#ddicTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
    var selrow = $("#ddicTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#ddicTbl').getRowData(selrow);
		ddpAjaxCall({
			url : "viewDdic",
			data : rowData.ddicId,
			successFn : loadViewDdic
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function loadViewDdic(data){
	if(data.code=="000000"){
		showViewDdic();
		var ddicBean = data.responseEntity;

		$('#viewCategoryCode').html(ddicBean.categoryCode);
		$('#viewDdicCode').html(ddicBean.code);
		$('#viewDdicName').html(ddicBean.name);
		//$('#viewDdicParentCode').html(ddicBean.parentCode);
		$('#viewActivate').html(ddicBean.activateName);
		$('#viewOrderList').html(ddicBean.orderList);
		$('#viewDescription').text(ddicBean.description==null?"":ddicBean.description);
		$('#viewCreateUser').html(ddicBean.createUser);
		$('#viewCreateDate').html(ddicBean.viewCreateDate);
		$('#viewUpdateUser').html(ddicBean.updateUser);
		$('#viewUpdateDate').html(ddicBean.viewUpdateDate);
	}else{
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
function showViewDdic(){
	$('#viewDdicDialog').show().dialog('open');
}
function closeViewDdic(){
	$('#viewDdicDialog').show().dialog('close');
}
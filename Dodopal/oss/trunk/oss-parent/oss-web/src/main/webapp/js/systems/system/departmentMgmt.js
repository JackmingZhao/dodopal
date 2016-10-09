$(function() {
	initMainComponent();
	initDetailComponent();
//	initActivateDDICComponent();
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#departmentTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initTypeTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'typeDialog' ]
	});
	loadChargeId();
}


//加载用户负责人
function loadChargeId(){
	$('#chargeId').combobox({
		valueField : 'id',
		textField : 'name',
		editable : false,
	});
	var user = {
			activate : 0
	}
	ddpAjaxCall({
		url : "findUser",
		data : user,
		successFn : loadChargeIds
	});
}
function loadChargeIds(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#chargeId'), data.responseEntity)
		$('#chargeId').combobox('select',' ');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}



function initDetailComponent() {
	initTypeDialog();
}

function initTypeTbl(size) {
	var option = {
		datatype : function (pdata) {
			findDeprment();
	    },
	    colNames : ['ID','部门CODE','部门负责人ID','部门名称','部门负责人','备注'],
		colModel : [
		            { name : 'id', hidden : true},
		            { name : 'depCode', hidden : true },
		            { name : 'chargeId', hidden : true },
		            { name : 'depName', index : 'depName', width : 80, align : 'left', sortable : false },
		            { name : 'chargeName', index : 'chargeName', width : 80, align : 'left', sortable : false },
		            { name : 'remark', index : 'remark', width : 240, align : 'left', sortable : false,formatter: htmlFormatter }
				],
		//caption : "部门列表",
		//sortname : 'depName',
		height : size.height - 50,
		width : size.width,
		multiselect: true,
		pager : '#departmentTblPagination',
		toolbar : [ true, "top" ]
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#departmentTbl').jqGrid(option);
	 $("#t_departmentTbl").append($('#departmentTblToolbar'));
}
//初始化加载添加界面
function initTypeDialog() {
	$('#departmentDialog').dialog({
		collapsible : false,
		modal : true,
		closed : true,
		closable : false,
		width : 680,
		height : 300
	});
}
//添加后情况界面的属性
function addDepartment() {
	$('#depCode').attr('disabled',false);
	$('#remark').val('');
	clearAllText('departmentDialog');
	showDialog('departmentDialog');
}
//编辑部门列表
function editDepartment() {
	var selarrrow = $("#departmentTbl").getGridParam('selarrrow');// 多选
	if (selarrrow) {
		if (selarrrow.length == 1) {
			var rowData = $('#departmentTbl').getRowData(selarrrow[0]);
			var	depCode=rowData.depCode;
			ddpAjaxCall({
				url : "findDepartmentById",
				data : depCode,
				successFn : loadDepMent
			});
			$('#depCode').attr('disabled',true);
			showDialog('departmentDialog');
			
		} else if (selarrrow.length == 0){
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		} else {
			msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function loadDepMent(data){
	if(data.code == "000000"){
		clearAllText('departmentDialog');
		var depMent = data.responseEntity;
		if(depMent.activate=="0")
			$("input[name=activate]:eq(0)").attr("checked",'checked'); 
		else
			$("input[name=activate]:eq(1)").attr("checked",'checked'); 
		$('#id').val(depMent.id);
		$('#depCode').val(depMent.depCode);
		$('#depName').val(depMent.depName);
		$('#remark').val(depMent.remark);
		
		var box = $('#chargeId').combobox('getData');
		    if (box.length > 0) {
		   	 for(var i=0;i<box.length;i++){
		   	  if(box[i].id==depMent.chargeId){
		   	 $("#chargeId").combobox('select', box[i].id);
		   	  }
		   	 }
		    }
	}
}

//保存和更改部门
function saveDepartment() {
	var depCode = $.trim($('#depCode').val());
	var activate = $('input[name="activate"]:checked').val(); 
	var chargeId = $('#chargeId').combobox('getValue');
	if(isValidate('departmentDialog')){
		$.messager.confirm(systemConfirmLabel, "确定要保存部门信息吗？", function(r) {
			if (r) {
				var departments = {
						id : $('#id').val(),
						depCode : $('#depCode').val(),
						depName : $('#depName').val(),
						remark : $('#remark').val(),
						chargeId :chargeId,
						activate:activate
				};
				ddpAjaxCall({
					url : "saveDepartment",
					data : departments,
					successFn : afterSaveDeprment
				});
			}
		});
	}
}

function afterSaveDeprment(data) {
	if (data.code == "000000") {
		hideDialog('departmentDialog');
		findDeprment(1);
		$('#remark').val('');
		clearAllText('departmentDialog');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
//删除部门
function deleteDepartment() {
	var selarrrow = $("#departmentTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, deleteConfirmLabel, function(r) {
			if (r) {
				var depCodes = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#departmentTbl").getRowData(selarrrow[i]);
					depCodes.push(rowData.depCode);
				}
				ddpAjaxCall({
					url : "deleteDepartment",
					data : depCodes,
					successFn : afterDepartment
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

//查询部门信息列表
function findDeprment(defaultPageNo) {
	ddpAjaxCall({
		url : "findDepartmentPage",
		data : {
			depName : escapePeculiar($.trim($('#depNameQuery').val())),
			//activate : $('#activateQuery').combobox('getValue'),
			page : getJqgridPage('departmentTbl', defaultPageNo)
		},
		successFn : function(data) {
//			console.log(data.responseEntity.records);
//			$(data.responseEntity.records).each(function(i,v){
//				delete v["id"];
//			});
//			console.log(data.responseEntity.records);
			if (data.code == "000000") {
				loadJqGridPageData($('#departmentTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}
//启用部门
//function startDepartment() {
//	var selarrrow = $("#departmentTbl").getGridParam('selarrrow');// 多选
//	if (selarrrow !=null && selarrrow.length > 0) {
//		$.messager.confirm(systemConfirmLabel, "确定要启用此部门吗？", function(r) {
//			if (r) {
//				var depCodes = new Array();
//				for(var i=0; i<selarrrow.length; i++) {
//					var rowData = $("#departmentTbl").getRowData(selarrrow[i]);
//					depCodes.push(rowData.depCode);
//				}
//				ddpAjaxCall({
//					url : "startDepartment",
//					data : depCodes,
//					successFn : afterDepartment
//				});
//				
//			}
//		});
//	} else {
//		msgShow(systemWarnLabel, unSelectLabel, 'warning');
//	}
//}
////禁用此部门
//function disableDepartment()	{
//	var selarrrow = $("#departmentTbl").getGridParam('selarrrow');// 多选
//	if (selarrrow !=null && selarrrow.length > 0) {
//		$.messager.confirm(systemConfirmLabel, "确定要停用此部门吗？", function(r) {
//			if (r) {
//				var depCodes = new Array();
//				for(var i=0; i<selarrrow.length; i++) {
//					var rowData = $("#departmentTbl").getRowData(selarrrow[i]);
//					depCodes.push(rowData.depCode);
//				}
//				ddpAjaxCall({
//					url : "disableDepartment",
//					data : depCodes,
//					successFn : afterDepartment
//				});
//				
//			}
//		});
//	} else {
//		msgShow(systemWarnLabel, unSelectLabel, 'warning');
//	}
//}
//部门是否多选
function viewDepartment() {
	var selarrrow = $("#departmentTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
//重置查询部门
function resetDepartmentQuery() {
	clearAllText('departmentQueryCondition');
}

//启用停用和删除操作提示
function afterDepartment(data) {
	if(data.code == "000000") {
		findDeprment();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

//重置查询条件
function clearDep(){
	$('#departmentQueryCondition').find('input,select').each(function(){
	      $(this).val('');
	    });
}
$(function() {
	initMainComponent();
	initDetailComponent();
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#roleTbl',
			offsetHeight : 50,
			offsetWidth : 0
		},
		callback : initRoleTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'roleDialog' ]
	});
}

function initDetailComponent() {
	initRoleDialog();
}

function initRoleTbl(size) {
	var option = {
			datatype : function (pdata) {
				findRole();
		    },
	
	//$('#roleTbl').jqGrid({
		// datatype : function(pdata){
		// findRole(pdata);
		// },
		// jsonReader: {
		// root: "records",
		// page: "pageNo",
		// total: "totalPages",
		// records: "rows",
		// repeatitems: false
		// },
		//datatype : "local",
		colNames : [ 'roleId', 'operationIds', '角色名', '描述' ],
		colModel : [ {
			name : 'roleId',
			hidden : true
		}, {
			name : 'operationIds',
			hidden : true
		}, {
			name : 'name',
			index : 'name',
			width : 50,
			align : 'left',
			sortable : false
		}, {
			name : 'description',
			index : 'description',
			width : 150,
			align : 'left',
			sortable : false,
			formatter: htmlFormatter
		} ],
		//caption : "角色列表",
		//sortname : 'name',
		height : size.height - 50,
		width : size.width,
		pager : '#roleTblPagination',
		//rowNum : 100,
		multiselect: true,
		//rowList : [ 10,20,50,100 ],
		toolbar : [ true, "top" ]
		// onSortCol:jqGridClientSort,
	};
	 option = $.extend(option, jqgrid_server_opts);
	 $('#roleTbl').jqGrid(option);
	$("#t_roleTbl").append($('#roleTblToolbar'));
}
function initRoleDialog() {
	$('#roleDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function showRoleDialog() {
	$('#roleDialog').show().dialog('open');
}

function hideRoleDialog() {
	clearForm();
	$('#roleDialog').hide().dialog('close');
}

function addRole() {
	clearForm();
	var role = {};
	var options = {
		url : "loadPermissionTree",
		data : role,
		successFn : loadPermissionTree,
		failureFn : null,
		completeFn : null
	}
	ddpAjaxCall(options);
	showRoleDialog();
}

function loadPermissionTree(treeData) {
	if (treeData.code == "000000") {
		var roleData = treeData.responseEntity;
		$('#rolePermissionTree').tree({
			data : treeData.responseEntity,
			lines : true,
			checkbox : true,
			onCheck : function(node, checked) {
				if ($(node).tree('isLeaf', node.target)) {
					var requiredCheckedNodeId = node.attributes.requiredCheckedNodeId;
					if(node.id != requiredCheckedNodeId && (checked == true || checked == "true")) {
						//选中查询按钮之外的同级按钮时, 必须同时选中查询按钮
						var $requiedNode = $('#rolePermissionTree').tree('find', requiredCheckedNodeId);
						if($requiedNode.checked != true || $requiedNode.checked != "true" ) {
							$('#rolePermissionTree').tree('check', $requiedNode.target);
						}
					} else if(node.id == requiredCheckedNodeId && (checked == false || checked == "false")) {
						// 取消选中查询按钮时,其所有同级按钮必须已全部取消，否则不能取消该查询按钮
						var parent =$('#rolePermissionTree').tree('getParent', node.target);
						var children =$('#rolePermissionTree').tree('getChildren', parent.target);
						$.each(children, function(index, element) {
							if(element.checked== true || element.checked == "true") {
								$('#rolePermissionTree').tree('check', node.target);
								return false;
							}
						});
					}
				}
			}
		});
	}
}

function editRole() {
	var selarrrow = $("#roleTbl").getGridParam('selarrrow');// 多选
	if ( selarrrow.length > 1) {
		msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		return;
	}
	var selrow = $("#roleTbl").getGridParam('selrow');
	if (selrow) {
		var rowData = $('#roleTbl').getRowData(selrow);
		$("#rid").val(rowData.roleId);
		var role = {
				id: rowData.roleId,
				name : rowData.name,
				description : rowData.description,
				operationIds : rowData.operationIds
		};
		
		ddpAjaxCall({
			url : "loadPermissionTree",
			data : role,
			successFn : loadPermissionTree
		});
		ddpAjaxCall({
			url : "findRoleById",
			data : rowData.roleId,
			successFn : loadRole
		});
		
		$('#name').val(rowData.name);
		$('#description').val(rowData.description);
		showRoleDialog();
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function loadRole(data){
	if(data.code="000000"){
		role = data.responseEntity;
		$('#name').val(role.name);
		$('#description').val(role.description);
	}
}

function clearForm() {
	clearAllText('roleDialog');
	$('#name').val('');
	$("#rid").val('');
	$('#description').val('');
	$('#rolePermissionTree').empty();
}

function saveRole() {
	var name = $.trim($('#name').val());
	var description = $.trim($('#description').val());
//	if(name.length<2||name.length>20){
//		msgShow(systemWarnLabel, "角色名需在2到21长度", 'warning');
//		return;
//	}
//	if(description.length>200){
//		msgShow(systemWarnLabel, "描述需在200字节内", 'warning');
//		return;
//	}
//	if (name == '') {
//		msgShow(systemWarnLabel, "请输入角色名称", 'warning');
//	} else 
	if(isValidate('roleDialog')){
		$.messager.confirm(systemConfirmLabel, "确定要保存角色信息吗？", function(r) {
			if (r) {

				var value = $('#rolePermissionTree').tree('getChecked');
				var valueList = '';
				$.each(value, function(index, element) {
					valueList += ',' + element.id;
				});
				if(valueList==""){
					msgShow(systemWarnLabel, "必须给角色指定一个功能", 'warning');
					return;
				}
				var selrow = $("#roleTbl").getGridParam('selrow');
				var rowId=$("#rid").val();
				if (selrow) {
					var rowData = $('#roleTbl').getRowData(selrow);
					//rowId = rowData.roleId;
				}
				
				var role = {
					id : rowId,
					name : name,
					description : description,
					operationIds : valueList.substring(1)
				}
				ddpAjaxCall({
					url : "saveRole",
					data : role,
					successFn : afterAddRole
				});
			}
		});
	}
}



function afterAddRole(data) {
	if(data.code == "000000") {
		hideRoleDialog();
		findRole(1);
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function deleteRole() {
	
	var selarrrow = $("#roleTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, deleteConfirmLabel, function(r) {
			if (r) {
				var ids = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#roleTbl").getRowData(selarrrow[i]);
					ids.push(rowData.roleId);
				}
				ddpAjaxCall({
					url : "deleteRole",
					data : ids,
					successFn : afterDeleterole
				});
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function afterDeleterole(data) {
	if(data.code == '000000') {
		findRole();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function findRole(defaultPageNo) {
	var role = {
		name : escapePeculiar($.trim($('#roleNameQuery').val())),
		page : getJqgridPage('roleTbl', defaultPageNo)
	}
//	if(testValue($('#roleNameQuery').val())){
//		msgShow(systemWarnLabel, "字符中含有非法参数", 'warning');
//		return;
//	}
	ddpAjaxCall({
		url : "findRoleByPage",
		data : role,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#roleTbl'), data.responseEntity);
			}else{
				msgShow(systemWarnLabel, data.message, 'warning');
			}	
		}
	});
}

function resetRoleQuery() {
	$('#roleNameQuery').val('');
}
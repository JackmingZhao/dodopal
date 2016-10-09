$(function(){
	findMerRole();
	$('.page-navi').paginator({prefix:'merRole',pageSizeOnChange:findMerRole});
	highlightTitle();
	
	$('#merRoleName').focus(function(){
		validateRoleMerRoleName(false,false);
	});
	
});

function addMerRole(){
	resetDetailForm('detailForm');
	setTitleForAction('#merRoleDetail .tit-h3','添加角色');
	$('#merRoleId').val('');
	$('#merRoleCode').val('');

	ddpAjaxCall({
		url : "findMerchnatRoleFunctionByCode",
		data : {},
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#detailForm')[0].reset();
				$('#merRoleMain').hide();
				$('#merRoleDetail').show();
				
				$('#functionTree').jstree('destroy');
				$('#functionTree').on('select_node.jstree',jstreeNodeCheck)
																.on('deselect_node.jstree',jstreeNodeUnCheck)
																.jstree({ "plugins" : [ "wholerow","json_data","checkbox" ], 'core' : {
				    'data' : data.responseEntity
				} });
				
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
}

function jstreeNodeCheck(event,data){
	if(data.node && data.node.text){
		console.log(data.node.text);
		if(data.node.text != '查询'){
			var childrenIds = data.node.children_d;
			if(childrenIds.length==0){
				var id_prefix = data.node.id.substring(0,data.node.id.lastIndexOf('.'));
				var find_id = id_prefix + '.find';
				$('#functionTree').jstree(true).check_node(find_id);
				$('#functionTree').jstree(true).disable_node(find_id);
			}else{
				$(childrenIds).each(function(i,v){
					if(v.indexOf(".find")!=-1){
						$('#functionTree').jstree(true).check_node(v);
						$('#functionTree').jstree(true).disable_node(v);
					}
					
				})
			}
			
		}
	}
}

function jstreeNodeUnCheck(event,data){
	if(data.node && data.node.text){
		if(data.node.text != '查询'){
			var id_prefix = data.node.id.substring(0,data.node.id.lastIndexOf('.'));
			var functions = $('#functionTree').jstree(true).get_selected();
			console.log(id_prefix);
			var hasFunction = false;
			$(functions).each(function(i,v){
				if(v.indexOf(id_prefix) != -1 && v.indexOf('.find') == -1 && v != data.node.parent){
					hasFunction = true;
					return;
				}
			})
			
			if(!hasFunction){
				var id_prefix = data.node.id.substring(0,data.node.id.lastIndexOf('.'));
				console.log(id_prefix);
				var find_id = id_prefix + '.find';
				$('#functionTree').jstree(true).enable_node(find_id);
			}
			
			var childrenIds = data.node.children_d;
			$(childrenIds).each(function(i,v){
				if(v.indexOf(".find")!=-1){
					$('#functionTree').jstree(true).enable_node(v);					
				}
				
			})
		}
	}
}

function cancelEdit(){
	var callbackFn = function(){
		$('#detailForm')[0].reset();
		$('#merRoleMain').show();
		$('#merRoleDetail').hide();
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "确认要取消当前修改？", confirmOnClick: callbackFn});
}

function editMerRole(code){
	resetDetailForm('detailForm');
	setTitleForAction('#merRoleDetail .tit-h3','编辑角色');
	ddpAjaxCall({
		url : "findMerchnatRoleByCode",
		data : code,
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#auditTable').show();
				$('#detailForm')[0].reset();
				$('#merRoleMain').hide();
				$('#merRoleDetail').show();
				loadMerRole(data.responseEntity,false);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function loadMerRole(data,viewOnly){
	
	if(data.createDate){
		data.createDate = formatDate(data.createDate,'yyyy-MM-dd HH:mm:ss')
	}
	
	if(data.updateDate){
		data.updateDate = formatDate(data.updateDate,'yyyy-MM-dd HH:mm:ss')
	}
	
	$('#merRoleId').val(data.id);
	$('#merRoleCode').val(data.merRoleCode);
	$('#description').val(data.description);
	
	$('#functionTree').jstree('destroy');
	
	$('#functionTree').on('select_node.jstree',jstreeNodeCheck)
													.on('deselect_node.jstree',jstreeNodeUnCheck)
													.jstree({ "plugins" : [ "wholerow","json_data","checkbox" ], 'core' : {
														'data' : data.merRoleFunTreeList
														} });
	var functions = $('#functionTree').jstree(true).get_selected();
	var funFinds = new Array();
	$(functions).each(function(i,v){
		if(v.indexOf('.find') != -1){
			funFinds.push(v);
		}
	})
	
	$(funFinds).each(function(i,v){
		var id_prefix = v.substring(0,v.lastIndexOf('.'));
		$(functions).each(function(j,vf){
			if(vf.indexOf(id_prefix) != -1  && vf.indexOf('.find') == -1 && vf != id_prefix ){
				$('#functionTree').jstree(true).disable_node(v);
			}
		})
	})
	
	loadPojo(data,viewOnly);
}

function delMerRole(code){
	$.messagerBox({type:"confirm", title:"删除提示", msg: "您确认删除该角色吗？", confirmOnClick: confirmDelMerRole, param:code});
}

function confirmDelMerRole(deleteCode){
	var deleteIds = [];
	deleteIds.push(deleteCode);
	
	console.log(deleteIds);
	
	ddpAjaxCall({
		url : "deleteMerchnatRoleByIds",
		data : deleteIds,
		successFn : function(data) {
			if (data.code == "000000") {
				$('.pop-win').hide();
				findMerRole($('.page-navi').paginator('getPage'));
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

var roleNameDuplicated = false;
function validateRoleMerRoleName(state,idExist){
	
	var ajaxMethod = function(){
		var merRole = {
				id : $('#merRoleId').val(),
				merRoleName: $('#merRoleName').val()
		};
		if(!idExist){
			ddpAjaxCall({
				url : "checkMerRoleNameExist",
				data : merRole,
				successFn : function(data) {
					console.log(data);
					if(data.code == "000000" && (data.responseEntity == 'false' || data.responseEntity == false)){
						$.validationHandler(true, 'merRoleNameVal');
						roleNameDuplicated = false;
					}else{
						$.validationHandler(false, 'merRoleNameVal','角色名已经存在');
						roleNameDuplicated = true;
					}
				}
			});
		}
		return true;
	}
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:$.numberEnCn,
			valiDatInpId:"merRoleName",
			warnMessageElement:"merRoleNameVal",
			msg:"输入的角色名格式不正确",
			mixlength:"1",
			maxlength:"20",
			ajaxMethod:ajaxMethod
		};
	
	return $.warnDataFnHandler(data);
}

function validateRoleFunctions(){
	var functions = $('#functionTree').jstree(true).get_selected();
	$("#functionTree").find(".jstree-undetermined").each(function (i, element) {
		functions.push($(element).closest('.jstree-node').attr("id"));
    });

	if(!functions || functions.length == 0){
		$.validationHandler(false, 'functionTreeVal', '角色权限不能为空');
		return false;
	}else{
		$.validationHandler(true, 'functionTreeVal');
		return true;
	}
}

function validateRole(merRole){
	var formValid = true;
	var idExist = false;
	if(merRole.id){
		idExist = true;
	}
	formValid = validateRoleMerRoleName(true,idExist) && formValid;
	formValid = validateRoleFunctions() && formValid;
	formValid = formValid && !roleNameDuplicated;
	
	return formValid;
}

function saveMerRole(){
	var merRole = {
			id : $('#merRoleId').val(),
			merRoleName: $('#merRoleName').val(),
			description : $('#description').val(),
			merRoleCode : $('#merRoleCode').val()
	};
	
	if(!validateRole(merRole)){
		console.log('val error');
		return false;
	}

	var functions = $('#functionTree').jstree(true).get_selected();
	/*$("#functionTree").find(".jstree-undetermined").each(function (i, element) {
		functions.push($(element).closest('.jstree-node').attr("id"));
    });*/

	var functionDTOs = [];
	
	$(functions).each(function(i,v){
		var fun = {};
		fun.merFunCode = v;
		functionDTOs.push(fun);
	});
	
	merRole['merRoleFunDTOList'] = functionDTOs;
	
	console.log('###############save mer role##############');
	console.log(merRole);
	console.log('###############save mer role##############');
	
	ddpAjaxCall({
		url : "saveMerchnatRole",
		data : merRole,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				$('#detailForm')[0].reset();
				$('#merRoleMain').show();
				$('#merRoleDetail').hide();
				
				var page = $('.page-navi').paginator('getPage');
				if(!merRole.id){
					page = {pageNo:1,pageSize:page.pageSize};
				}
				
				findMerRole(page);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}

function findMerRole(page){
	console.log('findMerRole page ::' + page);
	
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	if(page.pageSize==null){
		page.pageSize=10;
	}
	var queryBean ={ 
			merRoleName:escapePeculiar($.trim($('#merRoleNameQuery').val())),
			page:page
	}
	ddpAjaxCall({
		url : "findMerchnatRoles",
		data : queryBean,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				console.log(data);
				var html = '';
				$('#displayTbl tbody').empty();
				
				var i = 1;
				
				if(data.responseEntity && data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						var tdCss = getTdCss(i);
						
						html = '<tr>';
						
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += getSequence(data.responseEntity,i);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += htmlTagFormat(v.merRoleName);
						html += '</td class="'+tdCss+'">';
						
						html += '<td class="'+tdCss+'" style="word-break:break-all" >'
						html += htmlTagFormat(v.description == null ? '' : v.description);
						html += '</td>';
							
						html += '<td class="'+tdCss+' a-center">'
						
						if(hasPermission('merchant.role.modify')){
							html += '<a href="javascript:void(0);" title="修改" class="edit-icon02" onclick="editMerRole(\''+v.merRoleCode+'\');"></a>';
						}
						
						if(hasPermission('merchant.role.del')){
							html += '<a href="javascript:void(0);" title="删除" class="del-icon" onclick="delMerRole(\''+v.merRoleCode+'\');"></a>';
						}
						
						html += '</td>';
						
						html += '<td class="nobor">&nbsp;</td>';
							
						html += '</tr>';
						$('#displayTbl').append(html);
					});
					
					
					
				}else{
					$('.null-box').show();
				}
				var pageSize = 0;
				var pageNo = 0;
				var totalPages = 0;
				var rows = 0;
				if(data.responseEntity !=null){
					 pageSize = data.responseEntity.pageSize;
					 pageNo = data.responseEntity.pageNo;
					 totalPages = data.responseEntity.totalPages;
					 rows = data.responseEntity.rows;
				}
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}
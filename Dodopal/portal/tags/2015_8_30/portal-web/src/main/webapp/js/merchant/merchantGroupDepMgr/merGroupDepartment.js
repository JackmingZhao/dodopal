$(function(){
	findMerGroupDepartment();
	$('.page-navi').paginator({prefix:'merGroupDep',pageSizeOnChange:findMerGroupDepartment});
	highlightTitle();
	
	$('#depName').focus(function(){
		validateDepName(false);
	});
	
});

function addMerGroupDep(){
	resetDetailForm('detailForm');
	setTitleForAction('#merGroupDepDetail .tit-h3','新增部门');
	$('#merGroupDepId').val('');
	$('#merGroupDepMain').hide();
	$('#merGroupDepDetailView').hide();
	$('#auditTable').hide();
	$('#merGroupDepDetail').show();
}

function editMerGroupDep(code){
	resetDetailForm('detailForm');
	setTitleForAction('#merGroupDepDetail .tit-h3','编辑部门');
	$('#merGroupDepId').val('');
	
	ddpAjaxCall({
		url : "findMerchnatDepById",
		data : code,
		successFn : function(data) {
			//console.log(data);
			
			if (data.code == "000000") {
				$('#auditTable').show();
				$('#detailForm')[0].reset();
				$('#merGroupDepMain').hide();
				$('#merGroupDepDetailView').hide();
				$('#merGroupDepDetail').show();
				
				$('#merGroupDepId').val(data.responseEntity.id);
				$('#depName').val(data.responseEntity.depName);
				$('#remark').val(data.responseEntity.remark);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function viewMerGroupDep(code){
	//console.log(code);
	
	clearReadOnlyForm('merGroupDepDetailView');
	
	ddpAjaxCall({
		url : "findMerchnatDepById",
		data : code,
		successFn : function(data) {
			//console.log(data);
			
			if (data.code == "000000") {
				$('#merGroupDepMain').hide();
				$('#merGroupDepDetail').hide();
				$('#merGroupDepDetailView').show();
				
				if(data.responseEntity.depName){
					$('#depNameSpan').text(data.responseEntity.depName);
				}
				
				if(data.responseEntity.remark){
					$('#remarkSpan').text(data.responseEntity.remark);
				}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function deleteMerGroupDep(id){
	$.messagerBox({type:"confirm", title:"删除提示", msg: "您确认删除该部门？", confirmOnClick: confirmDeleteGroupDep, param:id});
}

function confirmDeleteGroupDep(deleteId){
	var deleteIds = [];
	deleteIds.push(deleteId);
	//console.log(deleteIds);
	ddpAjaxCall({
		url : "deleteMerchnatDepById",
		data : deleteIds,
		successFn : function(data) {
			if (data.code == "000000") {
				$('.pop-win').hide();
				findMerGroupDepartment($('.page-navi').paginator('getPage'));
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

var depNameDuplicated = false;
function validateDepName(state){
	var ajaxMethod = function(){
		var merGroupDep = {
				id : $('#merGroupDepId').val(),
				depName : $.trim($('#depName').val()),
				remark : $.trim($('#remark').val())
		}
		
		ddpAjaxCall({
			url : "checkMerGroupDepNameExist",
			data : merGroupDep,
			successFn : function(data) {
				//console.log(data);
				if(data.code == "000000" && (data.responseEntity == 'false' || data.responseEntity == false)) {
					$.validationHandler(true, 'depNameVal');
					depNameDuplicated = false;
				} else {
					depNameDuplicated = true;
					$.validationHandler(false, 'depNameVal', "该部门名称已存在，请您重新输入");
				}
			}
		});
		return true;
	}
	
	var data ={submitState:state,
			required:true,
			warnCheckFn:$.numberEnCn,
			valiDatInpId:"depName",
			warnMessageElement:"depNameVal",
			msg:"输入的部门名称格式不正确",
			mixlength:"2",
			maxlength:"20",
			ajaxMethod:ajaxMethod
		};
	
	return $.warnDataFnHandler(data);
}

function validateMerGroupDep(merGroupDep){
	return validateDepName(true) && !depNameDuplicated;
}

function saveMerGroupDep(){
	var merGroupDep = {
			id : $('#merGroupDepId').val(),
			depName : $.trim($('#depName').val()),
			remark : $.trim($('#remark').val())
	}
	
	if(!validateMerGroupDep(merGroupDep)){
		return false;
	}
	
	ddpAjaxCall({
		url : "saveMerchnatGroupDep",
		data : merGroupDep,
		successFn : function(data) {
			//console.log(data);
			if (data.code == "000000") {
				$('#detailForm')[0].reset();
				$('#merGroupDepMain').show();
				$('#merGroupDepDetail').hide();
				
				var page = $('.page-navi').paginator('getPage');
				if(!merGroupDep.id){
					page = {pageNo:1,pageSize:page.pageSize}
				}
				
				findMerGroupDepartment(page);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}

function findMerGroupDepartment(page){
	//console.log('findMerGroupDepartment page::' + page);
	
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	
	var queryBean ={ 
			depName:escapePeculiar($.trim($('#depNameQuery').val())),
			page:page
	}
	
	ddpAjaxCall({
		url : "findMerchnatDeps",
		data : queryBean,
		successFn : function(data) {
			//console.log(data);
			if (data.code == "000000") {
				$('#displayTbl tbody').empty();
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$('.null-box').hide();
					var html = '';
					$(data.responseEntity.records).each(function(i,v){
						var tdCss = getTdCss(i);
						
						html = '<tr>';
						
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += 	getSequence(data.responseEntity,i);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">';
						html += v.depName;
						html += '</td>';
						
						html += '<td class="'+tdCss+'" style="word-break:break-all" >'
						html += htmlTagFormat(v.remark == null ? "" : v.remark);
						html += '</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						
						if(hasPermission('merchant.dep.modify')){
							html += '<a href="#" class="edit-icon02" title="修改" onclick="editMerGroupDep(\''+v.id+'\');"></a>';
						}
						
						/*if(hasPermission('merchant.dep.view')){
							html += '<a href="#" class="text-icon" title="查看" onclick="viewMerGroupDep(\''+v.id+'\');"></a>';
						}*/
						
						if(hasPermission('merchant.dep.del')){
							html += '<a href="#" class="del-icon" title="删除" onclick="deleteMerGroupDep(\''+v.id+'\');"></a>';
						}
						
						html += '</td>';
						
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '</tr>';
						
						$('#displayTbl tbody').append(html);
					});
					var pageSize = data.responseEntity.pageSize;
					var pageNo = data.responseEntity.pageNo;
					var totalPages = data.responseEntity.totalPages;
					var rows = data.responseEntity.rows;
					$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				}else{
					$('.page-navi').paginator('setPage');
					$('.null-box').show();
				}
				
				
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}
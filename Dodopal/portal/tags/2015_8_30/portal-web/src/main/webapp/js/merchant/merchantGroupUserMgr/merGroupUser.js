$(function(){
	findMerGroupUser();
	$('.page-navi').paginator({prefix:'merGroupUser',pageSizeOnChange:findMerGroupUser});
	loadDepartment();
	highlightTitle();
	
	addFocusoutEvent();
	/*$("#employeeDate").click(function (e) {
		var ths = this;
		calendar.show({
			id: this,
			ok: function () {
				$('#employeeDate').val(ths.value);
				validateEmployeeDate();
				$('.calendar').hide();
			}
		});
	});*/

	
});

function loadDepartment(){
	ddpAjaxCall({
		url : $.base + "/merchantGroupDep/findAllMerchnatDeps",
		data : {},
		successFn : function(data) {
			if (data.code == "000000") {
				$('#depIdQuery').empty();
				$('#depId').empty();
				$('#depIdQuery').append('<option value="">--请选择--</option>');
				$('#depId').append('<option value="">--请选择--</option>');
				
				$(data.responseEntity).each(function(i,v){
					$('#depIdQuery').append('<option value="'+v.id+'">' + v.depName + '</option>');
				});
				
				$(data.responseEntity).each(function(i,v){
					$('#depId').append('<option value="'+v.id+'">' + v.depName + '</option>');
				});
				
				selectUiInit();
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function addMerGroupUser(){
	resetDetailForm('detailForm'); 
	setTitleForAction('#merGroupUserDetail .tit-h3','添加人员');
	$('#rechargeWay').val('0');
	selectUiInit();
	$('#merGroupUserId').val('');
	displayOrHideStatus(false);
	$('#merGroupUserMain').hide();
	$('#auditTable').hide();
	$('#merGroupUserDetail').show();
	$('#merGroupUserDetailView').hide();
}

function cancelEdit(){
	var callbackFn = function(){
		$('#detailForm')[0].reset();
		$('#merGroupUserMain').show();
		$('#merGroupUserDetail').hide();
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "确认要取消当前修改？", confirmOnClick: callbackFn});
}

function importMerGroupUser(){
	$('#importPopup').show();
	$('#groupUserFile').val('');
	$('#ye').val('');
	console.log('clear file inputs');
}

function confirmImport(){
	$('#importPopup').hide();
	$.ajaxFileUpload({
		url : "importGroupUser",
		secureuri : false,
		fileElementId : "groupUserFile",
		dataType : "json",
		success : function(data) {
			console.log(data);
			if (data.code == "000000") {
				findMerGroupUser($('.page-navi').paginator('getPage'));
				$('#importPopup').hide();
				loadDepartment();
			}else{
				$('#importPopup').hide();
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		},
		error : function(data) {
			console.log(data.code);
			$('#importPopup').hide();
			$.messagerBox({type: 'warn', title:"信息提示", msg: "上传出错"});
		}
	});
	
	return false;
}

function editMerGroupUser(code){
	resetDetailForm('detailForm');
	setTitleForAction('#merGroupUserDetail .tit-h3','编辑人员');
	$('#merGroupUserId').val('');
	displayOrHideStatus(true);
	ddpAjaxCall({
		url : "findMerchnatGroupUserById",
		data : code,
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#auditTable').show();
				$('#detailForm')[0].reset();
				$('#merGroupUserMain').hide();
				$('#merGroupUserDetailView').hide();
				$('#merGroupUserDetail').show();
				loadMerGroupUser(data.responseEntity,false);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function deleteMerGroupUser(id){
	$.messagerBox({type:"confirm", title:"删除提示", msg: "您确认删除该人员吗？", confirmOnClick: confirmDeleteMerGroupUser, param:id});
}

function confirmDeleteMerGroupUser(delId){
	ddpAjaxCall({
		url : "deleteMerchnatGroupUserById",
		data : delId,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				$('.pop-win').hide();
				findMerGroupUser($('.page-navi').paginator('getPage'));
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function viewMerGroupUser(code){
	
	clearReadOnlyForm('merGroupUserDetailView');
	
	ddpAjaxCall({
		url : "findMerchnatGroupUserById",
		data : code,
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#merGroupUserMain').hide();
				$('#merGroupUserDetailView').show();
				$('#merGroupUserDetail').hide();
				loadMerGroupUser(data.responseEntity,true);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function loadMerGroupUser(data,viewOnly){
	$('#merGroupUserId').val(data.id);
	
	if(data.remark)
		$('#remark').text(data.remark);

	data.employeeDate = formatDate(data.employeeDate,'yyyy-MM-dd');
	
	if(viewOnly){
		data.empType = data.empType == '0'? '在职' :'离职';
		data.rechargeWay = data.rechargeWay == '0' ? '固定钱包充值' : '';
		console.log(data.rechargeWay);
	}else{
		$('input[name=empType][value='+data.empType+']').prop('checked', true);
	}
	
	loadPojo(data,viewOnly);
	
	console.log(data.depId);
	
	$('#depId').val(data.depId);
	$('#rechargeWay').val(data.rechargeWay);
	selectUiInit();
}

function displayOrHideStatus(show){
	if(show == true){
		$('#statusTH').show();
		$('#statusTD').show();
	}else{
		$('#statusTH').hide();
		$('#statusTD').hide();
	}
}

function getFormValues(){
	var merGroupUser = {
			id : $('#merGroupUserId').val(),
			depId : $('#depId').val(),
			gpUserName: $.trim($('#gpUserName').val()),
			cardCode: $.trim($('#cardCode').val()),
			cardType : $('#cardType').val(),
			mobiltle : $('#mobiltle').val(),
			phone : $.trim($('#phone').val()),
			identityNum : $('#identityNum').val(),
			rechargeAmount : $.trim($('#rechargeAmount').val()),
			rechargeWay : $('#rechargeWay').val(),
			employeeDate : $('#employeeDate').val(),
			empType : $('input[name=empType]:checked').val(),
			remark : $('#remark').val()
	}
	return merGroupUser;
}

function saveMerGroupUser(){
	
	var merGroupUser = getFormValues();
	
	if(!validateMerGroupUser(merGroupUser)){
		return false;
	}
	
	console.log('###############save group user##############');
	console.log(merGroupUser);
	console.log('###############save group user##############');
	
	ddpAjaxCall({
		url : "saveMerchnatGroupUser",
		data : merGroupUser,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				$('#detailForm')[0].reset();
				$('#merGroupUserMain').show();
				$('#merGroupUserDetail').hide();
				
				var page = $('.page-navi').paginator('getPage');
				if(!merGroupUser.id){
					page = {pageNo:1,pageSize:page.pageSize}
				}
				
				findMerGroupUser(page);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}

function findMerGroupUser(page){
	console.log('findMerGroupUser page ::' + page);
	
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	
	var queryBean ={ 
			depId : $('#depIdQuery').val(),
			gpUserName:escapePeculiar($.trim($('#gpUserNameQuery').val())),
			cardCode:escapePeculiar($.trim($('#cardCodeQuery').val())),
			empType:$('#empTypeQuery').val(),
			page:page
	}
	
	ddpAjaxCall({
		url : "findMerchnatGroupUsers",
		data : queryBean,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				console.log(data);
				var html = '';
				$('#displayTbl tbody').empty();
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						
						var tdCss = getTdCss(i);
						
						html += '<td class="nobor">&nbsp;</td>';
						
//						html += '<td class="'+tdCss+' a-center">';
//						html += '<input id="'+v.id+'" name="merGroupUsers" type="checkbox" onclick="toggleDelBtn(\'merGroupUsers\',\'deleteBtn\')"/>'
//						html += '</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += 	getSequence(data.responseEntity,i);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.depName;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += htmlTagFormat(v.gpUserName);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += htmlTagFormat(v.cardCode);
						html += '</td>';
								
						html += '<td class="'+tdCss+'">'
						html += v.cardType == null ? '' : v.cardType;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.rechargeAmount;
						html += '</td>';
							
						html += '<td class="'+tdCss+'">'
						html += v.rechargeWay == '0' ? '固定钱包充值' : '';
						html += '</td>';
								
						html += '<td class="'+tdCss+'">'
						html += v.employeeDate == null ? '' : formatDate(v.employeeDate,'yyyy-MM-dd');
						html += '</td>';
//产品确认：列表不显示 身份证号						
//						html += '<td class="'+tdCss+'">'
//						html += htmlTagFormat(v.identityNum == null ? '' : v.identityNum);
//						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.empType == '0' ? '在职' : '离职';
						html += '</td>';
							
						html += '<td class="'+tdCss+' a-center">'
						
						if(hasPermission('merchant.ps.modify')){
							html += '<a href="javascript:void(0);" title="修改" class="edit-icon02" onclick="editMerGroupUser(\''+v.id+'\');"></a>';
						}
						
						if(hasPermission('merchant.ps.view')){
							html += '<a href="javascript:void(0);" title="查看" class="text-icon" onclick="viewMerGroupUser(\''+v.id+'\');"></a>';
						}
						
						if(hasPermission('merchant.ps.del')){
							html += '<a href="#" class="del-icon" title="删除" onclick="deleteMerGroupUser(\''+v.id+'\');"></a>';
						}
						
						html += '</td>';
						
						html += '<td class="nobor">&nbsp;</td>';
							
						html += '</tr>';
						$('#displayTbl').append(html);
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
$(function(){
	findMerchantUser();
	$('#merchantUserPaginator').paginator({prefix:'merchantUser',pageSizeOnChange:findMerchantUser});
	highlightTitle();
	createDdicSelectWithSelectId("merUserIdentityType","IDENTITY_TYPE");
	addFocusEvent();
});

function addMerchantUser(){
	resetDetailForm('detailForm');
	setTitleForAction('#merchantUserDetail .tit-h3','创建新用户');
	$('#merchantUserId').val('');
	$('#merchantUserMain').hide();
	$('#auditTable').hide();
	$('#merchantUserDetail').show();
	$('#merchantUserDetailView').hide();
	$('#merchantUserRole').hide();
	disableOrEnableUserNameAndMobile(false);
	showOrHidePasswords(true);
	loadDepartment();
}

function toggleActivateBtn(cbName){
	var ids = [];
	$('input[name='+cbName+']').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}
	});
	
	if(ids.length > 0){
		disableOrEnableBtn(false,'#qiyong');
		disableOrEnableBtn(false,'#tingyong');
	}else{
		disableOrEnableBtn(true,'#qiyong');
		disableOrEnableBtn(true,'#tingyong');
	}
	
}

function cancelEdit(){
	var callbackFn = function(){
		$('#detailForm')[0].reset();
		$('#merchantUserMain').show();
		$('#merchantUserDetail').hide();
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "确认要取消当前修改？", confirmOnClick: callbackFn});
}

function startOrStopUser(flag){
	var ids = [];
	$('input[name=merchantUsers]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).attr('id'));
		}
	});
	if(ids.length == 0){
		return;
	}
	
	if(flag == true){
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认启用？", confirmOnClick: startOrStopUserConfirm, param:true});
	}else{
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认停用？", confirmOnClick: startOrStopUserConfirm, param:false});
	}
}

function startOrStopUserConfirm(flag) {
	var ids = [];
	$('input[name=merchantUsers]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).attr('id'));
		}
	});
	
	var activate = '';
	if(flag == true){
		activate = '0';
		ddpAjaxCall({
			url : "batchStartMerOperator",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findMerchantUser($('#merchantUserPaginator').paginator('getPage'));
					$('#qiyongBox').hide();
					toggleActivateBtn('merchantUsers');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}else{
		activate = '1';
		ddpAjaxCall({
			url : "batchStopMerOperator",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findMerchantUser($('#merchantUserPaginator').paginator('getPage'));
					$('#tingyongBox').hide();
					toggleActivateBtn('merchantUsers');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}
	
}

function loadDepartment(callbackFn){
	ddpAjaxCall({
		url : $.base + "/merchantGroupDep/findAllMerchnatDeps",
		data : {},
		successFn : function(data) {
			if (data.code == "000000") {
				$('#departmentList').empty();
				
				var n = 1;
				$(data.responseEntity).each(function(i,v){
					$('#departmentList').append('<li style="width:150xp;"><label title="'+v.depName+'" class="zxx_text_overflow"><input type="checkbox" value="'+v.id+'" />'+v.depName+'</label></li>');
					if(n >= 5 && (n%5 == 0)){
						$('#departmentList').append('<br>');
					}
					n++;
				});
				if(callbackFn){
					callbackFn();
				}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function loadMerchantRoles(callbackFn){
	
	var page = {pageNo:1,pageSize:10000};
	
	var queryBean ={ 
			page:page
	}
	
	ddpAjaxCall({
		url : $.base + "/merchantRole/findMerchnatRoles",
		data : queryBean,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				var html = '';
				$('#merchantUserRoleTable tbody').empty();
				
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('#merchantUserRoleNullBox').hide();
						html = '<tr>';
						
						var tdCss = getTdCss(i);
						
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += '<input id="'+v.merRoleCode+'" name="merchantRoles" type="checkbox" />'
						html += '</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += 	getSequence(data.responseEntity,i);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.merRoleName;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.description == null ? '' : v.description;
						html += '</td>';
							
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '</tr>';
						$('#merchantUserRoleTable').append(html);
					});
				}else{
					$('#merchantUserRoleNullBox').show();
				}
				
				if(callbackFn){
					callbackFn(data);
				}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}


function getSelectedDepartment(){
	var depIds = [];
	$('#departmentList li input').each(function(i,v){
		var dep = $(v).val();
		if($(v).is(':checked')){
			depIds.push(dep);
		}
	});
	return depIds;
}

function getSelectedRoles(){
	var roleIds = [];
	$('input[name=merchantRoles]').each(function(i,v){
		var role = $(v).attr('id');
		if($(v).is(':checked')){
			roleIds.push(role);
		}
	});
	return roleIds;
}

function editMerchantUser(code){
	disableOrEnableUserNameAndMobile(true);
	setTitleForAction('#merchantUserDetail .tit-h3','编辑用户');
	showOrHidePasswords(false);
	resetDetailForm('detailForm');
	$('#merchantUserId').val('');
	ddpAjaxCall({
		url : "findMerchnatUserByCode",
		data : code,
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#auditTable').show();
				$('#detailForm')[0].reset();
				$('#merchantUserMain').hide();
				$('#merchantUserDetailView').hide();
				$('#merchantUserRole').hide();
				$('#merchantUserDetail').show();
				loadMerchantUser(data.responseEntity,false);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function disableOrEnableUserNameAndMobile(disabled){
	$('#merUserName').attr('disabled',disabled);
	$('#merUserMobile').attr('disabled',disabled);
}

function showOrHidePasswords(show){
	if(show == true){
		$('#passwordTR').show();
	}else{
		$('#passwordTR').hide();
	}
}

function viewMerchantUser(code){
	console.log('viewMerchantUser');
	setTitleForAction('#merchantUserDetailView .tit-h3','查看用户');
	clearReadOnlyForm('merchantUserDetailView');
	$('#cancelViewBtn').show();
	
	ddpAjaxCall({
		url : "findMerchnatUserByCode",
		data : code,
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#merchantUserMain').hide();
				$('#merchantUserRole').hide();
				$('#merchantUserDetail').hide();
				$('#resetTableDivider').hide();
				$('#resetTable').hide();
				$('#merchantUserDetailView').show();
				loadMerchantUser(data.responseEntity,true);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function resetUserPwd(code){
	console.log('resetUserPwd');
	setTitleForAction('#merchantUserDetailView .tit-h3','重置密码');
	$('#cancelViewBtn').hide();
	resetDetailForm('merUserResetForm');
	ddpAjaxCall({
		url : "findMerchnatUserByCode",
		data : code,
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#merchantUserMain').hide();
				$('#merchantUserRole').hide();
				$('#merchantUserDetail').hide();
				$('#merchantUserDetailView').show();
				$('#resetTableDivider').show();
				$('#resetTable').show();
				$('#merchantUserSpanId').val(data.responseEntity.id);
				loadMerchantUser(data.responseEntity,true);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function saveAssignUserRoles(){
	var merchantUser = {
			userCode : $('#role_merchantUserId').val(),
			merUserName : $('#role_merUserName').text(),
			merUserRoleList:getSelectedRoles()
	}
	
	console.log('###############config merchant user role##############');
	console.log(merchantUser);
	console.log('###############config merchant user role##############');
	
	ddpAjaxCall({
		url : "configMerOperatorRole",
		data : merchantUser,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				$('#merchantUserMain').show();
				$('#merchantUserDetail').hide();
				$('#merchantUserDetailView').hide();
				$('#merchantUserRole').hide();
				//alert('角色保存成功。');
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}

function assignUserRoles(code){
	console.log('assignUserRoles');
	ddpAjaxCall({
		url : "findMerchnatUserByCode",
		data : code,
		successFn : function(data) {
			console.log(data);
			
			if (data.code == "000000") {
				$('#merchantUserMain').hide();
				$('#merchantUserRole').show();
				$('#merchantUserDetail').hide();
				$('#merchantUserDetailView').hide();
				
				$('#role_merchantUserId').val(data.responseEntity.userCode);
				$('#role_merUserName').text(data.responseEntity.merUserName);
				$('#role_activate').text(data.responseEntity.activate == '0'? '启用' :'停用');
				
				var callbackFun = function(){
					if(data.responseEntity.merUserRoleList && data.responseEntity.merUserRoleList.length >0 ){
						$(data.responseEntity.merUserRoleList).each(function(i,v){
							$('#' + v).prop('checked',true);
						});
					}
				}
				
				loadMerchantRoles(callbackFun);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function saveResetPassword(){
	var merchantUser = {
			id : $('#merchantUserSpanId').val(),
			merUserPWD: md5(md5($('#newPassword').val()))
	}
	
	console.log('###############reset merchant user##############');
	console.log(merchantUser);
	console.log('###############reset merchant user##############');
	
	if(!(validateResetMerUserPWD(true) && validateResetMerUserPWDConfirm(true))){
		return false;
	}
	
	ddpAjaxCall({
		url : "resetMerchnatUserPwd",
		data : merchantUser,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				$('#merchantUserMain').show();
				$('#merchantUserDetail').hide();
				$('#merchantUserDetailView').hide();
				$('#merchantUserRole').hide();
				//alert('密码重置成功。');
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}

function loadMerchantUser(data,viewOnly){
	if(data.employeeDate){
		data.employeeDate = formatDate(data.employeeDate,'yyyy-MM-dd');
	}
	
	if(data.createDate){
		data.createDate = formatDate(data.createDate,'yyyy-MM-dd HH:mm:ss');
	}
	
	if(data.updateDate){
		data.updateDate = formatDate(data.updateDate,'yyyy-MM-dd HH:mm:ss');
	}
	
	if(viewOnly){
		data.merUserIdentityType = formatIdentityType(data.merUserIdentityType);
		data.activate = data.activate == '0'? '启用' :'停用';
	}else{
		$('input[name=activate][value='+data.activate+']').prop('checked', true);
	}
	
	$('#merchantUserId').val(data.id);
	$('#merUserPWDConfirm').val(data.merUserPWD);
	
	if(data.merUserRemark)
		$('#merUserRemark').text(data.merUserRemark);
	
	$('#merUserIdentityType').val(data.merUserIdentityType);
	selectUiInit();
	loadPojo(data,viewOnly);
	
	if(viewOnly == false){
		if(data.merGroupDeptList && data.merGroupDeptList.length > 0){
			var callbackFn = function(){
				$('#departmentList li input').each(function(i,v){
					var depId = $(v).val(); 
					if(data.merGroupDeptList.indexOf(depId) != -1){
						$(v).prop('checked',true);
					}
				});
			}
		}
		loadDepartment(callbackFn);
	}else{
		if(data.merGroupDeptNameListSpan && data.merGroupDeptNameListSpan.length > 0){
			var html = '';
			$(data.merGroupDeptNameListSpan).each(function(i,v){
				html += v + " ";
			})
			$('#merGroupDeptNameListSpan').text(html);
		}
	}
	
}

function getFormValues(){
	var merchantUser = {
			id : $('#merchantUserId').val(),
			merUserName : $('#merUserName').val(),
			merUserPWD: md5(md5($('#merUserPWD').val())),
			merUserIdentityType: $('#merUserIdentityType').val(),
			merUserIdentityNumber : $('#merUserIdentityNumber').val(),
			merUserNickName : $('#merUserNickName').val(),
			merUserMobile : $('#merUserMobile').val(),
			merUserTelephone : $('#merUserTelephone').val(),
			merUserEmail : $('#merUserEmail').val(),
			activate : $('input[name=activate]:checked').val(),
			merUserRemark : $('#merUserRemark').val(),
			merGroupDeptList: getSelectedDepartment()
	}
	return merchantUser;
}

function saveMerchantUser(){
	var merchantUser = getFormValues();
	if(!validateMerchantUser(merchantUser)){
		return false;
	}
	
	ddpAjaxCall({
		url : "saveMerchantUser",
		data : merchantUser,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				$('#detailForm')[0].reset();
				$('#merchantUserMain').show();
				$('#merchantUserDetail').hide();
				$('#merchantUserRole').hide();
				
				var page = $('#merchantUserPaginator').paginator('getPage');
				if(!merchantUser.id){
					page = {pageNo:1,pageSize:page.pageSize}
				}
				
				findMerchantUser(page);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
	
	return false;
}

function findMerchantUser(page){
	console.log('findMerchantUser page ::' + page);
	
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	
	var queryBean ={ 
			merUserName : escapePeculiar($.trim($('#merUserNameQuery').val())),
			merUserNickName:escapePeculiar($.trim($('#merUserNickNameQuery').val())),
			merUserMobile:escapePeculiar($.trim($('#merUserMobileQuery').val())),
			activate:$('#activateQuery').val() == 'all' ? null : $('#activateQuery').val(),
			page:page
	}
	
	ddpAjaxCall({
		url : "findMerchnatUsers",
		data : queryBean,
		successFn : function(data) {
			console.log(data);
			if (data.code == "000000") {
				console.log(data);
				
				var html = '';
				$('#displayTbl tbody').empty();
				toggleActivateBtn('merchantUsers');
				
				if(data.responseEntity && data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('#merchantUserNullbox').hide();
						var tdCss = getTdCss(i);
						
						html = '<tr>';
						
						html += '<td class="nobor">&nbsp;</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += '<input id="'+v.userCode+'" name="merchantUsers" type="checkbox" onclick="toggleActivateBtn(\'merchantUsers\')"/>'
						html += '</td>';
						
						html += '<td class="'+tdCss+' a-center">';
						html += 	getSequence(data.responseEntity,i);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.merUserName;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.merUserNickName;
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.merUserMobile;
						html += '</td>';
								
						html += '<td class="'+tdCss+'">'
						html += v.merUserIdentityType == null ? '' : formatIdentityType(v.merUserIdentityType);
						html += '</td>';
						
						html += '<td class="'+tdCss+'">'
						html += v.merUserIdentityNumber == null ? '' : v.merUserIdentityNumber;
						html += '</td>';
							
						html += '<td class="'+tdCss+'">'
						html += v.activate == '0'? '启用' :'停用';
						html += '</td>';
								
						html += '<td class="'+tdCss+' a-center">'
						if(hasPermission('merchant.user.modify')){
							html += '<a href="javascript:void(0);" title="修改" class="edit-icon02" onclick="editMerchantUser(\''+v.userCode+'\');"></a>';
						}
						
						if(hasPermission('merchant.user.role')){
							html += '<a href="javascript:void(0);" title="分配角色" class="peo-icon" onclick="assignUserRoles(\''+v.userCode+'\');"></a>';
						}
						
						if(hasPermission('merchant.user.pwd')){
							html += '<a href="javascript:void(0);" title="重置密码" class="lock-icon" onclick="resetUserPwd(\''+v.userCode+'\');"></a>';
						}
						
						if(hasPermission('merchant.user.view')){
							html += '<a href="javascript:void(0);" title="查看" class="text-icon" onclick="viewMerchantUser(\''+v.userCode+'\');"></a>';
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
					
					$('#merchantUserPaginator').paginator('setPage',pageNo,pageSize,totalPages,rows);
				}else{
					$('#merchantUserPaginator').paginator('setPage');
					$('#merchantUserNullbox').show();
				}
				
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}

function cancelEditRole(){
	var callbackFn = function(){
		$('#merchantUserMain').show();
		$('#merchantUserDetail').hide();
		$('#merchantUserDetailView').hide();
		$('#merchantUserRole').hide();
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "确认要取消当前修改？", confirmOnClick: callbackFn});
}

function cancelResetPwd(){
	
	var callbackFn = function(){
		$('#merchantUserMain').show();
		$('#merchantUserDetail').hide();
		$('#merchantUserDetailView').hide();
		$('#merchantUserRole').hide();
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "确认要取消当前修改？", confirmOnClick: callbackFn});
	
}

function formatIdentityType(idType){
	if(idType == '0'){
		return '身份证';
	}
	if(idType == '1'){
		return '驾照';
	}
	if(idType == '2'){
		return '护照';
	}
	return '';
}
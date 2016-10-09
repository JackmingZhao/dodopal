$(function() {
	initMainComponent();
	initDetailComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
	loadDepCode();	
	loadMerUserIdentityType();
	parent.closeGlobalWaitingDialog();
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#userTbl',
			offsetHeight :50,
			offsetWidth : 0
		},
		callback : initUserTbl,
		toolbar : [ true, 'top' ]
		//dialogs : [ 'userDialog' ]
	});
}

//加载证件类型
function loadMerUserIdentityType(){
	$('#identityType').combobox({
		valueField : 'code',
		textField : 'name',
		editable : false,
		width : 148
	});
	var ddic = {
			categoryCode : 'IDENTITY_TYPE'
	}
	loadDdics(ddic, loadMerUserIdentityTypes);
}
function loadMerUserIdentityTypes(data) {
	if (data.code == "000000") {
		addTipsOption(data.responseEntity);
		reLoadComboboxData($('#identityType'), data.responseEntity)
		$('#identityType').combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}




//加载部门信息
function loadDepCode(){
	$('#departmentCode').combobox({
		valueField : 'depCode',
		textField : 'depName',
		editable : false,
		width : 148,
	});
	var department = {
			activate : "0"
	}
	ddpAjaxCall({
		url : "findDepartment",
		data : department,
		successFn : loadDepCodes
	});
//	 var box = $('#departmentCode').combobox("getData");
//	 for(var i=0;i<box.length;i++){
//		 if(box[i].depCode=="0"){
//   			 //alert(box[i].deptId);
//   			$("#departmentCode").combobox('select', box[i].depCode);
//   		 }
//    }
	
}
function loadDepCodes(data) {
	if (data.code == "000000") {
		data.responseEntity.unshift({depCode:'',depName:'--请选择--'});
		reLoadComboboxData($('#departmentCode'), data.responseEntity);
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


function initDetailComponent() {
	$('#addressLine').append(createAreaComponent("address", constant.createAreaWithStreet));
	initAreaComponent("address", globalAreaComboboxWidth);
	initUserDialog();
	loadDepCode();
	if($.browser.version == '8.0'){
		$('#street_address').css("width","448px");
		$('#comments').css("width","658px");
	}else{
		$('#street_address').css("width","455px");
		$('#comments').css("width","665px");
	}

}

function initUserTbl(size) {
	var option = {
			datatype : function (pdata) {
				findUser();
		    },
		colNames : [ 'userId', 'roleIds', '用户名', '姓名','所属部门','电子邮箱','最新登录时间','最新登录IP','注册日期','启用标识'],
		colModel : [ {name : 'userId',hidden : true}, 
		             {name : 'roleIds',hidden : true},
		             {name : 'loginName',index : 'loginName',width : 100,align : 'left',sortable : false},
		             {name : 'name',index : 'name',width : 100,align : 'left',sortable : false
		}, {
			name : 'deptName',
			index : 'deptName',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'email',
			index : 'email',
			width : 100,
			align : 'left',
			sortable : false
		},{
			name : 'loginDate',
			index : 'loginDate',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: cellDateFormatter
		} ,{
			name : 'loginIp',
			index : 'loginIp',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'createDate',
			index : 'createDate',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: function cellDateFormatter(cellval, el, rowData) {
				if (cellval != null && $.trim(cellval) != '') {
					return formatDate(cellval, 'yyyy-MM-dd');
				} else {
					return '';
				}
			}
		} , {
			name : 'activate',
			index : 'activate',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
				if(cellval == 0) {
					return '启用';
				} else if(cellval == 1){
					return '停用';
				}
			}
		},],
		//caption : "用户列表",
		//sortname : 'name',
		height : size.height-50,
		width : size.width,
		multiselect: true,
		pager : '#userTblPagination',
		toolbar : [ true, "top" ]
		    };
	 option = $.extend(option, jqgrid_server_opts);
	 $('#userTbl').jqGrid(option);
	$("#t_userTbl").append($('#userTblToolbar'));
}
function initUserDialog() {
	$('#userDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function showUserDialog() {
	$('#userDialog').show().dialog('open');
}

function hideUserDialog() {
	clearForm();
	$('#userDialog').hide().dialog('close');
}

function addUser() {
	$("input[name=activate]:eq(0)").attr("checked",'checked'); 
	$("input[name=activate]:eq(0)").removeAttr("disabled",'disabled'); 
	$("input[name=activate]:eq(1)").removeAttr("disabled",'disabled'); 
	
	$("input[name=sex]:eq(0)").attr("checked",'checked'); 
	$('#identityType').combobox('clear');
	clearForm();
	$("#loginName").removeAttr("readonly"); 
	$('#loginNamefield').attr('disabled',false);
	 $("#loginName").removeAttr("disabled");
	$("#pwd").addClass("easyui-validatebox");
	 $("#pwd2").addClass("easyui-validatebox");
	 //加载dept数据
	var box = $('#departmentCode').combobox('getData');
    if (box.length > 0) {
   			$("#departmentCode").combobox('select', box[0].depCode);
   	 }
    $("#province_address").next("span").find("input.combo-value").val("");
	$("#province_address").next("span").find("input.combo-text").val("--请选择--");
    //密码区域
	$('#pwdArea').show();
	$('#passwordLine').show();
	var user = {};
	var options = {
		url : "loadRoles",
		data : user,
		successFn : loadRoles
	}
	ddpAjaxCall(options);
	showUserDialog();
}

function loadRoles(treeData) {
	var roleData = treeData.responseEntity;
	var row = 0;
	if(treeData.responseEntity!=null){
		
		if(roleData.length%5==0){
			row=roleData.length/5;
		}else{
			row=parseInt(roleData.length/5+1);
		}
		var html="";
		for(var a=0;a<row;a++){
			html +="<tr>";
			for(var i=a*5;i<roleData.length&&i<(a+1)*5;i++){
				html +="<td>";
				var boxText = roleData[i].text.length>6?roleData[i].text.substr(0,6)+"...":roleData[i].text;
				if(roleData[i].checked==true){
					html +="<label title='"+roleData[i].text+"'><input type='checkbox' name='role' value='"+roleData[i].id+"' checked>"+boxText+"</label>"
				}else{
					html +="<label title='"+roleData[i].text+"'><input type='checkbox' name='role' value='"+roleData[i].id+"' >"+boxText+"</label>"
				}
				html +="</td>"
			}
			html +="</tr>"
		}
		$("#roleList").empty();
		$("#roleList").append(html);
	}
//	if (treeData.code == "000000") {
//		$('#roleTree').tree({
//			data : treeData.responseEntity,
//			lines : true,
//			checkbox : true
//		});
//	}
}

function editUser() {
	clearForm();
	$('#passwordLine').hide();
	$('#activate').removeAttr("checked");
	$('#loginNamefield').attr('disabled',true);
	$("#loginName").attr("disabled",true);
	$("#loginName").attr('readonly',true); 
	//前端校验
	 $("#pwd").removeClass("easyui-validatebox");
	 $("#pwd2").removeClass("easyui-validatebox");
	var selarrrow = $("#userTbl").getGridParam('selarrrow');// 多选
	if (selarrrow) {
		if (selarrrow.length == 1) {
			var rowData = $('#userTbl').getRowData(selarrrow[0]);
			var user = {
					id: rowData.userId,
					name : rowData.name,
					loginName : rowData.loginName,
					roleIds : rowData.roleIds
			};
			ddpAjaxCall({
				url : "loadRoles",
				data : user,
				successFn : loadRoles
			});
			ddpAjaxCall({
				url : "viewUser",
				data : user.id,
				successFn : loadUser
			});
			$('#pwdArea').hide();
			$('#name').val(rowData.name);
			$('#loginName').val(rowData.loginName);
			$('#userId').val(rowData.userId);
			if(rowData.activate == '启用'){
				$("input[name=activate]:eq(0)").attr("checked",'checked'); 
			}
			else{
				$("input[name=activate]:eq(1)").attr("checked",'checked');
			 }
			 $("input[name=activate]:eq(0)").attr("disabled",'disabled');
			 $("input[name=activate]:eq(1)").attr("disabled",'disabled'); 
			showUserDialog();
		} else if (selarrrow.length == 0){
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		} else {
			msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function loadUser(data){
	var user = data.responseEntity;
	if(user.sexId=="0")
		$("input[name=sex]:eq(0)").attr("checked",'checked'); 
	else
		$("input[name=sex]:eq(1)").attr("checked",'checked'); 
	$('#identityType').combobox('select',user.identityType==null?"":user.identityType);
	//$("#identityType option[value='"+user.identityType+"']").attr("selected",true);
	$('#email').val(user.email);
	$('#identityId').val(user.identityId);
	$('#tel').val(user.tel);
	$('#mobile').val(user.mobile);
	//$("#identityId option[value='"+user.identityId+"']").attr("select","selected");
	$('#departmentCode').val(user.departmentCode);
	clearAreaComponent("address");
	setAreaComponent("address", user.provinceId, user.cityId, user.address);
	$('#zipCode').val(user.zipCode);
	if(user.provinceId==null||user.provinceId==""){
		$("#province_address").next("span").find("input.combo-value").val("");
		$("#province_address").next("span").find("input.combo-text").val("--请选择--");
	}
//	$('#provinceId').val(user.provinceId);
//	$('#cityId').val(user.cityId);
//	$('#address').val(user.address);
	$('#nickName').val(user.nickName);
	$('#comments').val(user.comments);
	var box = $('#departmentCode').combobox('getData');
	//alert(box.length);
    if (box.length > 0) {
   	 for(var i=0;i<box.length;i++){
   		 if(box[i].depCode==user.departmentCode){
   			 //alert(box[i].deptId);
   			$("#departmentCode").combobox('select', box[i].depCode);
   		 }
   	 }
    }
}

function clearForm() {
	clearAllText('userDialog');
	clearAreaComponent("address");
	$('#userId').val('');
	//$('#activate').removeAttr("checked");
	$('#name').val('');
	$('#loginName').val('');
	$('#pwd').val('');
	$('#pwd2').val('');
	$('#roleTree').empty();
	$('#email').val(''),
	$("#identityType").combobox('select', "");
//	$('#identityType').val(''),
	$('#identityId').val(''),
	$('#tel').val(''),
	$('#mobile').val(''),
	$('#departmentCode').val(''),
	$('#zipCode').val(''),
	$('#provinceId').val(''),
	$('#cityId').val(''),
	$('#address').val(''),
	$('#nickName').val(''),
	$('#comments').val('')
}

function resetDataQuery() {	
	$('#findTB').find('input').each(function(){
      $(this).val('');
    });
	$('#findTB').find('select').each(function(){
	      $(this).find('option:first').attr('selected','selected');
	});
}

/**
 * 保存用户信息
 */
function saveUserInfo() {
	var szReg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ ;
	var name = $.trim($('#name').val());
	var email =$.trim($('#email').val());
	var zipCode = $.trim($('#zipCode').val());
//	if(testValue($.trim($('#loginName').val()))){
//		msgShow(systemWarnLabel, "用户名含有非法字符", 'warning');
//		return;
//	}
//	if(nameTest(name)){
//		msgShow(systemWarnLabel, "姓名含有非法字符", 'warning');
//		return;
//	}
//	var re= /^[1-9][0-9]{5}$/;
//
//	if(checkUserInfo($.trim($('#identityId').val()),$('#identityType').combobox('getValue'))){
//		return;
//	}
//	
//	if ($.trim($('#loginName').val()) == ''||$.trim($('#loginName').val()).length<4) {
//		msgShow(systemWarnLabel, "用户名为空，或用户名长度小于4", 'warning');
//	}else if(!szReg.test(email)){
//		msgShow(systemWarnLabel, "请输入正确的邮箱！", 'warning');
//	}
//	else if(!$("#pwdArea").is(":hidden")&&pwdCheck($.trim($('#pwd').val()),$.trim($('#pwd2').val()))){
//		//增加用户 检测密码 
//		return;
//	}else 
//else if (name == ''||name.length<2) {
//		msgShow(systemWarnLabel, "姓名为空，或姓名长度小于2", 'warning');
//	}else if($.trim($('#nickName').val())!=''&&$.trim($('#nickName').val()).length<2){
//		msgShow(systemWarnLabel, "昵称长度必须大于2字节", 'warning');
//	}else if($('#mobile').val()!=""&&!testModelValue($('#mobile').val())){
//		//手机号不为空，则校验
//		msgShow(systemWarnLabel, "手机号格式不正确", 'warning');
//		return;
//	}else if(zipCode!=""&&!re.test(zipCode)){
//		//邮编不为空，则校验
//		msgShow(systemWarnLabel, "邮编格式不正确", 'warning');
//		return;	
//	}else if(getStreetFromCompoent('address').length>200){
//		msgShow(systemWarnLabel, "地址长度过长，请保持在200个字以内", 'warning');
//		return;	
//	} else {
//	}
	if(isValidate('userDialog')) {
		var value = $('#roleTree').tree('getChecked');
		var valueList = '';
		 $("input[name=role]").each(function() {  
	            if ($(this).attr("checked")) {  
	            	valueList += ','+$(this).val();  
	            }  
	        });  
		if(valueList==""){
			msgShow(systemWarnLabel, "必须给用户指定一个角色", 'warning');
			return;
		}
		 if($('#departmentCode').combobox('getValue')==""){
			 msgShow(systemWarnLabel, "必须给用户指定一个部门", 'warning');
			 return;
		 }
		$.messager.confirm(systemConfirmLabel, "确定要保存用户信息吗？", function(r) {
			if (r) {
				var activate = $("input[name='activate']:checked").val();
				var md5pwd = md5($("#pwd").val());
				var user = {
					id : $('#userId').val(),
					name : $.trim($('#name').val()),
					loginName : $.trim($('#loginName').val()),
					password :md5pwd,
					roleIds : valueList.substring(1),
					activate : activate,
					email:email,
					identityType:$('#identityType').combobox('getValue'),
					identityId:$.trim($('#identityId').val()),
					tel:$.trim($('#tel').val()),
					mobile:$.trim($('#mobile').val()),
					departmentCode:$('#departmentCode').combobox('getValue'),
					//$('#departmentCode').combobox('getValue')
					zipCode:$.trim($('#zipCode').val()),
					provinceId:getProvinceCodeFromCompoent('address'),
					cityId:getCityCodeFromCompoent('address'),
					address:getStreetFromCompoent('address'),
					nickName:$.trim($('#nickName').val()),
					sexId:$("input[name='sex']:checked").val(),
					comments:$.trim($('#comments').val())
				}
				ddpAjaxCall({
					url : "saveUser",
					data : user,
					successFn : afterAddUser
				});
			}
		});
	}
}


/*
 * 检查用户字段
 * */
function checkUserInfo(id,type){
	if(id!=""&&type!=""){
		//身份类型与身份id都填值
		isIDCard1=/^(\d{15}|\d{17}[\d|X|x])$/;  
		if(type==0){
			if(!isIDCard1.test(id)){
				msgShow(systemWarnLabel, "身份证号码不正确！", 'warning');
				return true;
			}
		}else if(type==1){
			if(!isIDCard1.test(id)){
				msgShow(systemWarnLabel, "驾驶证号码不正确！", 'warning');
				return true;
			}
		}
		return false;
	}else if(id==""&&type==""){
		//都不填
		return false;
	}else{
		//一个填了一个未填
		msgShow(systemWarnLabel, "请检查证件类型或证件号码是否有一个为选中（输入）！", 'warning');
		return true;
	}
}


function afterAddUser(data) {
	if(data.code == "000000") {
		hideUserDialog();
		findUser(1);
		clearForm();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function deleteUser() {
	var selarrrow = $("#userTbl").getGridParam('selarrrow');// 多选
	if (selarrrow) {
		if (selarrrow.length == 1) {
			var user = $('#userTbl').getRowData(selarrrow[0]);
			$.messager.confirm(systemConfirmLabel, deleteConfirmLabel, function(r) {
				if (r) {
					ddpAjaxCall({
						url : "deleteUser",
						data : user.userId,
						successFn : afterWork
					});
					
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

function afterDeleteUser(data) {
	if(data.code == "000000") {
		findUser();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function findUser(defaultPageNo) {
	//var act = $('#activateFind').val();
	var user = {
		name : escapePeculiar($.trim($('#userNameQuery').val())),
		loginName : escapePeculiar($.trim($('#userLoginNameQuery').val())),
		activate:$('#activateFind').combobox('getValue'),
		page : getJqgridPage('userTbl', defaultPageNo)
	}
	ddpAjaxCall({
		url : "findUserByPage",
		data : user,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#userTbl'), data.responseEntity);
			}else{
				msgShow(systemWarnLabel, data.message, 'warning');
			}		
		}
	});
}


function disableUser() {
	var selarrrow = $("#userTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要停用用户信息吗？", function(r) {
			if (r) {
				var userIds = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#userTbl").getRowData(selarrrow[i]);
					userIds.push(rowData.userId);
				}
				ddpAjaxCall({
					url : "disableUser",
					data : userIds,
					successFn : afterWork
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function startUser() {
	var selarrrow = $("#userTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要启用用户信息吗？", function(r) {
			if (r) {
				var userIds = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#userTbl").getRowData(selarrrow[i]);
					userIds.push(rowData.userId);
				}
				ddpAjaxCall({
					url : "startUser",
					data : userIds,
					successFn : afterWork
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
//修改用户密码
function resetPWD(){
	var selarrrow = $("#userTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 1) {
		msgShow(systemWarnLabel, "只能选择一条记录", 'warning');
		return;
	}
	var rowData = $('#userTbl').getRowData(selarrrow[0]);
	if(!rowData){
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
		return;
	}
	var email = rowData.email;
	var loginName = rowData.loginName;
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel,"系统将随机生成一个密码，重置后将以邮件形式发送到该用户邮箱中，您确定重置此用户密码吗？<table><tr><th>用户名</th><td>"+loginName+"</td></tr><tr><th>邮箱</th><td>"+email+"</td></tr></table>", function(r) {
			if (r) {
				var user = {
						loginName:loginName,
						email:email
				};
				ddpAjaxCall({
					url : "restPwdUser",
					data : user,
					successFn : afterWork
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function afterWork(data) {
	if(data.code == "000000") {
		msgShow(systemWarnLabel, "操作成功！", 'warning');
		findUser();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
/**
 * @param 校验密码
 */
function pwdCheck(pwd,pwd2){
	if (pwd== '') {
		msgShow(systemWarnLabel, "请输入用户密码", 'warning');
		return true; 
	}else if(pwd.length<6){
		msgShow(systemWarnLabel, "密码长度至少6位", 'warning');
		return true; 
	}else if(pwd.length>20){
		msgShow(systemWarnLabel, "密码长度最多20位", 'warning');
		return true; 
	}else if (pwd2 == '') {
		msgShow(systemWarnLabel, "请再次输入密码", 'warning');
		return true; 
	} else if (pwd != pwd2) {
		msgShow(systemWarnLabel, "两次密码不一致", 'warning');
		return true;
	} 
	return false;
}

/*************************************************  数据导出  *****************************************************************/
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					
	"typeSelStr"	: "User", 	
	"toUrl"			: "exportExcelUserControl" 	,
	"parDiaHeight"  : "150"
};
var filterConStr = [	
		{'name':'loginName', 	'value': "escapePeculiar($.trim($('#userLoginNameQuery').val()))"},	
		{'name':'name',	'value': "escapePeculiar($.trim($('#userNameQuery').val()))"		},
		{'name':'activate',	'value': "escapePeculiar($('#activateFind').combobox('getValue'))"}
	];

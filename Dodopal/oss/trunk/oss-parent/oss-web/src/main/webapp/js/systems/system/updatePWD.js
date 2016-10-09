

function upPwdUser() {
	var pwd = $.trim($('#newPwd').val());
	var pwd2= $.trim($('#newPwd2').val());
	var oldPwd = $.trim($('#oldPwd').val());
	if(oldPwd==''){
		msgShow(systemWarnLabel, "请输入原密码", 'warning');
	}else if (pwdCheck(pwd,pwd2)) {
		return;
	}else {
		$.messager.confirm(systemConfirmLabel, "确定要修改用户密码吗？", function(r) {
			if (r) {
				var user = {
					"oldPwd" : md5(oldPwd),
					"pwd":md5(pwd)
				}
				ddpAjaxCall({
					url : "systems/system/upPwdUser",
					data : user,
					successFn : upPwdUserDialog
				});
			}
		});
	}
}

//修改个人密码
function toUpdatePWD(){
	$('#newPwd').val('');
	$('#newPwd2').val('');
	$('#oldPwd').val('');
	showDialog('viewMerchantUser');
	//addTab("修改密码", "systems/system/toUpdatePWD", "", "system.toUpdatePWD");
}


function upPwdUserDialog(data) {
	if(data.code == "000000") {
		msgShow(systemWarnLabel,"操作成功！"	, 'warning');
		hideDialog('viewMerchantUser');
		$('#newPwd').val('');
		$('#newPwd2').val('');
		$('#oldPwd').val('');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}
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
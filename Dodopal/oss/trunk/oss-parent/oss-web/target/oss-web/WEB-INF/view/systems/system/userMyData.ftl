<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
</head>
<title>我的资料</title>
<style>
table.viewtable th {
width:150px;
}
table.viewtable td{
width:300px;
}
</style>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
	<div id="viewMerchantUser" class="easyui-dialog" title="编辑用户" closed="true" draggable="false" maximized="true" toolbar="#upPwdDialogToolbar">
		<form id="updatePWDForm">
			<input type="hidden" name="userId" id="userId"></input>
			<fieldset>
				<legend>我的资料</legend>
				<table class="viewtable" >
					<tr>
						<th>登录名:</th> 
						<td>${(user.loginName)!}</td>
						<th>昵称:</th>
						<td>${(user.nickName)!}</td>
						</tr>
						<tr>
							<th>姓名:</th>
							<td>
							${(user.name)!}
							</td>
							<th>部门:</th>
							<td>
							${(user.departmentCode)!}
							
							</td>
						</tr>
						<tr>
						
						<th>移动电话:</th>
						<td>
						${(user.mobile)!}
						</td>
						<th>固定电话:</th>
						<td>
						${(user.tel)!}
		                </td>
						</tr>
						
						<tr>
							<th>电子邮箱:</th>
							<td>
							${(user.email)!}
							</td>
							<th>注册日期:</th>
							<td>
							  ${user.createDate?string("yyyy-MM-dd")}
							</td>
						</tr>
							<tr>
							<th>联系地址:</th>
							<td>
							${(user.address)!}
							</td>
						</tr>
				</table>
				<br/>
			</fieldset>
		</form>
	</div>
</body>
</html>
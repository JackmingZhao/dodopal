<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	
</head>
<title>用户详情查看</title>
</head>
<body  class="easyui-layout">
    
	
	<div id="userDialogToolbar" style="text-align:right;">
		<a href="javascript:history.back()" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" >返回</a>
	</div>
	<div id="dataTblPagination"></div>
	<div id="dataDialog" title="" draggable="false" maximized="true" >
		<form id="userDialogForm">
			<input type="hidden" name="userId" id="userId" value = "${(user.id)!}"></input>
			<fieldset>
				<legend>用户详情</legend>
				<table class="viewtable" style="BORDER-RIGHT: 1px ; BORDER-TOP: 1px ; BORDER-LEFT:  1px ; BORDER-BOTTOM: 1px dashed;">
					<tr>
					    <th>手机号码:</th>
						<td>${(user.merUserMobile)!}</td>
						<th>用户名:</th>
						<td >${(user.merUserName)!}</td>
					</tr>
					<tr>
					    <th>姓名:</th>
						<td>${(user.merUserNickName)!}</td>
						<th>性别:</th>
						<td ><#if user.merUserSex??>
                            <#if user.merUserSex=='1'>
                                   	 男
                            <#elseif user.merUserSex=='0'>
                                   	女 
                            </#if>
		                    <#else>
		                        &nbsp;
		                    </#if></td>
						
					</tr>					
					<tr>
						<th>证件号码:</th>
						<td>${(user.merUserIdentityNumber)!}</td>
						<th>电子邮箱:</th>
						<td >${(user.merUserEmail)!}</td>
					</tr>
					
					<tr>
						<th>详细地址:</th>
						<td>${(user.merUserAdds)!}</td>
						<th>开户时间:</th>
						<td>
						  <#if user.createDate??>
                            ${user.createDate?string("yyyy-MM-dd HH:mm:ss")}
                        <#else>
                           1 &nbsp;
                        </#if>
						</td>
					</tr>
					
					
				</table>
				
			</fieldset>
			
		</form>
	</div>
</body>

</html>
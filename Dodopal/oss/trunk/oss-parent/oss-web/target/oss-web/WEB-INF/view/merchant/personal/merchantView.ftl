<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/merchant/personal/merchantList.js"></script>
	
</head>
<title>企业用户信息查看</title>
<style>
table.viewtable th {
	width: 150px;
}
table.viewtable td {
	width: 300px;
}
table.viewtable {
border-collapse:separate; border-spacing:10px;}
</style>
</head>



<body  class="easyui-layout" style="overflow: hidden;">
		<div id="upPwdDialogToolbar" style="text-align:right;">
		<a href="javascript:history.go(-1)" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" >返回</a>
	</div>
	<div id="viewMerchantUser" class="easyui-dialog" title="编辑用户" closed="true" draggable="false" maximized="true" toolbar="#upPwdDialogToolbar">
		<form id="updatePWDForm">
			<input type="hidden" name="userId" id="userId"></input>
			<fieldset>
				<legend>查看用户详情</legend>
				<table class="viewtable" style="BORDER-RIGHT: 1px ; BORDER-TOP: 1px ; BORDER-LEFT:  1px ; BORDER-BOTTOM: 1px dashed;">
					<tr>
						<th>所属商户名称:</th> 
						<td>${(user.merchantName)!}</td>
						<th>用户名:</th>
						<td>${(user.merUserName)!}</td>
						</tr>
						<tr>
							<th>联系人:</th>
							<td>
							${(user.merUserNickName)!}
							</td>
							<th>手机号码:</th>
							<td>
							${(user.merUserMobile)!}
							
							</td>
						</tr>
						<tr>
						
						<th>启用标识:</th>
						<td>
						<#if user.activate??>
                            <#if user.activate=='0'>
                                   	 启用
                            <#elseif user.activate=='1'>
                                   	停用
                            </#if>
		                    <#else>
		                        &nbsp;
		                </#if>
						</td>
						<th>用户角色:</th>
						<td>
						<#if user.merUserFlag??>
                            <#if user.merUserFlag=='1000'>
                                   	 管理员
                            <#elseif user.merUserFlag=='1100'>
                                   	普通操作员
                            </#if>
		                    <#else>
		                        &nbsp;
		                </#if>
		                </td>
						</tr>
						
						<tr>
							<th>证件类型:</th>
							<td>
							<#if user.merUserIdentityType??>
                            <#if user.merUserIdentityType=='0'>
                                   	身份证
                            <#elseif user.merUserIdentityType=='1'>
                                   	驾照
                             <#elseif user.merUserIdentityType="2">
                            		 护照
                            </#if>
		                    <#else>
		                        &nbsp;
		                	</#if>
							</td>
							<th>证件号码:</th>
							<td>
							${(user.merUserIdentityNumber)!}
							</td>
						</tr>
							<tr>
							<th>性别:</th>
							<td>
							<#if user.merUserSex??>
                            <#if user.merUserSex=='0'>
                                   	男
                            <#elseif user.merUserSex=='1'>
                                   	女
                            </#if>
		                    <#else>
		                        &nbsp;
		                	</#if>
							</td>
							<th>电子邮箱:</th>
							<td>
							${(user.merUserEmail)!}
							</td>
						</tr>
						<tr >
						<th valign="top">备注:</th>
						<td colspan="3" style="word-break:break-all">
						${(user.merUserRemark)!}
						</td>
						</tr>
				</table>
				<br/>
			</fieldset>
			<br/>
			
				<table class="viewtable">
					<tr>
						<th>审核人</th>
						<td>
						${(user.merchantStateUser)!}
						</td>
						<th>审核时间</th>
						<td>
						<#if user.merchantStateDate??>
						${(user.merchantStateDate)?string("yyyy-MM-dd HH:mm:ss")}
						<#else>
						&nbsp;
						</#if>
						</td>
					</tr>
					<tr>
						<th>编辑人</th>
						<td>
						${(user.updateUser)!}
						</td>
						<th>编辑时间</th>
						<td>
						<#if user.updateDate??>
						${(user.updateDate)?string("yyyy-MM-dd HH:mm:ss")}
						<#else>
						&nbsp;
						</#if>
						</td>
					</tr>
					
				</table>
				<br/>
		
		</form>
	</div>
	
	
	
	
</body>
</html>
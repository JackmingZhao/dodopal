<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/merchant/enterprise/unverify.js"></script>
<style>
table.viewtable th {
	width: 150px;
}
table.viewtable td {
	width: 300px;
}
table.viewtable {
border-collapse:separate; border-spacing:10px;}
.ax_水平线 {
    font-size: 13px;
    color: #333;
    text-align: center;
    line-height: normal;
}
</style>
</head>
<title>审核不通过商户信息详情</title>
</head>
<body  class="easyui-layout" style="overflow:auto">
    
	
	<div id="userDialogToolbar" style="text-align:right;">
		<a href="javascript:history.go(-1)" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" >返回</a>
	</div>

	<div id="OpenMerchantDialog" title="查看商户" draggable="false" maximized="true" >
				<h2>注册信息</h2> 
				<table class="viewtable" style="BORDER-RIGHT: 1px ; BORDER-TOP: 1px ; BORDER-LEFT:  1px ; BORDER-BOTTOM: 1px dashed;">
				<tr>
					<tr>
						<th>商户名称:</th>
						<td>${(merchant.merName)!}</td>
						<th>商户类型:</th>
						<td>
						<#if merchant.merType??>
							<#switch merchant.merType>
           					<#case "10">
           						代理商户
              				<#break>
              				<#case "11">
           						代理商户自有网点
              				<#break>
              				<#case "12">
           						连锁商户
              				<#break>
              				<#case "13">
           						连锁直营网点
              				<#break>
              				<#case "14">
           						连锁加盟商户
              				<#break>
              				<#case "15">
           						都都宝自有网点
              				<#break>
              				<#case "16">
           						集团商户
              				<#break>
              				<#case "18">
           						外接商户
              				<#break>
           					<#default>未知的商户类型
        					</#switch>
        				<#else>
        				&nbsp;
        				</#if>
						</td>
					</tr>
					
					<tr>
					<th>用户名:</th>
						<td>
						${(merchant.merchantUserBean.merUserName)!}
						</td>
						<th>联系人:</th>
						<td>
						${(merchant.merLinkUser)!}
						</td>
						
					</tr>
					
					<tr>
					<th>手机号码:</th>
						<td>
						${(merchant.merLinkUserMobile)!}
						</td>
						<th>通卡公司：</th>
						<td>
						${(merchant.providerCode)!}
						</td>
					</tr>
					
					<tr>
						<th>详细地址:</th>
						<td>
						${(merchant.merAdds)!}
						</td>
					</tr>
				</table>
				<br/>
				</fieldset>
				<br/>
			
				<table class="viewtable">
				
				<tr>
						<th>创建人</th>
						<td>
						${(merchant.createUser)!}
						</td>
						<th>创建时间</th>
						<td>
						<#if merchant.merStateDate??>
						${(merchant.createDate)?string("yyyy-MM-dd HH:mm:ss")}
						<#else>
						&nbsp;
						</#if>
						</td>
					</tr>
					<tr>
						<th>审核人</th>
						<td>
						${(merchant.merStateUser)!}
						</td>
						<th>审核时间</th>
						<td>
						<#if merchant.merStateDate??>
						${(merchant.merStateDate)?string("yyyy-MM-dd HH:mm:ss")}
						<#else>
						&nbsp;
						</#if>
						</td>
					</tr>
					<tr>
						<th>编辑人</th>
						<td>
						${(merchant.updateUser)!}
						</td>
						<th>编辑时间</th>
						<td>
						<#if merchant.merStateDate??>
						${(merchant.updateDate)?string("yyyy-MM-dd HH:mm:ss")}
						<#else>
						&nbsp;
						</#if>
						</td>
					</tr>
				</table>
				<br/>
	</div>
	</body>
</html>
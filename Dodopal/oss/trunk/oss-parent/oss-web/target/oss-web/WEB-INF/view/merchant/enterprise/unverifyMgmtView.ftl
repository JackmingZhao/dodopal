<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/merchant/enterprise/unverify.js"></script>
<style>

table.viewtable {
border-collapse:separate; border-spacing:10px;}
</style>
</head>
<title>未审核商户信息详情</title>
</head>
<body  class="easyui-layout" style="overflow:auto">
    
	
	<div id="userDialogToolbar" style="text-align:right;">
		<a href="javascript:history.go(-1)" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" >返回</a>
	</div>

	<div id="OpenMerchantDialog" title="查看商户" draggable="false" maximized="true" >
			<fieldset>
				<legend>注册信息 </legend>
				<table class="viewtable">
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
					<tr id="passwordLine">
						<th>商户分类:</th>
						<td>
						<#if merchant.merClassify??>
                            <#if merchant.merClassify=='0'>
                                   	正式商户
                            <#elseif merchant.merClassify=='1'>
                                   	测试商户
                            </#if>
		                <#else>
		                        &nbsp;
		                </#if>
						</td>
						<th>商户属性:</th>
						<td>
						<#if merchant.merProperty??>
                            <#if merchant.merProperty=='0'>
                                   	标准商户
                            <#elseif merchant.merProperty=='1'>
                                   	自定义商户
                            </#if>
		                <#else>
		                        &nbsp;
		                </#if>
						</td>
					</tr>
					<tr>
						<th>账户类型:</th>
						<td>
						<#if merchant.accountType??>
                            <#if merchant.accountType=='1'>
                                   	资金
                            <#elseif merchant.accountType=='2'>
                                   	授信
                            </#if>
		                <#else>
		                        &nbsp;
		                </#if>
						</td>
						<th>用户名:</th>
						<td>
						${(merchant.merUserName)!}
						</td>
					</tr>
					<tr>
						<th>联系人:</th>
						<td>
						${(merchant.merLinkUser)!}
						</td>
						<th>手机号码:</th>
						<td>
						${(merchant.merLinkUserMobile)!}
						</td>
					</tr>
					
					<tr>
						<th>通卡公司：</th>
						<td>
						<#if merBusRateList??>
							<#list merBusRateList as merBusRate>  
       							${(merBusRate.yktCode)!}
						</#list> 
						<#else>
						&nbsp;
						</#if>
						</td>
					</tr>
					<tr>
						<th>启用标识:</th>
						<td>
						<#if merchant.accountType??>
                            <#if merchant.accountType=='0'>
                                   	启用
                            <#elseif merchant.accountType=='1'>
                                  	 停用
                            </#if>
		                <#else>
		                        &nbsp;
		                </#if>
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
				<fieldset>
				<legend>补充信息</legend>
				<table class="viewtable">
					<tr>
						<th>经营范围:</th>
						<td>
						<#if merchant.merType??>
						<#switch merchant.merType>
						<#case "0">
           					便利店
              			<#break>
          				 <#case "1">
          				生活缴费
              			<#break>
           				<#case "2">
           					生活服务
              			<#break>
              			<#case "3">
           					其他
              			<#break>
           				<#default> &nbsp;
        				</#switch>
        				<#else>
        				 &nbsp;
        				</#if>
						</td>
						<th>性别:</th>
						<td>
						<#if merchant.merUserSex??>
                            <#if merchant.merUserSex=='1'>
                                   	男
                            <#elseif merchant.merUserSex=='2'>
                                   	女
                            </#if>
		                <#else>
		                        &nbsp;
		                </#if>
						</td>
					</tr>
					<tr>
						<th>固定电话:</th>
						<td>
						${(merchant.merUserTelephone)!}
						</td>
						<th>传真:</th>
						<td>
						${(merchant.merFax)!}
						</td>
					</tr>
					<tr>
						<th>证件类型:</th>
						<td>
						<#if merchant.merchantUserBean.merUserIdentityType??>
						<#switch merchant.merchantUserBean.merUserIdentityType>
          				 <#case "1">
          				身份证
              			<#break>
           				<#case "2">
           					军官证
              			<#break>
              			<#case "3">
           					护照
              			<#break>
              			<#case "4">
           					其他
              			<#break>
           				<#default>
           				 &nbsp;
        				</#switch>
        				<#else>
        				&nbsp;
        				</#if>
						</td>
						<th>证件号码:</th>
						<td>
						${(merchant.merchantUserBean.merUserIdentityNumber)!}
						</td>
					</tr>
					<tr>
						<th>电子邮箱:</th>
						<td>
						${(merchant.merUserEmail)!}
						</td>
						<th>邮编:</th>
						<td>
						${(merchant.merZip)!}
						</td>
					</tr>
					<tr>
						<th>开户银行:</th>
						<td>
						<#if merchant.merBankName??>
						<#switch merchant.merBankName>
          				 <#case "1">
          				工商银行
              			<#break>
           				<#case "2">
           					交通银行
              			<#break>
              			<#case "3">
           					建设银行
              			<#break>
              			<#case "4">
           					农业银行
              			<#break>
              			<#case "5">
           					中国银行
              			<#break>
           				<#default>
           				 &nbsp;
        				</#switch>
						<#else>
						&nbsp;
						</#if>
						</td>
						<th>开户行账户:</th>
						<td>
						${(merchant.merBankAccount)!}
						</td>
					</tr>
					<tr>
						<th>开户名称:</th>
						<td>
						${(merchant.merBankUserName)!}
						</td>
						<th>都都宝固定IP:</th>
						<td>
						${(merchant.merDdpLinkIp)!}
						</td>
					</tr>
					<tr>
						<th>都都宝联系人:</th>
						<td>
						${(merchant.merDdpLinkUser)!}
						</td>
						<th>都都宝联系人方式:</th>
						<td>
						${(merchant.merDdpLinkUserMobile)!}
						</td>
					</tr>
					<tr>
						<th valign="top">备注:</th>
						<td colspan="3" style="word-break:break-all">
						${(merchant.merchantUserBean.merUserRemark)!}
						</td>
					</tr>
				</table>
				<br/>
			</fieldset>
	</div>
	</body>
</html>
<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<#include "../../include.ftl"/>

<script src="${base}/js/common/ddpDdic.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'></script>
<script type="text/javascript" src="../js/portalValidationHandler.js"></script>
<script src="${base}/js/merchant/merchantInfo/merchantInfo.js"></script>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<style>
.zxx_text_overflow{
			overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display:block;
    width:70px;
}
</style>
<!-- InstanceBeginEditable name="doctitle" -->
<title>商户信息</title>
<!-- InstanceEndEditable -->
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>

<body>

<#include "../../header.ftl"/>

<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="com-bor-box com-bor-box01 com-top-bor">
		<h4 class="tit-h4">账户信息</h4>
		<input type="hidden" value="${(merchant.id)!}" id="id" />
				<input type="hidden" value="${(merchant.merCode)!}" id="merCode" />
			<table  class="base-table base-table01">
				<col width="120" />
				<col width="330" />
				<col width="140" />
				<tr>
					<th>商户编码：</th>
					<td>${(merchant.merCode)!}</td>
					<th>商户名称：</th>
					<td>${(merchant.merName)!}</td>
				</tr>
				<tr>
					<th>商户类型：</th>
					<td>
					${(merchant.merTypeView)!}
					</td>
					<th>账户类型：</th>
					<td>
					${(merchant.fundTypeView)!}
					</td>
				</tr>
				<tr>
					<th>用户名：</th>
					<td>${(merchant.merchantUserBean.merUserName)!}</td>
					<th>手机号码：</th>
					<td>${(merchant.merLinkUserMobile)!}</td>
				</tr>
				<#if sessionUser.merType =='14'>
				<tr>
					<th>额度来源：</th>
					<td>${(merchant.merDdpInfoBean.limitSourceView)!}</td>
				</tr>
				</#if>
				<tr>
					<th>详细地址：</th>
					<td style="white-space:pre"><#if merchant.merProName??>${(merchant.merProName)!}<#else></#if><#if merchant.merCityName??> ${(merchant.merCityName)!}市 <#else></#if><#if merchant.merAdds??>${(merchant.merAdds)!}<#else></#if></td>
					<th>通卡公司：</th>
					<td>
					<#if (merchant.merBusRateBeanList?size>0)>
						<span 
						style="display:block;width:210px;height:32px;float:left;" 
						class="zxx_text_overflow"
						title="${(merchant.providerNameView)!}">
							${(merchant.providerNameView)!}
						</span>
							<!--<span title="" class="zxx_text_overflow">
							</span>-->	
							<a href="#" class="blue-link">费率信息
					<#else>
						&nbsp;
					</#if>
						<div class="tit-pop"> 
						<i class="icon-top"></i>
								<table width="0" border="0">
									<tr>
										<th>通卡公司名称</th>
										<th>业务名称</th>
										<th>费率分类</th>
										<th>数值</th>
									</tr>
							<#if merchant.merBusRateBeanList??>
								<#list merchant.merBusRateBeanList as merBusRate>  
									<tr>
										<td>${(merBusRate.proName)!}</td>
										<td>${(merBusRate.rateName)!}</td>
										<td>${(merBusRate.rateTypeView)!}</td>
										<td>${(merBusRate.rate)!}</td>	
									</tr>
								</#list> 
							<#else>
							</#if>
								</table>
						</div>  
						</a>
				
					</td>
				</tr>
				<tr>
					<th>都都宝联系人：</th>
					<td>${(merchant.merDdpLinkUser)!}</td>
					<th>都都宝联系人电话：</th>
					<td>${(merchant.merDdpLinkUserMobile)!}</td>
				</tr>
			</table>
			<div class="des-line"></div>
			<h4 class="tit-h4">商户资料</h4>
			<div id="viewMerchantInfo" style="clear:both">
			<table class="base-table base-table01">
				<col width="120" />
				<col width="330" />
				<col width="140" />
				<tr>
					<th>联系人：</th>
					<td>
						<span id="merLinkUserSpan">
						${(merchant.merLinkUser)!}
						</span>
					</td>
					<th>固定电话：</th>
					<td>
						<span id="merTelephoneSpan">
							${(merchant.merTelephone)!}
						</span>
					</td>
				</tr>
				<tr>
					<th>传真：</th>
					<td>
						<span id="merFaxSpan">
							${(merchant.merFax)!}
						</span>
					</td>
					<th>电子邮箱：</th>
					<td>
						<span id="merEmailSpan">
							${(merchant.merEmail)!}
						</span>
					</td>
				</tr>
				<tr>
					<th>证件类型：</th>
					<td>
					<span id="merUserIdentityTypeSpan" name="merUserIdentityTypeSpan">
					${(merchant.merchantUserBean.merUserIdentityTypeView)!}
					</span>
						
					</td>
					<th>证件号码：</th>
					<td>
						<span id="merUserIdentityNumberSpan">
						${(merchant.merchantUserBean.merUserIdentityNumber!)?html}
						</span>
					</td>
				</tr>
				<tr>
				<th>经营范围：</th>
					<td>
						<span id="merBusinessScopeIdSpan">
						${(merchant.merBusinessScopeIdView)!}
        				</span>
					</td>
					<th>邮编：</th>
					<td>
					${(merchant.merZip)!}
					</td>
					
				</tr>
				<tr>
					<th>开户银行：</th>
					<td>
						<span id="merBankNameSpan">
					${(merchant.merBankNameView?replace(' ','&nbsp;'))!}
						</span>
					</td>
					<th>开户行账号：</th>
					<td>
						<span id="merBankAccountSpan">
						${(merchant.merBankAccount?replace(' ','&nbsp;'))!}
						</span>
					</td>
				</tr>
				<tr>
					<th>开户名称：</th>
					<td>
						<span id="merBankUserNameSpan">
							${(merchant.merBankUserName?replace(' ','&nbsp;'))!}
						</span>
					</td>
					
				</tr>
				<tr>
				<td id="area">
				</td>
				</tr>
				<tr>
					<td class="a-center" colspan="4">
						<@sec.accessControl permission="merchant.info.modify">
	  						<div id="editButton">
	  							<input value="编辑" class="orange-btn-h32"  type="button" onclick="toEdit();">
	  						</div>
  						</@sec.accessControl>
  						</td>
				</tr>
			</table>
			</div>
			<div id="editMerchantInfo" style="display:none; clear:both">
				<table class="base-table base-table01" >
					<col width="120" />
					<col width="330" />
					<col width="140" />
					<tr>
						<th><font color="red">*</font>联系人：</th>
						<td>
							<input type="text" id="merLinkUser" onfocus="checkLinkUser(false);"  value="${(merchant.merLinkUser)!}" class="com-input-txt w260" myPlaceholder="2-20位字符，可由中文或英文组成" required="true" maxlength="20" />
							<div class="tip-error" id="merLinkUserWarn"></div>
						</td>
						<th>固定电话：</th>
						<td>
							<input type="text" id="merTelephone" onfocus="checkPhone(false);" value="${(merchant.merTelephone)!}"  class="com-input-txt w260" myPlaceholder="固定电话，如：021-62382888" maxlength="20"/>
							<div class="tip-error" id="merTelephoneWarn"></div>
						</td>
					</tr>
					<tr>
						<th>传真：</th>
						<td>
							<input type="text" id="merFax" value="${(merchant.merFax)!}" onfocus="checkFax(false);" class="com-input-txt w260" myPlaceholder="传真，如：021-62382888"  maxlength="20"/>
							<div class="tip-error" id="merFaxSpanWarn" ></div>
						</td>
						<th>电子邮箱：</th>
						<td>
							<input type="text" id="merEmail" onfocus="checkEmail(false);" value="${(merchant.merEmail)!}" class="com-input-txt w260" myPlaceholder="请正确输入邮箱格式" maxlength="60"/>
							<div class="tip-error" id="merEmailWarn" ></div>
						</td>
					</tr>
					<tr>
						<th>证件类型：</th>
						<td>
							<select  id="merUserIdentityType" name="merUserIdentityType">
							</select>
							<div class="tip-error" id="merUserIdentityTypeWarn"></div>
							
						</td>
						<th>证件号码：</th>
						<td>
							<input type="text"  id="merUserIdentityNumber" onfocus="checkIdentityNumber(false,'merUserIdentityType','merUserIdentityNumber','merUserIdentityNumberWarn');" value="${(merchant.merchantUserBean.merUserIdentityNumber)!}" class="com-input-txt w260" myPlaceholder="请输入正确的证件号码" maxlength="20"/>
							<div class="tip-error" id="merUserIdentityNumberWarn" ></div>
						</td>
					</tr>
					<tr>
					<th>经营范围：</th>
						<td>
						<!--TODO 经营范围数据字典-->
							<select class="com-input-txt w260" name="merBusinessScopeId"  id="merBusinessScopeId"  >
							</select>
						</td>
					<th>邮编：</th>
					<td>
					<input type="text" value="${(merchant.merZip)!}" id="merZip" onfocus="checkZip(false);" maxlength="6" class="com-input-txt w260" myPlaceholder="请输入邮政编码" />
					<div class="tip-error tip-red-error" id="merZipWarn"></div>
					</td>
				</tr>
					<tr>
						<th>开户银行：</th>
						<td>
							<select class="com-input-txt w260" id="merBankName" name="merBankName" >
							</select>
							<div class="tip-error" id="merBankNameWarn" ></div>
						</td>
						<th>开户行账号：</th>
						<td>
						<input type="text" id="merBankAccount"  onfocus="checkMerBankAccount(false,'merBankName','merBankAccount','merBankAccountWarn');" value="${(merchant.merBankAccount?replace(' ','&nbsp;'))!}" class="com-input-txt w260" myPlaceholder="请输入您的开户账号"  maxlength="19"/>
						<div class="tip-error" id="merBankAccountWarn"></div>
						</td>
					</tr>
					<tr>
						<th>开户名称：</th>
						<td>
							<input type="text" id="merBankUserName" onfocus="checkMerBankUserName(false)" value="${(merchant.merBankUserName)!}" class="com-input-txt w260" myPlaceholder="请输入开户名称" maxlength="50" />
							<div class="tip-error" id="merBankUserNameWarn"></div>
						</td>
					</tr>
					<tr>
					<td id="area">
					</td>
					</tr>
					<tr>
						<td class="a-center" colspan="4">
	  						<@sec.accessControl permission="merchant.info.modify">			
								<div id="saveButton">
									<input type="button" onclick="updateInfo();" value="保存" class="orange-btn-h32" id="saveButton">
									<input type="button" onclick="cancelEdit();" value="取消"  class="orange-btn-text32">
		  						</div>
	  						</@sec.accessControl>
	  						</td>
					</tr>
				</table>
			</div>
	</div>
	<!-- InstanceEndEditable --> </div>
	
<#include "../../footer.ftl"/>

<!-- InstanceBeginEditable name="pop" --> 
<!-- InstanceEndEditable -->
</body>
<!-- InstanceEnd -->
</html>

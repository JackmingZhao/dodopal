<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<#include "../include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>个性化设置</title>
<!-- InstanceEndEditable -->
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<script src="../js/ddp/accountSecure.js" type="text/javascript"></script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="account-setup mb30">
		<#include "../accountNav.ftl"/>
		<div class="account-box">
			<div class="ehs-box">
				<div class="boxl">
					<p>设置账户交易提示<a href="javascript:;" class="img03" js="editImg" id="img032"></a></p>
				</div>
				<div class="boxr"><a href="javascript:;" js="editOne" class="orange-btn-h26" id="payInfoFlgViewButton" onclick="payInfoFlgView()">设置</a>
					<form action="/">
						<table class="base-table" style="display: none;" id="payInfoFlgTable"> 
							<colgroup>
							<col width="120">
							</colgroup>
							<tbody>
								<tr>
									<th>弹出提示信息：</th>
									<td>
					                    <label><input type="radio" name="payinfoFlg" value="0" checked="checked"/>是</label>
					                    <label><input type="radio" name="payinfoFlg" value="1"/>否</label>
									</td>
								</tr>
								<tr>
									<th></th>
									<td><div class="btn-box">
											<input type="button" value="确认" class="orange-btn-h32" onclick="upPayInfoFlg()">
										</div></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- InstanceEndEditable --> </div>
<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

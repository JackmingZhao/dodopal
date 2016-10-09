<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />

<#include "../../include.ftl"/>
<script src="${base}/js/common/ddpDdic.js"></script>
<script type="text/javascript" src="../js/merchant/merchantUser/merchantUserVal.js"></script>
<script type="text/javascript" src="../js/merchant/merchantUser/merchantUser.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type='text/javascript' src='${scriptUrl}/util/moneyFormatter.js'> </script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css">


<style>
.zxx_text_overflow{
			overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display:block;
    width:100px;
}
</style>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<title>用户管理</title>
</head>
<body>

<#include "../../header.ftl"/>

<div class="con-main" id="merchantUserMain"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">用户管理</h3>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form id="queryForm" action="/">
				<ul class="clearfix">
					<li>
						<label><span>用户名：</span>
							<input type="text" class="com-input-txt w88" id="merUserNameQuery" />
						</label>
					</li>
					<li>
						<label><span>真实姓名：</span>
							<input type="text" class="com-input-txt w88" id="merUserNickNameQuery" />
						</label>
					</li>
					<li>
						<label><span>手机号码：</span>
							<input type="text" class="com-input-txt w88" value="" id="merUserMobileQuery" />
						</label>
					</li>
					<li>
						<label><span>启用标识：</span>
							<select id="activateQuery" name="activateQuery">
								<option value="0">启用</option>
								<option value="1">停用</option>
								<option value="">全部</option>
							</select>
						</label>
					</li>
					<li class="btn-list">
						<@sec.accessControl permission="merchant.user.find">
							<input type="button" value="查询" class="orange-btn-h26" onclick="findMerchantUser();" />
						</@sec.accessControl>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryForm');" />
					</li>
					</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box">
		<ul class="navi-ul clearfix">
			<li>
				<@sec.accessControl permission="merchant.user.add">
					<a href="#" class="orange-btn-h22" onclick="addMerchantUser();">新增</a>
				</@sec.accessControl>
			</li>
			<li>
				<@sec.accessControl permission="merchant.user.activate">
					<a href="javascript:void(0);" onclick="startOrStopUser(true);" id="qiyong"  disabled="disabled" class="gray-btn-h22">启用</a>
				</@sec.accessControl>
			</li>
			<li>
				<@sec.accessControl permission="merchant.user.inactivate">
					<a href="javascript:void(0);" onclick="startOrStopUser(false);" id="tingyong"  disabled="disabled" class="gray-btn-h22">停用</a>
				</@sec.accessControl>
			</li>
			
		</ul>
		<table id="displayTbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<colgroup>
			<col width="2%" />
			<col width="4%" />
			<col width="5%" />
			<col width="11%" />
			<col width="11%" />
			<col width="10%" />
			<col width="10%" />
			<col width="17%" />
			<col width="10%" />
			<col width="15%" />
			<col width="2%" />
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center"><input type="checkbox" onclick="toggle(this,'merchantUsers');toggleActivateBtn('merchantUsers');" /></th>
				<th class="a-center">序号</th>
				<th>用户名</th>
				<th>真实姓名</th>
				<th>手机号码</th>
				<th>证件类型</th>
				<th>证件号码</th>
				<th>启用标识</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<div class="null-box" id="merchantUserNullbox" style="display:none;">请创建新用户</div>
		
		<p class="page-navi" id="merchantUserPaginator">
		</p>
		
	</div>
	</div>
	
	<#include "merchantUserDetail.ftl"/>
	
	<#include "merchantUserDetailView.ftl"/>
	
	<#include "merchantUserRole.ftl"/>
	
	<#include "../../footer.ftl"/>

</body>

</html>
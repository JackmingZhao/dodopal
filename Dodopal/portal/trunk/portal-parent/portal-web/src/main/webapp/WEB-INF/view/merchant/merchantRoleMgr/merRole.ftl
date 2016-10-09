<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />

<#include "../../include.ftl"/>
<script type="text/javascript" src="../js/merchant/merchantRoleMgr/merRole.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>

<script type="text/javascript" src="../js/jstree/jstree.min.js"></script>
<link type="text/css" href="../js/jstree/themes/default/style.min.css" rel="stylesheet" >

<!--[if lte IE 9]>
<style>
#functionTree {
	overflow-x:hidden !important;
}
</style>
<![endif]-->
<style>
.btn-box {
	padding: 0px 0px 25px 0px;
	margin: 10px 0px 0px 0px;
}
</style>
<title>角色管理</title>
</head>
<body>

<#include "../../header.ftl"/>

<div class="con-main" id="merRoleMain"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">角色管理</h3>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form id="queryForm" action="#" onkeypress="return event.keyCode != 13;">
				<ul class="clearfix">
					<li>
						<label><span>角色名称：</span>
							<input type="text" class="com-input-txt w88" id="merRoleNameQuery" />
						</label>
					</li>
					</li>
					
					<li class="btn-list">
						<@sec.accessControl permission="merchant.role.find">
							<input type="button" value="查询" class="orange-btn-h26" onclick="return findMerRole();"/>
						</@sec.accessControl>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryForm');"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box">
		<ul class="navi-ul clearfix">
			<li>
				<@sec.accessControl permission="merchant.role.add">
				<a href="javascript:void(0);" class="orange-btn-h22" onclick="addMerRole();">新增</a>
				</@sec.accessControl>
			</li>
		</ul>
		<table id="displayTbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<colgroup>
			<col width="2%" />
			<col width="4%" />
			<col width="30%" />
			<col width="40%" />
			<col width="10%" />
			<col width="2%" />
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center">序号</th>
				<th>角色名称</th>
				<th>角色描述</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<div class="null-box" style="display:none;">请创建新角色</div>
		
		<p class="page-navi">
		</p>
		
	</div>
	</div>
	
	<#include "merRoleDetail.ftl"/>
	
	<#include "../../footer.ftl"/>

</body>

</html>
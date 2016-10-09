<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "include.ftl"/>
	<script type="text/javascript" src="js/index.js"></script>
	<script type="text/javascript" src="js/systems/system/updatePWD.js"></script>
	<link href="css/index.css" rel="stylesheet" type="text/css">
	<style type="text/css">
		.tree-node-selected{
			line-height : 16px;
			background:#3baae3;
			color:#fff;
		}
		.tree-node-hover{
			background:#d7ebf9;
		}
	</style>
	<title>OSS</title>
</head>
<body class="easyui-layout" >
	<div class="header-wrap"  data-options="region:'north',split:true" style="height: 99px;">
		<div class="header-left-bg">
	        <div class="header-p">
					<a class="current" href="#" id="headUserName">欢迎您：${(SESSION_USER.name)!''}</a>
	                <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
					<a href="#"  onclick="viewMyData();">我的资料</a>
	                <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
					<a href="#"  onclick="toUpdatePWD();">修改密码</a>
	                <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
					<a href="logout"  >安全退出</a>&nbsp;
			</div>
		</div>
		<div class="header-nav" id="menuPanle">
			<#if menus??>
	    	  <#list menus as menuItem>
	    	  		<a href="#" class="current-nav" id="${menuItem.id}" onclick="loadMenu('${menuItem.id}')"><strong>${menuItem.name}</strong></a>
			  </#list>
			 </#if>
		</div>
	</div>
	
	</div>

	<div data-options="region:'south',split:false" class="footer">
		<span>COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED</span>
	</div>
	
	<div data-options="region:'west',split:true,iconCls:'icon-windows'" title="导航菜单" style="width: 180px;" id="west">
		
	</div>
	
	<div data-options="region:'center',split:true">
		<div id="tabs" class="easyui-tabs" data-options="closable:true, fit:true, border:false">
			<div title="首页" style="text-align: center; overflow: hidden;" data-options="iconCls:'icon-home', closable:true">
				<img src="${base}/css/images/welcomePage.png" style="margin-top:8%;"/>
			</div>
		</div>
	</div>
	
	<div id="tabsMenu" class="easyui-menu" style="width: 150px;display:none;">
		<div id="tabsMenu-tabclose" data-options="iconCls:'icon-close'">关闭</div>
		<div id="tabsMenu-tabcloseall" data-options="iconCls:'icon-closeAll'">全部关闭</div>
		<div id="tabsMenu-tabcloseother" data-options="iconCls:'icon-closeOthers'">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="tabsMenu-tabcloseright" data-options="iconCls:'icon-closeRight'">当前页右侧全部关闭</div>
		<div id="tabsMenu-tabcloseleft" data-options="iconCls:'icon-closeLeft'">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="tabsMenu-exit" data-options="iconCls:'icon-exit'">退出</div>
	</div>
	
	<div id="globalWaitingDialog">
		<img src="css/images/busy.gif" width="100%" height="90%"/>
	</div>
	
	<div id="upPwdDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="upPwdUser();">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('viewMerchantUser')">取消</a>
			</div>
	<div id="viewMerchantUser"  title="修改密码" closed="true" draggable="false" toolbar="#upPwdDialogToolbar">
		<form id="updatePWDForm">
			<input type="hidden" name="userId" id="userId"></input>
				<table class="viewtable">
					<tr>
						<th>原密码:</th> 
						<td>
						<input type="password" id="oldPwd" name ="oldPwd" style="width:220px" />
						</td>
						</tr>
						<tr>
						<th>新密码:</th>
						<td>
							<input type="password" id="newPwd" name ="newPwd" style="width:220px" />
						
						</td>
						</tr>
						<tr>
						<th>确认密码:</th>
						<td>
						<input type="password" id="newPwd2" name ="newPwd2" style="width:220px" />
						</td>
						</tr>
				</table>
				<br/>
		</form>
	</div>
</body>
</html>
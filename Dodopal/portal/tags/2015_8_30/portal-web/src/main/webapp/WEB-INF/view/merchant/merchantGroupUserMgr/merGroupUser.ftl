<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />

<#include "../../include.ftl"/>
<script type="text/javascript" src="../js/merchant/merchantGroupUserMgr/merGroupUser.js"></script>
<script type="text/javascript" src="../js/merchant/merchantGroupUserMgr/merGroupUserVal.js"></script>

<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/ajaxfileupload.js"></script>

<link href="${base}/css/calendar.css" rel="stylesheet" type="text/css">
<style>
#select_rechargeWay {
	background-color:#ccc;
}

div.fileinputs {
	position: relative;
}

div.fakefile {
	position: absolute;
	top: 0px;
	left: 0px;
	z-index: 1;
}

input.file {
	position: relative;
	text-align: right;
	-moz-opacity:0 ;
	filter:alpha(opacity: 0);
	opacity: 0;
	z-index: 2;
}

</style>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<title>人员管理</title>
</head>
<body>

<#include "../../header.ftl"/>

<div class="con-main" id="merGroupUserMain"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">人员管理</h3>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form id="queryForm" action="#">
				<ul class="clearfix">
					<li>
						<label><span>部门：</span>
							<select id="depIdQuery" name="depIdQuery" >	</select>
						</label>
					</li>
					<li>
						<label><span>姓名：</span>
							<input id="gpUserNameQuery" type="text" class="com-input-txt w88" />
						</label>
					</li>
					
					<li>
						<label><span>卡号：</span>
							<input id="cardCodeQuery" type="text" class="com-input-txt w88" />
						</label>
					</li>
					
					<li>
						<label><span>状态：</span>
							<select id="empTypeQuery" name="empTypeQuery">
								<option value="0">在职</option>
								<option value="1">离职</option>
								<option value="">全部</option>
							</select>
						</label>
					</li>
						
					<li class="btn-list">
						<@sec.accessControl permission="merchant.ps.find">
							<input type="button" value="查询" class="orange-btn-h26" onclick="findMerGroupUser();"/>
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
				<@sec.accessControl permission="merchant.ps.add">
					<a href="#" class="orange-btn-h22" onclick="addMerGroupUser();">新增</a>
				</@sec.accessControl>
			</li>
			<li>
				<@sec.accessControl permission="merchant.ps.batchadd">
					<a href="javascript:void(0);" onclick="importMerGroupUser();" class="orange-btn-h22" js="piliangBox">批量导入</a>
				</@sec.accessControl>
			</li>
			<li class="fr">金额单位(元)</li>
		</ul>
		<table id="displayTbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<colgroup>
			<col width="2%" />
			<#--<col width="3%" />-->
			<col width="6%" />
			<col width="15%" />
			<col width="15%" />
			<col width="11%" />
			<col width="5%" />
			<col width="7%" />
			<col width="9%" />
			<col width="11%" />
			<#--<col width="9%" /> -->
			<col width="5%" />
			<col width="20%" />
			<col width="2%" />
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<#-- <th class="a-center"><input type="checkbox" onclick="toggle(this,'merGroupUsers');toggleDelBtn('merGroupUsers','deleteBtn');" /></th>-->
				<th class="a-center">序号</th>
				<th>部门名称</th>
				<th>姓名</th>
				<th>卡号</th>
				<th>卡类型</th>
				<th>充值金额</th>
				<th>充值方式</th>
				<th>入职日期</th>
				<#--<th>身份证</th> -->
				<th>状态</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<div class="null-box" style="display:none;">请创建新人员</div>
		
		<p class="page-navi">
		<span class="fl">模版下载：<a href="${scriptUrl}/../template/groupUserTempl.xls" target="_blank">Excel格式</a></span>
		</p>
		
	</div>
	</div>
	
	<#include "merGroupUserDetail.ftl"/>
	
	<#include "merGroupUserDetailView.ftl"/>
	

<div class="pop-win" js="piliangBox" style="display: none;" id="importPopup">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px; margin:-90px 0 0 -265px; height:290px;"></div>
	<div class="pop-box" style="width: 520px; margin:-85px 0 0 -260px; height:280px;">
		<h3>批量导入（Excel格式）</h3>
		<div class="innerBox" style="padding-top:10px;">
			<p style="margin-left:109px;">
				<strong>导入文件之前，请注意如下事项：</strong><br />
				1.请导入excel文件。<br />
				2.文件中内容项，请按照模板文件中的示例进行填充。<br />
				3.最大导入数量为2000条。
			</p>
			<form action="/">
				<table  class="base-table">
					<col width="120" />
					<tr>
						<th>文件：</th>
						<td>
							<div class="fileinputs">
								<input type="file" class="file" name="groupUserFile" id="groupUserFile" onchange="ye.value=value" style="padding:8px !important; width:335px;" />
								<div class="fakefile">
									<input id="ye" name="ye" class="com-input-txt w260" >
									<input type=button value="浏览" class="btn-orange" style="width:60px;">
								</div>
							</div>
														
							</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input type="submit" class="pop-borange mr20" value="确认" onclick="confirmImport();return false;">
							<input type="button" class="pop-bgrange mr20" value="取消" onclick="$(this).closest('.pop-win').hide();">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
	
	
	<#include "../../footer.ftl"/>

</body>

</html>
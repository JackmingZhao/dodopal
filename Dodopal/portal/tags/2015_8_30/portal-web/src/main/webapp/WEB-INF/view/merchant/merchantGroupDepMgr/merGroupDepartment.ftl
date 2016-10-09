<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />

<#include "../../include.ftl"/>

<script type="text/javascript" src="../js/merchant/merchantGroupDepMgr/merGroupDepartment.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>

<title>部门管理</title>
</head>
<body>

<#include "../../header.ftl"/>

<div class="con-main" id="merGroupDepMain"> 
	<h3 class="tit-h3">部门管理</h3>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form id="queryForm" action="#" onkeypress="return event.keyCode != 13;">
				<ul class="clearfix">
					<li>
						<label><span>部门名称：</span>
							<input type="text" id="depNameQuery" class="com-input-txt w88" />
						</label>
					</li>
					<li class="btn-list">
						<@sec.accessControl permission="merchant.dep.find">
						<input type="button" value="查询" onclick="return findMerGroupDepartment();" class="orange-btn-h26" />
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
				<@sec.accessControl permission="merchant.dep.add">
					<a href="javascript:void(0);" class="orange-btn-h22" onclick="addMerGroupDep();">新增</a>
				</@sec.accessControl>
			</li>
		</ul>
		<table id="displayTbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<colgroup>
			<col width="1%" />
			<col width="2%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="1%" />
			<col width="1%" />
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center">序号</th>
				<th>部门名称</th>
				<th>备注</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<div class="null-box">请创建新部门</div>
		
		<p class="page-navi">
		</p>
	</div>
	</div>

<div class="con-main" id="merGroupDepDetail" style="display:none;">
	<h3 class="tit-h3">部门详情</h3>
	<div class="com-bor-box com-bor-box01">
	<form id="detailForm" action="#">
		<input type="hidden" id="merGroupDepId" >
		<table class="base-table">
			<col width="158" />
			<tr>
				<th><span class="red">*</span>部门名称：</th>
				<td>
					<input type="text" value="" class="com-input-txt w260" myPlaceholder="部门名称只能包含中文、数字、英文，长度在2-20个字符之间"   maxlength="20" id="depName" />
					<div class="tip-error" id="depNameVal"></div>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>
					<textarea class="area-w268" id="remark" maxlength="200" onkeydown="checkMaxlength(this,200);" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"></textarea>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<div class="btn-box">
					<input type="submit" class="orange-btn-h32" value="保存"  onclick="return saveMerGroupDep();" >
					<input type="button" value="取消" class="orange-btn-text32" onclick="cancelEdit('merGroupDep');" />
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
	</div>
	
	<div class="con-main" id="merGroupDepDetailView" style="display:none;"> 
	<h3 class="tit-h3">部门详情</h3>
	<div class="com-bor-box com-bor-box01">
			<div class="des-line"></div>
			<h4 class="tit-h4">部门信息</h4>
			<table class="base-table">
				<col width="120" />
				<tr>
					<th>部门名称：</th>
					<td><span id="depNameSpan"></span></td>
				</tr>
				<tr>
					<th>备注：</th>
					<td><span id="remarkSpan"></span></td>
				</tr>
				<tr>
					<td colspan="2" class="a-center">
						<div class="btn-box">
						<a href="#" class="orange-btn-h32" onclick="cancelView('merGroupDep');">返回</a>
						</div>
					</td>
				</tr>
			</table>
	</div>
</div>
	

	<#include "../../footer.ftl"/>

</body>

</html>
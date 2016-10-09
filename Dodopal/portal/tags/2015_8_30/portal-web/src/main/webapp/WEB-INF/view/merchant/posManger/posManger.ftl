<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<script src="${base}/js/jquery-1.9.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>POS管理</title>
<!-- InstanceEndEditable -->
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script type="text/javascript" src="${base}/js/merchant/posManger/posManger.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
</head>

<body>

<#include "../../header.ftl"/>

<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<h3 class="tit-h3">POS管理</h3>
	<div class="seach-top-box">
		<div class="seach-top-inner">
				<ul class="clearfix">
					<li>
						<label><span>POS号：</span>
							<input type="text" id="posCode" class="com-input-txt w74"/>
						</label>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findPosList();" />
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearPosQuery();"/>
					</li>
				</ul>
		</div>
	</div>
	<div class="com-bor-box ">
		<ul class="navi-ul clearfix">
			<@sec.accessControl permission="merchant.pos.bind">
			<li>
				<a href="javascript:toBinding();"  class="orange-btn-h22">绑定</a>
			</li>
			</@sec.accessControl>
			<@sec.accessControl permission="merchant.pos.unbind">
			<li>
				<a href="javascript:toUnBinding();"  class="gray-btn-h22" id="deleteBtn">解绑</a>
			</li>
			</@sec.accessControl>
		</ul>
		<table  id="displayTbl" width="100%" border="0" cellspacing="0" cellpadding="0"  class="com-table01">
			<colgroup>
				<col width="2%" />
				<col width="3%" />
				<col width="13%" />
				<col width="25%" />
				<col width="20%" />
				<col width="35%" />
				<col width="2%" />
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th class="a-center"><input type="checkbox"  onclick="checkAll(this,'pos');" id="allPos"/></th>
					<th>序号</th>
					<th>POS号</th>
					<th>绑定时间</th>
					<th>备注</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div class="null-box"></div>
		<p class="page-navi">
		</p>
	</div>
	<!-- InstanceEndEditable --> </div>

<#include "../../footer.ftl"/>

<!-- InstanceBeginEditable name="pop" -->
<div class="pop-win" id="unBindingBoxDiv" js="delBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确认解绑POS吗？</span>
            </div>
			<ul class="ul-btn">
				<li><a href="javascript:;" onclick="unBinding();" class="pop-borange">确认</a></li>
				<li><a href="javascript:closeBindding();" onclick="$(this).closest('.pop-win').hide();" class="pop-bgrange">取消</a></li>
			</ul>
	</div>
</div>
</div>

<div class="pop-win" id="bangdingBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"  style="width: 530px;  height: 280px; margin-top: -140px; margin-left: -265px;"></div>
	<div class="pop-box" style="width: 520px; height: 270px; margin-left: -260px; margin-top: -135px;">
		<h3>信息提示</h3>
		<div class="innerBox">
			<form action="/" id="bindingInfo">
				<table  class="base-table">
					<col width="140" />
					
					<tr>
						<th>POS号：</th>
						<td>
							<input  class="com-input-txt w260" id="posCodeBindTemp" disabled>
							<input type="hidden" class="com-input-txt w260" id="posCodeBind" disabled>
							<input type=button value="获取pos号"  class="btn-orange" onclick="getPOS();"></td>
					</tr>
					<tr>
						<th>备注：</th>
						<td><textarea class="area-w268 remark"  id="remarks" onkeyup="checkRemark();" onkeydown="checkMaxlength(this,200);" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200" ></textarea><div class="tip-error" id="remarksWarn" style="display: none;"></div></td>
					</tr>
					<tr>
						<th></th>
						<td><input type="button" class="pop-borange mr20" value="确认" onclick="bindingPos();">
							<input type="button" class="pop-bgrange mr20" onclick="closeBinding();" value="取消" ></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<!-- InstanceEndEditable -->
</body>
<!-- InstanceEnd --></html>

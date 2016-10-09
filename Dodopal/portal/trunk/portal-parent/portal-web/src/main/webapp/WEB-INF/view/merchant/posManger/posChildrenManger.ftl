<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>子商户POS管理</title>
<!-- InstanceEndEditable -->
<script src="${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js"></script>
<script src="${base}/js/hCalendar.js" type="text/javascript"></script>
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${base}/js/merchant/posManger/posChildrenManger.js"></script>
</head>

<body>
<#include "../../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
<#include "../../childrenPosNav.ftl"/>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form action="/">
				<ul class="clearfix">
					<li>
						<label><span>商户名称：</span>
							<input id="merName" type="text" class="com-input-txt w88" />
						</label>
					</li>
					<li>
						<label><span>POS号：</span>
							<input type="text" id="posCode" class="com-input-txt w88" />
						</label>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" onclick="findPosList();" class="orange-btn-h26"  />
						<input type="reset" value="清空" class="orange-btn-text26" />
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box ">
		<ul class="navi-ul clearfix">
			<@sec.accessControl permission="merchant.child.pos.activate">
			<li>
			<a href="javascript:toStartShow();" js="qiyong" id="qiyong" class="gray-btn-h22">启用</a>
			</li>
			</@sec.accessControl>
			<@sec.accessControl permission="merchant.child.pos.inactivate">
			<li>
			<a href="javascript:toStopShow();" js="tingyong" id="tingyong" class="gray-btn-h22">停用</a>
			</li>
			</@sec.accessControl>
		</ul>
		<table width="100%"  id="displayTbl" border="0" cellspacing="0" cellpadding="0" class="com-table01">
		<colgroup>
			<col width="2%" />
			<col width="3%" />
			<col width="6%" />
			<col width="16%" />
			<col width="16%" />
			<col width="18%" />
			<col width="18%" />
			<col width="22%" />
			<col width="2%" />
		</colgroup>
		<thead>
		<tr>
				<th>&nbsp;</th>
				<th class="a-center"><input type="checkbox" onclick="checkAll(this,'pos');" id="allPos" /></th>
				<th>序号</th>
				<th>商户名称</th>
				<th>POS号</th>
				<th>启用标识</th>
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
<script type="text/javascript">
$(document).ready(function(e) {
	$('.bg-win,[js="clopop"]').click(function(){
		$(this).closest('.pop-win').hide();
	});
});
</script> 
<!-- InstanceBeginEditable name="pop" --> 
<script type="text/javascript">
$(document).ready(function(e) {
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="子商户管理"){
			$(this).addClass('cur');
		}
	});
});
</script>
<div class="pop-win" js="tingyongBox" id="tingyongBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确认停用？</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="stopPos();">确认</a></li>
				<li><a href="javascript:toStopHide();" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="pop-win" js="qiyongBox" id="qiyongBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
	<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确认启用？</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" onclick="startPos();" class="pop-borange" onclick="popclo(this)">确认</a></li>
				<li><a href="javascript:;" onclick="toStartHide();" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- InstanceEndEditable -->
</body>
<!-- InstanceEnd --></html>

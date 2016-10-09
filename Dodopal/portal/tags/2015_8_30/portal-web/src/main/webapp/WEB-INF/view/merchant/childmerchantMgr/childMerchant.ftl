<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<script src="${base}/js/jquery-1.9.1.min.js"></script>
<script src="${base}/js/common/area.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>子商户信息</title>
<!-- InstanceEndEditable -->
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
<#include "../../include.ftl"/>
<script type="text/javascript" src="../js/merchant/childMerchantMgr/childMerchant.js"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantMgr/checkChildMerchant.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantMgr/checkNotVerChildMerchant.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantMgr/checkVerChildMerchant.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantMgr/childMerchantAdd.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantMgr/childMerchantVer.js"></script>
<script type="text/javascript" src="../js/merchant/childMerchantMgr/childMerchantNotVer.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
</head>

<body>
<#include "../../header.ftl"/>
<div class="con-main" id="childMerchantMain"> <!-- InstanceBeginEditable name="main" -->
<#include "../../childrenPosNav.ftl"/>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form action="/" id="childMerchantFrom">
				<ul class="clearfix">
					<li>
						<label><span>子商户名称：</span>
							<input type="text" class="com-input-txt w88"  id="merNameQuery" name="merNameQuery"/>
						</label>
					</li>
					<li>
						<label><span>联系人：</span>
							<input type="text" class="com-input-txt w88" id="merLinkUserQuery" name="merLinkUserQuery"/>
						</label>
					</li>
<!--					<li class="select-wid04"><span>省市：</span>
					<select name="province" id="province">
					</select>
					<select name="city" id="cityce">
					</select></li>-->
					<li>
						<label><span>启用标识：</span>
							<select name="activateQuery" id="activateQuery">
								<option value='0'  selected = "selected">启用</option>
								<option value='1'>停用</option>
								<option value=''>全部</option>
							</select>
						</label>
					</li>
					<li class="btn-list">
					<@sec.accessControl permission="merchant.child.info">
						<input type="button" value="查询" class="orange-btn-h26"    onclick="findChildMerchant()"/>
					</@sec.accessControl>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearChildMerChant('childMerchantFrom')"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box ">
		<ul class="navi-ul clearfix">
			<li>
			<@sec.accessControl permission="merchant.child.info.add">
			<a href="#" class="orange-btn-h22"style="width:66px;" onclick="openChildMerchantView(1);">创建子商户</a>
			</@sec.accessControl>
			</li>
			<li>
			<@sec.accessControl permission="merchant.child.info.activate">
			<a href="javascript:void(0);" onclick="startOrStopChildMer(true);" js="qiyong" id="qiyong1" class="gray-btn-h22" >启用</a>
			</@sec.accessControl>
			</li>
			<li>
			<@sec.accessControl permission="merchant.child.info.inactivate">
			<a href="javascript:void(0);" onclick="startOrStopChildMer(false);"js="tingyong" id="tingyong1" class="gray-btn-h22" >停用</a>
			</@sec.accessControl>
			</li>
		</ul>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id="childMerchantTbl">
			<colgroup>
			<col width="2%" />
			<col width="3%" />
			<col width="3%" />
			<!--<col width="12%" />-->
			<col width="12%" />
			<col width="7%" />
			<col width="7%" />
			<col width="9%" />
			<!--<col width="4%" />-->
			<!--<col width="3%" />-->
			<col width="8%" />
			<col width="6%" />
			<col width="12%" />
			<col width="7%" />
			<col width="3%" />
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center"><input type="checkbox"  onclick="toggle(this,'childMerchants');toggleActivateBtn('childMerchants');" id="allChild"/></th>
				<th class="a-left">序号</th>
				<!--<th class="a-left">子商户编码</th>-->
				<th class="a-left">子商户名称</th>
				<th class="a-left">商户类型</th>
				<th class="a-left">联系人</th>
				<th class="a-left">手机号码</th>
				<!--<th class="a-left">省份</th>-->
				<!--<th class="a-left">城市</th>-->
				<th class="a-left">审核状态</th>
				<th class="a-left">启用标识</th>
				<th class="a-left">开户时间</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div class="null-box">请创建子商户</div>
		<p class="page-navi">
		</p>
	</div>
	<!-- InstanceEndEditable --> </div>
<!-- childMerchant QueryInfo Start -->
<#include "childMerchantView.ftl"/>
<!-- childMerchant QueryInfo end -->
<!-- childMerchant Add Start -->
<#include "childMerchantAdd.ftl"/>
<!-- childMerchant Add end -->
<!-- childMerchant VerEdit Start -->
<#include "childMerchantVerEdit.ftl"/>
<!-- childMerchant VerEdit end -->
<!-- childMerchant NotVerEdit Start -->
<#include "childMerchantNotVerEdit.ftl"/>
<!-- childMerchant NotVerEdit end -->
<#include "../../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>

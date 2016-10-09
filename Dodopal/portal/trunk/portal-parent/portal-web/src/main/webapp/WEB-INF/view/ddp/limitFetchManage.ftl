<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>额度提取管理</title>
<#include "../include.ftl"/>
<script>
var merLimitType ='${sessionUser.merType}';
</script>
<script type="text/javascript" src="../js/ddp/limitFetchManage.js"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="seach-top-box com-top-bor">
		<div class="seach-top-inner">
			<form action="/" id="limitForm">
				<ul class="clearfix">
					<li> 
						<label><span>提取状态：</span>
							<select name="statreQuery" id="statreQuery">
								<option value='0'  selected = "selected">待确认</option>
								<option value='1'>已确认</option>
								<option value='2'>已取消</option>
								<option value='3'>已拒绝</option>
							</select>
						</label>
					</li>
					<#if sessionUser.merType =='12'>
                	<li>
						<span>商户名称：</span>
							<input type="text" class="com-input-txt w74"  id="childMerName" name="childMerName" />
					</li>
					</#if>
					<li class="w340">
						<span>提取日期：</span>
							<input id="extractStartDate" name="extractStartDate" type="text" class="com-input-txt w74"  placeholder="日历" id="sdate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  -
						  <input id="extractEndDate" name="extractEndDate" type="text" class="com-input-txt w74"  placeholder="日历" id="sdate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findLimitExtractConfirmPage()"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('limitForm')"/>
					</li>
				</ul>
			</form>
		</div>
        <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id="merLimitExtTable">
				<colgroup>
				<col width="2%" />
				<col width="3%" />
				<col width="9%" />
				<#if sessionUser.merType =='14'>
				<col width="12%" />
				</#if>
				<#if sessionUser.merType =='13'>
				<col width="12%" />
				</#if>
				<#if sessionUser.merType =='12'>
				<col width="12%" />
				</#if>
				<col width="12%" />
				<col width="12%" />
                <col width="5%" />
                <col width="2%" />
                </colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>提取金额</th>
					<#if sessionUser.merType =='14'>
					<th>上级商户名称</th>
					</#if>
					<#if sessionUser.merType =='13'>
					<th>上级商户名称</th>
					</#if>
					<#if sessionUser.merType =='12'>
					<th>下级商户名称</th>
					</#if>
					<th>提取状态</th>
					<th>提取时间</th>
					<th class="taCenter">操作</th>
					<th>&nbsp;</th>
				</tr>
				</thead>
				 <tbody></tbody>
			</table>
		<p class="page-navi"></p>
	</div>
</div>
	<#include "../footer.ftl"/>
</body>
</html>

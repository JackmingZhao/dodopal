<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<title>分润查询</title>
<#include "../include.ftl"/>
<script src="../js/calendar/calendar.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../js/ddp/profitManage.js"></script>
<script src="${base}/js/common/select.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main" id="profitManageView"> <!-- InstanceBeginEditable name="main" -->
	<div class="seach-top-box com-top-bor">
		<div class="seach-top-inner">
			<form action="profitExport" id="recordForm">
				<ul class="clearfix">
					<li class="w300">
						<span>起止日期：</span>
							<input type="text" readonly="true" id="createDateStart" name="createDateStart" class="com-input-txt w74" onfocus="c.showMoreDay = false;c.show(this,'');" />
							-
							<input type="text" readonly="true" id="createDateEnd" name="createDateEnd" class="com-input-txt w74" onfocus="c.showMoreDay = false;c.show(this,'');" />
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findProfit()"/>
						<input type="reset" value="清空" class="orange-btn-text26" />
					</li>
				</ul>
			</form>
		</div>
         <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
			<table width="100%" border="0" id="profitTb" cellspacing="0" cellpadding="0" class="com-table01">
				<colgroup>
				<col width="2%" />
				<col width="15%" />
				<col width="15%" />
				<col width="15%" />
                <col width="15%" />
				<col width="15%" />
				<col width="5%" />
                <col width="2%" />
                </colgroup>
                <thead>
				<tr>
					<th>&nbsp;</th>
					<th>汇总日期</th>
					<th>业务类型</th>
					<th>交易笔数</th>
                    <th>交易金额</th>
					<th>分润</th>
					<th class="a-center">详情</th>
					<th>&nbsp;</th>
				</tr>
				</thead>
				<tbody></tbody>
			</table>
			<div class="null-box" style="display:none;"></div>
			<@sec.accessControl permission="ddp.profit.export">
			   <p class="page-navi" id="profitManage"><span class="fl">交易记录下载：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportProfit"
			       onclick="exportProfit()">导出Excel</a></span></p>
			</@sec.accessControl>
	</div>
</div>
	<#include "profitDetails.ftl"/>
	<#include "../footer.ftl"/>
	<script type="text/javascript">
$(document).ready(function(e) {

	$('[js="zhangdan"]').click(function(){
		$('[js="zhangdanBox"]').show();
	});
	
	
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="分润查询"){
			$(this).addClass('cur');
		}
	});
});

</script>
</body>
</html>

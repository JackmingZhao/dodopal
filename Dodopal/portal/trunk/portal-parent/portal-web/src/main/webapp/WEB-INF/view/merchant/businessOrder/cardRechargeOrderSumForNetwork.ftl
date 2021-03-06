<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>一卡通充值</title>
<!-- InstanceEndEditable -->
<#include "../../include.ftl"/>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/calendar/calendar.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/merchant/businessOrder/cardRechargeOrderSumForNetwork.js" ></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
<link href="${styleUrl}/portal/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<#include "sumOrderNav.ftl"/>
	<div id='countDiv'>
		<div class="seach-top-box pb0">
			<div class="seach-top-inner">
				<form action="/" id='queryForm'>
					<ul class="clearfix">
						<li class="w130">
							<span>订单状态：</span>
								<select id='proOrderState' name="proOrderState" >
									<option value='0'>成功</option>
									<option value='1'>失败</option>
									<option value='2'>可疑</option>
								</select>
						</li>
						<li class="w350"><span class="w130">交易日期：</span>
							  <input name="startdate" id='startdate' readonly="readonly" type="text" class="com-input-txt w74" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							  -
							  <input name="enddate" id='enddate' readonly="readonly" type="text" class="com-input-txt w74" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  </li>
						<li class="btn-list">
							<input type="button" onclick='findRechargeOrderCount();' value="查询" class="orange-btn-h26" />
							<input type="button" onclick='myClearQueryForm();' value="清空" class="orange-btn-text26" />
						</li>
					</ul>
				</form>
			</div>
			</div>
	        <div class="com-bor-box02">
	        <ul class="navi-ul01 clearfix">
				<li class="fr">金额单位(元)</li>
			</ul>
	        </div>
	        <div class="com-bor-box">
			<table id='displayTbl' width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
				<col width="2%" />
				<col width="4%" />
				<col width="10%" />
				<col width="8%" />
				<col width="2%" />
				<col width="2%" />
				<thead>
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>充值笔数</th>
					<th>充值金额（元）</th>
					<th>操作</th>
					<th>&nbsp;</th>
				</tr>
				</thead>
		      	<tbody>
		      </tbody>
			</table>
		</div>
	</div>
	
	<div id='viewCountDiv' style='display:none;'>
		<div class="com-bor-box com-bor-box01">
		<div class="deal">
			<div class="seach-top-inner">
				<form action="/">
					<h3 class="pdf10">交易明细</h3>
				</form>
			</div>
		</div>
		<form action="/" id='displayTbl'>
			<div class="w890 mb0">
            	<div class="com-bor-box02">
        		<ul class="navi-ul01 clearfix">
					<li class="fr">金额单位(元)</li>
				</ul>
       		 	</div>
			<table id='tblList' width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<col width="2%" />
			<col width="3%" />
			<col width="10%" />
			<col width="5%" />
			<col width="7%" />
			<col width="7%" />
			<col width="5%" />
			<col width="10%" />
			<col width="5%" />
			<col width="11%" />
			<col width="1%" />
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th>序号</th>
				<th>订单编号</th>
				<th>城市</th>
				<th>充值金额</th>
				<th>实付金额</th>
				<th>利润</th>
				<th>POS号</th>
				<th>订单状态</th>
				<th>订单创建时间</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
	      	<tbody>
	      </tbody>
		</table>
		<div class="null-box"></div>
		<p class="page-navi">
		<span class="fl">业务订单汇总详情：<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportChildified" onclick="exportExcel('${base}/businessOrder/exportFindRechargeOrder','queryForm');">导出Excel</a></span>
		</p>
			<div class="a-center btn-box">
				<a href="javascript:hideViewSumList();" class="orange-btn-h32">返回</a>
			</div>
		</div>
		</form>
		</div>
	</div>
</div>
	<!-- InstanceEndEditable --> </div>
<#include "../../footer.ftl"/>
<!-- InstanceBeginEditable name="pop" --> 
<!-- InstanceEndEditable --> 
</body>
<!-- InstanceEnd --></html>

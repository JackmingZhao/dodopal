<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>业务订单管理</title>
<!-- InstanceEndEditable -->
<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<#include "../../include.ftl"/>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../js/merchant/childMerProductOrder/childMerProductOrder.js"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<#include "../../header.ftl"/>
<div class="con-main" id="childMerProductOrderMain">  <!-- InstanceBeginEditable name="main" -->
<#include "../../childrenPosNav.ftl"/>
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form id="queryChildProductOrderForm"  method="post">
				<ul class="clearfix">
					<li class="w315">
						<label><span class="w100">日期范围：</span>
						<input name="orderDateStart" id="orderDateStart" type="text" class="com-input-txt w74"  placeholder="起始时间" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						-
						<input name="orderDateEnd" id="orderDateEnd" type="text" class="com-input-txt w74"  placeholder="结束时间" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					&nbsp;
					</label>
					</li>
					
					<li class="w190"><span class="w80">订单编号：</span>
						<input type="text" class="com-input-txt w88"  id="proOrderNum" name="proOrderNum"/>
					</li>
					
					<li><span class="w80">城市：</span>
							<input type="text" class="com-input-txt w88"  id="cityName" name="cityName"/>
					</li>
					
					 <li><span class="w80">订单状态：</span>
						<select name="proOrderState" id="proOrderState">
                            <option selected="selected" value="">--请选择--</option>
						</select>
					</li>  
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findChildMerProductOrderByPage();"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryChildProductOrderForm');"/>
					</li>
				</ul>
				<ul class="clearfix">
					<li class="w315">
						<label><span class="w100">充值金额范围：</span>
							<input type="text" class="com-input-txt w74" id="txnAmtStart" name="txnAmtStart"/>
							-
							<input type="text" class="com-input-txt w74" id="txnAmtEnd" name="txnAmtEnd"/>
						元</label>
					</li>
                    <li class="w190"><span class="w80">商户名称：</span>
						<input type="text" class="com-input-txt w88" id="merName" name="merName"/>
					</li>
                    <li><span class="w80">POS号：</span>
                    	<input type="text" class="com-input-txt w88" id="posCode" name="posCode"/>
                    </li>
			  </ul>
			</form>
		</div>
	</div>
<div class="com-bor-box ">
	<div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
		</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id= "childMerProductOrderTbl">
        <colgroup>
            <col width="2%" />
            <col width="3%" />
            <col width="13%" />
            <col width="8%" />
            <col width="6%" />
            <col width="8%" />
            <col width="8%" />
            <col width="8%" />
            <col width="12%" />
            <col width="8%" />
            <col width="14%" />
            <col width="6%" />
            <col width="6%" />
            <col width="2%" />
        </colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>订单编号</th>
					<th>商户名称</th>
					<th>城市</th>
					<th>充值金额</th>
					<th>实付金额</th>
					<th>利润</th>
					<th>POS号</th>
					<th>订单状态</th>
					<th>订单创建时间</th>
					<th>POS备注</th>
					<th class="a-center">操作</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			</table>
		<div class="null-box"></div>
			<p class="page-navi"><span class="fl">订单下载：<a  href="#"  onclick="excelChildProductOrder()" >Excel格式</a></span>
			</p>
	</div>
	<!-- InstanceEndEditable --> </div>
	<#include "childMerProductOrderView.ftl"/>
<#include "../../footer.ftl"/>

</body>
<!-- InstanceEnd --></html>

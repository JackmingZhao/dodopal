<div class="con-main" id="childMerOrderSumDetailMain"> <!-- InstanceBeginEditable name="main" -->
  <#include "../../childrenPosNav.ftl"/>
	<ul class="tab-list01 clearfix">
	  <@sec.accessControl permission="merchant.child.ordersum.recharge">
		<li><a href="${base}/childMerProductOrder/childMerProductOrderSum" class="cur">一卡通充值</a></li>
	  </@sec.accessControl>
	  <@sec.accessControl permission="merchant.child.ordersum.purchase">
		<li><a href="${base}/prvd/cardConsumCollect">一卡通消费</a></li>
	  </@sec.accessControl>
	</ul>
	
	<div class="com-bor-box com-bor-box01">
		<div class="deal">
		<div class="seach-top-inner">
			<form action="exportChildOrderSumDetail" id="childMerOrderSumDetailFrom">
			<input type="hidden" name="merNameDetail" id="merNameDetail" value=""></input>
			<input type="hidden" name="merCodeDetail" id="merCodeDetail" value=""></input>
			<input type="hidden" name="orderStateDetail" id="orderStateDetail" value=""></input>
			<input type="hidden" name="tradeDateStartDetail" id="tradeDateStartDetail" value=""></input>
			<input type="hidden" name="tradeDateEndDetail" id="tradeDateEndDetail" value=""></input>
			<h3 class="pdf10">交易明细</h3>
				<ul class="clearfix">
					<li>
						<label><span class="w70">商户名称：</span>
							<span class="a-left" style="width:300px;" id="merNameSpan"></span>
						</label>
						</li>
					<!--	<li>
						<label><span class="w70">POS号：</span>
							<span class="a-left" style="width:300px;" id="posSpan"></span>
						</label>
					</li> -->
				</ul>
			</form>
		</div>
	</div>
			<form action="/">
			<div class="w890 mb0">
			 <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
			<table width="100%" id="childOrderSumDetailTb" border="0" cellspacing="0" cellpadding="0" class="com-table01">
			<colgroup>
			<col width="1%" />
			<col width="3%" />
			<col width="8%" />
			<col width="8%" />
			<col width="4%" />
			<col width="7%" />
			<col width="7%" />
			<col width="6%" />
			<col width="8%" />
			<col width="8%" />
			<col width="5%" />
			<col width="7%" />
			<col width="5%" />
			<col width="1%" />
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th  class="a-center">序号</th>
				<th>订单编号</th>
				<th>商户名称</th>
				<th>城市</th>
				<th>充值金额</th>
				<th>实付金额</th>
				<th>利润</th>
				<th>卡号</th>
				<th>POS号</th>
				<th>订单状态</th>
				<th>订单创建时间</th>
				<th>POS备注</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div class="null-box"></div>
	    <p class="page-navi" id="childMerOrderSumDetailPaginator">
	    <span class="fl">信息下载：<a href="#"  onclick="exportExcel('exportChildOrderSumDetail','childMerOrderSumDetailFrom')">导出Excel</a></span>
		 </p>
			<div class="a-center btn-box">
				<input type="button" value="返回" class="orange-btn-h32" onclick="returnSum();" />
			</div>
		</div>
		</form>
	</div>
	<!-- InstanceEndEditable --> </div>
<div class="con-main" id="cardConsumeCollectDetailsForm"> <!-- InstanceBeginEditable name="main" -->
<#include "../../childrenPosNav.ftl"/>
	<ul class="tab-list01 clearfix">
		<@sec.accessControl permission="merchant.child.ordersum.recharge">
		  <li><a href="${base}/childMerProductOrder/childMerProductOrderSum" >一卡通充值</a></li>
		</@sec.accessControl>
	   <@sec.accessControl permission="merchant.child.ordersum.purchase">
		  <li><a href="${base}/prvd/cardConsumCollect" class="cur">一卡通消费</a></li>
	   </@sec.accessControl>
	</ul>
	
	<div class="com-bor-box com-bor-box01">
		<div class="deal">
		<div class="seach-top-inner">
			<form action="exportCardConsumCollectDetails" id="CardConsumDetailForm">
			<input type="hidden" id="merNameDetail" name="merNameDetail" value=""/>
			<input type="hidden" id="statesDetail" name="statesDetail" value=""/>
			<input type="hidden" id="startDateDetail" name="startDateDetail" value=""/>
			<input type="hidden" id="endDateDetail" name="endDateDetail" value=""/>
			
			<h3 class="pdf10">交易明细</h3>
				<ul class="clearfix">
					<li>
						<label><span class="w70">商户名称：</span>
							<span class="a-left" style="width:300px" id="merNameSpan"></span>
						</label>
					</li>
					<!-- <li>
						<label><span class="w70">POS号：</span>
							<span class="a-left" id="proCodeSpan"></span>
						</label>
					</li> -->
				</ul>
			</form>
		</div>
	</div>
			<div class="com-bor-box02">
	        <ul class="navi-ul01 clearfix">
				<li class="fr">金额单位(元)</li>
			</ul>
	        </div>
			<table cellspacing="0" id="cardConsumCollectDetailsTb" cellpadding="0" border="0" class="com-table01">
			<colgroup>
			<col width="2%">
			<col width="3%">
			<col width="8%">
			<col width="8%">
			<col width="4%">
			<col width="7%">
			<col width="7%">
			<col width="7%">
			<col width="7%">
			<col width="7%">
			<col width="7%">
			<col width="5%">
			<col width="8%">
			<col width="7%">
			<col width="2%">
			</colgroup><thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center">序号</th>
				<th>订单编号</th>
				<th>商户名称</th>
				<th>城市</th>
				<th>应付金额</th>
				<th>实付金额</th>
				<th>消费前金额</th>
				<th>消费后余额</th>
				<th>卡号</th>
				<th>POS号</th>
				<th>订单状态</th>
				<th>订单创建时间</th>
				<th>POS备注</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div class="null-box" style="display:none;"></div>
		<p class="page-navi" id="cardConsumCollectDetails"><span class="fl">交易记录下载：                                                                                     
		<a href="#"  onclick="exportExcel('exportCardConsumCollectDetails','CardConsumDetailForm')">导出Excel</a></span></p>
			<div class="a-center btn-box">
				<a href="#" onclick="returnSum();" class="orange-btn-h32">返回</a>
			</div>
		</div>
</div>

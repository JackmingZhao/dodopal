<div class="con-main" id="merDiscountDetail"> <!-- InstanceBeginEditable name="main" -->
	<div class="new-peo">
		<h3 class="tit-h3">折扣详情</h3>
		<div class="com-bor-box com-bor-box01">
			<form id="detailForm" action="/">
				<table class="base-table base-table01">
					<col width="120" />
					<col width="330" />
					<col width="120" />
					<tr>
						<th>折扣：</th>
						<td id="discountSpan"></td>
						<th>排序号：</th>
						<td id="sortSpan"></td>
					</tr>
					<tr>
						<th>启用标识：</th>
						<td id="activateSpan"></td>
						 <#if sessionUser.merType =='12'>
						<th>连锁直营网点：</th>
						<td colspan="3"id="childMerSpan" style="word-break:break-all"></td>
						<#else>
					    </#if>
					</tr>
                    <tr>
						<td colspan="4" class="a-center"><div class="btn-box"><a href="#" class="orange-btn-h32" onclick="btnReturn();">返回</a></div></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
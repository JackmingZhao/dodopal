<div class="con-main" id="merRoleDetail" style="display:none;">
	<h3 class="tit-h3">角色信息</h3>
	<div class="com-bor-box com-bor-box01">
	<form id="detailForm" action="#">
		<input type="hidden" id="merRoleId" >
		<input type="hidden" id="merRoleCode" >
		
		<table class="base-table">
			<col width="140" />
			<col width="320" />
			<col width="140" />
			<tr>
				<th><span class="red">*</span>角色名称：</th>
				<td>
					<input type="text" value="" class="com-input-txt w260" myPlaceholder="中文、数字、英文，长度在1-20个字符之间"  id="merRoleName" maxlength="20" />
					<div class="tip-error" id="merRoleNameVal"></div>
				</td>
				<th>描述：</th>
				<td>
					<input class="com-input-txt w260" id="description" maxlength="200" onkeydown="checkMaxlength(this,200);"></input>
				</td>
			</tr>
			<tr>
					<th><span class="red">*</span>角色权限：</th>
					<td colspan="3">
						<div class="jiao-box" id="functionTree" style="border: 1px solid silver;height:200px;overflow:auto;position:relative;"></div>
						<div class="tip-error" id="functionTreeVal"></div>
					</td>
			</tr>
			<tr>
				<td colspan="4" class="a-center">
					<div class="btn-box">
					<input type="submit" class="orange-btn-h32" value="保存"  onclick="return saveMerRole();"/>
					<input type="button" value="取消" class="orange-btn-text32" onclick="cancelEdit('merRole');" />
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
	</div>
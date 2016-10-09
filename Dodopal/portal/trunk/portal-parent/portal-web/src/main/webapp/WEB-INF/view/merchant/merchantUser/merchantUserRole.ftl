<div class="con-main" id="merchantUserRole" style="display:none;"> 
	<h3 class="tit-h3">分配角色</h3>
	<div class="com-bor-box com-bor-box01">
		
			<h4 class="tit-h4">用户基本资料</h4>
			<table class="base-table">
				<col width="140" />
				<col width="330" />
				<col width="120" />
				<tr>
					<th>用户名：</th>
					<td><span id="role_merUserName"></span></td>
					<th>启用标识：</th>
					<td><span id="role_activate"></span></td>
				</tr>
			</table>
			
			<h4 class="tit-h4">角色列表</h4>
			<form action="/">
			<div class="w890">
			<table cellspacing="0" cellpadding="0" border="0" class="com-table01" id="merchantUserRoleTable">
			<colgroup>
			<col width="1%">
			<col width="2%">
			<col width="5%">
			<col width="10%">
			<col width="40%">
			<col width="1%">
			</colgroup>
			<thead>
				<tr>
				<th>&nbsp;</th>
				<th class="a-center"><input type="checkbox" onclick="toggle(this,'merchantRoles');"></th>
				<th class="a-center">序号</th>
				<th>角色名称</th>
				<th>角色描述</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			</tbody	>
		</table>
			<div class="null-box" id="merchantUserRoleNullBox" ></div>
			<div class="a-center" style="margin-top:20px;">
			<input type="submit" class="orange-btn-h32" onclick="saveAssignUserRoles();return false;" value="保存" />
			<input type="button" value="取消" class="orange-btn-text32" onclick="cancelEditRole();" />
			</div>
			</div>
			</div>
			
			<input type="hidden" id="role_merchantUserId" >
		</form>
	</div>
</div>

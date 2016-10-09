<div id="viewPosTypeDialogToolbar" style="text-align:right;">
	<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewPosTypeDialog')">返回</a>
</div>
<div id="viewPosTypeDialog" style="align:center;" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewPosTypeDialogToolbar">
	<fieldset>
		<legend>POS型号</legend>
		<table  class="viewOnly">
		<tr>
			<th style="width:77px;">型号编码:</th>
			<td id="view_code" name="view_code"></td>
			<th style="width:77px;">型号名称:</th>
			<td name="view_name" id="view_name" ></td>
		</tr>
		<tr>
			<th>启用标识:</th>
			<td id="view_activate" name="view_activate">
			</td>
		</tr>
		<tr>
			<th valign="top">备注:</th>
			<td name="view_comments" id="view_comments" style="word-break:break-all"></td>
		</tr>	
		<tr>
			<th>创建时间:</th>
			<td id="view_createDate"></td>
			<th>编辑时间:</th>
			<td id="view_updateDate"></td>
		</tr>
		<tr>
			<th>创建人:</th>
			<td id="view_createUser"></td>
			<th>编辑人:</th>
			<td id="view_updateUser"></td>
		</tr>
	</table>
	</fieldset>
</div>
	
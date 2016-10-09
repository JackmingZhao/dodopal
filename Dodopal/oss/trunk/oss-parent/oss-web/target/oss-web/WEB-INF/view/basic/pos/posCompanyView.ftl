<div id="viewCompanyDialogToolbar" style="text-align:right;">
	<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewCompanyDialog')">返回</a>
</div>
<div id="viewCompanyDialog" style="align:center;" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewCompanyDialogToolbar">
	<fieldset>
		<legend>POS厂商</legend>
		<table  class="viewOnly">
			<tr>
				<th style="width:77px;">厂商编号:</th>
				<td name="view_code" id="view_code"></td>
				<th style="width:77px;">厂商名称:</th>
				<td name="view_name" id="view_name"></td>
			</tr>
			<tr>
				<th>厂商负责人:</th>
				<td name="view_charger" id="view_charger" ></td>
				<th>联系电话:</th>
				<td name="view_phone" id="view_phone"></td>
			</tr>
			<tr>
			   <#--
				<th>启用标识:</th>
				<td name="view_activate" id="view_activate">
				</td>-->
				<th>邮编:</th>
				<td name="view_zipCode" id="view_zipCode"></td>
				<th>传真:</th>
				<td name="view_fax" id="view_fax"></td>
			</tr>
			<tr>
				<th>邮箱:</th>
				<td name="view_mail" id="view_mail"></td>
				<th>厂商地址:</th>
				<td name="view_address" id="view_address"/></td>
			</tr>
	</table>
	</fieldset>
    <fieldset>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">创建人:</th>
                <td id="view_createUser"></td>
                <th style="width:77px;">创建时间:</th>
                <td id="view_createDate"></td>
            </tr>
            <tr>
                <th>编辑人:</th>
                <td id="view_updateUser"></td>
                <th>编辑时间:</th>
                <td id="view_updateDate"></td>
            </tr>
        </table>
        <br/>
    </fieldset>
</div>
	
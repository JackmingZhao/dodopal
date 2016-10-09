<div id="viewPosDialogToolbar" style="text-align:right;">
	<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewPosDialog')">返回</a>
</div>
<div id="viewPosDialog" style="align:center;" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#viewPosDialogToolbar">
	<fieldset>
		<legend>POS信息</legend>
		<table  class="viewOnly">
		<tr>
			<th style="width:77px;">POS编码:</th>
			<td name="view_code" id="view_code"></td>
			<th style="width:77px;">POS厂商:</th>
			<td id="view_posCompanyName" name="view_posCompanyName"></td>
		</tr>
		<tr>
			<th>POS型号:</th>
			<td id="view_posTypeName"></td>
			<th>POS状态:</th>
			<td id="view_status" >
			</td>
		</tr>
		<tr>
			<th>POS版本:</th>
			<td name="view_version" id="view_version"></td>
			<th>POS批次:</th>
			<td name="view_serialNo" id="view_serialNo"></td>
		</tr>
		<tr>
			<th>所属商户:</th>
			<td name="view_merchantName" id="view_merchantName"></td>
			<th>商户类型:</th>
			<td name="view_merchantType" id="view_merchantType"></td>
		</tr>
		<tr>
			<th>所属城市:</th>
			<td><span id="view_provinceName"></span>&nbsp;<span id="view_cityName"></span></td>
			<th>采购成本:</th>
			<td><span name="view_unitCost" id="view_unitCost" ></span>元</td>
		</tr>
		<tr>
			<th>是否绑定:</th>
			<td name="view_bind" id="view_bind" ></td>
			<th>绑定时间:</th>
			<td name="view_bundlingDate" id="view_bundlingDate" ></td>
		</tr>
		<tr>
			<th>限制笔数:</th>
			<td name="view_maxTimes" id="view_maxTimes" ></td>
		</tr>
		<tr>
			<th valign="top">备&nbsp;注:</th>
			<td name="view_comments" id="view_comments" style="word-break:break-all" colspan='3'></td>
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
	
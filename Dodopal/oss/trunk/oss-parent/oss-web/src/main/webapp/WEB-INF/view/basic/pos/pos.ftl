<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1989/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<#include "../../common/merchantSearchModel.ftl"/>
	<script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'> </script>
	<script type="text/javascript" src="../../js/common/merchantSearchModel.js"></script>
	<script type="text/javascript" src="../../js/basic/pos/pos.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/ajaxfileupload.js"></script>
	<title>OSS</title>
	<style type="text/css">
        table.edittable tr td span.comb input.combo-text.validatebox-text {
            width: 182px;
        }
	</style>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:85px;overflow: hidden;">
		<table id="posQueryCondition" class="viewtable">
			<tr>
				<th>POS编号:</th>
				<td><input type="text" id="posCodeQuery" maxlength="32"></td>
				 <th>POS型号:</th>
                <td><!--<input type="text" id="posTypeNameQuery" style="width:100px;">-->
                <select id="posTypeNameQuery" class="easyui-combobox" editable="false" style="width: 133px;" panelHeight="140">
				</select>
                </td>
				<th>POS厂商:</th>
				<td><input type="text" id="posCompanyNameQuery" ></td>
                <th>POS所属城市:</th>
                <td id="cityQuery"></td>
                <th></th>
                <td>
				<@sec.accessControl permission="pos.info.query">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPos(1);">查询</a>
				</@sec.accessControl>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('posQueryCondition');">重置</a>
                </td>
			</tr>
			<tr>
				<th>版本:</th>
				<td><input type="text" id="versionQuery"  style="width:126px;"></td>
				<th>是否绑定:</th>
				<td>
					<select id="bindFind"  class="easyui-combobox" editable="false" panelHeight="70" style="width: 133px;">
					<option value='' selected>-请选择-</option>
					<option value="0" >绑定</option>
					<option value="1">未绑定</option>
				</select>
				</td>
				<th>POS状态:</th>
				<td>
					<select id="statusQuery"  class="easyui-combobox" editable="false" style="width: 133px;" panelHeight="140">
						<option value="0" selected>启用</option>
						<option value="1">停用</option>
						<option value="2">作废</option>
						<option value="3">消费封锁</option>
						<option value="4">充值封锁</option>
						<option value='' >全部</option>
					</select>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="posTbl" fit="true"></table>
	</div>
	<div id="posTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="pos.info.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addPos" onclick="addPos()">添加</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editPos" onclick="editPos()">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deletePos" onclick="deletePos()">删除</a>
		</@sec.accessControl>

		<@sec.accessControl permission="pos.info.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activatePos" onclick="posOperation('OPER_ENABLE')">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.inactivate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="inactivatePos" onclick="posOperation('OPER_DISABLE')">停用</a>
		</@sec.accessControl>

		<@sec.accessControl permission="pos.info.bind">
			<a href="#" class="easyui-linkbutton" iconCls="icon-bind" plain="true" id="bindPos" onclick="posOperation('OPER_BUNDLING')">绑定</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.unbind">
			<a href="#" class="easyui-linkbutton" iconCls="icon-unbind" plain="true" id="unbindPos" onclick="posOperation('OPER_UNBUNDLING')">解绑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.import">
			<a href="${scriptUrl}/../template/pos.xlsx" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="templateDownload" >模板导出</a>
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-import" onclick="showDialog('importPosDialog');">导入</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.modifyCity">
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-city" onclick="modifyPosByBatch('modifyCityDialog');">批量修改城市</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.modifyVersion">
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-version" onclick="modifyPosByBatch('modifyVersionDialog');">批量修改版本</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.limit">
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-count" onclick="modifyPosByBatch('modifyLimitationDialog');">批量修改限制笔数</a>
		</@sec.accessControl>
		<@sec.accessControl permission="pos.info.export">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnExportPos">导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="posTblPagination"></div>
	<div id="posDialog" title="" draggable="false" maximized="true" toolbar="#posDialogToolbar">
		<input type="hidden" name="posId" id="posId"></input>
		<fieldset>
			<legend>POS信息</legend>
			<table class="edittable">
				<tr>
					<th>POS编码:</th>
					<td>
						<input name="code" id="code" type="text"  class="easyui-validatebox" style="width:198px;" maxlength=32 data-options="validType:'enNoUs[1,32]'" />
						<input name="codeStart" id="codeStart" type="text" style="width:90px;display:none;" class="easyui-validatebox" maxlength=12 data-options="validType:'posCode'" />
						<span id="score" style="display:none;">-</span>
						<input name="codeEnd" id="codeEnd" type="text" style="width:90px;display:none;" class="easyui-validatebox" maxlength=12 data-options="validType:'posCode'" />
						<a href="#" class="easyui-linkbutton" id="posCodes"  onclick="posCodes();">号段录入</a>
						<font color="red">*</font>
					</td>
					<th>POS厂商:</th>
					<td>
						<select id="posCompanyCode" class="easyui-combobox" editable="false">

						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>POS型号名称:</th>
					<td>
						<select id="posTypeCode" class="easyui-combobox" editable="false">

						</select>
						<font color="red">*</font>
					</td>
					<th>POS状态:</th>
					<td>
						<select id="status"  class="easyui-combobox" editable="false" style="width: 202px;">
							<option value=''>--请选择--</option>
							<option value="0" selected>启用</option>
							<option value="1">停用</option>
							<option value="2">作废</option>
							<option value="3">消费封锁</option>
							<option value="4">充值封锁</option>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>版本:</th>
					<td><input name="version" id="version" type="text" style="width:198px;"   class="easyui-validatebox" data-options="required:true, validType:'lengthRange[1,64]'"  maxlength="64"/><font color="red">*</font></td>
					<th>POS批次号:</th>
					<td><input name="serialNo" id="serialNo" type="text" style="width:198px;"  class="easyui-validatebox" data-options="required:true, validType:'numberEn[0,20]'" maxlength="20"/><font color="red">*</font></td>
				</tr>
				<tr>
					<th>所属城市:</th>
					<td id="cityCode"></td>
					<th>采购成本:</th>
					<td><input name="unitCost" id="unitCost" type="text" style="width:198px;text-align:right;" class="easyui-validatebox" data-options="validType:'amt'" maxLength="11"/> 元</td>
				</tr>
				<tr>
					<th>限制笔数:</th>
					<td><input name="maxTimes" id="maxTimes" style="width:198px;" type="text" maxLength="10" class="easyui-validatebox" data-options="validType:'number'" /></td>
				</tr>
				<tr>
					<th valign="top">备注:</th>
                    <td colspan="6">
                        <textarea rows="6" name="comments" id="comments" style="width: 582px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
                    </td>
				</tr>
			</table>
		</fieldset>
		<div id="posDialogToolbar" style="text-align:right;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePos()">保存</a>
			<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('posDialog')">取消</a>
		</div>
	</div>

	<div id="bindDialog" title="绑定" draggable="false" maximized="false">
		<br/>
		<br/>
		<table id="bindCondition" style="padding:4px;">
			<tr>
				<th>绑定类型:</th>
				<td><select id="merParentType" /></td>
				<td><input type="text" id="merCode" name="merCode" style="display:none;" disabled /></td>
				<td><input type="text" id="merName" name="merName" style="width: 100px;height:19px;" disabled /></td>
				<td><input type="button" onclick="showMerchantSearchDialog();" value="选择"/></td>
			</tr>
		</table>
	</div>

		<div id="modifyCityDialog" title="批量修改城市" draggable="false" maximized="false">
			<table class="edittable" >
				<tr>
					<th>省市:</th>
					<td id="modifyCity"></td>
				</tr>
			</table>

		</div>
		<div id="modifyVersionDialog" title="批量修改版本" draggable="false" maximized="false">
			<table class="edittable" >
				<tr>
					<th>版本:</th>
					<td>
						<input id="modifyVersion" name="modifyVersion" type="text" maxlength="64"> </input>
					</td>
				</tr>
			</table>
		</div>

		<div id="modifyLimitationDialog" title="批量修改限制笔数" draggable="false" maximized="false">
			<table class="edittable" >
				<tr>
					<th>限制笔数:</th>
					<td>
						<input id="modifyLimitation" name="modifyLimitation" type="text" maxLength="10"> </input>
					</td>
				</tr>
			</table>
		</div>

		<div id="importPosDialog" title="批量导入" draggable="false" maximized="false">
			<table class="edittable">
				<tr>
					<th>注：当需录入大量的POS信息时，可将POS信息以<span style="color:red;">Excel</span>文件的形式导入到系统中。</th>
				</tr>
			</table>
			<table class="edittable">
				<tr>
					<th><span style="color:blue;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件格式为：<span></th>
					<td></td>
				</tr>
				<tr>
					<th></th>
					<td>POS厂商：需输入POS厂商编码</td>
				</tr>
				<tr>
					<th></th>
					<td>POS型号：需输入POS型号编码</td>
				</tr>
				<tr>
					<th></th>
					<td>采购成本：数字格式以元为单位</td>
				</tr>
				<tr>
				</tr>
				<tr>
					<th>请选择文件:</th>
					<td>
						<input type="file" id="posFile" name="posFile"/>
					</td>
				</tr>
			</table>
		</div>
	<#include "posView.ftl"/>
</body>
</html>
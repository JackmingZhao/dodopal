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
	<#include "../../common/posSearchModel.ftl"/>
	<script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'> </script>
	<script type="text/javascript" src="../../js/common/merchantSearchModel.js"></script>
	<script type="text/javascript" src="../../js/basic/psamMgmt/psam.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/ajaxfileupload.js"></script>
	<script type="text/javascript" src="../../js/basic/psamMgmt/merchantSearchModel.js"></script>
	<script type="text/javascript" src="../../js/basic/psamMgmt/posSearchModel.js"></script>
	<title>OSS</title>
	<style type="text/css">
        table.edittable tr td span.comb input.combo-text.validatebox-text {
            width: 182px;
        }
	</style>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:85px;overflow: hidden;">
		<table id="psamQueryCondition" class="viewtable">
			<tr>
				<th>PSAM号:</th>
				<td><input type="text" id="samNoQuery" maxlength="32"></td>
				<th>通卡商户号:</th>
                <td><input type="text" id="mchntidQuery" style="width:100px;">
                </td>
				<th>通卡编号:</th>
				<td><input type="text" id="yktCodeQuery" ></td>
               	<th>通卡名称:</th>
				<td><input type="text" id="yktNameQuery" ></td>
                <th></th>
                <td>
				<@sec.accessControl permission="psamMgmt.psam.query">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findPsam(1);">查询</a>
				</@sec.accessControl>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('psamQueryCondition');">重置</a>
                </td>
			</tr>
			<tr>
				<th>POS号:</th>
				<td><input type="text" id="posIdQuery"  style="width:126px;"></td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="psamTbl" fit="true"></table>
	</div>
	<div id="psamTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="psamMgmt.psam.add">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addPsam" onclick="addPsam()">添加</a>
		</@sec.accessControl>
		<@sec.accessControl permission="psamMgmt.psam.modify">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editPsam" onclick="editPsam()">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="psamMgmt.psam.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deletePsam" onclick="deletePsam()">删除</a>
		</@sec.accessControl>

		<@sec.accessControl permission="psamMgmt.psam.activate">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="activateMer" onclick="activateMer()">启用商户</a>
		</@sec.accessControl>
	
		<@sec.accessControl permission="psamMgmt.psam.modifyPara">
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-version" onclick="modifyPara();">修改重新下载参数</a>
		</@sec.accessControl>
		<@sec.accessControl permission="psamMgmt.psam.signin">
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-version" onclick="signin();">签到</a>
		</@sec.accessControl>
		<@sec.accessControl permission="psamMgmt.psam.signoff">
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-version" onclick="signoff();">签退</a>
		</@sec.accessControl>
		<@sec.accessControl permission="psamMgmt.psam.import">
			<a href="${scriptUrl}/../template/psam.xlsx" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="templateDownload" >模板导出</a>
			<a href="#" class="easyui-linkbutton"  plain="true" iconCls="icon-import" onclick="showDialog('importPsamDialog');">导入</a>
		</@sec.accessControl>
		<@sec.accessControl permission="psamMgmt.psam.export">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnExportPsam">导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="psamTblPagination"></div>
	
	<div id="psamDialog" title="" draggable="false" maximized="true" toolbar="#psamDialogToolbar">
		<input type="hidden" name="posId" id="posId"></input>
		<fieldset>
			<legend>PSAM卡</legend>
			<table class="edittable">
			 <input name="id" id="id" type="hidden"/>
             <input name="samSignId" id="samSignId" type="hidden"/>
             <input name="posMertbId" id="posMertbId" type="hidden"/>
             <input name="posSeqId" id="posSeqId" type="hidden"/>
				<tr>
				 <th style="width:77px;">通卡公司:</th>
                <td>
                    <input class="easyui-combobox" id="icdcProA" style="width:206px;"
                           data-options="valueField:'id',textField:'name',required:true"/><font color="red">*</font>
                    <input name="icdcProACode" id="icdcProACode" type="hidden"/>
                </td>
                <th style="width:77px;">城市:</th>
                <td>
                    <input id="cityA" class="easyui-combobox" style="width: 202px;"
                           data-options="valueField:'id',textField:'name',required:true"/><font color="red">*</font>
                   <input name="cityACode" id="cityACode" type="hidden"/>
                </td>
			   </tr>
				<tr>
					<th>PSAM号:</th>
					<td><input name="psamCode" id="psamCode" type="text" style="width:198px;"   class="easyui-validatebox" data-options="required:true, validType:'number[0,12]'"  maxlength="12"/><font color="red">*</font></td>
					<th>通卡商户号:</th>
					<td><input name="merId" id="merId" type="text" style="width:198px;"  class="easyui-validatebox" data-options="validType:'number[0,12]'" maxlength="12"/></td>
				</tr>
				<tr>
				
				<th style="width:93px;">商户:</th>
				<td>
                    <input name="merCode" id="merCode" style="width:155px;" disabled/>
					<input type="button" onclick="findMerName();" id="finMER" style="height:24px;" value="选择"/>
					</td>
					<th>商户名称:</th>
				   <td> <input name="merName" id="merName" class="easyui-validatebox" style="width:198px;" disabled/></td>
				</tr>
				
				<tr>
				<th style="width:93px;">POS编号:</th>
				<td>
                    <input name="posCode" id="posCode" style="width:155px;" disabled/>
					<input type="button" onclick="findPosName();" id="finPos" style="height:24px;" value="选择"/>
					</td>
				
					<th>POS类型:</th>
                   <td>
                   <select id="posType"  class="easyui-combobox" editable="false" style="width: 202px;" panelHeight="70">
					<option value='' selected>---请选择---</option>
					<option value="0">web（家用机）</option>
					<option value="1">手持机（商用机）</option>
				   </select>
                   </td>
				</tr>
			</table>
		</fieldset>
		<div id="psamDialogToolbar" style="text-align:right;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePsam()">保存</a>
			<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('psamDialog')">取消</a>
		</div>
	</div>
	<div id="importPsamDialog" title="批量导入" draggable="false" maximized="false">
		<table class="edittable">
			<tr>
				<th>注：当需录入大量的PSAM信息时，可将POS信息以<span style="color:red;">Excel</span>文件的形式导入到系统中。</th>
			</tr>
		</table>
		<table class="edittable">
			<tr>
				<th><span style="color:blue;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件格式为：<span></th>
				<td></td>
			</tr>
			<tr>
				<th></th>
				<td>PSAM&nbsp;号：需输入PSAM号</td>
			</tr>
			<tr>
				<th></th>
				<td>通卡编码：需输入通卡编码</td>
			</tr>
			<tr>
				<th></th>
				<td>城市编码：需输入城市编码</td>
			</tr>
			<tr>
			</tr>
			<tr>
				<th>请选择文件:</th>
				<td>
					<input type="file" id="psamFile" name="psamFile"/>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
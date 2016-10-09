<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>额度管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="../../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/product/supplier/yktLimitInfo.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
	    <table id="queryCondition" class="viewtable">
	        <tr>
	            <th>通卡公司名称:</th>
	            <td><input type="text" id="yktNameQuery"/></td>
	            <th></th>
	            <td>
	            	<@sec.accessControl permission="supplier.icdc.limit.query">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="limitQuery(1);">查询</a>
	                </@sec.accessControl>
	                &nbsp;&nbsp;
	                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('queryCondition')">重置</a>
	            </td>
	        </tr>
	    </table>
	</div>
	<div region="center" border="false"><table id="icdcLimitInfoTbl" fit="true" ></table></div>
    <div id="icdcLimitInfoTblPagination"></div>
	<div id="icdcLimitInfoTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="supplier.icdc.limit.modify">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="modifyLimit();">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.limit.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="viewLimit();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.limit.export">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnSelExcCol" >导出Excel</a>
		</@sec.accessControl>
	</div>

	<!--画面隐藏值-->
	<input type="hidden" id="limitId"></input>
	
	<!--编辑额度信息-->
	<div id="modifyLimitDialog" title="" draggable="false" maximized="true" toolbar="#modifyLimitDialogToolbar" style="display:none;">
		<div id="modifyLimitDialogToolbar" style="text-align:right;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveLimit()">保存</a>
			<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('modifyLimitDialog');">取消</a>
		</div>
		<fieldset>
			<legend>额度信息</legend>
			<table id="modifyLimitTable" class="edittable">
				<tr>
					<th style="width:93px;">通卡公司名称:</th>
					<td>
						<input id="yktName" type="text" style="width:195px;" disabled="disabled"/>
					</td>
					<th style="width:93px;">有效期:</th>
					<td>
						<input id="yktExpireDate" type="text" style="width:195px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}'});"/>
					</td>
				</tr>
				<tr>
					<th >报警额度:</th>
					<td >
						<input id="yktWarnLimit" type="text" style="text-align:right;width:195px;"  class="easyui-validatebox"  
							data-options="required:true" maxLength="8"
							onkeypress = 'return /^\d$/.test(String.fromCharCode(event.keyCode||event.keycode||event.which))'
							oninput= 'this.value = this.value.replace(/\D+/g, "")'
							onpropertychange='if(!/\D+/.test(this.value)){return;};this.value=this.value.replace(/\D+/g, "")'/>&nbsp;元
						<font color="red">*</font>
					</td>
					<th>终止充值额度:</th>
					<td>
						<input id="yktStopLimit" type="text" style="text-align:right;width:195px;"  class="easyui-validatebox" 
							data-options="required:true" maxLength="8"
							onkeypress = 'return /^\d$/.test(String.fromCharCode(event.keyCode||event.keycode||event.which))'
							oninput= 'this.value = this.value.replace(/\D+/g, "")'
							onpropertychange='if(!/\D+/.test(this.value)){return;};this.value=this.value.replace(/\D+/g, "")'/>&nbsp;元
						<font color="red">*</font>
					</td>
			    </tr>
				<tr>
					<th>购买总金额:</th>
					<td><input id="yktTotalAmtLimit" type="text" style="text-align:right;width:195px;" disabled="disabled"/>&nbsp;元</td>
					<th>剩余额度:</th>
					<td><input id="yktSurPlusLimit" type="text" style="text-align:right;width:195px;" disabled="disabled"/>&nbsp;元</td>
				</tr>
			    <tr>
					<th valign="top" style="width:93px;">备注:</th>
					<td rowspan="6" colspan="3">
						<textarea rows="6" id="remarkShow" style="width: 535px;" 
								  onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" 
								  onKeyPress="return onmykeypress(this);"  maxlength="40"></textarea>
				   </td>
				</tr>
			</table>
		</fieldset>
	</div>
	<!--查看额度信息-->
	<div id="viewLimitDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewLimitDialogToolbar">
		<div id="viewLimitDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="hideDialog('viewLimitDialog');">返回</a>
		</div>
	    <fieldset>
	        <legend>额度信息</legend>
	        <table class="viewOnly">
				<tr>
					<th style="width:77px;">通卡公司名称:</th>
					<td name="view_yktName" id="view_yktName"></td>
					<th style="width:77px;">有效期:</th>
					<td id="view_yktExpireDate" name="view_yktExpireDate"></td>
				</tr>
				<tr>
					<th>报警额度:</th>
					<td name="view_yktWarnLimit" id="view_yktWarnLimit" ></td>
					<th>终止充值额度:</th>
					<td name="view_yktStopLimit" id="view_yktStopLimit" ></td>
				</tr>
				<tr>
					<th>购买总金额:</th>
					<td name="view_totalAmtLimit" id="view_totalAmtLimit" ></td>
					<th>剩余额度:</th>
					<td name="view_surPlusLimit" id="view_surPlusLimit" ></td>
				</tr>
				<tr>
					<th valign="top">备&nbsp;注:</th>
					<td name="view_remark" id="view_remark" style="word-break:break-all" colspan='3' rowspan="1"></td>
				</tr>
	        </table>
	        <br/>
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
	
</body>
</html>
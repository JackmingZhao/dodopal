<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>额度批次管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="../../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/product/supplier/yktLimitBatchInfo.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:100px;overflow: hidden;">
		<form id="listForm" action="exportYktLimitBatchInfo" method="post">  
		    <table id="queryCondition" class="viewtable">
		        <tr>
		            <th>通卡公司名称:</th>
		            <td><input type="text" id="yktNameQuery" name = "yktNameQuery" style="width:175px"/></td>
		            <th>申请人:</th>
		            <td><input type="text" id="applyUserNameQuery" name = "applyUserNameQuery" style="width:80px"/></td>
		            <th>申请日期:</th>
		            <td>
						<input type="text" style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#applyDateEndQuery').val()});" id="applyDateStartQuery" name="applyDateStartQuery"/>
						-
						<input type="text"  style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#applyDateStartQuery').val()});" id="applyDateEndQuery" name="applyDateEndQuery"/>
					</td>
	                <th>申请金额:</th>
					<td>
						<input type="text" id="applyAmtStartQuery" name="applyAmtStartQuery" style="width:80px;"> -
						<input type="text" id="applyAmtEndQuery" name="applyAmtEndQuery" style="width:80px;">
					</td>
					<th></th>
		            <td>
		            	<@sec.accessControl permission="supplier.icdc.batch.query">
		                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="limitBatchQuery(1);">查询</a>
		                </@sec.accessControl>
		                &nbsp;&nbsp;
		                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('queryCondition')">重置</a>
		            </td>
		        </tr>
	            <tr>
	                <th>财务打款额度:</th>
					<td>
						<input type="text" id="financialPayAmtStartQuery" name="financialPayAmtStartQuery" style="width:80px;"> -
						<input type="text" id="financialPayAmtEndQuery" name="financialPayAmtEndQuery" style="width:80px;">
					</td>
		            <th>审核人:</th>
		            <td><input type="text" id="auditUserNameQuery" name = "auditUserNameQuery" style="width:80px"/></td>
		            <th>审核日期:</th>
		            <td>
						<input type="text" style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#auditDateEndQuery').val()});" id="auditDateStartQuery" name="auditDateStartQuery"/>
						-
						<input type="text"  style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#auditDateStartQuery').val()});" id="auditDateEndQuery" name="auditDateEndQuery"/>
					</td>
		           <th>审核状态:</th>
		            <td>
		                <select id="auditStateQuery" name="auditStateQuery" class="easyui-combobox" editable="false" panelHeight="100" style="width: 180px;">
		                    <option value='' selected="selected">--请选择-</option>
		                    <option value='0'>未审核</option>
		                    <option value='1'>审核通过</option>	                    
							<option value='2'>审核拒绝</option>
		                </select>
		            </td>
					<th></th>
					<td></td>
				</tr>
	            <tr>
	                <th>通卡增加额度:</th>
					<td>
						<input type="text" id="yktAddLimitStartQuery" name="yktAddLimitStartQuery" style="width:80px;"> -
						<input type="text" id="yktAddLimitEndQuery" name="yktAddLimitEndQuery" style="width:80px;">
					</td>
		            <th>复核人:</th>
		            <td><input type="text" id="checkUserNameQuery" name = "checkUserNameQuery" style="width:80px"/></td>
		            <th>复核日期:</th>
		            <td>
						<input type="text" style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#checkDateEndQuery').val()});" id="checkDateStartQuery" name="checkDateStartQuery"/>
						-
						<input type="text"  style="width:80px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#checkDateStartQuery').val()});" id="checkDateEndQuery" name="checkDateEndQuery"/>
					</td>
		           <th>复核状态:</th>
		            <td>
		                <select id="checkStateQuery" name="checkStateQuery" class="easyui-combobox" editable="false" panelHeight="100" style="width: 180px;">
		                    <option value='' selected="selected">--请选择-</option>
		                    <option value='0'>待复核</option>
		                    <option value='1'>复核中</option>	                    
							<option value='2'>复核完了</option>
		                </select>
		            </td>
					<th></th>
					<td></td>
				</tr>
		    </table>
	    </form>
	</div>
	<div region="center" border="false"><table id="icdcLimitBatchInfoTbl" fit="true" ></table></div>
    <div id="icdcLimitBatchInfoTblPagination"></div>
	<div id="icdcLimitBatchInfoTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="supplier.icdc.batch.add">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="applyBuyLimit();">申请购买额度</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.batch.modify">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="modifyLimitBatch();">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.batch.delete">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteLimitbatch();">删除</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.batch.audit">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-count" plain="true" onclick="auditLimitbatch();">审核</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.batch.check">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-user" plain="true" onclick="checkLimitbatch();">复核</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.batch.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="viewBatchInfo();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.batch.export">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnSelExcCol" >导出Excel</a>
		</@sec.accessControl>
	</div>
	
	<!--画面隐藏值-->
	<input type="hidden" id="userNameHidden" value="${(SESSION_USER.name)!''}"></input>
	<input type="hidden" id="userIdHidden" value="${(SESSION_USER.id)!''}"></input>
	<input type="hidden" id="batchCodeHidden"></input>
	<input type="hidden" id="idHidden" ></input>
	<input type="hidden" id="yktCodeHidden" ></input>
	
	<!--额度批次申请单-->
	<div id="limitBatchDialog" title="" draggable="false" maximized="true" toolbar="#limitDialogToolbar" style="display:none;">
		<div id="limitDialogToolbar" style="text-align:right;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="applyBuyLimitClick()" id="applyButton" style="display:none;">申请</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveLimitBatchClick()" id="saveButton" style="display:none;">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="passApplyClick()" id="passApplyButton" style="display:none;">通过</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="refuseApplyClick()" id="refuseApplyButton" style="display:none;">拒绝</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="checkLimitBatchClick()" id="checkButton" style="display:none;">保存</a>
			<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('limitBatchDialog');">取消</a>
		</div>
		<fieldset id="applyLimitBatchTable">
			<legend>额度购买批次申请单</legend>
			<table class="edittable">
				<tr>
					<th width="15%">通卡公司:</th>
					<td >
                    	<input class="easyui-combobox" id="yktName" panelHeight="150" editable="false" style="width:303px;" data-options="valueField:'id',textField:'name',required:true"/>
                    	<font color="red">*</font>
                	</td>
					<th width="15%">申请说明:</th>
					<td width="35%" rowspan="4">
						<textarea rows="4" id="applyExplaination" style="width: 300px;height:120px;" 
								  onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" 
								  onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
				   </td>
				</tr>
				<tr>
					<th >申请额度:</th>
					<td >
						<input id="applyAmtLimit" type="text" style="text-align:right;width:300px;" maxLength="11"/>&nbsp;元
						<font color="red">*</font>
					</td>					
			    </tr>
				<tr>
					<th>申请日期:</th>
					<td>
						<input id="applyDate" type="text" style="width:300px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						<font id="yktNameFont" color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>申请人:</th>
					<td>
						<input id="applyUserName" type="text" style="width:300px;" disabled="disabled"/>
					</td>
				</tr>
			</table>
		</fieldset>
	    <fieldset id="auditLimitBatchTable">
	        <table class="edittable">
				<tr>
					<th width="15%">财务打款额度:</th>
					<td width="35%">
							<input id="financialPayAmt" type="text" style="text-align:right;width:300px;" maxLength="11">&nbsp;元
						<font color="red">*</font>
					</td>
					<th width="15%">审核人:</th>
					<td width="35%">
						<input id="auditUserName" type="text" style="width:300px;"  disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<th>打款手续费:</th>
					<td>
						<input id="financialPayFee" type="text" style="text-align:right;width:300px;"/>&nbsp;元
					</td>
					<th>审核说明:</th>
					<td rowspan="4">
						<textarea rows="4" id="auditExplaination" style="width: 300px;height:120px;" 
								  onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" 
								  onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
				   </td>	
				</tr>	
				<tr>
					<th>付款渠道:</th>
					<td>
						<input type="text" id="paymentChannel" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" 
								  onKeyPress="return onmykeypress(this);" style="width: 300px;" maxlength="60">						
					</td>
				</tr>
				<tr>
					<th>打款日期:</th>
					<td>
						<input id="financialPayDate" type="text" style="width:300px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						<!--<font id="financialPayDateFont" color="red">*</font>-->
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>审核日期:</th>
					<td>
						<input id="auditDate" type="text" style="width:300px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						<font color="red">*</font>
					</td>
				</tr>
	        </table>
	        <br/>
	    </fieldset>
  		<fieldset id="checkLimitBatchTable">
	        <table class="edittable">
				<tr>
					<th width="15%">通卡已加额度:</th>
					<td width="35%">
						<input id="oldYktAddLimit" type="text" style="width:300px;text-align:right;"  disabled="disabled"/>&nbsp;元						
					</td>
					<th width="15%">复核说明:</th>
					<td width="35%" rowspan="5">
						<textarea rows="5" id="checkExplaination" style="width: 300px;height:160px;" 
								  onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" 
								  onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
				   </td>
				</tr>
				<tr>
					<th>通卡新加额度:</th>
					<td width="35%">
							<input id="newYktAddLimit" type="text" style="text-align:right;width:300px;" maxLength="11"/>&nbsp;元
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>通卡加款日期:</th>
					<td>
						<input id="yktAddLimitDate" type="text" style="width:300px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>复核日期:</th>
					<td>
						<input id="checkDate" type="text" style="width:300px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th >复核人:</th>
					<td width="35%">
						<input id="checkUserName" type="text" style="width:300px;"  disabled="disabled"/>
					</td>
				</tr>
	        </table>
	        <br/>
	    </fieldset>
	</div>
	

	<!--查看额度批次详细信息-->
	<div id="viewLimitBatchDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewLimitBatchDialogToolbar">
		<div id="viewLimitBatchDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="hideDialog('viewLimitBatchDialog');">返回</a>
		</div>
	    <fieldset>
	        <legend>额度批次信息</legend>
	        <table class="viewOnly">
				<tr>
					<th width="15%" >通卡公司名称:</th>
					<td width="35%" name="view_yktName" id="view_yktName"></td>
					<th width="15%" >购买批次:</th>
					<td width="35%" name="view_batchCode" id="view_batchCode"></td>
				</tr>
				<tr>
					<th>申请额度:</th>
					<td name="view_applyAmtLimit" id="view_applyAmtLimit"></td>
					<th>申请人:</th>
					<td name="view_applyUserName" id="view_applyUserName"></td>
				</tr>
				<tr>
					<th>申请日期:</th>
					<td name="view_applyDate" id="view_applyDate"></td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th valign="top">申请说明:</th>
					<td name="view_applyExplaination" id="view_applyExplaination" style="word-break:break-all" colspan='3' rowspan="1"></td>
				</tr>
				<tr>
					<th>财务打款额度:</th>
					<td name="view_financialPayAmt" id="view_financialPayAmt"></td>
					<th>打款手续费:</th>
					<td name="view_financialPayFee" id="view_financialPayFee"></td>
				</tr>
				<tr>
					<th>打款日期:</th>
					<td name="view_financialPayDate" id="view_financialPayDate"></td>
					<th>审核人:</th>
					<td name="view_auditUserName" id="view_auditUserName"></td>
				</tr>
				<tr>
					<th>审核状态:</th>
					<td name="view_auditStateView" id="view_auditStateView"></td>
					<th>审核日期:</th>
					<td name="view_auditDate" id="view_auditDate"></td>
				</tr>
				<tr>
					<th valign="top">付款渠道:</th>
					<td name="view_paymentChannel" id="view_paymentChannel" style="word-break:break-all" colspan='3' rowspan="1"></td>
				</tr>	
				<tr>
					<th valign="top">审核说明:</th>
					<td name="view_auditExplaination" id="view_auditExplaination" style="word-break:break-all" colspan='3' rowspan="1"></td>
				</tr>				
				<tr>
					<th>通卡增加额度:</th>
					<td name="view_yktAddLimit" id="view_yktAddLimit"></td>
					<th>通卡加款日期:</th>
					<td name="view_yktAddLimitDate" id="view_yktAddLimitDate"></td>
				</tr>
				<tr>
					<th>复核日期:</th>
					<td name="view_checkDate" id="view_checkDate"></td>
					<th>复核人:</th>
					<td name="view_checkUserName" id="view_checkUserName"></td>
				</tr>
				<tr>
					<th>复核状态:</th>
					<td name="view_checkStateView" id="view_checkStateView"></td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th valign="top">复核说明:</th>
					<td name="view_checkExplaination" id="view_checkExplaination" style="word-break:break-all" colspan='3' rowspan="1"></td>
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
	                <th  width="15%" >创建人:</th>
	                <td  width="35%" id="view_createUser"></td>
	                <th  width="15%" >创建时间:</th>
	                <td  width="35%" id="view_createDate"></td>
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
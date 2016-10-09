<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>调账管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/account/accountMgmt/merchantSearch.js"></script>
	<script type="text/javascript" src="../../js/account/accountMgmt/merchantUserSearch.js"></script>
    <script type="text/javascript" src="../../js/account/accountMgmt/adjustment.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:110px;overflow: hidden;">
	    <table id="queryCondition" class="viewtable">
	        <tr>
	        	<th>客户名称:</th>
				<td><input type="text" id="customerNameQuery" name="customerNameQuery" maxlength="128"></td>
				<th>客户号:</th>
				<td><input type="text" id="customerNoQuery" name="customerNoQuery" maxlength="40"></td>
				<th>申请人:</th>
				<td>
                	<input type="text" id="accountAdjustApplyUserQuery" name="accountAdjustApplyUserQuery" maxlength="40">
				</td>
				<th>申请日期:</th>
				<td>
				<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#accountAdjustApplyEndDate').val()});" id="accountAdjustApplyStartDate" name="accountAdjustApplyStartDate"/>
				-
				<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#accountAdjustApplyStartDate').val()});" id="accountAdjustApplyEndDate" name="accountAdjustApplyEndDate"/>
				</td>
				
				<th></th>
				<td>
				<@sec.accessControl permission="accountMgmt.adjustment.query">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findAdjustments(1);">查询</a>
				</@sec.accessControl>
				<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAdjustmentQuery();">重置</a>
                </td>
			</tr>
			<tr>
				 <th>账户类型:</th>
                <td>
                	<select id="fundTypeQuery"  class="easyui-combobox" editable="false" panelHeight="70" style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="0" >资金账户</option>
						<option value="1">授信账户</option>
					</select>
                </td>
				<th>调账类型:</th>
                <td>
					<select id="accountAdjustTypeQuery"  class="easyui-combobox" editable="false" panelHeight="70" style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="7" >正调账</option>
						<option value="8">反调账</option>
					</select>
				</td>
               <th>审核人:</th>
                <td>
                <input type="text" id="accountAdjustAuditUserQuery" name="accountAdjustAuditUserQuery" maxlength="40">
				</td>
                <th>审核时间:</th>
				<td>
				<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#accountAdjustAuditEndDate').val()});" id="accountAdjustAuditStartDate" name="accountAdjustAuditStartDate"/>
				-
				<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#accountAdjustAuditStartDate').val()});" id="accountAdjustAuditEndDate" name="accountAdjustAuditEndDate"/>
				</td>
			</tr>
			<tr>
				<th>状&nbsp;&nbsp;&nbsp;态:</th>
				<td>
					<select id="accountAdjustStateQuery"  class="easyui-combobox" editable="false" panelHeight="120" style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="0" >未审批</option>
						<option value="1">调账成功</option>
						<option value="2" >调账失败</option>
						<option value="3">审批不通过</option>
					</select>
				</td>
			</tr>
	    </table>
	</div>
	<div region="center" border="false">
		<table id="adjustmentTbl" fit="true" ></table>
	</div>
	<div id="adjustmentToolbar" class="tableToolbar">
		<@sec.accessControl permission="accountMgmt.adjustment.apply">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="applyAdjustment();">调账申请</a>
		</@sec.accessControl>
		<@sec.accessControl permission="accountMgmt.adjustment.modify">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAdjustment();">调账修改</a>
		</@sec.accessControl>
		<@sec.accessControl permission="accountMgmt.adjustment.delete">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteAdjustment();">调账删除</a>
		</@sec.accessControl>
		<@sec.accessControl permission="accountMgmt.adjustment.approve">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" onclick="approveAdjustment();">调账执行</a>
		</@sec.accessControl>
		<@sec.accessControl permission="accountMgmt.adjustment.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="adjustmentView();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="accountMgmt.adjustment.export">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="adjustmentTblPagination"></div>
	<div id="adjustmentDialog" title="调账管理" draggable="false" maximized="true" toolbar="#adjustmentDialogToolbar">
		<form id="adjustmentDialogForm">
			<input type="hidden" name="adjustmentId" id="adjustmentId"></input>
			<input type="hidden" name="accountAdjustCode" id="accountAdjustCode"></input>
			<table  class="edittable">
				<tr>
					<th>调账方式:</th>
					<td>
						<label><input  name="accountAdjustType" type="radio" value="7" />正调账</label> 
						<label><input  name="accountAdjustType" type="radio" value="8" />反调账 </label><font color="red">*</font> 
					</td>
					<th>客户类型:</th>
					<td>
						<label><input  name="customerType" id="customerTypePerson" type="radio" value="0" />个人</label> 
						<label><input  name="customerType" id="customerTypeMer" type="radio" value="1" />企业</label><font color="red">*</font> 
					</td>
				</tr>
				<tr>
					<th>客户号:</th>
					<td><input name="customerNo" id="customerNo" type="text" disabled="true" style="width:188px;"/></td>
					<th>客户名称:</th>
					<td><input name="customerName" id="customerName" type="text" disabled="true" style="width:188px;"/></td>
				</tr>
				<tr>
					<th>账户类型:</th>
					<td>
						<label id="fundTypeFund"><input  name="fundType"  type="radio" value="0" />资金账户</label> 
						<label id="fundTypeAuthorized"><input  name="fundType"  type="radio" value="1" />授信账户</label><font color="red">*</font> 
					</td>
					<th>账户号:</th>
					<td>
						<input name="fundAccountCode" id="fundAccountCode" type="text" disabled="true" style="width: 188px;"/>
						<input name="fundAcctCode" id="fundAcctCode" type="hidden"/>
						<input name="authorizedAcctCode" id="authorizedAcctCode" type="hidden"/>
					</td>
				</tr>
				<tr>
					<th>调账金额:</th>
					<td><input name="accountAdjustAmount" id="accountAdjustAmount" type="text" style="text-align:left;width:188px;" class="easyui-validatebox" data-options="required:true,validType:'amount'"/>&nbsp;元<font color="red">*</font> </td>
					<th id="creditAmtTh">授信额度:</th>
					<td id="creditAmtTd"><input name="creditAmt" id="creditAmt" type="text" style="width: 188px;" disabled="true" />&nbsp;元</td>
				</tr>
				<tr id="creditAmtTr">
					<th>可用额度:</th>
					<td><input name="availableBalance" id="availableBalance" type="text" style="width: 188px;" disabled="true" />&nbsp;元</td>
					<th>调帐后可用金额:</th>
					<td><input name="newAvailableBalance" id="newAvailableBalance" type="text" style="width: 188px;" disabled="true" />&nbsp;元</td>
				</tr>
				<tr>
					<th valign="top">调账原因:</th>
                    <td colspan="4">
                        <textarea rows="6" name="accountAdjustReason" id="accountAdjustReason" style="width: 500px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="255" class="easyui-validatebox" data-options="required:true"/></textarea>
                        <font color="red">*</font> 
                    </td>
				</tr>
				<tr id="auditLine" style="display:none;">
                    <th valign="top">审核说明:</th>
                    <td colspan="4">
                        <textarea rows="6" name="accountAdjustAuditExplain" id="accountAdjustAuditExplain" style="width: 500px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="255" /></textarea>
                    </td>
                    
				</tr>
			</table>
			<div id="adjustmentDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="saveAdjustment" onclick="saveAdjustment()">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="updateAdjustment" onclick="updateAdjustment()">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="approveAdjustmentBtn" onclick="approveAdjustmentAction()">执行</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="rejectAdjustmentBtn" onclick="rejectAdjustment()">拒绝</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('adjustmentDialog')">取消</a>
			</div>
		</form>
	</div>
	
	<div id="approveDialog" title="批量执行" draggable="false" toolbar="#approveDialogToolbar">
			<input type="hidden" name="adjustmentIds" id="adjustmentIds"></input>
			<br/>
			<br/>
			<table  class="edittable">
				<tr>
				</tr>
				<tr>
                    <th valign="top">审核说明:</th>
                    <td colspan="6">
                        <textarea rows="6" name="auditExplain" id="auditExplain" style="width: 500px;" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"  maxlength="255" /></textarea>
                    </td>
				</tr>
			</table>
			<div id="approveDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="approveAdjustmentList()">执行</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="rejectAdjustmentList()">拒绝</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('approveDialog')">取消</a>
			</div>
	</div>
	
	<div id="viewAdjustmentDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewAdjustmentDialogToolbar">
		<div id="viewAdjustmentDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewAdjustmentDialog');">返回</a>
		</div>
	    <fieldset>
	        <legend>调账信息</legend>
	        <table class="viewOnly">
				<tr>
					<th style="width:77px;">调账单编号:</th>
					<td name="view_accountAdjustCode" id="view_accountAdjustCode"></td>
					<th style="width:77px;">调账方式 :</th>
					<td id="view_accountAdjustTypeStr" name="view_sourceOrderNumStr"></td>
				</tr>
				<tr>
					<th>客户类型 :</th>
					<td name="view_customerTypeStr" id="view_customerTypeStr" ></td>
					<th>客户号:</th>
					<td name="view_customerNo" id="view_customerNo" ></td>
				</tr>
				<tr>
					<th>客户名称:</th>
					<td id="view_customerName"></td>
					<th>账户类型:</th>
					<td id="view_fundTypeStr"></td>
				</tr>
				<tr>
					<th>账户号 :</th>
					<td id="view_fundAccountCode"></td>
					<th>调账金额(元):</th>
					<td id="view_accountAdjustAmountStr" ></td>
				</tr>
				<tr>
					<th>调账原因:</th>
					<td id="view_accountAdjustReason" style="word-break:break-all" ></td>
					<th>状态:</th>
					<td><span id="view_accountAdjustStateStr"></td>
				</tr>
				<tr>
					<th>申请人:</th>
					<td id="view_accountAdjustApplyUser"></td>
					<th>申请时间:</th>
					<td id="accountAdjustApplyDate"></td>
				</tr>
				
				<tr>
					<th>审核人:</th>
					<td id="view_accountAdjustAuditUser"></td>
					<th>审核时间:</th>
					<td id="accountAdjustAuditDate"></td>
				</tr>
				<tr>
					<th>审核说明:</th>
					<td id="view_accountAdjustAuditExplain" style="word-break:break-all" ></td>
					<th>完成时间 :</th>
					<td id="completeDate"></td>
				</tr>
				<tr>
					<th>创建人:</th>
					<td id="view_createUser"></td>
					<th>创建时间:</th>
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
	
	<div id="merchantSearchDialog" title="企业" draggable="false" resizable="true">
		 <div class="easyui-layout" fit="true">  
	        <!--商户查询条件 开始-->
			<div region="north" border="false" style="height:60px;overflow: hidden;">
				<table id="merchantQueryCondition" class="viewtable" style="padding:4px;">
					<tr>
						<td>
							<select id="queryType" class="easyui-combobox" editable="false" style="width: 100px;">
									<option value="name" selected>商户名称</option>
									<option value="code">商户编码</option>
							</select>
							<input name="merchantQuery" id="merchantQuery" type="text" class="easyui-validatebox" style="width:200px; height:19px;"/>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMerchantName();">查询</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('merchantQueryCondition');">重置</a>
								&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="selectMerchants();">确定</a>
						</td>
					</tr>
				</table>
			</div>
			<!-- 商户查询条件 结束 -->
	        <!--商户列表显示 开始 -->
			<div region="center" border="false" style="overflow: hidden;">
				<table id="merchantListTbl"></table>
			</div>
			<div id="merchantListTblPagination"></div>
			<!-- 商户列表显示 结束 -->
    	 </div>  
	</div>
	
	<div id="merchantUserSearchDialog" title="个人" draggable="false" resizable="true">
		 <div class="easyui-layout" fit="true">  
	        <!--商户查询条件 开始-->
			<div region="north" border="false" style="height:60px;overflow: hidden;">
				<table id="merchantUserQueryCondition" class="viewtable" style="padding:4px;">
					<tr>
						<td>
							<select id="userQueryType" class="easyui-combobox" editable="false" style="width: 100px;">
									<option value="custName" selected>用户姓名</option>
									<option value="custNo">用户编码</option>
							</select>
							<input name="merchantUserQuery" id="merchantUserQuery" type="text" class="easyui-validatebox" style="width:200px; height:19px;"/>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMerchantUser();">查询</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('merchantUserQueryCondition');">重置</a>
								&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="selectMerchatUser();">确定</a>
						</td>
					</tr>
				</table>
			</div>
			<!-- 商户查询条件 结束 -->
	        <!--商户列表显示 开始 -->
			<div region="center" border="false" style="overflow: hidden;">
				<table id="merchantUserListTbl"></table>
			</div>
			<div id="merchantUserListTblPagination"></div>
			<!-- 商户列表显示 结束 -->
    	 </div>  
	</div>
</body>
</html>
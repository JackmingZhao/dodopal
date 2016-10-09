<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<meta http-equiv="Pragma" contect="no-cache">
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0">   
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/merchant/personal/merchantList.js"></script>
	<script type="text/javascript" src="../../js/utils/jsFilterUtil.js"></script>
	<script type='text/javascript' src='${scriptUrl}/util/moneyFormatter.js'> </script>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<style type="text/css">
        table.edittable tr td span.comb input.combo-text.validatebox-text {
            width: 182px;
        }
	</style>
</head>
<title>企业用户信息查看</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:110px;overflow: hidden;">
		<table id="findTB" class="viewtable">
			<tr>
				<th>用户名:</th>
				<td>
					<input type="text" id="merUserName" name ="merUserName" style="width: 123px;"/>
				</td>
				<th>联系人:</th>
				<td>
					<input type="text" id="merUserNickName" name ="merUserNickName" style="width: 175px;"/>
				</td>
				<th>所属商户名称:</th>
				<td>
					<input type="text" id="merchantName" name ="merchantName" style="width: 123px;"/>
				</td>
				
				 <th>手机号码:</th>
				<td>
					<input type="text" id="merUserMobile" name ="merUserMobile" style="width: 123px;"/>
					<@sec.accessControl permission="merchant.personal.users.query">
			&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findData(1);">查询</a>

			</@sec.accessControl>
										&nbsp;
					<a href="#" class="easyui-linkbutton"  iconCls="icon-eraser" onclick="clearAllText('findTB');">重置</a>
				</td>
				
			</tr>
			<tr>
				
				<th>商户类型:</th>
				<td>
					<select id="merchantType" name="merchantType" panelHeight="80" class="easyui-combobox" editable="false" style="width: 128px;">
					</select>
				</td>
				<th>用户标志:</th>
				<td>
					<select id="merUserFlag" name="merUserFlag"  panelHeight="80" class="easyui-combobox" editable="false" style="width: 180px;">
						<option value=''>--请选择--</option>
						<option value='1000'>管理员</option>
						<option value='1100'>普通操作员</option>
					</select>
				</td>
				<th>审核状态:</th>
				<td>
					<select id="merchantState" name="merchantState" panelHeight="95" class="easyui-combobox" editable="false" style="width: 128px;">
						<option value='1' selected>审核通过</option>
						<option value='0'>未审核</option>
						<option value='2'>审核不通过</option>
						<option value=''>全部</option>
					</select>
				</td>
				
				<th>启用标识:</th>
				<td>
					<select id="activate" name="activate" panelHeight="75" class="easyui-combobox" editable="false" style="width: 128px;">
						<option value='0'>启用</option>
						<option value='1'>停用</option>
						<option value=''>全部</option>
					</select></td>
			</tr>
			<tr>
				<th>来&nbsp;源:</th>
				<td>
				<select id="merUserSource" panelHeight="80" name="merUserSource" class="easyui-combobox" editable="false" style="width: 128px;">
					<option value='' selected = "selected">-- 请选择 --</option>
					<option value='0'>门户</option>
					<option value='1'>OSS</option>
				</select>
				</td>
				<th>开户日期:</th>
				<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#createDateEnd').val()});" id="createDateStart" name="createDateStart" style="width:80px;" />
					   ~
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#createDateStart').val()});" id="createDateEnd" name="createDateEnd"  style="width:80px;" />
			 	</td>
			</tr>
		</table>
	</div>
	
	
	
	
	
	
	
	
	
	<div region="center" border="false">
		<table id="dataTbl" fit="true"></table>
	</div>
	<div id="dataTblToolbar">	
	<@sec.accessControl permission="merchant.personal.users.editor">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editor" onclick="editMerchantUser()">编辑</a>
		</@sec.accessControl>
		
			<@sec.accessControl permission="merchant.personal.users.start">
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="start" onclick="startUser()">启用</a>
		</@sec.accessControl>
			<@sec.accessControl permission="merchant.personal.users.disable">
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="disable" onclick="stopUser()">停用</a>
		</@sec.accessControl>
			<@sec.accessControl permission="merchant.personal.users.find">
			<a href="#" class="easyui-linkbutton"  iconCls="icon-view" plain="true"  onclick="viewMerchantUser()">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="merchant.personal.users.updatepwd">
			<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" plain="true" id="resetPwd" onclick="resetPwd()">重置密码</a>
		</@sec.accessControl>
		<@sec.accessControl permission="merchant.personal.users.exportMerchantList">
			<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportMerchantList">导出Excel</a>
		</@sec.accessControl>

		</div>
	<div id="dataTblPagination"></div>
	
	
	
	
		
	<div id="editMerchantUser" closable="false" title="" class="easyui-dialog" closed="true" draggable="false" maximized="true" toolbar="#upPwdDialogToolbar">
		<form id="updatePWDForm">
			<input type="hidden" name="userId" id="userId"></input>
			<fieldset> 
				<legend>编辑用户</legend>
				<table class="edittable">
					<tr>
						<th>所属商户名称:</th> 
						<td><input name="edmerName" id="edmerName" disabled="" type="text" readOnly /><font color="red">*</font></td>
						<th>用户名:</th>
						<td><input name="edUserName" id="edUserName" disabled="" type="text" readOnly/><font color="red">*</font></td>
						</tr>
						<tr>
						<th>用户标志:</th>
						<td>
							<input id="edMerUserFlag" name="edMerUserFlag" type="text" value=""  readOnly disabled=""/>
						</td>
						<th>启用标识:</th>
						<td>
						<label><input  name="edActivate" type="radio" value="0" />启用</label> 
						<label><input  name="edActivate" type="radio" value="1" />停用 </label><font color="red">*</font> 
						</td>
						</tr>
						<tr>
							<th>联系人:</th>
							<td><input name="edMerUserNickName" id="edMerUserNickName" maxlength="20" type="text" class="easyui-validatebox" data-options="required:true,validType:'enCn[2,20]'" /><font color="red">*</font></td>
							<th>手机号码:</th>
							<td><input name="edMerUserMobile" id="edMerUserMobile" type="text" maxlength="11" class="easyui-validatebox" data-options="required:true,validType:'mobile'"/><font color="red">*</font></td>
						</tr>
						<tr>
							<th>证件类型:</th>
							<td>
							<select id="edMerUserIdentityType" name="edMerUserIdentityType" class="easyui-combobox"  panelHeight="100" style="width: 131px;"></select>
							</td>
							<th>证件号码:</th>
							<td><input name="edMerUserIdentityNumber" id="edMerUserIdentityNumber"  maxlength="18" type="text" class="easyui-validatebox" validType=ddpComboboxIdCard['#edMerUserIdentityType'] /></td>
						</tr>
							<tr>
							<th>性别:</th>
							<td>
							<label><input id="edMerUserSex" name="edMerUserSex" type="radio" value="0" />男</label> 
							<label><input id="edMerUserSex" name="edMerUserSex" type="radio" value="1" />女 </label> 
							</td>
							<th>电子邮箱:</th>
							<td><input name="edMerUserEmail" id="edMerUserEmail" type="text"  maxlength="60" class="easyui-validatebox" data-options="validType:'email'"/></td>
						</tr>
						</tr>
						<tr>
							<th>学历:</th>
							<td>
							<select id="edEducation" name="edEducation" class="easyui-combobox"  panelHeight="100" style="width: 131px;"></select>
							</td>
							<th>婚姻状况:</th>
							<td>
							<select id="edIsMarried" name="edIsMarried" class="easyui-combobox"  panelHeight="70" style="width: 131px;"></select>
							</td>
						</tr>
						<tr>
							<th>出生年月:</th>
							<td>
							<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:new Date()});" id="edBirthday" name="edBirthday" />							
							</td>
							<th>年收入（元）:</th>
							<td><input name="edIncome" id="edIncome" type="text"  style="text-align: right;"/></td>
						</tr>
						
						<tr >
						<th valign="top">备注:</th>
						<td colspan="3" style="word-break:break-all">
						<textarea rows="6" name="edMerUserRemark" id="edMerUserRemark" style="width: 450px;" class="easyui-validatebox"  data-options="validType:'lengthRange[0,200]'" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);" maxlength="200"></textarea>
						</td>
						</tr>
				</table>
				<br/>
			</fieldset>
			<div id="upPwdDialogToolbar" style="text-align:right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="updateUser();">保存</a>
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelAction('editMerchantUser')">取消</a>
			</div>
		</form>
	</div>
	
	<div id="viewMerchantUserDialogToolbar" style="text-align:right;">
		<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeViewMerchant()">返回</a>
	</div>

	<div id="viewMerchantUserDialog" style="align:center;" class="easyui-dialog" closable="false" title="" closed="true" draggable="false" maximized="true" toolbar="#viewMerchantUserDialogToolbar">
			<input type="hidden" name="userId" id="userId"></input>
			<fieldset>
				<legend>查看用户详情</legend>
				<table class="viewOnly">
					<tr>
						<th style="width:77px;">所属商户:</th> 
						<td id="merchantNameView"></td>
						<th style="width:77px;">用户名:</th>
						<td id="merUserNameView"></td>
					</tr>
					<tr>
						<th>联系人:</th>
						<td id="merUserNickNameView"></td>
						<th>手机号码:</th>
						<td id="merUserMobileView"></td>
					</tr>
					<tr>
						<th>启用标识:</th>
						<td id="activateView"></td>
						<th>用户标志:</th>
						<td id="merUserFlagView"></td>
					</tr>
					<tr>
						<th>证件类型:</th>
						<td id="merUserIdentityTypeView"></td>
						<th>证件号码:</th>
						<td id="merUserIdentityNumberView"></td>
					</tr>
					<tr>
						<th>性别:</th>
						<td id="merUserSexView"></td>
						<th>电子邮箱:</th>
						<td id="merUserEmailView"></td>
					</tr>
					<tr>
						<th>学历:</th>
						<td id="educationView">
						</td>
						<th>婚姻状况:</th>
						<td id="isMarriedView">
						</td>
					</tr>
					</tr>
						<tr>
						<th>出生年月:</th>
						<td id="birthdayView">
						</td>
						<th>年收入:</th>
						<td id="incomeView"></td>
					</tr>
					<tr>
						<th>省份:</th>
						<td id ="MerUserProName"></td>
						<th>城市:</th>
						<td id ="MerUserCityName"></td>
					</tr>
					<tr >
						<th valign="top">用户地址:</th>
						<td colspan="3" id="merUserAddsView" style="word-break:break-all"></td>
					</tr>
					<tr >
						<th valign="top">备注:</th>
						<td colspan="3" id="merUserRemarkView" style="word-break:break-all"></td>
					</tr>
					<tr >
						<th >来源:</th>
						<td colspan="3" id="merUserSourceView"></td>
					</tr>
					<tr>
					    <th>创建人:</th>
						<td id="createUserView"></td>
						<th>创建时间:</th>
						<td id="createDateView"></td>
					</tr>
					<tr>						
						<th>编辑人:</th>
						<td id="updateUserView"></td>
						<th>编辑时间:</th>
						<td id="updateDateView"></td>
					</tr>
				</table>
				<br/>
			</fieldset>
			<br/>
	</div>
</body>
</html>
<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	
</head>
<title>OSS</title>
</head>
<body  class="easyui-layout" style="overflow: hidden;">
    <div region="north" border="false" style="height:80px;overflow: hidden;">
		
		<table id="findTB" class="viewtable">
			<tr>
				<th>用户名:</th>
				<td><input type="text" id="merUserName" name ="merUserName" /></td>
				<th>姓名:</th>
				<td><input type="text" id="merUserNickName" name ="merUserNickName" style="width:128px" /></td>
				<th>开户日期:</th>
				<td>
				<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#createDateEnd').val()});" id="createDateStart" name="createDateStart"/>
				  ~
               <input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#createDateStart').val()});" id="createDateEnd" name="createDateEnd"/>
               
			    </td>
				<td><a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="findData(1);">查询</a>
                    <a href="#" class="easyui-linkbutton"  iconCls="icon-eraser" onclick="clearAllText('findTB');;">重置</a></td>
			</tr>
			<tr>				
				<th>性别:</th>
				<td><select id="merUserSex" name ="merUserSex" class="easyui-combobox" panelHeight="70" editable="false" style="width: 134px;">
						<option value = "">--请选择--</option>
						<option value = "0">男</option>
						<option value = "1">女</option>
				    </select>
				</td>
				<th>手机号:</th>
				<td><input type="text" id="merUserMobile" name ="merUserMobile" style="width:128px"/></td>
				<th>&nbsp;</td>
			    <td>&nbsp;</td>
                <td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="dataTbl" fit="true"></table>
	</div>
	<div id="dataTblToolbar" class = "tableToolbar">	
		<@sec.accessControl permission="merchant.personal.user.find">
			<a href="#" class="easyui-linkbutton" iconCls="icon-view"  plain="true"  onclick="findUser()">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="merchant.personal.user.restpwd">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="disable" onclick="resetPwd()">重置密码</a>
		</@sec.accessControl>
		<@sec.accessControl permission="merchant.personal.user.export">
			<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	
	<div id="dataTblPagination"></div>
	<div id="dataDialog" title="" draggable="false" style="align:center;" closed="true" class="easyui-dialog" maximized="true" toolbar="#userDialogToolbar">
		<form id="userDialogForm">
			<fieldset>
				<legend>用户详情</legend>
				<table class="viewOnly">
					<tr>
						<th style="width:77px;">用户名:</th>
						<td id ="VmerUserName"></td>
					    <th style="width:77px;">手机号码:</th>
						<td id ="VmerUserMobile"></td>
					</tr>
					<tr>
					    <th>姓名:</th>
						<td id ="VmerUserNickName"></td>
						<th>性别:</th>
						<td id ="VmerUserSex"></td>
						
					</tr>					
					<tr>
						<th>证件号码:</th>
						<td id ="VmerUserIdentityNumber"></td>
						<th>电子邮箱:</th>
						<td id ="VmerUserEmail"></td>
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
					<tr>
						<th>详细地址:</th>
						<td id ="VmerUserAdds"></td>
						<th>开户时间:</th>
						<td id ="VcreateDate"></td>
					</tr>
					
					
				</table>
				
			</fieldset>
			<div id="userDialogToolbar" style="text-align:right;">
				<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="hideDataDialog()">返回</a>
			</div>
		</form>
	</div>
</body>
<#include "user_list_js.ftl"/>
</html>
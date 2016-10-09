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
    <!-- 
		<div class="ui-state-default" style="border:none;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="findData();">查询</a>
				<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-eraser" onclick="resetDataQuery();">重置</a>  
				
		</div>
		-->
		<table id="findTB" class="viewtable" >
			<tr>
			    <th>用户名:</th>
				<td><input type="text" id="merUserName" name ="merUserName" style="width:127px" /></td>
				
				<th>姓名:</th>
				<td><input type="text" id="merUserNameName" name ="merUserNameName" style="width:127px" /></td>
				<th>绑卡日期:</th>
				<td>
				<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#bundLingDateEnd').val()});" id="bundLingDateStart" name="bundLingDateStart"/>
				  ~
               <input type="text"   style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#bundLingDateStart').val()});" id="bundLingDateEnd" name="bundLingDateEnd"/>
               
			    </td>
				 <td><a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="findData(1);">查询</a>
                   <a href="#" class="easyui-linkbutton"  iconCls="icon-eraser" onclick="resetDataQuery();">重置</a></td>
			</tr>
			<tr>
			   <th>卡号:</th>
				<td><input type="text" id="cardCode" name ="cardCode" style="width:127px"/></td>
			  <th>卡类型:</th>
			  <td>
				 <select id="cardType" panelHeight="78" class="easyui-combobox" editable="false" style="width: 131px;">
				 	<option value=''>--请选择--</option>
				 	<option value='1'>CPU</option>
				 	<option value='2'>M1</option>
                 </select>
				</td>
				<th>解绑日期:</th>
				<td>
				  <input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#unBundLingDateEnd').val()});" id="unBundLingDateStart" name="unBundLingDateStart"/>
				  ~
				  <input type="text"  style="width:100px"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#unBundLingDateStart').val()});" id="unBundLingDateEnd" name="unBundLingDateEnd"/>
                </td>
               
              
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="dataTbl" fit="true"></table>
	</div>
	<div id="dataTblToolbar" class = "tableToolbar">
		<@sec.accessControl permission="merchant.personal.usercard.unbundling">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="disable" onclick="unbundlingCard()">解绑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="merchant.personal.usercard.export">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="dataTblPagination"></div>
	
</body>
<#include "user_card_list_js.ftl"/>
</html>
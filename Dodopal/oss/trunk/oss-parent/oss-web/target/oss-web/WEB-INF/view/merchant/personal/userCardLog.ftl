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
    <div region="north" border="false" style="height:60px;overflow: hidden;">
		<table id="findTB" class="viewtable" >
			<tr>
			    <th>用户名:</th>
				<td><input type="text" id="merUserName" name ="merUserName" style="width:127px" /></td>
				<th>卡号:</th>
				<td><input type="text" id="code" name ="code" style="width:127px" /></td>
				<th>卡类型:</th>
				<td><select id="cardType" name ="cardType" class="easyui-combobox" panelHeight="auto" editable="false" style="width: 134px;">
						<option value=''>--请选择--</option>
					 	<option value='1'>CPU</option>
					 	<option value='2'>M1</option>
				    </select>
				</td>
			    <th>来源:</th>
				<td><select id="source" name ="source" class="easyui-combobox" panelHeight="auto" editable="false" style="width: 134px;">
						<option value = "">--请选择--</option>
						<option value = "0">门户</option>
						<option value = "1">OSS</option>
				    </select>
				</td>
		
				<th>操作人姓名:</th>
				<td><input type="text" id="operName" name ="operName" style="width:127px"/></td>
				<th></th>
				 <td><a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="findData(1);">查询</a>
                    <a href="#" class="easyui-linkbutton"  iconCls="icon-eraser" onclick="clearAllText('findTB');">重置</a></td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<table id="dataTbl" fit="true"></table>
	</div>
	<div id="dataTblToolbar" class = "tableToolbar">
		<@sec.accessControl permission="merchant.personal.usercardlog.export">
			<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>
	<div id="dataTblPagination"></div>
	
</body>
<#include "user_card_log_js.ftl"/>
</html>
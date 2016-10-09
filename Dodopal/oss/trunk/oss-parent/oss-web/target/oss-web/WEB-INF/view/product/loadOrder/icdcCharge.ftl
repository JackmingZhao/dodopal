<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>公交卡充值</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
		    <script type="text/javascript" src="${base}/js/common/exportExcelUtil.js"></script>
	    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../js/product/loadOrder/icdcCharge.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:85px;overflow: hidden;">
	    <table id="queryCondition" class="viewtable">
	        <tr>
	         	<th>客户名称:</th>
                <td><input type="text" id="merchantNameQuery" name="merchantNameQuery"></td>
                 <th>订单编号:</th>
				<td><input type="text" id="orderNumQuery" name="orderNumQuery" maxlength="20"></td>
                <th>外部订单号:</th>
				<td><input type="text" id="sourceOrderNumQuery" name="sourceOrderNumQuery" maxlength="64"></td>
				<td>
				<@sec.accessControl permission="loadOrder.icdcCharge.query">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findLoadOrders(1);">查询</a>
				</@sec.accessControl>
				<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearIcdcAllText();">重置</a>
                </td>
			</tr>
			<tr>
				<th>供应商:</th>
                <td>
                <select id="supplierQuery" class="easyui-combobox" data-options="valueField:'id',textField:'name'" style="width: 133px;"/>
				</td>
				<th>卡号:</th>
				<td><input type="text" id="cardNumQuery" name="cardNumQuery" maxlength="32"></td>
               <th>圈存状态:</th>
				<td>
					<select id="orderStatusQuery"  class="easyui-combobox" editable="false" panelHeight="120" style="width: 133px;">
						<option value='' selected>-请选择-</option>
						<option value="0" >下单成功</option>
						<option value="1">充值成功</option>
						<option value="2" >充值失败</option>
						<option value="3">已退款</option>
						<option value="4">创建订单成功</option>
						<option value="5">充值中</option>
						<option value="6">退款中</option>
					</select>
				</td>
				
			</tr>
	    </table>
	</div>
	<div region="center" border="false"><table id="icdcChargeTbl" fit="true" ></table></div>
    <div id="icdcChargeTblPagination"></div>									
	<div id="icdcChargeTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="loadOrder.icdcCharge.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="icdcChargeView();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="loadOrder.icdcCharge.export">
			<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportLoadOrder" >导出Excel</a>
		</@sec.accessControl>
	</div>

	<div id="viewIcdcDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewIcdcDialogToolbar">
		<div id="viewIcdcDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewIcdcDialog');">返回</a>
		</div>
	    <fieldset>
	        <legend>圈存订单信息</legend>
	        <table class="viewOnly">
				<tr>
					<th style="width:77px;">订单编号:</th>
					<td name="view_orderNum" id="view_orderNum"></td>
					<th style="width:77px;">外部订单号:</th>
					<td id="view_extMerOrderNum" name="view_extMerOrderNum"></td>
				</tr>
				<tr>
					<th>客户号 :</th>
					<td name="view_customerCode" id="view_customerCode" ></td>
					<th>客户名称:</th>
					<td name="view_customerName" id="view_customerName" ></td>
				</tr>
				<tr>
					<th>产品编号:</th>
					<td id="view_productCode"></td>
					<th>产品名称:</th>
					<td id="view_productName"></td>
				</tr>
				<tr>
					<th>城市:</th>
					<td id="view_cityName"></td>
				</tr>
				<tr>
					<th>充值金额（元）:</th>
					<td id="view_loadAmt"></td>
					<th>卡号:</th>
					<td id="view_cardFaceNum" ></td>
				</tr>
				<tr>
					<th>圈存状态:</th>
					<td id="view_status"></td>
					<th>外部下单时间 :</th>
					<td><span id="view_extMerOrderTime"></td>
				</tr>
				<tr>
					<th valign="top">备&nbsp;注:</th>
					<td name="view_comments" id="view_comments" style="word-break:break-all" colspan='3' rowspan="1"></td>
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
</body>
</html>
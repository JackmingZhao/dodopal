<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>公交卡充值订单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type="text/javascript" src="../../js/common/exportExceClearing.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../js/product/buscardbusiness/icdcOrder.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:130px;overflow: hidden;">
		<form id="listForm" action="exportIcdcOrder" method="post">  
	    <table id="queryCondition" class="viewtable">
	        <tr>
	        	<th>客户名称:</th>
                <td><input type="text" id="merNameQuery" name="merNameQuery" style="width:157px;"></td>
				<th>客户类型:</th>
				<td>
					<select id="merType" name="merType" class="easyui-combobox" editable="false" panelHeight="200" style="width:133px;">
						<option value='' selected>--请选择--</option>
						<option value="99" >个人</option>
						<option value="10">代理商户</option>
						<option value="11" >代理商自有网点</option>
						<option value="12" >连锁商户</option>
						<option value="13" >连锁直营网点</option>
						<option value="14" >连锁加盟网点</option>
						<option value="15" >都都宝自有网点</option>
						<option value="16" >集团商户</option>
						<option value="17" >供应商</option>
						<option value="18" >外接商户</option>
					</select>
				</td>
				<th>供应商:</th>
				<td >
                	<input class="easyui-combobox" id="yktName" panelHeight="150" editable="false" style="width:133px;" data-options="valueField:'id',textField:'name'"/>
            	</td>
                <th>充值金额:</th>
				<td>
					<input type="text" id="txnAmtStartQuery" name="txnAmtStartQuery" style="width:100px;"> -
					<input type="text" id="txnAmtEndQuery" name="txnAmtEndQuery" style="width:100px;">
				</td>
				<th></th>
                <td>
				<@sec.accessControl permission="buscardbusiness.icdcOrder.query">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findProductOrder(1);">查询</a>&nbsp;
				</@sec.accessControl>
				<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearIcdcAllText();">重置</a>
                </td>
			</tr>
			<tr>
				<th>订单编号:</th>
				<td><input type="text" id="proOrderNumQuery" name="proOrderNumQuery" style="width:157px;"></td>
				<th>外部订单号:</th>
                <td><input type="text" id="merOrderNumQuery" name="merOrderNumQuery" ></td>
                <th>POS号:</th>
                <td><input type="text" id="posCodeQuery" name="posCodeQuery"></td>
                <th>卡号:</th>
				<td><input type="text" id="orderCardnoQuery" name="orderCardnoQuery" style="width:215px;"></td>
			</tr>
			<tr>
				<th>订单状态:</th>
				<td>
					<select id="proOrderStateQuery" name="proOrderStateQuery" class="easyui-combobox" editable="false" style="width:160px;" panelHeight="150" >
						<option value='' selected>--请选择--</option>
						<option value="0" >待付款</option>
						<option value="1">已付款</option>
						<option value="2" >充值失败</option>
						<option value="3">充值中</option>
						<option value="4">充值成功</option>
						<option value="5">充值可疑</option>
					</select>
				</td>
				<th>内部状态:</th>
				<td>
					<select id="proInnerStateQuery" name="proInnerStateQuery" class="easyui-combobox" editable="false" style="width:133px;"  panelHeight="150">
						<option value='' selected>--请选择--</option>
						<option value="00" >订单创建成功</option>
						<option value="10" >账户充值成功</option>
						<option value="11" >账户充值失败</option>
						<option value="12">网银支付成功</option>
						<option value="20">资金冻结失败</option>
						<option value="21">申请充值密钥失败</option>
						<option value="22">卡片充值失败</option>
						<option value="23">资金解冻成功</option>
						<option value="24">资金解冻失败</option>
						<option value="25">网银支付失败</option>
						<option value="30">资金冻结成功</option>
						<option value="31" >申请充值密钥成功</option>
						<option value="40">卡片充值成功</option>
						<option value="41">资金扣款成功</option>
						<option value="42">资金扣款失败</option>
						<option value="50">上传充值未知</option>
					</select>
				</td>
                <th>可疑状态:</th>
                <td>
                	<select id="suspiciouStates" name="suspiciouStates" class="easyui-combobox" editable="false" style="width:133px;" panelHeight="90" >
						<option value='' selected>--请选择--</option>
						<option value="0" >不可疑</option>
						<option value="1">可疑未处理</option>
						<option value="2" >可疑已处理</option>
					</select>
                </td>
				<th>订单日期:</th>
				<td>
					<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#orderDateEndQuery').val()});" id="orderDateStartQuery" name="orderDateStartQuery"/>
					-
					<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#orderDateStartQuery').val()});" id="orderDateEndQuery" name="orderDateEndQuery"/>
				</td>
			</tr>
			<tr>
			    <th>支付类型:</th>
                <td>
                	<select id="payTypeQuery" name="payTypeQuery" class="easyui-combobox" editable="false" style="width:160px;" panelHeight="110">
						<option value='' selected>--请选择--</option>
						<option value="0" >账户支付</option>
						<option value="1">一卡通支付</option>
						<option value="2" >在线支付</option>
						<option value="3">银行支付</option>
					</select>
                </td>
                <th><td></td></th>
                <th><td></td></th>
                <th><td></td></th>
			</tr>
	    </table>
	    </form>
	</div>
	<div region="center" border="false"><table id="icdcOrderTbl" fit="true" ></table></div>
    <div id="icdcOrderTblPagination"></div>									
	<div id="icdcOrderTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="buscardbusiness.icdcOrder.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="viewProductOrderDetails();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="buscardbusiness.icdcOrder.export">
			<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnSelExcCol" >导出Excel</a>	
	    </@sec.accessControl>
	</div>

	<div id="viewIcdcOrderDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewIcdcDialogToolbar">
		<div id="viewIcdcDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewIcdcOrderDialog');">返回</a>
		</div>
	    <fieldset>
	        <legend>订单信息</legend>
	        <table class="viewOnly">
				<tr>
					<th style="width:77px;">产品库订单编号:</th>
					<td name="view_proOrderNum" id="view_proOrderNum"></td>
					<th style="width:77px;">产品编号:</th>
					<td id="view_proCode" name="view_proCode"></td>
				</tr>
				<tr>
					<th>产品名称 :</th>
					<td name="view_proName" id="view_proName" ></td>
					<th>充值金额:</th>
					<td><span name="view_showTxnAmt" id="view_showTxnAmt"></span>&nbsp;元</td>
				</tr>
				<tr>
					<th>城市名称:</th>
					<td id="view_cityName"></td>
					<th>通卡公司名称:</th>
					<td id="view_yktName"></td>
				</tr>
				<tr>
					<th>实付（收）金额:</th>
					<td><span id="view_showReceivedPrice"></span>&nbsp;元</td>
					<th>一卡通充值费率:</th>
					<td><span id="view_yktRechargeRate"></span>&nbsp;‰</td>
				</tr>
				<tr>
					<th>商户费率类型:</th>
					<td><span id="view_merRateTypeStr"></span></td>
					<th>商户费率:</th>
					<td><span id="view_merRate"></span>&nbsp;<span id="view_merRateTypeUnit"></span></td>
				</tr>
				<tr>
					<th>支付服务费率类型:</th>
					<td><span id="view_payServiceTypeStr"></span></td>
					<th>支付服务费率:</th>
					<td><span id="view_payServiceRate"></span>&nbsp;<span id="view_payServiceTypeUnit"></span></td>
				</tr>
				<tr>
					<th>商户进货价:</th>
					<td><span  id="view_showMerPurchaasePrice"></span>&nbsp;元</td>
					<th>订单时间 :</th>
					<td><span id="view_proOrderDateStr"></span></td>
				</tr>
				<tr>
					<th>卡面号:</th>
					<td id="view_orderCardno"></td>
					<th>POS号 :</th>
					<td><span id="view_posCode"></span></td>
				</tr>
				<tr>
					<th>商户利润:</th>
					<td><span id="view_showMerGain"></span>&nbsp;元</td>
					<th>充值前金额 :</th>
					<td><span id="view_showBefbal"></span>&nbsp;元</td>
				</tr>
				<tr>
					<th>充值后金额:</th>
					<td><span id="view_showBlackAmt"></span>&nbsp;元</td>
					<th>支付类型 :</th>
					<td><span id="view_payTypeStr" name="view_payTypeStr"></span></td>
				</tr>
				<tr>
					<th>支付方式:</th>
					<td id="view_payWay"></td>
					<th>订单状态 :</th>
					<td><span id="view_proOrderStateView"></span></td>
				</tr>
				<tr>
					<th>订单内部状态:</th>
					<td id="view_proInnerStateStr"></td>
					<th>订单前内部状态 :</th>
					<td><span id="view_proBeforInnerStateStr"></span></td>
				</tr>
				<tr>
					<th>可疑处理状态:</th>
					<td id="view_proSuspiciousStateStr"></td>
					<th>可疑处理说明:</th>
					<td><span id="view_proSuspiciousExplain"></span></td>
				</tr>
				<tr>
					<th>客户名称:</th>
					<td id="view_merName"></td>
					<th>客户类型:</th>
					<td><span id="view_merUserTypeStr"></span></td>
				</tr>
				<tr>
					<th>圈存标识:</th>
					<td id="view_loadFlagStr"></td>
					<th>外部订单号:</th>
					<td><span id="view_merOrderNum"></span></td>
				</tr>
				<tr>
					<th>圈存订单号:</th>
					<td id="view_loadOrderNum"></td>
					<th>来源:</th>
					<td><span id="view_sourceStr"></span></td>
				</tr>
				<tr>
					<th>清算标志:</th>
					<td id="view_clearingMarkStr"></td>
					<th>操作员:</th>
					<td><span id="view_userName"></span></td>
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
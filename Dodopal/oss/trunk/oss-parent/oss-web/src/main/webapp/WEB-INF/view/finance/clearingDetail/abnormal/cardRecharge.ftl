<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>一卡通充值异常</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../../include.ftl"/>
	<script type="text/javascript" src="../js/common/exportExceClearing.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../js/finance/clearingDetail/abnormal/cardRecharge.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		<form id="listForm" action="exportIcdcOrder" method="post">  
    		<table id="queryCondition" class="viewtable">
        <tr>
        	<th>订单号:</th>
            <td><input type="text" id="orderNo" name="orderNo"></td>
            
            <th>客户名称:</th>
           	<td><input type="text" id="customerName" name="customerName"></td>
            
            <th>订单日期:</th>
      		<td>
				<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#eDate').val()});" id="sDate" name="sDate"/>
				-
				<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#sDate').val()});" id="eDate" name="eDate"/>
			</td>
			
			
           	 
            
            
            <th></th>
	        <td>
			<@sec.accessControl permission="clearingDetailManage.abnormal.cardRecharge.query">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findCardRecharge(1);">查询</a>&nbsp;
			</@sec.accessControl>
				<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearIcdcAllText();">重置</a>
	        </td>
        </tr>
    </table>
    </form>
	</div>
	<div region="center" border="false"><table id="cardRechargeTbl" fit="true" ></table></div>
    <div id="cardRechargeTblPagination"></div>									
	<div id="cardRechargeTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="clearingDetailManage.abnormal.cardRecharge.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="viewCardRechargeDetails();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="clearingDetailManage.abnormal.cardRecharge.import">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>

	<div id="viewCardRechargeDialog" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewCardRechargeDialogToolbar">
	    <fieldset>
         <legend>清分记录详细信息</legend>
	        <table class="viewOnly">
            <tr>
                <th style="width:77px;">订单号:</th>
                <td id="orderNoView"></td>
                <th style="width:77px;">订单时间 :</th>
                <td id="orderDateView"></td>
            </tr>
            <tr>
                <th>商户编号:</th>
                <td id="customerNoView"></td>
                <th>商户名称:</th>
                <td id="customerNameView"></td>
            </tr>
            <tr>
                <th>商户类型:</th>
                <td id="customerTypeView"></td>
                <th>来源:</th>
                <td id="orderFromView"></td>
            </tr>
            <tr>
                <th>商户充值费率类型 :</th>
                <td id="customerRateTypeView"></td>
                <th>商户充值费率:</th>
                <td id="customerRateView"></td>
            </tr>
             <tr>
                 <th>订单金额:</th>
                <td id="orderAmountView"></td>
                <th>商户实际支付金额 :</th>
                <td id="custoerRealPayAmountView"></td>
            </tr>
             <tr>
                <th>商户应得分润:</th>
                <td id="customerShouldProfitView"></td>
                <th>服务费:</th>
                <td id="ddpGetMerchantPayFeeView"></td>
            </tr>
             <tr>
                <th>商户服务费率类型:</th>
                <td id="serviceRateTypeView"></td>
                <th>商户服务费率:</th>
                <td id="serviceRateView"></td>
            </tr>
             <tr>
                <th>支付网关:</th>
                <td id="payGatewayView"></td>
                <th>支付类型 :</th>
                <td id="payTypeView"></td>
            </tr>
             <tr>
                <th>支付方式:</th>
                <td id="payWayNameView"></td>
                <th>充值费率:</th>
                <td id="ddpSupplierRateView"></td>
            </tr>
             <tr>
                <th>应收返点:</th>
                <td id="supplierToDdpShouldRebateView"></td>
                <th>实收返点:</th>
                <td id="supplierToDdpRealRebateView"></td>
            </tr>
            <tr>
                <th>银行手续费率(千分比(‰)):</th>
                <td id="ddpBankRateView"></td>
                <th>银行手续费:</th>
                <td id="ddpToBankFeeView"></td>
            </tr>
	        </table>
	        <br/>
    </fieldset>
</div>
	<div id="viewCardRechargeDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewCardRechargeDialog');">返回</a>
		</div>
</body>
</html>
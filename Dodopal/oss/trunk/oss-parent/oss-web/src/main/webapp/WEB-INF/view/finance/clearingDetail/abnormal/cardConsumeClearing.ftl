<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>一卡通消费清分异常</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../../include.ftl"/>
	<script type="text/javascript" src="../../../js/common/exportExceClearing.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/finance/clearingDetail/abnormal/cardConsumeClearing.js"></script>
</head>                                                                            
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
		 
    	<table id="queryCondition" class="viewtable">
        <tr>
            <th>订单号:</th>
            <td><input type="text" id="orderNo" name="orderNo"></td>
            
			<th>客户名称:</th>
           	<td><input type="text" id="customerName" name="customerName"></td>
           	 
            <th>订单日期:</th>
      		<td>
				<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#orderDateEndQuery').val()});" id="orderDateStartQuery" name="orderDateStartQuery"/>
				-
				<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#orderDateStartQuery').val()});" id="orderDateEndQuery" name="orderDateEndQuery"/>
			</td>
          	
          	<th></th>
	        <td>
			<@sec.accessControl permission="clearingDetailManage.abnormal.cardConsume.query">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findProductOrder(1);">查询</a>&nbsp;
			</@sec.accessControl>
				<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearIcdcAllText();">重置</a>
	        </td>
        </tr>
    </table>
	</div>
	<div region="center" border="false"><table id="icdcOrderTbl" fit="true" ></table></div>
    <div id="icdcOrderTblPagination"></div>									
	<div id="icdcOrderTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="clearingDetailManage.abnormal.cardConsume.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="viewProductOrderDetails();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="clearingDetailManage.abnormal.cardConsume.import">
		<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
		</@sec.accessControl>
	</div>

		<div id="viewIcdcOrderDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewIcdcDialogToolbar">
		<div id="viewIcdcDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewIcdcOrderDialog');">返回</a>
		</div>
	    <fieldset>
         <legend>一卡通消费清分信息</legend>
	        <table class="viewOnly">
            <tr>
                <th style="width:77px;">订单号:</th>
                <td id="orderNoView"></td>
                <th style="width:77px;">订单时间:</th>
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
                <td id="sourceView"></td>
            </tr>
            <tr>
                <th>订单金额:</th>
                <td id="tradeAmtView"></td>
                <th>转出金额:</th>
                <td id="customerRealPayView"></td>
            </tr>
             <tr>
                <th>服务费:</th>
                <td id="DDPGetMerchantPayFeeView"></td>
                <th>服务费率类型:</th>
                <td id="serviceRateTypeView"></td>
            </tr>
             <tr>
                <th>服务费率:</th>
                <td id="serviceFeeView"></td>
                <th>支付网关:</th>
                <td id="payGateWay"></td>
            </tr>
            
           <tr>
                <th>支付类型:</th>
                <td id="payTypeView"></td>
                <th>支付方式:</th>
                <td id="payWayView"></td>
            </tr>
             <tr>
                <th>手续费率:</th>
                <td id="bankRateView"></td>
                <th>手续费:</th>
                <td id="bankFeeView"></td>
            </tr>
             <tr>
                <th>DDP应收银行业务费用:</th>
                <td id="DDPFromBankShouldFeeView"></td>
                <th>DDP实收银行业务费用:</th>
                <td id="DDPFromBankRealFeeView"></td>
            </tr>
	        </table>
	        <br/>
    </fieldset>
</div>
</body>
</html>
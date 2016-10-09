<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <meta http-equiv="Pragma" contect="no-cache">
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0"> 
	<#include "../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'></script>
    <script type="text/javascript" src="../js/crdSysOrder/crdSysOrder.js"></script>
    <script type="text/javascript" src="../js/common/exportExceClearing.js"></script>
    <title>卡服务订单</title>
</head>
<body class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:105px; overflow: hidden;">
<form id="listForm" action="exportCrdSysOrder" method="post">  
    <table id="crdSysOrderQueryCondition" class="viewtable">
        <tr>
            <th>卡服务订单号:</th>
            <td><input type="text" id="crdOrderNum" name="crdOrderNum" style="width:118px;"></td>
            <th>产品库主订单号:</th>
            <td><input type="text" id="proOrderNum" name="proOrderNum" style="width:118px;"></td>
            <th>POS号:</th>
            <td><input type="text" id="posCode" name="posCode" style="width:118px;"></td>
            
            <th></th>
            <td>
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findCrdSysOrder(1);">查询</a>
                &nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser"
                   onclick="clearCrdAllText('crdSysOrderQueryCondition');">重置</a>
            </td>
        </tr>
        <tr>
        	<th>卡号:</th>
            <td><input type="text" id="checkCardNo" name="checkCardNo" style="width:118px;"></td>
        	<th>卡类型:</th>
            <td>
            <select id="cardType" name="cardType" class="easyui-combobox" editable="false" style="width:98px;">
           	</select>
            </td>
         	<th>产品名称:</th>
            <td><input type="text" id="proName" name="proName" style="width:118px;"></td>
            <th>交易金额:</th>
			<td>
					<input type="text"  id="txnAmtStart" name="txnAmtStart" style="width:50px;"/>
					   ~
	               	<input type="text"  id="txnAmtEnd" name="txnAmtEnd"  style="width:50px;"/>
			</td>
        </tr>
        
         <tr>
         
           <th>订单状态:</th>
            <td>
                <select id="crdOrderStates" name="crdOrderStates" class="easyui-combobox" editable="false" style="width:118px;">
                </select>
            </td>
			<th>订单前状态:</th>
            <td>
                <select id="crdBeforeorderStates" name="crdBeforeorderStates" class="easyui-combobox" editable="false" style="width:118px;">
                </select>
            </td>
			<th>交易日期:</th>
			<td>
					<input type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#afterProceRateDateEnd').val()});" id="afterProceRateDateStart" style="width:80px;" />
					   ~
	               	<input type="text"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#afterProceRateDateStart').val()});" id="afterProceRateDateEnd" style="width:80px;" />
			</td>
            
        </tr>
    </table>
    </form>
</div>
<div region="center" border="false" >
    <table id="crdSysOrderTbl" fit="true"></table>
</div>
<div id="crdSysOrderTblToolbar" class="tableToolbar">
<@sec.accessControl permission="crdSys.crdSysOrder.view">
    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="queryVerified"
       onclick="findCrdSysOrderByCode()">查看</a>
</@sec.accessControl>
<@sec.accessControl permission="crdSys.crdSysOrder.export">
<!--     <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" id="exportVerified" onclick="exportExcel('exportCrdSysOrder','listForm')">导出Excel</a>
 -->    <a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
</@sec.accessControl>
</div>
<!--查询卡服务订单详情开始-->
<div id="viewCrdSysOrderDialogToolbar" style="text-align:right;">
    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="closeViewCrdSysOrder()">返回</a>
</div>
<div id="viewCrdSysOrderDialog" style="align:center;" class="easyui-dialog" closable="false" title="" closed="true" draggable="false" maximized="true" toolbar="#viewCrdSysOrderDialogToolbar">
    <fieldset>
        <legend>卡服务订单信息</legend>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">卡服务订单号:</th>
                <td id="crdOrderNumView"></td>
                <th style="width:77px;">产品库主订单号:</th>
                <td id="proOrderNumView"></td>
            </tr>
            <tr>
                <th>产品编号:</th>
                <td id="proCodeView"></td>
                 <th>卡服务订单交易状态:</th>
                <td id="crdOrderStatesView"></td>
            </tr>
            <tr>
                <th>上次卡服务订单交易状态:</th>
                <td id="crdBeforeorderStatesView"></td>
                <th>交易应答码:</th>
                <td id="respCodeView"></td>
            </tr>
             <tr>
                <th>用户编号:</th>
                <td id="userCodeView"></td>
                <th>城市编号:</th>
                <td id="cityCodeView"></td>
            </tr>
             <tr>
                <th>一卡通名称:</th>
                <td id="yktNameView"></td>
                <th>卡内号:</th>
                <td id="cardInnerNoView"></td>
            </tr>
             <tr>
                <th>卡面号:</th>
                <td id="cardFaceNoView"></td>
                <th>前台显示的卡面号:</th>
                <td id="orderCardNoView"></td>
            </tr>
            <tr>
                <th>卡类型:</th>
                <td id="cardTypeView"></td>
                <th>设备类型:</th>
                <td id="posTypeView"></td>
            </tr>
            <tr>
                <th>设备流水号:</th>
                <td id="posSeqView"></td>
                <th>交易前卡余额:</th>
                <td id="befbalView"></td>
            </tr>
            <tr>
                <th>交易后卡余额:</th>
                <td id="blackAmtView"></td>
                <th>交易金额:</th>
                <td id="txnAmtView"></td>
            </tr>
            <tr>
                <th>卡内允许最大金额:</th>
                <td id="cardLimitView"></td>
                <th>数据来源:</th>
                <td id="sourceView"></td>
            </tr>
            <tr>
                <th>交易类型:</th>
                <td id="txnTypeView"></td>
                <th>交易流水号:</th>
                <td id="txnSeqView"></td>
            </tr>
            <tr>
                <th>交易时间:</th>
                <td id="txnDateView"></td>
                 <th>交易结束标志:</th>
                <td id="tradeEndFlagView"></td>
                
               <!-- <th>交易时间:</th>
                <td id="txnTimeView"></td> -->
            </tr>
            <tr>
                <th>验卡卡号:</th>
                <td id="checkCardNoView"></td>
                <th>验卡请求时间:</th>
                <td id="checkSendCardDateView"></td>
            </tr>
            <tr>
                <th>验卡应答时间:</th>
                <td id="checkResCardDateView"></td>
                <th>验卡读卡设备号:</th>
                <td id="checkCardPosCodeView"></td>
            </tr>
            <tr>
                <th>充值卡号:</th>
                <td id="chargeCardNoView"></td>
                <th>充值设备号:</th>
                <td id="chargeCardPosCodeView"></td>
            </tr>
            
               <tr>
                <th>充值请求时间:</th>
                <td id="chargeSendCardDateView"></td>
                <th>充值应答时间:</th>
                <td id="chargeResCardDateView"></td>
            </tr>
               <tr>
                <th>结果请求时间:</th>
                <td id="resultSendCardDateView"></td>
                <th>结果应答时间:</th>
                <td id="resultResCardDateView"></td>
            </tr>
               <tr>
                <th>充值类型:</th>
                <td id="chargeTypeView"></td>
                <th>月票类型:</th>
                <td id="metropassTypeView"></td>
            </tr>
               <tr>
                <th>月票充值开始日期:</th>
                <td id="metropassChargeStartDateView"></td>
                <th>月票充值结束日期:</th>
                <td id="metropassChargeEndDateView"></td>
            </tr>
               <tr>
                <th>可疑处理标识:</th>
                <td id="dounknowFlagView"></td>
                <th>卡片计数器:</th>
                <td id="cardCounterView"></td>
            </tr>
               <tr>
                <th>充值组装卡交易记录:</th>
                <td id="afterCardNotesView"></td>
                <th>充值前卡片最后一条交易记录:</th>
                <td id="beforeCardNotesView"></td>
            </tr>
             <tr>
                <th>交易步骤:</th>
                <td id="tradeStepView"></td>
                <th>交易步骤版本:</th>
                <td id="tradeStepVerView"></td>
            </tr>
            
        </table>
        </fieldset>
    <fieldset>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">创建人:</th>
                <td id="createUserView"></td>
                <th style="width:77px;">创建时间:</th>
                <td id="createDateView"></td>
            </tr>
            <tr>
                <th style="width:77px;">编辑人:</th>
                <td id="updateUserView"></td>
                <th style="width:77px;">编辑时间:</th>
                <td id="updateDateView"></td>
            </tr>
        </table>
    </fieldset>
</div>
<!--查看卡服务订单详情结束-->
<div id="crdSysOrderTblPagination"></div>
</body>
</html>
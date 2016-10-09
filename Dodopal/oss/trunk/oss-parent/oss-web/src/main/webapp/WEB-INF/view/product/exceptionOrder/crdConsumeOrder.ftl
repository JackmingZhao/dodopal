<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>一卡通消费订单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
	<script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'></script>
    <script type="text/javascript" src="../../../js/product/exceptionOrder/productConsumeOrder.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
	<script type="text/javascript" src="../../../js/common/exportExceClearing.js"></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:85px;overflow: hidden;">
		<form id="listForm" action="excelproductConsumeOrder" method="post">  
	    <table id="queryCondition" class="viewtable">
	        <tr>
				<th>客户名称:</th>
				<td><input type="text" id="merName" name="merName" style="width:157px;"></td>
				<th>订单编号:</th>
				<td><input type="text" id="orderNum" name="orderNum"></td><th>供应商:</th>
				<td >
                	<input class="easyui-combobox" id="yktName" panelHeight="150" editable="false" style="width:160px;" data-options="valueField:'id',textField:'name'"/>
            	</td>
                <th>实收金额:</th>
				<td>
					<input type="text" id="txnAmtStart" name="txnAmtStart" style="width:100px;"> -
					<input type="text" id="txnAmtEnd" name="txnAmtEnd" style="width:100px;">
				</td>
				

				<th></th>
                <td>
				<@sec.accessControl permission="exceptionOrder.crdConsumeOrder.query">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findProductConsumeOrder(1);">查询</a>&nbsp;
				</@sec.accessControl>
					<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearIcdcAllText();">重置</a>
                </td>
			</tr>
			<tr>
                <th>POS号:</th>
                <td><input type="text" id="posCode" name="posCode"></td>
                <th>卡号:</th>
				<td><input type="text" id="cardNum" name="cardNum"></td>
				<th>订单状态:</th>
				<td>
					<select id="queryStates" name="queryStates" class="easyui-combobox" editable="false" panelHeight="110" style="width:160px;">
						<option value='' selected>--请选择--</option>
						<option value="0" >待支付</option>
						<option value="1">支付失败</option>
						<option value="2" >支付成功</option>
						<option value="3" >支付中</option>
					</select>
				</td>
				<th>订单日期:</th>
				<td>
					<input type="text" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:$('#orderDateEnd').val()});" id="orderDateStart" name="orderDateStart"/>
					-
					<input type="text"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:$('#orderDateStart').val()});" id="orderDateEnd" name="orderDateEnd"/>
				</td>
			</tr>
		<!--<tr>
				
				<th hidden>客户类型:</th>
				<td hidden>
					<select id="merUserType" name="merUserType" class="easyui-combobox" editable="false" panelHeight="200" style="width:133px;">
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
				<th hidden>内部状态:</th>
				<td hidden>
					<select id="innerQueryStates" name="innerQueryStates" class="easyui-combobox" editable="false" panelHeight="185"  style="width: 133px;">
						<option value='' selected>--请选择--</option>
						<option value="00" >订单创建成功</option>
						<option value="01">验卡成功</option>
						<option value="02" >申请消费密钥成功</option>
						<option value="10" >验卡失败</option>
						<option value="11" >申请消费密钥失败</option>
						<option value="12" >扣款失败</option>
						<option value="20" >扣款成功</option>
						<option value="30" >扣款未知</option>
					</select>
				</td>
			</tr>-->
	    </table>
	    </form>
	</div>
	<div region="center" border="false"><table id="icdcAcqTbl" fit="true" ></table></div>
    <div id="icdcAcqTblPagination"></div>									
	<div id="icdcAcqTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="exceptionOrder.crdConsumeOrder.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="viewProductConsumeDetails();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="exceptionOrder.crdConsumeOrder.handle">
		<a href="#" class="easyui-linkbutton" iconCls="icon-user" plain="true" id="exceptionHandle" onclick="exceptionHandle();">异常处理</a>
	    </@sec.accessControl>
	    <@sec.accessControl permission="exceptionOrder.crdConsumeOrder.export">
	    <a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
	    </@sec.accessControl>
	</div>
	
	<div id="viewIcdcConsumeDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewIcdcDialogToolbar">
		<div id="viewIcdcDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAction('viewIcdcConsumeDialog');">返回</a>
		</div>
	    <fieldset>
	        <legend>一卡通消费订单详情</legend>
	        <table class="viewOnly">
				<tr>
					<th style="width:77px;">订单编号：</th>
					<td name="view_orderNum" id="view_orderNum"></td>
					<th style="width:77px;">商户名称：</th>
					<td id="view_merName" name="view_merName"></td>
				</tr>
				<tr>
					<th>城市：</th>
					<td name="view_cityName" id="view_cityName" ></td>
					<th>通卡公司名称：</th>
					<td><span name="view_yktName" id="view_yktName"></span></td>
				</tr>
				<tr>
					<th>POS号：</th>
					<td id="view_posCode"></td>
					<th>卡号：</th>
					<td id="view_cardNum"></td>
				</tr>
				<tr>
					<th>应收金额：</th>
					<td><span id="view_originalPrice"></span>&nbsp;元</td>
					<th>实收金额：</th>
					<td><span id="view_receivedPrice"></span>&nbsp;元</td>
				</tr>
				<tr>
					<th>消费前金额：</th>
					<td><span  id="view_befbal"></span>&nbsp;元</td>
					<th>消费后金额：</th>
					<td><span id="view_blackAmt"></span>&nbsp;元</td>
				</tr>
				<tr>
					<th>内部订单状态：</th>
					<td><span id="view_innerStates"></span></td>
					<th>订单状态：</th>
					<td><span id="view_states"></span></td>
				</tr>
				<tr>
					<th>商户服务费率类型：</th>
					<td><span id="view_serviceRateType"></span></td>
					 <th>商户服务费率：</th>
					<td><span id="view_serviceRate"></span></td>
				</tr>
				<tr>
					<th>操作员：</th>
					<td><span id="view_userName"></span></td>
					<th>订单时间：</th>
					<td><span id="view_proOrderDate"></span></td>
				</tr>
	        </table>
	        <br/>
	    </fieldset>
	</div>
	
	<div id="exceptionHandleDialog" class="easyui-dialog" title="异常处理" style="width:400px;height:200px;" data-options="iconCls:'icon-user',modal:true,closed:true">  
			<div id='messageDiv' style="padding:10px;height:60px;">
			</div>
			<div id='radioDiv' style="padding:10px;height:20px;display:none">
				<span style='color:red'><strong>选择一种处理方式:</strong></span><br/><br/>
			    <label><input  name="handleSign" type="radio" value="2" />消费成功</label>&nbsp;&nbsp;&nbsp;&nbsp; 
				<label><input  name="handleSign" type="radio" value="1" />消费失败</label> 
			</div>
	</div>  
	
</body>
</html>
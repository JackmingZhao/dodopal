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
    <script type="text/javascript" src="../../js/merchant/enterprise/discount.js"></script>
    <title>商户折扣管理详情信息</title>

</head>
<body class="easyui-layout" style="overflow: hidden;">

	<!-- 查询条件 ----------------------------------------------------------------------------------->
	<div region="north" border="false" style="height:60px;overflow: hidden;">
	    <table id="discountQueryCondition" class="viewtable">
	        <tr>
	            <th>用户折扣:</th>
	            <td><input type="text" id="discountThresholdQuerry" class="easyui-numberbox" style="width:199px;" data-options="required:true,min:0,max:9.99,precision:2"></td>
	            <td>
	                &nbsp;&nbsp;
	                <a href="#" id="discountQueryBtn" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
	                &nbsp;&nbsp;
	                <a href="#" id="resetDisQueryBtn" class="easyui-linkbutton" iconCls="icon-eraser" >重置</a>
	            </td>
	        </tr>

	    </table>
	</div>
	
	<!-- 数据展示 ----------------------------------------------------------------------------------->
	<div region="center" border="false">
	    <table id="discountListTbl" fit="true"></table>
	</div>
	
	<!-- 功能标签 ----------------------------------------------------------------------------------->
	<div id="discountTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="merchant.enterprise.discount.addDiscount">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add"    plain="true" id="addDiscount" >添加折扣</a>
	    </@sec.accessControl>
	    
		<@sec.accessControl permission="merchant.enterprise.discount.edtDiscount">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" id="edtDiscount" >编辑</a>
	    </@sec.accessControl>
	    
		<@sec.accessControl permission="merchant.enterprise.discount.viwDiscount">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-view"   plain="true" id="viwDiscount" >查看</a>
	    </@sec.accessControl>
	    
		<@sec.accessControl permission="merchant.enterprise.discount.delDiscount">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="delDiscount" >删除</a>
	    </@sec.accessControl>
	</div>
	<div id="discountTblPagination"></div><!-- 翻页 -->
	
	<!-- 添加编辑 ----------------------------------------------------------------------------------->
	<div id="discountDialog" class="easyui-dialog" title="" draggable="false" maximized="true" toolbar="#discountDialogToolbar">
		<!-- <form id="discountDialogForm"> -->
		<fieldset>
			<legend id="titleCon">编辑折扣</legend>
			<hr/>
			<table class="edittable">
						<input type="hidden" name="discountId" id="discountId" />
				<tr>
					<th style="width:63px;">用户折扣:<font color="red">*</font></th>
					<td>
						<input id="discountThreshold" type="text" class="easyui-numberbox" prompt="必须是数字..." style="width:199px;" data-options="required:true,min:0,max:9.99,precision:2" maxlength="4"/>
					</td>
					<th>结算折扣:<font color="red">*</font></th>
					<td>
						<input id="setDiscount" type="text" class="easyui-numberbox" style="width:199px;" data-options="required:true,min:0,max:9.99,precision:2" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<th>开始日期:<font color="red">*</font></th>
					<td>
						<input id="beginDate" style="width:199px;" type="text" /><!-- ,disabled:false,value:'2012-12-13',minDate:'2012-12-12',maxDate:'2012-12-25' -->
					</td>
					<th>结束日期:<font color="red">*</font></td>
					<td>
						<input id="endDate" style="width:199px;" type="text" />
					</td>
				</tr>
				<tr>
					<th>星期:<font color="red">*</font></th>
					<td colspan="3">
                    <label><input type="checkbox" name="week" value="1" checked="checked"/>星期一</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label><input type="checkbox" name="week" value="2" checked="checked"/>星期二</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label><input type="checkbox" name="week" value="3" checked="checked"/>星期三</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label><input type="checkbox" name="week" value="4" checked="checked"/>星期四</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label><input type="checkbox" name="week" value="5" checked="checked"/>星期五</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label><input type="checkbox" name="week" value="6" checked="checked"/>星期六</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label><input type="checkbox" name="week" value="7" checked="checked"/>星期日</label>
                    <!-- <input type="button" id="testBtn" value="试0.0试" /> -->
				</tr>
				<tr>
					<th>时段:<font color="red">*</font></th>
					<td colspan="3">
                    	<label><input class="TimeChoose" id="wholeTimeChoose" type="radio" name="TimeChoose" value="0" checked="checked" />全时段</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    	<label><input class="TimeChoose" id="partTimeChoose" type="radio" name="TimeChoose" value="1" />指定时段</label>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                    	<label id="lBeginTime">开始时间:</label><input type="input" id="mBeginTime" name="mBeginTime" />&nbsp&nbsp&nbsp
                    	<label id="lEndTime">结束时间:</label><input type="input" id="mEndTime" name="mEndTime" />
                    	<!-- -->
                    	<input type="hidden" name="beginTime" id="beginTime"  />
                    	<input type="hidden" name="endTime" id="endTime"  />
					</td>
				</tr>
        </table>
        
		<fieldset>
		<hr/>
			<legend>商户</legend>
			<button type="button" id="boundMer">绑定商户</button>
			<div id="resultSelMer" fit="true"></div>
			<hr/>
		</fieldset>
        
    </fieldset>
    <br/>
    <div id="discountDialogToolbar" style="text-align:right;">
        <a href="#" class="easyui-linkbutton" id="saveBtn" iconCls="icon-save" plain="true" >保存</a>
        <a href="#" class="easyui-linkbutton" id="cancBtn" iconCls="icon-cancel" plain="true" style="margin-right:10px;" >取消</a>
    </div>
</div>

<div id="selMerDia" title="商户绑定选择列表" draggable="true">

		<input type="text" style="text-align:left;" id="merName" />
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searSelMerBtn" plain="true" style="margin-right:230px;">查询</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save"   id="saveSelMerBtn" plain="true" >确定</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="cancSelMerBtn" plain="true"  style="margin-right:10px;">取消</a>
			<div id="selMerDiaToolbar" class="tableToolbar" style="text-align:right;"></div>
	
	<div region="center" border="false">
		<table id="merListTbl" fit="true" ></table>
	</div>
	<div id="merTblPagination"></div><!-- 翻页 -->
</div>
	
	
	
	<!-- 查看 ----------------------------------------------------------------------------------->
	<div id="discountViewDia" class="easyui-dialog" title="" draggable="false" maximized="true" toolbar="#discountViewDiaToolbar">
		<fieldset>
			<legend>查看折扣</legend>
			<hr/>
			<table class="edittable">
						<input type="hidden" id="discountIdView" />
				<tr>
					<th style="width:63px;">用户折扣:<font color="red">*</font></th>
					<td id="discountThresholdView">
					</td>
					<th>结算折扣:<font color="red">*</font></th>
					<td id="setDiscountView">
					</td>
				</tr>
				<tr>
					<th>开始日期:<font color="red">*</font></th>
					<td id="beginDateView">
					</td>
					<th>结束日期:<font color="red">*</font></td>
					<td id="endDateView">
					</td>
				</tr>
				<tr>
					<th>星期:<font color="red">*</font></th>
					<td colspan="3">
                    <label style="color:grey;"><input type="checkbox" name="weekView" value="1" readonly disabled />星期一</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label style="color:grey;"><input type="checkbox" name="weekView" value="2" readonly disabled />星期二</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label style="color:grey;"><input type="checkbox" name="weekView" value="3" readonly disabled />星期三</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label style="color:grey;"><input type="checkbox" name="weekView" value="4" readonly disabled />星期四</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label style="color:grey;"><input type="checkbox" name="weekView" value="5" readonly disabled />星期五</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label style="color:grey;"><input type="checkbox" name="weekView" value="6" readonly disabled />星期六</label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    <label style="color:grey;"><input type="checkbox" name="weekView" value="7" readonly disabled />星期日</label>
                    <!-- <input type="button" id="testBtn" value="试0.0试" /> -->
				</tr>
				<tr>
					<th>时段:<font color="red">*</font></th>
					<td colspan="3">
                    	<label>开始时间:</label>&nbsp&nbsp<label id="beginTimeView" ></label>&nbsp&nbsp&nbsp&nbsp&nbsp
                    	<label>结束时间:</label>&nbsp&nbsp<label id="endTimeView" ></label>
					</td>
				</tr>
        </table>
        
		<fieldset>
			<legend>商户</legend>
			<div id="resultSelMerView" fit="true"></div>
		</fieldset>
    </fieldset>
    <br/>
    <div id="discountViewDiaToolbar" style="text-align:right;">
        <a href="#" class="easyui-linkbutton" id="cancViewBtn" iconCls="icon-cancel" plain="true" style="margin-right:10px;" >返回</a>
    </div>
</div>


	
	
</body>
</html>
<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>基础信息管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="../../../js/common/exportExceClearing.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../../js/product/supplier/yktBasicInfo.js"></script>
    <script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'></script>
</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="north" border="false" style="height:60px;overflow: hidden;">
	    <table id="queryCondition" class="viewtable">
	        <tr>
	            <th>通卡公司名称:</th>
	            <td><input type="text" id="icdcNameQuery"/></td>
	            <th>启用标识:</th>
	            <td>
	                <select id="icdcActiveQuery" class="easyui-combobox" editable="false" panelHeight="70"></select>
	            </td>
	            <th></th>
	            <td>
	            	<@sec.accessControl permission="supplier.icdc.basic.query">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="icdcQuery(1);">查询</a>
	                </@sec.accessControl>
	                &nbsp;&nbsp;
	                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearIcdcAllText();">重置</a>
	            </td>
	        </tr>
	    </table>
	</div>
	<div region="center" border="false"><table id="icdcBasicInfoTbl" fit="true" ></table></div>
    <div id="icdcBasicInfoTblPagination"></div>									
	<div id="icdcBasicInfoTblToolbar" class="tableToolbar">
		<@sec.accessControl permission="supplier.icdc.basic.add">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="icdcAdd();">添加</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.basic.modify">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="icdcModify();">编辑</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.basic.batchopen">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" onclick="icdcEnable();">启用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.basic.batchstop">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" onclick="icdcDisable();">停用</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.basic.view">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="icdcView();">查看</a>
		</@sec.accessControl>
		<@sec.accessControl permission="supplier.icdc.basic.export">
		    <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnSelExcCol" >导出Excel</a>
		</@sec.accessControl>
	</div>

	<div id="viewIcdcDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false" toolbar="#viewIcdcDialogToolbar">
		<div id="viewIcdcDialogToolbar" style="text-align:right;">
		    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeIcdcView();">返回</a>
		</div>
	    <fieldset>
	        <legend>基础信息</legend>
	        <table class="viewOnly">
				<tr>
					<th style="width:77px;">一卡通代码:</th>
					<td name="view_yktCode" id="view_yktCode"></td>
					<th style="width:77px;">通卡公司名称:</th>
					<td id="view_yktName" name="view_yktName"></td>
				</tr>
				<tr>
					<th>所在城市:</th>
					<td><span id="view_provinceName"></span>&nbsp;<span id="view_cityName"></span></td>
					<th>业务城市:</th>
					<td id="view_yktBusinessCity" ></td>
				</tr>
				<tr>
					<th valign="top">详细地址:</th>
					<td name="view_yktAddress" id="view_yktAddress" colspan='3' rowspan="1" style="word-break:break-all"></td>
				</tr>
				<tr>
					<th>付款方式:</th>
					<td id="view_yktPayFlag"></td>
					<th>启用标识:</th>
					<td id="view_activate"></td>
				</tr>
				<tr>
					<th>充值限制时间:</th>
					<td name="view_yktRechargeLimitTime" id="view_yktRechargeLimitTime" ></td>
					<th>消费限制时间:</th>
					<td name="view_yktConsumeLimitTime" id="view_yktConsumeLimitTime" ></td>
				</tr>
				<tr>
					<th>开通一卡通充值:</th>
					<td><input id="view_yktIsRecharge" type="checkbox"  checked="checked" disabled="disabled"/></td>
					<th>一卡通充值费率:</th>
					<td name="view_yktRechargeRate" id="view_yktRechargeRate" ></td>
				</tr>
				<tr>
					<th>充值业务结算类型:</th>
					<td name="view_yktRechargeSetType" id="view_yktRechargeSetType" ></td>
					<th>充值业务结算类型值:</th>
					<td name="view_yktRechargeSetPara" id="view_yktRechargeSetPara" ></td>
				</tr>
				<tr>
					<th>开通一卡通支付:</th>
					<td><input id="view_yktIsPay" type="checkbox"  disabled="disabled"/></td>
					<th>一卡通支付费率:</th>
					<td name="view_yktPayRate" id="view_yktPayRate" ></td>
				</tr>
				<tr>
					<th>支付业务结算类型:</th>
					<td name="view_yktPaysetType" id="view_yktPaysetType" ></td>
					<th>支付业务结算类型值:</th>
					<td name="view_yktPaySetPara" id="view_yktPaySetPara" ></td>
				</tr>
				<tr>
					<th>接入IP地址:</th>
					<td name="view_yktIpAddress" id="view_yktIpAddress" ></td>
					<th>接入端口号:</th>
					<td name="view_yktPort" id="view_yktPort" ></td>
				</tr>
				<tr>
					<th>卡内允许的最大金额:</th>
					<td name="view_yktCardMaxLimit" id="view_yktCardMaxLimit"></td>
					<th>通卡公司固定电话:</th>
					<td name="view_yktTel" id="view_yktTel" ></td>
				</tr>
				<tr>
					<th>联系人姓名:</th>
					<td name="view_yktLinkUser" id="view_yktLinkUser" ></td>
					<th>联系人手机号:</th>
					<td name="view_yktMobile" id="view_yktMobile" ></td>
				</tr>
				<tr>
					<th valign="top">备&nbsp;注:</th>
					<td name="view_remark" id="view_remark" style="word-break:break-all" colspan='3' rowspan="1"></td>
				</tr>
	        </table>
	        <br/>
	    </fieldset>
	    <fieldset>
	        <table class="viewOnly">
	            <tr>
	                <th style="width:110px;">创建人:</th>
	                <td id="view_createUser"></td>
	                <th style="width:110px;">创建时间:</th>
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

	<div id="icdcDialog" title="" draggable="false" maximized="true" toolbar="#icdcDialogToolbar" style="display:none;">
		<div id="icdcDialogToolbar" style="text-align:right;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveIcdc()">保存</a>
			<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelSaveIcdcAction();">取消</a>
		</div>
		<input type="hidden" id="icdcId"></input>
		<input name="merCode" id="merCode" type="hidden"/>
		<fieldset>
			<legend>基础信息</legend>
			<table id="icdcInfoTable" class="edittable">
				<tr>
					<th width="15%">一卡通代码:</th>
					<td width="35%">
						<input id="yktCode" type="text"  class="easyui-validatebox" style="width:300px;" maxlength="10" 
							data-options="required:true,validType:'numberMaxLength[1,10]'" />			
						<font color="red">*</font>
					</td>
					<th width="15%">付款方式:</th>
					<td width="35%">
						<label><input  name="yktPayFlag" type="radio" value="0" />预购额度</label> 
						<label><input  name="yktPayFlag" type="radio" value="1" />后结算</label>
					</td>
				</tr>
				<tr>
					<th width="80px;">通卡公司名称:</th>
					<td width="250px;">
						<input id="yktName" type="text"  class="easyui-validatebox" style="width:300px;" maxlength="40" 
							data-options="required:true,validType:'cnEnNo[1,40]'"/>
						<font color="red">*</font>
					</td>
					<th>启用标识:</th>
					<td>
						<label><input  name="activate" type="radio" value="0" />启用</label> 
						<label><input  name="activate" type="radio" value="1" />停用 </label>
					</td>
				</tr>
				<tr>
					<th>详细地址:</th>
					<td colspan="3">
						<div style="float:left;">
							<div style="float:left;" id="yktProvinceCity"></div>
							<div style="float:left;padding-left:3px;">
								<input type="text" id="yktAddress" class="easyui-validatebox" data-options="required:true,validType:'lengthNumber[200]'" style="width: 502px;height:18px;" maxlength="200">
								<font color="red">*</font>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th>业务城市:</th>
					<td>
						<input id="businessCity" type="text"  class="easyui-validatebox" style="width:300px;font-color:blue;" disabled="disabled"/>
						<input id="" type="button" style="height:22px;" value="选择" onclick="showBussinessCity();"/><font color="red">*</font>
					</td>
					
				</tr>
				<tr>
					<th>充值限制时间</th>
					<td>
						<input type="text" style="width:140px" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" id="yktRechargeLimitStartTime" name="yktRechargeLimitStartTime"/>
						-
						<input type="text"  style="width:140px" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" id="yktRechargeLimitEndTime" name="yktRechargeLimitEndTime"/>
					</td>
					<th>消费限制时间</th>
					<td>
						<input type="text" style="width:140px" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" id="yktConsumeLimitStartTime" name="yktConsumeLimitStartTime"/>
						-
						<input type="text"  style="width:140px" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" id="yktConsumeLimitEndTime" name="yktConsumeLimitEndTime"/>
					</td>
				</tr>
				<tr>
					<th>开通一卡通充值:</th>
					<td><input id="yktIsRecharge" type="checkbox"  onchange="chooseBussiness('yktIsRecharge');"/></td>
					<th>一卡通充值费率:</th>
					<td>
						<input id="yktRechargeRate" type="text" style="text-align:right;width:300px;"/>(‰)
						<font id="yktRechargeRateFont" color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>充值业务结算类型:</th>
					<td>
						<select id="yktRechargeSetType" class="easyui-combobox" editable="false" panelHeight="90"></select>
						<font id="yktRechargeSetTypeFont" color="red">*</font>
					</td>
					<th>充值业务结算类型值:</th>
					<td>
						<input id="yktRechargeSetPara" type="text" style="text-align:right;width:300px;" maxLength="8"
							onkeypress = 'return /^\d$/.test(String.fromCharCode(event.keyCode||event.keycode||event.which))'
							oninput= 'this.value = this.value.replace(/\D+/g, "")'
							onpropertychange='if(!/\D+/.test(this.value)){return;};this.value=this.value.replace(/\D+/g, "")'
							onblur = 'this.value = this.value.replace(/\D+/g, "")'/>
						<font id="yktRechargeSetParaTypeFont"></font>
						<font id="yktRechargeSetParaFont" color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>开通一卡通支付:</th>
					<td><input id="yktIsPay" type="checkbox" onchange="chooseBussiness('yktIsPay');"/></td>
					<th>一卡通支付费率:</th>
					<td>
						<input id="yktPayRate" type="text" style="text-align:right;width:300px;"/>(‰)
						<font id="yktPayRateFont" color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>支付业务结算类型:</th>
					<td>
						<select id="yktPaysetType" class="easyui-combobox" editable="false" panelHeight="90"></select>
						<font id="yktPaysetTypeFont" color="red">*</font>
					</td>
					<th>支付业务结算类型值:</th>
					<td>
						<input id="yktPaySetPara" type="text" style="text-align:right;width:300px;" maxLength="8"
							onkeypress = 'return /^\d$/.test(String.fromCharCode(event.keyCode||event.keycode||event.which))'
							oninput= 'this.value = this.value.replace(/\D+/g, "")'
							onpropertychange='if(!/\D+/.test(this.value)){return;};this.value=this.value.replace(/\D+/g, "")'
							onblur = 'this.value = this.value.replace(/\D+/g, "")'/>
						<font id="yktPaySetParaTypeFont"></font>
						<font id="yktPaySetParaFont" color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>接入IP地址:</th>
					<td><input id="yktIpAddress" type="text" style="width:300px;" class="easyui-validatebox" data-options="validType:'IP'" maxLength="20"/></td>
					<th>接入端口号:</th>
					<td><input id="yktPort" type="text" style="width:300px;" class="easyui-validatebox" data-options="validType:'port'" maxLength="10"/></td>
				</tr>
				<tr>
					<th>卡内允许的最大金额:</th>
					<td>
						<input id="yktCardMaxLimit" type="text" style="text-align:right;width:300px;" maxLength="8"
							onkeypress = 'return /^\d$/.test(String.fromCharCode(event.keyCode||event.keycode||event.which))'
							oninput= 'this.value = this.value.replace(/\D+/g, "")'
							onpropertychange='if(!/\D+/.test(this.value)){return;};this.value=this.value.replace(/\D+/g, "")'/>元
					</td>
					<th>备注:</th>
					<td rowspan="6">
						<textarea rows="6" id="remark" style="width: 300px;height:120px;" 
								  onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" 
								  onKeyPress="return onmykeypress(this);"  maxlength="200"></textarea>
				   </td>
				</tr>
				<tr>
					<th>联系人姓名:</th>
					<td><input id="yktLinkUser" type="text" style="width:300px;" class="easyui-validatebox" data-options="validType:'enCn[0,50]'" maxLength="50"/></td>
				</tr>
				<tr>
					<th>联系人手机号:</th>
					<td><input id="yktMobile" type="text" style="width:300px;" class="easyui-validatebox" data-options="validType:'mobile'" maxLength="11"/></td>
				</tr>
				<tr>
					<th>固定电话:</th>
					<td><input id="yktTel" type="text" style="width:300px;" class="easyui-validatebox" data-options="validType:'telephone'" maxLength="20"/></td>
				</tr>
			</table>
		</fieldset>
	</div>
	
	<div id="dd" class="easyui-dialog" title="业务城市选项卡" style="width:490px;height:350px;" data-options="iconCls:'icon-save',closable:false,resizable:false,modal:true,closed:true">   
	    <div id="zz" title="选项卡"  style="padding:10px;width:450px;height:30px;">
	    	<input id="chooseCityName" type="text" class="easyui-searchbox" style="width:300px;height:25px;vertical-align:middle;line-height:25px;"/>
	    	<input type="button" style="width:50px;height:25px;" value="确定" onclick="sureCity();"/>
	    	<input type="button" style="width:50px;height:25px;" value="清空" onclick="cancelCity();"/>
	    	<input type="hidden" id="chooseCity">
			<input type="hidden" id="icdcCode"></input>
			<input type="hidden" id="icdcCityId"></input>
			<input type="hidden" id="icdcCityName"></input>
	    </div>
	    <div id="tt" title="选项卡" class="easyui-tabs" style="width:450px;height:250px;">  
	    	<div title="ABCD"  style="padding:20px;" id="ABCD"></div>   
	    	<div title="EFGH"  style="padding:20px;" id="EFGH"></div>
	    	<div title="IJK"   style="padding:20px;" id="IJK"></div>   
	    	<div title="LM"    style="padding:20px;" id="LM"></div>   
	    	<div title="NOPQR" style="padding:20px;" id="NOPQR"></div>   
	    	<div title="ST"    style="padding:20px;" id="ST"></div>  
	    	<div title="UVWX"  style="padding:20px;" id="UVWX"></div> 
	    	<div title="YZ"    style="padding:20px;" id="YZ"></div> 
		</div>    
	</div>  
</body>
</html>
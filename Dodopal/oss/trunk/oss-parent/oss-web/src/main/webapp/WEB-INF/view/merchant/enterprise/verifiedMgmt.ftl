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
<#include "../../include.ftl"/>
    <!-- 上级商户类型 -->
<#include "../../common/merchantSearchModel.ftl"/>
    <!--Component css-->
    <link rel="stylesheet" type="text/css" href="../../css/ykt.css">
    <style type="text/css">
        .base-table {
            width: 100%;
            line-height: 32px;
        }

        .base-table th {
            font-size: 14px;
            color: #444444;
            text-align: right;
            padding: 0 5px 10px;
            font-weight: normal;
            vertical-align: top;
        }

        .base-table th .red {
            color: #ff2626;
            vertical-align: middle;
            margin-right: 2px;
        }

        .base-table td {
            padding: 0 5px;
            color: #808080;
            font-size: 14px;
            vertical-align: top;
        }

        .base-table td input,
        .base-table td textarea {
            font-size: 12px;
        }

        .base-table td textarea {
            border: 1px solid #ccc;
            padding: 5px;
        }

        .base-table01 th,
        .base-table01 td {
            font-size: 14px;
            padding: 0px 5px;
            height: auto
        }

        .tit-pop {
            border: 1px solid #37b4e9;
            width: 420px;
            display: none;
            background-color: #f6f6f6;
            position: absolute;
            margin: 10px 0;
            padding: 0 20px;
            left: -280px;
            top: 20px;
            z-index: 9;
        }

        .tit-pop01 {
            padding: 0px 20px 10px;
            width: 460px;
        }

        .tit-pop table {
            width: 100%;
            border-collapse: collapse;
        }

        .tit-pop table th {
            border-bottom: 1px dashed #bdbebf;
            text-align: center;
            font-size: 14px;
            color: #808080;
            padding: 8px 5px;
        }

        .tit-pop table td {
            text-align: center;
            color: #444444;
            font-size: 12px;
            padding: 8px 5px;
            vertical-align: middle;
        }
        }
    </style>
    <!--Component JS -->
    <script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'></script>
    <script type="text/javascript" src="../../js/common/merchantddic.js"></script>
    <script type="text/javascript" src="../../js/common/merchntType.js"></script>
    <script type="text/javascript" src="../../js/common/yktLoadInfo.js"></script>
    <script type="text/javascript" src="../../js/common/merchantSearchModel.js"></script>
    <script type="text/javascript" src="../../js/merchant/enterprise/verified.js"></script>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <title>审核通过商户信息</title>
</head>


<body class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:105px;overflow: hidden;">
    <table id="merChantQueryCondition" class="viewtable">
        <tr>
            <th>商户名称:</th>
            <td><input type="text" id="merNameQuery" style="width:118px;"></td>
            <th>商户编码:</th>
            <td><input type="text" id="merCodeQuery" style="width:200px;"></td>
             <th>商户类型:</th>
            <td><select id="merTypeQuery" style="width:150px;"  class="easyui-combobox" editable="false" ></select></td>
             <th>商户分类:</th>
            <td><select id="merClassifyQuery" style="width:100px;" class="easyui-combobox" panelHeight="70px;"></select></td>
              <th></th>
            <td>
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMerchantUsers(1);">查询</a>
                &nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser"
                   onclick="clearAllText('merChantQueryCondition');">重置</a>
            </td>
        <tr>
            <th>商户属性:</th>
            <td id="merPropertyFirst"><select id="merPropertyQuery" class="easyui-combobox" style="width:128px;" panelHeight="70px;"></select></td>
             <th>上级商户名称(精确):</th>
            <td><input type="text" id="merParentNameQuery" style="width:200px;"></td>
            <th>来源:</th>
            <td>
                <select id="sourceQuery" class="easyui-combobox" editable="false" style="width: 118px;" panelHeight="70px;">
                    <option value='' selected="selected">--请选择--</option>
                    <option value='0'>门户</option>
                    <option value='1'>OSS</option>
                </select>
            </td>
             <th>启用标识:</th>
            <td>
                <select id="activateQuery" class="easyui-combobox" editable="false" style="width: 120px;" panelHeight="70px;">
                    <option value='0' selected="selected">启用</option>
                    <option value='1'>停用</option>
                     <option value=''>全部</option>
                </select>
            </td>
           </tr>
           <tr>
            <th>业务类型:</th>
            <td>
                <select id="bussQuery" class="easyui-combobox" editable="false" style="width: 120px;" panelHeight="70px;">
                    <option value='' selected="selected">--请选择--</option>
                    <option value='01'>一卡通充值</option>
                    <option value='03'>一卡通消费</option>
                </select>
            </td>
            <th>省市:</th>
            <td id="proCityQuery"></td>
        </tr>
    </table>
</div>
<div region="center" border="false">
    <table id="merchantUserTbl" fit="true"></table>
</div>
<div id="merchantUserTblToolbar" class="tableToolbar">
<@sec.accessControl permission="merchant.enterprise.verified.audit">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="auditVerified" onclick="auditVerified()">开户</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.verified.editor">
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editorVerified"
       onclick="editorVerified()">编辑</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.verified.start">
    <a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="startVerified"
       onclick="startMerchant()">启用</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.verified.disable">
    <a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="disableVerified"
       onclick="disableMerchant()">停用</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.verified.find">
    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="findVerified"
       onclick="findVerified()">查看</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.verified.exportVerifie">
    <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportVerifieMgmt">导出Excel</a>
</@sec.accessControl>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editorMerDisCount"
       onclick="editorMerDisCount()">编辑折扣</a>
</div>
<div id="merchantUserTblPagination"></div>
<div id="OpenMerchantDialog" class="easyui-dialog" title="" draggable="false" maximized="true" toolbar="#openMerchantDialogToolbar">
    <fieldset>
        <legend>注册信息</legend>
        <table class="edittable">
            <input name="merCode" id="merCode" type="hidden"/>
            <input name="merState" id="merState" type="hidden"/>
            <input name="userCode" id="userCode" type="hidden"/>
            <input name="flag" id="flag" value="0" type="hidden"/>
            <input name="merPT" id="merPT" value="" type="hidden"/>
            <input name="selectedProRateInfo" id="selectedProRateInfo" type="hidden"/><!-- 通卡公司为一卡通充值的临时保存选中的数据 -->
            <input name="selectedProRateInfoPayment" id="selectedProRateInfoPayment" type="hidden"/><!-- 通卡公司为一卡通消费的临时保存选中的数据 -->
            <input name="selectedProRateIcLoadInfo" id="selectedProRateIcLoadInfo" type="hidden"/><!-- 通卡公司为圈存的临时保存选中的数据 -->
            
            <tr>
                <th style="width:93px;">商户名称:</th>
                <td><input id="merName" type="text" class="easyui-validatebox" style="width:199px;"
                           data-options="required:true,validType:'merName'" maxlength="50"/><font
                        color="red">*</font></td>
                <th style="width:93px;">商户类型:</th>
                <td style="width:250px;"><select id="merType" name="merType" editable="false"></select><font color="red">*</font>
                </td>
            </tr>
            <tr>
                <th>商户分类:</th>
                <td>
                    <label><input type="radio" name="merClassify" value="0" checked="checked"/>正式商户</label>
                    <label><input type="radio" name="merClassify" value="1"/>测试商户</label><font color="red">*</font>
                </td>
                <th>商户属性:</th>
                <td>
                    <label><input type="radio" name="merProperty" value="0" checked="checked"
                                  onclick="merPropertyClick('stander');"/>标准商户</label>
                    <label><input type="radio" name="merProperty" value="1" onclick="merPropertyClick('customer');"/>自定义商户</label>
                    <a href="#" class="easyui-linkbutton" plain="true" onclick="merPropertyClick('customer')"
                       id="viewMerProperty" style="display:none;">查看</a><font color="red">*</font>
                </td>
            </tr>
            <tr id="merTypeParentLine">
                <th style="width:93px;">上级商户类型:</th>
                <td><select id="merParentType" editable="false"></select><font color="red">*</font></td>
                <th style="width:93px;">上级商户名称:</th>
                <td>
                    <input name="merParentName" id="merParentName" class="easyui-validatebox" style="width:199px;"
                           disabled/>
                    <input name="merParentCode" id="merParentCode" type="hidden"/>
                    <input type="button" onclick="findMerParentName();" id="finMER" style="height:22px;" value="选择"/>
                </td>
            </tr>
            <tr>
                <th>账户类型:</th>
                <td>
                    <label><input type="radio" name="fundType" value="0" checked="checked"/>资金</label>
                    <label><input type="radio" name="fundType" value="1"/>授信
                        <font color="red">*</font>
                </td>
                <th>用户名:</th>
                <td>
                    <input name="merUserName" id="merUserName" type="text" class="easyui-validatebox"
                           style="width:199px;" data-options="required:true,validType:'AazNumber'" maxlength="20"/><font
                        color="red">*</font>
                </td>
            </tr>
            <tr>
                <th>联系人:</th>
                <td>
                    <input name="merLinkUser" id="merLinkUser" type="text" class="easyui-validatebox"
                           style="width:199px;" data-options="required:true,validType:'merLinkUser[2,20]'"
                           maxlength="20"/><font color="red">*</font>
                </td>
                <th>手机号码:</th>
                <td>
                    <input id="merLinkUserMobile" name="merLinkUserMobile" type="text" class="easyui-validatebox"
                           style="width:199px;" data-options="required:true,validType:'mobile'" maxlength="11"/><font
                        color="red">*</font>
                </td>
            </tr>
            
            <tr>
					<th>结算类型:</th>
					<td>
						<select id="settlementType" class="easyui-combobox" editable="false"></select>
						<font id="settlementTypeFont" color="red">*</font>
					</td>
					<th>结算阀值:</th>
					<td><input id="settlementThreshold" type="text" style="text-align:right;width:199px;" maxLength="8"
							onkeypress = 'return /^\d$/.test(String.fromCharCode(event.keyCode||event.keycode||event.which))'
							oninput= 'this.value = this.value.replace(/\D+/g, "")'
							onpropertychange='if(!/\D+/.test(this.value)){return;};this.value=this.value.replace(/\D+/g, "")'
							onblur = 'this.value = this.value.replace(/\D+/g, "")'/>
						<font id="settlementThresholdTypeFont"></font>
						<font id="settlementThresholdFont" color="red">*</font>
					</td>
			</tr>
            <tr>
                <th>启用标识:</th>
                <td>
                    <label><input type="radio" name="activate" value="0" checked="checked"/>启用</label>
                    <label><input type="radio" name="activate" value="1"/>停用</label><font color="red">*</font>
                </td>
                <th id="isAutoDistibuteOne"  style="display:none;">是否自动分配额度:</th>
                <td id="isAutoDistibuteTwo"  style="display:none;">
                    <label><input type="radio" name="isAutoDistibute" value="0"  checked="checked"   onclick="isAutoDistibuteCheck();"/>是</label>
                    <label><input type="radio" name="isAutoDistibute" value="1"  onclick="notIsAutoDistibuteCheck();"/>否</label>
                    <label><input type="radio" name="isAutoDistibute" value="2"  onclick="notIsAutoDistibuteCheck();"/>共享额度</label><font color="red">*</font>
                </td>
                
             <!--   <th id="limitSourceOne"  style="display:none;">额度来源:</th>
                <td id="limitSourceTwo"  style="display:none;">
                    <label><input type="radio" name="limitSource" value="0"  checked="checked" />自己购买</label>
                    <label><input type="radio" name="limitSource" value="1" />上级分配</label><font color="red">*</font>
                </td>-->
            </tr>
            
            
            <tr id="isAutoDistibuteLine"  style="display:none;">
					<th>额度阀值:</th>
					<td>
						<input id="limitThreshold" type="text" style="text-align:right;width:199px;"></input><font color="red">*</font>
					</td>
					<th>自动分配额度阀值:</th>
					<td>
						<input id="selfMotionLimitThreshold" type="text" style="text-align:right;width:199px;"></input><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th>商圈/区域:</th>
					<td>
					     <input id="tradeArea" name="tradeArea" type="text" class="easyui-validatebox" style="width:199px;" maxlength="20"></input><font color="red"></font>
					</td>
					<th>商户打款人:</th>
					<td>
					     <input id="merPayMoneyUser" name="merPayMoneyUser" type="text" class="easyui-validatebox" style="width:199px;" maxlength="50"></input><font color="red"></font>
					</td>
			</tr>
            <tr>
                <th id="merAddress">详细地址:</th>
                <td id="merAddressLine" colspan="5">
                	<span style="width:4px;"></span>
                    <input id="merAddressName" type="text" style="width:318px;height: 20px;" class="easyui-validatebox"
                           data-options="required:true" maxlength="200"/>
                </td>
            </tr>
            <tr id="PrdRateTypeLine">
                <th>业务类型:</th>
                <td id="PrdRateType" colspan="5">
                </td>
            </tr>
            <tr id="yktInfoUrlLine" style="display:none;">
                <th>页面回调URL:</th>
                <td><input name="YktPageCallbackUrl" id="YktPageCallbackUrl" class="easyui-validatebox" style="width:199px;"/></td>
                <th>服务器回调URL:</th>
                <td><input name="YktServiceNoticeUrl" id="YktServiceNoticeUrl" class="easyui-validatebox" style="width:199px;"/></td>
            </tr>
            <tr id="pageCheckCallbackUrlLine" style="display:none;">
                <th>服务器验卡URL:</th>
                <td><input name="pageCheckCallbackUrl" id="pageCheckCallbackUrl" class="easyui-validatebox" style="width:199px;"/></td>
            	<th>是否弹出错误信息:</th>
                <td>
                    <label><input type="radio" name="isShowErrorMsg" value="0"  checked="checked" />是</label>
                    <label><input type="radio" name="isShowErrorMsg" value="1" />否</label><font color="red">*</font>
                </td>
            </tr>
            <tr id="TKNameVerLine" style="display:none;"> 
                <th>通卡公司(充值):</th>
                <td colspan="5">
                    <input type="text" name="providerCode" id="providerCode" disabled style="width:524px;"/>
                    <input name="ykt" id="ykt" type="button" style="height:22px;"
                           onclick="yktSelector('yktInfoContent');" value="选择"/><font color="red">*</font>
                </td>
            </tr>
            <tr id="yktTblLine" style="display:none;">
                <th></th>
                <td colspan="5">
                    <table id="yktTbl"></table>
                </td>
            </tr>
            <tr id="TKNameVerLinePayment" style="display:none;"> 
                <th>通卡公司(消费):</th>
                <td colspan="5">
                    <input type="text" name="providerCodePayment" id="providerCodePayment" disabled style="width:524px;"/>
                    <input name="yktPayment" id="yktPayment" type="button" style="height:22px;"
                           onclick="yktSelectorPayment('yktInfoContentPayment');" value="选择"/><font color="red">*</font>
                </td>
            </tr>
            <tr id="yktTblLinePayment" style="display:none;">
                <th></th>
                <td colspan="5">
                    <table id="yktTblPayment"></table>
                </td>
            </tr>
            <tr id="IcLoadLine" style="display:none;"> 
                <th>通卡公司(圈存):</th>
                <td colspan="5">
                    <input type="text" name="icLoad" id="icLoad" disabled style="width:524px;"/>
                    <input name="icLoadBtn" id="icLoadBtn" type="button" style="height:22px;"
                           onclick="yktIcLoadPanel('yktIcLoad');" value="选择"/><font color="red">*</font>
                </td>
            </tr>
            <tr id="yktIcLoadTblLine" style="display:none;">
                <th></th>
                <td colspan="5">
                    <table id="yktIcLoadTbl"></table>
                </td>
            </tr>
        </table>
    </fieldset>
    <br/>
    <fieldset>
        <legend>补充信息</legend>
        <table class="edittable">
            <tr>
                <th>经营范围:</th>
                <td>
                    <select id="merBusinessScopeId" name="merBusinessScopeId"></select>
                </td>
                <th>性别:</th>
                <td>
                    <label><input type="radio" name="merUserSex" value="0" checked="checked"/>男</label>
                    <label><input type="radio" name="merUserSex" value="1"/>女</label>
                </td>
            </tr>
            <tr>
                <th>固定电话:</th>
                <td>
                    <input name="merUserTelephone" id="merUserTelephone" type="text" class="easyui-validatebox"
                           style="width:199px;" data-options="validType:'telephone'" maxlength="20"/>
                </td>
                <th>传真:</th>
                <td>
                    <input name="merFax" id="merFax" type="text" class="easyui-validatebox" style="width:199px;"
                           data-options="validType:'fax'" maxlength="20"/>
                </td>
            </tr>
            <tr>
                <th>证件类型:</th>
                <td>
                    <select id="merUserIdentityType" name="merUserIdentityType"></select>
                </td>
                <th>证件号码:</th>
                <td>
                    <input name="merUserIdentityNumber" id="merUserIdentityNumber" class="easyui-validatebox"
                           style="width:199px;" validType=ddpComboboxIdCard['#merUserIdentityType'] type="text"
                           maxlength="20"/>
                </td>
            </tr>
            <tr>
                <th>电子邮箱:</th>
                <td>
                    <input name="merUserEmail" id="merUserEmail" type="text" class="easyui-validatebox"
                           style="width:199px;" data-options="validType:'email'" maxlength="60"/>
                </td>
                <th>邮编:</th>
                <td>
                    <input name="merZip" id="merZip" type="text" class="easyui-validatebox" style="width:199px;"
                           data-options="validType:'zip'" maxlength="6"/>
                </td>
            </tr>
            <tr>
                <th>学历:</th>
                <td>
                  <select id="education" name="education" class="easyui-combobox"  style="width:199px;"></select>
                </td>
                <th>年收入（元）:</th>
                <td>
	                <input id="income" type="text" style="width:199px;"></input>
                </td>
            </tr>
            <tr>
                <th>生日:</th>
                <td>
                <input id="birthday" name="birthday" style="width:199px;" type="text" onfocus="WdatePicker({skin:'whyGreen',minDate:'1900-01-01',maxDate:'%y-%M-%d'})"/>
                </td>
                <th>婚姻状况:</th>
                <td>
                   <select id="isMarried" name="isMarried" class="easyui-combobox"  panelHeight="100"></select>
                </td>
            </tr>
            <tr>
                <th>开户银行:</th>
                <td>
                    <select id="merBankName" name="merBankName" class="easyui-combobox"></select>
                </td>
                <th>开户行账户:</th>
                <td>
                    <input name="merBankAccount" id="merBankAccount" class="easyui-validatebox" style="width:199px;" type="text"/>
                </td>
            </tr>
            <tr>
                <th>开户名称:</th>
                <td>
                    <input name="merBankUserName" id="merBankUserName" class="easyui-validatebox" style="width:199px;"
                           type="text" data-options="validType:'enCn[2,50]'" maxlength="50"/>
                </td>
                <th>都都宝联系人:</th>
                <td>
                    <input name="merDdpLinkUser" id="merDdpLinkUser" class="easyui-validatebox" style="width:199px;"
                           data-options="validType:'enCn[2,20]'" type="text" maxlength="20"/>
                </td>
            </tr>
            <tr>
                <th>都都宝联系人电话:</th>
                <td>
                    <input name="merDdpLinkUserMobile" id="merDdpLinkUserMobile" class="easyui-validatebox"
                           data-options="validType:'mobileAndTel'" style="width:199px;" type="text" maxlength="20"/>
                </td>
                <th id="ddpIpLable">都都宝固定IP:</th>
                <td id="ddpIpLine">
                    <input name="merDdpLinkIp" id="merDdpLinkIp" class="easyui-validatebox" style="width:199px;"
                           type="text" data-options="validType:'IP'" maxlength="15"/>
                </td>
            </tr>
            <!--<tr id="encryptionLine">-->
            <tr>
                <th>商户签名秘钥:</th>
                <td><input name="merMd5paypwd" id="merMd5paypwd" class="easyui-validatebox" style="width:199px;"
                           data-options="validType:'egNumber'" maxlength="32"/></td>
                <th>商户验签秘钥:</th>
                <td><input name="merMd5Backpaypwd" id="merMd5Backpaypwd" class="easyui-validatebox" style="width:199px;"
                           data-options="validType:'egNumber'" maxlength="32"/></td>
            </tr>
            <tr>
                <th valign="top">备注:</th>
                <td colspan="4"  style="word-break:break-all"><textarea rows="4" name="merUserRemark" id="merUserRemark" style="width:520px;"
                                          maxlength="200" data-options="validType:'lengthNumber[200]'" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"></textarea></td>
            </tr>
        </table>
        <br/>
    </fieldset>
    <div id="openMerchantDialogToolbar" style="text-align:right;">
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="openMerchantUser()">保存</a>
        <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
           onclick="cancelAction('OpenMerchantDialog')">取消</a>
    </div>
</div>
<div id="customerDialog" title="自定义商户信息"  draggable="true" maximized="false">
    <div id="customerProperty"></div>
</div>
<input type="hidden" id="dataflag" />
<div id="yktDialog" title="通卡公司信息(充值)" draggable="true" maximized="false">
    <table id="yktInfoContent" class="checkboxView"></table>
</div>
<div id="yktDialogPayment" title="通卡公司信息(消费)" draggable="true" maximized="false">
	<table id="yktInfoContentPayment" class="checkboxView"></table>
</div>
<div id="yktIcLoadDialog" title="通卡公司信息(圈存)" draggable="true" maximized="false">
	<table id="yktIcLoad" class="checkboxView"></table>
</div>
<div id="viewMerchantDialogToolbar" style="text-align:right;">
    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="closeViewMerchant()">返回</a>
</div>
<div id="viewMerchantDialog" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false"
     toolbar="#viewMerchantDialogToolbar">
    <fieldset>
        <legend>注册信息</legend>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">商户名称:</th>
                <td id="merNameView"></td>
                <th style="width:77px;">商户类型:</th>
                <td id="merTypeView"></td>
            </tr>
            <tr id="passwordLine">
                <th>商户分类:</th>
                <td id="merClassifyView"></td>
                <th>商户属性:</th>
                <td id="merPropertyView"></td>
            </tr>
            <tr>
                <th>上级商户类型:</th>
                <td id="merParentTypeView"></td>
                <th>上级商户名称:</th>
                <td id="merParentNameView"></td>
            </tr>
            <tr>
                <th>账户类型:</th>
                <td id="fundTypeView"><!--1:资金/2:授信--></td>
                <th>用户名:</th>
                <td id="merUserNameView"></td>
            </tr>
            <tr>
                <th>联系人:</th>
                <td id="merLinkUserView"></td>
                <th>手机号码:</th>
                <td id="merLinkUserMobileView"></td>
            </tr>
            <tr>
					<th>结算类型:</th>
					<td id="settlementTypeView">
					</td>
					<th>结算阀值:</th>
					<td id="settlementThresholdView">
					</td>
			</tr>
             <tr>
                <th>启用标识:</th>
                <td id="activateView">
                </td>
                <th id="isAutoDistibuteOneView"  style="display:none;">是否自动分配额度:</th>
                <td id="isAutoDistibuteTwoView"  style="display:none;">
                </td>
               <!-- <th id="limitSourceOneView"  style="display:none;">额度来源:</th>
                <td id="limitSourceTwoView"  style="display:none;">
                </td>-->
            </tr>
            
            
            <tr id="isAutoDistibuteLineView"  style="display:none;">
					<th>额度阀值:</th>
					<td id="limitThresholdView">
					</td>
					<th>自动分配额度阀值:</th>
					<td id="selfMotionLimitThresholdView">
					</td>
				</tr>
                <tr>
					<th>商圈/区域:</th>
					<td  id="tradeAreaView"></td>
					<th>商户打款人:</th>
					<td  id="merPayMoneyUserView"></td>
			</tr>
            <tr>
                <th>详细地址:</th>
                <td colspan="3" id="merAddsView"></td>
            </tr>
  
            <tr>
                <th>业务类型:</th>
                <td id="prdRateTypeView" colspan="3">
                </td>
            </tr>
            <tr>
                <th>通卡公司:</th>
                <td colspan="3" id="tkgsNameView"></td>
            </tr>
        </table>
        <table class="base-table base-table01" id="yktView">
            <tr>
                <th>&nbsp;</th>
                <td colspan="3" style="padding-left: 95px;padding-top: 2px">
                    <div class="tit-pop" style="display: block; position: static; width: 640px;" id="yktDIVView">
                        <table id="yktTableView" width="100%" border="0" class="">
                            <colgroup>
                                <col width="2%"/>
                                <col width="8%"/>
                                <col width="8%"/>
                                <col width="8%"/>
                                <col width="7%"/>
                                <col width="2%"/>
                            </colgroup>
                            <thead>
                            <tr>
                                <th>&nbsp;</th>
                                <th style="font-size: 12px">通卡公司名称</th>
                                <th style="font-size: 12px">业务</th>
                                <th style="font-size: 12px">费率分类</th>
                                <th style="font-size: 12px">数值</th>
                                <th style="font-size: 12px">&nbsp;</th>
                            </tr>
                            </thead>
                            <tbody id="yktTableTbodyView">
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
    </fieldset>
    <br/>
    <fieldset>
        <legend>补充信息</legend>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">经营范围:</th>
                <td id="merBusinessScopeNameView"></td>
                <th style="width:77px;">性别:</th>
                <td id="merUserSexView"></td>
            </tr>
            <tr>
                <th>固定电话:</th>
                <td id="merUserTelephoneView"></td>
                <th>传真:</th>
                <td id="merFaxView"></td>
            </tr>
            <tr>
                <th>证件类型:</th>
                <td id="merUserIdentityTypeView"></td>
                <th>证件号码:</th>
                <td id="merUserIdentityNumberView"></td>
            </tr>
            <tr>
                <th>电子邮箱:</th>
                <td id="merUserEmailView"></td>
                <th>邮编:</th>
                <td id="merZipView"></td>
            </tr>
              <tr>
                <th>学历:</th>
                <td id ="educationView">
                </td>
                <th>年收入（元）:</th>
                <td id="incomeView">
                </td>
            </tr>
            <tr>
                <th>生日:</th>
                <td id="birthdayView">
                </td>
                <th>是否已婚:</th>
                <td id="isMarriedView">
                </td>
            </tr>
            <tr>
                <th>开户银行:</th>
                <td id="merBankNameView"></td>
                <th>开户行账户:</th>
                <td id="merBankAccountView"></td>
            </tr>
            <tr>
                <th>开户名称:</th>
                <td id="merBankUserNameView"></td>
                <th>都都宝固定IP:</th>
                <td id="merDdpLinkIpView"></td>
            </tr>
            <tr>
                <th>都都宝联系人:</th>
                <td id="merDdpLinkUserView"></td>
                <th>联系人电话:</th>
                <td id="merDdpLinkUserMobileView"></td>
            </tr>
            <tr>
                <th valign="top">备注:</th>
                <td colspan="3" id="merUserRemarkView" style="word-break:break-all"></td>
            </tr>
        </table>
        <br/>
    </fieldset>
    <fieldset>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">来源:</th>
                <td id="sourceView"></td>
                <th style="width:77px;"></th>
                <td></td>
            </tr>
            <tr>
                <th>创建人:</th>
                <td id="createUserView"></td>
                <th>创建时间:</th>
                <td id="createDateView"></td>
            </tr>
            <tr>
                <th>审核人:</th>
                <td id="merStateUserView"></td>
                <th>审核时间:</th>
                <td id="merStateDateView"></td>
            </tr>
            <tr>
                <th>编辑人:</th>
                <td id="updateUserView"></td>
                <th>编辑时间:</th>
                <td id="updateDateView"></td>
            </tr>
        </table>
        <br/>
    </fieldset>
</div>
<!-- 编辑折扣start -->
<div id="merDisCountDialog"  title="编辑折扣" draggable="false" resizable="true" toolbar="merDisCountDialogToolbar">
		<div  class="tableToolbar" >
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="enable" onclick="showAllTranDiscount()">绑定折扣</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="disable" onclick="operateAccountById()">解除折扣</a>
		</div>
        <!--交易折扣记录列表显示start -->
		<div region="center" border="false" style="overflow: hidden;">
			<table id="merDisCountListTbl"></table>
		</div>
		<div id="merDisCountListTblPagination"></div>
		<!--交易折扣记录列表显示end -->
</div>
<!-- 编辑折扣end -->
<!-- 绑定折扣start -->
<div id="merDisCountAllDialog"  title="绑定折扣" draggable="true" resizable="true" toolbar="merDisCountAllDialogToolbar">
		<div  class="tableToolbar" >
			<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="enable" onclick="insertTranDiscount()">绑定折扣</a>
		</div>
        <!--交易折扣记录列表显示start -->
		<div region="center" border="false" style="overflow: hidden;">
			<table id="merDisCountAllListTbl"></table>
		</div>
		<div id="merDisCountAllListTblPagination"></div>
		<!--交易折扣记录列表显示end -->
</div>
<!-- 绑定折扣end -->
</body>
</html>
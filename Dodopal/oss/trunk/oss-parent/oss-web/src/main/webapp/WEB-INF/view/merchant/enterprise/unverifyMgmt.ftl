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
    </style>
    <!--Component JS -->
    <script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'></script>
    <script type="text/javascript" src="../../js/common/merchantddicUnver.js"></script>
    <script type="text/javascript" src="../../js/common/merchntType.js"></script>
    <script type="text/javascript" src="../../js/common/unverifyYktLoadInfo.js"></script>
    <script type="text/javascript" src="../../js/common/yktLoadInfo.js"></script>
    <script type="text/javascript" src="../../js/common/merUnverSearchModel.js"></script>
    <script type="text/javascript" src="../../js/merchant/enterprise/unverify.js"></script>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <title>未审核商户信息</title>
</head>
<body class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:90px;overflow: hidden;">
    <table id="merChantQueryCondition" class="viewtable">
        <tr>
            <th>商户名称:</th>
            <td><input type="text" id="merNameQuery"></td>
            <th>上级商户名称（精确）:</th>
            <td><input type="text" id="merParentNameQuery"></td>
            <th>省市:</th>
            <td id="proCityQuery" panelHeight="70px;"></td>
            <td>
                &nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findUnvenrifyMerchants(1);">查询</a>
                &nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser"
                   onclick="clearAllText('merChantQueryCondition');">重置</a>
            </td>
        </tr>
        <tr>
            
            <th>联系人:</th>
            <td><input type="text" id="merLinkUserQuery"></td>
            <th>手机号码:</th>
            <td><input type="text" id="merLinkUserMobileQuery"></td>

        </tr>
    </table>
</div>
<div region="center" border="false">
    <table id="unverifyTbl" fit="true"></table>
</div>
<div id="unverifyMerchantTblToolbar" class="tableToolbar">
<@sec.accessControl permission="merchant.enterprise.unverify.audit">
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editorVerified"
       onclick="editorVerified()">审核</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.unverify.find">
    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="findVerified"
       onclick="findVerified()">查看</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.unverify.exportUnverify">
   <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportUnverifyMgmt">导出Excel</a>
</@sec.accessControl>
</div>
<!--详情页面开始-->
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
                <th>账户类型:</th>
                <td id="fundTypeView"></td>
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
                <td id="activateView"></td>
                 <th id="isAutoDistibuteOneView"  style="display:none;">是否自动分配额度:</th>
                <td id="isAutoDistibuteTwoView"  style="display:none;">
                </td>
                </td>
              <!--  <th id="limitSourceOneView"  style="display:none;">额度来源:</th>
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
                <td id ="educationView"></td>
                <th>年收入（元）:</th>
                <td id="incomeView"></td>
            </tr>
            <tr>
                <th>生日:</th>
                <td id="birthdayView"></td>
                <th>是否已婚:</th>
                <td id="isMarriedView"></td>
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
                <th style="width:77px;">创建人:</th>
                <td id="createUserView"></td>
                <th style="width:77px;">创建时间:</th>
                <td id="createDateView"></td>
            </tr>
        </table>
    </fieldset>
</div>

<!--详情页面结束-->
<div id="unverifyMerchantTblPagination"></div>
<div id="unverifyMerchantDialog" title="" draggable="false" maximized="true" toolbar="#unverifyMerchantDialogToolbar">
    <fieldset>
        <legend>注册信息</legend>
        <table class="edittable">
            <input name="merCode" id="merCode" type="hidden"/>
            <input name="userCode" id="userCode" type="hidden"/>
            <input name="flag" id="flag" value="1" type="hidden"/>
             <input name="merPT" id="merPT" value="" type="hidden"/>
             <input name="merUnVer" id="merUnVer" value="0" type="hidden"/>
             <input name="selectedProRateInfo" id="selectedProRateInfo" type="hidden"/>
             <input name="selectedProRateInfoPayment" id="selectedProRateInfo" type="hidden"/>
            <tr>
                <th style="width:93px;">商户名称:</th>
                <td><input id="merName" type="text" style="width:198px;" class="easyui-validatebox"
                           data-options="required:true,validType:'merName'" maxlength="50"/><font
                        color="red">*</font></td>
                <th style="width:93px;">商户类型:</th>
                <td style="width:250px;"><select id="merType" name="merType" style="width:200px;"></select><font color="red">*</font></td>
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
                    <label><input type="radio" name="merProperty" value="1" onclick="merPropertyClick('customer');"/>自定义商户
                        <a href="#" class="easyui-linkbutton" plain="true" onclick="merPropertyClick('customer');"
                           id="viewMerProperty" style="display:none;">查看</a></label><font color="red">*</font>
                </td>
            </tr>
            <tr id="merTypeParentLine">
                <th style="width:93px;">上级商户类型:</th>
                <td><select id="merParentTypes" name="merParentTypes"></select><font color="red">*</font></td>
                <th style="width:93px;">上级商户名称:</th>
                <td style="width:250px;">
                    <input name="merParentName" id="merParentName" style="width:199px;" disabled/>
                    <input name="merParentCode" id="merParentCode" type="hidden"/>
                    <input type="button" onclick="findMerParentName();" id="finMER" value="选择" style="height:24px;"/>
                    <font color="red">*</font>
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
                    <input name="merUserName" id="merUserName" style="width:198px;" type="text"
                           class="easyui-validatebox" data-options="required:true,validType:'AazNumber'"
                           maxlength="20"/><font color="red">*</font>
                </td>
            </tr>
            <tr>
                <th>联系人:</th>
                <td>
                    <input name="merLinkUser" id="merLinkUser" style="width:198px;" type="text"
                           class="easyui-validatebox" data-options="required:true,validType:'merLinkUser[2,20]'"
                           maxlength="20"/><font color="red">*</font>
                </td>
                <th>手机号码:</th>
                <td>
                    <input id="merLinkUserMobile" name="merLinkUserMobile" style="width:199px;" type="text"
                           class="easyui-validatebox" data-options="required:true,validType:'mobile'"
                           maxlength="11"/><font color="red">*</font>
                </td>
            </tr>
            <tr>
					<th>结算类型:</th>
					<td>
						<select id="settlementType" class="easyui-combobox"  style="width:199px;" editable="false"></select>
						<font id="settlementTypeFont" color="red">*</font>
					</td>
					<th>结算阀值:</th>
					<td>
					<input id="settlementThreshold" type="text" style="text-align:right;width:199px;" maxLength="8"
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
                    <label><input type="radio" name="isAutoDistibute" value="0"  checked="checked"  onclick="isAutoDistibuteCheck();"/>是</label>
                    <label><input type="radio" name="isAutoDistibute" value="1"  onclick="notIsAutoDistibuteCheck();"/>否</label>
                    <label><input type="radio" name="isAutoDistibute" value="2"  onclick="notIsAutoDistibuteCheck();"/>共享额度</label><font color="red">*</font>
                </td>
                
                <!--<th id="limitSourceOne"  style="display:none;">额度来源:</th>
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
             <tr>
					<th>商圈/区域:</th>
					<td>
					     <input id="tradeArea" name="tradeArea" type="text" class="easyui-validatebox" style="text-align:right;width:199px;" maxlength="20"></input><font color="red"></font>
					</td>
					<th>商户打款人:</th>
					<td>
					     <input id="merPayMoneyUser" name="merPayMoneyUser" type="text" class="easyui-validatebox" style="text-align:right;width:199px;" maxlength="50"></input><font color="red"></font>
					</td>
			</tr>
            <tr>
                <th id="merAddress">详细地址:</th>
                <td id="merAddressLine" colspan="4">
                	<span style="width:4px;"></span>
                    <input id="merAddressName" type="text" style="width:318px; height:18px;" class="easyui-validatebox"
                           data-options="required:true" maxlength="200"/><font color="red">*</font>
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
            </tr>
            <tr id="TKNameLine" style="display:none;">
                <th>通卡公司(充值):</th>
                <td colspan="4">
                    <input type="text" name="providerCode" id="providerCode" disabled style="width:524px;"/>
                    <input name="ykt" id="ykt" type="button" onclick="yktSelector('yktInfoContent');" value="选择" style="height:24px;"/><font color="red">*</font>
                </td>
            </tr>
            <tr id="yktTblLine" style="display:none;">
                <th></th>
                <td colspan="4">
                    <table id="yktTbl"></table>
                </td>
            </tr>
            <tr id="TKNameLinePayment" style="display:none;">
                <th>通卡公司(消费):</th>
                <td colspan="4">
                    <input type="text" name="providerCodePayment" id="providerCodePayment" disabled style="width:524px;"/>
                    <input name="yktPayment" id="yktPayment" type="button" onclick="yktSelectorPayment('yktInfoContentPayment');" value="选择" style="height:24px;"/><font color="red">*</font>
                </td>
            </tr>
            <tr id="yktTblLinePayment" style="display:none;">
                <th></th>
                <td colspan="4">
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
                           style="width:198px;" data-options="validType:'telephone'" maxlength="20"/>
                </td>
                <th>传真:</th>
                <td>
                    <input name="merFax" id="merFax" class="easyui-validatebox" style="width:198px;"
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
                    <input name="merUserIdentityNumber" class="easyui-validatebox" id="merUserIdentityNumber"
                           type="text" style="width:198px;" validType=ddpComboboxIdCard['#merUserIdentityType']
                           type="text" maxlength="20"/>
                </td>
            </tr>
            <tr>
                <th>电子邮箱:</th>
                <td>
                    <input name="merUserEmail" id="merUserEmail" style="width:198px;" type="text"
                           class="easyui-validatebox" style="width:199px;" data-options="validType:'email'"
                           maxlength="60"/>
                </td>
                <th>邮编:</th>
                <td>
                    <input name="merZip" id="merZip" type="text" style="width:198px;" class="easyui-validatebox"
                           data-options="validType:'zip'" maxlength="6"/>
                </td>
            </tr>
            <tr>
                <th>学历:</th>
                <td>
                  <select id="education" name="education" class="easyui-combobox"  style="width:199px;" ></select>
                </td>
                <th>年收入（元）:</th>
                <td>
                <input id="income" name="income" type="text" style="width:199px;" ></input>
                </td>
            </tr>
            <tr>
                <th>生日:</th>
                <td>
                <input id="birthday" name="birthday" style="width:199px;" type="text" onfocus="WdatePicker({skin:'whyGreen',minDate:'1900-01-01',maxDate:'%y-%M-%d'})"/>
                </td>
                <th>婚姻状况:</th>
                <td>
                   <select id="isMarried" class="easyui-combobox"  panelHeight="100"  style="width:199px;" editable="false"></select>
                </td>
            </tr>
            <tr>
                <th>开户银行:</th>
                <td>
                    <select id="merBankName" class="easyui-combobox" editable="false" style="width: 200px;">
                    </select>
                </td>
                <th>开户行账户:</th>
                <td>
                    <input name="merBankAccount" id="merBankAccount" type="text" class="easyui-validatebox" style="width:198px;"/>
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
                    <input name="merDdpLinkIp" id="merDdpLinkIp" type="text" style="width:198px;"
                           class="easyui-validatebox" data-options="validType:'IP'" maxlength="15"/>
                </td>
            </tr>
            <tr id="encryptionLine">
                <th>商户签名秘钥:</th>
                <td><input name="merMd5paypwd" id="merMd5paypwd" style="width:198px;" class="easyui-validatebox"
                           data-options="validType:'egNumber'" maxlength="32"/></td>
                <th>商户验签秘钥:</th>
                <td><input name="merMd5Backpaypwd" id="merMd5Backpaypwd" style="width:198px;" class="easyui-validatebox"
                           data-options="validType:'egNumber'" maxlength="32"/></td>
            </tr>
            <tr>
                <th>备注:</th>
                <td colspan="4"><textarea rows="4" name="merUserRemark" id="merUserRemark"  style="width:520px;"
                                          maxlength="200" class="easyui-validatebox"
                                          data-options="validType:'lengthNumber[200]'" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"></textarea></td>
            </tr>
        </table>
        <br/>
    </fieldset>
    <div id="unverifyMerchantDialogToolbar" style="text-align:right;">
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="unverifyMerchant('1')">审核通过</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
           onclick="openRejectReasonDialog()">审核不通过</a>
        <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
           onclick="cancelAction('unverifyMerchantDialog')">取消</a>
    </div>
</div>
<!--审核不通过原因-->
<div id="rejectReasonDialog" title="审核不通过原因" draggable="false" maximized="false" style="overflow: hidden;">
	<div id="rejectReason">
		<textarea rows="10" name="merRejectReason" id="merRejectReason" style="width:430px;" class="easyui-validatebox" data-options="required:true" maxlength="500"></textarea>
	</div>
	<br>
	<div style="text-align:center;">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="unverifyNotMerchant('2')">确定</a>
    	&nbsp;
    	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="hideDialog('rejectReasonDialog');">取消</a>
	</div>
</div>
<div id="customerDialog" title="自定义商户信息" draggable="true" maximized="false">
    <div id="customerProperty"></div>
</div>
<input type="hidden" id="dataflag" />
<div id="yktDialog" title="通卡公司信息" draggable="true" maximized="false">
    <table id="yktInfoContent" class="checkboxView"></table>
</div>
<div id="yktDialogPayment" title="通卡公司信息" draggable="true" maximized="false">
    <table id="yktInfoContentPayment" class="checkboxView"></table>
</div>
<div id="yktIcLoadDialog" title="通卡公司信息(圈存)" draggable="true" maximized="false">
	<table id="yktIcLoad" class="checkboxView"></table>
</div>
</body>
</html>
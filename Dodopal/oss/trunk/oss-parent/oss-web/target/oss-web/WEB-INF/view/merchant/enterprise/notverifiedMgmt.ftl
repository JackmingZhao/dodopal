<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
<#include "../../include.ftl"/>
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
    <script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'></script>
    <script type="text/javascript" src="../../js/common/merchantddic.js"></script>
    <script type="text/javascript" src="../../js/common/merchntType.js"></script>
    <script type="text/javascript" src="../../js/common/merchantSearchModel.js"></script>
    <script type="text/javascript" src="../../js/merchant/enterprise/notverified.js"></script>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
	<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <title>审核不通过商户信息</title>
</head>
<body class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:60px;overflow: hidden;">
    <table id="merChantQueryCondition" class="viewtable">
        <tr>
            <th>商户名称:</th>
            <td><input type="text" id="merNameQuery" style="width:125px;"></td>
            <th>上级商户名称(精确):</th>
            <td><input type="text" id="merParentNameQuery" style="width:125px;"></td>
            <th>手机号码:</th>
            <td><input type="text" id="merLinkUserMobileQuery" style="width:100px;"></td>
            <th>省市:</th>
            <td id="proCityQuery" panelHeight="70px;"></td>
            <td>
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findNotverifiedMerchantUsers(1);">查询</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser"
                   onclick="clearAllText('merChantQueryCondition');">重置</a>
            </td>
        </tr>
    </table>
</div>
<div region="center" border="false">
    <table id="merNotverifiedTbl" fit="true"></table>
</div>
<div id="merNotverifiedTblToolbar">
<@sec.accessControl permission="merchant.enterprise.notverified.find">
    <a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="findNotverified"
       onclick="viewNotverifiedMerchantUser()">查看</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.notverified.delete">
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="delNotverified"
       onclick="delNotverifMerchant()">删除</a>
</@sec.accessControl>
<@sec.accessControl permission="merchant.enterprise.notverified.exportNotverified">
  <a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="exportNotverifiedMgmt">导出Excel</a>
</@sec.accessControl>
</div>
<div id="viewMerchantDialogToolbar" style="text-align:right;">
    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="closeViewMerchant()">返回</a>
</div>
<div id="viewMerchantDialog" style="align:center;" title="" maximized="true" class="easyui-dialog" closed="true"
     draggable="false" toolbar="#viewMerchantDialogToolbar">
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
                <td id="accountTypeView"><!--1:资金/2:授信--></td>
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
                <td colspan="3" id="activateView"></td>
                <th id="isAutoDistibuteOneView"  style="display:none;">是否自动分配额度:</th>
                <td id="isAutoDistibuteTwoView"  style="display:none;">
                </td>
               <!-- <th id="limitSourceOneView"  style="display:none;">额度来源:</th>
                <td id="limitSourceTwoView"  style="display:none;">
                </td>-->
            </tr>
            <tr>
                <th>详细地址:</th>
                <td colspan="3" id="merAddsView"></td>
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
<div id="merNotverifiedTblPagination"></div>
</body>
</html>
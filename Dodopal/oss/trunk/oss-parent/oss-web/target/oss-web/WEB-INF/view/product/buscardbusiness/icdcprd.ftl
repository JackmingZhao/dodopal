<#assign sec=JspTaglibs["/WEB-INF/tld/ossTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <#include "../../include.ftl"/>
    <!--Component JS -->
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="../../js/product/buscardbusiness/icdcprd.js"></script>
    <style type="text/css">
    </style>
    <title>一卡通充值产品</title>
</head>
<body class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:60px;overflow: hidden;">
    <table id="queryCondition" class="viewtable">
        <tr>
            <th>产品名称:</th>
            <td><input type="text" id="nameQuery" style="width: 150px;"></td>
            <th>供应商:</th>
            <td>
                <input id="supplierQuery" class="easyui-combobox" data-options="valueField:'id',textField:'name'" style="width: 150px;"></input>
            </td>
            <th>业务城市:</th>
            <td>
                <input id="cityQuery" class="easyui-combobox" data-options="valueField:'id',textField:'name'" style="width: 100px;"></input>
            </td>
            <th>面值:</th>
            <td><input type="text" id="valueQuery" style="width: 90px;" maxlength="4">元</td>
            <th>状态:</th>
            <td>
                <select id="saleUporDownQuery" class="easyui-combobox" editable="false" style="width: 55px;">
                    <option value='0' selected="selected">上架</option>
                    <option value='1'>下架</option>
					<option value=''>全部</option>
                </select>
            </td>
            <th></th>
            <td>
            	<@sec.accessControl permission="buscardbusiness.icdcprd.query">
                	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="icdcPrdQuery();">查询</a>
                </@sec.accessControl>&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="resetPrdQuery();">重置</a>
            </td>
        </tr>
    </table>
</div>
<div region="center" border="false">
    <table id="dataTable" fit="true"></table>
</div>
<div id="dataTableToolbar">
	<@sec.accessControl permission="buscardbusiness.icdcprd.add">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="auditVerified" onclick="addIcdcPrd();">添加</a>
	</@sec.accessControl>
	<@sec.accessControl permission="buscardbusiness.icdcprd.modify">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editorVerified" onclick="modifyIcdcPrd();">编辑</a>
	</@sec.accessControl>
	<@sec.accessControl permission="buscardbusiness.icdcprd.putaway">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="findVerified" onclick="putAwayIcdcPrd();">上架</a>
	</@sec.accessControl>
	<@sec.accessControl permission="buscardbusiness.icdcprd.soltout">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="findVerified" onclick="soltOutIcdcPrd();">下架</a>
	</@sec.accessControl>
	<@sec.accessControl permission="buscardbusiness.icdcprd.view">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-view" plain="true" id="disableVerified" onclick="viewIcdcPrd();">查看</a>
	</@sec.accessControl>
	<@sec.accessControl permission="buscardbusiness.icdcprd.export">
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" id="btnSelExcCol" >导出Excel</a>
	</@sec.accessControl>
</div>
<div id="dataTablePagination"></div>

<div id="icdcPrdAddDialog" class="easyui-dialog" maximized="true" title="" closed="true" draggable="false"
     toolbar="#icdcPrdAddDialogToolbar">
    <fieldset>
        <legend id="dialogNameA">一卡通充值产品</legend>
        <table class="edittable">
            <input name="id" id="id" type="hidden"/>
            <tr>
                <th style="width:77px;">通卡公司:</th>
                <td>
                    <input class="easyui-combobox" id="icdcProA" style="width:206px;"
                           data-options="valueField:'id',textField:'name',required:true"/><font color="red">*</font>
                </td>
                <th style="width:77px;">城市:</th>
                <td>
                    <input id="cityA" class="easyui-combobox" style="width: 202px;"
                           data-options="valueField:'id',textField:'name',required:true"/><font color="red">*</font>
                </td>
            </tr>
            <tr>
                <th>面价:</th>
                <td><input id="priceValueA" type="text" class="easyui-validatebox" style="width:202px;"
                           data-options="required:true,validType:'icdcprdAmount'" maxlength="8"/>元<font
                        color="red">*</font></td>
                <th>状态:</th>
                <td>
                    <select id="statusA" class="easyui-combobox" editable="false" data-options="required:true" style="width: 202px;">
                        <option value="0" selected="selected">上架</option>
                        <option value="1">下架</option>
                    </select>
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <th valign="top">备&nbsp;注:</th>
                <td colspan="3">
                    <textarea id="commentsA" rows="6" cols="60" maxlength="200" data-options="validType:'lengthNumber[200]'" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);" style="width: 524px;"></textarea>
                </td>
            </tr>
        </table>
    </fieldset>
</div>

<div id="icdcPrdAddDialogToolbar" style="text-align:right;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveIcdcPrd();">保存</a>
    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="cancelAction('icdcPrdAddDialog')">取消</a>
</div>
<!-- 编辑界面对话框 -->
<div id="icdcPrdEditDialog" class="easyui-dialog" maximized="true" title="" closed="true" draggable="false"
     toolbar="#icdcPrdEditDialogToolbar">
    <fieldset>
        <legend id="dialogName">一卡通充值产品</legend>
        <table class="edittable">
            <input name="id" id="id" type="hidden"/>
            <tr>
                <th style="width:77px;">产品编码:</th>
                <td><input id="codeE" type="text" class="easyui-validatebox" style="width:199px;" maxlength="50"/><font
                        color="red">*</font></td>
                <th style="width:77px;">产品名称:</th>
                <td><input id="nameE" type="text" class="easyui-validatebox" style="width:199px;" maxlength="50"/><font
                        color="red">*</font></td>
            </tr>
            <tr>
                <th>通卡公司:</th>
                <td>
                    <input class="easyui-combobox" id="icdcProE" name="icdcProE" style="width:202px;"
                           data-options="valueField:'id',textField:'name'">
                    <font color="red">*</font>
                </td>
                <th>城市:</th>
                <td>
                    <input id="cityE" class="easyui-combobox" style="width: 202px;">
                    </input>
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <th>面价:</th>
                <td><input id="priceValueE" type="text" class="easyui-validatebox" style="width:199px;text-align:right;"
                           data-options="required:true,validType:'positiveInteger'" maxlength="10"/>元<font
                        color="red">*</font></td>
                <th>状态:</th>
                <td>
                    <select id="statusE" class="easyui-combobox" style="width: 202px;">
                        <option value="0" selected="selected">上架</option>
                        <option value="1">下架</option>
                    </select>
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <th valign="top">备&nbsp;注:</th>
                <td colspan="3">
                    <textarea id="commentsE" rows="6" cols="60" maxlength="200" data-options="validType:'lengthNumber[200]'" onpropertychange="onmyinput(this)" oninput="return onmyinput(this)" onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);" style="width: 522px;"></textarea>
                </td>
            </tr>
        </table>
    </fieldset>
</div>
<div id="icdcPrdEditDialogToolbar" style="text-align:right;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="updateIcdcPrd();">保存</a>
    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="cancelAction('icdcPrdEditDialog')">取消</a>
</div>

<!-- 查看详情界面对话框 -->
<div id="icdcPrdViewDialog" title="" maximized="true" class="easyui-dialog" closed="true" draggable="false"
     toolbar="#icdcPrdViewDialogToolbar">
    <fieldset>
        <legend>一卡通充值产品</legend>
        <table class="viewOnly">
            <tr>
                <th style="width:77px;">产品编码:</th>
                <td id="codeView"></td>
                <th style="width:77px;">产品名称:</th>
                <td id="nameView"></td>
            </tr>
            <tr>
                <th>通卡公司:</th>
                <td id="icdcProView"></td>
                <th>城市:</th>
                <td id="cityView"></td>
            </tr>
            <tr>
                <th>费率:</th>
                <td id="rateView"></td>
                <th>成本价:</th>
                <td id="costPriceView"></td>
            </tr>
            <tr>
                <th>面价:</th>
                <td id="valuePriceView"></td>
                <th>状态:</th>
                <td id="statusView"></td>
            </tr>
            <tr>
                <th valign="top">备注:</th>
                <td id="commentsView" style="word-break:break-all"></td>
            </tr>
        </table>
        <br/>
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
                <th>编辑人:</th>
                <td id="updateUserView"></td>
                <th>编辑时间:</th>
                <td id="updateDateView"></td>
            </tr>
        </table>
        <br/>
    </fieldset>
    <br/>
</div>
<div id="icdcPrdViewDialogToolbar" style="text-align:right;">
    <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="closeViewIcdcPrd();">返回</a>
</div>
<!-- --------------------------------------------------------对话框结束---------------------------------------------------------- -->

</body>
</html>
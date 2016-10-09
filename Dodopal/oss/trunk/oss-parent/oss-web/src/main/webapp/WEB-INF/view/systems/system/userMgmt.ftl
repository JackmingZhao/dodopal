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
    <script type="text/javascript" src="../../js/systems/system/userMgmt.js"></script>
    <script type="text/javascript" src="../../js/utils/jsFilterUtil.js"></script>
    <script type='text/javascript' src='${scriptUrl}/component/areaComponent.js'></script>
    <script type="text/javascript" src="../../js/common/exportExcelUtil.js"></script>
    <script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
    <title>OSS</title>
</head>
<body class="easyui-layout" style="overflow: hidden;">
<div region="north" border="false" style="height:60px;overflow: hidden;">
    <div class="ui-state-default" style="border:none;">
    </div>
    <form name="userQuery" id="userQuery">
        <table id="findTB" class="viewtable">
            <tr>
                <th>用户名:</th>
                <td><input type="text" id="userLoginNameQuery"></td>
                <th>姓名:</th>
                <td><input type="text" id="userNameQuery"></td>
                <th>启用标识:</th>
                <td>
                    <select id="activateFind" panelHeight="78" class="easyui-combobox" editable="false" style="width: 146px;">
                        <option value="0" selected="selected">启用</option>
                        <option value="1">停用</option>
                        <option value="">全部</option>
                    </select>
                </td>
                <td style="width:20px;"></td>
            <@sec.accessControl permission="system.user.query">
                <td>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findUser(1);">查询</a>
                </td>
            </@sec.accessControl>

                <td style="width:10px;"></td>
                <td>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('findTB');">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div region="center" border="false">
    <table id="userTbl" fit="true"></table>
</div>
<div id="userTblToolbar" class="tableToolbar">
<@sec.accessControl permission="system.user.add">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addUser" onclick="addUser()">添加</a>
</@sec.accessControl>
<@sec.accessControl permission="system.user.modify">
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editUser" onclick="editUser()">编辑</a>
</@sec.accessControl>
    <#--<@sec.accessControl permission="system.user.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="deleteUser" onclick="deleteUser()">删除</a>
		</@sec.accessControl>-->
<@sec.accessControl permission="system.user.start">
    <a href="#" class="easyui-linkbutton" iconCls="icon-activate" plain="true" id="startUser"
       onclick="startUser()">启用</a>
</@sec.accessControl>
<@sec.accessControl permission="system.user.disable">
    <a href="#" class="easyui-linkbutton" iconCls="icon-inactivate" plain="true" id="disable"
       onclick="disableUser()">停用</a>
</@sec.accessControl>
<@sec.accessControl permission="system.user.updatepwd">
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="resetPWD"
       onclick="resetPWD();">重置密码</a>
</@sec.accessControl>
<@sec.accessControl permission="system.user.export">
<a id="btnSelExcCol" href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" >导出Excel</a>
</@sec.accessControl>
</div>
<div id="userTblPagination"></div>
<div id="userDialog" title="" draggable="false" maximized="true" toolbar="#userDialogToolbar">
    <form id="userDialogForm" name="userForm">
        <input type="hidden" name="userId" id="userId"></input>
        <fieldset>
            <legend>基础信息</legend>
            <table class="edittable">
                <tr>
                    <th>用户名:</th>
                    <td id="loginNamefield"><input name="loginName" style="width: 146px;" id="loginName" type="text"
                                                   maxlength="20" class="easyui-validatebox"
                                                   data-options="required:true,validType:'numberEnUndreScore[4,20]'"/><font
                            size="1" color="red">*</font></td>
                    <th>电子邮箱:</th>
                    <td><input name="email" style="width: 146px;" id="email" type="text" maxlength="60"
                               class="easyui-validatebox" data-options="required:true,validType:'email'"/><font size="1"
                                                                                                                color="red">*</font>
                    </td>
                    <th>启用标识:</th>
                    <td>
                        <label><input name="activate" type="radio" value="0" checked="checked"/>启用</label>
                        <label><input name="activate" type="radio" value="1"/>停用 </label><font color="red">*</font>
                    </td>
                </tr>
                <tr id="pwdArea">
                    <th>密码:</th>
                    <td><input name="pwd" style="width: 146px;" id="pwd"  maxlength="20" type="password" class="easyui-validatebox"
                               data-options="required:true,validType:'pwdLength'"/><font size="1"
                                                                                                color="red">*</font></td>
                    <th>重复密码:</th>
                    <td><input name="pwd2" style="width: 146px;" id="pwd2" type="password" class="easyui-validatebox"
                               data-options="required:true" validType="checkPWD['#pwd']"/><font size="1"
                                                                                                color="red">*</font>
                    </td>
                </tr>
            </table>
               <div Style="padding-top:10px;">角色信息: </div>
            <ul id='roleTree' style='margin:5px;'></ul>
            <table id="roleList" class="edittable">
            </table>
        </fieldset>
        <fieldset>
            <legend>个人信息</legend>
            <table class="edittable">
                <tr>
                    <th>部门:</th>
                    <td>
                        <select id="departmentCode" class="easyui-combobox" style="width: 148px;">
                        </select>
                        <font size="1" color="red">*</font>
                    </td>
                    <th>姓名:</th>
                    <td><input name="name" id="name" type="text" maxlength="20" class="easyui-validatebox"
                               data-options="required:true,validType:'enCn[2,20]'"/><font size="1" color="red">*</font>
                    </td>
                    <th>昵称:</th>
                    <td><input name="nickName" id="nickName" type="text" maxlength="20" class="easyui-validatebox"
                               data-options="validType:'enCn[2,20]'"/></td>
                </tr>
                <tr>
                    <th>手机号:</th>
                    <td><input name="mobile" style="width: 144px;" id="mobile" type="text" maxlength="11" class="easyui-validatebox"
                               data-options="validType:'mobile'"/><font size="1" color="red"></font></td>
                    <th>固定电话:</th>
                    <td><input name="tel" id="tel" type="text" maxlength="20" class="easyui-validatebox"
                               data-options="validType:'telephone'"/></td>
                    <th>性别:</th>
                    <td>
                        <label><input name="sex" type="radio" value="0" checked="checked"/>男</label>
                        <label><input name="sex" type="radio" value="1"/>女</label>
                    </td>
                </tr>
                <tr>
                    <th>证件类型:</th>
                    <td>
                        <select id="identityType" panelHeight="100" class="easyui-combobox" editable="false" style="width: 148px;">
                        </select>
                    </td>
                    <th>证件号码:</th>
                    <td>
               <input name="identityId" id="identityId" type="text" maxlength="20" class="easyui-validatebox"
                               validType=ddpComboboxIdCard['#identityType'] />
                    </td>
                    <th>邮编:</th>
                    <td><input name="zipCode" id="zipCode" type="text" maxlength="6" class="easyui-validatebox"
                               data-options="validType:'zip'"/></td>
                </tr>
            </table>
            <table class="edittable">
                <tr id="addressLine" colspan="4">
                    <th id="address">详细地址:</th>
                </tr>
                <tr>
                    <th valign="top">备注:</th>
                    <td colspan="4" style="word-break:break-all">
                        <textarea rows="6" name="comments" id="comments" style="width: 665px;" maxlength="200"
                                  class="easyui-validatebox" data-options="validType:'lengthRange[0,200]'"
                                  onpropertychange="onmyinput(this)" oninput="return onmyinput(this)"
                                  onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"></textarea>
                    </td>
                </tr>
            </table>
        </fieldset>
        <div id="userDialogToolbar" style="text-align:right;">
            <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveUserInfo();">保存</a>
            <a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
               onclick="cancelAction('userDialog')">取消</a>
        </div>
    </form>
</div>
</body>
</html>
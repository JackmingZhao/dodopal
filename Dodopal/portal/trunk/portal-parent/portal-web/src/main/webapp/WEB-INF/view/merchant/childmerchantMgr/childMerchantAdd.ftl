<div class="con-main" id="childMerchantDetail" style="display:none;"> <!-- InstanceBeginEditable name="main" -->
    <div class="new-peo">
        <h3 class="tit-h3">子商户开户信息</h3>

        <div class="com-bor-box com-bor-box01">
            <div>
                <h4 class="tit-h4">注册信息</h4>
            </div>
            <form action="/" id="addChildMerchantForm">
                <input type="hidden" id="merProType" name="merProType" value="${(sessionUser.merType)!}"/>
                <table class="base-table">
                    <col width="120"/>
                    <col width="330"/>
                    <col width="120"/>
                <#if sessionUser.merType =='12'>
                    <tr>
                        <th><span class="red">*</span>商户类型：</th>
                        <td><select name="merType" id="merType" onChange="checkMerType()">
                            <option value="" selected>－－ 请选择 －－</option>
                            <option value="13">直营</option>
                            <option value="14">加盟</option>
                        </select>

                            <div class="tip-error tip-red-error" id="merTypeERR"></div>
                        </td>
                        <th>启用标识：</th>
                        <td>
                            <ul class="ipt-list clearfix">
                                <li>
                                    <label>
                                        <input type="radio" name="activate" checked="checked" value="0"/>
                                        启用</label>
                                </li>
                                <li>
                                    <label>
                                        <input type="radio" name="activate" value="1"/>
                                        停用</label>
                                </li>
                            </ul>
                            <div class="tip-error tip-red-error"></div>
                        </td>
                    </tr>
                    <tr id="isAutoShow">
                        <th><span class="red">*</span>是否自动分配：</th>
                        <td>
                            <ul class="ipt-list clearfix">
                                <li>
                                    <label>
                                        <input type="radio" name="isAuto" value="0" onclick="isAutoClick('true')"/>
                                        是</label>
                                </li>
                                <li>
                                    <label>
                                        <input type="radio" name="isAuto" checked="checked" value="1"
                                               onclick="isAutoClick('false')"/>
                                        否</label>
                                </li>
                                <li>
                                    <label>
                                        <input type="radio" name="isAuto" value="2" onclick="isAutoClick('false')"/>
                                        共享额度</label>
                                </li>
                            </ul>
                            <div class="tip-error tip-red-error">< /div>
                        </td>
                    </tr>

                    <tr id="thresholdShow">
                        <th><span class="red">*</span>额度阀值：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="数字，整数6位，两位小数" id="threshold"
                                   name="threshold" maxlength="20" onfocus="validateChildThreshold(false);"/>

                            <div class="tip-error tip-red-error" id="thresholdERR"></div>
                            <div class="tip-error tip-red-error" id="limitERR2" style="display:none"></div>
                        </td>
                        <th><span class="red">*</span>自动分配额度：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="数字，整数6位，两位小数" id="limit"
                                   name="limit" maxlength="20" onfocus="validateChildLimit(false);"/>

                            <div class="tip-error tip-red-error" id="limitERR"></div>
                            <div class="tip-error tip-red-error" id="limitERR2" style="display:none"></div>
                        </td>
                    </tr>

                    <!--  <tr id="isAutoJoinShow">
                                <th><span class="red">*</span>额度来源：</th>
                                <td><ul class="ipt-list clearfix">
                                        <li>
                                            <label>
                                                <input type="radio"  name="isAutoJoin" checked="checked" value="0"  />
                                                自己购买</label>
                                        </li>
                                        <li>
                                            <label>
                                                <input type="radio" name="isAutoJoin" value="1" />
                                                上级分配</label>
                                        </li>
                                    </ul>
                                    <div class="tip-error tip-red-error"></div></td>
                            </tr>-->
                </#if>
                    <tr>
                        <th><span class="red">*</span>商户名称：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="支持中文、数字及字母" id="merName"
                                   name="merName" maxlength="50" onfocus="validateChildMerName(false);"/>

                            <div class="tip-error tip-red-error" id="merNameERR"></div>
                            <div class="tip-error tip-red-error" id="merNameERR2" style="display:none"></div>
                        </td>
                        <th><span class="red">*</span>用户名：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder='4-20位字符，支持字母、数字、“_”，首位为字母'
                                   id="merUserName" name="merUserName" maxlength="20"
                                   onfocus="validateChildMerUserName(false);"/>

                            <div class="tip-error tip-red-error" id="merUserNameERR"></div>
                            <div class="tip-error tip-red-error" id="merUserNameERR2" style="display:none"></div>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>密码：</th>
                        <td><input type="password" class="com-input-txt w260" myPlaceholder="6-20位字符，支持数字、字母及符号"
                                   id="merUserPWD" name="merUserPWD" maxlength="20"
                                   onfocus="validateMerUserPWD(false);"/>

                            <div class="tip-error tip-red-error" id="merUserPWDERR"></div>
                            <div class="tip-error tip-red-error" id="merUserPWDERR2" style="display:none"></div>
                        </td>
                        <th><span class="red">*</span>确认密码：</th>
                        <td><input type="password" class="com-input-txt w260" myPlaceholder="请再次输入密码" id="merUserPWDTwo"
                                   name="merUserPWDTwo" maxlength="20" onfocus="validateMerUserPWD2(false);"/>

                            <div class="tip-error tip-red-error" id="merUserPWDTwoERR"></div>
                            <div class="tip-error tip-red-error" id="merUserPWDTwoERR2" style="display:none"></div>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>联系人：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="2-20字符，支持中文、英文"
                                   id="merLinkUser" name="merLinkUser" maxlength="20"
                                   onfocus="validateMerLinkUser(false);"/>

                            <div class="tip-error tip-red-error" id="merLinkUserERR"></div>
                        </td>
                        <th><span class="red">*</span>手机号码：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="输入的手机号可作为登录名"
                                   id="merLinkUserMobile" name="merLinkUserMobile" maxlength="11"
                                   onfocus="validateChildMerUserMobile(false);"/>

                            <div class="tip-error tip-red-error" id="merLinkUserMobileERR"></div>
                            <div class="tip-error tip-red-error" id="merLinkUserMobileERR2" style="display:none"></div>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>详细地址：</th>
                        <td colspan="3" class="select-wid03">
                            <select id="provinceAdd" name="provinceAdd">
                            </select>
                            <select id="cityAdd" name="cityAdd">
                            </select>
                            <input type="tel" class="com-input-txt w490" myPlaceholder="例如：XX市XX区XX街XX号XX室" id="merAdds"
                                   name="merAdds" maxlength="200" onfocus="validateMerAdds(false);"/>

                            <div class="tip-error tip-red-error" id="merAddsERR" style="margin-left:224px;"></div>
                        </td>
                    </tr>
                    <tr id="PrdRateTypeLine">
                        <th>业务类型:</th>
                        <td id="PrdRateType" colspan="5">
                        </td>
                    </tr>
                </table>

                <table class="base-table" width="100%" style="display:none;" id="yktTableLine">
                    <col width="120"/>
                    <tr id="yktLine">
                        <th>通卡公司：</th>
                        <td colspan="3"><input type="text" class="com-input-txt w714" placeholder="" disabled
                                               id="YKTCodeADD"/>
                            <a href="javascript:;" class="blue-link" id="xuanzhe">选择</a>

                            <div class="tit-pop tit-pop01" id="yktView" style="display: none;">
                                <table width="100%" border="0" class="base-table base-table01 tongka-list"
                                       id="yktViewCheckBox">
                                    <col width="25%"/>
                                    <col width="25%"/>
                                    <col width="25%"/>
                                    <col width="25%"/>
                                </table>
                                <div class="btn-box taCenter" style="margin: 85px 0px 0px 0px;">
                                    <a href="javascript:;" class="pop-borange" onclick="findRateType();">确认</a>
                                    <a href="javascript:;" class="pop-bgrange" onclick="closeRateType();">取消</a>
                                </div>
                            </div>
                            <div class="tit-pop tit-pop02" js="okbox" style="display: none;" id="rateTypeView">
                                <table id="yktRateTypeTable" width="100%" border="0" class="base-table">
                                    <colgroup>
                                        <col width="3%"/>
                                        <col width="8%"/>
                                        <col width="8%"/>
                                        <col width="8%"/>
                                        <col width="7%"/>
                                        <col width="2%"/>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" style="vertical-align: middle;" id="rateTitleCheckId"
                                                   onclick="toggle(this,'addCheckbox');"/></th>
                                        <th>通卡公司名称</th>
                                        <th>业务名称</th>
                                        <th>费率分类</th>
                                        <th>数值</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <div class="tip-error tip-red-error"></div>
                        </td>
                    </tr>
                </table>
                <div class="des-line"></div>
                <h4 class="tit-h4">补充信息</h4>
                <table class="base-table">
                    <col width="120"/>
                    <col width="330"/>
                    <col width="120"/>
                    <tr>
                        <th>经营范围：</th>
                        <td><select id="merBusinessScopeId" name="merBusinessScopeId">
                        </select>

                            <div class="tip-error tip-red-error"></div>
                        </td>
                        <th>电子邮箱：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="格式：yyyy@xxx.com" id="merEmail"
                                   name="merEmail" maxlength="60" onfocus="validateMerEmail(false);"/>

                            <div class="tip-error tip-red-error" id="emailERR"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>固定电话：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="如：021-62382888"
                                   id="merTelephone" name="merTelephone" maxlength="20"
                                   onfocus="validateMerTelephone(false);"/>

                            <div class="tip-error tip-red-error" id="merTelephoneERR"></div>
                        </td>
                        <th>传真：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="如：021-62382888" id="merFax"
                                   name="merFax" maxlength="20" onfocus="validateMerFax(false);"/>

                            <div class="tip-error tip-red-error" id="merFaxERR"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>证件类型：</th>
                        <td><select name="merUserIdentityType" id="merUserIdentityType">
                            <option value="" selected>－－ 请选择 －－</option>
                            <option value="0">身份证</option>
                            <option value="1">驾照</option>
                            <option value="2">护照</option>
                        </select>

                            <div class="tip-error tip-red-error"></div>
                        </td>
                        <th> 证件号码：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="请正确输入证件号码"
                                   id="merUserIdentityNumber" name="merUserIdentityNumber" maxlength="20"
                                   onfocus="checkIdentityNumber(false,'merUserIdentityType','merUserIdentityNumber','merUserIdentityNumberERR');"/>

                            <div class="tip-error tip-red-error" id="merUserIdentityNumberERR"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>邮编：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="如：210000" id="merZip"
                                   name="merZip" maxlength="6" onfocus="validateMerZip(false);"/>

                            <div class="tip-error tip-red-error" id="merZipERR"></div>
                        </td>
                        <th>开户银行：</th>
                        <td><select id="merBankName" name="merBankName"></select>

                            <div class="tip-error tip-red-error"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>开户行账号：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="请输入正确的开户银行账号"
                                   id="merBankAccount" name="merBankAccount" maxlength="19"
                                   onfocus="checkMerBankAccount(false,'merBankName','merBankAccount','merBankAccountERR');"/>

                            <div class="tip-error tip-red-error" id="merBankAccountERR"></div>
                        </td>
                        <th>开户名称：</th>
                        <td><input type="text" class="com-input-txt w260" myPlaceholder="请输入正确的开户名称"
                                   id="merBankUserName" name="merBankUserName" maxlength="50"
                                   onfocus="validateMerBankUserName(false);"/>

                            <div class="tip-error tip-red-error" id="merBankUserNameERR"></div>
                        </td>
                    </tr>

                    <tr>
                        <th>学历：</th>
                        <td>
                            <select id="education" name="education"></select>

                            <div class="tip-error" id="educationVal"></div>
                        </td>
                        <th>年收入(元)：</th>
                        <td>
                            <input type="text" id="income" style="text-align: right;" class="com-input-txt w260"
                                   myPlaceholder="数字，整数8位，两位小数 ， 单位：元" maxlength="60" onfocus="validateIncome(false);"/>

                            <div class="tip-error" id="incomeVal"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>出生年月：</th>
                        <td>
                            <input type="text" id="birthday" class="com-input-txt w260" myPlaceholder="请选择生日"
                                   readonly="readonly"
                                   onfocus="c.showMoreDay = false;c.timeOver=new Date();c.show(this,'');"
                                   maxlength="20"/>

                            <div class="tip-error" id="birthdayVal"></div>
                        </td>
                        <th>婚姻状况：</th>
                        <td>
                            <select id="isMarried" name="isMarried"></select>

                            <div class="tip-error" id="isMarriedVal"></div>
                        </td>
                    </tr>

                    <tr>
                        <th>备注：</th>
                        <td><textarea class="area-w268 remark" id="merUserRemark" onkeydown="checkMaxlength(this,200);"
                                      onpropertychange="onmyinput(this)" oninput="return onmyinput(this)"
                                      onPaste="return onmypaste(this);" onKeyPress="return onmykeypress(this);"
                                      maxlength="200"></textarea>

                            <div class="tip-error tip-red-error" id="merUserRemarkERR"></div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" class="a-center">
                            <div class="btn-box">
                                <input type="submit" value="保存" class="orange-btn-h32"
                                       onclick="return saveChildMerchant();">
                                <input type="reset" class="orange-btn-text32" value="取消"
                                       onclick="clearChileMerchantView('childMerchant');">
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
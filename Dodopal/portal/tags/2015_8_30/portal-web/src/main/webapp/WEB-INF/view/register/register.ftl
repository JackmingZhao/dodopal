<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<link rel="shortcut icon" type="image/x-icon" href="${base}/favicon.ico"  media="screen"/>
<link href="${base}/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/reg.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${base}/js/jquery-1.9.1.min.js'></script>
<script type="text/javascript" src="${base}/js/common/select.js" ></script>

<script type='text/javascript' src='${scriptUrl}/util/md5.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'></script>
<script type="text/javascript" src="${base}/js/common/area.js"></script>
<script type='text/javascript' src='${base}/js/common/placeholders.js'></script>
<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'></script>
<script type="text/javascript" src="../js/portalValidationHandler.js"></script>
<script type="text/javascript" src="../js/register/register.js"></script>
<script type="text/javascript">
	$(function(){
		$('#personalKaptcha').click(function() {//生成验证码  
	     	$('#personalKaptchaImg').attr('src', '${base}/captchImage/create' + '?kaptchaType=personRegister'  +'&t='+Date());
	     })   
	     
	     $('#merKaptcha').click(function() {//生成验证码  
	     	$('#merKaptchaImg').attr('src', '${base}/captchImage/create' + '?kaptchaType=merRegister'  +'&t='+Date());
	     }) 
	});
	$.base ='${base}';
</script>
<!--[if IE]>
<script type='text/javascript' src='${base}/js/json2.js'> </script>
<script type='text/javascript' src='${base}/js/common/ie_compatiable.js'> </script>
<![endif]-->
</head>
<body>
<div class="wd-top">
	<div class="container">
		<p class="wd-top-left" title="都都宝-24小时免费服务电话"><i class="phone-icon"></i>400-817-1000</p>
        <ul class="wd-top-right">
        	<li><span>|</span>&nbsp;<a>帮助中心</a></li>
        </ul>
    </div>
</div>
<div class="clear"></div>
<div class="wd-header">
	<div class="container">
    	<a class="dodopal-logo" href="${base}"></a>
        <div class="dodopal-title">
        	<p>注册账户</p>
        </div>       
    </div>
</div>
<div class="wd-main wd-main-min">
	<div class="container">
    	<div class="tab-div">
        	<div class="tabs">
            	<ul class="tab-nav-s-1">
                	<li id="tabs-up" class=" wd-icon tab-nav-action"><strong>个人用户</strong></li>
                    <li id="tabs-down" class=" wd-icon tab-nav"><strong>商户用户</strong></li>
                </ul>
            </div>
           <div class=" wd-icon tabs-body-bg"></div>
           <div class="tabs-body">    
           <form action="${base}/register/registerUser" method="post" id="userRegisterForm">       	
            <div id="flag-1" class="flag">
            	<p style=" width:386px; text-align:right;">已有都都宝账户，马上<a href="${base}" class="wd-blue">&nbsp;登录</a></p>
               <table class="tab-table">
              <tr>
                <th>手机号码：</th>
                <td><div><input id="merUserMobile" name="merUserMobile" type="text" class="wd-input" autocomplete="off" maxlength="11"/><input id="merUserMobile2" name="merUserMobile2" type="hidden"/><span class="tips" style="display:none;">输入的手机号可作为登录名</span><span class="tips  tip-error  tip-red-error" style="display:none;" id="merUserMobileValidation"></span></div></td></tr>
                <tr>
                <th>验证码：</th>
                <td height="125"><div id="merUserCaptchaDiv"><input  name="merUserCaptcha" id="merUserCaptcha" type="text" class="index-input index-input-width2" maxlength="4" style="width:127px;"/>
            	<span class="img-code" id="personalKaptcha"><img src="${base}/captchImage/create?kaptchaType=personRegister" title="看不清，换一张" style=" cursor:pointer;" alt="" id="personalKaptchaImg" /></span> <span class="tips tip-error tip-red-error" style="display:none;margin-left:5px;" id="merUserCaptchaValidation" >验证码不正确</span></div>
                <div style=" display:none;" id="merUserMobileCaptchaDiv"><input type="text"  class="index-input index-input-width2"  style="width:124px;" id="mobileCheckCode" name="mobileCheckCode"  maxlength="4"/>&nbsp;<input id="requestAuthCodeBtn" onclick="requestAuthCode();" name="button" type="button" class="wd-orageBtn2" value="获取验证码"  style=" cursor:pointer;vertical-align: middle;" /><i class="tips" id="merUserSerialNumber" style="display: none;"></i><span class="tips tip-error tip-red-error" id="requestAuthCodeBtnValidation" style="display:none;" ></span></div></td></tr>
                <tr>
                <th></th>
                <td><div><input id="btn-p" type="button" class="wd-btn wd-btn-1" value="下一步"  onclick="registerUserStep1();" /></div></td></tr>
                <tr>
                <th></th>
                <td><div>
                        <input type="checkbox" class="wd-checkbox" id="registerUserStep1Check" onclick="registerUserStep1CheckClick();" checked="true" />
            		<p id="wd-fwtk">我已阅读并接受<a  href="#" style="color: #37b4e9;">&nbsp;都都宝服务条款</a></p><span class="tips"  style="display:none;color: #ff0000;vertical-align:top;" id="registerUserStep1CheckValidation">请接受服务条款</span></div></td></tr>
            </table>
            </div>
            <div id="flag-2" class="flag" style="display:none">
            	<p style=" width:386px; text-align:right;">已有都都宝账户，马上<a href="${base}" class="wd-blue">&nbsp;登录</a></p>
               <table class="tab-table">
               	<tr>
                <th>用户名：</th>
                <td><div><input type="text" class="wd-input" id="merUserName" name="merUserName" maxlength="20"/><span  class="tips" style="display:none;" id="merUserNameTips">4-20位字符，支持字母、数字、“_”，首位为字母</span><span  class="tips tip-error tip-red-error" style="display:none;" id="merUserNameValidation"></span></div></td></tr>
                <tr>
                <th>密码：</th>
                <td ><div><input type="password"  class="wd-input" id="merUserPWD" name="merUserPWD"  maxlength="20"/><span class="tips" style="display:none;">6-20位字符，支持数字、字母及符号</span><span  class="tips tip-error tip-red-error" style="display:none;" id="merUserPWDValidation"></span></div></td></tr>
                <tr>
                <th>确认密码：</th>
                <td ><div><input type="password"  class="wd-input" id="merUserPWD2" name="merUserPWD2"  maxlength="20"/><span class="tips" style="display:none;">请再次输入密码</span><span class="tips tip-error tip-red-error" style="display:none;" id="merUserPWD2Validation"></span></div></td></tr>
                <tr>
                <th></th>
                <td><div><input type="button" class="wd-btn wd-btn-1" value="注&nbsp;册" id="personalRegisterBtn" onClick="registerUserStep2();"/></div></td></tr>
            </table>
            </div>
            </form>
        </div>
        	<div  class="tabs-body">
        		<form action="${base}/register/registerMerchant" method="post" id="merRegisterForm">
            	<div id="flag-3" class="flag" style="display:none">
            	<p style=" width:386px; text-align:right;">已有都都宝账户，马上<a href="${base}" class="wd-orage1">&nbsp;登录</a></p>
               <table class="tab-table">
               	<tr>
                <th>商户名称：</th>
                <td><div><input type="text" class="wd-input" id="merName" name="merName" maxlength="50" /><span class="tips" style="display:none;">支持中文、数字及字母</span><span class="tips tip-error tip-red-error" style="display:none;" id="merNameValidation"></span></div></td></tr>
                <tr>
                <th>联系人：</th>
                <td><div><input type="text" class="wd-input" id="merLinkUser" name="merLinkUser" maxlength="20"/><span  class="tips" style="display:none;">2-20字符，支持中文、英文</span><span class="tips tip-error tip-red-error" style="display:none;" id="merLinkUserValidation"></span></div></td></tr>
                <tr>
                <th>手机号码：</th>
                <td><div><input type="text" class="wd-input" id="merLinkUserMobile" name="merLinkUserMobile" maxlength="11"/><input type="hidden" id="merLinkUserMobile2" name="merLinkUserMobile2"/><span class="tips" style="display:none;">输入的手机号可作为登录名</span><span class="tips tip-error tip-red-error" style="display:none;" id="merLinkUserMobileValidation"></span></div></td></tr>
                <tr>
                <th>验证码：</th>
                <td >
                <div  id="merCaptchaDiv"> <input name="merCaptcha" id="merCaptcha" type="text" class="index-input index-input-width2" style="width:127px;" maxlength="4"/>
            	<span class="img-code" id="merKaptcha"><img src="${base}/captchImage/create?kaptchaType=merRegister" title="看不清，换一张" style="cursor:pointer;" alt="" id="merKaptchaImg"/></span><span  class="tips tip-error tip-red-error" style="display:none;" id="merKaptchaValidation"></span> </div>
                <div style=" display:none;" id="merMobileCaptchaDiv"><input type="text"  class="index-input index-input-width2" style="width:124px" id="merLinkUserMobileCheckCode" name="merLinkUserMobileCheckCode"  maxlength="4"/>&nbsp;<input type="button" class="wd-orageBtn3" value="获取验证码" style=" cursor:pointer;vertical-align: middle;" onclick="requestAuthCode2();" id="requestAuthCodeBtn2"/><i class="tips" id="merSerialNumber" style="display: none;"></i><span  class="tips tip-error tip-red-error" style="display:none;" id="merMobileCodeValidation"></span></div></td></tr>
                <tr>
                <th>省市：</th>
                <td  class="select-wid04">
                	<div>
                	<select name="province" id="province"> </select>
					<select name="city" id="city"> </select>
					<span class="tips" style="display:none;margin-left:5px;line-height: 2.3;color: #ff0000;" id="addressCityValidation"></span>
					
					</div>
                </td></tr>
                <tr>
                <th>详细地址：</th>
                <td><div><input type="text" class="wd-input" id="address" name="address" maxlength="200"/><span class="tips" style="display:none;" id="addressTips">例如：XX市XX区XX街XX号XX室</span><span class="tips tip-error tip-red-error" style="display:none;" id="addressValidation"></span></div></td></tr>
                <tr>
                <th></th>
                <td><div><input id="btn-b" type="button" class="wd-btn wd-btn-1" value="下一步"  onClick="registerMerStep1();"/></div></td></tr>
                <tr>
                <th></th>
                <td><div>
                        <input type="checkbox" class="wd-checkbox" id="merRegisterStep1Check" onclick="merRegisterStep1CheckClick();" checked="true"/>
            		<p id="wd-fwtk">我已阅读并接受<a  href="#" class="wd-orage1">都都宝服务条款</a></p><span class="tips" style="display:none;color: #ff0000;vertical-align:top;" id="merRegisterStep1CheckValidation">请接受服务条款</span></div></td></tr>
            </table>
            </div>
            <div id="flag-4" class="flag" style="display:none;">
            	<p style=" width:386px; text-align:right;">已有都都宝账户，马上<a href="${base}" class="wd-orage1">&nbsp;登录</a></p>
               <table class="tab-table">
               	<tr>
                <th>用户名：</th>
                <td><div><input type="text" class="wd-input" id="merchantUserName" name="merchantUserName" maxlength="20" /><span  class="tips" style="display:none;" id="merchantUserNameTips">4-20位字符，支持字母、数字、“_”，首位为字母</span><span  class="tips tip-error tip-red-error" style="display:none;" id="merchantUserNameValidation"></span></div></td></tr>
                <tr>
                <th>密码：</th>
                <td ><div><input type="password"  class="wd-input" id="merchantUserPWD" name="merchantUserPWD" maxlength="20"/><span  class="tips" style="display:none;">6-20位字符，支持数字、字母及符号</span><span  class="tips tip-error tip-red-error" style="display:none;" id="merchantUserPWDValidation"></span></div></td></tr>
                <tr>
                <th>确认密码：</th>
                <td ><div><input type="password"  class="wd-input" id="merchantUserPWD2" name="merchantUserPWD2"  maxlength="20"/><span  class="tips" style="display:none;">请再次输入密码</span><span  class="tips tip-error tip-red-error" style="display:none;" id="merchantUserPWD2Validation"></span></div></td></tr>
                <tr>
                <th></th>
                <td><div><input type="button" class="wd-btn wd-btn-1" value="注&nbsp;册" onClick="registerMerStep2();" id='registerMerStep2Btn'/></div></td></tr>
            </table>
            </div>
            </form>
        </div>
    </div>
</div>
<div class="clear"></div>
<div class="wd-footer">
	<div class="footer-p">
    	<p><a href="#">关于都都宝</a>&nbsp;|&nbsp;<a href="#">系统公告</a>&nbsp;|&nbsp;<a href="#">帮助中心</a>&nbsp;|&nbsp;官方微博 <a href="#">http://weibo.com/dodopal</a></p>
        <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
    </div>
</div>
</body>
</html>

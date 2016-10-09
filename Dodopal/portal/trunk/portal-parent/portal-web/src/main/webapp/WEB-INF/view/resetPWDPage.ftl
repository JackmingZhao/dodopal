<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/md5.js'> </script>
<!--[if IE]>
<script type='text/javascript' src='${base}/js/json2.js'> </script>
<script type='text/javascript' src='${base}/js/common/ie_compatiable.js'> </script>
<![endif]-->
<script src="${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js"></script>
<title>找回密码</title>
<link href="${styleUrl}/portal/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/r-base.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src='${scriptUrl}/util/ddpValidationBox.js'> </script>
<script src="${base}/js/merchant/merchantUser/resetPWD.js"></script>

<script type="text/javascript">
	$(function(){
		$('#refreshKaptcha').click(function() {//生成验证码  
	     	$('#kaptchaImage').attr('src', '${base}/captchImage/create' + '?kaptchaType=personResetPwd'  +'&t='+Date());
	     })   
	     
	     $('#yzm').on("keydown", function(event) {
			if (event.keyCode == 13) {
			//TODO
			checkCode();
			  }
		});  
		
	});
</script>
</head>

<body>
<div class="wd-top">
	<div class="container">
		<p class="wd-top-left" title="都都宝-24小时免费服务电话"><img class="phone-icon" src="${styleUrl}/portal/css/icons/phone-icon.png" />400-817-1000</p>
        <ul class="wd-top-right">
        	<li><span>|</span>&nbsp;<a>帮助中心</a></li>
        </ul>
    </div>
</div>
<div class="clear"></div>
<div class="wd-header">
	<div class="container">
    	<a class="dodopal-logo" href="${base}/"></a>
        <div class="dodopal-title">
        	<p>找回密码</p>
        </div>       
    </div>
</div>
<div class="clear"></div>
<div class="wd-main wd-main-min">
	<div class="container">
    	<div class="tab-contain">
        <p style="font-size:14px; margin-bottom:10px; width:824px;">您正在找回的都都宝账户是：<span id="merUserName"></span><span style="float:right;">已有账户，马上<a href="login" class="wd-orage1">&nbsp;登录</a></span></p>
           <div class="tabs-body tabs-body1">
	           <div  class="flag"  id="authCode">	
	               <table class="tab-table" style="margin-left:74px;">
	               	<tr>
	               	<th>账号：</th>
	               	    <td><div><input type="text" id="mobileCode"  onblur="checkMobile();"   class="wd-input wd-input-float" /></div> 
                <div class="tip-div"><span class="tips tip-error tip-red-error"  style="color:red" id="mobileMessage"></span></div></td></tr>
	                <th>验证码：</th>
	                <td height="125">
	                <div> 
	                <input name="yzm" id="yzm" type="text" onkeyup="checkCode(false);" maxlength="4" class="index-input index-input-width2 wd-input-float" />
	            		<span class="img-code" id="refreshKaptcha" >
	            		<img src="${base}/captchImage/create?kaptchaType=personResetPwd" title="看不清，换一张" style="cursor:pointer;" id="kaptchaImage"/></span> 
	            		<span id="yzmMessage" style="color:red" class="tips tip-error tip-red-error"></span></div>
	                </td>
	                </tr>
	                <tr>
	                 <th></th>
		                <td>
			                <div style="margin:10px 0px;">
			                	<input id="btn-p" type="button" class="wd-btn wd-btn-1" value="提&nbsp;交" onClick="checkCode(true);"/>
			                </div>
		                </td>
	                </tr>
	            	</table>    
	            </div>	
            <div id="sendAuthCode" class="flag" style="display:none">
               <table class="tab-table" style=" margin-left:74px;">
               	<tr>
               	<th>手机号码：</th>
               	<td>
                <div ><p style=" height:20px; line-height:20px;"><span id="userMobile"></span></p></div>
                </td></tr>
                <tr>
                <th>验证码：</th>
                <td>
                 <div class="text-code">
                 <input type="text"  id="authCodeInput" class="wd-input wd-input-float"  style="width:120px" maxlength='4'/>
	                    <input name="button" type="button" id="sendCode" class="wd-orage wd-input-float" value="获取验证码" onclick="sendAuthCode();"  style=" cursor:pointer;" />
                      </div>
                 <div class="tip-div" >
               	 <span id="authCodeMessage" class="tips tip-error tip-red-error"></span>
               	 <span id="authCodeErrorMessage" style="color:red" class="tips tip-error tip-red-error"></span>
               	 </div>
                </td>
                </tr>
                <tr><th></th><td><div style="margin:10px 0px;"><input id="btn-p" type="button" class="wd-btn wd-btn-1" value="提&nbsp;交" onClick="checkAuthCode();"/></div></td></tr>
            </table>            
            </div>
            <div id="newPWD" class="flag" style="display:none">
            <table class="tab-table" style="margin-left:74px;">
               	<tr> <th>新密码:</th>
               	<td>
               	<div>
               	<input type="password" id="pwd" class="wd-input wd-input-float" onblur="checkPwd(0);" maxlength="20"/>
               	</div>
               	<div class="tip-div">
               	<span id="pwdMassage" style="color:red;" class="tips tip-error tip-red-error"></span>
               	</div></td>
               	 </tr>
                <tr>
                <th>确认新密码:</th>
                <td height="125" >
                <div><input type="password" onblur="checkPwd(1);" class="wd-input wd-input-float"  id="pwd2" maxlength="20" />
                </div>
                
                <div class="tip-div">
                <span id="againMessage" style="color:red" class="tips tip-error tip-red-error"></span></div></td></tr>
                <tr><th></th><td><div style="margin:10px 0px;"><input id="btn-p" type="button" class="wd-btn wd-btn-1" value="确&nbsp;认" onClick="modifyPWD();"/></div></td></tr>
            </table>
            </div>
            	        
          	</div>
           
            
                        
      </div>
</div>
<div class="clear"></div>
<div class="wd-footer">
	<div class="container footer-p">
    <p><a href="http://www.dodopal.com/" target="_blank">&nbsp;|&nbsp;官方微博 <a href="http://weibo.com/dodopal" target="_blank">http://weibo.com/dodopal</a></p>
        <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
    </div>
</div>
</body>
</html>

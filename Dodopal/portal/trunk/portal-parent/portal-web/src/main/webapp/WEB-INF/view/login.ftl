<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<title>都都宝欢迎您_首页</title>
<link rel="shortcut icon" type="image/x-icon" href="${base}/favicon.ico"  media="screen"/>
<link href="${styleUrl}/portal/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/util/md5.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<script type='text/javascript' src='${base}/js/common/placeholders.js'></script>
<script type="text/javascript">
	$(function(){
		$('#refreshKaptcha').click(function() {//生成验证码  
	     	$('#kaptchaImage').attr('src', '${base}/captchImage/create' + '?kaptchaType=login'  +'&t='+Date());
	     })   
	     
	     $('#loginCaptcha').on("keydown", function(event) {
			if (event.keyCode == 13) {
		         checkLoginInfo();
			  }
		});  
		$('#pageGrp').hide();
		
	});
	
	function validateLoginInfo() {
		$('#sessionGrp').hide();
		var valid = false;
		if(isBlank($('#j_username').val())) {
			$('#errtipName').html('请输入用户名/手机号码');
			$('#errtipName').show();
			$('#pageGrp').show();
		} else if(isBlank($('#j_password').val())) {
			$('#errtipName').html('请输入密码');
			$('#errtipName').show();
			$('#pageGrp').show();
		}  else if(isBlank($('#loginCaptcha').val())) {		   
		    //==============测试完毕  修改回来========TODO=======
		    // valid = true;
			$('#errtipName').html('请输入验证码');
			$('#errtipName').show();
			$('#pageGrp').show();
		} else if($('#loginCaptcha').val().length != 4) {
		    //==============测试完毕  修改回来========TODO=======
		    // valid = true;
			$('#errtipName').html('验证码错误');
			$('#errtipName').show();
			$('#pageGrp').show();
		}  else {
			valid = true;
		}
		return valid;
	}
	
	function checkLoginInfo() {
		if(validateLoginInfo()) {
			var psssWd = $('#j_password').val();
			$('#j_password').val(md5(md5(psssWd)));
			$("#loginForm").submit();
		}
	}

	// 这里主要是当用户名存在时让用户名提示语消失
	function userNameOnfocus() {
		var userName = document.getElementById('j_username');
		userName.focus();
		setCaretAtEnd (userName);
	}

	// 将光标定位在文字后方
	function setCaretAtEnd (field) { 
		if (field.createTextRange) { 
			var r = field.createTextRange(); 
			r.moveStart('character', field.value.length); 
			r.collapse(); 
			r.select(); 
		}
	}
</script>

</head>
<body <#if SESSION_ERROR_LOGIN_MSG??>onload="userNameOnfocus()"</#if>>
<div class="wd-top">
	<div class="container">
		<p class="wd-top-left" title="都都宝-24小时免费服务电话"><img class="phone-icon" src="${styleUrl}/portal/css/icons/phone-icon.png"/>400-817-1000</p>
        <ul class="wd-top-right">
        </ul>
    </div>
</div>
<div class="clear"></div>
<div class="wd-header wd-header-index">
	<div class="container">
    	<a class="dodopal-logo" href="${base}/"></a>
        <div></div>
    </div>
</div>
<div class="clear"></div>
	<form action="${base}/app/j_spring_security_check" method="post" id="loginForm">
	
	<div class="wd-main">
	<div class="index-banner">
	<div class="banner-btn"><a class="corrent"></a><a></a><a></a><a></a><a style="margin-right:0px;"></a>
    </div>
	 <div class="banner"> <img  class="img" src="${styleUrl}/portal/css/images/img06.jpg"/> 
    <img  class="img" src="${styleUrl}/portal/css/images/img02.jpg" /> 
    <img  class="img" src="${styleUrl}/portal/css/images/img03.jpg" /> 
    <img  class="img" src="${styleUrl}/portal/css/images/img04.jpg" /> 
    <img  class="img" src="${styleUrl}/portal/css/images/img05.jpg" />
</div>
		<div class="container">
			<div class="index-login">
            	<h2 class="login-title">登录都都宝账户</h2>
				<div>
					<#if SESSION_ERROR_LOGIN_MSG??>
						<div class="error-div" id="sessionGrp">
						<div class="error-group" > <i class="errtip-icon"></i>
							<div class="account-errtip">${SESSION_ERROR_LOGIN_MSG}</div>
						</div>
						</div>
					</#if>
					<div class="error-div" id="pageGrp"  style="display:none;">
					<div class="error-group" ><i class="errtip-icon"></i>
						<div class="account-errtip" id="errtipName" style="display:none;">请输入用户名/手机号码</div>
					</div>
					</div>
					<table >
            <tr>
              <td><div class="index-input-div2">
                <label class="user-icon"></label>
                <input type="text" class="index-input index-input-width3" placeholder=" 用户名 / 手机号" autocomplete="off"   id="j_username" name="j_username" value="${SESSION_ERROR_LOGIN_NAME!}"/>
              </div></td>
            </tr>
            <tr>
              <td><div class="index-input-div2 ">
                <label class="pw-icon"></label>
                <input type="password" class="index-input index-input-width3" placeholder=" 密码" autocomplete="off" id="j_password" name="j_password" />
              </div></td>
            </tr>
            <tr>
              <td><div class="index-input-div2 ">
                <label class="yan-icon"></label>
                <input type="text" class="index-input index-input-width4" placeholder=" 验证码"  id="loginCaptcha" name="loginCaptcha"/>
                <span class="img-code"  id="refreshKaptcha"><img src="${base}/captchImage/create?kaptchaType=login" title="看不清，换一张" style="cursor:pointer;" id="kaptchaImage"/></span></div></td>
            </tr>
            <tr>
              <td height="50px"><input type="button" class="index-input-btn" value="登&nbsp;&nbsp;录" onclick="checkLoginInfo();"/></td>
            </tr>
            <tr>
              <td><div class="index-input-div1">
                <p class="wd-wjmm-a"><a href="${base}/toResetPWD">忘记密码？</a><a href="${base}/register/registerPage" style="margin-left:110px;">免费注册</a></p>
              </div></td>
            </tr>
          </table>
				</div>
                </div>
			</div> 
		</div>
	</form>

<div class="clear"></div>
<div class="wd-footer">
	<div class="container footer-p">
    	<p><a href="http://www.dodopal.com/" target="_blank">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="http://weibo.com/dodopal" target="_blank">http://weibo.com/dodopal</a></p>
        <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
    </div>
</div>
<script>
$(function(){
	var x=0;
	$(".banner-btn a").click(function(){
		x=$(".banner-btn a").index(this)
		$(".index-banner .img").hide()
		$(".index-banner .img").eq(x).fadeIn()
		$(".banner-btn a").removeClass("corrent")
		$(".banner-btn a").eq(x).addClass("corrent")
	})
	var timer = null;
	function startFade(){
		x++;
		if(x>4){ x=0;}
		$('.banner img').fadeOut();
		$('.banner img').eq(x).fadeIn();
	}
	function play(){
		timer = setInterval(function(){
		startFade();
		$(".banner-btn a").removeClass("corrent")
		$(".banner-btn a").eq(x).addClass("corrent")
		},5000);
	}
	play();
	function stopFade(){
		clearInterval(timer);
	}
	$('.banner').mouseover(function(){
		stopFade();
	})
	$('.banner').mouseout(function(){
		play();
	})
})
</script>
</body>
</html>

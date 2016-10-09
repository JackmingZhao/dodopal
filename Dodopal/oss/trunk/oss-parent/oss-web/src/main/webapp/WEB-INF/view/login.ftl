<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<script language="JavaScript"> 
		if (window != top)  {
			top.location.href = location.href; 
		}
	</script>
<title>OSS系统管理平台-登录</title>
<link rel="shortcut icon" type="image/x-icon" href="${base}/favicon.ico"  media="screen"/>
<link href="${base}/css/login.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='${scriptUrl}/common/jquery-easyui-1.3.1/jquery-1.8.0.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'> </script>
<script type='text/javascript' src='${scriptUrl}/util/md5.js'> </script>

<script type="text/javascript">
		function changeGif() {
		//生成验证码  
	     	$('#kaptchaImage').attr('src', '${base}/captchImage/create' + '?kaptchaType=login'  +'&t='+Date());
	     }
//回车提交
document.onkeydown=function(event){ 
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){ 
	checkLoginInfo();
	} 
} 
	function checkLoginInfo() {
		if(validateLoginInfo()) {
			var psssWd = $('#j_password').val();
			$('#j_password').val(md5(psssWd));
			$("#loginForm").submit();
		}
	}
	
	function validateLoginInfo() {
		var valid = false;
		$('#sessionGrp').hide();
		if(isBlank($('#j_username').val())) {
			$('#pageGrp').html('请输入用户名');
			$('#pageGrp').show();
		} else if(isBlank($('#j_password').val())) {
			$('#pageGrp').html('请输入密码');
			$('#pageGrp').show();
		} else if(isBlank($('#loginCaptcha').val())) {		   
			$('#pageGrp').html('请输入验证码');
			$('#pageGrp').show();
		} else {
			valid = true;
		}
		return valid;
	}
</script>

</head>

<body>
<div>
   <div class="login">
   	<div class="login-main">
    	<div class="main-l"></div>
    	<div class="main-m">
		<form class="login-form" name="loginForm" id="loginForm" method="post" action="${base}/app/j_spring_security_check">
			<div>
			<div style="margin:0px 0px 60% 35% ! important; position: absolute;">
				<p style="color:red;font-size:12px; text-align:center;" id="sessionGrp">${(SESSION_ERROR_LOGIN_MSG!"")}</p>
				<p style="color:red;font-size:12px; text-align:center;" id="pageGrp" style="display:none;"></p>
				</div>
				<br/>
				<ul>
				  <li>
						<label>用户名:</label>
						<span>
							<input name="j_username" id="j_username" type="text"/>
						</span>
				  </li>
				  <li>
						<label>密&nbsp;&nbsp;&nbsp;码:</label>
						<span class="password">
							<input type="password" name="j_password" id="j_password"/>
						</span>
				  </li>
					<p style="color:red;font-size:12px;"></p>
					<li>
					<label>验证码:</label>
					<span>
						<input type="text"  id="loginCaptcha" name="loginCaptcha"  style="width:40px;" maxlength='4'/>
					</span>
					<span class="img-code"  id="refreshKaptcha">
					<img src="${base}/captchImage/create?kaptchaType=login" title="看不清，换一张" style="cursor:pointer;" id="kaptchaImage" onclick='changeGif();'/>
					</span>
					</li>
				</ul>
			</div>
            <div class="login-btn">
			  <input type="button" name="button" id="button" class="loginbtn" value="登录" style=" background-color:#e47f12" onclick="checkLoginInfo();"/>
              <input type="reset" name="button" id="button" class="loginbtn" value="重置" style="background-color:#808080"/>
				
            </div>
          </form>
            </div>
            <div class="main-r"></div>
		</div>
	</div>
</div>
</body>
</html>

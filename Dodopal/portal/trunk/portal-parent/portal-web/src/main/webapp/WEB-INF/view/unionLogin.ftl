<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录中</title>

<script type='text/javascript' src='${scriptUrl}/common/jquery1.9.1/jquery-1.9.1.min.js'></script>
<script type='text/javascript' src='${scriptUrl}/util/util.js'></script>
<script type='text/javascript'>
	var intervalid; 
	$(function(){
		intervalid = setInterval("forwardPage()", 100); 
	});
	function forwardPage(){
		clearInterval(intervalid);
		$("#loginForm").submit();
	}
</script>
</head>

<body>

<!--注意：j_username要与 PortalConstants.REG_J_USERNAME对应-->
<form action="${base}/app/j_spring_security_check" method="post" id="loginForm">
	<input type="hidden" id="j_username" name="j_username" value="${regUserName?if_exists}" />
	<input type="hidden" id="j_password" name="j_password" value="${regUserPWD?if_exists}" />
</form>

</body>
</html>

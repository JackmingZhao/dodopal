<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="expires" content="-1">   
	<link href="${styleUrl}/portal/css/base.css" rel="stylesheet" type="text/css" />
	<link href="${styleUrl}/portal/css/index.css" rel="stylesheet" type="text/css" />
	<#include "../include.ftl"/>
	<script type="text/javascript" src="${base}/js/thirdlyView/thirdly.js"></script>
<title>外接商户内嵌测试充值</title>

</head>
<body>
<div class="con-main" id="viewOne">
<div class="seach-top-box">
		<div class="seach-top-inner">
			<form action="/" id="childMerchantFrom">
				<ul class="clearfix">
					<li>
						<label><span>外接商户号：</span>
							<input type="text" class="com-input-txt" width="250"  id="merCodeQuery" name="merCodeQuery"></input>
						</label>
					</li>
					<li class="btn-list">
						<input type="button" value="确认" class="orange-btn-h26" onclick="merCodeCheck()"></input>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
<div class="con-main"  id="viewTwo" style="display:none;">
  <div class="application-center clearfix">
	  <div class="app-left">
		  <ul class="clearfix">
			  <li class="snavi01"><a href="#" onclick="thirdlyMgmt()"><span><i></i>一卡通充值</span></a></li>
			  <li class="snavi03"><a href="#"><span><i></i>手机话费</span></a></li>
			  <li class="snavi04"><a href="#"><span><i></i>水费缴纳</span></a></li>
			  <li class="snavi05"><a href="#"><span><i></i>电费缴纳</span></a></li>
			  <li class="snavi06"><a href="#"><span><i></i>天然气缴纳</span></a></li>
			  <li class="snavi07"><a href="#"><span><i></i>有线电视费</span></a></li>
		  </ul>
	  </div>
	  <div class="app-right">
		  <iframe id="waijie" src="" height="580" width="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	  </div>
	</div>
</div>
</body>
</html>
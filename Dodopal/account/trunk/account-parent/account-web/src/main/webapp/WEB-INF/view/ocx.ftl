<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "include.ftl"/>
<script type='text/javascript' src='http://localhost:8080/webstatic/js/common/jquery-easyui-1.3.1/jquery-1.8.0.min.js'></script>	
<script type="text/javascript">

$().ready( function() {
var oxcStr = '<OBJECT ID="OCXFAPAY"  name="OCXFAPAY" CLASSID="CLSID:${(CLSID)!}" ';
		oxcStr+=' HEIGHT=0 WIDTH=0 codebase="${ocxUrl}/${(ykt)!}.CAB#version=${(ver)!}}" viewastext>';
		oxcStr+='<SPAN STYLE="color:red">控件加载失败! -- 请检查浏览器的安全级别设置.</SPAN></OBJECT>';
		alert(oxcStr);
		$("#OCXBODY").append(oxcStr);
});

</script>
	
</head>
<title>Test</title>
</head>
<body id="OCXBODY">
   通卡code====${(ykt)!}=======版本号====${(ver)!}=========CLSID====${(CLSID)!}
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge" />
	<#include "include.ftl"/> <script type="text/javascript"
		src="js/testLog.js"></script>
</head>
<title>TestLog</title>
</head>
<body>
	<th>报文:</th>
	<td><textarea name="josnText" id="josnText" rows="14" cols="120">{"befbal":"29600","cardtype":"1","chargetype":"0","citycode":"1791","mercode":"059001000000523","merusertype":"1","messagetype":"2011","posid":"409020044973","postype":"4","productcode":"1000015","source":"0","specdata":{"ats":"107880A0022090007498BA39835AF317","file05":"869833000001FF153300000104A1CBBA0001201411030000000000030000","file15":"869833000001FFFF010033003300000104A1CBBA2014110420341104000100000000","rand":"3642088C","uid":"835AF317"},"sysdatetime":"20150909105111","tradecard":"3300000177712314","tradestartflag":"1","txntype":"1","usercode":"17323100000000609","userid":"10000000000000000600","ver":"1","yktcode":"330000"}</textarea></td>
	<br />
	<button type="button" id="checkCard">验卡查询</button>
	<button type="button" id="createCard">订单创建</button>
	<button type="button" id="getLoadOrderFun">充值申请</button>
	<button type="button" id="upload">结果上传</button>
	<button type="button" id="checkCardCosum">验卡查询(消费)</button>
	<button type="button" id="getLoadOrderCosum">消费申请(消费)</button>
</body>
</html>
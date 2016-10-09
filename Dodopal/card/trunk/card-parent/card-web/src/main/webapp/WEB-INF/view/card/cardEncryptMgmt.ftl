<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../include.ftl"/>
	<script type="text/javascript" src="../js/card/cardEncrypt.js"></script>
	
</head>
<title>加密解密</title>
</head>
<body >
	<table>
		<tr>
			<th>加密前字符串：</th>
			<td><input name="encryptName" id="encryptName" type="text" /></td>
			<th>加密后字符串:</th>
			<td><textarea rows="3" cols="20" id="decryptText" name="decryptText">
				</textarea></td>
			<th>解密后字符串:</th>
			<td><input name="decryptName" id="decryptName" type="text" ></input></td>
		</tr>
	</table>
	<input type="button" onclick="encryptMode()" value="加密"></input>
	<input type="button" onclick="decryptMode()" value="解密"></input>
	</div>
</body>
</html>
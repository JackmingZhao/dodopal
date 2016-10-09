<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "include.ftl"/>
		<script type="text/javascript" src="js/test.js"></script>
	
</head>
<title>OSS</title>
</head>
<body >
    <div>
	<table>
		<tr>
			<th>名称：</th>
			<td><input name="name" id="name" type="text" /></td>
			<th>描述:</th>
			<td><input name="description" id="description" type="text"/></td>
		</tr>
		<tr>
			<th>项目名:</th>
			<td><input name="appName" id="appName" ></input></td>
			<th>ID:</th>
			<td><input name="testId" id="testId" type="text"/></td>
		</tr>
	</table>
	
	<button type="button" id="add">新增</button>
	<button type="button" id="delete">删除</button>
	<button type="button" id="update" >修改</button>
	<button type="button" id="find" >查询</button>
	<button type="button" id="reset" >重置</button>
	<hr/>
	
	<table border="1"  id="displayTbl">
		<tr>
			<th>ID</th>
			<th>名称</th>
			<th>描述</th>
			<th>项目名</th>
		</tr>
	</table>
	</div>
</body>
</html>
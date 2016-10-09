<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
		<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="js/merchant.js"></script>
		
</head>
<title>Test</title>
</head>
<body >
    <div>
	<table>
		<tr>
			<th>迁移城市：</th>
			<td><select name="citycode" id="citycode">
								<option value='1532'  selected = "selected">青岛市</option>
								<option value='1592'>厦门市</option>
								<option value='1755'>深圳市</option>
								<option value='1123'>重庆市</option>
							</select></td>
		</tr>
	</table>	
    <hr/>
	<table>
	<tr>
			<td><button type="button" id="merchant">商户信息</button></td>
			<td><button type="button" id="group">集团信息</button></td>
			<td><button type="button" id="groupproxy">集团下网点</button></td>
			<td><button type="button" id="otherproxy">其他网点</button></td>
	</tr>
	<tr>
			<th>个人信息循环几页：</th>
			<td><input name="pageSize" id="totalPages" type="text" /></td>
			<td><button type="button" id="gr">个人信息</button></td>
	</tr>
	</table>
	<hr/>
	<table>
		<tr>
			<th>日志批次号：</th>
			<td><input name="batchId" id="batchId" type="text" /></td>
			<td><button type="button" id="qupici">查询</button></td>
		</tr>
	</table>
		<hr/>
	<table border="1"  id="displayTbl">
		<tr>
		<th>序号</th>
		<th>迁移批次</th>
		<th>老系统商户id或用户id</th>
		<th>老系统商户类型</th>
		<th>新平台商户号或用户号</th>
		<th>新平台商户类型</th>
		<th>迁移状态</th>
		</tr>
	</table>
	</div>
</body>
</html>
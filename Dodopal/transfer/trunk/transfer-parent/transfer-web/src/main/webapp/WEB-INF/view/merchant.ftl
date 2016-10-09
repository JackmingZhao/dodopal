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
    <button type="button" id="lsmancht">（乔）连锁商户</button>
	
	<button type="button" id="groupInfo">（陶）集团信息</button>
	<button type="button" id="lszyAndJm">（熊）普通直营和加盟网点</button>
	<button type="button" id="ddpzy">（马）嘟嘟宝自由网点</button>
	<hr/>
	<table>
		<tr>
			<th>个人信息循环几页：</th>
			<td><input name="pageSize" id="totalPages" type="text" /></td>
			<td><button type="button" id="gr">（还）个人信息</button></td>
		</tr>
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
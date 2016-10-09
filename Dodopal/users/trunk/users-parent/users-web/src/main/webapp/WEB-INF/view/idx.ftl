<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<script type="text/javascript" src="js/jq.js"></script>
	<script type="text/javascript" src="js/idx.js"></script>
</head>
<title>Demo</title>


</head>
<body>
<div>
	<table>
		<tr>
			<th>部门名称：</th>
			<td><input name="name" id="name" type="text" /></td>
			<th>部门负责人:</th>
			<td><input name="charger" id="charger" type="text"/></td>
		</tr>
		<tr>
			<th>部门电话:</th>
			<td><input name="phone" id="phone" ></input></td>
			<th>部门传真:</th>
			<td><input name="fax" id="fax"></input></td>
		</tr>
		<tr>
			<th>部门地址:</th>
			<td><input name="address" id="address" type="text" ></input></td>
			<th>部门ID(测试):</th>
			<td><input name="departmentId" id="departmentId" type="text" ></input></td>
		</tr>
		<tr>
			<th>备注:</th>
			<td colspan="3"><textarea rows="3" name="comments" id="comments" style="width: 470px;"></textarea></td>
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
			<th>部门ID</th>
			<th>部门名称</th>
			<th>部门负责人</th>
			<th>部门电话</th>
			<th>部门传真</th>
			<th>部门地址</th>
			<th>备注</th>
		</tr>
	</table>
	</div>
</body>
</html>
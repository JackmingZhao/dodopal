<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "../include.ftl"/>
	<script type="text/javascript" src="../js/loadOrder/loadOrder.js"></script>
	 <style type="text/css">
        .base-table {
            width: 100%;
            line-height: 32px;
        }

        .base-table th {
            font-size: 14px;
            color: #444444;
            text-align: right;
            padding: 0 5px 10px;
            font-weight: normal;
            vertical-align: top;
        }

        .base-table th .red {
            color: #ff2626;
            vertical-align: middle;
            margin-right: 2px;
        }

        .base-table td {
            padding: 0 5px;
            color: #808080;
            font-size: 14px;
            vertical-align: top;
        }

        .base-table td input,
        .base-table td textarea {
            font-size: 12px;
        }

        .base-table td textarea {
            border: 1px solid #ccc;
            padding: 5px;
        }

        .base-table01 th,
        .base-table01 td {
            font-size: 14px;
            padding: 0px 5px;
            height: auto
        }

        .tit-pop {
            border: 1px solid #37b4e9;
            width: 420px;
            display: none;
            background-color: #f6f6f6;
            position: absolute;
            margin: 10px 0;
            padding: 0 20px;
            left: -280px;
            top: 20px;
            z-index: 9;
        }

        .tit-pop01 {
            padding: 0px 20px 10px;
            width: 460px;
        }

        .tit-pop table {
            width: 100%;
            border-collapse: collapse;
        }

        .tit-pop table th {
            border-bottom: 1px dashed #bdbebf;
            text-align: center;
            font-size: 14px;
            color: #808080;
            padding: 8px 5px;
        }

        .tit-pop table td {
            text-align: center;
            color: #444444;
            font-size: 12px;
            padding: 8px 5px;
            vertical-align: middle;
        }
    </style>
</head>
<title>圈存订单</title>
</head>
<body >
<h3>6.2	根据卡号获取可用于一卡通充值的圈存订单</h3>
<table>
		<tr>
			<th>一卡通面号:</th>
			<td><input name="cardNum" id="cardNum" type="text" />
			</td>
		</tr>
	</table>
	<input type="button" onclick="findAvailableLoadOrdersByCardNum()" value="查询"></input>
 <div class="tit-pop" style="display: block; position: static; width: 640px; height: 100%;" id="yktDIVView">
        <table id="findAvailableOrderTbl" width="100%"  border="0" class="">
           <colgroup>
			<col width="21%" />
			<col width="11%" />
			<col width="11%" />
			<col width="11%" />
			</colgroup>
			<thead>
			<tr>
				<th class="a-left">圈存订单编号</th>
				<th class="a-left">外部订单号</th>
				<th class="a-left">卡号</th>
				<th class="a-left">商户号</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
        </table>
    </div>
</div>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>应用中心</title>
<#include "../../include.ftl"/>
<script src="${base}/js/applicationCenter/loadOrder/loadOrderList.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${base}/js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
</script>
</head>
<body>
<div class="wd-header">
	<div class="w1030 clearfix"> <a class="dodopal-logo" href="#"></a>
		<div class="dodopal-title">
			<p>圈存订单</p>
		</div>
	</div>
</div>
<div class="con-main">
<div class="application-center clearfix" id="loadOrderMain">
	  <div class="app-right">
		  <div class="app-pay-way quncun-tit">
			  <a href="${base}/loadOrder/toLoadOrder" class=''>圈存订单</a><a href="${base}/loadOrder/loadOrderList" class="blue current" style="padding-left:20px;">订单查询</a>
		  </div>
	   	  <div class="app-right com-bor-box" style="padding:0;width:1028px;margin-top:-1px;">
		  <div class="seach-top-inner seach-top-inners seach-top-inner-bg">
			  <form id="queryLoadOrderForm" action="excelLoadOrder" method="post">
				  <ul class="clearfix">
					  <li class="w310"><span class="w100">订单创建时间：</span>
						  <input name="startDate" readonly="readonly" type="text" class="com-input-txt w74"  placeholder="起始时间" id="startDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
						  -
						  <input name="endDate" readonly="readonly" type="text" class="com-input-txt w74"  placeholder="结束时间" id="endDate" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					  </li>
					  <li>
						  <label><span class="w100">订单编号：</span>
							  <input name="loadOrderNum" id="loadOrderNum" type="text" class="com-input-txt w88" />
						  </label>
					  </li>
					  <li>
						  <label><span class="w100">卡号：</span>
							  <input name="cardNo" id = "cardNo" type="text" class="com-input-txt w88" />
						  </label>
					  </li>
					  <li style="padding-left:40px;">
					  	  <input type="button" value="查询" class="orange-btn-h26" onclick="findLoadOrderByPage();"/>
						  <input type="reset" value="清空" class="orange-btn-text26" onclick="clearQueryForm('queryLoadOrderForm');"/>
					  </li>
				  </ul>
				  <ul class="clearfix" style="padding-top:14px;">
					  <li class="w310 yuan"><span class="w100">圈存金额范围：</span>
						  <input type="text" class="com-input-txt w74"  id="txnAmtStart" name="txnAmtStart"/>
							-
						  <input type="text" class="com-input-txt w74"  id="txnAmtEnd" name="txnAmtEnd"/>
                          元
					  </li>
					   <li><span class="w100">城市：</span>
						  <select name="cityCode" id="cityCode">
							  <option selected="selected" value="">--请选择--</option>
						  </select>
					  </li>
					  <li>
                      	<ul class="clearfix">
					 	  <li><span class="w100">订单状态：</span>
						  	<select name="loadOrderState" id="loadOrderState">
                                <option selected="selected" value="">--请选择--</option>
							</select>
					  </li>
				  </ul>
                      </li>
				  </ul>
			  </form>
		  </div>
		  <div class="com-bor-box02">
			  <ul class="navi-ul01 clearfix">
				  <li class="fr">金额单位(元)</li>
			  </ul>
		  </div>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="com-table01" id="loadOrderTbl">
			  <col width="2%" />
			  <col width="17%" />
			  <col width="9%" />
			  <col width="9%" />
			  <col width="9%" />
			  <col width="15%" />
			  <col width="9%" />
			  <col width="13%" />
			  <col width="4%" />
			  <thead>
			  <tr>
				  <th>&nbsp;</th>
				  <th>订单编号</th>
				  <th>城市</th>
				  <th>应收金额</th>
				  <th>实收金额</th>
				  <th>卡号</th>
				  <th>订单状态</th>
				  <th>订单创建时间</th>
				  <th>&nbsp;</th>
			  </tr>
			  </thead>
			  <tbody>
			</tbody>
		  </table>
		  <div class="null-box"></div>
		  <p class="page-navi"><span class="fl">订单下载：<a  href="#"  onclick="excelLoadOrder()" >Excel格式</a></span>
	  </div>
	</div>
	</div>
	</div>
<#include "../../footLog.ftl"/>
</body>
</html>

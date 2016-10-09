<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>一卡通充值</title>
<script src="${base}/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${base}/js/cardRecharge/cardRechargeWithUser.js" type="text/javascript"></script>
<script src="${base}/js/layer/layer.js" type="text/javascript"></script>
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/r-base.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/product/css/base.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
	<div class="application-center clearfix">
		<div class="app-right">
<div class="app-pay-way tit-h3">
	<a href="javascript:;" class="blue">一卡通充值</a><a href="/product-web/productOrder/productOrderMgr">订单查询</a>
</div>			<dl class="app-dl clearfix com-bor-box com-bor-box01 app-dl-s pd2070">
				<dt>所在城市：</dt>
<dd class="clearfix">
	<span id="city">南昌</span> 	
				</dd>	
				<dt>卡号：</dt>
			  	<dd class="clearfix">
					<p id="cardNumWarn"><i></i><span id="cardNumMessage"></span></p>
					<p><i></i><span id="cardNum"></span></p>
				</dd>
			  	<dt>卡余额：</dt>
			  	<dd>
			  	<p id="cardMoneyP"><i></i><span id="cardMoney"></span><span>元</span></p>
			  	</dd>
			  	<dt>充值金额：</dt>
				<dd class="recharge-amount" id="proPrice">
					<a  href="javascript:;" money="0.01" procode="1000015" proname="南昌1元">0.01<s></s></a><a href="javascript:;" money="0.02" procode="1000067" proname="南昌0元">0.02<s></s></a><a href="javascript:;" money="0.03" procode="1000069" proname="南昌0元">0.03<s></s></a><a href="javascript:;" money="0.09" procode="1000071" proname="南昌0元">0.09<s></s></a><a href="javascript:;" money="0.4" procode="1000073" proname="南昌0元">0.4<s></s></a><a href="javascript:;" money="1" procode="1000061" proname="南昌1元">1<s></s></a><a href="javascript:;" money="30" procode="1000031" proname="南昌30元">30<s></s></a><a href="javascript:;" money="50" procode="1000001" proname="南昌50元">50<s></s></a><a href="javascript:;" money="100" procode="1000003" proname="南昌100元">100<s></s></a><a href="javascript:;" money="200" procode="1000005" proname="南昌200元">200<s></s></a><a href="javascript:;" money="300" procode="1000007" proname="南昌300元">300<s></s></a><a href="javascript:;" money="400" procode="1000009" proname="南昌400元">400<s></s></a><a href="javascript:;" money="500" procode="1000011" proname="南昌500元">500<s></s></a>
					<a  href="javascript:;" money="0.01" procode="11101" proname="测试数据">错误数据<s></s></a>
				</dd>
				<!--
				
				-->
				<dt>商户号：</dt>
				<dd class="recharge-amount" ><input type="text" title='当前属性没用到' disabled id="mercode" name="mercode"/></dd>
				
				<dt>输入产品编码：</dt>
				<dd class="recharge-amount" ><input type="text" id="procode" name="procode"/></dd>
				<dt>都都宝订单号：</dt>
				<dd class="recharge-amount" ><input type="text" id="ddpOrder" name="ddpOrder"/></dd>
				<dt>外部订单号：</dt>
				<dd class="recharge-amount" ><input type="text" id="busOrder" name="busOrder"/></dd>
				<dt>操作时间：</dt>
				<dd class="recharge-amount" ><input type="text" id="optTime" name="optTime"/>日期格式例如:yyyyMMddHHmmss</dd>
				  <dt></dt>
			  <dd class="btn-recharge">
			  <a href="javascript:getCardInfo()"  class="orange-btn-h32" js="qurren">查看卡信息</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <a href="javascript:void(0)" style="background-color:#666666;" id="toCreateOrder" class="orange-btn-h32" js="qurren">验卡生单</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <a href="javascript:void(0)" style="background-color:#666666;" id="toRechargeCall" class="orange-btn-h32" js="qurren">立即充值</a>
			  </dd>
		  </dl>
		  <dl class="app-dl02">
			  <dt>充值提示：</dt>
				<dd>1.在整个充值过程中，请不要移动卡片；<br>
					2.在显示充值结果前，请不要关闭、刷新或后退浏览器窗口；<br>
					3.对于未提示充值成功的交易，请再次查询余额，通过对比卡内金额是否增加判断最终结果。</dd>
		  </dl>
		</div>
	</div>
	<div id='div1'>
		<p style="color:red;font-size:16px;">调用dll 返回消息显示</p>
		<div id='div11'></div>
		<div id='div111'></div>
	</div>
	<div id='div2'>
		<div id='div22'>
		</div>
		<div id='div222'>
		</div>
	</div>
	<div id='div3'>
		<div id='div33'>
		</div>
		<div id='div333'>
		</div>
	</div>
</div>
</body>
</html>

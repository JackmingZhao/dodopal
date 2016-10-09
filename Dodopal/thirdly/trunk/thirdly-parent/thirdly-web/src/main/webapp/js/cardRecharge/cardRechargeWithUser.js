//生成的订单编号
//var prdordernum ="";
var needPayMoney = '';
var productcode = '';
$.ocxUrl = 'http://192.168.1.250:8080/cscab';

$(function() {
/*	var oxcStr = '<OBJECT ID="OCXFAPAY"  name="OCXFAPAY" CLASSID="CLSID:EF5AC98D-3DA1-4F28-AFFE-285204B9534E" ';
	oxcStr += ' HEIGHT=0 WIDTH=0  viewastext>';
	oxcStr += '<SPAN STYLE="color:red">控件加载失败! -- 请检查浏览器的安全级别设置.</SPAN></OBJECT>';
	
	$("body").prepend(oxcStr);*/
	
	getOtherOCX("EF5AC98D-3DA1-4F28-AFFE-285204B9534E","1,0,1,36","330000E","打印机控件加载失败! -- 请检查浏览器的安全级别设置.","OCXFAPAY","OCXFAPAY");
	
	$('.recharge-amount a').click(function() {
		needPayMoney = $(this).attr("money");
		productcode = $(this).attr("proCode");
		$(this).addClass('a-click').siblings("a").removeClass("a-click");
	});
//	needPayMoney = $('.recharge-amount a').eq(0).attr("money");
//	productcode = $('.recharge-amount a').eq(0).attr("proCode");
})


function getOtherOCX(clsId,versionId,yktCode,msg,ocxId,ocxName){
	if(""==clsId||""==versionId||""==yktCode||null==clsId||null==versionId){
		return;
	}
	var defaultMsg = "控件加载失败! -- 请检查浏览器的安全级别设置.";
	if(msg!=undefined){
		defaultMsg = msg;
	}
	var oxcStr = '<OBJECT ID="'+ocxId+'"  name="'+ocxName+'" CLASSID="CLSID:'+clsId+'" ';
	oxcStr+=' HEIGHT=0 WIDTH=0 codebase="'+$.ocxUrl+'/'+yktCode+'.CAB#version='+versionId+'" viewastext>';
	oxcStr+='<SPAN STYLE="color:red">'+defaultMsg+'</SPAN></OBJECT>';
	$("body").prepend(oxcStr);
}


/*
 * 获取卡信息
 */
function getCardInfo() {
	var mercode = $("#mercode").val();
	var optTime = $("#optTime").val();
	var createTime = formatDate(new Date(), 'yyyyMMddHHmmss')
	if(optTime != ''){
		createTime = optTime;
	}
	var map = {
		"mercode":mercode,
		"operatorID" : 'liu00000001',// 操作员id(测试阶段暂时为空)
		"trandate" : createTime,// 操作时间
		"sign" : ''// 签名值(测试阶段暂时为空)
	};
	var json = JSON.stringify(map);
	$("#div11").html('获取卡信息输入参数: <font color="Black">'+json+"</font><hr/><br/>");
	// cardInfoBack为回调函数
	OCXFAPAY.Dodopal_InqueryCardAsync(json, cardInfoBack);
}
/*
 * 将返回的卡信息显示出来
 */
function cardInfoBack(rebackParm) {
	var ocxBean = eval("(" + rebackParm + ")");
	$("#div111").html('<font color="Blue">获取卡返回信息:&nbsp;&nbsp;</font><font color="Black">'+rebackParm+'</font><br/><hr/><br/>');
	if(parseInt(ocxBean.code)==0){
		layer.alert("状态:   "+ocxBean.code+",返回消息:"+ocxBean.message);
		// 卡号
		var cardNum = ocxBean.tradecard;
		$("#cardNum").html(cardNum);
		// 余额
		var cardMoney = ocxBean.befbal;
		$("#cardMoney").html(parseFloat(cardMoney) / 100);
		$("#toCreateOrder").click(createOrder);
		$("#toCreateOrder").css("background-color", "rgb(228, 127, 17)");
	}else{
		layer.alert("获取卡信息失败,状态:   "+ocxBean.code+",返回消息:"+ocxBean.message);
	}
	
}

/**
 * 验卡生单
 */
function createOrder() {
	var procode = $("#procode").val();
	if (productcode == '' && procode == '') {
		layer.alert("选择充值金额或者手动输入产品号");
		return;
	}else if(needPayMoney != '' && procode != ''){
		layer.alert("选择充值金额就不能手动输入产品号,两者只能用其一");
		return;
	}
	if(productcode == ''){
		productcode = procode;
	}
	var optTime = $("#optTime").val();
	var createTime = formatDate(new Date(), 'yyyyMMddHHmmss')
	if(optTime != ''){
		createTime = optTime;
	}
	
	var map = {
		"productcode" : productcode,// 产品编号
		"operatorID" : 'liu00000001',// 操作员id
		"trandate" : createTime,// 交易时间
		"sign" : ''// 签名值
	}
	var json = JSON.stringify(map);
	$("#div22").html(' 验卡生单输入参数: <font color="Black">'+json+"</font><hr/><br/>");
	try {
		OCXFAPAY.Dodopal_CreateRechargeOrderAsync(json, orderCallBack);
	} catch (e) {
		return;
	}
}

/**
 * 验卡生单接口回调执行充值
 */
function orderCallBack(outparm) {
	// 转换为对象获取创建订单结果code
	var bean = eval("(" + outparm + ")");
	$("#div222").html('<font color="Blue">验卡生单返回信息:&nbsp;&nbsp;</font><font color="Black">'+outparm+"</font><br/><hr/><br/>");
	// 创建订单返回码code
	if (parseInt(bean.code) == 0) {
		layer.alert("验卡生单返回状态:   "+bean.code+",返回消息:"+bean.message);
		
		//prdordernum = bean.prdordernum;
		
		// 商户平台业务处理：扣款等业务操作
		$("#toRechargeCall").click(toRechargeCall);
		$("#toRechargeCall").css("background-color", "rgb(228, 127, 17)");
		
		
		$("#ddpOrder").val(bean.prdordernum);
		
		var merordernum = "E" + parseInt(Math.random() * 1000000000);
		$("#busOrder").val(merordernum);
		
	} else {
		// 根据ocx函数返回错误码对应信息提示
		// 商户平台业务逻辑处理结束。
		layer.alert("订单生成失败,不能充值;返回状态:   "+bean.code+",返回消息:"+bean.message);
		return;
	}
}
/**
 * 执行充值 merordernum:外接商户生成的订单号 prdordernum:ddp生成的产品订单号
 */
function toRechargeCall() {
	$("#toRechargeCall").unbind('click');
	$("#toRechargeCall").css("background-color", "#666666");
	//var merordernum = "E" + parseInt(Math.random() * 1000000000);
	var ddpOrder = $("#ddpOrder").val();
	
	//if(ddpOrder != ''){
	//	prdordernum = ddpOrder;
	//}
	
	var busOrder = $("#busOrder").val();
	
	//if(busOrder != ''){
	//	merordernum = busOrder;
	//}
	
	var optTime = $("#optTime").val();
	var createTime = formatDate(new Date(), 'yyyyMMddHHmmss')
	if(optTime != ''){
		createTime = optTime;
	}
	var tempBean = {
		"merordercode" : busOrder,// 外接商户订单编号
		"prdordernum" : ddpOrder,// 都都宝平台订单号
//		"prdordernum" : prdordernum,// 都都宝平台订单号
		"trandate" : createTime,// 交易时间
		"sign" : ''// 签名值
	}
	// json
	var jsonStr = JSON.stringify(tempBean);
	$("#div33").html(' 执行充值输入参数: <font color="Black">'+jsonStr+"</font><hr/><br/>");
	try {
		// 调用ocx充值函数，传入rechargeCallBack回调函数
		OCXFAPAY.Dodopal_RechargeAsync(jsonStr, rechargeResultCallBack);
	} catch (e) {
		layer.alert("充值失败");
	}
}

/**
 * 充值结果回调
 */
function rechargeResultCallBack(creditJson) {
	var bean = eval("(" + creditJson + ")");
	$("#div333").html('<font color="Blue">充值结果返回信息:&nbsp;&nbsp;</font><font color="Black">'+creditJson+"</font><br/><hr/><br/>");
	// 拿到充值结果
	if (parseInt(bean.code) == 0) {
		layer.alert("充值返回状态:   "+bean.code+",返回消息:"+bean.message);
		// 充值成功
		var cardMoney = bean.befbal;
		// 修改显示的金额
		$("#cardMoney").html(parseFloat(cardMoney) / 100);
		prdordernum='';
	} else {
		// 充值失败
		layer.alert("充值失败返回状态:   "+bean.code+",返回消息:"+bean.message);
	}
}

/**
 * 时间对象的格式化;
 */
function formatDate(date, format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : date.getMonth() + 1, // month
		"d+" : date.getDate(), // day
		"H+" : date.getHours(), // hour
		"m+" : date.getMinutes(), // minute
		"s+" : date.getSeconds(), // second
		"q+" : Math.floor((date.getMonth() + 3) / 3), // quarter
		"S" : date.getMilliseconds()
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (date.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
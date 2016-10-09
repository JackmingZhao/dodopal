var cityCode="";
$(function(){
	ddpAjaxCall({
		url : $.base+"/external/getCardRechargeInfo",
		data : cityCode,
		successFn : getCardRechargeInfo
	});
	 var load_time = null;  
     var down_time = null;  
        
     flag = false;  
      //打开页面60秒不操作就跳转  
     load_time = setTimeout(function(){  
    	 if(''!=queryCardBean&&(queryCardBean.code) != 0){
    		 //验卡错误码
    		 callExternalWithCard(queryCardBean.code,'');
    	 }else{
    		 //长时间未操作错误码 TODO
    		 callExternalWithCard('','');
    	 }
     },60000);  
     document.onmousedown = function (){  
     clearInterval(load_time);  
     if(null != down_time){  
         clearInterval(down_time);  
     }  
     down_time = setTimeout(function(){  
    	 if(''!=queryCardBean&&(queryCardBean.code) != 0){
    		 //验卡错误码
    		 callExternalWithCard(queryCardBean.code,'');
    	 }else{
    		 //长时间未操作错误码
    		 callExternalWithCard('','');
    	 }
     },60000);  
 }  

});




var rate = "";
var isflagtrue = "0";
var isflagfalse = "1";//0圈存1非圈存
var singRateType =  '1';//单笔
var permillage = "2";//千分比

var intervalid = "";
var cardCode = "";//卡号
var prdList = "";
var payType = "0";
var merBean = "";
var payWranFlag ="0";//支付提示标志
var queryCardBean = "";
var needPayMoney = "";//支付金额
var productcode="";//产品编号
var apdu = new Array();
var payWayId = "";

function resetPrivateBean(){
	cardCode = null;
	queryCardBean = null;
//	needPayMoney = "";
//	productcode="";
	$("#resultDiv").hide();
	$("#posSpan").hide();
	$("#formRecharge").hide();
	$('.recharge-amount a').removeClass("a-click");
	$('.recharge-amount a').eq(0).addClass('a-click');
	needPayMoney = $('.recharge-amount a').eq(0).attr("money");
	productcode=$('.recharge-amount a').eq(0).attr("proCode");
	$("#orderA").removeClass("a-click");
	apdu = new Array();
}
function toChangeCity(name,code){
	$("#city").empty();
	$("#city").html(name);
	cityCode = code;
	
	window.location.href = $.base+"/external/toChangeCity?cityCode="+cityCode+"&bnscode=01";
}

/**
 * 获取卡信息
 */
function getCardRechargeInfo(data){
	var bean = data.responseEntity;
	merBean = data.responseEntity;
	if(data.code=="000000"){
		getOCX(bean.ocxClassId,bean.ocxVersion,bean.yktCode);

		if(null==merBean.payWranFlag||merBean.payWranFlag==""){
			payWranFlag = 0;
		}else{
			payWranFlag = merBean.payWranFlag;
		}
		$("#merName").html(merBean.merName);
		$("#printCompanyName").html(merBean.yktName);
		cityCode = bean.cityCode;
		bean.cityName;
		payWayId = bean.merPayWayId;
		areaLoad(bean);
		loadFirstWordCity(bean);
		var moneyList = bean.proPriceList;
		prdList = bean.prdProductYktList;
		//充值金额：这里其实指的是公交卡充值产品列表，可以根据基于城市查询公交卡充值产品接口获取数据。
		$("#city").html(bean.cityName);
		checkCardExist();
		var moneyString = '';
		if(null!=prdList){
			$.each(prdList, function(i, value) {
				rate = value.proRate;
				if(i=="0"){
					moneyString+="<a href='javascript:;' class='a-click' proName='"+value.proName+"' proCode='"+value.proCode+"' money='"+value.proPrice/100+"'>"+value.proPrice/100+"<s></s></a>";
					needPayMoney = value.proPrice/100;
					productcode =  value.proCode;
				}else{
					moneyString+="<a href='javascript:;' proCode='"+value.proCode+"' proName='"+value.proName+"' money='"+value.proPrice/100+"'>"+value.proPrice/100+"<s></s></a>";
				}
			});
		}
		$("#proPrice").html(moneyString);
		
		$('.recharge-amount a').click(function(){
			needPayMoney = $(this).attr("money");
			productcode =  $(this).attr("proCode");
			//$("input[name='orderList']:checked").attr("checked",false);
			$('.recharge-amount a').removeClass("a-click");
			$(this).addClass('a-click');//.siblings("a").removeClass("a-click");
		});
	}else{
		callExternalWithCard(data.code);
//		$.messagerBox({type: 'error', title:"信息提示", msg: ""+data.message+""});
//		callExternalWithCard(data.code,"");
	}
}

/**
 * 通知第三方
 */
function callExternalWithCard(code,ordernum){
	var orderNum = ''; 
	if(ordernum!=undefined){
		orderNum = "&orderNum="+ordernum;
	}
	window.location.href = $.base+"/external/toCardRechargeRUL?code="+code+orderNum;
}

/**
 * 验卡查询
 */
function cardInfoBack(rebackParm){
	resetPrivateBean();
	//console.log("回调获取验卡json:"+rebackParm);
	var ocxBean = eval("("+rebackParm+")");
	ocxErrorMsg(ocxBean);
	
	checkCardExist();
}


/**
 * 加载卡信息
 */
function loadCardInfo(ocxBean){
//	var ocxbean = getBackBean();

	//开启定时器
	queryCardBean = ocxBean;
	var cardNum = ocxBean.tradecard;
	apdu = ocxBean.apdu;
//	$("#proList").append(proHtml);
	$("#cardNumWarn").hide();
	//$("#cardMoneyWarn").hide();
	$("#cardMoneyP").show();
	$("#cardSpan").show();
	$("#cardNum").show();
	cardCode=ocxBean.tradecard;
	$("#cardNum").html(cardCode);
	$("#cardNumMessage").show();
	$("#cardMoney").html(Number(ocxBean.befbal/100).toFixed(2));
	
	$("#printPosCode").html(ocxBean.posid);	
	$("#printCardCode").html(ocxBean.tradecard);	
}

/**
 * 圈存取消产品充值金额
 */
function cancelReMoney(){
	$('.recharge-amount a').removeClass("a-click");
	$("#orderA").addClass('a-click');
	needPayMoney = Number($("#orderA").attr('money'));
	productcode="";
}

/**
 * 打开确认充值按钮
 */
function tocardRecharge(){
	showCommodityName();
	//单笔
	$("#rechargeMoney").html(Number(needPayMoney).toFixed(2));
	$("#balance").html($("#cardMoney").text());
	$("#formRecharge").show();
}

/**
 * 获取商户费率后的真实交易金额并判断当前交易是否合法
 * @returns 
 */
function getRealPayMoneyWithRate(){
	//单笔
	if(null==merBean.merRate){
		$.messagerBox({type: 'error', title:"信息提示", msg: "商户一卡通充值费率未找到"});
		return true;
	}else if(merBean.merRate.rateType==singRateType){
		if((Math.ceil(Number(needPayMoney)*100-Number(merBean.merRate.rate))/100).toFixed(2)<0){
			$.messagerBox({type: 'error', title:"信息提示", msg: "商户费率与产品金额冲突"});
			return true;
		}
		$("#realMoneyRes").html((Math.ceil(Number(needPayMoney)*100-Number(merBean.merRate.rate))/100).toFixed(2));
	}else if(merBean.merRate.rateType==permillage){
		if(Number(merBean.merRate.rate)/1000>1){
			$.messagerBox({type: 'error', title:"信息提示", msg: "商户费率不合法"});
			return true;
		}
		$("#realMoneyRes").html((Math.ceil((((1-Number(merBean.merRate.rate)/1000)*Number(needPayMoney))*100).toFixed(2))/100).toFixed(2));
	}
	return false;
}

/**
 * 显示商品名称
 */
function showCommodityName(){
	if($(".a-click").attr("proName")==undefined){
		
		$.each(prdList, function(i, value) {
			if(needPayMoney==value.proPrice/100){
				$('#commodityName').html($("#city").text()+needPayMoney+"元");
			}else{
				$('#commodityName').html($("#city").text()+"自定义产品");
			}
		});
		
		$("#realMoneyDiv").hide();
	}else{
		$("#realMoneyDiv").show();
		$('#commodityName').html($(".a-click").attr("proName"));
	}
	
}

/**
 * 创建订单
 */
function createOrder(){
	clearMoneyRe();
	$("#printConsumeTime").html(formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
	$("#resultDiv").show();
	$("#formRecharge").hide();
	showResultMessage();
	showLoadingIcon();
	clearInterval(intervalid);
	var map = createOrderBean();
	queryBean=JSON.stringify(map);
	//alert("创建订单传送给dll的json:"+queryBean);
	//console.log("创建订单传送给dll的json:"+queryBean);
	try{
		OCXFAPAY.Dodopal_CreateRechargeOrderAsync(queryBean, orderCallBack);
	}catch(e){
		var hideOrder = function (){
			$("#resultDiv").hide();
			location.reload(true); 
		}
		var msge = "创建订单失败";
		if(merBean.isShowErrorMsgBoolean){
			$.messagerBox({type: 'error', title:"信息提示", msg: msge,confirmOnClick:hideOrder});
		}
		//TODO
		
	}
}
/**
 * 创建订单回调执行充值
 */
function orderCallBack(outparm){
	//console.log("调用创建订单后的返回结果为:"+ outparm);
	var bean  = eval("("+outparm+")");
	//更新订单号
	if(parseInt(bean.code) == 0){
		$("#orderNumRes").html(bean.prdordernum);
		$("#printOrderNum").html(bean.prdordernum);
		callExternalWithCard(bean.code,bean.prdordernum);
		return;
	}else{
		$("#resultDiv").hide();
		//faileDiv(bean);
		var hideOrder = function (){
			$("#resultDiv").hide();
			callExternalWithCard(bean.code,"");
//			location.reload(true); 
		}
		var msge = bean.message;
		
		if(bean.code.indexOf("0")==0){
			msge = "服务器繁忙，请稍后再试<font size='2'>(错误码："+bean.code+")</font>";
		}
		if(merBean.isShowErrorMsgBoolean){
			$.messagerBox({type: 'error', title:"信息提示", msg: msge,confirmOnClick:hideOrder});
		}else{
			callExternalWithCard(bean.code,"");
		}
		
		checkCardExist();
	}
}



/**
 * 充值接口回调
 */
function rechargeCallBack(creditJson){
	//console.log("show:"+creditJson);
	resultDivOpen(eval("("+creditJson+")"));
}




/**
 * 检查是否弹出提示div窗口
 */
function checkWarnDiv(){
	if(parseInt(queryCardBean.code) != 0){
		return;
	}
	if($.trim(needPayMoney)==""||cardCode==""){
		if(cardCode==""){
//			$.messagerBox({type: 'error', title:"信息提示", msg: "请贴放卡片"});
			return;
		}
		if($.trim(needPayMoney)==""){
//			$.messagerBox({type: 'error', title:"信息提示", msg: "请选择产品"});
			return;
		}
	}
	if(Number((queryCardBean.befbal/100)+Number(needPayMoney))<=0){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "充值金额必须大于卡内余额"});
		return;
	}
	if(getRealPayMoneyWithRate()){
		return;
	}
//	var bindCard = Number(queryCardBean.befbal)+Number(needPayMoney);
//	if(Number(bindCard)>Number(queryCardBean.cardlimit)){
//		$.messagerBox({type: 'warn', title:"信息提示", msg: "金额超出上限"});
//		return;
//	}
//	if(parseInt(payWranFlag)==0){
//		tocardRecharge();
//	}else{
		createLagOrder();
//	}
}

/**
 * 延迟运行
 */
function createLagOrder(){
	clearMoneyRe();
	$("#resultDiv").show();
	$("#formRecharge").hide();
	showResultMessage();
	showLoadingIcon();
	createOrder();
	//setTimeout(createOrder,1000);//createOrder
}

/**
 * 显示loading
 */
function showLoadingIcon(){
	$('.ul-btn02,.ok-tips,.err-tips').hide();
	$('.wait-result').show();
}

/**
 * 充值结果button
 */
function showResultButton(){
	//$('.wait-result').slideUp(); 
	$('.wait-result').hide();
	$('.ul-btn').slideDown();
}

/**
 * 进行卡充值操作
 */
function showResultMessage(){
	$("#resultDiv").show();
	$("#formRecharge").hide();
	//卡号
	$("#cardNumRes").html(cardCode);
	
	//充值金额
	$("#rechargeMoneyRes").html(Number(needPayMoney).toFixed(2));
	
	$("#printRechargeMoney").html($("#rechargeMoneyRes").text());
	$("#printRealPayMoney").html($("#rechargeMoneyRes").text());
	
	//充值前卡内余额
	$("#befCardMoneyRes").html((Number(queryCardBean.befbal)/100).toFixed(2));
	//充值后卡内余额
	$("#behCardMoneyRes").html((Number(queryCardBean.befbal)/100).toFixed(2));
	//$("#cardMoney").html((queryCardBean.befbal/100)+needPayMoney);
	//订单应收金额
	//$("#orderMoneyRes").html(orderJson.txnamt/100);
	//订单实收金额
	//$("#realOrderMoneyRes").html(needPayMoney);
}

/**
 * 结果div打开
 */
function resultDivOpen(bean){
	try{
		//var outparm = OCXFAPAY.Dodopal_FindCard(queryBean);
		showResultButton();
		if(Number(bean.code) == 0)
		{
			$("#printButton").html("打印");
			successDiv(bean);
		}else{
			$("#printButton").html("取消");
			faileDiv(bean);
		}
		checkCardExist();
	}catch(ex){
		//开启定时器
		clearInterval(intervalid);
		$.messagerBox({type: 'error', title:"信息提示", msg: "请检查是否插入了pos，或使用了非IE浏览器"});
		$("#cardNumMessage").html("请检查是否插入了pos，或使用了非IE浏览器");
		//console.log("BmacInitPsam failure\r\nDetail="+ex.description+"Dodopal_FindCard");
	}
}

/**
 * 确认充值订单
 */
function popclo(obj){
	if($("#rFailMassage").is(":hidden") ){
		$(obj).closest('.pop-win').hide();
		getQueryBackBean();
	}else{
		location.reload(true);   
	}
}



function successDiv(bean){
	//$("#rSuccessMassage").show();
	$('#rSuccessMassage').slideDown();
	//$("#rFailMassage").hide();
//	//订单编号
//	$("#orderNumRes").html(orderJson.prdordernum);
//	//充值金额
//	$("#rechargeMoneyRes").html(Number(orderJson.txnamt)/100); 
//	//充值前卡内余额
//	$("#befCardMoneyRes").html((Number(orderJson.befbal)/100));
//	//充值后卡内余额
//	$("#behCardMoneyRes").html((Number(orderJson.befbal)/100)+(Number(orderJson.txnamt)/100));
	$("#behCardMoneyRes").html(((Number(queryCardBean.befbal)/100)+Number(needPayMoney)).toFixed(2));

	/**
	 * 打印
	 */
	$("#printBefCardMoney").html($("#befCardMoneyRes").text());
	$("#printBehCardMoney").html($("#behCardMoneyRes").text());
	//页面余额
	//$("#cardMoney").html(((Number(queryCardBean.befbal)/100)+Number(needPayMoney)).toFixed(2));
	//订单应收金额
	//$("#orderMoneyRes").html(orderJson.txnamt/100);
	//订单实收金额
	//$("#realOrderMoneyRes").html(orderJson.txnamt/100);
}


function clearMoneyRe(){
	//订单编号
	$("#orderNumRes").html('');
	$("#printOrderNum").html('');
	$("#printRechargeMoney").html('');
	$("#printBefCardMoney").html('');
	$("#printBehCardMoney").html('');
	$("#printRealPayMoney").html('');
	//充值金额
	$("#rechargeMoneyRes").html('');
	//充值前卡内余额
	$("#befCardMoneyRes").html('');
	//充值后卡内余额
	$("#behCardMoneyRes").html('');
	//订单应收金额
	//$("#orderMoneyRes").html('');
	//订单实收金额
	//$("#realOrderMoneyRes").html('');
}


function faileDiv(bean){

	$("#failDivRe").show();
	$("#rSuccessMassage").hide();
	//$("#rFailMassage").show();
	$('#rFailMassage').slideDown();
	//充值金额
	//充值前卡内余额
	$("#befCardMoneyRes").html((Number(queryCardBean.befbal)/100).toFixed(2));
	//充值后卡内余额
	$("#behCardMoneyRes").html((Number(queryCardBean.befbal)/100).toFixed(2));
	
	/**
	 * 打印
	 */
	$("#printBefCardMoney").html($("#befCardMoneyRes").text());
	$("#printBehCardMoney").html($("#behCardMoneyRes").text());
	
	//订单应收金额
	//$("#orderMoneyRes").html(0);
	//订单实收金额
	//$("#realOrderMoneyRes").html(0);
	$("#resultDiv").hide();
	var msge = getOCXMsgStr(bean,"充值失败");
//	if(parseInt(bean.code)==181020||parseInt(bean.code)==181021||parseInt(bean.code)==182021){
//		msge = bean.message;	
//	}else if(parseInt(bean.code) == 181010){
//		msge=bean.message;
//	}else if(parseInt(bean.code) == 181020){
//		msge = "充值失败！";
//	}else{
//		msge = bean.message;
//	}
	if(bean.code.indexOf("0")==0&&parseInt(bean.code)>10000){
		msge = "服务器繁忙，请稍后再试<font size='2'>(错误码："+bean.code+")</font>";
	}
	
	$.messagerBox({type: 'error', title:"信息提示", msg: msge});
}

function toPrinter(){
	try{
		if($("#rFailMassage").is(":hidden") ){
			var str = createPrintMapStr($("#printCompanyName").text());
			ocxPrint(str);
			$("#resultDiv").hide();
			location.reload(true); 
		}else{
			location.reload(true);   
		}
	}catch(ex){
	  //console.log(ex.description);
	}
}



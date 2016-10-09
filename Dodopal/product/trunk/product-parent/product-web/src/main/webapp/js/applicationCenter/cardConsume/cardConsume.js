


var cityCode="";


$(function(){
	ddpAjaxCall({
		url : $.base + "/cardConsume/getCardConsumeInfo",
		data : cityCode,
		successFn : getCardConsumeInfo
	});
	highlightTitle();
//	selectUiInit();
});
var payWayId = "";
var payWranFlag = "0";
var cardCode = "";
var intervalid="";
var noCardMsg = "请贴放卡片";
var trueMoneyMsg = "请输入正确的交易金额";
var merBean ="";
var cardMoney = "";
function getCardConsumeInfo(data){
	var bean = data.responseEntity;
	merBean = data.responseEntity;
	//console.log(merBean);
	if(data.code=="000000"){
		getOCX(bean.ocxClassId,bean.ocxVersion,bean.yktCode);
		getOtherOCX("73E062B2-BF5F-4952-8BE2-29387DFEBC74",
				"1,0,0,2","DDPrint","打印机控件加载失败! -- 请检查浏览器的安全级别设置.","OCXPRRINTER","OCXPRRINTER");
		if(null==merBean.payWranFlag||merBean.payWranFlag==""){
			payWranFlag = 0;
		}else{
			payWranFlag = merBean.payWranFlag;
		}
		$("#merName").html(merBean.merName);
		cityCode = bean.cityCode;
		payWayId = bean.merPayWayId;
		loadDiscount(bean.discountList);
		if(null!=merBean.ddpDiscount){
			$('#ddpDiscount').html(Number(merBean.ddpDiscount.discount/100).toFixed(2));
			$("#printDdpDiscountSpan").html(Number(merBean.ddpDiscount.discount/100).toFixed(2)+"折");
		}else{
			$('#ddpDiscount').html("无");
			$("#printDdpDiscountSpan").html("无折");
		}
		areaLoad(bean);
		var moneyList = bean.proPriceList;
		prdList = bean.prdProductYktList;
		//充值金额：这里其实指的是公交卡充值产品列表，可以根据基于城市查询公交卡充值产品接口获取数据。
		$("#city").html(bean.cityName);
		var moneyString = '';
		
	}else{
//		$.messagerBox({type: 'error', title:"信息提示", msg: ""+data.message+"",confirmOnClick:closeWebPage});
		if(data.code=="999986"){
			$.messagerBox({type: 'error', title:"信息提示", msg: data.message,confirmOnClick:closeWebPage});
		}else{
			$.messagerBox({type: 'error', title:"信息提示", msg: data.message});
		}
	}
}

function resetPrivateBean(){
	var cardCode = null;
	var cardMoney = null;
}

function loadDiscount(discountList){
	var html="";
	html+= "<option selected value='10'>无</option>";
	$(discountList).each(function(i,v){
		html+="<option value='"+v.discount+"'>";
		html+= v.discount;
		html+="</option>";
	});
	$("#discount").append(html);
	selectUiInit();
}

function loadCardInfo(ocxBean){
//	var ocxbean = getBackBean();
	//开启定时器
	queryCardBean = ocxBean;
	apdu = ocxBean.apdu;
//	$("#proList").append(proHtml);
	$("#cardNumWarn").hide();
	//$("#cardMoneyWarn").hide();
	$("#cardMoneyP").show();
	$("#cardSpan").show();
	$("#cardNum").show();
	cardMoney = Number(ocxBean.befbal/100).toFixed(2);
	cardCode=ocxBean.tradecard;//"3300000177712314",//
	$("#cardNum").html(cardCode);
	$("#cardNumMessage").show();
	$("#cardMoney").html(Number(ocxBean.befbal/100).toFixed(2));
}


/**
 * 刷卡消费时打开提示框
 */
function toConsume(){
	
	var money = $("#moneyInput").val();

	if(money==""||Number(money)==0){
		$.messagerBox({type: 'error', title:"信息提示", msg: trueMoneyMsg});
		return;
	}
	$("#payDiscount").html($("#discount").find("option:selected").text());
	if(getRealMoney()==0){
		return;
	}
	if(payWranFlag=="0"){
		$("#infoWarnDiv").show();
		$("#payWayName").html(merBean.yktName);
		$("#sPayMoney").html((Number(money)).toFixed(2));
		$("#payMoney").html(getRealMoney());
	}else{
		sureConsume();
	}
//	sureConsume
//	getQueryBackBeanForConsume();
}

function sureConsume(){
	if(cityCode == '1110'){
		$("#infoWarnDiv").hide();
		var map = createConsumeOrderAndFindBean();
		var orderJson =JSON.stringify(map);
		try{
			OCXFAPAY.Dodopal_ConsumeAsync(orderJson,consumeResultDiv,getCheckCardInfo);
			//HRESULT Dodopal_ConsumeAsync([in]BSTR jsonConsumptionInput , [in]VARIANT checkCardCallBack, [in] VARIANT completeNotify);
			showResultDivNoMoney();
			showLoadingIcon();
		}catch(ex){
			//开启定时器
//			clearInterval(intervalid);
			ocxNothingMsg();
		}
	}else{
		$("#infoWarnDiv").hide();
		var map = createConsumeOrderBean();
		var orderJson =JSON.stringify(map);
		try{
			OCXFAPAY.Dodopal_CreateConsumptionOrderAsync(orderJson,orderCallback);
			showResultDivNoMoney();
			showLoadingIcon();
		}catch(ex){
			//开启定时器
//			clearInterval(intervalid);
			ocxNothingMsg();
		}
	}
}

/**
 * 一卡通消费dll方法改版后验卡返回回调方法
 */
function getCheckCardInfo(bean){
	var data = eval("("+bean+")");
	if(Number(data.code) == 0){
		$("#orderNumRes").html(data.prdordernum);
		
		$("#printOrderNumRes").html($("#orderNumRes").text());
		$("#printConsumeTime").html(formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		
		cardInfoBack(bean);
	}else{
		$("#resultDiv").hide();
		var hideOrder = function (){
			location.reload(true); 
		}
		var msge = getOCXMsgStr(data,"一卡通支付失败！");
		$.messagerBox({type: 'error', title:"信息提示", msg: msge,confirmOnClick:hideOrder});
	}
}

function orderCallback(bean){
	//console.log("获取生单结果json"+bean);
	var data = eval("("+bean+")");
	if(Number(data.code) == 0){
		$("#orderNumRes").html(data.prdordernum);
		
		$("#printOrderNumRes").html($("#orderNumRes").text());
		$("#printConsumeTime").html(formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		var tempBean = createConsumeResultBean(data);
		var jsonStr=JSON.stringify(tempBean);
		OCXFAPAY.Dodopal_ConsumeAsync(jsonStr, consumeResultDiv,cardInfoBack);
	}else{
		$("#resultDiv").hide();
		var hideOrder = function (){
			location.reload(true); 
		}
		
		$.messagerBox({type: 'error', title:"信息提示", msg: data.message,confirmOnClick:hideOrder});

	}
}
var banTradingMsg = "暂时无法进行交易，错误码：";
function consumeResultDiv(bean){
	var data = eval("("+bean+")");
	//console.log("支付结果："+bean);
	if(Number(data.code) == 0){
		$("#afterMoneyLi").show();
		$("#toPrinter").html("打印");
		var money = $("#moneyInput").val();
		$('#rSuccessMassage').slideDown();
		$("#afterCardMoneySpan").html(Number(data.blackamt/100).toFixed(2));//Number(cardMoney-Number(money)).toFixed(2)
		$("#printCardMoneySpan").html(Number(data.blackamt/100).toFixed(2));
		
		//$("#rFailMassage").show();
	}else{
		if(data.code.indexOf("18") == 0){
			msge = data.message;	
			$("#afterMoneyLi").hide();
		}else if(data.code === "020016"){
			msge = banTradingMsg+ "020016";
		}
		var msge = getOCXMsgStr(data,"一卡通支付失败！");
		if(data.code.indexOf("0")==0&&parseInt(data.code)>10000){
			msge = "服务器繁忙，请稍后再试<font size='2'>(错误码："+bean.code+")</font>";;
		}
		if($("#failMessage").text()==""){
			$("#failMessage").html(msge);
		}
		$("#toPrinter").html("取消");
		$("#rSuccessMassage").hide();
		$('#rFailMassage').slideDown();
	}
	showResultButton();
}

function cardInfoBack(rebackParm){
	//console.log("回调获取验卡json:"+rebackParm);
	var ocxBean = eval("("+rebackParm+")");
//	ocxConsumeErrorMsg(ocxBean);
	if(Number(ocxBean.code) != 0){
		//暂时无法进行交易特殊处理错误码
		if(ocxBean.code === "020016"){
			$("#failMessage").html(banTradingMsg+ "020016");
		}else{
			$("#failMessage").html(ocxBean.message);	
		}
	}
	loadCardInfo(ocxBean);                           
	$("#printPosCode").html(ocxBean.posid);
	$("#printCardCode").html(ocxBean.tradecard);	
	toRememberFlag();
	if(Number(ocxBean.code) == 0){
		$("#afterCardUnit").show();
		$("#beforCardUnit").show();
	}
	
	
	$("#beforCardMoneySpan").html(Number(cardMoney).toFixed(2));
	$("#afterCardMoneySpan").html(Number(cardMoney).toFixed(2));
	
	//打印部分
	$("#printRMoneySpan").html($("#rMoneySpan").text());
	$("#printSMoneySpan").html($("#sMoneySpan").text());
	$("#printBeforCardMoneySpan").html($("#beforCardMoneySpan").text());
	
	
	
//	showResultMessage();
//	showLoadingIcon();
}

function toRememberFlag(){
	if(parseInt(payWranFlag)==0){
		if($("#payWarnFlag").prop("checked")){
			payWranFlag = $("#payWarnFlag").val();
			ddpAjaxCall({
				url : $.base + "/cardRecharge/toStopPayWarn",
				data : payWranFlag,
				successFn : function(data){
				}
			});
		}
	}
}
function toChangeCity(name,code){
	$("#city").empty();
	$("#city").html(name);
	cityCode = code;
	window.location.href = $.base + "/cardRecharge/toChangeCity?cityCode="+cityCode+"&bnscode=03";
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
	//$('#waitMsgId').hide();
}
function showResultDivNoMoney(){
	$("#resultDiv").show();
	$("#infoWarnDiv").hide();
	var money = $("#moneyInput").val();
	$("#payMoneySpan").html(getRealMoney());//Number(money).toFixed(2));
	$("#printPayMoneySpan").html($("#payMoneySpan").text());
	$("#printCardCode").html("");	
	$("#cardNum").html("");
	//充值金额
	//实收金额
	$("#rMoneySpan").html(getRealMoney());//Number(money).toFixed(2));
	//应收金额
	$("#sMoneySpan").html((Number(money)).toFixed(2));
	
	
	$("#afterCardUnit").hide();
	$("#beforCardUnit").hide();
	
	$("#beforCardMoneySpan").html();
	$("#afterCardMoneySpan").html();
	
	//打印部分
	$("#printRMoneySpan").html($("#rMoneySpan").text());
	$("#printSMoneySpan").html($("#sMoneySpan").text());
	$("#printBeforCardMoneySpan").html("");
	
}

function showResultMessage(){
	$("#resultDiv").show();
	$("#infoWarnDiv").hide();
	var money = $("#moneyInput").val();
	$("#payMoneySpan").html(getRealMoney());//Number(money).toFixed(2));
	$("#printPayMoneySpan").html($("#payMoneySpan").text());
	
	//充值金额
	//实收金额
	$("#rMoneySpan").html(getRealMoney());
	//应收金额
	$("#sMoneySpan").html((Number(money)).toFixed(2));
	
	$("#beforCardMoneySpan").html(Number(cardMoney).toFixed(2));
	$("#afterCardMoneySpan").html(Number(cardMoney).toFixed(2));
	
	//打印部分
	$("#printRMoneySpan").html($("#rMoneySpan").text());
	$("#printSMoneySpan").html($("#sMoneySpan").text());
	$("#printBeforCardMoneySpan").html($("#beforCardMoneySpan").text());
	
}
function popclo(){
	$("#resultDiv").hide();
	location.reload(true); 
	//$(obj).closest('.pop-win').hide();
}

function cancelOrder(){
	$("#infoWarnDiv").hide();
}

function cancelWarnDiv(){
	$("#warnConsumeDiv").hide();
}
function showRealMoney(){
	var money = $("#moneyInput").val();
	$("#keyDownShowSpan").show();
	$("#keyDownShow").html(getRealMoney());
}
function getRealMoney(){
	var money = $("#moneyInput").val();
	if(null!=merBean.ddpDiscount){
		return (Number(Number($("#discount").val())*Number(money)*Number(merBean.ddpDiscount.discount/100))/100).toFixed(2);
	}else{
		return (Number(Number($("#discount").val())*Number(money)*10)/100).toFixed(2);
	}
}
function toPrinter(){
	try{
		if($("#rFailMassage").is(":hidden")){
			$("#printPayDiscountSpan").html($("#discount").find("option:selected").text()+"折");
			var str = createPrintMapStr();
			ocxPrint(str);
			$("#resultDiv").hide();
			location.reload(true); 
		}else{
			popclo();
		}
	}catch(ex){
	   //console.log(ex.description);
	}
}
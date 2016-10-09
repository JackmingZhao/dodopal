var cityCode="";
$(function(){
	ddpAjaxCall({
		url : "getCardRechargeInfo",
		data : cityCode,
		successFn : getCardRechargeInfo
	});
	highlightTitle();
	$("#bindButton").bind("click", bindCard); 
	
	/*支付方式*/
	$('[js="pay-way-common"]').click(function(){
			$(this).addClass('blue').siblings().removeClass('blue');
			$('[js="pay-way-other"]').removeClass('blue');
			$('[js="pay-list1"]').css('display','block');
			$('[js="pay-way-ul"]').css('display','none');
			$('.pay-way-box').css('display','none');
		})
	$('[js="pay-way-other"]').click(function(){
			$(this).addClass('blue').siblings().removeClass('blue');
			$('[js="pay-way-common"]').removeClass('blue');
			$('[js="pay-list1"]').css('display','none');
			$('[js="pay-way-ul"]').css('display','block');
			$('.pay-way-box').css('display','block');
		})
	var setcity=0;
	$('[js="setCity"]').click(function(event){
		  event.stopPropagation();
		$('.set-city').show();
		setcity=1;
	});
	
	$('body').click(function(){
		if(setcity=1){
			$('.set-city').hide();
		}
	});
	var timer=null;
	


	$('.set-city-list li').click(function(event){
		event.stopPropagation();
		var i=$(this).index();
		$('.set-city-list li').find('a').removeClass('on');
		$(this).find('a').addClass('on');
		$('.set-city-dl').eq(i).show().siblings('.set-city-dl').hide();
	});
	$('.set-city-dl li a').click(function(event){
		event.stopPropagation();
	});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="应用产品"){
			$(this).addClass('cur');
		}
	});
	
$('.recharge-amount a').click(function(){
	$(this).addClass('a-click').siblings("a").removeClass("a-click");
});
  $(".pay-navi-ul li").each(function(i){
  $(".pay-navi-ul li").eq(i).click(function(){
  $(this).addClass("on").siblings("li").removeClass("on");
  $(".pay-way-box ul").eq(i).show().siblings().hide();
  });
 });
	
	
	$('.com-table01 tr:even').find('td').css("background-color",'#f6fafe');
	$('.com-table02 tr:even').find('td').css("background-color",'#f6fafe');
	$('.header-nav ul li').click(function(){
		var i=$(this).index();
		$('.header-nav ul li a').removeClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show();
		$(this).find('a').addClass('on');
	});
	if($('.header-inr-nav ul li a').hasClass('cur')){
		var i=$('.cur').closest('ul').index();
		$('.header-nav ul li a').removeClass('on').eq(i).addClass('on');
		$('.header-inr-nav ul').hide();
		$('.header-inr-nav ul').eq(i).show()
	};
	
});

function toChangeCity(name,code){
	$("#city").empty();
	$("#city").html(name);
	cityCode = code;
	window.location.href = "toChangeCity?cityCode="+cityCode+"&bnscode=01";
}


var isflagtrue = "0";
var isflagfalse = "1";//0圈存1非圈存
var needPayMoney = "";//支付金额
var intervalid = "";
var cardCode = "";//卡号
var prdList = "";
var isMixed = "0";
var payType = "0";
var merBean = "";
var payWranFlag ="0";//支付提示标志
var queryCardBean = "";
var productcode = "";
var apdu = new Array();
var payWayId = "";
var needBankMoney = -1;

/**
 * 重置全局
 */
function resetPrivateBean(){
	cardCode = null;
	queryCardBean = null;
	//更改默认选中产品后，验卡查询不在更新产品的code
//	needPayMoney = "";
//	productcode="";
	//payWayId="";
	//如果账户被选中，不做处理，否则第一个支付方式
	if($("#accountId").is(':checked')){
		//$("input[type='radio'][name='payWayRadio']").eq(1).attr("checked",true);
	}else{
		$("input[type='radio'][name='payWayRadio']").eq(0).attr("checked",true);
	}
	$("#resultDiv").hide();
	$("#formRecharge").hide();
	//圈存的区域隐藏
	$("#posSpan").hide();
	//选择默认产品
	$('.recharge-amount a').removeClass("a-click");
	$('.recharge-amount a').eq(0).addClass('a-click');
	needPayMoney = $('.recharge-amount a').eq(0).attr("money");
	productcode=$('.recharge-amount a').eq(0).attr("proCode");
	//取消圈存
	clickProFn();
	$("#orderA").removeClass("a-click");
	apdu = new Array();
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
		cityCode = bean.cityCode;
		bean.cityName;
		areaLoad(bean);
		loadCommonPayWay(bean);
		loadFirstWordCity(bean);
		var moneyList = bean.proPriceList;
		prdList = bean.prdProductYktList;
		//充值金额：这里其实指的是公交卡充值产品列表，可以根据基于城市查询公交卡充值产品接口获取数据。
		$("#city").html(bean.cityName);
		//getBackBean(createFindJson(bean.merCode,bean.userCode,bean.yktCode,"0",bean.cityCode,"0",bean.userId,bean.merUserType,"http://192.168.1.116:8086/card-web/checkCard"));
		//	intervalid = setInterval("firstPutCard()", 1000);
		checkCardExist(true)
//		setTimeout("checkCardExist(true)",1800);
		var moneyString = '';
		$.each(prdList, function(i, value) {
			if(i=="0"){
				moneyString+="<a href='javascript:;' class='a-click' proName='"+value.proName+"' proCode='"+value.proCode+"' money='"+value.proPrice/100+"'>"+value.proPrice/100+"<s></s></a>";
				needPayMoney = value.proPrice/100;
				productcode =  value.proCode;
				clickProFn();
			}else{
				moneyString+="<a href='javascript:;' proName='"+value.proName+"' proCode='"+value.proCode+"' money='"+value.proPrice/100+"'>"+value.proPrice/100+"<s></s></a>";
			}
		});
		$("#proPrice").html(moneyString);
		//账户余额显示
		if(Number(merBean.availableBalance)==0){
			var accHtml = "<input type='checkbox' disabled id='accountId' onclick='checkAccount();' name=''  value=''  />";
			$("#accountIdDiv").empty();
			$("#accountIdDiv").html(accHtml);
		}else{
			var accHtml = "<input type='radio' checked='true' id='accountId' onclick='checkAccount();' name='payWayRadio'  value=''  />";
			$("#accountIdDiv").empty();
			$("#accountIdDiv").html(accHtml);
			checkAccount();
		}
		
		$('.recharge-amount a').click(function(){
			$("#payWayDiv").show();
			$('.recharge-amount a').removeClass("a-click");
			$(this).addClass('a-click');
//			$(this).addClass('a-click').siblings("a").removeClass("a-click");
			needPayMoney = $(this).attr("money");
			productcode =  $(this).attr("proCode");
			//改变账户的type
			clickProFn();
			
//			$("input[name='orderList']:checked").attr("checked",false);
			
		});
	}else{
		if(data.code=="999986"){
			$.messagerBox({type: 'error', title:"信息提示", msg: data.message,confirmOnClick:closeWebPage});
		}else{
			$.messagerBox({type: 'error', title:"信息提示", msg: data.message});
		}
		$("#cardNumMessage").html(data.message);
//		$.messagerBox({type: 'error', title:"信息提示", msg: ""+data.message+"",confirmOnClick:closeWebPage});
	}
}


/**
 * 产品点击事件
 */
function clickProFn(){
	//圈存订单选中置空
	//$("input[name='orderList']:checked").attr("checked",false);
	//账户余额有钱的情况下
	if(Number(merBean.availableBalance)>0){
		$("#needPaySpan").show();
		//如果余额的钱足够支付
		if(Number(merBean.availableBalance)-Number(needPayMoney)>=0){
			$("#needPayMoney").html(needPayMoney);
//			$("#accountId").attr("type","radio");
//			$("#accountId").attr("name","payWayRadio");
			$("input[type='radio'][name='payWayRadio']").each(function(){
			     this.checked = false;
			});
			var accHtml = "<input type='radio' checked='true' id='accountId' onclick='checkAccount();' name='payWayRadio'  value='"+$("#accountId").val()+"'  />";
			$("#accountIdDiv").empty();
			$("#accountIdDiv").html(accHtml);
//			$("#accountId").prop("type","checkBox");
			needBankMoney=0;
			//清除选中
			$("#payAgainDiv").hide();
			//清除支付的样式 默认选中账户支付
			//hidePayAccount();
		}else{
			
						//否则不够支付
			$("#needPayMoney").html(Number(merBean.availableBalance));
			//还需要支付div打开
			$("#payAgainDiv").show();
			//显示支付金额
			needBankMoney=(Number(needPayMoney)-Number(merBean.availableBalance)).toFixed(2);
			$("#payAgain").html((Number(needPayMoney)-Number(merBean.availableBalance)).toFixed(2));
//			$("#accountId").attr("name","");
//			$("#accountId").attr("type","checkBox");
			var accHtml = "<input type='checkBox' checked='true' id='accountId' onclick='checkAccount();' name=''  value='"+$("#accountId").val()+"'  />";
			$("#accountIdDiv").empty();
			$("#accountIdDiv").html(accHtml);
			$("input[type='radio'][name='payWayRadio']").each(function(i, value){
				if(i==0){
					//$("input[type='radio'][name='payWayRadio']").eq(0).attr("checked",true);
					this.checked = true;
				}else{
					this.checked = false;
				}
			});
//			hidePayAccount();
		}
	}
}
/**
 * 显示账户扣款提示
 */
function showPayAccount(){
	$("#needPaySpan").show();
	$("#accountId").closest('.payway-ul').addClass('payway-ul-click');
	if(needBankMoney>0){
		$("#payAgainDiv").show();
	}
}

/**
 * 取消账户的选中及扣款提示
 */
function hidePayAccount(){
	if(!$("#accountId").is(':checked')){
		$("#needPaySpan").hide();
		$("#payAgainDiv").hide();
		$("#accountId").closest('.payway-ul').removeClass('payway-ul-click');
	}
}


/**
 * 选择账户支付
 */
function checkAccount(){
		if($("#accountId").is(':checked')){
			if(Number(needPayMoney)>0){
				showPayAccount();	
			}
			$("#accountId").closest('.payway-ul').addClass('payway-ul-click');
		}else{
			$("#accountId").closest('.payway-ul').removeClass('payway-ul-click');
			hidePayAccount();
		}
}



function loadCommonPayWay(bean){
	getOtherPayWay();
	if(bean.payCommonWayBean==null){
		$("#payCommonDiv").hide();
		$('[js="pay-way-other"]').addClass('blue')
		$('[js="pay-way-common"]').removeClass('blue');
	}else{
		$("#payWayCommonDiv").show();
		$('[js="pay-way-other"]').removeClass('blue');
		$('[js="pay-way-common"]').addClass('blue');
		var payHtml="";
		$.each(bean.payCommonWayBean,function (i, value){
			var payRate = "";
			if(payRate!=0&&payRate!=""){
				payRate = "服务费"+value.payServiceRate+"‰";
			}
			if(!value.isWeixinGate){//此处过滤掉支付网关为微信支付的支付方式
			payHtml += "<li><label>" +
						"<span class='prompt-span01'>"+payRate+"</span>" +
						"<input type='radio' name='payWayRadio' payName='"+value.payName+"' payRate='"+value.payServiceRate+"'  value='"+value.id+"' payTypeFlag='"+value.payType+"' onclick='hidePayAccount();' />" +
						"<span class='span-img'>" +
						"<img src='"+$.styleUrl+"/product/images/"+value.payLogo+"' disabled title='"+value.payName+"' />" +
					"</span></label></li>";
			}
		});
		$("#payCommonUl").html(payHtml);
		$("#payCommonDiv").show();
		$("#otherPayNav").hide();
		$("#payWayBox").hide();
	}
}

function getOtherPayWay(){
	var param = "";
	ddpAjaxCall({
		url : "getMorPayWay",
		data : param,
		successFn : loadOtherPayWay
	});
}

function loadOtherPayWay(data){
	var bean = data.responseEntity;
	if(data.code=="000000"){
		var bankHtml = "";
		var onlineHtml ="";
		var bankCount = 0;
		$.each(bean,function (i, value){
			var payRate = "";
			if(payRate!=0&&payRate!=""){
				payRate = "服务费"+value.payServiceRate+"‰";
			}
			/**
			 *    ("0", "账户支付"),
   					("2", "在线支付"),
					("3", "银行支付");
			 * 
			 */
			if(!value.isWeixinGate){//此处过滤掉支付网关为微信支付的支付方式
				if(value.payType=="0"){
					$("#accountDiv").show();
					$("#accountMoney").html(Number(merBean.availableBalance).toFixed(2));
					$("#accountId").val(value.id);
				}else if(value.payType=="3"){
					bankCount++;
					bankHtml+="	<li><label><span class='prompt-span01'>"+payRate+"</span>" +
									"<input type='radio' name='payWayRadio' payRate='"+value.payServiceRate+"' value='"+value.id+"' payName='"+value.payName+"' payTypeFlag='"+value.payType+"' onclick='hidePayAccount();' />" +
									"<span class='span-img'>" +
									"<img src='"+$.styleUrl+"/product/images/"+value.payLogo+"' disabled  title='"+value.payName+"' />" +
									"</span></label></li>";
				}else if(value.payType=="2"){
					onlineHtml+="	<li><label><span class='prompt-span01'>"+payRate+"</span>" +
									"<input type='radio' name='payWayRadio' value='"+value.id+"' payRate='"+value.payServiceRate+"' payName='"+value.payName+"' payTypeFlag='"+value.payType+"' onclick='hidePayAccount();'/>" +
									"<span class='span-img'>" +
									"<img src='"+$.styleUrl+"/product/images/"+value.payLogo+"' disabled  title='"+value.payName+"' />" +
									"</span></label></li>";
				}
			}
		});
		if(bankCount>5){
			bankHtml+="<li class='more-link only'><a href='javascript:;'><i class='i-more'></i><span id='markFlag'>更多</span></a></li>";
		}
		$("#payBankList").html(bankHtml);
		$("#payOnLineList").html(onlineHtml);
		$('[js="pay-list"] li:gt(4)').hide();
		$('[js="pay-list"] li.only').show();
		var a=0;
		$('[js="pay-list"] li.only').click(function(){
			if(a==0){
				$('[js="pay-list"] li').show();
				$('.i-more').css({'background-position':-31+'px '+-414+'px'});
				$("#markFlag").html("收起");
				a=1;
			}else{
				$('[js="pay-list"] li:gt(4)').hide();
				$('[js="pay-list"] li.only').show();
				$('.i-more').css({'background-position':-2+'px '+-414+'px'});
				$("#markFlag").html("更多");
				a=0;
			}		
		});
		//联合支付
		if($("#accountId").is(':checked')){
		//$("input[type='radio'][name='payWayRadio']").eq(1).attr("checked",true);
		}else{
			$("input[type='radio'][name='payWayRadio']").eq(0).attr("checked",true);
		}
	}else{
		$.messagerBox({type: 'error', title:"信息提示", msg: ""+data.message+""});
	}
}
/**
 * 
 * @param toTurnFlag 是否直接跳转网关
 */
function toSureOrder(toTurnFlag){
	if($("#orderA").hasClass('a-click')){
		payType = "0";
		createOrder();
		return;
	}
	if(productcode==""){
		//一卡通商品
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请选择充值金额"});
		return;
	}
	if($("#accountId").is(':checked')){
		//选择了账户支付,判断账户资金是否还需额外金额支付
		if(needBankMoney>0){
			payType = "2";
			isMixed = "1";
			openSureDiv("请选择支付方式","联合支付",needPayMoney,toTurnFlag);
			//如果有账户充值，但是金额不足判断，两个，即 账户跟银行是否一起选择
		}else{
			payType="0";
			openSureDiv("请选择支付方式","账户支付",needPayMoney,toTurnFlag);
		}
		//判断选中
	}else{
		//没有选择账户支付
		openSureDiv("请选择支付方式","",needPayMoney,toTurnFlag);
	}
}
function showPayServiceRate(amount){
	
	$('#payServiceRateMoneyLi').show();
	var payRate = $("input[type='radio'][name='payWayRadio']:checked").attr("payRate");
	$('#payServiceRateMoney').html((parseInt((((Number(payRate)/1000)*Number(amount))*100).toFixed(2))/100).toFixed(2));

}


function openSureDiv(warnMsg,title,amount,toTurnFlag){
	$('#payServiceRateMoneyLi').hide();
	var payId = $("input[type='radio'][name='payWayRadio']:checked").val();
	if($("input[type='radio'][name='payWayRadio']:checked").attr("payTypeFlag")=="2"){
		if(title==""){
			title=$("input[type='radio'][name='payWayRadio']:checked").attr("payName");//"第三方支付";
			showPayServiceRate(amount);
			payType="2";
		}
		
	}else if($("input[type='radio'][name='payWayRadio']:checked").attr("payTypeFlag")=="3"){
		if(title==""){
			title=$("input[type='radio'][name='payWayRadio']:checked").attr("payName");//"网银支付";
			showPayServiceRate(amount);
			payType="3";
		}
	}
	if(payId==null){
		$.messagerBox({type: 'warn', title:"信息提示", msg: warnMsg});
	}else if(toTurnFlag){
		//在跳确认提示的时候进行网关跳转处理
		toBankGateway();
	}else{
		$('#commodityName').html($(".a-click").attr("proName"));
		$('#paySureTitle').html(title);
		$('#paySureMoney').html(amount);
		$('[js="qurrenjiner"]').show();
	}
}

//$("#posSpan").show();

/**
 * 验卡查询
 */
function cardInfoBack(rebackParm){
	var ocxBean = eval("("+rebackParm+")");
	checkAccount();
	ocxErrorMsg(ocxBean);
	checkCardExist();
}

/**
 * 加载卡圈存信息
 */
function loadCardInfo(ocxBean){
//	var ocxbean = getBackBean();
	queryCardBean = ocxBean;
	var cardNum = ocxBean.tradecard;//'3300000177712314';//
	ddpAjaxCall({
		url : "../loadOrder/findAvailableLoadOrdersByCardNum",
		data : cardNum,
		successFn : loadOrderList
	});
	apdu = ocxBean.apdu;
//	$("#proList").append(proHtml);
	$("#cardNumWarn").hide();
	//$("#cardMoneyWarn").hide();
	$("#cardMoneyP").show();
	$("#cardSpan").show();
	$("#cardNum").show();
	cardCode=ocxBean.tradecard;//"3300000177712314",//
	$("#cardNum").html(cardCode);
	$("#cardMoney").html(Number(ocxBean.befbal/100).toFixed(2));
}


function loadOrderList(data){
	$("#posSpan").hide();
	var proHtml="";
	$("#orderSpan").empty();
	if(data.code=="000000"){
		var bean = data.responseEntity;
		if(bean.length>0){
			$.each(bean,function (i, value){
				if(cityCode==value.cityCode){
					$("#posSpan").show();
					proHtml += "<a href='javascript:;' id='orderA'  onclick='cancelReMoney();' ordCode='"+value.orderNum+"' money='"+value.chargeAmt/100+"'>"+Number(value.chargeAmt/100)+"<s></s></a>";
					return false;
//					proHtml+="<tr><td><label><input type='radio' name='orderList' value='"+value.orderNum+"' money='"+value.chargeAmt/100+"' onclick='cancelReMoney();'/></label></td>" +
//							"<td>"+value.orderNum+"</td>" +
//							"<td>"+Number(value.chargeAmt/100)+"</td>" +
//							"<td>"+value.merchantName+"</td>" +
//							"</tr>";
				}
			});
		}
	}
	$("#orderSpan").append(proHtml);
}
/**
 * 圈存取消产品充值金额
 */
function cancelReMoney(){
	$('.recharge-amount a').removeClass("a-click");
	$("#orderA").addClass('a-click');
	needPayMoney = Number($("#orderA").attr('money'));
	
	
	productcode="";
	hidePayAccount();
	$("#payWayDiv").hide();
}

/**
 * 打开确认充值按钮
 */
function tocardRecharge(){
	if($.trim(needPayMoney)==""||cardCode==""){
		return;
	}
	$("#rechargeMoney").html(needPayMoney);
	$("#balance").html($("#cardMoney").text());
	$("#formRecharge").show();
}
/**
 * 创建订单
 */
function createOrder(){
	clearInterval(intervalid);
	var myDate = new Date();
	//支付方式id
	payWayId = $("input[type='radio'][name='payWayRadio']:checked").val();
	var map = createOrderBean();
	queryBean=JSON.stringify(map);
	//console.log("创建订单传送给dll的json:"+queryBean);
	if($("#orderA").hasClass('a-click')){
		OCXFAPAY.Dodopal_CreateRechargeOrderAsync(queryBean, loadOrderCallBack);
	}else{
		OCXFAPAY.Dodopal_CreateRechargeOrderAsync(queryBean, orderCallBack);	
	}
}

function loadOrderCallBack(outparm){
	var bean  = eval("("+outparm+")");
	//console.log("调用创建订单后的返回结果为:"+ outparm);
	//更新订单号
	$("#orderNumRes").html(bean.prdordernum);
	
	if(parseInt(bean.code) == 0){
		$('[js="qurrenjiner"]').hide();
		$("#waitResult").show();
		var tempBean = createRechargeBean(bean);
		var jsonStr=JSON.stringify(tempBean);
		//console.log("获取下一步的json:"+jsonStr);
		//toBankGate
		if(parseInt(payWranFlag)==0){
			if($("#payWarnFlag").prop("checked")){
				payWranFlag = "1";
			}
		}
		window.open("userLoadOrderRecharge?orderNum="+tempBean.prdordernum);
		ddpAjaxCall({
			url : "getCardBindInfo",
			data : cardCode,
			successFn : loadCardBindInfoFn
		});
	}else{
		$("#formRecharge").hide();
		var hideOrder = function (){
			
			location.reload(true); 
		}
		var msge = bean.message;
		if(bean.code.indexOf("0")==0){
			msge = "服务器繁忙，请稍后再试<font size='2'>(错误码："+bean.code+")</font>";
		}
		$.messagerBox({type: 'error', title:"信息提示", msg: msge,confirmOnClick:hideOrder});
		checkCardExist();
	}
}

/**
 * 创建订单回调执行充值
 * 非圈存
 */
function orderCallBack(outparm){
	var bean  = eval("("+outparm+")");
	//console.log("调用创建订单后的返回结果为:"+ outparm);
	//更新订单号
	$("#orderNumRes").html(bean.prdordernum);
	
	if(parseInt(bean.code) == 0){
		$('[js="qurrenjiner"]').hide();
		$("#waitResult").show();
		var tempBean = createRechargeBean(bean);
		var jsonStr=JSON.stringify(tempBean);
		//console.log("获取下一步的json:"+jsonStr);
		//toBankGate
		if(parseInt(payWranFlag)==0){
			if($("#payWarnFlag").prop("checked")){
				payWranFlag = "1";
			}
		}
		var payId = $("input[type='radio'][name='payWayRadio']:checked").val();
		window.open("toBankGate?orderCode="+tempBean.prdordernum+"&payWayId="+payId+"&payWranFlag="+payWranFlag+"&isMixed="+isMixed+"&cityCode="+cityCode+"&businessType=01");
		ddpAjaxCall({
			url : "getCardBindInfo",
			data : cardCode,
			successFn : loadCardBindInfoFn
		});
	}else{
		$("#formRecharge").hide();
		var hideOrder = function (){
			location.reload(true); 
		}
		var msge = bean.message;
		if(bean.code.indexOf("0")==0){
			msge = "服务器繁忙，请稍后再试<font size='2'>(错误码："+bean.code+")</font>";
		}
		$.messagerBox({type: 'error', title:"信息提示", msg: msge,confirmOnClick:hideOrder});
	}
}


/**
 * 查找卡片是否绑定信息
 */
function loadCardBindInfoFn(data){
	if("000000"==data.code){
		if(Number(data.responseEntity)==0){
			 $('#regDoneButton').unbind();
			 $("#regDoneButton").one("click",function(){
				 $("#waitResult").hide();
				 $("#bindCardDiv").show();
				 $("#bindCardCode").html(cardCode);
				 //$.messagerBox({type:"confirm", title:"信息提示", msg: "当前充值的卡片尚未绑定，您需要绑定该卡吗？", confirmOnClick: bindCard, param:"",cancelOnClick:cancelBindCard});
			 }); 
		}else{
			 $("#regDoneButton").one("click",function(){
				 closeWebPage();
				// location.reload(true); 
				 }); 
		}
	}
}

/**
 * 绑卡
 */
var bindFlag = false;
function bindCard(){
	//隐藏
	if(bindFlag){
		return;
	}
	
	$("#bindButton").unbind("click"); 
	bindFlag = true;
	var map = {
			"cardCode":cardCode,
			"remarks":$.trim($("#bindCardRemarks").val()),
			"cardCityName":merBean.cityName,
			 "cardType":queryCardBean.cardtype
	}
	 ddpAjaxCall({
			url : "toBindCard",
			data : map,
			async:false,
			successFn : function(data){
				$("#bindCardDiv").hide();
				$.messagerBox({type: 'warn', title:"信息提示", msg:data.message,confirmOnClick: cancelBindCard});
				
				//location.reload(true); 
			}
	 		
		});
//	closeWebPage();
}

function cancelBindCard(){
//	location.reload(true); 
	closeWebPage();
}

/**
 * 充值接口回调
 */
function rechargeCallBack(creditJson){
	//console.log("show:"+creditJson);
	checkCardExist();
	resultDivOpen(eval("("+creditJson+")"));
}




/**
 * 检查是否弹出提示div窗口
 */
function checkWarnDiv(){
	if(Number(queryCardBean.code) != 0){
		return;
	}
	//是否选择产品，及放置卡
	if($.trim(needPayMoney)==""||cardCode==""||cardCode==null){
		if($.trim(needPayMoney)==""){
			$.messagerBox({type: 'warn', title:"信息提示", msg: "请选择产品"});
		}else{
			$.messagerBox({type: 'warn', title:"信息提示", msg: "请链接pos并放置卡片"});
		}
		return;
	}
	if(parseInt(payWranFlag)==0){
		toSureOrder(false);
	}else{
		toSureOrder(true);
	}
}
/**
 * 记住选择
 */
function toRememberFlag(){
	if(parseInt(payWranFlag)==0){
		if($("#payWarnFlag").prop("checked")){
			payWranFlag = $("#payWarnFlag").val();
			ddpAjaxCall({
				url : "toStopPayWarn",
				data : payWranFlag,
				successFn : function(data){
				}
			});
		}
	}
}

/**
 * 网关跳转
 */
function toBankGateway(){
	if($("#payWarnFlag").prop("checked")){
		payWranFlag = $("#payWarnFlag").val();
	}
	$('[js="qurrenjiner"]').hide();
	$("#waitResult").show();
	createOrder();
}

/**
 * 显示loading
 */
function showLoadingIcon(){
	$('.ul-btn,.ok-tips,.err-tips').hide();
	$('.wait-result').show();
}

/**
 * 充值结果button
 */
function showResultButton(){
	$('.wait-result').slideUp();
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
	$("#rechargeMoneyRes").html(needPayMoney);
	//充值前卡内余额
	$("#befCardMoneyRes").html((Number(queryCardBean.befbal)/100));
	//充值后卡内余额
	$("#hidCardMoneyRes").html((Number(queryCardBean.befbal)/100)+Number(needPayMoney));
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
		if(parseInt(bean.code) == 0)
		{
			successDiv(bean);
		}else{
			faileDiv(bean);
		}
		var map = createFindBean();
		queryBean=JSON.stringify(map);
		OCXFAPAY.Dodopal_FindCardAsync(queryBean,toChangeQueryBean);
	}catch(ex){
		clearInterval(intervalid);
		$.messagerBox({type: 'error', title:"信息提示", msg: "请检查是否插入了pos，或使用了非IE浏览器"});
		$("#cardNumMessage").html("请检查是否插入了pos，或使用了非IE浏览器");
		//console.log("BmacInitPsam failure\r\nDetail="+ex.description+"Dodopal_FindCard");
	}
}

function toChangeQueryBean(outparm){
	var bean = eval("("+outparm+")");
	queryCardBean = bean;
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
//	$("#hidCardMoneyRes").html((Number(orderJson.befbal)/100)+(Number(orderJson.txnamt)/100));
//	
	//页面余额
	$("#cardMoney").html($("#hidCardMoneyRes").html());
	//订单应收金额
	//$("#orderMoneyRes").html(orderJson.txnamt/100);
	//订单实收金额
	//$("#realOrderMoneyRes").html(orderJson.txnamt/100);
}


function clearMoneyRe(){
	//订单编号
	$("#orderNumRes").html('');
	//充值金额
	$("#rechargeMoneyRes").html('');
	//充值前卡内余额
	$("#befCardMoneyRes").html('');
	//充值后卡内余额
	$("#hidCardMoneyRes").html('');
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
	$("#befCardMoneyRes").html((Number(queryCardBean.befbal)/100));
	//充值后卡内余额
	$("#hidCardMoneyRes").html((Number(queryCardBean.befbal)/100));
	//订单应收金额
	//$("#orderMoneyRes").html(0);
	//订单实收金额
	//$("#realOrderMoneyRes").html(0);
}
function closeWebPage(){
	 if (navigator.userAgent.indexOf("MSIE") > 0) {
	  if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
	   window.opener = null;
	   window.close();
	  } else {
	   window.open('', '_top');
	   window.top.close();
	  }
	 }
	 else if (navigator.userAgent.indexOf("Firefox") > 0) {
	  window.location.href = 'about:blank ';
	 } else {
	  window.opener = null;
	  window.open('', '_self', '');
	  window.close();
	 }
	}
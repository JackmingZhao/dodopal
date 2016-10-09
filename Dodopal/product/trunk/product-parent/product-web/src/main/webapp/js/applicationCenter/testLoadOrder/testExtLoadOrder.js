
var cityCode="";
var productcode = "";


var needPayMoney = "";//支付金额
var cardCode = "";//卡号
var prdList = "";
var isMixed = "0";
var merBean = "";
var queryCardBean = "";

var apdu = new Array();

$(function(){
	ddpAjaxCall({
		url : $.base+"/testLoadOrder/getCityProductInfo",
		data : cityCode,
		successFn : getCityProductInfo
	});
	
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

/**
 * 切换城市
 * 
 */
function toChangeCity(name,code){
	$("#city").empty();
	$("#city").html(name);
	cityCode = code;
	window.location.href = "toChangeCity?cityCode="+cityCode;
}

/**
 * 城市、产品初期花
 */
function getCityProductInfo(data){
	var bean = data.responseEntity;
	merBean = data.responseEntity;
	if(data.code=="000000"){
		cityCode = bean.cityCode;
		// 城市选项卡生成
		areaLoad(bean);
		loadFirstWordCity(bean);
		// 产品列表
		var moneyList = bean.proPriceList;
		prdList = bean.prdProductYktList;
		//充值金额：这里其实指的是公交卡充值产品列表，可以根据基于城市查询公交卡充值产品接口获取数据。
		$("#city").html(bean.cityName);
		var moneyString = '';
		$.each(prdList, function(i, value) {
//			if(i=="0"){
//				moneyString+="<a href='javascript:;' class='a-click' proName='"+value.proName+"' proCode='"+value.proCode+"' money='"+value.proPrice/100+"'>"+value.proPrice/100+"<s></s></a>";
//				needPayMoney = value.proPrice/100;
//				productcode =  value.proCode;
//			}else{
				moneyString+="<a href='javascript:;' proName='"+value.proName+"' proCode='"+value.proCode+"' money='"+value.proPrice/100+"'>"+value.proPrice/100+"<s></s></a>";
//			}
		});
		$("#proPrice").html(moneyString);
		
		$('.recharge-amount a').click(function(){
			$('.recharge-amount a').removeClass("a-click");
			$(this).addClass('a-click');
			needPayMoney = $(this).attr("money");
			productcode =  $(this).attr("proCode");
			
			$("#loadAmount").attr("disabled","true");
			$("#chooseLoadAmount").removeAttr("checked");
		});
	}else{
		if(data.code=="999986"){
			$.messagerBox({type: 'error', title:"信息提示", msg: data.message,confirmOnClick:closeWebPage});
		}else{
			$.messagerBox({type: 'error', title:"信息提示", msg: data.message});
		}
	}
}

/**
 * 选择自定义输入圈存金额
 */
function chooseLoadAmount(){
	$('.recharge-amount a').removeClass("a-click");
	needPayMoney = "";
	productcode =  "";
	$("#loadAmount").removeAttr("disabled");
	
}


/**
 * 创建订单
 */
function createOrder(){
	cardCode = $("#cardNo").val();
	var extMerCode = $("#extMerCode").val();
	var extOrderNum = $("#extOrderNum").val();
	var extOrderTime = $("#extOrderTime").val();
	if (needPayMoney == "") {
		needPayMoney = $("#loadAmount").val();
		
	}
	ddpAjaxCall({
		url : $.base+"/loadOrder/extBookLoadOrder?sourceOrderNum="+extOrderNum+"&cardNum="+cardCode+"&merchantNum="+extMerCode+"&productNum="+productcode+"&cityCode="+cityCode+"&chargeAmt="+needPayMoney+"&sourceOrderTime="+extOrderTime,
		successFn : function (data){
			alert(data);
			$("#resultXml").html(data);
//			alert(data)
//			$.messagerBox({type: 'error', title:"信息结果", msg: data});
//			alert(data.code)
//			if(data.code=="000000"){
//				$("#waitResult").show();
////				var map = {
////						"payId":payWayId,
////						"prdordernum":data.responseEntity
////				}
////				orderCallback(map);
//			}else{
//				$.messagerBox({type: 'info', title:"信息提示", msg: data.message});
//			}
			
		}
	});
}

function orderCallback(tempBean){
	window.open($.base+"/cardRecharge/toBankGate?orderCode="+tempBean.prdordernum+"&payWayId="+tempBean.payId+"&payWranFlag="+payWranFlag+"&isMixed="+isMixed+"&cityCode="+cityCode+"&businessType=04");
	ddpAjaxCall({
		url : $.base+"/cardRecharge/getCardBindInfo",
		data : cardCode,
		successFn : loadCardBindInfoFn
	});
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
			url : $.base+"/cardRecharge/getCardBindInfo",
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
			msge = "服务器繁忙，请稍后再试";
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
		window.open("toBankGate?orderCode="+tempBean.prdordernum+"&payWayId="+payId+"&payWranFlag="+payWranFlag+"&isMixed="+isMixed+"&cityCode="+cityCode);
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
			msge = "服务器繁忙，请稍后再试";
		}
		$.messagerBox({type: 'error', title:"信息提示", msg: msge,confirmOnClick:hideOrder});
//		checkCardExist();
	}
}


/**
 * 检查是否弹出提示div窗口
 */
function checkWarnDiv(){
	
	
	//是否选择产品，及放置卡
	if(!checkCardNo()){//checkCard返回true 代表校验不通过
		
	}else if($.trim(needPayMoney)==""){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请选择产品"});
	}else if(parseInt(payWranFlag)==0){
		toSureOrder(false);
	}else{
		toSureOrder(true);
	}
}


/**
 * 显示loading
 */
function showLoadingIcon(){
	$('.ul-btn,.ok-tips,.err-tips').hide();
	$('.wait-result').show();
}


function closeWebPage() {
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
			window.opener = null;
			window.close();
		} else {
			window.open('', '_top');
			window.top.close();
		}
	} else if (navigator.userAgent.indexOf("Firefox") > 0) {
		window.location.href = 'about:blank ';
	} else {
		window.opener = null;
		window.open('', '_self', '');
		window.close();
	}
}

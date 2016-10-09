function createConsumeOrderBean(){
	try{
		var crMerCode = "";
		if(null!=merBean.merCode){
			crMerCode = merBean.merCode;
		}
		var money = $("#moneyInput").val();

		var createOrderMap = {
				 "merdiscount":$("#discount").val()+"",
				 "receivableAmt":getIntNumber(money),//应收金额（单位：分）
				 "receivedAmt":getIntNumber(getRealMoney()),//实收金额（单位：分）
				 "mercode":crMerCode,//商户编号
				 "source":"0",//来源
				 "payway":"",//支付方式
				 "userid":merBean.userId,//操作人
				 "citycode":cityCode,
				 "paytype":""//支付类型
		}
		return createOrderMap;
	}catch(e){
		var refresh = function (){
			location.reload(true); 
		}
		$.messagerBox({type: 'error', title:"信息提示", msg: "网络繁忙",confirmOnClick:refresh});
	}
}


function createConsumeResultBean(){
	var crMerCode = "";
	if(null!=merBean.merCode){
		crMerCode = merBean.merCode;
	}
	var money = $("#moneyInput").val();
	var resultMap = {
		 "txnamt":getIntNumber(getRealMoney()),//实收金额（单位：分）
		 "prdordernum":$("#orderNumRes").text(),
		 "mercode":crMerCode,
		 "usercode":merBean.userCode,
		 "source":"0",
		 "userid":merBean.userId
	}
	return resultMap;
}


function createConsumeOrderAndFindBean(){
	try{
		var crMerCode = "";
		if(null!=merBean.merCode){
			crMerCode = merBean.merCode;
		}
		var settldiscount = "";
		var userdiscount = "";
		if(null!=merBean.ddpDiscount){
			userdiscount = Number(merBean.ddpDiscount.discount)+"";
			settldiscount = Number(merBean.ddpDiscount.setDiscount)+"";
		}
		var receivedAmt = getIntNumber(getRealMoney());
		var money = $("#moneyInput").val();
		var createOrderMap = {
				 "txnamt":receivedAmt,//交易金额（单位：分）
				 "merdiscount":$("#discount").val()+"",
				 "prdordernum":$("#orderNumRes").text(),
				 "receivableAmt":getIntNumber(money),//应收金额（单位：分）
				 "receivedAmt":receivedAmt,//实收金额（单位：分）
				 "settldiscount":settldiscount,
				 "userdiscount":userdiscount,
				 "mercode":crMerCode,//商户编号
				 "source":"0",//来源
				 "payway":"",//支付方式
				 "usercode":merBean.userCode,
				 "userid":merBean.userId,//操作人
				 "citycode":cityCode,
				 "paytype":""//支付类型
		}
		return createOrderMap;
	}catch(e){
		var refresh = function (){
			location.reload(true); 
		}
		$.messagerBox({type: 'error', title:"信息提示", msg: "网络繁忙",confirmOnClick:refresh});
	}
}

function createFindBean(){
	var crMerCode = "";
	if(null!=merBean.merCode){
		crMerCode = merBean.merCode;
	}
	var map = {
			"mercode":crMerCode,
			"usercode":merBean.userCode,
			"source":"0",
			"userid":merBean.userId
	};
	return map;
}
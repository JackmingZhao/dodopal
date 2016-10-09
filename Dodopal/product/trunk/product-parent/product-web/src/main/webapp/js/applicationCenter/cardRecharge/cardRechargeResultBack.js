$(function(){
	cardRecharge();
});


function cardRecharge(){
	var map = {
			"mercode":merUserCode,
			"merordercode":"",
			"prdordernum":orderNum
			}
	var jsonStr=JSON.stringify(map);
	//console.log("传给dll："+jsonStr);
	OCXFAPAY.Dodopal_RechargeAsync(jsonStr,rechargeCallBack);
}
function rechargeCallBack(creditJson){
	//console.log("获取充值结果："+creditJson);
	var bean = eval("("+creditJson+")");
	if(parseInt(bean.code) == 0)
	{	
		$("#realMoney").html(Number(txnAmt).toFixed(2));
		$("#rebehindMoney").html((Number(txnAmt)+Number(befbal)).toFixed(2));
		$('#successMassage').slideDown();
		$('#successMassage').show();
		
		$('.wait-result').hide();
		$('.ul-btn').slideDown();
		window.opener.document.getElementById("recResult").value="true";
	}else{
		var msge = getOCXMsgStr(bean,"充值失败");
		if(bean.code.indexOf("0")==0&&parseInt(bean.code)>10000){
			msge = "服务器繁忙，请稍后再试<font size='2'>(错误码："+bean.code+")</font>";
		}
//		else if(parseInt(bean.code) == 181020){
//			msge = "充值失败！<font size='2'>（错误码：181020）</font>";
//		}else if(parseInt(bean.code) == 181010){
//			msge=bean.message;
//		}
		$.messagerBox({type: 'error', title:"信息提示", msg: msge});
		$('#failMessage').slideDown();
		$('#failMessage').show();
		$('.wait-result').hide();
		$('.ul-btn').slideDown();
		window.opener.document.getElementById("recResult").value="false";
	}
}

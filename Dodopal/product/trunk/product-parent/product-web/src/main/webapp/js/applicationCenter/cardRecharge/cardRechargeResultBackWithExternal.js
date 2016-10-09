$(function(){
	cardRecharge();
});
var resCode="";

function cardRecharge(){
	var map = {
			"mercode":merUserCode,
			"merordercode":merordernum,
			"prdordernum":orderNum
			}
	var jsonStr=JSON.stringify(map);
	//console.log("传给dll："+jsonStr);
	OCXFAPAY.Dodopal_RechargeAsync(jsonStr,rechargeCallBack);
	$("#returnPage").click(function (){
		window.location.href = $.base+"/external/toCallRechargeResultPageReturn?orderNum="+orderNum+"&resCode="+resCode;
	});
	
}
function rechargeCallBack(creditJson){
	
	//console.log("获取充值结果："+creditJson);
	var bean = eval("("+creditJson+")");
	resCode = bean.code;
	resMap = {
			"resCode":bean.code,
			"orderNum":orderNum
	};
	ddpAjaxCall({
		url : $.base+"/external/notifyRechargeResult",
		data : resMap,
		successFn : function (){
		}
	});
	timeReturn();
	if(parseInt(bean.code) == 0)
	{	
		$("#realMoney").html(Number(txnAmt).toFixed(2));
		$("#rebehindMoney").html((Number(txnAmt)+Number(befbal)).toFixed(2));
		$('#successMassage').slideDown();
		$('#successMassage').show();
		
		$('.wait-result').hide();
		$('.ul-btn').slideDown();
	}else{
		var msge = "充值失败<font size='2'>（错误码："+bean.code+"）</font>";//——+bean.message;
//		if(bean.code.indexOf("0")==0){
//			msge = "服务器繁忙，请稍后再试";
//		}else if(parseInt(bean.code) == 181020){
//			msge = "充值失败！";
//		}else if(parseInt(bean.code) == 181010){
//			msge=bean.message;
//		}
		if(isShowErrorMsg){
			$.messagerBox({type: 'error', title:"信息提示", msg: msge});
		}
		
		$('#failMessage').slideDown();
		$('#failMessage').show();
		$('.wait-result').hide();
		$('.ul-btn').slideDown();
	}
	
}
var time = 5;
var intervalid ="";
function timeReturn(){
	intervalid = setInterval(function(){
		if(time==0){
			clearInterval(intervalid);
			window.location.href = $.base+"/external/toCallRechargeResultPageReturn?orderNum="+orderNum+"&resCode="+resCode;
		}
		$('#autoReMsg').html(time+"秒后自动返回");
		$('#autoReMsgT').html(time+"秒后自动返回");
//		$("#waitReturnMessage").html(time+"秒后返回商户");
		time--;
		}, 1000); 	                                    
	
	
}
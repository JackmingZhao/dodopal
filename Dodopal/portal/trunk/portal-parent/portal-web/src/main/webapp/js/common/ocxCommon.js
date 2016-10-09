var ocxVersionIsNullMsg = '当前城市尚未开通一卡通充值';

function getOCX(clsId,versionId,yktCode,msg){
	if(""==clsId||""==versionId||""==yktCode||null==clsId||null==versionId){
		return;
	}
	var defaultMsg = "控件加载失败! -- 请检查浏览器的安全级别设置.";
	if(msg!=undefined){
		defaultMsg = msg;
	}
	var oxcStr = '<OBJECT ID="OCXFAPAY"  name="OCXFAPAY" CLASSID="CLSID:'+clsId+'" ';
	oxcStr+=' HEIGHT=0 WIDTH=0 codebase="'+$.ocxUrl+'/'+yktCode+'.CAB#version='+versionId+'" viewastext>';
	oxcStr+='<SPAN STYLE="color:red">'+defaultMsg+'</SPAN></OBJECT>';
	$("body").prepend(oxcStr);
}

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
function refreshPage(){
	location.reload(true); 
}

function getQueryBackBean(){
		if(null==merBean){
			$.messagerBox({type: 'error', title:"信息提示", msg: "网络繁忙",confirmOnClick:refreshPage});
			return;
		}
		if(cityCode==""){
			return;
		}
		//关掉定时器
		clearInterval(intervalid);
		var map = createFindBean();
		queryBean=JSON.stringify(map);
		resetPrivateBean();
		console.log("传送给dll的json:"+queryBean);
	try {
		buttonGrey();
		OCXFAPAY.Dodopal_CheckCardInRechargePhaseAysnc(queryBean,cardInfoBack);
	}catch(ex){
		//开启定时器
		buttonGrey();
		clearInterval(intervalid);
		console.log(merBean.ocxClassId);
		try{
			if(null==merBean.ocxVersion||""==merBean.ocxVersion){
				$.messagerBox({type: 'error', title:"信息提示", msg: ocxVersionIsNullMsg});
				$("#cardNumMessage").html(ocxVersionIsNullMsg);
			}else{
//				var reLoad = function (){
//					location.reload(true); 
//				}
//				$.messagerBox({type: 'error', title:"信息提示", msg: "请检查是否正确安装了控件",confirmOnClick:reLoad});
//				$("#cardNumMessage").html("请检查是否正确安装了控件");
			}
		}catch(e){
			$.messagerBox({type: 'error', title:"信息提示", msg: ocxVersionIsNullMsg});
			$("#cardNumMessage").html(ocxVersionIsNullMsg);
		}
		console.log("Dodopal_CheckCardInRechargePhaseAysnc抛出异常"+ex.description);
	}
}
 var banTradingMsg = "暂时无法进行交易，错误码：";

function ocxErrorMsg(ocxBean){
	buttonGrey();
	if(parseInt(ocxBean.code) == 0){
		loadCardInfo(ocxBean);
		$(".orange-btn-h32").css("background-color","#e47f11");
	}else if(ocxBean.code === "020016"){
		loadCardInfo(ocxBean);
		$("#cardNumWarn").show();
		$("#cardNumMessage").show();
		$("#cardNumMessage").html(banTradingMsg+ "020016");
	}else if(parseInt(ocxBean.code) == 181010){
		errorCard("读卡或写卡错误!");
	}else if(parseInt(ocxBean.code) == 181006){
		errorCard("暂不支持该卡业务!");
	}else if(ocxBean.code.indexOf("18") == 0){
		//18为ocx错误
		errorCard(ocxBean.message);
	}else{
		errorCard(banTradingMsg+ocxBean.code);
	}
	if(ocxBean.code.indexOf("0")==0){
		msge = "服务器繁忙，请稍后再试<font size='2'>(错误码："+ocxBean.code+")</font>";
	}
}

function ocxConsumeErrorMsg(ocxBean){
	if(parseInt(ocxBean.code) == 0){
		loadCardInfo(ocxBean);
	}else if(parseInt(ocxBean.code) == 181010){
		$.messagerBox({type: 'error', title:"信息提示", msg: "读卡或写卡错误!"});
	}else if(parseInt(ocxBean.code) == 181006){
		$.messagerBox({type: 'error', title:"信息提示", msg: "暂不支持该卡业务!"});
	}else if(parseInt(ocxBean.code) == 181002||parseInt(ocxBean.code) == 181003){
		$.messagerBox({type: 'error', title:"信息提示", msg: ocxBean.message});
	}else{
		$.messagerBox({type: 'error', title:"信息提示", msg: ocxBean.message});
	}
}
function errorCard(msg){
	$("#cardNumWarn").show();
	$("#cardNum").hide();
	$("#cardNumMessage").show();
	$("#cardSpan").hide();
	$("#cardMoneyP").hide();
	$("#cardNumMessage").html(msg);
}


function firstPutCard(){
	checkCardExist();
//	setTimeout("getQueryBackBean()",500);
//	getQueryBackBean();
}

function checkCardExist(flag){
	intervalid = setInterval("isStickCard("+flag+")", 1000); 
}
function buttonGrey(){
	$(".orange-btn-h32").css("background-color","#666666");
}
//var i = 0
function tofindCard(isArrived){
//	alert("新卡返回的结果"+isArrived);
	console.log("新卡返回"+isArrived);
	
//	i++;
//	if(i==3){
//		return;
//	}
	if(isArrived==true){
		//如果当前卡存在，而且卡为新卡
		getQueryBackBean();
	}
}
function  newOpen(da){
//	console.log("调用读卡");
	//console.log("新卡返回"+da);
//	alert("强制寻卡返回的结果"+da);
	getQueryBackBean();
}

function isStickCard(flag){
//	if(!confirm("是否新卡方法调用"))
//	 {
//		clearInterval(intervalid);
//		return;
//	 }
	try{
		console.log("验卡");
		if(flag!=undefined){
			if(flag){
				
			//	console.log("检测新卡");
				OCXFAPAY.Dodopal_DetectNewCardArrivedAsync(newOpen);
			}
			return;
		}
		OCXFAPAY.Dodopal_DetectNewCardArrivedAsync(tofindCard);
	}catch(e){
		clearInterval(intervalid);
		if(null==merBean.ocxVersion||""==merBean.ocxVersion){
			$.messagerBox({type: 'error', title:"信息提示", msg: ocxVersionIsNullMsg});
			$("#cardNumMessage").html(ocxVersionIsNullMsg);
		}else{
			intervalid = setInterval("isStickCard()", 3000);
		}
		buttonGrey();
	}
}
function getQueryBackBeanForConsume(){
	try {
		if(cityCode==""){
			return;
		}
		//关掉定时器
		clearInterval(intervalid);
		var map = createFindBean();
		queryBean=JSON.stringify(map);
		console.log("传送给dll的json:"+queryBean);
		OCXFAPAY.Dodopal_InqueryCardAsync(queryBean,cardInfoBack);
	}catch(ex){
		//开启定时器
		clearInterval(intervalid);
		ocxNothingMsg();
	}
}

function ocxNothingMsg(){
	try{
		if(null==merBean.ocxVersion||""==merBean.ocxVersion){
			$.messagerBox({type: 'error', title:"信息提示", msg: ocxVersionIsNullMsg});
			$("#cardNumMessage").html(ocxVersionIsNullMsg);
		}else{
//			$.messagerBox({type: 'error', title:"信息提示", msg: "请检查是否插入了pos，或使用了非IE浏览器"});
//			$("#cardNumMessage").html("请检查是否插入了pos，或使用了非IE浏览器");
		}
	}catch(e){
		$.messagerBox({type: 'error', title:"信息提示", msg: ocxVersionIsNullMsg});
		$("#cardNumMessage").html(ocxVersionIsNullMsg);
	}
}



function createPrintMapStr(title){
	var line = "@1234567890----------------------";
	var printWarnMsg = "";
	var head = "";
	//console.log(title);
	if(title!=undefined){
		head = "@1234567890@1234567890@1234567890@1234567890"+title+"-交易凭证";
		printWarnMsg = "@1234567890客服电话：400-817-1000@1234567890请妥善保管小票";
	}else{
		head = "@1234567890@1234567890@1234567890@1234567890都都宝-交易凭证";
		printWarnMsg = "@1234567890客服电话：400-817-1000@1234567890请妥善保管小票，请及时充值以确@1234567890保您的消费需求";
	}
	var foot = "@1234567890@1234567890@1234567890@1234567890";
	var printTime = "@1234567890打印时间："+formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
	var body = "";
	var merInfo = "";
	$("#printSpan li").each(function(i, value){
		if($(this).text().length>90){
			var arr = $(this).text().split("：");	
			body+="@1234567890"+arr[0]+"：@1234567890"+arr[1];
		}else{
			body+="@1234567890"+$(this).text();
		}
		
	});
	if($("#rFailMassage").is(":hidden") ){
		body+="@1234567890订单状态：交易成功";
	}else{
		body+="@1234567890订单状态：交易失败";
	}
	return head+line+body+line+printTime+printWarnMsg+foot;
}


function ocxPrint(inparam){
	try
	{
	  var errCode = OCXPRRINTER.Printer(inparam);
	 //console.log("打印结果："+errCode);
	}
	catch(ex)
	{	  //console.log(ex.description);
		$.messagerBox({type: 'error', title:"信息提示", msg: "打印出错，请检查打印机或控件安装情况"});

	}
}